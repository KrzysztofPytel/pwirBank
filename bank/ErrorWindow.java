package bank;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Klasa odpowiadajaca za stworzenie i wyswietlenie okna z komunikatem o bledzie
 */
public class ErrorWindow {

    private String msg;
    private JFrame window;
    private JLabel label;
    private JPanel contentPanel, buttonPanel, msgPanel;
    private JButton ok;

    /**
     * Konstruktor odpiwadajacy za stworzenie i wyswietlenie informacji o
     * bledzie
     *
     * @param msg String z wiadomoscia o bledzie
     */
    public ErrorWindow(String msg) {
        this.msg = msg;

        window = new JFrame("Houston we have a problem");
        window.setSize(250, 150);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        contentPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttonPanel = new JPanel();
        msgPanel = new JPanel();

        label = new JLabel(msg);

        ok = new JButton("OK");

        window.add(contentPanel);

        contentPanel.add(msgPanel);
        contentPanel.add(buttonPanel);

        msgPanel.add(label);
        buttonPanel.add(ok);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }

        });

    }
}
