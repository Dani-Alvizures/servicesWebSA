/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection.sevices;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import servicios_src.sistema_votacion;

/**
 *
 * @author kevin
 */
@WebService(serviceName = "reportes")
public class reportes {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "reporteDepartamento")
    public String reporteDepartamento(@WebParam(name = "codDepartamento") String codDepartamento) {        
        sistema_votacion repDepartamento = new sistema_votacion();
        String result = "";
        try {
            result = repDepartamento.generar_reporte_Departamento(codDepartamento);
        } catch (Exception e) {
            result = repDepartamento.excepcion_no_controlada(e.getMessage());
        }
        return result;
    }
    
    @WebMethod(operationName = "reporteMunicipio")
    public String reporteMunicipio(@WebParam(name = "codMunicipio") String codMunicipio) {
        sistema_votacion repMunicipio = new sistema_votacion();
        String result = "";
        try {
            result = repMunicipio.generar_reporte_Municipio(codMunicipio);
        } catch (Exception e) {
            result = repMunicipio.excepcion_no_controlada(e.getMessage());
        }
        return result;
    }
    
    @WebMethod(operationName = "reporteEdad")
    public String reporteEdad(@WebParam(name = "codDepartamento") String codDepartamento) {
        sistema_votacion repEdad = new sistema_votacion();
        String result = "";
        try {
            result = repEdad.generar_reporte_edad(codDepartamento);
        } catch (Exception e) {
            result = repEdad.excepcion_no_controlada(e.getMessage());
        }
        return result;
    }
    
    @WebMethod(operationName = "reporteGenero")
    public String reporteGenero(@WebParam(name = "codDepartamento") String codDepartamento) {
        sistema_votacion repGenero = new sistema_votacion();
        String result = "";
        try {
            result = repGenero.generar_reporte_genero(codDepartamento);
        } catch (Exception e) {
            result = repGenero.excepcion_no_controlada(e.getMessage());
        }
        return result;
    }
    
    @WebMethod(operationName = "reporteDuplicados")
    public String reporteDuplicados() {
        sistema_votacion repDuplicados = new sistema_votacion();
        String result = "";
        try {
            result = repDuplicados.reporte_duplicados();
        } catch (Exception e) {
            result = repDuplicados.excepcion_no_controlada(e.getMessage());
        }
        return result;
    }
}
