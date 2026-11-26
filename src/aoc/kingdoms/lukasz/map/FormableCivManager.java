package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.FBO.FBOProvincesBG;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamAchievementsManager;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court_Government;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class FormableCivManager {
   public static FormableCivManager.FormableCiv activeFormableCiv = new FormableCivManager.FormableCiv();

   public static final void loadActiveFormableCivilization(String sFile) {
      try {
         FileHandle fileList = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "data/" + sFile);
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(FormableCivManager.ConfigJson.class, "Data", FormableCivManager.FormableCiv.class);
         activeFormableCiv = json.fromJson(FormableCivManager.FormableCiv.class, fileContent);
      } catch (Exception var4) {
         CFG.LOG(var4);
      }
   }

   public static final FormableCivManager.FormableCiv getFormableCivilization(String sFile) {
      try {
         FileHandle fileList = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "data/" + sFile + ".txt");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(FormableCivManager.ConfigJson.class, "Data", FormableCivManager.FormableCiv.class);
         return json.fromJson(FormableCivManager.FormableCiv.class, fileContent);
      } catch (Exception var4) {
         CFG.LOG(var4);
         return null;
      }
   }

   public static final void buildFormableCivilizations() {
      for (int i = 0; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).clearTagsCanForm();
      }

      if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "AoH.txt").exists()) {
         FileHandle tempFileT = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "AoH.txt");
         String tempT = tempFileT.readString();
         String[] tagsSPLITED = tempT.split(";");
         int i = 0;

         for (int iSize = tagsSPLITED.length; i < iSize; i++) {
            try {
               loadActiveFormableCivilization(tagsSPLITED[i]);
               String formableRealTag = Game.ideologiesManager.getRealTag(activeFormableCiv.FormableCivTag);
               int j = 0;

               for (int jSize = activeFormableCiv.ClaimantsTag.size(); j < jSize; j++) {
                  String claimantTag = activeFormableCiv.ClaimantsTag.get(j);

                  for (int k = 1; k < Game.getCivsSize(); k++) {
                     if (Game.getCiv(k).getCivTag().equals(claimantTag)
                        || Game.getCiv(k).realTag.equals(claimantTag) && !Game.getCiv(k).realTag.equals(formableRealTag)) {
                        Game.getCiv(k).addTagsCanForm(activeFormableCiv.FormableCivTag);
                     }
                  }
               }
            } catch (Exception var18) {
               CFG.exceptionStack(var18);
            }
         }

         for (int ix = 0; ix < SteamManager.modsFoldersSize; ix++) {
            FileHandle[] files;
            if (FileManager.IS_MAC) {
               files = Gdx.files.external(SteamManager.modsFolders.get(ix) + "map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/").list();
            } else {
               files = Gdx.files.internal(SteamManager.modsFolders.get(ix) + "map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/").list();
            }

            for (FileHandle file : files) {
               try {
                  String fileContent = file.readString();
                  Json json = new Json();
                  json.setElementType(FormableCivManager.ConfigJson.class, "Data", FormableCivManager.FormableCiv.class);
                  activeFormableCiv = json.fromJson(FormableCivManager.FormableCiv.class, fileContent);
                  String formableRealTag = Game.ideologiesManager.getRealTag(activeFormableCiv.FormableCivTag);
                  int j = 0;

                  for (int jSize = activeFormableCiv.ClaimantsTag.size(); j < jSize; j++) {
                     String claimantTag = activeFormableCiv.ClaimantsTag.get(j);

                     for (int kx = 1; kx < Game.getCivsSize(); kx++) {
                        if (Game.getCiv(kx).getCivTag().equals(claimantTag)
                           || Game.ideologiesManager.getRealTag(Game.getCiv(kx).getCivTag()).equals(claimantTag)
                              && !Game.ideologiesManager.getRealTag(Game.getCiv(kx).getCivTag()).equals(formableRealTag)) {
                           Game.getCiv(kx).addTagsCanForm(activeFormableCiv.FormableCivTag);
                        }
                     }
                  }
               } catch (Exception var17) {
                  CFG.exceptionStack(var17);
               }
            }
         }

         for (int ix = 0; ix < SteamManager.itemsInstalledSize; ix++) {
            FileHandle[] files = Gdx.files
               .absolute(SteamManager.itemsInstalled.get(ix).getFolder() + "/" + "map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/")
               .list();

            for (FileHandle file : files) {
               try {
                  String fileContent = file.readString();
                  Json json = new Json();
                  json.setElementType(FormableCivManager.ConfigJson.class, "Data", FormableCivManager.FormableCiv.class);
                  activeFormableCiv = json.fromJson(FormableCivManager.FormableCiv.class, fileContent);
                  String formableRealTag = Game.ideologiesManager.getRealTag(activeFormableCiv.FormableCivTag);
                  int j = 0;

                  for (int jSize = activeFormableCiv.ClaimantsTag.size(); j < jSize; j++) {
                     String claimantTag = activeFormableCiv.ClaimantsTag.get(j);

                     for (int kxx = 1; kxx < Game.getCivsSize(); kxx++) {
                        if (Game.getCiv(kxx).getCivTag().equals(claimantTag)
                           || Game.getCiv(kxx).realTag.equals(claimantTag) && !Game.getCiv(kxx).realTag.equals(formableRealTag)) {
                           Game.getCiv(kxx).addTagsCanForm(activeFormableCiv.FormableCivTag);
                        }
                     }
                  }
               } catch (Exception var16) {
                  CFG.exceptionStack(var16);
               }
            }
         }
      }
   }

   public static final void updateFormableCivilizations(int nCivID) {
      try {
         Game.getCiv(nCivID).clearTagsCanForm();
         if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "AoH.txt").exists()) {
            FileHandle tempFileT = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "AoH.txt");
            String tempT = tempFileT.readString();
            String[] tagsSPLITED = tempT.split(";");
            int i = 0;

            for (int iSize = tagsSPLITED.length; i < iSize; i++) {
               try {
                  loadActiveFormableCivilization(tagsSPLITED[i]);
                  String formableRealTag = Game.ideologiesManager.getRealTag(activeFormableCiv.FormableCivTag);
                  int j = 0;

                  for (int jSize = activeFormableCiv.ClaimantsTag.size(); j < jSize; j++) {
                     if (Game.getCiv(nCivID).getCivTag().equals(activeFormableCiv.ClaimantsTag.get(j))
                        || Game.getCiv(nCivID).realTag.equals(activeFormableCiv.ClaimantsTag.get(j)) && !Game.getCiv(nCivID).realTag.equals(formableRealTag)) {
                        Game.getCiv(nCivID).addTagsCanForm(activeFormableCiv.FormableCivTag);
                     }
                  }
               } catch (Exception var9) {
                  CFG.exceptionStack(var9);
               }
            }
         }
      } catch (Exception var10) {
         CFG.exceptionStack(var10);
      }
   }

   public static boolean formCiv(int civID, String nCivTag) {
      if (!canFormACiv(civID, nCivTag)) {
         return false;
      } else {
         try {
            if (civID == Game.player.iCivID) {
               SteamAchievementsManager.unlockFormable(nCivTag);
            }
         } catch (Exception var3) {
         }

         Civilization var10000 = Game.getCiv(civID);
         var10000.fGold = var10000.fGold - GameValues.government.FORM_CIV_COST_GOLD;
         Game.LoadCivilizationData civData = Game.loadCivilization(nCivTag);
         Game.getCiv(civID).updateCivilizationTAG(nCivTag, civData.iR, civData.iG, civData.iB);
         Game.getCiv(civID).clearTagsCanForm();
         if (Game.player.iCivID == civID) {
            Game.player.playerStats4.unionTags.clear();
         }

         Game.addSimpleTask_First(new Game.SimpleTask("loadRuler" + civID, civID) {
            @Override
            public void update() {
               RulersManager.loadRuler(this.id, Game.getCiv(this.id).getCivTag(), false);
            }
         });
         Game.addSimpleTask_First(new Game.SimpleTask("updateFormableCivilizations" + civID, civID) {
            @Override
            public void update() {
               FormableCivManager.updateFormableCivilizations(this.id);
               if (this.id == Game.player.iCivID) {
                  Game.player.loadFormableCivs();
                  InGame_Court_Government.reloadFlags = true;
               }

               FBOProvincesBG.redrawnProvinces();
            }
         });
         return true;
      }
   }

   public static boolean canFormACiv(int civID, String nCivTag) {
      if (!doesNotExists_FormableCiv(nCivTag)) {
         return false;
      } else if (Game.getCiv(civID).diplomacy.isAtWar()) {
         return false;
      } else if (Game.getCiv(civID).getPuppetOfCivID() != civID) {
         return false;
      } else if (Game.getCiv(civID).fGold < GameValues.government.FORM_CIV_COST_GOLD) {
         return false;
      } else {
         FormableCivManager.FormableCiv formableCiv = getFormableCivilization(nCivTag);
         return formableCiv == null ? false : formableCiv.controlsAllProvinces(civID);
      }
   }

   protected static final boolean doesNotExists_FormableCiv(String nCivTag) {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).getNumOfProvinces() > 0 && nCivTag.equals(Game.getCiv(i).getCivTag())) {
            return false;
         }
      }

      return true;
   }

   public static MenuElement_Hover getHover(int playerTagID) {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();

      try {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Form") + ": "));
         nData.add(
            new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
               "" + Game.lang.getCiv(Game.getCiv(Game.player.iCivID).sTagsCanForm.get(playerTagID)), CFG.FONT_BOLD, Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               CFG.getPrecision2((float)GameValues.government.FORM_CIV_COST_GOLD, 1), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
         nData.add(
            new MenuElement_HoverElement_Type_Image(
               Game.getCiv(Game.player.iCivID).fGold >= GameValues.government.FORM_CIV_COST_GOLD ? Images.v : Images.x, CFG.PADDING, 0
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AtPeace"), CFG.FONT_REGULAR_SMALL));
         nData.add(new MenuElement_HoverElement_Type_Image(Game.getCiv(Game.player.iCivID).diplomacy.isAtWar() ? Images.x : Images.v, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("IsNotAVassal"), CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Image(
               Game.getCiv(Game.player.iCivID).getPuppetOfCivID() == Game.player.iCivID ? Images.v : Images.x, CFG.PADDING, 0
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               Game.lang.get("XDoesNotExist", Game.lang.getCiv(Game.getCiv(Game.player.iCivID).sTagsCanForm.get(playerTagID))), CFG.FONT_REGULAR_SMALL
            )
         );
         nData.add(
            new MenuElement_HoverElement_Type_Image(
               doesNotExists_FormableCiv(Game.getCiv(Game.player.iCivID).sTagsCanForm.get(playerTagID)) ? Images.v : Images.x, CFG.PADDING, 0
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("OwnsAllProvinces"), CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Image(
               Game.player.formableCivs.get(playerTagID).controlsAllProvinces(Game.player.iCivID) ? Images.v : Images.x, CFG.PADDING, 0
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return new MenuElement_Hover(nElements);
   }

   public static class ConfigJson {
      public String Age_of_History;
      public ArrayList Data;
   }

   public static class FormableCiv {
      public String FormableCivTag = null;
      public List<String> ClaimantsTag = new ArrayList<>();
      public List<Integer> Provinces = new ArrayList<>();
      public int CapitalProvinceID = -1;

      public final void addProvince(int id) {
         if (id >= 0) {
            for (int i = 0; i < this.Provinces.size(); i++) {
               if (this.Provinces.get(i) == id) {
                  return;
               }
            }

            this.Provinces.add(id);
         }
      }

      public final void removeProvince(int id) {
         if (id >= 0) {
            for (int i = 0; i < this.Provinces.size(); i++) {
               if (this.Provinces.get(i) == id) {
                  this.Provinces.remove(i);
                  return;
               }
            }
         }
      }

      public final void addClaimant(String nTag) {
         if (this.FormableCivTag == null || !this.FormableCivTag.equals(nTag)) {
            for (int i = 0; i < this.ClaimantsTag.size(); i++) {
               if (this.ClaimantsTag.get(i).equals(nTag)) {
                  return;
               }
            }

            this.ClaimantsTag.add(nTag);
         }
      }

      public final void removeClaimant(String nTag) {
         for (int i = 0; i < this.ClaimantsTag.size(); i++) {
            if (this.ClaimantsTag.get(i).equals(nTag)) {
               this.ClaimantsTag.remove(i);
               return;
            }
         }
      }

      public int getControlledProvinces(int civID) {
         int out = 0;

         for (int i = this.getProvincesSize() - 1; i >= 0; i--) {
            if (Game.getProvince(this.Provinces.get(i)).getCivID() == civID) {
               out++;
            }
         }

         return out;
      }

      public boolean controlsAllProvinces(int civID) {
         for (int i = this.getProvincesSize() - 1; i >= 0; i--) {
            if (Game.getProvince(this.Provinces.get(i)).getWasteland() < 0
               && Game.getProvince(this.Provinces.get(i)).getCivID() != civID
               && Game.getCiv(Game.getProvince(this.Provinces.get(i)).getCivID()).getPuppetOfCivID() != civID
               && Game.getProvince(this.Provinces.get(i)).getCivID() != 0) {
               return false;
            }
         }

         return true;
      }

      public int getClaimantsSize() {
         return this.ClaimantsTag.size();
      }

      public int getProvincesSize() {
         return this.Provinces.size();
      }

      public int getProvincesSize_WithoutNeutral() {
         int out = 0;

         for (int i = this.Provinces.size() - 1; i >= 0; i--) {
            if (Game.getProvince(this.Provinces.get(i)).getCivID() > 0) {
               out++;
            }
         }

         return out;
      }
   }
}
