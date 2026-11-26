package com.badlogic.gdx.backends.lwjgl3;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import java.io.File;

public final class Lwjgl3Files implements Files {
  public static final String externalPath = System.getProperty("user.home") + File.separator;
  
  public static final String localPath = new File("").getAbsolutePath() + File.separator;
  
  public FileHandle getFileHandle(String fileName, Files.FileType type) {
    return new Lwjgl3FileHandle(fileName, type);
  }
  
  public FileHandle classpath(String path) {
    return new Lwjgl3FileHandle(path, Files.FileType.Classpath);
  }
  
  public FileHandle internal(String path) {
    return new Lwjgl3FileHandle(path, Files.FileType.Internal);
  }
  
  public FileHandle external(String path) {
    return new Lwjgl3FileHandle(path, Files.FileType.External);
  }
  
  public FileHandle absolute(String path) {
    return new Lwjgl3FileHandle(path, Files.FileType.Absolute);
  }
  
  public FileHandle local(String path) {
    return new Lwjgl3FileHandle(path, Files.FileType.Local);
  }
  
  public String getExternalStoragePath() {
    return externalPath;
  }
  
  public boolean isExternalStorageAvailable() {
    return true;
  }
  
  public String getLocalStoragePath() {
    return localPath;
  }
  
  public boolean isLocalStorageAvailable() {
    return true;
  }
}
