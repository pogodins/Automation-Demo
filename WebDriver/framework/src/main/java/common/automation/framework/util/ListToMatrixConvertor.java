package common.automation.framework.util;

import java.util.List;

public class ListToMatrixConvertor {
	public static Object[][] convertListToMatrix(List<?> list){
		Object[][] matrix = new Object[list.size()][1];
		int i = 0;
		
		for(Object obj: list){
			matrix[i][0] = obj;
			i++;
		}
		
		return matrix;
	}
}
