package com.badlogic.gdx;

public interface ApplicationLogger {
  void log(String paramString1, String paramString2);
  
  void log(String paramString1, String paramString2, Throwable paramThrowable);
  
  void error(String paramString1, String paramString2);
  
  void error(String paramString1, String paramString2, Throwable paramThrowable);
  
  void debug(String paramString1, String paramString2);
  
  void debug(String paramString1, String paramString2, Throwable paramThrowable);
}
