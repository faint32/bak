package com.cmw.action.funds;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.funds.InterestEntity;
import com.cmw.entity.funds.InterestRecordsEntity;
import com.cmw.entity.sys.RestypeEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.funds.EntrustCustService;
import com.cmw.service.inter.funds.InterestRecordsService;
import com.cmw.service.inter.funds.InterestService;
import com.cmw.service.inter.sys.RestypeService;
import com.cmw.service.inter.sys.VarietyService;



/**
 * 委托客户资料  ACTION类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="利息支付ACTION",createDate="2014-01-15T00:00:00",author="李听",defaultVals="fuInterestRecords_")
@SuppressWarnings("serial")
public class InterestRecordsAction extends BaseAction {
	@Resource(name="interestRecordsService")
	private InterestRecordsService interestRecordsService;
	
	@Resource(name="restypeService")
	private RestypeService restypeService;
	@Resource(name="varietyService")
	private VarietyService varietyService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 委托客户资料 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			Integer status=getIVal("status");
			String name=getVal("name");
			String startDate1=getVal("startDate1");
			String endDate1=getVal("endDate1");
			String phone=getVal("phone");
			String contactTel=getVal("contactTel");
			String inAddress=getVal("inAddress");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("status", status);
			map.put("name", name);
			map.put("startDate1", startDate1);
			map.put("endDate1", endDate1);
			map.put("phone", phone);
			map.put("contactTel", contactTel);
			map.put("inAddress", inAddress);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = interestRecordsService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr(new JsonDataCallback(){
				public void makeJson(JSONObject jsonObj) {
					Long creator = jsonObj.getLong("creator");
					try {
						UserEntity creatorObj = UserCache.getUser(creator);
						if(null != creatorObj) jsonObj.put("creator", creatorObj.getEmpName());
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
				
			});
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 获取 委托客户资料 列表
	 * @return
	 * @throws Exception
	 */
	public String getName()throws Exception {
		try {
			Long sysId=getLVal("sysId");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("sysId", sysId);
			DataTable dt = restypeService.getLoanRecordsList(map, -1, -1);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr(new JsonDataCallback(){
				public void makeJson(JSONObject jsonObj) {
//					Long creator = jsonObj.getLong("creator");
//					try {
//						UserEntity creatorObj = UserCache.getUser(creator);
//						if(null != creatorObj) jsonObj.put("creator", creatorObj.getEmpName());
//					} catch (ServiceException e) {
//						e.printStackTrace();
//					}
				}
			});
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 委托客户资料 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			InterestRecordsEntity entity = interestRecordsService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					
				}
			});
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	public String getCusName()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			VarietyEntity entity = varietyService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					
				}
			});
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			Long id=getLVal("id");
			InterestRecordsEntity entity = BeanUtil.copyValue(InterestRecordsEntity.class,getRequest());
			System.out.println(id);
			entity.setId(id);
			interestRecordsService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this,entity, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 新增  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = interestRecordsService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("E", num);
			result = JsonUtil.getJsonString("code",code);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 删除  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			interestRecordsService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 委托客户资料 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			InterestRecordsEntity entity = interestRecordsService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 获取指定的 委托客户资料 下一个对象
	 * @return
	 * @throws Exception
	 * 
	 */
	
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			InterestRecordsEntity entity = interestRecordsService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
}