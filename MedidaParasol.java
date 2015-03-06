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
public class MedidaParasol {

    int hora;
    int numero_dia;
    double vdc;
    double idc;
    double pdc;
    double vac;
    double iac;
    double pac;
    double irradiancia;
    double temp_ambiente;
    double eficiencia_10min_hora[] = new double[ProcesaDiaParasol.DATOS_POR_HORA];
    AnalisisMedidaParasol analisis;

    public void Analizar() {
        analisis = new AnalisisMedidaParasol(hora, vdc, idc, pdc, vac, iac, pac, irradiancia, temp_ambiente);
    }

    @Override
    public String toString() {
        DecimalFormat formateador = new DecimalFormat("####");
        return "\nMedida:" + hora + " Tension dc:" + formateador.format(vdc) + " Corriente dc:" + formateador.format(idc) + " Potencia dc:" + formateador.format(pdc) + " Tension ac:" + formateador.format(vac) + " Corriente ac:" + formateador.format(iac) + " Potencia ac:" + formateador.format(pac) + " Irradiancia:" + formateador.format(irradiancia) + " Temperatura:" + formateador.format(temp_ambiente) + "\nAnalisis:" + analisis;
    }
}
