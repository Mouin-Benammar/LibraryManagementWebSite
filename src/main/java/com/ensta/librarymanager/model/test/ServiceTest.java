package com.ensta.librarymanager.model.test;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntImplService;
import com.ensta.librarymanager.service.LivreImplService;
import com.ensta.librarymanager.service.MembreImplService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceTest {
    public static void main(String args[]){
        EmpruntImplService emp = EmpruntImplService.getInstance();
        LivreImplService liv = LivreImplService.getInstance();
        MembreImplService mem = MembreImplService.getInstance();
        List<Livre> livres=new ArrayList<>();

        Membre M1=new Membre("mouin","ben ammar","boulevard des marechaud ","Mouin.ben-ammar@ensta-paris.fr","075225633");
        Livre l1=new Livre("50 shades of grey","E. L. James","0385363125");
        LocalDate d1= LocalDate.of(1998,11,10);
        LocalDate d2= LocalDate.of(2000,11,10);
        Emprunt E1=new Emprunt(M1,l1,d1,d2);
        int i=0;
        try {
            Livre livre=liv.getById(3);

        } catch ( ServiceException e) {
            e.printStackTrace();
        }




        try {
            livres = liv.getList();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            i=liv.count();
            System.out.println(i);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            i=liv.count();
            System.out.println(i);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            i= liv.create(l1.getTitre(),l1.getAuteur(),l1.getIsbn());
            System.out.println(liv.getById(i));
        } catch (ServiceException e) {
            e.printStackTrace();
        }


        try {
            liv.delete(i);
            liv.delete(i-1);
            liv.delete(i-2);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            i=liv.count();
            System.out.println(i);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            i=emp.count();
            System.out.println(i);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            List<Emprunt> emprunts =emp.getListCurrent();

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            List<Emprunt> emprunts =emp.getListCurrentByLivre(2);

        } catch (ServiceException e) {
            e.printStackTrace();
        }



    }
    }

