/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RouteDAO;
import dao.StationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Route;
import model.Station;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "BusDetailsController", urlPatterns = {"/bus-details"})
public class BusDetailsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        RouteDAO routeDAO = new RouteDAO();
        StationDAO stationDAO = new StationDAO();
        Route r;
        List<Station> s;
        try {
            r = routeDAO.getAllRoutesWithBusID(id);
            r.setStartTime(formatTime(r.getStartTime()));
            r.setEndTime(formatTime(r.getEndTime()));
            s = stationDAO.getStationByBusID(id);
            request.setAttribute("route", r);
            request.setAttribute("station", s);
        } catch (Exception ex) {
            Logger.getLogger(BusDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("bus-details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private String formatTime(String time) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            return timeFormat.format(timeFormat.parse(time));
        } catch (Exception ex) {
            return time;
        }
    }

}
