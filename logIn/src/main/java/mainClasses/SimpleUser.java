
package mainClasses;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Map;

/**
 *
 * @author Mike
 */
public class SimpleUser extends User{
    int user_id=0;
    public SimpleUser(Map<String, String> params){
        super(params);
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        String str = super.toString();
        str = str.substring(0, str.length()-1);
        str = str + ", user_id=" + this.getUser_id() + '}';
        return str;
    }
}

