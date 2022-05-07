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

@WebServlet(name = "GetfreeRandevouz", value = "/GetfreeRandevouz")
public class GetfreeRandevouz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        EditRandevouzTable ert = new EditRandevouzTable();
        ArrayList<Randevouz> randevouzs;
        ArrayList<Randevouz> freeRandevouzs;

        try{
            randevouzs = ert.getRandevouzByDocId(Integer.parseInt(id));
            freeRandevouzs = ert.getRandevouzByStatus(randevouzs, "free");
            if(freeRandevouzs==null) {
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
        for(int i=0; i<freeRandevouzs.size(); i++){
            JSONRandevouz.add(ert.randevouzToJSON(freeRandevouzs.get(i)));
        }
        out.print(JSONRandevouz);
        response.setStatus(200);
    }


}
