package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.GeneralManager;
import aoc.kingdoms.lukasz.map.army.ArmyGeneral;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menusInGame.InGame_Armies;
import aoc.kingdoms.lukasz.menusInGame.InGame_Generals;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceArmy;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonArmyGeneral_Assign extends Button {
   public int iCivID;
   public int imageID;
   public String sAttack;
   public int iAttackWidth;
   public String sDefense;
   public int iDefenseWidth;
   public static float iconScale = 1.0F;
   public static int attackIconWidth;
   public static int attackIconHeight;
   public static int defenseIconWidth;
   public static int defenseIconHeight;
   public int iDay;
   public int iMonth;
   public int iYear;
   public int statH = 0;
   public boolean recruitMode;
   public Image generalImage;
   public String generalKey;
   public float combatExperience;

   public ButtonArmyGeneral_Assign(
      int iPosX,
      int iPosY,
      int iWidth,
      String sName,
      int iCivID,
      int iAttack,
      int iDefense,
      int imageID,
      int iDay,
      int iMonth,
      int iYear,
      boolean recruitMode,
      String generalKey,
      String sIMG,
      int combatExperience
   ) {
      this.init(
         sName,
         CFG.FONT_REGULAR_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         Math.max(ImageManager.getImage(Images.generalFrame).getWidth(), iWidth),
         getButtonHeight(),
         true,
         true,
         false,
         false
      );
      this.iCivID = iCivID;
      this.iDay = iDay;
      this.iMonth = iMonth;
      this.iYear = iYear;
      this.imageID = imageID;
      this.sAttack = "" + iAttack;
      this.sDefense = "" + iDefense;
      this.combatExperience = Math.min(1.0F, Math.max(0.0F, (float)combatExperience / GameValues.generals.COMBAT_EXPERIENCE_MAX));
      this.recruitMode = recruitMode;
      this.generalKey = generalKey;
      this.statH = (ImageManager.getImage(Images.generalFrame).getHeight() - CFG.PADDING * 3) / 2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR), this.sAttack);
      this.iAttackWidth = (int)Renderer.glyphLayout.width;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR), this.sDefense);
      this.iDefenseWidth = (int)Renderer.glyphLayout.width;
      iconScale = this.getImageScale(Images.attack);
      attackIconWidth = (int)(ImageManager.getImage(Images.attack).getWidth() * iconScale);
      attackIconHeight = (int)(ImageManager.getImage(Images.attack).getHeight() * iconScale);
      defenseIconWidth = (int)(ImageManager.getImage(Images.defense).getWidth() * iconScale);
      defenseIconHeight = (int)(ImageManager.getImage(Images.defense).getHeight() * iconScale);
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }

      try {
         if (sIMG != null) {
            if (FileManager.loadFile("game/characters/" + CFG.getRescouresPath_Short() + sIMG + ".png").exists()) {
               this.generalImage = new Image(
                  ImageManager.loadTexture_RGB888("game/characters/" + CFG.getRescouresPath_Short() + sIMG + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               );
            } else if (FileManager.loadFile("game/characters/" + CFG.getRescouresPath_Short_H() + sIMG + ".png").exists()) {
               this.generalImage = new Image(
                  ImageManager.loadTexture_RGB888("game/characters/" + CFG.getRescouresPath_Short_H() + sIMG + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               );
            }
         }

         if (this.generalImage == null) {
            if (FileManager.loadFile(
                  "game/generals/"
                     + GeneralManager.getGeneralsImgPath(iCivID)
                     + CFG.getRescouresPath_Short()
                     + Game.getCiv(iCivID).iGroupID
                     + "/"
                     + imageID
                     + ".png"
               )
               .exists()) {
               this.generalImage = new Image(
                  ImageManager.loadTexture_RGB888(
                     "game/generals/"
                        + GeneralManager.getGeneralsImgPath(iCivID)
                        + CFG.getRescouresPath_Short()
                        + Game.getCiv(iCivID).iGroupID
                        + "/"
                        + imageID
                        + ".png"
                  ),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               );
            } else {
               this.generalImage = new Image(
                  ImageManager.loadTexture_RGB888(
                     "game/generals/" + GeneralManager.getGeneralsImgPath(iCivID) + CFG.getRescouresPath_Short() + Game.getCiv(iCivID).iGroupID + "/0.png"
                  ),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               );
            }
         }
      } catch (Exception var18) {
         CFG.exceptionStack(var18);
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.65F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), ImageManager.getImage(Images.generalFrame).getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.5F));
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getInnerWidth(),
            ImageManager.getImage(Images.generalFrame).getHeight()
         );
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getInnerWidth(),
            ImageManager.getImage(Images.generalFrame).getHeight()
         );
      oSB.setColor(new Color(Colors.BUTTON_BUILDING_COLORS_STATS.r, Colors.BUTTON_BUILDING_COLORS_STATS.g, Colors.BUTTON_BUILDING_COLORS_STATS.b, 0.65F));
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.getPosX() + this.getInnerPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getInnerWidth(), 1);
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrame).getHeight() - 1 + iTranslateY,
            this.getInnerWidth(),
            1
         );
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCornerEmpty(
            oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), ImageManager.getImage(Images.generalFrame).getHeight()
         );
      }

      try {
         this.generalImage
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.generalFrame).getWidth(),
               ImageManager.getImage(Images.generalFrame).getHeight()
            );
      } catch (Exception var6) {
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.pix
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrame).getHeight() - CFG.PADDING + iTranslateY,
            ImageManager.getImage(Images.generalFrame).getWidth(),
            CFG.PADDING
         );
      if (!this.getIsHovered() && !isActive) {
         oSB.setColor(Colors.COLOR_BOX_GOLD);
      } else {
         oSB.setColor(ProvinceDraw.progressBar);
      }

      Images.pix
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrame).getHeight() - CFG.PADDING + iTranslateY,
            (int)(ImageManager.getImage(Images.generalFrame).getWidth() * this.combatExperience),
            CFG.PADDING
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.75F));
      Images.pix
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrame).getHeight() - CFG.PADDING + iTranslateY,
            ImageManager.getImage(Images.generalFrame).getWidth(),
            1
         );
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.generalFrame).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
         Renderer.drawBox(
            oSB,
            Images.statsRectBGBorder,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.generalFrame).getWidth(),
            ImageManager.getImage(Images.generalFrame).getHeight(),
            1.0F
         );
         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrame).getHeight() + iTranslateY,
            this.getWidth(),
            getStatsHeight()
         );
      Images.gradientXY
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrame).getHeight() + iTranslateY,
            this.getWidth(),
            getStatsHeight(),
            false,
            true
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + ImageManager.getImage(Images.generalFrame).getHeight() + iTranslateY, 1, getStatsHeight());
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - 1 + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.generalFrame).getHeight() + iTranslateY,
            1,
            getStatsHeight()
         );
      oSB.setColor(Color.WHITE);
      this.drawStats(oSB, iTranslateX, iTranslateY, isActive);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + ImageManager.getImage(Images.generalFrame).getHeight() + CFG.PADDING * 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   public final void drawStats(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + CFG.PADDING + iTranslateY,
         this.getInnerWidth() - CFG.PADDING * 2,
         this.statH,
         1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + CFG.PADDING + this.statH - (CFG.TEXT_HEIGHT + CFG.PADDING * 2) + iTranslateY,
         this.getInnerWidth() - CFG.PADDING * 2,
         CFG.TEXT_HEIGHT + CFG.PADDING * 2,
         1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + CFG.PADDING * 2 + this.statH + iTranslateY,
         this.getInnerWidth() - CFG.PADDING * 2,
         this.statH,
         1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + CFG.PADDING * 2 + this.statH * 2 - (CFG.TEXT_HEIGHT + CFG.PADDING * 2) + iTranslateY,
         this.getInnerWidth() - CFG.PADDING * 2,
         CFG.TEXT_HEIGHT + CFG.PADDING * 2,
         1.0F
      );
      ImageManager.getImage(Images.attack)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + this.getInnerWidth() / 2 - attackIconWidth / 2 + iTranslateX,
            this.getPosY() + CFG.PADDING + (this.statH - CFG.TEXT_HEIGHT - CFG.PADDING * 2) / 2 - attackIconHeight / 2 + iTranslateY,
            attackIconWidth,
            attackIconHeight
         );
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_REGULAR,
         this.sAttack,
         this.getPosX() + this.getInnerPosX() + this.getInnerWidth() / 2 - this.iAttackWidth / 2 + iTranslateX,
         this.getPosY() + CFG.PADDING + this.statH - CFG.TEXT_HEIGHT - CFG.PADDING + iTranslateY,
         Colors.BUTTON_TEXT_HOVERED
      );
      ImageManager.getImage(Images.defense)
         .draw(
            oSB,
            this.getPosX() + this.getInnerPosX() + this.getInnerWidth() / 2 - defenseIconWidth / 2 + iTranslateX,
            this.getPosY()
               + CFG.PADDING
               + (this.statH - CFG.TEXT_HEIGHT - CFG.PADDING * 2) / 2
               - defenseIconHeight / 2
               + CFG.PADDING
               + this.statH
               + iTranslateY,
            defenseIconWidth,
            defenseIconHeight
         );
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_REGULAR,
         this.sDefense,
         this.getPosX() + this.getInnerPosX() + this.getInnerWidth() / 2 - this.iDefenseWidth / 2 + iTranslateX,
         this.getPosY() + CFG.PADDING + this.statH - CFG.TEXT_HEIGHT - CFG.PADDING + CFG.PADDING + this.statH + iTranslateY,
         Colors.BUTTON_TEXT_HOVERED
      );
   }

   public static int getStatsHeight() {
      return CFG.TEXT_HEIGHT_SMALL + CFG.PADDING * 4;
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.generalFrame).getHeight() + getStatsHeight();
   }

   private final float getImageScale(int iImageID) {
      return (float)(this.statH - CFG.TEXT_HEIGHT - CFG.PADDING * 4) / ImageManager.getImage(iImageID).getHeight();
   }

   public int getInnerPosX() {
      return ImageManager.getImage(Images.generalFrame).getWidth();
   }

   public int getInnerWidth() {
      return this.getWidth() - ImageManager.getImage(Images.generalFrame).getWidth();
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title(this.iCivID, this.getText()));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Attack") + ": ", this.sAttack, Images.attack, CFG.FONT_REGULAR_SMALL, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Defense") + ": ", this.sDefense, Images.defense, CFG.FONT_REGULAR_SMALL, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("CombatExperience") + ": ",
            CFG.getNumberWithSpaces("" + (int)(this.combatExperience * GameValues.generals.COMBAT_EXPERIENCE_MAX))
               + " / "
               + GameValues.generals.COMBAT_EXPERIENCE_MAX,
            Images.general,
            CFG.FONT_REGULAR_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("CombatExperienceDesc"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
      nElements.add(new MenuElement_HoverElement(nData, false));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Empty());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DateOfBirth") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + this.iDay + " " + Game_Calendar.getMonthName(this.iMonth) + " " + this.iYear, CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game.lang.get("XYearsOld", Math.min(99, Game_Calendar.currentYear - this.iYear)), CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.time, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (!this.recruitMode && this.iCivID == Game.player.iCivID && Game.menuManager.getVisibleInGame_ProvinceArmy()) {
         nData.add(new MenuElement_HoverElement_Type_Empty());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("AssignGeneralToThisUnit"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements, true);
      } else {
         this.menuElementHover = new MenuElement_Hover(nElements);
      }
   }

   @Override
   public void dispose() {
      if (this.generalImage != null) {
         this.generalImage.dispose();
         this.generalImage = null;
      }
   }

   @Override
   public void actionElement() {
      assignGeneral(this.iCivID, this.generalKey, true);
   }

   public static final void assignGeneral(int iCivID, String generalKey, boolean swapGenerals) {
      if (Game.menuManager.getVisibleInGame_ProvinceArmy() && InGame_ProvinceArmy.iCivFlagID == Game.player.iCivID) {
         InGame_Generals.assignProvinceID = InGame_ProvinceArmy.iProvinceID;
         InGame_Generals.assignArmyKey = InGame_ProvinceArmy.sActiveKEY;
      }

      if (generalKey != null && InGame_Generals.assignProvinceID >= 0) {
         int nArmyID = Game.getProvince(InGame_Generals.assignProvinceID).getArmyKeyID(InGame_Generals.assignArmyKey);
         if (nArmyID < 0) {
            for (int i = 0; i < Game.getProvincesSize(); i++) {
               int outID = Game.getProvince(i).getArmyKeyID(InGame_Generals.assignArmyKey, iCivID);
               if (outID >= 0) {
                  InGame_Generals.assignProvinceID = i;
                  nArmyID = outID;
                  break;
               }
            }
         }

         if (nArmyID >= 0 && Game.getProvince(InGame_Generals.assignProvinceID).getArmy(nArmyID).civID == Game.player.iCivID) {
            if (!swapGenerals && Game.getProvince(InGame_Generals.assignProvinceID).getArmy(nArmyID).armyGeneral != null) {
               return;
            }

            if (Game.getProvince(InGame_Generals.assignProvinceID).getArmy(nArmyID).armyGeneral != null) {
               if (Game.getProvince(InGame_Generals.assignProvinceID).getArmy(nArmyID).armyGeneral.key.equals(generalKey)) {
                  Game.getCiv(Game.player.iCivID).addGeneral(Game.getProvince(InGame_Generals.assignProvinceID).getArmy(nArmyID).armyGeneral);
                  Game.getProvince(InGame_Generals.assignProvinceID).getArmy(nArmyID).setArmyGeneral(null);
                  rebuildMenus();
                  return;
               }

               for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).iArmyPositionSize; ix++) {
                  for (int j = 0; j < Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmySize(); j++) {
                     if (Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(j).civID == Game.player.iCivID
                        && Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(j).armyGeneral != null
                        && Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(j).armyGeneral.key.equals(generalKey)) {
                        ArmyGeneral tempArmyGeneral = Game.getProvince(InGame_Generals.assignProvinceID).getArmy(nArmyID).armyGeneral;
                        Game.getProvince(InGame_Generals.assignProvinceID)
                           .getArmy(nArmyID)
                           .setArmyGeneral(Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(j).armyGeneral);
                        Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(j).setArmyGeneral(tempArmyGeneral);
                        rebuildMenus();
                        return;
                     }
                  }
               }
            }

            if (!Game.getCiv(Game.player.iCivID).assignGeneralToArmy(InGame_Generals.assignProvinceID, nArmyID, generalKey)) {
               for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).iArmyPositionSize; ix++) {
                  for (int jx = 0; jx < Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmySize(); jx++) {
                     if (Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).civID == Game.player.iCivID
                        && Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral != null
                        && Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral.key.equals(generalKey)) {
                        ArmyGeneral tempArmyGeneral = Game.getProvince(InGame_Generals.assignProvinceID).getArmy(nArmyID).armyGeneral;
                        Game.getProvince(InGame_Generals.assignProvinceID)
                           .getArmy(nArmyID)
                           .setArmyGeneral(Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).armyGeneral);
                        Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(ix)).getArmy(jx).setArmyGeneral(tempArmyGeneral);
                        rebuildMenus();
                        return;
                     }
                  }
               }
            }

            rebuildMenus();
         }
      }
   }

   public static final void rebuildMenus() {
      Game.menuManager.setVisibleInGame_Generals(false);
      if (Game.menuManager.getVisibleInGame_ProvinceArmy()) {
         Game.menuManager.rebuildInGame_ProvinceArmy(true, true);
      }

      if (Game.menuManager.getVisibleInGame_Armies()) {
         Game.menuManager.rebuildInGame_Armies(false, true);
         Game.menuManager.setVisibleInGame_Armies(true);
         InGame_Armies.lTime = 0L;
      }
   }
}
