package servlet;


import database.Check;
import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.MapConverter;
import mainClasses.SimpleUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LogIn", value = "/LogIn")
public class LogIn extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String queryFormatParameters = request.getReader().lines().reduce("", (accumulator, actual)
                -> accumulator + actual);
        //Store the parameters in a map
        Map<String, String> params;
        params = new MapConverter().queryToMap(queryFormatParameters);
        Check ck = new Check();
        try {
            if(!ck.userExists(params.get("username"))){
                response.setStatus(405);  //user does not exist
            }else if(!ck.isPasswordValid(params.get("username"), params.get("password"))){
                response.setStatus(406);  //invalid password
            }else{
                EditSimpleUserTable eut = new EditSimpleUserTable();
                EditDoctorTable edt = new EditDoctorTable();
                SimpleUser su = eut.databaseToSimpleUser(params.get("username"), params.get("password"));
                Doctor doc = edt.databaseToDoctor(params.get("username"), params.get("password"));

                HttpSession session=request.getSession();

                if(su!=null){   //user is simple user
                    session.setAttribute("username", su.getUsername());  //store the username
                    session.setAttribute("type", "su");     //its a simple user
                    if(su.getUsername().equals("admin"))
                        response.setStatus(203);  //code for admin
                    else
                        response.setStatus(201);  //code for user
                }else{          //user is doctor
                    if(doc.getCertified()==0) {      //doc is not certified so return code 407 (not certified -> dont login)
                        response.setStatus(409);
                    }else {
                        session.setAttribute("username", doc.getUsername());  //store the username
                        session.setAttribute("type", "doc");     //its a doctor
                        response.setStatus(202);  //code for doctor
                    }
                }
            }
        } catch (SQLException e) {
            response.setStatus(500);
            System.out.println("ex1" + e);
        }catch (ClassNotFoundException e){
            response.setStatus(500);
            System.out.println("ex2" + e);
        }
    }

}
