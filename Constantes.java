/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

/**
 *
 * @author Ruben
 */
public class Constantes {

    public static double[] DIAS_RADIACION = {17, 46, 75, 105, 135, 161, 198, 228, 258, 289, 319, 345};
    
    public static boolean GRAFICO_AGUIAR = false;
    public static boolean LINEAS_SOMBRA = true;

    public static double LATITUD = 40.4;

    public static double BETA = 35;
    public static double ALFA = 0;
    public static double ALFA_FACHADA = 0;
    public static double ALBEDO = 0.2;

    public static double HP = 4;
    public static double SP = 0;
    public static double DP = 0;//SP * Math.tan(Math.toRadians(ALFA_FACHADA - ALFA));
    public static double porcentaje_con_FV = 100;
    public static double LM = 1.2;
    public static double LM_FV = LM * (porcentaje_con_FV / 100.0);
    public static double AM = 16.9;

    public static double HV1 = 2;//Altura de la parte de arriba de la ventana, desde la parte de abajo del parasol
    public static double HV2 = 3;//Altura de la parte de abajo de la ventana, desde la parte de abajo del parasol
    public static double AV1 = 3;//Distancia desde la parte este del parasol hasta el lado este de la ventana
    public static double AV2 = 6;//Distancia desde la parte oeste del parasol hasta el lado oeste de la ventana

    public static int PUNTOS_DIA = 24;
    public static int PUNTOS_AÑO = 365;
    
    public static final double BO = 1367;

    public static GeneraGdm0 SERIE = new GeneraGdm0(10, LATITUD, Main.radiacion);
    public static double[][] ANYO_SERIE = SERIE.generaG0Diario();
}
