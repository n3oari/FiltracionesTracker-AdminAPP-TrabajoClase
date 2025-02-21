package t8_practica_filtraciones;

import java.io.*;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class T8_PRACTICA_FILTRACIONES {

    
    
    
    
    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
 
        
        try{
          Connection con =   MetodosToSql.establecerConexion();
            new UsersGUI();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
