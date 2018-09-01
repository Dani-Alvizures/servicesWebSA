/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios_src;
import java.sql.*;

/**
 *
 * @author kevin
 */
public class Conexion {
    private static Connection cnx = null;
    public static Connection get_Conexion() throws SQLException, ClassNotFoundException {
        if (cnx == null) {
            try {
               Class.forName("com.mysql.jdbc.Driver");
               cnx = DriverManager.getConnection("jdbc:mysql://192.168.0.10/test", "root", "root");
            } catch (SQLException ex) {
               throw new SQLException(ex);
            } catch (ClassNotFoundException ex) {
               throw new ClassCastException(ex.getMessage());
            }
         }
      return cnx;
    }
    public static void close() throws SQLException {
        if (cnx != null) {
           cnx.close();
        }
    }
}
