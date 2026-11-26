package aoc.kingdoms.lukasz.jakowski.FlagsEditor;

import com.badlogic.gdx.graphics.Color;

public class Flag_Overlay_GameData {
   public int iOverlayID = 0;
   public Color oColor = Color.WHITE;
   public int iPosX = 0;
   public int iPosY = 0;
   public int iWidth = 0;
   public int iHeight = 0;

   protected Flag_Overlay_GameData(int iOverlayID) {
      this.iOverlayID = iOverlayID;
   }
}
