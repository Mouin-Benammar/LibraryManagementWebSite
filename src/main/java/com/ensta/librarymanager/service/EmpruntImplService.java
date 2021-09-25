package com.ensta.librarymanager.service;

import com.ensta.librarymanager.DAO.EmpruntImplDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntImplService implements EmpruntService{
    private static EmpruntImplService instance;
    private EmpruntImplService() { }
    public static EmpruntImplService getInstance() {
        if(instance == null) {
            instance = new EmpruntImplService();
        }
        return instance;
    }
    @Override
    public List<Emprunt> getList() throws ServiceException {
        EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntImplDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntImplDao.getListCurrent();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntImplDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntImplDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public Emprunt getById(int id) throws ServiceException {
        EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            emprunt = empruntImplDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunt;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {


            EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();


            try {
               empruntImplDao.create(idMembre,idLivre,  dateEmprunt,LocalDate.now());
            }  catch (DaoException e1) {
                System.out.println(e1.getMessage());
            }


    }

    @Override
    public void returnBook(int id) throws ServiceException {


        EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();
        Emprunt emprunt=new Emprunt();
        try {
            emprunt= empruntImplDao.getById(id);
            emprunt.setDateRetour(LocalDate.now());
            empruntImplDao.update(emprunt);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Error while parsing: id=" + emprunt.getId(), e2);
        }
    }

    @Override
    public int count() throws ServiceException {


        EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();

            int i = -1;
            try {
                i = empruntImplDao.count();
            }  catch (DaoException e1) {
                System.out.println(e1.getMessage());
            }
            return i;
        }


    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        boolean result=true;
        try {
            EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();
            List<Emprunt> emprunts = empruntImplDao.getListCurrentByLivre(idLivre);
            if (emprunts.size() == 0) return false;
            else return result;
        }catch (DaoException e){
            throw new ServiceException();
        }

    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        int quota=membre.getAbonnement().getQuota();
        EmpruntImplDao empruntImplDao = EmpruntImplDao.getInstance();
        List<Emprunt> emprunts=getListCurrentByMembre(membre.getId());
        int cardinal=emprunts.size();
        if(cardinal<quota) return true;
        return false;

    }
}












