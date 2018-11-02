/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios_src;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kevin
 */
public class sistema_votacion {

    public sistema_votacion() {
    }
    
    /**
     * Consultar Mesa
     */
    public String consultar_mesa(String padron) throws Exception{
        String result = "";
        try {
            String querry = "SELECT"
                            + " dep.codDepartamento as \"codDepartamento\","
                            + " mun.codMunicipio as \"codMunicipio\","
                            + " cent.codCentro as \"codCentroVotacion\","
                            + " cent.direccion,"
                            + " mesa.noMesa as \"numMesa\","
                            + " lin.noLinea as \"numLinea\""
                            + " FROM"
                            + " departamento dep,"
                            + " municipio mun,"
                            + " centro_votacion cent,"
                            + " mesa_votacion mesa,"
                            + " linea_mesa lin"
                            + " WHERE"
                            + " mun.codDepartamento = dep.codDepartamento AND"
                            + " cent.codMunicipio = mun.codMunicipio AND"
                            + " mesa.codCentro = cent.codCentro AND"
                            + " lin.noMesa = mesa.noMesa AND"
                            + " lin.dpi = '" + padron+"'";
            PreparedStatement consulta = Conexion.get_Conexion().prepareStatement(querry);          
            ResultSet resultado = consulta.executeQuery();            
            if(resultado.next()){
                result = result + "{\n";
                result = result + "\t" + "codDepartamento:" + resultado.getInt("codDepartamento") + ",\n";
                result = result + "\t" + "codMunicipio:" + resultado.getInt("codMunicipio") + ",\n";
                result = result + "\t" + "codCentroVotacion:" + resultado.getInt("codCentroVotacion") + ",\n";
                result = result + "\t" + "direccion:\"" + resultado.getString("direccion") + "\",\n";
                result = result + "\t" + "numMesa:" + resultado.getInt("numMesa") + ",\n";
                result = result + "\t" + "numLinea:" + resultado.getInt("numLinea") + "\n";
                result = result + "}";
            } else {
                result = result + "{\n";
                result = result + "\tmesnaje:\"El padron seleccionado no existe en la base de datos\",\n";
                result = result + "\tesError:true\n";
                result = result + "}";
            }            
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
        return result;
    }
    
    /**
     * Emision de voto
     */
    public String emitir_voto(String padron, int partido) throws Exception {
        String resultado = "";        
        try {
            String validar = valida_voto(padron);
            if (validar.equals("valido")) {
                boolean insersion = insertar_voto(padron, partido);
                resultado = "{\n";
                resultado = resultado + "\t" + "mensaje:\"Emision voto\",\n";
                resultado = resultado + "\t" + "esError:"+String.valueOf(insersion)+"\n";
                //resultado = resultado + "\t" + "obj:\n";
                resultado = resultado + "}";
            } else {
                resultado = "{\n";
                resultado = resultado + "\t" + "mensaje:\""+validar+"\",\n";
                resultado = resultado + "\t" + "esError:true\n";
                //resultado = resultado + "\t" + "obj:\n";
                resultado = resultado + "}";
            }
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
        return resultado;
    }
    
    public String valida_voto(String dpi) throws SQLException {
        String resultado = "";
        try {
            //Validar que la persona exista en el padron
            String consulta_1 = "SELECT"
                                + " COUNT(1) as existe"
                                + " FROM"
                                + " linea_mesa"
                                + " WHERE dpi =" + dpi;
            PreparedStatement p_consulta_1 = Conexion.get_Conexion().prepareStatement(consulta_1);          
            ResultSet rest_1 = p_consulta_1.executeQuery();
            
            int respuesta_1 = 0;
            while(rest_1.next()){
                respuesta_1 = rest_1.getInt("existe");
            }
            if (respuesta_1 == 1) {
                resultado =  "valido";
            } else {
                return "Persona no empadronada";
            }
            
            //Valida que la persona no haya votado ya
            String consulta_2 = "SELECT"
                                + " COUNT(1) as existe"
                                + " FROM"
                                + " voto_realizado"
                                + " WHERE dpi =" + dpi;
            PreparedStatement p_consulta_2 = Conexion.get_Conexion().prepareStatement(consulta_2);          
            ResultSet rest_2 = p_consulta_2.executeQuery();
            
            int respuesta_2 = 1;
            while(rest_2.next()){
                respuesta_2 = rest_2.getInt("existe");
            }
            if (respuesta_2 == 0) {
                resultado = "valido";
            } else {
                //Actualizar a voto duplicado
                String voto_duplicado = "UPDATE test.voto_realizado"
                                        + " SET voto_duplicado='Duplicado'"
                                        + " WHERE dpi = ?";
                PreparedStatement p_voto_duplicado = Conexion.get_Conexion().prepareStatement(voto_duplicado);
                p_voto_duplicado.setString(1, dpi);                
                int salida_voto_duplicado = p_voto_duplicado.executeUpdate();
                if (salida_voto_duplicado == 1) {
                    return "La persona ya emitio su voto";
                } else {
                    return "Error no controlado";
                }                
            }            
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
        return resultado;
    }
    
    public boolean insertar_voto(String dpi, int partido) throws SQLException {
        boolean resultado = false;
        try {
            String consulta_1 = "INSERT INTO test.voto_realizado (noLinea, dpi) VALUES"
                                + " ("
                                + " (SELECT"
                                + " lin.noLinea"
                                + " FROM"
                                + " departamento dep,"
                                + " municipio mun,"
                                + " centro_votacion cent,"
                                + " mesa_votacion mesa,"
                                + " linea_mesa lin"
                                + " WHERE"
                                + " mun.codDepartamento = dep.codDepartamento AND"
                                + " cent.codMunicipio = mun.codMunicipio AND"
                                + " mesa.codCentro = cent.codCentro AND"
                                + " lin.noMesa = mesa.noMesa AND"
                                + " lin.dpi = ?), ?)";
            PreparedStatement p_consulta_1 = Conexion.get_Conexion().prepareStatement(consulta_1);
            p_consulta_1.setString(1, dpi);
            p_consulta_1.setString(2, dpi);
            int salida_1 = p_consulta_1.executeUpdate();
            if (salida_1 == 1) {
                resultado = true;
            } else {
                return false;
            }

            String consulta_2 = "INSERT INTO test.voto_mesa (noMesa, voto) VALUES"
                                + " ("
                                + " (SELECT"
                                + " mesa.noMesa"
                                + " FROM"
                                + " departamento dep,"
                                + " municipio mun,"
                                + " centro_votacion cent,"
                                + " mesa_votacion mesa,"
                                + " linea_mesa lin"
                                + " WHERE"
                                + " mun.codDepartamento = dep.codDepartamento AND"
                                + " cent.codMunicipio = mun.codMunicipio AND"
                                + " mesa.codCentro = cent.codCentro AND"
                                + " lin.noMesa = mesa.noMesa AND"
                                + " lin.dpi = ?), ?)";
            PreparedStatement p_consulta_2 = Conexion.get_Conexion().prepareStatement(consulta_2);
            p_consulta_2.setString(1, dpi);
            p_consulta_2.setInt(2, partido);
            int salida_2 = p_consulta_2.executeUpdate();
            if (salida_2 == 1) {
                resultado = false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
        return resultado;
    }
        
    /**
     * CRUD personas
     */
    
    public String crud_insert_persona(String dpi, String nombre, String apellido, String sexo, int departamento, int municipio, String nacimiento) throws Exception{
        String result = "";
        try {
            //convertir formato de fecha
            nacimiento = getFecha(nacimiento);
            String consulta = "INSERT INTO test.persona"
                            +" (dpi"
                            +" , Nombre"
                            +" , apellido"
                            +" , sexo"
                            +" , departamento"
                            +" , municipio"
                            +" , fecha_nacimiento)"
                            +" VALUES(?"
                            +" , ?"
                            +" , ?"
                            +" , ?"
                            +" , ?"
                            +" , ?"
                            +" , STR_TO_DATE(?,'%Y-%m-%d'))";            
            PreparedStatement p_consulta = Conexion.get_Conexion().prepareStatement(consulta);
            p_consulta.setString(1, dpi);
            p_consulta.setString(2, nombre);
            p_consulta.setString(3, apellido);
            p_consulta.setString(4, sexo);
            p_consulta.setInt(5, departamento);
            p_consulta.setInt(6, municipio);
            p_consulta.setString(7, nacimiento);
            int salida = p_consulta.executeUpdate();
            if (salida == 1) {
                result = "{\n";
                result = result + "\tmensaje:\"Persona agregada con exito\",\n";
                result = result + "\tesError:false\n";
                result = result + "}";
            } else {
                result = "{\n";
                result = result + "\tmensaje:\"No es posible insertar el registro.\",\n";
                result = result + "\tesError:true\n";
                result = result + "}";
            }
        } catch (Exception e) {
            result = "{\n";
            result = result + "\tmensaje:\"Exception: No es posible insertar el registro.\",\n";
            result = result + "\tesError:true\n";
            result = result + "}";
        } 
        return result;
    }
    
    public String crud_update_persona(String dpi, String nombre, String apellido, String sexo, int departamento, int municipio, String nacimiento) throws Exception {
        String result = "";
        try {
            nacimiento = getFecha(nacimiento);
            String consulta = "UPDATE test.persona"
                            +" SET "
                            +" Nombre=?,"
                            +" apellido=?,"
                            +" sexo=?,"
                            +" departamento=?,"
                            +" municipio=?,"
                            +" fecha_nacimiento = STR_TO_DATE(?,'%Y-%m-%d')"
                            +" WHERE dpi = ?";
            PreparedStatement p_consulta = Conexion.get_Conexion().prepareStatement(consulta);
            p_consulta.setString(1, nombre);
            p_consulta.setString(2, apellido);
            p_consulta.setString(3, sexo);
            p_consulta.setInt(4, departamento);
            p_consulta.setInt(5, municipio);
            p_consulta.setString(6, nacimiento);
            p_consulta.setString(7, dpi);
            int salida = p_consulta.executeUpdate();
            if (salida == 1) {
                result = "{\n";
                result = result + "\tmensaje:\"Datos de la persona actualizados.\",\n";
                result = result + "\tesError:false\n";
                result = result + "}";
            } else {
                result = "{\n";
                result = result + "\tmensaje:\"Error al modificar el registro.\",\n";
                result = result + "\tesError:true\n";
                result = result + "}";
            }
        } catch (Exception e) {
            result = "{\n";
            result = result + "\tmensaje:\"Error al modificar el registro. Excepcion\",\n";
            result = result + "\tesError:true\n";
            result = result + "}";
        }
        return result;
    }
    
    public String crud_delete_persona(String dpi) throws Exception {
        String result = "";
        try {
            String consulta = "DELETE FROM test.persona"
                            +" WHERE dpi = ?";
            PreparedStatement p_consulta = Conexion.get_Conexion().prepareStatement(consulta);
            p_consulta.setString(1, dpi);            
            int salida = p_consulta.executeUpdate();
            if (salida == 1) {
                result = "{\n";
                result = result + "\tmensaje:\"Los datos de la persona se eliminaron con exito\",\n";
                result = result + "\tesError:false\n";
                result = result + "}";
            } else {
                result = "{\n";
                result = result + "\tmensaje:\"Error al eliminar los datos de la persona.\",\n";
                result = result + "\tesError:true\n";
                result = result + "}";
            }
        } catch (Exception e) {
            result = "{\n";
            result = result + "\tmensaje:\"Error al eliminar los datos de la persona. Exception\",\n";
            result = result + "\tesError:true\n";
            result = result + "}";
        }
        return result;
    }
    
    /**
     * CRUD mesas
     */    
    public String crud_insert_mesa(int noMesa, int codMunicipio) throws SQLException {
        String result = "";
        try{
            String consulta = "INSERT INTO test.mesa_votacion"
                            +" (noMesa, codCentro)"
                            +" VALUES(?, (select codCentro from test.centro_votacion where codMunicipio = ?))";
            
            PreparedStatement p_consulta = Conexion.get_Conexion().prepareStatement(consulta);
            
            p_consulta.setInt(1, noMesa);
            p_consulta.setInt(2, codMunicipio);
            
            int salida = p_consulta.executeUpdate();
            if (salida == 1) {
                result = "{\n";
                result = result + "\tmensaje:\"Mesa agregada con exito\",\n";
                result = result + "\tesError:false\n";
                result = result + "}";
            } else {
                result = "{\n";
                result = result + "\tmensaje:\"No es posible insertar el registro.\",\n";
                result = result + "\tesError:true\n";
                result = result + "}";
            }
        } catch (Exception e) {
            result = "{\n";
            result = result + "\tmensaje:\"Error al insertar los datos de la mesa.\",\n";
            result = result + "\tesError:true\n";
            result = result + "}";
        }
        return result;
    }
    
    public String crud_update_mesa(int noMesa, int codMunicipio) throws SQLException {
        String result = "";
        try{
            /*UPDATE test.mesa_votacion
            SET codCentro=NULL
            WHERE noMesa = NULL;*/

            String consulta = "UPDATE test.mesa_votacion"
                            +" SET codCentro= (select codCentro from test.centro_votacion where codMunicipio = ?)"
                            +" WHERE noMesa = ?";
            PreparedStatement p_consulta = Conexion.get_Conexion().prepareStatement(consulta);
            
            p_consulta.setInt(1, codMunicipio);
            p_consulta.setInt(2, noMesa);
            
            int salida = p_consulta.executeUpdate();
            if (salida == 1) {
                result = "{\n";
                result = result + "\tmensaje:\"Mesa actualizada con exito\",\n";
                result = result + "\tesError:false\n";
                result = result + "}";
            } else {
                result = "{\n";
                result = result + "\tmensaje:\"No es posible actualizar el registro.\",\n";
                result = result + "\tesError:true\n";
                result = result + "}";
            }
        } catch (Exception e) {
            result = "{\n";
            result = result + "\tmensaje:\"Error al actualizar los datos de la mesa. Exception\",\n";
            result = result + "\tesError:true\n";
            result = result + "}";
        }
        return result;
    }
    
    public String crud_delete_mesa(int noMesa) throws SQLException {
        String result = "";
        try{
            String consulta = "DELETE FROM test.mesa_votacion"
                            +" WHERE noMesa = ?";;
            PreparedStatement p_consulta = Conexion.get_Conexion().prepareStatement(consulta);
            
            p_consulta.setInt(1, noMesa);            
            
            int salida = p_consulta.executeUpdate();
            if (salida == 1) {
                result = "{\n";
                result = result + "\tmensaje:\"Mesa eliminada con exito\",\n";
                result = result + "\tesError:false\n";
                result = result + "}";
            } else {
                result = "{\n";
                result = result + "\tmensaje:\"No es posible eliminar el registro.\",\n";
                result = result + "\tesError:true\n";
                result = result + "}";
            }
        } catch (Exception e) {
            result = "{\n";
            result = result + "\tmensaje:\"Error al eliminar los datos de la mesa. Exception\",\n";
            result = result + "\tesError:true\n";
            result = result + "}";
        }
        return result;
    }
    
     /**
     * ASIGNAR MESA
     */
    public String asignarMesa(String dpi, int codMunicipio, int mesa) throws Exception {
        String result = "";
        try {
            String consulta = "INSERT INTO test.linea_mesa"
                            +" (noLinea, dpi, noMesa)"
                            +" VALUES(NULL, ?, (select m.noMesa from mesa_votacion m, centro_votacion c where m.codCentro = c.codCentro and c.codMunicipio = ? and m.noMesa = ?))";
            PreparedStatement p_consulta = Conexion.get_Conexion().prepareStatement(consulta);                        
            p_consulta.setString(1, dpi);
            p_consulta.setInt(2, codMunicipio);
            p_consulta.setInt(3, mesa);
            int salida = p_consulta.executeUpdate();
            if (salida == 1) {
                result = "{\n";
                result = result + "\tmensaje:\"Mesa asignada con exito\",\n";
                result = result + "\tesError:false\n";
                result = result + "}";
            } else {
                result = "{\n";
                result = result + "\tmensaje:\"No es posible asignar la mesa.\",\n";
                result = result + "\tesError:true\n";
                result = result + "}";
            }
        } catch (Exception e) {
            result = "{\n";
            result = result + "\tmensaje:\"No es asignar la mesa. Existe un error o la mesa ya fue asignada\",\n";
            result = result + "\tesError:true\n";
            result = result + "}";
        }
        return result;
    }
    
    public String eliminarAsignacionMesa(String dpi) throws Exception {
        String result = "";
        try {
            String consulta = "DELETE FROM test.linea_mesa"
                            +" WHERE dpi= ?";
            PreparedStatement p_consulta = Conexion.get_Conexion().prepareStatement(consulta);                        
            p_consulta.setString(1, dpi);                        
            int salida = p_consulta.executeUpdate();
            if (salida == 1) {
                result = "{\n";
                result = result + "\tmensaje:\"Asignacion de mesa eliminada con exito.\",\n";
                result = result + "\tesError:false\n";
                result = result + "}";
            } else {
                result = "{\n";
                result = result + "\tmensaje:\"No es posible eliminar la asignacion\",\n";
                result = result + "\tesError:true\n";
                result = result + "}";
            }
        } catch (Exception e) {
            result = "{\n";
            result = result + "\tmensaje:\"No es posible eliminar la asignacion\",\n";
            result = result + "\tesError:true\n";
            result = result + "}";
        }
        return result;
    }
    
    /**
     * Acciones de respaldo
     */
    public String getFecha(String fecha){
        try {
            fecha = fecha.replaceAll("-", "");
            fecha = fecha.replaceAll("/", "");
            String anio = fecha.substring(0, 4);
            String mes = fecha.substring(4, 6);
            String dia = fecha.substring(6, 8);
            return anio + "-" + mes + "-" + dia;
        } catch (Exception e) {
            return "error";
        }        
    }
    
    public String excepcion_no_controlada(String mensaje){
        String result = "";
        result = result + "{\n";
        result = result + "\tmesnaje:\"Error no controlado: "+mensaje+"\",\n";
        result = result + "\tesError:false\n";
        result = result + "}";
        return result;
    }
}
