package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import exception.IllegalPasswordException;
import exception.ImportUserException;
import exception.ImportUserException.Type;
import database.gas.RegisterUserGas;

public class ExcelReader implements AutoCloseable{
	Workbook workBook;
	public ExcelReader(String path) throws EncryptedDocumentException, InvalidFormatException, IOException {
		InputStream inp = new FileInputStream(path);
		workBook = WorkbookFactory.create(inp);
		inp.close();
	}
	
	interface UserPropertySetter{
		void set(RegisterUserGas rug, Cell cell);
	}
	
	public List<RegisterUserGas> getUserGases() throws ImportUserException{
		ArrayList<RegisterUserGas> gs = new ArrayList<RegisterUserGas>();
		Sheet sheet = workBook.getSheetAt(0);
		Row r1 = sheet.getRow(0);
		int idCol = -1;
		int pwCol = -1;
		HashMap<Integer, UserPropertySetter> psmap = new HashMap<Integer, UserPropertySetter>();
		
		for(Cell cell : r1) {
			switch(cell.getStringCellValue()) {
			case "ID":
				idCol = cell.getColumnIndex();
				break;
			case "密码":
				pwCol = cell.getColumnIndex();
				break;
			case "姓名":
				psmap.put(cell.getColumnIndex(), (u, c) -> u.setName(stringFromCell(c)));
				break;
			default:
				break;
			}
		}
		
		if(idCol == -1) {
			throw new ImportUserException(ImportUserException.Type.NO_ID);
		}else if(pwCol == -1) {
			throw new ImportUserException(ImportUserException.Type.NO_PASSWORD);
		}
		
		for(int i = 1; i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Long id = longFormCell(row.getCell(idCol));
			if(id != null) {
				RegisterUserGas rug;
				try {
					rug = new RegisterUserGas(id, stringFromCell(row.getCell(pwCol)));
					for(int index : psmap.keySet()) {
						Cell cell = row.getCell(index);
						psmap.get(index).set(rug, cell);
					}
					gs.add(rug);
				} catch (IllegalPasswordException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
		return gs;
	}


	@Override
	public void close() throws Exception {
		workBook.close();
	}
	
	static Long longFormCell(Cell cell)  throws RuntimeException{
		switch(cell.getCellTypeEnum()) {
		case NUMERIC:
			return ((Double)cell.getNumericCellValue()).longValue();
		case STRING:
			return Long.parseLong(cell.getStringCellValue());
		case BLANK:
			return null;
		default:
			throw new RuntimeException("Can not get Long value from this cell");
		}
	}
	
	static String stringFromCellNTL(Cell cell) throws RuntimeException{
		switch(cell.getCellTypeEnum()) {
		case NUMERIC:
			return ((Long)((Double)cell.getNumericCellValue()).longValue()).toString();
		case STRING:
			return cell.getStringCellValue();
		case BLANK:
			return null;
		default:
			throw new RuntimeException("Can not get String value from this cell");
		}
	}
	
	static String stringFromCell(Cell cell) throws RuntimeException{
		switch(cell.getCellTypeEnum()) {
		case NUMERIC:
			return ((Double)cell.getNumericCellValue()).toString();
		case STRING:
			return cell.getStringCellValue();
		case BLANK:
			return null;
		default:
			throw new RuntimeException("Can not get String value from this cell");
		}
	}
	
}
