/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;
import model.Users;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UserController", urlPatterns = {"/user-management"})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users account = (Users) session.getAttribute("account");
        if (account != null && account.getRole().equals("admin")) {
            UserDAO userDAO = new UserDAO();
            String pageParam = request.getParameter("page");
            String searchParam = request.getParameter("search");
            int page = 1; // Default to the first page
            int pageSize = 5; // Set the desired page size
            if (pageParam != null && !pageParam.isEmpty()) {
                // Parse the page parameter to an integer
                page = Integer.parseInt(pageParam);
            }
            List<Users> users = userDAO.getAllUserWithParam(searchParam);

            List<Users> pagingUser = userDAO.Paging(users, page, pageSize);

            request.setAttribute("users", pagingUser);
            request.setAttribute("totalPages", users.size() % pageSize == 0 ? (users.size() / pageSize) : (users.size() / pageSize + 1));
            request.setAttribute("currentPage", page);
            request.setAttribute("searchParam", searchParam);
            request.getRequestDispatcher("user-management.jsp").forward(request, response);
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
        Users account = (Users) session.getAttribute("account");
        if (account != null && account.getRole().equals("admin")) {
            UserDAO userDAO = new UserDAO();
            String action = request.getParameter("action");
            switch (action) {
                case "add": {
                    String email = request.getParameter("email");
                    String name = request.getParameter("name");
                    String password = request.getParameter("psw");
                    String password2 = request.getParameter("psw2");
                    String role = request.getParameter("role");
                    if (password.equals(password2)) {
                        Users u = new Users(email, password, role, name);
                        boolean isAddes = userDAO.registerUser(u);
                        if (isAddes) {
                            String encodedMessage = URLEncoder.encode("Tạo tài khoản thành công!", "UTF-8");
                            String redirectURL = "user-management?msg=" + encodedMessage;
                            response.sendRedirect(redirectURL);
                        }
                    } else {
                        String encodedMessage = URLEncoder.encode("Mật khẩu chưa khớp!", "UTF-8");
                        String redirectURL = "user-management?err=" + encodedMessage;
                        response.sendRedirect(redirectURL);
                    }
                    break;
                }
                case "edit": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String email = request.getParameter("email");
                    String name = request.getParameter("name");
                    String role = request.getParameter("role");
                    Users u = new Users(id, email, role, name);
                    boolean isUpdated = userDAO.updateUser(u);
                    if (isUpdated) {
                        String encodedMessage = URLEncoder.encode("Cập nhật tài khoản thành công!", "UTF-8");
                        String redirectURL = "user-management?msg=" + encodedMessage;
                        response.sendRedirect(redirectURL);
                    } else {
                        String encodedMessage = URLEncoder.encode("Cập nhật tài khoản thất bại!", "UTF-8");
                        String redirectURL = "user-management?err=" + encodedMessage;
                        response.sendRedirect(redirectURL);
                    }
                    break;
                }
                case "delete": {
                    int id = Integer.parseInt(request.getParameter("id"));

                    boolean isDeleted = userDAO.deleteUser(id);
                    if (isDeleted) {
                        String encodedMessage = URLEncoder.encode("Xóa tài khoản thành công!", "UTF-8");
                        String redirectURL = "user-management?msg=" + encodedMessage;
                        response.sendRedirect(redirectURL);
                    } else {
                        String encodedMessage = URLEncoder.encode("Xóa tài khoản thất bại!", "UTF-8");
                        String redirectURL = "user-management?err=" + encodedMessage;
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
