package mainpackage;

import java.awt.*;
import java.util.ArrayList;

public class Machine implements Component, Runnable {

    private final int MACHINE_TIME, MACHINE_X, MACHINE_Y, MACHINE_DIAMETER, TEXT_X, TEXT_Y, ID, MIN_TIME, MAX_TIME;
    private final Color DEFAULT_COLOR = new Color(0, 200, 200);
    private final Graphics_Panel g_Panel;
    private Color color;
    private Rectangle rect;
    private ArrayList<Queue> previousQueues;
    private Queue nextQueue;
    private String machine_Text;
    private Product product;
    private boolean isAvailable;

    public Machine(Graphics_Panel g_Panel, int x, int y, int id, int min_Time, int max_Time) {
        this.g_Panel = g_Panel;
        ID = id;
        MIN_TIME = min_Time;
        MAX_TIME = max_Time;
        MACHINE_TIME = (int) (Math.random() * (MAX_TIME - MIN_TIME)) + MIN_TIME;
        MACHINE_X = x;
        MACHINE_Y = y;
        MACHINE_DIAMETER = 50;
        TEXT_X = x + 10;
        TEXT_Y = y + 30;
        color = DEFAULT_COLOR;
        rect = new Rectangle(MACHINE_X, MACHINE_Y, MACHINE_DIAMETER, MACHINE_DIAMETER);
        previousQueues = new ArrayList<Queue>();
        this.isAvailable = true;
    }

    public void addPrev_Queue(Queue queue) {
        previousQueues.add(queue);
    }

    public Queue getNextQueue() {
        return nextQueue;
    }

    public void setNextQueue(Queue nextQueue) {
        this.nextQueue = nextQueue;
    }

    public ArrayList<Queue> getPreviousQueues() {
        return previousQueues;
    }

    @Override
    public boolean isMachine() {
        return true;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void resetColor() {
        this.color = this.DEFAULT_COLOR;
    }

    @Override
    public Rectangle getRect() {
        return rect;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(MACHINE_X, MACHINE_Y, MACHINE_DIAMETER, MACHINE_DIAMETER);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        g.drawString(machine_Text, TEXT_X, TEXT_Y);
    }

    public void setMachine_Text(int machine_Ctr) {
        this.machine_Text = "M" + machine_Ctr;
    }

    public int getID() {
        return ID;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(MACHINE_TIME);
            resetColor();
            g_Panel.repaint();
            notifyQueues();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyQueues() throws InterruptedException {
        this.isAvailable = true;
        this.nextQueue.updateNext(product);
        for (Queue queue : previousQueues) {
            queue.updatePrevious();
        }
        Thread.currentThread().stop();
        return;
    }


    public void setElement(Product product) {
        this.product = product;
        setColor(product.getColor());
        g_Panel.repaint();
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setQueuesState(boolean state) {
        for (int i = 0; i < previousQueues.size(); i++)
            previousQueues.get(i).setReplayable(state);
        nextQueue.setReplayable(state);
    }
}
