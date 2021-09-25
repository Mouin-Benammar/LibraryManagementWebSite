package com.ensta.librarymanager.DAO;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LivreImplDao implements LivreDao{



    private static LivreImplDao instance=null;
    private LivreImplDao() { }
    public static LivreImplDao getInstance() {
        if(instance == null) {
            instance = new LivreImplDao();
        }
        return instance;
    }










    @Override
    public List<Livre> getList() throws DaoException {
        List<Livre> livres = new ArrayList<>();
        String SELECT_ALL_QUERY = "SELECT * FROM Livre;";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while(res.next()) {
                Livre l = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"),res.getString("isbn"));
                livres.add(l);
            }System.out.println("GET: " + livres);

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des livres", e);
        }
        return livres;
    }

    @Override
    public Livre getById(int id) throws DaoException {
        String SELECT_ONE_QUERY = "SELECT * FROM Livre WHERE id=?;";
        Livre livre = new Livre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                livre.setId(res.getInt("id"));
                livre.setTitre(res.getString("titre"));
                livre.setAuteur(res.getString("auteur"));
                livre.setIsbn(res.getString("isbn"));
            }

            System.out.println("GET: " + livre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du livre: id=" + id, e);
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
        return livre;
    }

    @Override
    public  int create(String titre, String auteur, String isbn) throws DaoException {
        String CREATE_QUERY = "INSERT INTO Livre (titre, auteur,isbn) VALUES (?,?,?);";
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Livre livre=new Livre(titre,auteur,isbn);
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if(res.next()){
                id = res.getInt(1);
            }

            System.out.println("CREATE: " + livre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du livre: " + livre, e);
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
        return id;
    }

    @Override
    public void update(Livre livre) throws DaoException {
        String UPDATE_QUERY = "UPDATE Livre SET titre=?, auteur=?,isbn=? WHERE id=?;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4, livre.getId());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE: " + livre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du livre: " + livre, e);
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
        String DELETE_QUERY = "DELETE FROM Livre WHERE id=?;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("DELETE: Livre" + id);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la suppression du livre: " + id, e);
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
        String COUNT_QUERY ="SELECT COUNT(*) AS count FROM livre";
        int total=0;

        try {Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_QUERY);
             ResultSet res = preparedStatement.executeQuery();

                if (res.next()) {
                    total = res.getInt(1);
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
         catch (SQLException e) {
            throw new DaoException("Problème lors de dénombrement de la liste des Livre", e);
        }

        return total;
    }
}
