package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntImplService;
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
@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        showinfo(request, response);
    }

    private void showinfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MembreImplService membreImplService = MembreImplService.getInstance();
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();

        List<Emprunt> emprunt_list = new ArrayList<>();
        Membre m=new Membre();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
        int id=Integer.parseInt(request.getParameter("id"));
        try {
            m = membreImplService.getById(id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("membre", m);


        try {
            emprunt_list = empruntImplService.getListCurrentByMembre(id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("emprunt_list", emprunt_list);

        dispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String abonnement = request.getParameter("abonnement");
        MembreImplService membreImplService = MembreImplService.getInstance();
        Membre membre=new Membre(id,nom,prenom,adresse,email  , telephone ,abonnement);

        try {
            membreImplService.update(membre);
            response.sendRedirect("/TP3Ensta/membre_details?id="+id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }


    }
}
