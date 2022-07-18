package mainpackage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Queue_Listener extends MouseAdapter {
    private Graphics_Panel g_Panel;

    public Queue_Listener(Graphics_Panel g_Panel) {
        this.g_Panel = g_Panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        g_Panel.addQueue(e.getX(), e.getY());
    }
}
