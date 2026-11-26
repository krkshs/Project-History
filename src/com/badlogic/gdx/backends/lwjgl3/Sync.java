package com.badlogic.gdx.backends.lwjgl3;

import org.lwjgl.glfw.GLFW;

class Sync {
  private static final long NANOS_IN_SECOND = 1000000000L;
  
  private long nextFrame = 0L;
  
  private boolean initialised = false;
  
  private RunningAvg sleepDurations = new RunningAvg(10);
  
  private RunningAvg yieldDurations = new RunningAvg(10);
  
  public void sync(int fps) {
    if (fps <= 0)
      return; 
    if (!this.initialised)
      initialise(); 
    try {
      long t1;
      for (long l1 = getTime(); this.nextFrame - l1 > this.sleepDurations.avg(); l1 = t1) {
        Thread.sleep(1L);
        this.sleepDurations.add((t1 = getTime()) - l1);
      } 
      this.sleepDurations.dampenForLowResTicker();
      for (long t0 = getTime(); this.nextFrame - t0 > this.yieldDurations.avg(); t0 = t1) {
        Thread.yield();
        this.yieldDurations.add((t1 = getTime()) - t0);
      } 
    } catch (InterruptedException e) {}
    this.nextFrame = Math.max(this.nextFrame + 1000000000L / (long)fps, getTime());
  }
  
  private void initialise() {
    this.initialised = true;
    this.sleepDurations.init(1000000L);
    this.yieldDurations.init((long)(int)((double)-(getTime() - getTime()) * 1.333D));
    this.nextFrame = getTime();
    String osName = System.getProperty("os.name");
    if (osName.startsWith("Win")) {
      Thread timerAccuracyThread = new Thread(new Runnable() {
            public void run() {
              try {
                Thread.sleep(Long.MAX_VALUE);
              } catch (Exception e) {}
            }
          });
      timerAccuracyThread.setName("LWJGL3 Timer");
      timerAccuracyThread.setDaemon(true);
      timerAccuracyThread.start();
    } 
  }
  
  private long getTime() {
    return (long)(GLFW.glfwGetTime() * 1.0E9D);
  }
  
  private class RunningAvg {
    private final long[] slots;
    
    private int offset;
    
    private static final long DAMPEN_THRESHOLD = 10000000L;
    
    private static final float DAMPEN_FACTOR = 0.9F;
    
    public RunningAvg(int slotCount) {
      this.slots = new long[slotCount];
      this.offset = 0;
    }
    
    public void init(long value) {
      while (this.offset < this.slots.length)
        this.slots[this.offset++] = value; 
    }
    
    public void add(long value) {
      this.slots[this.offset++ % this.slots.length] = value;
      this.offset %= this.slots.length;
    }
    
    public long avg() {
      long sum = 0L;
      for (int i = 0; i < this.slots.length; i++)
        sum += this.slots[i]; 
      return sum / (long)this.slots.length;
    }
    
    public void dampenForLowResTicker() {
      if (avg() > 10000000L)
        for (int i = 0; i < this.slots.length; i++)
          this.slots[i] = (long)((float)this.slots[i] * 0.9F);  
    }
  }
}
