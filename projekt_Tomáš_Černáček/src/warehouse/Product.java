package warehouse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import base.Weight;
import tools.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Product {
    private String productName;
    private int productAmount;
    private int productID = 0;
    private double productWeight;

    private static final String IMPORT = "IMPORT.json";

    public void addProduct(Product p) {
        File importF = new File(IMPORT);
        try {
            if (!importF.exists()) {
                importF.createNewFile();
            }

            JSONInfo jsonTest = new JSONInfo(importF);
            JSONArray productArr;
            boolean importExist = false;
            if(importF.length() != 0) {
                importExist = jsonTest.exist(p.productName, "productName");
                p.productID = jsonTest.availableIndex("product");
                Object obj = new JSONParser().parse(new FileReader(IMPORT));
                productArr = (JSONArray) obj;
            }
            else{
                p.productID = 0;
                productArr = new JSONArray();
            }

            if(!importExist) {
                JSONObject productObj = new JSONObject();
                productObj.put("productName", p.productName);
                productObj.put("id", String.valueOf(p.productID));
                productObj.put("weight", String.valueOf(p.productWeight));
                productObj.put("amount", String.valueOf(p.productAmount));
                productObj.put("time", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
                JSONObject product = new JSONObject();
                product.put("product", productObj);
                productArr.add(product);
            }else {
                for(int j = 0; j < productArr.size(); j++){
                    JSONObject product = (JSONObject) productArr.get(j);
                    JSONObject productObj = (JSONObject) product.get("product");
                    if(((String) productObj.get("productName")).equals(p.productName)){
                        p.productAmount = Integer.parseInt((String) productObj.get("amount"))+p.productAmount;
                        Weight weight = new Weight(Integer.parseInt((String) productObj.get("amount")), Double.parseDouble((String) productObj.get("weight")), p.productAmount);
                        productObj.put("amount", String.valueOf(p.productAmount));
                        productObj.put("weight", String.valueOf(weight.calculate()));
                        productObj.put("time", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
                        break;
                    }
                }
            }
            FileOutputStream outputStream = new FileOutputStream(IMPORT); byte[] strToBytes = productArr.toString().getBytes(); outputStream.write(strToBytes); //json update
        }catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Product(){

    }

    public Product(String productName, int productAmount){
        this.productName = productName;
        this.productAmount = productAmount;
        this.productID = (++productID);
        Random rand = new Random();
        this.productWeight = rand.nextInt(1000) / productAmount;
    }
}
