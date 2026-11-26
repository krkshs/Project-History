package aoc.kingdoms.lukasz.menus;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamPublishThread;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation_Value;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menus.LoadSave.Menu_Load_Workshop;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class WorkshopMenu extends Menu {
   public static List<String> lMods = new ArrayList<>();

   public WorkshopMenu() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 2;
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      lMods.clear();
      menuElements.add(new ButtonMain(Game.lang.get("Back"), 1, -1, paddingLeft, CFG.PADDING, CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = true;
            Game.menuManager.setViewID(View.EDITOR);
         }
      });
      buttonY += menuElements.get(0).getHeight() + buttonYPadding;
      FileHandle[] files;
      if (FileManager.IS_MAC) {
         files = Gdx.files.external("mods/").list();
      } else {
         files = Gdx.files.local("mods/").list();
      }

      for (FileHandle file : files) {
         lMods.add(file.name());
      }

      for (int i = 0; i < lMods.size(); i++) {
         if (!lMods.get(i).equals("GameCivs")) {
            menuElements.add(
               new ButtonCurrentSituation_Value(
                  lMods.get(i),
                  Images.technology2,
                  paddingLeft,
                  buttonY,
                  CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2,
                  CFG.BUTTON_HEIGHT,
                  ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
                  true,
                  i
               ) {
                  @Override
                  public void actionElement() {
                     if (!Gdx.files.internal("mods/" + WorkshopMenu.lMods.get(this.getCurrent()) + "/mod.txt").exists()
                        && (!FileManager.IS_MAC || !Gdx.files.external("mods/" + WorkshopMenu.lMods.get(this.getCurrent()) + "/mod.txt").exists())) {
                        Game.menuManager
                           .addToast_Error(Game.lang.get("MissingFile") + ": mods/" + WorkshopMenu.lMods.get(this.getCurrent()) + "/mod.txt", Images.x);
                     } else {
                        Menu_Load_Workshop.uploaded = false;
                        Game.menuManager.setViewIDWithoutAnimation(View.LOAD_WORKSHOP_PUBLISH);
                        SteamPublishThread.key = WorkshopMenu.lMods.get(this.getCurrent());
                        SteamPublishThread nSteamPublish = new SteamPublishThread();
                        nSteamPublish.start();
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Publish"), "", Images.technology2, CFG.FONT_REGULAR, CFG.FONT_REGULAR, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Line());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Text_Desc(
                           Game.lang.get("AfterClickingTheGameFreezesAndAllFilesAreTransferredJustWait"), CFG.FONT_REGULAR, Colors.HOVER_RIGHT
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         }
      }

      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH2,
         CFG.GAME_HEIGHT - titleHeight - menuY - CFG.PADDING * 2,
         menuElements,
         true,
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

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("SubmitYourModsToTheSteamWorkshop"));
   }
}
