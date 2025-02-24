package t8_practica_filtraciones;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdministradoresGUI extends GUI implements ActionListener {

    final private JButton INSERT;
    final private JButton QUERY;
    final private JButton UPDATE;
    final private JButton DELETE;
    final private JButton CLEAN;
    final private JButton MODIFY;
    final private JButton HISTORY;
    final private JButton EXIT;
    //TABLA
    private JTable table;
    private static DefaultTableModel model;
    private JScrollPane verticalScroll;
    private String[] header;
    private Object[][] data;

    public AdministradoresGUI() {

        super();
        this.setTitle(MetodosToSql.getAdminName() + "           🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕ ");
        this.setSize(780, 450);
        layout.setSize(800, 450);

        layout.setBackground(Color.GREEN);
        //BOTON AÑADIR FILTRACION
        INSERT = new JButton("Añadir filtración ");
        INSERT.setBounds(0, 0, 130, 40);
        INSERT.addActionListener(e -> {

            try {
                MetodosToSql.añadirFiltracion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        INSERT.setBackground(Color.black);
        INSERT.setForeground(Color.green);
        layout.add(INSERT);
        //BOTON REALIZAR QUERY
        QUERY = new JButton("Consulta SQL");
        QUERY.setBounds(130, 0, 130, 40);
        QUERY.addActionListener(this);
        QUERY.setBackground(Color.black);
        QUERY.setForeground(Color.green);
        layout.add(QUERY);
        //BOTON ACTUALIZAR
        UPDATE = new JButton("Refresh");
        UPDATE.setBounds(260, 0, 130, 40);
        UPDATE.setBackground(Color.black);
        UPDATE.setForeground(Color.green);
        UPDATE.addActionListener(this);
        layout.add(UPDATE);
        //BOTON BORRAR
        DELETE = new JButton("Borrar");
        DELETE.setBounds(390, 0, 130, 40);
        DELETE.setBackground(Color.black);
        DELETE.setForeground(Color.green);
        DELETE.addActionListener(this);
        layout.add(DELETE);
        //BOTON LIMPIAR
        CLEAN = new JButton("Limpiar");
        CLEAN.setBounds(520, 0, 130, 40);
        CLEAN.setBackground(Color.black);
        CLEAN.setForeground(Color.green);
        CLEAN.addActionListener(e -> model.setRowCount(0));
        layout.add(CLEAN);
        //BOTON MODIFICAR
        MODIFY = new JButton("Modificar");
        MODIFY.setBounds(650, 0, 130, 40);
        MODIFY.setBackground(Color.black);
        MODIFY.setForeground(Color.green);
        MODIFY.addActionListener(this);
        MODIFY.addActionListener(e -> {
            try {
                MetodosToSql.modifyQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        layout.add(MODIFY);

        //BOTON HISTORIAL
        HISTORY = new JButton("Historial");
        HISTORY.setBounds(0, 210, 130, 40);
        HISTORY.setBackground(Color.black);
        HISTORY.setForeground(Color.green);
        HISTORY.addActionListener(this);
        layout.add(HISTORY);

        EXIT = new JButton("Salir");
        EXIT.setBounds(645, 210, 130, 40);
        EXIT.setBackground(Color.black);
        EXIT.setForeground(Color.green);
        EXIT.addActionListener(e -> System.exit(0));

        layout.add(EXIT);

        header = new String[]{
            "id_filtracion", "plataforma", "fecha", "nº afectados",
            "descripción", "medidas"
        };
        data = new Object[0][6];
        model = new DefaultTableModel(data, header);

        table = new JTable(model);
        table.setBackground(Color.black);
        table.setForeground(Color.white);
        table.setGridColor(Color.green);
        table.setEnabled(true);
        table.getTableHeader().setReorderingAllowed(false); // desactiva poder mover las columnas

        // SCROLL
        verticalScroll = new JScrollPane(table);
        verticalScroll.setBounds(0, 250, 780, 300);
        verticalScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        verticalScroll.setForeground(Color.yellow);

        layout.add(verticalScroll); // Agrega el JScrollPane al layout

        layout.add(verticalScroll);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Scroll scroll = new Scroll(); //intanciar clase scroll para crear joptionpane de gran tamaño

        if (e.getSource() == QUERY) {

            String queryInput = JOptionPane.showInputDialog("Introduce la query (solo diponible columna usuario)");
            PreparedStatement pre;

            try {
                Connection con = MetodosToSql.establecerConexion();
                pre = con.prepareStatement(queryInput);
                ResultSet rs = pre.executeQuery();
                ArrayList<String> us = new ArrayList<>();
                System.out.println("****USUARIOS****");
                int contador = 0;
                while (rs.next()) {
                    String usuario = rs.getString("usuario");

                    us.add(usuario + " \n");
                    contador++;

                }
                String message = "";
                for (String usuario : us) {
                    message += usuario;
                }
                scroll.setMessage(message);
                JOptionPane.showMessageDialog(null, scroll.getJScrollPane(), "Encontrados: " + contador, JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e1) {

                System.out.println("[-] No se ha podido realizar la conexión");
                e1.printStackTrace();
            }
        }
        //BOTON INSERTAR
        if (e.getSource() == UPDATE) {
            AdministradoresGUI.refreshBD();

            JOptionPane.showMessageDialog(null, "Tabla actualizada ✔");

        }

        if (e.getSource() == DELETE) {

            try {
                Connection con = MetodosToSql.establecerConexion();
                int selectedRow = table.getSelectedRow();

                int option = JOptionPane.showConfirmDialog(null, "Seguro?", "⚠⚠⚠Advertencia⚠⚠⚠", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (selectedRow != -1 && option == JOptionPane.YES_OPTION) {

                    //obtenemos el valor del id de la filtracion seleccionada
                    int idFiltracion = (int) table.getValueAt(selectedRow, 0);

                    String queryDelete = "DELETE FROM filtraciones WHERE id_filtracion = ?";

                    PreparedStatement pre = con.prepareStatement(queryDelete);

                    pre.setInt(1, idFiltracion);
                    pre.executeUpdate();

                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Filtración eliminada✔✔✔✔");

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        if (e.getSource() == HISTORY) {

            String message = "";
            Scroll scrollHistory = new Scroll();
            try {
                Connection con = MetodosToSql.establecerConexion();
                String queryHistory = "SELECT * FROM historial";
                PreparedStatement pre = con.prepareStatement(queryHistory);
                ResultSet rs = pre.executeQuery();

                while (rs.next()) {
                    int idHistorial = rs.getInt(1);
                    Date date = rs.getDate(2);
                    String cambios = rs.getString(3);
                    int idAdmin = rs.getInt(4);

                    message
                            += "\nid historial -> " + idHistorial
                            + "\nfecha: " + date
                            + "\ncambios: " + cambios
                            + "\nid admin: " + idAdmin
                            + "\n--------------------";

                }
                scrollHistory.setMessage(message);
                JScrollPane sc = scrollHistory.getJScrollPane();
                JOptionPane.showMessageDialog(null, sc, "Historial cambios", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ee) {
                ee.printStackTrace();
            }

        }

    }

    //METODO PARA USAR LA TABLA EN OTRAS CLASES
    public static DefaultTableModel getModel() {
        return model;
    }

    //METODO QUE REFRESCA LA TABLA AL INICIALIZAR ADMINISTRADORESGUI
    public static void refreshBD() {

        try {

            Connection con = MetodosToSql.establecerConexion();
            String queryActualizar = "SELECT  id_filtracion, plataforma, fecha, numero_afectados, descripcion, medidas FROM FILTRACIONES";

            PreparedStatement pre = con.prepareStatement(queryActualizar);

            ResultSet rs = pre.executeQuery();

            while (rs.next()) {

                int idFiltracion = rs.getInt("id_filtracion");
                String plataforma = rs.getString("plataforma");
                Date fecha = rs.getDate("fecha");
                int numerosAfecatos = rs.getInt("numero_afectados");
                String descripcion = rs.getString("descripcion");
                String medidas = rs.getString("medidas");

                //Validacion para que no actualice filtraciones ya existentes en la tabla
                boolean existe = false;
                //obtiene el id de todas filas y las compara con el id filtration
                for (int i = 0; i < model.getRowCount(); i++) {
                    int id = (int) model.getValueAt(i, 0);
                    if (id == idFiltracion) {
                        existe = true;

                        model.setValueAt(plataforma, i, 1);  // Columna de plataforma
                        model.setValueAt(fecha, i, 2);       // Columna de fecha
                        model.setValueAt(numerosAfecatos, i, 3); // Columna de número de afectados
                        model.setValueAt(descripcion, i, 4);  // Columna de descripción
                        model.setValueAt(medidas, i, 5);     // Columna de medidas
                        break;
                    }
                }
                //si el id no exite,crea un objeto en la tabla
                if (!existe) {
                    Object[] row = new Object[]{
                        idFiltracion, plataforma, fecha, numerosAfecatos, descripcion, medidas

                    };

                    model.addRow(row);

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
