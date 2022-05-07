package servlet;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import mainClasses.MapConverter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * updates a record status 200 on success, 500 on exception
 */
@WebServlet(name = "UpdateData", value = "/UpdateData")
public class UpdateData extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryFormatParameters = request.getReader().lines().reduce("", (accumulator, actual)
                -> accumulator + actual);
        System.out.println("in update servlet " + queryFormatParameters);
        Map<String, String> params = new HashMap<>();
        params = new MapConverter().queryToMap(queryFormatParameters);
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        String type = (String)session.getAttribute("type");

        if(type.equals("su")){  //its a simple user
            System.out.println("simple");
            EditSimpleUserTable eut = new EditSimpleUserTable();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    eut.updateSimpleUser(username, entry.getKey(), entry.getValue());
                    response.setStatus(200);
                }catch (SQLException e){
                    System.out.println("in update user servlet " + e);
                    response.setStatus(500);
                }catch (ClassNotFoundException e){
                    System.out.println("in update servlet " + e);
                    response.setStatus(500);
                }
            }
        }else{
            System.out.println("doc");
            EditDoctorTable edt = new EditDoctorTable();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    edt.updateDoctor(username, entry.getKey(), entry.getValue());
                    response.setStatus(200);
                }catch (SQLException e){
                    System.out.println("in update user servlet " + e);
                    response.setStatus(500);
                }catch (ClassNotFoundException e){
                    System.out.println("in update servlet " + e);
                    response.setStatus(500);
                }
            }
        }
    }
}
