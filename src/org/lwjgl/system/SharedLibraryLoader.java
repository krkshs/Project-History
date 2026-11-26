package org.lwjgl.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import java.util.zip.CRC32;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import org.lwjgl.Version;

final class SharedLibraryLoader {
  private static final Lock EXTRACT_PATH_LOCK = new ReentrantLock();
  
  @Nullable
  @GuardedBy("EXTRACT_PATH_LOCK")
  private static Path extractPath;
  
  static FileChannel load(String name, String filename, URL resource) {
    try {
      Path extractedFile;
      EXTRACT_PATH_LOCK.lock();
      try {
        if (extractPath != null) {
          extractedFile = extractPath.resolve(filename);
        } else {
          extractedFile = getExtractPath(filename, resource);
          if (Platform.get() != Platform.WINDOWS || workaroundJDK8195129(extractedFile))
            initExtractPath(extractPath = extractedFile.getParent()); 
        } 
      } finally {
        EXTRACT_PATH_LOCK.unlock();
      } 
      return extract(extractedFile, resource);
    } catch (Exception e) {
      throw new RuntimeException("\tFailed to extract " + name + " library", e);
    } 
  }
  
  private static void initExtractPath(Path extractPath) {
    String newLibPath = extractPath.toAbsolutePath().toString();
    String libPath = Configuration.LIBRARY_PATH.get();
    if (libPath != null && !libPath.isEmpty())
      newLibPath = newLibPath + File.pathSeparator + libPath; 
    System.setProperty(Configuration.LIBRARY_PATH.getProperty(), newLibPath);
    Configuration.LIBRARY_PATH.set(newLibPath);
  }
  
  private static Path getExtractPath(String filename, URL resource) {
    String override = Configuration.SHARED_LIBRARY_EXTRACT_PATH.get();
    if (override != null)
      return Paths.get(override, filename); 
    String version = Version.getVersion().replace(' ', '-');
    Path tempDirectory = Paths.get(Configuration.SHARED_LIBRARY_EXTRACT_DIRECTORY.get("lwjgl" + System.getProperty("user.name")), version, filename);
    Path root;
    Path file = (root = Paths.get(System.getProperty("java.io.tmpdir"))).resolve(tempDirectory);
    if (canWrite(root, file, resource))
      return file; 
    tempDirectory = Paths.get(Configuration.SHARED_LIBRARY_EXTRACT_DIRECTORY.get("lwjgl"), version, filename);
    file = (root = Paths.get(System.getProperty("user.home"))).resolve(tempDirectory);
    if (canWrite(root, file, resource))
      return file; 
    file = (root = Paths.get("").toAbsolutePath()).resolve(tempDirectory);
    if (canWrite(root, file, resource))
      return file; 
    if (Platform.get() == Platform.WINDOWS) {
      String env = System.getenv("SystemRoot");
      if (env != null) {
        file = (root = Paths.get(env, "Temp")).resolve(tempDirectory);
        if (canWrite(root, file, resource))
          return file; 
      } 
      env = System.getenv("SystemDrive");
      if (env != null) {
        file = (root = Paths.get(env + "/")).resolve(Paths.get("Temp").resolve(tempDirectory));
        if (canWrite(root, file, resource))
          return file; 
      } 
    } 
    try {
      file = Files.createTempDirectory("lwjgl");
      root = file.getParent();
      file = file.resolve(filename);
      if (canWrite(root, file, resource))
        return file; 
    } catch (IOException e) {}
    throw new RuntimeException("Failed to find an appropriate directory to extract the native library");
  }
  
  private static FileChannel extract(Path file, URL resource) throws IOException {
    if (Files.exists(file))
      try (InputStream source = resource.openStream(); 
          InputStream target = Files.newInputStream(file)) {
        if (crc(inputStream) == crc(target)) {
          if (Configuration.DEBUG_LOADER.get(false))
            APIUtil.apiLog(String.format("\tFound at: %s", file)); 
          return lock(file);
        } 
      }  
    APIUtil.apiLog(String.format("    Extracting: %s", resource.getPath()));
    if (extractPath == null)
      APIUtil.apiLog(String.format("            to: %s", file)); 
    Files.createDirectories(file.getParent());
    try (InputStream source = resource.openStream()) {
      Files.copy(source, file, StandardCopyOption.REPLACE_EXISTING);
    } 
    return lock(file);
  }
  
  private static FileChannel lock(Path file) {
    try {
      FileChannel fc = FileChannel.open(file);
      if (fc.tryLock(0L, Long.MAX_VALUE, true) == null) {
        if (Configuration.DEBUG_LOADER.get(false))
          APIUtil.apiLog("\tFile is locked by another process, waiting..."); 
        fc.lock(0L, Long.MAX_VALUE, true);
      } 
      return fc;
    } catch (Exception e) {
      throw new RuntimeException("Failed to lock file.", e);
    } 
  }
  
  private static long crc(InputStream input) throws IOException {
    CRC32 crc = new CRC32();
    byte[] buffer = new byte[8192];
    int n;
    while ((n = input.read(buffer)) != -1)
      crc.update(buffer, 0, n); 
    return crc.getValue();
  }
  
  private static boolean canWrite(Path root, Path file, URL resource) {
    Path testFile;
    if (Files.exists(file)) {
      if (!Files.isWritable(file))
        return false; 
      testFile = file.getParent().resolve(".lwjgl.test");
    } else {
      try {
        Files.createDirectories(file.getParent());
      } catch (IOException ignored) {
        return false;
      } 
      testFile = file;
    } 
    try {
      Files.write(testFile, new byte[0]);
      Files.delete(testFile);
      if (workaroundJDK8195129(file))
        try (FileChannel ignored = extract(file, resource)) {
          System.load(file.toAbsolutePath().toString());
        }  
      return true;
    } catch (Throwable ignored) {
      if (file == testFile)
        canWriteCleanup(root, file); 
      return false;
    } 
  }
  
  private static void canWriteCleanup(Path root, Path file) {
    try {
      Files.deleteIfExists(file);
      Path parent = file.getParent();
      while (!Files.isSameFile(parent, root)) {
        try (Stream<Path> dir = Files.list(parent)) {
          if (dir.findAny().isPresent()) {
            if (dir != null) {
              if (null != null) {
                try {
                
                } catch (Throwable e) {
                  null.addSuppressed(e);
                } 
                break;
              } 
              break;
            } 
            break;
          } 
        } 
        Files.delete(parent);
        parent = parent.getParent();
      } 
    } catch (IOException e) {}
  }
  
  private static boolean workaroundJDK8195129(Path file) {
    return (Platform.get() == Platform.WINDOWS && file.toString().endsWith(".dll"));
  }
}
