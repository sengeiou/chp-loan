<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>畅捷实名认证页面</title>
<script type="text/javascript" src="${context}/js/payback/paybacksplit.js"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
function centerReductApply(){
	
	        art.dialog.confirm('确认实名认证？', 
			    function () {
			    	 var dialog = art.dialog({
					      content: '认证中。。。',
					      cancel:false,
					      lock:true
				 	 });
				     $("#CenterapplyPayForm").attr("action",ctx+"/borrow/certification/ChangJieSignService");
				     $("#CenterapplyPayForm").submit();
			  }, 
			    function () {
			      // art.dialog.tips('');
			  });
}
$(function(){
	   $("#queryCount").click(function(){
		   
		      var   bank =    $("#bankId").val();
		      var   dayName= $("#repayDate").val();
		      var  yearReplayDay = $("#search_yearReplayDay").val();
		      var   tlSign  = $("#searchtlSign").val();
		      var  model = $("#search_model_id").val();
		      var queryRule = $("#search_queryRule_id").val();
		      var overdueDays = $("#search_overdueDays_id").val();
		      var overCount = $("#search_overCount_id").val();
		      var loanStatus = $("#search_loanStatus_id").val();
			  $.ajax({  
				    type : "POST",
				    data : {'bank':bank,'dayName':dayName,'yearReplayDay':yearReplayDay,'tlSign':tlSign,
				    	     'queryRule':queryRule,'overdueDays':overdueDays,'overCount':overCount,'loanStatus':loanStatus,
				    	     'model':model
				    },
					url :ctx+"/borrow/certification/queryCount",
					datatype : "json",
					async: false,
					success : function(data){
						if(data){
							 $("#querycountId").html("<p >数据条数："+data+"</p>")
						 }
					 },
					error: function(){
						art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
					}
			  });
	      })
	})
	
	function stopOrStart(flag){
	  $.ajax({  
		    type : "POST",
			url :ctx+"/borrow/certification/stopSms",
			datatype : "json",
			data : {"flag":flag},
			async: false,
			success : function(data){
				if(data){
					if(flag=='1'){
						art.dialog.alert("停止成功！！！");
					}
					if(flag=='2'){
						art.dialog.alert("启动成功！！！");
					}
				
				 }
			 },
			error: function(){
				art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
			}
	  });
}

</script>
<style>
body{TEXT-ALIGN: center;}
#querycountId{ MARGIN-RIGHT: auto;
MARGIN-LEFT: auto;
height:200px;
width:400px;
vertical-align:middle;
line-height:200px;
}
</style>
</head>
<body>
	<div class="control-group" >
          <form method="post" action="${ctx}/borrow/certification/ChangJieSignService" id="CenterapplyPayForm">
		      <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		            <tr>
		                <td><label class="lab" >开户行名称：</label>
		                <sys:multipleBank bankClick="selectBankBtn" bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
		                <input id="search_applyBankName" type="text" class="input_text178" name="search_applyBankName"  value='${paramMap.applyBankName}'>&nbsp;<i id="selectBankBtn"
								class="icon-search" style="cursor: pointer;"></i> 
								<input type="hidden" id="bankId" name='search_bank' value='${paramMap.bank}'>
		                 <td><label class="lab">还款日：</label>
						  <sys:multirepaymentDate dateClick="dateClick" dateId="repayDate"></sys:multirepaymentDate>
						  <input id="repayDate"  name="search_dayName" class="input_text178" readonly ="readonly"   value="${paramMap.dayName}">
						  <i id="dateClick" class="icon-search" style="cursor: pointer;"></i> 
						 </td>
						 <td><label class="lab">还款日（年月日）：</label><input name="search_yearReplayDay" id="search_yearReplayDay"  type="text" readonly="readonly" maxlength="40" class="input_text70 Wdate"
					       value="<fmt:formatDate value="${paramMap.yearReplayDay}" pattern="yyyy-MM-dd"/>"
					       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'search_yearReplayDay\')}'});" style="cursor: pointer"/>
					     </td>
		            </tr>
		            <tr>
		                <td><label class="lab">发送条数：</label>
		                   <input type="text" class="input_text178" name="search_quantity" value="${paramMap.quantity}"></td>
					    </td>
					    
					     <td><label class="lab">通联批量签约：</label> 
		                  <select class="select180" name="search_tlSign" id="searchtlSign">
		                        <option value="">请选择</option>
			                    <c:forEach var="plat" items="${fns:getDictList('yes_no')}">
										<option value="${plat.value }"
										<c:if test="${paramMap.tlSign==plat.value }">selected</c:if>>${plat.label }</option>
							    </c:forEach>
						    </select>
				         </td>
				         <td><label class="lab">模式：</label><select class="select180" name="search_model" id="search_model_id">
			                <option value="">请选择</option>
					        <c:forEach var="flag" items="${fns:getDictList('jk_loan_model')}">
								<option value="${flag.value }"
									<c:if test="${paramMap.model==flag.value }">selected</c:if>>
									<c:if test="${flag.value=='1' }">${flag.label}</c:if>
									<c:if test="${flag.value!='1' }">非TG</c:if>
								</option>
							</c:forEach>
			                </select>
			                </td>
		             </tr>
		            
		         <tr>
		               <td><label class="lab">逾期天数：</label>
		                      <select class="select180" name="search_queryRule" id ="search_queryRule_id">
			                    <option value="">请选择</option>
								<option <c:if test="${paramMap.queryRule=='0'}">selected</c:if> value="0" > &lt;X </option>
								<option <c:if test="${paramMap.queryRule=='1'}">selected</c:if> value="1" > &gt;=X </option>
			                   </select>
		                    <input type ="number"  name ="search_overdueDays"  min="1" max="1000" value ="${paramMap.overdueDays}" id ="search_overdueDays_id">
		                </td>
		                
		                 <td><label class="lab">累计逾期期数：</label>
			                    <input type ="number"  name ="search_overCount"  min="1" max="1000" value ="${paramMap.overCount}" id ="search_overCount_id">
			             </td>
		                
			           <td><label class="lab">借款状态：</label>
			             <select class="select180" name="search_loanStatus" id ='search_loanStatus_id'>
		                 <option value="">请选择</option>
		                 <option value="87" <c:if test="${paramMap.loanStatus=='87'}">selected</c:if>>还款中</option>
						 <option value="88" <c:if test="${paramMap.loanStatus=='88'}">selected</c:if>>逾期</option>
						 <option value="89" <c:if test="${paramMap.loanStatus=='89'}">selected</c:if>>结清</option>
						 <option value="90" <c:if test="${paramMap.loanStatus=='90'}">selected</c:if>>提前结清</option>
						 <option value="91" <c:if test="${paramMap.loanStatus=='91'}">selected</c:if>>提前结清待审核</option>
						 <option value="92" <c:if test="${paramMap.loanStatus=='92'}">selected</c:if>>结清待确认</option>
	                         </select>
	                  </td>
		            </tr>
		        </table>
          </form>
          <div class="tright pr30 pb5 ">
					<button class="btn btn-primary" onclick="centerReductApply()">提交</button>
					<button class="btn btn-primary"  id ="queryCount">查询条数</button>
				    <button class="btn btn-primary"  onclick = "stopOrStart('1')">停止发送</button>
					<button class="btn btn-primary"  onclick = "stopOrStart('2')">开始发送</button>
	     </div>
	 </div>
	 <div id ='querycountId' ><div>
</body>
</html>