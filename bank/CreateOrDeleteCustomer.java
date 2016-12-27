package bank;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Timer;

/**
 * Klasa odpiwadajaca za stworzenie i usuwanie klientow
 */
public class CreateOrDeleteCustomer {

    private ArrayList<Customers> runningCustomers;
    private BankOperations bankOperations;
    private ShowInfo info;
    private Customers tempCustomer;
    private Timer timer;
    private int delay;
    private int chance;
    private int customers;

    /**
     * Konstruktor odpowiadajaca za tworzenie lub usuwanie klientow
     * wykorzystujacy klase Timer. Szansa na stworzenie klienta - 60%, usuniecie
     * - 40%.
     *
     * @param bankOperations obiekt klasy BankOperations
     * @param info obiekt klasy ShowInfo
     * @param listaKont obiekt klasy JComboBox zawierajacy numery akrytwnych
     * kont
     * @param removeButton obiekt klasy JButton usuwajacy konta
     */
    public CreateOrDeleteCustomer(BankOperations bankOperations, ShowInfo info, JComboBox listaKont, JButton removeButton) {
        this.bankOperations = bankOperations;
        this.info = info;
        runningCustomers = new ArrayList();
        customers = 0;

        delay = ThreadLocalRandom.current().nextInt(1000, 5000);
        if (customers == 0) {
            tempCustomer = new Customers(bankOperations, info, listaKont, removeButton);
            runningCustomers.add(tempCustomer);
            customers++;
        }
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chance = ThreadLocalRandom.current().nextInt(1, 100);

                if (chance > 60) {
                    if (runningCustomers.size() != 0) {
                        runningCustomers.get(0).deleteCustomer();
                        runningCustomers.remove(0);
                    }
                } else {
                    tempCustomer = new Customers(bankOperations, info, listaKont, removeButton);
                    runningCustomers.add(tempCustomer);
                    customers++;
                }
            }
        };
        timer = new Timer(delay, al);
        timer.start();

    }

}
