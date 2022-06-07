package system;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tools.JSONInfo;
import base.Weight;

public class AutoCrafter {

    private int productID;
    private Weight productWeight;
    public final void craft(){
        try {
            File rf = new File("recipe.json");
            if (rf.exists()) {
                Object obj = new JSONParser().parse(new FileReader(rf.getName()));
                JSONArray recipeList = (JSONArray) obj;
                for(int i = 0; i < recipeList.size(); i++){
                    JSONObject recipe = (JSONObject) recipeList.get(i);
                    JSONObject recipeObj = (JSONObject) recipe.get("product");
                    String productName = (String) recipeObj.get("productName");
                    JSONArray ingredientsList = (JSONArray) recipeObj.get("ingredients");
                    int count = 0;
                    for(int j = 0; j < ingredientsList.size(); j++){
                        JSONObject ingredientObj = (JSONObject) ingredientsList.get(j);
                        String ingredientName = (String) ingredientObj.get("ingredientName");
                        int amount = Integer.parseInt((String) ingredientObj.get("ingredientAmount"));
                        File importFile = new File("IMPORT.json");
                        Object impObj = new JSONParser().parse(new FileReader(importFile.getName()));
                        JSONArray importList = (JSONArray) impObj;
                        for(int k = 0; k < importList.size(); k++){
                            JSONObject impor = (JSONObject) importList.get(k);
                            JSONObject imporObj = (JSONObject) impor.get("product");
                            String importName = (String) imporObj.get("productName");
                            int importAmount = Integer.parseInt((String) imporObj.get("amount"));
                            if(importName.equals(ingredientName) && (importAmount - amount) >= 0){
                                count++;
                            }
                        }
                    }
                    if(count == ingredientsList.size()) {
                        File exportFile = new File("EXPORT.json");
                        if(!exportFile.exists()){
                            exportFile.createNewFile();
                        }
                        JSONInfo ji = new JSONInfo(exportFile);
                        JSONArray exportArr;
                        boolean productExist = false;
                        int value = 0;
                        if(exportFile.length() != 0) {
                            productExist = ji.exist(productName, "productName");
                            productID = ji.availableIndex("product");
                            value = ji.getProductAmount(productName, "amount");
                            Object exObj = new JSONParser().parse(new FileReader(exportFile.getName()));
                            exportArr = (JSONArray) exObj;
                        }else{
                            productID = 0;
                            exportArr = new JSONArray();
                        }
                        String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
                        if(!productExist) {
                            JSONObject newExpObj = new JSONObject();
                            newExpObj.put("productName", productName);
                            newExpObj.put("id", String.valueOf(productID));
                            newExpObj.put("time", currentTime);
                            newExpObj.put("amount", String.valueOf(1));
                            JSONObject newExp = new JSONObject();
                            newExp.put("product", newExpObj);
                            exportArr.add(newExp);
                        }else{
                            for(int l = 0; l < exportArr.size(); l++){
                                JSONObject export = (JSONObject) exportArr.get(l);
                                JSONObject exportObj = (JSONObject) export.get("product");
                                String name = (String) exportObj.get("productName");
                                if(name.equals(productName)) {
                                    exportObj.put("amount", String.valueOf(value+1));
                                    exportObj.put("time", currentTime);
                                    break;
                                }
                            }
                        }
                        FileOutputStream outputStream = new FileOutputStream(exportFile); byte[] strToBytes = exportArr.toString().getBytes(); outputStream.write(strToBytes); //json update
                        for(int k = 0; k < ingredientsList.size(); k++){
                            JSONObject ingredienceObj = (JSONObject) ingredientsList.get(k);
                            String ingredienceName2 = (String) ingredienceObj.get("ingredientName");
                            int amount2 = Integer.parseInt((String) ingredienceObj.get("ingredientAmount"));
                            File impFile = new File("IMPORT.json");
                            if(impFile.exists()) {
                                Object imObj = new JSONParser().parse(new FileReader(impFile.getName()));
                                JSONArray importArr = (JSONArray) imObj;
                                for(int m = 0; m < importArr.size(); m++){
                                    JSONObject impor = (JSONObject) importArr.get(m);
                                    JSONObject importObj = (JSONObject) impor.get("product");
                                    String name = (String) importObj.get("productName");
                                    if(name.equals(ingredienceName2)){
                                        int result = Integer.parseInt((String) importObj.get("amount")) - amount2;
                                        productWeight = new Weight(Integer.parseInt((String) importObj.get("amount")), Float.parseFloat((String) importObj.get("weight")), result);
                                        if(result == 0){
                                            importArr.remove(impor);
                                        }else{
                                            importObj.put("time", currentTime);
                                            importObj.put("weight", String.valueOf(productWeight.calculate()));
                                            importObj.put("amount", String.valueOf(result));
                                        }
                                    }
                                }
                                FileOutputStream outputStream2 = new FileOutputStream(impFile); byte[] strToBytes2 = importArr.toString().getBytes(); outputStream2.write(strToBytes2); //json update
                            }
                        }
                        //System.out.println("I´ve crafted one " + productName);
                    }
                }
            }
        }catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } {

        }
    }

    public AutoCrafter(){

    }
}
