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

public class ButtonUnit3_3 extends MenuElement {
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
   public Color colorMain;
   public Color colorOver;
   public int statW = 0;
   public static final Color COLOR_STATS = new Color(0.039215688F, 0.05882353F, 0.09803922F, 0.6F);

   public ButtonUnit3_3(int unitTypeID, int armyID, int iPosX, int iPosY, int nWidth, boolean isClickable, boolean isResearched) {
      this.typeOfElement = MenuElement_Type.BUTTON;
      this.unitTypeID = unitTypeID;
      this.armyID = armyID;
      this.fontID = CFG.FONT_BOLD;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(nWidth);
      this.setHeight(ImageManager.getImage(Images.unitsFrame).getHeight() + getPaddingIMG() * 2);
      this.setText(ArmyManager.lArmy.get(unitTypeID).get(armyID).Name);
      this.sAttack = "" + ArmyManager.lArmy.get(unitTypeID).get(armyID).getAttack(Game.player.iCivID);
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR), this.sAttack);
      this.iAttackWidth = (int)Renderer.glyphLayout.width;
      this.sDefense = "" + ArmyManager.lArmy.get(unitTypeID).get(armyID).getDefense(Game.player.iCivID);
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR), this.sDefense);
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
      timeIconWidth = (int)(ImageManager.getImage(Images.time).getWidth() * iconScale);
      timeIconHeight = (int)(ImageManager.getImage(Images.time).getHeight() * iconScale);
      goldIconWidth = (int)(ImageManager.getImage(Images.gold).getWidth() * iconScale);
      goldIconHeight = (int)(ImageManager.getImage(Images.gold).getHeight() * iconScale);
      float iconScale2 = Math.min(0.9F, this.getImageScale2(Images.attack));
      speedIconWidth = (int)(ImageManager.getImage(Images.movementSpeed).getWidth() * iconScale2);
      speedIconHeight = (int)(ImageManager.getImage(Images.movementSpeed).getHeight() * iconScale2);
      attackIconWidth = (int)(ImageManager.getImage(Images.attack).getWidth() * iconScale2);
      attackIconHeight = (int)(ImageManager.getImage(Images.attack).getHeight() * iconScale2);
      defenseIconWidth = (int)(ImageManager.getImage(Images.defense).getWidth() * iconScale2);
      defenseIconHeight = (int)(ImageManager.getImage(Images.defense).getHeight() * iconScale2);
      this.maxIconWidth = Math.max(timeIconWidth, Math.max(goldIconWidth, speedIconWidth));
      iconAttackDefenseMaxWidth = Math.max(attackIconWidth, defenseIconWidth);
      this.statW = (this.getInnerWidth() - CFG.PADDING * 4) / 3;
      this.setClickable(isClickable);
      this.setVisible(true);
      if (isResearched) {
         this.colorBG = Colors.COLOR_GRADIENT_BG_BLUE;
         this.colorMain = Colors.COLOR_NOTIFICATION_BG;
         this.colorOver = Colors.COLOR_NOTIFICATION_OVER;
      } else {
         this.colorBG = Colors.COLOR_GRADIENT_BG;
         this.colorMain = Colors.COLOR_NOTIFICATION_BG_RED;
         this.colorOver = Colors.COLOR_NOTIFICATION_OVER_RED;
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

   public int getButtonWidth() {
      return ImageManager.getImage(Images.unitsFrame).getWidth();
   }

   public static int getPaddingIMG() {
      return CFG.PADDING;
   }

   public int getInnerPosX() {
      return this.getButtonWidth();
   }

   public int getInnerWidth() {
      return this.getWidth() - this.getButtonWidth();
   }

   public int getTitleHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 5;
   }

   public int getTopH() {
      return ImageManager.getImage(Images.unitsFrame).getHeight() + getPaddingIMG() * 2;
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
      oSB.setColor(new Color(this.colorMain.r, this.colorMain.g, this.colorMain.b, !this.getIsHovered() && !isActive ? 0.25F : 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(this.colorBG.r, this.colorBG.g, this.colorBG.b, !this.getIsHovered() && !isActive ? 0.75F : 1.0F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getButtonWidth(), this.getTopH(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(this.colorOver.r, this.colorOver.g, this.colorOver.b, 0.3F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(this.colorOver.r, this.colorOver.g, this.colorOver.b, 0.35F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(this.colorOver.r, this.colorOver.g, this.colorOver.b, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(this.colorMain.r, this.colorMain.g, this.colorMain.b, 0.25F));
      Renderer.drawBox(
         oSB, Images.statsRectBGBorder, this.getPosX() - 1 + iTranslateX, this.getPosY() - 1 + iTranslateY, this.getWidth() + 2, this.getHeight() + 2, 1.0F
      );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(this.colorOver.r, this.colorOver.g, this.colorOver.b, 0.85F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(this.colorOver.r, this.colorOver.g, this.colorOver.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(this.colorBG.r, this.colorBG.g, this.colorBG.b, !this.getIsHovered() && !isActive ? 0.3F : 0.5F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + CFG.PADDING + iTranslateY,
         this.getInnerWidth() - CFG.PADDING * 2,
         this.getTitleHeight() - CFG.PADDING,
         1.0F
      );
      if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_RECRUIT_ARMY
         && Game.armyRecruit.unitID == this.unitTypeID
         && Game.armyRecruit.armyID == this.armyID) {
         oSB.setColor(new Color(Colors.HOVER_POSITIVE.r, Colors.HOVER_POSITIVE.g, Colors.HOVER_POSITIVE.b, !this.getIsHovered() && !isActive ? 0.25F : 0.3F));
         Images.gradientFull
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               this.getInnerWidth() - CFG.PADDING * 2,
               this.getTitleHeight() - CFG.PADDING
            );
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               this.getInnerWidth() - CFG.PADDING * 2,
               this.getTitleHeight() - CFG.PADDING
            );
      }

      oSB.setColor(Color.WHITE);
      ArmyManager.armyImages
         .get(ArmyManager.lArmy.get(this.unitTypeID).get(this.armyID).ImageID)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            iY + getPaddingIMG(),
            ImageManager.getImage(Images.unitsFrame).getWidth(),
            ImageManager.getImage(Images.unitsFrame).getHeight()
         );
      ImageManager.getImage(Images.unitsFrame).draw(oSB, this.getPosX() + iTranslateX, iY + getPaddingIMG());
      if (isActive) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            iY + getPaddingIMG(),
            ImageManager.getImage(Images.unitsFrame).getWidth(),
            ImageManager.getImage(Images.unitsFrame).getHeight(),
            Colors.COLOR_BOX_ACTIVE
         );
      } else if (this.getIsHovered()) {
         Renderer.drawBoxLineFrame(
            oSB,
            this.getPosX() + iTranslateX,
            iY + getPaddingIMG(),
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
         this.getPosY() + this.getHeight() - getPaddingIMG() - this.iTextHeight - CFG.PADDING * 2 + iTranslateY,
         this.getColor(isActive)
      );
      int centerY = this.getTitleHeight() + (this.getHeight() - this.getTitleHeight()) / 2;
      int timeY = CFG.PADDING + (this.getTitleHeight() - CFG.PADDING) / 2;
      ImageManager.getImage(Images.gold)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING * 3 - goldIconWidth + iTranslateX,
            this.getPosY() + timeY - goldIconHeight / 2 + iTranslateY,
            goldIconWidth,
            goldIconHeight
         );
      ImageManager.getImage(Images.time)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING * 6 - timeIconWidth - goldIconWidth - this.iCostWidth + iTranslateX,
            this.getPosY() + timeY - timeIconHeight / 2 + iTranslateY,
            timeIconWidth,
            timeIconHeight
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sCost,
         this.getPosX() + this.getWidth() - CFG.PADDING * 4 - goldIconWidth - this.iCostWidth + iTranslateX,
         this.getPosY() + timeY - this.iCostHeight / 2 + iTranslateY,
         Colors.HOVER_RIGHT2
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sRecruitmentTime,
         this.getPosX() + this.getWidth() - CFG.PADDING * 7 - timeIconWidth - goldIconWidth - this.iCostWidth - this.iRecruitmentTimeWidth + iTranslateX,
         this.getPosY() + timeY - this.iRecruitmentTimeHeight / 2 + iTranslateY,
         Colors.HOVER_RIGHT2
      );
      int tH = this.getHeight() - this.getTitleHeight() - CFG.PADDING * 2;
      int textH = CFG.TEXT_HEIGHT + CFG.PADDING * 3;
      int tHInner = (tH - textH) / 2;
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + this.getTitleHeight() + CFG.PADDING + iTranslateY,
         this.statW,
         tH,
         1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING - textH + iTranslateY,
         this.statW,
         textH,
         1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 2 + this.statW + iTranslateX,
         this.getPosY() + this.getTitleHeight() + CFG.PADDING + iTranslateY,
         this.statW,
         tH,
         1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 2 + this.statW + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING - textH + iTranslateY,
         this.statW,
         textH,
         1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 3 + this.statW * 2 + iTranslateX,
         this.getPosY() + this.getTitleHeight() + CFG.PADDING + iTranslateY,
         this.statW,
         tH,
         1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 3 + this.statW * 2 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING - textH + iTranslateY,
         this.statW,
         textH,
         1.0F
      );
      ImageManager.getImage(Images.attack)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + CFG.PADDING + this.statW / 2 - attackIconWidth / 2 + iTranslateX,
            this.getPosY() + this.getTitleHeight() + CFG.PADDING + tHInner - attackIconHeight / 2 + iTranslateY,
            attackIconWidth,
            attackIconHeight
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR,
         this.sAttack,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + this.statW / 2 - this.iAttackWidth / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING * 2 - CFG.PADDING / 2 - CFG.TEXT_HEIGHT / 2 - this.iSpeedHeight / 2 + iTranslateY,
         Colors.HOVER_LEFT2
      );
      ImageManager.getImage(Images.defense)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + CFG.PADDING * 2 + this.statW + this.statW / 2 - defenseIconWidth / 2 + iTranslateX,
            this.getPosY() + this.getTitleHeight() + CFG.PADDING + tHInner - defenseIconHeight / 2 + iTranslateY,
            defenseIconWidth,
            defenseIconHeight
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR,
         this.sDefense,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 2 + this.statW + this.statW / 2 - this.iDefenseWidth / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING * 2 - CFG.PADDING / 2 - CFG.TEXT_HEIGHT / 2 - this.iSpeedHeight / 2 + iTranslateY,
         Colors.HOVER_LEFT2
      );
      ImageManager.getImage(Images.movementSpeed)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + CFG.PADDING * 3 + this.statW * 2 + this.statW / 2 - speedIconWidth / 2 + iTranslateX,
            this.getPosY() + this.getTitleHeight() + CFG.PADDING + tHInner - speedIconHeight / 2 + iTranslateY,
            speedIconWidth,
            speedIconHeight
         );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR,
         this.sSpeed,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 3 + this.statW * 2 + this.statW / 2 - this.iSpeedWidth / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING * 2 - CFG.PADDING / 2 - CFG.TEXT_HEIGHT / 2 - this.iSpeedHeight / 2 + iTranslateY,
         Colors.HOVER_LEFT2
      );
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

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   public final float getImageScale2(int iImageID) {
      return (float)(this.getHeight() - this.getTitleHeight() - CFG.PADDING * 5 - CFG.TEXT_HEIGHT) / ImageManager.getImage(iImageID).getHeight();
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
