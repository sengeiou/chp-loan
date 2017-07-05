<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#inputForm").attr("action", "${ctx}/car/refund/refundAudit/refundBacklogJump");
	$("#inputForm").submit();
	return false;
}
$(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		$('#inputForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#inputForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');  
		 $("select").trigger("change");
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 
	// 点击全选
	$("#checkAll").click(function(){
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
		}
	});
	

	//导出excel
	$("#offLineDao").click(function(){
		var flag = true;
		var idVal = "";
		var RefundAuditEx = $("#inputForm").serialize();
		if($(":input[name='checkBoxItem']:checked").length==0){
			
			 var flag=confirm("确认对所有待处理和处理中的数据导出？");
	           if(flag){
	        	   RefundAuditEx+="&idVal="+idVal;

	        	   window.location=ctx+"/car/refund/refundAudit/refundExl?"+RefundAuditEx;
	        	 
	        	
	           }else{
	        	   return;
	           }
	           $("input[name='checkBoxItem']").not("input:checked").each(function(){
					if($("#statusFlag"+$(this).val()).val()=="待处理")
					{//待处理导出后改为处理中
						$("#statusFlag"+$(this).val()).val("处理中");
						$("#statusTd"+$(this).val()).html($("#updateStatus").val());
					}
		    	});				
			}else{
			$(":input[name='checkBoxItem']:checked").each(function(){
				if($("#statusFlag"+$(this).val()).val()=="待处理"||$("#statusFlag"+$(this).val()).val()=="处理中")
				{//待处理勾选项ID组装
					if(idVal!="")
		    		{
		    			idVal+=","+$(this).val();
		    		}else{
		    			idVal=$(this).val();
		    		}
				
				}else{
					flag = false;
				}
	    	});
			if(!flag)
			{
				art.dialog.alert("只有待处理和处理中状态的数据才能导出!");
				return false;
			}
			RefundAuditEx+="&idVal="+idVal;
			
		 	window.location=ctx+"/car/refund/refundAudit/refundExl?"+RefundAuditEx;	 	
		 	
		 	var idArray = idVal.split(",");
			for(var i =0; i<idArray.length; i++)
			{
				$("#statusFlag"+idArray[i]).val("处理中");
				$("#statusTd"+idArray[i]).html($("#updateStatus").val());
			}
		}

	});
	// 上传回执结果,在上传之前要进行验证，所以上传回执结果要使用Ajax进行控制
	 $('#sureBtn').off('click').on('click',function(){
        	var flag = true;
        	$("input[type=file]").each(function(){
	        	 if(this.value==""||this.value==null){
	        		 art.dialog.alert("请选择文件");
	        		 flag = false;
	        	 }else{
		        	 index = this.value.lastIndexOf(".");
		        	 endStr = this.value.substring(index+1,this.value.length);
		        	 if(!(endStr.toLowerCase()=='xls'||endStr.toLowerCase()=='xlsx')){
		        		 art.dialog.alert("请选择Excel文件");
		        		 flag = false;
		        	 }
	        	 }
        	});
        	if(!flag){
        	 	return;
        	}
        	var formData = new FormData($( "#uploadAuditForm" )[0]);
      	  	$.ajax({
      		   type: "POST",
      		   url: ctx + "/car/refund/refundAudit/importResult",
      		   data:formData,
      		   cache: false,  
               processData: false,  
               contentType: false ,
      		   dataType : 'text',
      		   success: function(data){
      			   if(data == 'false'){
      				   $.jBox.error('传入的数据不正确', '提示信息');
      			   }else if(data == 'true'){
      				   $.jBox.info('上传操作成功', '提示信息');
      				   window.location = ctx + "/car/refund/refundAudit/refundBacklogJump";
      			   }
      		   },
      		   error:function(data){
      			   $.jBox.error('传输错误', '提示信息');
      		   }
      		});
		//$("#uploadAuditForm").submit();
		//ajaxFileUpload();
	});
	
	// 线上放款弹出框
	$("#onLineRefundBtn").click(function(){
		var checkVal = null;
		
		if($(":input[name='checkBoxItem']:checked").length==0){
			
			art.dialog.alert("请选择线上退款数据！");
	        return false;  				
		 }else{
			 	var flag = true;
			 	// 对选中的单子进行线上放款，根据退款id
				$(":input[name='checkBoxItem']:checked").each(function(){
					if($("#statusFlag"+$(this).val()).val()=="待处理")
					{//待处理勾选项ID组装
						if(checkVal!=null)
		         		{
		         			checkVal+=";"+$(this).val();
		         		}else{
		         			checkVal=$(this).val();
		         		}
					}else{
						flag = false;
					}
	         		
	         	});
			 	if(!flag)
		 		{
					art.dialog.alert("只有待处理状态的数据才能发送平台!");
					return false;
		 		}
				$(this).attr("data-target","#online");
				$("#check").val(checkVal);
			 
		 }		
	});
	
	// 线上退款确认,获得选择的平台，
	$("#onlineBtn").click(function(){
		var grantWay=$("#plat").attr("selected","selected").val();
		if(grantWay==""||grantWay==null)
		{
			art.dialog.alert('请选择发送平台!');
			return false;
		}
		var checkVal=$("#check").val();

		online(checkVal,grantWay);
	});
	
	// 线上退款处理
	function online(checkVal,grantWay){
			$.ajax({
				type : 'post',
				url : ctx + '/car/refund/refundAudit/onlineRefundDeal',
				data : {
					'checkVal':checkVal,
					'grantWay':grantWay
					},
				dataType:'json',
				success : function(data) {
					if(data){
						art.dialog.alert('发送成功!',function(){
							window.location=ctx+'/car/refund/refundAudit/refundBacklogJump';
						});
					}else{
						art.dialog.alert("失败了");
					}
				}
			});
	}
	
	// 关闭窗口
	function closeModal(id){
		$("#"+id).modal('hide');
	}
	//点击全选反选选择框
	$("#checkAll").click(function(){
		var totalMoney = 0.00;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				totalMoney+=parseFloat($(this).attr("urgeMoeny"));
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
			totalMoney+=parseFloat($("#deductsAmountHide").val());
		}
		$("#deductAmount").text(fmoney(totalMoney,2));
	});
	
	//计算金额
	$(":input[name='checkBoxItem']").click(function(){
		var totalMoney=0.00;
			$(":input[name='checkBoxItem']:checked").each(function(){
				totalMoney+=parseFloat($(this).attr("urgeMoeny"));
	    	});
			if ($(":input[name='checkBoxItem']:checked").length==0) {
				totalMoney+=parseFloat($("#deductsAmountHide").val());
			}
			$("#deductAmount").text(fmoney(totalMoney,2));
	
	});
});	
//金额精确到小数点后2位
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
</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
       <form:form  method="post" id="inputForm" modelAttribute="carRefundInfo">
       	 <input id="deductsAmountHide" type="hidden" name="deductsAmount" value="${deductsAmount}">
         <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
         <input name="menuId" type="hidden" value="${param.menuId}" />
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="updateStatus" type="hidden" value="${carRefundInfo.staticValue}">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
               <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="loanCustomerName"></form:input></td>
               <td><label class="lab">门店：</label> 
					<input id="txtStore" name="storeName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${secret.store }" readonly="true"/> 
					<i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore">
               </td>
               <td><label class="lab">回盘结果：</label><form:select class="select180" path="auditStatus">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('car_refund_status')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
						</form:select></td>
            </tr>
            <tr>
				<td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>		
				<td><label class="lab">开户行：</label>
						<form:select class="select180" path="cardBank">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_open_bank')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
						</form:select>
				</td>
				<td><label class="lab">是否电销：</label><form:select class="select180" path="customerTelesalesFlag"><option value="">请选择</option>
			    <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
			    </form:select></td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
              <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
        
		</div>
	</form:form>
	   </div>
	   <p class="mb5">
	    &nbsp;&nbsp;
		<button class="btn btn-small" id="offLineDao">导出</button>
		<button id="offLineShang" role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal">上传回执</button>
		<button id="onLineRefundBtn" role="button" class="btn btn-small"  data-toggle="modal">发送平台</button>
    	<span class="red">应划扣累计金额：</span><span class="red" id="deductAmount"><fmt:formatNumber value='${deductsAmount}' pattern="#,##0.00"/>
    	</span>元
		</p>
		<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
		
		<sys:columnCtrl pageToken="carRefundList"></sys:columnCtrl>
        <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom: 200px;">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>序号</th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>应划扣金额</th>
			<th>实划扣金额</th>
			<th>批借期限(天)</th>
            <th>开户行</th>
			<th>借款状态</th>
			<th>回盘结果</th>
			<th>回盘原因</th>
			<th>是否电销</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ refundList!=null && fn:length(refundList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${refundList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" value="${item.id}" urgeMoeny="${item.urgeMoeny}"/>
             	<input type="hidden" id="statusFlag${item.id}" value="${item.auditStatus}"/>
             </td>
             <td>${index}</td>
             <td>${item.contractCode}</td>
             <td>${item.storeName}</td>
             <td>${item.loanCustomerName}</td>
             <td>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.productType)}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeMoeny}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeDecuteMoeny}' pattern="#,#00.00"/></td>
             <td>${item.contractMonths}</td>
             <td>${item.cardBank}</td>
             <td>${item.dictLoanStatus}</td>
             <td id="statusTd${item.id}">${item.auditStatus}</td>
             <td>${item.auditRefuseReason}</td>
             <td>${item.customerTelesalesFlag}</td>      
             <td>
             	<button class="btn_edit" name="historyBtn" value='${item.applyId}' onclick="showCarLoanHis('${item.loanCode}')">历史</button>
             </td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ refundList==null || fn:length(refundList.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>
	</div>
     <div class="pagination">${refundList}</div> 
     
	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> 
			<div class="modal-dialog" role="document">
				    <div class="modal-content">
				    <form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post">
					<div class="modal-header">
					    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					</div>
					<div class="modal-body">
                      <input type='file' name="file" id="fileid">
                    </div>
					</form>
					<div class="modal-footer">
       					 <button class="btn btn-primary"  class="close" id="sureBtn">确认</button>
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal">取消</button>
   					</div>
			</div>
			</div>
		
	</div>
	
	<div class="modal fade" id="online" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
       <div class="modal-content">
       <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="online">线上退款</h4>
	   </div>
       <div class="modal-body">
       <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">发送平台：</label>
                <select class="select180" id="plat"><option value="">请选择</option>
			    <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="card_type">
					<option value="${card_type.value}">${card_type.label}</option>
				 </c:forEach>
			    </select></td>
				<input type="hidden" id="check">
            </tr>
        </table>
     </div>
    <div class="modal-footer">
    <button class="btn btn-primary"  class="close" data-dismiss="modal" id="onlineBtn">确认</button>
    <button class="btn btn-primary"  class="close" data-dismiss="modal" onclick="closeModal('online')">取消</button>
    </div>
    </div>
	</div>
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
</div>     
</body>
</html>