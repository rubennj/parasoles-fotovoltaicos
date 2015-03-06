/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

/**
 *
 * @author Ruben
 */
public class RadiacionAnual {

    static final double[] d = {17, 46, 75, 105, 135, 161, 198, 228, 258, 289, 319, 345};
    static final Matriz dia = new Matriz(d);
    static final Matriz declinacion = Matriz.seno(dia.mas(284).por(2 * Math.PI / 365)).por(23.45).grado_Radian();
    
    static final int MESES = d.length;

    public static Matriz Genera(double latitud_grados, double beta_grados, double alfa_grados, Matriz Gdm0_kW, double albedo) {

        double latitud = Math.toRadians(latitud_grados);
        double alfa = Math.toRadians(alfa_grados);
        double beta = Math.toRadians(beta_grados);
        Matriz Gdm0 = Gdm0_kW.por(1000);

        Matriz excentricidad = Matriz.coseno(dia.por(2 * Math.PI / 365)).por(0.033).mas(1);

        Matriz Ws = Matriz.acoseno(Matriz.tangente(declinacion).por(-1).por(Math.tan(latitud))).por(-1);

        Matriz BOdm0 = excentricidad.por(Constantes.BO * Constantes.PUNTOS_DIA / Math.PI).por(Math.cos(latitud)).por(Matriz.coseno(declinacion)).por(Matriz.coseno(Ws).por(Ws).menos(Matriz.seno(Ws)));

        Matriz Ktm = new Matriz(1, MESES);
        for (int mes = 0; mes < MESES; mes++) {
            if (BOdm0.get(mes) == 0) {
                Ktm.set(mes, 0);
            } else {
                Ktm.set(mes, Gdm0.get(mes) / BOdm0.get(mes));
            }
        }

        Matriz Kdm = Ktm.por(1.13).menos(1).por(-1);

        Matriz Ddm0 = Gdm0.por(Kdm);
        Matriz Bdm0 = Gdm0.menos(Ddm0);

        Matriz Wrad = new Matriz(1, Constantes.PUNTOS_DIA);
        double Wh = 0;
        for (int hora = 0; hora < Constantes.PUNTOS_DIA; hora++) {
            Wh = hora - Constantes.PUNTOS_DIA / 2;
            Wrad.set(0, hora, Wh * Math.PI / 12);
        }

        Matriz rd = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz rg = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz D0 = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz G0 = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz B0 = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz a = new Matriz(MESES);
        Matriz b = new Matriz(MESES);
        Matriz COSENO_theta_zs = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz theta_zs = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz COSENO_tetha_s = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz B_ab = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz k2 = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz Dc_ab = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz Di_ab = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz D_ab = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz R_ab = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz G_ab = new Matriz(MESES, Constantes.PUNTOS_DIA);
        Matriz Bdm_ab = new Matriz(MESES);
        Matriz Ddm_ab = new Matriz(MESES);
        Matriz Rdm_ab = new Matriz(MESES);
        Matriz Gdm_ab = new Matriz(MESES);
        Matriz G2 = new Matriz(MESES);

        for (int mes = 0; mes < MESES; mes++) {
            rd.setDia(mes, Matriz.coseno(Wrad).menos(Math.cos(Ws.get(mes))).por(Math.PI / 24).divide(Ws.get(mes) * Math.cos(Ws.get(mes)) - Math.sin(Ws.get(mes))));

            for (int hora = 0; hora < Constantes.PUNTOS_DIA; hora++) {
                if (rd.get(mes, hora) * Ddm0.get(mes) > 0) {
                    D0.set(mes, hora, rd.get(mes, hora) * Ddm0.get(mes));
                } else {
                    D0.set(mes, hora, 0);
                }
            }

            a.set(mes, 0.409 - 0.5016 * Math.sin(Ws.get(mes) + Math.PI / 3));
            b.set(mes, 0.6609 + 0.4767 * Math.sin(Ws.get(mes) + Math.PI / 3));

            rg.setDia(mes, rd.getDia(mes).por(Matriz.coseno(Wrad).por(b.get(mes)).mas(a.get(mes))));

            for (int hora = 0; hora < Constantes.PUNTOS_DIA; hora++) {
                if (rd.get(mes, hora) * Gdm0.get(mes) > 0) {
                    G0.set(mes, hora, rg.get(mes, hora) * Gdm0.get(mes));
                } else {
                    G0.set(mes, hora, 0);
                }
            }

            B0.setDia(mes, G0.getDia(mes).menos(D0.getDia(mes)));

            COSENO_theta_zs.setDia(mes, (Matriz.coseno(Wrad).por(Math.cos(declinacion.get(mes)) * Math.cos(latitud))).mas(Math.sin(declinacion.get(mes)) * Math.sin(latitud)));

            theta_zs.setDia(mes, Matriz.acoseno(COSENO_theta_zs.getDia(mes)));

            COSENO_tetha_s.setDia(mes, Matriz.coseno(Wrad).por(Math.cos(declinacion.get(mes)) * Math.cos(latitud) * Math.cos(beta)).mas(Math.sin(declinacion.get(mes)) * Math.sin(latitud) * Math.cos(beta)
                    - Math.sin(declinacion.get(mes)) * Math.cos(latitud) * Math.sin(beta) * Math.cos(alfa)).mas(Matriz.coseno(Wrad).por(Math.cos(declinacion.get(mes)) * Math.sin(latitud) * Math.sin(beta) * Math.cos(alfa)).mas(Matriz.seno(Wrad).por(Math.cos(declinacion.get(mes)) * Math.sin(alfa) * Math.sin(beta)))));

            for (int hora = 0; hora < Constantes.PUNTOS_DIA; hora++) {
                if (COSENO_tetha_s.get(mes, hora) > 0) {
                    B_ab.set(mes, hora, B0.get(mes, hora) * COSENO_tetha_s.get(mes, hora) / COSENO_theta_zs.get(mes, hora));
                } else {
                    B_ab.set(mes, hora, 0);
                }
            }

            k2.setDia(mes, (G0.getDia(mes).menos(D0.getDia(mes))).divide(COSENO_theta_zs.getDia(mes).por(Constantes.BO * excentricidad.get(mes))));

            for (int hora = 0; hora < Constantes.PUNTOS_DIA; hora++) {
                if (COSENO_tetha_s.get(mes, hora) > 0) {//TODO generar difusa directamenre de valores horarios reales
                    Dc_ab.set(mes, hora, k2.get(mes, hora) * D0.get(mes, hora) * COSENO_tetha_s.get(mes, hora) / COSENO_theta_zs.get(mes, hora));
                } else {
                    Dc_ab.set(mes, hora, 0);
                }
            }

            Di_ab.setDia(mes, D0.getDia(mes).por(k2.getDia(mes).menos(1).por(-1)).por((Math.cos(beta) + 1) / 2));

            D_ab.setDia(mes, Dc_ab.getDia(mes).mas(Di_ab.getDia(mes)));

            R_ab.setDia(mes, G0.getDia(mes).por(albedo).por((1 - Math.cos(beta)) / 2));

            G_ab.setDia(mes, B_ab.getDia(mes).mas(D_ab.getDia(mes)).mas(R_ab.getDia(mes)));

            ///////////////////

            Bdm_ab.set(mes, B_ab.sumaHoras(mes));
            Ddm_ab.set(mes, D_ab.sumaHoras(mes));
            Rdm_ab.set(mes, R_ab.sumaHoras(mes));
            Gdm_ab.set(mes, G_ab.sumaHoras(mes));

        }

//        System.out.println("Valor: " + G_ab.toString());

        return G_ab;

    }
}
