package mapeditor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class GamePanel extends JPanel implements Runnable {
   private Thread gameThread;
   private volatile boolean bRunning = false;
   private Image baseImage;
   private Graphics baseGraphic;
   public static int GWIDTH = 1920;
   public static int GHEIGHT = 1020;
   private static Dimension gameThreadDim;
   private static String fileName;
   private static String fileNameJson;
   private static String sBackgroundImage;
   private static final boolean bRepeatBackground = true;
   private static final boolean export = false;
   protected static boolean MAXIMUM_SIZE = true;
   private boolean bDrawProvinces = true;
   protected static boolean bDrawProvincePoints = true;
   protected static boolean bPrettyPrint = true;
   protected static boolean bDrawCities = false;
   protected static boolean bDrawCitiesNames = true;
   protected static boolean bDrawArmy = false;
   private boolean bChooseProvinceMode = false;
   private boolean bChangePointMode = false;
   private boolean bAddPointToProvinceMode = false;
   private boolean bRemovePointMode = false;
   private int iEndOfLandProvinces = -1;
   private static boolean CONVERT_AOH2_MAP_PROVINCES_DATA_TO_AOH3 = false;
   private List<Integer> iRemovedProvinceID = new ArrayList<>();
   private List<Image> mapEditorBackground = new ArrayList<>();
   private int iMapEditorBackgroundWidth;
   private int iMapEditorBackgroundHeight;
   private List<Image> mapEditorBackground2 = new ArrayList<>();
   private boolean drawBGMain = false;
   private int iHeightOfSingleBG = 1;
   private int iWidthOfSingleBG = 1;
   private int gameMapSize = 0;
   private int iXMapPos = 0;
   private int iYMapPos = 0;
   private float fMapScale = 1.0F;
   private int iShiftXY = 15;
   private int iActiveProvinceID = -1;
   private boolean bSelectedPointsToChangePosition = false;
   private int iChangePositionX = 0;
   private int iChangePositionY = 0;
   private boolean drawBG = true;
   private boolean drawNeighboring = false;
   private List<Button> lButtons = new ArrayList<>();
   private Vector vProvinces;
   private GeneralPath currentProvince = null;
   private int currentProvincePoints = 0;
   private GamePanel.NeighboringProvincesThread _NeighboringProvincesThread = null;
   private boolean neighboringProvincesReadyToUse = false;
   private List<List<Integer>> lProvincesPointsX = new ArrayList<>();
   private List<List<Integer>> lProvincesPointsY = new ArrayList<>();
   private List<Integer> lProvincesCenterX = new ArrayList<>();
   private List<Integer> lProvincesCenterY = new ArrayList<>();
   private List<Integer> lProvincesMinX = new ArrayList<>();
   private List<Integer> lProvincesMinY = new ArrayList<>();
   private List<Integer> lProvincesMaxX = new ArrayList<>();
   private List<Integer> lProvincesMaxY = new ArrayList<>();
   private List<Integer> currentPointsX = new ArrayList<>();
   private List<Integer> currentPointsY = new ArrayList<>();
   private static List<String> citiesNames = new ArrayList<>();
   private static List<Integer> citiesX = new ArrayList<>();
   private static List<Integer> citiesY = new ArrayList<>();
   private int iCurrentProvinceMinX;
   private int iCurrentProvinceMaxX;
   private int iCurrentProvinceMinY;
   private int iCurrentProvinceMaxY;
   private int iCurrentProvinceShiftArmyX = 0;
   private int iCurrentProvinceShiftArmyY = 0;
   private List<Integer> lContinent = new ArrayList<>();
   private List<Integer> pathR = new ArrayList<>();
   private List<Integer> pathG = new ArrayList<>();
   private List<Integer> pathB = new ArrayList<>();
   private List<List<Integer>> neighboring = new ArrayList<>();
   private int iLandProvinceAlpha = 120;
   private int iSeaProvinceAlpha = 80;
   private int iLastPressedPosX = 0;
   private int iLastPressedPosY = 0;
   private int iMousePosX = 0;
   private int iMousePosY = 0;
   private static final byte iPadding = 5;
   private List<Boolean> isInView = new ArrayList<>();
   private String topText = "Lukasz Jakowski Games";
   private String downText = "Age of History 3";
   private String shortcuts = "O, C, V, Z, L, P, M";
   public static String mapName;
   public static int BackgroundSize_X = 1;
   public static int BackgroundSize_Y = 1;
   public static float BackgroundScale = 1.0F;
   public static int provincesPerFile = 2000;
   boolean foundFirst = false;
   boolean forward = false;

   private final void resetModes() {
      this.bChangePointMode = false;
      this.bAddPointToProvinceMode = false;
      this.bRemovePointMode = false;
      this.bChooseProvinceMode = false;
      this.bSelectedPointsToChangePosition = false;
   }

   public final void clearCurrentData() {
      if (this.currentPointsX.size() > 1) {
         if (this.iRemovedProvinceID.size() > 0) {
            GeneralPath nPath = new GeneralPath();
            int i = 0;

            for (int nSize = this.currentPointsX.size(); i < nSize; i++) {
               if (i == 0) {
                  nPath.moveTo(this.currentPointsX.get(i).intValue(), this.currentPointsY.get(i).intValue());
               } else {
                  nPath.lineTo(this.currentPointsX.get(i).intValue(), this.currentPointsY.get(i).intValue());
               }
            }

            this.vProvinces.set(this.iRemovedProvinceID.get(0), nPath);
            List<Integer> tempX = new ArrayList<>();

            for (int ix = 0; ix < this.currentPointsX.size(); ix++) {
               tempX.add(this.currentPointsX.get(ix));
            }

            List<Integer> tempY = new ArrayList<>();

            for (int ix = 0; ix < this.currentPointsY.size(); ix++) {
               tempY.add(this.currentPointsY.get(ix));
            }

            this.lProvincesPointsX.set(this.iRemovedProvinceID.get(0), tempX);
            this.lProvincesPointsY.set(this.iRemovedProvinceID.get(0), tempY);
            int tempMinX = this.iMapEditorBackgroundWidth;
            int tempMaxX = -this.iMapEditorBackgroundWidth;
            int tempMinY = this.iMapEditorBackgroundHeight;
            int tempMaxY = -this.iMapEditorBackgroundHeight;

            for (int j = 0; j < this.lProvincesPointsX.get(this.iRemovedProvinceID.get(0)).size(); j++) {
               if (this.lProvincesPointsX.get(this.iRemovedProvinceID.get(0)).get(j) < tempMinX) {
                  tempMinX = this.lProvincesPointsX.get(this.iRemovedProvinceID.get(0)).get(j);
               }

               if (this.lProvincesPointsX.get(this.iRemovedProvinceID.get(0)).get(j) > tempMaxX) {
                  tempMaxX = this.lProvincesPointsX.get(this.iRemovedProvinceID.get(0)).get(j);
               }

               if (this.lProvincesPointsY.get(this.iRemovedProvinceID.get(0)).get(j) < tempMinY) {
                  tempMinY = this.lProvincesPointsY.get(this.iRemovedProvinceID.get(0)).get(j);
               }

               if (this.lProvincesPointsY.get(this.iRemovedProvinceID.get(0)).get(j) > tempMaxY) {
                  tempMaxY = this.lProvincesPointsY.get(this.iRemovedProvinceID.get(0)).get(j);
               }
            }

            this.lProvincesMaxX.set(this.iRemovedProvinceID.get(0), tempMaxX);
            this.lProvincesMaxY.set(this.iRemovedProvinceID.get(0), tempMaxY);
            this.lProvincesMinX.set(this.iRemovedProvinceID.get(0), tempMinX);
            this.lProvincesMinY.set(this.iRemovedProvinceID.get(0), tempMinY);
            this.lProvincesCenterX.set(this.iRemovedProvinceID.get(0), (tempMaxX + tempMinX) / 2);
            this.lProvincesCenterY.set(this.iRemovedProvinceID.get(0), (tempMaxY + tempMinY) / 2);
            this.iRemovedProvinceID.remove(0);
            this.currentPointsX.clear();
            this.currentPointsY.clear();
            this.iCurrentProvinceMinX = 0;
            this.iCurrentProvinceMaxX = 0;
            this.iCurrentProvinceMinY = 0;
            this.iCurrentProvinceMaxY = 0;
            this.iCurrentProvinceShiftArmyX = 0;
            this.iCurrentProvinceShiftArmyY = 0;
            this.currentProvincePoints = 0;
            this.currentProvince = null;
         } else {
            this.addColor();
            GeneralPath nPath = new GeneralPath();
            int i = 0;

            for (int nSizex = this.currentPointsX.size(); i < nSizex; i++) {
               if (i == 0) {
                  nPath.moveTo(this.currentPointsX.get(i).intValue(), this.currentPointsY.get(i).intValue());
               } else {
                  nPath.lineTo(this.currentPointsX.get(i).intValue(), this.currentPointsY.get(i).intValue());
               }
            }

            this.vProvinces.add(nPath);
            List<Integer> tempX = new ArrayList<>();

            for (int ix = 0; ix < this.currentPointsX.size(); ix++) {
               tempX.add(this.currentPointsX.get(ix));
            }

            List<Integer> tempY = new ArrayList<>();

            for (int ix = 0; ix < this.currentPointsY.size(); ix++) {
               tempY.add(this.currentPointsY.get(ix));
            }

            this.lProvincesPointsX.add(tempX);
            this.lProvincesPointsY.add(tempY);

            for (int ix = this.vProvinces.size() - 1; ix < this.vProvinces.size(); ix++) {
               int tempMinX = this.iMapEditorBackgroundWidth;
               int tempMaxX = -this.iMapEditorBackgroundWidth;
               int tempMinY = this.iMapEditorBackgroundHeight;
               int tempMaxY = -this.iMapEditorBackgroundHeight;

               for (int j = 0; j < this.lProvincesPointsX.get(ix).size(); j++) {
                  if (this.lProvincesPointsX.get(ix).get(j) < tempMinX) {
                     tempMinX = this.lProvincesPointsX.get(ix).get(j);
                  }

                  if (this.lProvincesPointsX.get(ix).get(j) > tempMaxX) {
                     tempMaxX = this.lProvincesPointsX.get(ix).get(j);
                  }

                  if (this.lProvincesPointsY.get(ix).get(j) < tempMinY) {
                     tempMinY = this.lProvincesPointsY.get(ix).get(j);
                  }

                  if (this.lProvincesPointsY.get(ix).get(j) > tempMaxY) {
                     tempMaxY = this.lProvincesPointsY.get(ix).get(j);
                  }
               }

               this.lProvincesMaxX.add(tempMaxX);
               this.lProvincesMaxY.add(tempMaxY);
               this.lProvincesMinX.add(tempMinX);
               this.lProvincesMinY.add(tempMinY);
               this.lProvincesCenterX.add((tempMaxX + tempMinX) / 2);
               this.lProvincesCenterY.add((tempMaxY + tempMinY) / 2);
            }

            this.currentPointsX.clear();
            this.currentPointsY.clear();
            this.iCurrentProvinceMinX = 0;
            this.iCurrentProvinceMaxX = 0;
            this.iCurrentProvinceMinY = 0;
            this.iCurrentProvinceMaxY = 0;
            this.iCurrentProvinceShiftArmyX = 0;
            this.iCurrentProvinceShiftArmyY = 0;
            this.currentProvincePoints = 0;
            this.currentProvince = null;
         }
      }
   }

   protected GamePanel() {
      this.loadProvincesFromFile();
      this.addKeyListener();
      this.addWheelListener();
      this.addMotionListener();
      this.addMouseListener();
      gameThreadDim = new Dimension(GWIDTH, GHEIGHT);
      this.setPreferredSize(gameThreadDim);
      this.setBackground(Color.BLACK);
      this.setFocusable(true);

      for (int j = 0; j < BackgroundSize_Y; j++) {
         for (int i = 0; i < BackgroundSize_X; i++) {
            this.mapEditorBackground.add(this.ImageReader(mapName + "/background/main/" + j + "_" + i + ".png"));
         }
      }

      this.gameMapSize = this.mapEditorBackground.size();
      this.iMapEditorBackgroundWidth = 0;

      for (int i = 0; i < BackgroundSize_X; i++) {
         this.iMapEditorBackgroundWidth = this.iMapEditorBackgroundWidth + this.mapEditorBackground.get(i).getWidth(this);
      }

      this.iMapEditorBackgroundHeight = 0;

      for (int i = 0; i < BackgroundSize_Y; i++) {
         this.iMapEditorBackgroundHeight = this.iMapEditorBackgroundHeight + this.mapEditorBackground.get(i * BackgroundSize_X).getHeight(this);
      }

      this.iMapEditorBackgroundWidth = (int)(this.iMapEditorBackgroundWidth * BackgroundScale);
      this.iMapEditorBackgroundHeight = (int)(this.iMapEditorBackgroundHeight * BackgroundScale);
      this.iHeightOfSingleBG = (int)(this.mapEditorBackground.get(0).getHeight(this) * BackgroundScale);
      this.iWidthOfSingleBG = (int)(this.mapEditorBackground.get(0).getWidth(this) * BackgroundScale);

      for (int j = 0; j < BackgroundSize_Y; j++) {
         for (int i = 0; i < BackgroundSize_X; i++) {
            this.mapEditorBackground2.add(this.ImageReader(mapName + "/background/mapEditor/" + j + "_" + i + ".png"));
         }
      }
   }

   public void draw(Graphics g) {
      Graphics2D g2d = (Graphics2D)g.create();
      g2d.setColor(Color.LIGHT_GRAY);
      g2d.fillRect(0, 0, GWIDTH, GHEIGHT);
      g2d.scale(this.fMapScale, this.fMapScale);
      if (this.drawBG) {
         if (this.drawBGMain) {
            int j = BackgroundSize_Y - 1;
            int currID = this.gameMapSize - 1;

            for (int tempHeight = this.iHeightOfSingleBG * (BackgroundSize_Y - 1); j >= 0; j--) {
               int tempWidth = this.iMapEditorBackgroundWidth - (int)(this.mapEditorBackground.get(0).getWidth(this) * BackgroundScale);

               for (int i = BackgroundSize_X - 1; i >= 0; i--) {
                  g2d.drawImage(
                     this.mapEditorBackground.get(currID),
                     -this.iXMapPos + tempWidth,
                     -this.iYMapPos + tempHeight,
                     this.iWidthOfSingleBG,
                     this.iHeightOfSingleBG,
                     null
                  );
                  tempWidth = (int)(tempWidth - this.mapEditorBackground.get(currID).getWidth(this) * BackgroundScale);
                  currID--;
               }

               tempHeight = (int)(tempHeight - this.mapEditorBackground.get(0).getHeight(this) * BackgroundScale);
            }
         } else {
            int j = BackgroundSize_Y - 1;
            int currID = this.gameMapSize - 1;

            for (int tempHeight = this.iHeightOfSingleBG * (BackgroundSize_Y - 1); j >= 0; j--) {
               int tempWidth = this.iMapEditorBackgroundWidth - (int)(this.mapEditorBackground2.get(0).getWidth(this) * BackgroundScale);

               for (int i = BackgroundSize_X - 1; i >= 0; i--) {
                  g2d.drawImage(
                     this.mapEditorBackground2.get(currID),
                     -this.iXMapPos + tempWidth,
                     -this.iYMapPos + tempHeight,
                     this.iWidthOfSingleBG,
                     this.iHeightOfSingleBG,
                     null
                  );
                  tempWidth = (int)(tempWidth - this.mapEditorBackground2.get(currID).getWidth(this) * BackgroundScale);
                  currID--;
               }

               tempHeight = (int)(tempHeight - this.mapEditorBackground2.get(0).getHeight(this) * BackgroundScale);
            }
         }
      }

      int j = BackgroundSize_Y - 1;
      int currID = this.gameMapSize - 1;

      for (int tempHeight = this.iHeightOfSingleBG * (BackgroundSize_Y - 1); j >= 0; j--) {
         int tempWidth = this.iMapEditorBackgroundWidth
            + this.iMapEditorBackgroundWidth
            - (int)(this.mapEditorBackground.get(0).getWidth(this) * BackgroundScale);

         for (int i = BackgroundSize_X - 1; i >= 0; i--) {
            g2d.drawImage(
               this.mapEditorBackground.get(currID),
               -this.iXMapPos + tempWidth,
               -this.iYMapPos + tempHeight,
               this.iWidthOfSingleBG,
               this.iHeightOfSingleBG,
               null
            );
            tempWidth = (int)(tempWidth - this.mapEditorBackground.get(currID).getWidth(this) * BackgroundScale);
            currID--;
         }

         tempHeight = (int)(tempHeight - this.mapEditorBackground.get(0).getHeight(this) * BackgroundScale);
      }

      try {
         this.updateInView();
         g2d.translate(-this.iXMapPos, -this.iYMapPos);
         if (this.bDrawProvinces) {
            this.drawProvinces(g2d);
            this.drawActiveProvince(g2d);
         }

         this.drawArmy(g2d);
         this.drawPoints(g2d);
         Graphics2D g2d2 = (Graphics2D)g.create();
         if (this.iXMapPos < 0) {
            g2d2.scale(this.fMapScale, this.fMapScale);
            g2d2.translate(-this.iMapEditorBackgroundWidth - this.iXMapPos, -this.iYMapPos);
            if (this.bDrawProvinces) {
               this.drawProvinces(g2d2);
               this.drawActiveProvince(g2d2);
            }

            this.drawArmy(g2d2);
            this.drawPoints(g2d2);
         }

         if (this.iXMapPos + GWIDTH / this.fMapScale >= this.iMapEditorBackgroundWidth) {
            Graphics2D var13 = (Graphics2D)g.create();
            var13.scale(this.fMapScale, this.fMapScale);
            var13.translate(this.iMapEditorBackgroundWidth - this.iXMapPos, -this.iYMapPos);
            if (this.bDrawProvinces) {
               this.drawProvinces(var13);
               this.drawActiveProvince(var13);
            }

            this.drawArmy(var13);
            this.drawPoints(var13);
         }

         if (this.currentPointsX.size() > 1) {
            g2d.setColor(new Color(48, 65, 122, 125));
            if (this.currentProvincePoints > 1) {
               g2d.fill(this.currentProvince);
            }

            if (bDrawProvincePoints) {
               g2d.setColor(new Color(0, 0, 0, 255));
               g2d.draw3DRect(this.currentPointsX.get(0), this.currentPointsY.get(0), 0, 0, false);

               for (int i = 1; i < this.currentPointsX.size(); i++) {
                  g2d.setColor(new Color(255, 0, 255, 255));
                  g2d.draw3DRect(this.currentPointsX.get(i), this.currentPointsY.get(i), 0, 0, false);
               }
            }
         }
      } catch (NullPointerException var8) {
      } catch (IndexOutOfBoundsException var9) {
      }

      if (this.drawNeighboring) {
         g2d.setColor(Color.red);
         this.drawNeighboring(g2d);
      }

      g2d.setColor(Color.red);
      g2d.draw3DRect((int)(this.iMousePosX / this.fMapScale) + this.iXMapPos, (int)(this.iMousePosY / this.fMapScale) + this.iYMapPos, 0, 0, false);

      for (int i = 0; i < this.lButtons.size(); i++) {
         this.lButtons.get(i).draw(g);
      }

      if (this.bChangePointMode || this.bChooseProvinceMode || this.bAddPointToProvinceMode || this.bRemovePointMode || this.iRemovedProvinceID.size() > 0) {
         this.drawMode(g, g2d);
      }

      this.drawTextInfo(g, g2d);
   }

   private final void updateInView() {
      for (int i = 0; i < this.vProvinces.size(); i++) {
         this.isInView
            .set(
               i,
               (
                     this.iXMapPos + GWIDTH / this.fMapScale >= this.lProvincesMinX.get(i).intValue() && this.iXMapPos <= this.lProvincesMaxX.get(i)
                        || this.iXMapPos + GWIDTH / this.fMapScale >= this.lProvincesMinX.get(i) + this.iMapEditorBackgroundWidth
                           && this.iXMapPos <= this.lProvincesMaxX.get(i) + this.iMapEditorBackgroundWidth
                        || this.iXMapPos + GWIDTH / this.fMapScale >= this.lProvincesMinX.get(i) - this.iMapEditorBackgroundWidth
                           && this.iXMapPos <= this.lProvincesMaxX.get(i) - this.iMapEditorBackgroundWidth
                  )
                  && this.iYMapPos + GHEIGHT / this.fMapScale >= this.lProvincesMinY.get(i).intValue()
                  && this.iYMapPos <= this.lProvincesMaxY.get(i)
            );
      }
   }

   private void drawProvinces(Graphics2D g2d) {
      try {
         for (int i = 0; i < this.vProvinces.size(); i++) {
            if (this.isInView.get(i)) {
               try {
                  if (i == this.iActiveProvinceID) {
                     g2d.setColor(new Color(255, 255, 255, 255));
                  } else {
                     g2d.setColor(new Color(this.pathR.get(i), this.pathG.get(i), this.pathB.get(i), this.iLandProvinceAlpha));
                  }
               } catch (IndexOutOfBoundsException var4) {
               }

               g2d.fill((GeneralPath)this.vProvinces.elementAt(i));
            }
         }
      } catch (IndexOutOfBoundsException var5) {
      } catch (NullPointerException var6) {
      }
   }

   private void drawActiveProvince(Graphics2D g2d) {
      if (this.iActiveProvinceID >= 0) {
         g2d.setColor(new Color(255, 255, 255, 255));
         g2d.fill((GeneralPath)this.vProvinces.elementAt(this.iActiveProvinceID));
      }
   }

   private void drawPoints(Graphics g2d) {
      try {
         if (bDrawProvincePoints) {
            for (int i = 0; i < this.vProvinces.size(); i++) {
               if (this.isInView.get(i)) {
                  int j = 0;

                  for (int jSize = this.lProvincesPointsX.get(i).size(); j < jSize; j++) {
                     g2d.setColor(new Color(this.pathG.get(i), this.pathB.get(i), this.pathR.get(i), 255));
                     g2d.draw3DRect(this.lProvincesPointsX.get(i).get(j), this.lProvincesPointsY.get(i).get(j), 0, 0, false);
                  }
               }
            }
         }
      } catch (IndexOutOfBoundsException var9) {
      } catch (NullPointerException var10) {
      }

      try {
         if (bDrawCities) {
            Font font = new Font("Arial", 1, 3);
            g2d.setFont(font);
            AffineTransform affinetransform = new AffineTransform();
            new FontRenderContext(affinetransform, true, true);

            for (int ix = 0; ix < citiesX.size(); ix++) {
               try {
                  if ((
                        this.iXMapPos + GWIDTH / this.fMapScale >= citiesX.get(ix).intValue() && this.iXMapPos <= citiesX.get(ix)
                           || this.iXMapPos + GWIDTH / this.fMapScale >= citiesX.get(ix) + this.iMapEditorBackgroundWidth
                              && this.iXMapPos <= citiesX.get(ix) + this.iMapEditorBackgroundWidth
                           || this.iXMapPos + GWIDTH / this.fMapScale >= citiesX.get(ix) - this.iMapEditorBackgroundWidth
                              && this.iXMapPos <= citiesX.get(ix) - this.iMapEditorBackgroundWidth
                     )
                     && this.iYMapPos + GHEIGHT / this.fMapScale >= citiesY.get(ix).intValue()
                     && this.iYMapPos <= citiesY.get(ix)) {
                     g2d.setColor(Color.BLACK);
                     g2d.draw3DRect(citiesX.get(ix) - 1, citiesY.get(ix) - 1, 2, 2, false);
                     if (bDrawCitiesNames) {
                        g2d.setColor(new Color(240, 240, 240, 255));
                        g2d.drawString(citiesNames.get(ix), citiesX.get(ix) - 1, citiesY.get(ix) - 1);
                     }
                  }
               } catch (IndexOutOfBoundsException var7) {
               }
            }
         }
      } catch (IndexOutOfBoundsException var8) {
      }
   }

   private void drawArmy(Graphics g2d) {
      if (bDrawArmy) {
         for (int i = 0; i < this.vProvinces.size(); i++) {
            if (this.iXMapPos + GWIDTH / this.fMapScale >= this.lProvincesMinX.get(i).intValue()
               && this.iXMapPos <= this.lProvincesMaxX.get(i)
               && this.iYMapPos + GHEIGHT / this.fMapScale >= this.lProvincesMinY.get(i).intValue()
               && this.iYMapPos <= this.lProvincesMaxY.get(i)) {
               Font font = new Font("SansSerif", 1, 14);
               g2d.setFont(font);
               AffineTransform affinetransform = new AffineTransform();
               FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
               g2d.setColor(new Color(10, 10, 10, 255));
               g2d.fillRect(
                  this.lProvincesCenterX.get(i) - 2 - (int)font.getStringBounds(i + "", frc).getWidth() / 2,
                  this.lProvincesCenterY.get(i) - 2 - (int)font.getStringBounds("2500", frc).getHeight() / 2 + 1,
                  (int)font.getStringBounds(i + "", frc).getWidth() + 4,
                  (int)font.getStringBounds(i + "", frc).getHeight() / 2 + 2
               );
               g2d.fillRect(
                  this.lProvincesCenterX.get(i) - 2 - (int)font.getStringBounds(i + "", frc).getWidth() / 2 + 1,
                  this.lProvincesCenterY.get(i) - 2 - (int)font.getStringBounds("2500", frc).getHeight() / 2,
                  (int)font.getStringBounds(i + "", frc).getWidth() + 2,
                  1
               );
               g2d.fillRect(
                  this.lProvincesCenterX.get(i) - 2 - (int)font.getStringBounds(i + "", frc).getWidth() / 2 + 1,
                  this.lProvincesCenterY.get(i)
                     - 2
                     - (int)font.getStringBounds("2500", frc).getHeight() / 2
                     + (int)font.getStringBounds(i + "", frc).getHeight() / 2
                     + 3,
                  (int)font.getStringBounds(i + "", frc).getWidth() + 2,
                  1
               );
               g2d.setColor(new Color(255, 255, 45, 255));
               g2d.drawString(i + "", this.lProvincesCenterX.get(i) - (int)font.getStringBounds(i + "", frc).getWidth() / 2, this.lProvincesCenterY.get(i) + 1);
            }
         }
      }
   }

   private void drawCurrentArmy(Graphics g2d) {
      if (bDrawArmy) {
         Font font = new Font("SansSerif", 1, 14);
         g2d.setFont(font);
         AffineTransform affinetransform = new AffineTransform();
         FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
         g2d.setColor(new Color(10, 10, 10, 255));
         g2d.fillRect(
            (this.iCurrentProvinceMaxX + this.iCurrentProvinceMinX) / 2
               - 2
               - (int)font.getStringBounds("XY", frc).getWidth() / 2
               + this.iCurrentProvinceShiftArmyX,
            (this.iCurrentProvinceMaxY + this.iCurrentProvinceMinY) / 2
               - 1
               - (int)font.getStringBounds("XY", frc).getHeight() / 2
               + this.iCurrentProvinceShiftArmyY,
            (int)font.getStringBounds("XY", frc).getWidth() + 4,
            (int)font.getStringBounds("XY", frc).getHeight() / 2 + 2
         );
         g2d.fillRect(
            (this.iCurrentProvinceMaxX + this.iCurrentProvinceMinX) / 2
               - 1
               - (int)font.getStringBounds("XY", frc).getWidth() / 2
               + this.iCurrentProvinceShiftArmyX,
            (this.iCurrentProvinceMaxY + this.iCurrentProvinceMinY) / 2
               - 2
               - (int)font.getStringBounds("XY", frc).getHeight() / 2
               + this.iCurrentProvinceShiftArmyY,
            (int)font.getStringBounds("XY", frc).getWidth() + 2,
            1
         );
         g2d.fillRect(
            (this.iCurrentProvinceMaxX + this.iCurrentProvinceMinX) / 2
               - 1
               - (int)font.getStringBounds("XY", frc).getWidth() / 2
               + this.iCurrentProvinceShiftArmyX,
            (this.iCurrentProvinceMaxY + this.iCurrentProvinceMinY) / 2
               - 2
               - (int)font.getStringBounds("XY", frc).getHeight() / 2
               + (int)font.getStringBounds("XY", frc).getHeight() / 2
               + 3
               + this.iCurrentProvinceShiftArmyY,
            (int)font.getStringBounds("XY", frc).getWidth() + 2,
            1
         );
         if (this.iCurrentProvinceShiftArmyX == 0 && this.iCurrentProvinceShiftArmyY == 0) {
            g2d.setColor(new Color(255, 255, 45, 255));
         } else {
            g2d.setColor(new Color(178, 50, 35, 255));
         }

         g2d.drawString(
            "XY",
            (this.iCurrentProvinceMaxX + this.iCurrentProvinceMinX) / 2 - (int)font.getStringBounds("XY", frc).getWidth() / 2 + this.iCurrentProvinceShiftArmyX,
            (this.iCurrentProvinceMaxY + this.iCurrentProvinceMinY) / 2 + 1 + this.iCurrentProvinceShiftArmyY
         );
      }
   }

   private void drawTextInfo(Graphics g, Graphics g2d) {
      Graphics var10 = (Graphics2D)g.create();
      var10.setColor(Color.BLACK);
      Font font = new Font("SansSerif", 1, 18);
      var10.setFont(font);
      AffineTransform affinetransform = new AffineTransform();
      FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
      int iMax = font.getStringBounds("X: " + this.iLastPressedPosX + " | Y: " + this.iLastPressedPosY, frc).getWidth()
            > font.getStringBounds("Scale: " + this.fMapScale + " | S: " + this.iShiftXY, frc).getWidth()
         ? (int)font.getStringBounds("X: " + this.iLastPressedPosX + " | Y: " + this.iLastPressedPosY, frc).getWidth()
         : (int)font.getStringBounds("Scale: " + this.fMapScale + " | S: " + this.iShiftXY, frc).getWidth();
      var10.setColor(new Color(10, 10, 10, 200));
      var10.fillRect(GWIDTH - iMax - 10, 0, iMax + 10, (5 + (int)font.getStringBounds("AOC", frc).getHeight()) * 2 + 5);
      var10.setColor(new Color(64, 64, 64, 255));
      var10.drawRect(GWIDTH - iMax - 10, -1, iMax + 10, (5 + (int)font.getStringBounds("AOC", frc).getHeight()) * 2 + 5);
      var10.setColor(new Color(240, 240, 240, 255));
      var10.drawString(
         "Scale: " + this.fMapScale + " | S: " + this.iShiftXY, GWIDTH - iMax - 5, (int)font.getStringBounds("Scale: " + this.fMapScale, frc).getHeight()
      );
      var10.drawString(
         "X: " + this.iLastPressedPosX + " | Y: " + this.iLastPressedPosY,
         GWIDTH - (int)font.getStringBounds("X: " + this.iLastPressedPosX + " | Y: " + this.iLastPressedPosY, frc).getWidth() - 5,
         (int)font.getStringBounds("X: " + this.iLastPressedPosX + " | Y: " + this.iLastPressedPosY, frc).getHeight() * 2
      );
      Font font2 = new Font("SansSerif", 1, 14);
      var10.setFont(font2);
      AffineTransform affinetransform2 = new AffineTransform();
      FontRenderContext frc2 = new FontRenderContext(affinetransform2, true, true);
      var10.setColor(new Color(240, 240, 240, 75));
      var10.drawString(this.topText, 5, 5 + (int)font2.getStringBounds(this.topText, frc2).getHeight());
      var10.drawString(this.downText, 5, 10 + (int)font2.getStringBounds(this.topText, frc2).getHeight() * 2);
   }

   private void drawMode(Graphics g, Graphics g2d) {
      Graphics var9 = (Graphics2D)g.create();
      Font font = new Font("SansSerif", 1, 20);
      var9.setFont(font);
      AffineTransform affinetransform = new AffineTransform();
      FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
      if (this.bChangePointMode && this.bSelectedPointsToChangePosition) {
         var9.setColor(new Color(34, 168, 20, 200));
      } else if (this.bAddPointToProvinceMode) {
         var9.setColor(new Color(46, 51, 96, 200));
      } else if (this.bRemovePointMode) {
         var9.setColor(Color.RED);
      } else {
         var9.setColor(new Color(10, 10, 10, 200));
      }

      var9.fillRect(0, 0, GWIDTH, 5 + (int)font.getStringBounds("AOC", frc).getHeight() + 5);

      try {
         String modeText;
         if (this.iRemovedProvinceID.size() > 0 && !this.bChooseProvinceMode) {
            var9.setColor(Color.white);
            modeText = "Now create a new province, it will be added in place of removed province ID: "
               + this.iRemovedProvinceID.get(0)
               + " [Size: "
               + this.iRemovedProvinceID.size()
               + "]";
         } else {
            var9.setColor(new Color(250, 78, 20, 255));
            modeText = "Mode: "
               + (
                  this.bChangePointMode
                     ? "Change Points Position"
                     : (
                        this.bChooseProvinceMode
                           ? "Remove a Province"
                           : (this.bAddPointToProvinceMode ? "Add Point To Province" : (this.bRemovePointMode ? "Remove Point" : ""))
                     )
               );
         }

         var9.drawString(
            modeText, GWIDTH / 2 - (int)(font.getStringBounds(modeText, frc).getWidth() / 2.0), (int)font.getStringBounds("MODE:", frc).getHeight()
         );
      } catch (Exception var8) {
      }
   }

   private void drawNeighboring(Graphics2D g2d) {
      if (this.neighboringProvincesReadyToUse) {
         for (int i = 0; i < this.neighboring.size(); i++) {
            for (int j = 0; j < this.neighboring.get(i).size(); j++) {
               g2d.setColor(Color.BLACK);
               g2d.drawLine(
                  this.lProvincesCenterX.get(i),
                  this.lProvincesCenterY.get(i),
                  this.lProvincesCenterX.get(this.neighboring.get(i).get(j)),
                  this.lProvincesCenterY.get(this.neighboring.get(i).get(j))
               );
            }
         }
      }
   }

   public static void loadConfigFile() {
      FileReader fr = null;
      String sLine = "";

      try {
         fr = new FileReader("MapEditorSettings.txt");
         BufferedReader bfr = new BufferedReader(fr);

         while ((sLine = bfr.readLine()) != null) {
            String[] tempR = sLine.split("=");

            try {
               if (tempR[0].equals("MAP_NAME")) {
                  mapName = tempR[1];
                  fileName = mapName + "/data/ProvincePoints/ProvincePoints.txt";
                  fileNameJson = mapName + "/data/ProvincePoints/ProvincePoints.json";
                  sBackgroundImage = mapName + "/background/0_0.png";
               } else if (tempR[0].equals("WIDTH")) {
                  GWIDTH = Integer.parseInt(tempR[1]);
               } else if (tempR[0].equals("HEIGHT")) {
                  GHEIGHT = Integer.parseInt(tempR[1]);
               } else if (tempR[0].equals("MAXIMUM_SIZE")) {
                  MAXIMUM_SIZE = Boolean.parseBoolean(tempR[1]);
               } else if (tempR[0].equals("DRAW_POINTS")) {
                  bDrawProvincePoints = Boolean.parseBoolean(tempR[1]);
               } else if (tempR[0].equals("DRAW_PROVINCE_ID")) {
                  bDrawArmy = Boolean.parseBoolean(tempR[1]);
               } else if (tempR[0].equals("PROVINCES_PER_FILE")) {
                  provincesPerFile = Integer.parseInt(tempR[1]);
               } else if (tempR[0].equals("CUT_FROM_PROVINCE_MODE")) {
                  if (Boolean.parseBoolean(tempR[1])) {
                     fileName = mapName + "/data/ProvincePoints_Cut/ProvincePoints_Cut.txt";
                     fileNameJson = mapName + "/data/ProvincePoints_Cut/ProvincePoints_Cut.json";
                  }
               } else if (tempR[0].equals("CONVERT_AOH2_MAP_PROVINCES_DATA_TO_AOH3") && Boolean.parseBoolean(tempR[1])) {
                  CONVERT_AOH2_MAP_PROVINCES_DATA_TO_AOH3 = true;
               }
            } catch (IndexOutOfBoundsException var5) {
            } catch (IllegalArgumentException var6) {
            }
         }

         fr.close();
      } catch (IOException var7) {
      }

      loadBackgroundData();
      loadCities();
   }

   public static void loadBackgroundData() {
      FileReader fr = null;
      String sLine = "";

      try {
         fr = new FileReader(mapName + "/config.json");
         BufferedReader bfr = new BufferedReader(fr);

         while ((sLine = bfr.readLine()) != null) {
            String[] tempR = sLine.split(":");

            try {
               if (tempR[0].contains("BackgroundSize_X")) {
                  BackgroundSize_X = Integer.parseInt(tempR[1].replaceAll("[^0-9]", ""));
               } else if (tempR[0].contains("BackgroundSize_Y")) {
                  BackgroundSize_Y = Integer.parseInt(tempR[1].replaceAll("[^0-9]", ""));
               } else if (tempR[0].contains("BackgroundScale")) {
                  BackgroundScale = Math.max(1.0F, Float.parseFloat(tempR[1].replaceAll("[^0-9]", "")) / 10.0F);
                  System.out.println("BackgroundScale: " + BackgroundScale);
               }
            } catch (IndexOutOfBoundsException var5) {
            } catch (IllegalArgumentException var6) {
            }
         }

         fr.close();
      } catch (IOException var7) {
      }
   }

   public static void loadCities() {
      FileReader fr = null;
      String sLine = "";

      try {
         fr = new FileReader(mapName + "/cities/cities.json");
         BufferedReader bfr = new BufferedReader(fr);
         String tName = "";
         int tX = 0;
         int tY = 0;

         while ((sLine = bfr.readLine()) != null) {
            try {
               if (sLine.indexOf("Name") >= 0) {
                  tName = "";
                  boolean startAdd = false;

                  for (int i = 0; i < sLine.length(); i++) {
                     if (sLine.charAt(i) == ':') {
                        startAdd = true;
                     }

                     if (startAdd) {
                        tName = tName + sLine.charAt(i);
                     }
                  }

                  tName = tName.replace(",", "").replace(":", "").replace(" ", "").replace("\"", "");
               } else if (sLine.indexOf(120) >= 0) {
                  String tNum = "";

                  for (int i = 0; i < sLine.length(); i++) {
                     if (sLine.charAt(i) >= '0' && sLine.charAt(i) <= '9') {
                        tNum = tNum + sLine.charAt(i);
                     }
                  }

                  tX = Integer.parseInt(tNum);
               } else if (sLine.indexOf("y") >= 0) {
                  String tNum = "";

                  for (int ix = 0; ix < sLine.length(); ix++) {
                     if (sLine.charAt(ix) >= '0' && sLine.charAt(ix) <= '9') {
                        tNum = tNum + sLine.charAt(ix);
                     }
                  }

                  tY = Integer.parseInt(tNum);
                  citiesX.add(tX);
                  citiesY.add(tY);
                  citiesNames.add(tName + "0");
               }
            } catch (IndexOutOfBoundsException var20) {
            } catch (IllegalArgumentException var21) {
            }
         }

         fr.close();
      } catch (IOException var22) {
      }

      try {
         fr = new FileReader(mapName + "/cities/cities_1.json");
         BufferedReader bfr = new BufferedReader(fr);
         String tName = "";
         int tX = 0;
         int tY = 0;

         while ((sLine = bfr.readLine()) != null) {
            try {
               if (sLine.indexOf("Name") >= 0) {
                  tName = "";
                  boolean startAdd = false;

                  for (int ixx = 0; ixx < sLine.length(); ixx++) {
                     if (sLine.charAt(ixx) == ':') {
                        startAdd = true;
                     }

                     if (startAdd) {
                        tName = tName + sLine.charAt(ixx);
                     }
                  }

                  tName = tName.replace(",", "").replace(":", "").replace(" ", "").replace("\"", "");
               } else if (sLine.indexOf(120) >= 0) {
                  String tNum = "";

                  for (int ixx = 0; ixx < sLine.length(); ixx++) {
                     if (sLine.charAt(ixx) >= '0' && sLine.charAt(ixx) <= '9') {
                        tNum = tNum + sLine.charAt(ixx);
                     }
                  }

                  tX = Integer.parseInt(tNum);
               } else if (sLine.indexOf("y") >= 0) {
                  String tNum = "";

                  for (int ixxx = 0; ixxx < sLine.length(); ixxx++) {
                     if (sLine.charAt(ixxx) >= '0' && sLine.charAt(ixxx) <= '9') {
                        tNum = tNum + sLine.charAt(ixxx);
                     }
                  }

                  tY = Integer.parseInt(tNum);
                  citiesX.add(tX);
                  citiesY.add(tY);
                  citiesNames.add(tName + "1");
               }
            } catch (IndexOutOfBoundsException var17) {
            } catch (IllegalArgumentException var18) {
            }
         }

         fr.close();
      } catch (IOException var19) {
      }

      try {
         fr = new FileReader(mapName + "/cities/cities_2.json");
         BufferedReader bfr = new BufferedReader(fr);
         String tName = "";
         int tX = 0;
         int tY = 0;

         while ((sLine = bfr.readLine()) != null) {
            try {
               if (sLine.indexOf("Name") >= 0) {
                  tName = "";
                  boolean startAdd = false;

                  for (int ixxxx = 0; ixxxx < sLine.length(); ixxxx++) {
                     if (sLine.charAt(ixxxx) == ':') {
                        startAdd = true;
                     }

                     if (startAdd) {
                        tName = tName + sLine.charAt(ixxxx);
                     }
                  }

                  tName = tName.replace(",", "").replace(":", "").replace(" ", "").replace("\"", "");
               } else if (sLine.indexOf(120) >= 0) {
                  String tNum = "";

                  for (int ixxxx = 0; ixxxx < sLine.length(); ixxxx++) {
                     if (sLine.charAt(ixxxx) >= '0' && sLine.charAt(ixxxx) <= '9') {
                        tNum = tNum + sLine.charAt(ixxxx);
                     }
                  }

                  tX = Integer.parseInt(tNum);
               } else if (sLine.indexOf("y") >= 0) {
                  String tNum = "";

                  for (int ixxxxx = 0; ixxxxx < sLine.length(); ixxxxx++) {
                     if (sLine.charAt(ixxxxx) >= '0' && sLine.charAt(ixxxxx) <= '9') {
                        tNum = tNum + sLine.charAt(ixxxxx);
                     }
                  }

                  tY = Integer.parseInt(tNum);
                  citiesX.add(tX);
                  citiesY.add(tY);
                  citiesNames.add(tName + "2");
               }
            } catch (IndexOutOfBoundsException var14) {
            } catch (IllegalArgumentException var15) {
            }
         }

         fr.close();
      } catch (IOException var16) {
      }

      try {
         fr = new FileReader(mapName + "/cities/cities_3.json");
         BufferedReader bfr = new BufferedReader(fr);
         String tName = "";
         int tX = 0;
         int tY = 0;

         while ((sLine = bfr.readLine()) != null) {
            try {
               if (sLine.indexOf("Name") >= 0) {
                  tName = "";
                  boolean startAdd = false;

                  for (int ixxxxxx = 0; ixxxxxx < sLine.length(); ixxxxxx++) {
                     if (sLine.charAt(ixxxxxx) == ':') {
                        startAdd = true;
                     }

                     if (startAdd) {
                        tName = tName + sLine.charAt(ixxxxxx);
                     }
                  }

                  tName = tName.replace(",", "").replace(":", "").replace(" ", "").replace("\"", "");
               } else if (sLine.indexOf(120) >= 0) {
                  String tNum = "";

                  for (int ixxxxxx = 0; ixxxxxx < sLine.length(); ixxxxxx++) {
                     if (sLine.charAt(ixxxxxx) >= '0' && sLine.charAt(ixxxxxx) <= '9') {
                        tNum = tNum + sLine.charAt(ixxxxxx);
                     }
                  }

                  tX = Integer.parseInt(tNum);
               } else if (sLine.indexOf("y") >= 0) {
                  String tNum = "";

                  for (int ixxxxxxx = 0; ixxxxxxx < sLine.length(); ixxxxxxx++) {
                     if (sLine.charAt(ixxxxxxx) >= '0' && sLine.charAt(ixxxxxxx) <= '9') {
                        tNum = tNum + sLine.charAt(ixxxxxxx);
                     }
                  }

                  tY = Integer.parseInt(tNum);
                  citiesX.add(tX);
                  citiesY.add(tY);
                  citiesNames.add(tName + "3");
               }
            } catch (IndexOutOfBoundsException var11) {
            } catch (IllegalArgumentException var12) {
            }
         }

         fr.close();
      } catch (IOException var13) {
      }

      try {
         fr = new FileReader(mapName + "/cities/cities_4.json");
         BufferedReader bfr = new BufferedReader(fr);
         String tName = "";
         int tX = 0;
         int tY = 0;

         while ((sLine = bfr.readLine()) != null) {
            try {
               if (sLine.indexOf("Name") >= 0) {
                  tName = "";
                  boolean startAdd = false;

                  for (int ixxxxxxxx = 0; ixxxxxxxx < sLine.length(); ixxxxxxxx++) {
                     if (sLine.charAt(ixxxxxxxx) == ':') {
                        startAdd = true;
                     }

                     if (startAdd) {
                        tName = tName + sLine.charAt(ixxxxxxxx);
                     }
                  }

                  tName = tName.replace(",", "").replace(":", "").replace(" ", "").replace("\"", "");
               } else if (sLine.indexOf(120) >= 0) {
                  String tNum = "";

                  for (int ixxxxxxxx = 0; ixxxxxxxx < sLine.length(); ixxxxxxxx++) {
                     if (sLine.charAt(ixxxxxxxx) >= '0' && sLine.charAt(ixxxxxxxx) <= '9') {
                        tNum = tNum + sLine.charAt(ixxxxxxxx);
                     }
                  }

                  tX = Integer.parseInt(tNum);
               } else if (sLine.indexOf("y") >= 0) {
                  String tNum = "";

                  for (int ixxxxxxxxx = 0; ixxxxxxxxx < sLine.length(); ixxxxxxxxx++) {
                     if (sLine.charAt(ixxxxxxxxx) >= '0' && sLine.charAt(ixxxxxxxxx) <= '9') {
                        tNum = tNum + sLine.charAt(ixxxxxxxxx);
                     }
                  }

                  tY = Integer.parseInt(tNum);
                  citiesX.add(tX);
                  citiesY.add(tY);
                  citiesNames.add(tName + "4");
               }
            } catch (IndexOutOfBoundsException var8) {
            } catch (IllegalArgumentException var9) {
            }
         }

         fr.close();
      } catch (IOException var10) {
      }
   }

   private String clearProvincePointsText(String sLine, String extra) {
      sLine = sLine.replace("\t", "");
      sLine = sLine.replace(" ", "");
      sLine = sLine.replace("[", "");
      sLine = sLine.replace("]", "");
      return sLine.replace(extra, "");
   }

   private void loadProvincesFromFile() {
      this.vProvinces = new Vector();
      FileReader fr = null;
      String sLine = "";
      this.lProvincesPointsX.clear();
      this.lProvincesPointsY.clear();
      if (!CONVERT_AOH2_MAP_PROVINCES_DATA_TO_AOH3) {
         int i = 0;

         while (i < 5000 && this.loadProvincesFromFile(i)) {
            i++;
         }

         this.buildProvinceColors();
      } else {
         try {
            fr = new FileReader("convertMapData.txt");
            BufferedReader bfr = new BufferedReader(fr);

            while ((sLine = bfr.readLine()) != null) {
               List<Integer> tempPointsX = new ArrayList<>();
               List<Integer> tempPointsY = new ArrayList<>();
               String[] sX = sLine.split(",");
               int i = 0;

               for (int nSize = sX.length; i < nSize; i++) {
                  tempPointsX.add(Integer.parseInt(sX[i]));
               }

               sLine = bfr.readLine();
               sX = sLine.split(",");
               i = 0;

               for (int nSize = sX.length; i < nSize; i++) {
                  tempPointsY.add(Integer.parseInt(sX[i]));
               }

               GeneralPath nPath = new GeneralPath();
               int ix = 0;

               for (int nSize = sX.length; ix < nSize; ix++) {
                  if (ix == 0) {
                     nPath.moveTo(tempPointsX.get(ix).intValue(), tempPointsY.get(ix).intValue());
                  } else {
                     nPath.lineTo(tempPointsX.get(ix).intValue(), tempPointsY.get(ix).intValue());
                  }
               }

               this.vProvinces.add(nPath);
               this.lProvincesPointsX.add(tempPointsX);
               this.lProvincesPointsY.add(tempPointsY);
            }

            this.buildProvinceColors();
            fr.close();
         } catch (IOException var10) {
         }
      }

      for (int i = 0; i < this.vProvinces.size(); i++) {
         int tempMinX = 1000000000;
         int tempMaxX = -1000000000;
         int tempMinY = 1000000000;
         int tempMaxY = -1000000000;

         for (int j = 0; j < this.lProvincesPointsX.get(i).size(); j++) {
            if (this.lProvincesPointsX.get(i).get(j) < tempMinX) {
               tempMinX = this.lProvincesPointsX.get(i).get(j);
            }

            if (this.lProvincesPointsX.get(i).get(j) > tempMaxX) {
               tempMaxX = this.lProvincesPointsX.get(i).get(j);
            }

            if (this.lProvincesPointsY.get(i).get(j) < tempMinY) {
               tempMinY = this.lProvincesPointsY.get(i).get(j);
            }

            if (this.lProvincesPointsY.get(i).get(j) > tempMaxY) {
               tempMaxY = this.lProvincesPointsY.get(i).get(j);
            }
         }

         this.lProvincesMaxX.add(tempMaxX);
         this.lProvincesMaxY.add(tempMaxY);
         this.lProvincesMinX.add(tempMinX);
         this.lProvincesMinY.add(tempMinY);
         this.lProvincesCenterX.add((tempMaxX + tempMinX) / 2);
         this.lProvincesCenterY.add((tempMaxY + tempMinY) / 2);
      }
   }

   public final boolean loadProvincesFromFile_2(int id) {
      try {
         String content = new String(fileNameJson.substring(0, fileNameJson.indexOf(".json")) + (id > 0 ? "_" + id : "") + ".json");
         JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();
         JsonArray dataArray = jsonObject.getAsJsonArray("Data");
         List<Integer> lPointsX = new ArrayList<>();
         List<Integer> lPointsY = new ArrayList<>();

         for (int i = 0; i < dataArray.size(); i++) {
            JsonObject dataObject = dataArray.get(i).getAsJsonObject();
            JsonArray pointsXArray = dataObject.getAsJsonArray("pX");
            JsonArray pointsYArray = dataObject.getAsJsonArray("pY");

            for (int j = 0; j < pointsXArray.size(); j++) {
               lPointsX.add(pointsXArray.get(j).getAsInt());
            }

            for (int j = 0; j < pointsYArray.size(); j++) {
               lPointsY.add(pointsYArray.get(j).getAsInt());
            }
         }

         return true;
      } catch (Exception var12) {
         return false;
      }
   }

   public final boolean loadProvincesFromFile(int id) {
      FileReader fr = null;
      String sLine = "";

      try {
         fr = new FileReader(fileNameJson.substring(0, fileNameJson.indexOf(".json")) + (id > 0 ? "_" + id : "") + ".json");
         BufferedReader bfr = new BufferedReader(fr);

         while ((sLine = bfr.readLine()) != null) {
            List<Integer> tempPointsX = new ArrayList<>();
            List<Integer> tempPointsY = new ArrayList<>();
            if (sLine.contains("pX")) {
               sLine = this.clearProvincePointsText(sLine, "pX:");
               String[] sX = sLine.split(",");
               int i = 0;

               for (int nSize = sX.length; i < nSize; i++) {
                  tempPointsX.add(Integer.parseInt(sX[i]));
               }

               sLine = bfr.readLine();
               sLine = this.clearProvincePointsText(sLine, "pY:");
               sX = sLine.split(",");
               i = 0;

               for (int nSize = sX.length; i < nSize; i++) {
                  tempPointsY.add(Integer.parseInt(sX[i]));
               }

               GeneralPath nPath = new GeneralPath();
               int ix = 0;

               for (int nSize = sX.length; ix < nSize; ix++) {
                  if (ix == 0) {
                     nPath.moveTo(tempPointsX.get(ix).intValue(), tempPointsY.get(ix).intValue());
                  } else {
                     nPath.lineTo(tempPointsX.get(ix).intValue(), tempPointsY.get(ix).intValue());
                  }
               }

               this.vProvinces.add(nPath);
               this.lProvincesPointsX.add(tempPointsX);
               this.lProvincesPointsY.add(tempPointsY);
            }
         }

         fr.close();
         return true;
      } catch (Exception var11) {
         return false;
      }
   }

   private void buildProvinceColors() {
      Random oR = new Random();
      this.pathR.clear();
      this.pathG.clear();
      this.pathB.clear();

      for (int i = 0; i < this.vProvinces.size(); i++) {
         this.pathR.add(oR.nextInt(255));
         this.pathG.add(oR.nextInt(255));
         this.pathB.add(oR.nextInt(255));
         this.isInView.add(false);
      }
   }

   private void addColor() {
      Random oR = new Random();
      this.pathR.add(oR.nextInt(255));
      this.pathG.add(oR.nextInt(255));
      this.pathB.add(oR.nextInt(255));
      this.isInView.add(false);
   }

   private void addKeyListener() {
      this.addKeyListener(
         new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               switch (e.getKeyCode()) {
                  case 8:
                     if (GamePanel.this.currentPointsX.size() > 0) {
                        GamePanel.this.currentPointsX.remove(GamePanel.this.currentPointsX.size() - 1);
                        GamePanel.this.currentPointsY.remove(GamePanel.this.currentPointsY.size() - 1);
                        if (GamePanel.this.currentPointsX.size() > 0) {
                           GamePanel.this.iLastPressedPosX = GamePanel.this.currentPointsX.get(GamePanel.this.currentPointsX.size() - 1);
                           GamePanel.this.iLastPressedPosY = GamePanel.this.currentPointsY.get(GamePanel.this.currentPointsY.size() - 1);
                        }

                        GamePanel.this.currentProvincePoints--;
                        GamePanel.this.rebuildCurrentProvince();
                     }
                  case 9:
                  case 11:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 17:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 22:
                  case 23:
                  case 24:
                  case 25:
                  case 26:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 33:
                  case 34:
                  case 35:
                  case 36:
                  case 41:
                  case 42:
                  case 43:
                  case 44:
                  case 45:
                  case 46:
                  case 47:
                  case 48:
                  case 49:
                  case 50:
                  case 51:
                  case 52:
                  case 53:
                  case 54:
                  case 55:
                  case 56:
                  case 57:
                  case 58:
                  case 59:
                  case 60:
                  case 61:
                  case 62:
                  case 63:
                  case 64:
                  case 70:
                  case 71:
                  case 72:
                  case 82:
                  case 84:
                  case 85:
                  case 91:
                  case 92:
                  case 93:
                  case 94:
                  case 95:
                  case 96:
                  case 97:
                  case 98:
                  case 99:
                  case 100:
                  case 101:
                  case 102:
                  case 103:
                  case 104:
                  case 105:
                  case 106:
                  case 107:
                  case 108:
                  case 109:
                  case 110:
                  case 111:
                  case 112:
                  case 113:
                  case 114:
                  case 115:
                  case 116:
                  case 117:
                  case 118:
                  case 119:
                  case 120:
                  case 121:
                  case 122:
                  case 123:
                  case 124:
                  case 125:
                  case 126:
                  default:
                     break;
                  case 10:
                     GamePanel.this.saveProvincesJson();
                     break;
                  case 16:
                  case 69:
                     GamePanel.this.iShiftXY++;
                     break;
                  case 27:
                  case 81:
                     GamePanel.this.iShiftXY--;
                     break;
                  case 32:
                     GamePanel.this.bDrawProvinces = !GamePanel.this.bDrawProvinces;
                     break;
                  case 37:
                     GamePanel.this.iXMapPos = GamePanel.this.iXMapPos - GamePanel.this.iShiftXY;
                     break;
                  case 38:
                     GamePanel.this.iYMapPos = GamePanel.this.iYMapPos - GamePanel.this.iShiftXY;
                     break;
                  case 39:
                     GamePanel.this.iXMapPos = GamePanel.this.iXMapPos + GamePanel.this.iShiftXY;
                     break;
                  case 40:
                     GamePanel.this.iYMapPos = GamePanel.this.iYMapPos + GamePanel.this.iShiftXY;
                     break;
                  case 65:
                     GamePanel.this.iXMapPos = GamePanel.this.iXMapPos - GamePanel.this.iShiftXY;
                     break;
                  case 66:
                     if (!GamePanel.this.bRemovePointMode) {
                        GamePanel.this.resetModes();
                     }

                     GamePanel.this.bRemovePointMode = !GamePanel.this.bRemovePointMode;
                     break;
                  case 67:
                     GamePanel.bDrawCities = !GamePanel.bDrawCities;
                     break;
                  case 68:
                     GamePanel.this.iXMapPos = GamePanel.this.iXMapPos + GamePanel.this.iShiftXY;
                     break;
                  case 73:
                     GamePanel.this.drawNeighboring = !GamePanel.this.drawNeighboring;
                     break;
                  case 74:
                     if (!GamePanel.this.bChooseProvinceMode) {
                        GamePanel.this.resetModes();
                     }

                     GamePanel.this.bChooseProvinceMode = !GamePanel.this.bChooseProvinceMode;
                     break;
                  case 75:
                     GamePanel.this.iActiveProvinceID = -1;
                     break;
                  case 76:
                     GamePanel.this.buildProvinceColors();
                     break;
                  case 77:
                     if (!GamePanel.this.bChangePointMode) {
                        GamePanel.this.resetModes();
                     }

                     GamePanel.this.bChangePointMode = !GamePanel.this.bChangePointMode;
                     break;
                  case 78:
                     if (!GamePanel.this.bAddPointToProvinceMode) {
                        GamePanel.this.resetModes();
                     }

                     GamePanel.this.bAddPointToProvinceMode = !GamePanel.this.bAddPointToProvinceMode;
                     break;
                  case 79:
                     GamePanel.bDrawProvincePoints = !GamePanel.bDrawProvincePoints;
                     break;
                  case 80:
                     GamePanel.bDrawArmy = !GamePanel.bDrawArmy;
                     break;
                  case 83:
                     GamePanel.this.iYMapPos = GamePanel.this.iYMapPos + GamePanel.this.iShiftXY;
                     break;
                  case 86:
                     GamePanel.bDrawCitiesNames = !GamePanel.bDrawCitiesNames;
                     break;
                  case 87:
                     GamePanel.this.iYMapPos = GamePanel.this.iYMapPos - GamePanel.this.iShiftXY;
                     break;
                  case 88:
                     GamePanel.this.drawBGMain = !GamePanel.this.drawBGMain;
                     break;
                  case 89:
                     GamePanel.this.autoPath();
                     break;
                  case 90:
                     GamePanel.this.drawBG = !GamePanel.this.drawBG;
                     break;
                  case 127:
                     for (int i = 0; i < GamePanel.this.vProvinces.size(); i++) {
                        for (int j = 0; j < GamePanel.this.lProvincesPointsX.get(i).size(); j++) {
                           if (GamePanel.this.lProvincesPointsX.get(i).get(j)
                                 == (int)(GamePanel.this.iMousePosX / GamePanel.this.fMapScale + GamePanel.this.iXMapPos)
                              && GamePanel.this.lProvincesPointsY.get(i).get(j)
                                 == (int)(GamePanel.this.iMousePosY / GamePanel.this.fMapScale + GamePanel.this.iYMapPos)) {
                              GamePanel.this.lProvincesPointsX.get(i).remove(j);
                              GamePanel.this.lProvincesPointsY.get(i).remove(j);
                              GamePanel.this.rebuildPath(i);
                              break;
                           }
                        }
                     }
               }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
         }
      );
   }

   private void addWheelListener() {
      this.addMouseWheelListener(e -> {
         int steps = e.getWheelRotation();
         if (this.fMapScale > 1.0F) {
            this.fMapScale = (int)this.fMapScale;
            this.fMapScale -= steps;
         } else if (this.fMapScale < 0.1F) {
            this.fMapScale = 0.1F;
         } else if (steps < 0) {
            this.fMapScale += 0.05F;
            if (this.fMapScale > 1.0F) {
               this.fMapScale = 2.0F;
            }
         } else {
            this.fMapScale -= 0.05F;
            if (this.fMapScale < 0.1F) {
               this.fMapScale = 0.1F;
            }
         }
      });
   }

   private void addMotionListener() {
      this.addMouseMotionListener(new MouseAdapter() {
         @Override
         public void mouseMoved(MouseEvent e) {
            GamePanel.this.iMousePosX = e.getX();
            GamePanel.this.iMousePosY = e.getY();
         }

         @Override
         public void mouseDragged(MouseEvent e) {
         }
      });
   }

   private void addMouseListener() {
      this.addMouseListener(
         new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               int modifiers = e.getModifiers();
               if ((modifiers & 16) == 16) {
                  GamePanel.this.iLastPressedPosX = (int)(GamePanel.this.iXMapPos + GamePanel.this.iMousePosX / GamePanel.this.fMapScale);
                  GamePanel.this.iLastPressedPosY = (int)(GamePanel.this.iYMapPos + GamePanel.this.iMousePosY / GamePanel.this.fMapScale);
                  if (GamePanel.this.bChooseProvinceMode) {
                     GamePanel.this.setProvinceID();
                  } else if (GamePanel.this.bChangePointMode) {
                     GamePanel.this.changePointPosition();
                  } else if (GamePanel.this.bAddPointToProvinceMode) {
                     GamePanel.this.addPointToProvince();
                  } else if (GamePanel.this.bRemovePointMode) {
                     GamePanel.this.removePoint();
                  } else if (GamePanel.this.currentProvincePoints > 0) {
                     if (GamePanel.this.currentPointsX.get(GamePanel.this.currentPointsX.size() - 1) != GamePanel.this.iLastPressedPosX
                        || GamePanel.this.currentPointsY.get(GamePanel.this.currentPointsY.size() - 1) != GamePanel.this.iLastPressedPosY) {
                        GamePanel.this.addPointToCurrentProvince(GamePanel.this.iLastPressedPosX, GamePanel.this.iLastPressedPosY);
                     }
                  } else {
                     GamePanel.this.addPointToCurrentProvince(GamePanel.this.iLastPressedPosX, GamePanel.this.iLastPressedPosY);
                  }
               }

               if ((modifiers & 4) == 4) {
                  GamePanel.this.iXMapPos = (int)(GamePanel.this.iMousePosX / GamePanel.this.fMapScale + GamePanel.this.iXMapPos);
                  GamePanel.this.iYMapPos = (int)(GamePanel.this.iMousePosY / GamePanel.this.fMapScale + GamePanel.this.iYMapPos);
               }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
         }
      );
   }

   private void addPointToCurrentProvince(int nX, int nY) {
      this.currentPointsX.add(nX);
      this.currentPointsY.add(nY);
      if (this.currentProvincePoints == 0) {
         this.currentProvince = new GeneralPath();
         this.currentProvince.moveTo((int)(this.iXMapPos + this.iMousePosX / this.fMapScale), (int)(this.iYMapPos + this.iMousePosY / this.fMapScale));
         this.iCurrentProvinceMaxX = this.iCurrentProvinceMinX = (int)(this.iXMapPos + this.iMousePosX / this.fMapScale);
         this.iCurrentProvinceMaxY = this.iCurrentProvinceMinY = (int)(this.iYMapPos + this.iMousePosY / this.fMapScale);
      } else {
         this.currentProvince.lineTo(nX, nY);
         if (this.iCurrentProvinceMaxX < this.currentPointsX.get(this.currentPointsX.size() - 1)) {
            this.iCurrentProvinceMaxX = this.currentPointsX.get(this.currentPointsX.size() - 1);
         }

         if (this.iCurrentProvinceMinX > this.currentPointsX.get(this.currentPointsX.size() - 1)) {
            this.iCurrentProvinceMinX = this.currentPointsX.get(this.currentPointsX.size() - 1);
         }

         if (this.iCurrentProvinceMaxY < this.currentPointsY.get(this.currentPointsY.size() - 1)) {
            this.iCurrentProvinceMaxY = this.currentPointsY.get(this.currentPointsY.size() - 1);
         }

         if (this.iCurrentProvinceMinY > this.currentPointsY.get(this.currentPointsY.size() - 1)) {
            this.iCurrentProvinceMinY = this.currentPointsY.get(this.currentPointsY.size() - 1);
         }
      }

      this.currentProvincePoints++;
   }

   private void updateCurrentProvinceCenterPoint() {
      if (this.currentPointsX.size() > 0) {
         this.iCurrentProvinceMaxX = this.iCurrentProvinceMinX = this.currentPointsX.get(0);
         this.iCurrentProvinceMaxY = this.iCurrentProvinceMinY = this.currentPointsY.get(0);

         for (int i = 1; i < this.currentPointsX.size(); i++) {
            if (this.iCurrentProvinceMaxX < this.currentPointsX.get(i)) {
               this.iCurrentProvinceMaxX = this.currentPointsX.get(i);
            }

            if (this.iCurrentProvinceMinX > this.currentPointsX.get(i)) {
               this.iCurrentProvinceMinX = this.currentPointsX.get(i);
            }

            if (this.iCurrentProvinceMaxY < this.currentPointsY.get(i)) {
               this.iCurrentProvinceMaxY = this.currentPointsY.get(i);
            }

            if (this.iCurrentProvinceMinY > this.currentPointsY.get(i)) {
               this.iCurrentProvinceMinY = this.currentPointsY.get(i);
            }
         }
      }
   }

   private void rebuildCurrentProvince() {
      int i = 0;

      for (int iSize = this.currentPointsX.size(); i < iSize; i++) {
         if (i == 0) {
            this.currentProvince = new GeneralPath();
            this.currentProvince.moveTo(this.currentPointsX.get(i).intValue(), this.currentPointsY.get(i).intValue());
         } else {
            this.currentProvince.lineTo(this.currentPointsX.get(i).intValue(), this.currentPointsY.get(i).intValue());
         }
      }

      this.updateCurrentProvinceCenterPoint();
   }

   private void rebuildPath(int id) {
      GeneralPath oP = new GeneralPath();
      int i = 0;

      for (int iSize = this.lProvincesPointsX.get(id).size(); i < iSize; i++) {
         if (i == 0) {
            oP.moveTo(this.lProvincesPointsX.get(id).get(i).intValue(), this.lProvincesPointsY.get(id).get(i).intValue());
         } else {
            oP.lineTo(this.lProvincesPointsX.get(id).get(i).intValue(), this.lProvincesPointsY.get(id).get(i).intValue());
         }
      }

      this.vProvinces.set(id, oP);
   }

   private void removePoint() {
      for (int i = 0; i < this.vProvinces.size(); i++) {
         for (int j = 0; j < this.lProvincesPointsX.get(i).size(); j++) {
            if (this.lProvincesPointsX.get(i).get(j) == this.iLastPressedPosX && this.lProvincesPointsY.get(i).get(j) == this.iLastPressedPosY) {
               List<Integer> tempPointsX = new ArrayList<>();
               List<Integer> tempPointsY = new ArrayList<>();

               for (int a = 0; a < this.lProvincesPointsX.get(i).size(); a++) {
                  if (a != j) {
                     tempPointsX.add(this.lProvincesPointsX.get(i).get(a));
                     tempPointsY.add(this.lProvincesPointsY.get(i).get(a));
                  }
               }

               this.lProvincesPointsX.set(i, tempPointsX);
               this.lProvincesPointsY.set(i, tempPointsY);
               this.rebuildPath(i);
            }
         }
      }
   }

   private void addPointToProvince() {
      for (int i = 0; i < this.vProvinces.size(); i++) {
         for (int j = 0; j < this.lProvincesPointsX.get(i).size(); j++) {
            if (this.lProvincesPointsX.get(i).get(j) == this.iLastPressedPosX && this.lProvincesPointsY.get(i).get(j) == this.iLastPressedPosY) {
               List<Integer> tempPointsX = new ArrayList<>();
               List<Integer> tempPointsY = new ArrayList<>();

               for (int a = 0; a < this.lProvincesPointsX.get(i).size(); a++) {
                  tempPointsX.add(this.lProvincesPointsX.get(i).get(a));
                  tempPointsY.add(this.lProvincesPointsY.get(i).get(a));
                  if (a == j) {
                     tempPointsX.add(this.lProvincesPointsX.get(i).get(a) + 1);
                     tempPointsY.add(this.lProvincesPointsY.get(i).get(a));
                  }
               }

               this.lProvincesPointsX.set(i, tempPointsX);
               this.lProvincesPointsY.set(i, tempPointsY);
               this.rebuildPath(i);
               break;
            }
         }
      }
   }

   private void changePointPosition() {
      if (this.bSelectedPointsToChangePosition) {
         for (int i = 0; i < this.vProvinces.size(); i++) {
            for (int j = 0; j < this.lProvincesPointsX.get(i).size(); j++) {
               if (this.lProvincesPointsX.get(i).get(j) == this.iChangePositionX && this.lProvincesPointsY.get(i).get(j) == this.iChangePositionY) {
                  this.lProvincesPointsX.get(i).set(j, this.iLastPressedPosX);
                  this.lProvincesPointsY.get(i).set(j, this.iLastPressedPosY);
                  this.rebuildPath(i);
               }
            }
         }

         this.bSelectedPointsToChangePosition = false;
      } else {
         this.iChangePositionX = this.iLastPressedPosX;
         this.iChangePositionY = this.iLastPressedPosY;

         for (int i = 0; i < this.vProvinces.size(); i++) {
            for (int jx = 0; jx < this.lProvincesPointsX.get(i).size(); jx++) {
               if (this.lProvincesPointsX.get(i).get(jx) == this.iChangePositionX && this.lProvincesPointsY.get(i).get(jx) == this.iChangePositionY) {
                  this.bSelectedPointsToChangePosition = true;
                  return;
               }
            }
         }
      }
   }

   private void setProvinceID() {
      for (int i = 0; i < this.vProvinces.size(); i++) {
         if (this.pathContains(this.iLastPressedPosX, this.iLastPressedPosY, i)) {
            this.iRemovedProvinceID.add(i);
            GeneralPath nPath = new GeneralPath();
            nPath.moveTo(0.0F, 0.0F);
            nPath.lineTo(1.0F, 0.0F);
            nPath.lineTo(1.0F, 1.0F);
            nPath.lineTo(0.0F, 1.0F);
            this.vProvinces.set(i, nPath);
            this.lProvincesPointsX.set(i, new ArrayList<>());
            this.lProvincesPointsY.set(i, new ArrayList<>());
            this.lProvincesCenterX.set(i, 0);
            this.lProvincesCenterY.set(i, 0);
            this.lProvincesMinX.set(i, 0);
            this.lProvincesMinY.set(i, 0);
            this.lProvincesMaxX.set(i, 0);
            this.lProvincesMaxY.set(i, 0);

            for (int k = 1; k < 10; k++) {
               File myObj = new File(mapName + "/data/scales/" + k + "/" + i);
               myObj.delete();
            }

            this.bChooseProvinceMode = false;
            return;
         }
      }
   }

   private final void moveAllProvinces(int nX, int nY) {
      for (int i = this.vProvinces.size() - 1; i >= 0; i--) {
         for (int a = 0; a < this.lProvincesPointsX.get(i).size(); a++) {
            this.lProvincesPointsX.get(i).set(a, this.lProvincesPointsX.get(i).get(a) + nX);
            this.lProvincesPointsY.get(i).set(a, this.lProvincesPointsY.get(i).get(a) + nY);
         }
      }
   }

   private final void removeAllProvincesOutsideTheBox(int nX, int nY, int nX2, int nY2) {
      for (int i = this.vProvinces.size() - 1; i >= 0; i--) {
         if (nX <= this.lProvincesMinX.get(i) && this.lProvincesMaxX.get(i) <= nX2 && nY <= this.lProvincesMinY.get(i) && this.lProvincesMaxY.get(i) <= nY2) {
            for (int a = 0; a < this.lProvincesPointsX.get(i).size(); a++) {
               this.lProvincesPointsX.get(i).set(a, this.lProvincesPointsX.get(i).get(a) * 2);
               this.lProvincesPointsY.get(i).set(a, this.lProvincesPointsY.get(i).get(a) * 2);
            }
         } else {
            this.vProvinces.remove(i);
            this.lProvincesPointsX.remove(i);
            this.lProvincesPointsY.remove(i);
            this.lProvincesCenterX.remove(i);
            this.lProvincesCenterY.remove(i);
            this.lProvincesMinX.remove(i);
            this.lProvincesMinY.remove(i);
            this.lProvincesMaxX.remove(i);
            this.lProvincesMaxY.remove(i);
         }
      }
   }

   private boolean pathContains(int nX, int nY, int nProvinceID) {
      boolean output = false;
      int i = 0;
      int j = this.lProvincesPointsX.get(nProvinceID).size() - 1;

      for (int iSize = this.lProvincesPointsX.get(nProvinceID).size(); i < iSize; j = i++) {
         if (this.lProvincesPointsY.get(nProvinceID).get(i) > nY != this.lProvincesPointsY.get(nProvinceID).get(j) > nY
            && nX
               < (this.lProvincesPointsX.get(nProvinceID).get(j) - this.lProvincesPointsX.get(nProvinceID).get(i))
                     * (nY - this.lProvincesPointsY.get(nProvinceID).get(i))
                     / (this.lProvincesPointsY.get(nProvinceID).get(j) - this.lProvincesPointsY.get(nProvinceID).get(i))
                  + this.lProvincesPointsX.get(nProvinceID).get(j)) {
            output = !output;
         }
      }

      return output;
   }

   private void saveProv() {
      FileWriter fw = null;

      try {
         fw = new FileWriter(this.lProvincesPointsX.size() + "");
         BufferedWriter bw = new BufferedWriter(fw);
         int i = 0;

         for (int nSize = this.currentPointsX.size(); i < nSize; i++) {
            bw.write(this.currentPointsX.get(i) + (i != nSize - 1 ? "," : ""));
         }

         bw.write(";");
         i = 0;

         for (int nSize = this.currentPointsY.size(); i < nSize; i++) {
            bw.write(this.currentPointsY.get(i) + (i != nSize - 1 ? "," : ""));
         }

         bw.close();
         fw.close();
      } catch (IOException var5) {
         System.out.println("AoCC");
      }
   }

   private void saveProvinces() {
      FileWriter fw = null;

      try {
         fw = new FileWriter(fileName);
         BufferedWriter bw = new BufferedWriter(fw);
         int i = 0;

         for (int nSize = this.lProvincesPointsX.size(); i < nSize; i++) {
            int j = 0;

            for (int jSize = this.lProvincesPointsX.get(i).size(); j < jSize; j++) {
               bw.write(this.lProvincesPointsX.get(i).get(j) + (j != jSize - 1 ? "," : ""));
            }

            bw.newLine();
            j = 0;

            for (int jSize = this.lProvincesPointsY.get(i).size(); j < jSize; j++) {
               bw.write(this.lProvincesPointsY.get(i).get(j) + (j != jSize - 1 ? "," : ""));
            }

            if (i != nSize - 1) {
               bw.newLine();
            }
         }

         if (this.currentProvincePoints > 1) {
            bw.newLine();
            i = 0;

            for (int nSize = this.currentPointsX.size(); i < nSize; i++) {
               bw.write(this.currentPointsX.get(i) + (i != nSize - 1 ? "," : ""));
            }

            bw.newLine();
            i = 0;

            for (int nSize = this.currentPointsY.size(); i < nSize; i++) {
               bw.write(this.currentPointsY.get(i) + (i != nSize - 1 ? "," : ""));
            }
         }

         bw.close();
         fw.close();
      } catch (IOException var7) {
      }

      this.clearCurrentData();
   }

   private void saveProvincesJson() {
      this.clearCurrentData();

      for (int i = 0; i < Math.ceil(this.lProvincesPointsX.size() / provincesPerFile) + 1.0; i++) {
         this.saveProvincesJson(i);
      }
   }

   private void saveProvincesJson(int id) {
      FileWriter fw = null;

      try {
         fw = new FileWriter(fileNameJson.substring(0, fileNameJson.indexOf(".json")) + (id > 0 ? "_" + id : "") + ".json");
         BufferedWriter bw = new BufferedWriter(fw);
         bw.write("{");
         bw.newLine();
         bw.write("Age_of_History: Data,");
         bw.newLine();
         bw.write("Data: [");
         bw.newLine();
         int nProvinceID = provincesPerFile * id;
         int i = provincesPerFile * id;
         int p = 0;

         for (int nSize = this.lProvincesPointsX.size(); i < nSize && p < provincesPerFile; p++) {
            if (this.lProvincesPointsX.get(i).size() != 0) {
               bw.write("{");
               bw.newLine();
               bw.write("pX:[");
               int j = 0;

               for (int jSize = this.lProvincesPointsX.get(i).size(); j < jSize; j++) {
                  bw.write(this.lProvincesPointsX.get(i).get(j) + (j != jSize - 1 ? "," : ""));
               }

               bw.write("],");
               bw.newLine();
               bw.write("pY:[");
               j = 0;

               for (int jSize = this.lProvincesPointsY.get(i).size(); j < jSize; j++) {
                  bw.write(this.lProvincesPointsY.get(i).get(j) + (j != jSize - 1 ? "," : ""));
               }

               bw.write("]");
               bw.newLine();
               bw.write("},");
               bw.newLine();
            }

            i++;
         }

         bw.write("]");
         bw.newLine();
         bw.write("}");
         bw.close();
         fw.close();
      } catch (IOException var10) {
      }
   }

   private void autoPath() {
      int startID = 0;
      int i = 0;

      for (int iSize = this.lProvincesPointsX.size(); i < iSize; i++) {
         boolean found = false;
         int k = 0;

         for (int kSize = this.lProvincesPointsX.get(i).size(); k < kSize; k++) {
            if (Objects.equals(this.currentPointsX.get(this.currentPointsX.size() - 1), this.lProvincesPointsX.get(i).get(k))
               && Objects.equals(this.currentPointsY.get(this.currentPointsY.size() - 1), this.lProvincesPointsY.get(i).get(k))) {
               if (k != 0
                  && Objects.equals(this.currentPointsX.get(this.currentPointsX.size() - 2), this.lProvincesPointsX.get(i).get(k - 1))
                  && Objects.equals(this.currentPointsY.get(this.currentPointsY.size() - 2), this.lProvincesPointsY.get(i).get(k - 1))) {
                  found = true;
                  this.forward = true;
                  startID = k + 1;
               } else if (k + 1 < kSize
                  && Objects.equals(this.currentPointsX.get(this.currentPointsX.size() - 2), this.lProvincesPointsX.get(i).get(k + 1))
                  && Objects.equals(this.currentPointsY.get(this.currentPointsY.size() - 2), this.lProvincesPointsY.get(i).get(k + 1))) {
                  this.forward = false;
                  found = true;
                  startID = k - 1;
               }
               break;
            }
         }

         if (found) {
            if (!this.forward) {
               while (startID > 0) {
                  k = 0;

                  for (int nSize = this.lProvincesPointsX.size(); k < nSize; k++) {
                     if (i != k) {
                        int o = 0;

                        for (int oSize = this.lProvincesPointsX.get(k).size(); o < oSize; o++) {
                           if (Objects.equals(this.lProvincesPointsX.get(k).get(o), this.lProvincesPointsX.get(i).get(startID))
                              && Objects.equals(this.lProvincesPointsY.get(k).get(o), this.lProvincesPointsY.get(i).get(startID))) {
                              return;
                           }
                        }
                     }
                  }

                  k = 0;

                  for (int nSizex = this.currentPointsX.size(); k < nSizex; k++) {
                     if (Objects.equals(this.currentPointsX.get(k), this.lProvincesPointsX.get(i).get(startID))
                        && Objects.equals(this.currentPointsY.get(k), this.lProvincesPointsY.get(i).get(startID))) {
                        return;
                     }
                  }

                  this.addPointToCurrentProvince(this.lProvincesPointsX.get(i).get(startID), this.lProvincesPointsY.get(i).get(startID));
                  startID--;
               }
               break;
            }

            for (int kSizex = this.lProvincesPointsX.get(i).size(); startID < kSizex; startID++) {
               int n = 0;

               for (int nSizexx = this.lProvincesPointsX.size(); n < nSizexx; n++) {
                  if (i != n) {
                     int o = 0;

                     for (int oSizex = this.lProvincesPointsX.get(n).size(); o < oSizex; o++) {
                        if (Objects.equals(this.lProvincesPointsX.get(n).get(o), this.lProvincesPointsX.get(i).get(startID))
                           && Objects.equals(this.lProvincesPointsY.get(n).get(o), this.lProvincesPointsY.get(i).get(startID))) {
                           return;
                        }
                     }
                  }
               }

               n = 0;

               for (int nSizexxx = this.currentPointsX.size(); n < nSizexxx; n++) {
                  if (Objects.equals(this.currentPointsX.get(n), this.lProvincesPointsX.get(i).get(startID))
                     && Objects.equals(this.currentPointsY.get(n), this.lProvincesPointsY.get(i).get(startID))) {
                     return;
                  }
               }

               this.addPointToCurrentProvince(this.lProvincesPointsX.get(i).get(startID), this.lProvincesPointsY.get(i).get(startID));
            }
            break;
         }
      }

      this.rebuildCurrentProvince();
   }

   private void exportData() {
      int i = 0;

      for (int iSize = this.lProvincesPointsX.size(); i < iSize; i++) {
         System.out.println("nProvinces.add(get" + i + "());");
      }

      i = 0;

      for (int iSize = this.lProvincesPointsX.size(); i < iSize; i++) {
         System.out.println("\tprivate final Province get" + i + "() {");
         System.out
            .println(
               "\t\treturn new Province("
                  + i
                  + ", "
                  + (i > this.iEndOfLandProvinces ? -2 : (this.seaAcces(i) ? 0 : -1))
                  + ", get"
                  + i
                  + "X(), get"
                  + i
                  + "Y(), getPB"
                  + i
                  + "(), new short[]{"
                  + this.getNeighboring(i)
                  + "}, new short[]{"
                  + this.getNeighSea(i)
                  + "});"
            );
         System.out.println("\t}\n");
      }

      System.out.println("\t// -------------------------------------------------\n");
      i = 0;

      for (int iSize = this.lProvincesPointsX.size(); i < iSize; i++) {
         System.out.println("\tprivate final Province_Border[] getPB" + i + "() {");
         boolean retNull = true;

         for (int j = i + 1; j < this.lProvincesPointsX.size(); j++) {
            boolean found = false;
            int n = 0;

            for (int nSize = this.lProvincesPointsX.get(i).size(); n < nSize; n++) {
               int o = 0;

               for (int oSize = this.lProvincesPointsX.get(j).size(); o < oSize; o++) {
                  if (this.lProvincesPointsX.get(i).get(n).equals(this.lProvincesPointsX.get(j).get(o))
                     && this.lProvincesPointsY.get(i).get(n).equals(this.lProvincesPointsY.get(j).get(o))) {
                     found = true;
                     if (retNull) {
                        System.out.print("\t\treturn new Province_Border[]{");
                     }

                     retNull = false;
                     break;
                  }
               }

               if (found) {
                  break;
               }
            }

            if (found) {
               List<Integer> pX = new ArrayList<>();
               List<Integer> pY = new ArrayList<>();
               boolean l1 = false;
               int o = 0;

               for (int oSizex = this.lProvincesPointsX.get(j).size(); o < oSizex; o++) {
                  if (this.lProvincesPointsX.get(i).get(this.lProvincesPointsX.get(i).size() - 1).equals(this.lProvincesPointsX.get(j).get(o))
                     && this.lProvincesPointsY.get(i).get(this.lProvincesPointsY.get(i).size() - 1).equals(this.lProvincesPointsY.get(j).get(o))) {
                     l1 = true;
                  }
               }

               if (l1) {
                  l1 = false;
                  o = 0;

                  for (int oSizexx = this.lProvincesPointsX.get(i).size(); o < oSizexx; o++) {
                     if (this.lProvincesPointsX.get(j).get(this.lProvincesPointsX.get(j).size() - 1).equals(this.lProvincesPointsX.get(i).get(o))
                        && this.lProvincesPointsY.get(j).get(this.lProvincesPointsY.get(j).size() - 1).equals(this.lProvincesPointsY.get(i).get(o))) {
                        l1 = true;
                     }
                  }

                  if (!l1) {
                     boolean f1 = false;

                     for (int ox = this.lProvincesPointsX.get(j).size() - 1; ox >= 0; ox--) {
                        if (!f1) {
                           int nx = 0;

                           for (int nSize = this.lProvincesPointsX.get(i).size(); nx < nSize; nx++) {
                              if (this.lProvincesPointsX.get(j).get(ox).equals(this.lProvincesPointsX.get(i).get(nx))
                                 && this.lProvincesPointsY.get(j).get(ox).equals(this.lProvincesPointsY.get(i).get(nx))) {
                                 f1 = true;
                                 pX.add(this.lProvincesPointsX.get(j).get(ox));
                                 pY.add(this.lProvincesPointsY.get(j).get(ox));
                                 break;
                              }
                           }
                        } else {
                           boolean end = true;
                           int nx = 0;

                           for (int nSizex = this.lProvincesPointsX.get(i).size(); nx < nSizex; nx++) {
                              if (this.lProvincesPointsX.get(j).get(ox).equals(this.lProvincesPointsX.get(i).get(nx))
                                 && this.lProvincesPointsY.get(j).get(ox).equals(this.lProvincesPointsY.get(i).get(nx))) {
                                 end = false;
                                 pX.add(this.lProvincesPointsX.get(j).get(ox));
                                 pY.add(this.lProvincesPointsY.get(j).get(ox));
                                 break;
                              }
                           }

                           if (end) {
                              break;
                           }
                        }
                     }
                  } else {
                     o = 0;
                     boolean t1 = false;

                     for (int oxx = this.lProvincesPointsX.get(i).size() - 1; oxx >= 0; oxx--) {
                        t1 = false;
                        int nx = 0;

                        for (int nSizexx = this.lProvincesPointsX.get(j).size(); nx < nSizexx; nx++) {
                           if (this.lProvincesPointsX.get(i).get(oxx).equals(this.lProvincesPointsX.get(j).get(nx))
                              && this.lProvincesPointsY.get(i).get(oxx).equals(this.lProvincesPointsY.get(j).get(nx))) {
                              t1 = true;
                              break;
                           }
                        }

                        if (!t1) {
                           while (oxx < this.lProvincesPointsX.get(i).size() - 1) {
                              pX.add(this.lProvincesPointsX.get(i).get(oxx));
                              pY.add(this.lProvincesPointsY.get(i).get(oxx));
                              oxx++;
                           }
                           break;
                        }
                     }

                     for (int h = 0; h < this.lProvincesPointsX.get(i).size(); h++) {
                        boolean addT = false;

                        for (int nx = 0; nx < this.lProvincesPointsX.get(j).size(); nx++) {
                           if (this.lProvincesPointsX.get(i).get(h).equals(this.lProvincesPointsX.get(j).get(nx))
                              && this.lProvincesPointsY.get(i).get(h).equals(this.lProvincesPointsY.get(j).get(nx))) {
                              addT = true;
                              break;
                           }
                        }

                        if (!addT) {
                           break;
                        }

                        pX.add(this.lProvincesPointsX.get(i).get(h));
                        pY.add(this.lProvincesPointsY.get(i).get(h));
                     }
                  }
               } else {
                  boolean f1 = false;

                  for (int oxx = this.lProvincesPointsX.get(i).size() - 1; oxx >= 0; oxx--) {
                     if (!f1) {
                        int nxx = 0;

                        for (int nSizexxx = this.lProvincesPointsX.get(j).size(); nxx < nSizexxx; nxx++) {
                           if (this.lProvincesPointsX.get(i).get(oxx).equals(this.lProvincesPointsX.get(j).get(nxx))
                              && this.lProvincesPointsY.get(i).get(oxx).equals(this.lProvincesPointsY.get(j).get(nxx))) {
                              f1 = true;
                              pX.add(this.lProvincesPointsX.get(i).get(oxx));
                              pY.add(this.lProvincesPointsY.get(i).get(oxx));
                              break;
                           }
                        }
                     } else {
                        boolean end = true;
                        int nxx = 0;

                        for (int nSizexxxx = this.lProvincesPointsX.get(j).size(); nxx < nSizexxxx; nxx++) {
                           if (this.lProvincesPointsX.get(i).get(oxx).equals(this.lProvincesPointsX.get(j).get(nxx))
                              && this.lProvincesPointsY.get(i).get(oxx).equals(this.lProvincesPointsY.get(j).get(nxx))) {
                              end = false;
                              pX.add(this.lProvincesPointsX.get(i).get(oxx));
                              pY.add(this.lProvincesPointsY.get(i).get(oxx));
                              break;
                           }
                        }

                        if (end) {
                           break;
                        }
                     }
                  }
               }

               System.out.print("\n\t\t\t\tnew Province_Border(" + j + ", ");
               System.out.print("new short[] {");
               o = 0;

               for (int aSize = pX.size(); o < aSize; o++) {
                  System.out.print(pX.get(o) + ",");
               }

               System.out.print("},");
               System.out.print("new short[] {");
               o = 0;

               for (int aSize = pY.size(); o < aSize; o++) {
                  System.out.print(pY.get(o) + ",");
               }

               System.out.print("}");
               System.out.print("),");
            }
         }

         if (retNull) {
            System.out.print("\t\treturn null;");
         } else {
            System.out.print("};");
         }

         System.out.println("\n\t}");
      }

      System.out.println("\t// -------------------------------------------------\n");
      i = 0;

      for (int iSize = this.lProvincesPointsX.size(); i < iSize; i++) {
         System.out.println("\tprivate final short[] get" + i + "X() {");
         System.out.print("\t\treturn new short[]{");
         int j = 0;

         for (int jSize = this.lProvincesPointsX.get(i).size(); j < jSize; j++) {
            System.out.print(this.lProvincesPointsX.get(i).get(j) + ",");
         }

         System.out.println("};");
         System.out.println("\t}");
         System.out.println("\tprivate final short[] get" + i + "Y() {");
         System.out.print("\t\treturn new short[]{");
         j = 0;

         for (int jSize = this.lProvincesPointsY.get(i).size(); j < jSize; j++) {
            System.out.print(this.lProvincesPointsY.get(i).get(j) + ",");
         }

         System.out.println("};");
         System.out.println("\t}");
      }
   }

   String getNeighboring(int i) {
      String output = "";

      for (int j = 0; j < this.neighboring.get(i).size(); j++) {
         boolean add = true;

         for (int k = j + 1; k < this.neighboring.get(i).size(); k++) {
            int a = this.neighboring.get(i).get(k);
            int c = this.neighboring.get(i).get(j);
            if (a == c) {
               add = false;
            }
         }

         int c = this.neighboring.get(i).get(j);
         if (c > this.iEndOfLandProvinces && i < this.iEndOfLandProvinces + 1) {
            add = false;
         }

         if (add) {
            output = output + this.neighboring.get(i).get(j) + ", ";
         }
      }

      return output;
   }

   private String getNeighSea(int i) {
      String outp = "";
      if (i > this.iEndOfLandProvinces) {
         return "";
      } else {
         for (int j = 0; j < this.neighboring.get(i).size(); j++) {
            boolean add = true;

            for (int k = j + 1; k < this.neighboring.get(i).size(); k++) {
               int a = this.neighboring.get(i).get(k);
               int c = this.neighboring.get(i).get(j);
               if (a == c) {
                  add = false;
               }

               if (c < this.iEndOfLandProvinces + 1) {
                  add = false;
               }
            }

            if (this.neighboring.get(i).get(j) < this.iEndOfLandProvinces + 1) {
               add = false;
            }

            if (add) {
               outp = outp + this.neighboring.get(i).get(j) + ", ";
            }
         }

         return outp;
      }
   }

   boolean seaAcces(int i) {
      for (int j = 0; j < this.neighboring.get(i).size(); j++) {
         for (int k = 0; k < j; k++) {
            if (this.neighboring.get(i).get(j) > this.iEndOfLandProvinces) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void run() {
      while (this.bRunning) {
         this.gameThreadRender();
         this.paintScreen();
      }
   }

   private void paintScreen() {
      try {
         Graphics g = this.getGraphics();
         if (this.baseImage != null && g != null) {
            g.drawImage(this.baseImage, 0, 0, null);
         }

         Toolkit.getDefaultToolkit().sync();
         g.dispose();
      } catch (Exception var3) {
         System.err.println(var3);
      }

      this.requestFocusInWindow();
   }

   private void StartGame() {
      if (this.gameThread == null || !this.bRunning) {
         this.gameThread = new Thread(this);
         this.gameThread.start();
         this.bRunning = true;
      }
   }

   private void gameThreadRender() {
      if (this.baseImage == null || GWIDTH != this.getWidth() || GHEIGHT != this.getHeight()) {
         if (this.getWidth() > 0 && this.getHeight() > 0) {
            GWIDTH = this.getWidth();
            GHEIGHT = this.getHeight();
         }

         this.baseImage = this.createImage(GWIDTH, GHEIGHT);
         if (this.baseImage == null) {
            return;
         }

         this.baseGraphic = this.baseImage.getGraphics();
      }

      this.baseGraphic.setColor(Color.WHITE);
      this.baseGraphic.fillRect(0, 0, GWIDTH, GHEIGHT);
      this.draw(this.baseGraphic);
   }

   public void StopGame() {
      if (this.bRunning) {
         this.bRunning = false;
      }
   }

   @Override
   public void addNotify() {
      super.addNotify();
      this.StartGame();
   }

   public BufferedImage ImageReader(String nFile) {
      try {
         File imageFile = new File(nFile);
         return ImageIO.read(imageFile);
      } catch (IOException var3) {
         return null;
      }
   }

   private class NeighboringProvincesThread extends Thread {
      @Override
      public void run() {
         try {
            int i = 0;

            for (int iSize = GamePanel.this.lProvincesPointsX.size(); i < iSize; i++) {
               List<Integer> oC = new ArrayList<>();
               int j = 0;

               for (int jSize = GamePanel.this.lProvincesPointsX.get(i).size(); j < jSize; j++) {
                  int k = 0;

                  for (int kSize = GamePanel.this.lProvincesPointsX.size(); k < kSize; k++) {
                     if (k != i) {
                        int u = 0;

                        for (int uSize = GamePanel.this.lProvincesPointsX.get(k).size(); u < uSize; u++) {
                           int a = GamePanel.this.lProvincesPointsX.get(i).get(j);
                           int c = GamePanel.this.lProvincesPointsX.get(k).get(u);
                           if (a == c || a + GamePanel.this.iMapEditorBackgroundWidth == c || a == c + GamePanel.this.iMapEditorBackgroundWidth) {
                              int e = GamePanel.this.lProvincesPointsY.get(i).get(j);
                              int h = GamePanel.this.lProvincesPointsY.get(k).get(u);
                              if (e == h) {
                                 oC.add(k);
                                 k = kSize;
                                 break;
                              }
                           }
                        }
                     }
                  }
               }

               GamePanel.this.neighboring.add(oC);
            }

            GamePanel.this.neighboringProvincesReadyToUse = true;
         } catch (IndexOutOfBoundsException var14) {
         }
      }
   }
}
