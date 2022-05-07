package servlet;

import database.tables.EditRandevouzTable;
import mainClasses.Randevouz;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetDoctorRandevouz", value = "/GetDoctorRandevouz")
public class GetDoctorRandevouz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String docId = request.getParameter("doctor_id");
        PrintWriter out = response.getWriter();
        EditRandevouzTable ert = new EditRandevouzTable();
        ArrayList<Randevouz> randevouzs;
        ArrayList<Randevouz> booked;
        ArrayList<Randevouz> free;
        ArrayList<String> JSONRandevouz = new ArrayList<>();
        try{
            randevouzs = ert.getRandevouzByDocId(Integer.parseInt(docId));
        }catch (SQLException e){
            System.out.println("get doc randev " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("get doc randev " + e);
            response.setStatus(500);
            return;
        }

        /*keep the free randevouz*/
        free = ert.getRandevouzByStatus(randevouzs, "free");

        /*keep the selected randevouz*/
        booked = ert.getRandevouzByStatus(randevouzs, "selected");

        if(free==null && booked==null){
            response.setStatus(411);
            return;
        }
        /*merge them*/
        try {
            for (int i = 0; i < free.size(); i++)
                JSONRandevouz.add(ert.randevouzToJSON(free.get(i)));

        }catch (NullPointerException e){
            System.out.println("free array is null");
        }

        try {
            for (int i = 0; i < booked.size(); i++)
                JSONRandevouz.add(ert.randevouzToJSON(booked.get(i)));
        }catch (NullPointerException e){
            System.out.println("booked array is null");
        }

        out.println(JSONRandevouz);
        response.setStatus(200);
    }

}
