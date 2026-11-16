package com.upn.util;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

public class EstiloBriale {

    public static final Color BG_CREMA = new Color(255, 253, 252);
    public static final Color BG_ROSA = new Color(246, 236, 233);
    public static final Color BG_HEADER = new Color(234, 224, 221);
    public static final Color BORDE_SUAVE = new Color(232, 218, 214);
    public static final Color TEXTO_CAFE = new Color(74, 53, 49);
    public static final Color TERRACOTA = new Color(196, 117, 115);
    public static final Color TERRACOTA_OSCURO = new Color(143, 86, 84);
    public static final Color VERDE_SALVIA = new Color(46, 125, 50);

    public static void estilizarTituloSeccion(JLabel lbl, Ikon ikon) {
        lbl.setIcon(FontIcon.of(ikon, 16, TERRACOTA_OSCURO));
        lbl.setIconTextGap(8);
        lbl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDE_SUAVE),
                BorderFactory.createEmptyBorder(0, 0, 6, 0)
        ));
    }

    public static void estilizarBotonPrimario(JButton btn, Ikon ikon, int radio) {
        aplicarBotonRedondeado(btn, TERRACOTA, Color.WHITE, null, ikon, 15, radio);
    }

    public static void estilizarBotonSecundario(JButton btn, Ikon ikon, int radio) {
        aplicarBotonRedondeado(btn, BG_CREMA, TEXTO_CAFE, BORDE_SUAVE, ikon, 15, radio);
    }

    public static void estilizarBotonBeige(JButton btn, Ikon ikon, int radio) {
        aplicarBotonRedondeado(btn, BG_HEADER, TEXTO_CAFE, BORDE_SUAVE, ikon, 15, radio);
    }

    private static void aplicarBotonRedondeado(JButton btn, Color fondo, Color texto,
            Color bordeColor, Ikon ikon, int tamIcono, int radio) {
        btn.setForeground(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        if (ikon != null) {
            btn.setIcon(FontIcon.of(ikon, tamIcono, texto));
            btn.setIconTextGap(8);
        }
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));

        btn.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (!btn.isEnabled()) {
                    g2.setColor(BG_HEADER);
                } else if (btn.getModel().isRollover()) {
                    g2.setColor(fondo.darker());
                } else {
                    g2.setColor(fondo);
                }

                g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radio, radio);

                if (bordeColor != null || !btn.isEnabled()) {
                    g2.setColor(BORDE_SUAVE);
                    g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radio, radio);
                }
                g2.dispose();
                super.paint(g, c);
            }
        });
    }

    public static void estilizarTabla(JTable tabla, JScrollPane scroll) {
        tabla.setBackground(BG_CREMA);
        tabla.setForeground(TEXTO_CAFE);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(28);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(BG_ROSA);
        tabla.setSelectionBackground(BG_HEADER);
        tabla.setSelectionForeground(TEXTO_CAFE);

        JTableHeader header = tabla.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, val, sel, foc, r, c);
                setBackground(BG_HEADER);
                setForeground(TEXTO_CAFE);
                setFont(new Font("Segoe UI", Font.BOLD, 12));
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 1, BORDE_SUAVE),
                        BorderFactory.createEmptyBorder(6, 10, 6, 10)
                ));
                return this;
            }
        });

        if (scroll != null) {
            scroll.getViewport().setBackground(BG_CREMA);
            scroll.setBorder(BorderFactory.createLineBorder(BORDE_SUAVE, 1));
        }
    }

    public static void estilizarComboBox(JComboBox<?> combo) {
        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton btnFlecha = new JButton(FontIcon.of(Feather.CHEVRON_DOWN, 16, TERRACOTA_OSCURO));
                btnFlecha.setBackground(BG_CREMA);
                btnFlecha.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 6));
                btnFlecha.setContentAreaFilled(false);
                btnFlecha.setFocusPainted(false);
                btnFlecha.setBorderPainted(false);
                return btnFlecha;
            }

            @Override
            protected ComboPopup createPopup() {
                BasicComboPopup popup = new BasicComboPopup(comboBox);
                popup.setBorder(BorderFactory.createLineBorder(BORDE_SUAVE, 1));
                return popup;
            }
        });

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(new Font("Segoe UI", isSelected ? Font.BOLD : Font.PLAIN, 13));
                setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

                if (isSelected) {
                    setBackground(BG_HEADER);
                    setForeground(TERRACOTA_OSCURO);
                } else {
                    setBackground(BG_CREMA);
                    setForeground(TEXTO_CAFE);
                }
                return this;
            }
        });
    }

    public static void estilizarDateChooser(JDateChooser dtc) {
        dtc.setBackground(BG_CREMA);
        dtc.setBorder(BorderFactory.createLineBorder(BORDE_SUAVE, 1));
        if (dtc.getDateEditor().getUiComponent() instanceof JTextField txtFecha) {
            txtFecha.setBackground(BG_CREMA);
            txtFecha.setForeground(TEXTO_CAFE);
            txtFecha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            txtFecha.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 4));
        }
        dtc.getCalendarButton().setIcon(FontIcon.of(Feather.CALENDAR, 15, TERRACOTA_OSCURO));
        dtc.getCalendarButton().setBackground(BG_HEADER);
        dtc.getCalendarButton().setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        dtc.getCalendarButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
