/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

/**
 *
 * @author Ruben
 */
public class AnalisisMedidaRadiacion {

    static final double ALBEDO = 0.2;
    static final double ALFA = -8;
    static final double BETA = 35;
    static final double HP = 4;
    static final double LM = 1.2;
    static final double AM = 26 * 0.65;
    static final double LATITUD = 40.4;
    static final double LONGITUD = -3.7;
    static final double LONGITUD_REFERENCIA = 0;
    static final double COEF_PEREZ[][] = {
        {-0.042, 0.550, -0.044, -0.120, 0.138, -0.034},
        {0.261, 0.559, -0.243, -0.019, 0.083, -0.081},
        {0.481, 0.460, -0.354, 0.077, 0.006, -0.116},
        {0.825, 0.187, -0.532, 0.172, -0.050, -0.151},
        {1.102, -0.299, -0.586, 0.350, -0.398, -0.171},
        {1.226, -0.451, -0.617, 0.444, -0.949, -0.073},
        {1.367, -0.838, -0.655, 0.431, -1.750, 0.094},
        {0.978, -0.812, -0.393, 0.335, -2.160, 0.186}
    };
    double porcentaje_radiacion_sombreada;
    double porcentaje_sombreado_vertical;
    double porcentaje_sombreado_horizontal;
    double elevacion;
    double acimut;
    double g_ab;

    public AnalisisMedidaRadiacion() {//Borrable??
    }

    AnalisisMedidaRadiacion(int numero_dia, int hora, double g_0, double d_0, double b_n, double temp_ambiente) {
        double declinacion = 23.45 * Math.sin(((2 * Math.PI) / 365) * (numero_dia + 284));

        double ecuacion_tiempo = (-7.64 * Math.sin((2 * Math.PI) / 365 * (numero_dia - 2)) + 9.86 * Math.sin((2 * Math.PI) / 365 * 2 * (numero_dia - 80))) / 60;

        int adelanto;
        if (numero_dia > 88 && numero_dia < 299) {
            adelanto = 2;
        } else {
            adelanto = 1;
        }

        double tiempo_solar = (hora - 12 + ecuacion_tiempo - adelanto) * 15 - (LONGITUD - LONGITUD_REFERENCIA);

        double cos_theta_zs = Math.sin(Math.toRadians(declinacion)) * Math.sin(Math.toRadians(LATITUD))
                + Math.cos(Math.toRadians(declinacion)) * Math.cos(Math.toRadians(LATITUD)) * Math.cos(Math.toRadians(tiempo_solar));
        double cos_theta_s = Math.sin(Math.toRadians(declinacion)) * Math.sin(Math.toRadians(LATITUD)) * Math.cos(Math.toRadians(BETA))
                - Math.sin(Math.toRadians(declinacion)) * Math.cos(Math.toRadians(LATITUD)) * Math.sin(Math.toRadians(BETA)) * Math.cos(Math.toRadians(ALFA))
                + Math.cos(Math.toRadians(declinacion)) * Math.cos(Math.toRadians(LATITUD)) * Math.cos(Math.toRadians(BETA)) * Math.cos(Math.toRadians(tiempo_solar))
                + Math.cos(Math.toRadians(declinacion)) * Math.sin(Math.toRadians(LATITUD)) * Math.sin(Math.toRadians(BETA)) * Math.cos(Math.toRadians(ALFA)) * Math.cos(Math.toRadians(tiempo_solar))
                + Math.cos(Math.toRadians(declinacion)) * Math.sin(Math.toRadians(ALFA)) * Math.sin(Math.toRadians(BETA)) * Math.sin(Math.toRadians(tiempo_solar));

        double delta = d_0 / (Constantes.BO * cos_theta_zs);
        double epsilon = (d_0 + b_n) / d_0;

        double f11 = 0;
        double f12 = 0;
        double f13 = 0;
        double f21 = 0;
        double f22 = 0;
        double f23 = 0;

        if (epsilon >= 0.99999 && epsilon < 1.056) {
            f11 = COEF_PEREZ[0][0];
            f12 = COEF_PEREZ[0][1];
            f13 = COEF_PEREZ[0][2];
            f21 = COEF_PEREZ[0][3];
            f22 = COEF_PEREZ[0][4];
            f23 = COEF_PEREZ[0][5];
        } else if (epsilon >= 1.056 && epsilon < 1.253) {
            f11 = COEF_PEREZ[1][0];
            f12 = COEF_PEREZ[1][1];
            f13 = COEF_PEREZ[1][2];
            f21 = COEF_PEREZ[1][3];
            f22 = COEF_PEREZ[1][4];
            f23 = COEF_PEREZ[1][5];
        } else if (epsilon >= 1.253 && epsilon < 1.586) {
            f11 = COEF_PEREZ[2][0];
            f12 = COEF_PEREZ[2][1];
            f13 = COEF_PEREZ[2][2];
            f21 = COEF_PEREZ[2][3];
            f22 = COEF_PEREZ[2][4];
            f23 = COEF_PEREZ[2][5];
        } else if (epsilon >= 1.586 && epsilon < 2.134) {
            f11 = COEF_PEREZ[3][0];
            f12 = COEF_PEREZ[3][1];
            f13 = COEF_PEREZ[3][2];
            f21 = COEF_PEREZ[3][3];
            f22 = COEF_PEREZ[3][4];
            f23 = COEF_PEREZ[3][5];
        } else if (epsilon >= 2.134 && epsilon < 3.230) {
            f11 = COEF_PEREZ[4][0];
            f12 = COEF_PEREZ[4][1];
            f13 = COEF_PEREZ[4][2];
            f21 = COEF_PEREZ[4][3];
            f22 = COEF_PEREZ[4][4];
            f23 = COEF_PEREZ[4][5];
        } else if (epsilon >= 3.230 && epsilon < 5.980) {
            f11 = COEF_PEREZ[5][0];
            f12 = COEF_PEREZ[5][1];
            f13 = COEF_PEREZ[5][2];
            f21 = COEF_PEREZ[5][3];
            f22 = COEF_PEREZ[5][4];
            f23 = COEF_PEREZ[5][5];
        } else if (epsilon >= 5.980 && epsilon < 10.080) {
            f11 = COEF_PEREZ[6][0];
            f12 = COEF_PEREZ[6][1];
            f13 = COEF_PEREZ[6][2];
            f21 = COEF_PEREZ[6][3];
            f22 = COEF_PEREZ[6][4];
            f23 = COEF_PEREZ[6][5];
        } else if (epsilon >= 10.080) {
            f11 = COEF_PEREZ[7][0];
            f12 = COEF_PEREZ[7][1];
            f13 = COEF_PEREZ[7][2];
            f21 = COEF_PEREZ[7][3];
            f22 = COEF_PEREZ[7][4];
            f23 = COEF_PEREZ[7][5];
        }

        double f1 = f11 + f12 * delta + f13 * Math.acos(cos_theta_zs);
        double f2 = f21 + f22 * delta + f23 * Math.acos(cos_theta_zs);

        double factor_transmitancia = 1;//1 - 0.0663 * Math.pow(Math.acos(cos_theta_s), 2) + 0.0882 * Math.pow(Math.acos(cos_theta_s), 3) - 0.194 * Math.pow(Math.acos(cos_theta_s), 4);

        double dc_ab = d_0 * (f1 * (Math.max(cos_theta_s, 0) / cos_theta_zs)) * factor_transmitancia;
        dc_ab = Math.max(dc_ab, 0);

        double dh_ab = d_0 * (f2 * Math.sin(Math.toRadians(BETA))) * 1;//0.856;
        dh_ab = Math.max(dh_ab, 0);

        double dr_ab = d_0 * ((1 - f1) * (1 + Math.cos(Math.toRadians(BETA))) / 2) * 1;//0.856;
        dr_ab = Math.max(dr_ab, 0);

        double b_ab = b_n * Math.max(cos_theta_s, 0) * factor_transmitancia;

        double r_ab = g_0 * ALBEDO * ((1 - Math.cos(Math.toRadians(BETA))) / 2) * 1;//0.856;

        g_ab = (dc_ab + dh_ab + dr_ab) + b_ab + r_ab;

        porcentaje_radiacion_sombreada = (dc_ab + b_ab) / g_ab;
        if (java.lang.Double.isNaN(porcentaje_radiacion_sombreada)) {
            porcentaje_radiacion_sombreada = 0;
        }

        elevacion = Math.toDegrees(Math.asin(cos_theta_zs));
        acimut = Math.toDegrees(Math.acos((Math.sin(Math.toRadians(elevacion)) * Math.sin(Math.toRadians(LATITUD)) - Math.sin(Math.toRadians(declinacion)))
                / (Math.cos(Math.toRadians(elevacion)) * Math.cos(Math.toRadians(LATITUD)))));

        porcentaje_sombreado_vertical = PorcentajeSombreadoVertical(acimut, elevacion);
        porcentaje_sombreado_horizontal = PorcentajeSombreadoHorizontal(acimut, elevacion);
    }

    public static double PorcentajeSombreadoVertical(double acimut_sin_alfa, double elevacion) {
        double porcentaje;
        double acimut = acimut_sin_alfa - ALFA;

        porcentaje = 1 - (HP * Math.cos(Math.toRadians(acimut)))
                / (LM * (Math.sin(Math.toRadians(BETA)) * Math.cos(Math.toRadians(acimut)) + Math.tan(Math.toRadians(elevacion)) * Math.cos(Math.toRadians(BETA))));
        return porcentaje;
    }

    public static double PorcentajeSombreadoHorizontal(double acimut_sin_alfa, double elevacion) {
        double porcentaje;
        double acimut = acimut_sin_alfa - ALFA;

        porcentaje = 1 - (((HP * Math.cos(Math.toRadians(BETA))) * Math.sin(Math.toRadians(acimut)))
                / (AM * (Math.sin(Math.toRadians(BETA)) * Math.cos(Math.toRadians(acimut)) + Math.tan(Math.toRadians(elevacion)) * Math.cos(Math.toRadians(BETA)))));
        return porcentaje;
    }
}
