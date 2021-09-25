package com.ensta.librarymanager.DAO;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreImplDao implements MembreDao{



    private static MembreImplDao instance;
    private MembreImplDao() { }
    public static MembreImplDao getInstance() {
        if(instance == null) {
            instance = new MembreImplDao();
        }
        return instance;
    }




    private static final String CREATE_QUERY = "INSERT INTO membre (nom, prenom,adresse,email,telephone,abonnement) VALUES (?,?,?,?,?,?);";
    private static final String SELECT_ONE_QUERY = "SELECT * FROM membre WHERE id=?;";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM membre ORDER BY nom, prenom;";
    private static final String UPDATE_QUERY = "UPDATE membre SET nom=?, prenom=?,adresse=?,email=?,telephone=?,abonnement=? WHERE id=?;";
    private static final String DELETE_QUERY = "DELETE FROM membre WHERE id=?;";
    private static final String COUNT_QUERY ="SELECT COUNT(id) AS count FROM membre";
    @Override
    public List<Membre> getList() throws DaoException {
        List<Membre> membre = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while(res.next()) {
                Membre m = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"),res.getString("adresse"),res.getString("email"),res.getString("telephone"),res.getString("abonnement"));
                membre.add(m);
            }
            System.out.println("GET: " + membre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des livres", e);
        }
        return membre;
    }

    @Override
    public Membre getById(int id) throws DaoException {
        Membre membre = new Membre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                membre.setId(res.getInt("id"));
                membre.setNom(res.getString("nom"));
                membre.setPrenom(res.getString("prenom"));
                membre.setAdresse(res.getString("adresse"));
                membre.setEmail(res.getString("email"));
                membre.setTelephone(res.getString("telephone"));
                membre.setAbonnement(res.getString("abonnement"));
            }

            System.out.println("GET: " + membre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du Membre: id=" + id, e);
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
        return membre;
    }

    @Override
    public  int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Membre membre = new Membre(nom,prenom,adresse,email,telephone);
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            System.out.println("azer");
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            System.out.println("789");
            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4, membre.getEmail());
            preparedStatement.setString(5, membre.getTelephone());
            preparedStatement.setString(6, membre.getAbonnement().toString());
            System.out.println("123");
            preparedStatement.executeUpdate();

            System.out.println("here");
            res = preparedStatement.getGeneratedKeys();
            if(res.next()){
                id = res.getInt(1);
            }

            System.out.println("CREATE: " + membre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du membre: " + membre, e);
        } finally {

            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public void update(Membre membre) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4, membre.getEmail());
            preparedStatement.setString(5, membre.getTelephone());
            preparedStatement.setString(6, membre.getAbonnement().toString());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE: " + membre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du membre: " + membre, e);
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
    public void delete(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("DELETE: membre" + id);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la suppression du membre: " + id, e);
        }  finally {
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
    public  int count() throws DaoException {
        int total=0;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){if(res.next()){
            total = res.getInt(1);
        }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de dénombrement de la liste des Membre", e);
        }
        return total;
    }
}
