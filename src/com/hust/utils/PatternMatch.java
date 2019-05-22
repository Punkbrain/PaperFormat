package com.hust.utils;

/**
 * ������ʽ
 * @author zheng
 *
 */
public class PatternMatch {

	//ð��������ʽ    ƥ��ʾ��        2014:
	public static final String findNumAndColon = "\\d{4}:";
	
	//ƥ��ʾ��    3): 
	public static final String findNumAndBracketsColon = "\\d\\):";
	
	//ƥ�������ַ�   
	public static final String findChineseCharacters = "[\u4e00-\u9fa5]";
	
	//ƥ��ʾ����  1-2
	public static final String findNumToNum = "\\d{1}-\\d{1}";
	
	
}
