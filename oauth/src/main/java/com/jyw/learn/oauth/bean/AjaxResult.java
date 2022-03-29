package com.jyw.learn.oauth.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @ClassName:  AjaxResult   
 * @Description: 统一结果返回类
 *
 */
@Data
public class AjaxResult<T> implements Serializable {
	
	private static final long serialVersionUID = -6023771566281922918L;
	public static final String SUCCESS_CODE="10000";
	public static final String ERROR_CODE="10900";
	
    private T data;
    private String code;
    private String msg;
	private String message;


	/**
     * 默认错误码 + 空数据
     * @return
     */
    public static AjaxResult success() {
    	AjaxResult obj = new AjaxResult();
    	obj.setCode("10000");
    	return obj;
    }
    /**
     * 默认错误码 + 数据
     * @return
     */
    public static AjaxResult successWithData(Object object) {
    	AjaxResult obj = new AjaxResult();
    	obj.setData(object);
    	obj.setCode("10000");
    	return obj;
    }
    /**
     * 指定错误码 + 空数据
     * @param resultCode
     * @return
     */
    public static AjaxResult success(String resultCode){
    	AjaxResult obj = new AjaxResult();
    	obj.setCode(resultCode);
    	return obj;
	}
    /**
     * 指定错误码 + 数据
     * @param code
     * @param object
     * @return
     */
	public static AjaxResult successWithData(String code, Object object) {
		AjaxResult obj = new AjaxResult();
		obj.setCode(code);
		obj.setData(object);
		return obj;
	}
    /**
     * 
     * @Title: failure   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @param msg
     * @param: @param type true为业务消息描述， false为业务消息代码
     * @param: @return      
     * @return: AjaxResult      
     * @throws
     */

	public static AjaxResult failure() {
		AjaxResult obj = new AjaxResult();
		obj.setCode("10900");
		return obj;
	}

	public static AjaxResult failure(String code) {
		AjaxResult obj = new AjaxResult();
		obj.setCode(code);
		return obj;
	}
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}


	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public static AjaxResult failure(String code, String msg) {
		// TODO Auto-generated method stub
		AjaxResult obj = new AjaxResult();
		obj.code=code;
		obj.msg=msg;
		return obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
