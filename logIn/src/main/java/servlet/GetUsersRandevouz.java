package servlet;

import database.tables.EditRandevouzTable;
import mainClasses.Randevouz;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetUsersRandevouz", value = "/GetUsersRandevouz")
public class GetUsersRandevouz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String userId = request.getParameter("user_id");
        EditRandevouzTable ert = new EditRandevouzTable();
        ArrayList<Randevouz> randevouzs;
        ArrayList<Randevouz> usersRandevouzs;

        try{
            randevouzs = ert.getRandevouzByUserId(Integer.parseInt(userId));
            //keep only the selected
            usersRandevouzs = ert.getRandevouzByStatus(randevouzs, "selected");
            if(usersRandevouzs==null) {
                response.setStatus(411);
                return;
            }
        }catch (SQLException e) {
            System.out.println("get randvz " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("get randvz " + e);
            response.setStatus(500);
            return;
        }

        //to JSON
        ArrayList<String> JSONRandevouz = new ArrayList<>();
        for(int i=0; i<usersRandevouzs.size(); i++){
            JSONRandevouz.add(ert.randevouzToJSON(usersRandevouzs.get(i)));
        }
        out.print(JSONRandevouz);
        response.setStatus(200);
    }

}
