package com.bohai.dataCenter.service;

import java.util.List;

import com.bohai.dataCenter.vo.PaginationParamVO;
import com.bohai.dataCenter.vo.UserVO;

public interface UserService {

	/**
	 * 分页查询用户信息
	 * @return
	 */
	public List<UserVO> queryUsersPagination(PaginationParamVO paramVO);
}
