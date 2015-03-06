/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ruben
 */
public class InformeDiaParasol {

    public static void GeneraInforme(ArrayList<MedidaParasol> valores_hora, String dia) {

        String reportName = "informe " + dia;
        JRBeanCollectionDataSource dataSource;
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        Map parametros = new HashMap();
        parametros.put("LOGO", "logo.jpg");
        parametros.put("P_TITULO", "Analisis horario de irradiacion y rendimiento");
        parametros.put("P_SUBTITULO", dia);
        try {
            //1-Llenar el datasource con la informacion de la base de datos o de donde este, en este caso "hardcode"
            Collection lista = CargaDatos(valores_hora);
            dataSource = new JRBeanCollectionDataSource(lista);

            //2-Compilamos el archivo XML y lo cargamos en memoria
            jasperReport = JasperCompileManager.compileReport("plantilla.jrxml");

            //3-Llenamos el reporte con la informaci�n (de la DB) y par�metros necesarios para la consulta
            jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parametros, dataSource);

            //4-Exportamos el reporte a pdf y lo guardamos en disco
            JasperExportManager.exportReportToPdfFile(
                    jasperPrint, reportName + ".pdf");

            System.out.println("Hecho!");

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private static Collection CargaDatos(ArrayList<MedidaParasol> valores_hora) {
        Collection lista = new ArrayList();
        for (int hora = 0; hora < valores_hora.size(); hora++) {
            lista.add(new BeanMedida_Analisis(new Date(2000, 1, 1, valores_hora.get(hora).hora, 0),
                    new Double(valores_hora.get(hora).vdc),
                    new Double(valores_hora.get(hora).idc),
                    new Double(valores_hora.get(hora).pdc),
                    new Double(valores_hora.get(hora).vac),
                    new Double(valores_hora.get(hora).iac),
                    new Double(valores_hora.get(hora).pac),
                    new Double(valores_hora.get(hora).irradiancia),
                    new Double(valores_hora.get(hora).temp_ambiente),
                    new Double(valores_hora.get(hora).analisis.temp_celula),
                    new Double(valores_hora.get(hora).analisis.Ya),
                    new Double(valores_hora.get(hora).analisis.Yf),
                    new Double(valores_hora.get(hora).analisis.Yr),
                    new Double(valores_hora.get(hora).analisis.Yrt),
                    new Double(valores_hora.get(hora).analisis.PR),
                    new Double(valores_hora.get(hora).analisis.PRt),
                    new Double(valores_hora.get(hora).analisis.Eta_ct),
                    new Double(valores_hora.get(hora).analisis.Eta_ce),
                    new Double(valores_hora.get(hora).analisis.Eta_ei)));
        }
        return lista;
    }
}
