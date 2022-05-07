package servlet;

import database.tables.EditBloodTestTable;
import database.tables.EditSimpleUserTable;
import mainClasses.BloodTest;
import mainClasses.SimpleUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetClientBloodtests", value = "/GetClientBloodtests")
public class GetClientBloodtests extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String client = request.getParameter("client");
        SimpleUser su;
        try {
            su = new EditSimpleUserTable().databaseToSimpleUser(client);
        } catch (SQLException e) {
            System.out.println("get client bloodtest getting user " + e);
            response.setStatus(500);
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("get client bloodtest getting user " + e);
            response.setStatus(500);
            return;
        }

        if(su == null)
            return;
        String amka = su.getAmka();
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
            System.out.println("in get clients bloodtests " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("in get clients bloodtests " + e);
            response.setStatus(500);
        }
    }


}
