package t8_practica_filtraciones;

import java.io.*;
import java.util.ArrayList;

import java.util.List;

public class Metodos {

    public Metodos() {

    }

    //METODO PARA GENERAR UN DICCIONARIO DE EMAILS, USANDO UN .TXT DE NOMBRES
    public static void emailGenerator() throws FileNotFoundException, IOException {

        File userDic = new File("src/us.txt");
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
        System.out.println("........emails generados correctamente");
        br.close();
        bw.close();

    }

    //METOOD PARA LEER UN .TXT CON CONTRASEÑAS Y METERLO EN UN ARRAY LIST
    public static ArrayList<String> crearContraseña(String archivo) throws FileNotFoundException, IOException {

        File userDic = new File("src/archivos/twpass.txt");
        ArrayList<String> contraseñas = new ArrayList();
        if (userDic.exists()) {
            System.out.println("...el diccionario de contraseñas existe");
        }

        FileReader fr = new FileReader(userDic);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        int count = 0;

        while ((linea = br.readLine()) != null && count < 100) {
            contraseñas.add(linea);
            count++;

        }
        br.close();
        System.out.println(".........Contraseñas creados correctamente..... "
                + "nº contraseñas creados " + count);

        return contraseñas;
    }

    //METOOD PARA LEER UN .TXT CON USUARIOS Y METERLO EN UN ARRAY LIST
    public static ArrayList<String> crearUsuarios(String archivo) throws FileNotFoundException, IOException {

        File userDic = new File("src/archivos/us.txt");
        ArrayList<String> usuarios = new ArrayList();
        if (userDic.exists()) {
            System.out.println("...el diccionario de usuarios existe");
        }

        FileReader fr = new FileReader(userDic);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        int count = 0;

        while ((linea = br.readLine()) != null && count < 100) {
            usuarios.add(linea);
            count++;

        }
        br.close();
        System.out.println(".........Usuarios creados correctamente..... "
                + "nº usuarios creados " + count);

        return usuarios;
    }

    //METOOD PARA LEER UN .TXT CON EMAILS Y METERLO EN UN ARRAY LIST
    public static ArrayList<String> crearEmails(String archivo) throws FileNotFoundException, IOException {

        File userDic = new File("src/archivos/emailsGen.txt");
        ArrayList<String> emails = new ArrayList();
        if (userDic.exists()) {
            System.out.println("...el diccionario de emails existe");
        }

        FileReader fr = new FileReader(userDic);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        int count = 0;

        while ((linea = br.readLine()) != null && count < 100) {
            emails.add(linea);
            count++;

        }
        br.close();
        System.out.println(".........Usuarios creados correctamente..... "
                + "nº emails creados " + count);

        return emails;
    }

    //METODO PARA GENERAR NUMEROS DE TELEFONO Y AÑADIR A UN ARRAYLIST
    public static ArrayList<Integer> crearNum() {

        ArrayList<Integer> listaNums = new ArrayList<>();
        int primerNum = 600000000; // con esto se consigue que el primer numero siempre sea un 6
        int count=0;
        for (int i = 0; i < 100; i++) {

            int num = (int) (Math.random() * 99999999);
            listaNums.add(primerNum + num);
            count++;
        }
        System.out.println("............Numeros de telefonos creados correctamente"
                + " nº numeros creados  " + count );

        return listaNums;
    }

}
