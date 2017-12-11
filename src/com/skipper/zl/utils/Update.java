package com.skipper.zl.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Update {
	private String workSpacePath; // 工作空间 E:\\workspace
	private String projectName; //  项目名 gdscloa_new
	private String root; // 根目录名称   如  webapp
	private String patch; // 生成的补丁文件
	private String rootPath; // java文件所在根目录，多个用分号隔开
	private int count = 0; // 成功导入数
	private int errorCount = 0; // 导入出错数
	
	public String getWorkSpacePath() {
		return workSpacePath;
	}

	public void setWorkSpacePath(String workSpacePath) {
		this.workSpacePath = workSpacePath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getPatch() {
		return patch;
	}

	public void setPatch(String patch) {
		this.patch = patch;
	}
	
	

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	/**
	 * 工作空间  项目  根目录  补丁路径  项目java文件目录：多个用分号隔开
	 * @param workSpacePath 
	 * @param projectName
	 * @param root
	 * @param patch
	 * @param rootPath
	 */
	public Update(String workSpacePath, String projectName, String root, String patch, String rootPath) {
		this.workSpacePath = workSpacePath;
		this.projectName = projectName;
		this.root = root;
		this.patch = patch;
		this.rootPath = rootPath;
	}
	
	/**
	 * @return 返回路径格式为  根路径 + 目录  不包括项目所在路径
	 */
	private List<String> getFileList() {
		List<String> list = new ArrayList<String>();
		FileInputStream fis = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(getPatch());
			br = new BufferedReader(new InputStreamReader(fis,"utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("Index:")) {
					line = line.substring("Index:".length()).trim();	
					if (getRootPath() != null) {
						String[] roots = getRootPath().split(";");
						for (String r : roots) {
							if (line.startsWith(r + "/")) {// 处理 src目录下的文件
								line = root + "\\WEB-INF\\classes" + line.substring(r.length());
								if (line.endsWith(".java")) // 处理class文件
									line = line.substring(0, line.length() - 4) + "class";
								
							}
						}
					}
					list.add(line);
				}
			}
		} catch (Exception e) {
			System.out.print("补丁文件读取异常！");
		} finally {
			try {
				if (br != null)
					br.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}
	
	/**
	 * @param files  patch导出的路径
	 * @param target 要导入的路径
	 */
	private void CopyFile(List<String> files, String target) {
		StringBuffer desFileFullPath = new StringBuffer();
		String proPath = this.workSpacePath + "\\" + this.projectName + "\\";
		for (int i = 0; i < files.size(); i++) {
			desFileFullPath.append(target).append("\\").append(files.get(i));
			String desPath = "";
			if (desFileFullPath.lastIndexOf("/") != -1) 
				desPath = desFileFullPath.substring(0, desFileFullPath.lastIndexOf("/"));
			else 
				desPath = desFileFullPath.substring(0, desFileFullPath.lastIndexOf("\\"));
			File dFile = new File(desPath); // 目标文件目录
			if (!dFile.exists())
				dFile.mkdirs();
			copyFile(proPath + files.get(i), desFileFullPath.toString());
			if (files.get(i).endsWith(".class"))
				copyInnerClass(files.get(i), target);
			desFileFullPath.setLength(0);
		}
	}
	
	/**
	 * 复制 innerclass
	 * @param filePath 路径 不包括 项目路径
	 * @param target 目标目录
	 */
	private void copyInnerClass(String filePath, String target) {
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length() - 6);
		String root = this.workSpacePath + "\\" + this.projectName + "\\";
		String sourcePath = root + filePath.substring(0, filePath.lastIndexOf("/")) + "/";
		File packFile = new File(sourcePath);
		if (packFile.isDirectory()) {
			String[] files = packFile.list();
			for (String f : files) {
				if (f.endsWith(".class") && f.startsWith(fileName+"$")) {
					copyFile(sourcePath + f, target + "\\" + filePath.substring(0, filePath.lastIndexOf("/")) + "/" + f);
				}
			}
		}
		
	}
	
	private void copyFile(String source, String target) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		System.out.println("source = " + source + "\ntarget = " + target);
		try {
			bis = new BufferedInputStream(new FileInputStream(new File(source)));
			bos = new BufferedOutputStream(new FileOutputStream(new File(target)));
			byte[] b = new byte[1024 * 5]; 
			int len;  
            while ((len = bis.read(b)) != -1) {  
            	bos.write(b, 0, len);  
            }  
            bos.flush(); 
            System.out.println( " 第  " + (++count) + " 导入成功！");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println( " 第 " + (++errorCount) + " 导入失败！");
		} finally {

			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// 省残联
		/*String ws = "F:\\wzl\\workspaceForEclipse4.7";
		String pn = "gdscloa_new";
		String root = "webapp";*/
		
/*		// 省残联
		String ws = "F:\\wzl\\eclipseworkplace";
		String pn = "gdscloa_new";
		String root = "webapp";*/
		
	/*	// win10 会议系统
		String ws = "F:\\wzl\\hbuilderworkplace";
		String pn = "智能会议";  
		String root = "";*/
		
		// 省残联短信平台 test
/*		String ws = "F:\\wzl\\workspaceForEclipse4.7";
		String pn = "CLMSG";
		String root = "WebContent";*/
		
		// 会议系统
		String ws = "F:\\wzl\\workspaceForEclipse4.7";
		String pn = "MeetingService";
		String root = "WebContent";
		
		String patch = "D:\\patch.txt";
		String target = "D:\\update"; 
		String rootPath = "src;system";
		Update update = new Update(ws, pn, root, patch, rootPath);
		update.CopyFile(update.getFileList(), target);
	}
}