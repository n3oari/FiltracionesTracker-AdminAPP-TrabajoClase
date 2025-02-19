package t8_practica_filtraciones;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.table.DefaultTableModel;

//CLASE SUPER QUE DE ELLA HEREDARAN USERSGUI Y ADMINSGUI
public class GUI extends JFrame {

    protected JPanel layout;
    protected JLabel imgL;
    protected JButton exit;
    protected ImageIcon imgg;
    protected Image img;
    //TABLA
    protected JTable table;
    protected DefaultTableModel model;
    protected JScrollPane scroll;
    protected String[] header;
    protected Object[][] data;

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
        JButton exit = new JButton("Salir");
        exit.setBounds(545, 375, 200, 50);
        exit.setBackground(Color.black);
        exit.setForeground(Color.red);
        exit.addActionListener(e -> System.exit(0));
        layout.add(exit);
        //HEADER
        header = new String[]{
            "id_filtracion", "plataforma", "fecha", "nº afectados",
            "descripción", "medidas"
        };
        data = new Object[0][6];
        model = new DefaultTableModel(data, header);

        table = new JTable(model);
        table.setBackground(Color.black);
        table.setForeground(Color.white);
        
     //   table.getGridColor();
        table.setGridColor(Color.green);
        
        table.setEnabled(true); //desactiova editar las celdas
        table.getTableHeader().setReorderingAllowed(false); //desactiva poder mover las columnas
        //SCROLL
        scroll = new JScrollPane(table);
        scroll.setBounds(0, 200, 780, 400);
        
        scroll.setForeground(Color.yellow);

        this.setVisible(true);
    }
    
      
    public static void main(String[] args) {

        new GUI();

    }

}
