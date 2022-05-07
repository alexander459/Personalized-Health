/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainClasses;

import java.util.Map;

/**
 *
 * @author micha
 */
public class Message {

    int message_id, doctor_id, user_id;
    String bloodtype;
    String date_time;
    String message;
    String sender;
    int blood_donation;


    public int getMessage_id() {
        return message_id;
    }
    public int getDoctor_id() {
        return doctor_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public String getBloodtype() {
        return bloodtype;
    }
    public String getDate_time() {
        return date_time;
    }
    public String getMessage() {
        return message;
    }
    public String getSender() {
        return sender;
    }
    public int getBlood_donation() {
        return blood_donation;
    }



    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }
    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }
    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setBlood_donation(int blood_donation) {
        this.blood_donation = blood_donation;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", doctor_id=" + doctor_id +
                ", user_id=" + user_id +
                ", bloodtype='" + bloodtype + '\'' +
                ", date_time='" + date_time + '\'' +
                ", message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", blood_donation=" + blood_donation +
                '}';
    }
}
