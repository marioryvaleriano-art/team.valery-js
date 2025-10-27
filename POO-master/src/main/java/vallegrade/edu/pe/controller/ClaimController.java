package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.ClaimDAO;
import java.sql.SQLException;
import java.util.List;

public class ClaimController {
    private ClaimDAO dao = new ClaimDAO();

    public void registrarReclamo(String reclamoId, String fechaPresentacion, String nombre, String apellidos,
                                 String tipoDoc, String numeroDoc, String telefono, String email,
                                 String producto, String docCompra, String fechaCompra, String sucursal,
                                 String tipoReclamo, String detalle, String pedido) throws SQLException {
        dao.registrarReclamo(reclamoId, fechaPresentacion, nombre, apellidos, tipoDoc, numeroDoc,
                telefono, email, producto, docCompra, fechaCompra, sucursal, tipoReclamo, detalle, pedido);
    }

    public void modificarReclamo(String reclamoId, String telefono, String email, String detalle) throws SQLException {
        dao.modificarReclamo(reclamoId, telefono, email, detalle);
    }

    public void eliminarReclamo(String reclamoId) throws SQLException {
        dao.eliminarReclamo(reclamoId);
    }

    public List<String[]> listarReclamos() throws SQLException {
        return dao.listarReclamos();
    }
}
