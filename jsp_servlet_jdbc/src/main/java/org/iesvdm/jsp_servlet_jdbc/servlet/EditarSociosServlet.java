package org.iesvdm.jsp_servlet_jdbc.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAO;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAOImpl;
import org.iesvdm.jsp_servlet_jdbc.model.Socio;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "EditarSociosServlet", value = "/editarSocio")
public class EditarSociosServlet extends HttpServlet {

    private SocioDAO socioDAO = new SocioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
        int socioID = -1 ;
        boolean valida = true;

        try {
            socioID = Integer.parseInt(request.getParameter("socioID"));
        } catch (Exception ex) {
            ex.printStackTrace();
            valida = false;
        }

        if (valida) {

            Optional<Socio> optionalSocio = this.socioDAO.find(socioID);

            if (optionalSocio.isPresent()) {

                Socio socioAEditar = optionalSocio.get();

                request.setAttribute("socioAEditar", socioAEditar);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioEditarSocio.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario de edición.
        int socioID = Integer.parseInt(request.getParameter("socioID"));
        String nombre = request.getParameter("nombre");
        int estatura = Integer.parseInt(request.getParameter("estatura"));
        int edad = Integer.parseInt(request.getParameter("edad"));
        String localidad = request.getParameter("localidad");

        // Crear un objeto Socio con los nuevos datos.
        Socio socioActualizado = new Socio(socioID, nombre, estatura, edad, localidad);

        // Actualizar el socio en la base de datos.
        socioDAO.update(socioActualizado);

        // Redireccionar a la página de listar socios después de la actualización.
        response.sendRedirect(request.getContextPath() + "/listarSocios.jsp");
    }
}
