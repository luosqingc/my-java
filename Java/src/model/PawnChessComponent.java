package model;

import view.ChessGameFrame;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的车
 */
public class PawnChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image Pawn_WHITE;
    private static Image Pawn_BLACK;


    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image PawnImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (Pawn_WHITE == null) {
            Pawn_WHITE = ImageIO.read(new File("./images/Pawn-white.png"));
        }

        if (Pawn_BLACK == null) {
            Pawn_BLACK = ImageIO.read(new File("./images/Pawn-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定PawnImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                PawnImage = Pawn_WHITE;
                this.setName("p");
            } else if (color == ChessColor.BLACK) {
                PawnImage = Pawn_BLACK;
                this.setName("P");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
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
        int col = destination.getY();
        int a = source.getX();
        int b = source.getY();
        if (a==6||a==1) {
            if (chessComponents[source.getX()][source.getY()].chessColor == ChessColor.BLACK) {
                if (row == a + 2 && col == b) {
                    if (!(chessComponents[row - 1][col] instanceof EmptySlotComponent)) {
                    } else {
                        if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        } else {return true;}
                    }
                }
                if (row == a + 1 && col == b) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    } else {return true;}}
                if (row == a + 1 && col == b+1) {
                        if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                            if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
                                {return true;}
                        } else return false;
                    }
                if (row == a + 1 && col == b-1) {
                        if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                            if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
                                {return true;}
                        } else return false;
                    }

            } else {
            if (row == a - 2 && col == b) {
                if (!(chessComponents[row + 1][col] instanceof EmptySlotComponent)) {
                } else {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {

                    } else {return true;}
                }
            }
            if (row == a - 1 && col == b) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {

                } else {return true;}

            }
            if (row == a - 1 && col == b + 1) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
                        {return true;}
                } else return false;
            }
            if (row == a - 1 && col == b - 1) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
                        {return true;}
                } else return false;
            }
        }
        } else {
            if (chessComponents[source.getX()][source.getY()].chessColor == ChessColor.BLACK) {
                if (row == a + 1 && col == b) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
//                        if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
//                            {chessComponents[a][b].setStep(chessComponents[a][b].getStep()+1);return true;}
                    } else {return true;}

                }
                if (row == a + 1 && col == b+1) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
                            {return true;}
                    } else return false;
                }
                if (row == a + 1 && col == b-1) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
                            {return true;}
                    } else return false;
                }
            } else {
                if (row == a - 1 && col == b) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
//                        if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
//                            {chessComponents[a][b].setStep(chessComponents[a][b].getStep()+1);return true;}
                    } else {return true;}

                }
                if (row == a - 1 && col == b+1) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
                            {return true;}
                    } else return false;
                }
                if (row == a - 1 && col == b-1) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        if (chessComponents[row][col].chessColor != chessComponents[source.getX()][source.getY()].chessColor)
                            {return true;}
                    } else return false;
                }
            }
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
//        g.drawImage(PawnImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(PawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
