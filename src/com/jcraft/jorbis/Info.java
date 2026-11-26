package com.jcraft.jorbis;

import com.jcraft.jogg.Buffer;
import com.jcraft.jogg.Packet;

public class Info {
  private static final int OV_EBADPACKET = -136;
  
  private static final int OV_ENOTAUDIO = -135;
  
  private static byte[] _vorbis = "vorbis".getBytes();
  
  private static final int VI_TIMEB = 1;
  
  private static final int VI_FLOORB = 2;
  
  private static final int VI_RESB = 3;
  
  private static final int VI_MAPB = 1;
  
  private static final int VI_WINDOWB = 1;
  
  public int version;
  
  public int channels;
  
  public int rate;
  
  int bitrate_upper;
  
  int bitrate_nominal;
  
  int bitrate_lower;
  
  int[] blocksizes = new int[2];
  
  int modes;
  
  int maps;
  
  int times;
  
  int floors;
  
  int residues;
  
  int books;
  
  int psys;
  
  InfoMode[] mode_param = null;
  
  int[] map_type = null;
  
  Object[] map_param = null;
  
  int[] time_type = null;
  
  Object[] time_param = null;
  
  int[] floor_type = null;
  
  Object[] floor_param = null;
  
  int[] residue_type = null;
  
  Object[] residue_param = null;
  
  StaticCodeBook[] book_param = null;
  
  PsyInfo[] psy_param = new PsyInfo[64];
  
  int envelopesa;
  
  float preecho_thresh;
  
  float preecho_clamp;
  
  public void init() {
    this.rate = 0;
  }
  
  public void clear() {
    for (int i2 = 0; i2 < this.modes; i2++)
      this.mode_param[i2] = null; 
    this.mode_param = null;
    for (int i1 = 0; i1 < this.maps; i1++)
      FuncMapping.mapping_P[this.map_type[i1]].free_info(this.map_param[i1]); 
    this.map_param = null;
    for (int n = 0; n < this.times; n++)
      FuncTime.time_P[this.time_type[n]].free_info(this.time_param[n]); 
    this.time_param = null;
    for (int m = 0; m < this.floors; m++)
      FuncFloor.floor_P[this.floor_type[m]].free_info(this.floor_param[m]); 
    this.floor_param = null;
    for (int k = 0; k < this.residues; k++)
      FuncResidue.residue_P[this.residue_type[k]].free_info(this.residue_param[k]); 
    this.residue_param = null;
    for (int j = 0; j < this.books; j++) {
      if (this.book_param[j] != null) {
        this.book_param[j].clear();
        this.book_param[j] = null;
      } 
    } 
    this.book_param = null;
    for (int i = 0; i < this.psys; i++)
      this.psy_param[i].free(); 
  }
  
  int unpack_info(Buffer opb) {
    this.version = opb.read(32);
    if (this.version != 0)
      return -1; 
    this.channels = opb.read(8);
    this.rate = opb.read(32);
    this.bitrate_upper = opb.read(32);
    this.bitrate_nominal = opb.read(32);
    this.bitrate_lower = opb.read(32);
    this.blocksizes[0] = 1 << opb.read(4);
    this.blocksizes[1] = 1 << opb.read(4);
    if (this.rate < 1 || this.channels < 1 || this.blocksizes[0] < 8 || this.blocksizes[1] < this.blocksizes[0] || opb.read(1) != 1) {
      clear();
      return -1;
    } 
    return 0;
  }
  
  int unpack_books(Buffer opb) {
    this.books = opb.read(8) + 1;
    if (this.book_param == null || this.book_param.length != this.books)
      this.book_param = new StaticCodeBook[this.books]; 
    for (int i1 = 0; i1 < this.books; i1++) {
      this.book_param[i1] = new StaticCodeBook();
      if (this.book_param[i1].unpack(opb) != 0) {
        clear();
        return -1;
      } 
    } 
    this.times = opb.read(6) + 1;
    if (this.time_type == null || this.time_type.length != this.times)
      this.time_type = new int[this.times]; 
    if (this.time_param == null || this.time_param.length != this.times)
      this.time_param = new Object[this.times]; 
    for (int n = 0; n < this.times; n++) {
      this.time_type[n] = opb.read(16);
      if (this.time_type[n] < 0 || this.time_type[n] >= 1) {
        clear();
        return -1;
      } 
      this.time_param[n] = FuncTime.time_P[this.time_type[n]].unpack(this, opb);
      if (this.time_param[n] == null) {
        clear();
        return -1;
      } 
    } 
    this.floors = opb.read(6) + 1;
    if (this.floor_type == null || this.floor_type.length != this.floors)
      this.floor_type = new int[this.floors]; 
    if (this.floor_param == null || this.floor_param.length != this.floors)
      this.floor_param = new Object[this.floors]; 
    for (int m = 0; m < this.floors; m++) {
      this.floor_type[m] = opb.read(16);
      if (this.floor_type[m] < 0 || this.floor_type[m] >= 2) {
        clear();
        return -1;
      } 
      this.floor_param[m] = FuncFloor.floor_P[this.floor_type[m]].unpack(this, opb);
      if (this.floor_param[m] == null) {
        clear();
        return -1;
      } 
    } 
    this.residues = opb.read(6) + 1;
    if (this.residue_type == null || this.residue_type.length != this.residues)
      this.residue_type = new int[this.residues]; 
    if (this.residue_param == null || this.residue_param.length != this.residues)
      this.residue_param = new Object[this.residues]; 
    for (int k = 0; k < this.residues; k++) {
      this.residue_type[k] = opb.read(16);
      if (this.residue_type[k] < 0 || this.residue_type[k] >= 3) {
        clear();
        return -1;
      } 
      this.residue_param[k] = FuncResidue.residue_P[this.residue_type[k]].unpack(this, opb);
      if (this.residue_param[k] == null) {
        clear();
        return -1;
      } 
    } 
    this.maps = opb.read(6) + 1;
    if (this.map_type == null || this.map_type.length != this.maps)
      this.map_type = new int[this.maps]; 
    if (this.map_param == null || this.map_param.length != this.maps)
      this.map_param = new Object[this.maps]; 
    for (int j = 0; j < this.maps; j++) {
      this.map_type[j] = opb.read(16);
      if (this.map_type[j] < 0 || this.map_type[j] >= 1) {
        clear();
        return -1;
      } 
      this.map_param[j] = FuncMapping.mapping_P[this.map_type[j]].unpack(this, opb);
      if (this.map_param[j] == null) {
        clear();
        return -1;
      } 
    } 
    this.modes = opb.read(6) + 1;
    if (this.mode_param == null || this.mode_param.length != this.modes)
      this.mode_param = new InfoMode[this.modes]; 
    for (int i = 0; i < this.modes; i++) {
      this.mode_param[i] = new InfoMode();
      (this.mode_param[i]).blockflag = opb.read(1);
      (this.mode_param[i]).windowtype = opb.read(16);
      (this.mode_param[i]).transformtype = opb.read(16);
      (this.mode_param[i]).mapping = opb.read(8);
      if ((this.mode_param[i]).windowtype >= 1 || (this.mode_param[i]).transformtype >= 1 || (this.mode_param[i]).mapping >= this.maps) {
        clear();
        return -1;
      } 
    } 
    if (opb.read(1) != 1) {
      clear();
      return -1;
    } 
    return 0;
  }
  
  public int synthesis_headerin(Comment vc, Packet op) {
    Buffer opb = new Buffer();
    if (op != null) {
      opb.readinit(op.packet_base, op.packet, op.bytes);
      byte[] buffer = new byte[6];
      int packtype = opb.read(8);
      opb.read(buffer, 6);
      if (buffer[0] != 118 || buffer[1] != 111 || buffer[2] != 114 || buffer[3] != 98 || buffer[4] != 105 || buffer[5] != 115)
        return -1; 
      switch (packtype) {
        case 1:
          if (op.b_o_s == 0)
            return -1; 
          if (this.rate != 0)
            return -1; 
          return unpack_info(opb);
        case 3:
          if (this.rate == 0)
            return -1; 
          return vc.unpack(opb);
        case 5:
          if (this.rate == 0 || vc.vendor == null)
            return -1; 
          return unpack_books(opb);
      } 
    } 
    return -1;
  }
  
  int pack_info(Buffer opb) {
    opb.write(1, 8);
    opb.write(_vorbis);
    opb.write(0, 32);
    opb.write(this.channels, 8);
    opb.write(this.rate, 32);
    opb.write(this.bitrate_upper, 32);
    opb.write(this.bitrate_nominal, 32);
    opb.write(this.bitrate_lower, 32);
    opb.write(Util.ilog2(this.blocksizes[0]), 4);
    opb.write(Util.ilog2(this.blocksizes[1]), 4);
    opb.write(1, 1);
    return 0;
  }
  
  int pack_books(Buffer opb) {
    opb.write(5, 8);
    opb.write(_vorbis);
    opb.write(this.books - 1, 8);
    for (int i1 = 0; i1 < this.books; i1++) {
      if (this.book_param[i1].pack(opb) != 0)
        return -1; 
    } 
    opb.write(this.times - 1, 6);
    for (int n = 0; n < this.times; n++) {
      opb.write(this.time_type[n], 16);
      FuncTime.time_P[this.time_type[n]].pack(this.time_param[n], opb);
    } 
    opb.write(this.floors - 1, 6);
    for (int m = 0; m < this.floors; m++) {
      opb.write(this.floor_type[m], 16);
      FuncFloor.floor_P[this.floor_type[m]].pack(this.floor_param[m], opb);
    } 
    opb.write(this.residues - 1, 6);
    for (int k = 0; k < this.residues; k++) {
      opb.write(this.residue_type[k], 16);
      FuncResidue.residue_P[this.residue_type[k]].pack(this.residue_param[k], opb);
    } 
    opb.write(this.maps - 1, 6);
    for (int j = 0; j < this.maps; j++) {
      opb.write(this.map_type[j], 16);
      FuncMapping.mapping_P[this.map_type[j]].pack(this, this.map_param[j], opb);
    } 
    opb.write(this.modes - 1, 6);
    for (int i = 0; i < this.modes; i++) {
      opb.write((this.mode_param[i]).blockflag, 1);
      opb.write((this.mode_param[i]).windowtype, 16);
      opb.write((this.mode_param[i]).transformtype, 16);
      opb.write((this.mode_param[i]).mapping, 8);
    } 
    opb.write(1, 1);
    return 0;
  }
  
  public int blocksize(Packet op) {
    Buffer opb = new Buffer();
    opb.readinit(op.packet_base, op.packet, op.bytes);
    if (opb.read(1) != 0)
      return -135; 
    int modebits = 0;
    int v = this.modes;
    while (v > 1) {
      modebits++;
      v >>>= 1;
    } 
    int mode = opb.read(modebits);
    if (mode == -1)
      return -136; 
    return this.blocksizes[(this.mode_param[mode]).blockflag];
  }
  
  public String toString() {
    return "version:" + new Integer(this.version) + ", channels:" + new Integer(this.channels) + ", rate:" + new Integer(this.rate) + ", bitrate:" + new Integer(this.bitrate_upper) + "," + new Integer(this.bitrate_nominal) + "," + new Integer(this.bitrate_lower);
  }
}
