package bank;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;

/**
 * Klasa odpowiadajaca za dzialanie danego konta, wykorzystujaca klase Timer
 */
public class RunAccount {

    private Timer timer;
    private BankOperations bankOperations;
    private Account acc;
    private ShowInfo info;
    private final long ACCOUNT_NUMBER;

    /**
     * Konstruktor odpowiadajacy za stworzenie i uruchomienie obiektu klasy
     * Timer. Operacje wykonywane przez powyzsza klase sa wykonywane losowo co
     * (1000ms-5000ms)
     *
     * @param bankOperations obiket klasy BankOperations
     * @param info obiekt klasy ShowInfo
     * @param ACCOUNT_NUMBER numer konta ktore ma zostac powolane do zycia
     * @param acc obiekt klasy Account
     */
    public RunAccount(BankOperations bankOperations, ShowInfo info, final long ACCOUNT_NUMBER, Account acc) {
        this.acc = acc;
        this.info = info;
        this.bankOperations = bankOperations;
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAcc();
            }

        };
        timer = new Timer(ThreadLocalRandom.current().nextInt(1000, 5000 + 1), al);
        timer.start();
    }

    /**
     * Metoda odpowiadajaca za wykonywane operacje na koncie wartosc 1 powoduje
     * dodanie kwoty do konta wartosc 2 powoduje odjecie kwoty z konta kwota
     * przekazywana do metody odpowiadajacej za powyzsze operacje pryjmuje
     * wartosc (1-1000)
     */
    public void runAcc() {
        if (!bankOperations.accountExist(ACCOUNT_NUMBER)) {
            stop();
        }
        int operation = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        double ammount = ThreadLocalRandom.current().nextDouble(1.00, 1000.00);
        if (operation == 1) {
            acc.addFunds(ammount);
        } else if (operation == 2) {
            acc.withdraw(ammount);
        }
        info.refresh();
    }

    /**
     * Metoda odpowiadajaca za zatrzymanie pracy timera
     */
    public void stop() {
        timer.stop();
    }
}
