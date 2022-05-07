package servlet;

import database.tables.EditBloodTestTable;
import mainClasses.BloodTest;
import mainClasses.MapConverter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * adds a new blood test
 */
@WebServlet(name = "AddExams", value = "/AddExams")
public class AddExams extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String queryFormatParameters = request.getReader().lines().reduce("", (accumulator, actual)
                -> accumulator + actual);
        System.out.println(queryFormatParameters);
        Map<String, String> params = new MapConverter().queryToMap(queryFormatParameters);
        BloodTest bt = new BloodTest(params);
        System.out.println("bl t " + bt);

        EditBloodTestTable ebt = new EditBloodTestTable();
        try{
            ebt.createNewBloodTest(bt);
            response.setStatus(200);
        }catch(ClassNotFoundException e){
            System.out.println("in ad exams " + e);
            response.setStatus(500);
        }catch (SQLException e){
            System.out.print("in add exams " + e);
            response.setStatus(500);
        }
    }
}
