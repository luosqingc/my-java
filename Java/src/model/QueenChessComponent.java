package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的车
 */
public class QueenChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image Queen_WHITE;
    private static Image Queen_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image QueenImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (Queen_WHITE == null) {
            Queen_WHITE = ImageIO.read(new File("./images/Queen-white.png"));
        }

        if (Queen_BLACK == null) {
            Queen_BLACK = ImageIO.read(new File("./images/Queen-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定QueenImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                QueenImage = Queen_WHITE;
                this.setName("q");
            } else if (color == ChessColor.BLACK) {
                QueenImage = Queen_BLACK;
                this.setName("Q");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
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
        Boolean K1=true;Boolean K0=true;
        if (destination.getX()-source.getX()==destination.getY()-source.getY()) {
            int row1 = Math.min(source.getX(), destination.getX());
            for (int col1 = Math.min(source.getY(), destination.getY()) + 1;
                 col1 < Math.max(source.getY(), destination.getY()); col1++) {
                if (!(chessComponents[row1+col1-(Math.min(source.getY(), destination.getY()))][col1] instanceof EmptySlotComponent)) {
                    K1= false;
                }
            }
        } else if (destination.getX()-source.getX()==-destination.getY()+source.getY()) {
            int col1 = Math.min(source.getY(), destination.getY());
            for (int row1 = Math.min(source.getX(), destination.getX()) + 1;
                 row1 < Math.max(source.getX(), destination.getX()); row1++) {
                if (!(chessComponents[row1][col1+row1-(Math.min(source.getX(), destination.getX()))] instanceof EmptySlotComponent)) {
                    K1= false;
                }
            }
        } else { // Not on the same row or the same column.
            K1= false;
        }

        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    K0= false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    K0= false;
                }
            }
        } else { // Not on the same row or the same column.
            K0= false;
        }
        System.out.println(K1.toString());
        System.out.println(K0.toString());
       if(K1||K0){
           return true;
       }else return false;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(QueenImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(QueenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
