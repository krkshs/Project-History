package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.province.ProvinceNamesManager;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class City {
   public int iPosX;
   public int iPosY;
   public String sCityName = null;
   public String sCityNameOriginal = null;
   public int iWidth = 0;
   public int iHeight = 0;
   public int iCityLevel = 0;
   public int imageID = 0;
   public static final int PADDING = 10;
   public static final int PADDING_NAME = 5;

   public City(String sName, int nPosX, int nPosY, int iCityLevel) {
      this.sCityName = sName;
      this.sCityNameOriginal = sName;
      this.updateCityNameWidth();
      this.iPosX = nPosX;
      this.iPosY = nPosY;
      this.iCityLevel = iCityLevel;
      this.imageID = Game.oR.nextInt(Game.mapCities.imageCity.size());
   }

   public final void drawCity(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, float fAlpha2, float fontScale) {
      Province province = Game.getProvince(nProvinceID);
      float tScale = province.cityScale * Game.mapScale.getCurrentScale();
      if (province.getFortLevel() > 0) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha2));
         Game.mapCities
            .imageCityFort
            .get(this.imageID)
            .draw(
               oSB,
               (int)(
                  (this.getPosX() + province.getTranslateProvincePosX()) * nScale - Game.mapCities.imageCityFort.get(this.imageID).getWidth() * tScale / 2.0F
               ),
               (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale) - (int)(Game.mapCities.imageCityFort.get(this.imageID).getHeight() * tScale / 2.0F),
               tScale
            );
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha2));
         Game.mapCities
            .imageCity
            .get(this.imageID)
            .draw(
               oSB,
               (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - Game.mapCities.imageCity.get(this.imageID).getWidth() * tScale / 2.0F),
               (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale) - (int)(Game.mapCities.imageCity.get(this.imageID).getHeight() * tScale / 2.0F),
               tScale
            );
      }
   }

   public final void drawCity_CivFlagName(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha) {
      this.drawCityName_Capital_Civ(oSB, nProvinceID, nScale, fAlpha);
   }

   public final void drawCity_CivFlag(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha) {
      Map.drawProvincesFlags.drawCity_CivFlag(oSB, nProvinceID, nScale, fAlpha, this.getDrawPosX(nProvinceID, nScale), this.getDrawPosY(nProvinceID, nScale));
   }

   public final void drawCity_InGame(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, float fAlpha2, float fontScale) {
      Province province = Game.getProvince(nProvinceID);
      float tScale = province.cityScale * Game.mapScale.getCurrentScale();
      if (province.getFortLevel() > 0) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha2));
         Image image = Game.mapCities.imageCityFort.get(this.imageID);
         image.draw(
            oSB,
            (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - image.getWidth() * tScale / 2.0F),
            (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale) - (int)(image.getHeight() * tScale / 2.0F),
            tScale
         );
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha2));
         Image image = Game.mapCities.imageCity.get(this.imageID);
         image.draw(
            oSB,
            (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - image.getWidth() * tScale / 2.0F),
            (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale) - (int)(image.getHeight() * tScale / 2.0F),
            tScale
         );
      }
   }

   public final void drawCity_InGame_NamesLow(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, float fAlpha2, float fontScale) {
      Province province = Game.getProvince(nProvinceID);
      float tScale = Game.mapCities.citiesSettings.citiesScale_CurrentScale[this.iCityLevel];
      if (province.getFortLevel() > 0) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
         Image image = Game.mapCities.imageCityFort.get(this.imageID);
         image.draw(
            oSB,
            (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - image.getWidth() * tScale / 2.0F),
            (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale) - (int)(image.getHeight() * tScale / 2.0F),
            tScale
         );
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha2));
         Image image = Game.mapCities.imageCity.get(this.imageID);
         image.draw(
            oSB,
            (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - image.getWidth() * tScale / 2.0F),
            (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale) - (int)(image.getHeight() * tScale / 2.0F),
            tScale
         );
         this.drawCityName(
            oSB,
            (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - this.iWidth * fontScale / 2.0F),
            (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale) + (int)(image.getHeight() * tScale / 2.0F + CFG.PADDING + CFG.PADDING / 2),
            fAlpha,
            fontScale
         );
      }
   }

   public final void drawCity_InGame_NamesLow_OnlyName(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, float fAlpha2, float fontScale) {
      this.drawCityName(
         oSB,
         (int)((this.getPosX() + Game.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale - this.iWidth * fontScale / 2.0F),
         (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale),
         fAlpha,
         fontScale
      );
      float tScale = Game.mapCities.citiesSettings.citiesScale_CurrentScale[this.iCityLevel];
   }

   public final void drawCity_NameNotCapital(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, float fontScale) {
      Province province = Game.getProvince(nProvinceID);
      float tScale = Game.mapCities.citiesSettings.citiesScale_CurrentScale[this.iCityLevel];
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
      if (province.getFortLevel() > 0) {
         if (!province.isCapital) {
            this.drawCityName(
               oSB,
               (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - this.iWidth * fontScale / 2.0F),
               (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale)
                  + (int)(Game.mapCities.imageCityFort.get(this.imageID).getHeight() * tScale / 2.0F + CFG.PADDING + CFG.PADDING / 2),
               fAlpha,
               fontScale
            );
         }
      } else if (!province.isCapital) {
         this.drawCityName(
            oSB,
            (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - this.iWidth * fontScale / 2.0F),
            (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale)
               + (int)(Game.mapCities.imageCity.get(this.imageID).getHeight() * tScale / 2.0F + CFG.PADDING + CFG.PADDING / 2),
            fAlpha,
            fontScale
         );
      }
   }

   public final void drawCity_Name(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, float fontScale) {
      Province province = Game.getProvince(nProvinceID);
      float tScale = Game.mapCities.citiesSettings.citiesScale_CurrentScale[this.iCityLevel];
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
      if (province.getFortLevel() > 0) {
         if (province.isCapital) {
            this.drawCityName_Capital(oSB, nProvinceID, nScale, fAlpha, tScale);
         } else {
            this.drawCityName(
               oSB,
               (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - this.iWidth * fontScale / 2.0F),
               (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale)
                  + (int)(Game.mapCities.imageCityFort.get(this.imageID).getHeight() * tScale / 2.0F + CFG.PADDING + CFG.PADDING / 2),
               fAlpha,
               fontScale
            );
         }
      } else if (province.isCapital) {
         this.drawCityName_Capital(oSB, nProvinceID, nScale, fAlpha, tScale);
      } else {
         this.drawCityName(
            oSB,
            (int)((this.getPosX() + province.getTranslateProvincePosX()) * nScale - this.iWidth * fontScale / 2.0F),
            (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale)
               + (int)(Game.mapCities.imageCity.get(this.imageID).getHeight() * tScale / 2.0F + CFG.PADDING + CFG.PADDING / 2),
            fAlpha,
            fontScale
         );
      }
   }

   private final void drawCityName(SpriteBatch oSB, int nX, int nY, float fAlpha, float fontScale) {
      Renderer.fontMain.get(4).getData().setScale(fontScale);
      Renderer.drawText(
         oSB,
         4,
         this.sCityName,
         nX,
         nY,
         new Color(MapCities.COLOR_CITY_NAME.r, MapCities.COLOR_CITY_NAME.g, MapCities.COLOR_CITY_NAME.b, MapCities.COLOR_CITY_NAME.a * fAlpha)
      );
   }

   private final void drawCityName_Capital(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha, float tScale) {
      int nPosX = this.getDrawPosX(nProvinceID, nScale);
      int nPosY = this.getDrawPosY(nProvinceID, nScale);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.9F * fAlpha));
      int tWidth = this.getDrawWidth();
      nPosX -= tWidth / 2;
      oSB.setShader(Renderer.shaderAlpha);
      Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getFlag().getTexture().bind(1);
      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.capitalLeft).draw(oSB, nPosX, nPosY);
      if (Game.iHoveredCapitalProvinceID == nProvinceID) {
         ImageManager.getImage(Images.capitalLeft).draw(oSB, nPosX, nPosY);
      }

      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.capitalLeftOver).draw(oSB, nPosX, nPosY);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F * fAlpha));
      ImageManager.getImage(Images.capitalRight)
         .draw2(
            oSB,
            nPosX + ImageManager.getImage(Images.capitalLeft).getWidth(),
            nPosY,
            this.iWidth + 5 + 10,
            ImageManager.getImage(Images.capitalRight).getHeight(),
            true,
            false
         );
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
      Renderer.fontMain.get(4).getData().setScale(Game.settingsManager.CITIES_CAPITAL_FONT_SCALE);
      Renderer.drawText(
         oSB,
         4,
         "" + this.sCityName,
         nPosX + ImageManager.getImage(Images.capitalLeft).getWidth() + 5,
         nPosY + (ImageManager.getImage(Images.capitalLeft).getHeight() - this.iHeight) / 2,
         new Color(
            MapCities.COLOR_CITY_CAPITAL_NAME.r,
            MapCities.COLOR_CITY_CAPITAL_NAME.g,
            MapCities.COLOR_CITY_CAPITAL_NAME.b,
            MapCities.COLOR_CITY_CAPITAL_NAME.a * fAlpha
         )
      );
      oSB.setColor(Color.WHITE);
   }

   private final void drawCityName_Capital_Civ(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha) {
      int nPosX = this.getDrawPosX(nProvinceID, nScale);
      int nPosY = this.getDrawPosY(nProvinceID, nScale);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.9F * fAlpha));
      nPosX -= this.getDrawWidth() / 2;
      oSB.setShader(Renderer.shaderAlpha);
      Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getFlag().getTexture().bind(1);
      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.capitalLeft).draw(oSB, nPosX, nPosY);
      if (Game.iHoveredCapitalProvinceID == nProvinceID) {
         ImageManager.getImage(Images.capitalLeft).draw(oSB, nPosX, nPosY);
      }

      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.capitalLeftOver).draw(oSB, nPosX, nPosY);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F * fAlpha));
      ImageManager.getImage(Images.capitalRight)
         .draw2(
            oSB,
            nPosX + ImageManager.getImage(Images.capitalLeft).getWidth(),
            nPosY,
            Game.getCiv(Game.getProvince(nProvinceID).getCivID()).iCapitalNameWidth + 5 + 10,
            ImageManager.getImage(Images.capitalRight).getHeight(),
            true,
            false
         );
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
      Renderer.fontMain.get(4).getData().setScale(Game.settingsManager.CITIES_CAPITAL_FONT_SCALE);
      Game.mapCities.capitalCityName.draw(oSB, nProvinceID, fAlpha, nPosX + 5, nPosY);
      oSB.setColor(Color.WHITE);
   }

   public final void drawCityName_Capital_CivFlag_War(SpriteBatch oSB, int nProvinceID, float nScale, float fAlpha) {
      int nPosX = this.getDrawPosX(nProvinceID, nScale);
      int nPosY = this.getDrawPosY(nProvinceID, nScale);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F * fAlpha));
      if (Game.getCiv(Game.getProvince(nProvinceID).getCivID()).iCivRankID == 4) {
         ImageManager.getImage(Images.flagCapitalWar_l)
            .draw(
               oSB,
               nPosX - ImageManager.getImage(Images.flagCapitalWar_l).getWidth() / 2,
               nPosY + ImageManager.getImage(Images.flagCapitalMask_l).getHeight() / 2 - ImageManager.getImage(Images.flagCapitalWar_l).getHeight() / 2
            );
      } else if (Game.getCiv(Game.getProvince(nProvinceID).getCivID()).iCivRankID > 1) {
         ImageManager.getImage(Images.flagCapitalWar)
            .draw(
               oSB,
               nPosX - ImageManager.getImage(Images.flagCapitalWar).getWidth() / 2,
               nPosY + ImageManager.getImage(Images.flagCapitalMask).getHeight() / 2 - ImageManager.getImage(Images.flagCapitalWar).getHeight() / 2
            );
      } else {
         ImageManager.getImage(Images.flagCapitalWar_s)
            .draw(
               oSB,
               nPosX - ImageManager.getImage(Images.flagCapitalWar_s).getWidth() / 2,
               nPosY + ImageManager.getImage(Images.flagCapitalMask_s).getHeight() / 2 - ImageManager.getImage(Images.flagCapitalWar_s).getHeight() / 2
            );
      }
   }

   public final int getDrawPosX(int nProvinceID, float nScale) {
      return (int)((this.getPosX() + Game.getProvince(nProvinceID).getTranslateProvincePosX()) * nScale);
   }

   public final int getDrawPosY(int nProvinceID, float nScale) {
      return (int)((this.getPosY() + Game.mapCoords.getPosY()) * nScale)
         + (int)(
            Game.mapCities.imageCityFort.get(this.imageID).getHeight() * Game.mapCities.citiesSettings.citiesScale_CurrentScale[this.iCityLevel] / 2.0F
               + CFG.PADDING
               + CFG.PADDING / 2
         );
   }

   public final int getDrawWidth() {
      return this.iWidth + ImageManager.getImage(Images.capitalLeft).getWidth() + 10 + 5;
   }

   public final void setCityNameOriginal(int nProvinceID) {
      if (!this.sCityNameOriginal.equals(this.sCityName)) {
         this.sCityName = this.sCityNameOriginal;
         this.updateCityNameWidth();
         Game.getProvince(nProvinceID).setProvinceName(this.sCityName);
         Game.addSimpleTask(new Game.SimpleTask("buildProvNameData" + nProvinceID, nProvinceID) {
            @Override
            public void update() {
               ProvinceNamesManager.buildProvNameData(this.id, true);
            }
         });
      }
   }

   public final void setCityName(int nProvinceID, String sName) {
      if (!this.sCityName.equals(sName)) {
         this.sCityName = sName;
         this.updateCityNameWidth();
         Game.getProvince(nProvinceID).setProvinceName(this.sCityName);
         Game.addSimpleTask(new Game.SimpleTask("rebuildProvNameData" + nProvinceID, nProvinceID) {
            @Override
            public void update() {
               ProvinceNamesManager.buildProvNameData(this.id, true);
            }
         });
      }
   }

   public final int getPosX() {
      return this.iPosX;
   }

   public final int getPosY() {
      return this.iPosY;
   }

   protected final void updateCityNameWidth() {
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(4), this.sCityName);
      this.iWidth = (int)glyphLayout.width;
      this.iHeight = (int)glyphLayout.height;
   }
}
