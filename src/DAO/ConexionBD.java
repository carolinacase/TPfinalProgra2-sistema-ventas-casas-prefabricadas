package DAO;

import Excepciones.DBConexionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection conexion;

    public static Connection obtenerConexion() throws DBConexionException {
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            throw new DBConexionException("Conexion no encontrada");
        }
        return conexion;
    }
    public static void cerrarConexion() throws DBConexionException {
        try {
            conexion.close();
        }catch (SQLException e){
            throw new DBConexionException("Conexion no encontrada");
        }
    }

}
