package com.skipper.zl.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DB {
	
	private List<String> names = new ArrayList<String>();
	private List<String> types = new ArrayList<String>();
	private List<String> comments = new ArrayList<String>();


	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	// get Connection
	public Connection getConn() {
		
		String rootPath = null;
		String url = null;
		String driver = null;
		String user = null;
		String password = null;
		Connection con = null;
		Properties p = new Properties();
		rootPath = System.getProperty("user.dir");	
		Properties props =new Properties();
		
		// 参数设定
		try {
			p.load(new FileInputStream(rootPath +"/config/JDBC.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("配置文件不存在...");
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
		url = p.getProperty("url");
		driver = p.getProperty("driver");
		user = p.getProperty("user");
		password = p.getProperty("password");
		props.setProperty("user", user);
		props.setProperty("password", password);
		props.setProperty("remarks", "true"); // set remarks true

		// 加载驱动
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			System.out.println("无法找到驱动...");
			e1.printStackTrace();

		}
		// 获取连接
		try {
			con = DriverManager.getConnection(url, props);
			System.out.println("数据库连接成功!");
		} catch (SQLException e) {
			System.out.println("数据库连接失败...");
			e.printStackTrace();

		}

		return con;
	}
	
	public void generateModel(Connection conns, String tableName) {
		PreparedStatement psts = null;
		DatabaseMetaData dbmd = null;
		ResultSet resultSet = null;
		try {
			String sql = "select * from " + tableName;
			psts = conns.prepareStatement(sql);
			dbmd = conns.getMetaData();
			resultSet = dbmd.getTables(null, getSchema(conns), tableName.toUpperCase(), new String[] { "TABLE" });
			while (resultSet.next()) {
		    	String tableName1=resultSet.getString("TABLE_NAME");	
		    	if(tableName1.equalsIgnoreCase(tableName)){
		    		ResultSet rs = conns.getMetaData().getColumns(null, getSchema(conns),tableName.toUpperCase(), "%");
		    		while(rs.next()){	 
		    			names.add(rs.getString("COLUMN_NAME"));
		    			types.add(rs.getString("TYPE_NAME"));
		    			comments.add(rs.getString("REMARKS"));
		    			/*System.out.println("列：" + rs.getString("COLUMN_NAME"));
		    			System.out.println("类型：" + rs.getString("TYPE_NAME"));
		    			System.out.println("注释：" + rs.getString("REMARKS"));	  */  
		    			//System.out.println("类型：" + rs.getString("TYPE_NAME"));
		    		}
		    		rs.close();
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(conns, psts, resultSet);
			
		}
	}	
	
	// close方法
	private void close(Connection conn, PreparedStatement pst, ResultSet rs) {
	
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		if (pst != null) {
			try {
				pst.close();
				pst = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
		}
	}
	
	private void close(Connection conn, PreparedStatement pst) {
	
		if (pst != null) {
			try {
				pst.close();
				pst = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
		}
	}
	
	
	//其他数据库不需要这个方法 oracle和db2需要
	private static String getSchema(Connection conn) throws Exception {
		String schema;
		schema = conn.getMetaData().getUserName();
		if ((schema == null) || (schema.length() == 0)) {
			throw new Exception("ORACLE数据库模式不允许为空");
		}
		return schema.toUpperCase().toString();

	}	
	
	public static void main(String[] args) {
		//new DB().generateModel("gwgl_publicity");
	}
}
