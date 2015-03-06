/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

/**
 *
 * @author Ruben
 */
public class PosicionSolDia {

//    static final double[] d = {81, 172, 355, 0};
//    static final Matriz dias_caracteristicos = new Matriz(d);
//    static final Matriz declinacion = Matriz.seno(dias_caracteristicos.mas(284).por(2 * Math.PI / 365)).por(23.45).grado_Radian();
    static double[] d = {51, 81, 111, 172, 355, 0};
//    static final double[] d = {17, 46, 75, 105, 135, 161, 198, 228, 258, 289, 319, 345};
    static int PUNTOS_AÑO = d.length;
    double dia;
    double latitud;
    Matriz dias_caracteristicos;
    Matriz declinacion;
    Matriz psi_s = new Matriz(PUNTOS_AÑO, Constantes.PUNTOS_DIA);
    Matriz gamma_s = new Matriz(PUNTOS_AÑO, Constantes.PUNTOS_DIA);

    public PosicionSolDia(double latitud_grados, int dia_elegido) {
        dia = (double) dia_elegido;
        latitud = Math.toRadians(latitud_grados);

        dias_caracteristicos = new Matriz(d);
        dias_caracteristicos.set(PUNTOS_AÑO - 1, dia);

        declinacion = Matriz.seno(dias_caracteristicos.mas(284).por(2 * Math.PI / 365)).por(23.45).grado_Radian();

        Matriz Wrad = new Matriz(Constantes.PUNTOS_DIA);
        double Wh = 0;
        for (int hora = 0; hora < Constantes.PUNTOS_DIA; hora++) {
            Wh = hora - Constantes.PUNTOS_DIA / 2;
            Wrad.set(0, hora, Wh * 2 * Math.PI / Constantes.PUNTOS_DIA);
        }

        Matriz COSENO_theta_zs = new Matriz(PUNTOS_AÑO, Constantes.PUNTOS_DIA);
        Matriz theta_zs = new Matriz(PUNTOS_AÑO, Constantes.PUNTOS_DIA);
        Matriz COSENO_psi_s = new Matriz(PUNTOS_AÑO, Constantes.PUNTOS_DIA);

        for (int mes = 0; mes < PUNTOS_AÑO; mes++) {

//            System.out.println(COSENO_theta_zs+"mes: "+mes+"as: "+(Matriz.coseno(Wrad).por(Math.cos(declinacion.get(mes)) * Math.cos(latitud))).mas(Math.sin(declinacion.get(mes)) * Math.sin(latitud)));
            COSENO_theta_zs.setDia(mes, (Matriz.coseno(Wrad).por(Math.cos(declinacion.get(mes)) * Math.cos(latitud))).mas(Math.sin(declinacion.get(mes)) * Math.sin(latitud)));

            theta_zs.setDia(mes, Matriz.acoseno(COSENO_theta_zs.getDia(mes)));

            gamma_s.setDia(mes, theta_zs.radian_Grado().getDia(mes).menos(90).por(-1));

            COSENO_psi_s.setDia(mes, COSENO_theta_zs.getDia(mes).por(Math.sin(latitud)).menos(Math.sin(declinacion.get(mes))).divide(Matriz.seno(theta_zs.getDia(mes)).por(Math.cos(latitud))).por(Math.signum(latitud)));
//            System.out.println("COSENO acimuth: "+COSENO_psi_s);

            for (int hora = 0; hora < Constantes.PUNTOS_DIA; hora++) {
                if (hora < Constantes.PUNTOS_DIA / 2) {
                    psi_s.set(mes, hora, Math.toDegrees(Math.acos(Redondea(COSENO_psi_s.get(mes, hora)))) * -1);
//                    System.out.println("Mes y hora " + mes + " " + hora + ":" + Redondea(COSENO_psi_s.get(mes, hora)));
                } else {
                    psi_s.set(mes, hora, Math.toDegrees(Math.acos(Redondea(COSENO_psi_s.get(mes, hora)))));
                }
            }
        }
//        System.out.println("Acimuth: " + psi_s.toString());
    }

    public double Redondea(double numero) {
        return Math.rint(numero * 1000) / 1000;
    }

    public static double MomentoPosicion(double acimut, double altura, int dia_elegido) {
//        latitud = Math.toRadians(latitud_grados);
        double COSENO_Ws;
        double Wh;
        double declinacion_dia;

        declinacion_dia = Math.toRadians(23.45 * Math.sin(2 * Math.PI / 365 * (dia_elegido + 284)));

        if (Math.signum(acimut) == -1) {
            COSENO_Ws = -1 * (Math.sin(Math.toRadians(altura)) - (Math.sin(declinacion_dia) * Math.sin(Constantes.LATITUD))) / (Math.cos(declinacion_dia) * Math.cos(Constantes.LATITUD));
            Wh = Math.acos(COSENO_Ws) * Constantes.PUNTOS_DIA / (2 * Math.PI);
        } else {
            COSENO_Ws = (Math.sin(Math.toRadians(altura)) - (Math.sin(declinacion_dia) * Math.sin(Constantes.LATITUD))) / (Math.cos(declinacion_dia) * Math.cos(Constantes.LATITUD));
            Wh = Math.acos(COSENO_Ws) * Constantes.PUNTOS_DIA / (2 * Math.PI) + 12;
        }
//        System.out.println("Wh " + Math.toDegrees(Math.acos(COSENO_Ws)));
//        Wh = Math.acos(COSENO_Ws) * Constantes.PUNTOS_DIA / (2 * Math.PI);

        return Wh;
    }
}
