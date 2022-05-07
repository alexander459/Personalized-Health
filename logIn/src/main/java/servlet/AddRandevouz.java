package servlet;

import database.tables.EditDoctorTable;
import database.tables.EditRandevouzTable;
import mainClasses.Doctor;
import mainClasses.Randevouz;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "AddRandevouz", value = "/AddRandevouz")
public class AddRandevouz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("date");
        String price = request.getParameter("price");
        String docInfo;
        String username = (String)request.getSession().getAttribute("username");
        Randevouz rnd = new Randevouz();
        PrintWriter out = response.getWriter();

        rnd.setDate_time(date);
        rnd.setPrice(Integer.parseInt(price));
        rnd.setStatus("free");

        Doctor doc;
        EditDoctorTable edt = new EditDoctorTable();
        try{
            doc = edt.databaseToDoctor(username);
            System.out.println("doc is " + doc);
        }catch (SQLException e){
            System.out.println("exception in add randevouz " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("exception in add randevouz " + e);
            response.setStatus(500);
            return;
        }

        docInfo = "Tel: " + doc.getTelephone() + ", Email: " + doc.getEmail() + ", Address: " + doc.getAddress();

        rnd.setDoctor_info(docInfo);
        rnd.setDoctor_id(doc.getDoctor_id());
        rnd.setUser_info("");

        EditRandevouzTable ert = new EditRandevouzTable();
        try{
            ert.createNewRandevouz(rnd);
            response.setStatus(200);
        }catch (ClassNotFoundException e){
            System.out.println("exception in add randevouz " + e);
            response.setStatus(500);
        }
    }

}
