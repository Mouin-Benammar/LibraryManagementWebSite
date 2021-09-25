package com.ensta.librarymanager.model;

import java.time.LocalDate;
public class Emprunt   {
    private int id;
    private Membre membre;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    @Override
    public String toString() {
        return String.format("Emprunt{%d} \n     membre :%s \n     livre :%s \n  dateEmprunt : %s \n  dateRetour : %s\n",id,membre,livre,dateEmprunt,dateRetour) ;
    }
    public Emprunt() {
        super();
    }

    public Emprunt(Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.membre = membre;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this(membre,livre,dateEmprunt,dateRetour);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

}
