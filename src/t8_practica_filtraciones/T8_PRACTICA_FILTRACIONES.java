package t8_practica_filtraciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class T8_PRACTICA_FILTRACIONES {

    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Quiere estbalecer una conexión e insertar datos? true/false");

        boolean a = sc.nextBoolean();
        if (a != false) {
            Connection con = MetodosToSql.establecerConexion();//LLAMA AL METODO QUE CREA UNA CONEXIÓN CON UNA BD SQL 
            if (con == null) { //SI NO HAY CONEXION, EL PROGRAMA SE DETIENE Y NO LLAMA AL RESTO DE METODOS
                return;
            }

            String[] querys = MetodosToSql.crearQuerys(); //LLAMA AL METODO QUE GENERA 1 QUERY POR TABLA
            MetodosToSql.insertarDatos(con, querys); // SE LLAMA AL MÉTODO QUE INSERTA DATOS EN LA BD, PASANDO LA CONEXIÓN Y LAS QUERIES.
            con.close(); //SE CIERRA LA CONEXION

            System.out.println("[-]...........Conexión cerrada correctamente");
            
        }

    }
}
