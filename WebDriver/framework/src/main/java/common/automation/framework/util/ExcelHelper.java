package common.automation.framework.util;

import java.io.FileInputStream;
import java.util.Calendar;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
	private FileInputStream fis = null;
    private Workbook workbook = null;
    private Sheet sheet = null;
    private Row row = null;
    private Cell cell = null;
    
    private int headerRowNum = 1;
    private int firstColNum = 0;
    
	public ExcelHelper(String path, int headerIndex, int firstColNum){
		try {
			fis = new FileInputStream(path);
			
			if (path.toLowerCase().endsWith("xlsx") == true) {
				workbook = new XSSFWorkbook(fis);
			} else if (path.toLowerCase().endsWith("xls") == true) {
				workbook = new HSSFWorkbook(fis);
			}
			
			sheet = workbook.getSheetAt(0);
			fis.close();
			headerRowNum = headerIndex;
			this.firstColNum = firstColNum;
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
    
    // returns the data from a cell
    public String getCellData(String sheetName, int colNum, int rowNum){
    	try{
    		if(rowNum <= 0)
    			return "";

    		int index = workbook.getSheetIndex(sheetName);

    		if(index == -1)
    			return "";
    		
    		sheet = workbook.getSheetAt(index);
    		row = sheet.getRow(rowNum-1);
    		if(row == null)
    			return "";
    		cell = row.getCell(colNum);
    		if(cell==null)
    			return "";

    		if(cell.getCellType() == Cell.CELL_TYPE_STRING)
    			return cell.getStringCellValue();
    		else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA ){
    			double cellValue = cell.getNumericCellValue();
    			String cellText;
    			//Checking if value is integer
    			if((cellValue == Math.floor(cellValue)) && !Double.isInfinite(cellValue)){
    				cellText = String.valueOf((int) cellValue);
    			}
    			else{
    				cellText = String.valueOf(cellValue);
    			}
    			
    			if (HSSFDateUtil.isCellDateFormatted(cell)) {
    				// format in form of M/D/YY
    				double d = cell.getNumericCellValue();

    				Calendar cal = Calendar.getInstance();
    				cal.setTime(HSSFDateUtil.getJavaDate(d));
    				cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
    				cellText = cal.get(Calendar.MONTH) + 1 + "/" +
    						cal.get(Calendar.DAY_OF_MONTH) + "/" +
    						cellText;
    			}
    			return cellText;
    		}else if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
    			return "";
    		else 
    			return String.valueOf(cell.getBooleanCellValue());
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
    	}
    }
	
	// returns the data from a cell
    public String getCellData(String sheetName, String colName, int rowNum){
    	try{
    		if(rowNum <= 0)
    			return "";
    		
    		int colNum = getColNum(sheetName, colName);
    		/*int index = workbook.getSheetIndex(sheetName);
    		if(index == -1)
    			return "";

    		int colNum = -1;

    		sheet = workbook.getSheetAt(index);
    		row = sheet.getRow(0);
    		for(int i=0; i<row.getLastCellNum(); i++){
    			if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())){
    				colNum = i;
    				break;
    			}
    		}*/
    		if(colNum == -1)
    			return "";
    		
    		return getCellData(sheetName, colNum, rowNum);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return "row " + rowNum + " or column " + colName + " does not exist in xls";
    	}
    }
	
	// returns the data from a cell
    public String getCellData(String sheetName, String sectionName, String colName, int rowNum){
    	try{
    		if(rowNum <= 0)
    			return "";
    		
    		int colNum = getColNum(sheetName, sectionName, colName);
    		
    		if(colNum == -1)
    			return "";
    		
    		return getCellData(sheetName, colNum, rowNum);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return "row " + rowNum + " or column " + colName + " does not exist in xls";
    	}
    }
	
	// returns the data from a cell found by a value in another column
    public String getCellData(String sheetName, String colName, String valueColName, String value){
    	try{
    		int rowNum = getRowNumByValue(sheetName, valueColName, value);
    		if(rowNum == -1)
    			return "";
    		
    		return getCellData(sheetName, colName, rowNum);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return "";
    	}
    }
	
	// returns the data from a cell found by a value in another column
    public String getCellData(String sheetName, String sectionName, String colName, String valueColName, String value){
    	try{
    		int rowNum = getRowNumByValue(sheetName, valueColName, value);
    		if(rowNum == -1)
    			return "";
    		
    		return getCellData(sheetName, sectionName, colName, rowNum);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return "";
    	}
    }
	
	//returns column number for given column name
    public int getColNum(String sheetName, String colName){
    	try{
    		int index = workbook.getSheetIndex(sheetName);
    		if(index == -1)
    			return -1;

    		int colNum = -1;

    		sheet = workbook.getSheetAt(index);
    		row = sheet.getRow(headerRowNum - 1);
    		for(int i = firstColNum; i<row.getLastCellNum(); i++){
    			if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())){
    				colNum = i;
    				break;
    			}
    		}
    		return colNum;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return -1;
    	}
    }
	
	//returns column number for given column name
    public int getColNum(String sheetName, String sectionName, String colName){
    	//Searching for fist column index in the specified section(previous to header row)
    	headerRowNum --;
    	int tmpFirstColNum = firstColNum;
    	firstColNum = getColNum(sheetName, sectionName);
    	headerRowNum ++;
    	
    	int colNum = getColNum(sheetName, colName);
    	firstColNum = tmpFirstColNum;
    	
    	return colNum;
    }
	
	//returns column number for given column name
    public int getRowNumByValue(String sheetName, String colName, String value){
    	try{
    		int index = workbook.getSheetIndex(sheetName);
    		if(index == -1)
    			return -1;

    		int colNum = getColNum(sheetName, colName);
    		
    		int numofrows = getNumOfNotEmptyRows(sheetName);
    	    String cellData;
    	    
    	    for(int rowNum = headerRowNum + 1; rowNum <= numofrows+1; rowNum++){
    	    	cellData = getCellData(sheetName, colNum, rowNum);
    	    	if(cellData.equals(value)){
    	    		return rowNum;
    	    	}
    	    }
    	    
    		return -1;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return -1;
    	}
    }
    
    public int getNumOfNotEmptyCols(String sheetName){
    	int numofcols = 0;
		while(!getCellData(sheetName, numofcols, 1).equals("")){
	        numofcols++;
	    }
		return numofcols;
    }
    
    public int getNumOfNotEmptyRows(String sheetName){
    	int firstDataRowNum = headerRowNum + 1;
    	int numofrows = 0;
    	String cellData = getCellData(sheetName, firstColNum, firstDataRowNum);
	    while(!cellData.equals("")){
	        numofrows++;
	        cellData = getCellData(sheetName, firstColNum, firstDataRowNum + numofrows);
	    }
		return numofrows;
    }
}
