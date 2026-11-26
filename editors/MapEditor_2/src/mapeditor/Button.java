package mapeditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Button {
   protected int iPosX;
   protected int iPosY;
   protected int iWidth;
   protected int iHeight;
   protected boolean visible = true;
   protected String sText = "";

   protected Button(String sText, int iPosX, int iPosY, int iWidth, int iHeight, boolean visible) {
      this.sText = sText;
      this.iPosX = iPosX;
      this.iPosY = iPosY;
      this.iWidth = iWidth;
      this.iHeight = iHeight;
      this.visible = visible;
   }

   public void draw(Graphics g) {
      Graphics2D g2d = (Graphics2D)g.create();
      Font font = new Font("SansSerif", 1, 14);
      g2d.setFont(font);
      g2d.setColor(new Color(255, 255, 255, 255));
      AffineTransform affinetransform = new AffineTransform();
      FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
      g2d.drawString(this.sText, this.iPosX, this.iPosY + (int)(font.getStringBounds(this.sText, frc).getHeight() / 2.0));
   }
}
