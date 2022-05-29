package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueenChessComponent extends ChessComponent {
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;
    private Image queenImage;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }


    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        if (color == ChessColor.WHITE) {
            super.setName("q");
        } else {
            super.setName("Q");
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
        int X= destination.getX();
        int Y= destination.getY();


        if (chessComponents[X][Y].getChessColor() != super.getChessColor()) {
                    boolean can = true;

                    if (source.getX() == X) {

                        for (int j = Math.min(source.getY(), Y) + 1; j < Math.max(source.getY(), Y); ++j) {
                            if (!(chessComponents[X][j] instanceof EmptySlotComponent)) {
                                can = false;
                                break;
                            }
                        }
                    } else if (source.getY() == Y) {


                        for (int i = Math.min(source.getX(), X) + 1; i < Math.max(source.getX(), X); ++i) {
                            if (!(chessComponents[i][Y] instanceof EmptySlotComponent)) {
                                can = false;
                                break;
                            }
                        }
                    } else if (Math.abs(X - source.getX()) == Math.abs(Y - source.getY())) {
                        if (X > source.getX() && Y > source.getY()) {
                            for (int i = 1; source.getX() + i < X; ++i) {
                                if (!(chessComponents[source.getX() + i][source.getY() + i] instanceof EmptySlotComponent)) {
                                    can = false;
                                    break;
                                }
                            }
                        } else if (X < source.getX() && Y < source.getY()) {
                            for (int i = 1; X + i < source.getX(); ++i) {
                                if (!(chessComponents[X + i][Y + i] instanceof EmptySlotComponent)) {
                                    can = false;
                                    break;
                                }
                            }
                        } else if (X > source.getX() && Y < source.getY()) {
                            for (int i = 1; source.getX() + i < X; ++i) {
                                if (!(chessComponents[source.getX() + i][source.getY() - i] instanceof EmptySlotComponent)) {
                                    can = false;
                                    break;
                                }
                            }
                        } else if (X < source.getX() && Y > source.getY()) {
                            for (int i = 1; X + i  < source.getX(); ++i) {
                                if (!(chessComponents[X + i][Y - i] instanceof EmptySlotComponent)) {
                                    can = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        can = false;
                    }
                    return can;
                }

        return false;
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
        g.drawImage(queenImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}


