package hit.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * 使用Connection接口提供的DatabaseMetaData接口，获得表结构
 * 两个经常用的方法：
 *         1、DatabaseMetaData m = conn.getMetaData();
 *            获得表结构
 *            ResultSet rs = m.getTables(null, null, null, new String[]{"TABLE"});
 *            ...
 *            rs.getString("TABLE_NAME")
 *            
 *         2、DatabaseMetaData m = conn.getMetaData();
 *            获得某一张表中的字段
 *            ResultSet rs = m.getColumns(null, null, "HELLOTABLE", null);
 *            ...
 *            rs.getString("COLUMN_NAME")和rs.getString("DATA_TYPE")
 * @author zengli
 * @date 2016/5/29
 */
public class DatabaseMetaDataTeat {
	public static void main(String[] args) throws Exception {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		Properties props = new Properties();
		props.put("user", "user1");
		props.put("password", "user1");
		Connection conn = DriverManager.getConnection("jdbc:derby:helloDB;create=false",props);
		
		DatabaseMetaData m = conn.getMetaData();
		/*
		ResultSet rs = m.getTables(null, null, null, new String[]{"TABLE"});
		//在ResultSet中有10列
		while(rs.next()){
			System.out.println("这是表的名字："+rs.getString("TABLE_NAME"));
		}
		*/
		
		ResultSet rs = m.getColumns(null, null, "HELLOTABLE", null);
		//在ResultSet中有10列
		while(rs.next()){
			//默认的字段类型是int类型
			System.out.println(rs.getString("COLUMN_NAME"));
			int dataType = rs.getInt("DATA_TYPE");
			switch(dataType) {
			case Types.VARCHAR:
				System.out.println("varchar");
				break;
			case Types.INTEGER:
				System.out.println("int");
				break;
			}
		}
		rs.close();
		conn.close();
		
		try{
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		}catch(SQLException se){
			/*
			 * 如果derby已经关闭了，你再次尝试去关它的时候就会出现异常，
			 * 提示输出"数据库已经关闭！"
			 */
			System.out.println();
			System.out.println("Database shut down normally!");
		}
	}
}
