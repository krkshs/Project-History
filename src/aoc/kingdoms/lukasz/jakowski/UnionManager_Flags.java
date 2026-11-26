package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class UnionManager_Flags {
   public List<Image> lFlags_H = new ArrayList<>();

   public final void loadImages() {
      try {
         for (int i = 0; i < 4; i++) {
            this.lFlags_H.add(new Image(new Texture(Gdx.files.internal("gfx/unions/unionH_0_" + i + ".png")), TextureFilter.Nearest));
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void generateFlag(SpriteBatch oSB) {
      if (!Game.player.playerStats4.flagsGenerated && Game.player.playerStats4.unionTags.size() > 1) {
         Game.player.playerStats4.flagsGenerated = true;
         this.generateFlag(oSB, Game.player.playerStats4.unionTags);
         String tName = "";

         for (int i = 0; i < Game.player.playerStats4.unionTags.size(); i++) {
            tName = tName + Game.lang.getCiv(Game.player.playerStats4.unionTags.get(i)) + (i == Game.player.playerStats4.unionTags.size() - 1 ? "" : " - ");
         }

         Game.getCiv(Game.player.iCivID).setCivName(tName);
      }
   }

   public final void generateFlag(SpriteBatch oSB, List<String> lTags) {
      try {
         List<Image> tempFlags = new ArrayList<>();

         for (int i = 0; i < lTags.size(); i++) {
            String realTag = Game.ideologiesManager.getRealTag(lTags.get(i));
            if (FileManager.loadFile("gfx/flagsXH/" + lTags.get(i) + ".png").exists()) {
               tempFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + lTags.get(i) + ".png"), Format.RGB888, false), TextureFilter.Linear));
            } else if (FileManager.loadFile("gfx/flagsXH/" + realTag + ".png").exists()) {
               tempFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + realTag + ".png"), Format.RGB888, false), TextureFilter.Linear));
            } else if (FileManager.loadFile("gfx/flagsH/" + lTags.get(i) + ".png").exists()) {
               tempFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsH/" + lTags.get(i) + ".png"), Format.RGB888, false), TextureFilter.Linear));
            } else if (FileManager.loadFile("gfx/flagsH/" + realTag + ".png").exists()) {
               tempFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsH/" + realTag + ".png"), Format.RGB888, false), TextureFilter.Linear));
            } else if (FileManager.loadFile("gfx/flags/" + lTags.get(i) + ".png").exists()) {
               tempFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/" + lTags.get(i) + ".png"), Format.RGB888, false), TextureFilter.Nearest));
            } else if (FileManager.loadFile("gfx/flags/" + realTag + ".png").exists()) {
               tempFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/" + realTag + ".png"), Format.RGB888, false), TextureFilter.Nearest));
            } else if (FileManager.loadFile("mods/GameCivs/gfx/flagsH/" + lTags.get(i) + ".png").exists()) {
               tempFlags.add(
                  new Image(new Texture(FileManager.loadFile("mods/GameCivs/gfx/flagsH/" + lTags.get(i) + ".png"), Format.RGB888, false), TextureFilter.Linear)
               );
            } else if (FileManager.loadFile("mods/GameCivs/gfx/flagsH/" + realTag + ".png").exists()) {
               tempFlags.add(
                  new Image(new Texture(FileManager.loadFile("mods/GameCivs/gfx/flagsH/" + realTag + ".png"), Format.RGB888, false), TextureFilter.Linear)
               );
            } else {
               tempFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/ran.png"), Format.RGB888, false), TextureFilter.Nearest));
            }
         }

         int FLAG_WIDTH_BIG = this.lFlags_H.get(0).getWidth();
         int FLAG_HEIGHT_BIG = this.lFlags_H.get(0).getHeight();
         oSB.setColor(Color.BLACK);
         Images.pix.draw(oSB, 0, 0, FLAG_WIDTH_BIG, FLAG_HEIGHT_BIG);
         oSB.setColor(Color.WHITE);

         for (int ix = 0; ix < tempFlags.size() && ix < 4; ix++) {
            oSB.setShader(Renderer.shaderAlpha);
            this.lFlags_H.get(ix).getTexture().bind(2);
            tempFlags.get(ix).getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            this.lFlags_H.get(ix).draw(oSB, 0, 0, false, true);
            oSB.setShader(Renderer.shaderDefault);
         }

         Image tGenerated = new Image(new Texture(ScreenUtils.getFrameBufferPixmap(0, CFG.GAME_HEIGHT - FLAG_HEIGHT_BIG, FLAG_WIDTH_BIG, FLAG_HEIGHT_BIG)));
         tGenerated.draw(oSB, 0, 0, false, true);

         try {
            oSB.flush();
            ScissorStack.popScissors();
         } catch (Exception var8) {
         }

         oSB.end();
         oSB.begin();
         oSB.setColor(Color.WHITE);
         tGenerated.getTexture().dispose();
         Image var14 = null;
         var14 = new Image(new Texture(ScreenUtils.getFrameBufferPixmap(0, CFG.GAME_HEIGHT - FLAG_HEIGHT_BIG, FLAG_WIDTH_BIG, FLAG_HEIGHT_BIG)));
         Game.getCiv(Game.player.iCivID).setFlag((Image)var14);

         for (int ix = 0; ix < tempFlags.size(); ix++) {
            tempFlags.get(ix).getTexture().dispose();
         }

         tempFlags.clear();
         tempFlags = null;
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }
}
