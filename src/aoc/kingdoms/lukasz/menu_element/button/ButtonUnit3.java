package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menusInGame.RecruitArmy.InGame_RecruitArmy;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonUnit3 extends MenuElement {
   public int unitTypeID;
   public int armyID;
   public String sText;
   public int iTextWidth = 0;
   public int iTextHeight = 0;
   public String sRecruitmentTime;
   public int iRecruitmentTimeWidth;
   public int iRecruitmentTimeHeight;
   public String sSpeed;
   public int iSpeedWidth;
   public int iSpeedHeight;
   public String sAttack;
   public int iAttackWidth;
   public String sDefense;
   public int iDefenseWidth;
   public String sCost;
   public int iCostWidth;
   public int iCostHeight;
   public static float iconScale = 1.0F;
   public static int iconAttackDefenseMaxWidth = 0;
   public static int attackIconWidth;
   public static int attackIconHeight;
   public static int defenseIconWidth;
   public static int defenseIconHeight;
   public static int timeIconWidth;
   public static int timeIconHeight;
   public static int goldIconWidth;
   public static int goldIconHeight;
   public static int speedIconWidth;
   public static int speedIconHeight;
   public int maxIconWidth = 0;
   public Color colorBG;
   public Color colorBGOver;
   public static final Color COLOR_STATS = new Color(0.039215688F, 0.05882353F, 0.09803922F, 0.6F);

   public ButtonUnit3(int unitTypeID, int armyID, int iPosX, int iPosY, int nWidth, boolean isClickable, boolean isResearched) {
      this.typeOfElement = MenuElement_Type.BUTTON;
      this.unitTypeID = unitTypeID;
      this.armyID = armyID;
      this.fontID = CFG.FONT_BOLD;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(nWidth);
      this.setHeight(ImageManager.getImage(Images.unitsFrame).getHeight());
      this.setText(ArmyManager.lArmy.get(unitTypeID).get(armyID).Name);
      this.sAttack = "" + ArmyManager.lArmy.get(unitTypeID).get(armyID).getAttack(Game.player.iCivID);
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR), "999");
      this.iAttackWidth = (int)Renderer.glyphLayout.width;
      this.sDefense = "" + ArmyManager.lArmy.get(unitTypeID).get(armyID).getDefense(Game.player.iCivID);
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR), "999");
      this.iDefenseWidth = (int)Renderer.glyphLayout.width;
      this.sRecruitmentTime = "" + ArmyManager.getRecruitmentTime(Game.player.iCivID, -1, unitTypeID, armyID);
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sRecruitmentTime);
      this.iRecruitmentTimeWidth = (int)Renderer.glyphLayout.width;
      this.iRecruitmentTimeHeight = (int)Renderer.glyphLayout.height;
      this.sSpeed = "" + ArmyManager.lArmy.get(unitTypeID).get(armyID).MovementSpeed;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR), this.sSpeed);
      this.iSpeedWidth = (int)Renderer.glyphLayout.width;
      this.iSpeedHeight = (int)Renderer.glyphLayout.height;
      this.sCost = "" + ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, unitTypeID, armyID);
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sCost);
      this.iCostWidth = (int)Renderer.glyphLayout.width;
      this.iCostHeight = (int)Renderer.glyphLayout.height;
      iconScale = this.getImageScale(Images.attack);
      attackIconWidth = (int)(ImageManager.getImage(Images.attack).getWidth() * iconScale);
      attackIconHeight = (int)(ImageManager.getImage(Images.attack).getHeight() * iconScale);
      defenseIconWidth = (int)(ImageManager.getImage(Images.defense).getWidth() * iconScale);
      defenseIconHeight = (int)(ImageManager.getImage(Images.defense).getHeight() * iconScale);
      timeIconWidth = (int)(ImageManager.getImage(Images.time).getWidth() * iconScale);
      timeIconHeight = (int)(ImageManager.getImage(Images.time).getHeight() * iconScale);
      goldIconWidth = (int)(ImageManager.getImage(Images.gold).getWidth() * iconScale);
      goldIconHeight = (int)(ImageManager.getImage(Images.gold).getHeight() * iconScale);
      speedIconWidth = (int)(ImageManager.getImage(Images.movementSpeed).getWidth() * iconScale);
      speedIconHeight = (int)(ImageManager.getImage(Images.movementSpeed).getHeight() * iconScale);
      this.maxIconWidth = Math.max(timeIconWidth, Math.max(goldIconWidth, speedIconWidth));
      iconAttackDefenseMaxWidth = Math.max(attackIconWidth, defenseIconWidth);
      this.setClickable(isClickable);
      this.setVisible(true);
      if (isResearched) {
         this.colorBG = Colors.COLOR_GRADIENT_BG_BLUE;
         this.colorBGOver = Colors.COLOR_GRADIENT_OVER_BLUE;
      } else {
         this.colorBG = Colors.COLOR_GRADIENT_BG;
         this.colorBGOver = Colors.COLOR_GRADIENT_OVER;
      }

      nWidth = ImageManager.getImage(Images.unitsFrame).getWidth() - CFG.PADDING * 2;
      int tWMax = 0;

      while (this.iTextWidth > nWidth && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
   }

   @Override
   public final void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      this.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
      this.drawText(oSB, iTranslateX, iTranslateY, isActive);
   }

   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered()
         || isActive
         || Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_RECRUIT_ARMY
            && Game.armyRecruit.unitID == this.unitTypeID
            && Game.armyRecruit.armyID == this.armyID) {
         Renderer.drawBoxCornerEmpty(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      int iY = this.getPosY() + iTranslateY;
      int iH = this.getHeight();
      oSB.setColor(new Color(this.colorBG.r, this.colorBG.g, this.colorBG.b, 0.4F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
      oSB.setColor(new Color(this.colorBG.r, this.colorBG.g, this.colorBG.b, 0.3F));
      ImageManager.getImage(Images.gradientHorizontal).draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, iY, this.getInnerWidth(), iH);
      oSB.setColor(new Color(COLOR_STATS.r, COLOR_STATS.g, COLOR_STATS.b, 0.4F));
      ImageManager.getImage(Images.gradientHorizontal).draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, iY, this.getInnerWidth(), iH);
      ImageManager.getImage(Images.gradientHorizontal).draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, iY, this.getInnerWidth(), 1);
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getInnerWidth(), 1);
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, iY + this.getTitleHeight() - 1, this.getInnerWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, iY, this.getInnerWidth(), CFG.PADDING);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY,
            this.getInnerWidth(),
            CFG.PADDING,
            false,
            true
         );
      oSB.setColor(new Color(this.colorBGOver.r, this.colorBGOver.g, this.colorBGOver.b, 0.75F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
      oSB.setColor(new Color(this.colorBGOver.r, this.colorBGOver.g, this.colorBGOver.b, 0.75F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
      oSB.setColor(new Color(this.colorBGOver.r, this.colorBGOver.g, this.colorBGOver.b, 1.0F));
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
      ImageManager.getImage(Images.gradientHorizontal).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(this.colorBG.r, this.colorBG.g, this.colorBG.b, 0.2F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() - CFG.PADDING + iTranslateY, this.getWidth(), CFG.PADDING, false, true);
      oSB.setColor(new Color(this.colorBG.r, this.colorBG.g, this.colorBG.b, 0.7F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getTitleHeight() - CFG.PADDING + iTranslateY, this.getWidth(), CFG.PADDING);
      if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_RECRUIT_ARMY
         && Game.armyRecruit.unitID == this.unitTypeID
         && Game.armyRecruit.armyID == this.armyID) {
         oSB.setColor(new Color(Colors.HOVER_POSITIVE.r, Colors.HOVER_POSITIVE.g, Colors.HOVER_POSITIVE.b, 0.15F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight(), false, true);
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getTitleHeight());
      }

      oSB.setColor(Color.WHITE);
      ArmyManager.armyImages
         .get(ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).ImageID)
         .draw(oSB, this.getPosX() + iTranslateX, iY, ImageManager.getImage(Images.unitsFrame).getWidth(), ImageManager.getImage(Images.unitsFrame).getHeight());
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.unitsFrame).draw(oSB, this.getPosX() + iTranslateX, iY);
      if (isActive) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            iY,
            ImageManager.getImage(Images.unitsFrame).getWidth(),
            ImageManager.getImage(Images.unitsFrame).getHeight(),
            Colors.COLOR_BOX_ACTIVE
         );
      } else if (this.getIsHovered()) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            iY,
            ImageManager.getImage(Images.unitsFrame).getWidth(),
            ImageManager.getImage(Images.unitsFrame).getHeight(),
            Colors.COLOR_BOX_HOVER
         );
      }
   }

   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getText(),
         this.getPosX() + ImageManager.getImage(Images.unitsFrame).getWidth() / 2 - this.iTextWidth / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - this.iTextHeight - CFG.PADDING * 2 + iTranslateY,
         this.getColor(isActive)
      );
      int centerY = this.getTitleHeight() + (this.getHeight() - this.getTitleHeight()) / 2;
      int timeY = this.getTitleHeight() / 2;
      ImageManager.getImage(Images.gold)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING * 2 - goldIconWidth + iTranslateX,
            this.getPosY() + timeY - goldIconHeight / 2 + iTranslateY,
            goldIconWidth,
            goldIconHeight
         );
      ImageManager.getImage(Images.time)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING * 5 - timeIconWidth - goldIconWidth - this.iCostWidth + iTranslateX,
            this.getPosY() + timeY - timeIconHeight / 2 + iTranslateY,
            timeIconWidth,
            timeIconHeight
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sCost,
         this.getPosX() + this.getWidth() - CFG.PADDING * 3 - goldIconWidth - this.iCostWidth + iTranslateX,
         this.getPosY() + timeY - this.iCostHeight / 2 + iTranslateY,
         Colors.HOVER_RIGHT2
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sRecruitmentTime,
         this.getPosX() + this.getWidth() - CFG.PADDING * 6 - timeIconWidth - goldIconWidth - this.iCostWidth - this.iRecruitmentTimeWidth + iTranslateX,
         this.getPosY() + timeY - this.iRecruitmentTimeHeight / 2 + iTranslateY,
         Colors.HOVER_RIGHT2
      );
      ImageManager.getImage(Images.movementSpeed)
         .draw(
            oSB,
            this.getPosX()
               + this.getInnerPosX()
               + CFG.PADDING * 8
               + iconAttackDefenseMaxWidth
               + this.iAttackWidth
               + (iconAttackDefenseMaxWidth - defenseIconWidth) / 2
               + iconAttackDefenseMaxWidth
               + this.iDefenseWidth
               + (iconAttackDefenseMaxWidth - speedIconWidth) / 2
               + iTranslateX,
            this.getPosY() + centerY - speedIconHeight / 2 + iTranslateY,
            speedIconWidth,
            speedIconHeight
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR,
         this.sSpeed,
         this.getPosX()
            + this.getInnerPosX()
            + CFG.PADDING * 10
            + iconAttackDefenseMaxWidth
            + this.iAttackWidth
            + (iconAttackDefenseMaxWidth - defenseIconWidth) / 2
            + iconAttackDefenseMaxWidth
            + this.iDefenseWidth
            + (iconAttackDefenseMaxWidth - speedIconWidth) / 2
            + speedIconWidth
            + iTranslateX,
         this.getPosY() + centerY - CFG.TEXT_HEIGHT / 2 + iTranslateY,
         Colors.HOVER_LEFT2
      );
      ImageManager.getImage(Images.attack)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + CFG.PADDING * 2 + (iconAttackDefenseMaxWidth - attackIconWidth) / 2 + iTranslateX,
            this.getPosY() + centerY - attackIconHeight / 2 + iTranslateY,
            attackIconWidth,
            attackIconHeight
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR,
         this.sAttack,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 4 + iconAttackDefenseMaxWidth + iTranslateX,
         this.getPosY() + centerY - CFG.TEXT_HEIGHT / 2 + iTranslateY,
         Colors.HOVER_LEFT2
      );
      ImageManager.getImage(Images.defense)
         .draw(
            oSB,
            this.getPosX()
               + this.getInnerPosX()
               + CFG.PADDING * 5
               + iconAttackDefenseMaxWidth
               + this.iAttackWidth
               + (iconAttackDefenseMaxWidth - defenseIconWidth) / 2
               + iTranslateX,
            this.getPosY() + centerY - attackIconHeight / 2 + iTranslateY,
            defenseIconWidth,
            defenseIconHeight
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR,
         this.sDefense,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 7 + iconAttackDefenseMaxWidth * 2 + this.iAttackWidth + iTranslateX,
         this.getPosY() + centerY - CFG.TEXT_HEIGHT / 2 + iTranslateY,
         Colors.HOVER_LEFT2
      );
   }

   public int getTitleHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 5;
   }

   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStatsHover(isActive, this.getIsHovered());
   }

   @Override
   public void setText(String sText) {
      this.sText = sText;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.getText());
      this.iTextWidth = (int)Renderer.glyphLayout.width;
      this.iTextHeight = (int)Renderer.glyphLayout.height;
   }

   @Override
   public int getTextHeight() {
      return this.iTextHeight;
   }

   @Override
   public String getText() {
      return this.sText;
   }

   public int getInnerPosX() {
      return ImageManager.getImage(Images.unitsFrame).getWidth();
   }

   public int getInnerWidth() {
      return this.getWidth() - ImageManager.getImage(Images.unitsFrame).getWidth();
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(
         new MenuElement_HoverElement_Type_TextTitle_BG_Center(ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD)
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Attack") + ": ",
            "" + ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).getAttack(Game.player.iCivID),
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
            "" + ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).getDefense(Game.player.iCivID),
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
            "" + ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).MovementSpeed,
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
            "" + ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).AttackRange,
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
            "" + CFG.getPrecision2(ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).SiegeProgress, 100),
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
                  ArmyManager.lUnitsTypes.get(this.unitTypeID).Line != 0 && ArmyManager.lUnitsTypes.get(this.unitTypeID).Line != 1
                     ? (ArmyManager.lUnitsTypes.get(this.unitTypeID).Line == 2 ? Game.lang.get("Support") : Game.lang.get("ThirdLine"))
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
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE, CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RecruitmentTime") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game.lang.get("XDays", ArmyManager.getRecruitmentTime(Game.player.iCivID, -1, this.unitTypeID, this.armyID)),
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_RIGHT
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER_TIME, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaintenanceCost") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game.lang.get("XPerMonth", CFG.getPrecision2(ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).MaintenanceCost, 100)),
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_RIGHT
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.armyMaintenance, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.unitTypeID, this.armyID), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).RequiredTechID >= 0) {
         try {
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RequiredTechnology") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "" + TechnologyTree.lTechnology.get(ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).RequiredTechID).Name,
                  CFG.FONT_BOLD_SMALL,
                  Colors.HOVER_RIGHT
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }

      this.menuElementHover = new MenuElement_Hover(nElements) {
         @Override
         public int getMinPosX() {
            return InGame_RecruitArmy.HOVER_POSX;
         }
      };
   }

   @Override
   public int getValue1() {
      return this.unitTypeID;
   }

   @Override
   public int getValue2() {
      return this.armyID;
   }
}
