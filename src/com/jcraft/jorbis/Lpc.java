package com.jcraft.jorbis;

class Lpc {
  Drft fft = new Drft();
  
  int ln;
  
  int m;
  
  static float lpc_from_data(float[] data, float[] lpc, int n, int m) {
    float[] aut = new float[m + 1];
    int j = m + 1;
    while (j-- != 0) {
      float d = 0.0F;
      for (int k = j; k < n; k++)
        d += data[k] * data[k - j]; 
      aut[j] = d;
    } 
    float error = aut[0];
    for (int i = 0; i < m; i++) {
      float r = -aut[i + 1];
      if (error == 0.0F) {
        for (int k = 0; k < m; k++)
          lpc[k] = 0.0F; 
        return 0.0F;
      } 
      for (j = 0; j < i; j++)
        r -= lpc[j] * aut[i - j]; 
      r /= error;
      lpc[i] = r;
      for (j = 0; j < i / 2; j++) {
        float tmp = lpc[j];
        lpc[j] = lpc[j] + r * lpc[i - 1 - j];
        lpc[i - 1 - j] = lpc[i - 1 - j] + r * tmp;
      } 
      if (i % 2 != 0)
        lpc[j] = lpc[j] + lpc[j] * r; 
      error = (float)((double)error * (1.0D - (double)(r * r)));
    } 
    return error;
  }
  
  float lpc_from_curve(float[] curve, float[] lpc) {
    int n = this.ln;
    float[] work = new float[n + n];
    float fscale = (float)(0.5D / (double)n);
    for (int k = 0; k < n; k++) {
      work[k * 2] = curve[k] * fscale;
      work[k * 2 + 1] = 0.0F;
    } 
    work[n * 2 - 1] = curve[n - 1] * fscale;
    n *= 2;
    this.fft.backward(work);
    for (int i = 0, j = n / 2; i < n / 2; ) {
      float temp = work[i];
      work[i++] = work[j];
      work[j++] = temp;
    } 
    return lpc_from_data(work, lpc, n, this.m);
  }
  
  void init(int mapped, int m) {
    this.ln = mapped;
    this.m = m;
    this.fft.init(mapped * 2);
  }
  
  void clear() {
    this.fft.clear();
  }
  
  static float FAST_HYPOT(float a, float b) {
    return (float)Math.sqrt((double)(a * a + b * b));
  }
  
  void lpc_to_curve(float[] curve, float[] lpc, float amp) {
    for (int j = 0; j < this.ln * 2; j++)
      curve[j] = 0.0F; 
    if (amp == 0.0F)
      return; 
    for (int i = 0; i < this.m; i++) {
      curve[i * 2 + 1] = lpc[i] / (4.0F * amp);
      curve[i * 2 + 2] = -lpc[i] / (4.0F * amp);
    } 
    this.fft.backward(curve);
    int l2 = this.ln * 2;
    float unit = (float)(1.0D / (double)amp);
    curve[0] = (float)(1.0D / (double)(curve[0] * 2.0F + unit));
    for (int k = 1; k < this.ln; k++) {
      float real = curve[k] + curve[l2 - k];
      float imag = curve[k] - curve[l2 - k];
      float a = real + unit;
      curve[k] = (float)(1.0D / (double)FAST_HYPOT(a, imag));
    } 
  }
}
