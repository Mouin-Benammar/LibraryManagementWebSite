package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.MembreImplService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/membre_add")
public class MembreAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        showAllPossible(request, response);
    }

    private void showAllPossible(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MembreImplService membreImplService = MembreImplService.getInstance();
        List<Membre> membres = new ArrayList<>();

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
        try {
            membres = membreImplService.getListMembreEmpruntPossible();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("Membres_dispo", membres);





        dispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        MembreImplService membreImplService = MembreImplService.getInstance();


        try {
            int id=  membreImplService.create(nom, prenom, adresse,email,telephone);
            response.sendRedirect("/TP3Ensta/membre_details?id="+id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }




    }
}
