package OrderSystem;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tools.JSONInfo;

public class Order {
    private String orderFileName;
    private String orderName;
    private String orderCreationDate;
    private static String orderState = "INCOMPLETE";
    private int adderID;
    private int orderID;
    private static final File f = new File("orderList.json");

    public static void addOrderToFile(File fo, int adderid) {
        try {
            boolean fileCreate = false;
            if (!f.exists()) {
                f.createNewFile();
                fileCreate = true;

            }
            boolean fileExist = false;
            if(!fileCreate && f.length() != 0) { //prevent from scanning empty JSON file
                Object obj = new JSONParser().parse(new FileReader("orderList.json")); //JSON stuff
                JSONArray orderList = (JSONArray) obj;
                String compare = fo.getName().substring(0, fo.getName().indexOf("."));
                for (int i = 0; i < orderList.size(); i++) {
                    JSONObject order = (JSONObject) orderList.get(i);
                    JSONObject orderObj = (JSONObject) order.get("order");
                    if (((String) orderObj.get("fileName")).equals(compare)) {
                        fileExist = true;
                        break;
                    }
                }
            }

            if(!fo.getName().equals(f.getName())) {
                if (!fileExist && f.exists()) {
                    String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
                    String fileName = fo.getName().substring(0, fo.getName().indexOf("."));

                    Scanner fos = new Scanner(fo);
                    Order order = new Order(fileName, fos.nextLine(), adderid, currentTime, orderState);
                    JSONArray orderArray;
                    if(f.length() != 0) {
                        Object obj = new JSONParser().parse(new FileReader("orderList.json"));
                        orderArray = (JSONArray) obj;
                    }else{
                        orderArray = new JSONArray();
                    }
                    JSONObject ord = new JSONObject();
                    JSONObject ordObj = new JSONObject();
                    ordObj.put("fileName", order.orderFileName);
                    ordObj.put("orderName", order.orderName);
                    ordObj.put("adderID", String.valueOf(order.adderID));
                    ordObj.put("id", String.valueOf(order.orderID));
                    ordObj.put("time", order.orderCreationDate);
                    ordObj.put("orderState", order.orderState);
                    JSONArray contentsArray = new JSONArray();

                    while(fos.hasNextLine()){
                        String temp[] = fos.nextLine().split(" ");
                        JSONObject contentsObj = new JSONObject();
                        contentsObj.put("productName", temp[0]);
                        contentsObj.put("amount", temp[1]);
                        contentsArray.add(contentsObj);
                    }
                    ordObj.put("products", contentsArray);
                    ord.put("order", ordObj);
                    orderArray.add(ord);
                    FileOutputStream outputStream = new FileOutputStream("orderList.json"); byte[] strToBytes = orderArray.toString().getBytes(); outputStream.write(strToBytes);
                    System.out.println("Order " + order.orderName + " was added successfully!");
                } else {
                    System.out.println("This order is already in our system.");
                }
            }else{
                System.out.println("Invalid file name!");
            }
        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public void getOrderState(Scanner lineScanner){
        try{
        if(f.exists() && f.length() != 0) { //prevent from scanning empty JSON file
            Object obj = new JSONParser().parse(new FileReader("orderList.json")); //JSON stuff
            JSONArray orderList = (JSONArray) obj;
            System.out.println("Insert order name:");
            String compare = lineScanner.nextLine();
            for (int i = 0; i < orderList.size(); i++) {
                JSONObject order = (JSONObject) orderList.get(i);
                JSONObject orderObj = (JSONObject) order.get("order");
                if (((String) orderObj.get("orderName")).equals(compare)) {
                    System.out.print("Order name: " + compare + "   ");
                    System.out.print("Order state: " + orderObj.get("orderState") + "   ");
                    System.out.print("Time: " + orderObj.get("time") + "\n");
                    break;
                }
            }
        }
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public Order(){

    }

    public Order(String orderFileName, String orderName, int adderID, String orderCreationDate, String orderState)
    {
        this.orderFileName = orderFileName;
        this.orderName = orderName;
        this.adderID = adderID;
        this.orderID = new JSONInfo(f).availableIndex("order");
        this.orderCreationDate = orderCreationDate;
        this.orderState = orderState;
    }
}