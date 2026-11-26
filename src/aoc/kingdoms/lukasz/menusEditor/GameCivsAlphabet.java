package aoc.kingdoms.lukasz.menusEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMainReverse;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;
import java.util.List;

public class GameCivsAlphabet extends Menu {
   private List<Character> lCharacters = new ArrayList<>();
   private String nSearch = "";

   public GameCivsAlphabet() {
      List<MenuElement> menuElements = new ArrayList<>();
      int nPosX = 0;
      menuElements.add(new ButtonMain("", 1, -1, nPosX, 0, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("AddCivilization"));
         }
      });
      nPosX += menuElements.get(menuElements.size() - 1).getWidth();
      if (Keyboard.keyboardMode) {
         menuElements.add(new ButtonMainReverse("", 1, -1, nPosX, 0, CFG.BUTTON_WIDTH * 2, true) {
            @Override
            public void updateLanguage() {
               if (GameCivs.sSearch.length() > 0) {
                  this.setText(Game.lang.get("Search") + ": " + GameCivs.sSearch);
               } else {
                  this.setText(Game.lang.get("Search"));
               }
            }

            @Override
            public String getTextToDraw() {
               return super.getTextToDraw() + Keyboard.getKeyboardVerticalLine();
            }
         });
      } else {
         menuElements.add(new ButtonMain("", 1, -1, nPosX, 0, CFG.BUTTON_WIDTH * 2, true) {
            @Override
            public void updateLanguage() {
               if (GameCivs.sSearch.length() > 0) {
                  this.setText(Game.lang.get("Search") + ": " + GameCivs.sSearch);
               } else {
                  this.setText(Game.lang.get("Search"));
               }
            }
         });
      }

      nPosX += menuElements.get(menuElements.size() - 1).getWidth();
      if (!Keyboard.keyboardMode && GameCivs.chosen_AlphabetCharachter.length() == 0) {
         menuElements.add(new ButtonMainReverse("", 0, -1, nPosX, 0, CFG.BUTTON_WIDTH * 2, true) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("AllCivilizations"));
            }
         });
      } else {
         menuElements.add(new ButtonMain("", 1, -1, nPosX, 0, CFG.BUTTON_WIDTH * 2, true) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("AllCivilizations"));
            }
         });
      }

      nPosX += menuElements.get(menuElements.size() - 1).getWidth();
      FileHandle tempFileT = FileManager.loadFile("game/Civilizations.txt");
      String tempT = tempFileT.readString();
      String[] tagsSPLITED = tempT.split(";");
      int i = 0;

      for (int iSize = tagsSPLITED.length; i < iSize; i++) {
         boolean addChar = true;

         for (int a = 0; a < this.lCharacters.size(); a++) {
            if (this.lCharacters.get(a) == Game.lang.getCiv(tagsSPLITED[i]).charAt(0)) {
               addChar = false;
               break;
            }
         }

         if (addChar) {
            this.lCharacters.add(Game.lang.getCiv(tagsSPLITED[i]).charAt(0));
         }
      }

      for (int ix = 0; ix < this.lCharacters.size() - 1; ix++) {
         for (int j = ix + 1; j < this.lCharacters.size(); j++) {
            if (this.lCharacters.get(ix) > this.lCharacters.get(j)) {
               char temp = this.lCharacters.get(ix);
               this.lCharacters.set(ix, this.lCharacters.get(j));
               this.lCharacters.set(j, temp);
            }
         }
      }

      for (int ix = 0; ix < this.lCharacters.size(); ix++) {
         if (GameCivs.chosen_AlphabetCharachter.length() > 0 && this.lCharacters.get(ix) == GameCivs.chosen_AlphabetCharachter.charAt(0)) {
            menuElements.add(new ButtonMainReverse("[" + this.lCharacters.get(ix) + "]", 0, -1, nPosX, 0, CFG.BUTTON_HEIGHT, true));
            nPosX += menuElements.get(menuElements.size() - 1).getWidth();
         } else {
            menuElements.add(new ButtonMain("[" + this.lCharacters.get(ix) + "]", 1, -1, nPosX, 0, CFG.BUTTON_HEIGHT, true));
            nPosX += menuElements.get(menuElements.size() - 1).getWidth();
         }
      }

      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT + CFG.PADDING * 2, menuElements, true);
   }

   @Override
   public void actionElement(int nMenuElementID) {
      switch (nMenuElementID) {
         case 0:
            GameCivsEdit.nCiv = new Game.LoadCivilizationData();
            GameCivsEdit.nCiv.Tag = "";
            GameCivsEdit.nCiv.iR = Game.oR.nextInt(255);
            GameCivsEdit.nCiv.iG = Game.oR.nextInt(255);
            GameCivsEdit.nCiv.iB = Game.oR.nextInt(255);
            GameCivsEdit.nCiv.Wiki = "";
            GameCivsEdit.goBackTo = View.EDITOR_GAMECIVS;
            Game.menuManager.setViewID(View.EDITOR_GAMECIVS_EDIT);
            return;
         case 1:
            Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SEARCH_GAMECIVS, GameCivs.sSearch);
            Game.menuManager.rebuildEditorGameCivs();
            return;
         case 2:
            if (GameCivs.chosen_AlphabetCharachter.length() > 0 || GameCivs.sSearch.length() > 0 || Keyboard.keyboardMode) {
               GameCivs.chosen_AlphabetCharachter = "";
               GameCivs.sSearch = "";
               Game.menuManager.setViewID(View.EDITOR_GAMECIVS);
            }

            return;
         default:
            if (GameCivs.chosen_AlphabetCharachter.length() == 0
               || GameCivs.sSearch.length() > 0
               || GameCivs.chosen_AlphabetCharachter.length() > 0 && GameCivs.chosen_AlphabetCharachter.charAt(0) != this.lCharacters.get(nMenuElementID - 3)) {
               GameCivs.chosen_AlphabetCharachter = "" + this.lCharacters.get(nMenuElementID - 3);
               GameCivs.sSearch = "";
               Game.menuManager.setViewID(View.EDITOR_GAMECIVS);
            }
      }
   }
}
