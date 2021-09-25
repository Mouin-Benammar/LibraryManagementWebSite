package com.ensta.librarymanager.service;


import com.ensta.librarymanager.DAO.LivreImplDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;

import java.util.ArrayList;
import java.util.List;

public class LivreImplService implements LivreService{
    private static LivreImplService instance;
    private LivreImplService() { }
    public static LivreImplService getInstance() {
        if(instance == null) {
            instance = new LivreImplService();
        }
        return instance;
    }
    @Override
    public List<Livre> getList() throws ServiceException {

            LivreImplDao livreImplDao = LivreImplDao.getInstance();
            List<Livre> livres = new ArrayList<>();
            try {
                livres = livreImplDao.getList();
            } catch (DaoException e1) {
                System.out.println(e1.getMessage());
            }
            return livres;

    }

    @Override
    public List<Livre> getListDispo() throws ServiceException {
        LivreImplDao livreImplDao = LivreImplDao.getInstance();
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();
        List<Livre> livres = new ArrayList<>();
        List<Livre> dispo = new ArrayList<>();
        try {
            livres = livreImplDao.getList();
            for(Livre l : livres){
                if(!empruntImplService.isLivreDispo(l.getId())) dispo.add(l);
            }
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
return dispo;

    }

    @Override
    public Livre getById(int id) throws ServiceException {
        LivreImplDao livreImplDao = LivreImplDao.getInstance();
        Livre livre = new Livre();
        try {
            livre = livreImplDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return livre;
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws ServiceException {
        if(titre==null){
            throw new ServiceException();
        }

        LivreImplDao livreImplDao = LivreImplDao.getInstance();

        int i = -1;
        try {
            i = livreImplDao.create(titre,auteur ,isbn);
        }  catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return i;
    }

    @Override
    public void update(Livre livre) throws ServiceException {
       String titre=livre.getTitre();
        if(titre==null){
            throw new ServiceException();
        }

        LivreImplDao livreImplDao = LivreImplDao.getInstance();
        try {
            livreImplDao.update(livre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Error while parsing: id=" + livre.getId(), e2);
        }
    }

    @Override
    public void delete(int id) throws ServiceException {

        LivreImplDao livreImplDao = LivreImplDao.getInstance();

        try {
            livreImplDao.delete(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Error while parsing: id="  + id, e2);
        }
    }

    @Override
        public int count() throws ServiceException {

            LivreImplDao livreImplDao = LivreImplDao.getInstance();

            int i = -1;
            try {
                i = livreImplDao.count();
            }  catch (DaoException e1) {
                System.out.println(e1.getMessage());
            }
            return i;
        }

}







