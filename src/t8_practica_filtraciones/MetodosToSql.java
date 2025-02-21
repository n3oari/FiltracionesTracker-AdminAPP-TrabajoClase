package t8_practica_filtraciones;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MetodosToSql {

    private static int afectados;
    private static int idAdmin = -1;
    private static String nameAdmin;

    /*---------------------METODOS INDEX----------------------
    0 - SETTETS Y GETTERS ID ADMIN
    1 - CREAR CONEXION
    2 - LEER DICCIONARIOS(ARCHIVOS) Y METERLOS EN LINKEDLINKS
    3 - AÑADIR FILTRACION A LA BD -> LLAMA AL 5
    4-  AÑADIR USUARIOS A LA BD
    5 - CUENTA LA CANTIDAD DE  LINEAS DE UN FILE 
    6 - GUARDAR ID ADMIN QUE SE LOGUEA 
    7 - QUERY PARA MODIFICAR TABLE + BD (USA EL ID DEL METODO 7)
    ----------------------------------------------------------
     */
    //0 SETTERS Y GETTERS ID ADMIN Y ADMIN NAME
    public static void setIdAdmin(int id) {
        idAdmin = id;
    }

    public static int getIdAdmin() {
        return idAdmin;
    }

    public static void setAdminName(String name) {
        nameAdmin = name;

    }

    public static String getAdminName() {
        return nameAdmin;
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

    // 2 -> METODO PARA LEER ARCHIVOS Y METERLOS EN UN LINKEDLIST DE <USUARIO><EMAILS><CONTRASEÑAS>
    // HE USADO UN LINKEDLIST PORQUE NO TIENE LIMITES DE ELEMENTOS (EL MAXIMO DE UNA ARRAYLIST ES 100000
    public static LinkedList<String> leerArchivo(File archivo, int afectados) throws FileNotFoundException, IOException {

        LinkedList<String> lista = new LinkedList<>();
        try {

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista; // Devuelve la cantidad de lineas en el File 
    }

    // 3 -> METODO QUE AÑADE UNA FILTRACION A LA TABLE + BD
    public static void añadirFiltracion() throws SQLException, IOException {

        DefaultTableModel model = AdministradoresGUI.getModel();

        try {
            String queryFiltraciones = "INSERT INTO FILTRACIONES (id_filtracion, plataforma, fecha, numero_afectados, descripcion, medidas) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedFiltraciones = establecerConexion().prepareStatement(queryFiltraciones, Statement.RETURN_GENERATED_KEYS);
            //El return generated keys se usa para obtener claves primaras(id_filtracion) cuando es autoincrementado (generada automaticamente por sql)
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
            //rellenamos las filas de la tabla
            Object[] row = new Object[]{id, plataforma, fecha, afectados, descripcion, medidas};
            model.addRow(row);

            //AL FINALIZAR DE INSERTAR LA FILTRACION, SE LLAMA AL METODO AÑADIRUSUARIOS
            //PARA AÑADIR LAS CREDENCIALES A LA FILTRACION
            //PASANDOLE ID DE LA FILTRACION QUE HEMOS AÑADIDO, EL NUMERO DE AFECTADOS, Y LA TABLEMODEL
            MetodosToSql.añadirUsuarios(id, afectados);

            //MANEJO DE EXCEPCIONES 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: ID debe ser numero entero", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Error: el formato de fecha debe ser  dd/MM/yyyy", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: comprueba conexion base datos", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: No se puede acceder al archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // 4 -> METODO PARA CREAR USUARIOS E INSERTARLOS A LA BASE DE DATOS CON UNA QUERY
    public static void añadirUsuarios(int idGenerado, int afectados) throws SQLException {

        try {
            String rutaUsers = JOptionPane.showInputDialog(null, "Introduce ruta diccionario usuarios");
            String rutaPass = JOptionPane.showInputDialog(null, "Introduce ruta diccionario contraseñas");
            String rutaEmails = JOptionPane.showInputDialog(null, "Introduce ruta diccionario emails");
            String rutaNum = JOptionPane.showInputDialog(null, "Introduce ruta diccionario numeros");

            File usuario = new File(rutaUsers);
            File pass = new File(rutaPass);
            File correo = new File(rutaEmails);
            File num = new File(rutaNum);

            //INSERT IGNORE SE UTILIZA PARA  QUE AL INTRODUCIR ALGUN DUPLICADO
            // EL SISTEMA IGNORE ESE ERROR Y NO INSERTE EL DUPLICADO
            String queryUsuarios = "INSERT IGNORE INTO CREDENCIALES (usuario, correo, contraseña, telefono, id_filtracion) VALUES (?,?,?,?,?)";
            PreparedStatement prepared = establecerConexion().prepareStatement(queryUsuarios);

            LinkedList<String> users = new LinkedList<>();
            LinkedList<String> emails = new LinkedList<>();
            LinkedList<String> passwds = new LinkedList<>();
            LinkedList<String> numbersPhone = new LinkedList<>();

            users = MetodosToSql.leerArchivo(usuario, afectados);
            emails = MetodosToSql.leerArchivo(correo, afectados);
            passwds = MetodosToSql.leerArchivo(pass, afectados);
            numbersPhone = (MetodosToSql.leerArchivo(num, afectados));
            //debug 
            System.out.println("Usuarios cargados: " + users.size());
            System.out.println("Correos cargados: " + emails.size());
            System.out.println("Contraseñas cargadas: " + passwds.size());
            System.out.println("Números de teléfono generados: " + numbersPhone.size());

            int contador = 0;
            for (int i = 0; i < afectados; i++) {
                // Aseguramos que las listas tienen suficientes elementos
                if (i >= users.size() || i >= emails.size() || i >= passwds.size() || i >= numbersPhone.size()) {
                    System.out.println("Error:  indice fuera de rango para el archivo.");
                    break; // Detenemos  si no hay suficientes elementos
                }
                //si todo ok, generamos por cada linea un usuario con su id(autogenerado),
                //nombre usuario,email,contraseña,numero de telefono
                prepared.setString(1, users.get(i));
                prepared.setString(2, emails.get(i));
                prepared.setString(3, passwds.get(i));
                prepared.setInt(4, Integer.parseInt(numbersPhone.get(i)));
                prepared.setInt(5, idGenerado); //queremos guardar el id autogenerado para usarlo en otros metodos
                prepared.executeUpdate();

                contador++;
            }

            JOptionPane.showMessageDialog(null, contador + " usuarios añadidos exitosamente", "Info", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("[+] Se han añadido los usuarios correctamente.");
            //MANEJO EXCEPCIONES
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "diccionario no encontrado", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    // 5 -> METODO QUE CUENTA LAS LINEAS DE UN ARCHIVO

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

    // 6 -> METODO PARA VERIFICAR ADMIN Y OBTENER SUS DATOS AL LOGEARSE
    //      PARA LLEVAR UN REGISTRO / Y USARLOS EN OTROS METODOS / QUERYS
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

            if (rs.next()) { //si existe un id asociado al usuario y contraseña entonces:

                JOptionPane.showMessageDialog(null, "[+]Adminitrador verficado exitosamente ");
                JOptionPane.showMessageDialog(null, ".........entrando como administrador");
                idAdmin = rs.getInt("id_administrador"); //obtener  id del admin loggeado
                nameAdmin = rs.getString("usuario"); // obtener nombre del admin loggeado

                //se usaran despues para mostar que admin esta logeado, y para la tabla historial
                MetodosToSql.setIdAdmin(idAdmin);
                MetodosToSql.setAdminName(nameAdmin);

                new AdministradoresGUI(); //abre la GUI administradores
                AdministradoresGUI.refreshBD(); //actualiza la tabla
            } else {
                JOptionPane.showMessageDialog(null, "....Usuario o contraseña incorrectos", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idAdmin;

    }

    // 7 -> METODO PARA MODIFICAR LA TABLE + LA BD
    public static void modifyQuery() throws SQLException {

        int idAdmin = MetodosToSql.getIdAdmin(); //obtener el id del admin loggeado
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
