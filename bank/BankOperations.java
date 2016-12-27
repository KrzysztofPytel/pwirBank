package bank;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 * Klasa odpowiadajaca za przechowywanie i operacje na kontach
 */
public class BankOperations {

    private RunAccount runAcc;
    private Account acc;
    private ArrayList<RunAccount> runingAccounts;
    private long lastAccountNumber = 0;
    private ArrayList<Long> accountNumberList;
    private ArrayList<Account> accountList;
    private ArrayList<ArrayList<Double>> accountBalanceList;

    /**
     * Konstruktor tworzy 2 listy przechowywujace liste obiektow klasy Account i
     * liste z numerami aktywnych kont
     *
     * @see Account
     */
    public BankOperations() {
        this.accountBalanceList = new ArrayList();
        runingAccounts = new ArrayList();
        accountNumberList = new ArrayList();
        accountList = new ArrayList();
    }

    /**
     * Metoda odpowiadajaca za stworzenie nowego konta
     *
     * @return numeer stworzonego konta
     */
    public final long newNumber() {
        lastAccountNumber++;
        final long NEW_NUMBER = lastAccountNumber;
        accountNumberList.add(NEW_NUMBER);
        return NEW_NUMBER;
    }

    /**
     * Metoda odpiwadajaca za stworzenie nowego konta
     *
     * @param newNumber numer konta
     * @param info obiekt klasy ShowInfo
     * @param bankOperations obiekt klasy BankOperations
     * @param listaKont obiekt klasy JComboBox
     * @param removeButton obiekt klasy JButton
     */
    public void newAccount(long newNumber, ShowInfo info, BankOperations bankOperations, JComboBox listaKont, JButton removeButton) {
        acc = new Account(newNumber, info);
        bankOperations.addAccount(acc);
        runAcc = new RunAccount(bankOperations, info, newNumber, acc);
        runingAccounts.add(runAcc);
        listaKont.addItem(String.format("Nr konta: %014d", newNumber));
        if (!removeButton.isEnabled()) {
            removeButton.setEnabled(true);
        }
    }

    /**
     * Metoda dodajaca obiekt klasy Acoount do listy
     *
     * @param acc obiekt klasy
     * @see Account
     */
    public void addAccount(Account acc) {
        accountList.add(acc);
    }

    /**
     * Metoda odpowiadajaca za zwrocenie z listy obiekt klasy Account
     *
     * @param index pozycja w liscie z jakiej ma zostac zwroony obiekt
     * @return obiekt klasy Account
     */
    public Account getAccount(int index) {
        return accountList.get(index);
    }

    /**
     * Metoda odpowiadajaca za skasowanie numeru
     *
     * @param ACCOUNT_NUMBER numer konta do skasowania
     * @param index indeks spod ktorego ma zostac numer
     * @param listaKont obiekt klasy JComboBox zawierajacy liste aktywnych kont
     * @param removeButton obiekt klasy JButton
     */
    public void deleteNumber(final long ACCOUNT_NUMBER, int index, JComboBox listaKont, JButton removeButton) {
        for (int i = 0; i < accountNumberList.size(); i++) {
            if (ACCOUNT_NUMBER == accountNumberList.get(i)) {
                runingAccounts.get(index).stop();
                runingAccounts.remove(index);
                accountNumberList.remove(i);
                accountList.remove(i);
                listaKont.removeItemAt(index);
                listaKont.setSelectedIndex(listaKont.getItemCount() - 1);
                if (getNumberOfAccounts() == 0) {
                    removeButton.setEnabled(false);
                } else {
                    removeButton.setEnabled(true);
                }
            }
        }
    }

    /**
     * Metoda odpowiadajaca za zwrocenie liczby aktywnych kont
     *
     * @return liczba kont
     */
    public int getNumberOfAccounts() {
        return accountNumberList.size();
    }

    /**
     * Metoda odpowiadajaa za zwrocenie numeru konta z listy
     *
     * @param index indeks z jakiego ma zostac zwrocony numer
     * @return numer konta
     */
    public long getNumber(int index) {
        return accountNumberList.get(index);
    }

    /**
     * Metoda odpowiadajaca za zwrocenie indeksu pod jakim znajduje sie na
     * liscie przyjety przez funkcje numer konta
     *
     * @param accountNumber numer konta
     * @return indeks z listy pod jakim znajduje sie numer
     */
    public int getNumberIndex(long accountNumber) {
        int index = 0;
        for (int i = 0; i < accountNumberList.size(); i++) {
            if (accountNumber == accountNumberList.get(i)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Metoda sprawdzajaca czy dany numer istnieje
     *
     * @param ACCOUNT_NUMBER numer konta do sprawdzenia
     * @return true jesli konto istnieje, false jesi numer nie istnieje, lub
     * zostal skasowany
     */
    public boolean accountExist(final long ACCOUNT_NUMBER) {
        for (int i = 0; i < accountNumberList.size(); i++) {
            if (ACCOUNT_NUMBER == accountNumberList.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda odpowiadajaca za dodanie kwoty do danego konta
     *
     * @param ammount kwota do transferu
     * @param accountNumber numer konta
     */
    public void doTransfer(double ammount, long accountNumber) {
        if (accountExist(accountNumber)) {
            accountList.get(getNumberIndex(accountNumber)).addFunds(ammount);
        }
    }

    /**
     * Metoda odpiwadajaca za stworzenie listy numerow kont
     *
     * @return lista numerow kont
     */
    public ArrayList<Long> getAccountNumberList() {
        return accountNumberList;
    }

    /**
     * Metoda odpowiadajaca za stworzenie listy z lista wplat na kazdym z kont
     *
     * @return lista z lista wplat na koncie
     */
    public ArrayList<ArrayList<Double>> getAccountsBalanceList() {
        for (int i = 0; i < accountNumberList.size(); i++) {
            accountBalanceList.add(accountList.get(i).balanceList());
        }
        return accountBalanceList;
    }

    /**
     * Metoda odpowiadajaca za wyczyszczenie historii srodkow
     */
    public void clearHistory() {
        accountBalanceList.clear();
    }
}
