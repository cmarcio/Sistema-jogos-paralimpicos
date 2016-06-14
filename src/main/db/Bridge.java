package main.db;

import java.sql.*;
import java.util.Scanner;
import java.util.StringJoiner;


public class Bridge {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@grad.icmc.usp.br:15215:orcl";

    //  Database credentials
    static final String USER = "a8937034";
    static final String PASS = "a8937034";

    public void insertTime(){
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table... TIME");
            System.out.println("DIGITE O NOME DO TIME E O ESTADO");
            Scanner scanner = new Scanner(System.in);
            String nome = "'" + scanner.next() + "'";
            String estado = "'" + scanner.next() + "'";
            stmt = conn.createStatement();

            String sql = "INSERT INTO TIME VALUES (" + nome + ", " + estado + ", 'profissional', 0)";
            stmt.executeUpdate(sql);

            System.out.println("Inserted records into the table...");

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }
}
