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
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
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
                String lugar = consulta.src_consultaMesa(valor_dpi);
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
        sistema_votacion votar = new sistema_votacion();
        String respuesta = "";
        try {
            String[] votos = JsonVoto.split(";");
            for (int i = 0; i <= votos.length-1; i++) {
                String datos[] = votos[i].split(",");
                    //DPI
                    String dpi[] = datos[0].split(":");
                    String valor_dpi = dpi[1];
                    //partido
                    String partido[] = datos[1].split(":");
                    String valor_partido = partido[1];
                    //Limpiar valores
                    valor_dpi = valor_dpi.substring(1, valor_dpi.length()-1);                
                    valor_partido = valor_partido.substring(0, 1);
                    System.out.println("Dpi:" + valor_dpi + ", Partido:" + valor_partido);
                    
                if (i == votos.length-1) {
                    respuesta = respuesta + votar.src_emisionVoto(valor_dpi, Integer.parseInt(valor_partido));
                } else {
                    respuesta = respuesta + votar.src_emisionVoto(valor_dpi, Integer.parseInt(valor_partido)) + ",";
                }
            }
        } catch (Exception e) {
        }
        return respuesta;
    }
}
