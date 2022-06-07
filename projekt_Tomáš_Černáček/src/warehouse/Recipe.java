package warehouse;

import infrastructure.Owner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class Recipe {

    private String recipeName;
    private ArrayList<String> ingredientName = new ArrayList<String>();
    private ArrayList<Integer> ingredientAmount = new ArrayList<Integer>();
    private File recipeFile = new File("recipe.json");

    public final void addRecipe(Scanner lineScanner, Owner owner) {
        System.out.println("Type Owner ID:");
        if(lineScanner.nextInt() == owner.getOwnerID()) {
            System.out.println("Type name of recipe:");
            lineScanner.nextLine();
            recipeName = lineScanner.nextLine();
            boolean recipeExist = false;
            try {
                if(!recipeFile.exists()){
                    recipeFile.createNewFile();
                }
                if(recipeFile.length() > 0){
                    Object obj = new JSONParser().parse(new FileReader(recipeFile.getName()));
                    JSONArray recipeList = (JSONArray) obj;
                    for(int i = 0; i < recipeList.size(); i++) {
                        JSONObject recipe = (JSONObject) recipeList.get(i);
                        JSONObject recipeObj = (JSONObject) recipe.get("product");
                        String productName = (String) recipeObj.get("productName");
                        if(productName.equals(recipeName)){
                            recipeExist = true;
                            break;
                        }
                    }
                }
                if(!recipeExist) {
                    System.out.println("Type recipe in following format:");
                    System.out.println("Ingredient_name Amount");
                    System.out.println("Type - to exit");
                    String input = "";
                    while (lineScanner.hasNextLine()) {
                        input = lineScanner.nextLine();
                        if(input.equals("-")){
                            break;
                        }
                        String inputArr[] = input.split(" ");
                        ingredientName.add(inputArr[0]);
                        ingredientAmount.add(Integer.parseInt(inputArr[1]));
                    }
                    Object obj = new JSONParser().parse(new FileReader(recipeFile.getName()));
                    JSONArray recipeList;
                    if (recipeFile.length() == 0) {
                        recipeList = new JSONArray();
                    } else {
                        recipeList = (JSONArray) obj;
                    }
                    JSONObject recipe = new JSONObject();
                    JSONObject recipeObj = new JSONObject();
                    recipeObj.put("productName", recipeName);
                    JSONArray ingredientList = new JSONArray();

                    for (int i = 0; i < ingredientName.size(); i++) {
                        JSONObject ingredientObj = new JSONObject();
                        ingredientObj.put("ingredientName", ingredientName.get(i));
                        ingredientObj.put("ingredientAmount", String.valueOf(ingredientAmount.get(i)));
                        ingredientList.add(ingredientObj);
                    }
                    recipeObj.put("ingredients", ingredientList);
                    recipe.put("product", recipeObj);
                    recipeList.add(recipe);
                    FileOutputStream outputStream = new FileOutputStream(recipeFile.getName());
                    byte[] strToBytes = recipeList.toString().getBytes();
                    outputStream.write(strToBytes);
                    System.out.println("Recipe " + recipeName + " was successfully added!");
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public final void removeRecipe(Scanner lineScanner, Owner owner) {
        System.out.println("Type Owner ID:");
        if(lineScanner.nextInt() == owner.getOwnerID()){
            System.out.println("Type name of recipe that you want to remove");
            lineScanner.nextLine();
            recipeName = lineScanner.nextLine();
            boolean removeSuccess = false;
            try {
                if (recipeFile.length() > 0) {
                    Object obj = new JSONParser().parse(new FileReader(recipeFile.getName()));
                    JSONArray recipeList = (JSONArray) obj;
                    for (int i = 0; i < recipeList.size(); i++) {
                        JSONObject recipe = (JSONObject) recipeList.get(i);
                        JSONObject recipeObj = (JSONObject) recipe.get("product");
                        String productName = (String) recipeObj.get("productName");
                        if (productName.equals(recipeName)) {
                            recipeList.remove(i);
                            removeSuccess = true;
                            break;
                        }
                    }
                    FileOutputStream outputStream = new FileOutputStream(recipeFile.getName());
                    byte[] strToBytes = recipeList.toString().getBytes();
                    outputStream.write(strToBytes);
                    if(removeSuccess) {
                        System.out.println("Recipe " + recipeName + " was successfully removed!");
                    }else{
                        System.out.println(recipeName + " does not exist!");
                    }
                }
            }catch (IOException | ParseException e){
                e.printStackTrace();
            }
        }
    }

    public void printAllRecipes(){
        try{
            if(recipeFile.exists() && recipeFile.length() > 0) {
                System.out.println("RECIPE LIST");
                Object obj = new JSONParser().parse(new FileReader(recipeFile.getName()));
                JSONArray recipeList = (JSONArray) obj;
                for (int i = 0; i < recipeList.size(); i++) {
                    JSONObject recipe = (JSONObject) recipeList.get(i);
                    JSONObject recipeObj = (JSONObject) recipe.get("product");
                    String productName = (String) recipeObj.get("productName");
                    System.out.println(productName + ":");
                    JSONArray ingArr = (JSONArray) recipeObj.get("ingredients");
                    for(int j = 0; j < ingArr.size(); j++){
                        JSONObject ing = (JSONObject) ingArr.get(j);
                        String ingName = (String) ing.get("ingredientName");
                        int ingAmount = Integer.parseInt((String) ing.get("ingredientAmount"));
                        System.out.println(ingAmount + " " + ingName + " ");
                    }
                }
            }
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }
    public Recipe(){

    }
}
