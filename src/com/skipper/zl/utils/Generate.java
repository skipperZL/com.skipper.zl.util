package com.skipper.zl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.List;



public class Generate {
	private String packageName; // 包名
	private String modelName; // modele名
	private String des; // 生成文件存放，目标目录
	private String tableName; // 表名
	private Class modelClass; // class
	private String pk; // primary key
	private String id; // model id
	private String sqlUtils; // cn.prpsdc.base.model.SQLUtils 
	
	public Generate(String packageName, String modelName, String des, String tableName, Class modelClass, String pk, String id, String sqlUtils) {
		this.packageName = packageName;
		this.modelName = modelName;
		this.des = des;
		this.tableName = tableName;
		this.modelClass = modelClass;
		this.pk = pk;
		this.id = id;
		this.sqlUtils = sqlUtils;
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
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Class getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class modelClass) {
		this.modelClass = modelClass;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSqlUtils() {
		return sqlUtils;
	}

	public void setSqlUtils(String sqlUtils) {
		this.sqlUtils = sqlUtils;
	}

	public String getFullQualifyName() {
		return packageName + "." + modelName.toLowerCase() + ".model." + modelName;
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
		generateFile(serviceInterface, "service", modelName + "Service", "java");
		generateFile(serviceStr, "service", modelName + "ServiceImpl", "java");	
		
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
		generateFile(daoInterface, "dao", modelName + "Dao", "java");
		generateFile(daoStr, "dao", modelName + "DaoImpl", "java");		
	}
	
	/**
	 * superClass 父类名   BaseAction or 工作流相关 OfficeWorkFlowAction
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
		
		generateFile(actionStr, "action", modelName + "Action", "java");	
	}
	
	public void generateSqlMapForWorkFlow() {
		String lowModelStr = modelName.toLowerCase();
		StringBuffer sqlMapStr = new StringBuffer();
		StringBuffer insertStr = new StringBuffer();
		StringBuffer updateByPrimaryKeyStr = new StringBuffer();
		StringBuffer selectByPrimaryKeyStr = new StringBuffer();
		StringBuffer mFieldStr = new StringBuffer();
		insertStr.append("\t\tinsert into ")
					.append(tableName + " (");
		Field[] fields = modelClass.getDeclaredFields();
		for (int i = 0; i<fields.length; i++) {
			if (i < fields.length - 1) {
				mFieldStr.append("#" + fields[i].getName() + "#" + ",");
				if (id.equals(fields[i].getName()) && !pk.equalsIgnoreCase(id)) {
					insertStr.append(pk + ",");
					selectByPrimaryKeyStr.append(pk + " " + fields[i].getName() + ",");
				} else {
					insertStr.append(fields[i].getName() + ",");
					selectByPrimaryKeyStr.append(fields[i].getName() + ",");
				}					
				if (!id.equals(fields[i].getName())) {
					if ((i+1) == (fields.length - 1) && id.equals(fields[i+1].getName()))
						updateByPrimaryKeyStr.append("\t\t" + fields[i].getName() + "=" + "#" + fields[i].getName() + "#\n");
					else 
						updateByPrimaryKeyStr.append("\t\t" + fields[i].getName() + "=" + "#" + fields[i].getName() + "#,\n");
				}				
			} else {
				insertStr.append(fields[i].getName() + ")\n");
				mFieldStr.append("#" + fields[i].getName() + "#)\n");
				if (id.equals(fields[i].getName()) && !pk.equalsIgnoreCase(id)) {
					selectByPrimaryKeyStr.append(pk + " " + fields[i].getName());
				} else {
					selectByPrimaryKeyStr.append(fields[i].getName());
				}
				if (!id.equals(fields[i].getName())) {
					updateByPrimaryKeyStr.append("\t\t" + fields[i].getName() + "=" + "#" + fields[i].getName() + "#\n");
				}
			}			
		}
		
		sqlMapStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
						.append("<!DOCTYPE sqlMap PUBLIC \"-//iBATIS.com//DTD SQL Map 2.0//EN\" \n")
						.append("\t\"http://ibatis.apache.org/dtd/sql-map-2.dtd\" >")
						.append("\n")
						.append("<sqlMap namespace=\""+ modelName +"\">")
						.append("\n")
						// insert
						.append("\t<insert id=\"insert\">\n")
						.append(insertStr)
						.append("\t\tvalues\n")
						.append("\t\t(")
						.append(mFieldStr)
						.append("\t</insert>\n\n")
						;
		
		// deleteByPrimaryKey
		sqlMapStr.append("\t<delete id=\"deleteByPrimaryKey\">\n")
		.append("\t\tdelete from " + tableName + " where " + pk + " = #" + id + "#\n")
		.append("\t</delete>\n\n")
		;

		// updateByPrimaryKey	
		sqlMapStr.append("\t<update id=\"updateByPrimaryKey\">\n")
					.append("\t\tupdate " + tableName + " set\n")
					.append(updateByPrimaryKeyStr)
					.append("\t\twhere " + pk + " = #" + id + "#\n")
					.append("\t</update>\n\n")				
					;
		// getAll
		sqlMapStr.append("\t<select id=\"getAll\" resultClass=\"" + getFullQualifyName() + "\">\n")
		.append("\t\tselect pub.* from ")
		.append(tableName)
		.append(", VV_PUB_FLOW pub\n")
		.append("\t\twhere t.docid=pub.docid\n")
		.append("\t</selet>\n\n")				
		;
		
		// selectByPrimaryKey
		sqlMapStr.append("\t<select id=\"selectByPrimaryKey\" resultClass=\"" + getFullQualifyName() + "\">\n")
		.append("\t\tselect ")
		.append(selectByPrimaryKeyStr + " \n")
		.append("\t\tfrom ")
		.append(tableName + "\n")
		.append("\t\twhere " + pk + " = #" + id + "#\n")
		.append("\t</selet>\n\n")				
		;
		
		//countByWhere_Clause	
		sqlMapStr.append("\t<select id=\"countByWhere_Clause\" parameterClass=\"" + sqlUtils + "\" resultClass=\"java.lang.Integer\">\n")
		.append("\t\tselect count(*) from \n")
		.append("\t\t(select pub.* from " + tableName + " t, V_PUB_FLOW pub where t.docid=pub.docid ) tmp \n")
		.append("\t\t<isParameterPresent>\n")
		.append("\t\t\t<include refid=\"IBatisDefault.Where_Clause\" />\n")
		.append("\t\t</isParameterPresent>\n")
		.append("\t</selet>\n\n")				
		;
		
		//selectByWhere_Clause	
		sqlMapStr.append("\t<select id=\"selectByWhere_Clause\" parameterClass=\"" + sqlUtils + "\" resultClass=\"" + getFullQualifyName() + "\">\n")
		.append("\t\tselect * from \n")
		.append("\t\t(select pub.* from " + tableName + " t, V_PUB_FLOW pub where t.docid=pub.docid ) tmp \n")
		.append("\t\t<isParameterPresent>\n")
		.append("\t\t\t<include refid=\"IBatisDefault.Where_Clause\" />\n")
		.append("\t\t\t<isNotNull property=\"orderByClause\">\n")
		.append("\t\t\t\torder by $orderByClause$\n")
		.append("\t\t\t</isNotNull>\n")
		.append("\t\t</isParameterPresent>\n")
		.append("\t</selet>\n\n")				
		;
		
		//count_selectAllByWhere_Clause	
		sqlMapStr.append("\t<select id=\"count_selectAllByWhere_Clause\" parameterClass=\"" + sqlUtils + "\" resultClass=\"java.lang.Integer\">\n")
		.append("\t\tselect count(*) from \n")
		.append("\t\t(select pub.* from " + tableName + " t, VV_PUB_FLOW pub where t.docid=pub.docid ) tmp \n")
		.append("\t\t<isParameterPresent>\n")
		.append("\t\t\t<include refid=\"IBatisDefault.Where_Clause\" />\n")
		.append("\t\t</isParameterPresent>\n")
		.append("\t</selet>\n\n")				
		;
		
		//selectAllByWhere_Clause	
		sqlMapStr.append("\t<select id=\"selectAllByWhere_Clause\" parameterClass=\"" + sqlUtils + "\" resultClass=\"" + getFullQualifyName() + "\">\n")
		.append("\t\tselect * from \n")
		.append("\t\t(select pub.* from " + tableName + " t, VV_PUB_FLOW pub where t.docid=pub.docid ) tmp \n")
		.append("\t\t<isParameterPresent>\n")
		.append("\t\t\t<include refid=\"IBatisDefault.Where_Clause\" />\n")
		.append("\t\t\t<isNotNull property=\"orderByClause\">\n")
		.append("\t\t\t\torder by $orderByClause$\n")
		.append("\t\t\t</isNotNull>\n")
		.append("\t\t</isParameterPresent>\n")
		.append("\t</selet>\n\n")				
		;
		
		//deleteByWhere_Clause
		sqlMapStr.append("\t<delete id=\"deleteByWhere_Clause\" parameterClass=\"" + sqlUtils + "\">\n")
		.append("\t\tdelete from " + tableName + "\n")
		.append("\t\twhere docid in (select docid from (select pub.* from " + tableName 
				+ " t, V_PUB_FLOW pub where t.docid=pub.docid ) tmp \n")
		.append("\t\t<include refid=\"IBatisDefault.Where_Clause\" />)\n")
		.append("\t</delete>\n\n")
		;
		
		sqlMapStr.append("</sqlMap>");
		generateFile(sqlMapStr, "", "sqlmap-" + lowModelStr, "xml");	
	}
	
	public void generateSqlMapForNormal() {
		String lowModelStr = modelName.toLowerCase();
		StringBuffer sqlMapStr = new StringBuffer();
		StringBuffer insertStr = new StringBuffer();
		StringBuffer updateByPrimaryKeyStr = new StringBuffer();
		StringBuffer selectByPrimaryKeyStr = new StringBuffer();
		StringBuffer mFieldStr = new StringBuffer();
		insertStr.append("\t\tinsert into ")
					.append(tableName + " (");
		Field[] fields = modelClass.getDeclaredFields();
		for (int i = 0; i<fields.length; i++) {
			if (i < fields.length - 1) {
				mFieldStr.append("#" + fields[i].getName() + "#" + ",");
				if (id.equals(fields[i].getName()) && !pk.equalsIgnoreCase(id)) {
					insertStr.append(pk + ",");
					selectByPrimaryKeyStr.append(pk + " " + fields[i].getName() + ",");
				} else {
					insertStr.append(fields[i].getName() + ",");
					selectByPrimaryKeyStr.append(fields[i].getName() + ",");
				}						
				if (!id.equals(fields[i].getName())) {
					if ((i+1) == (fields.length - 1) && id.equals(fields[i+1].getName()))
						updateByPrimaryKeyStr.append("\t\t" + fields[i].getName() + "=" + "#" + fields[i].getName() + "#\n");
					else 
						updateByPrimaryKeyStr.append("\t\t" + fields[i].getName() + "=" + "#" + fields[i].getName() + "#,\n");
				}				
			} else {
				insertStr.append(fields[i].getName() + ")\n");
				mFieldStr.append("#" + fields[i].getName() + "#)\n");
				if (id.equals(fields[i].getName()) && !pk.equalsIgnoreCase(id)) {
					selectByPrimaryKeyStr.append(pk + " " + fields[i].getName());
				} else {
					selectByPrimaryKeyStr.append(fields[i].getName());
				}
				if (!id.equals(fields[i].getName())) {
					updateByPrimaryKeyStr.append("\t\t" + fields[i].getName() + "=" + "#" + fields[i].getName() + "#\n");
				}
			}			
		}
		
		sqlMapStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
						.append("<!DOCTYPE sqlMap PUBLIC \"-//iBATIS.com//DTD SQL Map 2.0//EN\" \n")
						.append("\t\"http://ibatis.apache.org/dtd/sql-map-2.dtd\" >")
						.append("\n")
						.append("<sqlMap namespace=\""+ modelName +"\">")
						.append("\n")
						// insert
						.append("\t<insert id=\"insert\">\n")
						.append(insertStr)
						.append("\t\tvalues\n")
						.append("\t\t(")
						.append(mFieldStr)
						.append("\t</insert>\n\n")
						;
		
		// deleteByPrimaryKey
		sqlMapStr.append("\t<delete id=\"deleteByPrimaryKey\">\n")
		.append("\t\tdelete from " + tableName + " where " + pk + " = #" + id + "#\n")
		.append("\t</delete>\n\n")
		;

		// updateByPrimaryKey	
		sqlMapStr.append("\t<update id=\"updateByPrimaryKey\">\n")
					.append("\t\tupdate " + tableName + " set\n")
					.append(updateByPrimaryKeyStr)
					.append("\t\twhere " + pk + " = #" + id + "#\n")
					.append("\t</update>\n\n")				
					;
		// getAll 
		sqlMapStr.append("\t<select id=\"getAll\" resultClass=\"" + getFullQualifyName() + "\">\n")
		.append("\t\tselect " + selectByPrimaryKeyStr + " from ")
		.append(tableName)
		.append("\n")
		.append("\t</selet>\n\n")				
		;
		
		// selectByPrimaryKey
		sqlMapStr.append("\t<select id=\"selectByPrimaryKey\" resultClass=\"" + getFullQualifyName() + "\">\n")
		.append("\t\tselect ")
		.append(selectByPrimaryKeyStr + " \n")
		.append("\t\tfrom ")
		.append(tableName + "\n")
		.append("\t\twhere " + pk + " = #" + id + "#\n")
		.append("\t</selet>\n\n")				
		;
		
		//countByWhere_Clause	
		sqlMapStr.append("\t<select id=\"countByWhere_Clause\" parameterClass=\"" + sqlUtils + "\" resultClass=\"java.lang.Integer\">\n")
		.append("\t\tselect count(*) from \n")
		.append("\t\t(select t.* from " + tableName + " t) tmp \n")
		.append("\t\t<isParameterPresent>\n")
		.append("\t\t\t<include refid=\"IBatisDefault.Where_Clause\" />\n")
		.append("\t\t</isParameterPresent>\n")
		.append("\t</selet>\n\n")				
		;
		
		//selectByWhere_Clause	
		sqlMapStr.append("\t<select id=\"selectByWhere_Clause\" parameterClass=\"" + sqlUtils + "\" resultClass=\"" + getFullQualifyName() + "\">\n")
		.append("\t\tselect tmp.* from \n")
		.append("\t\t(select " + selectByPrimaryKeyStr + " from " + tableName + " t) tmp \n")
		.append("\t\t<isParameterPresent>\n")
		.append("\t\t\t<include refid=\"IBatisDefault.Where_Clause\" />\n")
		.append("\t\t\t<isNotNull property=\"orderByClause\">\n")
		.append("\t\t\t\torder by $orderByClause$\n")
		.append("\t\t\t</isNotNull>\n")
		.append("\t\t</isParameterPresent>\n")
		.append("\t</selet>\n\n")				
		;

		//deleteByWhere_Clause 
		sqlMapStr.append("\t<delete id=\"deleteByWhere_Clause\" parameterClass=\"" + sqlUtils + "\">\n")
		.append("\t\tdelete from " + tableName + "\n")
		.append("\t\twhere " + pk + " in (select " + pk + " from (select " + selectByPrimaryKeyStr + " from " + tableName 
				+ " t) tmp \n")
		.append("\t\t<include refid=\"IBatisDefault.Where_Clause\" />)\n")
		.append("\t</delete>\n\n")
		;
		
		sqlMapStr.append("</sqlMap>");
		generateFile(sqlMapStr, "", "sqlmap-" + lowModelStr, "xml");	
	}
	
	public void generateStrutsXml() {
		
	}
		
	public void generateFile(StringBuffer content,String type, String name, String suffix) {
		File file = new File(des + "\\" + modelName.toLowerCase() + "\\" + type);
		if (!file.exists())
			file.mkdirs();
		File desFile = new File(des + "\\" + modelName.toLowerCase() + "\\" + type + "\\" + name + "." + suffix);		
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(desFile),"UTF-8");
			out.write(content.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startForWorkFlow() {
		if (checkModel()) {
			generateService();
			generateDao();
			generateAction("OfficeWorkFlowAction");
			generateSqlMapForWorkFlow();
		}
	}
	
	public void startNormal() {
		if (checkModel()) {
			generateService();
			generateDao();
			generateAction("BaseAction");
			generateSqlMapForNormal();
		}
	}
	
	public boolean checkModel() {
		Field[] fields = modelClass.getDeclaredFields();
		if (fields.length < 2) {
			System.out.println("Model 字段数小于2，程序结束。");
			return false;
		}			
		if (id == null || "".equals(id.trim())) {
			System.out.println("Model id 不可为空。");
			return false;
		}		
		for (int i = 0; i<fields.length; i++) {
			if (id.equals(fields[i].getName())) 
				return true;
		}
		System.out.println("Model id 与 设置id字段不符。");
		return false;
	}
	
	/**
	 * @param superClass	ValueObject or WorkFlowBean
	 */
	public void generateModel(String superClass) {
		DB db = new DB();
		db.generateModel(db.getConn("Oracle"), "gwgl_publicity");
		StringBuffer modelStr = new StringBuffer();
		String lowModelStr = modelName.toLowerCase();
		List<String> names = db.getNames();
		List<String> types = db.getTypes();
		List<String> comments = db.getComments();
		modelStr.append("package ")
					.append(packageName)
					.append(".")
					.append(lowModelStr)
					.append(".model;\n")
					.append("\n\n")
					.append("public class " + modelName + " extends " + superClass + "{\n\n")
					;
		for (int i = 0; i<names.size(); i++) {
			modelStr.append("\t/** ")
					.append(comments.get(i) == null ? "" : comments.get(i))
					.append(" */\n")
					;
			modelStr.append("\t private ")
					.append(sqlType2JavaType(types.get(i)))
					.append(" ")
					.append(names.get(i).toLowerCase() + ";\n")
					;
		}
		modelStr.append("}");
		generateFile(modelStr, "model", modelName, "java");

	}

	/**
	 * 功能：获得列的数据类型
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("number")) {
			return "int";
		}  else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("VARCHAR2") || sqlType.equalsIgnoreCase("text")
				|| sqlType.equalsIgnoreCase("CLOB")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} 
		return null;
	}
	
	public static void main(String[] args) {
		String packageName = "cn.xioa.portal";
		String modelName = "Test";
		String des = "D:";
		String tableName = "pub_usertable";
		Class modelClass = Test.class;
		String pk = "publicityid"; //  表主键
		String id = "publicityid"; // 主键对应model id    model必需有此字段
		String sqlUtils = "cn.prpsdc.base.model.SQLUtils"; // 
		Generate g = new Generate(packageName, modelName, des, tableName, modelClass, pk, id, sqlUtils);
		
		//g.generateModel("ValueObject");
		g.startNormal();
	}
}
