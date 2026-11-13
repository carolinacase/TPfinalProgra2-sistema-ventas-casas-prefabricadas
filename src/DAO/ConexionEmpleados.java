package DAO;

import Excepciones.DBConexionException;
import Excepciones.DBCreationTableException;
import Modelo.Administrativo;
import Modelo.Empleado;
import Modelo.Vendedor;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class ConexionEmpleados {
    public static void crearTabla() throws DBConexionException{
        try {
            Connection conexion = ConexionBD.obtenerConexion();
            String query = "CREATE TABLE IF NOOT EXISTS empleados("
                    + "nombre VARCHAR(50) NOT NULL,"
                    + "apellido VARCHAR(50) NOT NULL,"
                    + "dni VARCHAR(8) NOT NULL,"
                    + "domicilio VARCHAR(20) NOT NULL,"
                    + "correo VARCHAR(20) NOT NULL,"
                    + "id INT NOT NULL,"
                    + "activo TINYINT(1),"
                    + "fecha_ingreso DATE NOT NULL,"
                    + "tipo_empleado VARCHAR(20) NOT NULL"
                    + "PRIMARY KEY(dni));";
            Statement ps = conexion.createStatement();
            ps.execute(query);
        }catch (SQLException e){
            throw new DBConexionException("No se pudo crear la tabla de empleados");
        }finally {
            ConexionBD.cerrarConexion();
        }
    }

    public static void insertarEmpleado(Empleado e) throws DBCreationTableException, DBConexionException{
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "INSERT INTO empleados VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getDni());
            ps.setString(4, e.getDomicilio());
            ps.setString(5, e.getCorreo());
            ps.setInt(6, e.getId());
            ps.setBoolean(7, e.isActivo());
            ps.setDate(8, Date.valueOf(e.getFechaIngreso()));
            if(e instanceof Vendedor){
                ps.setString(9, "VENDEDOR");
            }else if(e instanceof Administrativo){
                ps.setString(9, "ADMINISTRATIVO");
            }
            ps.executeUpdate();
        }catch (SQLException ex){
            throw new DBConexionException("No se pudo insertar al empleado");
        }finally {
            ConexionBD.cerrarConexion();
        }
    }


    public static Map<String, Empleado> listarEmpleados() throws DBCreationTableException, DBConexionException{
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "SELECT * from empleados";
        Map<String, Empleado> empleados = new HashMap<String, Empleado>();
        Empleado e;
        try {
            Statement ps = conexion.createStatement();
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()){
                String tipo = rs.getString("tipo_empleado");
                if(tipo.equals("VENDEDOR")){
                    e = new Vendedor(rs.getString("nombre"), rs.getString("apellido"),
                            rs.getString("dni"), rs.getString("domicilio"),
                            rs.getString("correo"), rs.getInt("id"),
                            rs.getBoolean("activo"), rs.getDate("fecha_ingreso").toLocalDate());
                }else if(tipo.equals("ADMINISTRATIVO")){
                    e = new Administrativo(rs.getString("nombre"), rs.getString("apellido"),
                            rs.getString("dni"), rs.getString("domicilio"),
                            rs.getString("correo"), rs.getInt("id"),
                            rs.getBoolean("activo"), rs.getDate("fecha_ingreso").toLocalDate());
                }else{
                    throw new SQLException("Tipo de empleado desconocido");
                }
                empleados.put(e.getDni(), e);
            }
        }catch (SQLException ex){
            throw new DBCreationTableException("No se pudieron listar los empleados");
        }finally {
            ConexionBD.cerrarConexion();
        }
        return empleados;
    }
}
