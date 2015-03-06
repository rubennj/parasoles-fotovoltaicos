/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Ruben
 */
public class GraficaPosicionSolAnual extends ApplicationFrame {

    /**
     * A demonstration application showing an XY series1 containing a null value.
     *
     * @param title  the frame title.
     */
    static final double DELTA = 0.000000000001;
    static final int RESOLUCION_ENTRE_CURVAS = 10;

    public GraficaPosicionSolAnual(final String title, double latitud, double beta, double Hp, double Lm, double Am, int n, Matriz elevacion, Matriz acimut) {

        super(title);

        final XYSeries[] series_sol = new XYSeries[PosicionSolAnual.PUNTOS_AÑO];
        final XYSeries[] lineas_horarias = new XYSeries[Constantes.PUNTOS_DIA];

//        for (int mes = 0; mes < PosicionSolAnual.PUNTOS_AÑO; mes++) {
//            series_sol[mes] = new XYSeries(mes + 1);
//        }
        series_sol[0] = new XYSeries("Enero");
        series_sol[1] = new XYSeries("Febrero");
        series_sol[2] = new XYSeries("Marzo");
        series_sol[3] = new XYSeries("Abril");
        series_sol[4] = new XYSeries("Mayo");
        series_sol[5] = new XYSeries("Junio");
        series_sol[6] = new XYSeries("Julio");
        series_sol[7] = new XYSeries("Agosto");
        series_sol[8] = new XYSeries("Septiembre");
        series_sol[9] = new XYSeries("Octubre");
        series_sol[10] = new XYSeries("Noviembre");
        series_sol[11] = new XYSeries("Diciembre");

        XYSeriesCollection dataset1 = new XYSeriesCollection();

        for (int mes = 0; mes < acimut.getDimensionFilas(); mes++) {
            for (int hora = 0; hora < acimut.getDimensionColumnas(); hora++) {
                if (elevacion.get(mes, hora) > 0) {
                    series_sol[mes].add(acimut.get(mes, hora), elevacion.get(mes, hora));
                } else {
                    series_sol[mes].add(acimut.get(mes, hora), 0);
                }
            }
            dataset1.addSeries(series_sol[mes]);
        }

        for (int hora = 0; hora < acimut.getDimensionColumnas(); hora++) {
            lineas_horarias[hora] = new XYSeries("Linea horaria");
            for (int mes = 0; mes < acimut.getDimensionFilas(); mes++) {
                if (elevacion.get(mes, hora) > 0) {
                    lineas_horarias[hora].add(acimut.get(mes, hora), elevacion.get(mes, hora));
                } else {
                    lineas_horarias[hora].add(acimut.get(mes, hora), 0);
                }
            }
            dataset1.addSeries(lineas_horarias[hora]);
        }

        double acimut_sombra[] = new double[181];
        double elevacion_sombra_horizontal[] = new double[181];
        double elevacion_sombra_vertical[] = new double[181];
        double divisiones = 100.0 / RESOLUCION_ENTRE_CURVAS + 1;

        XYSeries serie_sombra_horizontal[] = new XYSeries[(int) divisiones];
        XYSeries serie_sombra_vertical[] = new XYSeries[(int) divisiones];

        for (int porcentaje = 0; porcentaje < divisiones; porcentaje++) {
            serie_sombra_horizontal[porcentaje] = new XYSeries(RESOLUCION_ENTRE_CURVAS * porcentaje + "%");
            serie_sombra_vertical[porcentaje] = new XYSeries(RESOLUCION_ENTRE_CURVAS * porcentaje + "%");
            for (int i = 0; i < 181; i++) {
                acimut_sombra[i] = i - 90;
                elevacion_sombra_horizontal[i] = Math.toDegrees(Math.atan(((Hp + (porcentaje * RESOLUCION_ENTRE_CURVAS / 100.0 - 1) * Lm * Math.sin(Math.toRadians(beta))) * Math.cos(Math.toRadians(acimut_sombra[i])))
                        / ((1 - (porcentaje - DELTA) * RESOLUCION_ENTRE_CURVAS / 100.0) * Lm * Math.cos(Math.toRadians(beta)))));

                elevacion_sombra_vertical[i] = Math.toDegrees(Math.atan((Hp * Math.sin(Math.toRadians(acimut_sombra[i]))) / ((1 - porcentaje * RESOLUCION_ENTRE_CURVAS / 100.0) * Am * n * Math.signum(acimut_sombra[i])) - Math.tan(Math.toRadians(beta)) * Math.cos(Math.toRadians(acimut_sombra[i]))));

                serie_sombra_horizontal[porcentaje].add(acimut_sombra[i], elevacion_sombra_horizontal[i]);
                serie_sombra_vertical[porcentaje].add(acimut_sombra[i], elevacion_sombra_vertical[i]);
            }
            dataset1.addSeries(serie_sombra_horizontal[porcentaje]);
            dataset1.addSeries(serie_sombra_vertical[porcentaje]);
        }

        XYSeries serie_cortes = new XYSeries("Cortes");
        double acimut_corte;
        double altura_corte;
        int porcentaje = 0;

        for (int mes = 0; mes < series_sol.length; mes++) {
            if (serie_sombra_horizontal[porcentaje].getY(0).doubleValue() > Interpola(serie_sombra_horizontal[porcentaje].getX(0).doubleValue(), series_sol[mes])) {
                System.out.println("No hay sombras para el mes " + (mes + 1));
            } else {

                System.out.println("Serie_sombra: " + serie_sombra_horizontal[porcentaje].getItems().toString());
                acimut_corte = AcimutCorte(series_sol[mes], serie_sombra_horizontal[porcentaje]);
                altura_corte = Interpola(acimut_corte, serie_sombra_horizontal[porcentaje]);

                System.out.println("Mes " + (mes + 1) + ", acimut de corte " + acimut_corte + ", altura de corte " + altura_corte + ", hora:" + PosicionSolAnual.MomentoSombra(latitud, acimut_corte, altura_corte, mes));

                serie_cortes.add(acimut_corte, Interpola(acimut_corte, serie_sombra_horizontal[porcentaje]));
                serie_cortes.add(Math.abs(acimut_corte), Interpola(Math.abs(acimut_corte), serie_sombra_horizontal[porcentaje]));
            }
        }
        dataset1.addSeries(serie_cortes);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Posicion Sol anual",
                "Azimut",
                "Elevacion",
                dataset1,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        XYPlot plot = (XYPlot) chart.getPlot();

        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.lightGray);

        final XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRendererImpl(plot);
        renderer1.setBaseShapesVisible(false);

        for (int serie = 0; serie < dataset1.getSeriesCount(); serie++) {
            if (serie >= 0 && serie < PosicionSolAnual.PUNTOS_AÑO) {
                renderer1.setSeriesShapesVisible(serie, true);
            } else if (serie >= PosicionSolAnual.PUNTOS_AÑO && serie < Constantes.PUNTOS_DIA + PosicionSolAnual.PUNTOS_AÑO) {
                renderer1.setSeriesPaint(serie, Color.black);
                renderer1.setSeriesVisibleInLegend(serie, false);
            } else if (serie >= Constantes.PUNTOS_DIA + PosicionSolAnual.PUNTOS_AÑO && serie < Constantes.PUNTOS_DIA + PosicionSolAnual.PUNTOS_AÑO + 2 * 11) {
                renderer1.setSeriesStroke(serie, new BasicStroke(0.3f));
                renderer1.setSeriesPaint(serie, Color.black);
            } else if (serie >= Constantes.PUNTOS_DIA + PosicionSolAnual.PUNTOS_AÑO + 2 * 11 && serie < Constantes.PUNTOS_DIA + PosicionSolAnual.PUNTOS_AÑO + 2 * 11 + 1) {
//                renderer1.setSeriesStroke(SERIE, new BasicStroke(2f));
                renderer1.setSeriesLinesVisible(serie, false);
                renderer1.setSeriesPaint(serie, Color.red);
                renderer1.setSeriesShapesVisible(serie, true);
            } else if (serie == 69) {
                renderer1.setSeriesShapesVisible(serie, true);
            }
            plot.setRenderer(serie, renderer1);
        }

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 570));
        setContentPane(chartPanel);
    }

    public static double Interpola(double acimut, XYSeries dia_tipico) {
        double interpolado = dia_tipico.getY(0).doubleValue();
        int i_previo = 0;
        int i = 0;
        while (dia_tipico.getX(i).doubleValue() < acimut) {
            i++;
            interpolado = dia_tipico.getY(i_previo).doubleValue() + ((dia_tipico.getY(i).doubleValue() - dia_tipico.getY(i_previo).doubleValue()) / (dia_tipico.getX(i).doubleValue() - dia_tipico.getX(i_previo).doubleValue())) * (acimut - dia_tipico.getX(i_previo).doubleValue());
//            System.out.println("ptos" + dias.getX(i).doubleValue() + " " + dias.getX(i_previo).doubleValue() + "\n" + dias.getY(i).doubleValue() + " " + dias.getY(i_previo).doubleValue());
            i_previo = i;
        }
        System.out.println("Interpolado: " + interpolado);
        return interpolado;
    }

    public static double AcimutCorte(XYSeries serie_mes_sol, XYSeries serie_sombra) {
        double acimut_corte;
        double acimut_corte_anterior = 0;

        int indice = 0;
        while (serie_sombra.getY(indice).doubleValue() < Interpola(serie_sombra.getX(indice).doubleValue(), serie_mes_sol)) {
            indice++;
        }
        System.out.println("indice: " + indice);
        System.out.println("Entre " + serie_sombra.getX(indice - 1).doubleValue() + " y " + serie_sombra.getX(indice).doubleValue());

        acimut_corte = serie_sombra.getX(indice - 1).doubleValue();
        while (Interpola(acimut_corte, serie_sombra) < Interpola(acimut_corte, serie_mes_sol)) {
            acimut_corte_anterior = acimut_corte;
            acimut_corte += 1;
        }
        System.out.println("Entre " + acimut_corte_anterior + " y " + acimut_corte);

        acimut_corte = acimut_corte_anterior;
        while (Interpola(acimut_corte, serie_sombra) < Interpola(acimut_corte, serie_mes_sol)) {
            acimut_corte_anterior = acimut_corte;
            acimut_corte += 0.1;
        }
        System.out.println("Entre " + acimut_corte_anterior + " y " + acimut_corte);

        acimut_corte = acimut_corte_anterior;
        while (Interpola(acimut_corte, serie_sombra) < Interpola(acimut_corte, serie_mes_sol)) {
            acimut_corte_anterior = acimut_corte;
            acimut_corte += 0.01;
        }
        System.out.println("Entre " + acimut_corte_anterior + " y " + acimut_corte);

        acimut_corte = acimut_corte_anterior;
        while (Interpola(acimut_corte, serie_sombra) < Interpola(acimut_corte, serie_mes_sol)) {
            acimut_corte_anterior = acimut_corte;
            acimut_corte += 0.001;
        }
        System.out.println("Entre " + acimut_corte_anterior + " y " + acimut_corte);

        return acimut_corte;
    }

    private static class XYLineAndShapeRendererImpl extends XYLineAndShapeRenderer {

        private final XYPlot plot;
//        double[] radiacion = {1.8611, 2.9444, 3.7778, 5.2222, 5.8056, 6.5278, 7.2222, 6.4167, 4.6944, 3.1667, 2.0833, 1.6389};
//        Matriz Gdm0 = new Matriz(radiacion);
        Matriz G_ab = RadiacionAnual.Genera(Constantes.LATITUD, Constantes.BETA, Constantes.ALFA, Main.Gdm0, Constantes.ALBEDO);

        public XYLineAndShapeRendererImpl(XYPlot plot) {
            this.plot = plot;
        }

        @Override
        public Shape getItemShape(int mes, int indice) {
            double acimut = plot.getDataset().getXValue(mes, indice);
            double altura = plot.getDataset().getYValue(mes, indice);

            if (mes >= 0 && mes < PosicionSolAnual.PUNTOS_AÑO) {
//                System.out.println("Mes "+mes+" hora " + PosicionSolAnual.MomentoSombra(latitud, acimut, altura, mes) + " acimut y altura " + acimut + " " + altura + " indice " + indice);
                if (altura == 0.0) {
//                    System.out.println("Altura 0");
                    return new Ellipse2D.Double() {
                    };
                } else {
//                    System.out.println("Irradiacion: "+ (G_ab.get(mes, (int) Math.round(PosicionSolAnual.MomentoSombra(latitud, acimut, altura, mes)))));
                    return new Ellipse2D.Double(-(int) (G_ab.get(mes, (int) Math.round(PosicionSolAnual.MomentoSombra(Constantes.LATITUD, acimut, altura, mes)))) / 100, -(int) (G_ab.get(mes, (int) Math.round(PosicionSolAnual.MomentoSombra(Constantes.LATITUD, acimut, altura, mes)))) / 100, (int) (G_ab.get(mes, (int) Math.round(PosicionSolAnual.MomentoSombra(Constantes.LATITUD, acimut, altura, mes)))) / 50, (int) (G_ab.get(mes, (int) Math.round(PosicionSolAnual.MomentoSombra(Constantes.LATITUD, acimut, altura, mes)))) / 50);
                }
            } else {
                return this.lookupSeriesShape(mes);
            }
        }
    }
}
