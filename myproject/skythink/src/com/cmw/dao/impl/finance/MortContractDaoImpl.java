package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.finance.MortContractEntity;
import com.cmw.dao.inter.finance.MortContractDaoInter;


/**
 * 抵押合同  DAO实现类
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
@Description(remark="抵押合同DAO实现类",createDate="2013-01-11T00:00:00",author="pdh")
@Repository("mortContractDao")
public class MortContractDaoImpl extends GenericDaoAbs<MortContractEntity, Long> implements MortContractDaoInter {

}
