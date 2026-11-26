package aoc.kingdoms.lukasz.menus.NewGame;

import aoc.kingdoms.lukasz.jakowski.AA_KeyManager;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Ages;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame2;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class NewGame_Settings extends Menu {
   public NewGame_Settings() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuWidth = CFG.LEFT_MENU_WIDTH * 4 / 5;
      int menuX = CFG.PADDING * 2;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 3 + titleHeight;
      int buttonYPadding = CFG.PADDING + CFG.PADDING / 2;
      int buttonY = buttonYPadding;
      menuElements.add(
         new ButtonGame2(Game.lang.get("Fogofwar"), 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, CFG.BUTTON_HEIGHT2, true) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Fogofwar") + ": ",
                     Game.FOG_OF_WAR ? Game.lang.get("On") : Game.lang.get("Off"),
                     Images.settings,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               Game.FOG_OF_WAR = !Game.FOG_OF_WAR;
            }

            @Override
            public boolean getCheckboxState() {
               return Game.FOG_OF_WAR;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2(
            Game.lang.get("Difficulty") + ": " + Game.lang.get(GameValues.difficulty.NAME[Game.difficultyID]),
            1,
            -1,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            true,
            CFG.BUTTON_HEIGHT3
         ) {
            int id = 0;

            @Override
            public void buildElementHover() {
               this.menuElementHover = NewGame_Settings.getHover_Difficulty(Game.difficultyID);
            }

            @Override
            public String getTextToDraw() {
               if (this.id != Game.difficultyID) {
                  this.id = Game.difficultyID;
                  this.setText(Game.lang.get("Difficulty") + ": " + Game.lang.get(GameValues.difficulty.NAME[Game.difficultyID]));
               }

               return super.getTextToDraw();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonGame2("<<", 1, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2, true, CFG.BUTTON_HEIGHT2) {
         @Override
         public void buildElementHover() {
            this.menuElementHover = NewGame_Settings.getHover_Difficulty(Math.max(0, Game.difficultyID - 1));
         }

         @Override
         public void actionElement() {
            Game.difficultyID = Math.max(0, Game.difficultyID - 1);
         }
      });
      menuElements.add(
         new ButtonGame2(
            ">>",
            1,
            -1,
            paddingLeft + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2 + CFG.PADDING,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            true,
            CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public void buildElementHover() {
               this.menuElementHover = NewGame_Settings.getHover_Difficulty(Math.min(GameValues.difficulty.NAME.length - 1, Game.difficultyID + 1));
            }

            @Override
            public void actionElement() {
               Game.difficultyID = Math.min(GameValues.difficulty.NAME.length - 1, Game.difficultyID + 1);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2(
            Game.lang.get("Scenario") + ": " + Game.lang.get("Events"),
            1,
            -1,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            true,
            CFG.BUTTON_HEIGHT2,
            true
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Scenario") + ": " + Game.lang.get("Events") + ": ",
                     Game.SCENARIO_EVENTS ? Game.lang.get("On") : Game.lang.get("Off"),
                     Images.settings,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               Game.SCENARIO_EVENTS = !Game.SCENARIO_EVENTS;
            }

            @Override
            public boolean getCheckboxState() {
               return Game.SCENARIO_EVENTS;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2(
            Game.lang.get("CallAllies") + ": " + Game.lang.get(Game_Ages.getVassals()),
            1,
            -1,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            true,
            CFG.BUTTON_HEIGHT2,
            true
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("CallAllies") + ": " + Game.lang.get(Game_Ages.getVassals()) + ": ",
                     Game.ENABLE_CALL_VASSALS ? Game.lang.get("On") : Game.lang.get("Off"),
                     Images.vassal,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               Game.ENABLE_CALL_VASSALS = !Game.ENABLE_CALL_VASSALS;
            }

            @Override
            public boolean getCheckboxState() {
               return Game.ENABLE_CALL_VASSALS;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2(
            Game.lang.get("AIAggressiveness") + ": " + Game.aiAggressivnes, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, CFG.BUTTON_HEIGHT3
         ) {
            int id = 0;

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("AIAggressiveness"), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Default") + ": ",
                     ""
                        + Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHANCE
                        + " + "
                        + Game.lang.get("Extra")
                        + " / "
                        + Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_RANDOM_MAX,
                     Images.ai,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("AIAggressiveness") + ", " + Game.lang.get("Extra") + ": ",
                     "" + Game.aiAggressivnes,
                     Images.ai,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public String getTextToDraw() {
               if (this.id != Game.aiAggressivnes) {
                  this.id = Game.aiAggressivnes;
                  this.setText(Game.lang.get("AIAggressiveness") + ": " + Game.aiAggressivnes);
               }

               return super.getTextToDraw();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2("<<", 1, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2, true, CFG.BUTTON_HEIGHT2) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(
                     Game.lang.get("AIAggressiveness") + ", " + Game.lang.get("Extra") + ": -" + GameValues.newGame.NEW_GAME_AI_AGGRESSIVENESS_PER_CLICK,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.ai, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     "CTRL: ",
                     "-" + GameValues.newGame.NEW_GAME_AI_AGGRESSIVENESS_PER_CLICK * 10,
                     Images.ai,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Default") + ": ",
                     ""
                        + Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHANCE
                        + " + "
                        + Game.lang.get("Extra")
                        + " / "
                        + Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_RANDOM_MAX,
                     Images.ai,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               if (AA_KeyManager.CTRL_HOLD) {
                  Game.aiAggressivnes = Game.aiAggressivnes - GameValues.newGame.NEW_GAME_AI_AGGRESSIVENESS_PER_CLICK * 10;
               } else {
                  Game.aiAggressivnes = Game.aiAggressivnes - GameValues.newGame.NEW_GAME_AI_AGGRESSIVENESS_PER_CLICK;
               }
            }
         }
      );
      menuElements.add(
         new ButtonGame2(
            ">>",
            1,
            -1,
            paddingLeft + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2 + CFG.PADDING,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            true,
            CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(
                     Game.lang.get("AIAggressiveness") + ", " + Game.lang.get("Extra") + ": +" + GameValues.newGame.NEW_GAME_AI_AGGRESSIVENESS_PER_CLICK,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.ai, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     "CTRL: ",
                     "+" + GameValues.newGame.NEW_GAME_AI_AGGRESSIVENESS_PER_CLICK * 10,
                     Images.ai,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Default") + ": ",
                     ""
                        + Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHANCE
                        + " + "
                        + Game.lang.get("Extra")
                        + " / "
                        + Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_RANDOM_MAX,
                     Images.ai,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               if (AA_KeyManager.CTRL_HOLD) {
                  Game.aiAggressivnes = Game.aiAggressivnes + GameValues.newGame.NEW_GAME_AI_AGGRESSIVENESS_PER_CLICK * 10;
               } else {
                  Game.aiAggressivnes = Game.aiAggressivnes + GameValues.newGame.NEW_GAME_AI_AGGRESSIVENESS_PER_CLICK;
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2(Game.lang.get("SpectatorMode"), 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, CFG.BUTTON_HEIGHT2, true) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("SpectatorMode") + ": ",
                     Game.SPECTATOR_MODE ? Game.lang.get("On") : Game.lang.get("Off"),
                     Images.settings,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               Game.SPECTATOR_MODE = !Game.SPECTATOR_MODE;
            }

            @Override
            public boolean getCheckboxState() {
               return Game.SPECTATOR_MODE;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2(Game.lang.get("AgeOfChaos"), 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, CFG.BUTTON_HEIGHT2, true) {
            int id = 0;

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("AgeOfChaos") + ": " + Game.lang.get("YearsX", Game.AGE_OF_CHAOS_YEARS) + ": ",
                     Game.AGE_OF_CHAOS_MODE ? Game.lang.get("On") : Game.lang.get("Off"),
                     Images.settings,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos1")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos2", Game.AGE_OF_CHAOS_YEARS)));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos3")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos4")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public String getTextToDraw() {
               if (this.id != Game.AGE_OF_CHAOS_YEARS) {
                  this.id = Game.AGE_OF_CHAOS_YEARS;
                  this.setText(Game.lang.get("AgeOfChaos") + ": " + Game.lang.get("YearsX", Game.AGE_OF_CHAOS_YEARS));
               }

               return super.getTextToDraw();
            }

            @Override
            public void actionElement() {
               Game.AGE_OF_CHAOS_MODE = !Game.AGE_OF_CHAOS_MODE;
            }

            @Override
            public boolean getCheckboxState() {
               return Game.AGE_OF_CHAOS_MODE;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2("<<", 1, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2, true, CFG.BUTTON_HEIGHT2) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("AgeOfChaos") + ": -",
                     Game.lang.get("YearsX", 1),
                     Images.settings,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos1")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos2", Game.AGE_OF_CHAOS_YEARS)));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos3")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos4")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               if (AA_KeyManager.CTRL_HOLD) {
                  Game.AGE_OF_CHAOS_YEARS -= 5;
               } else {
                  Game.AGE_OF_CHAOS_YEARS--;
               }

               Game.AGE_OF_CHAOS_YEARS = Math.max(1, Game.AGE_OF_CHAOS_YEARS);
            }
         }
      );
      menuElements.add(
         new ButtonGame2(
            ">>",
            1,
            -1,
            paddingLeft + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2 + CFG.PADDING,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            true,
            CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("AgeOfChaos") + ": +",
                     Game.lang.get("YearsX", 1),
                     Images.settings,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos1")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos2", Game.AGE_OF_CHAOS_YEARS)));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos3")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AgeOfChaos4")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               if (AA_KeyManager.CTRL_HOLD) {
                  Game.AGE_OF_CHAOS_YEARS += 5;
               } else {
                  Game.AGE_OF_CHAOS_YEARS++;
               }

               Game.AGE_OF_CHAOS_YEARS = Math.max(1, Game.AGE_OF_CHAOS_YEARS);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2(Game.lang.get("Sandbox"), 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, CFG.BUTTON_HEIGHT2, true) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Sandbox") + ": ",
                     Game.SANDBOX ? Game.lang.get("On") : Game.lang.get("Off"),
                     Images.sandbox,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               Game.SANDBOX = !Game.SANDBOX;
            }

            @Override
            public boolean getCheckboxState() {
               return Game.SANDBOX;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame2(Game.lang.get("Peace Treaties: From Anyone"), 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, CFG.BUTTON_HEIGHT2, true) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Take provinces from anyone in peace treaties") + ": ",
                     GameValues.peace.PEACE_TAKE_PROVINCES_FROM_ALLIES ? Game.lang.get("On") : Game.lang.get("Off"),
                     Images.settings,
                     CFG.FONT_BOLD_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               GameValues.peace.PEACE_TAKE_PROVINCES_FROM_ALLIES = !GameValues.peace.PEACE_TAKE_PROVINCES_FROM_ALLIES;
            }

            @Override
            public boolean getCheckboxState() {
               return GameValues.peace.PEACE_TAKE_PROVINCES_FROM_ALLIES;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      this.initMenu(
         new MenuTitle(Game.lang.get("Settings"), 1.0F, titleHeight, true, true),
         menuX,
         menuY,
         menuWidth,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         false,
         false
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawEditorMenuBG(oSB, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight() + CFG.PADDING, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public static MenuElement_Hover getHover_Difficulty(int id) {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Difficulty") + ": ", CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear("" + Game.lang.get(GameValues.difficulty.NAME[id]), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.world, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Player") + ": ",
            "" + Game.lang.get("Bonuses"),
            Images.population,
            CFG.FONT_BOLD_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      float value = GameValues.difficulty.MONTHLY_INCOME[id];
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("MonthlyIncome") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100),
            Images.gold,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value > 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      value = GameValues.difficulty.INCOME_PRODUCTION[id];
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("IncomeProduction") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100) + "%",
            Images.goods,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value > 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      value = GameValues.difficulty.LEGACY[id] * 100.0F;
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Legacy") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100) + "%",
            Images.legacy,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value > 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      value = GameValues.difficulty.MANPOWER[id] * 100.0F;
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Manpower") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100) + "%",
            Game_Calendar.IMG_MANPOWER,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value > 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      value = GameValues.difficulty.RECRUIT_ARMY_COST[id];
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("ArmyRecruitmentCost") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100) + "%",
            Game_Calendar.IMG_MANPOWER,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value < 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      value = GameValues.difficulty.RECRUIT_ARMY_COST[id];
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("RecruitmentTime") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100) + "%",
            Game_Calendar.IMG_MANPOWER_TIME,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value < 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      value = GameValues.difficulty.REGIMENTS_LIMIT[id];
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("RegimentsLimit") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100),
            Images.regimentsLimit,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value > 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      value = GameValues.difficulty.CONSTRUCTION_COST[id] * 100.0F;
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("ConstructionCost") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100) + "%",
            Images.construction,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value < 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      value = GameValues.difficulty.CORE_COST[id];
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("CoreConstruction") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100) + "%",
            Images.core,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value < 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      value = GameValues.difficulty.RELIGION_COST[id];
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("ReligionConversionCost") + ": ",
            (value > 0.0F ? "+" : "") + CFG.getPrecision2(value, 100) + "%",
            Images.religion,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            value == 0.0F ? Colors.HOVER_NEUTRAL : (value < 0.0F ? Colors.HOVER_POSITIVE : Colors.HOVER_NEGATIVE)
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game_Ages.getVassals() + ": ",
            Game.lang.get("ProclaimIndependence"),
            GameValues.difficulty.CAN_VASSAL_PROCLAIM_INDEPENDENCE_AGAINST_PLAYER[id] ? Images.v : Images.x,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("FormACoalitionAgainst") + ": ",
            Game.lang.get("Player"),
            id > GameValues.difficulty.NORMAL_ID ? Images.v : Images.x,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      return new MenuElement_Hover(nElements);
   }
}
