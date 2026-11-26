package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menusInGame.InGame_War;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitleIMG_War extends MenuTitle {
   public int imageID;
   public String sTextLeft;
   public int iTextLeftWidth = 0;
   public String sTextRight;
   public int iTextRightWidth = 0;
   public String sTextLeft2;
   public int iTextLeftWidth2 = 0;
   public String sTextRight2;
   public int iTextRightWidth2 = 0;
   public String sText2;
   public int iText2Width = 0;
   public int iLastTurnID = 0;

   public MenuTitleIMG_War(
      String sText, String sTextLeft, String sTextRight, String sTextLeft2, String sTextRight2, boolean movable, boolean resizable, int imageID
   ) {
      super(sText, ImageManager.getImage(imageID).getHeight(), movable, resizable);
      this.imageID = imageID;
      this.sTextLeft = sTextLeft;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sTextLeft);
      this.iTextLeftWidth = (int)glyphLayout.width;
      this.sTextRight = sTextRight;
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sTextRight);
      this.iTextRightWidth = (int)glyphLayout.width;
      this.sTextLeft2 = sTextLeft2;
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD_SMALL), sTextLeft2);
      this.iTextLeftWidth2 = (int)glyphLayout.width;
      this.sTextRight2 = sTextRight2;
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD_SMALL), sTextRight2);
      this.iTextRightWidth2 = (int)glyphLayout.width;

      try {
         this.sText2 = Game_Calendar.getNumOfDates_ByTurnID(WarManager.lWars.get(InGame_War.key).iWarTurnID);
         glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sText2);
         this.iText2Width = (int)glyphLayout.width;
      } catch (Exception var11) {
         Game.menuManager.setVisibleInGame_War(false);
      }

      this.iLastTurnID = Game_Calendar.TURN_ID;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      ImageManager.getImage(this.imageID).draw(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
      this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
      this.drawText(oSB, nPosX, nPosY, nWidth, titleStatus);
   }

   @Override
   public void drawText(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      if (this.iLastTurnID != Game_Calendar.TURN_ID) {
         try {
            if (WarManager.lWars.containsKey(InGame_War.key)) {
               this.sText2 = Game_Calendar.getNumOfDates_ByTurnID(WarManager.lWars.get(InGame_War.key).iWarTurnID);
               Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sText2);
               this.iText2Width = (int)Renderer.glyphLayout.width;
               this.iLastTurnID = Game_Calendar.TURN_ID;
            }
         } catch (Exception var7) {
            if (!WarManager.lWars.containsKey(InGame_War.key)) {
               Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Wars") {
                  @Override
                  public void update() {
                     Game.menuManager.rebuildInGame_Wars();
                  }
               });
            }

            CFG.exceptionStack(var7);
         }
      }

      Renderer.drawText(
         oSB,
         this.fontID,
         this.getText(),
         nPosX + nWidth / 2 - this.getTextWidth() / 2,
         1 + nPosY - this.getHeight() + (this.getHeight() / 2 - this.getTextHeight()),
         this.getColorText(titleStatus)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sText2,
         nPosX + nWidth / 2 - this.iText2Width / 2,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING,
         MenuTitle.colorHovered
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_BOLD_SMALL,
         this.sTextLeft2,
         nPosX + CFG.PADDING + ImageManager.getImage(Images.rulerFrame).getWidth() / 2 - this.iTextLeftWidth2 / 2,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING,
         MenuTitle.colorDefault
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_BOLD_SMALL,
         this.sTextRight2,
         nPosX + nWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING - ImageManager.getImage(Images.rulerFrame).getWidth() / 2 - this.iTextRightWidth2 / 2,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING,
         MenuTitle.colorDefault
      );
      if (titleStatus == Status.HOVERED || titleStatus == Status.ACTIVE) {
         Renderer.drawText(
            oSB,
            CFG.FONT_REGULAR_SMALL,
            this.sTextLeft,
            nPosX + CFG.PADDING + ImageManager.getImage(Images.rulerFrame).getWidth() / 2 - this.iTextLeftWidth / 2,
            1 + nPosY - this.getHeight() + (this.getHeight() / 2 - this.getTextHeight()),
            MenuTitle.colorDefault
         );
         Renderer.drawText(
            oSB,
            CFG.FONT_REGULAR_SMALL,
            this.sTextRight,
            nPosX + nWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING - ImageManager.getImage(Images.rulerFrame).getWidth() / 2 - this.iTextRightWidth / 2,
            1 + nPosY - this.getHeight() + (this.getHeight() / 2 - this.getTextHeight()),
            MenuTitle.colorDefault
         );
      }
   }
}
