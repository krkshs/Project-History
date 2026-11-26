package aoc.kingdoms.lukasz.jakowski.FlagsEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.menusEditor.CreateCiv;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class FlagManager {
   public List<Flag_Division> lDivisions = new ArrayList<>();
   public List<Flag_Overlay> lOverlays = new ArrayList<>();
   public static final int FLAG_WIDTH = 154;
   public static final int FLAG_HEIGHT = 100;
   public Flag_GameData flagEdit;
   public int activeColorID = 0;
   private List<Image> divisionLayers = new ArrayList<>();
   private List<Flag_OverlayImage> lOverlaysImages = new ArrayList<>();

   public final void drawFlag(SpriteBatch oSB, int nPosX, int nPosY) {
      this.drawDivision(oSB, nPosX, nPosY);

      for (int i = 0; i < this.flagEdit.lOverlays.size(); i++) {
         this.drawOverlay(oSB, nPosX, nPosY, i);
      }
   }

   public final void drawFlag_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY) {
      this.drawDivision_FlagFrameSize(oSB, nPosX, nPosY);

      for (int i = 0; i < this.flagEdit.lOverlays.size(); i++) {
         this.drawOverlay_FlagFrameSize(oSB, nPosX, nPosY, i);
      }
   }

   public final void drawDivision(SpriteBatch oSB, int nPosX, int nPosY) {
      this.beginClip(oSB, nPosX, nPosY);
      oSB.setColor(this.flagEdit.lDivisionColors.get(0));
      Images.pix.draw(oSB, nPosX, nPosY, 154, 100);

      for (int i = 0; i < this.divisionLayers.size(); i++) {
         oSB.setColor(this.flagEdit.lDivisionColors.get(i + 1));
         this.divisionLayers.get(i).draw(oSB, nPosX, nPosY, 154, 100);
      }

      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   public final void drawDivision_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY) {
      this.beginClip_FlagFrameSize(oSB, nPosX, nPosY);
      oSB.setColor(this.flagEdit.lDivisionColors.get(0));
      Images.pix.draw(oSB, nPosX, nPosY, 154, 100);

      for (int i = 0; i < this.divisionLayers.size(); i++) {
         oSB.setColor(this.flagEdit.lDivisionColors.get(i + 1));
         this.divisionLayers.get(i).draw(oSB, nPosX, nPosY, 154, 100);
      }

      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   public final void drawDivisionBG(SpriteBatch oSB, int nPosX, int nPosY) {
      this.beginClip(oSB, nPosX, nPosY);
      oSB.setColor(this.flagEdit.lDivisionColors.get(0));
      Images.pix.draw(oSB, nPosX, nPosY, 154, 100);
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   public final void drawDivision(SpriteBatch oSB, int nPosX, int nPosY, int nID) {
      this.beginClip(oSB, nPosX, nPosY);
      oSB.setColor(this.flagEdit.lDivisionColors.get(0));
      Images.pix.draw(oSB, nPosX, nPosY, 154, 100);
      oSB.setColor(this.flagEdit.lDivisionColors.get(nID));
      this.divisionLayers.get(nID - 1).draw(oSB, nPosX, nPosY, 154, 100);
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   public final void drawDivision_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY, int nID) {
      this.beginClip_FlagFrameSize(oSB, nPosX, nPosY);
      oSB.setColor(this.flagEdit.lDivisionColors.get(0));
      Images.pix.draw(oSB, nPosX, nPosY, 154, 100);
      oSB.setColor(this.flagEdit.lDivisionColors.get(nID));
      this.divisionLayers.get(nID - 1).draw(oSB, nPosX, nPosY, 154, 100);
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   public final void drawOverlay(SpriteBatch oSB, int nPosX, int nPosY, int id) {
      this.beginClip(oSB, nPosX, nPosY);
      oSB.setColor(this.flagEdit.lOverlays.get(id).oColor);
      this.getOverlay(this.flagEdit.lOverlays.get(id).iOverlayID)
         .draw(
            oSB,
            nPosX + this.flagEdit.lOverlays.get(id).iPosX,
            nPosY + this.flagEdit.lOverlays.get(id).iPosY,
            this.flagEdit.lOverlays.get(id).iWidth,
            this.flagEdit.lOverlays.get(id).iHeight
         );
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   public final void drawOverlay_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY, int id) {
      this.beginClip_FlagFrameSize(oSB, nPosX, nPosY);
      float tScale = 1.0F;
      oSB.setColor(this.flagEdit.lOverlays.get(id).oColor);
      this.getOverlay(this.flagEdit.lOverlays.get(id).iOverlayID)
         .draw(
            oSB,
            nPosX + (int)(this.flagEdit.lOverlays.get(id).iPosX * tScale),
            nPosY + (int)(this.flagEdit.lOverlays.get(id).iPosY * tScale),
            (int)(this.flagEdit.lOverlays.get(id).iWidth * tScale),
            (int)(this.flagEdit.lOverlays.get(id).iHeight * tScale)
         );
      oSB.setColor(Color.WHITE);
      this.endClip(oSB);
   }

   private final void beginClip(SpriteBatch oSB, int nPosX, int nPosY) {
      Rectangle clipBounds = new Rectangle(nPosX, CFG.GAME_HEIGHT - nPosY, 154.0F, -100.0F);
      oSB.flush();
      ScissorStack.pushScissors(clipBounds);
   }

   private final void beginClip_FlagFrameSize(SpriteBatch oSB, int nPosX, int nPosY) {
      Rectangle clipBounds = new Rectangle(nPosX, CFG.GAME_HEIGHT - nPosY, 154.0F, -100.0F);
      oSB.flush();
      ScissorStack.pushScissors(clipBounds);
   }

   private final void endClip(SpriteBatch oSB) {
      try {
         oSB.flush();
         ScissorStack.popScissors();
      } catch (IllegalStateException var3) {
      }
   }

   public final void initFlagEdit() {
      this.flagEdit = new Flag_GameData();
      this.loadDivision();
      this.loadOverlays();
   }

   public final void updateDivision(boolean add) {
      this.flagEdit.iDivisionID += add ? 1 : -1;
      if (this.flagEdit.iDivisionID < 0) {
         this.flagEdit.iDivisionID = this.lDivisions.size() - 1;
      } else if (this.flagEdit.iDivisionID >= this.lDivisions.size()) {
         this.flagEdit.iDivisionID = 0;
      }

      this.loadDivision();
   }

   public final void loadDivision() {
      for (int i = 0; i < this.divisionLayers.size(); i++) {
         this.divisionLayers.get(i).getTexture().dispose();
      }

      this.divisionLayers.clear();

      for (int i = 0; i < this.lDivisions.get(this.flagEdit.iDivisionID).iLayers - 1; i++) {
         this.divisionLayers
            .add(
               new Image(
                  new Texture(Gdx.files.internal("gfx/editorFlags/divisions/" + this.lDivisions.get(this.flagEdit.iDivisionID).sName + "_" + i + ".png")),
                  TextureFilter.Nearest
               )
            );
      }

      for (int i = this.flagEdit.lDivisionColors.size(); i < this.lDivisions.get(this.flagEdit.iDivisionID).iLayers; i++) {
         if (i == 0) {
            this.flagEdit.lDivisionColors.add(Color.WHITE);
         } else if (i == 1) {
            this.flagEdit.lDivisionColors.add(new Color(0.9843137F, 0.0F, 0.2F, 1.0F));
         } else if (i == 2) {
            this.flagEdit.lDivisionColors.add(new Color(0.0F, 0.19607843F, 0.39607844F, 1.0F));
         } else if (i == 3) {
            this.flagEdit.lDivisionColors.add(new Color(1.0F, 0.80784315F, 0.0F, 1.0F));
         } else {
            Color tempColor = CFG.getRandomColor();
            this.flagEdit.lDivisionColors.add(new Color(tempColor.r, tempColor.g, tempColor.b, 1.0F));
         }
      }
   }

   public final void updateOverlay(int nID, boolean add) {
      int tempOver = this.flagEdit.lOverlays.get(nID).iOverlayID;
      this.flagEdit.lOverlays.get(nID).iOverlayID += add ? 1 : -1;
      if (this.flagEdit.lOverlays.get(nID).iOverlayID < 0) {
         this.flagEdit.lOverlays.get(nID).iOverlayID = this.lOverlays.size() - 1;
      } else if (this.flagEdit.lOverlays.get(nID).iOverlayID >= this.lOverlays.size()) {
         this.flagEdit.lOverlays.get(nID).iOverlayID = 0;
      }

      this.tryRemoveOverlay(tempOver);
      this.loadOverlayImage(this.flagEdit.lOverlays.get(nID).iOverlayID);
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iWidth = (int)Math.abs(
         this.getOverlay(this.flagEdit.lOverlays.get(nID).iOverlayID).getWidth() * this.lOverlays.get(this.flagEdit.lOverlays.get(nID).iOverlayID).Scale
      );
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iHeight = (int)Math.abs(
         this.getOverlay(this.flagEdit.lOverlays.get(nID).iOverlayID).getHeight() * this.lOverlays.get(this.flagEdit.lOverlays.get(nID).iOverlayID).Scale
      );
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iPosX = 77 - this.flagEdit.lOverlays.get(nID).iWidth / 2;
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iPosY = 50 - this.flagEdit.lOverlays.get(nID).iHeight / 2;
   }

   public final void addOverlay() {
      int tempOverlayID = 0;
      this.flagEdit.lOverlays.add(new Flag_Overlay_GameData(tempOverlayID));
      this.loadOverlayImage(tempOverlayID);
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iWidth = (int)Math.abs(
         this.getOverlay(tempOverlayID).getWidth() * this.lOverlays.get(tempOverlayID).Scale
      );
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iHeight = (int)Math.abs(
         this.getOverlay(tempOverlayID).getHeight() * this.lOverlays.get(tempOverlayID).Scale
      );
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iPosX = 77 - this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iWidth / 2;
      this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iPosY = 50 - this.flagEdit.lOverlays.get(this.flagEdit.lOverlays.size() - 1).iHeight / 2;
   }

   public final void removeOverlay(int nID) {
      int tempOverlayID = this.flagEdit.lOverlays.get(nID).iOverlayID;
      this.flagEdit.lOverlays.remove(nID);
      this.tryRemoveOverlay(tempOverlayID);
   }

   public final void moveOverlayUp(int nID) {
      if (nID > 0) {
         Flag_Overlay_GameData tempD = this.flagEdit.lOverlays.get(nID);
         this.flagEdit.lOverlays.set(nID, this.flagEdit.lOverlays.get(nID - 1));
         this.flagEdit.lOverlays.set(nID - 1, tempD);
      }
   }

   public final void loadOverlayImage(int iOverlayID) {
      for (int i = 0; i < this.lOverlaysImages.size(); i++) {
         if (iOverlayID == this.lOverlaysImages.get(i).iOverlayID) {
            return;
         }
      }

      this.lOverlaysImages.add(new Flag_OverlayImage(iOverlayID));
   }

   public final void tryRemoveOverlay(int iOverlayID) {
      for (int i = 0; i < this.flagEdit.lOverlays.size(); i++) {
         if (this.flagEdit.lOverlays.get(i).iOverlayID == iOverlayID) {
            return;
         }
      }

      for (int ix = 0; ix < this.lOverlaysImages.size(); ix++) {
         if (iOverlayID == this.lOverlaysImages.get(ix).iOverlayID) {
            this.lOverlaysImages.get(ix).imageOverlay.getTexture().dispose();
            this.lOverlaysImages.remove(ix);
            return;
         }
      }
   }

   public final Image getOverlay(int iOverlayID) {
      for (int i = 0; i < this.lOverlaysImages.size(); i++) {
         if (iOverlayID == this.lOverlaysImages.get(i).iOverlayID) {
            return this.lOverlaysImages.get(i).imageOverlay;
         }
      }

      return Images.pix;
   }

   public final void loadDivisions() {
      if (this.lDivisions != null) {
         this.lDivisions.clear();
      }

      this.lDivisions = new ArrayList<>();

      try {
         FileHandle fileList = FileManager.loadFile("gfx/editorFlags/divisions.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(FlagManager.ConfigDivisionsData.class, "Division", FlagManager.Data_Divisions.class);
         new FlagManager.ConfigDivisionsData();
         FlagManager.ConfigDivisionsData data = json.fromJson(FlagManager.ConfigDivisionsData.class, fileContent);

         for (Object e : data.Division) {
            FlagManager.Data_Divisions tempData = (FlagManager.Data_Divisions)e;
            this.lDivisions.add(new Flag_Division(tempData.Name, tempData.Layers));
         }
      } catch (GdxRuntimeException var8) {
      }
   }

   public final void loadOverlays() {
      if (this.lOverlays != null) {
         this.lOverlays.clear();
      }

      this.lOverlays = new ArrayList<>();

      try {
         FileHandle fileList = FileManager.loadFile("gfx/editorFlags/overlays.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(FlagManager.ConfigOverlayData.class, "Overlay", FlagManager.Data_Overlays.class);
         new FlagManager.ConfigOverlayData();
         FlagManager.ConfigOverlayData data = json.fromJson(FlagManager.ConfigOverlayData.class, fileContent);

         for (Object e : data.Overlay) {
            FlagManager.Data_Overlays tempData = (FlagManager.Data_Overlays)e;
            this.lOverlays.add(new Flag_Overlay(tempData.Name, tempData.Scale));
         }

         for (int i = 0; i < this.flagEdit.lOverlays.size(); i++) {
            this.loadOverlayImage(this.flagEdit.lOverlays.get(i).iOverlayID);
         }
      } catch (GdxRuntimeException var8) {
      }
   }

   public final void loadData() {
      this.clearData();
      this.loadDivisions();
   }

   public final void clearData() {
      if (this.lDivisions != null) {
         this.lDivisions.clear();
      }

      if (this.lOverlays != null) {
         this.lOverlays.clear();
      }

      for (int i = 0; i < this.divisionLayers.size(); i++) {
         this.divisionLayers.get(i).getTexture().dispose();
      }

      this.divisionLayers.clear();

      for (int i = 0; i < this.lOverlaysImages.size(); i++) {
         this.lOverlaysImages.get(i).imageOverlay.getTexture().dispose();
      }

      this.lOverlaysImages.clear();
   }

   public final void saveFlagTexture(SpriteBatch oSB) {
      this.drawFlag(oSB, 0, 0);
      Image tempFlagImage = new Image(new Texture(ScreenUtils.getFrameBufferPixmap(0, CFG.GAME_HEIGHT - 100, 154, 100)));

      try {
         tempFlagImage.getTexture().getTextureData().prepare();
      } catch (GdxRuntimeException var4) {
      }

      if (FileManager.IS_MAC) {
         PixmapIO.writePNG(
            Gdx.files.external("mods/GameCivs/gfx/flagsH/" + CreateCiv.nCiv.Tag + ".png"), tempFlagImage.getTexture().getTextureData().consumePixmap()
         );
      } else {
         PixmapIO.writePNG(
            Gdx.files.local("mods/GameCivs/gfx/flagsH/" + CreateCiv.nCiv.Tag + ".png"), tempFlagImage.getTexture().getTextureData().consumePixmap()
         );
      }

      oSB.setColor(Color.BLACK);
      Images.pix.draw(oSB, 0, 0, 154, 100);
      oSB.setColor(Color.WHITE);
      tempFlagImage.getTexture().dispose();
      tempFlagImage = null;
   }

   public static class ConfigDivisionsData {
      public String Age_of_Civilizations;
      public ArrayList Division;
   }

   public static class ConfigOverlayData {
      public String Age_of_Civilizations;
      public ArrayList Overlay;
   }

   public static class Data_Divisions {
      public String Name;
      public int Layers;
   }

   public static class Data_Overlays {
      public String Name;
      public float Scale;
   }
}
