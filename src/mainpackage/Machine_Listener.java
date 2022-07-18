package mainpackage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Machine_Listener extends MouseAdapter {

    private Graphics_Panel g_Panel;

    public Machine_Listener(Graphics_Panel g_Panel) {
        this.g_Panel = g_Panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        g_Panel.addMachine(e.getX(), e.getY());
    }
}
