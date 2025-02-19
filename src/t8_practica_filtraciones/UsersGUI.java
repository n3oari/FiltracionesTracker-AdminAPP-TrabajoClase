package t8_practica_filtraciones;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class UsersGUI extends GUI implements ActionListener {

    private JButton accederComoAdministrador;
    private JButton inputCredenciales;
   

    public UsersGUI() {

        super();

        this.setTitle("GUI USUARIO ¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯"
                + "¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯");

        accederComoAdministrador = new JButton("Acceso admin");
        accederComoAdministrador.setBounds(270, 375, 200, 50);
        accederComoAdministrador.addActionListener(this);
        accederComoAdministrador.setBackground(Color.BLACK);
        accederComoAdministrador.setForeground(Color.red);
        layout.add(accederComoAdministrador);

        inputCredenciales = new JButton("Buscar credenciales");
        inputCredenciales.setBounds(0, 375, 200, 50);
        inputCredenciales.addActionListener(this);
        inputCredenciales.setBackground(Color.BLACK);
        inputCredenciales.setForeground(Color.red);
        layout.add(inputCredenciales);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String message = "";

        if (e.getSource() == accederComoAdministrador) {

            String usuarioAdmin = JOptionPane.showInputDialog("Introduce usuario");
            String contraseñaAdmin = JOptionPane.showInputDialog("Introduce contraseña");

            try {
                Connection con = MetodosToSql.establecerConexion();

                String queryCheck = "SELECT usuario, contraseña FROM ADMINISTRADORES WHERE usuario = ? AND contraseña = ?";

                PreparedStatement pre = con.prepareStatement(queryCheck);

                pre.setString(1, usuarioAdmin);
                pre.setString(2, contraseñaAdmin);

                ResultSet rs = pre.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "[+]Adminitrador verficado exitosamente ");
                    JOptionPane.showMessageDialog(null, ".........entrando como administrador");

                    new AdministradoresGUI();

                } else {
                    JOptionPane.showMessageDialog(null, "....Usuario o contraseña incorrectos");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();

            }

        }

        if (e.getSource() == inputCredenciales) {

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
                        String queryCorreo = "SELECT c.usuario, f.plataforma,f.fecha FROM CREDENCIALES c "
                                + "JOIN filtraciones f ON c.id_filtracion = f.id_filtracion "
                                + "WHERE correo = ?";
                        PreparedStatement pre = con.prepareStatement(queryCorreo);
                        pre.setString(1, correo);

                        ResultSet rs = pre.executeQuery();

                        while (rs.next()) {

                            String usuario = rs.getString("usuario");
                            String plata = rs.getString("plataforma");
                            String fecha = rs.getString("fecha");

                            message += "\n[*]Usuario -> " + usuario + "\n[*]Plataforma -> " + plata + "\n[*]Fecha -> " + fecha + "\n";

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
                        String queryContraseña = "SELECT c.usuario, f.plataforma,f.fecha FROM CREDENCIALES c "
                                + "JOIN filtraciones f ON c.id_filtracion = f.id_filtracion "
                                + "WHERE contraseña = ?";
                        PreparedStatement pre2 = con.prepareStatement(queryContraseña);
                        pre2.setString(1, contraseña);

                        ResultSet rs2 = pre2.executeQuery();
                        while (rs2.next()) {

                            String usuario = rs2.getString("usuario");
                            String plata = rs2.getString("plataforma");
                            String fecha = rs2.getString("fecha");

                            JOptionPane.showMessageDialog(null,
                                    "!!!!!!!!Tus credenciales han sido comprometidas⚠⚠⚠⚠⚠⚠⚠!!!!!!!!"
                                    + "\n[*]Usuario -> " + usuario + "\n[*]Plataforma -> " + plata + "\n[*]Fecha -> " + fecha);

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
    }

    public static void main(String[] args) {

        new UsersGUI();
    }

}
