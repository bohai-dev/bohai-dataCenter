package com.bohai.dataCenter.service;

import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.CrmMediator;

/**
 * 居间人接口
 * @author caojia
 *
 */
public interface CrmMediatorService {

    /**
     * 居间人变更
     * @param mediator
     * @throws BohaiException
     */
    public void modifyCrmMediator(CrmMediator mediator) throws BohaiException;
    
    public void removeCrmMediator(CrmMediator mediator) throws BohaiException;
}
