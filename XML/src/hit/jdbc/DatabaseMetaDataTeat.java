package hit.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * ʹ��Connection�ӿ��ṩ��DatabaseMetaData�ӿڣ���ñ�ṹ
 * ���������õķ�����
 *         1��DatabaseMetaData m = conn.getMetaData();
 *            ��ñ�ṹ
 *            ResultSet rs = m.getTables(null, null, null, new String[]{"TABLE"});
 *            ...
 *            rs.getString("TABLE_NAME")
 *            
 *         2��DatabaseMetaData m = conn.getMetaData();
 *            ���ĳһ�ű��е��ֶ�
 *            ResultSet rs = m.getColumns(null, null, "HELLOTABLE", null);
 *            ...
 *            rs.getString("COLUMN_NAME")��rs.getString("DATA_TYPE")
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
		//��ResultSet����10��
		while(rs.next()){
			System.out.println("���Ǳ�����֣�"+rs.getString("TABLE_NAME"));
		}
		*/
		
		ResultSet rs = m.getColumns(null, null, "HELLOTABLE", null);
		//��ResultSet����10��
		while(rs.next()){
			//Ĭ�ϵ��ֶ�������int����
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
			 * ���derby�Ѿ��ر��ˣ����ٴγ���ȥ������ʱ��ͻ�����쳣��
			 * ��ʾ���"���ݿ��Ѿ��رգ�"
			 */
			System.out.println();
			System.out.println("Database shut down normally!");
		}
	}
}
