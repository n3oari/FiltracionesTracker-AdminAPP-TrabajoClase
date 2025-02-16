package t8_practica_filtraciones;

import java.awt.Dimension;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class MetodosToSql {

    private static Scanner sc = new Scanner(System.in);

    private static int afectados;

    /*------------METODOS INDEX-------------------
    1 - CREAR CONEXION
    2 - EMAIL DICCIONARIO GENERATOR
    3 - NUMEROS DE TELOFONO GENERATOR
    4 - LEER DICCIONARIOS Y AÑADIRLOS A ARRAYLISTS
    5-  AÑADIR USUARIOS + QUERY HACIA LA BASE DE DATOS
    --------------------------------------------------
     */
    // 1 -> METODO CREAR UNA CONEXION
    public static Connection establecerConexion() throws SQLException {

        String URL = "jdbc:mysql://localhost:3306/filtraciones";
        String USER = "root";
        String PASS = "";

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

    // 3 -> METODO PARA GENERAR NUMEROS DE TELEFONO Y AÑADIR A UN ARRAYLIST
    public static LinkedList<Integer> crearNum(int afectados) {
        LinkedList<Integer> listaNums = new LinkedList<>();
        int primerNum = 600000000; // con esto se consigue que el primer número siempre sea un 6
        for (int i = 0; i < afectados; i++) {
            int num = (int) (Math.random() * 99999999);
            listaNums.add(primerNum + num);
        }
        System.out.println("[+] Números de teléfonos creados correctamente. nº números creados " + afectados);
        return listaNums;
    }

    // 4 -> METODO PARA LEER ARCHIVOS Y METERLOS EN UN LINKEDLIST DE <USUARIO><EMAILS><CONTRASEÑAS>
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
        br.close(); // Cierra el archivo
        System.out.println("[+] Elementos creados correctamente..... nº elementos creados " + count);
        return lista; // Devuelve el ArrayList con todas las líneas
    }

    // 6 -> METODO QUE AÑADE UNA FILTRACION A LA BASE DE DATOS MEDIANTE UNA QUERY
    public static void añadirFiltracion(Connection con, DefaultTableModel model) throws SQLException, IOException {

        MetodosToSql.establecerConexion();
        String queryFiltraciones = "INSERT INTO FILTRACIONES (id_filtracion, plataforma, fecha, numero_afectados, descripcion, medidas) VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedFiltraciones = establecerConexion().prepareStatement(queryFiltraciones, Statement.RETURN_GENERATED_KEYS);

        String idIntroducido = (JOptionPane.showInputDialog("Introduce un ID"));
        int id = Integer.parseInt(idIntroducido);

        String plataforma = JOptionPane.showInputDialog("Introduce nombre plataforma");
        String fecha = JOptionPane.showInputDialog("Introduce fecha dd/MM/yyyy");
        DateTimeFormatter dateF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate locald = LocalDate.parse(fecha, dateF);
        Date sqlD = Date.valueOf(locald);

        String rutaUsuario = JOptionPane.showInputDialog("Número de afectados. Necesario introducir la ruta del diccionario de usuarios:");
        File diccionario = new File(rutaUsuario);

        if (diccionario.exists()) {
            afectados = MetodosToSql.contarDiccionario(diccionario);
        } else {
            JOptionPane.showMessageDialog(null, "El archivo de diccionario no existe. Verifica la ruta.");
            return;  // Salimos si el archivo no existe
        }

        String descripcion = JOptionPane.showInputDialog("Introduce descripción de la filtración");
        String medidas = JOptionPane.showInputDialog("Introduce las medidas tomadas");

        preparedFiltraciones.setInt(1, id);
        preparedFiltraciones.setString(2, plataforma);
        preparedFiltraciones.setDate(3, sqlD);
        preparedFiltraciones.setInt(4, afectados);
        preparedFiltraciones.setString(5, descripcion);
        preparedFiltraciones.setString(6, medidas);
        preparedFiltraciones.executeUpdate();

        Object[] row = new Object[]{id, plataforma, fecha, afectados, descripcion, medidas};
        model.addRow(row);

        //AL FINALIZAR DE INSERTAR LA FILTRACION, SE LLAMA AL METODO AÑADIRUSUARIOS
        //PARA AÑADIR LAS CREDENCIALES A LA FILTRACION
        MetodosToSql.añadirUsuarios(id, afectados, model);

    }

    // 5 -> METODO PARA CREAR USUARIOS E INSERTARLOS A LA BASE DE DATOS CON UNA QUERY
    public static void añadirUsuarios(int idGenerado, int afectados, DefaultTableModel model) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String message = "";

        String rutaUsers = JOptionPane.showInputDialog("Introduce ruta diccionario usuarios");
        String rutaPass = JOptionPane.showInputDialog("Introduce ruta diccionario contraseñas");
        String rutaEmails = JOptionPane.showInputDialog("Introduce ruta diccionario emails");

        File usuario = new File(rutaUsers);
        File pass = new File(rutaPass);
        File correo = new File(rutaEmails);

        String queryUsuarios = "INSERT IGNORE INTO CREDENCIALES (usuario, correo, contraseña, telefono, id_filtracion) VALUES (?,?,?,?,?)";
        PreparedStatement prepared = establecerConexion().prepareStatement(queryUsuarios);

        LinkedList<String> users = new LinkedList<>();
        LinkedList<String> emails = new LinkedList<>();
        LinkedList<String> passwds = new LinkedList<>();
        LinkedList<Integer> numbersPhone = new LinkedList<>();

        try {
            users = MetodosToSql.leerArchivo(usuario, afectados);
            emails = MetodosToSql.leerArchivo(correo, afectados);
            passwds = MetodosToSql.leerArchivo(pass, afectados);
            numbersPhone = MetodosToSql.crearNum(afectados);

            System.out.println("Usuarios cargados: " + users.size());
            System.out.println("Correos cargados: " + emails.size());
            System.out.println("Contraseñas cargadas: " + passwds.size());
            System.out.println("Números de teléfono generados: " + numbersPhone.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int contador = 0;
        for (int i = 0; i < afectados; i++) {
            // Aseguramos que las listas tienen suficientes elementos
            if (i >= users.size() || i >= emails.size() || i >= passwds.size() || i >= numbersPhone.size()) {
                System.out.println("¡Error! Índice fuera de rango para el archivo.");
                break; // Detenemos el ciclo si no hay suficientes elementos
            }

            prepared.setString(1, users.get(i));
            prepared.setString(2, emails.get(i));
            prepared.setString(3, passwds.get(i));
            prepared.setInt(4, numbersPhone.get(i));
            prepared.setInt(5, idGenerado);
            prepared.executeUpdate();

            contador++;

            message += "[" + contador + "] Usuario: " + users.get(i)
                    + ", Correo: " + emails.get(i)
                    + ", Contraseña: " + passwds.get(i)
                    + ", Teléfono: " + numbersPhone.get(i);

        }
        //   JOptionPane.showMessageDialog(null, scroll, "Advertencia de seguridad", JOptionPane.WARNING_MESSAGE);
        JOptionPane.showMessageDialog(null, "Filtracion añadida exitosamente", "Info", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, contador + " usuarios añadidos exitosamente", "Info", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("[+] Se han añadido los usuarios correctamente.");
        String mensaje = "";

    }

    // 7 -> METODO QUE CUENTA LAS LINEAS DE UN ARCHIVO
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

    public static void updateQuery() throws SQLException {

        try {
            Connection con = MetodosToSql.establecerConexion();

            int idInput = Integer.parseInt(JOptionPane.showInputDialog("Introduce el id de la filtración que deseas eliminar"));
            int opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "Selecciona "
                    + "\n[1] modificar plataforma "
                    + "\n[2] modificar fecha"
                    + "\n[3] modificar descripcion"
                    + "\n[4] modificar medidas"));

            String query = "";
            PreparedStatement pre = null;
            switch (opcion) {
                case 1:
                    String nuevaPlataforma = JOptionPane.showInputDialog("Introduce nueva plataforma");
                    query = "UPDATE FILTRACIONES SET plataforma = ?  WHERE id_filtracion = ?";
                    pre = con.prepareStatement(query);
                    pre.setString(1, nuevaPlataforma);
                    pre.setInt(2, idInput);
                    pre.executeUpdate();

                    break;
                case 2:
                    String nuevaFecha = JOptionPane.showInputDialog("Introduce nueva fecha");
                    query = "UPDATE FILTRACIONES SET fecha =? WHERE id_filtracion =?";
                    pre = con.prepareStatement(query);
                    pre.setString(1, nuevaFecha);
                    pre.setInt(2, idInput);
                    pre.executeUpdate();
                    
                    break;
                case 3:
                    String nuevaDescripcion = JOptionPane.showInputDialog("Introduce nueva descripcion");
                    query = "UPDATE FILTRACIONES SET descripcion =? WHERE id_filtracion =?";
                      pre = con.prepareStatement(query);
                    pre.setString(1, nuevaDescripcion);
                    pre.setInt(2, idInput);
                    pre.executeUpdate();
                    break;

                case 4:
                    String nuevaMedida = JOptionPane.showInputDialog("Introduce nueva medida");
                    query = "UPDATE FILTRACIONES SET medidas =? WHERE id_filtracion =?";
                      pre = con.prepareStatement(query);
                    pre.setString(1, nuevaMedida);
                    pre.setInt(2, idInput);
                    pre.executeUpdate();
                    break;
                default:

                    break;
            }
            JOptionPane.showMessageDialog(null, "[+]........Modificación realizada con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        MetodosToSql.establecerConexion();
    }
}
