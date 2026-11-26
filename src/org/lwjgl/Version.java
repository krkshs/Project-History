package org.lwjgl;

import org.lwjgl.system.APIUtil;

public final class Version {
  public static final int VERSION_MAJOR = 3;
  
  public static final int VERSION_MINOR = 2;
  
  public static final int VERSION_REVISION = 3;
  
  public static final BuildType BUILD_TYPE = BuildType.STABLE;
  
  private static final String version = String.valueOf(3) + '.' + '\002' + '.' + '\003' + BUILD_TYPE.postfix + ' ' + 

    
    (String)APIUtil.apiGetManifestValue("Implementation-Version").orElse("SNAPSHOT");
  
  public static void main(String[] args) {
    System.out.println(getVersion());
  }
  
  public static String getVersion() {
    return version;
  }
  
  public enum BuildType {
    ALPHA("a"),
    BETA("b"),
    STABLE("");
    
    public final String postfix;
    
    BuildType(String postfix) {
      this.postfix = postfix;
    }
  }
}
