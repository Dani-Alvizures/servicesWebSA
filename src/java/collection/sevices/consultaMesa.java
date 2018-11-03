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
@WebService(serviceName = "consultaMesa")
public class consultaMesa {

    /**
     * Servicio para la consulta de mesa de votacion
     */
    @WebMethod(operationName = "consultaMesa")
    public String consultaMesa(@WebParam(name = "dpi") String dpi){
        
        sistema_votacion consultarMesa = new sistema_votacion();
        String result = "";
        try {
            result = consultarMesa.consultar_mesa(dpi);
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }
    
    @WebMethod(operationName = "consultarMesa")
    public String consultarMesa(@WebParam(name = "data") String data){
        
        sistema_votacion consultarMesa = new sistema_votacion();
        String result = "";
        try {
            result = consultarMesa.consultar(data);
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }
}
