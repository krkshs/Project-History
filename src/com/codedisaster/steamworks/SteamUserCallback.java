package com.codedisaster.steamworks;

public interface SteamUserCallback {
  void onAuthSessionTicket(SteamAuthTicket paramSteamAuthTicket, SteamResult paramSteamResult);
  
  void onValidateAuthTicket(SteamID paramSteamID1, SteamAuth.AuthSessionResponse paramAuthSessionResponse, SteamID paramSteamID2);
  
  void onMicroTxnAuthorization(int paramInt, long paramLong, boolean paramBoolean);
  
  void onEncryptedAppTicket(SteamResult paramSteamResult);
}
