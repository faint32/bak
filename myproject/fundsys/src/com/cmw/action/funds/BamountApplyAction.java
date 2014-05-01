package com.cmw.action.funds;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysCodeConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.AppraiseEntity;
import com.cmw.entity.funds.BamountApplyEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.BussProccCache;
import com.cmw.service.impl.workflow.BussProccFlowService;
import com.cmw.service.inter.funds.BamountApplyService;
import com.cmw.service.inter.sys.FormCfgService;


/**
 * 汇票回款单表  ACTION类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票回款单表ACTION",createDate="2014-02-20T00:00:00",author="郑符明",defaultVals="fuBamountApply_")
@SuppressWarnings("serial")
public class BamountApplyAction extends BaseAction {
	@Resource(name="bamountApplyService")
	private BamountApplyService bamountApplyService;
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	private String result = ResultMsg.GRID_NODATA;
	@Resource(name="bussProccFlowService")
	private BussProccFlowService bussProccFlowService;
	
	/**
	 * 获取 汇票回款单表 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = bamountApplyService.getResultList(map);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	public String listBam()throws Exception {
		try {
			String name=getVal("name");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("name", name);
			DataTable dt = bamountApplyService.getLoanRecordsList(map, getStart(), getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	public String listAll()throws Exception {
		try {
//			Long formid=getLVal("formid");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
//			map.put("formid", formid);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = bamountApplyService.getResultList(map,getStart(),getLimit());
//			List<Object> dtL = dt.getDataSource();
//			Map<String, Object> value=new HashMap<String,Object>();
//			value.put("jsonData", dtL);
//			bamountApplyService.doComplexBusss(value);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	 * 获取 展期审批 一览表
	 * @return		
	 * @throws Exception
	 */
	public String  auditAll()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			DataTable dt = bamountApplyService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
 * 获取 历史 列表
 * @return		
 * @throws Exception
 */
public String auditHistory()throws Exception {
	try {
		UserEntity user = this.getCurUser();
		SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
		map.put(SysConstant.USER_KEY, user);
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2);
		String procIds = bussProccFlowService.getProcIdsByUser(user);
		if(StringHandler.isValidStr(procIds)){
			map.put("procIds", procIds);
		}
		DataTable dt = bamountApplyService.getResultList(map,getStart(),getLimit());
		result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
 * 获取 汇票回款单表 详情
 * @return
 * @throws Exception
 */
public String getDetil()throws Exception {
	try {
		Long id = getLVal("id");
		if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
		SHashMap<String, Object> map =new SHashMap<String, Object>();
		map.put("id", id);
		DataTable dt = bamountApplyService.getResultList(map,-1,-1);
//		result = FastJsonUtil.convertJsonToStr(entity);
		result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonObjStr();
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
	 * 获取 汇票回款单表 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			BamountApplyEntity entity = bamountApplyService.getEntity(Long.parseLong(id));
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
	 * 保存 汇票回款单表 
	 * @return
	 * @throws Exception
	 */
	
	public String save()throws Exception {
		try {
			BamountApplyEntity entity = BeanUtil.copyValue(BamountApplyEntity.class,getRequest());
			bamountApplyService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(ResultMsg.SAVE_SUCCESS);
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
	 * 新增  汇票回款单表 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = bamountApplyService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("B", num);
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
	 * 删除  汇票回款单表 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  汇票回款单表 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  汇票回款单表 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  汇票回款单表 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			bamountApplyService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 汇票回款单表 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BamountApplyEntity entity = bamountApplyService.navigationPrev(params);
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
	 * 获取委托合同申请单的 详情
	 * @return	./fuEntrustContract_detail.action
	 * @throws Exception
	 */
	
	public String detail()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			DataTable dtResult = bamountApplyService.detail(id);
			//breed,procId
			String breed = dtResult.getString("breed");
			String pdid = null;
			if(StringHandler.isValidStr(breed)){
				List<BussProccEntity> bussProccList = BussProccCache.getBussProccs(breed);
				if(null != bussProccList && bussProccList.size() > 0){
					BussProccEntity bussProccEntity = bussProccList.get(0);
					pdid = bussProccEntity.getPdid();
					dtResult.appendData("pdid", new Object[]{pdid});
				}
			}
			String procId = dtResult.getString("procId");
			if(StringHandler.isValidStr(pdid) || StringHandler.isValidStr(procId)){
				final JSONObject bussFormDatas = getBussFormDatas(id, pdid,procId);
				if(null != bussFormDatas && bussFormDatas.size() > 0){/*流程业务表单*/
					dtResult.appendData("bussFormDatas", new Object[]{bussFormDatas});
				}
			}
			result = dtResult.getJsonObjStr();
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
	 * 获取指定的 汇票回款单表 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BamountApplyEntity entity = bamountApplyService.navigationNext(params);
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
	/**
	 * 获取 展期审批待办 列表
	 * @return		
	 * @throws Exception
	 */
	public String auditlist()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			DataTable dt = bamountApplyService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	 * 获取业务表单菜单数据
	 * @param id
	 * @param pdid
	 * @param procId
	 * @return
	 * @throws ServiceException
	 */
	private JSONObject getBussFormDatas(Long id, String pdid, String procId)
			throws ServiceException {
		SHashMap<String, Object> formParams = new SHashMap<String, Object>();
		formParams.put("pdid", pdid);
		formParams.put("procId", procId);
		formParams.put(SysConstant.USER_KEY, this.getCurUser());
		formParams.put("bussType", SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY);
		formParams.put("bussCode", SysCodeConstant.BUSS_PROCC_CODE_EXTENSION);
		formParams.put("formId", id);
		final JSONObject bussFormDatas = formCfgService.getBussFormDatas(formParams);
		return bussFormDatas;
	}
	
	
	
}
