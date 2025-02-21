package t8_practica_filtraciones;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.table.DefaultTableModel;

//CLASE SUPER QUE DE ELLA HEREDARAN USERSGUI Y ADMINSGUI
public class GUI extends JFrame {

    protected JPanel layout;
    protected JLabel imgL;
    protected ImageIcon imgg;
    protected Image img;
    //TABLA
 

    public GUI() {

        this.setTitle("GUI super ");
        this.setSize(750, 450);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layout = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                if (imgg != null) {
                    g.drawImage(imgg.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        layout.setBackground(Color.RED);
        layout.setLayout(null);
        this.add(layout);

        File f = new File("img/ww.png");
        imgg = new ImageIcon(f.getAbsolutePath());

        //BOTON CERRAR
        JButton SALIR = new JButton("Salir");
        
       

        this.setVisible(true);
        
        
    }
    
    
    //METODO PARA USAR LA TABLA EN OTRAS CLASES
   
    
    
      
    public static void main(String[] args) {

        new GUI();

    }

}
