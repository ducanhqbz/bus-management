/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RouteDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import model.Route;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Route> routes = new ArrayList<>();

        try {
            RouteDAO routeDAO = new RouteDAO();
            routes = routeDAO.getAllRoutesWithBus();

            for (Route route : routes) {
                route.setStartTime(formatTime(route.getStartTime()));
                route.setEndTime(formatTime(route.getEndTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setCharacterEncoding("UTF-8");
        request.setAttribute("routes", routes);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    private String formatTime(String time) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            return timeFormat.format(timeFormat.parse(time));
        } catch (Exception ex) {
            return time;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startStation = request.getParameter("startStation");
        String endStation = request.getParameter("endStation");

        List<Route> routes = new ArrayList<>();
        try {
            RouteDAO routeDAO = new RouteDAO();
            routes = routeDAO.getRoutesByStations(startStation, endStation);

            for (Route route : routes) {
                route.setStartTime(formatTime(route.getStartTime()));
                route.setEndTime(formatTime(route.getEndTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setCharacterEncoding("UTF-8");
        request.setAttribute("routes", routes);
        request.setAttribute("startStation", startStation);
        request.setAttribute("endStation", endStation);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

}
