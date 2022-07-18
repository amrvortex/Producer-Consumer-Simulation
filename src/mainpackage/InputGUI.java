package mainpackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputGUI extends JFrame {

    JTextField min_InputRate;
    JTextField max_InputRate;
    JTextField min_Time;
    JTextField max_Time;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JLabel label5;
    JButton confirm;
    JPanel panel;
    private int cols = 30;
    private int width = 50;
    private int height = 50;
    private boolean isValid = true;

    public InputGUI() {
        super("Producer Consumer Simulator");
        min_InputRate = new JTextField(cols);
        max_InputRate = new JTextField(cols);
        min_Time = new JTextField(cols);
        max_Time = new JTextField(cols);
        label1 = new JLabel("Minimum Input Rate:");
        label2 = new JLabel("Maximum Input Rate:");
        label3 = new JLabel("Minimum Time:");
        label4 = new JLabel("Maximum Time:");
        label5 = new JLabel("Please not that time is in milliseconds   ");
        confirm = new JButton("confirm");
        panel = new JPanel();
        label1.setSize(width, height);
        min_InputRate.setSize(width, height);
        panel.add(label1);
        panel.add(min_InputRate);

        label2.setSize(width, height);
        max_InputRate.setSize(width, height);
        panel.add(label2);
        panel.add(max_InputRate);

        label3.setSize(width, height);
        min_Time.setSize(width, height);
        panel.add(label3);
        panel.add(min_Time);

        label4.setSize(width, height);
        max_Time.setSize(width, height);
        panel.add(label4);
        panel.add(max_Time);

        label5.setSize(width, height);
        panel.add(label5);

        confirm.setSize(width, height);
        panel.add(confirm);
        navigate(this);
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 280);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void navigate(InputGUI currentGUI) {
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int minRate = 0, maxRate = 0, minTime = 0, maxTime = 0;
                if (!isNumber(min_InputRate.getText()) || !isNumber(max_InputRate.getText())
                        || !isNumber(min_Time.getText()) || !isNumber(max_Time.getText()))
                    return;
                else {
                    minRate = Integer.parseInt(min_InputRate.getText());
                    maxRate = Integer.parseInt(max_InputRate.getText());
                    minTime = Integer.parseInt(min_Time.getText());
                    maxTime = Integer.parseInt(max_Time.getText());
                    if (minRate <= 0 || maxRate < minRate || minTime < 1 || maxTime < minTime)
                        return;
                    currentGUI.setVisible(false);
                    new GUI(minRate, maxRate, minTime, maxTime);
                }
            }
        });
    }

    private boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
