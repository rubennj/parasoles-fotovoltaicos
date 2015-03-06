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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ruben
 */
public class ProcesaAñoRadiacion {

    public static Map<String, MedidaRadiacion> CargaHoras(String fichero) throws FileNotFoundException, IOException {
        BufferedReader br = null;
        Map<String, MedidaRadiacion> valores_hora_anual = new HashMap<String, MedidaRadiacion>();
        String linea;
        String parte_linea[];

        // Apertura del fichero y creacion de BufferedReader para poder
        // hacer una lectura comoda (disponer del metodo readLine()).

        br = new BufferedReader(new FileReader(new File(fichero)));

        // Lectura del fichero
        br.readLine();//No procesa la 1ª linea (cabeceras)

        for (int hora = 0; (linea = br.readLine()) != null; hora++) {
            parte_linea = linea.split("\\t");

            MedidaRadiacion medida = new MedidaRadiacion();//necesario esta linea en esta posicion para ke vaya creando objetos Medida nuevos cada iteracion

            String[] fecha = parte_linea[0].split("/");
            medida.año = Integer.valueOf(fecha[0]).intValue();
            medida.mes = Integer.valueOf(fecha[1]).intValue();
            medida.dia = Integer.valueOf(fecha[2]).intValue();
            medida.numero_dia = new GregorianCalendar(2009, medida.mes - 1, medida.dia).get(GregorianCalendar.DAY_OF_YEAR);//Necisita un año no bisiesto para cuadrar la numeracion

            medida.hora = Integer.valueOf(parte_linea[1]).intValue();
            medida.g_0 = Double.valueOf(parte_linea[2]).doubleValue();
            medida.g_41= Double.valueOf(parte_linea[3]).doubleValue();
            medida.d_0 = Double.valueOf(parte_linea[4]).doubleValue();
            medida.b_n = Double.valueOf(parte_linea[5]).doubleValue();
            medida.temp_ambiente = Double.valueOf(parte_linea[6]).doubleValue();

            medida.Analizar();

            String indice = String.valueOf(medida.numero_dia) + "-" + String.valueOf(medida.hora);

            valores_hora_anual.put(indice, medida);
        }

        return valores_hora_anual;
    }
}
