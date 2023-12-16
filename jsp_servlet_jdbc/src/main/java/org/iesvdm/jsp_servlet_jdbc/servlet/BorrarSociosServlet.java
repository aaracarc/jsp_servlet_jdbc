package org.iesvdm.jsp_servlet_jdbc.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAO;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAOImpl;

import java.io.IOException;
@WebServlet(name = "BorrarSociosServlet", value = "/BorrarSociosServlet")
public class BorrarSociosServlet extends HttpServlet {
    private SocioDAO socioDAO = new SocioDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        try {
            // Obtener el ID del socio a borrar desde los parámetros de la solicitud.
            int socioId = Integer.parseInt(request.getParameter("socioId"));

            // Borrar el socio
            socioDAO.delete(socioId);

            // Redireccionar a la lista de socios después de borrar
            response.sendRedirect(request.getContextPath() + "/listarSocios.jsp");
            dispatcher = request.getRequestDispatcher("ListarSociosServlet");
        }catch (Exception ex) {
            request.setAttribute("error", "error");
            dispatcher = request.getRequestDispatcher("ListarSociosServlet");
        }
    }
}