package servlet;

import database.tables.EditTreatmentTable;
import mainClasses.Treatment;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * returns all the treatments for a user
 */
@WebServlet(name = "GetUserTreatment", value = "/GetUserTreatment")
public class GetUserTreatment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String userId = request.getParameter("user_id");
        response.setContentType("text/html");
        EditTreatmentTable ett = new EditTreatmentTable();
        ArrayList<Treatment> array;
        try{
            array = ett.getUserTreatment(Integer.parseInt(userId));
            response.setStatus(200);
        }catch (SQLException e){
            System.out.println("get user trt " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("get user trt " + e);
            response.setStatus(500);
            return;
        }

        if(array==null){
            response.setStatus(412);
            return;
        }
        ArrayList<String> JSONTrt = new ArrayList<>();
        for(int i=0; i<array.size(); i++)
            JSONTrt.add(ett.treatmentToJSON(array.get(i)));

        out.println(JSONTrt);
    }

}
