package t8_practica_filtraciones;

import java.io.IOException;
import Tablas.Servicios;
import java.util.ArrayList;

public class testingMain {

    //CLASE MAIN PARA TESTEAR METODOS ETC
    public static void main(String[] args) throws IOException {
        
        
        Metodos.crearNum();
        System.out.println(Metodos.crearNum());
        /*
        ArrayList<String> userNameTw = new ArrayList<>();
        ArrayList<String> emailsTw = new ArrayList<>();
        ArrayList<String> contraseñaTw = new ArrayList<>();

        try {
            userNameTw = Metodos.crearUsuarios("src/us.txt");
            emailsTw = Metodos.crearEmails("src/emailGen.txt");
            contraseñaTw = Metodos.crearContraseña("src/twpass.txt");

            System.out.println("---------" + "\nUSUARIOS " + "\n----------");
            for (String user : userNameTw) {
            System.out.println(user);
            }
            System.out.println("---------" + "\nEMAILS " + "\n----------");
            for (String email : emailsTw) {
                System.out.println(email);
            }
            System.out.println("---------" + "\nCONTRASEÑAS " + "\n----------");
            for (String contraseña : contraseñaTw) {
                System.out.println(contraseña);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Metodos m = new Metodos();
        m.emailGenerator();
         */
    }

}
