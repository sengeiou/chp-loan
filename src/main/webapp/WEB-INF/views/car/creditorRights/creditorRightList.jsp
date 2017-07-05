<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借债权信息列表</title>
<meta name="decorator" content="default" />
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript" src="${context}/js/car/creditorRights/creditorRights.js"></script>
<script type="text/javascript" src="${context}/js/car/creditorRights/creditorLog.js"></script>
<script type="text/javascript">
//结清&提前结清
function settleSub(id,settleType,channelType){
	 $.ajax({
   	  type:'POST',
   	  url:'${ctx}/car/creditorRight/settle',
   	  data:{id:id,settleType:settleType,channelType:channelType},
   	  dataType:'text',
  		  success:function(msg){
          		if('sucess' == msg){
					page(1,${page.pageSize});
          		}else{
          			art.artDialog.alert("未结清成功！");
          		}
          }
     });
}
//结清
function settle(id,channelType){
   	 art.dialog.confirm("是否确认结清?",function(){
   		settleSub(id,'36',channelType);
     });  
}
//提前结清
function settleEarly(id,channelType){
   	 art.dialog.confirm("是否确认提前结清?",function(){
   		settleSub(id,'37',channelType);
     });  
}

$(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
			// 划扣日期验证触发事件,点击搜索进行验证
			var startDate = $("#urgeDecuteDateStart").val();
			var endDate = $("#urgeDecuteDateEnd").val();
			if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
				var arrStart = startDate.split("-");
				var arrEnd = endDate.split("-");
				var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
				var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
				if(sd.getTime()>ed.getTime()){   
					art.dialog.alert("录入开始日期不能小于录入结束日期!",function(){
						$("#urgeDecuteDateStart").val("");
						$("#urgeDecuteDateEnd").val("");
					});
					return false;     
				}else{
					page(1,${page.pageSize});
				}  
			}else{
				page(1,${page.pageSize});
			} 
			$('#inputForm').submit(); 
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
		 if("${message}"!=""){
			 $('#uploadAuditedModalInfoMsg').modal('show');
		 }
		
});

function page(n,s){
	if(n) $("#pageNo").val(n);
	if(s) $("#pageSize").val(s);
	$("#inputForm").attr("action","${ctx}/car/creditorRight/list");
	$("#inputForm").submit();
	return false;
}

</script>
</head>
<body>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="creditorRightView" action="${ctx}/car/creditorRight/list" method="post" class="form-horizontal">
 	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
     <table id="searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">借款人姓名：</label>
             	<form:input id="loanCustomerName" path="loanCustomerName" class="input_text178"></form:input>
             </td>
             <td>
				<label class="lab">合同编号：</label> 
				<form:input id="contractCode" path="contractCode" class="input_text178"/>
		     </td>
             <td><label class="lab">证件号码：</label>
             	<form:input id="customerCertNum" path="customerCertNum" class="input_text178"/>
             </td>
         </tr>
         <tr>
           	<td><label class="lab">产品类型：</label>
                 <form:select id="productTypeID" path="productType" class="select180">
                   <option value="">请选择</option>
                    <c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="product_type">
                                     <form:option value="${product_type.productCode}">${product_type.productName}</form:option>
                    </c:forEach>  
                 </form:select>  
           	</td>
            <td><label class="lab">借款状态：</label>
           	    <form:select id="loanStatus" path="loanStatus" class="select180">
                       <option value="">请选择</option>
                       <form:options items="${fns:getDictLists('33,36,37,38','jk_car_loan_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                   </form:select>
            </td>
			<td><label class="lab">录入日期：</label>
				 <form:input path="entryDayStart" id="urgeDecuteDateStart" class="input_text70 Wdate" 
				 	onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer"/>
				 	
				  <form:input path="entryDayEnd" id="urgeDecuteDateEnd" class="input_text70 Wdate" 
				 	onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer"/>
			</td>
		</tr>
		 <tr>
  			<td>
  				<label class="lab">是否发送债权：</label>
				 	<form:select id="status" path="status" class="select180">
                       <option value="">请选择</option>
                       <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                   </form:select>			
		    </td>
  			<td>
  				<label class="lab">渠道：</label>
				 	<form:select id="channelType" path="channelType" class="select180">
                       <option value="">请选择</option>
                       <form:options items="${fns:getDictLists('2,3','jk_car_throuth_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                   </form:select>
		    </td>
         </tr>

     </table>
     <div  class="tright pr30 pb5">
            <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
 </div>
 </div>
</form:form> 
 
	<p class="mb5">
		<input type="checkbox" id="checkAll"/> 全选
		<input type="button" class="btn btn-small jkcj_lendCardebtInput_BulkImport" data-target="#uploadAuditedModal" data-toggle="modal" id="exportWatch" value="批量导入"/>
		<input type="button" id="offLineShang" role="button" class="btn btn-small jkcj_lendCardebtInput_input"
		  onclick="javascript:window.location.href='${ctx}/car/creditorRight/toAddPage'" value="录入" />
		<input type="button"  id="batchSend" role="button" class="btn btn-small jkcj_lendCardebtInput_SendBulk" value="批量发送债权" />
    </p>
    
    <sys:columnCtrl pageToken="carCreditorRightList"></sys:columnCtrl>
    <div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
      	<th></th>
        <th>序号</th>
		<th>合同编号</th>
		<th>借款人姓名</th>
		<th>借款人身份证号码</th>
		<th>债权来源</th>
		<th>产品类型</th>
		<th>借款用途</th>
		<th>借款状态</th>
		<th>职业情况</th>
		<th>车牌号码</th>
		<th>首次还款日</th>
		<th>合同签约日</th>
		<th>借款天数</th>
		<th>截止还款日期</th>
		<th>月利率%</th>
		<th>原始债权价值</th>
		<th>债权人</th>
		<th>还款金额</th>
		<th>还款方式</th>
		<th>渠道</th>
		<th>录入日期</th>
		<th>是否发送债权</th>
		<th>操作</th>
      </tr>
      </thead>
      	<c:if test="${ page.list!=null && fn:length(page.list)>0}">
       	<c:forEach items="${page.list}" var="item" varStatus="status"> 
        <tr>
          <td><input type="checkbox" name="checks" class="checks" value="${item.loanCode}" /></td>
          <td>${status.count}</td>
          <td>${item.contractCode}</td>
          <td>${item.loanCustomerName}</td>
          <td>${item.customerCertNum}</td>
          <td>${item.creditorRigthSource}</td>
          <td> ${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.productType)} </td>
          <td>${item.usageOfLoan}</td>
          <td>${item.loanStatus}</td>
          <td>${item.job}</td>
          <td>${item.plateNumbers}</td>
          <td><fmt:formatDate value='${item.downPaymentDay}' pattern="yyyy-MM-dd"/></td>
          <td><fmt:formatDate value='${item.contractDay}' pattern="yyyy-MM-dd"/></td>
          <td>${item.borrowingDays}</td>
          <td><fmt:formatDate value='${item.contractEndDay}' pattern="yyyy-MM-dd"/></td>
          <td>${item.monthlyInterestRate}</td>
          <td><fmt:formatNumber value='${item.primevalWorth}' pattern="#,#00.00"/></td>
          <td>${item.creditor}</td>
          <td><fmt:formatNumber value='${item.contractReplayAmount}' pattern="#,#00.00"/></td>
          <td>${item.contractReplayWay}</td>
          <td>${item.channelType}</td>
         
          <td><fmt:formatDate value='${item.createTime}' pattern="yyyy-MM-dd"/></td>
          <td>${item.status}</td>
          <td>
	            <c:if test="${item.status ne '是' }">
	              <input type="button" value="编辑" onclick="javascript:window.location.href='${ctx}/car/creditorRight/toModifyPage/${item.id}'" class="btn_edit jkcj_lendCardebtInput_Editor"></input>
	             </c:if>
	            <c:if test="${item.loanStatus ne '提前结清' && item.loanStatus ne '结清' }">
	              <input type="button" value="结清" name="manage" class="btn_edit jkcj_lendCardebtInput_Settlement" onclick="settle('${item.id}','${item.channelType}')" ></input>
	              <input type="button" value="提前结清" onclick="settleEarly('${item.id}','${item.channelType}')" class="btn_edit jkcj_lendCardebtInput_EarlySettlement"></input>
             	</c:if>
              <input type="button" data-target="#back_div" data-toggle="modal" name="back" onclick="showCalLog('${item.loanCode}')" value="历史"  class="btn_edit jkcj_lendCardebtInput_history" ></input>
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
			height: $(window).height()-320,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>

<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
        role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                    <div class="modal-content">																							
                    <form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post" >
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                        <h4 class="modal-title" id="myModalLabel">批量导入</h4>
                    </div>
                    <div class="modal-body">
                      <input type="file" name="file" id="fileid" >
                    </div>
                    <div class="modal-footer">
                         	<span style="float:left;color:red;">仅限于导入xls，xlsx格式的数据,且大小不超过10M</span>
       						<input type="button" id="sureBtn" class="btn btn-primary"  value="确定"/>
                           <input type="button" class="btn btn-primary"  class="close" data-dismiss="modal" value="取消" />
                       </div>
                    </form>
            </div>
            </div>
    </div>  
</body>
</html>