package servlet;

import database.tables.EditBloodTestTable;
import mainClasses.BloodTest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * returns all the bloodtests for a specific amka
 */
@WebServlet(name = "GetBloodtests", value = "/GetBloodtests")
public class GetBloodtests extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String amka = request.getParameter("amka");
        ArrayList<BloodTest> btests ;
        ArrayList<String> JSONbtests = new ArrayList<>();
        EditBloodTestTable ebt = new EditBloodTestTable();
        try{
            btests = ebt.getAllTestsByAmka(amka);
            //btests objcs to json
            for(int i=0; i<btests.size(); i++)
                JSONbtests.add(ebt.bloodTestToJSON(btests.get(i)));

            out.print(JSONbtests);     //send them back to the javaScript
            response.setStatus(200);
        }catch (SQLException e){
            System.out.println("in get all bloodtests " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("in get all bloodtests " + e);
            response.setStatus(500);
        }
    }
}
