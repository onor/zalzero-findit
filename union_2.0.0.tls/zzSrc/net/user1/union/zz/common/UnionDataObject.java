package net.user1.union.zz.common;

import java.util.LinkedHashMap;

import org.jooq.tools.json.JSONObject;

public class UnionDataObject {
	String dataStr = null;
	Boolean flag_changed = true;
	LinkedHashMap<String,Object> dataMap = null;
	
	public UnionDataObject(){
		dataMap = new LinkedHashMap<String,Object>();
	}
	
	public void append(String key,Object val){
		if(key != null && key.length() > 0 && val != null){
			dataMap.put(key, val);
			flag_changed = true;
		}
	}
	public void append(Integer key,Object val){
		if(key != null && val != null){
			dataMap.put(key.toString(), val);
			flag_changed = true;
		}
	}
	
	public String toString(){
		if(flag_changed){
			this.dataStr = JSONObject.toJSONString(this.dataMap);
			flag_changed = false;
		}
		return this.dataStr;
	}
	
	public Object toObject(){
		return this.dataMap;
	}
}
