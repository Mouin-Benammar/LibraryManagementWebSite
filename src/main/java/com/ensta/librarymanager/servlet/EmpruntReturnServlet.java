package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.service.EmpruntImplService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        showEmpruntsCurrent(request, response);
    }

    private void showEmpruntsCurrent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();
        List<Emprunt> emprunt_list = new ArrayList<>();

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
            try {
                emprunt_list = empruntImplService.getListCurrent();
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
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();


        try {
            empruntImplService.returnBook(id);
            response.sendRedirect("/TP3Ensta/emprunt_list");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }




    }

}
