package bank;

import java.util.ArrayList;

/**
 * Klasa odpowiadajaca za operacje na danym koncie
 */
public class Account {

    private final long ACCOUNT_NUMBER;
    private double balance;
    private ShowInfo info;
    private ArrayList<Double> balanceList;
    private int operationCount;

    /**
     * Konsruktor przyjmuje nastepujace parametry
     *
     * @param ACCOUNT_NUMBER nr konta
     * @param info Obiekt klasy ShowInfo
     */
    public Account(final long ACCOUNT_NUMBER, ShowInfo info) {
        operationCount = 0;
        balanceList = new ArrayList();
        this.info = info;
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
        balance = 0;
        balanceList.add(balance);
    }

    /**
     * Metoda odpowiadajaca za dodanie srodkow do konta
     *
     * @param ammount kwota wplacana
     */
    public synchronized void addFunds(double ammount) {
        balance += ammount;
        info.logAddFunds(ammount, ACCOUNT_NUMBER);
        balanceList.add(balance);
        operationCount++;
    }

    /**
     * Metoda odpowiadajaca za wyplate srodkow z konta, przy czym kwota podana w
     * parametrze nie moze spowodowac debetu na konie
     *
     * @param ammount kwota wyplacana
     */
    public void withdraw(double ammount) {
        if (ammount > balance) {
            info.logError(ACCOUNT_NUMBER);
        } else {
            balance -= ammount;
            balanceList.add(balance);
            info.logWithdraw(ammount, ACCOUNT_NUMBER);
            operationCount++;
        }
    }

    /**
     * Metoda zwracajaca historie srodkow na koncie
     *
     * @return lista z historia srodkow (double)
     */
    public ArrayList<Double> balanceList() {
        return balanceList;
    }

    /**
     * Metoda zwracajaca liczbe wykonanych operacji zakonconych sukcesem
     *
     * @return liczba wykonanych operacji
     */
    public int getOperationsCount() {
        return operationCount;
    }

    /**
     * Metoda zwracajac ilosc dostepnych srodkow na koncie
     *
     * @return dostepne srodki
     */
    public double checkBalance() {
        return balance;
    }
}
