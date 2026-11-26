/*
 * Decompiled with CFR 0.152.
 */
package aoc.kingdoms.lukasz.menusInGame.RecruitArmy;

import aoc.kingdoms.lukasz.jakowski.AA_KeyManager;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_Army_Composition;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyRecruit;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.ClickAnimation;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_Style_NewArmy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsOnlyIMG;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonUnit_03;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Vertical;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menus.MainMenu;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.RecruitArmy.InGame_RecruitArmy;
import aoc.kingdoms.lukasz.menusInGame.RecruitArmy.InGame_RecruitArmy_NewArmy_Battlefield;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RecruitArmy_NewArmy
extends Menu {
    public static int HOVER_POSX = 0;
    protected static final int ANIMATION_TIME = 60;
    public static long lTime = 0L;
    public static String key = "";
    public static int iCost = 0;
    public static int iRegiments = 0;
    public static float fMaintenance = 0.0f;
    public static int iProvinceID = -1;
    public static List<CreateNewArmy> createNewArmy = new ArrayList<CreateNewArmy>();

    public final void updateCreateNewArmy(int iUnitID, int iArmyID, boolean addUnit) {
        if (addUnit) {
            this.addUnit(iUnitID, iArmyID);
        } else {
            this.removeUnit(iUnitID, iArmyID);
        }
        this.updateCost();
    }

    public final void addUnit(int iUnitID, int iArmyID) {
        if (Game.getCiv((int)Game.player.iCivID).fManpower < (double)((iRegiments + 1) * Game.gameAges.lAges.get((int)Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE)) {
            Game.menuManager.addToastInsufficient(Game.lang.get("Manpower") + ": ", CFG.getPrecision2(Game.getCiv((int)Game.player.iCivID).fManpower, 1) + " / " + (iRegiments + 1) * Game.gameAges.lAges.get((int)Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE, Game_Calendar.IMG_MANPOWER);
            return;
        }
        float f = Game.getCiv((int)Game.player.iCivID).fGold;
        float f2 = iCost;
        float f3 = ArmyManager.lArmy.get((int)iUnitID).get((int)iArmyID).Cost;
        float f4 = Game.getCiv(Game.player.iCivID).getArmyRegimentSize() + Game.getCiv((int)Game.player.iCivID).iArmyRecruitSize_Total + iRegiments >= Game.getCiv((int)Game.player.iCivID).iRegimentsLimit ? GameValues.army.REGIMENTS_LIMIT_RECRUIT_COST_OVER : 1.0f;
        if (f < f2 + f3 * f4) {
            Game.menuManager.addToastInsufficient(Game.lang.get("InsufficientGold") + ": ", "" + CFG.getPrecision2((float)ArmyManager.lArmy.get((int)iUnitID).get((int)iArmyID).Cost * (Game.getCiv(Game.player.iCivID).getArmyRegimentSize() + Game.getCiv((int)Game.player.iCivID).iArmyRecruitSize_Total + iRegiments >= Game.getCiv((int)Game.player.iCivID).iRegimentsLimit ? GameValues.army.REGIMENTS_LIMIT_RECRUIT_COST_OVER : 1.0f), 10), Images.gold);
            return;
        }
        for (int i = createNewArmy.size() - 1; i >= 0; --i) {
            if (InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iUnitID != iUnitID || InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iArmyID != iArmyID) continue;
            ++InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).numOfRegiments;
            createNewArmy.get(i).updateTextWidth();
            return;
        }
        createNewArmy.add(new CreateNewArmy(iUnitID, iArmyID));
    }

    public final void removeUnit(int iUnitID, int iArmyID) {
        for (int i = createNewArmy.size() - 1; i >= 0; --i) {
            if (InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iUnitID != iUnitID || InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iArmyID != iArmyID) continue;
            --InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).numOfRegiments;
            if (InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).numOfRegiments == 0) {
                createNewArmy.remove(i);
            } else {
                createNewArmy.get(i).updateTextWidth();
            }
            return;
        }
    }

    public final void updateCost() {
        iCost = 0;
        fMaintenance = 0.0f;
        iRegiments = 0;
        for (int i = createNewArmy.size() - 1; i >= 0; --i) {
            for (int j = 0; j < InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).numOfRegiments; ++j) {
                iCost = (int)((float)iCost + (float)ArmyManager.lArmy.get((int)InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iUnitID).get((int)InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iArmyID).Cost * (Game.getCiv(Game.player.iCivID).getArmyRegimentSize() + Game.getCiv((int)Game.player.iCivID).iArmyRecruitSize_Total + iRegiments >= Game.getCiv((int)Game.player.iCivID).iRegimentsLimit ? GameValues.army.REGIMENTS_LIMIT_RECRUIT_COST_OVER : 1.0f));
                fMaintenance += ArmyManager.lArmy.get((int)InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iUnitID).get((int)InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iArmyID).MaintenanceCost;
                ++iRegiments;
            }
        }
        this.getMenuElement(1).setText("" + iRegiments);
        this.getMenuElement(4).setText(CFG.getNumberWithSpaces("" + iCost));
    }

    public void updateArmyComposition(int change) {
        int i;
        int numOfRegiments = Math.min(0, iRegiments + change);
        AI_Army_Composition armyComposition = new AI_Army_Composition(Game.player.iCivID, numOfRegiments);
        iCost = 0;
        iRegiments = 0;
        fMaintenance = 0.0f;
        createNewArmy.clear();
        for (i = 0; i < armyComposition.numFirstLine; ++i) {
            this.addUnit(Game.getCiv((int)Game.player.iCivID).unitsBest_FirstLine.get((int)0).unitID, Game.getCiv((int)Game.player.iCivID).unitsBest_FirstLine.get((int)0).armyID);
        }
        for (i = 0; i < armyComposition.numFlank; ++i) {
            this.addUnit(Game.getCiv((int)Game.player.iCivID).unitsBest_Flank.get((int)0).unitID, Game.getCiv((int)Game.player.iCivID).unitsBest_Flank.get((int)0).armyID);
        }
        for (i = 0; i < armyComposition.numSupport; ++i) {
            this.addUnit(Game.getCiv((int)Game.player.iCivID).unitsBest_Support.get((int)0).unitID, Game.getCiv((int)Game.player.iCivID).unitsBest_Support.get((int)0).armyID);
        }
        for (i = 0; i < armyComposition.numSiege; ++i) {
            this.addUnit(Game.getCiv((int)Game.player.iCivID).unitsBest_Siege.get((int)0).unitID, Game.getCiv((int)Game.player.iCivID).unitsBest_Siege.get((int)0).armyID);
        }
    }

    public InGame_RecruitArmy_NewArmy() {
        int j;
        int i;
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        iCost = 0;
        iRegiments = 0;
        fMaintenance = 0.0f;
        createNewArmy.clear();
        iProvinceID = -1;
        int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
        int titleHeight = ImageManager.getImage(Images.title2).getHeight();
        int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
        int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
        int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
        int buttonYPadding = CFG.PADDING;
        int buttonY = CFG.PADDING;
        int buttonX = paddingLeft;
        int textTitleH = Math.max(ImageManager.getImage(Images.battleArmy0).getHeight() * 2 + CFG.PADDING * 3, CFG.TEXT_HEIGHT + CFG.PADDING * 6);
        int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
        int c0W = (int)((float)(menuWidth - paddingLeft * 2 - CFG.PADDING * 2) / 3.0f);
        int leftW = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 3 / 5;
        int rightW = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 2 / 5;
        int maxIconWidth = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth();
        int backW = maxIconWidth + CFG.PADDING * 3;
        menuElements.add(new TextIcon_Vertical(Game.lang.get("Back"), Game_Calendar.IMG_MANPOWER, 0, buttonX, buttonY, backW, buttonH * 2 + CFG.PADDING){

            @Override
            public void actionElement() {
                if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_NEW_ARMY_CHOOSE_PROVINCE) {
                    Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                }
                Game.menuManager.rebuildInGame_RecruitArmy();
                Game.menuManager.setVisibleInGame_RecruitArmy(true);
            }

            @Override
            public void buildElementHover() {
                ArrayList<MenuElement_HoverElement> nElements = new ArrayList<MenuElement_HoverElement>();
                ArrayList<MenuElement_HoverElement_Type> nData = new ArrayList<MenuElement_HoverElement_Type>();
                nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Back") + ": ", Colors.HOVER_GOLD));
                nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
        });
        menuElements.add(new ButtonStatsRectIMG_Active_Click("0", Game_Calendar.IMG_MANPOWER, buttonX + backW + CFG.PADDING, buttonY, rightW - (backW + CFG.PADDING), buttonH, maxIconWidth, 0){

            @Override
            public void actionElement() {
            }

            @Override
            public void buildElementHover() {
                ArrayList<MenuElement_HoverElement> nElements = new ArrayList<MenuElement_HoverElement>();
                ArrayList<MenuElement_HoverElement_Type> nData = new ArrayList<MenuElement_HoverElement_Type>();
                nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Cost"), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("RecruitRegiments") + ": ", this.getText(), Game_Calendar.IMG_MANPOWER, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("Cost") + ", " + Game.lang.get("Manpower") + ": ", CFG.getNumberWithSpaces("" + iRegiments * Game.gameAges.lAges.get((int)Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE), Game_Calendar.IMG_MANPOWER, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Line());
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonusFlag("", Game.getCiv(Game.player.iCivID).getCivName(), Game.player.iCivID, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("Manpower") + ": ", CFG.getNumberWithSpaces("" + (int)Game.getCiv((int)Game.player.iCivID).fManpower), Game_Calendar.IMG_MANPOWER, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover(nElements);
            }
        });
        menuElements.add(new ButtonStatsRectIMG_Active_Click(Game.lang.get("ChooseAProvince"), Images.provinces, buttonX + CFG.PADDING + rightW, buttonY, leftW - buttonH - CFG.PADDING, buttonH, maxIconWidth, iProvinceID, true){

            @Override
            public void actionElement() {
                if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_NEW_ARMY_CHOOSE_PROVINCE) {
                    Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                } else {
                    Game.gameActiveProvince.resetLastActiveProvince();
                    Game.setActiveProvinceID(-1);
                    Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                    Game.mapModes.setActiveViewID(Game.mapModes.MODE_NEW_ARMY_CHOOSE_PROVINCE);
                }
            }

            @Override
            public void buildElementHover() {
                ArrayList<MenuElement_HoverElement> nElements = new ArrayList<MenuElement_HoverElement>();
                ArrayList<MenuElement_HoverElement_Type> nData = new ArrayList<MenuElement_HoverElement_Type>();
                nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ChooseAProvince"), Colors.HOVER_GOLD));
                nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                if (iProvinceID >= 0) {
                    nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("Province") + ": ", Game.getProvince(iProvinceID).getProvinceName(), Images.provinces, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                    nElements.add(new MenuElement_HoverElement(nData));
                    nData.clear();
                    nData.add(new MenuElement_HoverElement_Type_Line());
                    nElements.add(new MenuElement_HoverElement(nData));
                    nData.clear();
                }
                nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("ProvinceInWhichTheArmyWillBeStationed"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public String getTextToDraw() {
                if (iProvinceID != this.id && iProvinceID >= 0) {
                    this.setText(Game.getProvince(iProvinceID).getProvinceName());
                    this.id = iProvinceID;
                }
                return super.getTextToDraw();
            }

            @Override
            public void setText(String sText) {
                super.setText(sText);
                try {
                    int tWMax = 0;
                    while (this.iTextWidth >= this.getWidth() - (CFG.PADDING * 2 + this.maxIconWidth) && this.getText().length() > 5 && ++tWMax < 100) {
                        this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
                    }
                }
                catch (Exception ex) {
                    CFG.exceptionStack(ex);
                }
            }

            @Override
            protected Color getColor(boolean isActive) {
                if (this.id < 0) {
                    return Colors.HOVER_GOLD;
                }
                return super.getColor(isActive);
            }
        });
        menuElements.add(new ButtonStatsOnlyIMG(Images.information, buttonX + CFG.PADDING * 2 + rightW + (leftW - buttonH - CFG.PADDING), buttonY, buttonH, buttonH){

            @Override
            public void actionElement() {
                InGame_RecruitArmy_NewArmy_Battlefield.hideShowMenu();
            }

            @Override
            public void buildElementHover() {
                ArrayList<MenuElement_HoverElement> nElements = new ArrayList<MenuElement_HoverElement>();
                ArrayList<MenuElement_HoverElement_Type> nData = new ArrayList<MenuElement_HoverElement_Type>();
                nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ArmyDeployment"), Colors.HOVER_GOLD));
                nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.battle, CFG.PADDING, 0));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("ArmyComposition0"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Line());
                nElements.add(new MenuElement_HoverElement(nData, false));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("SecondLine1"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
                nElements.add(new MenuElement_HoverElement(nData, false));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Line());
                nElements.add(new MenuElement_HoverElement(nData, false));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("SecondLine2"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
                nElements.add(new MenuElement_HoverElement(nData, false));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Line());
                nElements.add(new MenuElement_HoverElement(nData, false));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("SecondLine3"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
                nElements.add(new MenuElement_HoverElement(nData, false));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover(nElements);
            }
        });
        menuElements.add(new ButtonStatsRectIMG_Active_Click("0", Images.gold, buttonX + backW + CFG.PADDING, buttonY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + buttonYPadding, rightW - (backW + CFG.PADDING), buttonH, maxIconWidth, 0){

            @Override
            public void actionElement() {
            }

            @Override
            public void buildElementHover() {
                ArrayList<MenuElement_HoverElement> nElements = new ArrayList<MenuElement_HoverElement>();
                ArrayList<MenuElement_HoverElement_Type> nData = new ArrayList<MenuElement_HoverElement_Type>();
                nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Cost"), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("RecruitRegiments") + ": ", "" + iRegiments, Game_Calendar.IMG_MANPOWER, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("Cost") + ": ", this.getText(), Images.gold, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Line());
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonusFlag("", Game.getCiv(Game.player.iCivID).getCivName(), Game.player.iCivID, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("Treasury") + ": ", CFG.getNumberWithSpaces("" + (int)Game.getCiv((int)Game.player.iCivID).fGold), Images.gold, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover(nElements);
            }
        });
        menuElements.add(new ButtonGame(Game.lang.get("CreateArmy"), CFG.FONT_REGULAR, -1, buttonX + CFG.PADDING + rightW, buttonY, leftW, buttonH, true){

            @Override
            public int getSFX() {
                return SoundsManager.SOUND_CREATE_ARMY;
            }

            @Override
            public void actionElement() {
                if (InGame_RecruitArmy_NewArmy.actionCreateNewArmy()) {
                    MenuManager.addClickAnimation(new ClickAnimation(this.getPosX() + this.getWidth() / 2 + InGame_RecruitArmy_NewArmy.this.getMenuPosX(), this.getPosY() + this.getHeight() / 2 + InGame_RecruitArmy_NewArmy.this.getMenuPosY(), this.getWidth(), this.getHeight()){

                        @Override
                        public Color getColor() {
                            return DiplomacyManager.COLOR_WAR;
                        }
                    });
                }
            }

            @Override
            public void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
                if (this.getIsHovered() || isActive) {
                    oSB.setColor(MainMenu.sparksColors);
                    MenuManager.sparksAnimationHover.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
                    oSB.setColor(Color.WHITE);
                }
            }

            @Override
            public void buildElementHover() {
                ArrayList<MenuElement_HoverElement> nElements = new ArrayList<MenuElement_HoverElement>();
                ArrayList<MenuElement_HoverElement_Type> nData = new ArrayList<MenuElement_HoverElement_Type>();
                nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Confirm"), Colors.HOVER_GOLD));
                nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.regimentsLimit, CFG.PADDING, 0));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("RecruitRegiments") + ": ", "" + iRegiments, Game_Calendar.IMG_MANPOWER, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Line());
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("Cost") + ": ", "" + iCost, Images.gold, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("Cost") + ", " + Game.lang.get("Manpower") + ": ", CFG.getNumberWithSpaces("" + iRegiments * Game.gameAges.lAges.get((int)Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE), Game_Calendar.IMG_MANPOWER, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("MaintenanceCost") + ": ", "" + CFG.getPrecision2(fMaintenance, 100), Images.gold, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Line());
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonusFlag("", Game.getCiv(Game.player.iCivID).getCivName(), Game.player.iCivID, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData, false));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("Treasury") + ": ", CFG.getNumberWithSpaces("" + (int)Game.getCiv((int)Game.player.iCivID).fGold), Images.gold, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                nData.add(new MenuElement_HoverElement_Type_Button_TextBonus(Game.lang.get("Manpower") + ": ", CFG.getNumberWithSpaces("" + (int)Game.getCiv((int)Game.player.iCivID).fManpower), Game_Calendar.IMG_MANPOWER, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD));
                nElements.add(new MenuElement_HoverElement(nData));
                nData.clear();
                this.menuElementHover = new MenuElement_Hover(nElements);
            }
        });
        buttonX = Images.boxTitleBORDERWIDTH;
        menuElements.add(InGame_RecruitArmy.getTitleFirstLine(buttonY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + buttonYPadding, menuWidth, textTitleH));
        buttonY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + buttonYPadding;
        int tempAdded = 0;
        for (i = 0; i < ArmyManager.iUnitsTypesSize; ++i) {
            for (j = 0; j < ArmyManager.lArmySize.get(i); ++j) {
                if (ArmyManager.lUnitsTypes.get((int)i).Line != 0 || !Game.getCiv(Game.player.iCivID).isUnitBest(i, j)) continue;
                ++tempAdded;
                menuElements.add(new ButtonUnit_03(i, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT, true){

                    @Override
                    public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                        super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
                        for (int i = createNewArmy.size() - 1; i >= 0; --i) {
                            if (InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iUnitID != this.unitTypeID || InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iArmyID != this.armyID) continue;
                            oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.75f));
                            Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + 7.getPaddingIMG() + CFG.PADDING + iTranslateY, InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).textWidth + CFG.PADDING * 4, CFG.TEXT_HEIGHT + CFG.PADDING * 2, 1.0f);
                            oSB.setColor(Color.WHITE);
                            Renderer.drawText(oSB, CFG.FONT_BOLD, "" + InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).numOfRegiments, this.getPosX() + CFG.PADDING * 3 + iTranslateX, this.getPosY() + 7.getPaddingIMG() + CFG.PADDING * 2 + iTranslateY, Colors.HOVER_GOLD);
                            break;
                        }
                    }

                    @Override
                    public void actionElement() {
                        if (AA_KeyManager.CTRL_HOLD || AA_KeyManager.SHIFT_HOLD) {
                            for (int i = 0; i < 5; ++i) {
                                InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 0);
                            }
                        } else {
                            InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 0);
                        }
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }

                    @Override
                    public void actionElementPPM() {
                        if (AA_KeyManager.CTRL_HOLD || AA_KeyManager.SHIFT_HOLD) {
                            for (int i = 0; i < 5; ++i) {
                                InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                            }
                        } else {
                            InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                        }
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }
                });
                menuElements.add(new ButtonGame_Style_NewArmy(i, j, true, "+", CFG.FONT_REGULAR, -1, paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING, buttonY, CFG.BUTTON_HEIGHT - CFG.PADDING, (ButtonUnit_03.getTopH() - CFG.PADDING) / 2, true){

                    @Override
                    public void actionElement() {
                        InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }
                });
                menuElements.add(new ButtonGame_Style_NewArmy(i, j, false, "-", CFG.FONT_REGULAR, -1, paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING, buttonY + (ButtonUnit_03.getTopH() - CFG.PADDING) / 2 + CFG.PADDING, CFG.BUTTON_HEIGHT - CFG.PADDING, (ButtonUnit_03.getTopH() - CFG.PADDING) / 2, true){

                    @Override
                    public void actionElement() {
                        InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }
                });
                buttonY += ButtonUnit_03.getTopH() + CFG.PADDING;
            }
        }
        if (tempAdded == 0) {
            menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
            buttonY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        }
        tempAdded = 0;
        menuElements.add(InGame_RecruitArmy.getTitleFirstLineSide(buttonY, menuWidth, textTitleH));
        buttonY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + buttonYPadding;
        for (i = 0; i < ArmyManager.iUnitsTypesSize; ++i) {
            for (j = 0; j < ArmyManager.lArmySize.get(i); ++j) {
                if (ArmyManager.lUnitsTypes.get((int)i).Line != 1 || !Game.getCiv(Game.player.iCivID).isUnitBest(i, j)) continue;
                ++tempAdded;
                menuElements.add(new ButtonUnit_03(i, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT, true){

                    @Override
                    public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                        super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
                        for (int i = createNewArmy.size() - 1; i >= 0; --i) {
                            if (InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iUnitID != this.unitTypeID || InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iArmyID != this.armyID) continue;
                            oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.75f));
                            Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + 10.getPaddingIMG() + CFG.PADDING + iTranslateY, InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).textWidth + CFG.PADDING * 4, CFG.TEXT_HEIGHT + CFG.PADDING * 2, 1.0f);
                            oSB.setColor(Color.WHITE);
                            Renderer.drawText(oSB, CFG.FONT_BOLD, "" + InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).numOfRegiments, this.getPosX() + CFG.PADDING * 3 + iTranslateX, this.getPosY() + 10.getPaddingIMG() + CFG.PADDING * 2 + iTranslateY, Colors.HOVER_GOLD);
                            break;
                        }
                    }

                    @Override
                    public void actionElement() {
                        if (AA_KeyManager.CTRL_HOLD || AA_KeyManager.SHIFT_HOLD) {
                            for (int i = 0; i < 5; ++i) {
                                InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 0);
                            }
                        } else {
                            InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 0);
                        }
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }

                    @Override
                    public void actionElementPPM() {
                        if (AA_KeyManager.CTRL_HOLD || AA_KeyManager.SHIFT_HOLD) {
                            for (int i = 0; i < 5; ++i) {
                                InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                            }
                        } else {
                            InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                        }
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }
                });
                menuElements.add(new ButtonGame_Style_NewArmy(i, j, true, "+", CFG.FONT_REGULAR, -1, paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING, buttonY, CFG.BUTTON_HEIGHT - CFG.PADDING, (ButtonUnit_03.getTopH() - CFG.PADDING) / 2, true){

                    @Override
                    public void actionElement() {
                        InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }
                });
                menuElements.add(new ButtonGame_Style_NewArmy(i, j, false, "-", CFG.FONT_REGULAR, -1, paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING, buttonY + (ButtonUnit_03.getTopH() - CFG.PADDING) / 2 + CFG.PADDING, CFG.BUTTON_HEIGHT - CFG.PADDING, (ButtonUnit_03.getTopH() - CFG.PADDING) / 2, true){

                    @Override
                    public void actionElement() {
                        InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }
                });
                buttonY += ButtonUnit_03.getTopH() + CFG.PADDING;
            }
        }
        if (tempAdded == 0) {
            menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
            buttonY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        }
        tempAdded = 0;
        menuElements.add(InGame_RecruitArmy.getTitleSecondLine(buttonY, menuWidth, textTitleH));
        buttonY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + buttonYPadding;
        for (i = 0; i < ArmyManager.iUnitsTypesSize; ++i) {
            for (j = 0; j < ArmyManager.lArmySize.get(i); ++j) {
                if (ArmyManager.lUnitsTypes.get((int)i).Line <= 1 || !Game.getCiv(Game.player.iCivID).isUnitBest(i, j)) continue;
                ++tempAdded;
                menuElements.add(new ButtonUnit_03(i, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT, true){

                    @Override
                    public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                        super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
                        for (int i = createNewArmy.size() - 1; i >= 0; --i) {
                            if (InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iUnitID != this.unitTypeID || InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iArmyID != this.armyID) continue;
                            oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.75f));
                            Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + 13.getPaddingIMG() + CFG.PADDING + iTranslateY, InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).textWidth + CFG.PADDING * 4, CFG.TEXT_HEIGHT + CFG.PADDING * 2, 1.0f);
                            oSB.setColor(Color.WHITE);
                            Renderer.drawText(oSB, CFG.FONT_BOLD, "" + InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).numOfRegiments, this.getPosX() + CFG.PADDING * 3 + iTranslateX, this.getPosY() + 13.getPaddingIMG() + CFG.PADDING * 2 + iTranslateY, Colors.HOVER_GOLD);
                            break;
                        }
                    }

                    @Override
                    public void actionElement() {
                        if (AA_KeyManager.CTRL_HOLD || AA_KeyManager.SHIFT_HOLD) {
                            for (int i = 0; i < 5; ++i) {
                                InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 0);
                            }
                        } else {
                            InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 0);
                        }
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }

                    @Override
                    public void actionElementPPM() {
                        if (AA_KeyManager.CTRL_HOLD || AA_KeyManager.SHIFT_HOLD) {
                            for (int i = 0; i < 5; ++i) {
                                InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                            }
                        } else {
                            InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                        }
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }
                });
                menuElements.add(new ButtonGame_Style_NewArmy(i, j, true, "+", CFG.FONT_REGULAR, -1, paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING, buttonY, CFG.BUTTON_HEIGHT - CFG.PADDING, (ButtonUnit_03.getTopH() - CFG.PADDING) / 2, true){

                    @Override
                    public void actionElement() {
                        InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }
                });
                menuElements.add(new ButtonGame_Style_NewArmy(i, j, false, "-", CFG.FONT_REGULAR, -1, paddingLeft + (menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT) + CFG.PADDING, buttonY + (ButtonUnit_03.getTopH() - CFG.PADDING) / 2 + CFG.PADDING, CFG.BUTTON_HEIGHT - CFG.PADDING, (ButtonUnit_03.getTopH() - CFG.PADDING) / 2, true){

                    @Override
                    public void actionElement() {
                        InGame_RecruitArmy_NewArmy.this.updateCreateNewArmy(this.getValue1(), this.getValue2(), this.getCurrent() == 1);
                        InGame_RecruitArmy_NewArmy_Battlefield.rebuildMenuIfVisible();
                    }
                });
                buttonY += ButtonUnit_03.getTopH() + CFG.PADDING;
            }
        }
        if (tempAdded == 0) {
            menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
            buttonY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        }
        tempAdded = 0;
        int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
        menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
        HOVER_POSX = menuX + menuWidth + CFG.PADDING;
        this.initMenu(new MenuTitleIMG_DoubleText(Game.lang.get("CreateNewArmy"), Game.lang.get("BattleWidth") + ": " + BattleManager.getBattleWidth(Game.player.iCivID), false, false, Images.title1Red){

            @Override
            public long getTime() {
                return lTime;
            }
        }, menuX, menuY, menuWidth, menuHeight, menuElements, true, true);
        this.drawScrollPositionAlways = false;
    }

    @Override
    public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
        if (lTime + 60L >= CFG.currentTimeMillis) {
            iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)((float)CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0f));
        }
        Renderer.drawBoxCorner(oSB, this.getPosX() + iTranslateX, this.getPosY() - this.getTitle().getHeight() + iTranslateY, this.getWidth(), this.getHeight() + this.getTitle().getHeight() + CFG.PADDING);
        Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
        ImageManager.getImage(Images.recruitArmyOver).draw2(oSB, this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.recruitArmyOver).getWidth() / 2 + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), Math.min(this.getHeight(), ImageManager.getImage(Images.recruitArmyOver).getHeight()));
        if (Game.getCiv((int)Game.player.iCivID).diplomacy.isAtWar()) {
            oSB.setColor(InGame_RecruitArmy.sparksColor);
            InGame_RecruitArmy.sparksAnimation.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), Images.sparkHeight, false, true);
            oSB.setColor(Color.WHITE);
        }
        super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        lTime = CFG.currentTimeMillis;
        if (!visible) {
            Game.menuManager.setVisibleInGame_RecruitArmy_Battlefield(false);
            if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_NEW_ARMY_CHOOSE_PROVINCE) {
                Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
            }
        }
    }

    @Override
    public void actionCloseMenu() {
        super.actionCloseMenu();
        if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_NEW_ARMY_CHOOSE_PROVINCE) {
            Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
        }
    }

    public static final boolean actionCreateNewArmy() {
        boolean out = false;
        if (createNewArmy.size() > 0) {
            if (iProvinceID >= 0) {
                if (Game.getCiv(Game.player.iCivID).getNumOfProvinces() > 0) {
                    String sKey = CFG.extraRandomTag();
                    Game.getCiv((int)Game.player.iCivID).lCreateNewArmy.put(sKey, iProvinceID);
                    ArrayList<Integer> tProvinces = new ArrayList<Integer>();
                    for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); ++i) {
                        if (Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).isOccupied()) continue;
                        tProvinces.add(Game.getCiv(Game.player.iCivID).getProvinceID(i));
                    }
                    int tProvincesSize = tProvinces.size();
                    if (tProvincesSize > 0) {
                        int iSize = createNewArmy.size();
                        for (int i = 0; i < iSize; ++i) {
                            for (int j = 0; j < InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).numOfRegiments; ++j) {
                                Game.getCiv(Game.player.iCivID).recruitArmy(new ArmyRecruit((Integer)tProvinces.get(Game.oR.nextInt(tProvincesSize)), InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iUnitID, InGame_RecruitArmy_NewArmy.createNewArmy.get((int)i).iArmyID, sKey));
                            }
                        }
                        Game.menuManager.addToastGold(Game.lang.get("Done"), Game_Calendar.IMG_MANPOWER);
                        out = true;
                    } else {
                        Game.menuManager.addToast_Error(Game.lang.get("NumberOfProvinces") + ": " + tProvincesSize, Images.provinces);
                    }
                }
            } else {
                Game.menuManager.addToast_Error(Game.lang.get("ChooseAProvince"), Images.provinces);
            }
        } else {
            Game.menuManager.addToast_Error(Game.lang.get("Army") + ": " + iRegiments, Game_Calendar.IMG_MANPOWER);
        }
        return out;
    }

    public static class CreateNewArmy {
        public int iUnitID;
        public int iArmyID;
        public int numOfRegiments = 1;
        public int textWidth = 0;

        public CreateNewArmy(int iUnitID, int iArmyID) {
            this.iUnitID = iUnitID;
            this.iArmyID = iArmyID;
            this.updateTextWidth();
        }

        public void updateTextWidth() {
            Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), "" + this.numOfRegiments);
            this.textWidth = (int)Renderer.glyphLayout.width;
        }
    }
}
