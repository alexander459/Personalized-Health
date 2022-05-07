package mainClasses;

import java.util.HashMap;
import java.util.Map;

public class MapConverter {

    /*takes a query format string and returns a map with the parameters*/
    public Map<String, String> queryToMap(String query){
        int i;
        Map<String, String> params = new HashMap<>();
        //create a String array with the parameters. string[0]: name0, string[1]: value0, string[2]: name1...
        String[] tokens = query.split("=|&");
        for(i=0; i<tokens.length/2; i++){
            params.put(tokens[i*2], tokens[(i*2)+1]);
        }

        if(!params.containsKey("blooddonor"))
            return params;
        if(params.get("blooddonor").equalsIgnoreCase("yes")){
            params.replace("blooddonor", "1");
        }else{
            params.replace("blooddonor", "0");
        }

        return params;
    }
}

