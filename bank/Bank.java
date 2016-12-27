package bank;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author Krzysztof Pytel
 * @author grupa: KrDzIs3012Io
 * @author nr indeksu: 174753
 */
/**
 * Klasa odpowiadajaca za stworzenie i wyswietlenie konta
 */
public class Bank {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame window = new JFrame("Bank");
                window.setSize(800, 600);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
                window.setResizable(false);
                window.setLocationRelativeTo(null);

                Panels contentWindow = new Panels(window);
            }
        });
    }
}
