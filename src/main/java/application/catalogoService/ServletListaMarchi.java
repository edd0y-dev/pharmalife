package application.catalogoService;

import storage.prodotto.Prodotto;
import storage.prodotto.ProdottoDAO;
import storage.prodotto.ProdottoDAOMethod;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ServletListaMarchi", value = "/ServletListaMarchi")
public class ServletListaMarchi extends HttpServlet {

    private   static int start=0;
    private static  final  int end=9;
    private ProdottoDAOMethod prodottoDAO;

    public ServletListaMarchi() throws SQLException {
        prodottoDAO = new ProdottoDAO();
    }

    public ServletListaMarchi(ProdottoDAO prodottoDAO) {
        this.prodottoDAO = prodottoDAO;
    }

    @Generated
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Generated
    @Override
    /**
     * richiama il metodo visualizzaMarchi
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        visualizzaListaMarchi(request, response);
    }

    /**
     * Questo metodo consente di visualizzare la lista dei marchi disponibili nel catalogo.
     * @pre //
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @post //
     */

    public void visualizzaListaMarchi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String opzione="Marchio";
        String nomeMarchio=(request.getParameter("value"));
        String nomejsp=request.getParameter("nomejsp");


        if(nomejsp.equals("header")){
            start=0;
        }else {
            start+=25;
        }
        ArrayList<Prodotto> prodotti= prodottoDAO.cercaProdottiMarchio(nomeMarchio,start,end);

        request.setAttribute("prodotti",prodotti);
        request.setAttribute("opzione",opzione);
        request.setAttribute("nomeMarchio",nomeMarchio);
        RequestDispatcher dispatcher= request.getRequestDispatcher("WEB-INF/pagine/listaProdotti.jsp");
        dispatcher.forward(request,response);
    }
}

