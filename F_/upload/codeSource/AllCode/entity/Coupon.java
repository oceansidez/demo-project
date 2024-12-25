package com.telecom.entity;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Table;

import com.telecom.base.BaseEntity;

@Table(name = "t_coupon")
public class Coupon extends BaseEntity {

    private static final long serialVersionUID = -1L;

    private Boolean isDelete;// 删除 1.yes 0.no
    private String couponName;// 卡券名称
    private String couponLogoPath;// 卡券logo
    private String creatorId;// 创建人 id
    private String creatorName;// 创建人 name
    private String creatorType;// 创建人 type 例如：管理员创建，商户创建
    private Date couponIssueStartDate;// 发券时间-起始时间
    private Date couponIssueEndDate;// 发券时间-终止时间
    private String couponType;// 卡券类型 1.满减券 2.折扣券 3.预约券 4.代金券 5.满反券 6.翼支付券
    private String couponDesc;// 卡券说明
    private String couponUseRules;// 卡券使用规则
    private Date couponEffectiveTimeDate;// 有效时间-在某一时间之前都是有效的 例如：2021.12.31日，表示都是有效的
    private Integer couponEffectiveTimeDay;// 领取时间-在领取后的 effective_time_day 天里面都是有效的 
    private Boolean couponGetRules;// 领取规则 1.按有效时间 0.按领取时间
    private BigDecimal couponAmount;// 卡券价值金额
    private BigDecimal couponBuyAmount;// 购买卡券需要的金额
    private Boolean enabled;// 是否上架 1.yes 0.no
    private Integer totalStock;// 总库存
    private Integer totalUse;// 总使用(核销)
    private Integer saveQuantity;// 最后一次库存数量
    private Boolean isInformStore;// 是否已提醒库存预警
    private String checkId;// 审核人id
    private String checkName;// 审核人 name
    private Integer checkStatus;// 审核状态 1.未审核 2.审核通过 3.未通过
    private Date checkDate;// 审核时间
    private Integer lastCheckTotalQuantity;// 审核通过时的总库存
    private Boolean isTemplate;// 是否模板 1.yes 0.no

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponLogoPath() {
        return couponLogoPath;
    }

    public void setCouponLogoPath(String couponLogoPath) {
        this.couponLogoPath = couponLogoPath;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(String creatorType) {
        this.creatorType = creatorType;
    }

    public Date getCouponIssueStartDate() {
        return couponIssueStartDate;
    }

    public void setCouponIssueStartDate(Date couponIssueStartDate) {
        this.couponIssueStartDate = couponIssueStartDate;
    }

    public Date getCouponIssueEndDate() {
        return couponIssueEndDate;
    }

    public void setCouponIssueEndDate(Date couponIssueEndDate) {
        this.couponIssueEndDate = couponIssueEndDate;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public String getCouponUseRules() {
        return couponUseRules;
    }

    public void setCouponUseRules(String couponUseRules) {
        this.couponUseRules = couponUseRules;
    }

    public Date getCouponEffectiveTimeDate() {
        return couponEffectiveTimeDate;
    }

    public void setCouponEffectiveTimeDate(Date couponEffectiveTimeDate) {
        this.couponEffectiveTimeDate = couponEffectiveTimeDate;
    }

    public Integer getCouponEffectiveTimeDay() {
        return couponEffectiveTimeDay;
    }

    public void setCouponEffectiveTimeDay(Integer couponEffectiveTimeDay) {
        this.couponEffectiveTimeDay = couponEffectiveTimeDay;
    }

    public Boolean getCouponGetRules() {
        return couponGetRules;
    }

    public void setCouponGetRules(Boolean couponGetRules) {
        this.couponGetRules = couponGetRules;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getCouponBuyAmount() {
        return couponBuyAmount;
    }

    public void setCouponBuyAmount(BigDecimal couponBuyAmount) {
        this.couponBuyAmount = couponBuyAmount;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
    }

    public Integer getTotalUse() {
        return totalUse;
    }

    public void setTotalUse(Integer totalUse) {
        this.totalUse = totalUse;
    }

    public Integer getSaveQuantity() {
        return saveQuantity;
    }

    public void setSaveQuantity(Integer saveQuantity) {
        this.saveQuantity = saveQuantity;
    }

    public Boolean getIsInformStore() {
        return isInformStore;
    }

    public void setIsInformStore(Boolean isInformStore) {
        this.isInformStore = isInformStore;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getLastCheckTotalQuantity() {
        return lastCheckTotalQuantity;
    }

    public void setLastCheckTotalQuantity(Integer lastCheckTotalQuantity) {
        this.lastCheckTotalQuantity = lastCheckTotalQuantity;
    }

    public Boolean getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(Boolean isTemplate) {
        this.isTemplate = isTemplate;
    }


}
