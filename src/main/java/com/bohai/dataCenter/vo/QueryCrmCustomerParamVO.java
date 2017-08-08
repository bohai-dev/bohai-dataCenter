package com.bohai.dataCenter.vo;

/**
 * 查询CRM客户基本信息入参
 * @author caojia
 *
 */
public class QueryCrmCustomerParamVO extends PaginationParamVO{
    
    private String deptCode;
    
    private String investorNo;
    
    private String investorName;
    
    private String belongType;
    
    private String belongTo;

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

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

    public String getBelongType() {
        return belongType;
    }

    public void setBelongType(String belongType) {
        this.belongType = belongType;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }
    
}
