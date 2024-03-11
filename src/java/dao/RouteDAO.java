/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Route;
import model.RoutePassesStation;

public class RouteDAO extends DBContext {

    public boolean addRoute(Route route) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO Route (startPoint, endPoint, startTime, endTime, frequency) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, route.getStartPoint());
            stmt.setString(2, route.getEndPoint());
            stmt.setString(3, route.getStartTime());
            stmt.setString(4, route.getEndTime());
            stmt.setInt(5, route.getFrequency());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RouteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Method to edit an existing route
    public boolean editRoute(Route route) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE Route SET startPoint=?, endPoint=?, startTime=?, endTime=?, frequency=? WHERE id=?")) {
            stmt.setString(1, route.getStartPoint());
            stmt.setString(2, route.getEndPoint());
            stmt.setString(3, route.getStartTime());
            stmt.setString(4, route.getEndTime());
            stmt.setInt(5, route.getFrequency());
            stmt.setInt(6, route.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RouteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Route> getAllRoutesWithBus() throws Exception {
        List<Route> routes = new ArrayList<>();
        try {
            String query = "SELECT r.*, b.name, b.price FROM Route r JOIN Bus b ON r.id = b.routeID";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query);  ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Route route = new Route();
                    route.setId(resultSet.getInt("id"));
                    route.setStartPoint(resultSet.getString("startPoint"));
                    route.setEndPoint(resultSet.getString("endPoint"));
                    route.setStartTime(resultSet.getString("startTime"));
                    route.setEndTime(resultSet.getString("endTime"));
                    route.setFrequency(resultSet.getInt("frequency"));
                    route.setName(resultSet.getString("name"));
                    route.setPrice(resultSet.getInt("price"));
                    routes.add(route);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return routes;
    }

    public List<Route> getAllRoutes() throws Exception {
        List<Route> routes = new ArrayList<>();
        try {
            String query = "SELECT * from Route";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query);  ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Route route = new Route();
                    route.setId(resultSet.getInt("id"));
                    route.setStartPoint(resultSet.getString("startPoint"));
                    route.setEndPoint(resultSet.getString("endPoint"));
                    route.setStartTime(resultSet.getString("startTime"));
                    route.setEndTime(resultSet.getString("endTime"));
                    route.setFrequency(resultSet.getInt("frequency"));
                    route.setRoutePassesStation(getAllRoutePassStattionByRouteID(resultSet.getInt("id"))
                    );
                    routes.add(route);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return routes;
    }

    public List<RoutePassesStation> getAllRoutePassStattionByRouteID(int id) {
        List<RoutePassesStation> routePassesStations = new ArrayList<>();
        try {
            String query = "SELECT r.id as routeID, s.id AS StationID, s.stationName AS StationName\n"
                    + "	   FROM Route r JOIN  RoutePassesStation rp ON r.id = rp.routeID\n"
                    + "    JOIN \n"
                    + "    Station s ON rp.stationID = s.id\n"
                    + "    where r.id =?";

            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        RoutePassesStation routerPassStation = new RoutePassesStation();
                        routerPassStation.setStationID(resultSet.getInt("routeID"));
                        routerPassStation.setStationID(resultSet.getInt("StationID"));
                        routerPassStation.setStationName(resultSet.getString("StationName"));
                        routePassesStations.add(routerPassStation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routePassesStations;

    }

    public List<Route> getAllRoutesWithBusWithParam(String searchParam) throws Exception {
        List<Route> routes = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT r.*,b.id as busID, b.name, b.price FROM Route r JOIN Bus b ON r.id = b.routeID where 1=1");
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND (b.name LIKE ? OR r.startPoint LIKE ? or r.endPoint LIKE ?)");
                list.add("%" + searchParam + "%");
                list.add("%" + searchParam + "%");
                list.add("%" + searchParam + "%");
            }
            query.append(" ORDER BY r.id DESC");
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(query.toString());
            mapParams(preparedStatement, list);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Route route = new Route();
                    route.setId(resultSet.getInt("id"));
                    route.setStartPoint(resultSet.getString("startPoint"));
                    route.setEndPoint(resultSet.getString("endPoint"));
                    route.setStartTime(resultSet.getString("startTime"));
                    route.setEndTime(resultSet.getString("endTime"));
                    route.setFrequency(resultSet.getInt("frequency"));
                    route.setBusID(resultSet.getInt("busID"));
                    route.setName(resultSet.getString("name"));
                    route.setPrice(resultSet.getInt("price"));
                    route.setRoutePassesStation(getAllRoutePassStattionByRouteID(resultSet.getInt("id"))
                    );
                    routes.add(route);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return routes;
    }

    public List<Route> getAllRouteWithParam(String searchParam) throws Exception {
        List<Route> routes = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * from Route where 1=1");
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append("AND (startPoint LIKE ? or endPoint LIKE ?)");
                list.add("%" + searchParam + "%");
                list.add("%" + searchParam + "%");
            }
            query.append(" ORDER BY id DESC");
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(query.toString());
            mapParams(preparedStatement, list);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Route route = new Route();
                    route.setId(resultSet.getInt("id"));
                    route.setStartPoint(resultSet.getString("startPoint"));
                    route.setEndPoint(resultSet.getString("endPoint"));
                    route.setStartTime(resultSet.getString("startTime"));
                    route.setEndTime(resultSet.getString("endTime"));
                    route.setFrequency(resultSet.getInt("frequency"));
//                    route.setBusID(resultSet.getInt("busID"));
//                    route.setName(resultSet.getString("name"));
//                    route.setPrice(resultSet.getInt("price"));
                    route.setRoutePassesStation(getAllRoutePassStattionByRouteID(resultSet.getInt("id"))
                    );
                    routes.add(route);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return routes;
    }

    public Route getAllRoutesWithBusID(int id) throws Exception {
        Route route = new Route();
        try {
            String query = "SELECT r.*, b.name, b.price FROM Route r JOIN Bus b ON r.id = b.routeID WHERE b.id = ?";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        route.setId(resultSet.getInt("id"));
                        route.setStartPoint(resultSet.getString("startPoint"));
                        route.setEndPoint(resultSet.getString("endPoint"));
                        route.setStartTime(resultSet.getString("startTime"));
                        route.setEndTime(resultSet.getString("endTime"));
                        route.setFrequency(resultSet.getInt("frequency"));
                        route.setName(resultSet.getString("name"));
                        route.setPrice(resultSet.getInt("price"));
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return route;
    }

    public List<Route> getRoutesByStations(String stationName1, String stationName2) {
        List<Route> routes = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT r.* ,b.name, b.price\n"
                    + "FROM Route r JOIN Bus b ON r.id = b.routeID\n"
                    + "JOIN RoutePassesStation rps_start ON r.id = rps_start.routeID\n"
                    + "JOIN Station s_start ON rps_start.stationID = s_start.id\n"
                    + "JOIN RoutePassesStation rps_end ON r.id = rps_end.routeID\n"
                    + "JOIN Station s_end ON rps_end.stationID = s_end.id\n"
                    + "WHERE (s_start.stationName LIKE ? or r.startPoint Like ?)\n"
                    + "AND (s_end.stationName LIKE ? OR r.endPoint Like ?)";

            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + stationName1 + "%");
                preparedStatement.setString(2, "%" + stationName1 + "%");
                preparedStatement.setString(3, "%" + stationName2 + "%");
                preparedStatement.setString(4, "%" + stationName2 + "%");

                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Route route = new Route();
                        route.setId(resultSet.getInt("id"));
                        route.setStartPoint(resultSet.getString("startPoint"));
                        route.setEndPoint(resultSet.getString("endPoint"));
                        route.setStartTime(resultSet.getString("startTime"));
                        route.setEndTime(resultSet.getString("endTime"));
                        route.setFrequency(resultSet.getInt("frequency"));
                        route.setName(resultSet.getString("name"));
                        route.setPrice(resultSet.getInt("price"));
                        routes.add(route);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    public void mapParams(PreparedStatement ps, List<Object> args) throws SQLException {
        int i = 1;
        for (Object arg : args) {
            if (arg instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                ps.setInt(i++, (Integer) arg);
            } else if (arg instanceof Long) {
                ps.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                ps.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                ps.setFloat(i++, (Float) arg);
            } else {
                ps.setString(i++, (String) arg);
            }

        }
    }

    public List<Route> Paging(List<Route> routes, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, routes.size());

        if (fromIndex > toIndex) {
            // Handle the case where fromIndex is greater than toIndex
            fromIndex = toIndex;
        }

        return routes.subList(fromIndex, toIndex);
    }

    public static void main(String[] args) {
        try {
            RouteDAO o = new RouteDAO();
            List<Route> r = o.getAllRouteWithParam("");
//            System.out.println(r);
            for (Route r1 : r) {
                System.out.println(r1);
            }
        } catch (Exception ex) {
            Logger.getLogger(RouteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
