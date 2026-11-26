package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_Scrollable extends MenuElement {
   protected String sText = null;
   protected int iTextWidth = -1;
   protected int iTextHeight = -1;
   private float fTextScale = 1.0F;
   private Color textColor;
   private boolean center = false;
   protected Text_Scrollable.TextPosition textPosition;
   private boolean scrollable = false;
   private int iScrollPosX = 0;
   private boolean scrollInRightDirection = true;
   private long lTime;
   private Text_Scrollable.DrawText drawText;

   public Text_Scrollable(String sText, int iPosX, int iPosY, int iWidth, Color textColor) {
      this.init(sText, iPosX, iPosY, iWidth, 0, textColor, 1.0F, 0);
   }

   public Text_Scrollable(String sText, int iPosX, int iPosY, int iWidth, int iHeight, Color textColor) {
      this.init(sText, iPosX, iPosY, iWidth, iHeight, textColor, 1.0F, 0);
   }

   public Text_Scrollable(String sText, int iPosX, int iPosY, int iWidth, Color textColor, float nTextScale) {
      this.init(sText, iPosX, iPosY, iWidth, 0, textColor, nTextScale, 0);
   }

   public Text_Scrollable(String sText, int iPosX, int iPosY, int iWidth, int iHeight, Color textColor, float nTextScale) {
      this.init(sText, iPosX, iPosY, iWidth, iHeight, textColor, nTextScale, 0);
   }

   public Text_Scrollable(String sText, int iPosX, int iPosY, int iWidth, int iHeight, Color textColor, float nTextScale, int iTextPosition) {
      this.init(sText, iPosX, iPosY, iWidth, iHeight, textColor, nTextScale, iTextPosition);
   }

   private final void init(String sText, int iPosX, int iPosY, int iWidth, int iHeight, Color textColor, float nTextScale, int iTextPosition) {
      this.typeOfElement = MenuElement_Type.TEXT_SCROLLABLE;
      this.iScrollPosX = iTextPosition;
      this.fTextScale = nTextScale;
      this.center = iTextPosition < 0;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      if (iHeight > 0) {
         this.setHeight(iHeight);
      }

      this.setText(sText);
      this.textColor = textColor;
      this.updateTextPosition();
      if (this.fTextScale != 1.0F) {
         this.drawText = new Text_Scrollable.DrawText() {
            @Override
            public void draw_Element(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               Renderer.setFontScale(Text_Scrollable.this.fTextScale);
               Renderer.drawTextWithShadow(
                  oSB,
                  Text_Scrollable.this.fontID,
                  Text_Scrollable.this.getText(),
                  Text_Scrollable.this.getPosX() + Text_Scrollable.this.textPosition.getTextPosition(isActive) + iTranslateX,
                  Text_Scrollable.this.getPosY() + Text_Scrollable.this.getHeight() / 2 - Text_Scrollable.this.iTextHeight / 2 + iTranslateY,
                  Text_Scrollable.this.getColor(isActive)
               );
               Renderer.resetFontScale();
            }
         };
      } else {
         this.drawText = new Text_Scrollable.DrawText() {
            @Override
            public void draw_Element(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               Renderer.drawTextWithShadow(
                  oSB,
                  Text_Scrollable.this.fontID,
                  Text_Scrollable.this.getText(),
                  Text_Scrollable.this.getPosX() + Text_Scrollable.this.textPosition.getTextPosition(isActive) + iTranslateX,
                  Text_Scrollable.this.getPosY() + Text_Scrollable.this.getHeight() / 2 - Text_Scrollable.this.iTextHeight / 2 + iTranslateY,
                  Text_Scrollable.this.getColor(isActive)
               );
            }
         };
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      if (Renderer.clipView_Start(
         oSB, this.getPosX() + iTranslateX, CFG.GAME_HEIGHT - this.getPosY() - iTranslateY, this.getWidth(), -this.getHeight() - CFG.PADDING * 2
      )) {
         this.draw_Element(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
         this.draw_EndClip(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
      }
   }

   protected void draw_Element(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      try {
         this.drawText.draw_Element(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
      } catch (NullPointerException var7) {
         var7.printStackTrace();
      }
   }

   protected void draw_EndClip(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      Renderer.clipView_End(oSB);
   }

   public Color getColor(boolean isActive) {
      return isActive
         ? Colors.BUTTON_TEXT_ACTIVE
         : (this.getIsHovered() ? Colors.BUTTON_TEXT_HOVERED : (this.getClickable() ? this.textColor : new Color(0.78F, 0.78F, 0.78F, 0.7F)));
   }

   @Override
   public final String getText() {
      return this.sText;
   }

   @Override
   public void setText(String sText) {
      this.sText = sText;

      try {
         Renderer.setFontScale(this.fTextScale);
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
         this.iTextWidth = (int)Renderer.glyphLayout.width;
         this.iTextHeight = (int)Renderer.glyphLayout.height;
         Renderer.resetFontScale();
         this.updateTextPosition();
         if (this.getHeight() < this.iTextHeight) {
            this.setHeight(this.iTextHeight);
         }
      } catch (NullPointerException var3) {
      }
   }

   protected final void updateTextPosition() {
      if (this.getTextWidth() > this.getWidth() + CFG.PADDING) {
         this.textPosition = new Text_Scrollable.TextPosition() {
            @Override
            public int getTextPosition(boolean isActive) {
               if (isActive) {
                  return Text_Scrollable.this.iScrollPosX;
               } else {
                  if (Text_Scrollable.this.lTime + 30L <= CFG.currentTimeMillis) {
                     if (Text_Scrollable.this.scrollInRightDirection) {
                        --Text_Scrollable.this.iScrollPosX;
                        if (Text_Scrollable.this.getWidth() - Text_Scrollable.this.iScrollPosX >= Text_Scrollable.this.getTextWidth() + CFG.PADDING) {
                           Text_Scrollable.this.scrollInRightDirection = !Text_Scrollable.this.scrollInRightDirection;
                        }
                     } else {
                        ++Text_Scrollable.this.iScrollPosX;
                        if (Text_Scrollable.this.iScrollPosX == CFG.PADDING) {
                           Text_Scrollable.this.scrollInRightDirection = !Text_Scrollable.this.scrollInRightDirection;
                        }
                     }

                     Text_Scrollable.this.lTime = CFG.currentTimeMillis;
                  }

                  return Text_Scrollable.this.iScrollPosX;
               }
            }
         };
         this.scrollable = true;
      } else {
         if (this.center) {
            this.textPosition = new Text_Scrollable.TextPosition() {
               @Override
               public int getTextPosition(boolean isActive) {
                  return Text_Scrollable.this.getWidth() / 2 - Text_Scrollable.this.getTextWidth() / 2;
               }
            };
         } else {
            this.textPosition = new Text_Scrollable.TextPosition() {
               @Override
               public int getTextPosition(boolean isActive) {
                  return 0;
               }
            };
         }

         this.scrollable = false;
      }

      this.iScrollPosX = 0;
      this.scrollInRightDirection = true;
   }

   @Override
   public void setScrollPosX(int scrollPosX) {
      this.iScrollPosX = scrollPosX;
      if (this.iScrollPosX > CFG.PADDING) {
         this.iScrollPosX = CFG.PADDING;
         this.scrollInRightDirection = true;
      } else if (this.getTextWidth() + CFG.PADDING - this.getWidth() < -this.iScrollPosX) {
         this.iScrollPosX = -(this.getTextWidth() + CFG.PADDING - this.getWidth());
         this.scrollInRightDirection = false;
      }
   }

   @Override
   public int getTextWidth() {
      return this.iTextWidth;
   }

   @Override
   public int getTextHeight() {
      return this.iTextHeight;
   }

   @Override
   public int getScrollPosX() {
      return this.iScrollPosX;
   }

   @Override
   public boolean getScrollable() {
      return this.scrollable;
   }

   @Override
   public void scrollByWheel(int nScoll) {
      this.scrollInRightDirection = nScoll < 0;
      this.setScrollPosX(this.getScrollPosX() + nScoll);
      this.lTime = CFG.currentTimeMillis + 375L;
   }

   private interface DrawText {
      void draw_Element(SpriteBatch var1, int var2, int var3, boolean var4, boolean var5);
   }

   interface TextPosition {
      int getTextPosition(boolean var1);
   }
}
