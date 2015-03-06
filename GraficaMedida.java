/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Ruben
 */
public class GraficaMedida extends ApplicationFrame {

    /**
     * A demonstration application showing an XY series1 containing a null value.
     *
     * @param title  the frame title.
     */
    static final double MAX_IRRADIANCIA = 1000;
    static final double MAX_TEMPERATURA = 40;
    static final double MAX_POTENCIA = 2000;

    public GraficaMedida(final String title, ArrayList<MedidaParasol> valores_hora) {

        super(title);
        final TimeSeries series1 = new TimeSeries("Irradiación");
        final TimeSeries series2 = new TimeSeries("Temp_amb");
        final TimeSeries series3 = new TimeSeries("Pdc");
        final TimeSeries series4 = new TimeSeries("Pac");

        for (int hora = 0; hora < valores_hora.size(); hora++) {
            series1.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).irradiancia);
            series2.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).temp_ambiente);
            series3.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).pdc);
            series4.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).pac);
        }

        TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        dataset1.addSeries(series1);

        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset2.addSeries(series2);

        TimeSeriesCollection dataset3 = new TimeSeriesCollection();
        dataset3.addSeries(series3);
        dataset3.addSeries(series4);

        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Datos medidos",
                "Hora",
                "Irradiacion (W/m2)",
                dataset1,
                true,
                true,
                false);

        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis eje = plot.getRangeAxis();
        eje.setAutoRange(false);
        eje.setUpperBound(MAX_IRRADIANCIA);
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
        domainAxis.setMinorTickCount(24);
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.HOUR, 1, formatter));
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.lightGray);

        final XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
        renderer1.setSeriesPaint(0, Color.blue);
        plot.setRenderer(0, renderer1);

        final XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        renderer2.setSeriesPaint(0, Color.red);
        plot.setRenderer(1, renderer2);

        final XYLineAndShapeRenderer renderer3 = new XYLineAndShapeRenderer();
        renderer3.setSeriesPaint(0, Color.black);
        renderer3.setSeriesPaint(1, Color.green);
        plot.setRenderer(2, renderer3);

        plot.setDataset(1, dataset2);
        plot.mapDatasetToRangeAxis(1, 1);
        NumberAxis eje1 = new NumberAxis("Temperatura ambiente (ºC)");
        eje1.setAutoRange(false);
        eje1.setUpperBound(MAX_TEMPERATURA);
        plot.setRangeAxis(1, eje1);

        plot.setDataset(2, dataset3);
        plot.mapDatasetToRangeAxis(2, 2);
        NumberAxis eje2 = new NumberAxis("Potencia (W)");
        eje2.setAutoRange(false);
        eje2.setUpperBound(MAX_POTENCIA);
        plot.setRangeAxis(2, eje2);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 570));
        setContentPane(chartPanel);
    }
}
