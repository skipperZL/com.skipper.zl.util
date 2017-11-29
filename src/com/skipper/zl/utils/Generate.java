package com.skipper.zl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;



public class Generate {
	private String packageName; // 包名
	private String modelName; // modele名
	private String des; // 生成文件存放，目标目录
	
	public Generate(String packageName, String modelName, String des) {
		this.packageName = packageName;
		this.modelName = modelName;
		this.des = des;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public void generateService() {
		String lowModelStr = modelName.toLowerCase();
		StringBuffer serviceStr = new StringBuffer();
		StringBuffer serviceInterface = new StringBuffer();
		// 包名  package cn.xioa.portal.device.service;
		serviceInterface.append("package ")
						.append(packageName)
						.append(".")
						.append(lowModelStr)
						.append(".service;\n");
		// public interface DeviceService  extends IDesignService<Device> {
		serviceInterface.append("public interface ")
						.append(modelName)
						.append("Service  extends IDesignService<")
						.append(modelName)
						.append("> {\n\n}");
		
		// 包名  package cn.xioa.portal.device.service;
		serviceStr.append("package ")
						.append(packageName)
						.append(".")
						.append(lowModelStr)
						.append(".service;\n\n");
		//@Service("meetingService")
		serviceStr.append("@Service(\"")
					.append(lowModelStr)
					.append("Service\")\n");
		// public class MeetingServiceImpl extends AbstractDesignService<Meeting> implements MeetingService{
		serviceStr.append("public class ")
						.append(modelName)
						.append("ServiceImpl extends AbstractDesignService<")
						.append(modelName)
						.append("> implements ")
						.append(modelName)
						.append("Service {\n\n");
		//@Resource(name="meetingDao")
		//private MeetingDao meetingDao;	
		//public IDesignDAO<Meeting> getDAO() {
		//	return meetingDao;
		//}
		serviceStr.append("\t@Resource(name=\"")
					.append(lowModelStr)
					.append("Dao\")\n")
					.append("\tprivate ")
					.append(modelName)
					.append("Dao ")
					.append(lowModelStr)
					.append("Dao;\n\n")
					.append("\tpublic IDesignDAO<")
					.append(modelName)
					.append("> getDAO() {\n")
					.append("\t\treturn ")
					.append(lowModelStr)
					.append("Dao;\n")
					.append("\t}\n")
					.append("}");
		generateFile(serviceInterface, "service", modelName + "Service");
		generateFile(serviceStr, "service", modelName + "ServiceImpl");	
		
	}
	
	public void generateDao() {
		String lowModelStr = modelName.toLowerCase();
		StringBuffer daoStr = new StringBuffer();
		StringBuffer daoInterface = new StringBuffer();
		// 包名 package cn.xioa.portal.device.dao;
		daoInterface.append("package ")
						.append(packageName)
						.append(".")
						.append(lowModelStr)
						.append(".dao;\n");
		// public interface DeviceDao extends IDesignDAO<Device>  {
		daoInterface.append("public interface ")
						.append(modelName)
						.append("Dao  extends IDesignDAO<")
						.append(modelName)
						.append("> {\n\n}");
		
		// 包名  package cn.xioa.portal.device.dao;
		daoStr.append("package ")
				.append(packageName)
				.append(".")
				.append(lowModelStr)
				.append(".dao;\n");
		//@Repository("deviceDao")
		daoStr.append("@Repository(\"")
					.append(lowModelStr)
					.append("Dao\")\n");
		// public class DeviceDaoImpl extends IBatisBaseDAO<Device> implements DeviceDao  {
		daoStr.append("public class ")
						.append(modelName)
						.append("DaoImpl extends IBatisBaseDAO<")
						.append(modelName)
						.append("> implements ")
						.append(modelName)
						.append("Dao {\n\n")
//						public DeviceDaoImpl() {
//							super(Device.class.getSimpleName());
//						}	
						.append("\tpublic ")
						.append(modelName)
						.append("DaoImpl() {\n")
						.append("\t\tsuper(")
						.append(modelName)
						.append(".class.getSimpleName());\n")
						.append("\t}\n")
						.append("\n}");
		generateFile(daoInterface, "dao", modelName + "Dao");
		generateFile(daoStr, "dao", modelName + "DaoImpl");		
	}
	
	/**
	 * superClass 父类名   BaseAction or 工作流相关
	 * 
	 * */
	public void generateAction(String superClass) {
		String lowModelStr = modelName.toLowerCase();
		StringBuffer actionStr = new StringBuffer();
		// 包名  cn.xioa.portal.device.action
		actionStr.append("package ")
						.append(packageName)
						.append(".")
						.append(lowModelStr)
						.append(".action;\n");
		// public class DeviceAction extends BaseAction<Device>{
		actionStr.append("public class ")
						.append(modelName)
						.append("Action extends ")
						.append(superClass)
						.append("<")
						.append(modelName)
						.append("> {\n\n");

//		public DeviceAction() {
//			super(new Device());
//		}
		actionStr.append("\tpublic ")
				.append(modelName)
				.append("Action() {\n")
				.append("\t\tsuper(new ")
				.append(modelName)
				.append("());\n")
				.append("\t}\n\n");
		
//		@Resource(name="deviceService")
//		private DeviceService deviceService;
//		
//		public IDesignService<Device> getService() {
//			return deviceService;
//		}
//
//		public String getPtitle() {
//			return "设备列表";
//		}
		actionStr.append("\t@Resource(name=\"")
					.append(lowModelStr)
					.append("Service\")\n")
					.append("\tprivate ")
					.append(modelName)
					.append("Service ")
					.append(lowModelStr)
					.append("Service;\n\n")
					.append("\tpublic IDesignService<")
					.append(modelName)
					.append("> getService() {\n")
					.append("\t\treturn ")
					.append(lowModelStr)
					.append("Service;\n")
					.append("\t}\n\n")
					.append("\tpublic String getPtitle() {\n")
					.append("\t\treturn \"\";\n")
					.append("\t}\n")
					.append("}");
		
		generateFile(actionStr, "action", modelName + "Action");	
	}
	
	public static void main(String[] args) {
		String packageName = "cn.xioa.portal";
		String modelName = "Comment";
		String des = "D:";
		Generate g = new Generate(packageName, modelName, des);
		g.generateService();
		g.generateDao();
		g.generateAction("BaseAction");
	}
	
	public void generateFile(StringBuffer content,String type, String name) {
		File file = new File(des + "\\" + modelName.toLowerCase() + "\\" + type);
		if (!file.exists())
			file.mkdirs();
		File desFile = new File(des + "\\" + modelName.toLowerCase() + "\\" + type + "\\" + name + ".java");		
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(desFile),"UTF-8");
			out.write(content.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
