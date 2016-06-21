package main.db;

import java.sql.*;
import java.util.ArrayList;

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

    public static ArrayList<Athlete> getAthletes(int field, String value){
        ArrayList<Athlete> athletes = new ArrayList<>();
        Statement stmt = null;
        Connection conn = connectDB();

        try {
            stmt = conn.createStatement();
            String sql;
            if(value == null)
                sql = "SELECT nome, numero, esporte, pais, genero, nascimento FROM atleta";
            else if(field == 1)
                sql = "SELECT nome, numero, esporte, pais, genero, nascimento FROM atleta " + "WHERE upper(nome) LIKE '%" + value.toUpperCase() + "%'";
            else
                sql = "SELECT nome, numero, esporte, pais, genero, nascimento FROM atleta " + "WHERE numero = '" + value + "'";
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

    public static ArrayList<Sport> getSports(String value) {
        ArrayList<Sport> sports = new ArrayList<>();
        Statement stmt = null;
        Connection conn = connectDB();

        try {
            stmt = conn.createStatement();
            String sql;
            if (value == null)
                sql = "SELECT nome FROM esporte";
            else
                sql = "SELECT nome FROM esporte " + "WHERE upper(nome) LIKE '%" + value.toUpperCase() + "%'";

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

    public static ArrayList<Country> getCountry(String value) {
        ArrayList<Country> countries = new ArrayList<>();
        Statement stmt = null;
        Connection conn = connectDB();

        try {
            stmt = conn.createStatement();
            String sql;
            if (value == null)
                sql = "SELECT nome, ouros, pratas, bronzes, total FROM pais ORDER BY ouros, pratas, bronzes, nome";
            else
                sql = "SELECT nome, ouros, pratas, bronzes, total FROM pais " + "WHERE upper(nome) LIKE '%" + value.toUpperCase() + "%'";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("nome");
                Integer gold = rs.getInt("ouros");
                Integer silver = rs.getInt("pratas");
                Integer bronze = rs.getInt("bronzes");
                Integer total = rs.getInt("total");

                //System.out.println();
                Country country = new Country(name);
                country.setGold(gold);
                country.setSilver(silver);
                country.setBronze(bronze);
                country.setTotal(total);
                country.setPlace(countries.size()+1);
                countries.add(country);
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

        return countries;
    }

    public static ArrayList<Disability> getDisability(String value) {
        ArrayList<Disability> disabilities = new ArrayList<>();
        Statement stmt = null;
        Connection conn = connectDB();

        try {
            stmt = conn.createStatement();
            String sql;
            if (value == null)
                sql = "SELECT nome FROM deficiencia";
            else
                sql = "SELECT nome FROM deficiencia " + "WHERE upper(nome) LIKE '%" + value.toUpperCase() + "%'";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("nome");

                //System.out.println();
                Disability disability = new Disability(name);
                disabilities.add(disability);
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

        return disabilities;
    }

    public static void removeAthlete(int key){
        Connection conn = connectDB();
        Integer pk = key;
        try {
            Statement sttm = conn.createStatement();
            String sql = "DELETE FROM atleta WHERE numero = " + pk.toString();
            sttm.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao remover atleta");
        }
    }

    public static void removeSport(String pk){
        Connection conn = connectDB();

        try {
            Statement sttm = conn.createStatement();
            String sql = "DELETE FROM esporte WHERE nome = '" + pk + "'";
            sttm.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao remover esporte");
        }
    }

    public static void removeCountry(String pk){
        Connection conn = connectDB();

        try {
            Statement sttm = conn.createStatement();
            String sql = "DELETE FROM pais WHERE nome = '" + pk + "'";
            sttm.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao remover pais");
        }
    }

    public static void removeDisability(String pk){
        Connection conn = connectDB();

        try {
            Statement sttm = conn.createStatement();
            String sql = "DELETE FROM deficiencia WHERE nome = '" + pk + "'";
            sttm.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao remover esporte");
        }
    }

    public static ArrayList<Disability> getAthleteDisabilities(int number) {
        ArrayList<Disability> disabilities = new ArrayList<>();
        Statement stmt = null;
        Connection conn = connectDB();

        try {
            stmt = conn.createStatement();
            String sql;

            sql = "SELECT d.nome " +
                    "from atleta a, deficiencia d, deficienciasatleta da " +
                    "where (a.numero = " + Integer.toString(number) + " and a.numero = da.atleta and da.deficiencia = d.nome)";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString(1);

                //System.out.println();
                Disability disability = new Disability(name);

                disabilities.add(disability);
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

        return disabilities;
    }
}
