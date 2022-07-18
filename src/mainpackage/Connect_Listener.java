package mainpackage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Connect_Listener extends MouseAdapter {

    private Graphics_Panel g_Panel;
    private LinkedList<Component> list;

    public Connect_Listener(Graphics_Panel g_Panel) {
        this.g_Panel = g_Panel;
        this.list = new LinkedList<Component>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Component component : g_Panel.accessComponents()) {
            if (component.getRect().contains(e.getX(), e.getY())) {
                list.add(component);
                break;
            }
        }
        if (list.size() == 2) {
            Component component_1 = list.get(0);
            Component component_2 = list.get(1);
            if ((component_1.isMachine() && component_2.isMachine()) ||
                    (!component_1.isMachine() && !component_2.isMachine())) {
                list.clear();
                return;
            }
            //from component_2 to component_1.
            if (component_1.getRect().getX() < component_2.getRect().getX()) {
                //component_1 is machine.
                //component_2 is queue.
                if (component_1.isMachine()) {
                    g_Panel.connect((Machine) component_1, (Queue) component_2, true);
                }
                //component_1 is queue.
                //component_2 is machine.
                else {
                    g_Panel.connect((Machine) component_2, (Queue) component_1, false);
                }
            }
            //from component_1 to component_2.
            else {
                //component_1 is machine.
                //component_2 is queue.
                if (component_1.isMachine()) {
                    g_Panel.connect((Machine) component_1, (Queue) component_2, false);
                }
                //component_1 is queue.
                //component_2 is machine.
                else {
                    g_Panel.connect((Machine) component_2, (Queue) component_1, true);
                }
            }
            list.clear();
        }

    }
}
