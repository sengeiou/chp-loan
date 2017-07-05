<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>数据_待还款划扣已办</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/payback/buckleHasBeenDone.js"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script src='${context}/js/common.js' type="text/javascript"></script>
</head>
<body>
    <div style="position:fixed;left:0;top:0;right:0;">
	<div class="control-group">
	<form id="auditForm" method="post">
		<input type="hidden" id="idVal" name="idVal" >
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
        	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input  name="menuId"  id ="menuId" type="hidden" value="${menuId}"/> 
            <tr>
                <td><label class="lab">客户姓名：</label><input value="${PaybackApply.customerName }" type="text" name="customerName" class="input_text178"></input></td>
                <td><label class="lab">合同编号：</label><input value="${PaybackApply.contractCode }" type="text" name="contractCode" class="input_text178"></input></td>
                <td><label class="lab">划扣平台：</label>
                	  <sys:multirePlatform platClick="platClick"  platId="platId" platName ='platName'></sys:multirePlatform>
			                <input id="platName" type="text" class="input_text178" name="dictDealTypeName"  value='${PaybackApply.dictDealTypeName}'>&nbsp;
			                <i id="platClick" class="icon-search" style="cursor: pointer;"></i> 
					  <input type="hidden" id="platId" name='dictDealTypeId' value='${PaybackApply.dictDealTypeId}'>
                </td>
            </tr>
            <tr>
                <td><label class="lab">门店：</label>
                    <input id="txtStore" name="storesName" type="text" maxlength="20" class="txt date input_text178" value="${PaybackApply.storesName }" /> 
					<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore" name= "stores" value="${PaybackApply.stores}">
					
					<input type="hidden" id="urgeManage" name= "urgeManage" value="${urgeManage}">
                </td>
                <td>
                	<label class="lab">还款日：</label>
				    <sys:multirepaymentDate dateClick="dateClick" dateId="repayDate"></sys:multirepaymentDate>
				    <input id="repayDate"  name="paybackDay" class="input_text178" readonly ="readonly"   value="${PaybackApply.paybackDay}">
				    <i id="dateClick" class="icon-search" style="cursor: pointer;"></i> 
                </td>
                <td><label class="lab">实还金额：</label><input value="${PaybackApply.alsoAmountOne }" onblur = "isDigit(this)" type="number" name="alsoAmountOne" class="input_text70">-<input  type="number" onblur = "isDigit(this)" name="alsoAmountTwo" class="input_text70" value="${PaybackApply.alsoAmountTwo}">
                  </td>
            </tr>
            <tr id="T1" style="display:none">
                <td><label class="lab">来源系统：</label><select class="select180" name="dictSourceType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${PaybackApply.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                              </c:forEach>
                	</select>
                </td>
                <td><label class="lab">渠道：</label>
                	<sys:multipleMark markClick="selectMarkBtn" markName="search_loanMarkLabel" markId="loanMark"></sys:multipleMark>
				<input id="search_loanMarkLabel" type="text" class="input_text178" name="loanMarkLabel"  value='${PaybackApply.loanMarkLabel}'>&nbsp;
				<i id="selectMarkBtn" class="icon-search" style="cursor: pointer;"></i> 
				<input type="hidden" id="loanMark" name='loanMark' value='${PaybackApply.loanMark}'>
                </td>
                <td><label class="lab">回盘结果：</label><select class="select180" name="splitBackResult">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_counteroffer_result')}" var="splitBackResult">
                                   <c:if test="${splitBackResult.value ==2 || splitBackResult.value ==1 || splitBackResult.value ==3 || splitBackResult.value ==6 || splitBackResult.value ==8}">
                                   <option value="${splitBackResult.value }" <c:if test="${PaybackApply.splitBackResult == splitBackResult.value }">selected</c:if>>${splitBackResult.label }</option>
                                  </c:if>
                              </c:forEach>
                	</select>
                </td>
            </tr>
            <tr id="T2" style="display:none">
                 <td><label class="lab">开户行名称：</label>
                 <sys:multipleBank bankClick="selectBankBtn" bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
                 <input id="search_applyBankName" type="text" class="input_text178" name="applyBankName"  value='${PaybackApply.applyBankName}'>&nbsp;<i id="selectBankBtn"
	         	 class="icon-search" style="cursor: pointer;"></i> 
		        <input type="hidden" id="bankId" name='bank' value='${PaybackApply.bank}'>
                </td>
                <td><label class="lab">还款状态：</label><select class="select180" name="dictPayStatus">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_repay_status')}" var="dictPayStatus">
                                   <option value="${dictPayStatus.value }" <c:if test="${PaybackApply.dictPayStatus == dictPayStatus.value }">selected</c:if>>${dictPayStatus.label }</option>
                              </c:forEach>
                	</select>
                </td>
				 <td><label class="lab">划扣日期：</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${PaybackApply.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>-<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${PaybackApply.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</label>
				 </td>
            </tr>
            <tr id="T3" style="display:none">
					<td><label class="lab">失败原因：</label> <input type="text"
							name="failReason" class="input_text178"
							value="${PaybackApply.failReason }" />
					</td>
					<td>
					  <label class="lab">模式：</label>
					 	<select class="select180" name="model">
							<option value="">全部</option>
						 	<c:forEach items="${fns:getDictList('jk_loan_model')}" var="card_type">
								<option value="${card_type.value }"
								<c:if test="${PaybackApply.model==card_type.value }">selected</c:if>>
								<c:if test="${card_type.value=='1' }">${card_type.label}</c:if>
								<c:if test="${card_type.value!='1' }">非TG</c:if>
								</option>
				             </c:forEach>
						</select>
					</td>
            </tr>
        </table>
	        <div class="tright pr30 pb5">
	        		<button class="btn btn-primary" id="searchBtn">搜索</button>
				<button  class="btn btn-primary" id="clearBtn">清除</button>
			 	<div style="float:left;margin-left:45%;padding-top:10px">
				 <img src="${context}/static/images/up.png" id="showMore" onclick="javascript:show();"></img>
				</div>
            </div>
          </form>
      
      </div>
    <p class="mb5">
        <c:if test="${jkhj_hkhkyb_dcebtn==null }">
    	<button class="btn btn-small" name="exportExcel">导出EXCEL</button>
    	</c:if>
    	<span class="red">划扣总金额:</span>
		<span class="red" id="total_money">${numTotal.total}</span>&nbsp;
		<span class="red">划扣总笔数:</span>
		<span class="red" id="total_num">${numTotal.num}</span>
    </p>
    <sys:columnCtrl pageToken="buckleHaBeen"></sys:columnCtrl>
    <div  class="box5">
    <table id="tableList" class="table table-hover  table-bordered table-condensed  "  style="width:100%;empty-cells:show; 
         border-collapse: collapse;
         margin:0 auto;">
         <thead>
        <tr>
            <th><input type="checkbox" id="checkAll"/>全选</th>
            <th>合同编号</th>
			<th>客户姓名</th>
			<th>门店名称</th>
			<th>批借期数</th>
			<th>首期还款日</th>
			<th>客户开户行</th>
			<th>还款状态</th>
			<th>还款方式</th>
			<th>申请还款金额</th>
			<th>实还金额</th>
			<th>还款类型 </th>
			<th>划扣日期</th>
			<th>还款日</th>
			<th>借款状态</th>
			<th>划扣平台</th>
			<th>回盘结果</th>
			<th>失败原因</th>
			<th>蓝补金额</th>
			<th>模式</th>
			<th>渠道</th>
			<th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${waitPage.list}" var="item" varStatus="num">
	        <tr>
	        	<td><input type="checkbox" name="checkBox" value="${item.id}" />
	        	    <input type="hidden"  value="${item.applyReallyAmount}" />
	        	</td>
	        	<td>${item.contractCode}</td>
	        	<td>${item.customerName}</td>
	        	<td>${item.orgName}</td>
	        	<td>${item.contractMonths}</td>
	        	<td><fmt:formatDate value="${item.contractReplayDay}" type="date"/></td>
	        	<td>${item.applyBankNameLabel}</td>
	        	<td>${item.dictPayStatusLabel}</td>
	        	<td>${item.dictRepayMethodLabel}</td>
	        	<td><fmt:formatNumber value='${item.applyAmount}' pattern="0.00"/></td>
	        	<td><fmt:formatNumber value='${item.applyReallyAmount}' pattern="0.00"/></td>
	        	<td>划扣</td>
	        	<td><fmt:formatDate value="${item.modifyTime}" type="date"/></td>
	        	<td>${item.paybackDay}</td>
	        	<td>${item.dictLoanStatusLabel}</td>
	        	<td>${item.dictDealTypeLabel}</td>
	        	<td>${item.splitBackResultLabel}</td>
	        	<td>${item.failReason}</td>
	        	<td><fmt:formatNumber value='${item.paybackBuleAmount}' pattern="0.00"/></td>
	        	<td>${item.modelLabel}</td>
	        	<td>${item.loanMarkLabel}</td>
	        	<td>
	        	<c:if test="${jkhj_hkhkyb_ckbtn==null }">
	        	<input type="button" name="seeBuckHasBeen" class="btn_edit" value="查看" />
	        	</c:if>
					<input type="hidden" value="${item.id}" />
				<c:if test="${jkhj_hkhkyb_lsbtn==null }">
        		<input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('','${item.id}','')" value="历史" />
        		</c:if>
        		<c:if test="${jkhj_hkhkyb_lsbtn==null }">
        		<input type="button" class="btn_edit" onclick="showPaybackHis('','${item.id}','lisi');" value="已拆分历史" />
        	     </c:if>
	        	</td>
	        </tr>
        </c:forEach>
        <c:if test="${waitPage.list==null || fn:length(waitPage.list)==0}">
			<tr>
				<td colspan="20" style="text-align: center;">没有符合条件的数据</td>
			</tr>
		</c:if>
    </table>
    <br>
    <br>
    </div>
		<div class="pagination">${waitPage}</div>

    </div>
    <script type="text/javascript">
    $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
  $('#tableList').bootstrapTable({
  method: 'get',
  cache: false,
  height: $(window).height()-280,

  pageSize: 20,
  pageNumber:1
  });
  $(window).resize(function () {
  $('#tableList').bootstrapTable('resetView');
  }); 
 </script>
</body>
</html>