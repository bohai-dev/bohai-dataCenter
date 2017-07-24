package com.bohai.dataCenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohai.dataCenter.entity.SysUser;
import com.bohai.dataCenter.persistence.SysUserMapper;
import com.bohai.dataCenter.service.UserService;
import com.bohai.dataCenter.vo.PaginationParamVO;
import com.bohai.dataCenter.vo.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public List<UserVO> queryUsersPagination(PaginationParamVO paramVO) {
		
		
		List<SysUser> list = this.sysUserMapper.queryUsers(null);
		
		List<UserVO> userVOs = new ArrayList<>();
		for(SysUser user:list){
			UserVO userVO = new UserVO();
			userVO.setUsername(user.getUsername());
			userVO.setPassword(user.getPassword());
			userVO.setCreateTime(user.getCreateTime());
			userVO.setUpdateTime(user.getUpdateTime());
			userVO.setId(user.getId());
			userVO.setFullName(user.getFullName());
			if(user.getLocked() != null){
				String locked = user.getLocked().equals("0")?"正常":"锁定";
				userVO.setLocked(locked);
			}
			userVOs.add(userVO);
		}
		
		return userVOs;
	}

}
