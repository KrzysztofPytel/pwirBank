package bank;

import java.awt.Desktop;
import jxl.Workbook;
import jxl.write.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Klasa odpowiadajaca za zapisanie informacji z aktywnych kont do pliku XLS
 */
public class XlsLog {

    private BankOperations bankOperations;
    private static String fileLocation;
    private ArrayList<ArrayList<Double>> balanceList;
    private ArrayList<Long> accountNumber;
    private File file;
    private WritableSheet[] excelSheet;
    private boolean open;
    private boolean log;
    private DialogueWindow dw;

    /**
     * Konstruktor odpowiadajacy za stworzenie klasy CreateXls tworzacej plik
     * XLS
     *
     * @param balanceList obiekt klasy BalanceList
     * @param accountNumber lista z numerami aktywnych kont
     * @param bankOperations lista z listami historii srodkow na danych kontach
     */
    public XlsLog(ArrayList<ArrayList<Double>> balanceList, ArrayList<Long> accountNumber, BankOperations bankOperations) {
        this.bankOperations = bankOperations;
        this.balanceList = balanceList;
        this.accountNumber = accountNumber;

        log = false;

        CreateXls createXls = new CreateXls(this);
        createXls.execute();
    }

    /**
     * Metoda odpowiadajaca za otworzenie pliku XLS
     */
    public void open() {
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop is not supported");
        }

        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            try {
                desktop.open(file);
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Metoda zwracajaca informacje czy plik XLS zostal stworzony
     *
     * @return
     */
    public boolean logExists() {
        return log;
    }

    /**
     * Klasa odpiwadajaca za stworzenie pliku XLS rozszerzajaca klase
     * SwingWorkeer.
     */
    private class CreateXls extends SwingWorker<Void, Void> {

        private XlsLog xls;

        /**
         * @param xls obiekt klasy XlsLog
         */
        CreateXls(XlsLog xls) {
            this.xls = xls;
        }

        /**
         * Metoda odpiwiadajaca za stworzenie pliku XLS
         *
         * @return null
         * @throws Exception
         */
        @Override
        protected Void doInBackground() throws Exception {
            WritableWorkbook workBook = null;
            try {
                FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("XLS files", "xls");
                JFileChooser saveAsFileChooser = new JFileChooser();
                saveAsFileChooser.setApproveButtonText("Zapisz");
                saveAsFileChooser.setFileFilter(extensionFilter);
                int actionDialog = saveAsFileChooser.showOpenDialog(new JFrame());
                if (actionDialog != JFileChooser.APPROVE_OPTION) {
                    return null;
                }

                file = saveAsFileChooser.getSelectedFile();
                if (!file.getName().endsWith(".xls")) {
                    file = new File(file.getAbsolutePath() + ".xls");
                }

                fileLocation = file.getPath().toString();

                System.out.println("eloszka");
                workBook = Workbook.createWorkbook(new File(fileLocation));

                WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
                cellFont.setBoldStyle(WritableFont.BOLD);
                WritableCellFormat cellFormat = new WritableCellFormat();
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setFont(cellFont);

                WritableFont cellFont2 = new WritableFont(WritableFont.ARIAL, 10);
                WritableCellFormat cellFormat2 = new WritableCellFormat();
                cellFormat2.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat2.setFont(cellFont2);

                excelSheet = new WritableSheet[accountNumber.size()];
                for (int i = 0; i < accountNumber.size(); i++) {
                    long number = accountNumber.get(i);
                    excelSheet[i] = workBook.createSheet(String.format("%014d", number), 0);
                    Label label = new Label(0, 0, "LP", cellFormat);
                    excelSheet[i].addCell(label);

                    label = new Label(1, 0, "Balance", cellFormat);
                    excelSheet[i].addCell(label);

                    label = new Label(2, 0, "Operacja", cellFormat);
                    excelSheet[i].addCell(label);

                    label = new Label(3, 0, "Kwota", cellFormat);
                    excelSheet[i].addCell(label);

                    for (int j = 0; j < balanceList.get(i).size(); j++) {
                        label = new Label(0, j + 1, Integer.toString(j + 1), cellFormat2);
                        excelSheet[i].addCell(label);

                        label = new Label(1, j + 1, String.format("%.2f", balanceList.get(i).get(j)), cellFormat2);
                        excelSheet[i].addCell(label);

                        if (j > 0) {
                            double before = balanceList.get(i).get(j - 1);
                            double now = balanceList.get(i).get(j);

                            if ((now - before) > 0) {
                                label = new Label(2, j, "wplata", cellFormat2);
                                excelSheet[i].addCell(label);

                                double kwota = now - before;
                                label = new Label(3, j, String.format("%.2f", kwota), cellFormat2);
                                excelSheet[i].addCell(label);
                            } else {
                                label = new Label(2, j, "wyplata", cellFormat2);
                                excelSheet[i].addCell(label);

                                double kwota = now - before;
                                label = new Label(3, j, String.format("%.2f", kwota), cellFormat2);
                                excelSheet[i].addCell(label);
                            }
                        }
                    }

                }

                workBook.write();

            } catch (IOException e) {
            } catch (WriteException e) {
            } finally {

                if (workBook != null) {
                    try {
                        workBook.close();
                    } catch (IOException e) {
                    } catch (WriteException e) {
                    }
                }

            }
            return null;
        }

        /**
         * Metoda odpowiadajaca za otworzenie okna dialogowego gdy plik zostal
         * stworzony i wyczyszczenie historii bilansu na kontach
         */
        protected void done() {
            if (file != null) {
                log = true;
                dw = new DialogueWindow(xls);
            }
            bankOperations.clearHistory();
        }

    }

}
