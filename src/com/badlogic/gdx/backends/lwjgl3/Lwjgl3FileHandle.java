package com.badlogic.gdx.backends.lwjgl3;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.File;

public final class Lwjgl3FileHandle extends FileHandle {
  public Lwjgl3FileHandle(String fileName, Files.FileType type) {
    super(fileName, type);
  }
  
  public Lwjgl3FileHandle(File file, Files.FileType type) {
    super(file, type);
  }
  
  public FileHandle child(String name) {
    if (this.file.getPath().length() == 0)
      return new Lwjgl3FileHandle(new File(name), this.type); 
    return new Lwjgl3FileHandle(new File(this.file, name), this.type);
  }
  
  public FileHandle sibling(String name) {
    if (this.file.getPath().length() == 0)
      throw new GdxRuntimeException("Cannot get the sibling of the root."); 
    return new Lwjgl3FileHandle(new File(this.file.getParent(), name), this.type);
  }
  
  public FileHandle parent() {
    File parent = this.file.getParentFile();
    if (parent == null)
      if (this.type == Files.FileType.Absolute) {
        parent = new File("/");
      } else
        parent = new File("");  
    return new Lwjgl3FileHandle(parent, this.type);
  }
  
  public File file() {
    if (this.type == Files.FileType.External)
      return new File(Lwjgl3Files.externalPath, this.file.getPath()); 
    if (this.type == Files.FileType.Local)
      return new File(Lwjgl3Files.localPath, this.file.getPath()); 
    return this.file;
  }
}
