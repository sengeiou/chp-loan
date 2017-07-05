<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>已放款列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
			// 放款日期验证触发事件,点击搜索进行验证    
			var startDate = $("#lendingTimeStart").val();
			var endDate = $("#lendingTimeEnd").val();
			if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
				var arrStart = startDate.split("-");
				var arrEnd = endDate.split("-");
				var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
				var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
				if(sd.getTime()>ed.getTime()){   
					art.dialog.alert("放款日期开始日期不能大于放款日期结束日期!",function(){
						$("#lendingTimeStart").val("");
						$("#lendingTimeEnd").val("");
					});
					return false;     
				}else{
					$('#grantForm').submit(); 
				}  
			}else{
				$('#grantForm').submit(); 
			} 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#grantForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("#hdStore").val('');  
		 $("select").trigger("change");
	 });
	 $('#showMore').bind('click',function(){
		show(); 
		 
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#grantForm").attr("action", "${ctx}/common/carHistory/carLoanSuccessDoneList");
      $("#grantForm").submit();
      return false;
  }  
</script>
<script type="text/javascript">
$(document).ready(function(){
		//导出excel,默认导出全部
		$("#daoBtn").click(function(){
		var idVal = "";
		var grantForm = $("#grantForm").serialize();
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
	    		if(idVal!="")
	    		{
	    			idVal+=","+$(this).attr("fId");
	    		}else{
	    			idVal=$(this).attr("fId");
	    		}
	    	});
			art.dialog.confirm("确认对选中的放款已办数据导出？",function(){
				window.location.href=ctx+"/car/common/grantDone/grantDoneHaveEx?idVal="+idVal+"&"+grantForm;
			});
		} else {
			art.dialog.confirm("确认对所有的放款已办数据导出？",function(){
				window.location.href = ctx + "/car/common/grantDone/grantDoneHaveEx?idVal=" + idVal + "&" + grantForm;
			});
		}
	});
	// 点击全选
	$("#checkAll").click(function(){
		var deductAmount=0.00;
		var deductNumber = 0;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				deductAmount+=parseFloat($(this).attr("finalAuditAmount") == "" ? 0 : $(this).attr("finalAuditAmount"));
				deductNumber++;
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
			deductAmount=parseFloat($("#hiddenTotalAmount").val());
			deductNumber=${page.count}; 
		}
		$("#deductAmount").text(fmoney(deductAmount,2));
		$("#totalNum").text(deductNumber);
	});
	
	//计算金额和放款总笔数
	$(":input[name='checkBoxItem']").click(function(){
		var deductAmount=0.00;
		var deductNumber = 0;
		
			$(":input[name='checkBoxItem']:checked").each(function(){
				deductAmount+=parseFloat($(this).attr("finalAuditAmount") == "" ? 0 : $(this).attr("finalAuditAmount"));
				deductNumber++; 
        	});
			if ($(":input[name='checkBoxItem']:checked").length==0) {
				deductAmount+=parseFloat($("#hiddenTotalAmount").val());
				deductNumber=${page.count}; 
			}
			$("#deductAmount").text(fmoney(deductAmount,2));
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
});
</script>
</head>
<body>
 <div class="control-group">
 <form:form id="grantForm" modelAttribute="carLoanStatusHisEx" action="${ctx}/common/carHistory/carLoanSuccessDoneList" method="post" class="form-horizontal">
     <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
     <input name="menuId" type="hidden" value="${param.menuId}" />
     <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
     <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td>
             	<label class="lab">客户姓名：</label>
             	<form:input type="text" class="input_text178" path="loanCustomerName"></form:input>
             </td>
             <td>
             	<label class="lab">合同编号：</label>
             	<form:input type="text" class="input_text178" path="contractCode"></form:input>
             </td>
            <td>			
				<label class="lab">门店：</label> 
				<input id="txtStore" name="storeName"
					type="text" maxlength="20" class="txt date input_text178"
					value="${secret.store }" readonly="true"/> 
				<i id="selectStoresBtn"
				class="icon-search" style="cursor: pointer;"></i>
				<input type="hidden" id="hdStore">
		     </td>
         </tr>
         <tr>
             <td><label class="lab">渠道：</label>
             	<form:select id="loanFlag" path="loanFlag" class="select180">
                   <option value="">请选择</option>
	                    <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			              </c:forEach>
		         </form:select>
             </td>
         	
            <td colspan=""><label class="lab">是否电销：</label>
				<form:radiobuttons path="telesalesFlag" items="${fns:getDictList('jk_telemarketing')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
		    </td>
		    <td><label class="lab">放款日期：</label>
		    <form:input id="lendingTimeStart"  path="lendingTimeStart"  type="text" class="Wdate input_text70" size="10"  
                     onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"></form:input>-<form:input id="lendingTimeEnd" path="lendingTimeEnd"  type="text" class="Wdate input_text70" size="10"  
                     onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"></form:input>
                </td>
         </tr>
     </table>
     <div  class="tright pr30 pb5">
            <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
   </div>
  </form:form> 
 </div>

	<p class="mb5">
	    <input type="hidden" id="hiddenTotalAmount" value='${amount}'></input>
    	<button class="btn btn-small" id="daoBtn">导出excel</button>
    	&nbsp;&nbsp;<label class="lab1"><span class="red">放款累计金额：</span></label><label class="red" id="deductAmount">
    	<fmt:formatNumber value='${amount}' pattern="#,#0.00"/></label>元
		&nbsp;&nbsp;<label class="lab1"><span class="red">放款总笔数：</span></label>
		<label class="red" id="totalNum">${count }</label>笔
						<input type="hidden" id="num" value="${count }">
				<input type="hidden" id="hiddenTotalGrant" value='${amount}'></input>
    </p>
    
       <sys:columnCtrl pageToken="carLoanSuccessDoneList"></sys:columnCtrl>
    <div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
      	<th><input type="checkbox" id="checkAll"/></th>
      	<th>序号</th>
		<th>客户姓名</th>
		<th>团队经理</th>
		<th>客户经理</th>
		<th>合同编号</th>
		<th>合同金额（元）</th>
		<th>原放款金额（元）</th>
		<th>放款账号</th>
		<th>开户行</th>
		<th>账号姓名</th>
		<th>门店名称</th>
		<th>放款时间</th>
		<th>操作人员</th>
		<th>渠道</th>
		<th>是否电销</th>
		<th>操作</th>
      </tr>
      </thead>
      <c:if test="${ page.list != null && fn:length(page.list)>0}">
       	<c:forEach items="${page.list}" var="item" varStatus="status">
        <tr>
          <td><input type="checkbox" name="checkBoxItem" fId="${item.applyId}"  finalAuditAmount='${item.finalAuditAmount}'/></td>
          <td>${(page.pageNo-1)*page.pageSize+status.count}</td>
          <td>${item.loanCustomerName}</td>
          <td>${item.teamManagerName}</td>
          <td>${item.costumerManagerName}</td>
          <td>${item.contractCode}</td>
          <td><fmt:formatNumber value='${item.contractAmount}' pattern='#,##0.00' /></td>
          <td><fmt:formatNumber value='${item.grantAmount}' pattern='#,##0.00' /></td>
	      <td>${item.bankCardNo}</td>
		  <td>${item.midBankName}</td>
	      <td>${item.middleName}</td>
	      <td>${item.storeName}</td>
          <td><fmt:formatDate value='${item.lendingTime}' pattern="yyyy-MM-dd"/></td>
	      <td>${item.lendingUserId}</td>
          <%-- <td>${item.borrowTursteeFlag }</td> --%>
          <td>${item.loanFlag}</td>
          <td>${item.telesalesFlag}</td>
          <td>
              <input type="button" value="历史" class="btn_edit"  onclick="showCarLoanHis('${item.loanCode}')"></input>
          </td>           
      </tr>  
     </c:forEach>     
       </c:if>
      <c:if test="${ page.list==null || fn:length(page.list)==0}">
        <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
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