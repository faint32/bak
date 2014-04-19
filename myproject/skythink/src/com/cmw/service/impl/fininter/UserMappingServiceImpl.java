package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.fininter.UserMappingDaoInter;
import com.cmw.entity.fininter.UserMappingEntity;
import com.cmw.service.inter.fininter.UserMappingService;


/**
 * 用户帐号映射  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="用户帐号映射业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("userMappingService")
public class UserMappingServiceImpl extends AbsService<UserMappingEntity, Long> implements  UserMappingService {
	@Autowired
	private UserMappingDaoInter userMappingDao;
	@Override
	public GenericDaoInter<UserMappingEntity, Long> getDao() {
		return userMappingDao;
	}

}
