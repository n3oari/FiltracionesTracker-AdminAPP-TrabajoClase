package t8_practica_filtraciones;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class MetodosToSql {

    private static final String url = "jdbc:mysql://localhost:3306/filtraciones";
    private static final String user = "root";
    private static final String pass = "";
    private static int count = 0; //CONTADOR PARA LLEVAR UN CONTROL
    private static int indexRandom = (int) (Math.random() * 10);

    /*------------METODOS INDEX-------------------
    1 - CREAR CONEXION
    2- CREAR QUERYS
    3- INSERTAR DATOS
    4- GENERAR DICCIONARIO EMAILS
    5- LEER DICCIONARIO CONTRASEÑAS Y CREAR ARRAYLIST
    6- LEER DICCIONARIO USUARIOS Y CREAR ARRAYLIST
    7- LEER DICCIONARIO EMAILS Y CREAR ARRAYLIST
    8- GENERAR NUMEROS DE TELEFONO
     */
    
    
    
    // 1 - > METODO CREAR UNA CONEXION
    public static Connection establecerConexion() throws SQLException {

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("[+]...........Conexión establecida con exito");

        } catch (SQLException e) {
            System.out.println("[-]No se ha podido realizar la conexion");
            e.printStackTrace();
        }
        return con;
    }

    // 2 - > METODO PARA CREAR UNA QUERY POR CADA TABLA
    public static String[] crearQuerys() {

        String queryTemplate = "INSERT IGNORE INTO %s (usuario,correo,contraseña,telefono) VALUES (?,?,?,?)";
        String[] tablas = {
            "TWITTER", "INSTAGRAM", "PORNHUB", "AMAZON",
            "GMAIL", "DROPBOX", "YOUTUBE", "TELEGRAM"
        };
        String[] allQuerys = new String[tablas.length];

        for (int i = 0; i < tablas.length; i++) {
            allQuerys[i] = String.format(queryTemplate, tablas[i]);

            count++;
        }
        System.out.println("[+]Se han creado " + count + " querys correctamente");
        return allQuerys;
    }
    // 3 -> METODO PARA INSERTAR DATOS

    public static void insertarDatos(Connection con, String[] querys) {

        //ARRAYLIST PARA CADA COLUMNA DE LAS TABLAS
        ArrayList<String> users = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> passwds = new ArrayList<>();
        ArrayList<Integer> numbersPhone = new ArrayList<>();

        //ARRAY CON 8 PREPARED STATEMENTS
        PreparedStatement[] allPreparedStatements = new PreparedStatement[8];

        /*SE INTRODUCE VALORES A LAS COLUMNAS LLAMANDO A LOS METODOS
        QUE LEEN LOS DICCIONARIOS E INTRODUCEN LOS VALORES*/
        try {
            users = MetodosToSql.crearUsuarios("src/archivos/usuarios.txt");
            emails = MetodosToSql.crearEmails("src/archivos/emailGen.txt");
            passwds = MetodosToSql.crearContraseña("src/archivos/contraseñas.txt");
            numbersPhone = MetodosToSql.crearNum();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            // BUCLE PARA PREPARAR TODAS LAS SENTENCIAS
            for (int i = 0; i < querys.length; i++) {
                allPreparedStatements[i] = con.prepareStatement(querys[i]);
            }

            //BUCLE ANIDADO PARA GENERAR QUERYS A TODAS LAS TABLAS
            for (int i = 0; i < users.size(); i++) {
                for (int j = 0; j < allPreparedStatements.length; j++) {

                    allPreparedStatements[j].setString(1, users.get(i));
                    allPreparedStatements[j].setString(2, emails.get(i));
                    allPreparedStatements[j].setString(3, passwds.get(i));
                    allPreparedStatements[j].setInt(4, numbersPhone.get(i));

                    allPreparedStatements[j].executeUpdate();
                    count++;
                }
            }
            System.out.println("[+]Se han insertado  " + (count / querys.length) + " columnas a todas las tablas");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 4 -> METODO PARA GENERAR UN DICCIONARIO DE EMAILS, USANDO UN .TXT DE NOMBRES
    public static void emailGenerator() throws FileNotFoundException, IOException {

        File userDic = new File("src/archivos/usuarios.txt");
        File emailsGenerados = new File("src/archivos/emailsGen.txt");

        FileReader fr = new FileReader(userDic);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(emailsGenerados);
        BufferedWriter bw = new BufferedWriter(fw);
        String linea;

        while ((linea = br.readLine()) != null) {
            bw.write(linea + "@gmail.com");
            bw.newLine();

        }
        System.out.println("[+]emails generados correctamente");
        br.close();
        bw.close();

    }

    // 5 -> METODO PARA LEER UN .TXT CON CONTRASEÑAS Y METERLO EN UN ARRAY LIST
    public static ArrayList<String> crearContraseña(String archivo) throws FileNotFoundException, IOException {

        File userDic = new File("src/archivos/contraseñas.txt");
        ArrayList<String> contraseñas = new ArrayList();
        if (userDic.exists()) {
            System.out.println("!!!el diccionario de contraseñas existe!!!");
        }

        FileReader fr = new FileReader(userDic);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        int count = 0;

        while ((linea = br.readLine()) != null && count < 1000) {
            contraseñas.add(linea);
            count++;

        }
        br.close();
        System.out.println("[+]Contraseñas creados correctamente..... "
                + "nº contraseñas creados " + count);

        return contraseñas;
    }

    // 6 - > METODO  PARA LEER UN .TXT CON USUARIOS Y METERLO EN UN ARRAY LIST
    public static ArrayList<String> crearUsuarios(String archivo) throws FileNotFoundException, IOException {

        File userDic = new File("src/archivos/usuarios.txt");
        ArrayList<String> usuarios = new ArrayList();
        if (userDic.exists()) {
            System.out.println("!!!el diccionario de usuarios existe!!!");
        }

        FileReader fr = new FileReader(userDic);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        int count = 0;

        while ((linea = br.readLine()) != null && count < 1000) {
            usuarios.add(linea);
            count++;

        }
        br.close();
        System.out.println("[+]Usuarios creados correctamente..... "
                + "nº usuarios creados " + count);

        return usuarios;
    }

    // 7 - > METODO PARA LEER UN .TXT CON EMAILS Y METERLO EN UN ARRAY LIST
    public static ArrayList<String> crearEmails(String archivo) throws FileNotFoundException, IOException {

        File userDic = new File("src/archivos/emailsGen.txt");
        ArrayList<String> emails = new ArrayList();
        if (userDic.exists()) {
            System.out.println("!!!el diccionario de emails existe!!!");
        }

        FileReader fr = new FileReader(userDic);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        int count = 0;

        while ((linea = br.readLine()) != null && count < 1000) {
            emails.add(linea);
            count++;

        }
        br.close();
        System.out.println("[+]Usuarios creados correctamente..... "
                + "nº emails creados " + count);

        return emails;
    }

    // 8 - > METODO PARA GENERAR NUMEROS DE TELEFONO Y AÑADIR A UN ARRAYLIST
    public static ArrayList<Integer> crearNum() {

        ArrayList<Integer> listaNums = new ArrayList<>();
        int primerNum = 600000000; // con esto se consigue que el primer numero siempre sea un 6
        int count = 0;
        for (int i = 0; i < 1000; i++) {

            int num = (int) (Math.random() * 99999999);
            listaNums.add(primerNum + num);
            count++;
        }
        System.out.println("[+]Numeros de telefonos creados correctamente"
                + " nº numeros creados  " + count);

        return listaNums;
    }

}
