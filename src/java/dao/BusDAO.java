/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Bus;

/**
 *
 * @author ADMIN
 */
public class BusDAO extends DBContext {

    public Bus getBusById(int id) {
        try {
            String query = "select * from bus b JOIN Route r ON b.routeID = r.id where b.id = ?";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Bus(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price"),
                                resultSet.getInt("routerID")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addBus(Bus bus) {
        try {
            String query = "INSERT INTO bus (name, price, routeID) VALUES (?, ?, ?)";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, bus.getName());
                preparedStatement.setInt(2, bus.getPrice());
                preparedStatement.setInt(3, bus.getRouteID());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editBus(Bus bus) {
        try {
            String query = "UPDATE bus SET name = ?, price = ?, routeID = ? WHERE id = ?";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, bus.getName());
                preparedStatement.setInt(2, bus.getPrice());
                preparedStatement.setInt(3, bus.getRouteID());
                preparedStatement.setInt(4, bus.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBus(int id) {
        try {
            String query = "DELETE FROM bus WHERE id = ?";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
