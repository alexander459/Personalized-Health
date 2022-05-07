package servlet;

import database.tables.EditRandevouzTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BookRandevou", value = "/BookRandevou")
public class BookRandevou extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rndvId = request.getParameter("randevouz_id");
        String userId = request.getParameter("user_id");
        String userInfo = "Tel: " + request.getParameter("user_tel") + ", Email: " + request.getParameter("user_email") + ", AMKA: " + request.getParameter("user_amka");
        EditRandevouzTable ert = new EditRandevouzTable();
        try{
            ert.updateRandevouz(Integer.parseInt(rndvId), Integer.parseInt(userId), userInfo, "selected");
            response.setStatus(200);
        }catch (SQLException e){
            System.out.println("in book rndvz " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("in book rndvz " + e);
            response.setStatus(500);
        }
    }

}
