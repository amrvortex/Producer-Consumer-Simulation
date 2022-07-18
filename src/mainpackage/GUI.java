package mainpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class GUI extends JFrame {

    final int BUTTON_WIDTH = 110;
    final int BUTTON_HEIGHT = 30;
    private final int MIN_RATE, MAX_RATE;
    Graphics_Panel g_Panel;
    JPanel options_Panel;
    JPanel input_Panel;
    JButton newSimulation;
    JButton runSimulation;
    JButton replaySimulation;
    JButton addMachine;
    JButton addQueue;
    JButton connect;
    Machine_Listener ml;
    Queue_Listener ql;
    Connect_Listener cl;
    private Random rand = new Random();
    private int productsSize;
    private boolean isRun = false;

    public GUI(int min_Rate, int max_Rate, int min_Time, int max_Time) {
        super("Producer Consumer Simulator");
        MIN_RATE = min_Rate;
        MAX_RATE = max_Rate;
        g_Panel = new Graphics_Panel(min_Time, max_Time);
        options_Panel = new JPanel();
        addOptionsPanel();
        addGraphicsPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void addGraphicsPanel() {
        ml = new Machine_Listener(g_Panel);
        ql = new Queue_Listener(g_Panel);
        cl = new Connect_Listener(g_Panel);
        this.getContentPane().add(g_Panel, BorderLayout.CENTER);
    }

    private void addOptionsPanel() {
        options_Panel.setBackground(Color.LIGHT_GRAY);
        addOptions();
        this.getContentPane().add(options_Panel, BorderLayout.PAGE_END);
    }

    private void addOptions() {
        newSimulation = new JButton("New");
        runSimulation = new JButton("Run");
        replaySimulation = new JButton("Replay");
        addMachine = new JButton("Add Machine");
        addQueue = new JButton("Add Queue");
        connect = new JButton("Connect");
        newSimulation.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        runSimulation.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        replaySimulation.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        addMachine.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        addQueue.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        connect.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        addMachine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseListener[] ms = g_Panel.getMouseListeners();
                for (MouseListener m : ms) {
                    g_Panel.removeMouseListener(m);
                }
                g_Panel.addMouseListener(ml);
            }
        });
        addQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseListener[] ms = g_Panel.getMouseListeners();
                for (MouseListener m : ms) {
                    g_Panel.removeMouseListener(m);
                }
                g_Panel.addMouseListener(ql);
            }
        });
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseListener[] ms = g_Panel.getMouseListeners();
                for (MouseListener m : ms) {
                    g_Panel.removeMouseListener(m);
                }
                g_Panel.addMouseListener(cl);
            }
        });
        runSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseListener[] ms = g_Panel.getMouseListeners();
                for (MouseListener m : ms) {
                    g_Panel.removeMouseListener(m);
                }
                if (!g_Panel.isReady() || g_Panel.accessComponents().size() == 0)
                    return;
                isRun = true;
                g_Panel.determineEndPoints();
                g_Panel.clearLastQueues();
                productsSize = (int) (Math.random() * (MAX_RATE - MIN_RATE)) + MIN_RATE;
                g_Panel.generateRandomProducts(productsSize);
                g_Panel.getFirstQueue().setQueueElements(g_Panel.getProducts());
                g_Panel.setQueuesState(false);
                g_Panel.initializeRandomChoices();
                Thread mainThread = new Thread(g_Panel.getFirstQueue());
                mainThread.start();
            }
        });
        newSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseListener[] ms = g_Panel.getMouseListeners();
                for (MouseListener m : ms) {
                    g_Panel.removeMouseListener(m);
                }
                g_Panel.clearComponents();
                g_Panel.repaint();
            }
        });
        replaySimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseListener[] ms = g_Panel.getMouseListeners();
                for (MouseListener m : ms) {
                    g_Panel.removeMouseListener(m);
                }
                if (!g_Panel.isReady() || g_Panel.accessComponents().size() == 0 || !isRun)
                    return;
                g_Panel.determineEndPoints();
                g_Panel.clearLastQueues();
                g_Panel.getFirstQueue().setQueueElements(g_Panel.getProducts());
                g_Panel.setQueuesState(true);
                Thread mainThread = new Thread(g_Panel.getFirstQueue());
                mainThread.start();
            }
        });
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(newSimulation);
        menuBar.add(runSimulation);
        menuBar.add(replaySimulation);
        menuBar.add(addMachine);
        menuBar.add(addQueue);
        menuBar.add(connect);
        options_Panel.add(menuBar);
    }

}
