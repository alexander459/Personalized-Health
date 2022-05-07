/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainClasses;


import java.util.Map;

/**
 *
 * @author Mike
 */
public class User {
    String username,email,password,firstname,lastname,birthdate;
    String gender;
    String amka;
    String country,city,address;
    Double lat,lon;
    String telephone;
    int height;
    double weight;
    int blooddonor;
    String bloodtype;

    public User(Map<String, String> params){
        this.setUsername(params.get("username"));
        this.setEmail(params.get("email"));
        this.setPassword(params.get("password"));
        this.setFirstname(params.get("firstname"));
        this.setLastname(params.get("lastname"));
        this.setBirthdate(params.get("birthday"));
        this.setGender(params.get("gender"));
        this.setAmka(params.get("amka"));
        this.setCountry(params.get("country"));
        this.setCity(params.get("city"));
        this.setAddress(params.get("address"));
        this.setLat(Double.parseDouble(params.get("lat")));
        this.setLon(Double.parseDouble(params.get("lon")));
        this.setTelephone(params.get("telephone"));
        //following fields are optional so they can be null
        try {
            this.setHeight(Integer.parseInt(params.get("height")));
        }catch(NumberFormatException e){
            this.setHeight(-1);
        }

        //parseDouble throws 2 exceptions
        try{
            this.setWeight(Double.parseDouble(params.get("weight")));
        }catch (NumberFormatException e){
            this.setWeight(-1);
            System.out.println("ex1 " + e);
        }catch (NullPointerException e){
            this.setWeight(-1);
            System.out.println("ex2 " + e);
            System.out.println("weight is "+params.get("weight"));
        }
        //they can not be null by default
        this.setBlooddonor(Integer.parseInt(params.get("blooddonor")));
        this.setBloodtype(params.get("bloodtype"));
    }

    /*SETTERS*/
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setAmka(String amka) {
        this.amka = amka;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setBlooddonor(int blooddonor) {
        this.blooddonor = blooddonor;
    }
    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }


    /*GETTERS*/
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getBirthdate() {
        return birthdate;
    }
    public String getGender() {
        return gender;
    }
    public String getAmka() {
        return amka;
    }
    public String getCountry() {
        return country;
    }
    public String getCity() {
        return city;
    }
    public String getAddress() {
        return address;
    }
    public Double getLat() {
        return lat;
    }
    public Double getLon() {
        return lon;
    }
    public String getTelephone() {
        return telephone;
    }
    public int getHeight() {
        return height;
    }
    public double getWeight() {
        return weight;
    }
    public int isBloodDonor() {
        return blooddonor;
    }
    public String getBloodtype() {
        return bloodtype;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", gender='" + gender + '\'' +
                ", amka='" + amka + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", telephone='" + telephone + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", blooddonor=" + blooddonor +
                ", bloodtype='" + bloodtype + '\'' +
                '}';
    }
}