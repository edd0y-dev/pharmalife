package application.adminService;

import storage.messaggio.Messaggio;
import storage.messaggio.MessaggioDAO;
import storage.messaggio.MessaggioDAOMethod;
import storage.ordine.Ordine;
import storage.ordine.OrdineDAO;
import storage.ordine.OrdineDAOMethod;
import storage.prodotto.Prodotto;
import storage.prodotto.ProdottoDAO;
import storage.prodotto.ProdottoDAOMethod;
import storage.utente.Utente;
import storage.utente.UtenteDAO;
import storage.utente.UtenteDAOMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ServletAdmin", value = "/ServletAdmin")
public class ServletAdmin extends HttpServlet {
    private MessaggioDAOMethod messaggioDAO;
    private UtenteDAOMethod utenteDAO;
    private ProdottoDAOMethod prodottoDAO;
    private OrdineDAOMethod ordineDAO;
    private ArrayList<Messaggio> messaggi;
    private ArrayList<Utente> utenti;
    private ArrayList<Prodotto> prodotti;
    private ArrayList<Ordine> ordini;

    public ServletAdmin() throws SQLException {
        messaggioDAO= new MessaggioDAO();
        utenteDAO= new UtenteDAO();
        prodottoDAO= new ProdottoDAO();
        ordineDAO= new OrdineDAO();
        messaggi= new ArrayList<>();
    }
    public ServletAdmin(MessaggioDAO messaggioDAO,UtenteDAO utenteDAO,ProdottoDAO prodottoDAO,OrdineDAO ordineDAO){
        this.messaggioDAO=messaggioDAO;
        this.utenteDAO=utenteDAO;
        this.prodottoDAO=prodottoDAO;
        this.ordineDAO=ordineDAO;
    }

    /**
     * richiama il doPost
     */
    @Generated
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }

    /**
     *
     * @param request oggetto che contenenti i dati inviati dal client ,relativi alla scelta dell'admin sull'inserrire un prodotto
     * al catalogo, visualizzare la lista dei messaggi degli utenti o  varie statistiche sulla piattaforma
     * @param response oggetto della Servlet utile per eseguire il forward
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String valore=request.getParameter("value");
            String pagina="";
            switch (valore){
                case "insertProdotto" :
                    pagina="WEB-INF/pagine/admin/insertProdotto.jsp";
                break;
                case "messaggi":
                    visualizzaMessaggi(request);
                    pagina="WEB-INF/pagine/admin/assistenzaUtenti.jsp";
                break;
                case "statistiche":
                    visualizzaStatistiche(request);
                    pagina="WEB-INF/pagine/admin/statistiche.jsp";
                    break;
            }
            RequestDispatcher dispatcher=request.getRequestDispatcher(pagina);
            dispatcher.forward(request,response);
    }

    /**
     * Questo metodo serve a settare nella request la lista di tutti i messaggi inviati da tutti gli utenti
     * @param request oggetto della Servlet utile allo scopo di settare i valori da inviare al client
     */
    public void visualizzaMessaggi(HttpServletRequest request){
        this.messaggi=messaggioDAO.doRetrieveByAllMessaggi();
        request.setAttribute("messaggi",messaggi);
    }

    /**
     * Questo metodo recupera dal database al la dimensione della lista  di tutti i messaggi, utenti iscitti alla piattaforma,
     * prodotti presenti nel catalogo e ordini effettuati da tutti gli utenti
     * La size di queste liste viene settata nella request
     * @param request oggetto della Servlet utile allo scopo di settare i valori da inviare al client
     */
    public void visualizzaStatistiche(HttpServletRequest request){
         this.messaggi = messaggioDAO.doRetrieveByAllMessaggi();
         this.utenti = utenteDAO.doRetrieveByAllUtenti();
         this.prodotti = prodottoDAO.doRetrieveByAllProdotti();
         this.ordini = ordineDAO.doRetraiveByAllOrdini();
         int sizeMessaggi = messaggi.size();
         int sizeUtenti = utenteDAO.doRetrieveByAllUtenti().size();
         int sizeProdotti = prodotti.size();
         int sizeOrdini = ordini.size();
         request.setAttribute("messaggi", sizeMessaggi);
         request.setAttribute("utenti", sizeUtenti);
         request.setAttribute("prodotti", sizeProdotti);
         request.setAttribute("ordini", sizeOrdini);
    }


}
