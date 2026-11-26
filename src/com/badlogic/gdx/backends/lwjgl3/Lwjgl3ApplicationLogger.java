package com.badlogic.gdx.backends.lwjgl3;

import com.badlogic.gdx.ApplicationLogger;

public class Lwjgl3ApplicationLogger implements ApplicationLogger {
  public void log(String tag, String message) {
    System.out.println("[" + tag + "] " + message);
  }
  
  public void log(String tag, String message, Throwable exception) {
    System.out.println("[" + tag + "] " + message);
    exception.printStackTrace(System.out);
  }
  
  public void error(String tag, String message) {
    System.err.println("[" + tag + "] " + message);
  }
  
  public void error(String tag, String message, Throwable exception) {
    System.err.println("[" + tag + "] " + message);
    exception.printStackTrace(System.err);
  }
  
  public void debug(String tag, String message) {
    System.out.println("[" + tag + "] " + message);
  }
  
  public void debug(String tag, String message, Throwable exception) {
    System.out.println("[" + tag + "] " + message);
    exception.printStackTrace(System.out);
  }
}
