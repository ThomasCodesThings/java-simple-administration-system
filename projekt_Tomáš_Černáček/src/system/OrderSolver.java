package system;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class OrderSolver {

    public void solve(){
    try{
        File of = new File("orderList.json");
        if(of.exists()){
            Object obj = new JSONParser().parse(new FileReader("orderList.json"));
            JSONArray orderList = (JSONArray) obj;
            for(int i = 0; i < orderList.size(); i++){
                JSONObject order = (JSONObject) orderList.get(i);
                JSONObject orderObj = (JSONObject) order.get("order");
                JSONObject updateObj = (JSONObject) order.get("orderState");
                String orderName = (String) orderObj.get("orderName");
                String orderState = (String) orderObj.get("orderState");
                if(orderState.equals("INCOMPLETE")) {
                    JSONArray productList = (JSONArray) orderObj.get("products");
                    int count = 0;
                    File ef = new File("EXPORT.json");
                    if (ef.exists()) {
                        Object expObj = new JSONParser().parse(new FileReader(ef.getName()));
                        JSONArray exportArr = (JSONArray) expObj;
                        for (int j = 0; j < productList.size(); j++) {
                            JSONObject productObj = (JSONObject) productList.get(j);
                            String productName = (String) productObj.get("productName");
                            int productAmount = Integer.parseInt((String) productObj.get("amount"));

                            for(int l = 0; l < exportArr.size(); l++){
                                JSONObject export = (JSONObject) exportArr.get(l);
                                JSONObject exportObj = (JSONObject) export.get("product");
                                String name = (String) exportObj.get("productName");
                                int amount = Integer.parseInt((String) exportObj.get("amount"));
                                if(name.equals(productName) && amount - productAmount >= 0) {
                                    count++;
                                }
                            }
                        }
                        if (count == productList.size()) {
                            for (int k = 0; k < productList.size(); k++) {
                                JSONObject productObj = (JSONObject) productList.get(k);
                                String name = (String) productObj.get("productName");
                                int amount = Integer.parseInt((String) productObj.get("amount"));
                                for(int m = 0; m < exportArr.size(); m++){
                                    JSONObject export = (JSONObject) exportArr.get(m);
                                    JSONObject exportObj = (JSONObject) export.get("product");
                                    String name2 = (String) exportObj.get("productName");
                                    int amount2 = Integer.parseInt((String) exportObj.get("amount"));
                                    String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
                                    if(name.equals(name2) && amount2 - amount >= 0) {
                                        if (amount2 - amount == 0) {
                                            exportArr.remove(export);
                                        } else {
                                            exportObj.put("time", currentTime);
                                            //double newWeight = (Double.parseDouble((String) exportObj.get("weight"))) / (Double.parseDouble((String) exportObj.get("amount"))) * amount;
                                            exportObj.put("amount", String.valueOf(amount2 - amount));
                                            //exportObj.put("weight", String.valueOf(newWeight));
                                        }
                                    }
                                }

                            }
                            orderObj.put("orderState", "COMPLETE");
                            //System.out.println("I´ve solved order " + orderName);
                            FileOutputStream outputStream = new FileOutputStream(ef); byte[] strToBytes = exportArr.toString().getBytes(); outputStream.write(strToBytes);
                            FileOutputStream outputStream2 = new FileOutputStream(of); byte[] strToBytes2 = orderList.toString().getBytes(); outputStream2.write(strToBytes2);
                        }
                    }
                }
            }
        }
    }catch(IOException | ParseException e){
        e.printStackTrace();
    }
    }

    public OrderSolver(){

    }
}
