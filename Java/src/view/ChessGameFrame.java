package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private static JLabel statusLabel;
    private static JLabel countLabel;
    public static int counti = 0;

    public static JLabel getCountLabel() {
        return countLabel;
    }

    public static JLabel getStatusLabel() {
        return statusLabel;
    }

    public ChessGameFrame(int width, int height) {
        setTitle("2022 JAVA Project"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addLabel();
        addChessboard();
        addcountLabel();
        addHelloButton();
        addLoadButton();
        addReloadButton();
        addSaveButton();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }


    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("WHITE");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        ChessGameFrame.statusLabel = statusLabel;
        add(statusLabel);
    }

    private void addcountLabel() {
        JLabel countLabel = new JLabel("0");
        countLabel.setLocation(HEIGTH, HEIGTH / 5);
        countLabel.setSize(200, 60);
        countLabel.setFont(new Font("Rockwell", Font.BOLD, 10));
        ChessGameFrame.countLabel = countLabel;
        add(countLabel);
    }


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);


    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile(path);
//            this.setVisible(false);
//            addChessboard();
//            statusLabel.repaint();
            if (Chessboard.Error == false) {
                counti = 0;
                countLabel.setText(""+counti);
                this.repaint();
            } else {
                gameController.getChessboard().setVisible(false);
                counti = 0;
                countLabel.setText(""+counti);
                statusLabel.setText("WHITE");
                addChessboard();
                this.repaint();
            }
//            this.setVisible(true);
//            repaint();
        });
    }

    private void addReloadButton() {
        JButton button = new JButton("Reload");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click Reload");
            gameController.getChessboard().setVisible(false);
            counti = 0;
            countLabel.setText(""+counti);
            statusLabel.setText("WHITE");
            addChessboard();
        });
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click Save");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
            JOptionPane.showMessageDialog(null, "clicked Save Btn", " ", JOptionPane.PLAIN_MESSAGE);
            String filePath = JOptionPane.showInputDialog(this, "input the name here");
            gameController.writeDataToFile(filePath);
            JOptionPane.showMessageDialog(null, "Save successfully!", "Remind", JOptionPane.INFORMATION_MESSAGE);
        });

    }
}
