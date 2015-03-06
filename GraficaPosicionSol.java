/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author Ruben
 */
public class GraficaPosicionSol {

    /**
     * A demonstration application showing an XY series1 containing a null value.
     *
     * @param title  the frame title.
     */
    static final int RESOLUCION_ENTRE_CURVAS = 10;
    static final int PUNTOS_LINEA = 181;//entre -90 y +90 porque esta en un fachada (y es sombreado total), si no aparecen sombras incompletas en ciertos casos
    static final int DIVISIONES = 100 / RESOLUCION_ENTRE_CURVAS + 1;
    static final int[] DIAS_EXTREMOS = {51, 81, 111, 172, 355};
    static final int MAXIMO_ESCALA = 1000;
    public JFreeChart chart;
    public double radiacion_anual_horizontal = 0;
    public double radiacion_anual = 0;
    public double sombreado_anual_fachada = 0;
    public double sombreado_anual_mutuo_maximo = 0;
    public double sombreado_anual_mutuo_area = 0;

    public GraficaPosicionSol() {
//        int porcentaje_con_FV = 66;
//        Constantes.ALFA = 5.76;
//        Constantes.ALFA_FACHADA = 5.76;
////        Constantes.BETA = 45;
//        Constantes.AM = 2700;
//        Constantes.LM_FV = 300;
//        Constantes.LM_FV *= (porcentaje_con_FV / 100.0);
//        Constantes.HP = 282;
//        Constantes.SP = 0;
//        Constantes.DP = 0;

        XYSeriesCollection lineas = new XYSeriesCollection();

        DefaultXYZDataset fondo = new DefaultXYZDataset();
        XYSeries[] series_sol = new XYSeries[DIAS_EXTREMOS.length];
        XYSeries[] lineas_horarias = new XYSeries[Constantes.PUNTOS_DIA];

        double max_G_ab = DatosSol(lineas, fondo, series_sol, lineas_horarias);

        double acimut_sombra[] = new double[PUNTOS_LINEA];
        double elevacion_sombra_vertical[] = new double[PUNTOS_LINEA];
        double elevacion_sombra_horizontal[] = new double[PUNTOS_LINEA];

        XYSeries serie_sombra_vertical[] = new XYSeries[(int) DIVISIONES];
        XYSeries serie_sombra_horizontal[] = new XYSeries[(int) DIVISIONES];

        for (int porcentaje = 0; porcentaje < DIVISIONES; porcentaje++) {
            serie_sombra_vertical[porcentaje] = new XYSeries(RESOLUCION_ENTRE_CURVAS * porcentaje + "%");
            serie_sombra_horizontal[porcentaje] = new XYSeries(RESOLUCION_ENTRE_CURVAS * porcentaje + "%");
            for (int i = 0; i < PUNTOS_LINEA; i++) {
                acimut_sombra[i] = i - (PUNTOS_LINEA - 1) / 2;

                elevacion_sombra_vertical[i] = ElevacionSombraVertical(acimut_sombra[i], porcentaje * RESOLUCION_ENTRE_CURVAS / 100.0);
                elevacion_sombra_horizontal[i] = ElevacionSombraHorizontal(acimut_sombra[i], porcentaje * RESOLUCION_ENTRE_CURVAS / 100.0);

                serie_sombra_vertical[porcentaje].add(acimut_sombra[i] + Constantes.ALFA, elevacion_sombra_vertical[i]);
                serie_sombra_horizontal[porcentaje].add(acimut_sombra[i] + Constantes.ALFA, elevacion_sombra_horizontal[i]);
            }
            if (Constantes.LINEAS_SOMBRA == true) {
                lineas.addSeries(serie_sombra_vertical[porcentaje]);
                lineas.addSeries(serie_sombra_horizontal[porcentaje]);
            }
        }

        System.out.println("Sombreado horizontal directo %: " + PorcentajeSombreadoHorizontal(35, 25));
        System.out.println("Sombreado vertical directo %: " + PorcentajeSombreadoVertical(35, 25));

        chart = ChartFactory.createXYLineChart(
                "Elevación",
                "Azimut",
                "Elevación",
                lineas,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDataset(1, fondo);

        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.lightGray);
        NumberAxis rango = new NumberAxis();
        rango.setRange(0, 92);
        plot.setRangeAxis(rango);
        if (Constantes.LINEAS_SOMBRA == true) {
            ValueMarker m0 = new ValueMarker(Constantes.ALFA);
            m0.setPaint(Color.getHSBColor(0.4f, 0.6f, 0.6f));
            plot.addDomainMarker(m0);

            plot.addDomainMarker(new ValueMarker(-90 + Constantes.ALFA_FACHADA));
            ValueMarker m1 = new ValueMarker(-90 + Constantes.ALFA);
            m1.setPaint(Color.getHSBColor(0.95f, 0.6f, 0.6f));
            plot.addDomainMarker(m1);

            plot.addDomainMarker(new ValueMarker(90 + Constantes.ALFA_FACHADA));
            ValueMarker m2 = new ValueMarker(90 + Constantes.ALFA);
            m2.setPaint(Color.getHSBColor(0.95f, 0.6f, 0.6f));
            plot.addDomainMarker(m2);

            ValueMarker m = new ValueMarker(0);
            m.setPaint(Color.getHSBColor(0.4f, 0.6f, 0.6f));
            plot.addRangeMarker(m);
        }
        final XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();//Impl(plot);
        renderer1.setBaseShapesVisible(false);
        renderer1.setBaseSeriesVisibleInLegend(false);

        StandardXYItemLabelGenerator generator = new StandardXYItemLabelGeneratorImpl();

        renderer1.setSeriesItemLabelGenerator(3, generator);
        renderer1.setSeriesItemLabelsVisible(3, true);
        renderer1.setSeriesPositiveItemLabelPosition(3, new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.BOTTOM_CENTER));

        final XYBlockRenderer renderer2 = new XYBlockRenderer();
        LookupPaintScale escala = new LookupPaintScale(0, max_G_ab, Color.lightGray);
        for (int color = 0; color <= max_G_ab - 100; color = color + 10) {
            escala.add(color, Color.getHSBColor(1 - (color + 10) * 1 / ((float) max_G_ab - 100), 0.4f, 1.0f));
        }
        PaintScaleLegend psl = new PaintScaleLegend(escala, new NumberAxis());
        psl.setPadding(5, 5, 5, 20);
        psl.setAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        psl.setPosition(RectangleEdge.RIGHT);
        chart.addSubtitle(psl);

        renderer2.setPaintScale(escala);
        renderer2.setBaseSeriesVisible(false);
        renderer2.setBaseSeriesVisibleInLegend(false);
        renderer2.setBlockHeight(1);
        renderer2.setBlockWidth(5);

        for (int serie = 0; serie < lineas.getSeriesCount(); serie++) {
            if (serie >= 0 && serie < series_sol.length) {
                renderer1.setSeriesPaint(serie, Color.black);
            } else if (serie >= series_sol.length && serie < lineas_horarias.length + series_sol.length) {
                renderer1.setSeriesPaint(serie, Color.black);
                renderer1.setSeriesStroke(serie, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{5f, 8f}, 0f));
            } else if (serie >= lineas_horarias.length + series_sol.length && serie < lineas_horarias.length + series_sol.length + 2 * DIVISIONES) {
                renderer1.setSeriesPaint(serie, Color.darkGray);
                renderer1.setSeriesStroke(serie, new BasicStroke(0.5f));
                renderer1.setSeriesItemLabelGenerator(serie, generator);
                renderer1.setSeriesItemLabelFont(serie, new Font("Default", Font.PLAIN, 8));
                renderer1.setSeriesItemLabelPaint(serie, Color.gray);

                if (serie % 2 == 0) {
                    renderer1.setSeriesPaint(serie, Color.getHSBColor(0.4f, 0.6f, 0.6f));
                    renderer1.setSeriesPositiveItemLabelPosition(serie, new ItemLabelPosition(ItemLabelAnchor.INSIDE3, TextAnchor.CENTER_LEFT));
                } else {
                    renderer1.setSeriesPaint(serie, Color.getHSBColor(0.95f, 0.6f, 0.6f));
                    renderer1.setSeriesPositiveItemLabelPosition(serie, new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.BOTTOM_CENTER));
                }
                renderer1.setSeriesItemLabelsVisible(serie, true);
            } else if (serie >= lineas_horarias.length + series_sol.length + 2 * DIVISIONES && serie < lineas_horarias.length + series_sol.length + 2 * DIVISIONES + 1) {
//                renderer1.setSeriesStroke(SERIE, new BasicStroke(2f));
                renderer1.setSeriesLinesVisible(serie, false);
                renderer1.setSeriesPaint(serie, Color.red);
                renderer1.setSeriesShapesVisible(serie, true);
            } else if (serie >= lineas_horarias.length + series_sol.length + 2 * DIVISIONES + 1 && serie < lineas_horarias.length + series_sol.length + 2 * DIVISIONES + 1 + 4) {
                renderer1.setSeriesPaint(serie, Color.decode("#008000"));
                renderer1.setSeriesStroke(serie, new BasicStroke(2f));
            }
            plot.setRenderer(0, renderer1);
            plot.setRenderer(1, renderer2);
        }
    }

    public double DatosSol(XYSeriesCollection lineas, DefaultXYZDataset fondo, XYSeries[] series_sol, XYSeries[] lineas_horarias) {
        DatosSolMomento datos_sol = new DatosSolMomento("Aguiar");
        DatosSolMomento datos_sol_grafico = new DatosSolMomento("Lineal");
        double area_sombreada;

        if (Constantes.GRAFICO_AGUIAR == true) {//Cambia la representacion grafica, pero NO los calculos energeticos
            datos_sol_grafico = datos_sol;
        }

        radiacion_anual_horizontal = datos_sol.radiacion_anual_horizontal;

        for (int dia = 0; dia < DatosSolMomento.NUM_DIAS; dia++) {
            double[][] datos_dia = new double[3][DatosSolMomento.MOM_DIA];
            for (int hora = 0; hora < DatosSolMomento.MOM_DIA; hora++) {
                datos_dia[0][hora] = datos_sol_grafico.acimut.get(dia, hora);
                datos_dia[1][hora] = datos_sol_grafico.elevacion.get(dia, hora);
                datos_dia[2][hora] = datos_sol_grafico.G_ab.get(dia, hora);

                radiacion_anual += datos_sol.G_ab.get(dia, hora) / DatosSolMomento.MOM_HORA;

                //El sombreado es para: elevaciones>0, [-90,+90] (total por fachada), [elevacion>ele_sombra0% y elevacion<ele_sombra100%] (%sombra_horizontal>0) y ademas esta entre acimut[aci_sombra0% <-> aci_sombra100%] (%sombra_vertical>0)
                //Se puede multiplicar el sombreado por el %sombra en vez de todo o nada...
                if (datos_sol.elevacion.get(dia, hora) > 0) {
                    if (datos_sol.acimut.get(dia, hora) < -90 + Constantes.ALFA_FACHADA || datos_sol.acimut.get(dia, hora) > 90 + Constantes.ALFA_FACHADA) {
                        sombreado_anual_fachada += (datos_sol.B_ab.get(dia, hora) + datos_sol.Dc_ab.get(dia, hora)) / DatosSolMomento.MOM_HORA;//Se divide por DatosSolMomento.MOM_HORA, las divisiones por hora que se hacen.
                    } else {
                        if (PorcentajeSombreadoHorizontal(datos_sol.acimut.get(dia, hora), datos_sol.elevacion.get(dia, hora)) > 0 && PorcentajeSombreadoVertical(datos_sol.acimut.get(dia, hora), datos_sol.elevacion.get(dia, hora)) > 0) {
                            sombreado_anual_mutuo_maximo += (datos_sol.B_ab.get(dia, hora) + datos_sol.Dc_ab.get(dia, hora)) / DatosSolMomento.MOM_HORA;
                            area_sombreada = PorcentajeSombreadoHorizontal(datos_sol.acimut.get(dia, hora), datos_sol.elevacion.get(dia, hora)) / 100 * PorcentajeSombreadoVertical(datos_sol.acimut.get(dia, hora), datos_sol.elevacion.get(dia, hora)) / 100;
                            if (area_sombreada > 1) {
                                area_sombreada = 1;
                            }
                            sombreado_anual_mutuo_area += (datos_sol.B_ab.get(dia, hora) + datos_sol.Dc_ab.get(dia, hora)) / DatosSolMomento.MOM_HORA * area_sombreada;
                        }
                    }
                }
            }
            fondo.addSeries(dia, datos_dia);
        }

        for (int dia = 0; dia < series_sol.length; dia++) {
            series_sol[dia] = new XYSeries(dia + 1);
            for (int hora = 0; hora < DatosSolMomento.MOM_DIA; hora++) {
                series_sol[dia].add(datos_sol.acimut.get(DIAS_EXTREMOS[dia], hora), datos_sol.elevacion.get(DIAS_EXTREMOS[dia], hora));
            }
            lineas.addSeries(series_sol[dia]);
        }

        for (int hora = 0; hora < lineas_horarias.length; hora++) {
            lineas_horarias[hora] = new XYSeries("Linea horaria");
            for (int dia = 0; dia < DatosSolMomento.NUM_DIAS; dia += 30) {//Separación de las líneas horarias discontinuas
                lineas_horarias[hora].add(datos_sol.acimut.get(dia, hora * DatosSolMomento.MOM_HORA), datos_sol.elevacion.get(dia, hora * DatosSolMomento.MOM_HORA));
            }
            lineas.addSeries(lineas_horarias[hora]);
        }

        System.out.println("radiacion y sombreado: " + radiacion_anual + " " + sombreado_anual_mutuo_maximo + " " + (sombreado_anual_mutuo_maximo / radiacion_anual) * 100 + "%" + "+" + (sombreado_anual_fachada / radiacion_anual) * 100 + "%" + "=" + ((sombreado_anual_fachada + sombreado_anual_mutuo_maximo) / radiacion_anual) * 100 + "%" + " " + (sombreado_anual_mutuo_area / radiacion_anual) * 100 + "%");
        return MAXIMO_ESCALA;//Valor maximo de escala
    }

    public static double PorcentajeSombreadoVertical(double acimut_sin_alfa, double elevacion) {
        double porcentaje;
        double acimut = acimut_sin_alfa - Constantes.ALFA;
        if ((Constantes.HP < Constantes.LM_FV * Math.sin(Math.toRadians(Constantes.BETA))) || (Constantes.SP < Constantes.LM_FV * Math.cos(Math.toRadians(Constantes.BETA)))) {
            porcentaje = 1 - (Constantes.SP * Math.tan(Math.toRadians(elevacion)) + Constantes.HP * Math.cos(Math.toRadians(acimut))) / (Constantes.LM_FV * (Math.sin(Math.toRadians(Constantes.BETA)) * Math.cos(Math.toRadians(acimut)) + Math.tan(Math.toRadians(elevacion)) * Math.cos(Math.toRadians(Constantes.BETA))));
        } else {
            porcentaje = 0;
        }
        return porcentaje * 100;
    }

    public static double PorcentajeSombreadoHorizontal(double acimut_sin_alfa, double elevacion) {
        double porcentaje;
        double acimut = acimut_sin_alfa - Constantes.ALFA;
        if ((Constantes.HP < Constantes.LM_FV * Math.sin(Math.toRadians(Constantes.BETA)))) {
            porcentaje = 1 - ((((Constantes.HP * Math.cos(Math.toRadians(Constantes.BETA)) - Constantes.SP * Math.sin(Math.toRadians(Constantes.BETA))) * Math.sin(Math.toRadians(acimut))) / (Constantes.AM * Math.signum(acimut * -1) * (Math.sin(Math.toRadians(Constantes.BETA)) * Math.cos(Math.toRadians(acimut)) + Math.tan(Math.toRadians(elevacion)) * Math.cos(Math.toRadians(Constantes.BETA))))) + Constantes.DP / (Constantes.AM * Math.signum(acimut * -1)));
        } else if ((Constantes.SP < Constantes.LM_FV * Math.cos(Math.toRadians(Constantes.BETA)))) {
            porcentaje = 1 - ((((Constantes.HP * Math.cos(Math.toRadians(Constantes.BETA)) - Constantes.SP * Math.sin(Math.toRadians(Constantes.BETA))) * Math.sin(Math.toRadians(acimut))) / (Constantes.AM * (Math.sin(Math.toRadians(Constantes.BETA)) * Math.cos(Math.toRadians(acimut)) + Math.tan(Math.toRadians(elevacion)) * Math.cos(Math.toRadians(Constantes.BETA))))) + Constantes.DP / Constantes.AM);
        } else {
            porcentaje = 0;
        }
        return porcentaje * 100;
    }

    public static double ElevacionSombraVertical(double acimut_sombra, double porcentaje) {
        double elevacion_sombra_vertical;
        if ((Constantes.HP < Constantes.LM_FV * Math.sin(Math.toRadians(Constantes.BETA))) || (Constantes.SP < Constantes.LM_FV * Math.cos(Math.toRadians(Constantes.BETA)))) {
            elevacion_sombra_vertical = Math.toDegrees(Math.atan((Constantes.HP - (1 - porcentaje) * Constantes.LM_FV * Math.sin(Math.toRadians(Constantes.BETA))) * Math.cos(Math.toRadians(acimut_sombra)) / ((1 - porcentaje) * Constantes.LM_FV * Math.cos(Math.toRadians(Constantes.BETA)) - Constantes.SP)));
        } else {
            elevacion_sombra_vertical = 0;
        }

        return elevacion_sombra_vertical;
    }

    public static double ElevacionSombraHorizontal(double acimut_sombra, double porcentaje) {
        double elevacion_sombra_horizontal;
        if ((Constantes.HP < Constantes.LM_FV * Math.sin(Math.toRadians(Constantes.BETA)))) {
            elevacion_sombra_horizontal = Math.toDegrees(Math.atan(((Constantes.HP - Constantes.SP * Math.tan(Math.toRadians(Constantes.BETA))) * Math.sin(Math.toRadians(acimut_sombra))) / ((1 - porcentaje) * Constantes.AM * Math.signum(acimut_sombra * -1) - Constantes.DP) - Math.tan(Math.toRadians(Constantes.BETA)) * Math.cos(Math.toRadians(acimut_sombra))));
        } else if ((Constantes.SP < Constantes.LM_FV * Math.cos(Math.toRadians(Constantes.BETA)))) {
            elevacion_sombra_horizontal = Math.toDegrees(Math.atan(((Constantes.HP - Constantes.SP * Math.tan(Math.toRadians(Constantes.BETA))) * Math.sin(Math.toRadians(acimut_sombra))) / ((1 - porcentaje) * Constantes.AM * Math.signum(acimut_sombra) - Constantes.DP) - Math.tan(Math.toRadians(Constantes.BETA)) * Math.cos(Math.toRadians(acimut_sombra))));
        } else {
            elevacion_sombra_horizontal = 0;
        }
        return elevacion_sombra_horizontal;
    }

    public static double DiaSol(double acimut, double elevacion) {
        double dia;
        double declinacion;

        declinacion = -1 * Math.asin(Math.cos(Math.toRadians(acimut)) * Math.cos(Math.toRadians(elevacion)) * Math.cos(Math.toRadians(Constantes.LATITUD)) - Math.sin(Math.toRadians(elevacion)) * Math.sin(Math.toRadians(Constantes.LATITUD)));
        dia = Math.asin(declinacion / Math.toRadians(23.45)) * (365 / (2 * Math.PI)) - 284;
        if (dia < 0) {
            dia += 365;
        }
        return dia;
    }

    public static double HoraSol(double acimut, double elevacion) {
        double hora;
        double declinacion;

        declinacion = -1 * Math.asin(Math.cos(Math.toRadians(acimut)) * Math.cos(Math.toRadians(elevacion)) * Math.cos(Math.toRadians(Constantes.LATITUD)) - Math.sin(Math.toRadians(elevacion)) * Math.sin(Math.toRadians(Constantes.LATITUD)));
        hora = Math.acos((Math.sin(Math.toRadians(elevacion)) - Math.sin(declinacion) * Math.sin(Math.toRadians(Constantes.LATITUD))) / (Math.cos(declinacion) * Math.cos(Math.toRadians(Constantes.LATITUD))));
        hora *= (12 / Math.PI);
        if (acimut < 0) {
            hora *= -1;
        }

        return hora;
    }

    public static double ElevacionVentanaHorizontal(double acimut_ventana, double posicion) {
        double elevacion_ventana_horizontal = Math.toDegrees(Math.atan((posicion * Math.cos(Math.toRadians(acimut_ventana))) / (Constantes.LM_FV * Math.cos(Math.toRadians(Constantes.BETA)))));
        return elevacion_ventana_horizontal;
    }

    public static double ElevacionVentanaVertical(double acimut_ventana, double posicion_vertical, double posicion_horizontal) {
        double elevacion_ventana_vertical = Math.toDegrees(Math.atan((posicion_vertical * Math.sin(Math.toRadians(acimut_ventana)) * Math.signum(acimut_ventana)) / posicion_horizontal));
        return elevacion_ventana_vertical;
    }

    private static class StandardXYItemLabelGeneratorImpl extends StandardXYItemLabelGenerator {

        @Override
        public String generateLabel(XYDataset dataset, int serie, int indice) {
            if (serie == 3 && indice % DatosSolMomento.MOM_HORA == 0) {
                return String.valueOf(indice / DatosSolMomento.MOM_HORA - Constantes.PUNTOS_DIA / 2) + "h";
            } else if (serie >= Constantes.PUNTOS_DIA + DIAS_EXTREMOS.length && serie < Constantes.PUNTOS_DIA + DIAS_EXTREMOS.length + 2 * DIVISIONES && serie % 2 != 0 && (dataset.getX(serie, indice).doubleValue() == 0.0 + Constantes.ALFA)) {
                return (serie - (Constantes.PUNTOS_DIA + DIAS_EXTREMOS.length)) * (DIVISIONES - 1) / 2 + "%";
            } else {
                if (Constantes.BETA == 90) {
                    if (serie >= Constantes.PUNTOS_DIA + DIAS_EXTREMOS.length && serie < Constantes.PUNTOS_DIA + DIAS_EXTREMOS.length + 2 * DIVISIONES && serie % 2 == 0 && (dataset.getX(serie, indice).doubleValue() == +Constantes.ALFA)) {
                        return (serie - (Constantes.PUNTOS_DIA + DIAS_EXTREMOS.length) - 1) * (DIVISIONES - 1) / 2 + "%";
                    }
                } else {
                    if (serie >= Constantes.PUNTOS_DIA + DIAS_EXTREMOS.length && serie < Constantes.PUNTOS_DIA + DIAS_EXTREMOS.length + 2 * DIVISIONES && serie % 2 == 0 && (dataset.getX(serie, indice).doubleValue() == 90 + Constantes.ALFA)) {
                        return (serie - (Constantes.PUNTOS_DIA + DIAS_EXTREMOS.length) - 1) * (DIVISIONES - 1) / 2 + "%";
                    }
                }
            }
            return "";
        }
    }
}
