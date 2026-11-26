package aoc.kingdoms.lukasz.menu;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.jakowski.FBO.FBOProvincesBG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menusEditor.CreateCiv;
import aoc.kingdoms.lukasz.menusEditor.GameCivsEdit;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorPicker {
   private int iPosX = 100;
   private int iPosY = 100;
   private boolean visible = false;
   private int iSVHeight;
   private int iHUEWidth;
   private int iResizeHeight;
   private int fontID = 0;
   private boolean activeHUE = false;
   private boolean activeSV = false;
   private boolean activeResize = false;
   private boolean activeMove = false;
   private boolean activeClose = false;
   public static int activeRGB = -1;
   private int iActiveColorID = -1;
   private float fAlpha = 1.0F;
   private int iStartPosX;
   private int iStartPosY;
   private int iStartResizeHeight;
   private Color colorSVPos = Color.WHITE;
   private int iLastSVPosX;
   private int iLastSVPosY;
   private int iLastHUEPosY;
   private final float RGB_TEXT_SCALE = 0.9F;
   private int iRGBTextWidth;
   private int iRTextWidth;
   private int iGTextWidth;
   private int iBTextWidth;
   public static int ACTIVE_CIV_ID = 0;
   private List<ColorPicker.Box> lRGBBoxes = new ArrayList<>();
   private List<ColorPicker.Box> lColorsBoxes = new ArrayList<>();
   private List<Color> lColors = new ArrayList<>();
   public ColorPicker.ColorPicker_AoC_Action ColorPicker_AoC_Action;
   public static Color hueColor = new Color(1.0F, 0.0F, 0.0F, 1.0F);
   public static Color activeColor = new Color(1.0F, 0.0F, 0.0F, 1.0F);
   private float[] hsv = new float[]{0.0F, 1.0F, 1.0F};
   private float hueVal = 1.0F;

   public final void updateColorPicker_Action(ColorPicker.PickerAction nAction) {
      switch (nAction) {
         case NONE_ACTION:
            this.ColorPicker_AoC_Action = new ColorPicker.ColorPicker_AoC_Action() {
               @Override
               public void update() {
               }

               @Override
               public void setActiveProvince_Action() {
               }
            };
            break;
         case GAMECIVS_EDIT:
            this.ColorPicker_AoC_Action = new ColorPicker.ColorPicker_AoC_Action() {
               @Override
               public void update() {
                  GameCivsEdit.nCiv.iR = (int)(ColorPicker.activeColor.r * 255.0F);
                  GameCivsEdit.nCiv.iG = (int)(ColorPicker.activeColor.g * 255.0F);
                  GameCivsEdit.nCiv.iB = (int)(ColorPicker.activeColor.b * 255.0F);
               }

               @Override
               public void setActiveProvince_Action() {
               }
            };
            break;
         case CREATE_CIV_EDIT:
            this.ColorPicker_AoC_Action = new ColorPicker.ColorPicker_AoC_Action() {
               @Override
               public void update() {
                  CreateCiv.nCiv.iR = (int)(ColorPicker.activeColor.r * 255.0F);
                  CreateCiv.nCiv.iG = (int)(ColorPicker.activeColor.g * 255.0F);
                  CreateCiv.nCiv.iB = (int)(ColorPicker.activeColor.b * 255.0F);
               }

               @Override
               public void setActiveProvince_Action() {
               }
            };
            break;
         case CREATE_CIV_DIVISION:
            this.ColorPicker_AoC_Action = new ColorPicker.ColorPicker_AoC_Action() {
               @Override
               public void update() {
                  Game.flagManager
                     .flagEdit
                     .lDivisionColors
                     .set(Game.flagManager.activeColorID, new Color(ColorPicker.activeColor.r, ColorPicker.activeColor.g, ColorPicker.activeColor.b, 1.0F));
               }

               @Override
               public void setActiveProvince_Action() {
               }
            };
            break;
         case CIV_COLOR_NEWGAME:
            this.ColorPicker_AoC_Action = new ColorPicker.ColorPicker_AoC_Action() {
               @Override
               public void update() {
                  Game.getCiv(ColorPicker.ACTIVE_CIV_ID).setR((int)(ColorPicker.activeColor.r * 255.0F));
                  Game.getCiv(ColorPicker.ACTIVE_CIV_ID).setG((int)(ColorPicker.activeColor.g * 255.0F));
                  Game.getCiv(ColorPicker.ACTIVE_CIV_ID).setB((int)(ColorPicker.activeColor.b * 255.0F));
               }

               @Override
               public void setActiveProvince_Action() {
               }
            };
            break;
         case CIV_COLOR_INGAME:
            this.ColorPicker_AoC_Action = new ColorPicker.ColorPicker_AoC_Action() {
               @Override
               public void update() {
                  Game.getCiv(ColorPicker.ACTIVE_CIV_ID).setR((int)(ColorPicker.activeColor.r * 255.0F));
                  Game.getCiv(ColorPicker.ACTIVE_CIV_ID).setG((int)(ColorPicker.activeColor.g * 255.0F));
                  Game.getCiv(ColorPicker.ACTIVE_CIV_ID).setB((int)(ColorPicker.activeColor.b * 255.0F));
                  FBOProvincesBG.redrawnProvinces();
               }

               @Override
               public void setActiveProvince_Action() {
               }
            };
            break;
         default:
            this.ColorPicker_AoC_Action = new ColorPicker.ColorPicker_AoC_Action() {
               @Override
               public void update() {
               }

               @Override
               public void setActiveProvince_Action() {
               }
            };
      }
   }

   public ColorPicker() {
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(this.fontID), "G 255");
      this.iRGBTextWidth = (int)glyphLayout.width;
      this.updateRGBWidth();
      this.lRGBBoxes
         .add(
            new ColorPicker.Box(
               CFG.PADDING,
               ImageManager.getImage(Images.btn_close).getHeight() + ImageManager.getImage(Images.btn_close).getHeight() / 2,
               this.iRGBTextWidth + CFG.PADDING * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 2
            )
         );
      this.lRGBBoxes
         .add(
            new ColorPicker.Box(
               CFG.PADDING,
               ImageManager.getImage(Images.btn_close).getHeight()
                  + ImageManager.getImage(Images.btn_close).getHeight() / 2
                  + CFG.PADDING
                  + CFG.TEXT_HEIGHT
                  + CFG.PADDING * 2,
               this.iRGBTextWidth + CFG.PADDING * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 2
            )
         );
      this.lRGBBoxes
         .add(
            new ColorPicker.Box(
               CFG.PADDING,
               ImageManager.getImage(Images.btn_close).getHeight()
                  + ImageManager.getImage(Images.btn_close).getHeight() / 2
                  + CFG.PADDING * 2
                  + (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 2,
               this.iRGBTextWidth + CFG.PADDING * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 2
            )
         );
      this.updateColorPicker_Action(ColorPicker.PickerAction.NONE_ACTION);
   }

   public final void buildColors() {
      this.lColorsBoxes.add(new ColorPicker.Box(0, CFG.PADDING, this.getColorBoxWidth(), CFG.TEXT_HEIGHT + CFG.PADDING * 2));
      this.lColors.add(new Color(activeColor.r, activeColor.g, activeColor.b, 1.0F));
      new Random();

      for (int i = this.lColorsBoxes.get(0).getPosX() + this.lColorsBoxes.get(0).getWidth(); i < CFG.GAME_WIDTH; i += this.getColorBoxWidth()) {
         this.lColorsBoxes.add(new ColorPicker.Box(i, CFG.PADDING, this.getColorBoxWidth(), CFG.TEXT_HEIGHT + CFG.PADDING * 2));
         this.lColors.add(new Color(Game.oR.nextInt(256) / 255.0F, Game.oR.nextInt(256) / 255.0F, Game.oR.nextInt(256) / 255.0F, 1.0F));
      }
   }

   public final void updateColors() {
      this.lColors.set(0, new Color(activeColor.r, activeColor.g, activeColor.b, 1.0F));

      for (int i = 1; i < this.lColors.size(); i++) {
         this.lColors.set(i, new Color(Game.oR.nextInt(256) / 255.0F, Game.oR.nextInt(256) / 255.0F, Game.oR.nextInt(256) / 255.0F, 1.0F));
      }
   }

   private final int getPickerHue_InnerShadowWidth() {
      return CFG.PADDING * 2;
   }

   public final void draw(SpriteBatch oSB, int iTranslateX) {
      oSB.setColor(1.0F, 1.0F, 1.0F, this.fAlpha);
      Renderer.drawBoxHover(
         oSB,
         this.iPosX - CFG.PADDING * 2 + iTranslateX,
         this.iPosY - CFG.PADDING * 2,
         this.getWidth() + CFG.PADDING * 4,
         this.getHeight() + CFG.PADDING * 4,
         this.fAlpha
      );
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.pickerHUE).draw(oSB, this.iPosX + this.iSVHeight + CFG.PADDING + iTranslateX, this.iPosY, this.iHUEWidth, this.iSVHeight);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.275F));
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.iPosX + this.iSVHeight + CFG.PADDING + iTranslateX, this.iPosY, this.getPickerHue_InnerShadowWidth(), this.iSVHeight);
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth - this.getPickerHue_InnerShadowWidth() + iTranslateX,
            this.iPosY,
            this.getPickerHue_InnerShadowWidth(),
            this.iSVHeight,
            true,
            false
         );
      oSB.setColor(Color.WHITE);
      Images.pix.draw(oSB, this.iPosX + iTranslateX, this.iPosY, this.iSVHeight, this.iSVHeight);
      oSB.setColor(hueColor);
      ImageManager.getImage(Images.pickerSV).draw(oSB, this.iPosX + iTranslateX, this.iPosY, this.iSVHeight, this.iSVHeight);
      if (this.activeMove) {
         oSB.setColor(Color.BLACK);
         ImageManager.getImage(Images.pickerEdge).draw(oSB, this.iPosX + 1 + iTranslateX, this.iPosY, true, true);
      } else {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.825F));
         ImageManager.getImage(Images.pickerEdge).draw(oSB, this.iPosX + 1 + iTranslateX, this.iPosY, true, true);
      }

      if (this.activeResize) {
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.pickerEdge)
            .draw(
               oSB,
               this.iPosX + this.iSVHeight - ImageManager.getImage(Images.pickerEdge).getWidth() + iTranslateX,
               this.iPosY + this.iSVHeight - ImageManager.getImage(Images.pickerEdge).getHeight()
            );
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.675F));
         ImageManager.getImage(Images.pickerEdge)
            .draw(
               oSB,
               this.iPosX + this.iSVHeight - ImageManager.getImage(Images.pickerEdge).getWidth() + iTranslateX,
               this.iPosY + this.iSVHeight - ImageManager.getImage(Images.pickerEdge).getHeight()
            );
         if (!Renderer.clipView_Start(oSB, this.iPosX + iTranslateX, CFG.GAME_HEIGHT - this.iPosY, this.iSVHeight, -this.iSVHeight)) {
            return;
         }

         oSB.setColor(this.colorSVPos);
         ImageManager.getImage(Images.pickerSVPos)
            .draw(
               oSB,
               this.iPosX + this.iLastSVPosX - ImageManager.getImage(Images.pickerSVPos).getWidth() / 2 + iTranslateX,
               this.iPosY + this.iLastSVPosY - ImageManager.getImage(Images.pickerSVPos).getHeight() / 2
            );
         Renderer.clipView_End(oSB);
         oSB.setColor(0.0F, 0.0F, 0.0F, this.fAlpha);
         Images.pix
            .draw2(
               oSB,
               this.iPosX + this.iSVHeight + CFG.PADDING + CFG.PADDING + iTranslateX,
               this.iPosY - 1 + this.iLastHUEPosY,
               this.iHUEWidth - CFG.PADDING * 2 + 1,
               1
            );
      }

      oSB.setColor(Color.BLACK);
      Renderer.drawRect(oSB, this.iPosX + iTranslateX, this.iPosY - 1, this.iSVHeight, this.iSVHeight + 2);
      Renderer.drawRect(oSB, this.iPosX + CFG.PADDING + this.iSVHeight + iTranslateX, this.iPosY - 1, this.iHUEWidth, this.iSVHeight + 2);
      this.drawRGBText(
         oSB,
         0,
         this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + this.lRGBBoxes.get(0).getPosX() + iTranslateX,
         this.iPosY + this.lRGBBoxes.get(0).getPosY(),
         this.lRGBBoxes.get(0).getWidth(),
         this.lRGBBoxes.get(0).getHeight(),
         "R",
         "" + (int)(activeColor.r * 255.0F),
         this.iRTextWidth
      );
      this.drawRGBText(
         oSB,
         1,
         this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + this.lRGBBoxes.get(1).getPosX() + iTranslateX,
         this.iPosY + this.lRGBBoxes.get(1).getPosY(),
         this.lRGBBoxes.get(1).getWidth(),
         this.lRGBBoxes.get(1).getHeight(),
         "G",
         "" + (int)(activeColor.g * 255.0F),
         this.iGTextWidth
      );
      this.drawRGBText(
         oSB,
         2,
         this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + this.lRGBBoxes.get(2).getPosX() + iTranslateX,
         this.iPosY + this.lRGBBoxes.get(2).getPosY(),
         this.lRGBBoxes.get(2).getWidth(),
         this.lRGBBoxes.get(2).getHeight(),
         "B",
         "" + (int)(activeColor.b * 255.0F),
         this.iBTextWidth
      );
      this.drawColorBoxes(oSB, this.iPosX + iTranslateX, this.iPosY + this.iSVHeight);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.175F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.iPosX + this.lColorsBoxes.get(0).getPosX() + iTranslateX,
            this.iPosY + this.iSVHeight + this.lColorsBoxes.get(0).getPosY(),
            this.getWidth(),
            this.lColorsBoxes.get(this.lColorsBoxes.size() - 1).getHeight(),
            false,
            true
         );
      if (this.iActiveColorID >= 0) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               this.iPosX + this.lColorsBoxes.get(this.iActiveColorID).getPosX() + iTranslateX,
               this.iPosY + this.iSVHeight + this.lColorsBoxes.get(0).getPosY(),
               this.lColorsBoxes.get(this.iActiveColorID).getWidth(),
               this.lColorsBoxes.get(this.lColorsBoxes.size() - 1).getHeight()
            );
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, this.fAlpha));
      Renderer.drawRect(
         oSB,
         this.iPosX + this.lColorsBoxes.get(0).getPosX() + iTranslateX,
         this.iPosY + this.iSVHeight + this.lColorsBoxes.get(0).getPosY(),
         this.getWidth(),
         this.lColorsBoxes.get(this.lColorsBoxes.size() - 1).getHeight()
      );
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(this.activeClose ? Images.btnh_close : Images.btn_close)
         .draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.btn_close).getWidth() + iTranslateX, this.getPosY());
   }

   public final void drawRGBText(SpriteBatch oSB, int boxID, int nPosX, int nPosY, int nWidth, int nHeight, String sLeft, String sRight, int nRightWidth) {
      oSB.setColor(Colors.COLOR_COLOR_PICKER_RGB_BG);
      Images.pix.draw(oSB, nPosX, nPosY, nWidth, nHeight);
      if (activeRGB == boxID) {
         oSB.setColor(Colors.COLOR_LOADING_SPLIT_ACTIVE);
      } else {
         oSB.setColor(Colors.COLOR_LOADING_SPLIT);
      }

      Renderer.drawRect(oSB, nPosX, nPosY, nWidth, nHeight);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawRect(oSB, nPosX - 1, nPosY - 1, nWidth + 2, nHeight + 2);
      oSB.setColor(Color.WHITE);
      Renderer.drawText(oSB, this.fontID, sLeft, nPosX + CFG.PADDING, nPosY + nHeight / 2 - CFG.TEXT_HEIGHT / 2, new Color(0.84F, 0.84F, 0.88F, 1.0F));
      Renderer.fontMain.get(this.fontID).getData().setScale(0.9F);
      Renderer.drawText(
         oSB, this.fontID, sRight, nPosX + nWidth - CFG.PADDING - (int)(nRightWidth * 0.9F), nPosY + nHeight / 2 - CFG.TEXT_HEIGHT / 2, Color.WHITE
      );
      Renderer.fontMain.get(this.fontID).getData().setScale(1.0F);
   }

   public final void drawColorBoxes(SpriteBatch oSB, int nPosX, int nPosY) {
      for (int i = this.lColorsBoxes.size() - 1; i >= 0; i--) {
         if (this.lColorsBoxes.get(i).getVisible()) {
            oSB.setColor(this.lColors.get(i).r, this.lColors.get(i).g, this.lColors.get(i).b, this.fAlpha);
            Images.pix
               .draw(
                  oSB,
                  nPosX + this.lColorsBoxes.get(i).getPosX(),
                  nPosY + this.lColorsBoxes.get(i).getPosY(),
                  this.lColorsBoxes.get(i).getWidth(),
                  this.lColorsBoxes.get(i).getHeight()
               );
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, this.fAlpha));
            ImageManager.getImage(Images.gradientVertical)
               .draw(
                  oSB,
                  nPosX + this.lColorsBoxes.get(i).getPosX() + this.lColorsBoxes.get(i).getWidth(),
                  nPosY + this.lColorsBoxes.get(i).getPosY(),
                  1,
                  this.lColorsBoxes.get(i).getHeight()
               );
         }
      }
   }

   public final void touchUp() {
      if (this.activeResize) {
         this.iLastSVPosX = (int)((float)(this.iLastSVPosX * this.iSVHeight) / this.iStartResizeHeight);
         this.iLastSVPosY = (int)((float)(this.iLastSVPosY * this.iSVHeight) / this.iStartResizeHeight);
         this.iLastHUEPosY = (int)((float)(this.iLastHUEPosY * this.iSVHeight) / this.iStartResizeHeight);
      } else if (this.activeClose
         && this.iLastSVPosX >= this.getPosX() + this.getWidth() - ImageManager.getImage(Images.btn_close).getWidth()
         && this.iLastSVPosX <= this.getPosX() + this.getWidth()
         && this.iLastSVPosY >= this.iPosY
         && this.iLastSVPosY <= this.iPosY + ImageManager.getImage(Images.btn_close).getHeight()) {
         this.setVisible(false, null);
      }

      this.activeSV = false;
      this.activeHUE = false;
      this.activeResize = false;
      this.activeMove = false;
      this.activeClose = false;
      this.iActiveColorID = -1;
      this.fAlpha = 1.0F;
   }

   public final void touch(int screenX, int screenY) {
      if (this.activeHUE) {
         if (screenY <= this.iPosY) {
            screenY = this.iPosY + 1;
         } else if (screenY > this.iPosY + this.iSVHeight) {
            screenY = this.iPosY + this.iSVHeight;
         }

         if (screenX < this.iPosX + this.iSVHeight + CFG.PADDING) {
            screenX = this.iPosX + this.iSVHeight + CFG.PADDING;
         } else if (screenX > this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth) {
            screenX = this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth;
         }

         this.updateHUE(screenY);
         this.updateSV(this.iPosX + this.iLastSVPosX, this.iPosY + this.iLastSVPosY);
      } else if (this.activeSV) {
         if (screenY < this.iPosY) {
            screenY = this.iPosY;
         } else if (screenY > this.iPosY + this.iSVHeight) {
            screenY = this.iPosY + this.iSVHeight;
         }

         if (screenX < this.iPosX) {
            screenX = this.iPosX;
         } else if (screenX > this.iPosX + this.iSVHeight) {
            screenX = this.iPosX + this.iSVHeight;
         }

         this.updateSV(screenX, screenY);
         this.iLastSVPosX = screenX - this.iPosX;
         this.iLastSVPosY = screenY - this.iPosY;
      } else {
         if (this.activeResize) {
            this.setSVHeight(screenY - this.iPosY - this.iStartPosY);
            return;
         }

         if (this.activeMove) {
            this.setPosX(screenX - this.iStartPosX);
            this.setPosY(screenY - this.iStartPosY);
            this.fAlpha = 0.75F;
            return;
         }

         if (this.iActiveColorID >= 0) {
            for (int i = 0; i < this.lColorsBoxes.size(); i++) {
               if (this.lColorsBoxes.get(i).getVisible()
                  && screenX >= this.iPosX + this.lColorsBoxes.get(i).getPosX()
                  && screenX <= this.iPosX + this.lColorsBoxes.get(i).getPosX() + this.lColorsBoxes.get(i).getWidth()) {
                  this.iActiveColorID = i;
                  if ((int)(activeColor.r * 255.0F) != (int)(this.lColors.get(i).r * 255.0F)
                     && (int)(activeColor.r * 255.0F) != (int)(this.lColors.get(i).g * 255.0F)
                     && (int)(activeColor.r * 255.0F) != (int)(this.lColors.get(i).b * 255.0F)) {
                     this.RGBtoHSV((int)(this.lColors.get(i).r * 255.0F), (int)(this.lColors.get(i).g * 255.0F), (int)(this.lColors.get(i).b * 255.0F));
                  }
                  break;
               }
            }
         } else if (activeRGB >= 0) {
            this.setActiveRGB_Box(screenX, screenY);
            this.showKeyboard();
         } else if (this.activeClose) {
            this.iLastSVPosX = screenX;
            this.iLastSVPosY = screenY;
         } else {
            if (screenX >= this.iPosX + this.iSVHeight - this.iResizeHeight
               && screenX <= this.iPosX + this.iSVHeight
               && screenY >= this.iPosY + this.iSVHeight - this.iResizeHeight
               && screenY <= this.iPosY + this.iSVHeight) {
               this.activeResize = true;
               this.iStartPosY = screenY - this.iPosY - this.iSVHeight;
               this.iStartResizeHeight = this.iSVHeight;
               this.fAlpha = 0.75F;
               return;
            }

            if (screenX >= this.iPosX && screenX <= this.iPosX + this.iResizeHeight && screenY >= this.iPosY && screenY <= this.iPosY + this.iResizeHeight) {
               this.activeMove = true;
               this.iStartPosX = screenX - this.iPosX;
               this.iStartPosY = screenY - this.iPosY;
               return;
            }

            if (screenX >= this.iPosX + this.iSVHeight + CFG.PADDING
               && screenX <= this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth
               && screenY >= this.iPosY
               && screenY <= this.iPosY + this.iSVHeight) {
               this.updateHUE(screenY);
               this.updateSV(this.iPosX + this.iLastSVPosX, this.iPosY + this.iLastSVPosY);
               this.activeHUE = true;
               activeRGB = -1;
               Game.keyboard.hideKeyboard();
            } else if (screenX >= this.iPosX && screenX <= this.iPosX + this.iSVHeight && screenY >= this.iPosY && screenY <= this.iPosY + this.iSVHeight) {
               this.updateSV(screenX, screenY);
               this.activeSV = true;
               this.iLastSVPosX = screenX - this.iPosX;
               this.iLastSVPosY = screenY - this.iPosY;
               activeRGB = -1;
               Game.keyboard.hideKeyboard();
            } else if (screenX >= this.iPosX + this.lColorsBoxes.get(0).getPosX()
               && screenX <= this.iPosX + this.getWidth()
               && screenY >= this.iPosY + this.iSVHeight + this.lColorsBoxes.get(0).getPosY()
               && screenY <= this.iPosY + this.iSVHeight + this.lColorsBoxes.get(0).getPosY() + this.lColorsBoxes.get(0).getHeight()) {
               for (int ix = 0; ix < this.lColorsBoxes.size(); ix++) {
                  if (this.lColorsBoxes.get(ix).getVisible()
                     && screenX >= this.iPosX + this.lColorsBoxes.get(ix).getPosX()
                     && screenX <= this.iPosX + this.lColorsBoxes.get(ix).getPosX() + this.lColorsBoxes.get(ix).getWidth()
                     && screenY >= this.iPosY + this.iSVHeight + this.lColorsBoxes.get(ix).getPosY()
                     && screenY <= this.iPosY + this.iSVHeight + this.lColorsBoxes.get(ix).getPosY() + this.lColorsBoxes.get(ix).getHeight()) {
                     this.iActiveColorID = ix;
                     if ((int)(activeColor.r * 255.0F) != (int)(this.lColors.get(ix).r * 255.0F)
                        && (int)(activeColor.r * 255.0F) != (int)(this.lColors.get(ix).g * 255.0F)
                        && (int)(activeColor.r * 255.0F) != (int)(this.lColors.get(ix).b * 255.0F)) {
                        this.RGBtoHSV((int)(this.lColors.get(ix).r * 255.0F), (int)(this.lColors.get(ix).g * 255.0F), (int)(this.lColors.get(ix).b * 255.0F));
                     }
                     break;
                  }
               }

               activeRGB = -1;
               Game.keyboard.hideKeyboard();
            } else if (screenX >= this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + CFG.PADDING + this.lRGBBoxes.get(0).getPosX()
               && screenX
                  <= this.iPosX
                     + this.iSVHeight
                     + CFG.PADDING
                     + this.iHUEWidth
                     + CFG.PADDING
                     + this.lRGBBoxes.get(0).getPosX()
                     + this.lRGBBoxes.get(0).getWidth()
               && screenY >= this.iPosY + this.lRGBBoxes.get(0).getPosY()
               && screenY <= this.iPosY + this.lRGBBoxes.get(2).getPosY() + this.lRGBBoxes.get(2).getHeight()) {
               this.setActiveRGB_Box(screenX, screenY);
               this.showKeyboard();
            } else if (screenX >= this.getPosX() + this.getWidth() - ImageManager.getImage(Images.btn_close).getWidth()
               && screenX <= this.getPosX() + this.getWidth()
               && screenY >= this.iPosY
               && screenY <= this.iPosY + ImageManager.getImage(Images.btn_close).getHeight()) {
               this.activeClose = true;
            }
         }
      }

      this.ColorPicker_AoC_Action.update();
   }

   private final void showKeyboard() {
      if (activeRGB >= 0) {
         if (Keyboard.keyboardMode) {
            Game.keyboard.hideKeyboard();
         }

         Game.keyboard
            .showKeyboard(
               Keyboard.KeyboardActionType.COLORPICKER,
               activeRGB == 0 ? "" + (int)(activeColor.r * 255.0F) : (activeRGB == 1 ? "" + (int)(activeColor.g * 255.0F) : "" + (int)(activeColor.b * 255.0F))
            );
      }
   }

   private final void setActiveRGB_Box(int screenX, int screenY) {
      for (int i = 0; i < this.lRGBBoxes.size(); i++) {
         if (screenX >= this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + CFG.PADDING + this.lRGBBoxes.get(i).getPosX()
            && screenX
               <= this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + CFG.PADDING + this.lRGBBoxes.get(i).getPosX() + this.lRGBBoxes.get(i).getWidth()
            && screenY >= this.iPosY + this.lRGBBoxes.get(i).getPosY()
            && screenY <= this.iPosY + this.lRGBBoxes.get(i).getPosY() + this.lRGBBoxes.get(i).getHeight()) {
            activeRGB = i;
            return;
         }
      }

      activeRGB = -1;
   }

   private final void updateHUE(int screenY) {
      float perc = 1.0F - (float)(screenY - this.iPosY) / this.iSVHeight;
      this.hsv[0] = this.hueVal = perc * 360.0F;
      this.hsv[1] = this.hsv[2] = 1.0F;
      this.HSVtoRGB(this.hsv, hueColor);
      this.updateSV(this.iLastSVPosX, this.iLastSVPosY);
      this.iLastHUEPosY = screenY - this.iPosY;
   }

   private final void updateSV(int screenX, int screenY) {
      float sat = (float)(screenX - this.iPosX) / this.iSVHeight;
      float val = 1.0F - (float)(screenY - this.iPosY) / this.iSVHeight;
      this.hsv[0] = this.hueVal;
      this.hsv[1] = sat;
      this.hsv[2] = val;
      this.updateColorSVPos(screenY - this.iPosY);
      this.HSVtoRGB(this.hsv, activeColor);
      this.updateRGBWidth();
   }

   private final void updateColorSVPos(int nPosY) {
      if (this.iSVHeight * 0.1F > nPosY) {
         this.colorSVPos = Color.BLACK;
      } else {
         this.colorSVPos = Color.WHITE;
      }
   }

   public final void setActiveRGBColor(float R, float G, float B) {
      this.setActiveRGBColor((int)(R * 255.0F), (int)(G * 255.0F), (int)(B * 255.0F));
   }

   public final void setActiveRGBColor(int R, int G, int B) {
      if (Keyboard.keyboardMode || activeRGB >= 0) {
         activeRGB = -1;
         Game.keyboard.hideKeyboard();
      }

      this.RGBtoHSV(R, G, B);
   }

   public final void RGBtoHSV(int R, int G, int B) {
      float x = Math.min(Math.min(R, G), B);
      float val = Math.max(Math.max(R, G), B);
      if (x == val) {
         this.hsv[0] = 0.0F;
         this.hsv[1] = 0.0F;
      } else {
         float f = R == (int)x ? G - B : (G == (int)x ? B - R : R - G);
         float i = R == (int)x ? 3.0F : (G == (int)x ? 5 : 1);
         this.hsv[0] = (i - f / (val - x)) * 60.0F % 360.0F;
         this.hsv[1] = (val - x) / val;
      }

      this.hsv[2] = val / 255.0F;
      this.hueVal = this.hsv[0];
      this.iLastSVPosX = (int)(this.hsv[1] * this.iSVHeight);
      this.iLastSVPosY = (int)(-this.hsv[2] * this.iSVHeight + this.iSVHeight);
      this.iLastHUEPosY = (int)(this.iSVHeight - this.hsv[0] / 360.0F * this.iSVHeight);
      this.updateSV(this.iPosY + this.iLastSVPosX, this.iPosY + this.iLastSVPosY);
      this.updateHUE(this.iPosY + this.iLastHUEPosY);
      activeColor.r = R / 255.0F;
      activeColor.g = G / 255.0F;
      activeColor.b = B / 255.0F;
      this.updateRGBWidth();
   }

   private final void HSVtoRGB(float[] hsv, Color rgbOut) {
      float h = hsv[0];
      float s = hsv[1];
      float v = hsv[2];
      float r;
      float g;
      float b;
      if (s == 0.0F) {
         b = v;
         g = v;
         r = v;
      } else {
         h /= 60.0F;
         int i = (int)h;
         float f = h - i;
         float p = v * (1.0F - s);
         float q = v * (1.0F - s * f);
         float t = v * (1.0F - s * (1.0F - f));
         switch (i) {
            case 0:
               r = v;
               g = t;
               b = p;
               break;
            case 1:
               r = q;
               g = v;
               b = p;
               break;
            case 2:
               r = p;
               g = v;
               b = t;
               break;
            case 3:
               r = p;
               g = q;
               b = v;
               break;
            case 4:
               r = t;
               g = p;
               b = v;
               break;
            default:
               r = v;
               g = p;
               b = q;
         }
      }

      rgbOut.r = r;
      rgbOut.g = g;
      rgbOut.b = b;
      rgbOut.a = 1.0F;
   }

   public final void updateRGBWidth() {
      this.iRTextWidth = Renderer.getTextWidth("" + (int)(activeColor.r * 255.0F));
      this.iGTextWidth = Renderer.getTextWidth("" + (int)(activeColor.g * 255.0F));
      this.iBTextWidth = Renderer.getTextWidth("" + (int)(activeColor.b * 255.0F));
   }

   public final void setPosX(int iPosX) {
      if (iPosX > CFG.GAME_WIDTH - ImageManager.getImage(Images.pickerSV).getHeight() / 2) {
         iPosX = CFG.GAME_WIDTH - ImageManager.getImage(Images.pickerSV).getHeight() / 2;
      } else if (iPosX < CFG.PADDING * 2) {
         iPosX = CFG.PADDING * 2;
      }

      this.iPosX = iPosX;
   }

   public final int getPosX() {
      return this.iPosX;
   }

   public final void setPosY(int iPosY) {
      if (iPosY > CFG.GAME_HEIGHT - ImageManager.getImage(Images.pickerSV).getHeight() / 2) {
         iPosY = CFG.GAME_HEIGHT - ImageManager.getImage(Images.pickerSV).getHeight() / 2;
      } else if (iPosY < CFG.PADDING * 2) {
         iPosY = CFG.PADDING * 2;
      }

      this.iPosY = iPosY;
   }

   public final int getPosY() {
      return this.iPosY;
   }

   public final int getWidth() {
      return this.iSVHeight + this.iHUEWidth + CFG.PADDING + this.iRGBTextWidth + CFG.PADDING * 3;
   }

   public final int getHeight() {
      return this.iSVHeight + CFG.TEXT_HEIGHT + CFG.PADDING * 3;
   }

   public final Color getActiveColor() {
      return activeColor;
   }

   public final boolean getVisible() {
      return this.visible;
   }

   public final void hideColorPicker() {
      this.visible = false;
      this.ColorPicker_AoC_Action = new ColorPicker.ColorPicker_AoC_Action() {
         @Override
         public void update() {
         }

         @Override
         public void setActiveProvince_Action() {
         }
      };
   }

   public final void setVisible(boolean visible, ColorPicker.PickerAction nAction) {
      if (nAction != null) {
         this.updateColorPicker_Action(nAction);
      } else {
         this.ColorPicker_AoC_Action = new ColorPicker.ColorPicker_AoC_Action() {
            @Override
            public void update() {
            }

            @Override
            public void setActiveProvince_Action() {
            }
         };
      }

      this.visible = visible;
      if (Keyboard.keyboardMode || activeRGB >= 0) {
         activeRGB = -1;
         Game.keyboard.hideKeyboard();
      }
   }

   public ColorPicker.ColorPicker_AoC_Action getColorPickerAction() {
      return this.ColorPicker_AoC_Action;
   }

   public final void setHueWidth(int iHUEWidth) {
      this.iHUEWidth = iHUEWidth;
   }

   public final void setSVHeight(int iSVHeight) {
      if (iSVHeight < ImageManager.getImage(Images.pickerSV).getHeight()) {
         iSVHeight = ImageManager.getImage(Images.pickerSV).getHeight();
      } else if (this.getPosY() + iSVHeight + CFG.TEXT_HEIGHT + CFG.PADDING * 5 > CFG.GAME_HEIGHT) {
         iSVHeight = CFG.GAME_HEIGHT - this.getPosY() - (CFG.TEXT_HEIGHT + CFG.PADDING * 5);
      }

      this.iSVHeight = iSVHeight;

      for (int i = 1; i < this.lColorsBoxes.size(); i++) {
         this.lColorsBoxes.get(i).setWidth(this.lColorsBoxes.get(0).getWidth());
         this.lColorsBoxes.get(i).setVisible(true);
      }

      for (int i = this.lColorsBoxes.size() - 1; i > 0; i--) {
         if (this.lColorsBoxes.get(i).getPosX() > this.getWidth()) {
            this.lColorsBoxes.get(i).setVisible(false);
         } else {
            this.lColorsBoxes.get(i).setVisible(true);
            if (this.lColorsBoxes.get(i).getPosX() + this.lColorsBoxes.get(i).getWidth() > this.getWidth()) {
               this.lColorsBoxes.get(i).setWidth(this.getWidth() - this.lColorsBoxes.get(i).getPosX());
            }
         }
      }
   }

   public final int getColorBoxWidth() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
   }

   public final void setResizeHeight(int iResizeHeight) {
      this.iResizeHeight = iResizeHeight;
   }

   class Box {
      private int iPosX;
      private int iPosY;
      private int iWidth;
      private int iHeight;
      private boolean visible = true;

      public Box(int iPosX, int iPosY, int iWidth, int iHeight) {
         this.iPosX = iPosX;
         this.iPosY = iPosY;
         this.iWidth = iWidth;
         this.iHeight = iHeight;
      }

      public final int getPosX() {
         return this.iPosX;
      }

      public final void setPosX(int iPosX) {
         this.iPosX = iPosX;
      }

      public final int getPosY() {
         return this.iPosY;
      }

      public final void setPosY(int iPosY) {
         this.iPosY = iPosY;
      }

      public final int getWidth() {
         return this.iWidth;
      }

      public final int getHeight() {
         return this.iHeight;
      }

      public final void setWidth(int iWidth) {
         this.iWidth = iWidth;
      }

      public final void setHeight(int iHeight) {
         this.iHeight = iHeight;
      }

      public final void setVisible(boolean visible) {
         this.visible = visible;
      }

      public final boolean getVisible() {
         return this.visible;
      }
   }

   public interface ColorPicker_AoC_Action {
      void update();

      void setActiveProvince_Action();
   }

   public static enum PickerAction {
      NONE_ACTION,
      GAMECIVS_EDIT,
      CREATE_CIV_EDIT,
      CREATE_CIV_DIVISION,
      CREATE_CIV_OVERLAY,
      CIV_COLOR_NEWGAME,
      CIV_COLOR_INGAME;
   }
}
