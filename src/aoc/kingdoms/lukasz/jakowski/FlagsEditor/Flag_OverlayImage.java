package aoc.kingdoms.lukasz.jakowski.FlagsEditor;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Flag_OverlayImage {
   public int iOverlayID = 0;
   public Image imageOverlay;

   public Flag_OverlayImage(int iOverlayID) {
      this.iOverlayID = iOverlayID;
      this.imageOverlay = new Image(
         new Texture(Gdx.files.internal("gfx/editorFlags/overlays/" + Game.flagManager.lOverlays.get(iOverlayID).sName + ".png")), TextureFilter.Linear
      );
   }
}
