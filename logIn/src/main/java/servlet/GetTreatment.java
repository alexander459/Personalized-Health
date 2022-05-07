package servlet;

import database.tables.EditTreatmentTable;
import mainClasses.Treatment;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * returns a treatment with id <id>
 */
@WebServlet(name = "GetTreatment", value = "/GetTreatment")
public class GetTreatment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        EditTreatmentTable ett = new EditTreatmentTable();
        try{
            Treatment trt = ett.databaseToTreatment(Integer.parseInt(id));
            String trtJSON = ett.treatmentToJSON(trt);
            System.out.println(trtJSON);
            out.println(trtJSON);
        }catch (SQLException e){
            System.out.println("Exception in get treatment " + e);
        }catch (ClassNotFoundException e){
            System.out.println("Exception in get treatment " + e);
        }
    }
}