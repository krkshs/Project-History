package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.Array;

public class BSpline<T extends Vector<T>> implements Path<T> {
  private static final float d6 = 0.16666667F;
  
  public T[] controlPoints;
  
  public Array<T> knots;
  
  public int degree;
  
  public boolean continuous;
  
  public int spanCount;
  
  private T tmp;
  
  private T tmp2;
  
  private T tmp3;
  
  public static <T extends Vector<T>> T cubic(T out, float t, T[] points, boolean continuous, T tmp) {
    int n = continuous ? points.length : (points.length - 3);
    float u = t * (float)n;
    int i = (t >= 1.0F) ? (n - 1) : (int)u;
    u -= (float)i;
    return BSpline.<T>cubic(out, i, u, points, continuous, tmp);
  }
  
  public static <T extends Vector<T>> T cubic_derivative(T out, float t, T[] points, boolean continuous, T tmp) {
    int n = continuous ? points.length : (points.length - 3);
    float u = t * (float)n;
    int i = (t >= 1.0F) ? (n - 1) : (int)u;
    u -= (float)i;
    return BSpline.<T>cubic(out, i, u, points, continuous, tmp);
  }
  
  public static <T extends Vector<T>> T cubic(T out, int i, float u, T[] points, boolean continuous, T tmp) {
    int n = points.length;
    float dt = 1.0F - u;
    float t2 = u * u;
    float t3 = t2 * u;
    out.set(points[i]).scl((3.0F * t3 - 6.0F * t2 + 4.0F) * 0.16666667F);
    if (continuous || i > 0)
      out.add(tmp.set(points[(n + i - 1) % n]).scl(dt * dt * dt * 0.16666667F)); 
    if (continuous || i < n - 1)
      out.add(tmp.set(points[(i + 1) % n]).scl((-3.0F * t3 + 3.0F * t2 + 3.0F * u + 1.0F) * 0.16666667F)); 
    if (continuous || i < n - 2)
      out.add(tmp.set(points[(i + 2) % n]).scl(t3 * 0.16666667F)); 
    return out;
  }
  
  public static <T extends Vector<T>> T cubic_derivative(T out, int i, float u, T[] points, boolean continuous, T tmp) {
    int n = points.length;
    float dt = 1.0F - u;
    float t2 = u * u;
    float t3 = t2 * u;
    out.set(points[i]).scl(1.5F * t2 - 2.0F * u);
    if (continuous || i > 0)
      out.add(tmp.set(points[(n + i - 1) % n]).scl(-0.5F * dt * dt)); 
    if (continuous || i < n - 1)
      out.add(tmp.set(points[(i + 1) % n]).scl(-1.5F * t2 + u + 0.5F)); 
    if (continuous || i < n - 2)
      out.add(tmp.set(points[(i + 2) % n]).scl(0.5F * t2)); 
    return out;
  }
  
  public static <T extends Vector<T>> T calculate(T out, float t, T[] points, int degree, boolean continuous, T tmp) {
    int n = continuous ? points.length : (points.length - degree);
    float u = t * (float)n;
    int i = (t >= 1.0F) ? (n - 1) : (int)u;
    u -= (float)i;
    return BSpline.<T>calculate(out, i, u, points, degree, continuous, tmp);
  }
  
  public static <T extends Vector<T>> T derivative(T out, float t, T[] points, int degree, boolean continuous, T tmp) {
    int n = continuous ? points.length : (points.length - degree);
    float u = t * (float)n;
    int i = (t >= 1.0F) ? (n - 1) : (int)u;
    u -= (float)i;
    return BSpline.<T>derivative(out, i, u, points, degree, continuous, tmp);
  }
  
  public static <T extends Vector<T>> T calculate(T out, int i, float u, T[] points, int degree, boolean continuous, T tmp) {
    switch (degree) {
      case 3:
        return BSpline.<T>cubic(out, i, u, points, continuous, tmp);
    } 
    throw new IllegalArgumentException();
  }
  
  public static <T extends Vector<T>> T derivative(T out, int i, float u, T[] points, int degree, boolean continuous, T tmp) {
    switch (degree) {
      case 3:
        return BSpline.<T>cubic_derivative(out, i, u, points, continuous, tmp);
    } 
    throw new IllegalArgumentException();
  }
  
  public BSpline() {}
  
  public BSpline(T[] controlPoints, int degree, boolean continuous) {
    set(controlPoints, degree, continuous);
  }
  
  public BSpline set(T[] controlPoints, int degree, boolean continuous) {
    if (this.tmp == null)
      this.tmp = controlPoints[0].cpy(); 
    if (this.tmp2 == null)
      this.tmp2 = controlPoints[0].cpy(); 
    if (this.tmp3 == null)
      this.tmp3 = controlPoints[0].cpy(); 
    this.controlPoints = controlPoints;
    this.degree = degree;
    this.continuous = continuous;
    this.spanCount = continuous ? controlPoints.length : (controlPoints.length - degree);
    if (this.knots == null) {
      this.knots = new Array<>(this.spanCount);
    } else {
      this.knots.clear();
      this.knots.ensureCapacity(this.spanCount);
    } 
    for (int i = 0; i < this.spanCount; i++)
      this.knots.add(BSpline.<T>calculate(controlPoints[0].cpy(), continuous ? i : (int)((float)i + 0.5F * (float)degree), 0.0F, controlPoints, degree, continuous, this.tmp)); 
    return this;
  }
  
  public T valueAt(T out, float t) {
    int n = this.spanCount;
    float u = t * (float)n;
    int i = (t >= 1.0F) ? (n - 1) : (int)u;
    u -= (float)i;
    return valueAt(out, i, u);
  }
  
  public T valueAt(T out, int span, float u) {
    return BSpline.<T>calculate(out, this.continuous ? span : (span + (int)((float)this.degree * 0.5F)), u, this.controlPoints, this.degree, this.continuous, this.tmp);
  }
  
  public T derivativeAt(T out, float t) {
    int n = this.spanCount;
    float u = t * (float)n;
    int i = (t >= 1.0F) ? (n - 1) : (int)u;
    u -= (float)i;
    return derivativeAt(out, i, u);
  }
  
  public T derivativeAt(T out, int span, float u) {
    return BSpline.<T>derivative(out, this.continuous ? span : (span + (int)((float)this.degree * 0.5F)), u, this.controlPoints, this.degree, this.continuous, this.tmp);
  }
  
  public int nearest(T in) {
    return nearest(in, 0, this.spanCount);
  }
  
  public int nearest(T in, int start, int count) {
    while (start < 0)
      start += this.spanCount; 
    int result = start % this.spanCount;
    float dst = in.dst2(this.knots.get(result));
    for (int i = 1; i < count; i++) {
      int idx = (start + i) % this.spanCount;
      float d = in.dst2(this.knots.get(idx));
      if (d < dst) {
        dst = d;
        result = idx;
      } 
    } 
    return result;
  }
  
  public float approximate(T v) {
    return approximate(v, nearest(v));
  }
  
  public float approximate(T in, int start, int count) {
    return approximate(in, nearest(in, start, count));
  }
  
  public float approximate(T in, int near) {
    T P1, P2, P3;
    int n = near;
    T nearest = this.knots.get(n);
    T previous = this.knots.get((n > 0) ? (n - 1) : (this.spanCount - 1));
    T next = this.knots.get((n + 1) % this.spanCount);
    float dstPrev2 = in.dst2(previous);
    float dstNext2 = in.dst2(next);
    if (dstNext2 < dstPrev2) {
      P1 = nearest;
      P2 = next;
      P3 = in;
    } else {
      P1 = previous;
      P2 = nearest;
      P3 = in;
      n = (n > 0) ? (n - 1) : (this.spanCount - 1);
    } 
    float L1Sqr = P1.dst2(P2);
    float L2Sqr = P3.dst2(P2);
    float L3Sqr = P3.dst2(P1);
    float L1 = (float)Math.sqrt((double)L1Sqr);
    float s = (L2Sqr + L1Sqr - L3Sqr) / (2.0F * L1);
    float u = MathUtils.clamp((L1 - s) / L1, 0.0F, 1.0F);
    return ((float)n + u) / (float)this.spanCount;
  }
  
  public float locate(T v) {
    return approximate(v);
  }
  
  public float approxLength(int samples) {
    float tempLength = 0.0F;
    for (int i = 0; i < samples; i++) {
      this.tmp2.set(this.tmp3);
      valueAt(this.tmp3, (float)i / ((float)samples - 1.0F));
      if (i > 0)
        tempLength += this.tmp2.dst(this.tmp3); 
    } 
    return tempLength;
  }
}
