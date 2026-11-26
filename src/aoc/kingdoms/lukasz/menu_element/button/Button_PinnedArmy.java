package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.GeneralManager;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_BattleArmy;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceArmy;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_PinnedArmy extends Button {
   public String key = null;
   public int iActiveGeneralIMG_ID = -1;
   public int iArmyNum = -4894584;
   public int iProvinceID;
   public int iLastTurnID = -975159;
   public Image generalImage;

   public Button_PinnedArmy(String key, int iPosX, int iPosY) {
      this.init(
         "",
         CFG.FONT_BOLD_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.pinnedGeneralFrame).getWidth(),
         getButtonHeight(),
         true,
         true,
         false,
         false
      );
      this.key = key;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      try {
         if (Game_Calendar.TURN_ID != this.iLastTurnID) {
            this.iLastTurnID = Game_Calendar.TURN_ID;
            Game.ArmyPos nArmyPos = Game.findArmy_FullCheck(Game.player.iCivID, this.key);
            if (nArmyPos != null) {
               if (Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral == null) {
                  this.iActiveGeneralIMG_ID = -1;
                  if (this.generalImage != null) {
                     this.generalImage.dispose();
                     this.generalImage = null;
                  }
               } else if (this.iActiveGeneralIMG_ID != Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral.g) {
                  this.iActiveGeneralIMG_ID = Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral.g;
                  if (this.generalImage != null) {
                     this.generalImage.dispose();
                     this.generalImage = null;
                  }

                  if (Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral.sI != null) {
                     if (FileManager.loadFile(
                           "game/characters/"
                              + CFG.getRescouresPath_Short()
                              + Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral.sI
                              + ".png"
                        )
                        .exists()) {
                        this.generalImage = new Image(
                           ImageManager.loadTexture_RGB888(
                              "game/characters/"
                                 + CFG.getRescouresPath_Short()
                                 + Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral.sI
                                 + ".png"
                           ),
                           TextureFilter.Linear,
                           TextureWrap.ClampToEdge
                        );
                     } else if (FileManager.loadFile(
                           "game/characters/"
                              + CFG.getRescouresPath_Short_H()
                              + Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral.sI
                              + ".png"
                        )
                        .exists()) {
                        this.generalImage = new Image(
                           ImageManager.loadTexture_RGB888(
                              "game/characters/"
                                 + CFG.getRescouresPath_Short_H()
                                 + Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral.sI
                                 + ".png"
                           ),
                           TextureFilter.Linear,
                           TextureWrap.ClampToEdge
                        );
                     }
                  }

                  if (this.generalImage == null) {
                     if (FileManager.loadFile(
                           "game/generals/"
                              + GeneralManager.getGeneralsImgPath(Game.player.iCivID)
                              + CFG.getRescouresPath_Short()
                              + Game.getCiv(Game.player.iCivID).iGroupID
                              + "/"
                              + this.iActiveGeneralIMG_ID
                              + ".png"
                        )
                        .exists()) {
                        this.generalImage = new Image(
                           ImageManager.loadTexture_RGB888(
                              "game/generals/"
                                 + GeneralManager.getGeneralsImgPath(Game.player.iCivID)
                                 + CFG.getRescouresPath_Short()
                                 + Game.getCiv(Game.player.iCivID).iGroupID
                                 + "/"
                                 + this.iActiveGeneralIMG_ID
                                 + ".png"
                           ),
                           TextureFilter.Linear,
                           TextureWrap.ClampToEdge
                        );
                     } else {
                        this.generalImage = new Image(
                           ImageManager.loadTexture_RGB888(
                              "game/generals/"
                                 + GeneralManager.getGeneralsImgPath(Game.player.iCivID)
                                 + CFG.getRescouresPath_Short()
                                 + Game.getCiv(Game.player.iCivID).iGroupID
                                 + "/0.png"
                           ),
                           TextureFilter.Linear,
                           TextureWrap.ClampToEdge
                        );
                     }
                  }
               }

               if (this.iArmyNum != Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).iArmy) {
                  this.iArmyNum = Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).iArmy;
                  this.setText(Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).sArmy);
               }
            } else {
               Game.player.removePinArmy(this.key);
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      oSB.setShader(Renderer.shaderAlpha);

      try {
         if (this.iActiveGeneralIMG_ID >= 0 && this.generalImage != null) {
            this.generalImage.getTexture().bind(1);
         } else {
            Game.generalManager.noGeneral.getTexture().bind(1);
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.pinnedGeneralMask).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      if (this.getIsHovered() || isActive) {
         ImageManager.getImage(Images.pinnedGeneralFrame).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      }

      ImageManager.getImage(Images.pinnedGeneralFrame).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - this.iTextHeight - CFG.PADDING - CFG.PADDING / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      try {
         if (Game.activeArmySize == 1 && Game.activeArmy.get(0).key.equals(this.key)) {
            return Colors.HOVER_GOLD;
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return super.getColor(isActive);
   }

   @Override
   public String getTextToDraw() {
      return this.sText;
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.pinnedGeneralFrame).getHeight();
   }

   @Override
   public void actionElement() {
      Game.ArmyPos nArmyPos = Game.findArmy_FullCheck(Game.player.iCivID, this.key);
      if (nArmyPos != null) {
         if (nArmyPos.iProvinceID >= 0 && Game.activeArmySize == 1 && Game.activeArmy.get(0).key.equals(this.key)) {
            Game.mapCoords.centerToProvinceID(nArmyPos.iProvinceID);
         } else {
            Game.setActiveArmy(nArmyPos.iProvinceID, this.key);
            InGame_ProvinceArmy.iActiveID = 0;
            InGame_ProvinceArmy.sActiveKEY = this.key;
            Game.menuManager.rebuildInGame_ProvinceArmy();
            Game.menuManager.rebuildInGame_ProvinceArmy_HideMenus();
            Game.gameActiveProvince.resetLastActiveProvince();
            Game.setActiveProvinceID(-1);
            Game.animationHover.resetAnimationData();
         }
      } else {
         Game.menuManager.addToast_Error(Game.lang.get("ArmyNotFound"));
      }
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      Game.ArmyPos nArmyPos = Game.findArmy_FullCheck(Game.player.iCivID, this.key);
      if (nArmyPos != null) {
         if (nArmyPos.iProvinceID >= 0 && Game.activeArmySize == 1 && Game.activeArmy.get(0).key.equals(this.key)) {
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CenterToProvince"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.center, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         } else {
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SelectArmy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }

         if (Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral != null) {
            nData.add(
               new MenuElement_HoverElement_Type_Button_TextBonus(
                  Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).armyGeneral.n,
                  "",
                  Images.general,
                  CFG.FONT_REGULAR_SMALL,
                  CFG.FONT_REGULAR_SMALL,
                  Colors.HOVER_LEFT,
                  Colors.HOVER_LEFT
               )
            );
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }

         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.getProvince(nArmyPos.iProvinceID).getProvinceName(),
               "",
               Images.provinces,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_RIGHT,
               Colors.HOVER_RIGHT
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Empty());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();

         for (int i = 0; i < Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).iArmyRegimentSize; i++) {
            if (ArmyManager.lUnitsTypes.get(Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(i).uID).Line < 2) {
               int tUnits = Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(i).num;
               int numOfRegiments = 1;

               for (int o = i + 1;
                  o < Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).iArmyRegimentSize
                     && Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(i).uID
                        == Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(o).uID
                     && Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(i).aID
                        == Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(o).aID;
                  o++
               ) {
                  i++;
                  numOfRegiments++;
                  tUnits += Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(i).num;
               }

               nData.add(
                  new MenuElement_HoverElement_Type_BattleArmy(
                     "" + tUnits,
                     Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(i).uID,
                     Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(i).aID,
                     i > 0 ? CFG.PADDING : 0
                  )
               );
            }
         }

         for (int ix = 0; ix < Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).iArmyRegimentSize; ix++) {
            if (ArmyManager.lUnitsTypes.get(Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(ix).uID).Line >= 2) {
               int tUnits = Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(ix).num;
               int numOfRegiments = 1;

               for (int o = ix + 1;
                  o < Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).iArmyRegimentSize
                     && Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(ix).uID
                        == Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(o).uID
                     && Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(ix).aID
                        == Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(o).aID;
                  o++
               ) {
                  ix++;
                  numOfRegiments++;
                  tUnits += Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(ix).num;
               }

               nData.add(
                  new MenuElement_HoverElement_Type_BattleArmy(
                     "" + tUnits,
                     Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(ix).uID,
                     Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).lArmyRegiment.get(ix).aID,
                     ix > 0 ? CFG.PADDING : 0
                  )
               );
            }
         }

         if (nData.size() > 0) {
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      } else {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SelectArmy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   public int getSFX() {
      return Game.soundsManager.getSelectedArmy();
   }
}
