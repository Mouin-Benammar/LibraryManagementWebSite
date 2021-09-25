package com.ensta.librarymanager.model.test;

import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;

import java.time.LocalDate;

public class ModelTest {
    public static  void main(String[] args) {
        Membre M1=new Membre("mouin","ben ammar","boulevard des marechaud ","Mouin.ben-ammar@ensta-paris.fr","075225633");
        Livre l1=new Livre("50 shades of grey","E. L. James","dzfzfze");
        Membre M2=new Membre("Mourad","ben ammar","boulevard des marechaud ","Mouin.ben-ammar@ensta-paris.fr","075225633");
        Livre l2=new Livre("Tower of god","unknown","dzfzfze");
        System.out.println(M1);
        System.out.println(l1);
        System.out.println(M2);
        System.out.println(l2);
        LocalDate d1= LocalDate.of(1998,11,10);
        LocalDate d2= LocalDate.of(2000,11,10);
        Emprunt E1=new Emprunt(M1,l1,d1,d2);
        System.out.println(E1);
    }
}
