package com.bohai.dataCenter.vo;

/**
 * 查询居间人产生的利润入参
 * @author caojia
 */
public class QueryMediatorProfitParamVO {
    
    /**
     * 居间人编号
     */
    private String mediatorNo;
    
    /**
     * 居间人名称
     */
    private String mediatorName;

    public String getMediatorNo() {
        return mediatorNo;
    }

    public void setMediatorNo(String mediatorNo) {
        this.mediatorNo = mediatorNo;
    }

    public String getMediatorName() {
        return mediatorName;
    }

    public void setMediatorName(String mediatorName) {
        this.mediatorName = mediatorName;
    }

    
    
}
