package com.bohai.dataCenter.service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmMarketer;

/**
 * 营销人员接口
 * @author caojia
 *
 */
public interface CrmMarketerService {

    /**
     * 营销人员变更
     * @throws BohaiException
     */
    public void modifyCrmMarketer(CrmMarketer marketer) throws BohaiException;
    
    /**
     * 营销人员删除
     * @param marketer
     * @throws BohaiException
     */
    public void removeCrmMarketer(CrmMarketer marketer) throws BohaiException;
    
}
