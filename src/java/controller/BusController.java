/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BusDAO;
import dao.RouteDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Bus;
import model.Route;
import model.Users;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "BusController", urlPatterns = {"/bus-management"})
public class BusController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Users account = (Users) session.getAttribute("account");
        if (account != null && account.getRole().equals("employee")) {
            RouteDAO routeDAO = new RouteDAO();
            String pageParam = request.getParameter("page");
            String searchParam = request.getParameter("search");
            int page = 1; // Default to the first page
            int pageSize = 5; // Set the desired page size
            if (pageParam != null && !pageParam.isEmpty()) {
                // Parse the page parameter to an integer
                page = Integer.parseInt(pageParam);
            }
            try {
                List<Route> routes = routeDAO.getAllRoutesWithBusWithParam(searchParam);
                List<Route> routesForSelect = routeDAO.getAllRoutes();
                List<Route> pagingRoute = routeDAO.Paging(routes, page, pageSize);
                for (Route route : routes) {
                    route.setStartTime(formatTime(route.getStartTime()));
                    route.setEndTime(formatTime(route.getEndTime()));
                }

                request.setAttribute("routes", pagingRoute);
                request.setAttribute("listR", routesForSelect);
                request.setAttribute("totalPages", routes.size() % pageSize == 0 ? (routes.size() / pageSize) : (routes.size() / pageSize + 1));
                request.setAttribute("currentPage", page);
                request.setAttribute("searchParam", searchParam);
            } catch (Exception ex) {
                Logger.getLogger(BusController.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.getRequestDispatcher("bus-management.jsp").forward(request, response);
        } else {
            String encodedMessage = URLEncoder.encode("Vui lòng đăng nhập để tiếp tục!", "UTF-8");
            String redirectURL = "login?err=" + encodedMessage;
            response.sendRedirect(redirectURL);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Users account = (Users) session.getAttribute("account");
        if (account != null && account.getRole().equals("employee")) {
            String action = request.getParameter("action");
            if (action.equals("add")) {

                String name = request.getParameter("busName");
                int price = Integer.parseInt(request.getParameter("price"));
                int routeId = Integer.parseInt(request.getParameter("routeID"));
                BusDAO busDAO = new BusDAO();
                Bus bus = new Bus(name, price, routeId);
                boolean isAdded = busDAO.addBus(bus);
                if (isAdded) {
                    String encodedMessage = URLEncoder.encode("Tạo Bus thành công!", "UTF-8");
                    String redirectURL = "bus-management?msg=" + encodedMessage;
                    response.sendRedirect(redirectURL);
                } else {
                    String encodedMessage = URLEncoder.encode("Tạo Bus thất bại!", "UTF-8");
                    String redirectURL = "bus-management?err=" + encodedMessage;
                    response.sendRedirect(redirectURL);
                }
            } else if (action.equals("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("busName");
                int price = Integer.parseInt(request.getParameter("price"));
                int routeId = Integer.parseInt(request.getParameter("routeID"));
                BusDAO busDAO = new BusDAO();
                Bus bus = new Bus(id, name, price, routeId);
                System.out.println("Bus" + bus);
                boolean isAdded = busDAO.editBus(bus);
                if (isAdded) {
                    String encodedMessage = URLEncoder.encode("Sửa Bus thành công!", "UTF-8");
                    String redirectURL = "bus-management?msg=" + encodedMessage;
                    response.sendRedirect(redirectURL);
                } else {
                    String encodedMessage = URLEncoder.encode("sửa Bus thất bại!", "UTF-8");
                    String redirectURL = "bus-management?err=" + encodedMessage;
                    response.sendRedirect(redirectURL);

                }
            }
        } else {
            String encodedMessage = URLEncoder.encode("Vui lòng đăng nhập để tiếp tục!", "UTF-8");
            String redirectURL = "login?err=" + encodedMessage;
            response.sendRedirect(redirectURL);
        }

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
