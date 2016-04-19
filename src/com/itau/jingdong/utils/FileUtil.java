package com.itau.jingdong.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static String sdPath = Environment.getExternalStorageDirectory()
			.getPath() + "/avert";

	/**
	 * @param path
	 * @return
	 */
	public static int getFileCounts(String path) {
		int lens = 0;
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			File[] fileItems = file.listFiles();
			if (fileItems != null) {
				for (int i = 0; i < fileItems.length; i++) {
					File item = fileItems[i];
					if (item.isFile()) {
						lens++;
					} else if (item.isDirectory()) {
						File[] childFiles = item.listFiles();
						if (childFiles != null) {
							lens += childFiles.length;
						}
					}
				}
			}
		}
		return lens;
	}

	/**
	 * 保存bitmap图像
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 */
	public static void saveFile(Bitmap bm) throws IOException {
		File dirFile = new File(sdPath);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(sdPath );
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		S.p("文件保存成功..............");
		bos.flush();
		bos.close();
	}

	static Bitmap bitmap=null;
	public static Bitmap loadFile() throws IOException{
		File file=new File(sdPath);
		if (file.canRead()) {
		            S.p("文件可读....");
		        }
		        if (file.canWrite()) {
		        	 S.p("文件可写.....");
		        }
		        File mFile=new File(sdPath);
		        if (mFile.exists()) {
		            bitmap=BitmapFactory.decodeFile(sdPath);
		            return bitmap;
		        }
				return bitmap;        
		
	}
	/**
	 * @param name
	 * @return
	 */
	public static String readFile(String name) {
		FileInputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		// File file = new File(rootPath+name);
		File file = new File(name);
		if (!file.exists()) {
			return null;
		}
		StringBuffer res = new StringBuffer();
		try {
			// is = new FileInputStream(rootPath+name);
			is = new FileInputStream(name);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String x = null;
			while ((x = br.readLine()) != null) {
				res.append(x);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res.toString();
	}

	/**
	 * @param filename
	 * @param content
	 */
	public static void writeToSd(String filename, String content) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(content);
			bw.flush();
			osw.flush();
			bw.close();
			osw.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public static void copyFile(String src, String dst) throws IOException {
		FileInputStream fis = new FileInputStream(new File(src));
		BufferedInputStream inBuff = new BufferedInputStream(fis);
		FileOutputStream fos = new FileOutputStream(new File(dst));
		BufferedOutputStream outBuff = new BufferedOutputStream(fos);
		byte[] b = new byte[1024];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		outBuff.flush();
		outBuff.close();
		fos.close();
		inBuff.close();
		fis.close();
	}

	/**
	 * 删除文件夹
	 * 
	 * @param file
	 */
	public static void deleteFolder(File file) {
		if (file != null && file.exists() && file.isDirectory()) {
			for (File item : file.listFiles()) {
				item.delete();
			}
			file.delete();
		}
	}

	public static void deleteFiles(String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			file.delete();
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int j = 0; j < filelist.length; j++) {
				File filessFile = new File(path + File.separator + filelist[j]);
				if (!filessFile.isDirectory()) {
					filessFile.delete();
				} else if (filessFile.isDirectory()) {
					// 遞歸調用
					deleteFiles(path + File.separator + filelist[j]);
				}
			}
			file.delete();
		}
	}

	/**
	 * @Title: fileIsExists
	 * @Description: 判断文件是否存在
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean fileIsExists(File file) {
		if (!file.exists()) {
			return false;
		}
		return true;
	}

	private static String FormetFileSize(double fileSize) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileSize == 0) {
			return wrongSize;
		}
		if (fileSize < 1024) {
			fileSizeString = df.format(fileSize) + "B";
		} else if (fileSize < 1048576) {
			fileSizeString = df.format(fileSize / 1024) + "KB";
		} else if (fileSize < 1073741824) {
			fileSizeString = df.format(fileSize / (1024 * 1024)) + "MB";
		} else {
			fileSizeString = df.format(fileSize / (1024 * 1024 * 1024)) + "GB";
		}
		return fileSizeString;
	}
}