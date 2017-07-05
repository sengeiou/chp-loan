<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借已划扣列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
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
					art.dialog.alert("划扣开始日期不能大于划扣结束日期!",function(){
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
		        	 window.location.href = ctx + "/car/grant/grantDeducts/exportExcel2?idVal=" + idVal + "&" + carLoanStatusHisEx;
		        	 });
		  }else{
			  art.dialog.confirm("确认对所有的划扣已办数据导出？",function(){
		        	 window.location.href = ctx + "/car/grant/grantDeducts/exportExcel2?idVal=" + idVal + "&" + carLoanStatusHisEx;
		        	 });
		  }
	  });
	 
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#inputForm").attr("action", "${ctx}/common/carHistory/drawDoneList");
      $("#inputForm").submit();
      return false;
  }  
</script>
</head>
<body>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carLoanStatusHisEx" method="post" class="form-horizontal">
     <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
     <input name="menuId" type="hidden" value="${param.menuId}" />
	 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
     <table id = "searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="loanCustomerName" class="input_text178"/>
             </td>
             <td><label class="lab">合同编号：</label>
             	<form:input id="contractCode" path="contractCode" class="input_text178"/>
             </td>
             <td><sys:multipleBank bankClick="selectBankBtn"
							bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
						<label class="lab">开户行：</label><form:input type="text"
							id="search_applyBankName" name="search_applyBankName"
							class="input_text178" path="midBankName"></form:input> <i
						id="selectBankBtn" class="icon-search" style="cursor: pointer;"></i>
			</td>
         </tr>
         <tr>
             <td><label class="lab">产品类型：</label>
            <!-- 	<form:select id="productTypeContract" path="productTypeContract"  class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_car_loan_product_type')}" var="product_type">
		                             <form:option value="${product_type.value}" name="productTypeContract" >${product_type.label},${product_type.value}</form:option>
		            </c:forEach>  -->
		         </form:select>
		         <form:select id="productType" path="productType" class="select180">
                   <option value="">请选择</option>
                   <form:options items="${fncjk:getPrd('products_type_car_credit')}" itemLabel="productName" itemValue="productCode" htmlEscape="false"/>
		         </form:select>
             </td>
             <td>
             	<label class="lab">划扣日期：</label>
             	<input type="text" id="urgeDecuteDateStart" name="urgeDecuteDateStart" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" class="input_text70 Wdate" value="<fmt:formatDate value='${carLoanStatusHisEx.urgeDecuteDateStart}' type='date' pattern='yyyy-MM-dd' />" />-
             	<input type="text" id="urgeDecuteDateEnd" name="urgeDecuteDateEnd" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" class="input_text70 Wdate" value="<fmt:formatDate value='${carLoanStatusHisEx.urgeDecuteDateEnd}' type='date' pattern='yyyy-MM-dd' />" />
             </td>
             <td><label class="lab">划扣平台：</label><form:select id="dictDealType" class="select180" path="dictDealType">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
				</form:select>
				</td>
         </tr>
		 <tr id="T1" style="display: none">
		 	
		 	<td>
		 		<label class="lab">回盘结果：</label>
		 		<form:select id="dictDealStatus" path="dictDealStatus" class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_counteroffer_result')}" var="dictDealStatus">
		                             <form:option value="${dictDealStatus.value}">${dictDealStatus.label}</form:option>
		            </c:forEach>  
		         </form:select>
		 	</td>
		 	<td>
		 		<label class="lab">银行卡号：</label>
		 		<form:input id="bankCardNo" type="text" path="bankCardNo" class="input_text178" />
		 	</td>
		 	 <td><label class="lab">渠道：</label>
             	<form:select id="loanFlag" path="loanFlag" class="select180">
                   <option value="">请选择</option>
	                    <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			              </c:forEach>
		         </form:select>
             </td>
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
		<th>开户行</th>
		<!-- <th>银行卡号</th>-->
		<th>借款产品</th>
		<th>应划扣金额</th>
		<th>划扣日期</th>
		<th>回盘结果</th>
		<th>回盘原因</th>
		<th>划扣平台</th>
		<th>渠道</th>
		<th>操作</th>
      </tr>
      </thead>
      	<c:if test="${ page.list != null && fn:length(page.list)>0}">
       	<c:forEach items="${page.list}" var="item" varStatus="status"> 
        <tr>
          <td><input class="checks" type="checkbox" name="checkBoxItem" urgeDecuteMoeny="${item.urgeDecuteMoeny }"  value="${item.applyId }"/></td>
          <td>${status.count}</td>
          <td>${item.contractCode}</td>
          <td>${item.loanCustomerName}</td>
          <td>${item.midBankName}</td>
          <!-- <td>${item.bankCardNo}</td>-->
          <td>${item.finalProductType}</td>
          <td><fmt:formatNumber value="${item.urgeMoeny}" type="number" pattern="0.00"/></td>
          <td><fmt:formatDate value="${item.urgeDecuteDate}" pattern="yyyy-MM-dd"/></td>
          <td>${item.dictDealStatus}</td>
          <td>${item.dictDealReason}</td>
          <td>${item.dictDealType}</td>
          <td>${item.loanFlag}</td>
          <td>
              <input type="button" value="历史" class="btn_edit"  onclick="showCarLoanHis('${item.loanCode}')"></input>
              <!-- <input type="button" value="查看" class="btn_edit" id="butss" onclick="showCarLoanInfo('${item.loanCode}')"> -->
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