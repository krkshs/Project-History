package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.events.EventsManager;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Title;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Text_StaticBG_Mission extends Text_StaticBG_ID {
   public int missionImage = 0;
   public int eventType = 0;

   public Text_StaticBG_Mission(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int eventType, int id, int missionImage) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, EventsManager.missionImages.get(missionImage).getHeight(), id);
      this.eventType = eventType;
      this.missionImage = missionImage;
   }

   public void drawBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      this.drawBG(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r,
            Colors.COLOR_GRADIENT_BG_BLUE.g,
            Colors.COLOR_GRADIENT_BG_BLUE.b,
            !this.getIsHovered() && !isActive ? 0.35F : 0.65F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);

      try {
         oSB.setColor(Color.WHITE);
         EventsManager.missionImages
            .get(this.missionImage)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - EventsManager.missionImages.get(this.missionImage).getWidth() + iTranslateX,
               this.getPosY() + iTranslateY
            );
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.85F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStats4(isActive, this.getIsHovered());
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(
         new MenuElement_HoverElement_Type_Title(Images.missions, "", this.getText(), Colors.HOVER_GOLD, Game.lang.get("Mission"), "", Colors.HOVER_RIGHT)
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      String sInner = "- ";
      boolean addType = true;
      if (EventsManager.getActiveEvent(this.id, this.eventType).mission_desc.length() > 0) {
         nData.add(
            new MenuElement_HoverElement_Type_Text_Desc(
               Game.lang.get(EventsManager.getActiveEvent(this.id, this.eventType).mission_desc), CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT2
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      try {
         for (int i = 0; i < EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.size(); i++) {
            for (int j = 0; j < EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAnd.size(); j++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAnd.get(j).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAnd.get(j).getText(), CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAnd.get(j).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAnd.get(j).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAnd.get(j).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAnd.get(j).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("and") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jx = 0; jx < EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOr.size(); jx++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOr.get(jx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOr.get(jx).getText(), CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOr.get(jx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOr.get(jx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOr.get(jx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOr.get(jx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("or") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxx = 0; jxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAndNot.size(); jxx++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAndNot.get(jxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAndNot.get(jxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAndNot.get(jxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAndNot.get(jxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAndNot.get(jxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersAndNot.get(jxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("andNot") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxx = 0; jxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOrNot.size(); jxxx++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOrNot.get(jxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOrNot.get(jxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOrNot.get(jxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOrNot.get(jxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOrNot.get(jxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersAnd.get(i).triggersOrNot.get(jxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("orNot") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }
         }

         addType = true;

         for (int i = 0; i < EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.size(); i++) {
            for (int jxxxx = 0; jxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAnd.size(); jxxxx++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAnd.get(jxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("andNot"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAnd.get(jxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAnd.get(jxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAnd.get(jxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAnd.get(jxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAnd.get(jxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("and") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxxxx = 0; jxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOr.size(); jxxxxx++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOr.get(jxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("andNot"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOr.get(jxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOr.get(jxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOr.get(jxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOr.get(jxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOr.get(jxxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("or") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxxxxx = 0; jxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAndNot.size(); jxxxxxx++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAndNot.get(jxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("andNot"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAndNot.get(jxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAndNot.get(jxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAndNot.get(jxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAndNot.get(jxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersAndNot.get(jxxxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("andNot") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxxxxxx = 0; jxxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOrNot.size(); jxxxxxxx++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOrNot.get(jxxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("andNot"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOrNot.get(jxxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOrNot.get(jxxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOrNot.get(jxxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOrNot.get(jxxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersAndNot.get(i).triggersOrNot.get(jxxxxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("orNot") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }
         }

         addType = true;

         for (int i = 0; i < EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.size(); i++) {
            for (int jxxxxxxxx = 0; jxxxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAnd.size(); jxxxxxxxx++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAnd.get(jxxxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("or"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAnd.get(jxxxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAnd.get(jxxxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAnd.get(jxxxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAnd.get(jxxxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAnd.get(jxxxxxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("and") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxxxxxxxx = 0; jxxxxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOr.size(); jxxxxxxxxx++) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOr.get(jxxxxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("or"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOr.get(jxxxxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOr.get(jxxxxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOr.get(jxxxxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOr.get(jxxxxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOr.get(jxxxxxxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("or") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxxxxxxxxx = 0;
               jxxxxxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAndNot.size();
               jxxxxxxxxxx++
            ) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAndNot.get(jxxxxxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("or"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAndNot.get(jxxxxxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAndNot.get(jxxxxxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAndNot.get(jxxxxxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAndNot.get(jxxxxxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersAndNot.get(jxxxxxxxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("andNot") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxxxxxxxxxx = 0;
               jxxxxxxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOrNot.size();
               jxxxxxxxxxxx++
            ) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOrNot.get(jxxxxxxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("or"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOrNot.get(jxxxxxxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOrNot.get(jxxxxxxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOrNot.get(jxxxxxxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOrNot.get(jxxxxxxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersOr.get(i).triggersOrNot.get(jxxxxxxxxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("orNot") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }
         }

         addType = true;

         for (int i = 0; i < EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.size(); i++) {
            for (int jxxxxxxxxxxxx = 0;
               jxxxxxxxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAnd.size();
               jxxxxxxxxxxxx++
            ) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAnd.get(jxxxxxxxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("orNot"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAnd.get(jxxxxxxxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAnd.get(jxxxxxxxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAnd.get(jxxxxxxxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAnd.get(jxxxxxxxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAnd.get(jxxxxxxxxxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("and") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxxxxxxxxxxxx = 0;
               jxxxxxxxxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOr.size();
               jxxxxxxxxxxxxx++
            ) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOr.get(jxxxxxxxxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("orNot"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOr.get(jxxxxxxxxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOr.get(jxxxxxxxxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOr.get(jxxxxxxxxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOr.get(jxxxxxxxxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOr.get(jxxxxxxxxxxxxx).getImage(), CFG.PADDING, 0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("or") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxxxxxxxxxxxxx = 0;
               jxxxxxxxxxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAndNot.size();
               jxxxxxxxxxxxxxx++
            ) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAndNot.get(jxxxxxxxxxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("orNot"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAndNot.get(jxxxxxxxxxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAndNot.get(jxxxxxxxxxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAndNot.get(jxxxxxxxxxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAndNot.get(jxxxxxxxxxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersAndNot.get(jxxxxxxxxxxxxxx).getImage(),
                           CFG.PADDING,
                           0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("andNot") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }

            for (int jxxxxxxxxxxxxxxx = 0;
               jxxxxxxxxxxxxxxx < EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOrNot.size();
               jxxxxxxxxxxxxxxx++
            ) {
               if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOrNot.get(jxxxxxxxxxxxxxxx).getText().length() > 0) {
                  if (addType) {
                     addType = false;
                     nData.add(new MenuElement_HoverElement_Type_Text("" + Game.lang.get("orNot"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        sInner + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOrNot.get(jxxxxxxxxxxxxxxx).getText(),
                        CFG.FONT_REGULAR_SMALL
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOrNot.get(jxxxxxxxxxxxxxxx).getText2(),
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOrNot.get(jxxxxxxxxxxxxxxx).getText3(),
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT2
                     )
                  );
                  if (EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOrNot.get(jxxxxxxxxxxxxxxx).getImage() > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Image(
                           EventsManager.getActiveEvent(this.id, this.eventType).triggersOrNot.get(i).triggersOrNot.get(jxxxxxxxxxxxxxxx).getImage(),
                           CFG.PADDING,
                           0
                        )
                     );
                  }

                  nData.add(new MenuElement_HoverElement_Type_Text(" (" + Game.lang.get("orNot") + ")", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   public int getValue1() {
      return this.eventType;
   }
}
