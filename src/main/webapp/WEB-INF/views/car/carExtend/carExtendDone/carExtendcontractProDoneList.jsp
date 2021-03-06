<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借展期合同制作已办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendDetailsCheck.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		 page(1,${page.pageSize});
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
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
	 var contractNo='<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="dict" varStatus="status"><c:if test="${status.last}">${dict.label }</c:if></c:forEach>';
	 $(".contractNo").html(contractNo);
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#inputForm").attr("action", "${ctx}/common/carHistory/contractProDoneListExtend");
      $("#inputForm").submit();
      return false;
      
  }
  function contractsignUpCA(contractCode){
	  $.ajax({
		   type: "GET",
		   url : ctx + "/car/carExtendContract/contractsignUpCA?contractCode="+contractCode,
		   success: function(data){
			   if(data=="true"){
				   art.dialog.alert("合同CA签章成功！");
			   }else{
				   art.dialog.alert("合同CA签章失败！");
			   }
		  }
	});
  }
  /**
   * 弹出车借合同协议预览和下载 loanCode flag
   */
   function signDocShow(applyId,loanCode,contractCode){
 		 var url =ctx+"/car/carContract/checkRate/initPreviewSignContract?contractCode="+contractCode;
 	     art.dialog.open(url, {  
 			   id: 'protcl',
 			   title: '协议查看',
 			   lock:false,
 			   width:'95%',
 			   height:'95%',
 			   left:'0%',
 			   top:'0%',
 			   resize:true
 			},false); 
 	}
    function getImageUrl(loancode){
	 	$.ajax({
	  		type:'post',
	 		url :ctx + '/carpaperless/confirminfo/getExendImageUrl',
	 		data:{
	 			'loancode':loancode,'status':31
	 		},
	 		cache:false,
	 		dataType:'text',
	 		async:false,
	 		success:function(result) {
	 			if(""!=result&&null!=result){
	 				art.dialog.open(result, {
	 					title: "客户影像资料",	
	 					top: 80,
	 					lock: true,
	 					    width: 1000,
	 					    height: 450,
	 				}, false);	

	 			}
	 		},
	 		error : function() {
	 			art.dialog.alert('请求异常！', '提示信息');
	 		}
	    	});
	 }
  
</script>
</head>
<body>
	<div class="control-group">
		<form:form id="inputForm" modelAttribute="carLoanStatusHisEx"
			method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input name="menuId" type="hidden" value="${param.menuId}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table id="searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input
							id="customerName" path="loanCustomerName" class="input_text178" />
					</td>
					<td><label class="lab">门店：</label> <form:input id="txtStore"
						path="storeName" maxlength="20"
						class="txt date input_text178" value="${secret.store }" /> <i
						id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<td><label class="lab">产品类型：</label> <form:select
							id="productType" path="productType" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fncjk:getPrd('products_type_car_credit')}"
								var="product_type">
								<form:option value="${product_type.productCode}">${product_type.productName}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr>
					<td><label class="lab">身份证号：</label> <form:input
							path="certNum" class="input_text178" /></td>
					<td><label class="lab">合同编号：</label> <form:input
							path="contractCode" class="input_text178" /></td>
					<td><label class="lab">借款期限：</label>
					<form:select path="contractMonths" class="select180">
	                	<option value="">请选择</option>
						<c:forEach items="${fncjk:getPrdMonthsByType('products_type_car_credit')}" var="product_type">
							<form:option value="${product_type.key}">${product_type.value}</form:option>
						</c:forEach>
					</form:select></td>
				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">签约日期：</label> <form:input
						path="contractFactDay" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						style="cursor: pointer" class="Wdate input_text178" type="text"
						 size="10" /></td>
					<td><label class="lab">借款状态：</label> <form:select
							id="loanMonths" path="loanStatusCode" class="select180">
							<option value="">全部</option>
							<form:options items="${fns:getDictList('jk_car_loan_status')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
						<td><label class="lab">渠道：</label>
		             	<form:select id="loanFlag" path="loanFlag" class="select180">
		                   <option value="">请选择</option>
			                    <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			              </c:forEach>
				         </form:select>
		        </td>
		        </tr><tr id="T2" style="display: none">
		        	<td><label class="lab">无纸化：</label>
		             	<form:select id="paperLessFlag" path="paperLessFlag" class="select180">
		                   <option value="">请选择</option>
			               <option value="1">是</option> 
			               <option value="0">否</option>  
				         </form:select>
		        </td>
		            <td>
		              <label class="lab">系统来源：</label>
	                   <form:select id="dictSourceType" path="dictSourceType" class="select180">
							<option value="">请选择</option>
			                <option value="2">2.0</option> 
			                <option value="3">3.0</option>
						</form:select>
		            </td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
			
				<form:input type="hidden" id="hdStore" path="storeCode"/></td>
				<input type="button" class="btn btn-primary" id="searchBtn"
					value="搜索"></input> <input type="button" class="btn btn-primary"
					id="clearBtn" value="清除"></input>
				<div class="xiala" style="text-align: center;">
					<img src="${context}/static/images/up.png" id="showMore"></img>
				</div>
			</div>
	</div>
	</form:form>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
	<sys:columnCtrl pageToken="carExtendConProDoeList"></sys:columnCtrl>
	<span style="float:right;left: 145px; top: 43px;color:red;font-size:14px;" >当前合同版本号：<span class="contractNo"></span></span>
		<table id="tableList" class="table table-bordered table-condensed table-hover"
			style="margin-bottom: 200px;">
			<thead>
				<tr>
					<th>序号</th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>共借人</th>
					<th>门店名称</th>
					<th>所属省份</th>
					<th>合同金额</th>
					<th>降额</th>
					<th>展期费用</th>
					<th>展期应还总金额</th>
					<th>借款期限(天)</th>
					<th>产品类型</th>
					<th>车牌号码</th>
					<th>已展期次数</th>
					<th>借款状态</th>
					<!-- <th>是否电销</th> -->
					<th>渠道</th>
					<th>系统来源</th>
					<th>是否无纸化</th>
					<th>是否加盖成功</th>
					<th>合同版本号</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:if test="${ page.list != null && fn:length(page.list)>0}">
				<c:forEach items="${page.list}" var="item" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${item.contractCode}</td>
						<td>${item.loanCustomerName}</td>
						<td>${item.coborrowerName }</td>
						<td>${item.storeName}</td>
						<td>${item.provinceName }</td>
						<td><fmt:formatNumber value='${item.contractAmount}' pattern='#,##0.00' /></td>
						<td><fmt:formatNumber value='${item.derate}' pattern='#,##0.00' /></td>
						<td><fmt:formatNumber value='${item.extensionFee}' pattern='#,##0.00' /></td>
						<td><fmt:formatNumber value='${item.extendRepayMoney }' pattern='#,##0.00' /></td>
						<td>${item.loanMonths}</td>
						<td>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.productType)}</td>
						<td>${item.plateNumbers }</td>
						<td>${item.extendNum }</td>
						<td>${item.loanStatusCode}</td>
						<%-- <td>${item.telesalesFlag}</td> --%>
						<td>${item.loanFlag}</td>
						<td><fmt:formatNumber value="${item.dictSourceType}"  pattern="0.0"/></td>
						<td>
							<c:choose>
								<c:when test="${item.paperLessFlag=='1'}">是</c:when>
								<c:otherwise >否</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.signFlag=='1'}">是</c:when>
								<c:otherwise >否</c:otherwise>
							</c:choose>
						</td>
						<td>${item.contractVersion }</td>
						<td>
						<input type="button" value="查看" class="btn_edit jkcj_carExtendcontractProDoneList_view" id="butss"
						 onclick="extendContractDone('${item.applyId}')"></input>&nbsp;
						 <input type="button" class="btn_edit jkcj_extensionProductionContract_video" onclick="getImageUrl('${item.loanCode}')"  value="查看影像">&nbsp;
		                <input  class="btn_edit jkcj_carExtendContractDone_history" onclick="showCarLoanHis('${item.loanCode}')"  type="button" value="历史" />
		                <c:if test="${item.loanStatusCode=='待还款申请'&&item.signFlag!='1'}">
		                	<button class="btn_edit jkcj_carExtendcontractProDoneList_sigh" onclick="contractsignUpCA('${item.contractCode}')" >合同加盖</button>
		                </c:if>
		                <c:if test="${item.signFlag=='1'}">
                            <button  class="btn_edit jkcj_carExtendcontractProDoneList_viewcontract"   onclick="javascript:signDocShow('','${item.loanCode}','${item.contractCode}');">
                                                                            电子协议查看
                            </button>
                        </c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ page.list==null || fn:length(page.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有已办数据</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div class="pagination">${page}</div>
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-230,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>