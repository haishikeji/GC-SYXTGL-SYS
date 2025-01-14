package org.jeecg.common.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 流转条件
 *
 * @@author 品讯科技
 * @Date: 2019/8/23 15:39
 */
@Data
public class FlowCondition implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 天数
     */
    private Integer days;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 人数
     */
    private Integer peopleNum;

    /**
     * 预算 0预算内,1预算外
     */
    private String budget;

    /**
     * 固定资产 0非固定资产 ,1固定资产
     */
    private String fixAsset;

    /**
     * 单价超出限制 0未超出限制
     */
    private Integer unitPriceLimit;
    /**
     * 文稿类型
     */
    private String noticeType;

    /**
     * 发布端口
     */
    private String noticePlatform;

    /**
     * 维修人
     */
    private String repairsUser;

    /**
     * 维修部门
     */
    private String repairsDept;

    /**
     * 参会员工
     */
    private List eParticipates;

    /**
     * 采购人
     */
    private String purchaser;

    /**
     * 采购部门主任
     */
    private String purchasingDirector;

    /**
     * 采购部分管领导
     */
    private String procurementLeader;

    /**
     * 领取的物品的状态
     */
    private String goodsStatus;

    /**
     * 采购单价是否大于预算单价
     * 1 是
     *  2 否
     */
    private Integer purunitByBudPrice;

    /**
     * 部门集合
     */
//    private List<BudgetDeptDto> deptDtos;

}
