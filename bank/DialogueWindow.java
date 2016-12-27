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
 * Klasa odpowiadajaca za stworzenie i wyswietlenie okna dialogowego
 */
public class DialogueWindow {

    private JFrame dw;
    private JButton yes, no;
    private JPanel contentPanel;
    private JPanel buttonPanel, msgPanel;
    private JLabel msg;
    private XlsLog xls;

    /**
     * Konstruktor odpowiadajacy za stworzenie i wyswietlenie okna dialogowego
     * oraz przypisanie funkcji przyciskom
     *
     * @param xls obiekt klasy XlsLog
     * @see XlsLog
     */
    public DialogueWindow(XlsLog xls) {
        this.xls = xls;
        dw = new JFrame("Very Important Question");
        dw.setSize(250, 150);
        dw.setLocationRelativeTo(null);
        dw.setVisible(true);
        contentPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel = new JPanel();
        msgPanel = new JPanel();

        yes = new JButton("Tak");
        no = new JButton("Nie");

        msg = new JLabel("Otworzyc plik?");

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xls.open();
                dw.dispose();
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dw.dispose();
            }

        });

        dw.add(contentPanel);

        msgPanel.add(msg);

        buttonPanel.add(yes);
        buttonPanel.add(no);

        contentPanel.add(msgPanel);
        contentPanel.add(buttonPanel);
    }

}
