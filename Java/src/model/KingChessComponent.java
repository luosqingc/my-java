package model;

import controller.GameController;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示国际象棋里面的车
 */
public class KingChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image King_WHITE;
    private static Image King_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image KingImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (King_WHITE == null) {
            King_WHITE = ImageIO.read(new File("./images/King-white.png"));
        }

        if (King_BLACK == null) {
            King_BLACK = ImageIO.read(new File("./images/King-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定KingImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                KingImage = King_WHITE;
                this.setName("k");
            } else if (color == ChessColor.BLACK) {
                KingImage = King_BLACK;
                this.setName("K");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKingImage(color);
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
        int row = destination.getX();
        int col=destination.getY();
        int a=source.getX();
        int b=source.getY();
        if ((a-1 == row&&b+1==col)||(a-1 == row&&b==col)||(a-1 == row&&b-1==col)||(a == row&&b+1==col)||(a== row&&b-1==col)||(a+1 == row&&b+1==col)||(a+1 == row&&b==col)||(a+1 == row&&b-1==col)) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    if(chessComponents[row][col].chessColor!=chessComponents[source.getX()][source.getY()].chessColor)
                    return true;
                }else return true;

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
//        g.drawImage(KingImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(KingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }


}

