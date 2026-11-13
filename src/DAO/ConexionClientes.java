package DAO;

import Excepciones.DBConexionException;
import Excepciones.DBCreationTableException;
import Modelo.Cliente;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ConexionClientes {
    public static void crearTablaCliente()throws DBConexionException{
        try{
            Connection conexion = ConexionBD.obtenerConexion();
            String query = "CREATE TABLE IF NOT EXISTS clientes (" +
                    "  nombre VARCHAR(50) NOT NULL," +
                    "  apellido VARCHAR(50) NOT NULL," +
                    "  dni VARCHAR(8) NOT NULL," +
                    "  domicilio VARCHAR(20) NOT NULL," +
                    "  correo VARCHAR(20) NOT NULL," +
                    "  PRIMARY KEY (dni));";
            Statement ps = conexion.createStatement();
            ps.execute(query);
        }catch(SQLException e){
            throw new DBConexionException("No se pudo obtener tabla de clientes");
        }
        finally{
            ConexionBD.cerrarConexion();
        }
    }
    public static void insertarCliente(Cliente c) throws DBCreationTableException, DBConexionException {
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "INSERT INTO clientes VALUES (?,?,?,?,?);";

        try{
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1,c.getNombre());
            ps.setString(2,c.getApellido());
            ps.setString(3,c.getDni());
            ps.setString(4, c.getDomicilio());
            ps.setString(5,c.getCorreo());
            ps.executeUpdate();
        }catch(SQLException e){
            
        }
    }
}
