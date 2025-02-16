package t8_practica_filtraciones;

import java.awt.*;

import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdministradoresGUI extends GUI implements ActionListener {

    private JButton insert;
    private JButton query;
    private JButton actualizar;
    private JButton delete;
    private JButton limpiar;
    private JButton modificar;
    protected Connection con;

    //TABLE
    public AdministradoresGUI() {

        super();
        this.setTitle("ADMINISTRADORES ｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕ ");
        layout.add(scroll);

        this.setSize(780, 450);

        layout.setBackground(Color.blue);
        insert = new JButton("Añadir filtración ");
        insert.setBounds(0, 0, 130, 50);
        insert.addActionListener(this);
        insert.setBackground(Color.black);
        insert.setForeground(Color.blue);
        layout.add(insert);

        query = new JButton("Query");
        query.setBounds(130, 0, 130, 50);
        query.addActionListener(this);
        query.setBackground(Color.black);
        query.setForeground(Color.blue);
        layout.add(query);

        actualizar = new JButton("Actualizar");
        actualizar.setBounds(260, 0, 130, 50);
        actualizar.setBackground(Color.black);
        actualizar.setForeground(Color.blue);
        actualizar.addActionListener(this);
        layout.add(actualizar);

        delete = new JButton("Borrar");
        delete.setBounds(390, 0, 130, 50);
        delete.setBackground(Color.black);
        delete.setForeground(Color.blue);
        delete.addActionListener(this);
        layout.add(delete);

        limpiar = new JButton("Limpiar");
        limpiar.setBounds(520, 0, 130, 50);
        limpiar.setBackground(Color.black);
        limpiar.setForeground(Color.blue);
        limpiar.addActionListener(e -> model.setRowCount(0));
        layout.add(limpiar);

        modificar = new JButton("Modificar");
        modificar.setBounds(650, 0, 130, 50);
        modificar.setBackground(Color.black);
        modificar.setForeground(Color.blue);
        //  actualizar.addActionListener(e -> MetodosToSql.updateQuery());
        modificar.addActionListener(this);
        layout.add(modificar);

    }

    @Override

    public void actionPerformed(ActionEvent e) {

        Scroll scroll = new Scroll();

        //LLAMADA AL METODO PARA CREAR CONEXION
        if (e.getSource() == insert) {

            try {
                Connection con = MetodosToSql.establecerConexion();
                MetodosToSql.añadirFiltracion(con, model);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == query) {

            System.out.println("boton pulsado");
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
        if (e.getSource() == actualizar) {
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

            } catch (SQLException e2) {

                System.out.println("[-] No se ha podido realizar la conexión");
                e2.printStackTrace();

            }
        }

        if (e.getSource() == delete) {
            try {
                Connection con = MetodosToSql.establecerConexion();
                int selectedRow = table.getSelectedRow();

                int option = JOptionPane.showConfirmDialog(null, "Seguro?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (selectedRow != -1 && option == JOptionPane.YES_NO_OPTION) {

                    //obtenemos el valor del id de la filtracion seleccionada
                    int idFiltracion = (int) table.getValueAt(selectedRow, 0);

                    String queryDelete = "DELETE FROM filtraciones WHERE id_filtracion = ?";
                    try {
                        PreparedStatement pre = con.prepareStatement(queryDelete);

                        pre.setInt(1, idFiltracion);
                        pre.executeUpdate();

                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "Filtración eliminada");

                    } catch (SQLException exx) {
                        exx.printStackTrace();
                    }

                }

            } catch (SQLException e7) {
                e7.printStackTrace();
            }

        }
        if (e.getSource() == modificar) {
            System.out.println("ddddd");

            try {
                Connection con = MetodosToSql.establecerConexion();
                System.out.println("lsdl");
                MetodosToSql.updateQuery();

            } catch (SQLException e4) {
                e4.printStackTrace();
            }

        }

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
