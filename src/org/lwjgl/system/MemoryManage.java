package org.lwjgl.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;
import org.lwjgl.system.dyncall.DynCallback;
import org.lwjgl.system.libc.LibCStdlib;

final class MemoryManage {
  static MemoryUtil.MemoryAllocator getInstance() {
    Object allocator = Configuration.MEMORY_ALLOCATOR.get();
    if (allocator instanceof MemoryUtil.MemoryAllocator)
      return (MemoryUtil.MemoryAllocator)allocator; 
    if (!"system".equals(allocator)) {
      String className;
      if (allocator == null || "jemalloc".equals(allocator)) {
        className = "org.lwjgl.system.jemalloc.JEmallocAllocator";
      } else if ("rpmalloc".equals(allocator)) {
        className = "org.lwjgl.system.rpmalloc.RPmallocAllocator";
      } else
        className = allocator.toString(); 
      try {
        Class<?> allocatorClass = Class.forName(className);
        return allocatorClass.getConstructor().newInstance();
      } catch (Throwable t) {
        if (Checks.DEBUG && allocator != null)
          t.printStackTrace(APIUtil.DEBUG_STREAM); 
        APIUtil.apiLog(String.format("Warning: Failed to instantiate memory allocator: %s. Using the system default.", className));
      } 
    } 
    return new StdlibAllocator();
  }
  
  private static class StdlibAllocator implements MemoryUtil.MemoryAllocator {
    private StdlibAllocator() {}
    
    public long getMalloc() {
      return MemoryAccessJNI.malloc;
    }
    
    public long getCalloc() {
      return MemoryAccessJNI.calloc;
    }
    
    public long getRealloc() {
      return MemoryAccessJNI.realloc;
    }
    
    public long getFree() {
      return MemoryAccessJNI.free;
    }
    
    public long getAlignedAlloc() {
      return MemoryAccessJNI.aligned_alloc;
    }
    
    public long getAlignedFree() {
      return MemoryAccessJNI.aligned_free;
    }
    
    public long malloc(long size) {
      return LibCStdlib.nmalloc(size);
    }
    
    public long calloc(long num, long size) {
      return LibCStdlib.ncalloc(num, size);
    }
    
    public long realloc(long ptr, long size) {
      return LibCStdlib.nrealloc(ptr, size);
    }
    
    public void free(long ptr) {
      LibCStdlib.nfree(ptr);
    }
    
    public long aligned_alloc(long alignment, long size) {
      return LibCStdlib.naligned_alloc(alignment, size);
    }
    
    public void aligned_free(long ptr) {
      LibCStdlib.naligned_free(ptr);
    }
  }
  
  static class DebugAllocator implements MemoryUtil.MemoryAllocator {
    private static final ConcurrentMap<Long, Allocation> ALLOCATIONS = new ConcurrentHashMap<>();
    
    private static final ConcurrentMap<Long, String> THREADS = new ConcurrentHashMap<>();
    
    private final MemoryUtil.MemoryAllocator allocator;
    
    private final long[] callbacks;
    
    DebugAllocator(MemoryUtil.MemoryAllocator allocator) {
      this.allocator = allocator;
      this























































        
        .callbacks = new long[] { new CallbackI.P() {
            public String getSignature() {
              return "(p)p";
            }
            
            public long callback(long args) {
              long size = DynCallback.dcbArgPointer(args);
              return DebugAllocator.this.malloc(size);
            }
          }.address(), new CallbackI.P() {
            public String getSignature() {
              return "(pp)p";
            }
            
            public long callback(long args) {
              long num = DynCallback.dcbArgPointer(args);
              long size = DynCallback.dcbArgPointer(args);
              return DebugAllocator.this.calloc(num, size);
            }
          }.address(), new CallbackI.P() {
            public String getSignature() {
              return "(pp)p";
            }
            
            public long callback(long args) {
              long ptr = DynCallback.dcbArgPointer(args);
              long size = DynCallback.dcbArgPointer(args);
              return DebugAllocator.this.realloc(ptr, size);
            }
          }.address(), new CallbackI.V() {
            public String getSignature() {
              return "(p)v";
            }
            
            public void callback(long args) {
              long ptr = DynCallback.dcbArgPointer(args);
              DebugAllocator.this.free(ptr);
            }
          }.address(), new CallbackI.P() {
            public String getSignature() {
              return "(pp)p";
            }
            
            public long callback(long args) {
              long alignment = DynCallback.dcbArgPointer(args);
              long size = DynCallback.dcbArgPointer(args);
              return DebugAllocator.this.aligned_alloc(alignment, size);
            }
          }.address(), new CallbackI.V() {
            public String getSignature() {
              return "(p)v";
            }
            
            public void callback(long args) {
              long ptr = DynCallback.dcbArgPointer(args);
              DebugAllocator.this.aligned_free(ptr);
            }
          }.address() };
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
              for (long callback : this.callbacks)
                Callback.free(callback); 
              if (ALLOCATIONS.isEmpty())
                return; 
              for (Map.Entry<Long, Allocation> entry : ALLOCATIONS.entrySet()) {
                Long address = entry.getKey();
                Allocation allocation = entry.getValue();
                APIUtil.DEBUG_STREAM.format("[LWJGL] %d bytes leaked, thread %d (%s), address: 0x%s\n", allocation.size, 
                    
                    allocation.threadId, 
                    
                    THREADS.get(Long.valueOf(allocation.threadId)), 
                    Long.toHexString(address).toUpperCase());
                for (Object el : allocation.stackTrace)
                  APIUtil.DEBUG_STREAM.format("\tat %s\n", el.toString()); 
              } 
            }));
    }
    
    public long getMalloc() {
      return this.callbacks[0];
    }
    
    public long getCalloc() {
      return this.callbacks[1];
    }
    
    public long getRealloc() {
      return this.callbacks[2];
    }
    
    public long getFree() {
      return this.callbacks[3];
    }
    
    public long getAlignedAlloc() {
      return this.callbacks[4];
    }
    
    public long getAlignedFree() {
      return this.callbacks[5];
    }
    
    public long malloc(long size) {
      return track(this.allocator.malloc(size), size);
    }
    
    public long calloc(long num, long size) {
      return track(this.allocator.calloc(num, size), num * size);
    }
    
    public long realloc(long ptr, long size) {
      long oldSize = untrack(ptr);
      long address = this.allocator.realloc(ptr, size);
      if (address != 0L) {
        track(address, size);
      } else if (size != 0L) {
        track(ptr, oldSize);
      } 
      return address;
    }
    
    public void free(long ptr) {
      untrack(ptr);
      this.allocator.free(ptr);
    }
    
    public long aligned_alloc(long alignment, long size) {
      return track(this.allocator.aligned_alloc(alignment, size), size);
    }
    
    public void aligned_free(long ptr) {
      untrack(ptr);
      this.allocator.aligned_free(ptr);
    }
    
    static long track(long address, long size) {
      if (address != 0L) {
        Thread t = Thread.currentThread();
        Long threadId = t.getId();
        if (!THREADS.containsKey(threadId))
          THREADS.put(threadId, t.getName()); 
        Allocation allocation = ALLOCATIONS.put(address, new Allocation(StackWalkUtil.stackWalkGetTrace(), size));
        if (allocation != null)
          throw new IllegalStateException("The memory address specified is already being tracked: 0x" + Long.toHexString(address).toUpperCase()); 
      } 
      return address;
    }
    
    static long untrack(long address) {
      if (address == 0L)
        return 0L; 
      Allocation allocation = ALLOCATIONS.remove(Long.valueOf(address));
      if (allocation == null)
        throw new IllegalStateException("The memory address specified is not being tracked: 0x" + Long.toHexString(address).toUpperCase()); 
      return allocation.size;
    }
    
    private static class Allocation {
      private final Object[] stackTrace;
      
      @Nullable
      private StackTraceElement[] elements;
      
      final long size;
      
      final long threadId;
      
      Allocation(Object[] stackTrace, long size) {
        this.stackTrace = stackTrace;
        this.size = size;
        this.threadId = Thread.currentThread().getId();
      }
      
      private StackTraceElement[] getElements() {
        if (this.elements == null)
          this.elements = StackWalkUtil.stackWalkArray(this.stackTrace); 
        return this.elements;
      }
      
      public boolean equals(Object o) {
        if (this == o)
          return true; 
        if (o == null || getClass() != o.getClass())
          return false; 
        Allocation that = (Allocation)o;
        return Arrays.equals(getElements(), that.getElements());
      }
      
      public int hashCode() {
        return Arrays.hashCode(getElements());
      }
    }
    
    static void report(MemoryUtil.MemoryAllocationReport report) {
      for (Map.Entry<Long, Allocation> entry : ALLOCATIONS.entrySet()) {
        Allocation allocation = entry.getValue();
        report.invoke(entry.getKey(), allocation.size, allocation.threadId, THREADS.get(Long.valueOf(allocation.threadId)), allocation.getElements());
      } 
    }
    
    private static <T> void aggregate(T t, long size, Map<T, AtomicLong> map) {
      AtomicLong node = map.computeIfAbsent(t, k -> new AtomicLong());
      node.set(node.get() + size);
    }
    
    static void report(MemoryUtil.MemoryAllocationReport report, MemoryUtil.MemoryAllocationReport.Aggregate groupByStackTrace, boolean groupByThread) {
      long total;
      Map<StackTraceElement, AtomicLong> mapMethod;
      Map<Allocation, AtomicLong> mapStackTrace;
      switch (groupByStackTrace) {
        case ALL:
          if (groupByThread) {
            Map<Long, AtomicLong> mapThread = new HashMap<>();
            for (Allocation allocation : ALLOCATIONS.values())
              aggregate(allocation.threadId, allocation.size, mapThread); 
            for (Map.Entry<Long, AtomicLong> entry : mapThread.entrySet())
              report.invoke(0L, entry.getValue().get(), entry.getKey(), THREADS.get(entry.getKey()), null); 
            break;
          } 
          total = 0L;
          for (Allocation allocation : ALLOCATIONS.values())
            total += allocation.size; 
          report.invoke(0L, total, 0L, null, null);
          break;
        case GROUP_BY_METHOD:
          if (groupByThread) {
            Map<Long, Map<StackTraceElement, AtomicLong>> mapThreadMethod = new HashMap<>();
            for (Allocation allocation : ALLOCATIONS.values()) {
              Map<StackTraceElement, AtomicLong> map = mapThreadMethod.computeIfAbsent(allocation.threadId, k -> new HashMap());
              aggregate(allocation.getElements()[0], allocation.size, map);
            } 
            for (Map.Entry<Long, Map<StackTraceElement, AtomicLong>> tms : mapThreadMethod.entrySet()) {
              long threadId = tms.getKey();
              Map<StackTraceElement, AtomicLong> mapmapMethod = tms.getValue();
              for (Map.Entry<StackTraceElement, AtomicLong> ms : mapmapMethod.entrySet())
                report.invoke(0L, ms.getValue().get(), threadId, THREADS.get(Long.valueOf(threadId)), ms.getKey()); 
            } 
            break;
          } 
          mapMethod = new HashMap<>();
          for (Allocation allocation : ALLOCATIONS.values())
            aggregate(allocation.getElements()[0], allocation.size, mapMethod); 
          for (Map.Entry<StackTraceElement, AtomicLong> ms : mapMethod.entrySet())
            report.invoke(0L, ms.getValue().get(), 0L, null, ms.getKey()); 
          break;
        case GROUP_BY_STACKTRACE:
          if (groupByThread) {
            Map<Long, Map<Allocation, AtomicLong>> mapThreadStackTrace = new HashMap<>();
            for (Allocation allocation : ALLOCATIONS.values()) {
              Map<Allocation, AtomicLong> map = mapThreadStackTrace.computeIfAbsent(allocation.threadId, k -> new HashMap());
              aggregate(allocation, allocation.size, map);
            } 
            for (Map.Entry<Long, Map<Allocation, AtomicLong>> tss : mapThreadStackTrace.entrySet()) {
              long threadId = tss.getKey();
              Map<Allocation, AtomicLong> map = tss.getValue();
              for (Map.Entry<Allocation, AtomicLong> ss : map.entrySet())
                report.invoke(0L, ss.getValue().get(), threadId, THREADS.get(Long.valueOf(threadId)), ss.getKey().getElements()); 
            } 
            break;
          } 
          mapStackTrace = new HashMap<>();
          for (Allocation allocation : ALLOCATIONS.values())
            aggregate(allocation, allocation.size, mapStackTrace); 
          for (Map.Entry<Allocation, AtomicLong> ss : mapStackTrace.entrySet())
            report.invoke(0L, ss.getValue().get(), 0L, null, ss.getKey().getElements()); 
          break;
      } 
    }
  }
}
