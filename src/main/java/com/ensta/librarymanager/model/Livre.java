package com.ensta.librarymanager.model;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String isbn;

    @Override
    public String toString() {
        return String.format("Livre{%d} \n   Titre :%s \n   Auteur :%s \n   Isbn : %s\n",id,titre,auteur,isbn) ;
    }
    public Livre( String titre, String auteur, String isbn) {

        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
    }
    public Livre(int id, String titre, String auteur, String isbn) {
        this(  titre,  auteur,  isbn);
        this.id = id;

    }

    public Livre() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


}
