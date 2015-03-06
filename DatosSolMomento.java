/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

/**
 *
 * @author Ruben
 */
public class DatosSolMomento {

    public static int NUM_DIAS = Constantes.PUNTOS_AÑO;
    public static int MOM_HORA = 10;
    public static int MOM_DIA = Constantes.PUNTOS_DIA * MOM_HORA;
    public Matriz acimut = new Matriz(NUM_DIAS, MOM_DIA);
    public Matriz elevacion = new Matriz(NUM_DIAS, MOM_DIA);
    public Matriz B_ab = new Matriz(NUM_DIAS, MOM_DIA);
    public Matriz R_ab = new Matriz(NUM_DIAS, MOM_DIA);
    public Matriz Dc_ab = new Matriz(NUM_DIAS, MOM_DIA);
    public Matriz Di_ab = new Matriz(NUM_DIAS, MOM_DIA);
    public Matriz G_ab = new Matriz(NUM_DIAS, MOM_DIA);
    public double radiacion_anual_horizontal = 0;

    public DatosSolMomento(String modo) {
        double latitud = Math.toRadians(Constantes.LATITUD);
        double alfa = Math.toRadians(Constantes.ALFA);
        double beta = Math.toRadians(Constantes.BETA);

        Matriz dia_Gdm0 = new Matriz(NUM_DIAS);
        Matriz Gdm0 = new Matriz(NUM_DIAS);
        double radiacion_dia;

        for (int i = 0; i < NUM_DIAS; i++) {
            dia_Gdm0.set(i, i + 1);
            if (modo.equals("Aguiar")) {
                radiacion_dia = (Constantes.ANYO_SERIE[0][i] + Constantes.ANYO_SERIE[1][i] + Constantes.ANYO_SERIE[2][i] + Constantes.ANYO_SERIE[3][i] + Constantes.ANYO_SERIE[4][i] + Constantes.ANYO_SERIE[5][i] + Constantes.ANYO_SERIE[6][i] + Constantes.ANYO_SERIE[7][i] + Constantes.ANYO_SERIE[8][i] + Constantes.ANYO_SERIE[9][i]) / 10;
            } else {
                radiacion_dia = InterpolaRadiacion(i + 1);
            }
            Gdm0.set(i, radiacion_dia);
            radiacion_anual_horizontal += radiacion_dia;
        }
        System.out.println("Radiacion media diaria anual Gdm0: " + radiacion_anual_horizontal / NUM_DIAS);

        Matriz declinacion = Matriz.seno(dia_Gdm0.mas(284).por(2 * Math.PI / 365)).por(23.45).grado_Radian();

        Matriz excentricidad = Matriz.coseno(dia_Gdm0.por(2 * Math.PI / 365)).por(0.033).mas(1);

        Matriz Ws = Matriz.acoseno(Matriz.tangente(declinacion).por(-1).por(Math.tan(latitud))).por(-1);

        Matriz BOdm0 = excentricidad.por(Constantes.BO * 24 / Math.PI).por(Math.cos(latitud)).por(Matriz.coseno(declinacion)).por(Matriz.coseno(Ws).por(Ws).menos(Matriz.seno(Ws)));

        Matriz Ktm = new Matriz(NUM_DIAS);
        for (int dia = 0; dia < NUM_DIAS; dia++) {
            if (BOdm0.get(dia) == 0) {
                Ktm.set(dia, 0);
            } else {
                Ktm.set(dia, Gdm0.get(dia) / BOdm0.get(dia));
            }
        }

        Matriz Kdm = Ktm.por(1.13).menos(1).por(-1);

        Matriz Ddm0 = Gdm0.por(Kdm);
        Matriz Bdm0 = Gdm0.menos(Ddm0);

        Matriz Wrad = new Matriz(MOM_DIA);
        double Wh = 0;
        for (int hora = 0; hora < MOM_DIA; hora++) {
            Wh = hora - MOM_DIA / 2;
            Wrad.set(hora, Wh * Math.PI / (MOM_DIA / 2));
        }

        Matriz rd = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz rg = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz D0 = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz G0 = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz B0 = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz a = new Matriz(NUM_DIAS);
        Matriz b = new Matriz(NUM_DIAS);
        Matriz COSENO_theta_zs = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz theta_zs = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz COSENO_psi_s = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz COSENO_tetha_s = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz k2 = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz D_ab = new Matriz(NUM_DIAS, MOM_DIA);
        Matriz Bdm_ab = new Matriz(NUM_DIAS);
        Matriz Ddm_ab = new Matriz(NUM_DIAS);
        Matriz Rdm_ab = new Matriz(NUM_DIAS);
        Matriz Gdm_ab = new Matriz(NUM_DIAS);

        for (int dia = 0; dia < NUM_DIAS; dia++) {
            rd.setDia(dia, Matriz.coseno(Wrad).menos(Math.cos(Ws.get(dia))).por(Math.PI / 24).divide(Ws.get(dia) * Math.cos(Ws.get(dia)) - Math.sin(Ws.get(dia))));

            for (int hora = 0; hora < MOM_DIA; hora++) {
                if (rd.get(dia, hora) * Ddm0.get(dia) > 0) {
                    D0.set(dia, hora, rd.get(dia, hora) * Ddm0.get(dia));
                } else {
                    D0.set(dia, hora, 0);
                }
            }

            a.set(dia, 0.409 - 0.5016 * Math.sin(Ws.get(dia) + Math.PI / 3));
            b.set(dia, 0.6609 + 0.4767 * Math.sin(Ws.get(dia) + Math.PI / 3));

            rg.setDia(dia, rd.getDia(dia).por(Matriz.coseno(Wrad).por(b.get(dia)).mas(a.get(dia))));

            for (int hora = 0; hora < MOM_DIA; hora++) {
                if (rd.get(dia, hora) * Gdm0.get(dia) > 0) {
                    G0.set(dia, hora, rg.get(dia, hora) * Gdm0.get(dia));
                } else {
                    G0.set(dia, hora, 0);
                }
            }

            B0.setDia(dia, G0.getDia(dia).menos(D0.getDia(dia)));

            COSENO_theta_zs.setDia(dia, (Matriz.coseno(Wrad).por(Math.cos(declinacion.get(dia)) * Math.cos(latitud))).mas(Math.sin(declinacion.get(dia)) * Math.sin(latitud)));

            theta_zs.setDia(dia, Matriz.acoseno(COSENO_theta_zs.getDia(dia)));

            elevacion.setDia(dia, theta_zs.radian_Grado().getDia(dia).menos(90).por(-1));

            COSENO_psi_s.setDia(dia, COSENO_theta_zs.getDia(dia).por(Math.sin(latitud)).menos(Math.sin(declinacion.get(dia))).divide(Matriz.seno(theta_zs.getDia(dia)).por(Math.cos(latitud))).por(Math.signum(latitud)));
//            System.out.println("COSENO acimuth: "+COSENO_psi_s);

            for (int hora = 0; hora < MOM_DIA; hora++) {
                if (hora < MOM_DIA / 2) {
                    acimut.set(dia, hora, Math.toDegrees(Math.acos(Redondea(COSENO_psi_s.get(dia, hora)))) * -1);
//                    System.out.println("Dia y hora " + dia + " " + hora + ":" + Math.toDegrees(Math.acos(Redondea(COSENO_psi_s.get(dia, hora)))));
                } else {
                    acimut.set(dia, hora, Math.toDegrees(Math.acos(Redondea(COSENO_psi_s.get(dia, hora)))));
                }
            }

            COSENO_tetha_s.setDia(dia, Matriz.coseno(Wrad).por(Math.cos(declinacion.get(dia)) * Math.cos(latitud) * Math.cos(beta)).mas(Math.sin(declinacion.get(dia)) * Math.sin(latitud) * Math.cos(beta)
                    - Math.sin(declinacion.get(dia)) * Math.cos(latitud) * Math.sin(beta) * Math.cos(alfa)).mas(Matriz.coseno(Wrad).por(Math.cos(declinacion.get(dia)) * Math.sin(latitud) * Math.sin(beta) * Math.cos(alfa)).mas(Matriz.seno(Wrad).por(Math.cos(declinacion.get(dia)) * Math.sin(alfa) * Math.sin(beta)))));

            for (int hora = 0; hora < MOM_DIA; hora++) {
                if (COSENO_tetha_s.get(dia, hora) > 0) {
                    B_ab.set(dia, hora, B0.get(dia, hora) * COSENO_tetha_s.get(dia, hora) / COSENO_theta_zs.get(dia, hora));
                } else {
                    B_ab.set(dia, hora, 0);
                }
            }

            k2.setDia(dia, (G0.getDia(dia).menos(D0.getDia(dia))).divide(COSENO_theta_zs.getDia(dia).por(Constantes.BO * excentricidad.get(dia))));

            for (int hora = 0; hora < MOM_DIA; hora++) {
                if (COSENO_tetha_s.get(dia, hora) > 0) {//TODO generar difusa directamenre de valores horarios reales
                    Dc_ab.set(dia, hora, k2.get(dia, hora) * D0.get(dia, hora) * COSENO_tetha_s.get(dia, hora) / COSENO_theta_zs.get(dia, hora));
                } else {
                    Dc_ab.set(dia, hora, 0);
                }
            }

            Di_ab.setDia(dia, D0.getDia(dia).por(k2.getDia(dia).menos(1).por(-1)).por((Math.cos(beta) + 1) / 2));

            D_ab.setDia(dia, Dc_ab.getDia(dia).mas(Di_ab.getDia(dia)));

            R_ab.setDia(dia, G0.getDia(dia).por(Constantes.ALBEDO).por((1 - Math.cos(beta)) / 2));

            G_ab.setDia(dia, B_ab.getDia(dia).mas(D_ab.getDia(dia)).mas(R_ab.getDia(dia)));

            ///////////////////

            Bdm_ab.set(dia, B_ab.sumaHoras(dia));
            Ddm_ab.set(dia, D_ab.sumaHoras(dia));
            Rdm_ab.set(dia, R_ab.sumaHoras(dia));
            Gdm_ab.set(dia, G_ab.sumaHoras(dia));

        }
        double total_anual = 0;
        for (int dia = 0; dia < NUM_DIAS; dia++) {
            total_anual += Gdm_ab.get(dia) / MOM_HORA;
        }
        System.out.println("Valor Gdm_ab: " + total_anual / NUM_DIAS);
    }

    public double Redondea(double numero) {
        return Math.rint(numero * 1000) / 1000;
    }

    public static double InterpolaRadiacion(int dia) {
        double[] dias_interpolacion = new double[14];
        double[] radiacion_interpolacion = new double[14];
        double radiacion_dia1 = ((Main.radiacion[11]) + (Main.radiacion[0] - Main.radiacion[11]) * (365 - 345) / (17 + (365 - 345))) * 1000;

        dias_interpolacion[0] = 1;
        radiacion_interpolacion[0] = radiacion_dia1;
        dias_interpolacion[13] = 365;
        radiacion_interpolacion[13] = radiacion_dia1;

        for (int i = 0; i < Constantes.DIAS_RADIACION.length; i++) {
            dias_interpolacion[i + 1] = Constantes.DIAS_RADIACION[i];
            radiacion_interpolacion[i + 1] = Main.radiacion[i] * 1000;
        }

        double interpolado = radiacion_interpolacion[0];
        int i_previo = 0;
        int i = 0;
        while (dias_interpolacion[i] < dia) {
            i++;
            interpolado = radiacion_interpolacion[i_previo] + ((radiacion_interpolacion[i] - radiacion_interpolacion[i_previo]) / (dias_interpolacion[i] - dias_interpolacion[i_previo])) * (dia - dias_interpolacion[i_previo]);
            i_previo = i;
        }
        return interpolado;
    }
}
