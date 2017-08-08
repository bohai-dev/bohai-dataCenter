package com.bohai.dataCenter.vo;

/**
 * 查询投资者产生的利润入参
 * @author caojia
 */
public class QueryInvestorProfitParamVO {
    
    /**
     * 投资者编号
     */
    private String investorNo;
    
    /**
     * 投资者名称
     */
    private String investorName;

    public String getInvestorNo() {
        return investorNo;
    }

    public void setInvestorNo(String investorNo) {
        this.investorNo = investorNo;
    }

    public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    
}
