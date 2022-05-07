package servlet;

import database.tables.EditDoctorTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CertifyDoc", value = "/CertifyDoc")
public class CertifyDoc extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String uname = request.getParameter("username");
        EditDoctorTable edt = new EditDoctorTable();
        try{
            edt.updateDoctor(uname, "certified", "1");
            response.setStatus(200);
        }catch (SQLException e){
            response.setStatus(500);
            System.out.println("in certDoc Servlet " + e);
        }catch (ClassNotFoundException e){
            response.setStatus(500);
            System.out.println("in certDoc Servlet " + e);

        }
        return;
    }

}
