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
                    "  idVenta INT AUTO_INCREMENT," +     // MySQL GENERA EL ID SOLO
                    "  tipo VARCHAR(10) NOT NULL," +      // CONTADO O FINANCIADO
                    "  fechaSenia DATE NOT NULL," +
                    "  montoSenia DOUBLE NOT NULL," +
                    "  montoPagado DOUBLE NOT NULL," +
                    "  precioTotal DOUBLE NOT NULL," +
                    "  estado VARCHAR(10) NOT NULL," +    // ACTIVA O INACTIVA
                    "  dniCliente VARCHAR(8) NOT NULL," + // DE QUE CLIENTE ES LA VENTA
                    "  PRIMARY KEY (idVenta)," +
                    "  FOREIGN KEY (dniCliente) REFERENCES clientes(dni));"; // LA VENTA TIENE QUE SER DE UN CLIENTE EXISTENTE
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
        // NO SE MANDA idVenta: LO GENERA MySQL CON AUTO_INCREMENT
        String query = "INSERT INTO ventas (tipo, fechaSenia, montoSenia, montoPagado, precioTotal, estado, dniCliente) " +
                "VALUES (?,?,?,?,?,?,?);";
        int idGenerado = 0;

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            if (v instanceof Contado) {               // EL TIPO SE GUARDA COMO TEXTO
                ps.setString(1, "CONTADO");
            } else {
                ps.setString(1, "FINANCIADO");
            }
            ps.setDate(2, Date.valueOf(v.getFechaSenia()));   // LocalDate → FECHA DE SQL
            ps.setDouble(3, v.getMontoSenia());
            ps.setDouble(4, v.getMontoPagado());
            ps.setDouble(5, v.getPrecioTotal());
            ps.setString(6, v.getEstado().name());            // ENUM → TEXTO (ACTIVA / INACTIVA)
            ps.setString(7, v.getCliente().getDni());         // RELACION CON LA TABLA CLIENTES
            ps.executeUpdate();

            // CON LA MISMA CONEXION, LE PREGUNTAMOS A MySQL QUE ID LE ASIGNO
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()");

            if (rs.next()) {
                idGenerado = rs.getInt(1);   // LA PRIMERA (Y UNICA) COLUMNA DEL RESULTADO
            }
        } catch (SQLException e) {
            throw new DBCreateTableException("No se pudo insertar la venta: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion();
        }
        return idGenerado;
    }

}
