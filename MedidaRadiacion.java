package conexionredtrabajo1;

import java.text.DecimalFormat;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ruben
 */
public class MedidaRadiacion {

    int dia;
    int mes;
    int año;
    int numero_dia;
    int hora;
    double g_41;
    double g_0;
    double d_0;
    double b_n;
    double temp_ambiente;
    AnalisisMedidaRadiacion analisis;

    public void Analizar() {
        analisis = new AnalisisMedidaRadiacion(numero_dia, hora, g_0, d_0, b_n, temp_ambiente);
    }

    @Override
    public String toString() {
        DecimalFormat formateador = new DecimalFormat("####");
        return "\nMedida: " + dia + " " + hora + " G(0):" + formateador.format(g_0) + " D(0):" + formateador.format(d_0) + " B(t):" + formateador.format(b_n) + " Temperatura:" + formateador.format(temp_ambiente);
    }
}
