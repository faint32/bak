package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.PrepaymentRecordsEntity;


/**
 * 实收提前还款金额  DAO接口
 * @author 程明卫
 * @date 2013-11-03 T00:00:00
 */
 @Description(remark="实收提前还款金额Dao接口",createDate="2013-11-03 T00:00:00",author="程明卫")
public interface PrepaymentRecordsDaoInter  extends GenericDaoInter<PrepaymentRecordsEntity, Long>{
	 /**
	 * 获取逾期实收金额记录信息
	 * @param params
	 * @return
	 * @throws DaoException 
	 */
	public DataTable getLateRecords(SHashMap<String, Object> params) throws DaoException;
	 /**
	  * 根据实收金额日志ID获取放款数据
	  * @param params	过滤参数
	  * @param offset	分页的起始位置
	  * @param pageSize	每页大小
	  * @return	返回 DataTable 对象
	  * @throws DaoException
	  */
	 <K, V> DataTable getDsByAmountLogId(SHashMap<K, V> params, int offset, int pageSize)throws DaoException;
}
