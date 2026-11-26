package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyRecruit;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Flag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_QueueUnit extends Button {
   public ArmyRecruit armyRecruit;

   public Button_QueueUnit(int nPosX, int nPosY, ArmyRecruit armyRecruit) {
      this.init(
         null,
         CFG.FONT_BOLD_SMALL,
         -1,
         nPosX,
         nPosY,
         ImageManager.getImage(Images.unitsFrameMap).getWidth(),
         ImageManager.getImage(Images.unitsFrameMap).getHeight(),
         true,
         true,
         false,
         false
      );
      this.armyRecruit = armyRecruit;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Image progressBarFrame = ImageManager.getImage(Images.progressBarFrame);
      Image progressBarFrameMask = ImageManager.getImage(Images.progressBarFrameMask);
      Image unitsFrameMap = ImageManager.getImage(Images.unitsFrameMap);
      oSB.setShader(Renderer.shaderAlpha);
      ArmyManager.armyImages.get(ArmyManager.lArmy.get(this.armyRecruit.unitID).get(this.armyRecruit.armyID).ImageID).getTexture().bind(1);
      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.unitsFrameMapMask).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      unitsFrameMap.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      int tCenterX = (progressBarFrame.getWidth() - progressBarFrameMask.getWidth()) / 2;
      int tCenterY = (progressBarFrame.getHeight() - progressBarFrameMask.getHeight()) / 2;
      oSB.setColor(new Color(ProvinceDraw.progressBarBG));
      progressBarFrameMask.draw(
         oSB, this.getPosX() + iTranslateX + tCenterX, this.getPosY() + iTranslateY + unitsFrameMap.getHeight() - progressBarFrame.getHeight() + tCenterY
      );
      oSB.setColor(ProvinceDraw.progressBar);
      progressBarFrameMask.draw(
         oSB,
         this.getPosX() + iTranslateX + tCenterX,
         this.getPosY() + iTranslateY + unitsFrameMap.getHeight() - progressBarFrame.getHeight() + tCenterY,
         (int)Math.max(
            1.0F,
            progressBarFrameMask.getWidth()
               * (
                  (float)(ArmyManager.lArmy.get(this.armyRecruit.unitID).get(this.armyRecruit.armyID).RecruitmentTime - this.armyRecruit.timeLeft)
                     / ArmyManager.lArmy.get(this.armyRecruit.unitID).get(this.armyRecruit.armyID).RecruitmentTime
               )
         ),
         progressBarFrameMask.getHeight()
      );
      oSB.setColor(Color.WHITE);
      progressBarFrame.draw(
         oSB,
         this.getPosX() + iTranslateX + unitsFrameMap.getWidth() / 2 - progressBarFrame.getWidth() / 2,
         this.getPosY() + iTranslateY + unitsFrameMap.getHeight() - progressBarFrame.getHeight()
      );
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   public void actionElement() {
      Game.mapCoords.centerToProvinceID(this.armyRecruit.provinceID);
   }

   @Override
   public void actionElementPPM() {
      Game.getCiv(Game.player.iCivID).cancelRecruitArmy(this.armyRecruit.provinceID);
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(
         new MenuElement_HoverElement_Type_TextTitle_BG_Center(
            ArmyManager.lArmy.get(this.armyRecruit.unitID).get(this.armyRecruit.armyID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Attack") + ": ",
            "" + ArmyManager.lArmy.get(this.armyRecruit.unitID).get(this.armyRecruit.armyID).getAttack(Game.player.iCivID),
            Images.attack,
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Defense") + ": ",
            "" + ArmyManager.lArmy.get(this.armyRecruit.unitID).get(this.armyRecruit.armyID).getDefense(Game.player.iCivID),
            Images.defense,
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("MovementSpeed") + ": ",
            "" + ArmyManager.lArmy.get(this.armyRecruit.unitID).get(this.armyRecruit.armyID).MovementSpeed,
            Images.movementSpeed,
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("AttackRange") + ": ",
            "" + ArmyManager.lArmy.get(this.armyRecruit.unitID).get(this.armyRecruit.armyID).AttackRange,
            Images.attack,
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("SiegeAbility") + ": ",
            "" + CFG.getPrecision2(ArmyManager.lArmy.get(this.armyRecruit.unitID).get(this.armyRecruit.armyID).SiegeProgress, 100),
            Images.siege,
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BattlePosition") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            ""
               + (
                  ArmyManager.lUnitsTypes.get(this.armyRecruit.unitID).Line != 0 && ArmyManager.lUnitsTypes.get(this.armyRecruit.unitID).Line != 1
                     ? (ArmyManager.lUnitsTypes.get(this.armyRecruit.unitID).Line == 2 ? Game.lang.get("Support") : Game.lang.get("ThirdLine"))
                     : Game.lang.get("FirstLine")
               ),
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.getProvince(this.armyRecruit.provinceID).getProvinceName(), Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_Flag(Game.getProvince(this.armyRecruit.provinceID).getCivID(), CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
