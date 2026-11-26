package com.badlogic.gdx.utils;

public class PauseableThread extends Thread {
  final Runnable runnable;
  
  boolean paused = false;
  
  boolean exit = false;
  
  public PauseableThread(Runnable runnable) {
    this.runnable = runnable;
  }
  
  public void run() {
    while (true) {
      synchronized (this) {
        while (true) {
          try {
            if (this.paused) {
              wait();
              continue;
            } 
          } catch (InterruptedException e) {
            e.printStackTrace();
          } 
          break;
        } 
      } 
      if (this.exit)
        return; 
      this.runnable.run();
    } 
  }
  
  public void onPause() {
    this.paused = true;
  }
  
  public void onResume() {
    synchronized (this) {
      this.paused = false;
      notifyAll();
    } 
  }
  
  public boolean isPaused() {
    return this.paused;
  }
  
  public void stopThread() {
    this.exit = true;
    if (this.paused)
      onResume(); 
  }
}
