package mainClasses;

import java.util.Map;

/**
 *
 * @author Mike
 */
public class Doctor extends User{
    int doctor_id;
    String specialty, doctor_info;
    int certified;

    public Doctor(Map<String, String> params){
        super(params);
        this.setDoctor_info(params.get("doctor_info"));
        this.setSpecialty(params.get("specialty"));
        this.setCertified(0);   //the doctor must be certificated by an administrator
    }
    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_info() {
        return doctor_info;
    }

    public void setDoctor_info(String doctor_info) {
        this.doctor_info = doctor_info;
    }



    public int getCertified() {
        return certified;
    }

    public void setCertified(int certified) {
        this.certified = certified;
    }



    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString(){
        String str = super.toString();
        str = str.substring(0, str.length()-1);
        str = str + ", doctor_id=" + this.getDoctor_id() +
                ", speciality='" + this.getSpecialty() + '\'' +
                ", doctor_info='" + this.getDoctor_info() + '\'' +
                ", certified=" + this.getCertified() + '}';
        return str;
    }
}
