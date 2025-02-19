package t8_practica_filtraciones;

import java.awt.*;

import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdministradoresGUI extends GUI implements ActionListener {

    final private JButton INSERT;
    final private JButton QUERY;
    final private JButton UPDATE;
    final private JButton DELETE;
    final private JButton CLEAN;
    final private JButton MODIFY;

    public AdministradoresGUI() {

        super();
        this.setTitle("ADMINISTRADORES ｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕ ");
        layout.add(scroll);

        this.setSize(780, 450);

        layout.setBackground(Color.GREEN);

        //BOTON AÑADIR FILTRACION
        INSERT = new JButton("Añadir filtración ");
        INSERT.setBounds(0, 0, 130, 50);
        INSERT.addActionListener(this);
        INSERT.setBackground(Color.black);
        INSERT.setForeground(Color.blue);
        layout.add(INSERT);
        //BOTON REALIZAR QUERY
        QUERY = new JButton("Query");
        QUERY.setBounds(130, 0, 130, 50);
        QUERY.addActionListener(this);
        QUERY.setBackground(Color.black);
        QUERY.setForeground(Color.blue);
        layout.add(QUERY);
        //BOTON ACTUALIZAR
        UPDATE = new JButton("Actualizar");
        UPDATE.setBounds(260, 0, 130, 50);
        UPDATE.setBackground(Color.black);
        UPDATE.setForeground(Color.blue);
        UPDATE.addActionListener(this);
        layout.add(UPDATE);
        //BOTON BORRAR
        DELETE = new JButton("Borrar");
        DELETE.setBounds(390, 0, 130, 50);
        DELETE.setBackground(Color.black);
        DELETE.setForeground(Color.blue);
        DELETE.addActionListener(this);
        layout.add(DELETE);
        //BOTON LIMPIAR
        CLEAN = new JButton("Limpiar");
        CLEAN.setBounds(520, 0, 130, 50);
        CLEAN.setBackground(Color.black);
        CLEAN.setForeground(Color.blue);
        CLEAN.addActionListener(e -> model.setRowCount(0));
        layout.add(CLEAN);
        //BOTON MODIFICAR
        MODIFY = new JButton("Modificar");
        MODIFY.setBounds(650, 0, 130, 50);
        MODIFY.setBackground(Color.black);
        MODIFY.setForeground(Color.blue);
        MODIFY.addActionListener(this);
        MODIFY.addActionListener(e -> {
            try {
                MetodosToSql.modifyQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        layout.add(MODIFY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Scroll scroll = new Scroll(); //intanciar clase scroll para crear joptionpane de gran tamaño

        if (e.getSource() == INSERT) {

            try {

                MetodosToSql.añadirFiltracion(model);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == QUERY) {

            String queryInput = JOptionPane.showInputDialog("Introduce la query");
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

                    us.add("[*]" + usuario + " \n");
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
            System.out.println("Botón presionado");

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
                JOptionPane.showMessageDialog(null, "Tabla actualizada ✔");

            } catch (SQLException e2) {

                System.out.println("[-] No se ha podido realizar la conexión");
                e2.printStackTrace();

            }
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
                    try {
                        PreparedStatement pre = con.prepareStatement(queryDelete);

                        pre.setInt(1, idFiltracion);
                        pre.executeUpdate();

                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "Filtración eliminada✔✔✔✔");

                    } catch (SQLException exx) {
                        exx.printStackTrace();
                    }

                } else if (selectedRow == -1) {
                    JOptionPane.showInternalMessageDialog(null, "Selecciona una celda", "❌", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e7) {
                e7.printStackTrace();
            }

        }
        /*
        if (e.getSource() == MODIFY) {
           
            try {

                MetodosToSql.modifyQuery();

            } catch (SQLException e4) {
                e4.printStackTrace();
            }

        }
*/
    }

    //metodo que devuelve el model (para usarlo en otras clases)

    public DefaultTableModel getModel() {
        return model;
    }

    public static void main(String[] args) {

        new AdministradoresGUI();

        try {
            MetodosToSql.establecerConexion();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
