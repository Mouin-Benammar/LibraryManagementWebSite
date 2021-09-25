package com.ensta.librarymanager.model;

public class Membre {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Abonnement abonnement;

    @Override
    public String toString() {
        if(abonnement!=null) return String.format("Membre{%d} \n   Nom :%s \n   Prénom :%s \n   Adresse :%s \n   Email :%s \n   Telephone :%s \n   Abonnement :%s\n ",id,nom,prenom,adresse,email,telephone,abonnement.toString());
    else return String.format("Membre{%d} \n   Nom :%s \n   Prénom :%s \n   Adresse :%s \n   Email :%s \n   Telephone :%s \n   Abonnement:BASIC \n ",id,nom,prenom,adresse,email,telephone);
    }
    public Membre( String nom, String prenom, String adresse, String email, String telephone, String abonnement) {

        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.abonnement = Abonnement.valueOf(abonnement);
    }
    public Membre(int id, String nom, String prenom, String adresse, String email, String telephone, String abonnement) {
        this(  nom,  prenom,  adresse,  email,  telephone,  abonnement);
        this.id = id;

    }
    public Membre( String nom, String prenom, String adresse, String email, String telephone) {

        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.abonnement = Abonnement.valueOf("BASIC");
    }

    public Membre() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(String abonnement) {
        this.abonnement = Abonnement.valueOf(abonnement);
    }
}
