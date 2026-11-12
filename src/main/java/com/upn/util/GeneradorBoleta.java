package com.upn.util;

import com.upn.model.Cliente;
import com.upn.model.DetalleVenta;
import com.upn.model.Empleado;
import com.upn.model.Venta;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GeneradorBoleta {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static class ItemBoleta {

        String descripcion;
        String cantidad;
        String subtotal;

        public ItemBoleta(String cantidad, String descripcion, String subtotal) {
            this.cantidad = cantidad;
            this.descripcion = descripcion;
            this.subtotal = subtotal;
        }
    }

    public static String generarHtmlBoleta(Venta venta, Cliente cliente, Empleado empleado, DefaultTableModel modeloCarrito) {
        List<ItemBoleta> items = new ArrayList<>();
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            String desc = modeloCarrito.getValueAt(i, 1).toString();
            String cant = modeloCarrito.getValueAt(i, 2).toString();
            String sub = modeloCarrito.getValueAt(i, 4).toString();
            items.add(new ItemBoleta(cant, desc, sub));
        }
        return construirHtml(venta, cliente, empleado, items);
    }

    public static String generarHtmlBoletaDesdeEntidad(Venta venta) {
        if (venta == null) {
            return "<html><body><h3 style='color:red;'>Venta no disponible</h3></body></html>";
        }
        List<ItemBoleta> items = new ArrayList<>();
        if (venta.getDetalles() != null) {
            for (DetalleVenta d : venta.getDetalles()) {
                String desc = (d.getProducto() != null) ? d.getProducto().getNombre() : "Producto";
                String cant = String.valueOf(d.getCantidad());
                String sub = String.format("S/ %.2f", d.getSubtotal());
                items.add(new ItemBoleta(cant, desc, sub));
            }
        }
        return construirHtml(venta, venta.getCliente(), venta.getEmpleado(), items);
    }

    private static String construirHtml(Venta venta, Cliente cliente, Empleado empleado, List<ItemBoleta> items) {
        StringBuilder html = new StringBuilder();

        String fechaStr = (venta != null && venta.getFecha() != null)
                ? venta.getFecha().format(FORMATO_FECHA)
                : "---";

        double total = (venta != null) ? venta.getTotal() : 0.0;
        double adelanto = (venta != null) ? venta.getAdelanto() : 0.0;
        double saldo = (venta != null) ? venta.getSaldo() : 0.0;
        Long idVenta = (venta != null && venta.getId() != null) ? venta.getId() : 1L;

        String nombreEmpleado = (empleado != null && empleado.mostrarNombreCompleto() != null)
                ? empleado.mostrarNombreCompleto()
                : (empleado != null && empleado.getNombre() != null ? empleado.getNombre() : "Caja Central");

        html.append("<html><head><style>")
                .append("body { font-family: 'Courier New', monospace; font-size: 11px; margin: 10px; }")
                .append(".centro { text-align: center; }")
                .append(".linea { border-bottom: 1px dashed #000; margin: 6px 0; }")
                .append("table { width: 100%; border-collapse: collapse; font-size: 11px; }")
                .append(".der { text-align: right; }")
                .append(".izq { text-align: left; }")
                .append("</style></head><body>");

        // Encabezado
        html.append("<div class='centro'>")
                .append("<h2><b>PASTELERÍA BRIALE</b></h2>")
                .append("RUC: 20601234567<br>")
                .append("Calle Real 123 - Trujillo, Perú<br>")
                .append("Tel: (044) 283940<br>")
                .append("</div>")
                .append("<div class='linea'></div>");

        html.append("<b>BOLETA DE VENTA ELECTRÓNICA</b><br>")
                .append("<b>N° Venta:</b> B001-").append(String.format("%06d", idVenta)).append("<br>")
                .append("<b>Fecha:</b> ").append(fechaStr).append("<br>")
                .append("<b>Cajero:</b> ").append(nombreEmpleado).append("<br>")
                .append("<div class='linea'></div>");

        html.append("<b>CLIENTE:</b> ").append(cliente != null ? cliente.mostrarNombreCompleto() : "PÚBLICO GENERAL").append("<br>")
                .append("<b>DNI/RUC:</b> ").append(cliente != null && cliente.getDni() != null ? cliente.getDni() : "00000000").append("<br>")
                .append("<div class='linea'></div>");

        html.append("<table>")
                .append("<tr><th class='izq'>Cant.</th><th class='izq'>Producto</th><th class='der'>Total</th></tr>");

        for (ItemBoleta item : items) {
            html.append("<tr>")
                    .append("<td class='izq'>").append(item.cantidad).append("</td>")
                    .append("<td class='izq'>").append(item.descripcion).append("</td>")
                    .append("<td class='der'>").append(item.subtotal).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>")
                .append("<div class='linea'></div>");

        html.append("<table>")
                .append("<tr><td class='izq'>Subtotal:</td><td class='der'>S/ ").append(String.format("%.2f", total / 1.18)).append("</td></tr>")
                .append("<tr><td class='izq'>IGV (18%):</td><td class='der'>S/ ").append(String.format("%.2f", total - (total / 1.18))).append("</td></tr>")
                .append("<tr><td class='izq'><b>TOTAL:</b></td><td class='der'><b>S/ ").append(String.format("%.2f", total)).append("</b></td></tr>")
                .append("<tr><td class='izq'>Pago / Adelanto:</td><td class='der'>S/ ").append(String.format("%.2f", adelanto)).append("</td></tr>")
                .append("<tr><td class='izq'>Saldo / Vuelto:</td><td class='der'>S/ ").append(String.format("%.2f", Math.abs(saldo))).append("</td></tr>")
                .append("</table>")
                .append("<div class='linea'></div>");

        html.append("<div class='centro'><br>")
                .append("¡Gracias por su compra!<br>")
                .append("Conserve su comprobante")
                .append("</div></body></html>");

        return html.toString();
    }
}
