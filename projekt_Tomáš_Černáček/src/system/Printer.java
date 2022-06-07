package system;

import warehouse.Warehouse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public final class Printer {

    public void print(Warehouse warehouse){
        try{
            File f = new File("orderList.json");
            if(f.exists()){
                Object obj = new JSONParser().parse(new FileReader("orderList.json"));
                JSONArray orderList = (JSONArray) obj;
                for(int i = 0; i < orderList.size(); i++) {
                    JSONObject order = (JSONObject) orderList.get(i);
                    JSONObject orderObj = (JSONObject) order.get("order");
                    String orderState = (String) orderObj.get("orderState");
                    String fileName = (String) orderObj.get("fileName");
                    if(orderState.equals("COMPLETE")){
                        Document doc = new Document();
                        PdfWriter.getInstance(doc, new FileOutputStream(fileName + "_SOLVED.pdf"));
                        doc.open();
                        PdfPTable table = new PdfPTable(2);
                        String wInfo = warehouse.warehouseName + "\n" + warehouse.getStreet() + "\n"+ warehouse.getPSC() + " " + warehouse.getTownName() + "\n\n\n";
                        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
                        Paragraph warehouseInfo = new Paragraph(wInfo, font);
                        doc.add(warehouseInfo);
                        Paragraph paragraph = new Paragraph("Order details", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
                        JSONArray productList = (JSONArray) orderObj.get("products");
                        Font cellFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
                        PdfPCell productCell = new PdfPCell(new Paragraph("Product", new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
                        PdfPCell productAmountCell = new PdfPCell(new Paragraph("Amount", new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
                        table.addCell(productCell);
                        table.addCell(productAmountCell);
                        for(int j = 0; j < productList.size(); j++){
                            JSONObject productObj = (JSONObject) productList.get(j);
                            String productName = (String) productObj.get("productName");
                            int productAmount = Integer.parseInt((String) productObj.get("amount"));
                            PdfPCell cell1 = new PdfPCell(new Paragraph(productName, cellFont));
                            PdfPCell cell2 = new PdfPCell(new Paragraph(String.valueOf(productAmount), cellFont));
                            table.addCell(cell1);
                            table.addCell(cell2);
                        }
                        table.setHorizontalAlignment(Element.ALIGN_LEFT);
                        paragraph.add(table);
                        doc.add(paragraph);
                        Paragraph currentTime = new Paragraph(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()), font);
                        doc.add(currentTime);
                        doc.close();
                        System.gc();
                    }
                }
            }
        } catch (IOException | ParseException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public Printer(){

    }
}
