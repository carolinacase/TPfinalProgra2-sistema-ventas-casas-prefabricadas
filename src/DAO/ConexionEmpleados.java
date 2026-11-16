package DAO;

import Enumeradores.EstadoEmpleados;
import Excepciones.DBConexionException;
import Excepciones.DBCreateTableException;
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
            String query = "CREATE TABLE IF NOT EXISTS empleados("
                    + "nombre VARCHAR(50) NOT NULL,"
                    + "apellido VARCHAR(50) NOT NULL,"
                    + "dni VARCHAR(8) NOT NULL,"
                    + "domicilio VARCHAR(50) NOT NULL," // 50 PARA QUE ENTREN DIRECCIONES Y MAILS COMUNES
                    + "correo VARCHAR(50) NOT NULL,"
                    + "id INT AUTO_INCREMENT," // LEGAJO: LO GENERA MYSQL
                    + "activo TINYINT(1)," // ACTIVO O INACTIVO
                    + "fecha_ingreso DATE NOT NULL,"
                    + "tipo_empleado VARCHAR(20) NOT NULL," // VENDEDOR o ADMINISTRATIVO
                    + "PRIMARY KEY(dni)," // El dni sera nuestra primary key
                    + "UNIQUE(id));"; // MYSQL PIDE QUE EL AUTO_INCREMENT SEA UNICO
            Statement ps = conexion.createStatement();
            ps.execute(query);
        }catch (SQLException e){
            throw new DBConexionException("No se pudo crear la tabla de empleados: " + e.getMessage());
        }finally {
            ConexionBD.cerrarConexion();
        }
    }

    // GUARDA EL EMPLEADO Y DEVUELVE EL LEGAJO QUE GENERO MySQL
    public static int insertarEmpleado(Empleado e) throws DBCreateTableException, DBConexionException{
        Connection conexion = ConexionBD.obtenerConexion();
        // NO SE MANDA EL ID (LEGAJO): LO GENERA MySQL CON AUTO_INCREMENT
        String query = "INSERT INTO empleados (nombre, apellido, dni, domicilio, correo, activo, fecha_ingreso, tipo_empleado) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        int legajoGenerado = 0;
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getDni());
            ps.setString(4, e.getDomicilio());
            ps.setString(5, e.getCorreo());
            ps.setBoolean(6, e.getEstado().getValorEstado());
            ps.setDate(7, Date.valueOf(e.getFechaIngreso()));
            if(e instanceof Vendedor){ // Guardamos el tipo de empleado como texto.
                ps.setString(8, "VENDEDOR");
            }else if(e instanceof Administrativo){
                ps.setString(8, "ADMINISTRATIVO");
            }
            ps.executeUpdate();

            // CON LA MISMA CONEXION, LE PREGUNTAMOS A MySQL QUE LEGAJO LE ASIGNO
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                legajoGenerado = rs.getInt(1);
            }
        }catch (SQLException ex){
            throw new DBCreateTableException("No se pudo insertar el empleado: " + ex.getMessage()); // Lanzamos una excepcion si no se inserto al empleado de forma correcta
        }finally {
            ConexionBD.cerrarConexion();
        }
        return legajoGenerado;
    }

    // BORRA DE LA BASE EL EMPLEADO CON ESE DNI
    public static void eliminarEmpleado(String dni) throws DBCreateTableException, DBConexionException {
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "DELETE FROM empleados WHERE dni = ?;";
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, dni);      // EL ? SE REEMPLAZA POR EL DNI RECIBIDO
            ps.executeUpdate();        // DELETE ES UNA MODIFICACION, POR ESO executeUpdate
        } catch (SQLException e) {
            throw new DBCreateTableException("No se pudo eliminar el empleado: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion();
        }
    }

    public static Map<String, Empleado> listarEmpleados() throws DBCreateTableException, DBConexionException{
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "SELECT * from empleados";
        Map<String, Empleado> empleados = new HashMap<String, Empleado>(); // Creamos un HashMap con String para el dni como clave y empleado como valor.
        Empleado e;
        try {
            Statement ps = conexion.createStatement();
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()){
                EstadoEmpleados estado = rs.getBoolean("activo") ? EstadoEmpleados.ACTIVO : EstadoEmpleados.INACTIVO; // Creamos una variable de tipo EstadoEmpleados.
                String tipo = rs.getString("tipo_empleado"); // Creamos una variable de tipo String para el tipo de empleados
                if(tipo.equals("VENDEDOR")){ // Si es vendedor, creamos un objeto de tipo empleado, e instanciamos con con ResultSet y el constructor
                    e = new Vendedor(rs.getString("nombre"), rs.getString("apellido"),
                            rs.getString("dni"), rs.getString("domicilio"),
                            rs.getString("correo"), rs.getInt("id"),
                            estado, rs.getDate("fecha_ingreso").toLocalDate());
                }else if(tipo.equals("ADMINISTRATIVO")){ // Hacemos lo mismo con administrativo
                    e = new Administrativo(rs.getString("nombre"), rs.getString("apellido"),
                            rs.getString("dni"), rs.getString("domicilio"),
                            rs.getString("correo"), rs.getInt("id"),
                            estado, rs.getDate("fecha_ingreso").toLocalDate());
                }else{
                    throw new SQLException("Tipo de empleado desconocido"); // Lanzamos una excepcion si agregamos un empleado que no sea valido
                }
                empleados.put(e.getDni(), e); // Agregamos un empleado al HashMap
            }
        }catch (SQLException ex){
            throw new DBCreateTableException("No se pudieron listar los empleados");
        }finally {
            ConexionBD.cerrarConexion();
        }
        return empleados;
    }
}
