package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.EmpruntImplService;
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
@WebServlet("/livre_details")
public class LivreDetailsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        showinfo(request, response);
    }

    private void showinfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LivreImplService livreImplService = LivreImplService.getInstance();
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();

        List<Emprunt> emprunt_list = new ArrayList<>();
        Livre l=new Livre();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
        int id=Integer.parseInt(request.getParameter("id"));
        try {
            l = livreImplService.getById(id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("livre", l);

        try {
            emprunt_list = empruntImplService.getListCurrentByLivre(id);
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
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String isbn = request.getParameter("isbn");
        LivreImplService livreImplService = LivreImplService.getInstance();
Livre livre=new Livre(id,titre,auteur,isbn);

        try {
            livreImplService.update(livre);
            response.sendRedirect("/TP3Ensta/livre_details?id="+id);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }


    }
}
