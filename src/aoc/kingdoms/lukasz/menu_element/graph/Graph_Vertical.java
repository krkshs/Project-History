package aoc.kingdoms.lukasz.menu_element.graph;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ_Government;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ_Religion;
import aoc.kingdoms.lukasz.menusInGame.Goods.InGame_Goods_LargestProducers;
import aoc.kingdoms.lukasz.menusInGame.Right.InGame_RightGovernment;
import aoc.kingdoms.lukasz.menusInGame.Right.InGame_RightReligion;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Graph_Vertical extends MenuElement {
   public static List<Integer> graphCivs = new ArrayList<>();
   private List<Graph_Vertical_Data> lValues = new ArrayList<>();
   private int iValuesSize = 0;
   private int iValuesTotal = 0;
   private int iDataWidth = 0;
   private Graph_Vertical_Info verticalInfo;
   private Graph_Vertical_Data_Type GRAPH_DATA_TYPE;
   public boolean splitBy100 = false;
   public boolean drawShort = false;
   private boolean statisticsMode = false;
   private String sTextX;
   private String sTextY;
   private int iWidthTextY;
   private int iMinPoint;
   private int iMaxPoint;
   private float fAvaragePoint;
   private int iAvaragePosY;
   private byte bDecimal = 0;
   private boolean lessThanTen = false;
   private String sTotal = "";
   private boolean moveable = false;
   private int iButtonsPosX;
   private int iButtonsPosY;
   private int iHoveredID = -1;
   private boolean scrollModeY = false;
   private int iScrollPosX = -1;
   private int iScrollPosX2 = -1;
   private float fScrollNewMenuPosY = 0.0F;
   private Graph_Vertical.DrawStatisticsData drawStatisticsData;
   public boolean allowStatisticsMode = true;

   public Graph_Vertical(Graph_Vertical_Data_Type nType, String sTextX, String sTextY, int iPosX, int iPosY, int iWidth, int iHeight, boolean visible) {
      this.GRAPH_DATA_TYPE = nType;
      this.sTotal = Game.lang.get("Total");
      if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.NUM_OF_PROVINCES_BY_CONTINENT) {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(i));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int ix, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(ix).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + Graph_Vertical.this.lValues.get(ix).getValue(j),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(ix).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (ix + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + Graph_Vertical.this.lValues.get(ix).getValue()
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(ix).getValue(), Graph_Vertical.this.iValuesTotal, 4)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (ix + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + Graph_Vertical.this.iValuesTotal + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_LIST_PROVINCES) {
         for (int ix = graphCivs.size() - 1; ix >= 0; ix--) {
            if (graphCivs.get(ix) > 0 && graphCivs.get(ix) < Game.getCivsSize() && Game.getCiv(graphCivs.get(ix)).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(graphCivs.get(ix)));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + CFG.getNumberWithSpaces("" + Graph_Vertical.this.lValues.get(i).getValue(j)),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + CFG.getShortNumber(Graph_Vertical.this.lValues.get(i).getValue())
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + CFG.getShortNumber(Math.max(1, Graph_Vertical.this.iValuesTotal)) + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_LIST_POPULATION) {
         for (int ixx = graphCivs.size() - 1; ixx >= 0; ixx--) {
            if (graphCivs.get(ixx) > 0 && graphCivs.get(ixx) < Game.getCivsSize() && Game.getCiv(graphCivs.get(ixx)).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(graphCivs.get(ixx)));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + CFG.getNumberWithSpaces("" + Graph_Vertical.this.lValues.get(i).getValue(j)),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + CFG.getShortNumber(Graph_Vertical.this.lValues.get(i).getValue())
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + CFG.getShortNumber(Math.max(1, Graph_Vertical.this.iValuesTotal)) + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_POPULATION) {
         for (int ixxx = 1; ixxx < Game.getCivsSize(); ixxx++) {
            if (Game.getCiv(ixxx).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(ixxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + CFG.getNumberWithSpaces("" + Graph_Vertical.this.lValues.get(i).getValue(j)),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + CFG.getShortNumber(Graph_Vertical.this.lValues.get(i).getValue())
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + CFG.getShortNumber(Math.max(1, Graph_Vertical.this.iValuesTotal)) + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.GOVERNMENTS_CIVS) {
         for (int ixxxx = 1; ixxxx < Game.getCivsSize(); ixxxx++) {
            if (Game.getCiv(ixxxx).getNumOfProvinces() > 0 && Game.getCiv(ixxxx).getIdeologyID() == InGame_Civ_Government.iGovID) {
               this.lValues.add(new Graph_Vertical_Data(ixxxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + CFG.getNumberWithSpaces("" + Graph_Vertical.this.lValues.get(i).getValue(j)),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + CFG.getShortNumber(Graph_Vertical.this.lValues.get(i).getValue())
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + CFG.getShortNumber(Math.max(1, Graph_Vertical.this.iValuesTotal)) + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.GOVERNMENTS_CIVS_RIGHT) {
         for (int ixxxxx = 1; ixxxxx < Game.getCivsSize(); ixxxxx++) {
            if (Game.getCiv(ixxxxx).getNumOfProvinces() > 0 && Game.getCiv(ixxxxx).getIdeologyID() == InGame_RightGovernment.iGovID) {
               this.lValues.add(new Graph_Vertical_Data(ixxxxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + CFG.getNumberWithSpaces("" + Graph_Vertical.this.lValues.get(i).getValue(j)),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + CFG.getShortNumber(Graph_Vertical.this.lValues.get(i).getValue())
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + CFG.getShortNumber(Math.max(1, Graph_Vertical.this.iValuesTotal)) + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.RELIGION_CIVS) {
         for (int ixxxxxx = 1; ixxxxxx < Game.getCivsSize(); ixxxxxx++) {
            for (int j = 0; j < Game.getCiv(ixxxxxx).getNumOfProvinces(); j++) {
               if (Game.getProvince(Game.getCiv(ixxxxxx).getProvinceID(j)).getReligion() == InGame_Civ_Religion.iReligionID) {
                  this.lValues.add(new Graph_Vertical_Data(ixxxxxx));
                  break;
               }
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + CFG.getNumberWithSpaces("" + Graph_Vertical.this.lValues.get(i).getValue(j)),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + CFG.getShortNumber(Graph_Vertical.this.lValues.get(i).getValue())
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + CFG.getShortNumber(Math.max(1, Graph_Vertical.this.iValuesTotal)) + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.RELIGION_CIVS_RIGHT) {
         for (int ixxxxxx = 1; ixxxxxx < Game.getCivsSize(); ixxxxxx++) {
            for (int jx = 0; jx < Game.getCiv(ixxxxxx).getNumOfProvinces(); jx++) {
               if (Game.getProvince(Game.getCiv(ixxxxxx).getProvinceID(jx)).getReligion() == InGame_RightReligion.iReligionID) {
                  this.lValues.add(new Graph_Vertical_Data(ixxxxxx));
                  break;
               }
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + CFG.getNumberWithSpaces("" + Graph_Vertical.this.lValues.get(i).getValue(j)),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + CFG.getShortNumber(Graph_Vertical.this.lValues.get(i).getValue())
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + CFG.getShortNumber(Math.max(1, Graph_Vertical.this.iValuesTotal)) + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_INFRASTRUCTURE) {
         for (int ixxxxxx = 1; ixxxxxx < Game.getCivsSize(); ixxxxxx++) {
            if (Game.getCiv(ixxxxxx).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(ixxxxxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + Graph_Vertical.this.lValues.get(i).getValue(j),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + Graph_Vertical.this.lValues.get(i).getValue()
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + Graph_Vertical.this.iValuesTotal + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_CONSTRUCTED_BUILDINGS) {
         for (int ixxxxxxx = 1; ixxxxxxx < Game.getCivsSize(); ixxxxxxx++) {
            if (Game.getCiv(ixxxxxxx).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(ixxxxxxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + Graph_Vertical.this.lValues.get(i).getValue(j),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + Graph_Vertical.this.lValues.get(i).getValue()
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + Graph_Vertical.this.iValuesTotal + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_ECONOMY) {
         for (int ixxxxxxxx = 1; ixxxxxxxx < Game.getCivsSize(); ixxxxxxxx++) {
            if (Game.getCiv(ixxxxxxxx).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(ixxxxxxxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + Graph_Vertical.this.lValues.get(i).getValue(j),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  ""
                     + Graph_Vertical.this.lValues.get(i).getValue()
                     + " ["
                     + CFG.getPercentage(Graph_Vertical.this.lValues.get(i).getValue(), Graph_Vertical.this.iValuesTotal, 3)
                     + "%]",
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + Graph_Vertical.this.iValuesTotal + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_UNLOCKED_TECHS) {
         for (int ixxxxxxxxx = 1; ixxxxxxxxx < Game.getCivsSize(); ixxxxxxxxx++) {
            if (Game.getCiv(ixxxxxxxxx).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(ixxxxxxxxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + Graph_Vertical.this.lValues.get(i).getValue(j),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  "" + Graph_Vertical.this.lValues.get(i).getValue(),
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + Graph_Vertical.this.iValuesTotal + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_PRESTIGE) {
         for (int ixxxxxxxxxx = 1; ixxxxxxxxxx < Game.getCivsSize(); ixxxxxxxxxx++) {
            if (Game.getCiv(ixxxxxxxxxx).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(ixxxxxxxxxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + Graph_Vertical.this.lValues.get(i).getValue(j),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  "" + Graph_Vertical.this.lValues.get(i).getValue(),
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + Graph_Vertical.this.iValuesTotal + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.RESOURCE_PRODUCTION) {
         for (int ixxxxxxxxxxx = 1; ixxxxxxxxxxx < Game.getCivsSize(); ixxxxxxxxxxx++) {
            if (Game.getCiv(ixxxxxxxxxxx).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(ixxxxxxxxxxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + Graph_Vertical.this.lValues.get(i).getValue(j),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  "" + Graph_Vertical.this.lValues.get(i).getValue(),
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + Graph_Vertical.this.iValuesTotal + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_REGIMENTS_LIMIT) {
         for (int ixxxxxxxxxxxx = 1; ixxxxxxxxxxxx < Game.getCivsSize(); ixxxxxxxxxxxx++) {
            if (Game.getCiv(ixxxxxxxxxxxx).getNumOfProvinces() > 0) {
               this.lValues.add(new Graph_Vertical_Data(ixxxxxxxxxxxx));
            }
         }

         this.iValuesSize = this.lValues.size();
         this.drawShort = true;
         this.drawStatisticsData = new Graph_Vertical.DrawStatisticsData() {
            @Override
            public void draw(SpriteBatch oSB, int i, int tempOffsetX, int iTranslateX, int iTranslateY) {
               for (int j = 0; j < Graph_Vertical.this.lValues.get(i).getValuesSize(); j++) {
                  Graph_Vertical.this.drawStatisticsValue(
                     oSB,
                     "" + Graph_Vertical.this.lValues.get(i).getValue(j),
                     Graph_Vertical.this.getPosX()
                        + (int)(CFG.TEXT_HEIGHT * 0.7F)
                        + CFG.PADDING * 2
                        + Graph_Vertical.this.getStatisticsWidth() * 2
                        + Graph_Vertical.this.getStatisticsWidth()
                           * Graph_Vertical.this.verticalInfo.getSortedID(Graph_Vertical.this.lValues.get(i).getValueDataTypeID(j))
                        + iTranslateX,
                     Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
                  );
               }

               Graph_Vertical.this.drawStatisticsValue(
                  oSB,
                  "" + Graph_Vertical.this.lValues.get(i).getValue(),
                  Graph_Vertical.this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
                  Graph_Vertical.this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY
               );
            }

            @Override
            public String getTotal() {
               return Graph_Vertical.this.sTotal + " [" + Graph_Vertical.this.iValuesTotal + "]";
            }

            @Override
            public String getStatsLP(int i) {
               return Game.getCiv(Graph_Vertical.this.lValues.get(i).getCivID()).getCivName();
            }

            @Override
            public int getStatsLPCivFlagID(int i) {
               return Graph_Vertical.this.lValues.get(i).getCivID();
            }
         };
      }

      this.iDataWidth = CFG.CIV_FLAG_WIDTH;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setVisible(visible);
      this.sTextX = sTextX;
      this.sTextY = sTextY;
      Renderer.fontMain.get(0).getData().setScale(0.7F);
      Renderer.glyphLayout.setText(Renderer.fontMain.get(0), sTextY);
      this.iWidthTextY = (int)Renderer.glyphLayout.width;
      Renderer.fontMain.get(0).getData().setScale(1.0F);
      this.buildData();
      this.buildValuesHeights();
      this.typeOfElement = MenuElement_Type.GRAPH_VERTICAL;
   }

   public final void buildData() {
      if (this.lValues.size() != 0) {
         List<String> nTexts = new ArrayList<>();
         List<Color> nColors = new ArrayList<>();
         if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.NUM_OF_PROVINCES_BY_CONTINENT) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData();
            }

            for (int i = 1; i < Game.continents.iContinentsSize; i++) {
               nTexts.add(Game.continents.lContinents.get(i).sName);
               nColors.add(
                  new Color(
                     Game.continents.lContinents.get(i).iR / 255.0F,
                     Game.continents.lContinents.get(i).iG / 255.0F,
                     Game.continents.lContinents.get(i).iB / 255.0F,
                     1.0F
                  )
               );
            }
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_POPULATION) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_Population();
            }

            for (int i = 1; i < Game.continents.iContinentsSize; i++) {
               nTexts.add(Game.continents.lContinents.get(i).sName);
               nColors.add(
                  new Color(
                     Game.continents.lContinents.get(i).iR / 255.0F,
                     Game.continents.lContinents.get(i).iG / 255.0F,
                     Game.continents.lContinents.get(i).iB / 255.0F,
                     1.0F
                  )
               );
            }
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_LIST_PROVINCES) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_CivsProvinces();
            }

            nTexts.add(Game.lang.get("Provinces"));
            nColors.add(Colors.HOVER_GOLD);
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_LIST_POPULATION) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_CivsPopulation();
            }

            nTexts.add(Game.lang.get("Population"));
            nColors.add(Colors.COLOR_POPULATION);
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.GOVERNMENTS_CIVS) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_GovernmentCivs();
            }

            nTexts.add(Game.lang.get("Population"));
            nColors.add(
               new Color(
                  Game.ideologiesManager.getIdeology(InGame_Civ_Government.iGovID).Color[0],
                  Game.ideologiesManager.getIdeology(InGame_Civ_Government.iGovID).Color[1],
                  Game.ideologiesManager.getIdeology(InGame_Civ_Government.iGovID).Color[2],
                  1.0F
               )
            );
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.GOVERNMENTS_CIVS_RIGHT) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_GovernmentCivs();
            }

            nTexts.add(Game.lang.get("Population"));
            nColors.add(
               new Color(
                  Game.ideologiesManager.getIdeology(InGame_RightGovernment.iGovID).Color[0],
                  Game.ideologiesManager.getIdeology(InGame_RightGovernment.iGovID).Color[1],
                  Game.ideologiesManager.getIdeology(InGame_RightGovernment.iGovID).Color[2],
                  1.0F
               )
            );
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.RELIGION_CIVS) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_ReligionCivs();
            }

            nTexts.add(Game.lang.get("Population"));
            nColors.add(
               new Color(
                  Game.religionManager.getReligion(InGame_Civ_Religion.iReligionID).Color[0],
                  Game.religionManager.getReligion(InGame_Civ_Religion.iReligionID).Color[1],
                  Game.religionManager.getReligion(InGame_Civ_Religion.iReligionID).Color[2],
                  1.0F
               )
            );
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.RELIGION_CIVS_RIGHT) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_ReligionCivs_Right();
            }

            nTexts.add(Game.lang.get("Population"));
            nColors.add(
               new Color(
                  Game.religionManager.getReligion(InGame_RightReligion.iReligionID).Color[0],
                  Game.religionManager.getReligion(InGame_RightReligion.iReligionID).Color[1],
                  Game.religionManager.getReligion(InGame_RightReligion.iReligionID).Color[2],
                  1.0F
               )
            );
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_CONSTRUCTED_BUILDINGS) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_ConstructedBuildings();
            }

            for (int i = 1; i < Game.continents.iContinentsSize; i++) {
               nTexts.add(Game.continents.lContinents.get(i).sName);
               nColors.add(
                  new Color(
                     Game.continents.lContinents.get(i).iR / 255.0F,
                     Game.continents.lContinents.get(i).iG / 255.0F,
                     Game.continents.lContinents.get(i).iB / 255.0F,
                     1.0F
                  )
               );
            }
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_INFRASTRUCTURE) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_Infrastructure();
            }

            for (int i = 1; i < Game.continents.iContinentsSize; i++) {
               nTexts.add(Game.continents.lContinents.get(i).sName);
               nColors.add(
                  new Color(
                     Game.continents.lContinents.get(i).iR / 255.0F,
                     Game.continents.lContinents.get(i).iG / 255.0F,
                     Game.continents.lContinents.get(i).iB / 255.0F,
                     1.0F
                  )
               );
            }
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_ECONOMY) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_Economy();
            }

            for (int i = 1; i < Game.continents.iContinentsSize; i++) {
               nTexts.add(Game.continents.lContinents.get(i).sName);
               nColors.add(
                  new Color(
                     Game.continents.lContinents.get(i).iR / 255.0F,
                     Game.continents.lContinents.get(i).iG / 255.0F,
                     Game.continents.lContinents.get(i).iB / 255.0F,
                     1.0F
                  )
               );
            }
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_UNLOCKED_TECHS) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_UnlockedTechnologies();
            }

            nTexts.add(Game.lang.get("UnlockedTechnologies"));
            nColors.add(Colors.HOVER_GOLD);
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_PRESTIGE) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_Prestige();
            }

            nTexts.add(Game.lang.get("Prestige"));
            nColors.add(Colors.TECH_BLUE2);
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.RESOURCE_PRODUCTION) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_ResourceProduction();
            }

            nTexts.add(Game.lang.get("Production"));
            nColors.add(Colors.TECH_BLUE2);
         } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_REGIMENTS_LIMIT) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).buildContinentData_RegimentsLimit();
            }

            nTexts.add(Game.lang.get("RegimentsLimit"));
            nColors.add(Colors.TECH_BLUE);
         }

         this.verticalInfo = new Graph_Vertical_Info(nTexts, nColors, this.getWidth() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2), true);
         List<Graph_Vertical_Data> tempData = new ArrayList<>();

         for (int i = 0; i < this.iValuesSize; i++) {
            tempData.add(this.lValues.get(i));
         }

         this.lValues.clear();

         while (tempData.size() > 0) {
            int tempMaxID = 0;

            for (int i = 1; i < tempData.size(); i++) {
               if (tempData.get(i).getValue() > tempData.get(tempMaxID).getValue()) {
                  tempMaxID = i;
               }
            }

            this.lValues.add(tempData.get(tempMaxID));
            tempData.remove(tempMaxID);
         }

         try {
            this.iMinPoint = this.iMaxPoint = this.lValues.get(0).getValue();
         } catch (IndexOutOfBoundsException var8) {
            this.iMinPoint = 0;
         }

         this.fAvaragePoint = 0.0F;
         long tempAvarage = 0L;
         int tempAvarageSize = 0;

         for (int ix = 0; ix < this.iValuesSize; ix++) {
            if (this.iMaxPoint < this.lValues.get(ix).getValue()) {
               this.iMaxPoint = this.lValues.get(ix).getValue();
            }

            if (this.iMinPoint > this.lValues.get(ix).getValue()) {
               this.iMinPoint = this.lValues.get(ix).getValue();
            }

            if (this.lValues.get(ix).getValue() > 0) {
               tempAvarageSize++;
               tempAvarage += this.lValues.get(ix).getValue();
            }
         }

         this.fAvaragePoint = (float)tempAvarage / tempAvarageSize;
         this.iAvaragePosY = (int)(
            this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2
               - (this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2)
                  * (100.0F * this.fAvaragePoint)
                  / (this.iMaxPoint - this.iMinPoint)
                  / 100.0F
         );
         this.roundAverage();
         this.updateMoveable();
         this.updateInView();
         this.countValuesTotal();
      }
   }

   public final void countValuesTotal() {
      this.iValuesTotal = 0;

      for (int i = 0; i < this.iValuesSize; i++) {
         this.iValuesTotal = this.iValuesTotal + this.lValues.get(i).getValue();
      }
   }

   public final void buildValuesHeights() {
      for (int i = 0; i < this.iValuesSize; i++) {
         this.lValues
            .get(i)
            .buildHeights(this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2 - (CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2), this.iMaxPoint);
      }
   }

   @Override
   public void updateHover(int nPosX, int nPosY, int menuPosX, int menuPosY) {
      if (!this.statisticsMode) {
         if (nPosX >= menuPosX + this.getPosX()
            && nPosX <= menuPosX + this.getPosX() + this.getWidth()
            && nPosY >= menuPosY + this.getPosY()
            && nPosY <= menuPosY + this.getPosY() + this.getHeight()) {
            for (int i = 0; i < this.iValuesSize; i++) {
               if (nPosX
                     >= menuPosX + this.getPosX() + this.getWidth() - CFG.PADDING * (i + 1) - CFG.PADDING * i - this.iDataWidth * (i + 1) + this.iButtonsPosX
                  && nPosX
                     <= menuPosX
                        + this.getPosX()
                        + this.getWidth()
                        - CFG.PADDING * (i + 1)
                        - CFG.PADDING * i
                        - this.iDataWidth * (i + 1)
                        + this.iButtonsPosX
                        + this.iDataWidth) {
                  this.setHoveredID(i);
                  return;
               }
            }
         }

         this.setHoveredID(-1);
      } else {
         if (nPosX >= menuPosX + this.getPosX()
            && nPosX <= menuPosX + this.getPosX() + this.getWidth()
            && nPosY >= menuPosY + this.getPosY()
            && nPosY <= menuPosY + this.getPosY() + this.getHeight()) {
            for (int ix = 0; ix < this.iValuesSize; ix++) {
               if (nPosY >= menuPosY + this.getPosY() + this.iButtonsPosY + (int)(CFG.TEXT_HEIGHT * 0.7F + CFG.PADDING * 2) * ix
                  && nPosY
                     <= menuPosY
                        + this.getPosY()
                        + this.iButtonsPosY
                        + (int)(CFG.TEXT_HEIGHT * 0.7F + CFG.PADDING * 2) * ix
                        + (int)(CFG.TEXT_HEIGHT * 0.7F + CFG.PADDING * 2)) {
                  this.setHoveredID(ix);
                  return;
               }
            }
         }

         this.setHoveredID(-1);
      }
   }

   private final void setHoveredID(int nHoveredID) {
      if (this.iHoveredID != nHoveredID) {
         this.iHoveredID = nHoveredID;
         this.buildElementHover();
      }
   }

   @Override
   public void buildElementHover() {
      if (this.iHoveredID < 0 || this.statisticsMode) {
         this.menuElementHover = null;
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.NUM_OF_PROVINCES_BY_CONTINENT) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Provinces") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getNumOfProvinces()),
               Images.provinces,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_POPULATION) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Population") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getPopulationTotal()),
               Images.population,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_LIST_PROVINCES) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Provinces") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getNumOfProvinces()),
               Images.provinces,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_LIST_POPULATION) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Population") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getPopulationTotal()),
               Images.population,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.GOVERNMENTS_CIVS || this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.GOVERNMENTS_CIVS_RIGHT) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Population") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getPopulationTotal()),
               Images.population,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.RELIGION_CIVS || this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.RELIGION_CIVS_RIGHT) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Population") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getPopulationTotal()),
               Images.population,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_CONSTRUCTED_BUILDINGS) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("ConstructedBuildings") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getConstructedBuildings()),
               Images.buildings,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_INFRASTRUCTURE) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Infrastructure") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getInfrastructure()),
               Images.infrastructure,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_UNLOCKED_TECHS) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("UnlockedTechnologies") + ": ",
               "" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getResearchedTechnologies(),
               Game_Calendar.IMG_TECHNOLOGY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_PRESTIGE) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Prestige") + ": ",
               "" + CFG.getNumberWithSpaces("" + (int)Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).iCivRankScore),
               CivilizationRanking.getCivilizationRanking_IMG_STAR_CIVID(this.lValues.get(this.iHoveredID).getCivID()),
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.RESOURCE_PRODUCTION) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Production") + ": ",
               ""
                  + CFG.getNumberWithSpaces(
                     ""
                        + (int)ResourcesManager.getProducedGoods_ResourceCiv(
                           this.lValues.get(this.iHoveredID).getCivID(), InGame_Goods_LargestProducers.RESOURCE_ID
                        )
                  ),
               Images.goods,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_REGIMENTS_LIMIT) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("RegimentsLimit") + ": ",
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).iRegimentsLimit),
               Images.regimentsLimit,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else if (this.GRAPH_DATA_TYPE == Graph_Vertical_Data_Type.CIVS_ECONOMY) {
         List<MenuElement_HoverElement> nElements = new ArrayList<>();
         List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
         nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getCivName()));
         nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.lValues.get(this.iHoveredID).getCivID(), CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(
            new MenuElement_HoverElement_Type_Button_TextBonus(
               Game.lang.get("Economy") + ": ",
               "" + CFG.getNumberWithSpaces("" + (int)Game.getCiv(this.lValues.get(this.iHoveredID).getCivID()).getEconomyTotal()),
               Game_Calendar.IMG_ECONOMY,
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_LEFT,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         this.menuElementHover = new MenuElement_Hover(nElements);
      } else {
         this.menuElementHover = null;
      }
   }

   @Override
   public void setCheckboxState(boolean checkboxState) {
      this.buildValuesHeights();
      this.updateInView();
      this.verticalInfo.updateMoveable(this.getWidth() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2));
      this.updateMoveable();
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      if (this.scrollModeY) {
         if (this.statisticsMode) {
            if (Math.abs(this.fScrollNewMenuPosY) > 1.0F) {
               this.setCurrent(this.iButtonsPosY + (int)this.fScrollNewMenuPosY);
               this.fScrollNewMenuPosY *= 0.97F;
            } else {
               this.scrollModeY = false;
            }
         } else if (Math.abs(this.fScrollNewMenuPosY) > 1.0F) {
            this.setCurrent(this.iButtonsPosX + (int)this.fScrollNewMenuPosY);
            this.fScrollNewMenuPosY *= 0.97F;
         } else {
            this.scrollModeY = false;
         }
      }

      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.9F));
      ImageManager.getImage(Images.graphBG)
         .draw2(
            oSB,
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2,
            this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2
         );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.3F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2,
            this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2,
            false,
            true
         );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.3F));
      Images.gradientXY
         .draw(
            oSB,
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2,
            this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2,
            false,
            true
         );
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.065F));
      ImageManager.getImage(Images.noise)
         .draw2(
            oSB,
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + 1 + iTranslateY,
            this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2,
            this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2
         );
      Renderer.fontMain.get(0).getData().setScale(0.7F);
      Renderer.drawTextRotated(
         oSB,
         this.sTextY,
         this.getPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + this.getHeight() / 2 + this.iWidthTextY / 2 + iTranslateY,
         Graph.TEXT_COLOR,
         90.0F
      );
      this.verticalInfo
         .draw(
            oSB,
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING - (int)(CFG.TEXT_HEIGHT * 0.7F) + iTranslateY,
            this.getWidth() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2)
         );
      Renderer.fontMain.get(0).getData().setScale(1.0F);
      if (this.statisticsMode) {
         this.drawStatisticsBegan(oSB, 1 + iTranslateX, iTranslateY, isActive, scrollableY);
      } else {
         Images.pix
            .draw(
               oSB,
               this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + 1 + iTranslateX,
               this.getPosY()
                  + (this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) - CFG.CIV_FLAG_HEIGHT - CFG.PADDING * 2) / 2
                  + CFG.CIV_FLAG_HEIGHT
                  + CFG.PADDING * 2
                  + 1
                  + iTranslateY,
               CFG.PADDING - 1
            );
         oSB.setColor(Graph.GRAPH_LINES_DESC);
         ImageManager.getImage(Images.line_33)
            .draw2(
               oSB,
               this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
               this.getPosY() + CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2 + 1 + iTranslateY,
               this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2
            );
         ImageManager.getImage(Images.line_33)
            .draw2(
               oSB,
               this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
               this.getPosY() + this.iAvaragePosY + iTranslateY,
               this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2,
               1,
               0,
               -this.iButtonsPosX
            );
         Renderer.clipView_Start(
            oSB,
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + 1 + iTranslateX,
            CFG.GAME_HEIGHT - this.getPosY() - iTranslateY,
            this.getWidth() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) - 1,
            -this.getHeight()
         );
         if (this.getIsHovered() && this.iHoveredID >= 0) {
            oSB.setColor(Graph.GRAPH_LINES_DESC);
            ImageManager.getImage(Images.gradientVertical)
               .draw(
                  oSB,
                  this.getPosX()
                     + this.getWidth()
                     - 1
                     - CFG.PADDING * (this.iHoveredID + 1)
                     - CFG.PADDING * this.iHoveredID
                     - this.iDataWidth * (this.iHoveredID + 1)
                     + this.iButtonsPosX
                     + iTranslateX,
                  this.getPosY() + iTranslateY,
                  this.iDataWidth + 2,
                  this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2),
                  false,
                  true
               );
            oSB.setColor(new Color(Graph.GRAPH_LINES_DESC.r, Graph.GRAPH_LINES_DESC.g, Graph.GRAPH_LINES_DESC.b, 0.025F));
            ImageManager.getImage(Images.gradientHorizontal)
               .draw(
                  oSB,
                  this.getPosX()
                     + this.getWidth()
                     - 1
                     - CFG.PADDING * (this.iHoveredID + 1)
                     - CFG.PADDING * this.iHoveredID
                     - this.iDataWidth * (this.iHoveredID + 1)
                     + this.iButtonsPosX
                     + iTranslateX,
                  this.getPosY() + iTranslateY,
                  (this.iDataWidth + 2) / 4,
                  this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2),
                  false,
                  false
               );
            ImageManager.getImage(Images.gradientHorizontal)
               .draw(
                  oSB,
                  this.getPosX()
                     + this.getWidth()
                     + this.iDataWidth
                     + 2
                     - (this.iDataWidth + 2) / 4
                     - 1
                     - CFG.PADDING * (this.iHoveredID + 1)
                     - CFG.PADDING * this.iHoveredID
                     - this.iDataWidth * (this.iHoveredID + 1)
                     + this.iButtonsPosX
                     + iTranslateX,
                  this.getPosY() + iTranslateY,
                  (this.iDataWidth + 2) / 4,
                  this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2),
                  true,
                  false
               );
            oSB.setColor(Color.WHITE);
         }

         if (this.drawShort) {
            for (int i = 0; i < this.iValuesSize; i++) {
               if (this.lValues.get(i).getInView()) {
                  this.lValues
                     .get(i)
                     .drawData(
                        oSB,
                        this.getPosX()
                           + this.getWidth()
                           - CFG.PADDING * (i + 1)
                           - CFG.PADDING * i
                           - this.iDataWidth * (i + 1)
                           + this.iButtonsPosX
                           + iTranslateX,
                        this.getPosY() + iTranslateY,
                        this.iDataWidth,
                        this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2),
                        this.verticalInfo.getColors(),
                        this.verticalInfo.getSorted()
                     );
                  this.lValues
                     .get(i)
                     .drawDataTextValue_Short(
                        oSB,
                        this.getPosX()
                           + this.getWidth()
                           - CFG.PADDING * (i + 1)
                           - CFG.PADDING * i
                           - this.iDataWidth * (i + 1)
                           + this.iButtonsPosX
                           + iTranslateX,
                        this.getPosY() + iTranslateY,
                        this.iDataWidth,
                        this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2)
                     );
               }
            }
         } else if (this.splitBy100) {
            for (int ix = 0; ix < this.iValuesSize; ix++) {
               if (this.lValues.get(ix).getInView()) {
                  this.lValues
                     .get(ix)
                     .drawData(
                        oSB,
                        this.getPosX()
                           + this.getWidth()
                           - CFG.PADDING * (ix + 1)
                           - CFG.PADDING * ix
                           - this.iDataWidth * (ix + 1)
                           + this.iButtonsPosX
                           + iTranslateX,
                        this.getPosY() + iTranslateY,
                        this.iDataWidth,
                        this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2),
                        this.verticalInfo.getColors(),
                        this.verticalInfo.getSorted()
                     );
                  this.lValues
                     .get(ix)
                     .drawDataTextValue_Splitted(
                        oSB,
                        this.getPosX()
                           + this.getWidth()
                           - CFG.PADDING * (ix + 1)
                           - CFG.PADDING * ix
                           - this.iDataWidth * (ix + 1)
                           + this.iButtonsPosX
                           + iTranslateX,
                        this.getPosY() + iTranslateY,
                        this.iDataWidth,
                        this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2)
                     );
               }
            }
         } else {
            for (int ixx = 0; ixx < this.iValuesSize; ixx++) {
               if (this.lValues.get(ixx).getInView()) {
                  this.lValues
                     .get(ixx)
                     .drawData(
                        oSB,
                        this.getPosX()
                           + this.getWidth()
                           - CFG.PADDING * (ixx + 1)
                           - CFG.PADDING * ixx
                           - this.iDataWidth * (ixx + 1)
                           + this.iButtonsPosX
                           + iTranslateX,
                        this.getPosY() + iTranslateY,
                        this.iDataWidth,
                        this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2),
                        this.verticalInfo.getColors(),
                        this.verticalInfo.getSorted()
                     );
                  this.lValues
                     .get(ixx)
                     .drawDataTextValue(
                        oSB,
                        this.getPosX()
                           + this.getWidth()
                           - CFG.PADDING * (ixx + 1)
                           - CFG.PADDING * ixx
                           - this.iDataWidth * (ixx + 1)
                           + this.iButtonsPosX
                           + iTranslateX,
                        this.getPosY() + iTranslateY,
                        this.iDataWidth,
                        this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2)
                     );
               }
            }
         }

         Renderer.clipView_End(oSB);
         Renderer.fontMain.get(0).getData().setScale(Graph.POINTS_TEXT_SCALE);
         Renderer.drawText(
            oSB,
            "" + CFG.getNumberWithSpaces("" + this.iMaxPoint),
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + 1 + (int)(2.0F * CFG.GUI_SCALE) + iTranslateX,
            this.getPosY() + CFG.CIV_FLAG_HEIGHT + CFG.PADDING * 2 + 1 + iTranslateY - (int)(2.0F * CFG.GUI_SCALE + Graph.POINTS_TEXT_SCALE * CFG.TEXT_HEIGHT),
            Graph.DATA_COLOR
         );
         Renderer.fontMain.get(0).getData().setScale(1.0F);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
      Images.pix
         .draw(
            oSB,
            this.getPosX() - 1 + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            1,
            this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2 + CFG.PADDING
         );
      Images.pix
         .draw(
            oSB,
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 - CFG.PADDING + iTranslateX,
            this.getPosY() + this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2 + iTranslateY,
            this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2 + CFG.PADDING,
            1
         );
      oSB.setColor(Graph.GRAPH_BORDERS_COLOR);
      Images.pix
         .draw(
            oSB,
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            1,
            this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2 + CFG.PADDING
         );
      Images.pix
         .draw(
            oSB,
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 - CFG.PADDING + iTranslateX,
            this.getPosY() + this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2 + iTranslateY,
            this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2 + CFG.PADDING,
            1
         );
      Images.pix.draw(oSB, this.getPosX() + this.getWidth() - CFG.PADDING + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING);
      Images.pix.draw(oSB, this.getPosX() - 1 + this.getWidth() + iTranslateX, this.getPosY() + iTranslateY, 1, CFG.PADDING - 1);
      Images.pix.draw(oSB, this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING - 1);
      Images.pix
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - 1 + iTranslateX,
            this.getPosY() + this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) - CFG.PADDING + iTranslateY,
            1,
            CFG.PADDING - 1
         );
      oSB.setColor(Color.WHITE);
   }

   private final void drawStatisticsBegan(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      Renderer.fontMain.get(0).getData().setScale(0.7F);
      int tempOffsetX = 0;
      this.drawStatisticsBoxTitle(
         oSB,
         this.sTextX,
         this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getStatisticsWidth() * 2
      );
      tempOffsetX += this.getStatisticsWidth() * 2;

      for (int i = 0; i < this.verticalInfo.getTextSize(); i++) {
         this.drawStatisticsBoxTitle(
            oSB,
            this.verticalInfo.getText(i),
            this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getStatisticsWidth()
         );
         tempOffsetX += this.getStatisticsWidth();
      }

      this.drawStatisticsBoxTitle(
         oSB,
         this.drawStatisticsData.getTotal(),
         this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + tempOffsetX + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth() - tempOffsetX - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2)
      );
      Renderer.clipView_Start(
         oSB,
         this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + 1 + iTranslateX,
         CFG.GAME_HEIGHT - this.getPosY() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) - 2 - iTranslateY,
         this.getWidth() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) - 1,
         -this.getHeight() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * 2 + 1
      );
      this.drawStatisticsEnd(oSB, iTranslateX, this.iButtonsPosY + iTranslateY, isActive, scrollableY, tempOffsetX);
      Renderer.clipView_End(oSB);
      oSB.setColor(Graph.GRAPH_LINES_COLOR);

      for (int i = -1; i < this.verticalInfo.getTextSize() - 1; i++) {
         ImageManager.getImage(Images.line_32_vertical)
            .draw2(
               oSB,
               this.getPosX()
                  + (int)(CFG.TEXT_HEIGHT * 0.7F)
                  + CFG.PADDING * 2
                  + this.getStatisticsWidth() * 2
                  + this.getStatisticsWidth() * (i + 1)
                  - 1
                  + iTranslateX,
               this.getPosY() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateY,
               1,
               this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * 2,
               false,
               true
            );
      }

      Images.pix
         .draw(
            oSB,
            this.getPosX()
               + (int)(CFG.TEXT_HEIGHT * 0.7F)
               + CFG.PADDING * 2
               + this.getStatisticsWidth() * 2
               + this.getStatisticsWidth() * (this.verticalInfo.getTextSize() - 1 + 1)
               - 1
               + iTranslateX,
            this.getPosY() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateY,
            1,
            this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * 2,
            false,
            true
         );
      oSB.setColor(Color.WHITE);
   }

   private final void drawStatisticsEnd(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY, int tempOffsetX) {
      float tempFlagScale = CFG.TEXT_HEIGHT * 0.7F / CFG.CIV_FLAG_HEIGHT;

      for (int i = 0; i < this.iValuesSize; i++) {
         if (this.lValues.get(i).getInView()) {
            if (i % 2 == 0) {
               this.drawStatisticsRowBG(
                  oSB,
                  this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
                  this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY,
                  this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2
               );
            }

            if (this.getIsHovered() && i == this.iHoveredID - 1) {
               this.drawStatisticsRowHoverBG(
                  oSB,
                  this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
                  this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY,
                  this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2
               );
            }

            this.drawStatisticsRowLine(
               oSB,
               this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
               this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY,
               this.getWidth() - (int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2
            );
            oSB.setColor(Color.WHITE);
            Game.getCiv(this.drawStatisticsData.getStatsLPCivFlagID(i))
               .getFlag()
               .draw(
                  oSB,
                  this.getPosX() + (int)(CFG.TEXT_HEIGHT * 0.7F + CFG.CIV_COLOR_WIDTH) + CFG.PADDING * 2 + iTranslateX,
                  this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + CFG.PADDING + iTranslateY,
                  (int)Math.ceil(CFG.CIV_FLAG_WIDTH * tempFlagScale),
                  (int)Math.ceil(CFG.CIV_FLAG_HEIGHT * tempFlagScale)
               );
            this.drawStatisticsValue2(
               oSB,
               this.drawStatisticsData.getStatsLP(i),
               this.getPosX() + (int)(CFG.CIV_FLAG_WIDTH * tempFlagScale + CFG.CIV_COLOR_WIDTH) + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + iTranslateX,
               this.getPosY() + ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * (i + 1) + iTranslateY,
               this.getStatisticsWidth() * 2 - (int)(CFG.CIV_FLAG_WIDTH * tempFlagScale + CFG.CIV_COLOR_WIDTH)
            );
            this.drawStatisticsData.draw(oSB, i, tempOffsetX, iTranslateX, iTranslateY);
         }
      }

      Renderer.fontMain.get(0).getData().setScale(1.0F);
   }

   private final void drawStatisticsRowLine(SpriteBatch oSB, int nPosX, int nPosY, int nWidth) {
      oSB.setColor(Graph.GRAPH_LINES_COLOR);
      Images.line_32_off1.draw(oSB, nPosX, nPosY + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2, nWidth, 1);
   }

   private final void drawStatisticsRowBG(SpriteBatch oSB, int nPosX, int nPosY, int nWidth) {
      oSB.setColor(new Color(0.05F, 0.05F, 0.05F, 0.7F));
      Images.pix.draw(oSB, nPosX, nPosY, nWidth - 1, (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2);
   }

   private final void drawStatisticsRowHoverBG(SpriteBatch oSB, int nPosX, int nPosY, int nWidth) {
      oSB.setColor(new Color(Graph.GRAPH_LINES_DESC.r, Graph.GRAPH_LINES_DESC.g, Graph.GRAPH_LINES_DESC.b, 0.1F));
      ImageManager.getImage(Images.gradientHorizontal).draw(oSB, nPosX, nPosY, nWidth - 1, (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2);
   }

   private final void drawStatisticsBoxTitle(SpriteBatch oSB, String nText, int nPosX, int nPosY, int nWidth) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.7F));
      Images.pix.draw(oSB, nPosX, nPosY, nWidth, (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.6F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            nPosX,
            nPosY + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) / 2,
            nWidth,
            ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) / 2,
            false,
            true
         );
      oSB.setColor(Graph.GRAPH_BORDERS_COLOR);
      Images.pix.draw(oSB, nPosX, nPosY + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2, nWidth, 1);
      oSB.setColor(Graph.GRAPH_LINES_COLOR);
      ImageManager.getImage(Images.line_32_vertical).draw2(oSB, nPosX - 1 + nWidth, nPosY, 1, (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2);
      Renderer.clipView_Start(oSB, nPosX, CFG.GAME_HEIGHT - nPosY, nWidth - CFG.PADDING, -((int)(CFG.TEXT_HEIGHT * 0.7F)) - CFG.PADDING * 2);
      Renderer.drawText(oSB, nText, nPosX + CFG.PADDING, nPosY + CFG.PADDING, Graph.TEXT_COLOR);
      Renderer.clipView_End(oSB);
   }

   private final void drawStatisticsValue(SpriteBatch oSB, String nText, int nPosX, int nPosY) {
      Renderer.drawText(oSB, nText, nPosX + CFG.PADDING, nPosY + CFG.PADDING, new Color(1.0F, 1.0F, 1.0F, 0.45F));
   }

   private final void drawStatisticsValueWithFlag(SpriteBatch oSB, String nText, int nCivID, int nPosX, int nPosY) {
      float tempFlagScale = CFG.TEXT_HEIGHT * 0.7F / CFG.CIV_FLAG_HEIGHT;
      oSB.setColor(Color.WHITE);
      Game.getCiv(nCivID)
         .getFlag()
         .draw(
            oSB,
            nPosX + CFG.PADDING,
            nPosY + CFG.PADDING,
            (int)Math.ceil(CFG.CIV_FLAG_WIDTH * tempFlagScale),
            (int)Math.ceil(CFG.CIV_FLAG_HEIGHT * tempFlagScale)
         );
      Renderer.drawText(
         oSB, nText, nPosX + CFG.PADDING * 2 + (int)(CFG.CIV_FLAG_WIDTH * tempFlagScale), nPosY + CFG.PADDING, new Color(1.0F, 1.0F, 1.0F, 0.45F)
      );
   }

   private final void drawStatisticsValue2(SpriteBatch oSB, String nText, int nPosX, int nPosY, int nWidth) {
      Renderer.clipView_Start(oSB, nPosX, CFG.GAME_HEIGHT - nPosY, nWidth - CFG.PADDING, -this.getHeight());
      Renderer.drawText(oSB, nText, nPosX + CFG.CIV_COLOR_WIDTH, nPosY + CFG.PADDING, Graph.DATA_COLOR);
      Renderer.clipView_End(oSB);
   }

   private final int getStatisticsWidth() {
      return (this.getWidth() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2)) / (this.verticalInfo.getTextSize() + 3);
   }

   public final void updateInView() {
      if (this.statisticsMode) {
         for (int i = 0; i < this.iValuesSize; i++) {
            if (this.getButtonsPosY(i) + this.iButtonsPosY >= (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2
               && this.getButtonsPosY(i) + this.iButtonsPosY <= this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * 2) {
               this.lValues.get(i).setInView(true);
            } else if (this.getButtonsPosY(i) + this.iButtonsPosY + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 >= 0
               && this.getButtonsPosY(i) + (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2 + this.iButtonsPosY
                  <= this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * 2) {
               this.lValues.get(i).setInView(true);
            } else {
               this.lValues.get(i).setInView(false);
            }
         }
      } else {
         for (int ix = 0; ix < this.iValuesSize; ix++) {
            if (this.getButtonsPosX(ix) + this.iButtonsPosX >= (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2
               && this.getButtonsPosX(ix) + this.iButtonsPosX <= this.getWidth()) {
               this.lValues.get(ix).setInView(true);
            } else if (this.getButtonsPosX(ix) - this.iDataWidth + this.iButtonsPosX >= (int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2
               && this.getButtonsPosX(ix) - this.iDataWidth + this.iButtonsPosX <= this.getWidth()) {
               this.lValues.get(ix).setInView(true);
            } else {
               this.lValues.get(ix).setInView(false);
            }
         }
      }
   }

   private final int getButtonsPosX(int i) {
      return this.getWidth() - this.iDataWidth * i - CFG.PADDING - CFG.PADDING * 2 * i;
   }

   private final int getButtonsPosY(int i) {
      return (int)(CFG.TEXT_HEIGHT * 0.7F + CFG.PADDING * 2) * i;
   }

   private final void updateMoveable() {
      if (this.statisticsMode) {
         if (this.getButtonsPosY(this.iValuesSize) > this.getHeight() - (int)(CFG.TEXT_HEIGHT * 0.7F + CFG.PADDING * 2) * 2) {
            this.moveable = true;
         } else {
            this.moveable = false;
            this.iButtonsPosY = 0;
         }
      } else if (this.getButtonsWidth() > this.getWidth() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2)) {
         this.moveable = true;
      } else {
         this.moveable = false;
         this.iButtonsPosX = 0;
      }
   }

   @Override
   public boolean getScrollable() {
      return this.moveable;
   }

   @Override
   public void scrollByWheel(int nScoll) {
      this.scrollModeY = false;
      this.setCurrent(this.getCurrent() + nScoll);
   }

   private final int getButtonsWidth() {
      return this.iDataWidth * this.iValuesSize + CFG.PADDING * 2 * (this.iValuesSize - 1);
   }

   private final void roundAverage() {
      if (this.fAvaragePoint - (int)this.fAvaragePoint != 0.0F) {
         this.bDecimal = (byte)Math.round((this.fAvaragePoint - (int)this.fAvaragePoint) * 100.0F);
         this.fAvaragePoint = this.fAvaragePoint - (this.fAvaragePoint - (int)this.fAvaragePoint);
         this.lessThanTen = false;
         if (this.bDecimal % 10 == 0) {
            this.bDecimal = (byte)(this.bDecimal / 10);
         } else if (this.bDecimal < 10) {
            this.lessThanTen = true;
         }
      } else {
         this.bDecimal = 0;
      }
   }

   @Override
   public boolean getMoveable() {
      return this.moveable;
   }

   @Override
   public int getCurrent() {
      return this.statisticsMode ? this.iButtonsPosY : this.iButtonsPosX;
   }

   @Override
   public void setCurrent(int nButtonsPosX) {
      if (this.statisticsMode) {
         if (nButtonsPosX > 0) {
            nButtonsPosX = 0;
            Game.menuManager.setUpdateSliderMenuPosY(true);
            this.scrollModeY = false;
         } else if (nButtonsPosX
            < -((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * this.iValuesSize
               + (this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * 2)) {
            nButtonsPosX = -((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * this.iValuesSize
               + (this.getHeight() - ((int)(CFG.TEXT_HEIGHT * 0.7F) + CFG.PADDING * 2) * 2);
            Game.menuManager.setUpdateSliderMenuPosY(true);
            this.scrollModeY = false;
         }

         if (this.iButtonsPosY != nButtonsPosX) {
            this.iButtonsPosY = nButtonsPosX;
            this.updateInView();
         }
      } else {
         if (nButtonsPosX < 0) {
            nButtonsPosX = 0;
            Game.menuManager.setUpdateSliderMenuPosX(true);
            this.scrollModeY = false;
         } else if (nButtonsPosX > this.getButtonsWidth() - this.getWidth() + this.iDataWidth + CFG.PADDING - ((int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2)
            )
          {
            nButtonsPosX = this.getButtonsWidth() - this.getWidth() + this.iDataWidth + CFG.PADDING - ((int)(CFG.TEXT_HEIGHT * 0.7F) - CFG.PADDING * 2);
            Game.menuManager.setUpdateSliderMenuPosX(true);
            this.scrollModeY = false;
         }

         if (this.iButtonsPosX != nButtonsPosX) {
            this.iButtonsPosX = nButtonsPosX;
            this.updateInView();
         }
      }
   }

   @Override
   public final void scrollTheMenu() {
      if (this.moveable && this.iScrollPosX > 0 && this.iScrollPosX2 > 0 && Math.abs(this.iScrollPosX - this.iScrollPosX2) > 3.0F * CFG.DENSITY) {
         this.fScrollNewMenuPosY = (this.iScrollPosX - this.iScrollPosX2) * 1.25F;
         this.scrollModeY = true;
      }
   }

   @Override
   public final void setScrollPosY(int iScrollPosX) {
      this.iScrollPosX2 = this.iScrollPosX;
      this.iScrollPosX = iScrollPosX;
   }

   @Override
   public void stopScrolling() {
      this.iScrollPosX = this.iScrollPosX2 = -1;
      this.scrollModeY = false;
   }

   @Override
   public boolean getInStatisticsMode() {
      return this.statisticsMode;
   }

   @Override
   public void setInStatisticsMode(boolean inStatisticsMode) {
      if (this.allowStatisticsMode) {
         this.statisticsMode = inStatisticsMode;
         this.scrollModeY = false;
         this.iButtonsPosX = this.iButtonsPosY = 0;
         if (!this.statisticsMode) {
            for (int i = 0; i < this.iValuesSize; i++) {
               this.lValues.get(i).resetAnimation();
            }
         }

         this.updateMoveable();
         this.updateInView();
         this.setHoveredID(-1);
      }
   }

   @Override
   public void setVisible(boolean isVisible) {
      super.setVisible(isVisible);
      this.setHoveredID(-1);
   }

   interface DrawStatisticsData {
      void draw(SpriteBatch var1, int var2, int var3, int var4, int var5);

      String getTotal();

      String getStatsLP(int var1);

      int getStatsLPCivFlagID(int var1);
   }
}
