package main.db;

import javafx.beans.property.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Athlete {
    private StringProperty sport;
    private StringProperty name;
    private StringProperty country;
    private IntegerProperty number;
    private StringProperty genre;
    private ObjectProperty<LocalDate> birth;

    public Athlete(String name, String sport, String country, int number, String gender, LocalDate birth){
        this.name = new SimpleStringProperty(name);
        this.sport = new SimpleStringProperty(sport);
        this.country = new SimpleStringProperty(country);
        this.number = new SimpleIntegerProperty(number);
        this.genre = new SimpleStringProperty(gender);
        this.birth =  new SimpleObjectProperty<LocalDate>(birth);
    }
    public Athlete(String name, String sport, String country, int number, String gender){
        this.name = new SimpleStringProperty(name);
        this.sport = new SimpleStringProperty(sport);
        this.country = new SimpleStringProperty(country);
        this.number = new SimpleIntegerProperty(number);
        this.genre = new SimpleStringProperty(gender);
        this.birth =  new SimpleObjectProperty<LocalDate>();
    }

    public void insertIntoDB(){
        String insertSQL = "INSERT INTO ATLETA"
                + "(NOME, ESPORTE, PAIS, NUMERO, GENERO, NASCIMENTO) VALUES"
                + "(?,?,?,?,?,?)";

        // Get connection to data base
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = Bridge.connectDB();
            preparedStatement = conn.prepareStatement(insertSQL);

            preparedStatement.setString(1, name.get());
            preparedStatement.setString(2, sport.get());
            preparedStatement.setString(3, country.get());
            preparedStatement.setInt(4, number.get());
            preparedStatement.setString(5, genre.get());
            if(birth.get() != null)
                preparedStatement.setDate(6, Date.valueOf(birth.get()));
            else
                preparedStatement.setDate(6, null);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO INSERIR ATLETA!!!");
        } finally {
            // Close connection
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty(){
        return name;
    }

    public final String getCountry() {
        return country.get();
    }

    public final void setCountry(String country) {
        this.country.set(country);
    }

    public StringProperty countryProperty(){
        return country;
    }

    public final String getSport() {
        return sport.get();
    }

    public final void setSport(String sport){
        this.sport.set(sport);
    }

    public StringProperty sportProperty(){
        return sport;
    }

    public final LocalDate getBirth() {
        return birth.get();
    }

    public final void setBirth(LocalDate birth) {
        this.birth.set(birth);
    }

    public ObjectProperty<LocalDate> birthProperty(){
        return birth;
    }

    public final void setGenre(String genre) {
        this.genre.set(genre);
    }

    public final String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty(){
        return genre;
    }

    public final void setNumber(Integer number) {
        this.number.set(number);
    }


    public final int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty(){
        return number;
    }
}
