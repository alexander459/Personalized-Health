package servlet;

import database.tables.EditRandevouzTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "CancelRandevouz", value = "/CancelRandevouz")
public class CancelRandevouz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String randvzId = request.getParameter("randevouz_id");
        EditRandevouzTable ert = new EditRandevouzTable();

        try{
            ert.updateRandevouz(Integer.parseInt(randvzId), "canceled");
            response.setStatus(200);
        }catch (SQLException e) {
            System.out.println("get randvz " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("get randvz " + e);
            response.setStatus(500);
        }
    }

}
