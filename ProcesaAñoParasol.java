/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ruben
 */
public class ProcesaAñoParasol {

    public static int DIA_INICIO = 1;
    public static int DIA_FINAL = 365;

    public static Map<String, MedidaParasol> CargaDias(String parasol) throws FileNotFoundException, IOException {
        ArrayList<MedidaParasol> valores_hora_dia = new ArrayList<MedidaParasol>();
        AnalisisMedidaParasol analisis_dias[] = new AnalisisMedidaParasol[365];
        GregorianCalendar calendario = new GregorianCalendar();
        Map<String, MedidaParasol> valores_hora_año = new HashMap<String, MedidaParasol>();
        int dia;
        int mes;
        String fichero = "";

        for (int numero_dia = DIA_INICIO; numero_dia <= DIA_FINAL; numero_dia++) {
            calendario.set(GregorianCalendar.DAY_OF_YEAR, numero_dia);
            dia = calendario.get(GregorianCalendar.DAY_OF_MONTH);
            mes = calendario.get(GregorianCalendar.MONTH) + 1;

//            System.out.println("Numero dia del año: " + numero_dia + " " + dia + "-" + mes);

            try {
                fichero = dia + "-" + mes + "-08_" + parasol + ".dat";
                valores_hora_dia = ProcesaDiaParasol.CargaHoras("..\\..\\..\\datos\\" + parasol + "\\" + fichero);
                analisis_dias[numero_dia - 1] = ProcesaDiaParasol.AnalizaDia(valores_hora_dia);
            } catch (FileNotFoundException excepcion) {
                fichero = dia + "-" + mes + "-09_" + parasol + ".dat";
                valores_hora_dia = ProcesaDiaParasol.CargaHoras("..\\..\\..\\datos\\" + parasol + "\\" + fichero);
                analisis_dias[numero_dia - 1] = ProcesaDiaParasol.AnalizaDia(valores_hora_dia);
            } finally {
//                System.out.println("fichero: " + fichero);
            }

//            System.out.println("Valor horario nº 12: " + valores_hora_dia.get(12).analisis.toString());
//            System.out.println("Media diaria-\u003E" + analisis_dias[numero_dia - 1].toString());

            for (int hora = 0; hora < 24; hora++) {
                String indice = String.valueOf(numero_dia) + "-" + String.valueOf(hora);
                valores_hora_dia.get(hora).numero_dia = numero_dia;
                valores_hora_año.put(indice, valores_hora_dia.get(hora));
            }
        }
        System.out.println("\nMedia anual " + parasol + " " + AnalizaAño(analisis_dias));

        return valores_hora_año;
//        Informe.GeneraInforme(valores_hora1, fichero1);
    }

    public static AnalisisMedidaParasol AnalizaAño(AnalisisMedidaParasol analisis_dia[]) {
        AnalisisMedidaParasol año = new AnalisisMedidaParasol();

        for (int numero_dia = DIA_INICIO; numero_dia <= DIA_FINAL; numero_dia++) {
            año.Ya += analisis_dia[numero_dia - 1].Ya;
            año.Yf += analisis_dia[numero_dia - 1].Yf;
            año.Yr += analisis_dia[numero_dia - 1].Yr;
            año.Yrt += analisis_dia[numero_dia - 1].Yrt;
        }
        año.PR = año.Yf / año.Yr;
        año.PRt = año.Yf / año.Yrt;
        año.Eta_ct = año.Yrt / año.Yr;
        año.Eta_ce = año.Ya / año.Yrt;
        año.Eta_ei = año.Yf / año.Ya;

        return año;
    }
}
