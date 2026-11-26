package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Renderer.SparksAnimation;
import aoc.kingdoms.lukasz.map.battles.Battle;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Flag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battle;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_OutlinerBattle extends Button_Outliner {
   public SparksAnimation sparksAnimation = new SparksAnimation();
   public String battleKey;
   public int lastTurnID = -999645;
   public int civIDLeft;
   public int civIDRight;
   public int leftIconMaxW;

   public Button_OutlinerBattle(String battleKey, int nPosX, int nPosY, int nWidth, int nHeight) {
      super("", nPosX, nPosY, nWidth, nHeight, 0);
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.battleKey = battleKey;
      this.leftIconMaxW = Math.max(ImageManager.getImage(Images.relationsUp).getWidth(), ImageManager.getImage(Images.battle).getWidth());
      this.updateBattle();
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      this.updateBattle();
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
      this.sparksAnimation.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.battle)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING * 2 + (this.leftIconMaxW - ImageManager.getImage(Images.battle).getWidth()) / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.battle).getHeight() / 2 + iTranslateY
         );
      oSB.setShader(Renderer.shaderAlpha);
      if (this.civIDLeft >= 0) {
         Game.getCiv(this.civIDLeft).getFlag().getTexture().bind(1);
      } else {
         ImageManager.getImage(Images.rebelsFlag).getTexture().bind(1);
      }

      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.flagRect2Mask)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING * 4 + this.leftIconMaxW + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flagRect2).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(Images.flagRect2).getWidth(),
            ImageManager.getImage(Images.flagRect2).getHeight()
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.flagRect2)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING * 4 + this.leftIconMaxW + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flagRect2).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(Images.flagRect2).getWidth(),
            ImageManager.getImage(Images.flagRect2).getHeight()
         );
      oSB.setShader(Renderer.shaderAlpha);
      if (this.civIDRight >= 0) {
         Game.getCiv(this.civIDRight).getFlag().getTexture().bind(1);
      } else {
         ImageManager.getImage(Images.rebelsFlag).getTexture().bind(1);
      }

      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.flagRect2Mask)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING * 2 - ImageManager.getImage(Images.flagRect2).getWidth() + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flagRect2).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(Images.flagRect2).getWidth(),
            ImageManager.getImage(Images.flagRect2).getHeight()
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.flagRect2)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING * 2 - ImageManager.getImage(Images.flagRect2).getWidth() + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flagRect2).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(Images.flagRect2).getWidth(),
            ImageManager.getImage(Images.flagRect2).getHeight()
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX()
            + this.leftIconMaxW
            + ImageManager.getImage(Images.flagRect2).getWidth()
            + CFG.PADDING * 4
            + (this.getWidth() - (this.leftIconMaxW + ImageManager.getImage(Images.flagRect2).getWidth() * 2 + CFG.PADDING * 6)) / 2
            - this.getTextWidth() / 2
            + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();

      try {
         Battle battle = Game.battleManager.getBattle(this.battleKey);
         if (battle == null) {
            this.menuElementHover = null;
         } else {
            nData.add(
               new MenuElement_HoverElement_Type_TextTitle_BG(
                  Game.lang.get("BattleOf", Game.getProvince(battle.provinceID).getProvinceName()), Colors.HOVER_GOLD
               )
            );
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.battle, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Soldiers") + ":", CFG.FONT_REGULAR_SMALL));
            if (battle.attackingArmy.iCivID < 0) {
               nData.add(new MenuElement_HoverElement_Type_Image(Images.rebelsFlag, CFG.PADDING, CFG.PADDING));
            } else {
               nData.add(new MenuElement_HoverElement_Type_Flag(battle.attackingArmy.iCivID, CFG.PADDING, CFG.PADDING));
            }

            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  CFG.getNumberWithSpaces("" + battle.attackingArmy.numOfUnits)
                     + GameValues.battle.RIGHT_BATTLE_TEXT
                     + CFG.getNumberWithSpaces("" + battle.defendingArmy.numOfUnits),
                  CFG.FONT_BOLD_SMALL,
                  Colors.HOVER_GOLD
               )
            );
            if (battle.defendingArmy.iCivID < 0) {
               nData.add(new MenuElement_HoverElement_Type_Image(Images.rebelsFlag, CFG.PADDING, 0));
            } else {
               nData.add(new MenuElement_HoverElement_Type_Flag(battle.defendingArmy.iCivID, CFG.PADDING, 0));
            }

            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements);
         }
      } catch (Exception var4) {
         this.menuElementHover = null;
      }
   }

   public final void updateBattle() {
      try {
         if (this.lastTurnID != Game_Calendar.TURN_ID) {
            this.lastTurnID = Game_Calendar.TURN_ID;
            Battle battle = Game.battleManager.getBattle(this.battleKey);
            if (battle != null) {
               this.civIDLeft = battle.attackingArmy.iCivID;
               this.civIDRight = battle.defendingArmy.iCivID;
               this.setText(
                  CFG.getNumberWithSpaces("" + battle.attackingArmy.numOfUnits) + " vs " + CFG.getNumberWithSpaces("" + battle.defendingArmy.numOfUnits)
               );
               this.iCurrent = this.civIDLeft == Game.player.iCivID ? this.civIDRight : this.civIDLeft;
            } else {
               Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Right") {
                  @Override
                  public void update() {
                     Game.menuManager.rebuildInGame_Right();
                  }
               });
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      if (isHovered) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = this.iCurrent;
      }
   }

   @Override
   public void actionElement() {
      if (Game.menuManager.getVisibleInGame_Battle() && InGame_Battle.key.equals(this.battleKey)) {
         Game.menuManager.setVisibleInGame_Battle(false);
      } else {
         InGame_Battle.key = this.battleKey;
         Game.menuManager.rebuildInGame_Battle();
      }
   }

   @Override
   public void actionElementPPM() {
      Battle battle = Game.battleManager.getBattle(this.battleKey);
      if (battle != null) {
         Game.mapCoords.centerToProvinceID(battle.provinceID);
      }
   }
}
