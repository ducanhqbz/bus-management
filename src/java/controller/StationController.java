/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.StationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import model.Station;
import model.Users;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "StationController", urlPatterns = {"/station-management"})
public class StationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users account = (Users) session.getAttribute("account");
        if (account != null && account.getRole().equals("employee")) {
            String action = request.getParameter("action");
            switch (action) {
                case "add": {
                    String stationName = request.getParameter("stationName");
                    int routeId = Integer.parseInt(request.getParameter("routeId"));
                    StationDAO stationDAO = new StationDAO();
                    Station station = new Station(stationName);
                    System.out.println(routeId);
                    boolean isAdded = stationDAO.addStation(station);
                    if (isAdded) {
                        int newStationId = stationDAO.getLastStationID();
                        boolean isAddRoutePassStation = stationDAO.addStationToRoute(routeId, newStationId);
                        if (isAddRoutePassStation) {
                            String encodedMessage = URLEncoder.encode("Thêm trạm dừng thành công!", "UTF-8");
                            String redirectURL = "route-management?msg=" + encodedMessage;
                            response.sendRedirect(redirectURL);
                        }

                    } else {
                        String encodedMessage = URLEncoder.encode("Thêm trạm dừng thất bại!", "UTF-8");
                        String redirectURL = "route-management?err=" + encodedMessage;
                        response.sendRedirect(redirectURL);
                    }
                    break;
                }
                case "edit": {
                    int id = Integer.parseInt(request.getParameter("stationId"));
                    int routeId = Integer.parseInt(request.getParameter("routeId"));
                    System.out.println(routeId);
                    String stationName = request.getParameter("stationName");
                    StationDAO stationDAO = new StationDAO();
                    Station station = new Station(id, stationName);
                    boolean isAdded = stationDAO.updateStation(station);
                    if (isAdded) {
                        String encodedMessage = URLEncoder.encode("Sửa trạm dừng thành công!", "UTF-8");
                        String redirectURL = "route-management?msg=" + encodedMessage;
                        response.sendRedirect(redirectURL);
                    } else {
                        String encodedMessage = URLEncoder.encode("Sửa trạm dừng thất bại!", "UTF-8");
                        String redirectURL = "route-management?err=" + encodedMessage;
                        response.sendRedirect(redirectURL);
                    }
                    break;
                }
                case "delete": {
                    System.out.println("go to delete");
                    int stationId = Integer.parseInt(request.getParameter("stationId"));
                    int routeId = Integer.parseInt(request.getParameter("routeId"));

                    //                int routeId = Integer.parseInt(request.getParameter("routeId"));
                    StationDAO stationDAO = new StationDAO();
                    boolean isdeleted = stationDAO.deleteStation(routeId, stationId);
                    System.out.println(stationId + "hahahah " + routeId);
                    if (isdeleted) {
                        String encodedMessage = URLEncoder.encode("Xóa trạm dừng thành công!", "UTF-8");
                        String redirectURL = "route-management?msg=" + encodedMessage;
                        response.sendRedirect(redirectURL);
                    } else {
                        String encodedMessage = URLEncoder.encode("Xóa trạm dừng thất bại!", "UTF-8");
                        String redirectURL = "route-management?err=" + encodedMessage;
                        response.sendRedirect(redirectURL);
                    }
                    break;
                }
                default:
                    break;
            }
        } else {
            String encodedMessage = URLEncoder.encode("Vui lòng đăng nhập để tiếp tục!", "UTF-8");
            String redirectURL = "login?err=" + encodedMessage;
            response.sendRedirect(redirectURL);
        }
    }
}
