package application.global;

import storage.categoria.Categoria;
import storage.categoria.CategoriaDAO;
import storage.marchio.Marchio;
import storage.marchio.MarchioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ServletStart", value = "/ServletStart",loadOnStartup = 0) // Serve per chiamare automaticamente la servlet

@Generated
public class ServletStart extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();

        CategoriaDAO categoriaDAO= null;
        try {
            categoriaDAO = new CategoriaDAO();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ArrayList<Categoria>categorie= categoriaDAO.doRetraiveByAllCategorieRoot();
        MarchioDAO marchioDAO= null;
        try {
            marchioDAO = new MarchioDAO();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ArrayList<Marchio> marchi= marchioDAO.doRetraiveByAllMarchi();
        getServletContext().setAttribute("marchi",marchi); // ServletContext contiene attributi che sono visibili in tutte le pagine
        getServletContext().setAttribute("categorie",categorie);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
