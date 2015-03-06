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
public class AnalisisEficienciaInversor {

    public static void Analiza() throws FileNotFoundException, IOException {
        Map<String, MedidaParasol> valores_hora_parasol = ProcesaAñoParasol.CargaDias("X1");
        String momento;
        double eficiencia;
        int contador_0 = 0;
        int contador_5 = 0;
        int contador_10 = 0;
        int contador_20 = 0;
        int contador_30 = 0;
        int contador_40 = 0;
        int contador_50 = 0;
        int contador_60 = 0;
        int contador_70 = 0;
        int contador_80 = 0;
        int contador_90 = 0;
        int contador_100 = 0;

        for (int num_dia = 1; num_dia <= 365; num_dia++) {
            for (int hora = 0; hora < 24; hora++) {
                momento = String.valueOf(num_dia) + "-" + String.valueOf(hora);//numero_dia y hora
                for (int minuto = 0; minuto < ProcesaDiaParasol.DATOS_POR_HORA; minuto++) {
//                    System.out.println("Momento: " + num_dia + "-" + hora + "-" + minuto);
                    eficiencia = valores_hora_parasol.get(momento).eficiencia_10min_hora[minuto];
                    System.out.println(eficiencia);

                    if (eficiencia == 0.0) {
                        contador_0++;
                    } else if (eficiencia > 0.00 && eficiencia <= 0.05) {
                        contador_5++;
                    } else if (eficiencia > 0.05 && eficiencia <= 0.10) {
                        contador_10++;
                    } else if (eficiencia > 0.10 && eficiencia <= 0.20) {
                        contador_20++;
                    } else if (eficiencia > 0.20 && eficiencia <= 0.30) {
                        contador_30++;
                    } else if (eficiencia > 0.30 && eficiencia <= 0.40) {
                        contador_40++;
                    } else if (eficiencia > 0.40 && eficiencia <= 0.50) {
                        contador_50++;
                    } else if (eficiencia > 0.50 && eficiencia <= 0.60) {
                        contador_60++;
                    } else if (eficiencia > 0.60 && eficiencia <= 0.70) {
                        contador_70++;
                    } else if (eficiencia > 0.70 && eficiencia <= 0.80) {
                        contador_80++;
                    } else if (eficiencia > 0.80 && eficiencia <= 0.90) {
                        contador_90++;
                    } else if (eficiencia > 0.90 && eficiencia <= 1.00) {
                        contador_100++;
                        System.out.println("alto: " + eficiencia);
                    } else {
                        System.out.println("Caso raro! " + eficiencia);
                    }
                }
            }
        }
        System.out.println("Total ==0%: " + contador_0);
        System.out.println("Total <5%: " + contador_5);
        System.out.println("Total <10%: " + contador_10);
        System.out.println("Total <20%: " + contador_20);
        System.out.println("Total <30%: " + contador_30);
        System.out.println("Total <40%: " + contador_40);
        System.out.println("Total <50%: " + contador_50);
        System.out.println("Total <60%: " + contador_60);
        System.out.println("Total <70%: " + contador_70);
        System.out.println("Total <80%: " + contador_80);
        System.out.println("Total <90%: " + contador_90);
        System.out.println("Total <100%: " + contador_100);

    }
}
