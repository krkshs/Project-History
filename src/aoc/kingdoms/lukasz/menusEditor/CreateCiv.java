package aoc.kingdoms.lukasz.menusEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.menu.ColorPicker;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import java.util.ArrayList;
import java.util.List;

public class CreateCiv extends Menu {
   public static Game.LoadCivilizationData nCiv = new Game.LoadCivilizationData();
   private final String sCivTAG = "Civilization TAG";
   private String sCivName = "";
   public static View goBackTo = View.EDITOR;
   public static boolean saveFlag = false;

   public CreateCiv() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING * 2;
      int textPosX = CFG.PADDING * 4;
      this.sCivName = Game.lang.get("Name");
      if (nCiv.Name == null) {
         nCiv.Name = "";
      }

      if (nCiv.Tag == null) {
         nCiv.Tag = "" + CFG.extraRandomTag2();
      }

      nCiv.iR = Game.oR.nextInt(255);
      nCiv.iG = Game.oR.nextInt(255);
      nCiv.iB = Game.oR.nextInt(255);
      int buttonY = buttonYPadding + 100 + buttonYPadding;
      menuElements.add(
         new ButtonMain(nCiv.Tag, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
            @Override
            public String getTextToDraw() {
               return "Civilization TAG: "
                  + CreateCiv.nCiv.Tag
                  + (Keyboard.keyboardActionType == Keyboard.KeyboardActionType.CREATE_CIV_EDIT ? Keyboard.getKeyboardVerticalLine() : "");
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.CREATE_CIV_EDIT, CreateCiv.nCiv.Tag);
            }

            @Override
            public int getButtonBG() {
               return Keyboard.keyboardMode && Keyboard.keyboardActionType == Keyboard.KeyboardActionType.CREATE_CIV_EDIT
                  ? super.getButtonBG_Active()
                  : super.getButtonBG();
            }

            @Override
            public int getButtonBG_Active() {
               return Keyboard.keyboardMode && Keyboard.keyboardActionType == Keyboard.KeyboardActionType.CREATE_CIV_EDIT
                  ? super.getButtonBG()
                  : super.getButtonBG_Active();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     "CIV_TAG: ", CreateCiv.nCiv.Tag, Images.council, CFG.FONT_REGULAR, CFG.FONT_REGULAR, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text("Must be unique! No spaces and special characters!", CFG.FONT_REGULAR, Colors.HOVER_RIGHT));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonMain(nCiv.Name, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
            @Override
            public String getTextToDraw() {
               return CreateCiv.this.sCivName
                  + ": "
                  + CreateCiv.nCiv.Name
                  + (Keyboard.keyboardActionType == Keyboard.KeyboardActionType.CREATE_CIV_EDIT_NAME ? Keyboard.getKeyboardVerticalLine() : "");
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.CREATE_CIV_EDIT_NAME, CreateCiv.nCiv.Name);
            }

            @Override
            public int getButtonBG() {
               return Keyboard.keyboardMode && Keyboard.keyboardActionType == Keyboard.KeyboardActionType.CREATE_CIV_EDIT_NAME
                  ? super.getButtonBG_Active()
                  : super.getButtonBG();
            }

            @Override
            public int getButtonBG_Active() {
               return Keyboard.keyboardMode && Keyboard.keyboardActionType == Keyboard.KeyboardActionType.CREATE_CIV_EDIT_NAME
                  ? super.getButtonBG()
                  : super.getButtonBG_Active();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(Game.lang.get("CustomizeFlag"), 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            if (Game.menuManager.createCivFlag().getVisible()) {
               Game.menuManager.createCivFlag().setVisible(false);
            } else {
               Game.menuManager.createCivReligion().setVisible(false);
               Game.menuManager.createCivGroup().setVisible(false);
               Game.menuManager.createCivFlag().setVisible(false);
               Game.menuManager.rebuildCreateCivFlag();
               Game.menuManager.createCivFlag().setPosX(CreateCiv.this.getPosX() + CreateCiv.this.getWidth() + CFG.PADDING * 4);
            }
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonMain(null, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("CivilizationColor"));
            }

            @Override
            public void actionElement() {
               if (Game.menuManager.getColorPicker().getVisible()) {
                  Game.menuManager.getColorPicker().hideColorPicker();
               } else {
                  Game.menuManager.getColorPicker().setActiveRGBColor(CreateCiv.nCiv.iR / 255.0F, CreateCiv.nCiv.iG / 255.0F, CreateCiv.nCiv.iB / 255.0F);
                  Game.menuManager.getColorPicker().setVisible(true, ColorPicker.PickerAction.CREATE_CIV_EDIT);
                  Game.menuManager.getColorPicker().setPosX(CreateCiv.this.getPosX() + CreateCiv.this.getWidth() + CFG.PADDING * 4);
                  Game.menuManager.getColorPicker().setPosY(this.getPosY());
               }
            }

            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               oSB.setColor(new Color(CreateCiv.nCiv.iR / 255.0F, CreateCiv.nCiv.iG / 255.0F, CreateCiv.nCiv.iB / 255.0F, 1.0F));
               Images.pix
                  .draw(
                     oSB,
                     iTranslateX + this.getPosX() + this.getWidth() - CFG.CIV_FLAG_WIDTH - CFG.PADDING * 2,
                     iTranslateY + this.getPosY() + this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2,
                     CFG.CIV_FLAG_WIDTH,
                     CFG.CIV_FLAG_HEIGHT
                  );
               oSB.setColor(Color.WHITE);
               super.drawText(oSB, iTranslateX, iTranslateY, isActive);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Religion") + ": ");
         }

         @Override
         public String getTextToDraw() {
            return super.getTextToDraw() + Game.religionManager.getReligion(CreateCiv.nCiv.ReligionID).Name;
         }

         @Override
         public void actionElement() {
            if (Game.menuManager.createCivReligion().getVisible()) {
               Game.menuManager.createCivReligion().setVisible(false);
            } else {
               Game.menuManager.createCivGroup().setVisible(false);
               Game.menuManager.createCivFlag().setVisible(false);
               Game.menuManager.createCivReligion().setVisible(!Game.menuManager.createCivReligion().getVisible());
               Game.menuManager.createCivReligion().setPosX(CreateCiv.this.getPosX() + CreateCiv.this.getWidth() + CFG.PADDING * 4);
            }
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Group") + ": ");
         }

         @Override
         public String getTextToDraw() {
            return super.getTextToDraw() + RulersManager.groups.get(CreateCiv.nCiv.GroupID);
         }

         @Override
         public void actionElement() {
            if (Game.menuManager.createCivGroup().getVisible()) {
               Game.menuManager.createCivGroup().setVisible(false);
            } else {
               Game.menuManager.createCivReligion().setVisible(false);
               Game.menuManager.createCivFlag().setVisible(false);
               Game.menuManager.createCivGroup().setVisible(!Game.menuManager.createCivGroup().getVisible());
               Game.menuManager.createCivGroup().setPosX(CreateCiv.this.getPosX() + CreateCiv.this.getWidth() + CFG.PADDING * 4);
            }
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Save"));
         }

         @Override
         public void actionElement() {
            CreateCiv.this.save();
            CreateCiv.saveFlag = true;
         }

         @Override
         public boolean getClickable() {
            return CreateCiv.nCiv.Name.length() > 0 && super.getClickable();
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(CreateCiv.goBackTo);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH,
         CFG.GAME_HEIGHT - titleHeight - menuY - CFG.PADDING * 2,
         menuElements,
         true
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
      Game.flagManager.drawFlag(oSB, this.getPosX() + this.getWidth() / 2 - 77 + iTranslateX, this.getPosY() + CFG.PADDING * 2 + iTranslateY);
   }

   @Override
   public void drawTitle(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (saveFlag) {
         Game.flagManager.saveFlagTexture(oSB);
         Game.menuManager.setViewIDWithoutAnimation(goBackTo);
      } else {
         super.drawTitle(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      }
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("CreateNewCivilization"));
   }

   @Override
   public void actionElement(int nMenuElementID) {
      if (Game.menuManager.getColorPicker().getVisible() && nMenuElementID != 1) {
         Game.menuManager.getColorPicker().hideColorPicker();
      }

      if (Game.menuManager.createCivReligion().getVisible() && nMenuElementID != 2) {
         Game.menuManager.createCivReligion().setVisible(false);
      }

      if (Game.menuManager.createCivGroup().getVisible() && nMenuElementID != 3) {
         Game.menuManager.createCivGroup().setVisible(false);
      }

      super.actionElement(nMenuElementID);
   }

   public final void save() {
      Json json = new Json();
      json.setTypeName(null);
      json.setUsePrototypes(false);
      json.setIgnoreUnknownFields(true);
      json.setOutputType(OutputType.json);
      if (CFG.isDesktop()) {
         FileHandle file = FileManager.getSaveType("mods/GameCivs/game/civilizations/" + nCiv.Tag + ".json");
         file.writeString(json.prettyPrint(nCiv), false);
      } else {
         FileHandle file = Gdx.files.local("game/civilizations/" + nCiv.Tag + ".json");
         file.writeString(json.prettyPrint(nCiv), false);
      }

      this.saveCivsList();
   }

   private final void saveCivsList() {
      try {
         FileHandle file;
         if (FileManager.IS_MAC && Gdx.files.external("game/Civilizations.txt").exists()) {
            file = Gdx.files.external("game/Civilizations.txt");
         } else {
            file = Gdx.files.internal("game/Civilizations.txt");
         }

         String tempTags = file.readString();
         if (tempTags.indexOf(nCiv.Tag) < 0) {
            FileHandle fileSave = FileManager.getSaveType("game/Civilizations.txt");
            fileSave.writeString(tempTags + nCiv.Tag + ";", false);
         } else {
            String[] tempTagsSplited = tempTags.split(";");
            boolean tAdd = true;
            int i = 0;

            for (int iSize = tempTagsSplited.length; i < iSize; i++) {
               if (tempTagsSplited[i].equals(nCiv.Tag)) {
                  tAdd = false;
                  break;
               }
            }

            if (!tAdd) {
               this.onBackPressed();
               return;
            }

            FileHandle fileSave = FileManager.getSaveType("game/Civilizations.txt");
            fileSave.writeString(tempTags + nCiv.Tag + ";", false);
         }
      } catch (GdxRuntimeException var7) {
         FileHandle fileSave = FileManager.getSaveType("game/Civilizations.txt");
         fileSave.writeString(nCiv.Tag + ";", false);
      }
   }
}
