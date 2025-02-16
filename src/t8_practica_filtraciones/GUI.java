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
public class GUI extends JFrame {

    protected JPanel layout;
    protected Image img;

    protected JLabel imgL;
    protected JLabel i;

    protected ImageIcon imgg;
    protected ImageIcon imgg2;

    protected JButton exit;
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
            "descripcion", "medidas"
        };

        data = new Object[0][6];

        model = new DefaultTableModel(data, header);

        table = new JTable(model);
        table.setBackground(Color.blue);
        table.getGridColor();
        table.setGridColor(Color.black);
        table.setEnabled(true); //desactiova editar las celdas
        table.getTableHeader().setReorderingAllowed(false); //desactiva poder mover las columnas
        //  layout.add(table);

        //SCROLL
        scroll = new JScrollPane(table);

        scroll.setBounds(0, 200, 780, 400);
        scroll.setBackground(Color.BLUE);

        this.setVisible(true);
    }

    public static void main(String[] args) {

        new GUI();

    }

}
