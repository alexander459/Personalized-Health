package servlet;

import database.tables.EditMessageTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Message;
import mainClasses.SimpleUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetMessages", value = "/GetMessages")
public class GetMessages extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String client = request.getParameter("client");
        SimpleUser su;
        ArrayList<Message> messages;
        PrintWriter out = response.getWriter();
        try {
            su = new EditSimpleUserTable().databaseToSimpleUser(client);
            messages = new EditMessageTable().getClientsMessages(su.getUser_id());
            if(messages == null){
                response.setStatus(415);
                return;
            }
            ArrayList<String> JSONMessages = new ArrayList<>();
            for(int i=0; i<messages.size(); i++){
                JSONMessages.add(new EditMessageTable().messageToJSON(messages.get(i)));
            }
            response.setStatus(200);
            out.println(JSONMessages);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(500);
            return;
        }


    }


}
