package infrastructure;

import base.Person;
import base.Status;
import tools.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Owner  extends Person {

    protected final int ownerID = 12345;
    private static final String EMPLOYEES = "EMPLOYEES.json";
    private Status status;

    public String getStatus() {
        return status.toString();
    }

    public int getOwnerID(){
        return ownerID;
    }

    public void information(Owner o){
        System.out.println(o.status.getStatus() + ":");
        System.out.println(o.getFirstName() + " " + o.getLastName());
        System.out.println(o.getGender() + " " + o.getAge());
        System.out.println(o.getStreet());
        System.out.println(o.getPSC() + " " + o.getTownName());
    }

    public void openFile(Owner o) {
        try {
            File f = new File(EMPLOYEES);
            if (!f.exists()){
                FileWriter writer = new FileWriter(f);
                f.createNewFile();
                //Object obj = new JSONParser().parse(new FileReader(f));
                JSONArray ownerArr = new JSONArray();
                JSONObject owner = new JSONObject();
                JSONObject ownerObj = new JSONObject();
                ownerObj.put("status", o.getStatus());
                ownerObj.put("id", String.valueOf(o.ownerID));
                ownerObj.put("firstName", o.getFirstName());
                ownerObj.put("lastName", o.getLastName());
                ownerObj.put("gender", o.getGender());
                ownerObj.put("age", String.valueOf(o.getAge()));
                owner.put("employee", ownerObj);
                ownerArr.add(owner);
                FileOutputStream outputStream = new FileOutputStream(f); byte[] strToBytes = ownerArr.toString().getBytes(); outputStream.write(strToBytes); //json update
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void addNewEmployee(Scanner lineScanner, Owner o) {
        System.out.println("Do you want to act as secret employee?");
        String answer = lineScanner.nextLine();
        if (answer.equals("No") || answer.equals("no")) {
            System.out.println("Type Owner ID:");
            if (lineScanner.nextInt() == ownerID) {
                System.out.println("Insert new employee in following format:");
                System.out.println("First_name Last_name Gender Age");
                System.out.println("Employee´s ID will be assigned automatically");
                System.out.println("Type \"-\" and hit Enter to quit adding new employees.");
                String lineInput = "";
                while (lineScanner.hasNextLine()) {
                    lineInput = lineScanner.nextLine();
                    if (lineInput.equals("-")) {
                        break;
                    }
                    String inputArr[] = lineInput.split(" ");
                    if (inputArr.length == 4) {
                        if (Integer.parseInt(inputArr[3]) >= 15 && (inputArr[2].equals("male") || inputArr[2].equals("female"))) {
                            Employee employee = new Employee((new JSONInfo(new File(EMPLOYEES)).availableIndex("employee", 8100)), inputArr[0], inputArr[1], inputArr[2], Integer.parseInt(inputArr[3]));
                            try {
                                Object obj = new JSONParser().parse(new FileReader(EMPLOYEES));
                                JSONArray EmployeeArray = (JSONArray) obj;
                                boolean EmployeeExist = false;
                                for (int i = 0; i < EmployeeArray.size(); i++) {
                                    JSONObject tempE = (JSONObject) EmployeeArray.get(i);
                                    JSONObject tempEObj = (JSONObject) tempE.get("employee");
                                    String tempEFirstName = (String) tempEObj.get("firstName");
                                    String tempELastname = (String) tempEObj.get("lastName");
                                    String tempEGender = (String) tempEObj.get("gender");
                                    int tempEAge = Integer.parseInt((String) tempEObj.get("age"));
                                    if ((tempEFirstName.equals(employee.getFirstName()) && tempELastname.equals(employee.getLastName()) && tempEGender.equals(employee.getGender()) && tempEAge == employee.getAge())) {
                                        EmployeeExist = true;
                                    }

                                }
                                if (!EmployeeExist && employee.getEmployeeID() != ownerID) {
                                    JSONObject employe = new JSONObject();
                                    JSONObject employeeObj = new JSONObject();
                                    employeeObj.put("status", "EMPLOYEE");
                                    employeeObj.put("id", String.valueOf(employee.getEmployeeID()));
                                    employeeObj.put("firstName", employee.getFirstName());
                                    employeeObj.put("lastName", employee.getLastName());
                                    employeeObj.put("gender", employee.getGender());
                                    employeeObj.put("age", String.valueOf(employee.getAge()));
                                    employe.put("employee", employeeObj);
                                    EmployeeArray.add(employe);
                                    FileOutputStream outputStream = new FileOutputStream(EMPLOYEES);
                                    byte[] strToBytes = EmployeeArray.toString().getBytes();
                                    outputStream.write(strToBytes); //json update
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else{
                            System.out.println("New employee must at least 15 years old.");
                            break;
                        }
                    }
                }
            } else {
                System.out.println("Error:");
                System.out.println("UNAUTHORIZED ACCESS!");
            }
        }else{
            //small guessing game for fun
            int range = 10;
            int attempts = 5;
            System.out.println("Then guess number from 0 to " + range);
            System.out.println("You have " + attempts + " attempts!");
            System.out.println("Good luck!");
            int rndNumber = new Random().nextInt(range);
            boolean riddleSolved = false;
            for(int i = 0; i < attempts; i++){
                System.out.println("Attempt " + (i+1) + " out of " + attempts);
                int userAnswer= lineScanner.nextInt();
                if(rndNumber == userAnswer){
                    riddleSolved = true;
                    System.out.println("Congratulations!");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        //TODO clear console?
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
                }else if(userAnswer < rndNumber){
                    System.out.println("Try again with higher number");
                }else{
                    System.out.println("Try again with smaller number");
                }
            }
            if(riddleSolved) {
                Employee secretEmployee = new Supervisor("Martin", "Novacik", "male", 20); //UPCASTING
                System.out.println(secretEmployee.getClass());
                Supervisor secretSupervisor = (Supervisor) secretEmployee; //DOWNCASTING
                System.out.println(secretSupervisor.getClass());
                //secretSupervisor.setSuperID(111);
                System.out.println("Your ID is now " + secretSupervisor.getSuperID());
                secretSupervisor.addOrder(lineScanner, o);
            }else{
                System.out.println("Better luck next time!");
            }
        }
    }
    public void promoteEmployee(Scanner lineScanner){
        try {
            System.out.println("Insert Owner ID:");
                if (lineScanner.nextInt() == ownerID) {
                    System.out.println("Insert employee´s ID:");
                    int empID = lineScanner.nextInt();
                    if(empID != ownerID) {
                        Object obj = new JSONParser().parse(new FileReader(EMPLOYEES));
                        JSONArray EmployeeArray = (JSONArray) obj;
                        boolean isSupervisor = false;
                        boolean employeeExist = false;
                        for(int i = 0; i < EmployeeArray.size(); i++){
                            JSONObject tempE = (JSONObject) EmployeeArray.get(i);
                            JSONObject tempEObj = (JSONObject) tempE.get("employee");
                            int employeeID = Integer.parseInt((String) tempEObj.get("id"));
                            if((tempEObj.get("status")).equals("SUPERVISOR") && empID == employeeID){
                                isSupervisor = true;
                                break;
                            }
                            if(empID == employeeID && (!((String) tempEObj.get("status")).equals("SUPERVISOR"))){
                                String tempEFirstName = (String) tempEObj.get("firstName");
                                String tempELastname = (String) tempEObj.get("lastName");
                                tempEObj.put("status", "SUPERVISOR");
                                System.out.println(tempEFirstName + " " + tempELastname + " was promoted to supervisor!");
                                employeeExist = true;
                                FileOutputStream outputStream = new FileOutputStream(EMPLOYEES); byte[] strToBytes = EmployeeArray.toString().getBytes(); outputStream.write(strToBytes); //json update
                                break;
                            }
                        }
                        if(isSupervisor){
                            System.out.println("This employee is already a supervisor.");
                        } else if(!employeeExist) {
                            System.out.println("This employee does not exist");
                        }
                    }
                }else{
                    System.out.println("Error:");
                    System.out.println("USER NOT AUTHENTICATED!!!");
                }
                lineScanner.nextLine();
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public void kickEmployee(Scanner lineScanner) {
        try {
            System.out.println("Insert Owner ID:");
            if (lineScanner.nextInt() == ownerID) {
                System.out.println("Insert Employee ID:");
                int empID = lineScanner.nextInt();
                if(empID != ownerID) {
                    Object obj = new JSONParser().parse(new FileReader(EMPLOYEES));
                    JSONArray EmployeeArray = (JSONArray) obj;
                    for(int i = 0; i < EmployeeArray.size(); i++){
                        JSONObject tempE = (JSONObject) EmployeeArray.get(i);
                        JSONObject tempEObj = (JSONObject) tempE.get("employee");
                        int employeeID = Integer.parseInt((String) tempEObj.get("id"));
                        if(empID == employeeID){
                            String tempEFirstName = (String) tempEObj.get("firstName");
                            String tempELastname = (String) tempEObj.get("lastName");
                            EmployeeArray.remove(tempE);
                            System.out.println(tempEFirstName + " " + tempELastname + " got kicked from this company!");
                            FileOutputStream outputStream = new FileOutputStream(EMPLOYEES); byte[] strToBytes = EmployeeArray.toString().getBytes(); outputStream.write(strToBytes); //json update
                            break;
                        }
                    }
                } else{
                    System.out.println("Error:");
                    System.out.println("Employee with ID " + empID + " does not exist.");
                }
            } else {
                System.out.println("Error:");
                System.out.println("USER NOT AUTHENTICATED!!!");
            }
            lineScanner.nextLine();
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public Owner(String firstName, String lastName, String gender, int age, String townName, String PSC, String street){
        super(firstName, lastName, gender, age, townName, PSC, street);
        this.status = new Status();
        status.setStatus("OWNER");
    }
}
