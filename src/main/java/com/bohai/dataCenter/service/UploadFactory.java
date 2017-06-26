package com.bohai.dataCenter.service;

import com.bohai.dataCenter.controller.exception.BohaiException;

/**
 * 文件上传抽象工厂
 * @author caojia
 */
public interface UploadFactory {

	FileUploadService createService(String fileName) throws BohaiException;
}
