package com.bohai.dataCenter.entity;

import java.util.Date;

public class CrmMarketer {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.MARKETER_NO
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String marketerNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.MARKETER_NAME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String marketerName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.DEP_CODE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String depCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.DEP_NAME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String depName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.PERSONNEL_DIVISION
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String personnelDivision;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.STATUS
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.ENTRY_DATE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String entryDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.LEAVE_DATE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String leaveDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.ALLOCATION_PROPORTION
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String allocationProportion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.MARKETER_LEVEL
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String marketerLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.CERT_NO
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String certNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.TELEPHONE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String telephone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.ADDRESS
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.EMAIL
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.REMARK
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.CREATE_TIME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.UPDATE_TIME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.CERT_TYPE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String certType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_CRM_MARKETER.BIRETHDAY
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    private String birethday;

    private String deleteFlag;
    
    
    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.MARKETER_NO
     *
     * @return the value of T_CRM_MARKETER.MARKETER_NO
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getMarketerNo() {
        return marketerNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.MARKETER_NO
     *
     * @param marketerNo the value for T_CRM_MARKETER.MARKETER_NO
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setMarketerNo(String marketerNo) {
        this.marketerNo = marketerNo == null ? null : marketerNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.MARKETER_NAME
     *
     * @return the value of T_CRM_MARKETER.MARKETER_NAME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getMarketerName() {
        return marketerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.MARKETER_NAME
     *
     * @param marketerName the value for T_CRM_MARKETER.MARKETER_NAME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setMarketerName(String marketerName) {
        this.marketerName = marketerName == null ? null : marketerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.DEP_CODE
     *
     * @return the value of T_CRM_MARKETER.DEP_CODE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getDepCode() {
        return depCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.DEP_CODE
     *
     * @param depCode the value for T_CRM_MARKETER.DEP_CODE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.DEP_NAME
     *
     * @return the value of T_CRM_MARKETER.DEP_NAME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getDepName() {
        return depName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.DEP_NAME
     *
     * @param depName the value for T_CRM_MARKETER.DEP_NAME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.PERSONNEL_DIVISION
     *
     * @return the value of T_CRM_MARKETER.PERSONNEL_DIVISION
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getPersonnelDivision() {
        return personnelDivision;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.PERSONNEL_DIVISION
     *
     * @param personnelDivision the value for T_CRM_MARKETER.PERSONNEL_DIVISION
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setPersonnelDivision(String personnelDivision) {
        this.personnelDivision = personnelDivision == null ? null : personnelDivision.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.STATUS
     *
     * @return the value of T_CRM_MARKETER.STATUS
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.STATUS
     *
     * @param status the value for T_CRM_MARKETER.STATUS
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.ENTRY_DATE
     *
     * @return the value of T_CRM_MARKETER.ENTRY_DATE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.ENTRY_DATE
     *
     * @param entryDate the value for T_CRM_MARKETER.ENTRY_DATE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate == null ? null : entryDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.LEAVE_DATE
     *
     * @return the value of T_CRM_MARKETER.LEAVE_DATE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getLeaveDate() {
        return leaveDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.LEAVE_DATE
     *
     * @param leaveDate the value for T_CRM_MARKETER.LEAVE_DATE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate == null ? null : leaveDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.ALLOCATION_PROPORTION
     *
     * @return the value of T_CRM_MARKETER.ALLOCATION_PROPORTION
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getAllocationProportion() {
        return allocationProportion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.ALLOCATION_PROPORTION
     *
     * @param allocationProportion the value for T_CRM_MARKETER.ALLOCATION_PROPORTION
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setAllocationProportion(String allocationProportion) {
        this.allocationProportion = allocationProportion == null ? null : allocationProportion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.MARKETER_LEVEL
     *
     * @return the value of T_CRM_MARKETER.MARKETER_LEVEL
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getMarketerLevel() {
        return marketerLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.MARKETER_LEVEL
     *
     * @param marketerLevel the value for T_CRM_MARKETER.MARKETER_LEVEL
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setMarketerLevel(String marketerLevel) {
        this.marketerLevel = marketerLevel == null ? null : marketerLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.CERT_NO
     *
     * @return the value of T_CRM_MARKETER.CERT_NO
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getCertNo() {
        return certNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.CERT_NO
     *
     * @param certNo the value for T_CRM_MARKETER.CERT_NO
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.TELEPHONE
     *
     * @return the value of T_CRM_MARKETER.TELEPHONE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.TELEPHONE
     *
     * @param telephone the value for T_CRM_MARKETER.TELEPHONE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.ADDRESS
     *
     * @return the value of T_CRM_MARKETER.ADDRESS
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.ADDRESS
     *
     * @param address the value for T_CRM_MARKETER.ADDRESS
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.EMAIL
     *
     * @return the value of T_CRM_MARKETER.EMAIL
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.EMAIL
     *
     * @param email the value for T_CRM_MARKETER.EMAIL
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.REMARK
     *
     * @return the value of T_CRM_MARKETER.REMARK
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.REMARK
     *
     * @param remark the value for T_CRM_MARKETER.REMARK
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.CREATE_TIME
     *
     * @return the value of T_CRM_MARKETER.CREATE_TIME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.CREATE_TIME
     *
     * @param createTime the value for T_CRM_MARKETER.CREATE_TIME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.UPDATE_TIME
     *
     * @return the value of T_CRM_MARKETER.UPDATE_TIME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.UPDATE_TIME
     *
     * @param updateTime the value for T_CRM_MARKETER.UPDATE_TIME
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.CERT_TYPE
     *
     * @return the value of T_CRM_MARKETER.CERT_TYPE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getCertType() {
        return certType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.CERT_TYPE
     *
     * @param certType the value for T_CRM_MARKETER.CERT_TYPE
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setCertType(String certType) {
        this.certType = certType == null ? null : certType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_CRM_MARKETER.BIRETHDAY
     *
     * @return the value of T_CRM_MARKETER.BIRETHDAY
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public String getBirethday() {
        return birethday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_CRM_MARKETER.BIRETHDAY
     *
     * @param birethday the value for T_CRM_MARKETER.BIRETHDAY
     *
     * @mbggenerated Fri May 12 13:44:15 CST 2017
     */
    public void setBirethday(String birethday) {
        this.birethday = birethday == null ? null : birethday.trim();
    }
}