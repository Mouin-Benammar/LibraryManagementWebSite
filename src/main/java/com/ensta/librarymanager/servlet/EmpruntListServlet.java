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
import java.util.ArrayList;
import java.util.List;
@WebServlet("/emprunt_list")
public class EmpruntListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        showEmprunts(request, response);
    }

    private void showEmprunts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();
        List<Emprunt> emprunt_list = new ArrayList<>();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
        String test=request.getParameter("show");
        System.out.println("show = "+test);
        if(test!= null && test.equals("all")){
        try {
            emprunt_list = empruntImplService.getList();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

else{
        try {
            emprunt_list = empruntImplService.getListCurrent();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        }


        request.setAttribute("emprunt_list", emprunt_list);

        dispatcher.forward(request, response);
    }

}
