package mainpackage;

import java.awt.*;

public interface Component {
    public boolean isMachine();

    public void setColor(Color color);

    public void resetColor();

    public Rectangle getRect();

    public void draw(Graphics g);
}
