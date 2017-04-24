package com.bohai.dataCenter.service;

/**
 * 文件上传抽象工厂
 * @author caojia
 */
public interface UploadFactory {

	FileUploadService createService(String fileName);
}
