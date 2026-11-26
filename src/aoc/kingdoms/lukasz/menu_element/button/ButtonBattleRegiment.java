package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.RomanNumber;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.battles.BattleRegiment;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Army;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battle;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonBattleRegiment extends MenuElement {
   public int imageID;
   public int iCivID;
   public int id;
   public int offsetY = 0;
   public int attackRange;
   public boolean secondLine;
   public boolean defenders;

   public ButtonBattleRegiment(int nCivID, int imageID, int iPosX, int iPosY, int id, int offsetY, int attackRange, boolean secondLine, boolean defenders) {
      this.typeOfElement = MenuElement_Type.BUTTON;
      this.iCivID = nCivID;
      this.imageID = imageID;
      this.id = id;
      this.secondLine = secondLine;
      this.defenders = defenders;
      this.attackRange = attackRange * 2 + 1;
      this.offsetY = offsetY;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(ImageManager.getImage(imageID).getWidth() + 2);
      this.setHeight(ImageManager.getImage(imageID).getHeight() + 2);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(Game.getCiv(this.iCivID).getRGB(1.0F));
      Images.pix
         .draw(
            oSB,
            this.getPosX() + 1 + iTranslateX,
            this.getPosY() + 1 + iTranslateY,
            ImageManager.getImage(this.imageID).getWidth(),
            ImageManager.getImage(this.imageID).getHeight()
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, this.getIsHovered() ? 0.65F : 0.4F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + 1 + iTranslateX,
            this.getPosY() + 1 + iTranslateY,
            ImageManager.getImage(this.imageID).getWidth(),
            ImageManager.getImage(this.imageID).getHeight()
         );
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(this.imageID).draw(oSB, this.getPosX() + 1 + iTranslateX, this.getPosY() + 1 + iTranslateY);
      if (this.getIsHovered()) {
         oSB.setColor(Colors.HOVER_GOLD);
         Renderer.drawBox2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         this.drawHover(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
      }

      oSB.setColor(Color.WHITE);
   }

   public void drawHover(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      if (this.defenders) {
         int tHeight = InGame_Battle.getMiddleHeight() - 1 + (this.secondLine ? ImageManager.getImage(this.imageID).getHeight() + 2 : 0);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F));
         Images.pix.draw(oSB, this.getPosX() + this.getWidth() / 2 + 1 + iTranslateX, this.getPosY() - tHeight + 1 + iTranslateY, 1, tHeight - 1);
         Images.pix.draw(oSB, this.getPosX() + this.getWidth() / 2 - 1 + iTranslateX, this.getPosY() - tHeight + 1 + iTranslateY, 1, tHeight - 1);
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 + iTranslateX,
               this.getPosY() - tHeight + iTranslateY,
               (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange,
               1
            );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - 1 - (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 + iTranslateX,
               this.getPosY() - tHeight - CFG.PADDING - 1 + iTranslateY,
               1,
               CFG.PADDING + 2
            );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 + 1 + (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 - 1 + iTranslateX,
               this.getPosY() - tHeight - CFG.PADDING - 1 + iTranslateY,
               1,
               CFG.PADDING + 2
            );
         oSB.setColor(Colors.HOVER_GOLD);
         Images.pix.draw(oSB, this.getPosX() + this.getWidth() / 2 + iTranslateX, this.getPosY() - tHeight + iTranslateY, 1, tHeight);
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 + iTranslateX,
               this.getPosY() - tHeight - 1 + iTranslateY,
               (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange,
               1
            );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 + iTranslateX,
               this.getPosY() - tHeight - CFG.PADDING - 1 + iTranslateY,
               1,
               CFG.PADDING
            );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 + (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 - 1 + iTranslateX,
               this.getPosY() - tHeight - CFG.PADDING - 1 + iTranslateY,
               1,
               CFG.PADDING
            );
      } else {
         int tHeight = InGame_Battle.getMiddleHeight() - 1 + (this.secondLine ? ImageManager.getImage(this.imageID).getHeight() + 2 : 0);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F));
         Images.pix.draw(oSB, this.getPosX() + this.getWidth() / 2 + 1 + iTranslateX, this.getPosY() + this.getHeight() + iTranslateY, 1, tHeight - 1);
         Images.pix.draw(oSB, this.getPosX() + this.getWidth() / 2 - 1 + iTranslateX, this.getPosY() + this.getHeight() + iTranslateY, 1, tHeight - 1);
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 + iTranslateX,
               this.getPosY() + this.getHeight() + tHeight - 1 + iTranslateY,
               (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange,
               1
            );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - 1 - (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 + iTranslateX,
               this.getPosY() + this.getHeight() + tHeight - 1 + iTranslateY,
               1,
               CFG.PADDING + 1
            );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 + 1 + (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 - 1 + iTranslateX,
               this.getPosY() + this.getHeight() + tHeight - 1 + iTranslateY,
               1,
               CFG.PADDING + 1
            );
         oSB.setColor(Colors.HOVER_GOLD);
         Images.pix.draw(oSB, this.getPosX() + this.getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() + iTranslateY, 1, tHeight);
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 + iTranslateX,
               this.getPosY() + this.getHeight() + tHeight + iTranslateY,
               (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange - 1,
               1
            );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 + iTranslateX,
               this.getPosY() + this.getHeight() + tHeight + iTranslateY,
               1,
               CFG.PADDING
            );
         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 + (ImageManager.getImage(this.imageID).getWidth() + 1) * this.attackRange / 2 - 1 + iTranslateX,
               this.getPosY() + this.getHeight() + tHeight + iTranslateY,
               1,
               CFG.PADDING
            );
      }
   }

   @Override
   public void buildElementHover() {
      int battleID = Game.battleManager.getBattleID(InGame_Battle.key);
      if (battleID >= 0) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(
            new MenuElement_HoverElement_Type_FlagCiv_Title(
               this.getBattleRegiment(battleID, this.id).c,
               RomanNumber.getRoman(this.getBattleRegiment(battleID, this.id).rn) + ". " + Game.lang.get("Regiment")
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Army(
               "" + this.getBattleRegiment(battleID, this.id).aR.num,
               this.getBattleRegiment(battleID, this.id).aR.uID,
               this.getBattleRegiment(battleID, this.id).aR.aID,
               this.getBattleRegiment(battleID, this.id).c
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Casualties") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text("" + this.getBattleRegiment(battleID, this.id).ca, CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.skull, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("CasualtiesOfThePreviousDay") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text("" + this.getBattleRegiment(battleID, this.id).cL, CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.skull, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Retreated") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text("" + this.getBattleRegiment(battleID, this.id).re, CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.retreat, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RetreatsOnTheLastDay") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text("" + this.getBattleRegiment(battleID, this.id).rL, CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.retreat, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Morale") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               CFG.getPrecision2(this.getBattleRegiment(battleID, this.id).aR.mo * 100.0F, 1) + "%", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.morale, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DiceRoll") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(new MenuElement_HoverElement_Type_Text("" + this.getBattleRegiment(battleID, this.id).d, CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
         nData.add(new MenuElement_HoverElement_Type_Text(" / " + GameValues.battle.BATTLE_MAX_DICE_ROLL_REGIMENT, CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT2));
         nData.add(new MenuElement_HoverElement_Type_Image(Images.dice, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AttackRange") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + ArmyManager.lArmy.get(this.getBattleRegiment(battleID, this.id).aR.uID).get(this.getBattleRegiment(battleID, this.id).aR.aID).AttackRange,
               CFG.FONT_REGULAR_SMALL,
               Colors.HOVER_RIGHT
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("SiegeAbility") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               ""
                  + CFG.getPrecision2(
                     ArmyManager.lArmy.get(this.getBattleRegiment(battleID, this.id).aR.uID).get(this.getBattleRegiment(battleID, this.id).aR.aID).SiegeProgress,
                     100
                  ),
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_RIGHT
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.siege, CFG.PADDING, 0));
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
      } else {
         this.menuElementHover = null;
      }
   }

   public BattleRegiment getBattleRegiment(int battleID, int id) {
      return Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(id);
   }

   @Override
   public int getPosY() {
      return super.getPosY() + this.offsetY;
   }

   @Override
   public void setCurrent(int nCurrent) {
      this.offsetY = nCurrent;
   }

   @Override
   public int getCurrent() {
      return this.offsetY;
   }
}
