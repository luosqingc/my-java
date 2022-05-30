package view;


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

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    public static boolean Error = false;

    public ArrayList<String> chessboardrecord;

    public Chessboard(int width, int height) {
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
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initPawnOnBoard(1, 0, ChessColor.BLACK);
        initPawnOnBoard(1, 1, ChessColor.BLACK);
        initPawnOnBoard(1, 2, ChessColor.BLACK);
        initPawnOnBoard(1, 3, ChessColor.BLACK);
        initPawnOnBoard(1, 4, ChessColor.BLACK);
        initPawnOnBoard(1, 5, ChessColor.BLACK);
        initPawnOnBoard(1, 6, ChessColor.BLACK);
        initPawnOnBoard(1, 7, ChessColor.BLACK);
        initPawnOnBoard(6, 0, ChessColor.WHITE);
        initPawnOnBoard(6, 1, ChessColor.WHITE);
        initPawnOnBoard(6, 2, ChessColor.WHITE);
        initPawnOnBoard(6, 3, ChessColor.WHITE);
        initPawnOnBoard(6, 4, ChessColor.WHITE);
        initPawnOnBoard(6, 5, ChessColor.WHITE);
        initPawnOnBoard(6, 6, ChessColor.WHITE);
        initPawnOnBoard(6, 7, ChessColor.WHITE);
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
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
//        swapColor();
        ChessGameFrame.getStatusLabel().setText(currentColor.toString());
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            if (chess2 instanceof KingChessComponent) {
                if (!chess2.canMoveToCollect(this).contains(chess1.getChessboardPoint()) && chess1.canMoveToCollect(this).containsAll(chess2.canMoveToCollect(this))) {
                    JOptionPane.showMessageDialog(null, "You die!", "Game over!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
//        ChessGameFrame.getStatusLabel().setText(currentColor.toString());
//        swapColor();

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
        ChessGameFrame.getStatusLabel().setText(currentColor.toString());
        ChessGameFrame.counti++;
        ChessGameFrame.getCountLabel().setText("" + ChessGameFrame.counti);
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
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
        Error = false;
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
//        this.chessboard = chessboard;
        if (chessData.size() != 9) {
            JOptionPane.showMessageDialog(null, "缺少下一步行棋方", "Error!", JOptionPane.ERROR_MESSAGE);
            //chessData.clear();
            Error = true;
            return;
        }
        switch (chessData.get(8)) {
            case "w":
                this.currentColor = ChessColor.WHITE;
                break;
            case "b":
                this.currentColor = ChessColor.BLACK;
                break;
            default:
                JOptionPane.showMessageDialog(null, "缺少下一步行棋方,Unexpected value: " + chessData.get(8), "Error!", JOptionPane.ERROR_MESSAGE);
                //chessData.clear();
                Error = true;
                return;
        }
        StringBuilder Boss = new StringBuilder();
        for (String line : chessData) {
            Boss.append(line);
        }
        if (Boss.length() != 65) {
            JOptionPane.showMessageDialog(null, "棋盘并非 8*8,字符串长度:" + Boss.length(), "Error!", JOptionPane.ERROR_MESSAGE);
            Error = true;
            return;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (Boss.substring(i * 8 + j, i * 8 + j + 1)) {
                    case "R":
                        initRookOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case "K":
                        initKingOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case "N":
                        initKnightOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case "B":
                        initBishopOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case "Q":
                        initQueenOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case "P":
                        initPawnOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case "r":
                        initRookOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case "k":
                        initKingOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case "n":
                        initKnightOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case "b":
                        initBishopOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case "q":
                        initQueenOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case "p":
                        initPawnOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case "_":
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "棋子并非六种之一，棋子并非黑白棋子", "Error!", JOptionPane.ERROR_MESSAGE);
                        Error = true;
                        return;
                }
            }
        }
        ChessGameFrame.getStatusLabel().setText(currentColor.toString());
        ChessGameFrame.getStatusLabel().repaint();
    }

   public boolean peaceGameOver(){
        for (int i = 0; i <chessboardrecord.size() ; i++) {
            int count = 0;
           for (int j = 0; j < chessboardrecord.size(); j++) {
               if (chessboardrecord.get(i) == chessboardrecord.get(j)) {
                   count++;
               }
               if(count==3){return true;}
               else {return false;}


           }
        }return false;
    }
}
