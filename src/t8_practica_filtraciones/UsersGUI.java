package t8_practica_filtraciones;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class UsersGUI extends GUI implements ActionListener {

    final private JButton ACCEDERCOMOADMIN;
    final private JButton INPUTCREDENCIALES;
    final private JButton SHOWCOUNT;
    final private JButton EXIT;

    public UsersGUI() {

        super();

        this.setTitle("\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯"
                + "¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯");

        ACCEDERCOMOADMIN = new JButton("Acceso admin");
        ACCEDERCOMOADMIN.setBounds(270, 375, 200, 50);
        ACCEDERCOMOADMIN.addActionListener(e -> MetodosToSql.idAdminLogin());
        ACCEDERCOMOADMIN.setBackground(Color.BLACK);
        ACCEDERCOMOADMIN.setForeground(Color.red);
        layout.add(ACCEDERCOMOADMIN);

        INPUTCREDENCIALES = new JButton("Buscar credenciales");
        INPUTCREDENCIALES.setBounds(0, 375, 200, 50);
        INPUTCREDENCIALES.addActionListener(this);
        INPUTCREDENCIALES.setBackground(Color.BLACK);
        INPUTCREDENCIALES.setForeground(Color.red);
        layout.add(INPUTCREDENCIALES);

        SHOWCOUNT = new JButton("Contador");
        SHOWCOUNT.setBounds(550, 0, 200, 50);
        SHOWCOUNT.addActionListener(this);
        SHOWCOUNT.setBackground(Color.BLACK);
        SHOWCOUNT.setForeground(Color.red);
        layout.add(SHOWCOUNT);

        EXIT = new JButton("Salir");
        EXIT.setBounds(545, 375, 200, 50);
        EXIT.setBackground(Color.black);
        EXIT.setForeground(Color.RED);
        EXIT.addActionListener(e -> System.exit(0));

        layout.add(EXIT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String message = "";

        if (e.getSource() == INPUTCREDENCIALES) {

            Scroll scroll = new Scroll();
            boolean credencialEncontrada = false;
            try {
                Connection con = MetodosToSql.establecerConexion();

                int opcion = Integer.parseInt(JOptionPane.showInputDialog(
                        "Elige una opcion: "
                        + "\n[1]Buscar por correo"
                        + "\n[2]Buscar por contraseña"));

                switch (opcion) {
                    case 1:
                        String correo = JOptionPane.showInputDialog("Introduce correo");
                        String queryCorreo = "SELECT  f.plataforma,f.fecha FROM CREDENCIALES c "
                                + "JOIN filtraciones f ON c.id_filtracion = f.id_filtracion "
                                + "WHERE correo = ?";
                        PreparedStatement pre = con.prepareStatement(queryCorreo);
                        pre.setString(1, correo);

                        ResultSet rs = pre.executeQuery();

                        while (rs.next()) {

                            credencialEncontrada = true;
                            String plata = rs.getString("plataforma");
                            Date fecha = rs.getDate("fecha");

                            message += " \n[*]Plataforma -> " + plata + "\n[*]Fecha -> " + fecha + "\n-------------";

                        }
                        scroll.setMessage(message);

                        if (!credencialEncontrada) {
                            JOptionPane.showMessageDialog(null, "Tus credenciales no han sido comprometidas ✔");

                        } else {

                            JOptionPane.showMessageDialog(null, scroll.getJScrollPane(), "Credenciales comprometidas ⚠ ⚠   ", JOptionPane.WARNING_MESSAGE);
                            credencialEncontrada = true;

                        }
                        break;
                    case 2:
                        String contraseña = JOptionPane.showInputDialog("Introduce contraseña");
                        String queryContraseña = "SELECT   f.plataforma,f.fecha FROM CREDENCIALES c "
                                + "JOIN filtraciones f ON c.id_filtracion = f.id_filtracion "
                                + "WHERE contraseña = ?";
                        PreparedStatement pre2 = con.prepareStatement(queryContraseña);
                        pre2.setString(1, contraseña);

                        ResultSet rs2 = pre2.executeQuery();
                        while (rs2.next()) {

                            String plata = rs2.getString("plataforma");
                            String fecha = rs2.getString("fecha");

                            JOptionPane.showMessageDialog(null,
                                    "!!!!!!!!Tus credenciales han sido comprometidas⚠⚠⚠⚠⚠⚠⚠!!!!!!!!"
                                    + "0\n[*]Plataforma -> " + plata + "\n[*]Fecha -> " + fecha);

                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

       if (e.getSource() == SHOWCOUNT) {

    Connection con;
    try {
        // Establecer la conexión
        con = MetodosToSql.establecerConexion();

        // Consulta SQL para contar las columnas usuario, contraseña, correo y telefono
        String queryCount = "SELECT "
                + "    COUNT(usuario) AS countUsuario, "
                + "    COUNT(correo) AS countCorreo, "
                + "    COUNT(contraseña) AS countContraseña, "
                + "    COUNT(telefono) AS countTelefono "
                + "FROM credenciales";

        // Preparar la declaración
        PreparedStatement pre = con.prepareStatement(queryCount);

        // Ejecutar la consulta y obtener el resultado
        ResultSet rs = pre.executeQuery();

        // Procesar el resultado
        while (rs.next()) {
            // Obtener los valores de cada columna
            int contadorUsuario = rs.getInt("countUsuario");
            int contadorCorreo = rs.getInt("countCorreo");
            int contadorContraseña = rs.getInt("countContraseña");
            int contadorTelefono = rs.getInt("countTelefono");

            // Mostrar los resultados
            JOptionPane.showMessageDialog(null,   contadorUsuario +
                     + contadorCorreo 
                     + contadorContraseña 
                    + contadorTelefono);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    }

    public static void main(String[] args) {

        new UsersGUI();
    }

}
