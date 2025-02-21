package t8_practica_filtraciones;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class UsersGUI extends GUI implements ActionListener {

    final private JButton ACCEDERCOMOADMIN;
    final private JButton INPUTCREDENCIALES;
    final private JButton SHOWALL;
    final private JButton EXIT;

    public UsersGUI() {
        
       

        super();

        this.setTitle("GUI USUARIO ¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯¯\\_( ͡° ͜ʖ ͡°)_/¯"
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
        
        SHOWALL = new JButton("Contador");
        SHOWALL.setBounds(375, 0, 200, 50);
        SHOWALL.addActionListener(this);
        SHOWALL.setBackground(Color.BLACK);
        SHOWALL.setForeground(Color.red);
        layout.add(SHOWALL);
        
        

        layout.add(INPUTCREDENCIALES);

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
                        String queryCorreo = "SELECT DISTINCT f.plataforma,f.fecha FROM CREDENCIALES c "
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
                        String queryContraseña = "SELECT  DISTINCT f.plataforma,f.fecha FROM CREDENCIALES c "
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
        if(e.getSource()==SHOWALL){
            
             try {
                Connection con = MetodosToSql.establecerConexion();
            
            String showAllQuery = "SELECT COUNT(usuario) AS total FROM credenciales";
            
            PreparedStatement pre = con.prepareStatement(showAllQuery);
            
            ResultSet rs = pre.executeQuery();
            
            while(rs.next()){
                
                int contador = rs.getInt("total");
                
                JOptionPane.showMessageDialog(null, contador);
            }
            
        }catch(SQLException eee){
            eee.printStackTrace();
        }
        
    }
    }
    public static void main(String[] args) {

        new UsersGUI();
    }

}
