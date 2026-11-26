package aoc.kingdoms.lukasz.menusInGame.Civ;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc2_Special;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class InGame_Civ_AI {
   public static final List<MenuElement> getMenuElements(int civID, int buttonY, int menuWidth) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      menuElements.add(
         new Text_Title_v2_TextLR(
            "AI_PrepareForWarSize",
            "" + Game.getCiv(civID).aiCivDiplomacy.w.size(),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int i = 0; i < Game.getCiv(civID).aiCivDiplomacy.w.size(); i++) {
         menuElements.add(
            new Text_Desc2_Special(Game.getCiv(Game.getCiv(civID).aiCivDiplomacy.w.get(i).c).getCivName(), paddingLeft, buttonY, menuWidth - paddingLeft * 2)
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuElements.add(
         new Text_Title_v2_TextLR(
            "AI_PrepareForAllianceSize",
            "" + Game.getCiv(civID).aiCivDiplomacy.p.size(),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int i = 0; i < Game.getCiv(civID).aiCivDiplomacy.p.size(); i++) {
         menuElements.add(
            new Text_Desc2_Special(Game.getCiv(Game.getCiv(civID).aiCivDiplomacy.p.get(i).c).getCivName(), paddingLeft, buttonY, menuWidth - paddingLeft * 2)
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuElements.add(
         new Text_Title_v2_TextLR(
            "MergeTasksSize",
            "" + Game.getCiv(civID).aiMerge.aiMergeTasks.size(),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2_TextLR(
            "MoveUnits",
            "" + Game.getCiv(civID).getMoveUnitsSize(),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2_TextLR(
            "AI_CreateNewArmy_Task",
            "" + Game.getCiv(civID).aiCivCreateNewArmy.cNA.size(),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (Game.getCiv(civID).aiCivCreateNewArmy.cNA.size() > 0) {
         for (int i = 0; i < Game.getCiv(civID).aiCivCreateNewArmy.cNA.size(); i++) {
            menuElements.add(
               new Text_Desc2(
                  "#" + i + " -> RegimentsLeft: " + Game.getCiv(civID).aiCivCreateNewArmy.cNA.get(i).rL, paddingLeft, buttonY, menuWidth - paddingLeft * 2
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

            for (int j = 0; j < Game.getCiv(civID).aiCivCreateNewArmy.cNA.get(i).cre.size(); j++) {
               String text = ArmyManager.lArmy
                     .get(Game.getCiv(civID).aiCivCreateNewArmy.cNA.get(i).cre.get(j).u)
                     .get(Game.getCiv(civID).aiCivCreateNewArmy.cNA.get(i).cre.get(j).a)
                  .Name;
               text = text + " -> " + Game.getCiv(civID).aiCivCreateNewArmy.cNA.get(i).cre.get(j).n;
               menuElements.add(new Text_Desc2_Special(text, paddingLeft, buttonY, menuWidth - paddingLeft * 2));
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            }
         }
      }

      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("Recruit") + " -> " + Game.lang.get("Army"),
            "" + Game.getCiv(civID).iArmyRecruitSize + " Total: " + Game.getCiv(civID).iArmyRecruitSize_Total,
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      for (int i = 0; i < Game.getCiv(civID).lArmyRecruit.size(); i++) {
         for (int j = 0; j < Game.getCiv(civID).lArmyRecruit.get(i).size(); j++) {
            String text = ""
               + (
                  Game.getCiv(civID).lArmyRecruit.get(i).get(j).provinceID >= 0
                     ? Game.getProvince(Game.getCiv(civID).lArmyRecruit.get(i).get(j).provinceID).getProvinceName()
                     : "ProvinceID -1"
               )
               + ": ";
            text = text
               + ArmyManager.lArmy.get(Game.getCiv(civID).lArmyRecruit.get(i).get(j).unitID).get(Game.getCiv(civID).lArmyRecruit.get(i).get(j).armyID).Name
               + " -> ";
            text = text + Game.lang.get("DaysX", Game.getCiv(civID).lArmyRecruit.get(i).get(j).timeLeft);
            menuElements.add(new Text_Desc2_Special(text, paddingLeft, buttonY, menuWidth - paddingLeft * 2));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      }

      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("Diplomacy") + " -> " + Game.lang.get("Wars"),
            Game.lang.get("IaAtWar") + ": " + Game.getCiv(civID).diplomacy.isAtWar() + " Total: " + Game.getCiv(civID).diplomacy.lWars.size(),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      return menuElements;
   }
}
