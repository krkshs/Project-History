package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.FormableCivManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Minimap;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ColorTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menus.Dialog;
import aoc.kingdoms.lukasz.menusScenarioEditor.ScenarioCivilizations;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

public class EditorMap_FormableCivClaimants extends Menu {
   private String sCivsTag = "";
   private List<String> lCivsTags = null;
   private List<Image> lFlags = new ArrayList<>();
   private List<Integer> lLoadedFlags_TagsIDs = new ArrayList<>();

   public EditorMap_FormableCivClaimants() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 2;
      int menuX = CFG.GAME_WIDTH - CFG.LEFT_MENU_WIDTH - CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 6;
      int buttonYPadding = CFG.PADDING * 2;
      int textPosX = CFG.PADDING * 4;
      this.lCivsTags = new ArrayList<>();
      ScenarioCivilizations.listOfAllCivs = true;
      menuElements.add(new ButtonMain(Game.lang.get("AddCivilization"), 1, -1, paddingLeft, buttonYPadding, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            EditorMap_CivSelect.selectMode = EditorMap_CivSelect.SelectMode.FORMABLE_CLAIMANT;
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_CIV_SELECT);
         }
      });
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      List<String> lTempNames = new ArrayList<>();
      List<String> lTempTags = new ArrayList<>();
      int i = 0;

      for (int iSize = FormableCivManager.activeFormableCiv.getClaimantsSize(); i < iSize; i++) {
         lTempNames.add(Game.lang.getCiv(FormableCivManager.activeFormableCiv.ClaimantsTag.get(i)));
         lTempTags.add(FormableCivManager.activeFormableCiv.ClaimantsTag.get(i));
      }

      try {
         while (lTempNames.size() > 0) {
            i = 0;

            for (int ix = 1; ix < lTempNames.size(); ix++) {
               if (CFG.compareAlphabetic_TwoString(lTempNames.get(i), lTempNames.get(ix))) {
                  i = ix;
               }
            }

            menuElements.add(
               new ButtonMain(
                  Game.lang.getCiv(lTempTags.get(i)) + " [" + lTempTags.get(i) + "]",
                  1,
                  CFG.PADDING * 6 + CFG.CIV_FLAG_WIDTH,
                  paddingLeft,
                  buttonY,
                  CFG.LEFT_MENU_WIDTH - paddingLeft * 2 - CFG.BUTTON_HEIGHT,
                  true
               ) {
                  @Override
                  public void buildElementHover() {
                     try {
                        String tTag = this.getText().substring(this.getText().indexOf("[") + 1, this.getText().indexOf("]"));
                        Game.LoadCivilizationData nCivs = Game.loadCivilization(tTag);
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_ColorTitle(
                              new Color(nCivs.iR / 255.0F, nCivs.iG / 255.0F, nCivs.iB / 255.0F, 1.0F), CFG.FONT_BOLD_SMALL, CFG.PADDING
                           )
                        );
                        nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.getCiv(nCivs.Tag), Colors.HOVER_TITLE));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RemoveCivilization"), CFG.FONT_BOLD_SMALL));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     } catch (IndexOutOfBoundsException var5) {
                        super.buildElementHover();
                     }
                  }
               }
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
            this.lCivsTags.add(lTempTags.get(i));
            lTempNames.remove(i);
            lTempTags.remove(i);
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
         if (ScenarioCivilizations.listOfAllCivs) {
            for (int i = 1; i < this.getMenuElementsSize(); i += 2) {
               if (this.getMenuElement(i).getIsInView()) {
                  this.lFlags
                     .get(this.getFlagID((i - 1) / 2))
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
         } else {
            for (int ix = 0; ix < this.getMenuElementsSize(); ix++) {
               if (this.getMenuElement(ix).getIsInView()) {
                  this.lFlags
                     .get(this.getFlagID(ix))
                     .draw(
                        oSB,
                        this.getPosX() + this.getMenuElement(ix).getPosX() + CFG.PADDING * 3 + iTranslateX,
                        this.getMenuElement(ix).getPosY()
                           + this.getMenuPosY()
                           + this.getMenuElement(ix).getHeight() / 2
                           - CFG.CIV_FLAG_HEIGHT / 2
                           + iTranslateY,
                        CFG.CIV_FLAG_WIDTH,
                        CFG.CIV_FLAG_HEIGHT
                     );
                  ImageManager.getImage(Images.flag_rect)
                     .draw(
                        oSB,
                        this.getPosX() + this.getMenuElement(ix).getPosX() + CFG.PADDING * 3 + iTranslateX,
                        this.getMenuElement(ix).getPosY()
                           + this.getMenuPosY()
                           + this.getMenuElement(ix).getHeight() / 2
                           - CFG.CIV_FLAG_HEIGHT / 2
                           + iTranslateY
                     );
               }
            }
         }
      } catch (IndexOutOfBoundsException var7) {
      } catch (NullPointerException var8) {
      }

      super.endClip(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Claimants") + " [" + this.lCivsTags.size() + "]");
   }

   @Override
   public void actionElement(int nMenuElementID) {
      if (nMenuElementID == 0) {
         super.actionElement(nMenuElementID);
      } else if (nMenuElementID % 2 == 1) {
         FormableCivManager.activeFormableCiv.removeClaimant(this.lCivsTags.get((nMenuElementID - 1) / 2));
         Game.menuManager.setViewIDWithoutAnimation(View.EDITOR_MAPS_EDIT_FORMABLE_CIV);
      } else {
         String tempCivTag = this.lCivsTags.get((nMenuElementID - 1) / 2);

         try {
            Game.LoadCivilizationData nCiv = Game.loadCivilization(tempCivTag);
            Dialog.GO_TO_LINK = "https://en.wikipedia.org/wiki/" + nCiv.Wiki;
            Dialog.setDialogType(Dialog.DialogType.GO_TO_LINK);
         } catch (GdxRuntimeException var4) {
            Game.menuManager.addToast_Error(Game.lang.get("NoData"));
         }
      }
   }

   @Override
   public void updateMenuElements_IsInView() {
      super.updateMenuElements_IsInView();
      if (ScenarioCivilizations.listOfAllCivs) {
         int tempRandomButton = 1;

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
      } else {
         for (int ix = 0; ix < this.getMenuElementsSize(); ix++) {
            int tempTagID = this.getIsLoaded(this.lCivsTags.get(ix));
            if (this.getMenuElement(ix).getIsInView()) {
               if (tempTagID < 0) {
                  this.loadFlag(ix);
               }
            } else if (tempTagID >= 0) {
               this.lFlags.get(tempTagID).getTexture().dispose();
               this.lFlags.set(tempTagID, null);
               this.lFlags.remove(tempTagID);
               this.lLoadedFlags_TagsIDs.remove(tempTagID);
            }
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
