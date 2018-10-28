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
@WebService(serviceName = "crud_persona")
public class crud_persona {
    //String dpi, String nombre, String apellido, String sexo, int departamento, int municipio
    @WebMethod(operationName = "insert_persona")
    public String insert_persona(@WebParam(name = "dpi") String dpi, @WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, @WebParam(name = "sexo") String sexo, @WebParam(name = "departamento") int departamento, @WebParam(name = "municipio") int municipio){
        String resultado = "";
        sistema_votacion insert = new sistema_votacion();
        try {
            resultado = insert.insert_persona(dpi, nombre, apellido, sexo, departamento, municipio);
        } catch (Exception e) {
            resultado = e.getMessage();
        }
        return resultado;
    }
    
    @WebMethod(operationName = "update_persona")
    public String update_persona(@WebParam(name = "dpi") String dpi, @WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, @WebParam(name = "sexo") String sexo, @WebParam(name = "departamento") int departamento, @WebParam(name = "municipio") int municipio){
        String resultado = "";
        sistema_votacion update = new sistema_votacion();
        try {
            resultado = update.update_persona(dpi, nombre, apellido, sexo, departamento, municipio);
        } catch (Exception e) {
            resultado = e.getMessage();
        }
        return resultado;
    }
    
    @WebMethod(operationName = "delete_persona")
    public String delete_persona(@WebParam(name = "dpi") String dpi){
        String resultado = "";
        sistema_votacion delete = new sistema_votacion();
        try {
            resultado = delete.delete_persona(dpi);
        } catch (Exception e) {
            resultado = e.getMessage();
        }
        return resultado;
    }
}

