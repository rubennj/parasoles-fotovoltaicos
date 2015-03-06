/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Ruben
 */
public class ProcesaDiaParasol {

    static final int DATOS_POR_HORA = 60 / 10;//cada 10 minutos
    static final double POTENCIA_INVERSOR = 2500.0;

    public static ArrayList<MedidaParasol> CargaHoras(String fichero) throws FileNotFoundException, IOException {
        BufferedReader br = null;
        ArrayList<MedidaParasol> valores_minutos = new ArrayList<MedidaParasol>();
        ArrayList<MedidaParasol> valores_hora = new ArrayList<MedidaParasol>();

        String linea;
        String parte_linea[];

        // Apertura del fichero y creacion de BufferedReader para poder
        // hacer una lectura comoda (disponer del metodo readLine()).

        br = new BufferedReader(new FileReader(new File(fichero)));

        // Lectura del fichero

        for (int minuto = 0; (linea = br.readLine()) != null; minuto++) {
            parte_linea = linea.split("\\s+");

            for (int i = 0; i < 11; i++) {
                if (parte_linea[i].equals("NeuN")) {
                    parte_linea[i] = "0";
                }
            }
            MedidaParasol medida = new MedidaParasol();//necesario esta linea en esta posicion para que vaya creando objetos MedidaParasol nuevos cada iteracion

            medida.hora = Integer.valueOf(parte_linea[0]).intValue();
            medida.vdc = Double.valueOf(parte_linea[1]).doubleValue();
            medida.idc = Double.valueOf(parte_linea[2]).doubleValue();
            medida.pdc = Double.valueOf(parte_linea[3]).doubleValue();
            medida.vac = Double.valueOf(parte_linea[4]).doubleValue();
            medida.iac = Double.valueOf(parte_linea[5]).doubleValue();
            medida.pac = Double.valueOf(parte_linea[6]).doubleValue();
            //[7] esta a cero
            medida.irradiancia = Double.valueOf(parte_linea[8]).doubleValue();
            //[9] desconocido
            medida.temp_ambiente = Double.valueOf(parte_linea[10]).doubleValue();

            valores_minutos.add(minuto, medida);
        }

        //FUSION EN HORAS
        for (int hora = 0; hora < valores_minutos.size() / DATOS_POR_HORA; hora++) {
            MedidaParasol medida = new MedidaParasol();
            medida.hora = valores_minutos.get(hora).hora;
            for (int posicion = 0; posicion < DATOS_POR_HORA; posicion++) {//Realiza la media de los datos para cada hora
                medida.vdc += (valores_minutos.get(hora * DATOS_POR_HORA + posicion).vdc) / DATOS_POR_HORA;
                medida.idc += (valores_minutos.get(hora * DATOS_POR_HORA + posicion).idc) / DATOS_POR_HORA;
                medida.pdc += (valores_minutos.get(hora * DATOS_POR_HORA + posicion).pdc) / DATOS_POR_HORA;
                medida.vac += (valores_minutos.get(hora * DATOS_POR_HORA + posicion).vac) / DATOS_POR_HORA;
                medida.iac += (valores_minutos.get(hora * DATOS_POR_HORA + posicion).iac) / DATOS_POR_HORA;
                medida.pac += (valores_minutos.get(hora * DATOS_POR_HORA + posicion).pac) / DATOS_POR_HORA;
                medida.irradiancia += (valores_minutos.get(hora * DATOS_POR_HORA + posicion).irradiancia) / DATOS_POR_HORA;
                medida.temp_ambiente += (valores_minutos.get(hora * DATOS_POR_HORA + posicion).temp_ambiente) / DATOS_POR_HORA;
                medida.eficiencia_10min_hora[posicion] = valores_minutos.get(hora * DATOS_POR_HORA + posicion).pac / POTENCIA_INVERSOR;
                medida.Analizar();
            }
            valores_hora.add(hora, medida);

//            System.out.println(valores_hora.get(hora).toString());
        }

        return valores_hora;
    }

    public static AnalisisMedidaParasol AnalizaDia(ArrayList<MedidaParasol> valores_hora) {
        AnalisisMedidaParasol dia = new AnalisisMedidaParasol();

        for (int hora = 0; hora < valores_hora.size(); hora++) {
            dia.Ya += valores_hora.get(hora).analisis.Ya;
            dia.Yf += valores_hora.get(hora).analisis.Yf;
            dia.Yr += valores_hora.get(hora).analisis.Yr;
            dia.Yrt += valores_hora.get(hora).analisis.Yrt;
        }
        dia.PR = dia.Yf / dia.Yr;
        dia.PRt = dia.Yf / dia.Yrt;
        dia.Eta_ct = dia.Yrt / dia.Yr;
        dia.Eta_ce = dia.Ya / dia.Yrt;
        dia.Eta_ei = dia.Yf / dia.Ya;

//        if (dia.Yr == 0) {
//            dia.PR = 0;
//            dia.Eta_ct = 0;
//        } else {
//            dia.PR = dia.Yf / dia.Yr;
//            dia.Eta_ct = dia.Yrt / dia.Yr;
//        }
//        if (dia.Yrt == 0) {
//            dia.PRt = 0;
//            dia.Eta_ce = 0;
//        } else {
//            dia.PRt = dia.Yf / dia.Yrt;
//            dia.Eta_ce = dia.Ya / dia.Yrt;
//        }
//        if (dia.Ya == 0) {
//            dia.Eta_ei = 0;
//        } else {
//            dia.Eta_ei = dia.Yf / dia.Ya;
//        }

//        System.out.println("\nMedida dia:" + dia.toString());

        return dia;
    }
}
