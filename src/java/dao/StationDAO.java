package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Station;

public class StationDAO extends DBContext {

    // Method to get stations by bus ID
    public List<Station> getStationByBusID(int id) {
        List<Station> stations = new ArrayList<>();
        try {
            String query = "SELECT s.id, s.stationName FROM Bus b "
                    + "INNER JOIN Route r ON b.routeID = r.id "
                    + "INNER JOIN RoutePassesStation rps ON r.id = rps.routeID "
                    + "INNER JOIN Station s ON rps.stationID = s.id "
                    + "WHERE b.id = ?";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Station station = new Station();
                        station.setId(resultSet.getInt("id"));
                        station.setStationName(resultSet.getString("stationName"));
                        stations.add(station);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stations;
    }

    // Method to add a new station
    public boolean addStation(Station station) {
        try {
            String query = "INSERT INTO Station (stationName) VALUES (?)";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, station.getStationName());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing station
    public boolean updateStation(Station station) {
        try {
            String query = "UPDATE Station SET stationName = ? WHERE id = ?";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, station.getStationName());
                preparedStatement.setInt(2, station.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a station
    public boolean deleteStation(int routeID, int stationId) {
        try {
            String query = "DELETE FROM RoutePassesStation WHERE routeID = ? and stationID = ?";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, routeID);
                preparedStatement.setInt(2, stationId);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getLastStationID() {
        int lastStationID = -1; // Initialize with a default value

        try {
            String query = "select top(1) id from Station order by id desc";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        lastStationID = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastStationID;
    }

    // Method to add a station to a route
    public boolean addStationToRoute(int routeId, int stationId) {
        try {
            String query = "INSERT INTO RoutePassesStation (routeID, stationID) VALUES (?, ?)";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, routeId);
                preparedStatement.setInt(2, stationId);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {

    }
}
