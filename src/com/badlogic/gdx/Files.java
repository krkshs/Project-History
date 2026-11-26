package com.badlogic.gdx;

import com.badlogic.gdx.files.FileHandle;

public interface Files {
  FileHandle getFileHandle(String paramString, FileType paramFileType);
  
  FileHandle classpath(String paramString);
  
  FileHandle internal(String paramString);
  
  FileHandle external(String paramString);
  
  FileHandle absolute(String paramString);
  
  FileHandle local(String paramString);
  
  String getExternalStoragePath();
  
  boolean isExternalStorageAvailable();
  
  String getLocalStoragePath();
  
  boolean isLocalStorageAvailable();
  
  public enum FileType {
    Classpath, Internal, External, Absolute, Local;
  }
}
