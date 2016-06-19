package main.db;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Country {
    private StringProperty name;

    public Country(String name){
        this.name = new SimpleStringProperty(name);
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
}
