package com.youngmanster.collectionlibrary.data.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class ResultSet implements Serializable {

	private static final long serialVersionUID = 2510654675439416448L;

	private Map<String, Object> nameValueMap = new LinkedHashMap<String, Object>();

	private Map<Integer, Object> indexValueMap = new LinkedHashMap<Integer, Object>();

	private List<String> columnNameList = new ArrayList<String>();

	private int index = 0;

	/**
	 * set column value
	 * 
	 * @param columnName the table column name
	 * @param columnValue 
	 */
	void setValue(String columnName, Object columnValue) {
		columnName = columnName.toLowerCase();
		columnNameList.add(columnName);
		nameValueMap.put(columnName, columnValue);
		indexValueMap.put(index++, columnValue);
	}

	/**
	 * change value by index in this result set
	 * 
	 * @param index
	 * @param value
	 */
	public void changeValue(int index, Object value) {
		if (indexValueMap.containsKey(index)) {
			indexValueMap.put(index, value);
			nameValueMap.put(columnNameList.get(index), value);
		}
	}

	/**
	 * get value by column name
	 * 
	 * @param columnName
	 * @return return the value of this column
	 */
	public Object getValue(String columnName) {
		return nameValueMap.get(columnName.toLowerCase());
	}

	/**
	 *  get boolean type value
	 *  
	 * @param columnName
	 * @return if the value is a "1" or "true" , return true; or is a "null" or "0" or "false", return false.
	 * @exception ClassCastException
	 */
	public boolean getBooleanValue(String columnName) {
		Object value = getValue(columnName);
		if(value == null)
			return false;
		String strVal = value.toString().toLowerCase();
		if(strVal.equals("true") || strVal.equals("1")){
			return true;
		}else if(strVal.equals("false") || strVal.equals("0")){
			return false;
		}
		throw new ClassCastException(String.format("invalid boolean value : %s ", value));
	}

	/**
	 * 
	 * get long type value
	 * 
	 * @param columnName
	 */
	public long getLongValue(String columnName) {
		return (long) getDoubleValue(columnName);
	}

	/**
	 * 
	 * get int type value
	 * 
	 * @param columnName
	 */
	public int getIntValue(String columnName) {
		return (int) getLongValue(columnName);
	}

	/**
	 * 
	 * get short type value
	 * 
	 * @param columnName
	 */
	public short getShortValue(String columnName) {
		return (short) getIntValue(columnName);
	}

	/**
	 * 
	 * get float type vlue
	 * 
	 * @param columnName
	 */
	public float getFloatValue(String columnName) {
		return (float) getDoubleValue(columnName);
	}
	
	/**
	 * get double type value
	 * 
	 * @param columnName
	 * @return null return 0
	 * @exception ClassCastException
	 */
	public double getDoubleValue(String columnName) {
		Object value = getValue(columnName);
		if(value == null){
			return 0;
		}else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof Float) {
			return (Float) value;
		} else if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Short) {
			return (Short) value;
		} else if (value instanceof String) {
			if (isNum((String) value)) {
				return Double.parseDouble((String) value);
			}
		}
		throw new ClassCastException(String.format("invalid number %s ", value));
	}

	/**
	 * 
	 * get String type value
	 * 
	 * @param columnName
	 */
	public String getStringValue(String columnName) {
		Object value = getValue(columnName);
		if(value != null){
			return value.toString();
		}else{
			return null;
		}
	}

	/**
	 * get Date type value
	 * @param columnName
	 * @return
	 */
	public Date getDateValue(String columnName){
		String value = getStringValue(columnName);
		return value == null ? null : DateUtils.parseStr2Date(value);
	}
	
	/**
	 * get byte[] type value
	 * @param columnName
	 * @return
	 */
	public byte[] getBlobValue(String columnName){
		return (byte[])getValue(columnName);
	}
	
	/**
	 * get value by index
	 * 
	 * @param columnIndex
	 * @return
	 */
	public Object getValue(int columnIndex) {
		return indexValueMap.get(columnIndex);
	}

	/**
	 * the size of columns
	 * @return
	 */
	public int getSize() {
		return nameValueMap.size();
	}

	/**
	 * no result
	 * @return
	 */
	public boolean isEmpty() {
		return nameValueMap.isEmpty();
	}

	/**
	 * get column name by its index
	 * @param columnNum
	 * @return
	 */
	public String getColumnName(int columnNum){
		return columnNameList.get(columnNum);
	}
	
	/**
	 * get index of this column name
	 * @param columnName
	 * @return if the column name didn't exsits, return -1
	 */
	public int indexOfColumnName(String columnName){
		return columnNameList.indexOf(columnName.toLowerCase());
	}
	
	@Override
	public String toString() {
		return "Result [nameValueMap=" + nameValueMap + "]";
	}
	
	/**
	 * is this str a valid number
	 * 
	 * @param str
	 */
	private boolean isNum(String str) {
		return !str.equals("")&&str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
}
