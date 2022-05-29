package view;


import User.User;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    private JLabel statusLabel;
    public User currentPlayer;
    public ArrayList<User> userArrayList;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;

    public ClickController getClickController() {
        return clickController;
    }

    public Chessboard(int width, int height, JLabel statusLabel) {

        this.statusLabel=statusLabel;
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        iniKnightOnBoard(0,1,ChessColor.BLACK);
        iniKnightOnBoard(0,CHESSBOARD_SIZE-2,ChessColor.BLACK);
        iniKnightOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
        iniKnightOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-2,ChessColor.WHITE);

        iniBishopOnBoard(0,2,ChessColor.BLACK);
        iniBishopOnBoard(0,CHESSBOARD_SIZE-3,ChessColor.BLACK);
        iniBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        iniBishopOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-3,ChessColor.WHITE);

        iniKingOnBoard(0,4,ChessColor.BLACK);
        iniKingOnBoard(CHESSBOARD_SIZE-1,4,ChessColor.WHITE);

        iniQueenOnBoard(0,3,ChessColor.BLACK);
        iniQueenOnBoard(CHESSBOARD_SIZE-1,3,ChessColor.WHITE);

        for (int i = 0; i < 8; i++) {
            iniPawnOnBoard(1,i,ChessColor.BLACK);
        }
        for (int i = 0; i < 8; i++) {
            iniPawnOnBoard(CHESSBOARD_SIZE-2,i,ChessColor.WHITE);
        }

    }

    public void reinitialize(){
        initiateEmptyChessboard();


        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        iniKnightOnBoard(0,1,ChessColor.BLACK);
        iniKnightOnBoard(0,CHESSBOARD_SIZE-2,ChessColor.BLACK);
        iniKnightOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
        iniKnightOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-2,ChessColor.WHITE);

        iniBishopOnBoard(0,2,ChessColor.BLACK);
        iniBishopOnBoard(0,CHESSBOARD_SIZE-3,ChessColor.BLACK);
        iniBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        iniBishopOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-3,ChessColor.WHITE);

        iniKingOnBoard(0,4,ChessColor.BLACK);
        iniKingOnBoard(CHESSBOARD_SIZE-1,4,ChessColor.WHITE);

        iniQueenOnBoard(0,3,ChessColor.BLACK);
        iniQueenOnBoard(CHESSBOARD_SIZE-1,3,ChessColor.WHITE);

        for (int i = 0; i < 8; i++) {
            iniPawnOnBoard(1,i,ChessColor.BLACK);
        }
        for (int i = 0; i < 8; i++) {
            iniPawnOnBoard(CHESSBOARD_SIZE-2,i,ChessColor.WHITE);
        }

        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents.length; j++) {
                chessComponents[i][j].repaint();
            }
        }
        currentColor = ChessColor.WHITE;
        statusLabel.setText("WHITE");
        clickController.setFirst(null);
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();

    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        if (currentColor==ChessColor.WHITE) {
            statusLabel.setText("WHITE");
        }else {
            statusLabel.setText("BLACK");
        }
    }


    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    private void iniQueenOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void iniKingOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void iniKnightOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void iniBishopOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void iniPawnOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char c = chessData.get(i).charAt(j);
                switch (c) {
                    case 'B' -> iniBishopOnBoard(i,j,ChessColor.BLACK);
                    case 'b' -> iniBishopOnBoard(i,j,ChessColor.WHITE);
                    case 'K' -> iniKingOnBoard(i,j,ChessColor.BLACK);
                    case 'k' -> iniKingOnBoard(i,j,ChessColor.WHITE);
                    case 'N' -> iniKnightOnBoard(i,j,ChessColor.BLACK);
                    case 'n' -> iniKnightOnBoard(i,j,ChessColor.WHITE);
                    case 'P' -> iniPawnOnBoard(i,j,ChessColor.BLACK);
                    case 'p' -> iniPawnOnBoard(i,j,ChessColor.WHITE);
                    case 'Q' -> iniQueenOnBoard(i,j,ChessColor.BLACK);
                    case 'q' -> iniQueenOnBoard(i,j,ChessColor.WHITE);
                    case 'R' -> initRookOnBoard(i,j,ChessColor.BLACK);
                    case 'r' -> initRookOnBoard(i,j,ChessColor.WHITE);
                }
                chessComponents[i][j].repaint();
            }
        }

        switch (chessData.get(8).charAt(0)) {
            case 'w' -> {
                currentColor = ChessColor.WHITE;
                statusLabel.setText("WHITE");
            }
            case 'b' -> {
                currentColor = ChessColor.BLACK;
                statusLabel.setText("BLACK");
            }
        }

    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }
}
