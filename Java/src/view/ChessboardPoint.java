package view;

import java.util.Comparator;

/**
 * 这个类表示棋盘上的位置，如(0, 0), (0, 7)等等
 * 其中，左上角是(0, 0)，左下角是(7, 0)，右上角是(0, 7)，右下角是(7, 7)
 */
public class ChessboardPoint {
    private int x, y;

    public ChessboardPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "("+x + ","+y+") " + "on the chessboard is clicked!";
    }

    class ChessboardPointComparator implements Comparator<ChessboardPoint> {
        @Override
        public int compare(ChessboardPoint o1, ChessboardPoint o2) {
            if (o1.getX() == o2.getX()) {
                return o1.getY() < o2.getY() ? -1 : o1.getY() == o2.getY() ? 0 : 1;
            } else {
                if (o1.getX() < o2.getX()) return -1;
                else return 1;
            }
        }
    }
}
