package com.cmw.service.inter.funds;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.funds.BamountApplyEntity;


/**
 * 承诺书附件表  Service接口
 * @author 郑符明
 * @date 2014-02-22T00:00:00
 */
@Description(remark="承诺书附件表业务接口",createDate="2014-02-22T00:00:00",author="郑符明")
public interface BamountApplyService extends IService<BamountApplyEntity, Long> {
	DataTable detail(Long id) throws ServiceException;
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
}
