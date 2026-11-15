package DAO;

import Excepciones.DBConexionException;
import Excepciones.DBCreateTableException;
import Modelo.Cliente;
import Modelo.Contado;
import Modelo.Venta;

import java.sql.*;

public class ConexionVentas {
    public static void crearTablaVentas() throws DBConexionException {
        try {
            Connection conexion = ConexionBD.obtenerConexion();
            String query = "CREATE TABLE IF NOT EXISTS ventas (" +
                    "  idVenta INT AUTO_INCREMENT," +     // MySQL genera el id solo
                    "  tipo VARCHAR(10) NOT NULL," +      // CONTADO o FINANCIADO
                    "  fechaSenia DATE NOT NULL," +
                    "  montoSenia DOUBLE NOT NULL," +
                    "  montoPagado DOUBLE NOT NULL," +
                    "  precioTotal DOUBLE NOT NULL," +
                    "  estado VARCHAR(10) NOT NULL," +    // ACTIVA o INACTIVA
                    "  dniCliente VARCHAR(8) NOT NULL," + // de qué cliente es la venta
                    "  PRIMARY KEY (idVenta)," +
                    "  FOREIGN KEY (dniCliente) REFERENCES clientes(dni));"; // la venta tiene que ser de un cliente existente
            Statement st = conexion.createStatement();
            st.execute(query);
        } catch (SQLException e) {
            throw new DBConexionException("No se pudo crear la tabla de ventas: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion();
        }
    }

    public static int insertarVenta(Venta v) throws DBCreateTableException, DBConexionException {
        Connection conexion = ConexionBD.obtenerConexion();
        // no se manda idVenta: lo genera MySQL con AUTO_INCREMENT
        String query = "INSERT INTO ventas (tipo, fechaSenia, montoSenia, montoPagado, precioTotal, estado, dniCliente) " +
                "VALUES (?,?,?,?,?,?,?);";
        int idGenerado = 0;

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            if (v instanceof Contado) {               // el tipo se guarda como texto
                ps.setString(1, "CONTADO");
            } else {
                ps.setString(1, "FINANCIADO");
            }
            ps.setDate(2, Date.valueOf(v.getFechaSenia()));   // LocalDate → fecha de SQL
            ps.setDouble(3, v.getMontoSenia());
            ps.setDouble(4, v.getMontoPagado());
            ps.setDouble(5, v.getPrecioTotal());
            ps.setString(6, v.getEstado().name());            // enum → texto (ACTIVA / INACTIVA)
            ps.setString(7, v.getCliente().getDni());         // relación con la tabla clientes
            ps.executeUpdate();

            // con la MISMA conexión, le preguntamos a MySQL qué id le asignó
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()");

            if (rs.next()) {
                idGenerado = rs.getInt(1);   // la primera (y única) columna del resultado
            }
        } catch (SQLException e) {
            throw new DBCreateTableException("No se pudo insertar la venta: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion();
        }
        return idGenerado;
    }

}
