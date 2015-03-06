/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Ruben
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static public final double[] radiacion = {1.8611, 2.9444, 3.7778, 5.2222, 5.8056, 6.5278, 7.2222, 6.4167, 4.6944, 3.1667, 2.0833, 1.6389};//{1.821, 3.044, 4.449, 5.766, 5.313, 7.159, 7.995, 6.962, 4.913, 3.061, 2.418, 1.647}; medias de datos analizados
    static final Matriz Gdm0 = new Matriz(radiacion);

    public static void main(String[] args) {
        try {
//            AnalisisFicheros.Analiza();
            AnalisisEficienciaInversor.Analiza();
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException :" + ex);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }

//        int porcentaje_con_FV = 66;
//        Constantes.ALFA = 5.76;
//        Constantes.ALFA_FACHADA = 5.76;
//        Constantes.BETA = 45;
//        Constantes.N = 17;
//        Constantes.AM = 2700;
//        Constantes.LM = 300;
//        Constantes.LM *= (porcentaje_con_FV / 100.0);
//        Constantes.HP = 282;
//        Constantes.SP = 0;
//        System.out.println("SP: " + Constantes.SP);
//        System.out.println("LM: " + Constantes.LM);
//        Constantes.DP = 0;
////        Constantes.DP = Constantes.SP * Math.tan(Math.toRadians(Constantes.ALFA_FACHADA - Constantes.ALFA));
////        System.out.println("\nDP:" + Constantes.DP);
//
//        int dia = 10;
//        int mes = 6;
//        int num_dia = new GregorianCalendar(2009, mes - 1, dia).get(GregorianCalendar.DAY_OF_YEAR);
////        System.out.println("dia " + num_dia);
//
//        final GraficaPosicionSolDia demo5 = new GraficaPosicionSolDia("Grafica Posicion Sol día " + num_dia, num_dia, dia, mes, Constantes.LATITUD, Constantes.BETA, Constantes.HP, Constantes.LM, Constantes.AM, Constantes.N);
//        demo5.pack();
//        RefineryUtilities.centerFrameOnScreen(demo5);
//        demo5.setVisible(true);

        //        PosicionSolDia dia_prueba = new PosicionSolDia(Constantes.LATITUD, dia);
        //        Matriz G_ab = RadiacionAnual.Genera(Constantes.LATITUD, Constantes.BETA, Constantes.ALFA, Gdm0, Constantes.ALBEDO);
        //        System.out.println("G_ab: "+G_ab);
        //        final GraficaMedida demo = new GraficaMedida("Grafica medidas instalación: " + fichero1, valores_hora1);
        //        demo.pack();
        //        RefineryUtilities.centerFrameOnScreen(demo);
        //        demo.setVisible(true);
        //
        //        final GraficaAnalisis demo2 = new GraficaAnalisis("Grafica análisis instalación: " + fichero1, valores_hora1);
        //        demo2.pack();
        //        RefineryUtilities.centerFrameOnScreen(demo2);
        //        demo2.setVisible(true);
        //        final GraficaRadiacion demo3 = new GraficaRadiacion("Grafica productividad: " + fichero1, G_ab);
        //        demo3.pack();
        //        RefineryUtilities.centerFrameOnScreen(demo3);
        //        demo3.setVisible(true);
        //        final GraficaPosicionSolAnual demo4 = new GraficaPosicionSolAnual("Grafica Posicion Sol anual", Constantes.LATITUD, Constantes.BETA, Constantes.HP, Constantes.LM, Constantes.AM, Constantes.N, gamma_s, psi_s);
        //        demo4.pack();
        //        RefineryUtilities.centerFrameOnScreen(demo4);
        //        demo4.setVisible(true);
    }
}
