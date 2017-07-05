<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<%-- 	<script type="text/javascript" src="${context}/js/enter_select.js"></script> --%>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/car/grant/carPastGrantDeducts.js" type="text/javascript"></script>

<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#deductsForm").attr("action", "${ctx}/car/grant/grantDeducts/deductsInfo?returnUrl=carPastGrantDeductsList&result=bef");
	$("#deductsForm").submit();
	return false;
}

$(document).ready(function(){

	
	 // 划扣结果change事件
	  $(":input[name='sort']").bind('change',function(){
		// 获得划扣结果
		var splitBackResult=$(":input[name='sort']:checked").val();
		 if(splitBackResult == "1")
		 {
			 $('#back_div').show();
		 }else{
			 $('#back_div').hide();
		 }
	 }); 
});
</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
       <form:form  method="post" modelAttribute="urgeMoneyEx" id="deductsForm" action="${ctx }/car/grant/grantDeducts/deductsInfo?returnUrl=carPastGrantDeductsList&result=bef">
         <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
         <input name="menuId" type="hidden" value="${param.menuId}" />
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="returnUrl" type="hidden" value="carGrantDeductsList">
		 <input id="result" type="hidden" value="bef">
		 <input id="rule" type="hidden" path="rule">
        <table id = "searchTable" class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
               <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
               <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
              	<td><sys:multipleBank bankClick="selectBankBtn"
							bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
						<label class="lab">开户行：</label>						
						<form:input type="text"
							id="search_applyBankName" path="cardBankEcho"
							class="input_text178" ></form:input> <i
						id="selectBankBtn" class="icon-search" style="cursor: pointer;"></i>
							<form:input type="hidden"
							id="bankId" path="cardBank"
							class="input_text178"></form:input>
			</td>
            </tr>
            <tr>
                <td><label class="lab">借款产品：</label>
		         <form:select id="productType" path="productType" class="select180">
                   <option value="">请选择</option>
                   <form:options items="${fncjk:getPrd('products_type_car_credit')}" itemLabel="productName" itemValue="productCode" htmlEscape="false"/>
		         </form:select>
                </td>
                <td><label class="lab">划扣金额：</label><form:input type="text" class="input_text70" id="urgeMoenyStart" path="urgeMoenyStart" ></form:input>-<form:input type="text" class="input_text70" id="urgeMoenyEnd" path="urgeMoenyEnd" ></form:input>
                </td>
				<td><label class="lab">划扣平台：</label><form:select class="select180" path="dictDealType">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictLists('1,2,3','jk_deduct_plat')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
				</form:select>
				</td>
            </tr>
			<tr id="T1" style="display:none">
				 <td><label class="lab">是否电销：</label><form:select class="select180" path="customerTelesalesFlag"><option value="">请选择</option>
			    <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
			    </form:select>
			    </td>
			  <td><label class="lab">渠道：</label>
             	<form:select id="loanFlag" path="loanFlag" class="select180">
                   <option value="">请选择</option>
	                   <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<c:if test = "${loan_Flag.value!=1}">
			                   			<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			                   		</c:if>	
			                   </c:forEach>
		         </form:select>
             </td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
             <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
             <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="../../../../static/images/up.png" id="showMore"></img>
	     </div>
		</div>
	   </div>
		<p class="mb5">
		    		&nbsp;&nbsp;
		<input type="checkbox" id="checkAll"/>  全选   
		<input class="btn btn-small" id="daoHBtn" type="button" value="导出" data-toggle="modal"/>
		<input role="button" class="btn btn-small" data-target="#uploadBox" data-toggle="modal" id="shangF" type="button" value="上传回执"/>
		<input class="btn btn-small" id="sendF" type="button" value="发送平台" data-toggle="modal"/>
		<input class="btn btn-small" id="sendZ" type="button"   value="手动确认划扣"/>

    	<label class="lab1"><span class="red">划扣累计金额：</span></label><label class="red" id="totalGrantMoney"><fmt:formatNumber value='${deductsAmount}' pattern="#,##0.00"/>
    	</label>元
  
    	<input type="hidden" id="hiddenTotalGrant" value='${deductsAmount}'></input>
		<label class="lab1"><span class="red">划扣总笔数：</span></label><label class="red" id="totalNum">${totalNum }</label>笔
		<input type="hidden" id="num" value="${totalNum }">
		</p>
		
	    <div>
    	 <sys:columnCtrl pageToken="carPastDeductsList"></sys:columnCtrl>
    	</div>
		<div class="box5" style="overflow: auto; width: 100%;">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th></th>
            <th>序号</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>开户行</th>
            <th>借款产品</th>
            <th>应划扣金额</th>
            <th>实划扣金额</th>
            <th>划扣平台</th>
			<th>回盘结果</th>
			<th>回盘原因</th>
			<th>渠道</th>
			<th>P2P标识</th>
			<th>是否电销</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ urgeList!=null && fn:length(urgeList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${urgeList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" 
               urgeMoeny='${item.urgeMoeny}' reason='${item.splitBackResult}' grantAmount='${item.urgeMoeny}'
               deductsType='${item.dictDealType}' cid='${item.urgeId }' />
             </td>
             <td>${index}</td>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td>${item.cardBank}</td>
             <td>${item.productType}</td>
             <td><fmt:formatNumber value='${item.urgeMoeny}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeDecuteMoeny}' pattern="#,#00.00"/></td>
             <td>${item.dictDealType}</td>
             <td>${item.splitBackResult}</td>
             <td>${item.splitFailResult}</td>
			 <td>${item.dictLoanFlag}</td>
			 <td>${item.loanFlag}</td>     
             <td>${item.customerTelesalesFlag}</td> 
             <td>
             <input type="button" class="btn_edit" onclick="showCarLoanHis('${item.loanCode}')" value="历史" />
             </td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ urgeList==null || fn:length(urgeList.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>
	</div>
     <div class="pagination">${urgeList}</div> 
</form:form>
	
			<!--导入弹框  -->
	<div class="modal fade" id="uploadBox" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
       <div class="modal-content">
       <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="uploadBox">上传回执</h4>
	   </div>
       <div class="modal-body">
       <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">上传平台：</label>
				<select id="importDeducts">
				    		<option value="">请选择</option>
						<c:forEach items="${fns:getDictLists('1,2,3', 'jk_deduct_plat')}" var="item">
								<option value="${item.value}">${item.label}</option>
						</c:forEach>
				 </select>
				</td>
				<input type="hidden" id="checkUpload">
            </tr>
             <form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post">
            <tr>
            <td>
            			<label class="lab">上传文件：</label>
						<input type='file' name="file" id="fileid">	
            </td>
            </form>
            </tr>
        </table>
     </div>
    <div class="modal-footer">
    <span style="float:left;color:red;">仅限于导入xls，xlsx格式的数据,且大小不超过10M</span>
    <button class="btn btn-primary"  class="close" data-dismiss="modal" id="uploadBoxBtn">确认</button>
    <button class="btn btn-primary"  class="close" data-dismiss="modal" onclick="closeModal('online')">取消</button>
    </div>
    </div>
	</div>
 </div>
	
		<!--导出弹框  -->
	<div class="modal fade" id="offLineDao" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
       <div class="modal-content">
       <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="offLineDao">导出</h4>
	   </div>
       <div class="modal-body">
       <form id ="exportDeductPlat" >
       		       <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">导出平台：</label>
				<select id="exportDeducts" class="select180 required " >
				    		<option value="">请选择</option>
						<c:forEach items="${fns:getDictLists('1,2,3', 'jk_deduct_plat')}" var="item">
								<option value="${item.value}">${item.label}</option>
						</c:forEach>
				 </select>
				</td>
				<input type="hidden" id="checkDao">
            </tr>
        </table>
        
       </form>

     </div>
    <div class="modal-footer">
    <button class="btn btn-primary"  class="close"  id="offLineBtn">确认</button>
    <button class="btn btn-primary"  class="close" data-dismiss="modal" onclick="closeModal('online')">取消</button>
    </div>
    </div>
	</div>
 </div>

		<!--线上划扣弹框  -->
	<div class="modal fade" id="online" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
       <div class="modal-content">
       <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="online">线上划扣</h4>
	   </div>
       <div class="modal-body">
       <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">发送平台：</label>
				<select id="onlineDeducts" class="select180 " >
				    		<option value="">请选择</option>
						<c:forEach items="${fns:getDictLists('1,2,3', 'jk_deduct_plat')}" var="item">
								<option value="${item.value}">${item.label}</option>
						</c:forEach>
				 </select>
				</td>
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
 </div>
 
 		<!--手动确认弹框  -->
	<div class="modal fade" id="manualBtn" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
       <div class="modal-content">
       <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="online">手动确认</h4>
	   </div>
       <div class="modal-body">
       <form id="reasonValidate" >
       <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">划扣平台：</label>
				<select id="manualDeducts" class="select180 " >
				    		<option value="">请选择</option>
						<c:forEach items="${fns:getDictLists('1,2,3', 'jk_deduct_plat')}" var="item">
								<option value="${item.value}">${item.label}</option>
						</c:forEach>
				 </select>
				</td>
				<input type="hidden" id="check">
            </tr>
              <tr>
                <td><label class="lab">划扣结果：</label>
                <input type="radio" class="mr10" name="sort" checked="checked"value="2">划扣成功
                <input type="radio" class="mr10" name="sort"  value="1">划扣失败
				</td>
				
            </tr>
             <tr>
				<td >
						<div  id="back_div" style="display: none" >
									<label class="lab"><span style="color: red;">*</span>失败原因：</label>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<textarea rows="" cols="" class=" required textarea_refuse" name="consLoanRemarks"
									id="remark"></textarea>
									</p>
								</div>
				</td>
			</tr>
        </table>
        </form>
     </div>
    <div class="modal-footer">
    <button class="btn btn-primary"  class="close"  id="manualSureBtn">确认</button>
    <button class="btn btn-primary"  class="close" data-dismiss="modal" onclick="closeModal('online')">取消</button>
    </div>
    </div>
	</div>
 </div>
</body>
</html>