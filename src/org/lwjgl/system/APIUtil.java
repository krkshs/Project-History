package org.lwjgl.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.linux.LinuxLibrary;
import org.lwjgl.system.macosx.MacOSXLibrary;
import org.lwjgl.system.windows.WindowsLibrary;

public final class APIUtil {
  public static final PrintStream DEBUG_STREAM = getDebugStream();
  
  private static PrintStream getDebugStream() {
    PrintStream debugStream = System.err;
    Object state = Configuration.DEBUG_STREAM.get();
    if (state instanceof String) {
      try {
        Supplier<PrintStream> factory = Class.forName((String)state).getConstructor().newInstance();
        debugStream = factory.get();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else if (state instanceof Supplier) {
      debugStream = ((Supplier<PrintStream>)state).get();
    } else if (state instanceof PrintStream) {
      debugStream = (PrintStream)state;
    } 
    return debugStream;
  }
  
  public static void apiLog(@Nullable CharSequence msg) {
    if (Checks.DEBUG) {
      DEBUG_STREAM.print("[LWJGL] ");
      DEBUG_STREAM.println(msg);
    } 
  }
  
  public static Optional<String> apiGetManifestValue(String attributeName) {
    URL url = APIUtil.class.getClassLoader().getResource("org/lwjgl/system/APIUtil.class");
    if (url != null) {
      String classURL = url.toString();
      if (classURL.startsWith("jar:"))
        try (InputStream stream = new URL(classURL.substring(0, classURL.lastIndexOf('!') + 1) + '/' + "META-INF/MANIFEST.MF").openStream()) {
          return Optional.ofNullable(new Manifest(stream).getMainAttributes().getValue(attributeName));
        } catch (Exception e) {
          e.printStackTrace(DEBUG_STREAM);
        }  
    } 
    return Optional.empty();
  }
  
  public static String apiFindLibrary(String start, String name) {
    String libName = Platform.get().mapLibraryName(name);
    try (Stream<Path> paths = Files.find(
          Paths.get(start).toAbsolutePath(), Integer.MAX_VALUE, (path, attributes) -> 
          
          (attributes.isRegularFile() && path.getFileName().toString().equals(libName)))) {
      return 
        paths.findFirst()
        .map(Path::toString)
        .orElse(name);
    } catch (IOException e) {
      return name;
    } 
  }
  
  public static SharedLibrary apiCreateLibrary(String name) {
    switch (Platform.get()) {
      case WINDOWS:
        return new WindowsLibrary(name);
      case LINUX:
        return new LinuxLibrary(name);
      case MACOSX:
        return MacOSXLibrary.create(name);
    } 
    throw new IllegalStateException();
  }
  
  public static long apiGetFunctionAddress(FunctionProvider provider, String functionName) {
    long a = provider.getFunctionAddress(functionName);
    if (a == 0L)
      requiredFunctionMissing(functionName); 
    return a;
  }
  
  private static void requiredFunctionMissing(String functionName) {
    if (!Configuration.DISABLE_FUNCTION_CHECKS.get(false))
      throw new NullPointerException("A required function is missing: " + functionName); 
  }
  
  @Nullable
  public static ByteBuffer apiGetMappedBuffer(@Nullable ByteBuffer buffer, long mappedAddress, int capacity) {
    if (buffer != null && MemoryUtil.memAddress(buffer) == mappedAddress && buffer.capacity() == capacity)
      return buffer; 
    return (mappedAddress == 0L) ? null : MemoryUtil.wrap(MemoryUtil.BUFFER_BYTE, mappedAddress, capacity).order(MemoryUtil.NATIVE_ORDER);
  }
  
  public static long apiGetBytes(int elements, int elementShift) {
    return ((long)elements & 0xFFFFFFFFL) << elementShift;
  }
  
  public static long apiCheckAllocation(int elements, long bytes, long maxBytes) {
    if (Checks.DEBUG) {
      if (elements < 0)
        throw new IllegalArgumentException("Invalid number of elements"); 
      if (maxBytes + Long.MIN_VALUE < bytes + Long.MIN_VALUE)
        throw new IllegalArgumentException("The request allocation is too large"); 
    } 
    return bytes;
  }
  
  public static interface Encoder {
    ByteBuffer encode(CharSequence param1CharSequence, boolean param1Boolean);
  }
  
  public static class APIVersion implements Comparable<APIVersion> {
    public final int major;
    
    public final int minor;
    
    @Nullable
    public final String revision;
    
    @Nullable
    public final String implementation;
    
    public APIVersion(int major, int minor) {
      this(major, minor, null, null);
    }
    
    public APIVersion(int major, int minor, @Nullable String revision, @Nullable String implementation) {
      this.major = major;
      this.minor = minor;
      this.revision = revision;
      this.implementation = implementation;
    }
    
    public String toString() {
      StringBuilder sb = new StringBuilder(16);
      sb.append(this.major).append('.').append(this.minor);
      if (this.revision != null)
        sb.append('.').append(this.revision); 
      if (this.implementation != null)
        sb.append(" (").append(this.implementation).append(')'); 
      return sb.toString();
    }
    
    public boolean equals(Object o) {
      if (this == o)
        return true; 
      if (!(o instanceof APIVersion))
        return false; 
      APIVersion that = (APIVersion)o;
      return (this.major == that.major && this.minor == that.major && 
        
        Objects.equals(this.revision, that.revision) && 
        Objects.equals(this.implementation, that.implementation));
    }
    
    public int hashCode() {
      int result = this.major;
      result = 31 * result + this.minor;
      result = 31 * result + ((this.revision != null) ? this.revision.hashCode() : 0);
      result = 31 * result + ((this.implementation != null) ? this.implementation.hashCode() : 0);
      return result;
    }
    
    public int compareTo(APIVersion other) {
      if (this.major != other.major)
        return Integer.compare(this.major, other.major); 
      if (this.minor != other.minor)
        return Integer.compare(this.minor, other.minor); 
      return 0;
    }
  }
  
  @Nullable
  public static APIVersion apiParseVersion(Configuration<?> option) {
    APIVersion version;
    Object state = option.get();
    if (state instanceof String) {
      version = apiParseVersion((String)state, null);
    } else if (state instanceof APIVersion) {
      version = (APIVersion)state;
    } else
      version = null; 
    return version;
  }
  
  public static APIVersion apiParseVersion(String version) {
    return apiParseVersion(version, null);
  }
  
  public static APIVersion apiParseVersion(String version, @Nullable String prefix) {
    String pattern = "([0-9]+)[.]([0-9]+)([.]\\S+)?\\s*(.+)?";
    if (prefix != null)
      pattern = "(?:" + prefix + "\\s+)?" + pattern; 
    Matcher matcher = Pattern.compile(pattern).matcher(version);
    if (!matcher.matches())
      throw new IllegalArgumentException(String.format("Malformed API version string [%s]", version)); 
    return new APIVersion(
        Integer.parseInt(matcher.group(1)), 
        Integer.parseInt(matcher.group(2)), 
        matcher.group(3), 
        matcher.group(4));
  }
  
  public static String apiUnknownToken(int token) {
    return apiUnknownToken("Unknown", token);
  }
  
  public static String apiUnknownToken(String description, int token) {
    return String.format("%s [0x%X]", description, token);
  }
  
  public static Map<Integer, String> apiClassTokens(@Nullable BiPredicate<Field, Integer> filter, @Nullable Map<Integer, String> target, Class<?>... tokenClasses) {
    if (target == null)
      target = new HashMap<>(64); 
    int TOKEN_MODIFIERS = 25;
    for (Class<?> tokenClass : tokenClasses) {
      if (tokenClass != null)
        for (Field field : tokenClass.getDeclaredFields()) {
          if ((field.getModifiers() & TOKEN_MODIFIERS) == TOKEN_MODIFIERS && field.getType() == int.class)
            try {
              Integer value = field.getInt(null);
              if (filter == null || filter.test(field, value)) {
                String name = target.get(value);
                target.put(value, (name == null) ? field.getName() : (name + "|" + field.getName()));
              } 
            } catch (IllegalAccessException e) {} 
        }  
    } 
    return target;
  }
  
  public static long apiArray(MemoryStack stack, long... addresses) {
    PointerBuffer pointers = MemoryUtil.memPointerBuffer(stack.nmalloc(Pointer.POINTER_SIZE, addresses.length << Pointer.POINTER_SHIFT), addresses.length);
    for (long address : addresses)
      pointers.put(address); 
    return pointers.address;
  }
  
  public static long apiArray(MemoryStack stack, ByteBuffer... buffers) {
    PointerBuffer pointers = MemoryUtil.memPointerBuffer(stack.nmalloc(Pointer.POINTER_SIZE, buffers.length << Pointer.POINTER_SHIFT), buffers.length);
    for (ByteBuffer buffer : buffers)
      pointers.put(buffer); 
    return pointers.address;
  }
  
  public static long apiArrayp(MemoryStack stack, ByteBuffer... buffers) {
    long pointers = apiArray(stack, buffers);
    PointerBuffer lengths = stack.mallocPointer(buffers.length);
    for (ByteBuffer buffer : buffers)
      lengths.put((long)buffer.remaining()); 
    return pointers;
  }
  
  public static long apiArray(MemoryStack stack, Encoder encoder, CharSequence... strings) {
    PointerBuffer pointers = stack.mallocPointer(strings.length);
    for (CharSequence s : strings)
      pointers.put(encoder.encode(s, true)); 
    return pointers.address;
  }
  
  public static long apiArrayi(MemoryStack stack, Encoder encoder, CharSequence... strings) {
    PointerBuffer pointers = stack.mallocPointer(strings.length);
    IntBuffer lengths = stack.mallocInt(strings.length);
    for (CharSequence s : strings) {
      ByteBuffer buffer = encoder.encode(s, false);
      pointers.put(buffer);
      lengths.put(buffer.capacity());
    } 
    return pointers.address;
  }
  
  public static long apiArrayp(MemoryStack stack, Encoder encoder, CharSequence... strings) {
    PointerBuffer pointers = stack.mallocPointer(strings.length);
    PointerBuffer lengths = stack.mallocPointer(strings.length);
    for (CharSequence s : strings) {
      ByteBuffer buffer = encoder.encode(s, false);
      pointers.put(buffer);
      lengths.put((long)buffer.capacity());
    } 
    return pointers.address;
  }
  
  public static void apiArrayFree(long pointers, int length) {
    for (int i = length; --i >= 0;)
      MemoryUtil.nmemFree(MemoryUtil.memGetAddress(pointers + Integer.toUnsignedLong(i) * (long)Pointer.POINTER_SIZE)); 
  }
}
