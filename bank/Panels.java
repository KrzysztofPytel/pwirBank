package bank;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimeZone;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Klasa odpowiadajaca za stworzenie wygladu okna aplikacji
 */
public class Panels {

    private ShowInfo info;
    private BankOperations bankOperations;
    private XlsLog xls;
    private ErrorWindow errorWindow;

    private int index;

    private JFrame window;

    private JPanel mainPanel;
    private JPanel listPanel, logPanel, buttonPanel;
    private JPanel rightLogPanel, leftLogPanel, chartPanel;

    private JButton addButton, removeButton, createLogButton, openLog;

    private JTextArea leftLog;
    private JLabel rightLog;

    private JComboBox listaKont;

    /**
     * Konstruktor odpowiadajacy za dodanie elementow okna do okna glownego
     * aplikacji
     *
     * @param window okno do ktorego maja zostac dodane elementy
     */
    public Panels(JFrame window) {
        this.window = window;
        panels();
        buttons();
        list();
        logs();
        clock();
        info = new ShowInfo(rightLog, leftLog, chartPanel);
        bankOperations = new BankOperations();
        CreateOrDeleteCustomer codc = new CreateOrDeleteCustomer(bankOperations, info, listaKont, removeButton);
    }

    /**
     * Metoda tworzaca i dodajace do okna aplikacji 3 glowne panele: 1. Panel z
     * lista aktywnych kont 2. Panel na ktorym beda wyswietlane informacje
     * zwrotne 3. Panel z przyciskami
     */
    private void panels() {
        mainPanel = new JPanel(new BorderLayout());
        listPanel = new JPanel(new BorderLayout());
        logPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel = new JPanel();
        listPanel.setBackground(Color.decode("0xCDCDB4"));
        buttonPanel.setBackground(Color.decode("0xCDCDB4"));
        logPanel.setBackground(Color.decode("0xCDCDB4"));
        window.add(mainPanel);
        mainPanel.add(listPanel, BorderLayout.NORTH);
        mainPanel.add(logPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        leftLogPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        rightLogPanel = new JPanel(new GridLayout(2, 1, 0, 0));

        logPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        leftLogPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightLogPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        logPanel.add(leftLogPanel);
        logPanel.add(rightLogPanel);

    }

    /**
     * Metoda odpowiadajaca za stworzenie, dodanie i przypisanie akcji
     * przyciskom 1. dodawania nowego konta 2. usuwania wybranego konta
     */
    private void buttons() {
        addButton = new JButton("Stworz konto");
        removeButton = new JButton("Usun konto");
        createLogButton = new JButton("Log do XLS");
        openLog = new JButton("Open Log");
        removeButton.setEnabled(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long newNumber = bankOperations.newNumber();
                bankOperations.newAccount(newNumber, info, bankOperations, listaKont, removeButton);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankOperations.deleteNumber(bankOperations.getNumber(index), index, listaKont, removeButton);
            }
        });

        createLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bankOperations.getNumberOfAccounts() != 0) {
                    xls = new XlsLog(bankOperations.getAccountsBalanceList(), bankOperations.getAccountNumberList(), bankOperations);
                } else {
                    errorWindow = new ErrorWindow("Nie ma aktywnych kont");
                }
            }
        });

        openLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (xls != null) {
                    if (xls.logExists()) {
                        xls.open();
                    } else {
                        errorWindow = new ErrorWindow("Log nie istnieje");
                    }
                } else {
                    errorWindow = new ErrorWindow("Log nie istnieje");
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(createLogButton);
        buttonPanel.add(openLog);
    }

    /**
     * Metoda odpowiadajaca za stworzenie i dodanie 3 paneli do panelu z
     * informacja zwrotna 1. panel wyswietlajacy wykonane operacje na wszystkich
     * aktywnych kontach 2. panel wyswietlajacy informacje o wybranym koncie 3.
     * panel wyswietlajacy wykres z historia wybranego konta
     */
    private void logs() {
        leftLog = new JTextArea(10, 10);
        leftLog.setEditable(false);
        leftLogPanel.add(new JScrollPane(leftLog));
        leftLog.setBackground(Color.decode("0xE0EEEE"));

        rightLog = new JLabel();
        rightLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightLog.setHorizontalAlignment(JLabel.CENTER);
        rightLog.setVerticalAlignment(JLabel.NORTH);
        rightLog.setOpaque(true);
        rightLog.setBackground(Color.decode("0xE0EEEE"));

        rightLogPanel.add(rightLog);

        chartPanel = new JPanel();
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightLogPanel.add(chartPanel);
    }

    /**
     * Metoda odpowiadajaca za stworzenie i dodanie listy z aktywnymi kontami
     * oraz za odswiezenie informacji w przypadku zmiany wybranego konta
     */
    private void list() {
        listaKont = new JComboBox();
        listaKont.setPreferredSize(new Dimension(200, 30));
        listPanel.add(listaKont, BorderLayout.WEST);
        listaKont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = listaKont.getSelectedIndex();
                refreshInfo();
            }
        });
    }

    /**
     * Metoda odpowiadajaca za stworzenie obiektu klasy Clock
     */
    private void clock() {
        TimeZone Warszawa = TimeZone.getTimeZone("Europe/Warsaw");
        Clock clock = new Clock(listPanel, Warszawa);
    }

    /**
     * Metoda odswiezajaca informacje o danym koncie
     */
    public void refreshInfo() {
        if (bankOperations.getNumberOfAccounts() != 0) {
            long currentNumber = bankOperations.getNumber(index);
            Account acc = bankOperations.getAccount(index);
            info.accountInfo(currentNumber, acc);
        } else {
            info.accountInfo(0, null);
        }
    }
}
