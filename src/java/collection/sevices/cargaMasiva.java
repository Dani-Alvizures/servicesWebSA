/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection.sevices;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import jdk.nashorn.internal.parser.JSONParser;
import servicios_src.sistema_votacion;


/**
 *
 * @author kevin
 */
@WebService(serviceName = "cargaMasiva")
public class cargaMasiva {
    @WebMethod(operationName = "hola")
    public String hola(@WebParam(name = "nombre") String nombre) {        
        String respuesta = "Hola " + nombre;
        return respuesta;
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "cargaConsultas")
    public String cargaConsultas(@WebParam(name = "JsonConsulta") String JsonConsulta) {
        sistema_votacion consulta = new sistema_votacion();
        String respuesta = "";
        try {
            String[] consultas = JsonConsulta.split(";");
            for (int i = 0; i <= consultas.length-1; i++) {
                String encabezado = "";
                String dpis[] = consultas[i].split(":");
                //Limpiar valor
                String valor_dpi = dpis[1].substring(1, dpis[1].length()-2);
                System.out.println("Dpi:" + valor_dpi);
                String lugar = consulta.consultar_mesa(valor_dpi);
                if (!(lugar.equals(""))) {
                    encabezado = "{";
                    encabezado = encabezado + "\t" + "\"mensaje:Consulta de datos\",\n";
                    encabezado = encabezado + "\t" + "esError:false,\n";
                    encabezado = encabezado + "\t" + "obj:\n";
                    encabezado = encabezado + "{\n";
                    encabezado = encabezado + lugar + "\n";
                    encabezado = encabezado + "}";
                    if (i == consultas.length -1) {
                        encabezado = encabezado + "}";
                    } else {
                        encabezado = encabezado + "},";
                    }                   
                } else {
                    encabezado = "{";
                    encabezado = encabezado + "\t" + "\"mensaje:Consulta de datos\",\n";
                    if (i == consultas.length -1) {
                        encabezado = encabezado + "\t" + "esError:false\n";
                    } else {
                        encabezado = encabezado + "\t" + "esError:false,\n";
                    }                    
                    encabezado = "}";
                }
                respuesta = respuesta + encabezado;
            }            
        } catch (Exception e) {
        }
        return respuesta;
    }
    
    @WebMethod(operationName = "cargaVotos")
    public String cargaVotos(@WebParam(name = "JsonVoto") String JsonVoto) {
        String result = "";
        sistema_votacion cargaVotos = new sistema_votacion();
        try {
            result = cargaVotos.cargaVotos(JsonVoto);
        } catch (Exception e) {
            result = cargaVotos.excepcion_no_controlada(e.getMessage());
        }
        return result;
    }
    
    @WebMethod(operationName = "cargaPersonas")
    public String cargaPersonas(@WebParam(name = "dataPersonas") String dataPersonas){
        String result = "";
        sistema_votacion cargaPersonas = new sistema_votacion();
        try {
            result = cargaPersonas.cargaPersonas(dataPersonas);
        } catch (Exception e) {
            result = cargaPersonas.excepcion_no_controlada(e.getMessage());
        }
        return result;
    }
    
    @WebMethod(operationName = "cargaMesas")
    public String cargaMesas(@WebParam(name = "dataMesas") String dataMesas){
        String result = "";
        sistema_votacion cargaPersonas = new sistema_votacion();
        try {
            result = cargaPersonas.cargaMesas(dataMesas);
        } catch (Exception e) {
            result = cargaPersonas.excepcion_no_controlada(e.getMessage());
        }
        return result;
    }
}
