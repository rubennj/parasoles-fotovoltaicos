/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.text.DecimalFormat;

/**
 *
 * @author Ruben
 */
public class AnalisisMedidaParasol {

    static final double POT_GENERADOR = 2444;
    static final double COEF_TEMP = 0.44 / 100;
    static final double TONC = 47;
    double temp_celula;
    int hora;
    double Ya;
    double Yf;
    double Yr;
    double Yrt;
    double PR;
    double PRt;
    double Eta_ct;
    double Eta_ce;
    double Eta_ei;

    public AnalisisMedidaParasol() {//Borrable??
    }

    AnalisisMedidaParasol(int hora, double vdc, double idc, double pdc, double vac, double iac, double pac, double irradiancia, double temp_ambiente) {
        temp_celula = temp_ambiente + (TONC - 20) / 800 * irradiancia;
        this.hora = hora;
        Ya = pdc / POT_GENERADOR;
        Yf = pac / POT_GENERADOR;
        Yr = irradiancia / 1000;
        Yrt = Yr * (1 - COEF_TEMP * (temp_celula - 25));

//        if (Yr == 0) {
//            PR = 0;
//            Eta_ct = 0;
//        } else {
//            PR = Yf / Yr;
//            Eta_ct = Yrt / Yr;
//        }
//        if (Yrt == 0) {
//            PRt = 0;
//            Eta_ce = 0;
//        } else {
//            PRt = Yf / Yrt;
//            Eta_ce = Ya / Yrt;
//        }
//        if (Ya == 0) {
//            Eta_ei = 0;
//        } else {
//            Eta_ei = Yf / Ya;
//        }

        PR = Yf / Yr;
        PRt = Yf / Yrt;
        Eta_ct = Yrt / Yr;
        Eta_ce = Ya / Yrt;
        if (java.lang.Double.isNaN(Eta_ce)||java.lang.Double.isInfinite(Eta_ce)) {
            Eta_ce = 0;
        }
        Eta_ei = Yf / Ya;
    }

    @Override
    public String toString() {
        DecimalFormat formateador = new DecimalFormat("####.####");
        DecimalFormat formateador_porcentaje = new DecimalFormat("####.#%");
        return "Análisis: " + hora + " Tc:" + formateador.format(temp_celula) + " Ya:" + formateador.format(Ya) + " Yf:" + formateador.format(Yf) + " Yr:" + formateador.format(Yr) + " Yrt:" + formateador.format(Yrt) + " PR:" + formateador_porcentaje.format(PR) + " PRt:" + formateador_porcentaje.format(PRt) + " Eta_ct:" + formateador_porcentaje.format(Eta_ct) + " Eta_ce:" + formateador_porcentaje.format(Eta_ce) + " Eta_ei:" + formateador_porcentaje.format(Eta_ei);
    }
}
