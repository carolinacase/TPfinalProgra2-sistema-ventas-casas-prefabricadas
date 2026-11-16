package DAO;

import Excepciones.DBConexionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Creamos el URL, el usuario y la contraseña.
    private static final String URL = "jdbc:mysql://localhost:3306/casas_prefabricadas";
    private static final String USER = "root";
    private static final String PASSWORD = "bdd26";

    // Creamos el atributo conexion de tipo Connection.
    private static Connection conexion;

    // Usamos DriverManager para obtener la conexion.
    // Si la conexion no existe, lanzamos una excepcion llamada DBConexionException
    public static Connection obtenerConexion() throws DBConexionException {
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            // e.getMessage() dice el motivo (MySQL apagado, contraseña incorrecta, base inexistente...)
            throw new DBConexionException("No se pudo conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    // Usamos conexion para cerrarla y así liberar recursos.
    // Si la conexion no existe, debemos lanzar una excepcion para notificar.
    public static void cerrarConexion() throws DBConexionException {
        try {
            if (conexion != null) {   // SI NUNCA SE PUDO CONECTAR, NO HAY NADA QUE CERRAR (EVITA NullPointerException)
                conexion.close();
            }
        }catch (SQLException e){
            throw new DBConexionException("Conexion no encontrada");
        }
    }

}
