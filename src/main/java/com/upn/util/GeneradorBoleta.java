package com.upn.util;

import com.upn.model.Cliente;
import com.upn.model.Empleado;
import com.upn.model.Venta;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;

public class GeneradorBoleta {

    public static String generarHtmlBoleta(Venta venta, Cliente cliente, Empleado empleado, DefaultTableModel modeloCarrito) {
        StringBuilder html = new StringBuilder();

        String fechaStr = venta.getFechaVenta()!= null 
                ? venta.getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "---";

        html.append("<html><head><style>")
            .append("body { font-family: 'Courier New', monospace; font-size: 11px; margin: 10px; }")
            .append(".centro { text-align: center; }")
            .append(".linea { border-bottom: 1px dashed #000; margin: 6px 0; }")
            .append("table { width: 100%; border-collapse: collapse; font-size: 11px; }")
            .append(".der { text-align: right; }")
            .append(".izq { text-align: left; }")
            .append("</style></head><body>");

        // Encabezado de la Pastelería
        html.append("<div class='centro'>")
            .append("<h2><b>PASTELERÍA BRIALE</b></h2>")
            .append("RUC: 20601234567<br>")
            .append("Calle Real 123 - Trujillo, Perú<br>")
            .append("Tel: (044) 283940<br>")
            .append("</div>")
            .append("<div class='linea'></div>");

        // Datos del Comprobante
        html.append("<b>BOLETA DE VENTA ELECTRÓNICA</b><br>")
            .append("<b>N° Venta:</b> B001-").append(String.format("%06d", venta.getId()!= null ? venta.getId() : 1)).append("<br>")
            .append("<b>Fecha:</b> ").append(fechaStr).append("<br>")
            .append("<b>Cajero:</b> ").append(empleado != null ? empleado.getNombre() : "Caja Central").append("<br>")
            .append("<div class='linea'></div>");

        // Datos del Cliente
        html.append("<b>CLIENTE:</b> ").append(cliente != null ? cliente.mostrarNombreCompleto() : "PÚBLICO GENERAL").append("<br>")
            .append("<b>DNI/RUC:</b> ").append(cliente != null ? cliente.getDni() : "00000000").append("<br>")
            .append("<div class='linea'></div>");

        // Tabla de Productos
        html.append("<table>")
            .append("<tr><th class='izq'>Cant.</th><th class='izq'>Producto</th><th class='der'>Total</th></tr>");

        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            String desc = modeloCarrito.getValueAt(i, 1).toString();
            String cant = modeloCarrito.getValueAt(i, 2).toString();
            String sub = modeloCarrito.getValueAt(i, 4).toString();

            html.append("<tr>")
                .append("<td class='izq'>").append(cant).append("</td>")
                .append("<td class='izq'>").append(desc).append("</td>")
                .append("<td class='der'>").append(sub).append("</td>")
                .append("</tr>");
        }

        html.append("</table>")
            .append("<div class='linea'></div>");

        // Resumen de Montos
        html.append("<table>")
            .append("<tr><td class='izq'>Subtotal:</td><td class='der'>S/ ").append(String.format("%.2f", venta.getTotal() / 1.18)).append("</td></tr>")
            .append("<tr><td class='izq'>IGV (18%):</td><td class='der'>S/ ").append(String.format("%.2f", venta.getTotal() - (venta.getTotal() / 1.18))).append("</td></tr>")
            .append("<tr><td class='izq'><b>TOTAL:</b></td><td class='der'><b>S/ ").append(String.format("%.2f", venta.getTotal())).append("</b></td></tr>")
            .append("<tr><td class='izq'>Pago / Adelanto:</td><td class='der'>S/ ").append(String.format("%.2f", venta.getAdelanto())).append("</td></tr>")
            .append("<tr><td class='izq'>Saldo / Vuelto:</td><td class='der'>S/ ").append(String.format("%.2f", Math.abs(venta.getSaldo()))).append("</td></tr>")
            .append("</table>")
            .append("<div class='linea'></div>");

        // Pie de página
        html.append("<div class='centro'><br>")
            .append("¡Gracias por su compra!<br>")
            .append("Conserve su comprobante")
            .append("</div></body></html>");

        return html.toString();
    }
}