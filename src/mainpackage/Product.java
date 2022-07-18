package mainpackage;

import java.awt.*;

public class Product {

    private Color color;

    public Product() {
        int red = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        this.color = new Color(red, blue, green);
    }

    public Color getColor() {
        return color;
    }
}
