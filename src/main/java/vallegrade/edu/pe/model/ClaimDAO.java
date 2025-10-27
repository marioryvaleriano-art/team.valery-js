package vallegrande.edu.pe.model;

import vallegrande.edu.pe.database.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    public void registrarReclamo(String reclamoId, String fechaPresentacion, String nombre, String apellidos,
                                 String tipoDoc, String numeroDoc, String telefono, String email,
                                 String producto, String docCompra, String fechaCompra, String sucursal,
                                 String tipoReclamo, String detalle, String pedido) throws SQLException {

        String sql = "INSERT INTO reclamaciones " +
                "(reclamo_id, fecha_presentacion, nombre, apellidos, tipo_doc, numero_documento, telefono, email, producto, doc_compra, fecha_compra, sucursal, tipo_reclamo, detalle, pedido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reclamoId);
            ps.setDate(2, Date.valueOf(fechaPresentacion));
            ps.setString(3, nombre);
            ps.setString(4, apellidos);
            ps.setString(5, tipoDoc);
            ps.setString(6, numeroDoc);
            ps.setString(7, telefono);
            ps.setString(8, email);
            ps.setString(9, producto);
            ps.setString(10, docCompra);
            ps.setDate(11, Date.valueOf(fechaCompra));
            ps.setString(12, sucursal);
            ps.setString(13, tipoReclamo);
            ps.setString(14, detalle);
            ps.setString(15, pedido);

            ps.executeUpdate();
        }
    }

    public void modificarReclamo(String reclamoId, String telefono, String email, String detalle) throws SQLException {
        String sql = "UPDATE reclamaciones SET telefono=?, email=?, detalle=?, fecha_actualizacion=CURRENT_TIMESTAMP WHERE reclamo_id=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, telefono);
            ps.setString(2, email);
            ps.setString(3, detalle);
            ps.setString(4, reclamoId);
            ps.executeUpdate();
        }
    }

    public void eliminarReclamo(String reclamoId) throws SQLException {
        String sql = "DELETE FROM reclamaciones WHERE reclamo_id=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, reclamoId);
            ps.executeUpdate();
        }
    }

    public List<String[]> listarReclamos() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT reclamo_id, nombre, apellidos, telefono, email, producto, fecha_creacion FROM reclamaciones";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String[] fila = new String[]{
                        rs.getString("reclamo_id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("producto"),
                        rs.getString("fecha_creacion")
                };
                lista.add(fila);
            }
        }
        return lista;
    }
}
