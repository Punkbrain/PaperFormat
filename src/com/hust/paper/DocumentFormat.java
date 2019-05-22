package com.hust.paper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hust.utils.PatternMatch;

/**
 * �������׸�ʽ����Ӣ������Ϊ������������Ϊ����
 * ��Ҫ������[J],[D],[C]
 * 
 * @author zheng 
 * 
 * Ҫ��1����Ӣ�ľ��Ϊ�ָ�������Ϊauthor��title���ڿ��������������� 
 * 2��author�������Զ��ŷָ������������á�, et al����ʾ�����ж��ź��пո� 
 * 3��title��[J]ɾ�� 4���ڿ����ڿ�������ţ�ҳ��֮����~��
 * 
 * ע�⣺����Ŀֻ���ôӹȸ�ѧ�����ٶ�ѧ����֪�����������ס�
 * 
 * ��������[D]��Ӣ���ڿ�[J]��ʽ������ͬ��
 * [D]��Ҫ��ӡ�: [˶ʿ/��ʿѧλ����]. ����������ݺ���Ҫ�б�㡣
 * 
 * ���б�����ΪӢ�ĸ�ʽ����ÿ��������һ���ո�
 * 
 * ��Ի���[C]���ã���Ҫ��ӻ���ص㣬ʱ���Լ�������(���ֶ�)��
 * 
 * Ŀǰ�ó������������⣺1�����硰3):����ð�ź�������û�пո�2��[C]�������޸ĺ��"in: "�����׶�ո�* 
 */
public class DocumentFormat {

	public static void main(String[] args) {

		while (true) {
			/*System.out.println("��������Ҫ�޸ĵ�Ӣ���������ã�");
			Scanner sc = new Scanner(System.in);
			String string = sc.nextLine();
			System.out.println("���������Ϊ��" + string);*/
			String str = null;
			BufferedReader bre = null;
			
			System.out.println("������Ҫ����txt�ļ�·�������ƣ�����ʽʾ����path/paper.txt��"); // C:/Users/zheng/Desktop/paper.txt
			Scanner sc = new Scanner(System.in);
			String readPath = sc.nextLine();
			System.out.println("������Ҫд���txt�ļ�·�������ƣ�");   // C:/Users/zheng/Desktop/result.txt
			String writePath = sc.nextLine();
			boolean flag = false;
			System.out.println("�Ƿ񸲸�д���ļ��������ݣ�  1. ����    2. ������   ��������1����2��");
			String f = sc.nextLine();
			if (f.equals("1")) {
				flag = false;
			}else if (f.equals("2")) {
				flag = true;
			}else {
				System.out.println("�밴Ҫ�����룡");
				System.exit(0);
			}
			try {
				 bre = new BufferedReader(new InputStreamReader(new FileInputStream(readPath),"gbk"));
//				 FileWriter fw = new FileWriter(writePath, flag);
				 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writePath),"gbk"));
				while ((str = bre.readLine()) != null) // �ж����һ�в����ڣ�Ϊ�ս���ѭ��
				{
					System.out.println(str);// ԭ���������������
					
					String newpaper = processPaper(str);
					bw.write(newpaper+"\r\n");
					
				}
				
				bw.flush();
				bre.close();
				bw.close();
//				fw.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static String processPaper(String str) {
		
		libtex libtex1 = new libtex();
		String[] strlist = str.split("\\.");
//		for (String stri : strlist) {
//			System.out.println(stri);
//		}
		libtex1.setAuthor(strlist[0].trim());
		String newstring1 = null;
		//�ж��Ƿ�Ϊ����
		Pattern p = Pattern.compile(PatternMatch.findChineseCharacters);
        Matcher m = p.matcher(str);
		if (m.find() && strlist[1].contains("[D]")) {
			newstring1 = strlist[1].substring(0, strlist[1].length() - 3) + ": [˶ʿѧλ����]";
			libtex1.setJounal(strlist[2].trim() + ". ", "D");
		} else {
			// ����ڿ������ﺬ��[J]ɾ��
			if (strlist[1].contains("[J]")) {
				newstring1 = strlist[1].substring(0,
						strlist[1].length() - 3);
				libtex1.setJounal(strlist[2].trim(), "J");
			} else if (strlist[1].contains("[C]")) {
				libtex1.setIsjounal(false);
				newstring1 = strlist[1].replace("[C]//", ". in: ");
				libtex1.setJounal(strlist[2].trim(), "C");
			} else {
				newstring1 = strlist[1];
				libtex1.setJounal(strlist[2].trim(),"");
			}
		}
		
//		System.out.println(newstring1);
		libtex1.setTitle(newstring1.trim());
		
		System.out.println("�޸ĺ�Ľ��Ϊ��");
		System.out.println(libtex1.merge());
		
		
		return libtex1.merge();
	}

}
