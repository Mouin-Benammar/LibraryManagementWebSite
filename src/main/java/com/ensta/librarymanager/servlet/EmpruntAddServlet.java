package com.ensta.librarymanager.servlet;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntImplService;
import com.ensta.librarymanager.service.LivreImplService;
import com.ensta.librarymanager.service.MembreImplService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        showAllPossible(request, response);
    }

    private void showAllPossible(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();

        MembreImplService membreImplService = MembreImplService.getInstance();
        List<Membre> membres = new ArrayList<>();

        LivreImplService livreImplService = LivreImplService.getInstance();
        List<Livre> livres = new ArrayList<>();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
        try {
            livres = livreImplService.getListDispo();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("Livre_dispo", livres);


        try {
            membres = membreImplService.getListMembreEmpruntPossible();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("Membre_dispo", membres);



        dispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLivre = Integer.parseInt(request.getParameter("idLivre"));
        int idMembre = Integer.parseInt(request.getParameter("idMembre"));
        LocalDate DateEmprunt=LocalDate.now() ;
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();


        try {
           empruntImplService.create(idLivre, idMembre, DateEmprunt);
            response.sendRedirect("/TP3Ensta/emprunt_list");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }




    }

}
