package com.ensta.librarymanager.service;

import com.ensta.librarymanager.DAO.MembreImplDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Membre;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MembreImplService implements MembreService{
    private static MembreImplService instance;
    private MembreImplService() { }
    public static MembreImplService getInstance() {
        if(instance == null) {
            instance = new MembreImplService();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws ServiceException {
        MembreImplDao membreImplDao = MembreImplDao.getInstance();
        List<Membre> membres = new ArrayList<>();
        try {
            membres = membreImplDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membres;
    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        MembreImplDao membreImplDao = MembreImplDao.getInstance();
        List<Membre> membres = new ArrayList<>();
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();

        try {
            membres = membreImplDao.getList();
            for(Membre m : membres){
                if(!empruntImplService.isEmpruntPossible(m)) membres.remove(m);
            }
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membres;
    }

    @Override
    public Membre getById(int id) throws ServiceException {
        MembreImplDao membreImplDao = MembreImplDao.getInstance();
        Membre membre = new Membre();
        try {
            membre = membreImplDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membre;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException {
        if(nom==null||prenom==null){
            throw new ServiceException();
        }
            Membre membre = new Membre(nom, prenom,adresse,  email,  telephone);
            MembreImplDao membreImplDao = MembreImplDao.getInstance();

            int i = -1;
            try {
                i = membreImplDao.create(membre.getNom(),membre.getPrenom().toUpperCase(Locale.ROOT), membre.getAdresse(), membre.getEmail(), membre.getTelephone());
            }  catch (DaoException e1) {
                System.out.println(e1.getMessage());
            }
            return i;
    }

    @Override
    public void update(Membre membre) throws ServiceException {
        if(membre.getNom()==null||membre.getPrenom()==null){
            throw new ServiceException();
        }
        MembreImplDao membreImplDao = MembreImplDao.getInstance();


        membre.setPrenom(membre.getPrenom().toUpperCase(Locale.ROOT));
        try {
            membreImplDao.update(membre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Error while parsing: id=" + membre.getId(), e2);
        }

    }

    @Override
    public void delete(int id) throws ServiceException {
        MembreImplDao membreImplDao = MembreImplDao.getInstance();

        try {
            membreImplDao.delete(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Error while parsing: id="  + id, e2);
        }

    }

    @Override
    public int count() throws ServiceException {

        MembreImplDao membreImplDao = MembreImplDao.getInstance();

        int i = -1;
        try {
            i = membreImplDao.count();
        }  catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return i;
    }
}















