package tools;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONInfo implements JSOInterface{

    public int i = 0;
    public File f;

    public int availableIndex(String key){
        try{
            if(f.exists()){
                Object obj = new JSONParser().parse(new FileReader(f));
                JSONArray listArr = (JSONArray) obj;
                ArrayList<Integer> array = new ArrayList<Integer>();
                for(int j = 0; j < listArr.size(); j++){
                    JSONObject list = (JSONObject) listArr.get(j);
                    JSONObject listObj = (JSONObject) list.get(key);
                    int id =  Integer.parseInt((String) listObj.get("id"));
                    array.add(id);
                }
                Collections.sort(array);
                for(int k = 0; k < array.size(); k++){
                    if(array.get(k) == i){
                        i++;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int availableIndex(String key, int number){
        try{
            if(f.exists()){
                Object obj = new JSONParser().parse(new FileReader(f));
                JSONArray listArr = (JSONArray) obj;
                ArrayList<Integer> array = new ArrayList<Integer>();
                for(int j = 0; j < listArr.size(); j++){
                    JSONObject list = (JSONObject) listArr.get(j);
                    JSONObject listObj = (JSONObject) list.get(key);
                    int id =  Integer.parseInt((String) listObj.get("id"));
                    array.add(id);
                }
                Collections.sort(array);
                for(int k = 0; k < array.size(); k++){
                    if(array.get(k) == number){
                        number++;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }

    public int checkEmployeeID(int empID){
        try{
            if(f.exists()){
                Object obj = new JSONParser().parse(new FileReader(f));
                JSONArray listArr = (JSONArray) obj;
                for(int j = 0; j < listArr.size(); j++){
                    JSONObject list = (JSONObject) listArr.get(j);
                    JSONObject listObj = (JSONObject) list.get("employee");
                    int id =  Integer.parseInt((String) listObj.get("id"));
                    if(id == empID){
                        return id;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean exist(String name, String key){
        try{
            if(f.exists()) {
                Object obj = new JSONParser().parse(new FileReader(f.getName()));
                JSONArray listArr = (JSONArray) obj;
                for (int j = 0; j < listArr.size(); j++) {
                    JSONObject list = (JSONObject) listArr.get(j);
                    JSONObject listObj = (JSONObject) list.get("product");
                    String checkName = (String) listObj.get(key);
                    if (checkName.equals(name)) {
                        return true;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getProductTime(String name, String key){
        try{
            if(f.exists()) {
                Object obj = new JSONParser().parse(new FileReader(f.getName()));
                JSONArray listArr = (JSONArray) obj;
                for (int j = 0; j < listArr.size(); j++) {
                    JSONObject list = (JSONObject) listArr.get(j);
                    JSONObject listObj = (JSONObject) list.get("product");
                    String checkName = (String) listObj.get(key);
                    if (checkName.equals(name)) {
                        return (String) listObj.get("time");
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getProductAmount(String name, String key){
        try{
            if(f.exists()) {
                Object obj = new JSONParser().parse(new FileReader(f.getName()));
                JSONArray listArr = (JSONArray) obj;
                for (int j = 0; j < listArr.size(); j++) {
                    JSONObject list = (JSONObject) listArr.get(j);
                    JSONObject listObj = (JSONObject) list.get("product");
                    String checkName = (String) listObj.get("productName");
                    int productAmount = Integer.parseInt((String) listObj.get(key));
                    if (checkName.equals(name)) {
                        return productAmount;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public JSONInfo(File f){
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.f = f;
    }
}
