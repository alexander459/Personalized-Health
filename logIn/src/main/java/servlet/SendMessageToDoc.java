package servlet;

import database.tables.EditMessageTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Message;
import mainClasses.SimpleUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "SendMessageToDoc", value = "/SendMessageToDoc")
public class SendMessageToDoc extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String docId = request.getParameter("doctor_id");
        String text = request.getParameter("message");
        String username = (String)request.getSession().getAttribute("username");
        SimpleUser su;
        try {
            su = new EditSimpleUserTable().databaseToSimpleUser(username);
        }catch (SQLException e){
            System.out.println("send msg getting user " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("send msg getting user " + e);
            response.setStatus(500);
            return;
        }

        System.out.println(su);
        Message msg = new Message();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        msg.setDoctor_id(Integer.parseInt(docId));
        msg.setUser_id(Integer.parseInt(userId));
        msg.setBloodtype(su.getBloodtype());
        msg.setDate_time(dtf.format(now));
        msg.setMessage(text);
        msg.setSender(username);
        msg.setBlood_donation(su.isBloodDonor());

        try{
            EditMessageTable emt = new EditMessageTable();
            emt.createNewMessage(msg);
            response.setStatus(200);
        }catch (ClassNotFoundException e){
            System.out.println("send msg sending " + e);
            response.setStatus(500);
        }
    }


}
