package warehouse;

import OrderSystem.Order;
import base.Country;
import infrastructure.Employee;
import infrastructure.Owner;
import infrastructure.Supervisor;
import base.Residence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Warehouse extends Residence {

    public String warehouseName = "WareWere";
    public String warehouseDesc = "This is the best warehouse in this country!";
    private Country warehouseCountry;

    public void printImportItems() {
        try {
            System.out.println(warehouseName + " has following imported items:");
            Object obj  = new JSONParser().parse(new FileReader("IMPORT.json"));
            JSONArray ImportArray = (JSONArray) obj;
            for(int i = 0; i < ImportArray.size(); i++){
                for (int j = 0; j < ImportArray.size(); j++) {
                    JSONObject tempI = (JSONObject) ImportArray.get(j);
                    JSONObject tempIObj = (JSONObject) tempI.get("product");
                    String importProductName = (String) tempIObj.get("productName");
                    String importProductAmount = (String) tempIObj.get("amount");
                    String importProductWeight = (String) tempIObj.get("weight");
                    String importProductTime = (String) tempIObj.get("time");
                    String importProductID = (String) tempIObj.get("id");
                        if(i == Integer.parseInt(importProductID)){
                            System.out.println(importProductID + " " + importProductName + " " + importProductAmount + "[pcs] " + importProductWeight + "[kg] " + importProductTime);
                        }
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void printExportItems() {
        try {
            System.out.println(warehouseName + " has following items for export:");
            Object obj  = new JSONParser().parse(new FileReader("EXPORT.json"));
            JSONArray ExportArray = (JSONArray) obj;
            for(int i = 0; i < ExportArray.size(); i++){
            for (int j = 0; j < ExportArray.size(); j++) {
                JSONObject tempE = (JSONObject) ExportArray.get(j);
                JSONObject tempEObj = (JSONObject) tempE.get("product");
                String exportProductName = (String) tempEObj.get("productName");
                String exportProductAmount = (String) tempEObj.get("amount");
                String exportProductTime = (String) tempEObj.get("time");
                String exportProductID = (String) tempEObj.get("id");
                    if(i == Integer.parseInt(exportProductID)){
                        System.out.println(exportProductID + " " + exportProductName + " " + exportProductAmount + "[pcs] " + exportProductTime);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void printEmployeeList() {
        try {
            System.out.println(warehouseName + " has following exployees:");
            Object obj  = new JSONParser().parse(new FileReader("EMPLOYEES.json"));
            JSONArray EmployeeArray = (JSONArray) obj;
            String status[] = {"OWNER", "SUPERVISOR", "EMPLOYEE"};
            for(int i = 0; i < status.length; i++) {
                for (int j = 0; j < EmployeeArray.size(); j++) {
                    JSONObject tempE = (JSONObject) EmployeeArray.get(j);
                    JSONObject tempEObj = (JSONObject) tempE.get("employee");
                    if (((String) tempEObj.get("status")).equals(status[i])) {
                        String tempEFirstName = (String) tempEObj.get("firstName");
                        String tempELastname = (String) tempEObj.get("lastName");
                        String tempEGender = (String) tempEObj.get("gender");
                        int tempEAge = Integer.parseInt((String) tempEObj.get("age"));
                        int tempEID = Integer.parseInt((String) tempEObj.get("id"));
                        System.out.println(tempEID + " " + status[i] + " " + tempEFirstName + " " + tempELastname + " " + tempEAge + " " + tempEGender);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void printOrders(){
        try{
            System.out.println(warehouseName + " has following orders:");
            Object obj = new JSONParser().parse(new FileReader("orderList.json"));
            JSONArray orderList = (JSONArray) obj;
            String orderStates[] = {"INCOMPLETE", "COMPLETE"};
            for(int i = 0; i < orderList.size(); i++) {
                for (int j = 0; j < orderStates.length; j++) {
                    for (int k = 0; k < orderList.size(); k++) {
                        JSONObject order = (JSONObject) orderList.get(k);
                        JSONObject orderObj = (JSONObject) order.get("order");
                        String orderState = (String) orderObj.get("orderState");
                        String orderID = (String) orderObj.get("id");
                        String time = (String) orderObj.get("time");
                        String orderName = (String) orderObj.get("orderName");
                        if (orderState.equals(orderStates[j])) {
                            if(i == Integer.parseInt(orderID)) {
                                System.out.println(orderID + " " + orderState + " " + orderName + " " + orderID + " " + time);
                            }
                        }
                    }
                }
            }
        }catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void orderTime(Scanner s){
        try {
            System.out.println("Type one of these options:");
            System.out.println("(less/more)");
            String input = s.nextLine();
            System.out.println("Type number of hours:");
            int timeRequest = s.nextInt();
            Object obj = new JSONParser().parse(new FileReader("orderList.json"));
            JSONArray orderList = (JSONArray) obj;
            System.out.println("State of order\t\tName of order");
                for(int j = 0; j < orderList.size(); j++) {
                    JSONObject order = (JSONObject) orderList.get(j);
                    JSONObject orderObj = (JSONObject) order.get("order");
                    String orderState = (String) orderObj.get("orderState");
                    String time = (String) orderObj.get("time");
                    String orderName = (String) orderObj.get("orderName");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String currentTime = sdf.format(new Date());
                    Date d1 = sdf.parse(time);
                    Date d2 = sdf.parse(currentTime);
                    long timeDiff = d2.getTime() - d1.getTime();
                    long diffMin = TimeUnit.MILLISECONDS.toHours(timeDiff) % 24;
                    int difference = Math.toIntExact(diffMin);
                    if(input.equals("less") && difference <= timeRequest){
                        System.out.println(orderState + " " + orderName);
                        }else if(input.equals("more") && difference >= timeRequest){
                        System.out.println(orderState + " " + orderName);
                    }
                }
        }catch (java.text.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void taskList(){
        System.out.println("type:");
        System.out.println("\t\"emp -a\" to add new employee");
        System.out.println("\t\"emp -k\" to kick an employee(Owner permission required!!)");
        System.out.println("\t\"emp -p\" to promote an employee(Owner permission required!!)");
        System.out.println("\t\"help\" to print main menu");
        System.out.println("\t\"info\" to get warehouse info");
        System.out.println("\t\"ord -a\" to add new order(Owner or Supervisor permission required!!)");
        System.out.println("\t\"ord -get\" to print all orders made less or more than N hours ago");
        System.out.println("\t\"ord -s\" to print order state");
        System.out.println("\t\"prod -a\" to add new product to warehouse");
        System.out.println("\t\"print -e\" to print all items for export in warehouse");
        System.out.println("\t\"print -emp\" to print warehouse´s employees");
        System.out.println("\t\"print -i\" to print all imported items in warehouse");
        System.out.println("\t\"print -o\" to print all orders in warehouse");
        System.out.println("\t\"print -r\" to print all recipes");
        System.out.println("\t\"rec -a\" to add new recipe(Owner permission required!!)");
        System.out.println("\t\"rec -r\" to remove existing recipe(Owner permission required!!)");
        System.out.println("\t\"quit\" to quit this program");
    }

    public void informations(Warehouse w){
        System.out.println(w.warehouseDesc);
        System.out.println(w.warehouseName + " location: ");
        System.out.println(w.getStreet());
        System.out.println(w.getPSC() + " " +w.getTownName());
        System.out.println(w.warehouseCountry.getName() + " in " + w.warehouseCountry.getContinent());
    }

    public String getPSC() {
        return "overridding " + new Random().nextInt(1000);
    }

    public void mainMenu(Warehouse w, Owner o) {
            w.taskList();
            Scanner scanner = new Scanner(System.in);
            String input = "";
            while (!input.equals("quit")) {
                input = scanner.nextLine();
                switch (input) {
                    case "emp -a":
                        o.openFile(o);
                        o.addNewEmployee(scanner, o);
                        break;
                    case "emp -k":
                        o.kickEmployee(scanner);
                        break;
                    case "emp -p":
                        o.promoteEmployee(scanner);
                        break;
                    case "help":
                        w.taskList();
                        break;
                    case "info":
                        w.informations(w);
                        System.out.println("**************************************************");
                        o.information(o);
                        break;
                    case "ord -a":
                        new Supervisor().addOrder(scanner, o);
                        break;
                    case "ord -get":
                        w.orderTime(scanner);
                        break;
                    case "ord -s":
                        new Order().getOrderState(scanner);
                        break;
                    case "print -e":
                        w.printExportItems();
                        break;
                    case "print -emp":
                        w.printEmployeeList();
                        break;
                    case "print -i":
                        w.printImportItems();
                        break;
                    case "print -o":
                        w.printOrders();
                        break;
                    case "print -r":
                        new Recipe().printAllRecipes();
                        break;
                    case "prod -a":
                        new Employee().addNewProduct(scanner);
                        break;
                    case "rec -a":
                        new Recipe().addRecipe(scanner, o);
                        break;
                    case "rec -r":
                        new Recipe().removeRecipe(scanner, o);
                        break;
                    default:
                        System.out.println("Unknown command, please try again.");
                }
            }
            scanner.close();
            System.out.println("GoodBye :-)");
            System.out.println("Have a Blessed day.");
    }

    public Warehouse(String townName, String PSC, String street){
        super(townName, PSC, street);
        this.warehouseCountry = new Country();
        warehouseCountry.setName("Slovakia");
        warehouseCountry.setContinent("Europe");
    }

    /*TODO IMPORTANT!!!!!
    Ide to o to že chcem aby jeden while na stringy v warehouse.mainMenu bežal súčasne s
     s tým čo je v case "auto"(to chcem dať do samostatného while cyklu)
    => 2 while cykly v dvoch vláknach bežia parelelne počas behu programu.
     */
    /*public static void main(String[] args) {
        Owner owner = new Owner("Tomáš", "Černáček", "male", 21, "Stará Turá", "916 01", "Sesame St.");
        Warehouse warehouse = new Warehouse("Stará Turá", "916 01", "J. K. Gruska 15");
        warehouse.mainMenu(warehouse, owner);
    }*/
}
