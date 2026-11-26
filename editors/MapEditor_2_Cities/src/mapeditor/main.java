package mapeditor;

import java.awt.GridBagLayout;
import javax.swing.JFrame;

public class main extends JFrame {
   private GamePanel oGamePanel = new GamePanel();

   public static void main(String[] args) {
      GamePanel.loadConfigFile();
      new main();
   }

   protected main() {
      this.setSize(GamePanel.GWIDTH, GamePanel.GHEIGHT);
      this.setDefaultCloseOperation(3);
      this.setTitle("Age of History 3 - www.LukaszJakowski.pl");
      new GridBagLayout();
      this.setVisible(true);
      if (GamePanel.MAXIMUM_SIZE) {
         this.setExtendedState(6);
      }

      this.setResizable(true);
      this.add(this.oGamePanel);
   }
}
