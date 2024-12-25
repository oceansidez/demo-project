<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>t_coupon列表</title>
    <link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/static/common/js/jquery.pager.js"></script>
    <script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
    <script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
</head>
<body class="list">
<div class="bar">
    t_coupon列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
</div>
<div class="body">
    <form id="listForm" action="${base}/coupon/list" method="get">
        <div class="listBar">
            <input type="button" class="formButton" onclick="location.href='${base}/coupon/add'" value="添加t_coupon"
                   hidefocus/>
            &nbsp;&nbsp;
            <label>查找:</label>
            &nbsp;&nbsp;
            <input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus/>
            &nbsp;&nbsp;
            <label>每页显示: </label>
            <select name="pageSize" id="pageSize">
                <option value="10"<#if pager.pageSize == 10> selected</#if>>
                    10
                </option>
                <option value="20"<#if pager.pageSize == 20> selected</#if>>
                    20
                </option>
                <option value="50"<#if pager.pageSize == 50> selected</#if>>
                    50
                </option>
                <option value="100"<#if pager.pageSize == 100> selected</#if>>
                    100
                </option>
            </select>
        </div>
        <div class="listTable_outer">
            <table id="listTable" class="listTable">
                <tr class="noHover">
                    <th class="check">
                        <input type="checkbox" class="allCheck"/>
                    </th>
                    <th>
                        <a href="#" name="isDelete" hidefocus>删除 1.yes 0.no</a>
                    </th>
                    <th>
                        <a href="#" name="couponName" hidefocus>卡券名称</a>
                    </th>
                    <th>
                        <a href="#" name="couponLogoPath" hidefocus>卡券logo</a>
                    </th>
                    <th>
                        <a href="#" name="creatorId" hidefocus>创建人 id</a>
                    </th>
                    <th>
                        <a href="#" name="creatorName" hidefocus>创建人 name</a>
                    </th>
                    <th>
                        <a href="#" name="creatorType" hidefocus>创建人 type 例如：管理员创建，商户创建</a>
                    </th>
                    <th>
                        <a href="#" name="couponIssueStartDate" hidefocus>发券时间-起始时间</a>
                    </th>
                    <th>
                        <a href="#" name="couponIssueEndDate" hidefocus>发券时间-终止时间</a>
                    </th>
                    <th>
                        <a href="#" name="couponType" hidefocus>卡券类型 1.满减券 2.折扣券 3.预约券 4.代金券 5.满反券 6.翼支付券</a>
                    </th>
                    <th>
                        <a href="#" name="couponDesc" hidefocus>卡券说明</a>
                    </th>
                    <th>
                        <a href="#" name="couponUseRules" hidefocus>卡券使用规则</a>
                    </th>
                    <th>
                        <a href="#" name="couponEffectiveTimeDate" hidefocus>有效时间-在某一时间之前都是有效的
                            例如：2021.12.31日，表示都是有效的</a>
                    </th>
                    <th>
                        <a href="#" name="couponEffectiveTimeDay" hidefocus>领取时间-在领取后的 effective_time_day 天里面都是有效的 </a>
                    </th>
                    <th>
                        <a href="#" name="couponGetRules" hidefocus>领取规则 1.按有效时间 0.按领取时间</a>
                    </th>
                    <th>
                        <a href="#" name="couponAmount" hidefocus>卡券价值金额</a>
                    </th>
                    <th>
                        <a href="#" name="couponBuyAmount" hidefocus>购买卡券需要的金额</a>
                    </th>
                    <th>
                        <a href="#" name="enabled" hidefocus>是否上架 1.yes 0.no</a>
                    </th>
                    <th>
                        <a href="#" name="totalStock" hidefocus>总库存</a>
                    </th>
                    <th>
                        <a href="#" name="totalUse" hidefocus>总使用(核销)</a>
                    </th>
                    <th>
                        <a href="#" name="saveQuantity" hidefocus>最后一次库存数量</a>
                    </th>
                    <th>
                        <a href="#" name="isInformStore" hidefocus>是否已提醒库存预警</a>
                    </th>
                    <th>
                        <a href="#" name="checkId" hidefocus>审核人id</a>
                    </th>
                    <th>
                        <a href="#" name="checkName" hidefocus>审核人 name</a>
                    </th>
                    <th>
                        <a href="#" name="checkStatus" hidefocus>审核状态 1.未审核 2.审核通过 3.未通过</a>
                    </th>
                    <th>
                        <a href="#" name="checkDate" hidefocus>审核时间</a>
                    </th>
                    <th>
                        <a href="#" name="lastCheckTotalQuantity" hidefocus>审核通过时的总库存</a>
                    </th>
                    <th>
                        <a href="#" name="isTemplate" hidefocus>是否模板 1.yes 0.no</a>
                    </th>
                    <th>
                        <span>操作</span>
                    </th>
                </tr>
                <#list pager.result as coupon>
                    <tr>
                        <td>
                            <input type="checkbox" name="ids" value="${coupon.id}"/>
                        </td>
                        <td>
                            ${(coupon.isDelete)!}
                        </td>
                        <td>
                            ${(coupon.couponName)!}
                        </td>
                        <td>
                            ${(coupon.couponLogoPath)!}
                        </td>
                        <td>
                            ${(coupon.creatorId)!}
                        </td>
                        <td>
                            ${(coupon.creatorName)!}
                        </td>
                        <td>
                            ${(coupon.creatorType)!}
                        </td>
                        <td>
                            <span title="${coupon.couponIssueStartDate?string("yyyy-MM-dd HH:mm")}">${coupon.couponIssueStartDate?string("yyyy-MM-dd HH:mm")}</span>
                        </td>
                        <td>
                            <span title="${coupon.couponIssueEndDate?string("yyyy-MM-dd HH:mm")}">${coupon.couponIssueEndDate?string("yyyy-MM-dd HH:mm")}</span>
                        </td>
                        <td>
                            ${(coupon.couponType)!}
                        </td>
                        <td>
                            ${(coupon.couponDesc)!}
                        </td>
                        <td>
                            ${(coupon.couponUseRules)!}
                        </td>
                        <td>
                            <span title="${coupon.couponEffectiveTimeDate?string("yyyy-MM-dd HH:mm")}">${coupon.couponEffectiveTimeDate?string("yyyy-MM-dd HH:mm")}</span>
                        </td>
                        <td>
                            ${(coupon.couponEffectiveTimeDay)!}
                        </td>
                        <td>
                            ${(coupon.couponGetRules)!}
                        </td>
                        <td>
                            ${(coupon.couponAmount)!}
                        </td>
                        <td>
                            ${(coupon.couponBuyAmount)!}
                        </td>
                        <td>
                            ${(coupon.enabled)!}
                        </td>
                        <td>
                            ${(coupon.totalStock)!}
                        </td>
                        <td>
                            ${(coupon.totalUse)!}
                        </td>
                        <td>
                            ${(coupon.saveQuantity)!}
                        </td>
                        <td>
                            ${(coupon.isInformStore)!}
                        </td>
                        <td>
                            ${(coupon.checkId)!}
                        </td>
                        <td>
                            ${(coupon.checkName)!}
                        </td>
                        <td>
                            ${(coupon.checkStatus)!}
                        </td>
                        <td>
                            <span title="${coupon.checkDate?string("yyyy-MM-dd HH:mm")}">${coupon.checkDate?string("yyyy-MM-dd HH:mm")}</span>
                        </td>
                        <td>
                            ${(coupon.lastCheckTotalQuantity)!}
                        </td>
                        <td>
                            ${(coupon.isTemplate)!}
                        </td>
                        <td>
                            <a href="${base}/coupon/view/${coupon.id}" title="查看">[查看]</a>
                            <a href="${base}/coupon/edit/${coupon.id}" title="编辑">[编辑]</a>
                        </td>
                    </tr>
                </#list>
            </table>
            <#if (pager.result?size > 0)>
            <div class="pagerBar">
                <div class="delete">
                    <input type="button" id="deleteButton" class="formButton" url="${base}/coupon/delete" value="删 除"
                           disabled hidefocus/>
                </div>
                <div class="pager">
                    <#include "pager.ftl" />
                </div>
                <div>
                    <#else>
                        <div class="noRecord">没有找到任何记录!</div>
                    </#if>
                </div>
    </form>
</div>
</body>
</html>