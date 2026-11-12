package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConexionBD {


    Connection conn;

    public ConexionBD() {
        createConnection();
    }

    public void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/casas_prefabricadas", "root", "bdd2026");

            System.out.println("Conexion establecida con exito");
        } catch (ClassNotFoundException e) {

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
        }


    }


}
