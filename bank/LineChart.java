package bank;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Klasa odpowiadajaca za rysowanie wykresu liniowego przedstawiajaego historie
 * operacji na danym koncie. Korzystajaca z biblioteki open source JFreeChart
 */
public class LineChart extends ApplicationFrame {

    private ArrayList<Double> balanceList;
    private JPanel logPanel;
    private JFreeChart lineChart;
    private ChartPanel chartPanel;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private XYPlot plot;

    /**
     * Konstruktor klasy LineChart odpowiadajacy za stworzenie wykresu
     *
     * @param balance lista z historia stanu konta
     * @param logPanel panel na ktorym ma zostac narysowany wykres
     */
    public LineChart(ArrayList<Double> balance, JPanel logPanel) {
        super("");
        this.logPanel = logPanel;
        this.balanceList = balance;
        lineChart = ChartFactory.createXYLineChart("", "", "", createDataset(), PlotOrientation.VERTICAL, false, true, false);
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(360, 220));

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        plot = (XYPlot) lineChart.getPlot();

        plot.setDomainAxis(xAxis);
        plot.setRangeAxis(yAxis);

        logPanel.removeAll();
        logPanel.add(chartPanel);
    }

    /**
     * Metoda odpowiadajaca za zwrocenie danych do konta
     *
     * @return dataset z danymi do wykresu
     */
    private XYDataset createDataset() {
        final XYSeries series = new XYSeries("First");
        for (int i = 0; i < balanceList.size(); i++) {
            series.add(i, balanceList.get(i));
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }
}
