package mainpackage;

import java.awt.*;

public class Edge {

    private final int X1, Y1, X2, Y2;

    public Edge(Point point_1, Point point_2) {
        this.X1 = (int) point_1.getX();
        this.Y1 = (int) point_1.getY();
        this.X2 = (int) point_2.getX();
        this.Y2 = (int) point_2.getY();
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(X1, Y1, X2, Y2);
    }
}
