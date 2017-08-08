package com.bohai.dataCenter.service;

import org.springframework.web.multipart.MultipartFile;

import com.bohai.dataCenter.controller.exception.BohaiException;

/**
 * 文件上传接口
 * @author caojia
 */
public interface FileUploadService {

	/**
	 * 上传文件
	 * @param file
	 */
	public void upload(MultipartFile file,Object... objects) throws BohaiException;
}
