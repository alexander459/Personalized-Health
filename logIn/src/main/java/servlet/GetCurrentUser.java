package servlet;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.SimpleUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * returns the logged in user's username
 */
@WebServlet(name = "GetCurrentUser", value = "/GetCurrentUser")
public class GetCurrentUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(false);
        session.setMaxInactiveInterval(60*60);
        if(request.getRequestedSessionId()!=null && !request.isRequestedSessionIdValid()){
            System.out.println("expired");
            response.setStatus(408);        //session has been expired
        }else{
            System.out.println("not expired");
            String uname = (String)session.getAttribute("username");
            String type = (String)session.getAttribute("type");
            if(type.equals("su")){
                EditSimpleUserTable eut = new EditSimpleUserTable();
                try {
                    SimpleUser su = eut.databaseToSimpleUser(uname);
                    out.println(eut.simpleUserToJSON(su));
                    response.setStatus(200);
                }catch (SQLException e){
                    System.out.println("exception get current user " + e);
                    response.setStatus(500);
                }catch (ClassNotFoundException e){
                    System.out.println("exception get current user " + e);
                    response.setStatus(500);
                }
            }else{          //its a doctor
                EditDoctorTable edt = new EditDoctorTable();
                try {
                    Doctor doc = edt.databaseToDoctor(uname);
                    out.println(edt.doctorToJSON(doc));
                    response.setStatus(200);
                }catch (SQLException e){
                    System.out.println("exception get current user " + e);
                    response.setStatus(500);
                }catch (ClassNotFoundException e){
                    System.out.println("exception get current user " + e);
                    response.setStatus(500);
                }
            }
        }
    }

}
