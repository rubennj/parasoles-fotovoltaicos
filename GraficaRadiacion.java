/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Ruben
 */
public class GraficaRadiacion extends ApplicationFrame {

    /**
     * A demonstration application showing an XY series1 containing a null value.
     *
     * @param title  the frame title.
     */
    static final int MESES_AÑO = 12;
    static final int HORAS_DIA = 24;
    static final double MAX_IRRADIANCIA = 1000;

    public GraficaRadiacion(final String title, Matriz G_ab) {

        super(title);
        final TimeSeries series1 = new TimeSeries("Enero");
        final TimeSeries series2 = new TimeSeries("Febrero");
        final TimeSeries series3 = new TimeSeries("Marzo");
        final TimeSeries series4 = new TimeSeries("Abril");
        final TimeSeries series5 = new TimeSeries("Mayo");
        final TimeSeries series6 = new TimeSeries("Junio");
        final TimeSeries series7 = new TimeSeries("Julio");
        final TimeSeries series8 = new TimeSeries("Agosto");
        final TimeSeries series9 = new TimeSeries("Septiembre");
        final TimeSeries series10 = new TimeSeries("Octubre");
        final TimeSeries series11 = new TimeSeries("Noviembre");
        final TimeSeries series12 = new TimeSeries("Diciembre");

        for (int hora = 0; hora < HORAS_DIA; hora++) {
            series1.add(new Hour(hora, 10, 6, 2008), G_ab.get(0, hora));
            series2.add(new Hour(hora, 10, 6, 2008), G_ab.get(1, hora));
            series3.add(new Hour(hora, 10, 6, 2008), G_ab.get(2, hora));
            series4.add(new Hour(hora, 10, 6, 2008), G_ab.get(3, hora));
            series5.add(new Hour(hora, 10, 6, 2008), G_ab.get(4, hora));
            series6.add(new Hour(hora, 10, 6, 2008), G_ab.get(5, hora));
            series7.add(new Hour(hora, 10, 6, 2008), G_ab.get(6, hora));
            series8.add(new Hour(hora, 10, 6, 2008), G_ab.get(7, hora));
            series9.add(new Hour(hora, 10, 6, 2008), G_ab.get(8, hora));
            series10.add(new Hour(hora, 10, 6, 2008), G_ab.get(9, hora));
            series11.add(new Hour(hora, 10, 6, 2008), G_ab.get(10, hora));
            series12.add(new Hour(hora, 10, 6, 2008), G_ab.get(11, hora));
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        dataset.addSeries(series5);
        dataset.addSeries(series6);
        dataset.addSeries(series7);
        dataset.addSeries(series8);
        dataset.addSeries(series9);
        dataset.addSeries(series10);
        dataset.addSeries(series11);
        dataset.addSeries(series12);

        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Radiacion",
                "Hora",
                "Irradiacion (W/m2)",
                dataset,
                true,
                true,
                false);

        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis eje = plot.getRangeAxis();
        eje.setAutoRange(false);
        eje.setUpperBound(MAX_IRRADIANCIA);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(0, renderer);

//        XYTextAnnotation annotation = new XYTextAnnotation("World!", new Hour(12, 10, 6, 2008).getFirstMillisecond(), 250);
//        plot.addAnnotation(annotation);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 570));
        setContentPane(chartPanel);
    }
}
