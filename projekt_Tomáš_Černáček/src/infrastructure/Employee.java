package infrastructure;

import base.Person;
import base.Status;
import tools.JSONInfo;
import warehouse.Product;

import java.io.*;
import java.util.*;

public class Employee extends Person {
    private Status employeeStatus;
    private int ID = 8099;

    private static final String EMPLOYEES = "EMPLOYEES.json";

    public int getEmployeeID(){
        return ID;
    }

    public void setEmployeeID(int ID) {
        this.ID = ID;
    }

    public boolean addOrder(Scanner s, Owner o){
        System.out.println("I dont have permission to do that!");
        return false;
    }

    public void addNewProduct(Scanner s){
        String input = "";
        System.out.println("Enter your id:");
        int tempID = s.nextInt();
        int empID = new JSONInfo(new File(EMPLOYEES)).checkEmployeeID(tempID);
        s.nextLine();
        if(empID != 0) {
            System.out.println("Type products in following format:");
            System.out.println("Item Amount");
            System.out.println("product ID and weight will be assigned automatically");
            System.out.println("Type \"-\" and hit Enter to quit adding new products to the warehouse.");
            while(s.hasNextLine()) {
                input = s.nextLine();
                if(input.equals("-")) {
                    break;
                }else{
                    String inputArr[] = input.split(" ");
                    if(Integer.parseInt(inputArr[1]) > 0) {
                        Product p = new Product(inputArr[0], Integer.parseInt(inputArr[1]));
                        p.addProduct(p);
                    }
                }
            }
        }else{
            System.out.println("Wrong ID!!!");
        }
    }

    public Employee(){

    }

    public Employee(int ID, String firstName, String lastName, String gender, int age){
        super(firstName, lastName, gender, age);
        this.employeeStatus  = new Status();
        employeeStatus.setStatus("EMPLOYEE");
        this.ID = ID;
    }
}
