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
@WebService(serviceName = "servicios")
public class servicios {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hola " + txt + " !";
    }
    
    /**
     * Servicio para la emision de votos
     */
    @WebMethod(operationName = "emisionVoto")
    public boolean  emisionVoto(@WebParam(name = "dpi") String dpi, @WebParam(name = "codPartido") int codPartido){
        sistema_votacion emitirVoto = new sistema_votacion();
        //return emitirVoto.src_emisionVoto(dpi, codPartido);
        return false;
    }
    
    /**
     * Servicio para la consulta de mesa de votacion
     */
    @WebMethod(operationName = "consultaMesa")
    public String consultaMesa(@WebParam(name = "dpi") String dpi){
        
        sistema_votacion consultarMesa = new sistema_votacion();
        String result = "";
        try {
            result = consultarMesa.src_consultaMesa(dpi);
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }
}
