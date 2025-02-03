package t8_practica_filtraciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class T8_PRACTICA_FILTRACIONES {

    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {

        String url = "jdbc:mysql://localhost:3306/filtraciones";
        String user = "root";
        String pass = "";

        Connection con = DriverManager.getConnection(url, user, pass);
        System.out.println("...........Conexión establecida con exito");

        //--------------TABLA TWITTER---------------//
        //ARRAYLIST CON ATIRBUTOS LEIDOS DE UN .TXT  
        ArrayList<String> userNameTw = new ArrayList<>();
        ArrayList<String> emailsTw = new ArrayList<>();
        ArrayList<String> contraseñaTw = new ArrayList<>();
        ArrayList<Integer> numTw = new ArrayList<>();
        
        String queryPreparadaTw = "INSERT INTO TWITTER (usuario,correo,contraseña,telefono) VALUES (?,?,?,?)"; //SENTENCIA PREPARED
        try {
            userNameTw = Metodos.crearUsuarios("src/archivos/us.txt");
            emailsTw = Metodos.crearEmails("src/archivos/emailGen.txt");
            contraseñaTw = Metodos.crearContraseña("src/archivos/twpass.txt");
            numTw = Metodos.crearNum();
            
            
            /*
            //BUCLE PARA MOSTRAR LOS ARRAYLIST (INNECESARIO)
            System.out.println("---------" + "\nUSUARIOS " + "\n----------");
            for (String usuario : userNameTw) {
                System.out.println(usuario);
            }
            System.out.println("---------" + "\nEMAILS " + "\n----------");
            for (String email : emailsTw) {
                System.out.println(email);
            }
            System.out.println("---------" + "\nCONTRASEÑAS " + "\n----------");
            for (String contraseña : contraseñaTw) {
                System.out.println(contraseña);
            }
             */

        } catch (IOException e) {
            e.printStackTrace();
        }

        //BUCLE PARA INSERTAR EN LA TABLA EL ARRAYLIST 
        try {
            PreparedStatement pre = con.prepareStatement(queryPreparadaTw);
            int count = 0;
            for (int i = 0; i < userNameTw.size(); i++) {

                pre.setString(1, userNameTw.get(i));
                pre.setString(2, emailsTw.get(i));
                pre.setString(3, contraseñaTw.get(i));
                pre.setInt(4, numTw.get(i));

                pre.executeUpdate();
                count++;
            }
            System.out.println("..........Datos insertados en tabla Twitter correctamente........"
                    + " nº columnas insertadas -> " + count);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //-----------FIN TABLA TWITTER----------------------//

    }
}
