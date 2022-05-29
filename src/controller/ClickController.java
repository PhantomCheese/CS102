package controller;


import model.ChessColor;
import model.ChessComponent;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;

    public void setFirst(ChessComponent first) {
        this.first = first;
    }

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                first = null;
                if (chessComponent.getName().equals("K")){
                    JOptionPane.showMessageDialog(null,"白棋胜利！");
                    if (chessboard.currentPlayer.getColor()== ChessColor.WHITE){
                        chessboard.currentPlayer.setScore(chessboard.currentPlayer.getScore()+1);
                    }
                    autoSave();
                    chessboard.reinitialize();
                }
                if (chessComponent.getName().equals("k")){
                    JOptionPane.showMessageDialog(null,"黑棋胜利！");
                    if (chessboard.currentPlayer.getColor()== ChessColor.BLACK){
                        chessboard.currentPlayer.setScore(chessboard.currentPlayer.getScore()+1);
                    }
                    autoSave();
                    chessboard.reinitialize();
                }
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

    public void autoSave(){
        StringBuilder sb= new StringBuilder();
        for (int i = 0; i < chessboard.userArrayList.size(); i++) {
            sb.append(chessboard.userArrayList.get(i).getName()).append("/").append(chessboard.userArrayList.get(i).score).append("/").append("\n");
        }
        File file = new File("resource/UserList");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException a) {
            a.printStackTrace();
        }
    }
}

