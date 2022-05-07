package servlet;

import database.tables.EditRandevouzTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DoneRandevouz", value = "/DoneRandevouz")
public class DoneRandevouz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("randevouz_id");
        try{
            new EditRandevouzTable().updateRandevouz(Integer.parseInt(id), "done");
            response.setStatus(200);
        }catch (SQLException e){
            System.out.println("done randevouz " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("done randevouz " + e);
            response.setStatus(500);
        }
    }


}
