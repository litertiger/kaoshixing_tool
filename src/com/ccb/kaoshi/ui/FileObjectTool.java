package com.ccb.kaoshi.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class FileObjectTool {

	private static Logger log = Logger.getLogger(FileObjectTool.class);
	// 缓存保存信息
		public static void writeObjectToFile(Object obj, String filename) {
			File file = new File(filename);
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				ObjectOutputStream objOut = new ObjectOutputStream(out);
				objOut.writeObject(obj);
				objOut.flush();
				objOut.close();
				System.out.println("write object success!");
			} catch (IOException e) {
				System.out.println("write object failed");
				e.printStackTrace();
			}
		}

		public static Object readObjectFromFile(String filename) {
			long start = System.currentTimeMillis();
			Object temp = null;
			File file = new File(filename);
			FileInputStream in;
			try {
				in = new FileInputStream(file);
				ObjectInputStream objIn = new ObjectInputStream(in);
				temp = objIn.readObject();
				objIn.close();
				System.out.println("read object success!");
			} catch (IOException e) {
				System.out.println("read object failed");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();

			log.info("读取对象处理完时间：" + (end - start) + "ms");
			return temp;
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
