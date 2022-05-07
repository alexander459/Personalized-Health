package servlet;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteDoc", value = "/DeleteDoc")
public class DeleteDoc extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String target = request.getParameter("username");
        EditDoctorTable edt = new EditDoctorTable();
        try{
            response.setStatus(edt.deleteDoc(target));
        }catch (SQLException e){
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            response.setStatus(500);
        }
        return;
    }

}
