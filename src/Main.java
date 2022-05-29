import view.ChessGameFrame;
import view.Menu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            ImageIcon img= new ImageIcon();
            JLabel imgLabel= new JLabel(img);

            Menu menu = new Menu(1000,760,mainFrame);
            menu.setVisible(true);
        });
    }
}
