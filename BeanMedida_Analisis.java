/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.util.Date;

/**
 *
 * @author Ruben
 */
public class BeanMedida_Analisis {

    private Date hora;
    private Double vdc;
    private Double idc;
    private Double pdc;
    private Double vac;
    private Double iac;
    private Double pac;
    private Double irradiancia;
    private Double temp_ambiente;
    private Double temp_celula;
    private Double y_a;
    private Double y_f;
    private Double y_r;
    private Double y_rt;
    private Double pr;
    private Double prt;
    private Double eta_ct;
    private Double eta_ce;
    private Double eta_ei;

    public BeanMedida_Analisis() {
    }

    public BeanMedida_Analisis(Date hora, Double vdc, Double idc, Double pdc, Double vac, Double iac, Double pac, Double irradiancia, Double temp_ambiente, Double temp_celula, Double y_a, Double y_f, Double y_r, Double y_rt, Double pr, Double prt, Double eta_ct, Double eta_ce, Double eta_ei) {
        this.hora = hora;
        this.vdc = vdc;
        this.idc = idc;
        this.pdc = pdc;
        this.vac = vac;
        this.iac = iac;
        this.pac = pac;
        this.irradiancia = irradiancia;
        this.temp_ambiente = temp_ambiente;
        this.temp_celula = temp_celula;
        this.y_a = y_a;
        this.y_f = y_f;
        this.y_r = y_r;
        this.y_rt = y_rt;
        this.pr = pr;
        this.prt = prt;
        this.eta_ct = eta_ct;
        this.eta_ce = eta_ce;
        this.eta_ei = eta_ei;
    }

    public Double getEta_ce() {
        return eta_ce;
    }

    public void setEta_ce(Double eta_ce) {
        this.eta_ce = eta_ce;
    }

    public Double getEta_ct() {
        return eta_ct;
    }

    public void setEta_ct(Double eta_ct) {
        this.eta_ct = eta_ct;
    }

    public Double getEta_ei() {
        return eta_ei;
    }

    public void setEta_ei(Double eta_ei) {
        this.eta_ei = eta_ei;
    }

    public Double getPr() {
        return pr;
    }

    public void setPr(Double pr) {
        this.pr = pr;
    }

    public Double getPrt() {
        return prt;
    }

    public void setPrt(Double prt) {
        this.prt = prt;
    }

    public Double getY_f() {
        return y_f;
    }

    public void setY_f(Double y_f) {
        this.y_f = y_f;
    }

    public Double getY_r() {
        return y_r;
    }

    public void setY_r(Double y_r) {
        this.y_r = y_r;
    }

    public Double getY_rt() {
        return y_rt;
    }

    public void setY_rt(Double y_rt) {
        this.y_rt = y_rt;
    }

    public Double getY_a() {
        return y_a;
    }

    public void setY_a(Double y_a) {
        this.y_a = y_a;
    }

    public Double getIac() {
        return iac;
    }

    public void setIac(Double iac) {
        this.iac = iac;
    }

    public Double getIdc() {
        return idc;
    }

    public void setIdc(Double idc) {
        this.idc = idc;
    }

    public Double getIrradiancia() {
        return irradiancia;
    }

    public void setIrradiancia(Double irradiancia) {
        this.irradiancia = irradiancia;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Double getPac() {
        return pac;
    }

    public void setPac(Double pac) {
        this.pac = pac;
    }

    public Double getPdc() {
        return pdc;
    }

    public void setPdc(Double pdc) {
        this.pdc = pdc;
    }

    public Double getTemp_ambiente() {
        return temp_ambiente;
    }

    public void setTemp_ambiente(Double temp_ambiente) {
        this.temp_ambiente = temp_ambiente;
    }

    public Double getTemp_celula() {
        return temp_celula;
    }

    public void setTemp_celula(Double temp_celula) {
        this.temp_celula = temp_celula;
    }

    public Double getVac() {
        return vac;
    }

    public void setVac(Double vac) {
        this.vac = vac;
    }

    public Double getVdc() {
        return vdc;
    }

    public void setVdc(Double vdc) {
        this.vdc = vdc;
    }

}
