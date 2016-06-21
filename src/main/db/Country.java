package main.db;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Country {
    private StringProperty name;
    private IntegerProperty gold;
    private IntegerProperty silver;
    private IntegerProperty bronze;
    private IntegerProperty total;
    private IntegerProperty place;

    public Country(String name){
        this.name = new SimpleStringProperty(name);
        this.gold = new SimpleIntegerProperty();
        this.silver = new SimpleIntegerProperty();
        this.bronze = new SimpleIntegerProperty();
        this.total = new SimpleIntegerProperty();
        this.place = new SimpleIntegerProperty();
    }

    public void insertIntoDB() throws SQLException {
        String insertSQL = "INSERT INTO pais"
                + "(NOME) VALUES"
                + "(?)";

        // Get connection to data base
        Connection conn = Bridge.connectDB();
        PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);

        preparedStatement.setString(1, name.get());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        conn.close();
        System.out.println("Conexão encerrada");
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

    public final int getGold() {
        return gold.get();
    }

    public final void setGold(int gold) {
        this.gold.set(gold);
    }

    public IntegerProperty goldProperty(){
        return gold;
    }

    public final int getSilver() {
        return silver.get();
    }

    public final void setSilver(int silver) {
        this.silver.set(silver);
    }

    public IntegerProperty silverProperty(){
        return silver;
    }
    public final int getBronze() {
        return bronze.get();
    }

    public final void setBronze(int bronze) {
        this.bronze.set(bronze);
    }

    public IntegerProperty bronzeProperty(){
        return bronze;
    }
    public final int getTotal() {
        return total.get();
    }

    public final void setTotal(int total) {
        this.total.set(total);
    }

    public IntegerProperty totalProperty(){
        return total;
    }


    public final int getPlace() {
        return place.get();
    }

    public final void setPlace(int place) {
        this.place.set(place);
    }

    public IntegerProperty placeProperty(){
        return place;
    }
}