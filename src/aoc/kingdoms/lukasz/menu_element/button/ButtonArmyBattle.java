package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battle;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonArmyBattle extends Button {
   public int iCivID;
   public int numOfRegiments;
   public int iUnitTypeID;
   public int iArmyID;
   public int iAttackWidth;
   public int iDefenseWidth;
   public int unitImgWidth = 0;
   public boolean inBattleView;

   public ButtonArmyBattle(String nArmy, int numOfRegiments, int iCivID, int iPosX, int iPosY, int niUnitTypeID, int nArmyID, boolean inBattleView) {
      this.init(
         CFG.getNumberWithSpaces(nArmy),
         CFG.FONT_REGULAR_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         getButtonWidth(),
         getButtonHeight(),
         true,
         true,
         false,
         false
      );
      this.iUnitTypeID = niUnitTypeID;
      this.iArmyID = nArmyID;
      this.numOfRegiments = numOfRegiments;
      this.iCivID = iCivID;
      this.inBattleView = inBattleView;
      Renderer.glyphLayout
         .setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), "" + ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).getAttack(iCivID));
      this.iAttackWidth = (int)Renderer.glyphLayout.width;
      Renderer.glyphLayout
         .setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), "" + ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).getDefense(iCivID));
      this.iDefenseWidth = (int)Renderer.glyphLayout.width;
      this.unitImgWidth = (int)(
         ArmyManager.armyImages.get(ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).ImageID).getWidth()
            * (
               (float)ImageManager.getImage(Images.unitsFrameBattle).getHeight()
                  / ArmyManager.armyImages.get(ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).ImageID).getHeight()
            )
      );
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
            ImageManager.getImage(Images.unitsFrameBattle).getHeight()
         );
      }

      try {
         Renderer.clipViewPeek();
         Renderer.clipView_Start(
            oSB,
            this.getPosX() + iTranslateX,
            CFG.GAME_HEIGHT - (this.getPosY() + iTranslateY),
            ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
            -ImageManager.getImage(Images.unitsFrameBattle).getHeight()
         );
         ArmyManager.armyImages
            .get(ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).ImageID)
            .draw(
               oSB,
               this.getPosX() - this.unitImgWidth / 2 + ImageManager.getImage(Images.unitsFrameBattle).getWidth() / 2 + iTranslateX,
               this.getPosY() + iTranslateY,
               this.unitImgWidth,
               ImageManager.getImage(Images.unitsFrameBattle).getHeight()
            );
         Renderer.clipView_End(oSB);
         Renderer.clipViewPeek_Add(oSB);
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      ImageManager.getImage(Images.unitsFrameBattle).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      if (isActive) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
            ImageManager.getImage(Images.unitsFrameBattle).getHeight(),
            Colors.COLOR_BOX_ACTIVE
         );
      } else if (this.getIsHovered()) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.unitsFrameBattle).getWidth(),
            ImageManager.getImage(Images.unitsFrameBattle).getHeight(),
            Colors.COLOR_BOX_HOVER
         );
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.unitsFrameBattle).getHeight() + iTranslateY,
            this.getWidth(),
            getStatsHeight()
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB, this.getPosX() + iTranslateX, this.getPosY() + ImageManager.getImage(Images.unitsFrameBattle).getHeight() + iTranslateY, 1, getStatsHeight()
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - 1 + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.unitsFrameBattle).getHeight() + iTranslateY,
            1,
            getStatsHeight()
         );
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + ImageManager.getImage(Images.unitsFrameBattle).getHeight() + CFG.PADDING + iTranslateY,
         this.getColor(isActive)
      );
   }

   public static int getStatsHeight() {
      return CFG.TEXT_HEIGHT_SMALL + CFG.PADDING * 2;
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.unitsFrameBattle).getHeight() + getStatsHeight();
   }

   public static int getButtonWidth() {
      return ImageManager.getImage(Images.unitsFrameBattle).getWidth();
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(
         new MenuElement_HoverElement_Type_TextTitle_BG_Center(ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD)
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Attack") + ": ",
            "" + ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).getAttack(this.iCivID),
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
            "" + ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).getDefense(this.iCivID),
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
            "" + ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).MovementSpeed,
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
            "" + ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).AttackRange,
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
            "" + CFG.getPrecision2(ArmyManager.lArmy.get(this.iUnitTypeID).get(this.iArmyID).SiegeProgress, 100),
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
                  ArmyManager.lUnitsTypes.get(this.iUnitTypeID).Line != 0 && ArmyManager.lUnitsTypes.get(this.iUnitTypeID).Line != 1
                     ? (ArmyManager.lUnitsTypes.get(this.iUnitTypeID).Line == 2 ? Game.lang.get("Support") : Game.lang.get("ThirdLine"))
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
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Manpower") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(new MenuElement_HoverElement_Type_Text("" + this.getText(), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "/" + Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE * this.numOfRegiments, CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT2
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (this.inBattleView) {
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
      } else {
         this.menuElementHover = new MenuElement_Hover(nElements);
      }
   }
}
