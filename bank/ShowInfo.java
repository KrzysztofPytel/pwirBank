package bank;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Klasa odpowiadajaca za wyswietlenie informacji zwrotnych
 */
public class ShowInfo {

    private JTextArea leftLogPanel;
    private JLabel rightLog;
    private long tempNumber;
    private Account tempAcc;
    private JPanel chartPanel;

    /**
     * Konstruktor przyjmuje parametry
     *
     * @param rightLog gorny prawy panel
     * @param leftPanel lewy panel
     * @param chartPanel gorny dolny panel
     */
    public ShowInfo(JLabel rightLog, JTextArea leftPanel, JPanel chartPanel) {
        this.chartPanel = chartPanel;
        this.rightLog = rightLog;
        this.leftLogPanel = leftPanel;
    }

    /**
     * Metoda odpowiadajaca za wyswietlenie komunikatu w przypadku dodania kwoty
     * do konta
     *
     * @param ammount kwota
     * @param ACCOUNT_NUMBER numer konta
     */
    public void logAddFunds(double ammount, final long ACCOUNT_NUMBER) {
        leftLogPanel.append("++" + String.format("%014d", ACCOUNT_NUMBER) + ": wplacil " + String.format("%.2f", ammount) + '\n');
    }

    /**
     * Metoda odpowiadajaca za wyswietlenie komunikatu w przypadku odjecia kwoty
     * z konta
     *
     * @param ammount kwota
     * @param ACCOUNT_NUMBER numer konta
     */
    public void logWithdraw(double ammount, final long ACCOUNT_NUMBER) {
        leftLogPanel.append("++" + String.format("%014d", ACCOUNT_NUMBER) + ": wyplacil " + String.format("%.2f", ammount) + '\n');
    }

    /**
     * Metoda odpowiadajaca za wyswietlenie komunikatu w przypadku gdy odjecie
     * kwoty wywloaloby debet na koncie
     *
     * @param ACCOUNT_NUMBER numer konta
     */
    public void logError(final long ACCOUNT_NUMBER) {
        leftLogPanel.append("[ERROR]" + String.format("%014d", ACCOUNT_NUMBER) + ": BRAK SRODKOW!!!" + '\n');
    }

    /**
     * Metoda odpowiadajaca za wyswietlenie informacji o wybranym konie
     *
     * @param ACCOUNT_NUMBER numer konts
     * @param acc oniekt klasy Account
     * @see Account
     */
    public void accountInfo(final long ACCOUNT_NUMBER, Account acc) {
        tempAcc = acc;
        tempNumber = ACCOUNT_NUMBER;
        if (tempAcc == null) {
            rightLog.setText("<html><br>Nr konta: xxxxxxxxxxxxxx <br>Stan konta: 0.00</html>");
            ArrayList<Double> empty = new ArrayList();
            empty.add(0.00);
            new LineChart(empty, chartPanel);
        } else {
            rightLog.setText("<html><br>Nr konta: " + String.format("%014d", tempNumber)
                    + "<br>Stan konta: " + String.format("%.2f", tempAcc.checkBalance())
                    + "<br>Liczba operacji: " + tempAcc.getOperationsCount() + "</html>");
            new LineChart(tempAcc.balanceList(), chartPanel);
        }
    }

    /**
     * Metoda odpowiadajca za odswiezenie informacji o koncie
     */
    public void refresh() {
        if (tempAcc == null) {
            rightLog.setText("<html><br>Nr konta: xxxxxxxxxxxxxx <br>Stan konta: 0.00</html>");
            new LineChart(tempAcc.balanceList(), chartPanel);
        } else {
            rightLog.setText("<html><br>Nr konta: " + String.format("%014d", tempNumber)
                    + "<br>Stan konta: " + String.format("%.2f", tempAcc.checkBalance())
                    + "<br>Liczba operacji: " + tempAcc.getOperationsCount() + "</html>");
            new LineChart(tempAcc.balanceList(), chartPanel);
        }
    }
}
