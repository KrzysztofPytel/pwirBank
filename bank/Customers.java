package bank;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Timer;

/**
 * Klasa odpowiadajaca za Klientow wykonujacych przelewy
 */
public class Customers {

    private Timer timer;
    private BankOperations bankOperations;
    private ShowInfo info;
    private int delay;
    private double ammount;
    private int numberIndex;
    private long accountNumber;
    private int chance;

    /**
     * Konstruktor odpowiadajacy za stworzenie i uruchomienie obiektu klasy
     * Timer. Operacje dodania srodkow wykonywane sa losowo co (2000ms-10000ms)
     *
     * @param bankOperations obiekt klasy BankOperations
     * @param info obiekt klasy ShowInfo
     * @param listaKont obiekt klasy JComboBox zawierajacy liste aktywnych kont
     * @param removeButton obiekt klasy JButton usuwajacy wybrane konto
     */
    public Customers(BankOperations bankOperations, ShowInfo info, JComboBox listaKont, JButton removeButton) {
        delay = ThreadLocalRandom.current().nextInt(2000, 10000);
        this.info = info;
        this.bankOperations = bankOperations;
        if (bankOperations.getNumberOfAccounts() == 0) {
            long newNumber = bankOperations.newNumber();
            bankOperations.newAccount(newNumber, info, bankOperations, listaKont, removeButton);
        }
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delay = ThreadLocalRandom.current().nextInt(2000, 10000);
                ammount = ThreadLocalRandom.current().nextDouble(1.00, 1000.00);
                chance = ThreadLocalRandom.current().nextInt(1, 100);
                if (bankOperations.getNumberOfAccounts() > 0) {
                    if (chance < 95) {
                        numberIndex = ThreadLocalRandom.current().nextInt(0, bankOperations.getNumberOfAccounts());
                        accountNumber = bankOperations.getNumber(numberIndex);
                        bankOperations.doTransfer(ammount, accountNumber);
                        info.refresh();
                    } else {
                        long newNumber = bankOperations.newNumber();
                        bankOperations.newAccount(newNumber, info, bankOperations, listaKont, removeButton);
                    }
                }

            }
        };
        timer = new Timer(delay, al);
        timer.start();
    }

    /**
     * Metoda odpiwadajaca za usuniecie danego klienta - zatrzymanie Timera
     */
    public void deleteCustomer() {
        timer.stop();
    }

}
