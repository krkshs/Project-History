package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.FormableCivManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Minimap;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menusEditor.GameCivs;
import aoc.kingdoms.lukasz.menusScenarioEditor.ScenarioCivilizations;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

public class EditorMap_FormableCivFormable extends Menu {
   private String sCivsTag = "";
   private List<String> lCivsTags = null;
   private List<Image> lFlags = new ArrayList<>();
   private List<Integer> lLoadedFlags_TagsIDs = new ArrayList<>();

   public EditorMap_FormableCivFormable() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 2;
      int menuX = CFG.PADDING * 2;
      int menuY = CFG.PADDING * 2;
      int buttonYPadding = CFG.PADDING * 2;
      int textPosX = CFG.PADDING * 4;
      this.lCivsTags = new ArrayList<>();
      ScenarioCivilizations.listOfAllCivs = true;
      GameCivs.sSearch = "";
      menuElements.add(new ButtonMain(Game.lang.get("SelectCivilization"), 1, -1, paddingLeft, buttonYPadding, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            EditorMap_CivSelect.selectMode = EditorMap_CivSelect.SelectMode.FORMABLE_CIV;
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_CIV_SELECT);
         }
      });
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonMain(
            Game.lang.get("Capital")
               + ": "
               + (
                  FormableCivManager.activeFormableCiv.CapitalProvinceID >= 0
                     ? Game.getProvince(FormableCivManager.activeFormableCiv.CapitalProvinceID).getProvinceName()
                     : Game.lang.get("None")
               ),
            1,
            -1,
            paddingLeft,
            buttonY,
            CFG.LEFT_MENU_WIDTH - paddingLeft * 2,
            true
         ) {
            @Override
            public void actionElement() {
               if (Game.iActiveProvince >= 0) {
                  FormableCivManager.activeFormableCiv.CapitalProvinceID = Game.iActiveProvince;
                  FormableCivManager.activeFormableCiv.addProvince(Game.iActiveProvince);
                  this.setText(
                     Game.lang.get("Capital")
                        + ": "
                        + (
                           FormableCivManager.activeFormableCiv.CapitalProvinceID >= 0
                              ? Game.getProvince(FormableCivManager.activeFormableCiv.CapitalProvinceID).getProvinceName()
                              : Game.lang.get("None")
                        )
                  );
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      List<String> lTempNames = new ArrayList<>();
      List<String> lTempTags = new ArrayList<>();
      if (FormableCivManager.activeFormableCiv.FormableCivTag != null) {
         lTempNames.add(Game.lang.getCiv(FormableCivManager.activeFormableCiv.FormableCivTag));
         lTempTags.add(FormableCivManager.activeFormableCiv.FormableCivTag);
      }

      try {
         while (lTempNames.size() > 0) {
            int toAddID = 0;

            for (int i = 1; i < lTempNames.size(); i++) {
               if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(i))) {
                  toAddID = i;
               }
            }

            menuElements.add(
               new ButtonMain(
                  Game.lang.getCiv(lTempTags.get(toAddID)) + " [" + lTempTags.get(toAddID) + "]",
                  1,
                  CFG.PADDING * 6 + CFG.CIV_FLAG_WIDTH,
                  paddingLeft,
                  buttonY,
                  CFG.LEFT_MENU_WIDTH - paddingLeft * 2 - CFG.BUTTON_HEIGHT,
                  true
               ) {}
            );
            menuElements.add(
               new ButtonMain("", 1, CFG.PADDING * 2, paddingLeft + CFG.LEFT_MENU_WIDTH - paddingLeft * 2 - CFG.BUTTON_HEIGHT, buttonY, CFG.BUTTON_HEIGHT, true) {
                  @Override
                  protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                     ImageManager.getImage(Images.wiki)
                        .draw(
                           oSB,
                           iTranslateX + this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.wiki).getWidth() / 2,
                           iTranslateY + this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.wiki).getHeight() / 2
                        );
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
            this.lCivsTags.add(lTempTags.get(toAddID));
            lTempNames.remove(toAddID);
            lTempTags.remove(toAddID);
         }
      } catch (Exception var13) {
         CFG.exceptionStack(var13);
      }

      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, false),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH,
         Math.min(
            buttonY,
            CFG.GAME_HEIGHT
               - menuY
               - titleHeight
               - Game.mapBG.minimapOfCivilizations.getHeight()
               - Minimap.getPadding() * 2
               - CFG.TEXT_HEIGHT
               - CFG.PADDING * 7
         ),
         menuElements,
         true
      );
   }

   public final void drawEditorMenuBG(SpriteBatch oSB, int iX, int iY, int iWidth, int iHeight, int iTranslateX, int iTranslateY) {
      Renderer.drawBoxCorner(oSB, iX + iTranslateX, iY - this.getTitle().getHeight() + iTranslateY, iWidth, iHeight + this.getTitle().getHeight() + CFG.PADDING);
      Renderer.drawBox_EDGE_TOP_LR(oSB, Images.mainBox, iX + iTranslateX, iY + iTranslateY, iWidth, iHeight + CFG.PADDING, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      this.drawEditorMenuBG(oSB, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight(), iTranslateX, iTranslateY);
      super.beginClip(oSB, iTranslateX, iTranslateY, menuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, menuIsActive);

      try {
         for (int i = 2; i < this.getMenuElementsSize(); i += 2) {
            if (this.getMenuElement(i).getIsInView()) {
               this.lFlags
                  .get(this.getFlagID((i - 2) / 2))
                  .draw(
                     oSB,
                     this.getPosX() + this.getMenuElement(i).getPosX() + CFG.PADDING * 3 + iTranslateX,
                     this.getMenuElement(i).getPosY() + this.getMenuPosY() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY,
                     CFG.CIV_FLAG_WIDTH,
                     CFG.CIV_FLAG_HEIGHT
                  );
               ImageManager.getImage(Images.flag_rect)
                  .draw(
                     oSB,
                     this.getPosX() + this.getMenuElement(i).getPosX() + CFG.PADDING * 3 + iTranslateX,
                     this.getMenuElement(i).getPosY() + this.getMenuPosY() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY
                  );
            }
         }
      } catch (Exception var7) {
      }

      super.endClip(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("FormableCivilization"));
   }

   @Override
   public void updateMenuElements_IsInView() {
      super.updateMenuElements_IsInView();
      int tempRandomButton = 2;

      for (int i = tempRandomButton; i < this.getMenuElementsSize(); i += 2) {
         int tempTagID = this.getIsLoaded(this.lCivsTags.get((i - tempRandomButton) / 2));
         if (this.getMenuElement(i).getIsInView()) {
            if (tempTagID < 0) {
               this.loadFlag((i - tempRandomButton) / 2);
            }
         } else if (tempTagID >= 0) {
            this.lFlags.get(tempTagID).getTexture().dispose();
            this.lFlags.set(tempTagID, null);
            this.lFlags.remove(tempTagID);
            this.lLoadedFlags_TagsIDs.remove(tempTagID);
         }
      }
   }

   private final int getIsLoaded(String nCivTag) {
      for (int i = 0; i < this.lLoadedFlags_TagsIDs.size(); i++) {
         if (this.lCivsTags.get(this.lLoadedFlags_TagsIDs.get(i)).equals(nCivTag)) {
            return i;
         }
      }

      return -1;
   }

   private final int getFlagID(int nCivTagID) {
      for (int i = 0; i < this.lLoadedFlags_TagsIDs.size(); i++) {
         if (this.lLoadedFlags_TagsIDs.get(i) == nCivTagID) {
            return i;
         }
      }

      return 0;
   }

   private final void loadFlag(int nCivTagID) {
      try {
         try {
            try {
               this.lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/" + this.lCivsTags.get(nCivTagID) + ".png")), TextureFilter.Nearest));
            } catch (GdxRuntimeException var5) {
               this.lFlags
                  .add(
                     new Image(
                        new Texture(FileManager.loadFile("gfx/flags/" + Game.ideologiesManager.getRealTag(this.lCivsTags.get(nCivTagID)) + ".png")),
                        TextureFilter.Nearest
                     )
                  );
            }
         } catch (GdxRuntimeException var6) {
            try {
               this.lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + this.lCivsTags.get(nCivTagID) + ".png")), TextureFilter.Nearest));
            } catch (GdxRuntimeException var4) {
               this.lFlags
                  .add(
                     new Image(
                        new Texture(FileManager.loadFile("gfx/flagsXH/" + Game.ideologiesManager.getRealTag(this.lCivsTags.get(nCivTagID)) + ".png")),
                        TextureFilter.Nearest
                     )
                  );
            }
         }
      } catch (GdxRuntimeException var7) {
         this.lFlags.add(new Image(new Texture(FileManager.loadFile("gfx/flags/ran.png")), TextureFilter.Nearest));
      }

      this.lLoadedFlags_TagsIDs.add(nCivTagID);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      if (!visible) {
         this.disposeData();
      }
   }

   public void disposeData() {
      for (int i = 0; i < this.lFlags.size(); i++) {
         this.lFlags.get(i).getTexture().dispose();
      }

      this.lFlags.clear();
      this.lLoadedFlags_TagsIDs.clear();
      this.lCivsTags.clear();
   }
}
