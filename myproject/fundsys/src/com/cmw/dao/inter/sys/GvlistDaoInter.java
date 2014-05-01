package com.cmw.dao.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.sys.GvlistEntity;


/**
 * 基础数据  DAO接口
 * @author 彭登浩
 * @date 2012-11-19T00:00:00
 */
 @Description(remark="基础数据Dao接口",createDate="2012-11-19T00:00:00",author="彭登浩")
public interface GvlistDaoInter  extends GenericDaoInter<GvlistEntity, Long>{
	 	/**
		 * 根据指定的参数 获取下拉框数据源
		 * @param <K>
		 * @param <V>
		 * @param map 过滤参数
		 * @return 返回符合条件的 DataTable 对象
		 * @throws DaoException  抛出DaoException 
		 */
		<K, V> DataTable getDataSource(SHashMap<K, V> map) throws DaoException;
		/**
		 * 获取贷款业务结构报表所需的统计基础数据
		 * @param map
		 * @return
		 * @throws DaoException
		 */
		<K, V> DataTable getReportDt(SHashMap<K, V> map) throws DaoException;
}
