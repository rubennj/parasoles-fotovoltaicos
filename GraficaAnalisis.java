/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Ruben
 */
public class GraficaAnalisis extends ApplicationFrame {

    /**
     * A demonstration application showing an XY series1 containing a null value.
     *
     * @param title  the frame title.
     */
    static final double MAX_PRODUCTIVIDAD = 1;
    static final double MAX_RENDIMIENTO = 1.3;
    static final double MAX_TEMP_CELULA = 70;

    public GraficaAnalisis(final String title, ArrayList<MedidaParasol> valores_hora) {

        super(title);
        final TimeSeries series1 = new TimeSeries("Yr");
        final TimeSeries series2 = new TimeSeries("Yrt");
        final TimeSeries series3 = new TimeSeries("Ya");
        final TimeSeries series4 = new TimeSeries("Yf");
        final TimeSeries series5 = new TimeSeries("Temperatura celula");
        final TimeSeries series6 = new TimeSeries("PR");
        final TimeSeries series7 = new TimeSeries("PRt");
        final TimeSeries series8 = new TimeSeries("Eta_ct");
        final TimeSeries series9 = new TimeSeries("Eta_ce");
        final TimeSeries series10 = new TimeSeries("Eta_ei");

        for (int hora = 0; hora < valores_hora.size(); hora++) {
            //TODO: ver la fecha de cada Hour, pasar la de cada fichero?
            series1.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.Yr);
            series2.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.Yrt);
            series3.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.Ya);
            series4.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.Yf);
            series5.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.temp_celula);
            series6.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.PR);
            series7.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.PRt);
            series8.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.Eta_ct);
            series9.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.Eta_ce);
            series10.add(new Hour(hora, 10, 6, 2008), valores_hora.get(hora).analisis.Eta_ei);
        }

        TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        dataset1.addSeries(series1);
        dataset1.addSeries(series2);
        dataset1.addSeries(series3);
        dataset1.addSeries(series4);

        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset2.addSeries(series5);

        TimeSeriesCollection dataset3 = new TimeSeriesCollection();
        dataset3.addSeries(series6);
        dataset3.addSeries(series7);

        TimeSeriesCollection dataset4 = new TimeSeriesCollection();
        dataset4.addSeries(series8);
        dataset4.addSeries(series9);
        dataset4.addSeries(series10);

        //Productividades subplot1-a
        NumberAxis rangeAxis1 = new NumberAxis("Productividades (Wh/Wp)");
        rangeAxis1.setAutoRange(false);
        rangeAxis1.setUpperBound(MAX_PRODUCTIVIDAD);
        ClusteredXYBarRenderer renderer1a = new ClusteredXYBarRenderer(0, true);
        renderer1a.setShadowVisible(false);
        XYPlot subplot1 = new XYPlot(dataset1, null, rangeAxis1, renderer1a);
        subplot1.setDomainGridlinesVisible(true);
        subplot1.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
//        subplot1.setBackgroundPaint(Color.lightGray);
//        subplot1.setRangeGridlinePaint(Color.white);
//        subplot1.setDomainGridlinePaint(Color.white);
        //Productividades subplot1-a

        //Temp celula subplot1-b
        final XYLineAndShapeRenderer renderer1b = new XYLineAndShapeRenderer();
        renderer1b.setSeriesPaint(0, Color.red);
        subplot1.setRenderer(1, renderer1b);

        subplot1.setDataset(1, dataset2);
        NumberAxis eje1 = new NumberAxis("Temperatura celula (ºC)");
        eje1.setAutoRange(false);
        eje1.setUpperBound(MAX_TEMP_CELULA);
        subplot1.setRangeAxis(1, eje1);
        subplot1.mapDatasetToRangeAxis(1, 1);
        //Temp celula subplot1-b

        //PR subplot2-a
        NumberAxis rangeAxis2 = new NumberAxis("PR y Rendimientos (%)");
        rangeAxis2.setAutoRange(false);
        rangeAxis2.setUpperBound(MAX_RENDIMIENTO);
        ClusteredXYBarRenderer renderer2a = new ClusteredXYBarRenderer(0, true);
        renderer2a.setShadowVisible(false);
        XYPlot subplot2 = new XYPlot(dataset3, null, rangeAxis2, renderer2a);
        subplot2.setDomainGridlinesVisible(true);
        subplot2.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
//        subplot2.setBackgroundPaint(Color.lightGray);
//        subplot2.setRangeGridlinePaint(Color.white);
//        subplot2.setDomainGridlinePaint(Color.white);
        //PR subplot2-a

        //Perdidas subplot2-b
        final XYLineAndShapeRenderer renderer2b = new XYLineAndShapeRenderer();
        renderer2b.setSeriesPaint(0, Color.red);
        subplot2.setRenderer(1, renderer2b);

        subplot2.setDataset(1, dataset4);
        //Perdidas subplot2-b

        DateAxis domainAxis = new DateAxis("Horas");
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        domainAxis.setMinorTickCount(24);
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.HOUR, 1,formatter));
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(domainAxis);
        plot.add(subplot1, 1);
        plot.add(subplot2, 1);
        JFreeChart chart = new JFreeChart(
                "Analisis horario/dia",
                new Font("SansSerif", Font.BOLD, 12),
                plot,
                true);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 570));
        setContentPane(chartPanel);
    }
}
