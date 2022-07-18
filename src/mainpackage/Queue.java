package mainpackage;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Queue implements Component, Observer, Runnable {

    private final int QUEUE_X, QUEUE_Y, QUEUE_WIDTH, QUEUE_HEIGHT, TEXT_X, TEXT_Y, ID;
    private final Color DEFAULT_COLOR = new Color(200, 196, 30);
    private int currentChoice;
    private Color color;
    private Rectangle rect;

    private ArrayList<Machine> nextMachines;

    private LinkedList<Product> queueElements;

    private ArrayList<Integer> randomChoices;

    private boolean isReplayable;

    private String queue_Text;
    private Thread queueThread;
    private Random rand = new Random();

    public Queue(Graphics_Panel g_Panel, int x, int y, int id) {

        randomChoices = new ArrayList<Integer>();
        isReplayable = false;
        currentChoice = 0;

        ID = id;
        QUEUE_X = x;
        QUEUE_Y = y;
        QUEUE_WIDTH = 90;
        QUEUE_HEIGHT = 50;
        TEXT_X = QUEUE_X + 10;
        TEXT_Y = QUEUE_Y + 30;
        color = this.DEFAULT_COLOR;
        rect = new Rectangle(QUEUE_X, QUEUE_Y, QUEUE_WIDTH, QUEUE_HEIGHT);
        nextMachines = new ArrayList<Machine>();
        queueElements = new LinkedList<Product>();
        queue_Text = "Size: " + queueElements.size();
    }

    public void updateQueue_Text() {
        queue_Text = "Size: " + queueElements.size();
    }

    public void setQueueElements(LinkedList<Product> products) {
        queueElements = products;
    }

    public void addNext_Machine(Machine machine) {
        nextMachines.add(machine);
    }

    @Override
    public boolean isMachine() {
        return false;
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
        g.fillRect(QUEUE_X, QUEUE_Y, QUEUE_WIDTH, QUEUE_HEIGHT);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        g.drawString(queue_Text, TEXT_X, TEXT_Y);
    }

    public void resetRandomChoices(){
        this.randomChoices = new ArrayList<>();
    }
    public int getID() {
        return ID;
    }

    public void resetCurrentChoice() {
        this.currentChoice = 0;
    }

    public void setReplayable(boolean replayable) {
        isReplayable = replayable;
    }

    @Override
    public synchronized void run() {

        if (nextMachines.isEmpty()) {
            Thread.currentThread().stop();
            return;
        }
        if (this.queueThread == null) queueThread = Thread.currentThread();

        for (;;) {
            ArrayList<Machine> availableMachines = new ArrayList<Machine>();
            for (Machine machine : nextMachines) {
                if (machine.getIsAvailable()) {
                    availableMachines.add(machine);
                }
            }

            if (availableMachines.isEmpty() || queueElements.size() == 0) {
                queueThread.stop();
                return;
            } else {
                Machine randomMachine;
                synchronized (randomChoices) {
                    if (isReplayable) {
                        randomMachine = nextMachines.get(randomChoices.get(currentChoice++));
                        if (currentChoice == randomChoices.size())
                            resetCurrentChoice();
                    } else {
                        int randomIndex = rand.nextInt(availableMachines.size());
                        randomMachine = availableMachines.get(randomIndex);
                        randomChoices.add(nextMachines.indexOf(randomMachine));
                    }
                }
                randomMachine.setIsAvailable(false);
                synchronized (this.queueElements) {
                    randomMachine.setElement(queueElements.remove(0));
                }
                updateQueue_Text();
                Thread machineThread = new Thread(randomMachine);
                machineThread.start();
            }
        }
    }

    @Override
    public synchronized void updatePrevious() {
        if (queueThread == null || !queueThread.isAlive()) {
            queueThread = new Thread(this);
            queueThread.start();
        }
    }

    @Override
    public synchronized void updateNext(Product product) {
        this.queueElements.add(product);
        updateQueue_Text();
        if (!this.nextMachines.isEmpty() && (queueThread == null || !queueThread.isAlive())) {
            queueThread = new Thread(this);
            queueThread.start();
        }
    }

    public ArrayList<Machine> getNextMachines() {
        return nextMachines;
    }
}
