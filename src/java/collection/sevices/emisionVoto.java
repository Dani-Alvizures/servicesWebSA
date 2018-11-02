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
@WebService(serviceName = "emisionVoto")
public class emisionVoto {

    /**
     * Servicio para la emision de votos
     */
    @WebMethod(operationName = "emisionVoto")
    public String  emisionVoto(@WebParam(name = "dpi") String dpi, @WebParam(name = "codPartido") int codPartido){
        sistema_votacion emitirVoto = new sistema_votacion();
        String resultado = "";
        try {
            resultado = emitirVoto.emitir_voto(dpi, codPartido);
        } catch (Exception e) {
            resultado = e.getMessage();
        }
        return resultado;
    }
}
