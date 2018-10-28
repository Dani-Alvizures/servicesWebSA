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
@WebService(serviceName = "crud_mesa")
public class crud_mesa {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "insert_mesa")
    public String insert_mesa(@WebParam(name = "noMesa") int noMesa, @WebParam(name = "codCentroVotacion") int codCentroVotacion) {
        String resultado = "";
        sistema_votacion insert = new sistema_votacion();
        try {
            resultado = insert.insert_mesa(noMesa, codCentroVotacion);
        } catch (Exception e) {
            resultado = e.getMessage();
        }
        return resultado;
    }
    
    @WebMethod(operationName = "update_mesa")
    public String update_mesa(@WebParam(name = "noMesa") int noMesa, @WebParam(name = "codCentroVotacion") int codCentroVotacion) {
        String resultado = "";
        sistema_votacion update = new sistema_votacion();
        try {
            resultado = update.update_mesa(noMesa, codCentroVotacion);
        } catch (Exception e) {
            resultado = e.getMessage();
        }
        return resultado;
    }
    
    @WebMethod(operationName = "delete_mesa")
    public String delete_mesa(@WebParam(name = "noMesa") int noMesa) {
        String resultado = "";
        sistema_votacion delete = new sistema_votacion();
        try {
            resultado = delete.delete_mesa(noMesa);
        } catch (Exception e) {
            resultado = e.getMessage();
        }
        return resultado;
    }
}
