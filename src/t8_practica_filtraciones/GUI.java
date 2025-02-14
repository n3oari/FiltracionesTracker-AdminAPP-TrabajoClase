package t8_practica_filtraciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

//CLASE SUPER QUE DE ELLA HEREDARAN USERSGUI Y ADMINSGUI
public class GUI extends JFrame  {

    protected final int WIDTH = 1000;
    protected final int HEIGHT = 450;
    protected final int labelWidth = 180;
    protected final int labelHeight = 50;
    protected JPanel layout;
    protected Image img;
    protected JLabel[] labels;
    protected JLabel imgL;

    protected ImageIcon imgg;

    //BOTONES EN COMUN
    protected JButton delete;
    protected JButton exit;

    //TABLE
    protected JTable table;
    protected DefaultTableModel model;
    protected JScrollPane scroll;
    protected String[] header;
    protected Object[][] data;

 

    public GUI() {

        this.setTitle("GUI super ");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layout = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibuja la imagen redimensionada para llenar toda la ventana
                if (imgg != null) {
                    g.drawImage(imgg.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        //LAYOUT
        layout = new JPanel();
        layout.setBackground(java.awt.Color.RED);
        layout.setLayout(null);
        this.add(layout);

        System.out.println("Directorio de trabajo: " + System.getProperty("user.dir"));

        File f = new File("img/ww.png");
        imgg = new ImageIcon(f.getAbsolutePath());

        imgL = new JLabel(imgg);
        imgL.setBounds(0, 0, WIDTH, HEIGHT);  // Establecer la ubicación y el tamaño de la imagen
        layout.add(imgL);  // Añadir el JLabel con la imagen al layout

        JButton exit = new JButton("SALIR");
        exit.setBounds(0, 0, 100, 50);
        exit.addActionListener(e -> System.exit(0));
        layout.add(exit);

        //HEADER
        header = new String[]{
            "id_filtracion", "plataforma", "fecha", "nº afectados",
            "descripcion", "medidas"
        };
        data = new Object[0][6];

        model = new DefaultTableModel(data, header);

        table = new JTable(model);
        table.setBackground(Color.RED);

        //SCROLL
        scroll = new JScrollPane(table);
        scroll.setBounds(400, 0, 500, 800);
        scroll.setBackground(Color.BLUE);
        layout.add(scroll);
        
        //BOTON CERRAR
        
        exit = new JButton("Salir");
        exit.setBounds(0, 200, 150, 50);
        exit.setBackground(Color.green);
        exit.addActionListener(e -> System.exit(0));
        layout.add(exit);

        this.setVisible(true);
    }

    //metodo que devuelve el model (para usarlo en otras clases)
    public DefaultTableModel getModel() {
        return model;
    }
    
    
    
  

    public static void main(String[] args) {

        new GUI();

    }

   

}
