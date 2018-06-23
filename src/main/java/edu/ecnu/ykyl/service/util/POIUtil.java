package edu.ecnu.ykyl.service.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class POIUtil {
	
	@Test
	public void test(){
		String str ="1.0";
		double d = Double.parseDouble("1.0");
//		int parseInt = Integer.parseInt("1.0");
		System.out.println((int)d);
		
	}
	
	@Test
    public void showExcel2() throws Exception {
		//题目绝对id，题干，题支(ABCD),标准答案，题目分析，知识点，题目知识点id，题目在该知识点中的相对编号
		String absId = null;
		String quesContent = null;
		String OptA = null;
		String OptB = null;
		String OptC = null;
		String OptD = null;
		String stdAns = null;
		String quesAnalysis = null;
		String kpStr = null;
		String kpid = null;
		String relativeId = null;
		String temp = null;
		//获取知识点表t_knowledge_point中所有的的知识点
		String[] knowledgePoints = new String[25];
		
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("C:/Users/Carl_Hugo.DESKTOP-FJAE3QC/Desktop/题目导入/题目整理.xls")));
        HSSFSheet sheet = null;
        int i = workbook.getSheetIndex("题目表final"); // sheet表名
        sheet = workbook.getSheetAt(i);
        //外层循环 一行一行的扫描
        //getLastRowNum 获取最后一行的行标
        for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
        	String[] tempArr = new String[11];
            HSSFRow row = sheet.getRow(j);
            if (row != null) {
                for (int k = 0; k < row.getLastCellNum(); k++) {
                	// getLastCellNum
                	// 是获取最后一个不为空的列是第几个
                    if (row.getCell(k) != null) { // getCell 获取单元格数据
                        System.out.print(temp = row.getCell(k) + "\t");
                        switch(k){
	                        case 0:absId = temp;break;//题目的绝对id
	                        case 1:quesContent = temp;break;//题目内容
	                        case 2:OptA = temp;break;//A
	                        case 3:OptB = temp;break;//B
	                        case 4:OptC = temp;break;//C
	                        case 5:OptD = temp;break;//D
	                        case 6:stdAns = temp.substring(0, 1);break;//答案
	                        case 7:quesAnalysis = temp;break;//题目分析
	                        case 8:kpStr = temp;break;//知识点name
	                        case 9:kpid = temp;break;//知识点id
	                        case 10:relativeId = temp;break;//题目在该知识点中的相对id编号
                        }
                        tempArr[k]=temp;
                        //将该数组中除了2.3.4.5这四个索引的值封装到t_question表中
//                        insertQuestion(tempArr);
                        //将数组中2.3.4.5这四个索引的值（ABCD）封装，并传入当前question的绝对id
//                        insertQuestionOption(tempArr,tempArr[0]);
                    } else {
                        System.out.print("\t");
                    }
                }
            }
            System.out.println("");
        }
    }

}
