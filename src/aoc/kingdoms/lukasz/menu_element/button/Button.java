package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button extends MenuElement {
   protected static final Color COLOR_BUTTON_MENU_HOVER_BG = new Color(1.0F, 1.0F, 1.0F, 1.0F);
   protected Button.Checkbox oCheckbox;
   protected String sText = null;
   protected int iTextWidth = -1;
   protected int iTextHeight;
   protected int iTextPositionX;
   protected Button.TextPosition textPosition;
   protected boolean checkbox = false;
   private boolean checkboxState = false;

   public Button() {
   }

   public Button(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, CFG.BUTTON_HEIGHT, isClickable, true, false, false);
   }

   public Button(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, int nHeight, boolean isClickable) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, nHeight, isClickable, true, false, false);
   }

   public final void init(
      String sText,
      int fontID,
      int nTextPositionX,
      int iPosX,
      int iPosY,
      int iWidth,
      int iHeight,
      boolean isClickable,
      boolean isVisible,
      boolean checkbox,
      boolean checkboxState
   ) {
      this.typeOfElement = MenuElement_Type.BUTTON;
      this.fontID = fontID;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      this.iTextPositionX = nTextPositionX;
      if (nTextPositionX < 0) {
         this.textPosition = new Button.TextPosition() {
            @Override
            public int getTextPosition() {
               return Button.this.getWidth() / 2 - Button.this.getTextWidth() / 2;
            }
         };
      } else {
         this.textPosition = new Button.TextPosition() {
            @Override
            public int getTextPosition() {
               return Button.this.iTextPositionX;
            }
         };
      }

      this.checkbox = checkbox;
      this.checkboxState = checkboxState;
      this.oCheckbox = this.buildCheckbox();
      this.setClickable(isClickable);
      this.setVisible(isVisible);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      if (this.getClickable()) {
         this.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
      } else {
         oSB.setColor(1.0F, 1.0F, 1.0F, 0.45F);
         this.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
         oSB.setColor(Color.WHITE);
      }

      this.oCheckbox.drawCheckBox(oSB, iTranslateX, iTranslateY, scrollableY);
      this.drawText(oSB, iTranslateX, iTranslateY, isActive);
   }

   public Button.Checkbox buildCheckbox() {
      return this.checkbox
         ? new Button.Checkbox() {
            @Override
            public void drawCheckBox(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean scrollableY) {
               if (Button.this.getCheckboxState()) {
                  oSB.setColor(new Color(0.55F, 0.8F, 0.0F, 0.25F));
               } else {
                  oSB.setColor(new Color(0.8F, 0.137F, 0.0F, 0.25F));
               }

               ImageManager.getImage(Images.gradientHorizontal)
                  .draw(
                     oSB,
                     Button.this.getPosX() + iTranslateX,
                     Button.this.getPosY() + 1 + iTranslateY,
                     Button.this.getWidth() / 4,
                     Button.this.getHeight() - 2,
                     false,
                     false
                  );
               oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.1F));
               ImageManager.getImage(Images.gradientVertical)
                  .draw(
                     oSB,
                     Button.this.getPosX() + iTranslateX,
                     Button.this.getPosY() + 1 + iTranslateY,
                     Button.this.getWidth(),
                     Button.this.getHeight() / 4,
                     false,
                     false
                  );
               ImageManager.getImage(Images.gradientVertical)
                  .draw(
                     oSB,
                     Button.this.getPosX() + iTranslateX,
                     Button.this.getPosY() + Button.this.getHeight() - 1 + iTranslateY - Button.this.getHeight() / 4,
                     Button.this.getWidth(),
                     Button.this.getHeight() / 4,
                     false,
                     true
                  );
               oSB.setColor(Color.WHITE);
            }
         }
         : new Button.Checkbox() {
            @Override
            public void drawCheckBox(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean scrollableY) {
            }
         };
   }

   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (isActive) {
         ImageManager.getImage(Images.buttonMenuH).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth());
      } else if (this.getIsHovered() && this.getClickable()) {
         oSB.setColor(COLOR_BUTTON_MENU_HOVER_BG);
         ImageManager.getImage(Images.buttonMenuH).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth());
         oSB.setColor(Color.WHITE);
      } else {
         ImageManager.getImage(Images.buttonMenu)
            .draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.buttonMenu).getWidth());
         ImageManager.getImage(Images.buttonMenu)
            .draw2(
               oSB,
               this.getPosX() + this.getWidth() - ImageManager.getImage(Images.buttonMenu).getWidth() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.buttonMenu).getWidth(),
               ImageManager.getImage(Images.buttonMenu).getHeight(),
               true,
               false
            );
         oSB.setColor(new Color(Colors.COLOR_BOX_FRAME2.r, Colors.COLOR_BOX_FRAME2.g, Colors.COLOR_BOX_FRAME2.b, Colors.COLOR_BOX_FRAME2.a));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth() - 2, 1);
         oSB.setColor(Color.WHITE);
      }
   }

   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.BUTTON_TEXT_ACTIVE;
      } else if (this.getIsHovered()) {
         return Colors.BUTTON_TEXT_HOVERED;
      } else {
         return this.getClickable() ? Colors.BUTTON_TEXT : Colors.BUTTON_TEXT_DISABLED;
      }
   }

   @Override
   public String getText() {
      return this.sText;
   }

   @Override
   public String getTextToDraw() {
      return this.sText;
   }

   @Override
   public void setText(String sText) {
      this.sText = sText;

      try {
         if (sText != null) {
            GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
            glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.getText());
            this.iTextWidth = (int)glyphLayout.width;
            this.iTextHeight = (int)glyphLayout.height;
         } else {
            this.iTextWidth = this.iTextHeight = 0;
         }
      } catch (IndexOutOfBoundsException var3) {
         CFG.exceptionStack(var3);
      } catch (NullPointerException var4) {
         CFG.exceptionStack(var4);
      } catch (IllegalArgumentException var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void setCheckbox(boolean checkbox) {
      this.checkbox = checkbox;
   }

   @Override
   public boolean getCheckboxState() {
      return this.checkboxState;
   }

   @Override
   public void setCheckboxState(boolean checkboxState) {
      this.checkboxState = checkboxState;
   }

   @Override
   public int getTextWidth() {
      return this.iTextWidth;
   }

   @Override
   public int getTextPos() {
      return this.iTextPositionX;
   }

   @Override
   public int getTextHeight() {
      return this.iTextHeight;
   }

   interface Checkbox {
      void drawCheckBox(SpriteBatch var1, int var2, int var3, boolean var4);
   }

   public interface TextPosition {
      int getTextPosition();
   }
}
