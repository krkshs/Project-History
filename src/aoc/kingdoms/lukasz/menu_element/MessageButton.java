package aoc.kingdoms.lukasz.menu_element;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessage;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MessageButton extends Button {
   public static final int ANIMATION_TIME = 350;
   public long lTime = 0L;
   public static int messageMaskY;
   private int iCivID;
   public String key;
   public int imageID;
   public int expiresTurnID;
   public int imgW;
   public int imgH;

   public MessageButton(String key, int iCivID, int imageID, int expiresTurnID, long time, int iPosX, int iPosY, boolean isClickable) {
      this.iCivID = iCivID;
      this.key = key;
      this.imageID = imageID;
      this.expiresTurnID = expiresTurnID;
      this.imgW = (int)(ImageManager.getImage(imageID).getWidth() * 0.75F);
      this.imgH = (int)(ImageManager.getImage(imageID).getHeight() * 0.75F);
      this.lTime = time;
      this.init(
         "",
         this.fontID,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.messageBG).getWidth() + this.getExtraWidth(),
         ImageManager.getImage(Images.messageBG).getHeight(),
         isClickable,
         true,
         false,
         false
      );
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.lTime + 350L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - this.getHeight() + (int)(this.getHeight() * ((float)(CFG.currentTimeMillis - this.lTime) / 350.0F));
      }

      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 1.0F));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(
            oSB,
            this.getPosX() + ImageManager.getImage(Images.messageBG).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() - ImageManager.getImage(Images.messageBG).getWidth() / 2,
            this.getHeight()
         );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.5F));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(
            oSB,
            this.getPosX() + ImageManager.getImage(Images.messageBG).getWidth() / 2 + iTranslateX,
            this.getPosY() + CFG.PADDING + iTranslateY,
            this.getWidth() - ImageManager.getImage(Images.messageBG).getWidth() / 2,
            this.getHeight() - CFG.PADDING * 2
         );
      if (this.getIsHovered()) {
         ImageManager.getImage(Images.gradientXYVertical)
            .draw(
               oSB,
               this.getPosX() + ImageManager.getImage(Images.messageBG).getWidth() / 2 + iTranslateX,
               this.getPosY() + CFG.PADDING + iTranslateY,
               this.getWidth() - ImageManager.getImage(Images.messageBG).getWidth() / 2,
               this.getHeight() - CFG.PADDING * 2
            );
      }

      oSB.setColor(Color.WHITE);
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + ImageManager.getImage(Images.messageBG).getWidth() + this.getExtraWidth() / 2 - this.imgW / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.imgH / 2 + iTranslateY,
            this.imgW,
            this.imgH
         );
      ImageManager.getImage(Images.messageBG).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      oSB.setShader(Renderer.shaderAlpha);
      Game.getCiv(this.iCivID).getFlag().getTexture().bind(1);
      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.messageMask)
         .draw(
            oSB,
            this.getPosX() + ImageManager.getImage(Images.messageBG).getWidth() / 2 - ImageManager.getImage(Images.messageMask).getWidth() / 2 + iTranslateX,
            this.getPosY()
               + messageMaskY
               + ImageManager.getImage(Images.messageBG).getHeight() / 2
               - ImageManager.getImage(Images.messageMask).getHeight() / 2
               + iTranslateY
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.messageOver).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      if (this.getIsHovered()) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
         ImageManager.getImage(Images.messageOver).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      }

      oSB.setColor(Color.WHITE);
   }

   public int getExtraWidth() {
      return this.imgW + CFG.PADDING;
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      if (isHovered) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = this.iCivID;
      }
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = Game.player.getMessage_Hover(this.key);
   }

   @Override
   public void actionElement() {
      PMessage pMessage = Game.player.getMessage(this.key);
      if (pMessage != null) {
         pMessage.actionClick();
      } else {
         Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_MessagesSavePos") {
            @Override
            public void update() {
               Game.menuManager.rebuildInGame_MessagesSavePos();
            }
         });
      }
   }

   @Override
   public void actionElementPPM() {
      PMessage pMessage = Game.player.getMessage(this.key);
      if (pMessage != null) {
         pMessage.actionClick();
      } else {
         Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_MessagesSavePos") {
            @Override
            public void update() {
               Game.menuManager.rebuildInGame_MessagesSavePos();
            }
         });
      }
   }
}
