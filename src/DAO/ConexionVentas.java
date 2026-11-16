package DAO;

import Excepciones.DBConexionException;
import Excepciones.DBCreateTableException;
import Modelo.Cliente;
import Modelo.Contado;
import Modelo.Venta;

import java.sql.*;
import java.util.Locale;

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

    // ACTUALIZA CUANTO LLEVA PAGADO UNA VENTA (SE USA CADA VEZ QUE SE REGISTRA UN PAGO)
    public static void actualizarMontoPagado(int idVenta, double montoPagado) throws DBCreateTableException, DBConexionException {
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "UPDATE ventas SET montoPagado = ? WHERE idVenta = ?;";
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setDouble(1, montoPagado);
            ps.setInt(2, idVenta);
            ps.executeUpdate();   // UPDATE ES UNA MODIFICACION, POR ESO executeUpdate
        } catch (SQLException e) {
            throw new DBCreateTableException("No se pudo registrar el pago: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion();
        }
    }

    // ACTUALIZA EL ESTADO DE UNA VENTA (EJ: DE PENDIENTE A ACTIVA CUANDO SE COMPLETA LA DOCUMENTACION)
    public static void actualizarEstado(int idVenta, String estado) throws DBCreateTableException, DBConexionException {
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "UPDATE ventas SET estado = ? WHERE idVenta = ?;";
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, estado);
            ps.setInt(2, idVenta);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBCreateTableException("No se pudo actualizar el estado de la venta: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion();
        }
    }

    // TRAE TODAS LAS VENTAS GUARDADAS EN LA BASE (TAMBIEN LAS DE EJECUCIONES ANTERIORES)
    public static String listarVentas() throws DBCreateTableException, DBConexionException {
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "SELECT * FROM ventas ORDER BY idVenta;";
        StringBuilder sb = new StringBuilder();

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();   // SELECT ES UNA CONSULTA, POR ESO executeQuery
            sb.append(armarListado(rs));
        } catch (SQLException e) {
            throw new DBCreateTableException("No se pudieron listar las ventas: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion();
        }

        return sb.toString();
    }

    // TRAE DE LA BASE SOLO LAS VENTAS DE UN CLIENTE (UN CLIENTE PUEDE TENER VARIAS)
    public static String listarVentasPorCliente(String dni) throws DBCreateTableException, DBConexionException {
        Connection conexion = ConexionBD.obtenerConexion();
        String query = "SELECT * FROM ventas WHERE dniCliente = ? ORDER BY idVenta;";
        StringBuilder sb = new StringBuilder();

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, dni);   // EL DNI VA COMO PARAMETRO, NUNCA CONCATENADO
            ResultSet rs = ps.executeQuery();
            sb.append(armarListado(rs));
        } catch (SQLException e) {
            throw new DBCreateTableException("No se pudieron buscar las ventas del cliente: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion();
        }

        return sb.toString();
    }

    // ARMA UNA LINEA POR CADA VENTA DEL RESULTADO (LO USAN LOS DOS LISTADOS)
    private static String armarListado(ResultSet rs) throws SQLException {
        StringBuilder sb = new StringBuilder();

        while (rs.next()) {   // RECORRE FILA POR FILA
            sb.append("Venta Nº ").append(rs.getInt("idVenta"))
                    .append(" | ").append(rs.getString("tipo"))
                    .append(" | Fecha: ").append(rs.getDate("fechaSenia"))
                    .append(" | Cliente DNI: ").append(rs.getString("dniCliente"))
                    .append(" | Total: $").append(String.format(Locale.of("es", "AR"), "%,.2f", rs.getDouble("precioTotal")))
                    .append(" | Pagado: $").append(String.format(Locale.of("es", "AR"), "%,.2f", rs.getDouble("montoPagado")))
                    .append(" | Estado: ").append(rs.getString("estado"))
                    .append("\n");
        }

        return sb.toString();
    }

}
