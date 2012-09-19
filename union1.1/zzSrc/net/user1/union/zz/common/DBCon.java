package net.user1.union.zz.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.Factory;

public class DBCon {
	private static Logger logger = Logger.getLogger(DBCon.class);
	private static DBCon dbCon = null;
	private static String DB_PROPERTIES = "jooq.properties";
	
	private static String DB_CONN_STRING = "";
    private static String DRIVER_CLASS_NAME = "";
    private static String DB_USER_NAME = "";
    private static String DB_PASSWORD = "";
    
    private static Factory factory = null;
	private DBCon(){
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(DB_PROPERTIES));
		    if(properties.containsKey("jdbc.Driver") && properties.containsKey("jdbc.URL") && properties.containsKey("jdbc.User") && properties.containsKey("jdbc.Password")){
		    	DRIVER_CLASS_NAME = properties.getProperty("jdbc.Driver");
		    	DB_CONN_STRING = properties.getProperty("jdbc.URL");
		    	DB_USER_NAME = properties.getProperty("jdbc.User");
		    	DB_PASSWORD = properties.getProperty("jdbc.Password");
		    }
		} catch (IOException e) {
			logger.error("Exception occoured in DBCon() : " + e);
		}
	}
	
	private static void init(){
		DBCon.dbCon = new DBCon();
	}
	
	private static Connection getSimpleConnection() {
	    //See your driver documentation for the proper format of this string :
	    
	    Connection result = null;
	    try {
	       Class.forName(DRIVER_CLASS_NAME).newInstance();
	    }
	    catch (Exception ex){
	       logger.error("Check classpath. Cannot load db driver: " + DRIVER_CLASS_NAME);
	    }

	    try {
	      result = DriverManager.getConnection(DB_CONN_STRING, DB_USER_NAME, DB_PASSWORD);
	    }
	    catch (SQLException e){
	       logger.error("Driver loaded, but cannot connect to db: " + DB_CONN_STRING);
	    }
	    return result;
	  }
	
	public static Factory getFactory(){
		if(dbCon==null){
			init();
		}
		if (factory == null) {
			factory = new Factory(DBCon.getSimpleConnection(), SQLDialect.POSTGRES);
			try {
				factory.getConnection().setAutoCommit(false);
			} catch (Exception e) {
				logger.error("Error at BaseGameRoom:init()", e);
			}
		}
		return factory;
	}
	
	public static String getSerialisedPrimaryKeyForVarchar(Table<?> t,TableField<?, ?> tf_primaryKey){
		Long newTableId = DBCon.getSerialisedPrimaryKeyForLong(t, tf_primaryKey);
		return newTableId.toString();
	}
	
	public static Long getSerialisedPrimaryKeyForLong(Table<?> t,TableField<?, ?> tf_primaryKey){
		Long newTableId = new Long(0);
		try{
			Factory factory = getFactory();
			SelectQuery q = factory.selectQuery();
				q.addFrom(t);
				q.addOrderBy(t.getField("create_time").desc(),tf_primaryKey.cast(Integer.class).desc());
				q.addLimit(1);
			Record record = null;
			try {
				record = q.fetchOne();
			} catch (DataAccessException e) {
				logger.error("getSerialisedPrimaryKeyForVarchar()  - Exception occoured : " + e);
				e.printStackTrace();
			}
			
			try{
				if(record!=null){
					String lastCreatedId = record.getValueAsString(tf_primaryKey);
					if(lastCreatedId!=null && lastCreatedId.length()>0){					
						newTableId = Long.parseLong(lastCreatedId);
					}
				}
			}catch(Exception e){
				logger.error("getSerialisedPrimaryKeyForVarchar() - Exception occoured : " + e + newTableId);
				e.printStackTrace();
			}
		
		}catch(Exception e){
			logger.error("getSerialisedPrimaryKeyForVarchar() Exception occoured : " + e + "[newTableId:" + newTableId + ",Table:" + t + ",TableField:" + tf_primaryKey + "]");
			e.printStackTrace();
		}
	
		//Incrementing newTableId for new Record
		newTableId++;
		
		return newTableId;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	 public static Map sortByComparator( Map unsortMap) {
		 
	        List<Object> list = new LinkedList<Object>(unsortMap.entrySet());
	 
	        //sort list based on comparator
	        Collections.sort(list, new Comparator<Object>() {
				public int compare(Object o1, Object o2) {
		           return ((Comparable) ((Map.Entry) (o1)).getValue())
		           .compareTo(((Map.Entry) (o2)).getValue());
	             }
		});
	 
	        //put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		for (Iterator<Object> it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();
		     sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	 }	

}
