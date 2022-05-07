package servlet;

import database.tables.EditBloodTestTable;
import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import database.tables.EditTreatmentTable;
import mainClasses.BloodTest;
import mainClasses.Doctor;
import mainClasses.SimpleUser;
import mainClasses.Treatment;

import javax.print.Doc;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddTreatment", value = "/AddTreatment")
public class AddTreatment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDate = request.getParameter("start-date");
        String endDate = request.getParameter("end-date");
        String text = request.getParameter("text");
        String bloodtest_id = request.getParameter("bloodtest_id");

        System.out.println(startDate + " " + endDate + " " + text + " " + bloodtest_id + " ");

        EditDoctorTable edt = new EditDoctorTable();
        Doctor doc;
        try{
            doc = edt.databaseToDoctor((String)request.getSession().getAttribute("username"));
        }catch (SQLException e){
            System.out.println("Add Treatment get doc " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("Add Treatment get doc " + e);
            response.setStatus(500);
            return;
        }

        EditBloodTestTable ebt = new EditBloodTestTable();
        BloodTest bt;
        try{
            bt = ebt.databaseToBloodTest(Integer.parseInt(bloodtest_id));
        }catch (SQLException e){
            System.out.println("Add Treatment " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("Add Treatment " + e);
            response.setStatus(500);
            return;
        }


        //check if treatment exists. if so, delete it

        try{
            EditTreatmentTable ett = new EditTreatmentTable();
            Treatment trt = ett.databaseToTreatment(Integer.parseInt(bloodtest_id));
            if(trt!=null)
                ett.deleteTreatment(Integer.parseInt(bloodtest_id));
        }catch (SQLException e){
            System.out.println("adding treatment deleting " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("adding treatment deleting " + e);
            response.setStatus(500);
            return;
        }


        EditSimpleUserTable eut = new EditSimpleUserTable();
        int user_id;
        try{
            user_id = eut.getUserIdByAmka((bt.getAmka()));
        }catch (SQLException e){
            System.out.println("Add Treatment " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("Add Treatment " + e);
            response.setStatus(500);
            return;
        }

        Treatment trt = new Treatment();
        trt.setTreatment_text(text);
        trt.setBloodtest_id(Integer.parseInt(bloodtest_id));
        trt.setDoctor_id(doc.getDoctor_id());
        trt.setUser_id(user_id);
        trt.setStart_date(startDate);
        trt.setEnd_date(endDate);

        try{
            new EditTreatmentTable().createNewTreatment(trt);
            response.setStatus(200);
        }catch (SQLException e){
            System.out.println("add treatment adding " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("add treatment adding " + e);
            response.setStatus(500);
        }
    }

}
