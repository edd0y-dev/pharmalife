package storage.prodotto;
import storage.categoria.Categoria;
import storage.marchio.Marchio;
import storage.utente.Utente;
import storage.carrello.Carrello;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@interface Generated {
}

@Generated
public class Prodotto {
    private int codiceProdotto;

    private ArrayList<Utente> utente;
    private Categoria categoria;
    private String nome;
    private double prezzo;
    private int quantita;
    private Marchio marchio;
    private String pathImmagine;
    private String descrizione;

    public Prodotto() {
    }

    public Prodotto(int codiceProdotto, Carrello carrello, ArrayList<Utente> utente,
                    Categoria categoria, String nome, double prezzo, int quantita, Marchio marchio) {
        this.codiceProdotto = codiceProdotto;

        this.utente = utente;
        this.categoria = categoria;
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.marchio = marchio;
    }

    public int getCodiceProdotto() {
        return codiceProdotto;
    }

    public void setCodiceProdotto(int codiceProdotto) {
        this.codiceProdotto = codiceProdotto;
    }


    public ArrayList<Utente> getUtente() {
        return utente;
    }

    public void setUtente(ArrayList<Utente> utente) {
        this.utente = utente;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public Marchio getMarchio() {
        return marchio;
    }

    public void setMarchio(Marchio marchio) {
        this.marchio = marchio;
    }

    public void addUtente(Utente utente){
        this.utente.add(utente);
    }

    public String getPathImmagine() {
        return pathImmagine;
    }

    public void setPathImmagine(String pathImmagine) {
        this.pathImmagine = pathImmagine;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setPrezzoQuantita(int quantita){
        this.prezzo*=quantita;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodotto prodotto = (Prodotto) o;
        return codiceProdotto == prodotto.codiceProdotto;
    }


}