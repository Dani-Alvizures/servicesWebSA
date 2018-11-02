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
@WebService(serviceName = "asignarMesa")
public class asignarMesa {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "asignarMesa")
    public String asignarMesa(@WebParam(name = "dpi") String dpi, @WebParam(name = "codMunicipio") int codMunicipio, @WebParam(name = "noMesa") int noMesa) {
        //int noLinea, String dpi, int noMesa
        String resultado = "";
        sistema_votacion asignar = new sistema_votacion();
        try {
            resultado = asignar.asignarMesa(dpi, codMunicipio, noMesa);
        } catch (Exception e) {
            resultado = asignar.excepcion_no_controlada(e.getMessage());
        }
        return resultado ;
    }
    
    @WebMethod(operationName = "eliminarAsignacion")
    public String eliminarAsignacion (@WebParam(name = "dpi") String dpi) {
        sistema_votacion eliminarAsignacion = new sistema_votacion();
        String resultado = "";
        try {
            resultado = eliminarAsignacion.eliminarAsignacionMesa(dpi);
        } catch (Exception e) {
            resultado = eliminarAsignacion.excepcion_no_controlada(e.getMessage());
        }
        return resultado;
    }
}
