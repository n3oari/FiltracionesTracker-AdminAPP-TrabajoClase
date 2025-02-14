package t8_practica_filtraciones;

import java.awt.*;

import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AdministradoresGUI extends GUI implements ActionListener {

    private JLabel insertFiltration;
    private JTextField insertFiltrationT;

    private JButton insert;
    private JButton query;
    private JButton actualizar;
    private JButton delete;
    private JButton limpiar;
    private JButton modificar;
    protected Connection con;

    public AdministradoresGUI() {

        super();
        this.setTitle("ADMINISTRADORES ｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕｡ 🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕🗲｡◕‿‿◕ ");

        insert = new JButton("Insertar filtración ");
        insert.setBounds(0, 50, 150, 50);
        insert.addActionListener(this);
        insert.setBackground(Color.GREEN);
        layout.add(insert);

        query = new JButton("Realizar consulta");
        query.setBounds(0, 100, 150, 50);
        query.addActionListener(this);
        query.setBackground(Color.GREEN);
        layout.add(query);

        actualizar = new JButton("Actualizar");
        actualizar.setBounds(0, 150, 150, 50);
        actualizar.setBackground(Color.GREEN);
        actualizar.addActionListener(this);
        layout.add(actualizar);

        delete = new JButton("Borrar");
        delete.setBounds(150, 200, 150, 50);
        delete.setBackground(Color.GREEN);
        delete.addActionListener(this);
        layout.add(delete);

        limpiar = new JButton("Limpiar");
        limpiar.setBounds(0, 250, 150, 50);
        limpiar.setBackground(Color.green);
        limpiar.addActionListener(e -> model.setRowCount(0));
        layout.add(limpiar);

        modificar = new JButton("Modificar");
        modificar.setBounds(0, 350, 150, 50);
        modificar.setBackground(Color.green);
        //  actualizar.addActionListener(e -> MetodosToSql.updateQuery());
        modificar.addActionListener(this);
        layout.add(modificar);

    }

    @Override

    public void actionPerformed(ActionEvent e) {

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
                while (rs.next()) {
                    String usuario = rs.getString("usuario");

                    us.add("[*]" + usuario + " \n");

                    System.out.println("usuario -> " + usuario);
                }
                String saltoLinea = us.toString() + "\n";
                JOptionPane.showMessageDialog(null, saltoLinea);

            } catch (SQLException e1) {

                System.out.println("[-] No se ha podido realizar la conexión");
                e1.printStackTrace();

                /*
                    
                SELECT u.usuario FROM usuarios u
                JOIN filtraciones f
                ON u.id_filtracion = f.id_filtracion
                WHERE f.fecha < '2030-01-01'
                 */
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
                            break;
                        }
                    }
                    //si el id no exite,crea un objeto en la tabla
                    if (!existe) {
                        Object[] row = new Object[]{
                            idFiltracion, plataforma, fecha, numerosAfecatos, descripcion, medidas

                        };

                        model.addRow(row);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "La filtración con el ID " + idFiltracion + " ya está registrada en la tabla.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (SQLException e2) {

                System.out.println("[-] No se ha podido realizar la conexión");
                e2.printStackTrace();

            }
        }
        //BOTON BORRAR  !!!!!!!!!FALTA QUE ELIMINE LA BASE DE DATOS TAMBIEN!!!!!!!!!!!!!
        if (e.getSource() == delete) {
            try {
                MetodosToSql.establecerConexion();
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    model.removeRow(selectedRow); // Eliminar la fila seleccionada
                } else {
                    JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e7) {
                e7.printStackTrace();
            }
            /*
                    int idFiltracionInput = Integer.parseInt(JOptionPane.showInputDialog("Introduce el id de la filtración que deseas eliminar"));

                    String deleteQuery = "DELETE FROM FILTRACIONES WHERE id_filtracion = " + idFiltracionInput;

                    PreparedStatement pre = con.prepareStatement(deleteQuery);
                    pre.executeUpdate();

                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
             */
        }
        if (e.getSource() == modificar) {
            System.out.println("ddddd");

            try {
                MetodosToSql.establecerConexion();
                System.out.println("lsdl");
                MetodosToSql.updateQuery(con);

            } catch (SQLException e4) {
                e4.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {

        new AdministradoresGUI();

        try {
            MetodosToSql.establecerConexion();
        } catch (SQLException ex) {
            Logger.getLogger(AdministradoresGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
