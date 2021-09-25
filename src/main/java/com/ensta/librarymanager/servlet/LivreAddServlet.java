package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.LivreImplService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreImplService livreImplService = LivreImplService.getInstance();
        List<Livre> livres = new ArrayList<>();


        try {
            livres = livreImplService.getListDispo();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("Livres_dispo", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");




        dispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String isbn = request.getParameter("isbn");
        LivreImplService livreImplService = LivreImplService.getInstance();


        try {
          int id=  livreImplService.create(titre, auteur, isbn);
           request.setAttribute("id",id);
            response.sendRedirect("/TP3Ensta/livre_details?id="+id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }



}}
