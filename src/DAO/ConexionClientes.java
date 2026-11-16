package DAO;

import Excepciones.DBConexionException;
import Excepciones.DBCreateTableException;
import Modelo.Cliente;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ConexionClientes {
    // Creamos la tabla de clientes, con su dni como primary key
    // Si no se logra crear la tabla, lanzamos una excepcion
    public static void crearTablaCliente()throws DBConexionException{
        try{
            Connection conexion = ConexionBD.obtenerConexion();
            String query = "CREATE TABLE IF NOT EXISTS clientes (" +
                    "  nombre VARCHAR(50) NOT NULL," +
                    "  apellido VARCHAR(50) NOT NULL," +
                    "  dni VARCHAR(8) NOT NULL," +
                    "  domicilio VARCHAR(50) NOT NULL," +   // 50 PARA QUE ENTREN DIRECCIONES Y MAILS COMUNES
                    "  correo VARCHAR(50) NOT NULL," +
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

    // Si no se logra insertar un cliente, lanzamos tambien una excepcion
    public static void insertarCliente(Cliente c) throws DBCreateTableException, DBConexionException {
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
            throw new DBCreateTableException("No se pudo insertar el cliente: " + e.getMessage());
        }finally {
            ConexionBD.cerrarConexion();
        }
    }

    // Creamos un Map de tipo String para el dni (siendo su clave) y de tipo Cliente (siendo su valor)
    // Si no se logra obtener al cliente, lanzamos una excepcion
    // Por otro lado, agregamos el objeto de tipo cliente al Map y por ultimo retornamos el Map.
    public static Map<String, Cliente> obtenerClientes() throws DBCreateTableException, DBConexionException {
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "SELECT * FROM clientes";
        Map<String, Cliente> clientes = new HashMap<>();

        try{
            Statement ps = conexion.createStatement();
            ResultSet rs = ps.executeQuery(query);
            Cliente c;
            while(rs.next()){
                c = new Cliente(rs.getString("nombre"),rs.getString("apellido"),rs.getString("dni"),rs.getString("domicilio"),rs.getString("correo"));
                clientes.put(c.getDni(), c); // LA CLAVE ES EL DNI, IGUAL QUE EN GestoraClientes
            }

        }catch (SQLException e){
            throw new DBCreateTableException("No se pudo obtener el cliente");
        }
        finally{
            ConexionBD.cerrarConexion();
        }
        return clientes;
    }
}
