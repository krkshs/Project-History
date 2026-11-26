package org.lwjgl.system;

import java.util.function.Function;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public enum Platform {
  LINUX("Linux", "linux") {
    private final Pattern SO;
    
    String mapLibraryName(String name) {
      if (this.SO.matcher(name).find())
        return name; 
      return System.mapLibraryName(name);
    }
  },
  MACOSX("macOS", "macos") {
    private final Pattern DYLIB;
    
    String mapLibraryName(String name) {
      if (this.DYLIB.matcher(name).find())
        return name; 
      return System.mapLibraryName(name);
    }
  },
  WINDOWS("Windows", "windows") {
    String mapLibraryName(String name) {
      if (name.endsWith(".dll"))
        return name; 
      return System.mapLibraryName(name);
    }
  };
  
  public enum Architecture {
    X64(true),
    X86(false),
    ARM64(true),
    ARM32(false);
    
    static final Architecture current;
    
    final boolean is64Bit;
    
    static {
      String osArch = System.getProperty("os.arch");
      boolean is64Bit = (osArch.contains("64") || osArch.startsWith("armv8"));
      current = (osArch.startsWith("arm") || osArch.startsWith("aarch64")) ? (is64Bit ? ARM64 : ARM32) : (is64Bit ? X64 : X86);
    }
    
    Architecture(boolean is64Bit) {
      this.is64Bit = is64Bit;
    }
  }
  
  static {
    String osName = System.getProperty("os.name");
    if (osName.startsWith("Windows")) {
      current = WINDOWS;
    } else if (osName.startsWith("Linux") || osName.startsWith("FreeBSD") || osName.startsWith("SunOS") || osName.startsWith("Unix")) {
      current = LINUX;
    } else if (osName.startsWith("Mac OS X") || osName.startsWith("Darwin")) {
      current = MACOSX;
    } else
      throw new LinkageError("Unknown platform: " + osName); 
  }
  
  private static final Function<String, String> bundledLibraryNameMapper = getMapper(
      Configuration.BUNDLED_LIBRARY_NAME_MAPPER.get("default"), name -> 
      name, name -> 
      Architecture.current.is64Bit ? name : (name + "32"));
  
  private static final Function<String, String> bundledLibraryPathMapper = getMapper(
      Configuration.BUNDLED_LIBRARY_PATH_MAPPER.get("default"), name -> 
      current.nativePath + "/" + Architecture.current.name().toLowerCase() + "/" + name, name -> 
      name.substring(name.lastIndexOf('/')));
  
  private static final Platform current;
  
  private final String name;
  
  private final String nativePath;
  
  Platform(String name, String nativePath) {
    this.name = name;
    this.nativePath = nativePath;
  }
  
  public String getName() {
    return this.name;
  }
  
  public static Platform get() {
    return current;
  }
  
  public static Architecture getArchitecture() {
    return Architecture.current;
  }
  
  public static String mapLibraryNameBundled(String name) {
    return bundledLibraryNameMapper.apply(name);
  }
  
  static String mapLibraryPathBundled(String name) {
    return bundledLibraryPathMapper.apply(name);
  }
  
  private static Function<String, String> getMapper(@Nullable Object mapper, Function<String, String> defaultMapper, Function<String, String> legacyMapper) {
    if (mapper == null || "default".equals(mapper))
      return defaultMapper; 
    if ("legacy".equals(mapper))
      return legacyMapper; 
    if (mapper instanceof Function)
      return (Function<String, String>)mapper; 
    String className = mapper.toString();
    try {
      return 
        Class.forName(className)
        .getConstructor()
        .newInstance();
    } catch (Throwable t) {
      if (Checks.DEBUG)
        t.printStackTrace(APIUtil.DEBUG_STREAM); 
      APIUtil.apiLog(String.format("Warning: Failed to instantiate bundled library mapper: %s. Using the default.", className));
      return defaultMapper;
    } 
  }
  
  abstract String mapLibraryName(String paramString);
}
