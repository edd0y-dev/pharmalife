package storage.ordine;

import storage.carrello.Carrello;
import storage.utils.ConPool;
import storage.utente.Utente;

import java.sql.*;
import java.util.ArrayList;

@Generated
public class OrdineDAO implements OrdineDAOMethod {

    private ConPool conpool=ConPool.getInstance();
    private Connection connection= conpool.getConnection();

    public OrdineDAO() throws SQLException {

    }

    /**
     * Questo metodo retituisce un ordine con un determinato id
     * @param idOrdine è l'id che identifica l'ordine
     * @return un oggetto di tipo Ordine
     */
    public Ordine cercaOrdine(int idOrdine) {

        try{

            PreparedStatement ps;
            ps=connection.prepareStatement("select * from Ordine where idOrdine=? ");
            ps.setInt(1,idOrdine);

            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                Ordine ordine= new Ordine();
                ordine.setIdOrdine(rs.getInt(1));
                ordine.setDataOrdine(rs.getDate("dataOrdine"));
                ordine.setOra(rs.getTime("oraOrdine"));
                ordine.getCarrello().setListaProdottiString(rs.getString("listaProdotti"));
                ordine.getUtente().setCodiceFiscale(rs.getString("cfUtente"));
               return ordine;
            }
        }catch (SQLException sqlException){

        }
        return null;

    }


    /**
     * Questo metodo elimina un ordine con un determinato id
     * @param idOrdine è l'id che identifica l'ordine
     */
    @Override
    public void deleteOrdine(int idOrdine)  {
        try  {
            PreparedStatement ps;
            ps = connection.prepareStatement("delete from Ordine where idOrdine=?");
            ps.setInt(1, idOrdine);
            ps.execute();
        } catch (SQLException sqlException) {
        }
    }

    /**
     * Questo metodo genera un ordine dal carrello
     * @param o è un oggetto di tipo ordine
     */
    public void insertCarrello(Ordine o) {
        try  {
            System.out.println("wee");
            PreparedStatement ps = connection.prepareStatement("insert into Ordine(dataOrdine,oraOrdine,listaProdotti,cfUtente) " +
                    "value (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, o.getDataOrdine());
            ps.setTime(2, o.getOra());

            ps.setString(3,o.getUtente().getCarrello().prodottiToString().toString());
            ps.setString(4, o.getUtente().getCodiceFiscale());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            o.setIdOrdine(id);

        } catch (SQLException sqlException) {

        }
    }

    /**
     * Questo metodo fa update di un ordine con un determinato id
     * @param idOrdine è l'id che identifica l'ordine
     * @param o è un oggetto di tipo ordine
     */
    public void updateOrdine(Ordine o, int idOrdine) {
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("update Messaggio set dataOrdine = ?, oraOrdine = ?, cfUtente = ?" +
                    "where idOrdine = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, (Date) o.getDataOrdine());
            ps.setTime(2, o.getOra());
            ps.setString(3, o.getUtente().getCodiceFiscale());
            ps.setInt(4, o.getIdOrdine());
            if(ps.executeUpdate() != 1) {
                throw new RuntimeException("update error");
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }


    /**
     * Questo metodo retituisce tutti gli ordini
     * @return un ArrayList di oggetti di tipo ordine
     */
    public ArrayList<Ordine> doRetraiveByAllOrdini() {
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("select * from Ordine");
            ResultSet rs = ps.executeQuery();
            ArrayList<Ordine> lista = new ArrayList<>();
            while (rs.next()) {
                Ordine ordine=new Ordine();
                ordine.setIdOrdine(rs.getInt(1));
                ordine.setDataOrdine(rs.getDate(2));
                ordine.setOra(rs.getTime(3));
                Utente utente= new Utente();
                utente.setCodiceFiscale(rs.getString(4));
                ordine.setUtente(utente);
                lista.add(ordine);
            }

            return lista;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }


    /**
     * Questo metodo cerca degli ordini
     * @param start è il parametro di partenza per la ricerca
     * @param end è il parametro di arrivo per la ricerca
     * @return un oggetto di tipo Ordine
     */
    public ArrayList<Ordine> cercaOrdini(int start, int end) {
        ArrayList<Ordine> lista =new ArrayList<>();
        try{

            PreparedStatement ps=connection.prepareStatement("select * from Ordine order by idOrdine" +
                    "limit ? offset ?");
            ps.setInt(1,start);
            ps.setInt(2,end);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                Ordine ordine=new Ordine();
                ordine.setIdOrdine(rs.getInt(1));
                ordine.setDataOrdine(rs.getDate(2));
                ordine.setOra(rs.getTime(3));
                ordine.getUtente().setCodiceFiscale(rs.getString(4));
                lista.add(ordine);
            }

            return lista;
        }catch (SQLException sqlException){
            throw new RuntimeException(sqlException);
        }
    }


    /**
     * Questo metodo retituisce tutti gli ordini di un determinato utente
     * @param utente è un oggetto di tipo utente
     * @return un ArrayList di oggetti di tipo Ordine
     */
    @Override
    public ArrayList<Ordine> doRetraiveByAllById(Utente utente) {
        ArrayList<Ordine> ordini = new ArrayList<>();
        try{

            PreparedStatement ps=connection.prepareStatement("select distinct  * from Ordine where cfUtente=?");
            ps.setString(1,utente.getCodiceFiscale());
            ResultSet rs= ps.executeQuery();
            while (rs.next()){

                Ordine ordine= new Ordine();
                ordine.setIdOrdine(rs.getInt(1));
                ordine.setDataOrdine(rs.getDate("dataOrdine"));
                ordine.setOra(rs.getTime("oraOrdine"));
                Carrello carrello= new Carrello();
                carrello.setListaProdottiString(rs.getString("listaProdotti"));
                ordine.setCarrello(carrello);
                Utente utente1= new Utente();
                utente1.setCodiceFiscale(rs.getString("cfUtente"));
                ordine.setUtente(utente);
                ordini.add(ordine);
            }
        }catch (SQLException sqlException){
            throw new RuntimeException(sqlException);
        }
        return ordini;
    }




}