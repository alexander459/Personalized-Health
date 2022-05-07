package servlet;

import database.tables.EditBloodTestTable;
import database.tables.EditDoctorTable;
import database.tables.EditRandevouzTable;
import database.tables.EditSimpleUserTable;
import mainClasses.*;

import javax.print.Doc;
import javax.print.SimpleDoc;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetDoctorClients", value = "/GetDoctorClients")
public class GetDoctorClients extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String)request.getSession().getAttribute("username");
        PrintWriter out = response.getWriter();

        try{
            EditDoctorTable edt = new EditDoctorTable();
            Doctor doc = edt.databaseToDoctor(username);

            EditRandevouzTable ert = new EditRandevouzTable();
            ArrayList<Randevouz> randevouzs = ert.getRandevouzByDocId(doc.getDoctor_id());
            if(randevouzs==null || randevouzs.size()==0){
                response.setStatus(411);   //no randevouz so no clients
                return;
            }
            //get done randevouz
            randevouzs = ert.getRandevouzByStatus(randevouzs, "done");

            if(randevouzs==null || randevouzs.size()==0){
                response.setStatus(411);   //no randevouz so no clients
                return;
            }

            EditSimpleUserTable eut = new EditSimpleUserTable();
            ArrayList<SimpleUser> clients = new ArrayList<>();

            boolean found = false;
            //get all the clients
            for(int i=0; i<randevouzs.size(); i++){
                for(int j=0; j<clients.size(); j++){
                    if(randevouzs.get(i).getUser_id() == clients.get(j).getUser_id()) {
                        found = true;
                        break;
                    }
                }
                if(!found)
                    clients.add(eut.databaseToSimpleUser(randevouzs.get(i).getUser_id()));
                else
                    found = false;
            }

            //return the usernames
            StringBuffer JSONUsers = new StringBuffer();
            JSONUsers.append("[");
            for(int i=0; i<clients.size(); i++){
                JSONUsers.append("\"").append(clients.get(i).getUsername()).append("\"");
                if(i+1<clients.size())
                    JSONUsers.append(",");
            }
            JSONUsers.append("]");
            response.setStatus(200);
            out.println(JSONUsers);
            /*EditBloodTestTable ebt = new EditBloodTestTable();
            ArrayList<ArrayList<BloodTest>> bt = new ArrayList<>();

            if(randevouzs == null || randevouzs.size()==0){
                response.setStatus(414);            //no done randevous
                return;
            }

            SimpleUser su;
            boolean found=false;
            for(int i=0; i<randevouzs.size(); i++){
                su = new EditSimpleUserTable().databaseToSimpleUser(randevouzs.get(i).getUser_id());
                for(int j=0; j<bt.size(); j++){
                    if(bt.get(j).size()>0)
                        if(bt.get(j).get(0).getAmka().equals(su.getAmka()))
                            found=true;
                }
                if(!found)
                    bt.add(ebt.getAllTestsByAmka(su.getAmka()));
                found=false;
            }

            ArrayList<String> JSONbt = new ArrayList<>();
            for(int i=0; i<bt.size(); i++){
                for(int j=0; j<bt.get(i).size(); j++){
                    JSONbt.add(ebt.bloodTestToJSON(bt.get(i).get(j)));
                }
            }

            response.setStatus(200);
            out.println(JSONbt);*/
        }catch (SQLException e){
            System.out.println("Get docs clients " + e);
            response.setStatus(500);
            return;
        }catch (ClassNotFoundException e){
            System.out.println("Get docs clients " + e);
            response.setStatus(500);
            return;
        }
    }

}
