package aoc.kingdoms.lukasz.menu_element;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Touch;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class Minimap extends MenuElement {
   private int iWindowPosX;
   private int iWindowPosY;
   private int iWidnowHeight;
   public static int minimapBoxBorder = 0;
   public final Color boxColor = new Color(0.0F, 0.0F, 0.0F, 1.0F);

   public Minimap(int nPosX, int nPosY) {
      this.typeOfElement = MenuElement_Type.MINIMAP;
      this.setPosX(nPosX);
      this.setPosY(nPosY);
      this.setWidth(Game.mapBG.iMinimapWidth);
      this.setHeight(Game.mapBG.iMinimapHeight);
   }

   @Override
   public final void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      try {
         Game.mapBG.minimapOfCivilizations.draw(oSB, this.getPosX() + getPadding() + iTranslateX, this.getPosY() + getPadding() + iTranslateY, false, true);
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }

      try {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
         if (Game.mapBG.fMinimapScaled_Scale == 1.0F) {
            this.iWindowPosX = (int)(-Game.mapCoords.getPosX() / Game.mapBG.fMinimapScaleX);
            this.iWindowPosY = (int)(
               -Game.mapCoords.getPosY() / Game.mapBG.fMinimapScaleY < 0.0F ? 0.0F : -Game.mapCoords.getPosY() / Game.mapBG.fMinimapScaleY
            );
            this.iWidnowHeight = (int)(
               this.iWindowPosY + CFG.GAME_HEIGHT / Game.mapBG.fMinimapScaleY / Game.mapScale.getCurrentScale() > Game.mapBG.minimapOfCivilizations.getHeight()
                  ? Game.mapBG.minimapOfCivilizations.getHeight() - this.iWindowPosY
                  : CFG.GAME_HEIGHT / Game.mapBG.fMinimapScaleY / Game.mapScale.getCurrentScale()
            );
            Renderer.drawRect(
               oSB,
               this.getPosX() + this.iWindowPosX - 1 + iTranslateX + getPadding(),
               1 + this.getPosY() + this.iWindowPosY - 1 + iTranslateY + getPadding(),
               2
                  + (int)(
                     CFG.GAME_WIDTH / Game.mapBG.fMinimapScaleX / Game.mapScale.getCurrentScale() + this.iWindowPosX
                           > Game.mapBG.minimapOfCivilizations.getWidth()
                        ? Game.mapBG.minimapOfCivilizations.getWidth() - this.iWindowPosX
                        : CFG.GAME_WIDTH / Game.mapBG.fMinimapScaleX / Game.mapScale.getCurrentScale()
                  ),
               this.iWidnowHeight + 2
            );
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
            Renderer.drawRect(
               oSB,
               this.getPosX() + this.iWindowPosX + iTranslateX + getPadding(),
               1 + this.getPosY() + this.iWindowPosY + iTranslateY + getPadding(),
               (int)(
                  CFG.GAME_WIDTH / Game.mapBG.fMinimapScaleX / Game.mapScale.getCurrentScale() + this.iWindowPosX
                        > Game.mapBG.minimapOfCivilizations.getWidth()
                     ? Game.mapBG.minimapOfCivilizations.getWidth() - this.iWindowPosX
                     : CFG.GAME_WIDTH / Game.mapBG.fMinimapScaleX / Game.mapScale.getCurrentScale()
               ),
               this.iWidnowHeight
            );
            if (Game.mapCoords.getSecondSideOfMap()) {
               oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
               Renderer.drawRect(
                  oSB,
                  this.getPosX() + iTranslateX + getPadding(),
                  this.getPosY() + 1 + this.iWindowPosY + iTranslateY + getPadding(),
                  (int)Math.abs(
                     CFG.GAME_WIDTH / Game.mapScale.getCurrentScale() / Game.mapBG.fMinimapScaleX
                        - (Game.mapBG.getWidth() + Game.mapCoords.getPosX()) / Game.mapBG.fMinimapScaleX
                  ),
                  this.iWidnowHeight
               );
               oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
               Renderer.drawRect(
                  oSB,
                  this.getPosX() + iTranslateX + getPadding(),
                  this.getPosY() + 1 + this.iWindowPosY + iTranslateY + getPadding(),
                  (int)Math.abs(
                     CFG.GAME_WIDTH / Game.mapScale.getCurrentScale() / Game.mapBG.fMinimapScaleX
                        - (Game.mapBG.getWidth() + Game.mapCoords.getPosX()) / Game.mapBG.fMinimapScaleX
                  ),
                  this.iWidnowHeight
               );
            }
         } else {
            Rectangle clipBounds = new Rectangle(
               this.getPosX() + iTranslateX, CFG.GAME_HEIGHT - this.getPosY() - iTranslateY, this.getWidth(), -this.getHeight()
            );
            oSB.flush();
            ScissorStack.pushScissors(clipBounds);
            this.iWindowPosX = (int)(
               -(Game.mapCoords.getPosX() + Game.mapBG.iMinimapScaled_PosX) / Game.mapBG.getMinimapScaled_ScaleX()
                  - (
                     Game.mapBG.minimapBelowZero && -Game.mapCoords.getPosX() > Game.mapBG.getWidth() - Game.mapBG.getWidth() / Game.mapBG.fMinimapScaled_Scale
                        ? Game.mapBG.getWidth() / Game.mapBG.getMinimapScaled_ScaleX()
                        : 0.0F
                  )
            );
            this.iWindowPosY = (int)(-(Game.mapCoords.getPosY() + Game.mapBG.iMinimapScaled_PosY) / Game.mapBG.getMinimapScaled_ScaleY());
            this.iWidnowHeight = (int)(
               this.iWindowPosY + CFG.GAME_HEIGHT / Game.mapBG.getMinimapScaled_ScaleY() / Game.mapScale.getCurrentScale()
                     > Game.mapBG.minimapOfCivilizations.getHeight()
                  ? Game.mapBG.minimapOfCivilizations.getHeight() - this.iWindowPosY
                  : CFG.GAME_HEIGHT / Game.mapBG.getMinimapScaled_ScaleY() / Game.mapScale.getCurrentScale()
            );
            Renderer.drawRect(
               oSB,
               this.getPosX() + this.iWindowPosX - 1 + iTranslateX + getPadding(),
               1 + this.getPosY() + this.iWindowPosY - 1 + iTranslateY + getPadding(),
               2
                  + (int)(
                     CFG.GAME_WIDTH / Game.mapBG.getMinimapScaled_ScaleX() / Game.mapScale.getCurrentScale() + this.iWindowPosX
                           > Game.mapBG.minimapOfCivilizations.getWidth()
                        ? Game.mapBG.minimapOfCivilizations.getWidth() - this.iWindowPosX
                        : CFG.GAME_WIDTH / Game.mapBG.getMinimapScaled_ScaleX() / Game.mapScale.getCurrentScale()
                  ),
               this.iWidnowHeight + 2
            );
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
            Renderer.drawRect(
               oSB,
               this.getPosX() + this.iWindowPosX + iTranslateX + getPadding(),
               1 + this.getPosY() + this.iWindowPosY + iTranslateY + getPadding(),
               (int)(
                  CFG.GAME_WIDTH / Game.mapBG.getMinimapScaled_ScaleX() / Game.mapScale.getCurrentScale() + this.iWindowPosX
                        > Game.mapBG.minimapOfCivilizations.getWidth()
                     ? Game.mapBG.minimapOfCivilizations.getWidth() - this.iWindowPosX
                     : CFG.GAME_WIDTH / Game.mapBG.getMinimapScaled_ScaleX() / Game.mapScale.getCurrentScale()
               ),
               this.iWidnowHeight
            );

            try {
               oSB.flush();
               ScissorStack.popScissors();
            } catch (Exception var8) {
               CFG.exceptionStack(var8);
            }
         }

         oSB.setColor(Color.WHITE);
         Game.mapBG.minimapOver.draw(oSB, this.getPosX() + getPadding() + iTranslateX, this.getPosY() + getPadding() + iTranslateY);
      } catch (Exception var10) {
         CFG.exceptionStack(var10);
      }
   }

   public static int getPadding() {
      return 0;
   }

   @Override
   public int getWidth() {
      return Game.mapBG.iMinimapWidth + getPadding() * 2;
   }

   @Override
   public int getHeight() {
      return Game.mapBG.iMinimapHeight + getPadding() * 2;
   }

   @Override
   public void actionElement() {
      super.actionElement();
      Game.mapCoords.centerToMinimapClick(Touch.getMousePosX() - this.getPosX() - getPadding(), Touch.getMousePosY() - this.getPosY() - getPadding());
   }
}
