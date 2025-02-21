package t8_practica_filtraciones;

import java.io.*;
import java.sql.*;

public class T8_PRACTICA_FILTRACIONES {

    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {

        try {
            MetodosToSql.establecerConexion();
            new UsersGUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
