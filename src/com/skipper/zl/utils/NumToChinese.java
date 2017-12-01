package com.skipper.zl.utils;

public class NumToChinese {
	private static String[] chArray = {"零","一","二","三","四","五","六","七","八","九"};
	private static String[] chUnitSection = {"","万","亿","万亿","亿亿"};
	private static String[] chUnitArray = {"","十","百","千"};
	
	public static String numToChinese(int input) {
		boolean addZero = false;
		int chuPos = 0;
		String sectionStr = "";
		String chStr = "";
		if (input == 0) 
			return chArray[input];
		while(input > 0) {
			int section = input % 10000;
			if (addZero) {
				chStr = chArray[0] + chStr;
			}
			sectionStr = sectionToChinese(section);
			sectionStr += (section != 0) ? chUnitSection[chuPos] : chUnitSection[0];
			chStr = sectionStr + chStr;
			addZero = (section < 1000) && (section > 0);
			chuPos++;
			input = input/10000;
		}
			
		return chStr;
	}
	
	private static String sectionToChinese(int section) {
		boolean addZero = false;
		String chStr = "";
		int unitPos = 0;
		while (section > 0) {
			int num = section % 10;
			if (num == 0) {
				if (addZero) {
					chStr = chArray[0] + chStr;
					addZero = false;
				}
			} else {
				addZero = true;
				chStr = chArray[num] + chUnitArray[unitPos] + chStr;
			}	
			section = section/10;
			unitPos++;
			
		}		
		return chStr;
	}
	
	public static void main(String[] args) {
		System.out.println(NumToChinese.numToChinese(1005));
	}
	
}
