package controller;

import model.ChessColor;
import model.ChessComponent;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            if (!path.endsWith(".txt")) {
                JOptionPane.showMessageDialog(null, "文件格式错误(例如规定是 txt，导入的时候是 json)", "Error!", JOptionPane.ERROR_MESSAGE);
                Chessboard.Error = true;
                return null;
            }
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeDataToFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                JOptionPane.showMessageDialog(null, "it was created!", " ", JOptionPane.PLAIN_MESSAGE);
                FileWriter writer = new FileWriter(fileName + ".txt");
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        switch (chessboard.getChessComponents()[i][j].getName()) {
                            case "R":
                                writer.write("R");
                                break;
                            case "K":
                                writer.write("K");
                                break;
                            case "N":
                                writer.write("N");
                                break;
                            case "B":
                                writer.write("B");
                                break;
                            case "Q":
                                writer.write("Q");
                                break;
                            case "P":
                                writer.write("P");
                                break;
                            case "r":
                                writer.write("r");
                                break;
                            case "k":
                                writer.write("k");
                                break;
                            case "n":
                                writer.write("n");
                                break;
                            case "b":
                                writer.write("b");
                                break;
                            case "q":
                                writer.write("q");
                                break;
                            case "p":
                                writer.write("p");
                                break;
                            case "_":
                                writer.write("_");
                                break;
                        }
                        if (j == 7) {
                            writer.write("\n");
                            writer.flush();
                        }
                    }
                }
                writer.write(chessboard.getCurrentColor().toString().substring(0, 1).toLowerCase(Locale.ROOT) + "\n");
                writer.close();
            } else {
                JOptionPane.showMessageDialog(null, "存档已存在", " ", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "error!", " ", JOptionPane.PLAIN_MESSAGE);
            e.printStackTrace();
        }
    }

    //    public Label getstatusLabel(){
//        return ChessGameFrame.statusLabel;
//    }


}
