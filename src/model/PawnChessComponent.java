package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PawnChessComponent extends ChessComponent {

    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;


    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        if (color == ChessColor.WHITE) {
            super.setName("p");
        } else {
            super.setName("P");
        }
        initiateRookImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int X = destination.getX();
        int Y = destination.getY();
        if (super.getChessColor() == ChessColor.BLACK) {
            if (source.getX() == 1) {

                return source.getY() == Y && (X - source.getX() == 1 && chessComponents[X][Y] instanceof EmptySlotComponent || X - source.getX() == 2 && chessComponents[2][Y] instanceof EmptySlotComponent && chessComponents[3][Y] instanceof EmptySlotComponent) || Math.abs(Y - source.getY()) == 1 && X - source.getX() == 1 && chessComponents[X][Y].getChessColor() == ChessColor.WHITE;
            } else {

                return source.getY() == Y && X - source.getX() == 1 && chessComponents[X][Y] instanceof EmptySlotComponent || Math.abs(Y - source.getY()) == 1 && X - source.getX() == 1 && chessComponents[X][Y].getChessColor() == ChessColor.WHITE;

            }
        } else {
            if (source.getX() == 6) {

                return source.getY() == Y && (X - source.getX() == -1 && chessComponents[X][Y] instanceof EmptySlotComponent || X - source.getX() == -2 && chessComponents[5][Y] instanceof EmptySlotComponent && chessComponents[4][Y] instanceof EmptySlotComponent) || Math.abs(Y - source.getY()) == 1 && X - source.getX() == -1 && chessComponents[X][Y].getChessColor() == ChessColor.WHITE;

            } else {

                return source.getY() == Y && X - source.getX() == -1 && chessComponents[X][Y] instanceof EmptySlotComponent || Math.abs(Y - source.getY()) == 1 && X - source.getX() == -1 && chessComponents[X][Y].getChessColor() == ChessColor.BLACK;

            }
        }
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}

