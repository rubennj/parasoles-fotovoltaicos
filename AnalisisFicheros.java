/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Ruben
 */
public class AnalisisFicheros {

    public static void Analiza() throws FileNotFoundException, IOException {
//        ArrayList<MedidaParasol> valores_hora1 = new ArrayList<MedidaParasol>();
//        Analisis analisis_dia1 = new Analisis();
//        String fichero1 = "Sol_19-6-08_X1";//Sol_21-12-08_X1 Sol_19-6-08_X1 Sol_21-12-08_X2 Sol_19-6-08_X1 Sol_19-6-08_X2
//
//        valores_hora1 = ProcesaDia.CargaHoras("datos\\" + fichero1 + ".dat");
//        analisis_dia1 = ProcesaDia.AnalizaDia(valores_hora1);
//
//        System.out.println("Valor horario: " + valores_hora1.get(12).analisis.toString() + "\nMedia diaria-\u003E" + analisis_dia1.toString());

//        Informe.GeneraInforme(valores_hora1, fichero1);

        Map<String, MedidaParasol> valores_hora_parasol = ProcesaAñoParasol.CargaDias("X2");
        System.out.println("\nNº horas Radiacion PARASOL: " + valores_hora_parasol.size());

        Map<String, MedidaRadiacion> valores_hora_radiacion = ProcesaAñoRadiacion.CargaHoras("..\\..\\..\\" + "\\" + "consulta0152008_233042009.txt");
        System.out.println("Nº horas Radiacion ESTACION: " + valores_hora_radiacion.size());

        String momento;
        double energia_sombreada = 0;
        double energia_sombreada_fachada = 0;
        double energia_total_parasol = 0;
        double energia_total_radiacion = 0;
        double kWh_anual = 0;

        for (int num_dia = 1; num_dia <= 365; num_dia++) {
            for (int hora = 0; hora < 24; hora++) {
                momento = String.valueOf(num_dia) + "-" + String.valueOf(hora);//numero_dia y hora
                energia_total_parasol += valores_hora_parasol.get(momento).irradiancia;
                kWh_anual += valores_hora_parasol.get(momento).pac / 1000;
                try {
                    energia_total_radiacion += valores_hora_radiacion.get(momento).analisis.g_ab;
                    if (valores_hora_radiacion.get(momento).analisis.elevacion > 0 && (valores_hora_radiacion.get(momento).analisis.acimut > -90 + AnalisisMedidaRadiacion.ALFA && valores_hora_radiacion.get(momento).analisis.acimut < 90 + AnalisisMedidaRadiacion.ALFA) && (valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_vertical > 0 && valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_horizontal > 0)) {
                        energia_sombreada += valores_hora_parasol.get(momento).irradiancia * valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada;
                        System.out.println("mom-somb: " + momento + " energia: " + valores_hora_parasol.get(momento).irradiancia + " %rad_somb: " + valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada);
                        System.out.println("     %sombra: " + valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_vertical + ", " + valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_horizontal);
                    }
                    if (valores_hora_radiacion.get(momento).analisis.elevacion > 0 && (valores_hora_radiacion.get(momento).analisis.acimut < -90 + AnalisisMedidaRadiacion.ALFA || valores_hora_radiacion.get(momento).analisis.acimut > 90 + AnalisisMedidaRadiacion.ALFA)) {
                        energia_sombreada_fachada += valores_hora_parasol.get(momento).irradiancia * valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada;
                        System.out.println("mom-fach: " + momento + " energia: " + valores_hora_parasol.get(momento).irradiancia + " %rad_somb: " + valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada);
                    }
                } catch (NullPointerException excepcion1) {
//                    System.out.println("Falta dia " + num_dia + " hora " + hora);
                    try {
                        energia_total_radiacion += valores_hora_radiacion.get(momento).analisis.g_ab;
                        momento = String.valueOf(num_dia - 1) + "-" + String.valueOf(hora);//numero_dia y hora
                        if (valores_hora_radiacion.get(momento).analisis.elevacion > 0 && (valores_hora_radiacion.get(momento).analisis.acimut > -90 + AnalisisMedidaRadiacion.ALFA && valores_hora_radiacion.get(momento).analisis.acimut < 90 + AnalisisMedidaRadiacion.ALFA) && (valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_vertical > 0 && valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_horizontal > 0)) {
                            energia_sombreada += valores_hora_parasol.get(momento).irradiancia * valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada;
                        }
                        if (valores_hora_radiacion.get(momento).analisis.elevacion > 0 && (valores_hora_radiacion.get(momento).analisis.acimut < -90 + AnalisisMedidaRadiacion.ALFA || valores_hora_radiacion.get(momento).analisis.acimut > 90 + AnalisisMedidaRadiacion.ALFA)) {
                            energia_sombreada_fachada += valores_hora_parasol.get(momento).irradiancia * valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada;
                        }
                    } catch (NullPointerException excepcion2) {
                        try {
                            energia_total_radiacion += valores_hora_radiacion.get(momento).analisis.g_ab;
                            momento = String.valueOf(num_dia - 2) + "-" + String.valueOf(hora);//numero_dia y hora
                            if (valores_hora_radiacion.get(momento).analisis.elevacion > 0 && (valores_hora_radiacion.get(momento).analisis.acimut > -90 + AnalisisMedidaRadiacion.ALFA && valores_hora_radiacion.get(momento).analisis.acimut < 90 + AnalisisMedidaRadiacion.ALFA) && (valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_vertical > 0 && valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_horizontal > 0)) {
                                energia_sombreada += valores_hora_parasol.get(momento).irradiancia * valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada;
                            }
                            if (valores_hora_radiacion.get(momento).analisis.elevacion > 0 && (valores_hora_radiacion.get(momento).analisis.acimut < -90 + AnalisisMedidaRadiacion.ALFA || valores_hora_radiacion.get(momento).analisis.acimut > 90 + AnalisisMedidaRadiacion.ALFA)) {
                                energia_sombreada_fachada += valores_hora_parasol.get(momento).irradiancia * valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada;
                            }
                        } catch (NullPointerException excepcion3) {
                            try {
                                energia_total_radiacion += valores_hora_radiacion.get(momento).analisis.g_ab;
                                momento = String.valueOf(num_dia - 3) + "-" + String.valueOf(hora);//numero_dia y hora
                                if (valores_hora_radiacion.get(momento).analisis.elevacion > 0 && (valores_hora_radiacion.get(momento).analisis.acimut > -90 + AnalisisMedidaRadiacion.ALFA && valores_hora_radiacion.get(momento).analisis.acimut < 90 + AnalisisMedidaRadiacion.ALFA) && (valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_vertical > 0 && valores_hora_radiacion.get(momento).analisis.porcentaje_sombreado_horizontal > 0)) {
                                    energia_sombreada += valores_hora_parasol.get(momento).irradiancia * valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada;
                                }
                                if (valores_hora_radiacion.get(momento).analisis.elevacion > 0 && (valores_hora_radiacion.get(momento).analisis.acimut < -90 + AnalisisMedidaRadiacion.ALFA || valores_hora_radiacion.get(momento).analisis.acimut > 90 + AnalisisMedidaRadiacion.ALFA)) {
                                    energia_sombreada_fachada += valores_hora_parasol.get(momento).irradiancia * valores_hora_radiacion.get(momento).analisis.porcentaje_radiacion_sombreada;
                                }
                            } catch (NullPointerException excepcion4) {
                            }
                        }
                    }
                }
            }
        }
        System.out.println("% energia_sombreada: " + (energia_sombreada / energia_total_parasol) * 100);
        System.out.println("% energia_sombreada fachada: " + (energia_sombreada_fachada / energia_total_parasol) * 100);
        System.out.println("Gdm_ab medida parasol: " + energia_total_parasol / 365 + "\tGdm_ab medida radiacion estimado: " + energia_total_radiacion / 365);
        System.out.println("Energia kWh/año: " + kWh_anual);

//        Matriz psi_s = new Matriz(PosicionSolAnual.PUNTOS_AÑO, Constantes.PUNTOS_DIA);
//        Matriz gamma_s = new Matriz(PosicionSolAnual.PUNTOS_AÑO, Constantes.PUNTOS_DIA);
//        PosicionSolAnual.Genera(Constantes.LATITUD, gamma_s, psi_s);
    }
}
