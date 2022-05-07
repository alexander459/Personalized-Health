package servlet;

import database.tables.EditRandevouzTable;
import mainClasses.Randevouz;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * checks if the user can send message to the doctor. returns 200 if the user can or 413 if not
 */
@WebServlet(name = "CanSendMessage", value = "/CanSendMessage")
public class CanSendMessage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String docId = request.getParameter("doctor_id");
        ArrayList<Randevouz> randevouzs;
        EditRandevouzTable ert = new EditRandevouzTable();
        try{
            randevouzs = ert.getRandevouzByUserId(Integer.parseInt(userId));
        }catch (SQLException e){
            System.out.println("can send msg " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("can send msg " + e);
            response.setStatus(500);
            return;
        }
        randevouzs = ert.getRandevouzByStatus(randevouzs, "done");
        if(randevouzs==null){  //no done randevouz so can not send
            response.setStatus(413);
            return;
        }

        //check if in done randevouz exists the doc id
        for(int i=0; i<randevouzs.size(); i++){
            if(randevouzs.get(i).getDoctor_id()==Integer.parseInt(docId)){
                response.setStatus(200);
                return;
            }
        }

        response.setStatus(413);
    }

}
