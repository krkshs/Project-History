package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.LegacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menusInGame.InGame_Legacies;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonLegacy extends Button {
   public int legacyID;
   public int currentLvl;
   public String sUnlock;
   public int iUnlockWidth;

   public ButtonLegacy(int iPosX, int iPosY, int legacyID, int currentLvl) {
      this.legacyID = legacyID;
      this.currentLvl = currentLvl;
      this.init(
         Game.lang.get(LegacyManager.legacies.get(legacyID).Name),
         CFG.FONT_REGULAR_SMALL,
         -1,
         iPosX,
         iPosY,
         LegacyManager.legacyImages.get(LegacyManager.legacies.get(legacyID).ImageID).getWidth(),
         LegacyManager.legacyImages.get(LegacyManager.legacies.get(legacyID).ImageID).getHeight(),
         true,
         true,
         false,
         false
      );
      if (currentLvl + 1 < LegacyManager.legacies.get(legacyID).CostLegacy.length) {
         this.sUnlock = Game.lang.get("Unlock");
      } else {
         this.sUnlock = Game.lang.get("Max");
      }

      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sUnlock);
      this.iUnlockWidth = (int)Renderer.glyphLayout.width;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.currentLvl == LegacyManager.legacies.get(this.legacyID).CostLegacy.length - 1) {
         LegacyManager.legacyImages
            .get(LegacyManager.legacies.get(this.legacyID).ImageID)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      } else if (!this.getIsHovered() && !isActive) {
         oSB.setShader(Renderer.shaderBlackWhite);
         LegacyManager.legacyImages
            .get(LegacyManager.legacies.get(this.legacyID).ImageID)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
         oSB.setShader(Renderer.shaderDefault);
         if (this.currentLvl >= 0) {
            oSB.setColor(
               new Color(1.0F, 1.0F, 1.0F, 0.25F + 0.75F * ((float)(this.currentLvl + 1) / LegacyManager.legacies.get(this.legacyID).CostLegacy.length))
            );
            LegacyManager.legacyImages
               .get(LegacyManager.legacies.get(this.legacyID).ImageID)
               .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
            oSB.setColor(Color.WHITE);
         }
      } else {
         LegacyManager.legacyImages
            .get(LegacyManager.legacies.get(this.legacyID).ImageID)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      }

      if (InGame_Legacies.bgAlpha < 1.0F) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, (1.0F - InGame_Legacies.bgAlpha) / 4.0F));
         oSB.setShader(Renderer.shaderAlpha);
         LegacyManager.legacyImages.get(LegacyManager.legacies.get(this.legacyID).ImageID).getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               LegacyManager.legacyImages.get(LegacyManager.legacies.get(this.legacyID).ImageID).getWidth(),
               LegacyManager.legacyImages.get(LegacyManager.legacies.get(this.legacyID).ImageID).getHeight(),
               false,
               true
            );
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               LegacyManager.legacyImages.get(LegacyManager.legacies.get(this.legacyID).ImageID).getWidth(),
               LegacyManager.legacyImages.get(LegacyManager.legacies.get(this.legacyID).ImageID).getHeight()
            );
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() + iTranslateY, this.getWidth(), CFG.PADDING);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered()) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
         Renderer.drawBoxCornerAlpha(
            oSB,
            this.getPosX() - CFG.PADDING + this.getWidth() / 2 - this.iUnlockWidth / 2 + iTranslateX,
            this.getPosY() - CFG.PADDING * 3 + this.getHeight() - this.getTextHeight() + iTranslateY,
            this.iUnlockWidth + CFG.PADDING * 2,
            this.getTextHeight() + CFG.PADDING * 2
         );
         oSB.setColor(Color.WHITE);
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.sUnlock,
            this.getPosX() + this.getWidth() / 2 - this.iUnlockWidth / 2 + iTranslateX,
            this.getPosY() + this.getHeight() - this.getTextHeight() - CFG.PADDING * 2 + iTranslateY,
            Colors.COLOR_TEXT_MODIFIER_POSITIVE
         );
         oSB.setColor(ProvinceDraw.progressBarBG);
         ImageManager.getImage(Images.levelMask)
            .draw(
               oSB,
               this.getPosX() + CFG.PADDING + iTranslateX,
               this.getPosY() + this.getHeight() - CFG.PADDING - ImageManager.getImage(Images.levelFrame).getHeight() + iTranslateY
            );
         if (this.currentLvl >= 0) {
            oSB.setColor(ProvinceDraw.progressBar);
            ImageManager.getImage(Images.levelMask)
               .draw2(
                  oSB,
                  this.getPosX() + CFG.PADDING + iTranslateX,
                  this.getPosY() + this.getHeight() - CFG.PADDING - ImageManager.getImage(Images.levelFrame).getHeight() + iTranslateY,
                  ImageManager.getImage(Images.levelMask).getWidth() * (this.currentLvl + 1) / LegacyManager.legacies.get(this.legacyID).CostLegacy.length,
                  ImageManager.getImage(Images.levelMask).getHeight()
               );
         }

         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.levelFrame)
            .draw(
               oSB,
               this.getPosX() + CFG.PADDING + iTranslateX,
               this.getPosY() + this.getHeight() - CFG.PADDING - ImageManager.getImage(Images.levelFrame).getHeight() + iTranslateY
            );
      } else {
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.getTextToDraw(),
            this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
            this.getPosY() + this.getHeight() - this.getTextHeight() - CFG.PADDING * 2 + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStats4(isActive, this.getIsHovered());
   }

   @Override
   public int getCurrent() {
      return this.legacyID;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nData.add(
         new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
            " [" + Game.lang.get("Unlocked") + ": " + (this.currentLvl + 1) + "/" + LegacyManager.legacies.get(this.legacyID).CostLegacy.length + "]",
            CFG.FONT_REGULAR,
            Colors.HOVER_RIGHT2
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (LegacyManager.legacies.get(this.legacyID).ConstructionCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ConstructionCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int i = 0; i < LegacyManager.legacies.get(this.legacyID).ConstructionCost.length; i++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).ConstructionCost[i] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).ConstructionCost[i] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).ConstructionCost[i] * 100.0F, 10)
                        + "%",
                  i == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  i == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (i != LegacyManager.legacies.get(this.legacyID).ConstructionCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.construction, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).AdministrationBuildingsCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AdministrationBuildingsCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ix = 0; ix < LegacyManager.legacies.get(this.legacyID).AdministrationBuildingsCost.length; ix++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).AdministrationBuildingsCost[ix] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).AdministrationBuildingsCost[ix] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).AdministrationBuildingsCost[ix] * 100.0F, 10)
                        + "%",
                  ix == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ix == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ix != LegacyManager.legacies.get(this.legacyID).AdministrationBuildingsCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.construction, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MilitaryBuildingsCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MilitaryBuildingsCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixx = 0; ixx < LegacyManager.legacies.get(this.legacyID).MilitaryBuildingsCost.length; ixx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MilitaryBuildingsCost[ixx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).MilitaryBuildingsCost[ixx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).MilitaryBuildingsCost[ixx] * 100.0F, 10)
                        + "%",
                  ixx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixx != LegacyManager.legacies.get(this.legacyID).MilitaryBuildingsCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.construction, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).EconomyBuildingsCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("EconomyBuildingsCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxx = 0; ixxx < LegacyManager.legacies.get(this.legacyID).EconomyBuildingsCost.length; ixxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).EconomyBuildingsCost[ixxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).EconomyBuildingsCost[ixxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).EconomyBuildingsCost[ixxx] * 100.0F, 10)
                        + "%",
                  ixxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxx != LegacyManager.legacies.get(this.legacyID).EconomyBuildingsCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.construction, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).ConstructionTime != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ConstructionTime") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxx = 0; ixxxx < LegacyManager.legacies.get(this.legacyID).ConstructionTime.length; ixxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).ConstructionTime[ixxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).ConstructionTime[ixxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).ConstructionTime[ixxxx] * 100.0F, 10)
                        + "%",
                  ixxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxx != LegacyManager.legacies.get(this.legacyID).ConstructionTime.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.buildTime, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).WonderConstructionCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("WonderConstructionCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxx = 0; ixxxxx < LegacyManager.legacies.get(this.legacyID).WonderConstructionCost.length; ixxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).WonderConstructionCost[ixxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).WonderConstructionCost[ixxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).WonderConstructionCost[ixxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxx != LegacyManager.legacies.get(this.legacyID).WonderConstructionCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.mapModesWonders, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaximumAmountOfGold != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumAmountOfGold") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxx = 0; ixxxxxx < LegacyManager.legacies.get(this.legacyID).MaximumAmountOfGold.length; ixxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaximumAmountOfGold[ixxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).MaximumAmountOfGold[ixxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).MaximumAmountOfGold[ixxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxx != LegacyManager.legacies.get(this.legacyID).MaximumAmountOfGold.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).Loot != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Loot") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxx = 0; ixxxxxxx < LegacyManager.legacies.get(this.legacyID).Loot.length; ixxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).Loot[ixxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).Loot[ixxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).Loot[ixxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxx != LegacyManager.legacies.get(this.legacyID).Loot.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.loot, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).TaxEfficiency != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("TaxEfficiency") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxx = 0; ixxxxxxxx < LegacyManager.legacies.get(this.legacyID).TaxEfficiency.length; ixxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).TaxEfficiency[ixxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).TaxEfficiency[ixxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).TaxEfficiency[ixxxxxxxx], 10)
                        + "%",
                  ixxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxx != LegacyManager.legacies.get(this.legacyID).TaxEfficiency.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.tax, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).ProvinceMaintenance != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ProvinceMaintenance") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxx = 0; ixxxxxxxxx < LegacyManager.legacies.get(this.legacyID).ProvinceMaintenance.length; ixxxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).ProvinceMaintenance[ixxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).ProvinceMaintenance[ixxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).ProvinceMaintenance[ixxxxxxxxx], 10)
                        + "%",
                  ixxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxx != LegacyManager.legacies.get(this.legacyID).ProvinceMaintenance.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.provinces, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).BuildingsMaintenanceCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BuildingsMaintenanceCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxx = 0; ixxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).BuildingsMaintenanceCost.length; ixxxxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).BuildingsMaintenanceCost[ixxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).BuildingsMaintenanceCost[ixxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).BuildingsMaintenanceCost[ixxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).BuildingsMaintenanceCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.buildings, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).ManpowerRecoverySpeed != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ManpowerRecoverySpeed") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxx = 0; ixxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).ManpowerRecoverySpeed.length; ixxxxxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).ManpowerRecoverySpeed[ixxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).ManpowerRecoverySpeed[ixxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).ManpowerRecoverySpeed[ixxxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).ManpowerRecoverySpeed.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER_TIME, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaxManpower != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumManpower") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxx = 0; ixxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).MaxManpower.length; ixxxxxxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaxManpower[ixxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).MaxManpower[ixxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getNumberWithSpaces("" + LegacyManager.legacies.get(this.legacyID).MaxManpower[ixxxxxxxxxxxx])
                        + "",
                  ixxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).MaxManpower.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER_UP, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).Research != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Research") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxx = 0; ixxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).Research.length; ixxxxxxxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).Research[ixxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).Research[ixxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).Research[ixxxxxxxxxxxxx], 10)
                        + "%",
                  ixxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).Research.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).ResearchPoints != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ResearchPerMonth") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).ResearchPoints.length; ixxxxxxxxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).ResearchPoints[ixxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).ResearchPoints[ixxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).ResearchPoints[ixxxxxxxxxxxxxx], 10),
                  ixxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).ResearchPoints.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).GrowthRate != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("GrowthRate") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).GrowthRate.length; ixxxxxxxxxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).GrowthRate[ixxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).GrowthRate[ixxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).GrowthRate[ixxxxxxxxxxxxxxx], 10)
                        + "%",
                  ixxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).GrowthRate.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.populationGrowth, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).IncomeProduction != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("IncomeProduction") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).IncomeProduction.length; ixxxxxxxxxxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).IncomeProduction[ixxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).IncomeProduction[ixxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).IncomeProduction[ixxxxxxxxxxxxxxxx], 10)
                        + "%",
                  ixxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).IncomeProduction.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.goods, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).InvestInEconomyCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("InvestInEconomyCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).InvestInEconomyCost.length; ixxxxxxxxxxxxxxxxx++) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).InvestInEconomyCost[ixxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).InvestInEconomyCost[ixxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).InvestInEconomyCost[ixxxxxxxxxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).InvestInEconomyCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_ECONOMY_UP, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).IncreaseTaxEfficiencyCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("IncreaseTaxEfficiencyCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).IncreaseTaxEfficiencyCost.length;
            ixxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).IncreaseTaxEfficiencyCost[ixxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).IncreaseTaxEfficiencyCost[ixxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).IncreaseTaxEfficiencyCost[ixxxxxxxxxxxxxxxxxx] * 100.0F, 100)
                        + "%",
                  ixxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).IncreaseTaxEfficiencyCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.taxUp, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).IncreaseGrowthRateCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("IncreaseGrowthRateCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).IncreaseGrowthRateCost.length;
            ixxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).IncreaseGrowthRateCost[ixxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).IncreaseGrowthRateCost[ixxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).IncreaseGrowthRateCost[ixxxxxxxxxxxxxxxxxxx] * 100.0F, 100)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).IncreaseGrowthRateCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.populationGrowth, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).DevelopInfrastructureCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DevelopInfrastructureCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).DevelopInfrastructureCost.length;
            ixxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).DevelopInfrastructureCost[ixxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).DevelopInfrastructureCost[ixxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).DevelopInfrastructureCost[ixxxxxxxxxxxxxxxxxxxx] * 100.0F, 100)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).DevelopInfrastructureCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.infrastructureUp, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).ProductionEfficiency != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ProductionEfficiency") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).ProductionEfficiency.length;
            ixxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).ProductionEfficiency[ixxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).ProductionEfficiency[ixxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).ProductionEfficiency[ixxxxxxxxxxxxxxxxxxxxx], 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).ProductionEfficiency.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.goods, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).GeneralAttack != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("GeneralsAttack") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).GeneralAttack.length;
            ixxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).GeneralAttack[ixxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).GeneralAttack[ixxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).GeneralAttack[ixxxxxxxxxxxxxxxxxxxxxx], 10),
                  ixxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).GeneralAttack.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.attack, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).GeneralDefense != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("GeneralsDefense") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).GeneralDefense.length;
            ixxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).GeneralDefense[ixxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).GeneralDefense[ixxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).GeneralDefense[ixxxxxxxxxxxxxxxxxxxxxxx], 10),
                  ixxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).GeneralDefense.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.defense, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).RegimentsLimit != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RegimentsLimit") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).RegimentsLimit.length;
            ixxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).RegimentsLimit[ixxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).RegimentsLimit[ixxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).RegimentsLimit[ixxxxxxxxxxxxxxxxxxxxxxxx], 1),
                  ixxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).RegimentsLimit.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.regimentsLimit, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).BattleWidth != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BattleWidth") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).BattleWidth.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).BattleWidth[ixxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).BattleWidth[ixxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).BattleWidth[ixxxxxxxxxxxxxxxxxxxxxxxxx], 1),
                  ixxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).BattleWidth.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.battleWidth, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).AllCharactersLifeExpectancy != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AllCharactersLifeExpectancy") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).AllCharactersLifeExpectancy.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).AllCharactersLifeExpectancy[ixxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).AllCharactersLifeExpectancy[ixxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + Game.lang.get("YearsX", LegacyManager.legacies.get(this.legacyID).AllCharactersLifeExpectancy[ixxxxxxxxxxxxxxxxxxxxxxxxxx]),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).AllCharactersLifeExpectancy.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.council, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).ManpowerRecoveryFromADisbandedArmy != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ManpowerRecoveryFromADisbandedArmy") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).ManpowerRecoveryFromADisbandedArmy.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).ManpowerRecoveryFromADisbandedArmy[ixxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).ManpowerRecoveryFromADisbandedArmy[ixxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(
                           LegacyManager.legacies.get(this.legacyID).ManpowerRecoveryFromADisbandedArmy[ixxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10
                        )
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).ManpowerRecoveryFromADisbandedArmy.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER_DISBAND, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).Discipline != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Discipline") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).Discipline.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).Discipline[ixxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).Discipline[ixxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).Discipline[ixxxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).Discipline.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.discipline, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).UnitsAttack != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnitsAttack") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).UnitsAttack.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).UnitsAttack[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).UnitsAttack[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).UnitsAttack[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).UnitsAttack.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.attack, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).UnitsDefense != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnitsDefense") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).UnitsDefense.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).UnitsDefense[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).UnitsDefense[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).UnitsDefense[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).UnitsDefense.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.defense, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaxMorale != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaxMorale") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).MaxMorale.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaxMorale[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).MaxMorale[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).MaxMorale[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).MaxMorale.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.morale, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).SiegeEffectiveness != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("SiegeEffectiveness") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).SiegeEffectiveness.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).SiegeEffectiveness[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).SiegeEffectiveness[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).SiegeEffectiveness[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).SiegeEffectiveness.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.siege, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).ImproveRelationsModifier != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ImproveRelationsModifier") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).ImproveRelationsModifier.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).ImproveRelationsModifier[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).ImproveRelationsModifier[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).ImproveRelationsModifier[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).ImproveRelationsModifier.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.relations, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).IncomeFromVassals != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("IncomeFromVassals") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).IncomeFromVassals.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).IncomeFromVassals[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).IncomeFromVassals[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).IncomeFromVassals[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).IncomeFromVassals.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).DiplomacyPoints != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DiplomacyPoints") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).DiplomacyPoints.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).DiplomacyPoints[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).DiplomacyPoints[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).DiplomacyPoints[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).DiplomacyPoints.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.diplomacy, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).LoanInterest != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("LoanInterest") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).LoanInterest.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).LoanInterest[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).LoanInterest[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).LoanInterest[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).LoanInterest.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.loan, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaxNumberOfLoans != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumNumberOfLoans") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).MaxNumberOfLoans.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaxNumberOfLoans[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).MaxNumberOfLoans[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).MaxNumberOfLoans[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 1),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).MaxNumberOfLoans.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.loan, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaxNumOfAlliances != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaxNumOfAlliances") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).MaxNumOfAlliances.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaxNumOfAlliances[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).MaxNumOfAlliances[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).MaxNumOfAlliances[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 1),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).MaxNumOfAlliances.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.diplomacy, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).AdvisorMaxLevel != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumAdvisorSkillLevel") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).AdvisorMaxLevel.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).AdvisorMaxLevel[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).AdvisorMaxLevel[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).AdvisorMaxLevel[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 1),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).AdvisorMaxLevel.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.skill, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).AdvisorPoolSize != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AdvisorPool") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).AdvisorPoolSize.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).AdvisorPoolSize[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).AdvisorPoolSize[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2((float)LegacyManager.legacies.get(this.legacyID).AdvisorPoolSize[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 1),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).AdvisorPoolSize.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.council, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).AdvisorCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AdvisorCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).AdvisorCost.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).AdvisorCost[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).AdvisorCost[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).AdvisorCost[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).AdvisorCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.council, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademyForGenerals != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumLevelOfTheMilitaryAcademyForGenerals") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademyForGenerals.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademyForGenerals[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (
                           LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademyForGenerals[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx]
                                 > 0
                              ? "+"
                              : ""
                        )
                        + CFG.getPrecision2(
                           (float)LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademyForGenerals[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx],
                           1
                        ),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademyForGenerals.length - 1
               )
             {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademy != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumLevelOfTheMilitaryAcademy") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademy.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademy[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (
                           LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademy[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0
                              ? "+"
                              : ""
                        )
                        + CFG.getPrecision2(
                           (float)LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademy[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10
                        ),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheMilitaryAcademy.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheSupremeCourt != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumLevelOfTheSupremeCourt") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheSupremeCourt.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheSupremeCourt[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (
                           LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheSupremeCourt[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0
                              ? "+"
                              : ""
                        )
                        + CFG.getPrecision2(
                           (float)LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheSupremeCourt[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10
                        ),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).MaximumLevelOfTheSupremeCourt.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.stability, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaximumLevelOfCapitalCity != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumLevelOfCapitalCity") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).MaximumLevelOfCapitalCity.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaximumLevelOfCapitalCity[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).MaximumLevelOfCapitalCity[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2(
                           (float)LegacyManager.legacies.get(this.legacyID).MaximumLevelOfCapitalCity[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10
                        ),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).MaximumLevelOfCapitalCity.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.capital, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).GeneralCost != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("GeneralCost") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).GeneralCost.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).GeneralCost[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).GeneralCost[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).GeneralCost[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).GeneralCost.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).AggressiveExpansion != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AggressiveExpansion") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).AggressiveExpansion.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).AggressiveExpansion[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).AggressiveExpansion[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).AggressiveExpansion[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).AggressiveExpansion.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.war, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).DiseaseDeathRate != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DiseasesDeathRate") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).DiseaseDeathRate.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).DiseaseDeathRate[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).DiseaseDeathRate[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(
                           LegacyManager.legacies.get(this.legacyID).DiseaseDeathRate[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10
                        )
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).DiseaseDeathRate.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.disease, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).RecruitmentTime != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RecruitmentTime") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).RecruitmentTime.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).RecruitmentTime[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).RecruitmentTime[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(LegacyManager.legacies.get(this.legacyID).RecruitmentTime[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10)
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).RecruitmentTime.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER_TIME, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).ArmyMovementSpeed != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ArmyMovementSpeed") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).ArmyMovementSpeed.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).ArmyMovementSpeed[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).ArmyMovementSpeed[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(
                           LegacyManager.legacies.get(this.legacyID).ArmyMovementSpeed[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10
                        )
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).ArmyMovementSpeed.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.movementSpeed, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).BuildingSlot != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AdditionalBuildingsInProvince") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).BuildingSlot.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).BuildingSlot[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).BuildingSlot[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2(
                           (float)LegacyManager.legacies.get(this.legacyID).BuildingSlot[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 1
                        ),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).BuildingSlot.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.build, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).MaxInfrastructure != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumInfrastructureLevel") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).MaxInfrastructure.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).MaxInfrastructure[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).MaxInfrastructure[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0 ? "+" : "")
                        + CFG.getPrecision2(
                           (float)LegacyManager.legacies.get(this.legacyID).MaxInfrastructure[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx], 10
                        ),
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).MaxInfrastructure.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.infrastructure, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (LegacyManager.legacies.get(this.legacyID).Devastation != null) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Devastation") + ": ", CFG.FONT_REGULAR_SMALL));

         for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < LegacyManager.legacies.get(this.legacyID).Devastation.length;
            ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  LegacyManager.legacies.get(this.legacyID).Devastation[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] == 0.0F
                     ? "-"
                     : ""
                        + (LegacyManager.legacies.get(this.legacyID).Devastation[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] > 0.0F ? "+" : "")
                        + CFG.getPrecision2(
                           LegacyManager.legacies.get(this.legacyID).Devastation[ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * 100.0F, 10
                        )
                        + "%",
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == this.currentLvl ? Colors.HOVER_POSITIVE : Colors.HOVER_RIGHT
               )
            );
            if (ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != LegacyManager.legacies.get(this.legacyID).Devastation.length - 1) {
               nData.add(new MenuElement_HoverElement_Type_Text(" / ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
            }
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.devastation, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (this.currentLvl + 1 >= LegacyManager.legacies.get(this.legacyID).CostLegacy.length) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Max"), CFG.FONT_REGULAR_SMALL));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      } else {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("LegacyPoints") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + LegacyManager.legacies.get(this.legacyID).CostLegacy[this.currentLvl + 1],
               CFG.FONT_BOLD_SMALL,
               Game.getCiv(Game.player.iCivID).fLegacy >= LegacyManager.legacies.get(this.legacyID).CostLegacy[this.currentLvl + 1]
                  ? Colors.HOVER_GOLD
                  : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.legacy, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
