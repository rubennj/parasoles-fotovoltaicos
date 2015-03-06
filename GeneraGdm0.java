/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

/**
 *
 * @author Ruben
 */
import java.math.BigDecimal;

public class GeneraGdm0 {

    public GeneraGdm0(int anosCalculados, double phi, double[] datos) {
        this.anosCalculados = anosCalculados;
        this.phi = (phi / 180) * Math.PI;
        this.datos = datos;
    }
    private double matriz[][] = {
        {0.229, 0.333, 0.208, 0.042, 0.083, 0.042, 0.042, 0.021, 0, 0, 0.167, 0.319, 0.194, 0.139, 0.097, 0.028, 0.042, 0, 0.014, 0, 0.25, 0.25, 0.091, 0.136, 0.091, 0.046, 0.046, 0.023, 0.068, 0, 0.158, 0.237, 0.158, 0.263, 0.026, 0.053, 0.079, 0.026, 0, 0, 0.211, 0.053, 0.211, 0.158, 0.053, 0.053, 0.158, 0.105, 0, 0, 0.125, 0.125, 0.25, 0.188, 0.063, 0.125, 0, 0.125, 0, 0, 0.04, 0.24, 0.08, 0.12, 0.08, 0.08, 0.12, 0.12, 0.08, 0.04, 0, 0.25, 0, 0.125, 0, 0.125, 0.125, 0.25, 0.063, 0.063, 0, 0.25, 0, 0.125, 0.25, 0, 0.25, 0, 0, 0.125, 0, 0, 0, 0, 0, 0, 0.5, 0.25, 0, 0.25},
        {0, 0, 0.091, 0, 0.364, 0.091, 0.182, 0, 0.273, 0, 0.118, 0.118, 0.176, 0.118, 0.059, 0.118, 0.176, 0.059, 0.059, 0, 0.067, 0.267, 0.067, 0.2, 0.067, 0, 0.133, 0.133, 0, 0.067, 0.118, 0.235, 0, 0.235, 0.059, 0.176, 0.118, 0, 0.059, 0, 0.077, 0.154, 0.308, 0.077, 0.154, 0.077, 0, 0.077, 0.077, 0, 0.083, 0, 0.167, 0.25, 0.083, 0.167, 0, 0.083, 0.167, 0, 0.222, 0.222, 0, 0.111, 0.111, 0, 0.111, 0.222, 0, 0, 0.091, 0.182, 0.273, 0, 0.091, 0.273, 0, 0.091, 0, 0, 0.111, 0.111, 0.111, 0.222, 0, 0, 0, 0.222, 0.111, 0.111, 0, 0, 0, 0, 0, 0, 0.5, 0, 0, 0.5},
        {0.206, 0.088, 0.176, 0.176, 0.088, 0.029, 0.176, 0.029, 0.029, 0, 0.12, 0.1, 0.14, 0.16, 0.12, 0.22, 0.1, 0, 0.02, 0.02, 0.077, 0.123, 0.185, 0.123, 0.077, 0.139, 0.092, 0.123, 0.061, 0, 0.048, 0.111, 0.095, 0.206, 0.206, 0.19, 0.095, 0.048, 0, 0, 0.059, 0.137, 0.118, 0.137, 0.098, 0.118, 0.118, 0.157, 0.059, 0, 0.014, 0.097, 0.139, 0.153, 0.125, 0.139, 0.208, 0.056, 0.042, 0.028, 0.073, 0.101, 0.116, 0.145, 0.087, 0.159, 0.203, 0.087, 0.029, 0, 0.019, 0.037, 0.111, 0.056, 0.074, 0.111, 0.185, 0.296, 0.074, 0.037, 0.035, 0.069, 0.035, 0, 0.035, 0.103, 0.172, 0.138, 0.379, 0.035, 0, 0.167, 0.167, 0, 0.167, 0, 0, 0.333, 0, 0.167},
        {0.167, 0.167, 0.167, 0, 0.083, 0.125, 0, 0.167, 0.125, 0, 0.117, 0.117, 0.15, 0.117, 0.083, 0.117, 0.2, 0.067, 0.017, 0.017, 0.049, 0.085, 0.134, 0.158, 0.098, 0.11, 0.134, 0.134, 0.061, 0.037, 0.039, 0.09, 0.141, 0.141, 0.167, 0.141, 0.09, 0.141, 0.039, 0.013, 0.009, 0.139, 0.074, 0.093, 0.194, 0.139, 0.167, 0.093, 0.074, 0.019, 0.036, 0.018, 0.117, 0.099, 0.144, 0.18, 0.18, 0.117, 0.072, 0.036, 0, 0.046, 0.061, 0.061, 0.136, 0.159, 0.273, 0.167, 0.098, 0, 0.016, 0.056, 0.08, 0.128, 0.104, 0.08, 0.16, 0.208, 0.136, 0.032, 0.011, 0.053, 0.021, 0.043, 0.128, 0.096, 0.074, 0.223, 0.277, 0.074, 0, 0.074, 0.037, 0, 0.074, 0.074, 0.074, 0.074, 0.333, 0.259},
        {0.12, 0.2, 0.16, 0.12, 0.12, 0.12, 0.08, 0, 0.04, 0.04, 0.1, 0.08, 0.12, 0.14, 0.14, 0.2, 0.18, 0.04, 0, 0, 0.046, 0.114, 0.068, 0.171, 0.125, 0.171, 0.08, 0.159, 0.057, 0.011, 0.015, 0.061, 0.084, 0.099, 0.191, 0.153, 0.153, 0.115, 0.115, 0.015, 0.024, 0.03, 0.098, 0.098, 0.165, 0.195, 0.195, 0.14, 0.043, 0.012, 0.015, 0.026, 0.062, 0.124, 0.144, 0.17, 0.17, 0.222, 0.062, 0.005, 0, 0.013, 0.045, 0.108, 0.112, 0.175, 0.188, 0.224, 0.117, 0.018, 0.008, 0.023, 0.054, 0.066, 0.093, 0.125, 0.191, 0.253, 0.183, 0.004, 0.006, 0.022, 0.061, 0.033, 0.067, 0.083, 0.139, 0.222, 0.322, 0.044, 0, 0.046, 0.091, 0.091, 0.046, 0.046, 0.136, 0.091, 0.273, 0.182},
        {0.25, 0.179, 0.107, 0.107, 0.143, 0.071, 0.107, 0.036, 0, 0, 0.133, 0.022, 0.089, 0.111, 0.156, 0.178, 0.111, 0.133, 0.067, 0, 0.064, 0.048, 0.143, 0.048, 0.175, 0.143, 0.206, 0.095, 0.079, 0, 0, 0.022, 0.078, 0.111, 0.156, 0.156, 0.244, 0.167, 0.044, 0.022, 0.016, 0.027, 0.037, 0.069, 0.16, 0.219, 0.23, 0.16, 0.075, 0.005, 0.013, 0.025, 0.03, 0.093, 0.144, 0.202, 0.215, 0.219, 0.055, 0.004, 0.006, 0.041, 0.035, 0.064, 0.09, 0.18, 0.337, 0.192, 0.049, 0.006, 0.012, 0.021, 0.029, 0.035, 0.132, 0.123, 0.184, 0.371, 0.082, 0.012, 0.008, 0.016, 0.016, 0.024, 0.071, 0.103, 0.159, 0.27, 0.309, 0.024, 0, 0, 0, 0, 0.059, 0, 0.059, 0.294, 0.412, 0.176},
        {0.217, 0.087, 0, 0.174, 0.13, 0.087, 0.087, 0.13, 0.87, 0, 0.026, 0.079, 0.132, 0.079, 0.026, 0.158, 0.158, 0.132, 0.158, 0.053, 0.02, 0.02, 0.02, 0.04, 0.16, 0.18, 0.16, 0.2, 0.1, 0.1, 0.025, 0.013, 0.038, 0.076, 0.076, 0.139, 0.139, 0.266, 0.215, 0.013, 0.03, 0.03, 0.05, 0.02, 0.091, 0.131, 0.162, 0.283, 0.131, 0.071, 0.006, 0.006, 0.013, 0.057, 0.057, 0.121, 0.204, 0.287, 0.185, 0.064, 0.004, 0.026, 0.037, 0.03, 0.093, 0.107, 0.193, 0.307, 0.167, 0.037, 0.011, 0.009, 0.014, 0.042, 0.041, 0.071, 0.152, 0.418, 0.203, 0.041, 0.012, 0.022, 0.022, 0.038, 0.019, 0.05, 0.113, 0.281, 0.36, 0.084, 0.008, 0.024, 0.039, 0.039, 0.063, 0.039, 0.118, 0.118, 0.284, 0.268},
        {0.067, 0.133, 0.133, 0.067, 0.067, 0.2, 0.133, 0.133, 0.067, 0, 0.118, 0.059, 0.059, 0.059, 0.059, 0.118, 0.118, 0.235, 0.118, 0.059, 0, 0.024, 0.024, 0.049, 0.146, 0.073, 0.195, 0.244, 0.195, 0.049, 0.026, 0, 0.026, 0.026, 0.053, 0.184, 0.263, 0.184, 0.237, 0, 0.014, 0, 0.042, 0.056, 0.069, 0.097, 0.139, 0.306, 0.278, 0, 0.009, 0.009, 0.052, 0.069, 0.052, 0.112, 0.215, 0.285, 0.138, 0.06, 0.009, 0.009, 0.026, 0.017, 0.094, 0.099, 0.232, 0.283, 0.21, 0.021, 0.01, 0.014, 0.016, 0.019, 0.027, 0.062, 0.163, 0.467, 0.202, 0.019, 0.004, 0.007, 0.031, 0.017, 0.033, 0.05, 0.086, 0.252, 0.469, 0.05, 0, 0, 0.015, 0.046, 0.031, 0.046, 0.077, 0.123, 0.446, 0.215},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.25, 0.25, 0.5, 0, 0, 0, 0, 0, 0.25, 0, 0, 0.375, 0.25, 0.125, 0, 0, 0, 0.083, 0, 0.167, 0.167, 0.25, 0.333, 0, 0, 0, 0.042, 0.042, 0.042, 0.083, 0.083, 0.292, 0.292, 0.125, 0, 0, 0.032, 0, 0, 0.032, 0.129, 0.387, 0.355, 0.065, 0, 0, 0, 0.038, 0.038, 0.075, 0.047, 0.34, 0.415, 0.047, 0.004, 0.004, 0.007, 0.007, 0.011, 0.03, 0.052, 0.141, 0.654, 0.089, 0, 0, 0, 0, 0.061, 0.061, 0.03, 0.03, 0.349, 0.47},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0, 0, 0, 0.25, 0, 0, 0, 0.5, 0.25, 0, 0, 0, 0.143, 0.143, 0, 0.143, 0.143, 0.429, 0, 0, 0, 0, 0, 0.2, 0, 0, 0.2, 0.4, 0.2, 0, 0, 0, 0, 0, 0, 0, 0.222, 0.444, 0.333, 0, 0, 0, 0, 0, 0.08, 0.08, 0.08, 0.48, 0.24, 0.04, 0, 0, 0.027, 0.009, 0.027, 0.018, 0.135, 0.523, 0.252, 0.009, 0, 0, 0, 0.022, 0, 0.043, 0.043, 0.326, 0.511, 0.054, 0, 0, 0, 0.143, 0, 0, 0, 0.143, 0.714, 0}
    };
    private double ktmin[] = {0.031, 0.058, 0.051, 0.052, 0.028, 0.053, 0.044, 0.085, 0.010, 0.319};
    private double ktmax[] = {0.705, 0.694, 0.753, 0.753, 0.807, 0.856, 0.818, 0.846, 0.842, 0.865};
    private double[] dkt = new double[ktmin.length];
    private int diaDelAno;
    private int anosCalculados;
    private double phi; //Latitud en radianes
    private double[] datos;
    private double phiZS;
    private double[] ktUltimoDia;
    double promedio;
    double promediosMeses[];
    double[] promedioMensual;
    boolean control; //true si la diferencia (promedio entre todos los anos generados - valor real de G0) < 5%
    boolean controlSerieGenerada; //false si uno de los promedios varia mas de un 5% del promedio real

    //Subrutina para seleccionar la matriz correspondiente del mes
    private int seleccionaMatriz(double ktm) {
        //Seleccionar matriz adecuada segun el indice de claridad
        //ktm = kt promedio del mes
        //Para determinar el número del matriz, se compara el
        //ktAnterior con el ktMatriz de cada matriz
        double ktMatriz = 0.3;
        int numeroMatriz = 0;
        while ((ktm > ktMatriz) && (numeroMatriz < 9)) {
            ktMatriz = ktMatriz + 0.05;
            BigDecimal bd = new BigDecimal(ktMatriz);
            bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
            ktMatriz = bd.doubleValue();
            numeroMatriz += 1;
        }
        return numeroMatriz;
    }

    //Funcion para el calculo de la extraatmosférica diaria
    private double calculaExtraAt(double dia) {
        double delta = (((23.45 / 180) * Math.PI)) * Math.sin((2 * Math.PI / 365) * (dia + 284));
        int Bo = 1367;//constante solar

        phiZS = Math.acos(Math.sin(delta) * Math.sin(phi) + Math.cos(delta) * Math.cos(phi) * Math.cos(0));
        if (phiZS >= (Math.PI / 2)) {
            //System.out.println("0");
            return 0;
        } else {
            //elevacion para w=-PI: si phiZS<90, el sol no se pone
            phiZS = Math.acos(Math.sin(delta) * Math.sin(phi) + Math.cos(delta) * Math.cos(phi) * Math.cos(-Math.PI));
            double ws;
            if (phiZS < (Math.PI / 2)) {
                ws = -Math.PI;
            } else {
                ws = -Math.acos(-Math.tan(delta) * Math.tan(phi));
            }

            double epsilon = 1 + 0.033 * Math.cos((2 * Math.PI * dia) / 365);
            double BOd = 24 * Bo * epsilon / Math.PI * (Math.cos(phi) * Math.cos(delta)) * (ws * Math.cos(ws) - Math.sin(ws));
            return BOd;
        }
    }

    //Subrutina que calcula los valores para un mes de un ano
    private double[] calcIrradiacion(int numeroMatriz, double ktAnterior1, int diasAlMes) {
        double ktAnterior = ktAnterior1;
        double[] Gd0 = new double[diasAlMes];
        int numeroColumna = 0;
        for (int i = 0; i < diasAlMes; i++) {
//			determinar fila
            int numeroFila = 0;
            if (i == 0) {
                double ktFila = ktmin[numeroMatriz] + dkt[numeroMatriz];
                while (ktAnterior > ktFila) {
                    ktFila += dkt[numeroMatriz];
                    numeroFila += 1;
                }
                if (numeroFila > 9) {
                    numeroFila = 9;
                }
            } else {
                numeroFila = numeroColumna;
            }

            //determinar columna
            double n = Math.random();
            numeroColumna = 0;
            //Para determinar la ktActual, un numero aleatorio n se compara con el
            //primer valor de la matriz seleccionada. Si n es mayor,
            //se va a la proxima columna y suma el valor a la anterior
            double probabilidad = matriz[numeroMatriz][10 * numeroFila];
            while ((probabilidad <= n) && (numeroColumna < 9)) {
                numeroColumna += 1;
                probabilidad += matriz[numeroMatriz][10 * numeroFila + numeroColumna];
                BigDecimal bd = new BigDecimal(probabilidad);
                bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
                probabilidad = bd.doubleValue();
            }

            //el intervalo de kt de la columna seleccionada es
            //[ktmin+numeroColumna*dkt:ktmin+(numeroColumna+1)*dkt]
            //El valor de ktactual se calcula por interpolacion lineal
            //entre el valor máximo y mínimo.
            double ktActual = dkt[numeroMatriz] * n + ktmin[numeroMatriz] + numeroColumna * dkt[numeroMatriz];

            //definir este valor como ktAnterior del proximo dia
            ktAnterior = ktActual;
            //Calcular Gd0 para un dia
            Gd0[i] = ktActual * calculaExtraAt(diaDelAno);

            //Ir al proximo dia
            diaDelAno += 1;

            if (i == (diasAlMes - 1)) {
                //Guardar el valor del ultimo dia del mes calculado para cada ano
                ktUltimoDia[ano] = ktActual;
            }
        }

        //Calcular Gd0 promedio del mes
        double sum = 0;
        for (int j = 0; j < Gd0.length; j++) {
            sum += Gd0[j];
        }
        promedio = sum / Gd0.length;
        return Gd0;
    }
    private int ano;

    private double[] calculaKtm(double[] Gdm) {
        double ktm[] = new double[12];
        ktm[0] = 1000 * Gdm[0] / calculaExtraAt(17);
        ktm[1] = 1000 * Gdm[1] / calculaExtraAt(46);
        ktm[2] = 1000 * Gdm[2] / calculaExtraAt(75);
        ktm[3] = 1000 * Gdm[3] / calculaExtraAt(105);
        ktm[4] = 1000 * Gdm[4] / calculaExtraAt(135);
        ktm[5] = 1000 * Gdm[5] / calculaExtraAt(161);
        ktm[6] = 1000 * Gdm[6] / calculaExtraAt(198);
        ktm[7] = 1000 * Gdm[7] / calculaExtraAt(228);
        ktm[8] = 1000 * Gdm[8] / calculaExtraAt(258);
        ktm[9] = 1000 * Gdm[9] / calculaExtraAt(289);
        ktm[10] = 1000 * Gdm[10] / calculaExtraAt(319);
        ktm[11] = 1000 * Gdm[11] / calculaExtraAt(345);
        return ktm;
    }

    public double[][] generaG0Diario() {
        for (int i = 0; i < ktmin.length; i++) {
            dkt[i] = (ktmax[i] - ktmin[i]) / ktmin.length;
        }
        double[] ktm = calculaKtm(datos);
        int numeroMatriz;
        int diasAlMes;
        int primerDiaDelMes = 1;
        double[][] Gd0 = new double[anosCalculados][365];
        ktUltimoDia = new double[anosCalculados];

        //Variable para guardar el promedio de los dias de un mes
        promediosMeses = new double[anosCalculados];
        //Variable para el promedio mensual a lo largo de los anos calculados
        promedioMensual = new double[12];
        control = true;
        controlSerieGenerada = true;
        int intentos = 0;

        //Generar datos para 12 meses
        for (int i = 0; i < 12; i++) {

            //Seleccionar matriz del mes
            numeroMatriz = seleccionaMatriz(ktm[i]);
            //determinar cuantos dias tiene el mes
            if ((i == 0) | (i == 2) | (i == 4) | (i == 6) | (i == 7) | (i == 9) | (i == 11)) {
                diasAlMes = 31;
            } else if (i == 1) {
                diasAlMes = 28;
            } else {
                diasAlMes = 30;
            }

            double ktAnterior;
            //Generar datos para "anosCalculados" anos
            for (int j = 0; j < anosCalculados; j++) {
                ano = j;
                //Determinar dia del ano (1-365) para calcular B0d
                diaDelAno = primerDiaDelMes;
                if (i == 0) {
                    //Para enero, usar promedio de diciembre
                    ktAnterior = ktm[11];
                } else {
                    //Para otros meses: Ultimo dia del mes anterior
                    ktAnterior = ktUltimoDia[j];
                }
                //System.out.println("i "+i+" j "+j+"  ktAnterior "+ktAnterior);
                //Calcular 1 mes
                double[] Gd0Array = calcIrradiacion(numeroMatriz, ktAnterior, diasAlMes);
                for (int k = 0; k < Gd0Array.length; k++) {
                    Gd0[j][k + primerDiaDelMes - 1] = Gd0Array[k];
                }

                promediosMeses[j] = promedio; //alle Januar-Werte
            }

            double sum = 0;
            for (int k = 0; k < anosCalculados; k++) {
                sum += promediosMeses[k];
            }
            promedioMensual[i] = sum / anosCalculados;

            control = (Math.abs(datos[i] - promedioMensual[i] / 1000) / datos[i]) <= 0.05;

            if ((control == false) && (intentos >= 20)) {
                control = (Math.abs(datos[i] - promedioMensual[i] / 1000) / datos[i]) <= 0.1;
            }

            if ((control == false) && (intentos < 30)) {
                i -= 1;
                intentos += 1;
            } else {
                if (intentos == 30) {
                    System.out.println("Aviso: Cálculo inexacto de la base de datos.");
                    controlSerieGenerada = false;
                }
                primerDiaDelMes = diaDelAno;
                intentos = 0;
            }

        }
        return Gd0;
    }

    public boolean checkSerie() {
        return controlSerieGenerada;
    }
}
