package controller;

import model.ChessColor;
import view.Chessboard;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    public Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            if (!path.substring(path.length()-3).equals("txt")){
                JOptionPane.showMessageDialog(null,"need txt file !");
                return null;
            }
            List<String> chessData = Files.readAllLines(Path.of(path));
            boolean valid = true;
            String str = "BbKkNnPpQqRr_", bw = "bw";
            if (chessData.size() != 9) valid = false;
            for (int i = 0; i < 8; i++) {
                if (chessData.get(i).length() != 8) {
                    valid = false;
                    break;
                }
                for (int j = 0; j < 8; j++) {
                    if (!str.contains(String.valueOf(chessData.get(i).charAt(j)))) {
                        valid = false;
                        break;
                    }
                }
            }
            if (chessData.get(8).length() != 1) valid = false;
            if (!bw.contains(String.valueOf(chessData.get(8).charAt(0)))) valid = false;
            if (valid) {
                chessboard.loadGame(chessData);
                return chessData;
            }else {
                System.out.println("The save file is not valid !");
                JOptionPane.showMessageDialog(null,"The save file is not valid !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveGame(String path) {
        StringBuilder ChessboardGraph = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessboardGraph.append(chessboard.getChessComponents()[i][j].getName());
            }
            ChessboardGraph.append("\n");
        }
        if (chessboard.getCurrentColor() == ChessColor.WHITE) {
            ChessboardGraph.append("w");
        } else {
            ChessboardGraph.append("b");
        }
        System.out.println(ChessboardGraph);
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(ChessboardGraph.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        chessboard.reinitialize();
    }

    public Chessboard getChessboard() {
        return chessboard;
    }
}
