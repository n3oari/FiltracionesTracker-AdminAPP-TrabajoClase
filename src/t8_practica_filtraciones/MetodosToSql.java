package t8_practica_filtraciones;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import jdk.nashorn.internal.ir.BreakNode;

public class MetodosToSql {

    private static int afectados;
    private static int idAdmin = -1;
    private static String adminName;

    /*---------------------METODOS INDEX----------------------
    0 - SETTETS Y GETTERS ID ADMIN
    1 - CREAR CONEXION
    2 - GENEAR NUMEROS DE TELEFONO
    3 - LEER DICCIONARIOS(ARCHIVOS) Y METERLOS EN LINKEDLINKS
    4 - AÑADIR FILTRACION A LA BD -> LLAMA AL 5
    5-  AÑADIR USUARIOS A LA BD
    6 - CUENTA LA CANTIDAD DE  LINEAS DE UN FILE 
    7 - GUARDAR ID ADMIN QUE SE LOGUEA 
    8 - QUERY PARA MODIFICAR TABLE + BD (USA EL ID DEL METODO 7)
    ----------------------------------------------------------
     */
    //0 SETTERS Y GETTERS ID ADMIN / NOMBRE ADMIN
    public static void setIdAdmin(int id) {
        idAdmin = id;
    }

    public static int getIdAdmin() {
        return idAdmin;
    }

    public static void setAdminName(String name) {
        adminName = name;

    }

    public static String getAdminName() {

        return adminName;
    }

    // 1 -> METODO CREAR UNA CONEXION
    public static Connection establecerConexion() throws SQLException {

        final String URL = "jdbc:mysql://localhost:3306/filtraciones";
        final String USER = "root";
        final String PASS = "";

        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("[+]...........Conexión establecida con éxito");
        } catch (SQLException e) {
            System.out.println("[-] No se ha podido realizar la conexión");
            e.printStackTrace();
        }
        return con;
    }

    // 2 -> METODO PARA GENERAR NUMEROS DE TELEFONO Y AÑADIR A UN LINKEDLIST
// HE USADO LINKEDLIST EN VEZ DE ARRAYLIST PORQUE EL MAXIMO DE ELEMENTOS EN UN ARRAYLIST ES 100000
    /*
          
    public static LinkedList<Integer> generarNum(int afectados) {
        LinkedList<Integer> listaNums = new LinkedList<>();
        int primerNum = 600000000; // con esto se consigue que el primer número siempre sea un 6
        for (int i = 0; i < afectados; i++) {
            int num = (int) (Math.random() * 99999999);
            listaNums.add(primerNum + num);
        }
        System.out.println("[+] Números de teléfonos creados correctamente. nº números creados " + afectados);
        return listaNums;
    }
     */
    // 3 -> METODO PARA LEER ARCHIVOS Y METERLOS EN UN LINKEDLIST DE <USUARIO><EMAILS><CONTRASEÑAS>
    public static LinkedList<String> leerArchivo(File archivo, int afectados) throws FileNotFoundException, IOException {
        LinkedList<String> lista = new LinkedList<>();
        if (archivo.exists()) {
            System.out.println("!!! El diccionario existe !!!");
        }

        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        int count = 0;

        while ((linea = br.readLine()) != null && count < afectados) {
            lista.add(linea);
            count++;
        }
        br.close();
        System.out.println("[+] Elementos creados correctamente..... nº elementos creados " + count);
        return lista; // Devuelve la cantidad de lines en el File 
    }

    // 4 -> METODO QUE AÑADE UNA FILTRACION A LA TABLE + BD


    // 6 -> METODO QUE CUENTA LAS LINEAS DE UN ARCHIVO
    public static int contarDiccionario(File diccionario) throws FileNotFoundException, IOException {
        int contador = 0;
        FileReader fr = new FileReader(diccionario);
        BufferedReader br = new BufferedReader(fr);

        String linea = "";
        while ((linea = br.readLine()) != null) {
            contador++;
        }
        System.out.println("Total de líneas en " + diccionario.getName() + ": " + contador); // Depuración
        return contador;
    }

    // 7 -> METODO PARA OBTENER EL ID DEL ADMINISTRADOR AL LOGEARSE (Y USARLO EN OTROS METODOS / FUTURO)
    public static int idAdminLogin() {

        int idAdmin = -1;

        String usuarioAdmin = JOptionPane.showInputDialog("Introduce usuario");
        String contraseñaAdmin = JOptionPane.showInputDialog("Introduce contraseña");

        try {
            Connection con = establecerConexion();
            String queryGetId = "SELECT id_administrador,usuario FROM administradores WHERE usuario =? AND contraseña =?";
            PreparedStatement pre = con.prepareStatement(queryGetId);
            pre.setString(1, usuarioAdmin);
            pre.setString(2, contraseñaAdmin);

            ResultSet rs = pre.executeQuery(); //obtenemos el id

            if (rs.next()) {

                JOptionPane.showMessageDialog(null, "[+]Adminitrador verficado exitosamente ");
                JOptionPane.showMessageDialog(null, ".........entrando como administrador");
                idAdmin = rs.getInt("id_administrador");
                adminName = rs.getString("usuario");
                //  JOptionPane.showMessageDialog(null, "id admin generado " + idAdmin);
                MetodosToSql.setAdminName(adminName);
                MetodosToSql.setIdAdmin(idAdmin);

                new AdministradoresGUI();
                AdministradoresGUI.refreshBD(); //actualiza la tabla

            } else {
                JOptionPane.showMessageDialog(null, "....Usuario o contraseña incorrectos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idAdmin;

    }

    // 8 -> METODO PARA MODIFICAR LA TABLE + LA BD
    public static void modifyQuery() throws SQLException {

        int idAdmin = MetodosToSql.getIdAdmin();
        String cambios;

        try {
            Connection con = establecerConexion();

            int idInput = Integer.parseInt(JOptionPane.showInputDialog("Introduce el id de la filtración que deseas eliminar"));
            int opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "Selecciona "
                    + "\n[1] modificar plataforma "
                    + "\n[2] modificar fecha"
                    + "\n[3] modificar descripcion"
                    + "\n[4] modificar medidas"));

            String queryUpdate;
            String queryHistory = "INSERT INTO HISTORIAL (cambios, id_administrador) VALUES (?, ?);";
            PreparedStatement pre = null;
            switch (opcion) {
                case 1:
                    String nuevaPlataforma = JOptionPane.showInputDialog("Introduce nueva plataforma");
                    queryUpdate = "UPDATE FILTRACIONES SET plataforma = ?  WHERE id_filtracion = ?";
                    pre = con.prepareStatement(queryUpdate);
                    pre.setString(1, nuevaPlataforma);
                    pre.setInt(2, idInput);
                    pre.executeUpdate();
                    pre.close();
                    cambios = JOptionPane.showInputDialog("Resume los cambios realizados");
                    pre = con.prepareStatement(queryHistory);
                    pre.setString(1, cambios);
                    pre.setInt(2, idAdmin);
                    pre.executeUpdate();
                    JOptionPane.showMessageDialog(null, "[+] Cambios registrados ✔");

                    break;
                case 2:
                    String nuevaFecha = JOptionPane.showInputDialog("Introduce nueva fecha");
                    queryUpdate = "UPDATE FILTRACIONES SET fecha =? WHERE id_filtracion =?";
                    pre = con.prepareStatement(queryUpdate);
                    pre.setString(1, nuevaFecha);
                    pre.setInt(2, idInput);
                    pre.executeUpdate();

                    pre.close();
                    cambios = JOptionPane.showInputDialog("Resume los cambios realizados");
                    pre = con.prepareStatement(queryHistory);
                    pre.setString(1, cambios);
                    pre.setInt(2, idAdmin);
                    pre.executeUpdate();
                    JOptionPane.showMessageDialog(null, "[+] Cambios registrados ✔");

                    break;
                case 3:
                    String nuevaDescripcion = JOptionPane.showInputDialog("Introduce nueva descripcion");
                    queryUpdate = "UPDATE FILTRACIONES SET descripcion =? WHERE id_filtracion =?";
                    pre = con.prepareStatement(queryUpdate);
                    pre.setString(1, nuevaDescripcion);
                    pre.setInt(2, idInput);
                    pre.executeUpdate();

                    pre.close();
                    cambios = JOptionPane.showInputDialog("Resume los cambios realizados");
                    pre = con.prepareStatement(queryHistory);
                    pre.setString(1, cambios);
                    pre.setInt(2, idAdmin);
                    pre.executeUpdate();
                    JOptionPane.showMessageDialog(null, "[+] Cambios registrados ✔");
                    break;

                case 4:
                    String nuevaMedida = JOptionPane.showInputDialog("Introduce nueva medida");
                    queryUpdate = "UPDATE FILTRACIONES SET medidas =? WHERE id_filtracion =?";
                    pre = con.prepareStatement(queryUpdate);
                    pre.setString(1, nuevaMedida);
                    pre.setInt(2, idInput);
                    pre.executeUpdate();

                    pre.close();
                    cambios = JOptionPane.showInputDialog("Resume los cambios realizados");
                    pre = con.prepareStatement(queryHistory);
                    pre.setString(1, cambios);
                    pre.setInt(2, idAdmin);
                    pre.executeUpdate();
                    JOptionPane.showMessageDialog(null, "[+] Cambios registrados ✔");
                    break;
                default:

                    JOptionPane.showMessageDialog(null, "Introduce un valor valido", "Warning", JOptionPane.WARNING_MESSAGE);

                    break;
            }
            JOptionPane.showMessageDialog(null, "[+]........Modificación realizada con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
