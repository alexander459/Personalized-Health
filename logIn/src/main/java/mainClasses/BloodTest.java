/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainClasses;

import java.util.Map;

/**
 *
 * @author Mountantonakis
 */
public class BloodTest {
    int bloodtest_id;
    String amka;   //has set
    String test_date;  //has set
    String medical_center;  //has set
    double vitamin_d3;
    String vitamin_d3_level;
    double vitamin_b12;
    String vitamin_b12_level;
    double cholesterol;
    String cholesterol_level;
    double blood_sugar;
    String blood_sugar_level;
    double iron;
    String iron_level;

    public BloodTest(Map<String, String> params){
        System.out.println(params);
        this.setAmka(params.get("amka"));
        this.setTest_date(params.get("test_date"));
        this.setMedical_center(params.get("medical_center"));
        this.setVitamin_d3(Double.parseDouble(params.get("vitamin_d3")));
        this.setIron(Double.parseDouble(params.get("iron")));
        this.setVitamin_b12(Double.parseDouble(params.get("vitamin_b12")));
        this.setBlood_sugar(Double.parseDouble(params.get("blood_sugar")));
        this.setCholesterol(Double.parseDouble(params.get("cholesterol")));
        this.setValues();
    }

    public BloodTest(){

    }


    public void setBloodtest_id(int bloodtest_id) {
        this.bloodtest_id = bloodtest_id;
    }
    public void setAmka(String amka) {
        this.amka = amka;
    }
    public void setTest_date(String test_date) {
        this.test_date = test_date;
    }
    public void setMedical_center(String medical_center) {
        this.medical_center = medical_center;
    }
    public void setVitamin_d3(double vitamin_d3) {
        this.vitamin_d3 = vitamin_d3;
    }
    public void setIron(double iron) {
        this.iron = iron;
    }
    public void setVitamin_b12(double vitamin_b12) {
        this.vitamin_b12 = vitamin_b12;
    }
    public void setBlood_sugar(double blood_sugar) {
        this.blood_sugar = blood_sugar;
    }
    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void setValues(){
        this.setCholesterol_level();
        this.setBlood_sugar_level();
        this.setIron_level();
        this.setVitamin_b12_level();
        this.setVitamin_d3_level();
    }
    public void setVitamin_d3_level() {
        if(vitamin_d3>150){
            vitamin_d3_level="High";
        }
        else if(vitamin_d3>=30){
            vitamin_d3_level="Normal";
        }
        else if(vitamin_d3>0){
            vitamin_d3_level="Low";
        }
    }
    public void setVitamin_b12_level() {
        if(vitamin_b12>925){
            vitamin_b12_level="High";
        }
        else if(vitamin_b12>=160){
            vitamin_b12_level="Normal";
        }
        else if(vitamin_b12>0){
            vitamin_b12_level="Low";
        }
    }
    public void setCholesterol_level() {
        if(cholesterol>=200){
            cholesterol_level="High";
        }
        else if(cholesterol<200 && cholesterol>0){
            cholesterol_level="Normal";
        }
    }
    public void setBlood_sugar_level() {
        if(blood_sugar>110){
            blood_sugar_level="High";
        }
        else if(blood_sugar>=70){
            blood_sugar_level="Normal";
        }
        else if(blood_sugar>0){
            blood_sugar_level="Low";
        }

    }
    public void setIron_level() {
        if(iron>150){
            iron_level="High";
        }
        else if(iron>=60){
            iron_level="Normal";
        }
        else if(iron>0){
            iron_level="Low";
        }
    }



    public int getBloodtest_id() {
        return bloodtest_id;
    }
    public String getAmka() {
        return amka;
    }
    public String getTest_date() {
        return test_date;
    }
    public String getMedical_center() {
        return medical_center;
    }
    public double getVitamin_d3() {
        return vitamin_d3;
    }
    public double getIron() {
        return iron;
    }
    public double getVitamin_b12() {
        return vitamin_b12;
    }
    public double getBlood_sugar() {
        return blood_sugar;
    }
    public double getCholesterol() {
        return cholesterol;
    }

    public String getIron_level() {
        return iron_level;
    }
    public String getBlood_sugar_level() {
        return blood_sugar_level;
    }
    public String getCholesterol_level() {
        return cholesterol_level;
    }
    public String getVitamin_b12_level() {
        return vitamin_b12_level;
    }
    public String getVitamin_d3_level() {
        return vitamin_d3_level;
    }
}
