package com.ensta.librarymanager.DAO;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.time.LocalDate;

public class EmpruntImplDao implements EmpruntDao{


    private static EmpruntImplDao instance;
    private EmpruntImplDao() { }
    public static EmpruntImplDao getInstance() {
        if(instance == null) {
            instance = new EmpruntImplDao();
        }
        return instance;
    }




    private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
    private static final String SELECT_ALL_CURRENT_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
    private static final String SELECT_CURRENT_MEMBER_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
    private static final String SELECT_CURRENT_LIVRE_QUERY ="SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
    private static final String SELECT_ONE_QUERY="SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";

    private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
    private static final String UPDATE_QUERY = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?;";
    private static final String COUNT_QUERY ="SELECT COUNT(id) AS count FROM Emprunt;";
    @Override
    public List<Emprunt> getList() throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();

        try {Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet res = preparedStatement.executeQuery();

            while(res.next()) {
                int id=res.getInt("id")   ;
                Membre m= new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"),res.getString("adresse"),res.getString("email"),res.getString("telephone"),res.getString("abonnement"));
                Livre l=new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),res.getString("isbn"));
                LocalDate DateE=res.getDate("dateEmprunt").toLocalDate();
                Date d=res.getDate("dateRetour");
                LocalDate DateR=null;
                if(d!=null) DateR= ((java.sql.Date) d).toLocalDate();
                Emprunt emprunt = new Emprunt(id,m,l, DateE,DateR);
                emprunts.add(emprunt);
            }
            System.out.println("GET: " + emprunts);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CURRENT_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while(res.next()) {
                int id=res.getInt("id")   ;
                Membre m= new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"),res.getString("adresse"),res.getString("email"),res.getString("telephone"),res.getString("abonnement"));
                Livre l=new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),res.getString("isbn"));

                LocalDate DateE=res.getDate("dateEmprunt").toLocalDate();
                Date d=res.getDate("dateRetour");
                LocalDate DateR=null;
                if(d!=null){
                   DateR= ((java.sql.Date) d).toLocalDate();
                }

                Emprunt emprunt = new Emprunt(id,m,l, DateE,DateR);
                emprunts.add(emprunt);




            }
            System.out.println("GET: " + emprunts);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts  encore pas rendus", e);
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();

        try {Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT_MEMBER_QUERY);
             preparedStatement.setInt(1, idMembre);
             ResultSet res = preparedStatement.executeQuery();

            while(res.next()) {
                int id=res.getInt("id")   ;
                Membre m= new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"),res.getString("adresse"),res.getString("email"),res.getString("telephone"),res.getString("abonnement"));
                Livre l=new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),res.getString("isbn"));
                LocalDate DateE=res.getDate("dateEmprunt").toLocalDate();
                Date d=res.getDate("dateRetour");
                LocalDate DateR=null;
                if(d!=null){
                    DateR= ((java.sql.Date) d).toLocalDate();


                Emprunt emprunt = new Emprunt(id,m,l, DateE,DateR);
                emprunts.add(emprunt);
                }
            }
            System.out.println("GET: " + emprunts);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts  encore pas rendus per le membre "+idMembre, e);
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();

        try {Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT_LIVRE_QUERY);
             preparedStatement.setInt(1, idLivre);
             ResultSet res = preparedStatement.executeQuery();

            while(res.next()) {
                int id=res.getInt("id")   ;
                Membre m= new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"),res.getString("adresse"),res.getString("email"),res.getString("telephone"),res.getString("abonnement"));
                Livre l=new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),res.getString("isbn"));
                LocalDate DateE=res.getDate("dateEmprunt").toLocalDate();
                Date d=res.getDate("dateRetour");
                LocalDate DateR=null;
                if(d!=null){
                    DateR= ((java.sql.Date) d).toLocalDate();
                }

                Emprunt emprunt = new Emprunt(id,m,l, DateE,DateR);
                emprunts.add(emprunt);
            }
            System.out.println("GET: " + emprunts);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts  encore pas rendus le livre "+idLivre, e);
        }
        return emprunts;
    }

    @Override
    public Emprunt getById(int id) throws DaoException {
        Emprunt emprunt = new Emprunt();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                int Id=res.getInt("id")   ;
                Membre m= new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"),res.getString("adresse"),res.getString("email"),res.getString("telephone"),res.getString("abonnement"));
                Livre l=new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"),res.getString("isbn"));
                //String d1[]= res.getString("dateEmprunt").split("-");
                //String d2[]=res.getString("dateRetour").split("-");
                //LocalDate DateE= LocalDate.of(Integer.parseInt(d1[0]),Integer.parseInt(d1[1]),Integer.parseInt(d1[2]));
                //LocalDate DateR= LocalDate.of(Integer.parseInt(d2[0]),Integer.parseInt(d2[1]),Integer.parseInt(d2[2]));
                LocalDate DateE=res.getDate("dateEmprunt").toLocalDate();

                Date retour=res.getDate("dateRetour");
                LocalDate DateR=null;
                if(retour!=null){
                    DateR = ((java.sql.Date) retour).toLocalDate();
                }

                emprunt = new Emprunt(id,m,l, DateE,DateR);
            }

            System.out.println("GET: " + emprunt);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du emprunt: id=" + id, e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunt;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt,LocalDate dateRetour) throws DaoException {
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1,String.valueOf(idMembre));
            preparedStatement.setString(2, String.valueOf(idLivre));
            preparedStatement.setDate(3, Date.valueOf( dateEmprunt));
            preparedStatement.setDate(4, Date.valueOf(dateRetour));

            preparedStatement.executeUpdate();



            System.out.println("CREATE: " );
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du emprunt: " , e);
        } finally {

            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void update(Emprunt emprunt) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1,String.valueOf(emprunt.getMembre().getId()) );
            preparedStatement.setString(2, String.valueOf(emprunt.getLivre().getId()));
            preparedStatement.setString(3, emprunt.getDateEmprunt().toString());
            preparedStatement.setString(4, emprunt.getDateRetour().toString());
            preparedStatement.setInt(5, emprunt.getId());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE: " + emprunt);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du livre: " + emprunt, e);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int count() throws DaoException {
int total=0;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){if(res.next()){
            total = res.getInt(1);
        }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de  dénombrement de la liste des emprunts", e);
        }
        return total;
    }
}
