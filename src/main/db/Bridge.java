package main.db;

import oracle.sql.DATE;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Bridge {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@grad.icmc.usp.br:15215:orcl";

    //  Database credentials
    static final String USER = "a8937034";
    static final String PASS = "a8937034";

    public static Connection connectDB() {
        Connection conn = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            System.out.println("Driver registrado");
            // Open connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conexão estabelecida");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO ABRIR CONEXÃO");
        }
        return conn;
    }

    public static ArrayList<Athlete> getAthletes(String where){
        ArrayList<Athlete> athletes = new ArrayList<>();
        Statement stmt = null;
        Connection conn = connectDB();

        try {
            stmt = conn.createStatement();
            String sql;
            if(where == null)
                sql = "SELECT nome, numero, esporte, pais, genero, nascimento FROM atleta";
            else
                sql = "SELECT nome, numero, esporte, pais, genero, nascimento FROM atleta " + where;

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                int number  = rs.getInt("numero");
                String name = rs.getString("nome");
                String sport = rs.getString("esporte");
                String country = rs.getString("pais");
                String gender = rs.getString("genero");
                Date birth = rs.getDate("nascimento");

                //System.out.println();
                Athlete athlete = new Athlete(name, sport, country, number, gender);

                if(birth != null)
                    athlete.setBirth(birth.toLocalDate());

                athletes.add(athlete);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
                System.out.println("Conexão encerrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return athletes;
    }

    public static ArrayList<Sport> getSports(String where) {
        ArrayList<Sport> sports = new ArrayList<>();
        Statement stmt = null;
        Connection conn = connectDB();

        try {
            stmt = conn.createStatement();
            String sql;
            if (where == null)
                sql = "SELECT nome FROM esporte";
            else
                sql = "SELECT nome FROM esporte " + where;

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("nome");

                //System.out.println();
                Sport sport = new Sport(name);

                sports.add(sport);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
                System.out.println("Conexão encerrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sports;
    }
}
