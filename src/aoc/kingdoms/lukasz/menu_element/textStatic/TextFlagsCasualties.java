package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_BattleArmy;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battle;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class TextFlagsCasualties extends Button {
   public String sLeft;
   public String sCasualties;
   public int iCasualtiesWidth;
   public int iCasualtiesHeight;
   public int iconWidth;
   public int iconHeight;
   public int iconWidth2;
   public int iconHeight2;
   public int iconWidth3;
   public int iconHeight3;
   public boolean defenders;
   private List<TextFlagsCasualties.BattleStatsRegiments> tRegiments = new ArrayList<>();

   public TextFlagsCasualties(String sText, String sLeft, String sCasualties, int nPosX, int nPosY, int nWidth, int nHeight, boolean defenders) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, -1, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.defenders = defenders;
      this.sLeft = sLeft;
      this.sCasualties = sCasualties;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sCasualties);
      this.iCasualtiesWidth = (int)Renderer.glyphLayout.width;
      this.iCasualtiesHeight = (int)Renderer.glyphLayout.height;
      float iconScale = this.getImageScale(Images.skull);
      this.iconWidth = (int)(ImageManager.getImage(Images.skull).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(Images.skull).getHeight() * iconScale);
      iconScale = this.getImageScale(Images.retreat);
      this.iconWidth3 = (int)(ImageManager.getImage(Images.retreat).getWidth() * iconScale);
      this.iconHeight3 = (int)(ImageManager.getImage(Images.retreat).getHeight() * iconScale);
      iconScale = this.getImageScale(Game_Calendar.IMG_MANPOWER);
      this.iconWidth2 = (int)(ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth() * iconScale);
      this.iconHeight2 = (int)(ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getHeight() * iconScale);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.skull)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING - this.iconWidth + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iconHeight / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      ImageManager.getImage(Images.retreat)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.iconWidth - this.iconWidth3 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iconHeight / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      ImageManager.getImage(Game_Calendar.IMG_MANPOWER)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iconHeight2 / 2 + iTranslateY,
            this.iconWidth2,
            this.iconHeight2
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sCasualties,
         this.getPosX() + this.getWidth() - CFG.PADDING * 3 - this.iconWidth - this.iconWidth3 - this.iCasualtiesWidth + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iCasualtiesHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sLeft,
         this.getPosX() + CFG.PADDING * 2 + this.iconWidth2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iCasualtiesHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStatsHover(isActive, this.getIsHovered());
   }

   private final float getImageScale() {
      return (float)CFG.TEXT_HEIGHT / CFG.CIV_FLAG_HEIGHT;
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("NumberOfUnitsOnTheBattlefield") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
      nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (InGame_Battle.battleID >= 0) {
         this.tRegiments.clear();
         if (this.defenders) {
            int i = 0;

            for (int iSize = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.size(); i < iSize; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i) != null) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).aR.num
                  );
               }
            }

            i = 0;

            for (int iSizex = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.size(); i < iSizex; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i) != null) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).aR.num
                  );
               }
            }
         } else {
            int i = 0;

            for (int iSizexx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.size(); i < iSizexx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i) != null) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.num
                  );
               }
            }

            i = 0;

            for (int iSizexxx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.size(); i < iSizexxx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i) != null) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.num
                  );
               }
            }
         }

         List<Integer> tSorted = new ArrayList<>();
         List<Integer> tempReg = new ArrayList<>();
         int i = 0;

         for (int iSizexxxx = this.tRegiments.size(); i < iSizexxxx; i++) {
            tempReg.add(i);
         }

         while (tempReg.size() > 0) {
            i = 0;
            int ix = 1;

            for (int iSizexxxx = tempReg.size(); ix < iSizexxxx; ix++) {
               if (this.tRegiments.get(tempReg.get(i)).unitTypeID > this.tRegiments.get(tempReg.get(ix)).unitTypeID) {
                  i = ix;
               } else if (this.tRegiments.get(tempReg.get(i)).unitTypeID == this.tRegiments.get(tempReg.get(ix)).unitTypeID
                  && this.tRegiments.get(tempReg.get(i)).armyID > this.tRegiments.get(tempReg.get(ix)).armyID) {
                  i = ix;
               }
            }

            tSorted.add(tempReg.get(i));
            tempReg.remove(i);
         }

         for (int j = 0; j < 3; j++) {
            int ix = 0;

            for (int iSizexxxxx = this.tRegiments.size(); ix < iSizexxxxx; ix++) {
               if (ArmyManager.lUnitsTypes.get(this.tRegiments.get(tSorted.get(ix)).unitTypeID).Line == j) {
                  nData.add(
                     new MenuElement_HoverElement_Type_BattleArmy(
                        "" + this.tRegiments.get(tSorted.get(ix)).numOfUnits,
                        this.tRegiments.get(tSorted.get(ix)).unitTypeID,
                        this.tRegiments.get(tSorted.get(ix)).armyID,
                        ix > 0 ? CFG.PADDING : 0
                     )
                  );
               }
            }
         }

         if (nData.size() > 0) {
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      }

      if (this.defenders) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Casualties") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.iCasualties),
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.skull, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      } else {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Casualties") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.iCasualties),
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.skull, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (InGame_Battle.battleID >= 0) {
         this.tRegiments.clear();
         if (this.defenders) {
            int i = 0;

            for (int iSizexxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.size(); i < iSizexxxxxx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i) != null
                  && Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).ca > 0) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).ca
                  );
               }
            }

            i = 0;

            for (int iSizexxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.size(); i < iSizexxxxxxx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i) != null
                  && Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).ca > 0) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).ca
                  );
               }
            }

            i = 0;

            for (int iSizexxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.defeated.size(); i < iSizexxxxxxxx; i++) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.defeated.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.defeated.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.defeated.get(i).ca
               );
            }
         } else {
            int i = 0;

            for (int iSizexxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.size(); i < iSizexxxxxxxx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i) != null
                  && Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).ca > 0) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).ca
                  );
               }
            }

            i = 0;

            for (int iSizexxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.size(); i < iSizexxxxxxxxx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i) != null
                  && Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).ca > 0) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).ca
                  );
               }
            }

            i = 0;

            for (int iSizexxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.size(); i < iSizexxxxxxxxxx; i++) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).ca
               );
            }
         }

         List<Integer> tSorted = new ArrayList<>();
         List<Integer> tempReg = new ArrayList<>();
         int i = 0;

         for (int iSizexxxxxxxxxx = this.tRegiments.size(); i < iSizexxxxxxxxxx; i++) {
            tempReg.add(i);
         }

         while (tempReg.size() > 0) {
            i = 0;
            int ix = 1;

            for (int iSizexxxxxxxxxx = tempReg.size(); ix < iSizexxxxxxxxxx; ix++) {
               if (this.tRegiments.get(tempReg.get(i)).unitTypeID > this.tRegiments.get(tempReg.get(ix)).unitTypeID) {
                  i = ix;
               } else if (this.tRegiments.get(tempReg.get(i)).unitTypeID == this.tRegiments.get(tempReg.get(ix)).unitTypeID
                  && this.tRegiments.get(tempReg.get(i)).armyID > this.tRegiments.get(tempReg.get(ix)).armyID) {
                  i = ix;
               }
            }

            tSorted.add(tempReg.get(i));
            tempReg.remove(i);
         }

         for (int j = 0; j < 3; j++) {
            int ix = 0;

            for (int iSizexxxxxxxxxxx = this.tRegiments.size(); ix < iSizexxxxxxxxxxx; ix++) {
               if (ArmyManager.lUnitsTypes.get(this.tRegiments.get(tSorted.get(ix)).unitTypeID).Line == j) {
                  nData.add(
                     new MenuElement_HoverElement_Type_BattleArmy(
                        "" + this.tRegiments.get(tSorted.get(ix)).numOfUnits,
                        this.tRegiments.get(tSorted.get(ix)).unitTypeID,
                        this.tRegiments.get(tSorted.get(ix)).armyID,
                        ix > 0 ? CFG.PADDING : 0
                     )
                  );
               }
            }
         }

         if (nData.size() > 0) {
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      }

      if (this.defenders) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Retreated") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.iRetreated),
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.retreat, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      } else {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Retreated") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               CFG.getNumberWithSpaces("" + Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.iRetreated),
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.retreat, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (InGame_Battle.battleID >= 0) {
         this.tRegiments.clear();
         if (this.defenders) {
            int i = 0;

            for (int iSizexxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.size(); i < iSizexxxxxxxxxxxx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i) != null
                  && Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).re > 0) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.firstLine.get(i).re
                  );
               }
            }

            i = 0;

            for (int iSizexxxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.size(); i < iSizexxxxxxxxxxxxx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i) != null
                  && Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).re > 0) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.secondLine.get(i).re
                  );
               }
            }

            i = 0;

            for (int iSizexxxxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.defeated.size(); i < iSizexxxxxxxxxxxxxx; i++) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.defeated.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.defeated.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.defeated.get(i).re
               );
            }
         } else {
            int i = 0;

            for (int iSizexxxxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.size(); i < iSizexxxxxxxxxxxxxx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i) != null
                  && Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).re > 0) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).re
                  );
               }
            }

            i = 0;

            for (int iSizexxxxxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.size(); i < iSizexxxxxxxxxxxxxxx; i++) {
               if (Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i) != null
                  && Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).re > 0) {
                  this.addRegiment(
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.uID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.aID,
                     Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).re
                  );
               }
            }

            i = 0;

            for (int iSizexxxxxxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.size(); i < iSizexxxxxxxxxxxxxxxx; i++) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).re
               );
            }
         }

         List<Integer> tSorted = new ArrayList<>();
         List<Integer> tempReg = new ArrayList<>();
         int i = 0;

         for (int iSizexxxxxxxxxxxxxxxx = this.tRegiments.size(); i < iSizexxxxxxxxxxxxxxxx; i++) {
            tempReg.add(i);
         }

         while (tempReg.size() > 0) {
            i = 0;
            int ix = 1;

            for (int iSizexxxxxxxxxxxxxxxx = tempReg.size(); ix < iSizexxxxxxxxxxxxxxxx; ix++) {
               if (this.tRegiments.get(tempReg.get(i)).unitTypeID > this.tRegiments.get(tempReg.get(ix)).unitTypeID) {
                  i = ix;
               } else if (this.tRegiments.get(tempReg.get(i)).unitTypeID == this.tRegiments.get(tempReg.get(ix)).unitTypeID
                  && this.tRegiments.get(tempReg.get(i)).armyID > this.tRegiments.get(tempReg.get(ix)).armyID) {
                  i = ix;
               }
            }

            tSorted.add(tempReg.get(i));
            tempReg.remove(i);
         }

         for (int j = 0; j < 3; j++) {
            int ix = 0;

            for (int iSizexxxxxxxxxxxxxxxxx = this.tRegiments.size(); ix < iSizexxxxxxxxxxxxxxxxx; ix++) {
               if (ArmyManager.lUnitsTypes.get(this.tRegiments.get(tSorted.get(ix)).unitTypeID).Line == j) {
                  nData.add(
                     new MenuElement_HoverElement_Type_BattleArmy(
                        "" + this.tRegiments.get(tSorted.get(ix)).numOfUnits,
                        this.tRegiments.get(tSorted.get(ix)).unitTypeID,
                        this.tRegiments.get(tSorted.get(ix)).armyID,
                        ix > 0 ? CFG.PADDING : 0
                     )
                  );
               }
            }
         }

         if (nData.size() > 0) {
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      }

      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnitsInReserve") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(new MenuElement_HoverElement_Type_Text("" + this.sLeft, CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
      nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (InGame_Battle.battleID >= 0) {
         this.tRegiments.clear();
         if (this.defenders) {
            int i = 0;

            for (int iSizexxxxxxxxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.reserveFirstLine.size();
               i < iSizexxxxxxxxxxxxxxxxxx;
               i++
            ) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.reserveFirstLine.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.reserveFirstLine.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.reserveFirstLine.get(i).aR.num
               );
            }

            i = 0;

            for (int iSizexxxxxxxxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.reserveSecondLine.size();
               i < iSizexxxxxxxxxxxxxxxxxx;
               i++
            ) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.reserveSecondLine.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.reserveSecondLine.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).defendingArmy.reserveSecondLine.get(i).aR.num
               );
            }
         } else {
            int i = 0;

            for (int iSizexxxxxxxxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveFirstLine.size();
               i < iSizexxxxxxxxxxxxxxxxxx;
               i++
            ) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveFirstLine.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveFirstLine.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveFirstLine.get(i).aR.num
               );
            }

            i = 0;

            for (int iSizexxxxxxxxxxxxxxxxxx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveSecondLine.size();
               i < iSizexxxxxxxxxxxxxxxxxx;
               i++
            ) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveSecondLine.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveSecondLine.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveSecondLine.get(i).aR.num
               );
            }
         }

         List<Integer> tSorted = new ArrayList<>();
         List<Integer> tempReg = new ArrayList<>();
         int i = 0;

         for (int iSizexxxxxxxxxxxxxxxxxx = this.tRegiments.size(); i < iSizexxxxxxxxxxxxxxxxxx; i++) {
            tempReg.add(i);
         }

         while (tempReg.size() > 0) {
            i = 0;
            int ix = 1;

            for (int iSizexxxxxxxxxxxxxxxxxx = tempReg.size(); ix < iSizexxxxxxxxxxxxxxxxxx; ix++) {
               if (this.tRegiments.get(tempReg.get(i)).unitTypeID > this.tRegiments.get(tempReg.get(ix)).unitTypeID) {
                  i = ix;
               } else if (this.tRegiments.get(tempReg.get(i)).unitTypeID == this.tRegiments.get(tempReg.get(ix)).unitTypeID
                  && this.tRegiments.get(tempReg.get(i)).armyID > this.tRegiments.get(tempReg.get(ix)).armyID) {
                  i = ix;
               }
            }

            tSorted.add(tempReg.get(i));
            tempReg.remove(i);
         }

         for (int j = 0; j < 3; j++) {
            int ix = 0;

            for (int iSizexxxxxxxxxxxxxxxxxxx = this.tRegiments.size(); ix < iSizexxxxxxxxxxxxxxxxxxx; ix++) {
               if (ArmyManager.lUnitsTypes.get(this.tRegiments.get(tSorted.get(ix)).unitTypeID).Line == j) {
                  nData.add(
                     new MenuElement_HoverElement_Type_BattleArmy(
                        "" + this.tRegiments.get(tSorted.get(ix)).numOfUnits,
                        this.tRegiments.get(tSorted.get(ix)).unitTypeID,
                        this.tRegiments.get(tSorted.get(ix)).armyID,
                        ix > 0 ? CFG.PADDING : 0
                     )
                  );
               }
            }
         }

         if (nData.size() > 0) {
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      }

      nData.add(
         new MenuElement_HoverElement_Type_Text_Desc(
            Game.lang.get("UnitsThatHaventBeenDeployedOnTheNattlefieldDueToTheCompleteOccupationOfAvailableSpace"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT2
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements) {
         @Override
         public int getMinPosX() {
            return InGame_Battle.HOVER_POSX;
         }

         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY) {
            nPosY = InGame_Battle.HOVER_POSY + (CFG.GAME_HEIGHT - InGame_Battle.HOVER_POSY) / 2 - this.iHeight / 2;
            super.draw(oSB, nPosX, nPosY);
         }
      };
   }

   public void addRegiment(int unitTypeID, int armyID, int numOfUnits) {
      int i = 0;

      for (int iSize = this.tRegiments.size(); i < iSize; i++) {
         if (this.tRegiments.get(i).unitTypeID == unitTypeID && this.tRegiments.get(i).armyID == armyID) {
            this.tRegiments.get(i).numOfUnits += numOfUnits;
            return;
         }
      }

      this.tRegiments.add(new TextFlagsCasualties.BattleStatsRegiments(unitTypeID, armyID, numOfUnits));
   }

   public static class BattleStatsRegiments {
      public int unitTypeID;
      public int armyID;
      public int numOfUnits;

      public BattleStatsRegiments(int unitTypeID, int armyID, int numOfUnits) {
         this.unitTypeID = unitTypeID;
         this.armyID = armyID;
         this.numOfUnits = numOfUnits;
      }
   }
}
