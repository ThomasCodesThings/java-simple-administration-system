package infrastructure;

import OrderSystem.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;

public class Supervisor extends Employee{

    private static int superID = 111;

    public int getSuperID() {
        return superID;
    }

    public void setSuperID(int superID) {
        this.superID = superID;
    }

    public boolean addOrder(Scanner s, Owner o){
        System.out.println("Type owner or supervisor id:");
        int id = s.nextInt();
        boolean accessGranted = false;
        if(id == o.getOwnerID() || id == superID){
            accessGranted = true;
        }else {
            try {
                Object obj = new JSONParser().parse(new FileReader("EMPLOYEES.json"));
                JSONArray EmployeeArray = (JSONArray) obj;
                for(int i = 0; i < EmployeeArray.size(); i++){
                    JSONObject tempE = (JSONObject) EmployeeArray.get(i);
                    JSONObject tempEObj = (JSONObject) tempE.get("employee");
                    int employeeID = Integer.parseInt((String) tempEObj.get("id"));
                    if(id == employeeID && (tempEObj.get("status")).equals("SUPERVISOR")){
                        accessGranted = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(accessGranted) {
            System.out.println("Type order file name:");
            s.nextLine(); //consume "\n" character https://stackoverflow.com/questions/19335420/scan-nextline-does-not-work
            String orderFileName = s.nextLine();
            File orderFile = new File(orderFileName + ".txt");
            if (!orderFile.exists()) {
                System.out.println("Order file " + orderFileName + " does not exist.");
                return false;
            }else{
                new Order().addOrderToFile(orderFile, id);
            }
        }else {
            System.out.println("Error:");
            System.out.println("You are not allowed to do this!");
        }
        return false;
    }


    public Supervisor(){

    }

    public Supervisor(String firstName, String lastName, String gender, int age){
    super(new Employee().getEmployeeID(), firstName, lastName, gender, age);
    }
}
