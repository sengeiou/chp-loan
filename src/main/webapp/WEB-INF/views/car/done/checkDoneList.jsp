<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借门店提交查账已办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  //选择门店
		 loan.getstorelsit("txtStore","storeName");
	 $('#searchBtn').bind('click',function(){
			// 查账日期验证触发事件,点击搜索进行验证
			var startDate = $("#checkDateStart").val();
			var endDate = $("#checkDateEnd").val();
			if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
				var arrStart = startDate.split("-");
				var arrEnd = endDate.split("-");
				var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
				var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
				if(sd.getTime()>ed.getTime()){   
					art.dialog.alert("查账开始日期不能大于查账结束日期!",function(){
						$("#checkDateStart").val("");
						$("#checkDateEnd").val("");
					});
					return false;     
				}else{
					page(1,${page.pageSize});
				}  
			}else{
				page(1,${page.pageSize});
			} 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');  
		 $("select").trigger("change");
	 });
	 $('#showMore').bind('click',function(){
		 show(); 
	 });
	 /* //全选，全部不选js
	  $("#checkAll").bind('click',function(){
		  if(this.checked == true){
			  $(".checks").attr('checked',true);
		  }else {
			  $(".checks").attr('checked',false);
		  }
	  }); */
	// 点击全选
		$("#checkAll").click(function(){
			var urgeDecuteMoeny=0.00;
			var deductNumber = 0;
			if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
			{
				$(":input[name='checkBoxItem']").each(function() {
					$(this).attr("checked",'true');
					urgeDecuteMoeny+=parseFloat($(this).attr("urgeDecuteMoeny"));
					deductNumber+=1;
					});
			}else{
				$(":input[name='checkBoxItem']").each(function() {
					$(this).removeAttr("checked");
				});
			}
			$("#deductAmount").text(fmoney(urgeDecuteMoeny,2));
			$("#totalNum").text(deductNumber);
		});
		
		//计算金额和放款总笔数
		$(":input[name='checkBoxItem']").click(function(){
			var urgeDecuteMoeny=0.00;
			var deductNumber = 0;
				$(":input[name='checkBoxItem']:checked").each(function(){
					urgeDecuteMoeny+=parseFloat($(this).attr("urgeDecuteMoeny"));
					deductNumber+=1; 
	        	});
				$("#deductAmount").text(fmoney(urgeDecuteMoeny,2));
				$("#totalNum").text(deductNumber);

		});
		
		// 金额精确到小数点后2位
		  function fmoney(s, n) {  
		        n = n > 0 && n <= 20 ? n : 2;  
		        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
		        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
		        t = "";  
		        for (i = 0; i < l.length; i++) {  
		            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
		        }  
		        return t.split("").reverse().join("") + "." + r;  
		    }  
	 
	  //导出打款表excel
	  $("#exportWatch").bind('click',function(){
		  var idVal = "";
		  var carLoanStatusHisEx= $("#inputForm").serialize();
		  
		  if($(":input[name='checkBoxItem']:checked").length > 0){
			 
			  $(":input[name='checkBoxItem']:checked").each(function(){
		        	 if(idVal!="")
		        	 {
		        	 idVal+=","+$(this).val();
		        	 }else{
		        	 idVal=$(this).val();
		        	 }
		        	});
		        	 art.dialog.confirm("确认对选中的划扣已办数据导出？",function(){
		        	 window.location.href = ctx + "/car/grant/grantDeducts/exportExcel3?idVal=" + idVal + "&" + carLoanStatusHisEx;
		        	 });
		  }else{
			  if($(":input[name='checkBoxItem']").length > 0){
			  art.dialog.confirm("确认对所有的划扣已办数据导出？",function(){
		        	 window.location.href = ctx + "/car/grant/grantDeducts/checkDoneList?idVal=" + idVal + "&" + carLoanStatusHisEx;
		        	 });
			  }else {
				  artDialog.alert("没有可导出的数据！");
			  }
		  }
	  });
	 
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#inputForm").attr("action", "${ctx}/common/carHistory/checkDoneList");
      $("#inputForm").submit();
      return false;
  }  
	/**
	 * 页面点击查看按钮事件，合同编号，申请表的id
	 */
	 $(function(){
		 $(":input[name='checkDoneInfoBtn']").each(function(){
				$(this).bind('click',function(){
					$('#applyId').val($(this).attr('matchingId'));
					$('#matchingContractCode').val($(this).attr('contractCode'));
					$("#inputForm").submit();
				});
			});
	 })
	
  
</script>
</head>
<body>
 <div class="control-group">                                                                            
 <form:form id="inputForm" modelAttribute="carLoanStatusHisEx" action ="${ctx}/common/checkMatchDone/lookCheckDoneInfo" method="post" class="form-horizontal">
     <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
     <table id = "searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="loanCustomerName" class="input_text178"/>
             </td>
             <td><label class="lab">合同编号：</label>
             	<form:input id="contractCode" path="contractCode" class="input_text178"/>      
             </td>
             <td><label class="lab">实还金额：</label> 
             <form:input type="text" path="reallyAmountMin" class="input_text70"></form:input>-
             <form:input type="text" path="reallyAmountMax" class="input_text70"></form:input> 
             </td>
             
         </tr>
         <tr>
             <td>			
					<label class="lab">门店：</label> 
					<form:input id="txtStore" path="storeName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${secret.store }"  readonly="true"/> 
					<i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
		     	</td>
             	<td><label class="lab">查账日期：</label>
             	<form:input type="text" id="checkDateStart" path="checkDateStart" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" class="input_text70 Wdate" />-
             	<form:input type="text" id="checkDateEnd" path="checkDateEnd" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" class="input_text70 Wdate" />
             </td>
             <td><label class="lab">存入账户：</label>
					<form:select class="select180" id="creditMiDBankName" path="creditMiDBankName" >
						<option value="">请选择</option>
						<c:forEach var="itemMid" items="${middlePersonList}">
							<form:option value="${itemMid.bankCardNo}" >${itemMid.midBankName}</form:option>
						</c:forEach>
					</form:select>
				</td>
         </tr>
		 <tr id="T1" style="display: none">
		 	
		 	<td><label class="lab">标识：</label>
					<form:select id="conditionalThroughFlag" path="conditionalThroughFlag" class="select180">
                    <option value="">请选择</option>
					<form:options items="${fns:getDictList('jk_car_throuth_flag')}" itemLabel="label" itemValue="label" htmlEscape="false"/>
				</form:select>
					</td>
		 	<td><label class="lab">退款标识：</label>
					<form:select id="cardType" path="" class="select180">
                    <option value="">请选择</option>
					<form:options items="${fns:getDictList(' ')}" itemLabel="label" itemValue="label" htmlEscape="false"/>
				</form:select>
					</td>
		 	<td>
		 		<label class="lab">回盘结果：</label>
		 		<form:select id="dictDealStatus" path="dictDealStatus" class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_counteroffer_result')}" var="dictDealStatus">
		                             <form:option value="${dictDealStatus.value}">${dictDealStatus.label}</form:option>
		            </c:forEach>  
		         </form:select>
		 	</td>
		 	
		 </tr>
		 <tr id="T2" style="display: none">
		 <td><label class="lab">借款状态：</label> <form:select
							id="loanMonths" path="loanStatusCode" class="select180">
							<option value="">全部</option>
							<form:options items="${fns:getDictList('jk_car_loan_status')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
		 </tr>
     </table>
     <div  class="tright pr30 pb5">
           <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
           <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
     		<div class="xiala" style="text-align: center;">
					<img src="${context}/static/images/up.png" id="showMore"></img>
			</div>
 </div>
 </div>
</form:form> 

	<p class="mb5">
		<button class="btn btn-small" id="exportWatch" >导出excel</button>
		&nbsp;&nbsp;<label class="lab1"><span class="red">划扣累计金额：</span></label><label class="red" id="deductAmount">
    	<fmt:formatNumber value='${amount}' pattern="#,#0.00"/></label>元
		&nbsp;&nbsp;<label class="lab1"><span class="red">划扣总笔数：</span></label>
		<label class="red" id="totalNum">${count }</label>笔
    </p>
    
     <sys:columnCtrl pageToken="carDrawDoneList"></sys:columnCtrl>
     <div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"> 
   <table id="tableList" class="table table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th><input type="checkbox" id="checkAll"/></th>
        <th>序号</th>
		<th>合同编号</th>
		<th>客户姓名</th>
		<th>门店名称</th>
		<th>存入账户</th>
		<th>借款产品</th>
		<th>批借期数</th>
		<th>借款状态</th>
		<th>合同金额</th>
		<th>放款金额</th>
		<th>划扣费用</th>
		<th>未划金额</th>
		<th>申请查账金额</th>
		<th>实还金额</th>
		<th>查账日期</th>
		<th>回盘结果</th>
		<th>渠道</th>
		<th>退款标识</th>
		<th>操作	</th>
      </tr>
      </thead>
      	<c:if test="${ page.list != null && fn:length(page.list)>0}">
       	<c:forEach items="${page.list}" var="item" varStatus="status"> 
        <tr>
          <td><input class="checks" type="checkbox" name="checkBoxItem" urgeDecuteMoeny="${item.urgeDecuteMoeny }"  value="${item.applyId } "/></td>
          <td>${status.count}</td>
          <td>${item.contractCode}</td>
          <td>${item.loanCustomerName}</td>
          <td>${item.storeName }</td><!-- 门店名称 -->
          <td>${item.creditMiDBankName }</td><!-- 存入账户 -->
          <td>${item.finalProductType}</td>
          <td>${item.finalAuditMonths }</td><!-- 批借期限 -->
          <td>${item.loanStatusCode }</td><!-- 借款状态-->
          <td>${item.finalAuditAmount }</td><!-- 合同金额-->
          <td>${item.finalAuditAmount }</td><!-- 放款金额	-->
          <td><fmt:formatNumber value="${item.urgeMoeny}" type="number" pattern="0.00"/></td><!-- 划扣费用 -->
          <td>${item.unUrgeDecuteMoeny }</td><!-- 待划扣金额 -->
          <td>${item.urgeDecuteMoeny }</td><!-- 申请查账金额 -->
          <td>${item.reallyAmount }</td><!-- 实还金额 -->
          <td>${item.applyPayDay }</td><!-- 查账日期 -->
          <td>${item.matchingResult }</td><!-- 回盘结果 -->
          <td>${item.conditionalThroughFlag}</td><!-- 附条件标识 -->
          <td>-</td><!-- 退款标识 -->
         
          <td>
              <input type="button" value="历史" class="btn_edit"  onclick="showCarLoanHis('${item.loanCode}')"></input>
              <!-- <input type="button" value="查看" class="btn_edit" id="butss" name = "doneInfoBtn" onclick="showCarLoanInfo('${item.loanCode}')">   -->
              <!-- <input type="button" class="btn_edit" value="查看"  name="checkDoneInfoBtn" matchingId="${item.applyId}" contractCode = "${item.contractCode }" ">  -->      
          </td>           
      </tr>  
      </c:forEach>       
      </c:if>
      <c:if test="${ page.list==null || fn:length(page.list)==0}">
        <tr><td colspan="18" style="text-align:center;">没有已办数据</td></tr>
      </c:if>
     </table>
   </div>
<div class="pagination">${page}</div>
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>