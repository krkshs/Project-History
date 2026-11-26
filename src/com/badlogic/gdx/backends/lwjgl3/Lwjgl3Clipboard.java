package com.badlogic.gdx.backends.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Clipboard;
import org.lwjgl.glfw.GLFW;

public class Lwjgl3Clipboard implements Clipboard {
  public boolean hasContents() {
    String contents = getContents();
    return (contents != null && !contents.isEmpty());
  }
  
  public String getContents() {
    return GLFW.glfwGetClipboardString(((Lwjgl3Graphics)Gdx.graphics).getWindow().getWindowHandle());
  }
  
  public void setContents(String content) {
    GLFW.glfwSetClipboardString(((Lwjgl3Graphics)Gdx.graphics).getWindow().getWindowHandle(), content);
  }
}
