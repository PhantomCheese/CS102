package view;

import User.User;
import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    public GameController gameController;
    JLabel statusLabel;
    JLabel userLabel;







    public ChessGameFrame(int width, int height) {
        setTitle("国际象棋"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addLabel();
        addChessboard();

        addSaveButton();
        addLoadButton();
        addRestartButton();
        addUserLabel();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, statusLabel);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);

    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("WHITE");
        statusLabel.setLocation(HEIGHT, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addUserLabel(){
        userLabel= new JLabel("");
        userLabel.setLocation(HEIGHT-600, HEIGHT / 10-60);
        userLabel.setSize(400, 60);
        userLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(userLabel);
    }


    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "OK!"));
        button.setLocation(HEIGHT, HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click save");
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jfc.showDialog(new JLabel(), "选择存档文件");
            String path = jfc.getSelectedFile().getAbsolutePath();
            gameController.saveGame(path);
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jfc.showDialog(new JLabel(), "选择存档文件");
            String path = jfc.getSelectedFile().getAbsolutePath();
            System.out.println(path);
            gameController.loadGameFromFile(path);
        });
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.setLocation(HEIGHT, HEIGHT / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click restart");
            gameController.restart();
        });

    }


}
