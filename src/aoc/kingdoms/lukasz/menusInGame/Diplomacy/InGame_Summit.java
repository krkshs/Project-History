package aoc.kingdoms.lukasz.menusInGame.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_ImageSparks;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlue;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Summit extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static String summitName = "";
   public static int provinceID = 0;
   public static List<Integer> invitedCivs = new ArrayList<>();
   public static int activeInviteCiv = 0;

   public InGame_Summit() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title600).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int buttonX = paddingLeft;
      int maxWidth = ImageManager.getImage(Images.summitBig).getWidth() + CFG.PADDING * 4;
      int tempTitlePaddingY = CFG.PADDING;
      int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
      int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
      menuElements.add(
         new ButtonFlag_Diplomacy(
            invitedCivs.get(0),
            menuWidth / 2 - maxWidth / 2 - CFG.PADDING * 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
            buttonY + tempTitlePaddingY,
            true
         )
      );
      menuElements.add(new ButtonFlag_Diplomacy(invitedCivs.get(1), menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2, buttonY + tempTitlePaddingY, true));
      menuElements.add(
         new Text_Static_ID(
            invitedCivs.get(1),
            Game.getCiv(invitedCivs.get(1)).getCivName(),
            CFG.FONT_REGULAR,
            -1,
            menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2 + ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
            buttonY,
            tempTextW,
            tempTitleH
         )
      );
      menuElements.add(
         new Text_Static_ID(invitedCivs.get(0), Game.getCiv(invitedCivs.get(0)).getCivName(), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, tempTextW, tempTitleH)
      );
      int diplomacyWidth = CFG.BUTTON_WIDTH;
      menuElements.add(new TextIcon_Diplomacy(Images.summitBig, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
         @Override
         public Color getColorBar() {
            return DiplomacyManager.COLOR_SUMMIT;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new Text_Desc(Game.lang.get("SummitText"), paddingLeft, buttonY, menuWidth - paddingLeft * 2) {
         @Override
         protected Color getColor(boolean isActive) {
            return Colors.getColorButtonHover2(isActive, this.getIsHovered());
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_TitleBlue(
            Game.lang.get("ListOfInvitedCivilizations"),
            -1,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("ListOfInvitedCivilizations"));
            }

            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;

      for (int i = 0; i < invitedCivs.size(); i++) {
         if (i == 0) {
            menuElements.add(
               new Text_StaticBG_ID_FlagCiv(
                  "" + Game.getCiv(invitedCivs.get(i)).getCivName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  buttonX,
                  buttonY,
                  menuWidth - CFG.PADDING * 5 - diplomacyWidth,
                  buttonH,
                  invitedCivs.get(i)
               ) {
                  @Override
                  public void actionElement() {
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = CivilizationRanking.getHover_CivilizationRanking(this.getCurrent(), false, false);
                  }
               }
            );
            menuElements.add(
               new ButtonStatsRectIMG_Active(
                  "" + CFG.getPrecision2(hostSummitCostDiplomacyPoints(), 100),
                  Images.diplomacy,
                  menuWidth - CFG.PADDING * 2 - diplomacyWidth,
                  buttonY,
                  diplomacyWidth,
                  buttonH,
                  ImageManager.getImage(Images.diplomacy).getWidth(),
                  0
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         } else {
            menuElements.add(
               new Text_StaticBG_ID_FlagCiv(
                  "" + Game.getCiv(invitedCivs.get(i)).getCivName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  buttonX,
                  buttonY,
                  menuWidth - CFG.PADDING * 5 - diplomacyWidth,
                  buttonH,
                  invitedCivs.get(i)
               ) {
                  @Override
                  public void actionElement() {
                     for (int i = 2; i < InGame_Summit.invitedCivs.size(); i++) {
                        if (InGame_Summit.invitedCivs.get(i) == this.getCurrent()) {
                           InGame_Summit.invitedCivs.remove(i);
                           break;
                        }
                     }

                     Game.menuManager.rebuildInGame_Summit_SavePos();
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = CivilizationRanking.getHover_CivilizationRanking(this.getCurrent(), false, false);
                  }
               }
            );
            menuElements.add(
               new ButtonStatsRectIMG_Active(
                  "+" + GameValues.diplomacy.SUMMIT_DIPLOMACY_POINTS_COST_PER_CIVILIZATION,
                  Images.diplomacy,
                  menuWidth - CFG.PADDING * 2 - diplomacyWidth,
                  buttonY,
                  diplomacyWidth,
                  buttonH,
                  ImageManager.getImage(Images.diplomacy).getWidth(),
                  0
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      }

      menuElements.add(
         new Text_TitleBlue(
            Game.lang.get("InviteCivilization"),
            -1,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("InviteCivilization"));
            }

            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (Game.iActiveProvince >= 0
         && Game.getProvince(Game.iActiveProvince).getCivID() > 0
         && !invitedCivs.contains(Game.getProvince(Game.iActiveProvince).getCivID())) {
         activeInviteCiv = Game.getProvince(Game.iActiveProvince).getCivID();
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv(
               (DiplomacyManager.isAtWar(Game.player.iCivID, activeInviteCiv) ? Game.lang.get("AtWar") + ": " : "") + Game.getCiv(activeInviteCiv).getCivName(),
               CFG.FONT_REGULAR_SMALL,
               CFG.PADDING * 2,
               buttonX,
               buttonY,
               menuWidth - CFG.PADDING * 5 - diplomacyWidth,
               buttonH,
               activeInviteCiv
            ) {
               @Override
               public void actionElement() {
                  if (!DiplomacyManager.isAtWar(Game.player.iCivID, this.getCurrent())) {
                     InGame_Summit.invitedCivs.add(this.getCurrent());
                     Game.menuManager.rebuildInGame_Summit_SavePos();
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("InviteCivilization"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.summit, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Active(
               "" + GameValues.diplomacy.SUMMIT_DIPLOMACY_POINTS_COST_PER_CIVILIZATION,
               Images.diplomacy,
               menuWidth - CFG.PADDING * 2 - diplomacyWidth,
               buttonY,
               diplomacyWidth,
               buttonH,
               ImageManager.getImage(Images.diplomacy).getWidth(),
               0
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         menuElements.add(new Text_StaticBG(Game.lang.get("ChooseAProvince"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, buttonH));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int iconWidth = (int)Math.ceil(ImageManager.getImage(Images.gold).getWidth() * 1.5F);
      menuElements.add(
         new TextBonus(
            Game.lang.get("Cost") + ": ",
            "" + CFG.getPrecision2(hostSummitCostDiplomacyPoints(), 100),
            Images.diplomacy,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            iconWidth
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("DiplomacyPoints") + ": ",
                     "" + CFG.getPrecision2(InGame_Summit.hostSummitCostDiplomacyPoints(), 100),
                     Images.diplomacy,
                     CFG.FONT_REGULAR_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new Text_Desc(Game.lang.get("HostDiplomaticSummitDesc"), paddingLeft, buttonY, menuWidth - paddingLeft * 2) {
         @Override
         protected Color getColor(boolean isActive) {
            return Colors.getColorButtonHover2(isActive, this.getIsHovered());
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      paddingLeft += CFG.PADDING;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      menuElements.add(
         new ButtonGame_ImageSparks(
            Game.lang.get("HostDiplomaticSummit"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            true,
            Images.summit
         ) {
            @Override
            public void actionElement() {
               InGame_Summit.confirm();
            }

            @Override
            public boolean getClickable() {
               try {
                  return Game.getCiv(Game.player.iCivID).fDiplomacy >= InGame_Summit.hostSummitCostDiplomacyPoints();
               } catch (Exception var2) {
                  return super.getClickable();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("HostDiplomaticSummit"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.summit, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();

               try {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Location") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        Game.getProvince(InGame_Summit.provinceID).getProvinceName(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.summit, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               } catch (Exception var4) {
               }

               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DiplomacyPoints") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "-" + CFG.getPrecision2(InGame_Summit.hostSummitCostDiplomacyPoints(), 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("HostDiplomaticSummitDesc"), CFG.FONT_REGULAR_SMALL));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public int getSFX() {
               return SoundsManager.DIPLOMACY_CLICK;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
      this.initMenu(
         new MenuTitleIMG(summitName, true, false, Images.title600) {
            @Override
            public long getTime() {
               return InGame_Summit.lTime;
            }
         },
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         Math.min((int)(CFG.GAME_HEIGHT * 0.2F), CFG.GAME_HEIGHT / 2 - (menuHeight + titleHeight) / 2),
         menuWidth,
         menuHeight,
         menuElements,
         false,
         true
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      DiplomacyManager.updateInAnimation();
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop600,
         Images.insideBot600
      );
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.civInfoOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);

      try {
         if (Game.iActiveProvince >= 0
            && Game.getProvince(Game.iActiveProvince).getCivID() > 0
            && Game.getProvince(Game.iActiveProvince).getCivID() != Game.player.iCivID
            && Game.getProvince(Game.iActiveProvince).getCivID() != activeInviteCiv) {
            activeInviteCiv = Game.getProvince(Game.iActiveProvince).getCivID();
            Game.menuManager.rebuildInGame_Summit_SavePos();
         }
      } catch (Exception var7) {
      }
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      DiplomacyManager.updateAnimationTime();
   }

   public static float hostSummitCostDiplomacyPoints() {
      float out = GameValues.diplomacy.SUMMIT_DIPLOMACY_POINTS_COST_BASE;

      for (int i = 1; i < invitedCivs.size(); i++) {
         out += GameValues.diplomacy.SUMMIT_DIPLOMACY_POINTS_COST_PER_CIVILIZATION;
      }

      return out;
   }

   public static void confirm() {
      if (Game.getCiv(Game.player.iCivID).fDiplomacy < hostSummitCostDiplomacyPoints()) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("Cost") + ", " + Game.lang.get("DiplomacyPoints") + ": ",
               CFG.getPrecision2(hostSummitCostDiplomacyPoints(), 100),
               Images.diplomacy
            );
      } else {
         Civilization var10000 = Game.getCiv(Game.player.iCivID);
         var10000.fDiplomacy = var10000.fDiplomacy - hostSummitCostDiplomacyPoints();
         Game.menuManager.setVisibleInGame_PopUp(false);

         try {
            for (int i = 0; i < invitedCivs.size(); i++) {
               for (int j = i + 1; j < invitedCivs.size(); j++) {
                  Game.getCiv(invitedCivs.get(i))
                     .diplomacy
                     .setRelation(
                        invitedCivs.get(i),
                        invitedCivs.get(j),
                        Game.getCiv(invitedCivs.get(i)).diplomacy.getRelation(invitedCivs.get(j))
                           + GameValues.diplomacy.SUMMIT_IMPROVE_RELATIONS_BASE
                           + Game.oR.nextInt(GameValues.diplomacy.SUMMIT_IMPROVE_RELATIONS_RANDOM_100) / 100.0F
                     );
                  Game.getCiv(invitedCivs.get(j))
                     .diplomacy
                     .setRelation(
                        invitedCivs.get(j),
                        invitedCivs.get(i),
                        Game.getCiv(invitedCivs.get(j)).diplomacy.getRelation(invitedCivs.get(i))
                           + GameValues.diplomacy.SUMMIT_IMPROVE_RELATIONS_BASE
                           + Game.oR.nextInt(GameValues.diplomacy.SUMMIT_IMPROVE_RELATIONS_RANDOM_100) / 100.0F
                     );
               }
            }
         } catch (Exception var3) {
         }

         try {
            for (int i = 1; i < invitedCivs.size(); i++) {
               ProvinceDraw.addDiplomacyLines(
                  Game.getCiv(Game.player.iCivID).getCapitalProvinceID(), Game.getCiv(invitedCivs.get(i)).getCapitalProvinceID(), DiplomacyManager.COLOR_SUMMIT
               );
            }
         } catch (Exception var2) {
         }

         InGame_Info.iCivID = invitedCivs.get(1);
         InGame_Info.iCivID2 = Game.player.iCivID;
         Game.menuManager.rebuildInGame_Info(summitName, Game.lang.get("DiplomaticSummit"));
         InGame_Info.imgID = Images.infoDiplomacy;
         invitedCivs.clear();
      }
   }
}
