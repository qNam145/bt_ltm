package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bo.UserBO;

@WebServlet(urlPatterns = {"/auth", "/login", "/register"})
public class CheckLogin extends HttpServlet {
    private static final UserBO bo = new UserBO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().equals(req.getContextPath() + "/auth")) {
            System.out.println(req.getParameter("error"));
            if (req.getParameter("error") != null) {
                req.setAttribute("error", "Invalid username or password");
            }
            else {
                req.setAttribute("error", "");
            }
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String URL = req.getRequestURI().replace(req.getContextPath(), "");
        switch (URL) {
            case "/login":
                try {
                    boolean isValid = bo.isValidUser(username, password);
                    if (isValid) {
                        req.getSession().setAttribute("username", username);
                        resp.sendRedirect(req.getContextPath() + "/CompressVideo");
                    } else {
                        resp.sendRedirect(req.getContextPath() + "/auth?error");
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/register":
                try {
                    String regPassword = req.getParameter("reg-password");
                    bo.addUser(username, regPassword);
                    resp.sendRedirect(req.getContextPath() + "/auth");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
    
}
