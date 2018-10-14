package e.smwhitt.myco2;

import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;

public class Car_Emissions {

    public Map<String, Map<String, Map<String, ArrayList<Double>>>> createArray(){
        Map<String, Map<String, Map<String, ArrayList<Double>>>> carEmissions = new HashMap<>();

        final String file = "/Users/david/Downloads/vehicles.xls";
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();
            System.out.println("rows" + rows);
            int cols = 10; // No of columns
            int tmp = 0;

            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            for(int r = 1; r < rows; r++) {
                row = sheet.getRow(r);
                if(row != null) {
                        HSSFCell cell1 = row.getCell(0);
                        if(carEmissions.get(cell1.getStringCellValue()) == null) {
                            carEmissions.put(cell1.getStringCellValue(), new HashMap<String, Map<String, ArrayList<Double>>>());
                        }
                        HSSFCell cell2 = row.getCell(1);
                        if(carEmissions.get(cell1.getStringCellValue()).get(cell2.toString()) == null) {
                            carEmissions.get(cell1.getStringCellValue()).put(cell2.toString(), new HashMap<String, ArrayList<Double>>());
                        }
                        HSSFCell cell3 = row.getCell(2);
                        if(carEmissions.get(cell1.getStringCellValue()).get(cell2.toString()).get(cell3.toString()) == null) {
                            carEmissions.get(cell1.getStringCellValue()).get(cell2.toString()).put(cell3.toString(), new ArrayList<Double>());

                        HSSFCell cell4 = row.getCell(3);
                        double co2 = cell4.getNumericCellValue();
                        HSSFCell cell5 = row.getCell(4);
                        if(cell5.getNumericCellValue() != 0) {
                            co2 = (co2 + cell5.getNumericCellValue()) / 2;
                        }
                        carEmissions.get(cell1.getStringCellValue()).get(cell2.toString()).get(cell3.toString()).add(co2);

                        HSSFCell cell6 = row.getCell(5);
                        double mpg = cell6.getNumericCellValue();
                        HSSFCell cell7 = row.getCell(6);
                        if(cell7.getNumericCellValue() != 0) {
                            mpg = (mpg + cell7.getNumericCellValue()) / 2;
                        }
                        carEmissions.get(cell1.getStringCellValue()).get(cell2.toString()).get(cell3.toString()).add(mpg);

                        HSSFCell cell8 = row.getCell(7);
                        double annualFuelCost = cell8.getNumericCellValue();
                        HSSFCell cell9 = row.getCell(8);
                        if(cell9.getNumericCellValue() != 0) {
                            annualFuelCost = (annualFuelCost + cell9.getNumericCellValue()) / 2;
                        }
                        carEmissions.get(cell1.getStringCellValue()).get(cell2.toString()).get(cell3.toString()).add(annualFuelCost);

                        HSSFCell cell10 = row.getCell(9);
                        carEmissions.get(cell1.getStringCellValue()).get(cell2.toString()).get(cell3.toString()).add(cell10.getNumericCellValue());
                    }
                }
            }

        } catch(Exception ioe) {
            ioe.printStackTrace();
        }
        System.out.println(carEmissions.get("Chevrolet").get("S10 Pickup 4WD").get("1994.0").toString());
        return carEmissions;
    }

    public Map<String, Map<String, ArrayList<Double>>> SearchMake(Map<String, Map<String, Map<String, ArrayList<Double>>>> allCars, String carMake){
        return allCars.get(carMake);
    }

    public Map<String, ArrayList<Double>> SearchModel(Map<String, Map<String, ArrayList<Double>>> carMakes, String carModel){
        return carMakes.get(carModel);
    }

    public ArrayList<Double> SearchYear(Map<String, ArrayList<Double>> carModels, String carYear){
        return carModels.get(carYear);
    }
}
