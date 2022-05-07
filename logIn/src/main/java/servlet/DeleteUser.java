package servlet;

import database.tables.EditSimpleUserTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteUser", value = "/DeleteUser")
public class DeleteUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String target = request.getParameter("username");
        EditSimpleUserTable eut = new EditSimpleUserTable();
        try{
            response.setStatus(eut.deleteUser(target));
        }catch (SQLException e){
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            response.setStatus(500);
        }
        return;
    }
}
