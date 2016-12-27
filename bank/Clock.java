package bank;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

/**
 * Klasa odpowiadajaca za stworzenie i wyswietlenie zegara
 */
class Clock extends JLabel {

    private JLabel label;
    private JPanel panel;
    private TimeZone TimeZone;

    /**
     * Konstruktor klasy Clock odp
     *
     * @param panel panel na ktorym ma zostac wyswietlony zegar
     * @param TimeZone strefa czasowa
     */
    public Clock(JPanel panel, TimeZone TimeZone) {
        this.panel = panel;
        this.TimeZone = TimeZone;

        label = new JLabel();
        label.setFont(new Font("Consolas", Font.BOLD, 11));
        label.setBackground(Color.decode("0xCDCDB4"));

        panel.add(label, BorderLayout.EAST);
        ClockStart cs = new ClockStart();
        cs.start();

        label.setOpaque(true);

    }

    /**
     * Klasa odpiwadajaca za uruchomienie zegara wykorzystujaca SwingUtilities
     */
    private class ClockStart {

        public void start() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        Date clock = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        format.setTimeZone(TimeZone);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                label.setText(format.format(clock));
                            }
                        });
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                        }
                    }
                }

            }).start();
        }
    }

}
