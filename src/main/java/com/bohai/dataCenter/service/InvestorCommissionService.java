package com.bohai.dataCenter.service;

import com.bohai.dataCenter.entity.InvestorCommission;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Cteated by cxy on 2018/9/5
 */
public interface InvestorCommissionService {

    int insert(InvestorCommission commission);
    //解析表格
    void parseExcel(MultipartFile file,String startDate,String endDate) throws Exception;
}
