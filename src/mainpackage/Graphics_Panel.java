package mainpackage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Graphics_Panel extends JPanel {

    private final int MIN_TIME, MAX_TIME;
    private int machine_Ctr;
    private boolean isClearable;
    private ArrayList<Component> components;
    private ArrayList<Edge> edges;
    private ArrayList<Queue> lastQueues;
    private LinkedList<Product> products;
    private Queue FirstQueue;

    public Graphics_Panel(int min_Time, int max_Time) {
        MIN_TIME = min_Time;
        MAX_TIME = max_Time;
        this.components = new ArrayList<Component>();
        this.edges = new ArrayList<Edge>();
        lastQueues = new ArrayList<Queue>();
        FirstQueue = new Queue(this, 0, 0, 0);
        machine_Ctr = 0;
        isClearable = false;
    }

    public void initializeRandomChoices(){
        for (Component component : this.components){
            if(!component.isMachine()){
                Queue queue = (Queue)component;
                queue.resetRandomChoices();
            }
        }
    }

    public void addMachine(int x, int y) {
        Machine machine = new Machine(this, x, y, components.size(), MIN_TIME, MAX_TIME);
        machine.setMachine_Text(machine_Ctr++);
        components.add(machine);
        this.repaint();
    }

    public void addQueue(int x, int y) {
        Queue queue = new Queue(this, x, y, components.size());
        components.add(queue);
        this.repaint();
    }

    // case 1 from queue to machine.
    // case 2 from machine to queue.
    public void connect(Machine machine, Queue queue, boolean isFromQueue) {
        if (isFromQueue) {
            queue.addNext_Machine(machine);
            machine.addPrev_Queue(queue);
            Point point_1 = new Point((int) queue.getRect().getX(),
                    (int) (queue.getRect().getY() + queue.getRect().getHeight() / 2));
            Point point_2 = new Point((int) (machine.getRect().getX() + machine.getRect().getWidth()),
                    (int) (machine.getRect().getY() + machine.getRect().getHeight() / 2));
            Edge edge = new Edge(point_1, point_2);
            edges.add(edge);
        } else {
            if (machine.getNextQueue() != null) {
                return;
            }
            machine.setNextQueue(queue);
            Point point_1 = new Point((int) machine.getRect().getX(),
                    (int) (machine.getRect().getY() + machine.getRect().getHeight() / 2));
            Point point_2 = new Point((int) (queue.getRect().getX() + queue.getRect().getWidth()),
                    (int) (queue.getRect().getY() + queue.getRect().getHeight() / 2));
            Edge edge = new Edge(point_1, point_2);
            edges.add(edge);
        }
        components.set(machine.getID(), machine);
        components.set(queue.getID(), queue);
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Component component : components) {
            component.draw(g);
        }
        for (Edge edge : edges) {
            edge.draw(g);
        }
        if (isClearable) {
            g.clearRect(0, 0, this.getWidth(), this.getHeight());
            isClearable = false;
        }
    }

    public ArrayList<Component> accessComponents() {
        return components;
    }

    public void clearComponents() {
        isClearable = true;
        components.clear();
        edges.clear();
        machine_Ctr = 0;
    }

    public void clearLastQueues() {
        for (Queue lastQueue : lastQueues)
            lastQueue.setQueueElements(new LinkedList<Product>());
    }

    public void determineEndPoints() {
        int max_X = 0;
        //int min_X = this.getWidth();
        for (Component component : components) {
            if (!component.isMachine()) {
                if ((int) component.getRect().getX() > max_X) {
                    FirstQueue = (Queue) component;
                    max_X = (int) component.getRect().getX();
                }
                if (((Queue) component).getNextMachines().isEmpty())
                    lastQueues.add((Queue) component);
                //min_X = (int) component.getRect().getX();
            }
        }
    }

    public Queue getFirstQueue() {
        return FirstQueue;
    }

    public void setQueuesState(boolean state) {
        for (int i = 0; i < components.size(); i++) {
            if (!components.get(i).isMachine())
                ((Queue) components.get(i)).setReplayable(state);
        }
    }

    public void generateRandomProducts(int size) {
        products = new LinkedList<Product>();
        for (int i = 0; i < size; i++)
            products.add(new Product());
    }

    public LinkedList<Product> getProducts() {
        return (LinkedList<Product>) products.clone();
    }

    public boolean isReady() {
        boolean bool = true;
        for (Component component : components) {
            if (component.isMachine()) {
                bool = bool && ((Machine) component).getNextQueue() != null
                        && ((Machine) component).getPreviousQueues().size() != 0;
            }
        }
        return bool;
    }
}
