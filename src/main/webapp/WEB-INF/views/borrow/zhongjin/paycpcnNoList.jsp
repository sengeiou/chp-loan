<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>中金划扣列表</title>
<meta name="decorator" content="default"/>
<script src="${context}/js/zhongjin/zhongjin.js" type="text/javascript"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript">
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#deductsForm").attr("action", "${ctx }/borrow/zhongjin/nolist");
	$("#deductsForm").submit();
	return false;
}
var bankValueArray = new Array();
var bankNameArray = new Array();
	//省市级联
	 $(document).ready(function(){
			 $('#showMore').bind('click',function(){
				 show();

			});
			 loan.initCity("accounProviceU", "accounCityU", null);
		 var msg = '${message}';
		if (msg != "" && msg != null) {
			art.dialog.alert(msg);
		}
	 }); 
</script>
</head>
<body>
	<div class="control-group">
   <form:form  method="post" modelAttribute="UrgeServicesMoneyEx" id="deductsForm" name="deductsForm">
   <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }">
   <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">序号：</label>
                <input type="text" class="input_text178" id="serialnum" name="serialnum" maxlength="12" value="${cpcn.serialnum }"></input></td>
                <td><label class="lab">合同编号：</label>
                <input type="text" class="input_text178" id="contractCode" name="contractCode" maxlength="15" value="${cpcn.contractCode }"></input></td>
                <td><label class="lab">划扣金额：</label>
                <input type="text" class="input_text70" id="beginMoney" name="beginMoney" size="8" maxlength="15" value="${cpcn.beginMoney }">-<input type="text" class="input_text70" id="endMoney" name="endMoney" size="8" maxlength="15" value="${cpcn.endMoney }"></td>
            </tr>
            <tr>
				<td><label class="lab">银行名称：</label>
					<sys:multipleBank bankClick="selectBankBtn" bankName="bankName" bankId="banknum"></sys:multipleBank>
					<input id="bankName" type="text" class="input_text178" name="bankName"  value='${cpcn.bankName}' readonly>&nbsp;<i id="selectBankBtn"
						class="icon-search" style="cursor: pointer;"></i> 
					<input type="hidden" id="banknum" name='banknum' value='${cpcn.banknum}'>
				<%-- <select class="select180" id="banknum" name="banknum" >
            		<option value="">请选择</option>
            		<c:forEach items="${fns:getDictList('jk_open_bank')}" var="item">
						<option value="${item.value}" <c:if test="${item.value eq cpcn.banknum}">selected</c:if>>${item.label}</option>
					</c:forEach>
            	</select> --%></td>
				<td><label class="lab">户名：</label>
				<input type="text" class="input_text178" id="accountName" name="accountName" maxlength="18" value="${cpcn.accountName }"></input></td>
                <td><label class="lab">导入日期：</label>
                <input id="createTime" name="createTime" value="${cpcn.createTime}" type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" /></td>
            </tr>
           <tr id="T1" style="display:none">
            	<td><label class="lab">状态：</label>
            	<select class="select180" id="status" name="status">
						<option value="">请选择</option>
						<!-- <option value="0">待处理</option>
						<option value="1">实时</option>
						<option value="2">批量</option>
						<option value="3">放弃</option>
						<option value="4">预约</option>
						<option value="5">取消预约</option> -->
						<c:forEach items="${fns:getDictList('jk_cpcn_status')}"
								var="item">
								<c:if test="${item.value eq 0 or item.value eq 5}">
								<option value="${item.value}"
									<c:if test="${item.value eq cpcn.status}">selected</c:if>>${item.label}</option>
								</c:if>
							</c:forEach>
				</select></td>
				<td></td>
				<td></td>
            </tr> 
		
        </table>
         
        <div class="tright pr30 pb5"><input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
               <input type="button" class="btn btn-primary" onclick="clear1();" value="清除">
      <div style="float: left; margin-left: 45%; padding-top: 10px">
				  <img src="../../../static/images/up.png" id="showMore"></img>
       </div>
	    </div>
	    </form:form>
		</div>
		<p class="mb5">
    	<button class="btn btn-small" onclick="addPage();">新增</button>
    	<button class="btn btn-small" onclick="openImport();">导入</button>
    	<button class="btn btn-small" onclick="sendUpdate(1);">发送中金(实时)</button>
    	<button class="btn btn-small" onclick="sendUpdate(2);">发送中金(批量)</button>
    	<button class="btn btn-small" onclick="giveupStatus(3);">放弃</button>
    	<button class="btn btn-small" onclick="orderDeduct();">预约划扣</button>
    	&nbsp;&nbsp;<label class="lab1"><span class="red">划扣总金额：</span></label><label id="deductAmount" class="red" >
    	<fmt:formatNumber value='${deductsAmount}'  pattern="#,#0.00"/></label>元
    	<input type="hidden"  id="hiddenDeduct" value="0.00"/>
    	<input type="hidden" id="deduct" value="${deductsAmount}">
		&nbsp;&nbsp;<label class="lab1"><span class="red">划扣总笔数：</span></label>
		<label class="red" id="totalNum">${totalNum }</label>笔
		<input type="hidden" id="num" value="${totalNum }">
		<input type="hidden" id="hiddenNum" value="0"/>
		</p>
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" onclick="checkAll(this);" /></th>
            <th>序列号</th>
            <th>银行账号</th>
            <th>户名</th>
            <th>金额</th>
            <th>银行名称</th>
            <th>账户类型</th>
			<th>分支行省份</th>
			<th>分支行城市</th>
			<th>证件类型</th>
			<th>证件号码</th>
			<th>合同编号</th>
            <th>状态</th>
			<th>备注</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ items!=null && fn:length(items.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${items.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" value='${item.cpcnId }' onclick="chekbox_click();"
               <%-- deductAmount='${item.splitAmount}' reason='${item.splitBackResult}'
               deductsType='${item.dictDealType}' --%> cid='${item.cpcnId }'/>
             </td>
             <td>${item.serialNum}</td>
             <td>${item.accountNum}</td>
             <td>${item.accountName}</td>
             <td>${item.dealMoney}<c:if test="${item.dealMoney eq null}">0.00</c:if><input type="hidden" id="${item.cpcnId }money" name="${item.cpcnId }money" value="${item.dealMoney}<c:if test="${item.dealMoney eq null}">0.00</c:if>"></td>
             <td>${item.bankName}</td>
             <td>${item.accountTypeName}</td>
             <td>${item.accounProvice}</td>
             <td>${item.accounCity}</td>
             <td>${item.certTypeName}</td>
             <td>${item.certNum}</td>
             <td>${item.contractCode}</td>
             <td>${item.status}</td>
             <td>${item.note}</td>          
             <td>
             	<button class="btn_edit" id="history" onclick="updatePage('${item.cpcnId}')">修改</button>
             	<button class="btn_edit" id="history" onclick="showHistory('${item.cpcnId}');">历史</button>
             </td>
         </tr> 
         </c:forEach>  
         </c:if> 
         <c:if test="${ items==null || fn:length(items.list)==0}">
           <tr><td colspan="24" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
      
    </table>
<c:if test="${ items!=null || fn:length(items)>0}">
			  <div class="page">${items}</div>
			</c:if>


       <div id="paycpcnDIV" hidden="">
       <form  id="addForm" name="addForm" method="post" action="${ctx }/borrow/zhongjin/saveOrUpdate">
       <input type="hidden" id="cpcnIdU" name="cpcnId">
       <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab"><font color="red">*</font>银行账号：</label><input type="text" class="input-medium required" id="accountNumU" name="accountNum" maxlength="20"></input><span id="blackTip" style="color: red;"></span></td>
                <td><label class="lab"><font color="red">*</font>户名：</label><input type="text" class="input_text178" id="accountNameU" name="accountName" maxlength="15"></input></td>
            </tr>
            <tr>
            	<td><label class="lab"><font color="red">*</font>金额：</label><input type="text" class="input_text178" id="dealMoneyU" name="dealMoney" maxlength="15"></input></td>
            	<td><label class="lab">合同编号：</label><input type="text" class="input_text178" id="contractCodeU" name="contractCode" maxlength="15"></input></td>
            </tr>
            <tr>
            	<td><label class="lab"><font color="red">*</font>银行名称：</label><select class="select180" id="bankNumU" name="bankNum" >
            		<option value="">请选择</option>
            		<c:forEach items="${fns:getDictList('jk_open_bank')}" var="item">
            			<script> 
            			bankValueArray.push(${item.value});
            			bankNameArray.push('${item.label}'); 
            			</script>
						<option value="${item.value}">${item.label}</option>
					</c:forEach>
            	</select></td>
            	<td><label class="lab"><font color="red">*</font>账户类型：</label><select class="select180" id="accountTypeU" name="accountType">
            		<option value="">-- 请选择 --</option>
					<option value="11">个人账户</option>
					<option value="12">企业账户</option>
            	</select></td>
            </tr>
            <tr>
            	<td><label class="lab">分支行省份：</label><select class="select180" id="accounProviceU" name="accounProvice" >
            		<option value="" selected="selected">选择省份</option>
                	<c:forEach var="item" items="${provinceList}" >
		             	<option value="${item.areaCode }">${item.areaName}</option>
	            	</c:forEach>
            	</select></td>
            	<td><label class="lab">分支行城市：</label><select class="select180" id="accounCityU" name="accounCity">
            		<option value="">请选择</option>
            	</select></td>
            </tr>
            <tr>
            	<td><label class="lab"><font color="red">*</font>证件类型：</label><select class="select180" id="certTypeU" name="certType" >
            		<option value="">-- 请选择 --</option>
            		<option value="0">身份证</option>
            	</select></td>
            	<td><label class="lab">证件号码：</label><input type="text" class="select180" id="certNumU" name="certNum" maxlength="18"></input></td>
            </tr>
            <tr>
            	<td colspan="2"><label class="lab">备注：</label><textarea rows="5" cols="65" id="noteU" name="note" maxlength="60" style="height: 80px;"></textarea></td>
            </tr>
        </table>
        </form>
     </div>

	
     
	<div  id="uploadExcelModel" hidden="">
				<form id="uploadAuditForm" role="form" enctype="multipart/form-data" action="${ctx}/borrow/zhongjin/importExcel" method="POST">
					<input id="file" name="file" type="file" style="display: none" onchange="uploadPage();">
					<input id="photoCover" class="input_text178" type="text" /> 
					<a class="btn btn-primary" onclick="$('input[id=file]').click();">选择文件</a>
				</form>
	</div>
	
	<div  id="loanAccountOrderDeduct" hidden="">
				<form id="orderDeductForm" action="${ctx}/borrow/zhongjin/nolist" method="POST">
					<table id="backTB" class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
						<td>
							<label class="lab"><font color="red">*</font>划扣方式：</label>
							<input type="radio" name="deductType" value="0" checked/><font>实时</font>
							<input type="radio" name="deductType" value="1" /><font>批量</font>
						</td>
					</tr>
            <tr>
						<td>
							<label class="lab"><font color="red">*</font>预约日期：</label>
							<input id="appointmentDate" name="appointmentDate" value="<fmt:formatDate value='${param.orderDate}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate"   
                                        onClick="WdatePicker({skin:'whyGreen'})" readonly/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab"><font color="red">*</font>预约时间：</label>
							<input type="text" readonly="readonly" class="Wdate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm',disabledDates:['..:[1,2,4,5].','..:.[5]']})" onchange="selectIsAppointment(this.value,'0')" value="" maxlength="40" id="appointmentTime" name="appointmentTime">
							<img onclick="addRow();" src="/chp-loan/static/images/u207.png" alt="" title="添加预约时间">
						</td>
					</tr>

        </table>
				</form>
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
</body>
</html>