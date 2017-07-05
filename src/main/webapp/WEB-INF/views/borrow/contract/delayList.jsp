<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<meta name="decorator" content="default" />
<title>延期列表</title>
<script language="javascript">

function page(n, s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#postponeform").attr("action", "${ctx}/apply/contractAudit/delayList");
	$("#postponeform").submit();
	return false;
}
$(document).ready(function() {
		 loan.getstorelsit("storeNames","storeOrgIds");		
		//全选绑定
		$('#checkAll').bind('click',function() {
			if ($('#checkAll').attr('checked') == 'checked'|| $('#checkAll').attr('checked')) {
				$("input[name='checkBoxItem']").each(function() {
					$(this).attr('checked', true);
				});
			}else{
				$("input[name='checkBoxItem']").each(function() {
					$(this).attr('checked', false);
				});
			}
		});
		
		$("[name='checkBoxItem']").click(function() {
			var checkBox = $("input:checkbox[name='checkBoxItem']").length;
			var checkBoxchecked = $("input:checkbox[name='checkBoxItem']:checked").length;
			if(checkBox==checkBoxchecked){
				$("#checkAll").attr("checked", true);
			}else{
				$("#checkAll").attr("checked", false);
			}
		});
		//搜索绑定
		$('#searchBtn').bind('click',function() {
					
		});
		
		$('#postponeExport').bind('click',function() {
			var idVal = "";
			var delayEntity = $("#postponeform").serialize();
			if ($(":input[name='checkBoxItem']:checked").length > 0) {
				$(":input[name='checkBoxItem']:checked").each(function(){
							if (idVal != "") {
								idVal += ","+ $(this).attr("contractCode");
							} else {
								idVal = $(this).attr("contractCode");
							}
						});
			}
			window.location.href = ctx
			+ "/apply/contractAudit/postponeExl?delayEntity="+ delayEntity +"&ids="+idVal;
		
		});
		//隐藏/显示绑定
		$('#showMore').bind('click', function() {
			show();
		});
		//清除按钮绑定
		$('#clearBtn').bind('click', function() {
			$(':input','#postponeform') 
			.not(':button, :reset') 
			.val('') 
			.removeAttr('checked') 
			.removeAttr('selected'); 
			if($("select").length>0){ 
			$("select").each(function(){ 
			$(this).trigger("change"); 
			}); 
			}
		});
		
		 $.popuplayer(".edit");
});

 function postponeFunction(loanCode,applyId,contractCode,dictLoanStatus,obj){
			var postponeTime = $(obj).prev().val();
			if(postponeTime == null || postponeTime == ""){
				art.dialog.alert("请选择延期日期！");
				return
			} 
			$.ajax({
				type : 'post',
				url : ctx+'/apply/contractAudit/postpone',
				data :{
					'loanCode':loanCode,
					'applyId':applyId,
    				'postponeTime':postponeTime,
    				'contractCode':contractCode,
    				'dictLoanStatus':dictLoanStatus
    				},
				cache : false,
				dataType : 'json',
				async : false,
				success : function(result) {
					if(result.result){
						art.dialog.alert(result.msg);
						window.location.href = ctx + "/apply/contractAudit/delayList";
					}else{
						art.dialog.alert(result.msg);
					}
				}
			}); 
}
</script>
</head>
<body >
      <div class="control-group">
       
		 <form:form id="postponeform" modelAttribute="delayEntity" action="${ctx}/apply/contractAudit/delayList">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input type="hidden" name="menuId" value="${param.menuId }">
			<table class=" table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					    <form:input path="customerName" name="customerName" value="${delayEntity.customerName}" class="input_text178" />
					</td>
					<td><label class="lab">合同编号：</label>
				        <form:input path="contractCode" name="contractCode" value="${delayEntity.contractCode}" class="input_text178" />
					</td>
					<td><label class="lab">门店：</label>
					  <form:input path="storeNames" id="storeNames" class="input_text178" readonly="true"/> 
					  <i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
				   		 <form:hidden path="storeOrgIds" id="storeOrgIds" />
				 	</td>
					
				</tr>
				 <tr> 
				 <td><label class="lab">身份证号：</label>
				        <form:input path="customerCertNum" name="customerCertNum" value="${delayEntity.customerCertNum}" class="input_text178" />
				 </td>
				  <td><label class="lab">模式：</label> 
						<select name="model" class="select180" id="model">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                  <option value="${loanmodel.value }"
	                                   <c:if test="${delayEntity.model == loanmodel.value }">selected</c:if>>
	                                  <c:if test="${loanmodel.value=='0'}">
	                                    
	                                  </c:if>
	                                  <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                  </option>
	                           </c:forEach>
	                      </select>
	             	</td>
    				<td><label class="lab">渠道：</label>
					    <form:select path="loanFlag" class="select180" id ="loanFlag">
						  <option value=''>全部</option>
						  <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="mark">
							<c:if test="${mark.label!='TG' && mark.label!='附条件'}">
							  <option value="${mark.value}"
							  <c:if test="${delayEntity.loanFlag == mark.value}">
								     selected=true
							  </c:if>>${mark.label}
							  </option>
							</c:if>
						  </c:forEach>
						</form:select>
					</td>
				
				</tr>
				<tr id="T1" style="display: none">
				  <td><label class="lab">产品：</label>
				    <form:select path="productType" value="${delayEntity.productType}" class="select180">
						<option value=''>全部</option>
						<c:forEach items="${productList}" var="product">
						   <option value="${product.productCode}" 
						     <c:if test="${product.productCode == delayEntity.productType}">selected=true</c:if>
								>${product.productName}</option>
						  </c:forEach>
					 </form:select>
				  </td>
				  <td><label class="lab">风险等级：</label>
					      <select name="riskLevel" class="select180">
					          <option value=''>全部</option>
			    	        <c:forEach items="${fns:getDictList('jk_loan_risk_level')}" var="item">
					           <option value="${item.value}"
									 <c:if test="${delayEntity.riskLevel==item.value}">
									    selected=true
									  </c:if>
									>
									${item.label}
								  </option>
					         </c:forEach>
					     </select>
				   </td>
				   <td >
					  <label class="lab">是否电销：</label>
			           <select name="customerTelesalesFlag" class="select180">
						   <option value="">全部</option>
						   <c:forEach items="${fns:getDictList('yes_no')}" var="rsSrc">
						         <option value="${rsSrc.value}"
									 <c:if test="${delayEntity.customerTelesalesFlag==rsSrc.value}">
									    selected=true
									  </c:if>
									>${rsSrc.label}
								  </option>
						    </c:forEach>
						 </select>
					</td>
			    </tr>
			    <tr id="T2" style="display: none">
			         <td>
    			      <label class="lab">是否冻结：</label>
			          <select name="frozenCode" class="select180">
						   <option value="">全部</option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="frozenFlag">
						    	<option value="${frozenFlag.value}"
									 <c:if test="${delayEntity.frozenCode==frozenFlag.value}">
									    selected=true
									  </c:if>
									>${frozenFlag.label}
								</option>
		     				</c:forEach>
					 </select>
               	  </td>
			       <td> <label class="lab">合同版本号：</label>
				      <select name="contractVersion" class="select180">
							<option value=''>全部</option>
							<c:forEach items="${fns:getDictList('jk_contract_ver')}" var="mark">
								<option value="${mark.value}"
								 <c:if test="${delayEntity.contractVersion==mark.value}">
								    selected=true
								  </c:if>
								>${mark.label}</option>
							</c:forEach>
					  </select> 
				    </td>
					<td><label class="lab">借款状态：</label>
				    <form:select path="dictLoanStatus" value="${delayEntity.dictLoanStatus}" class="select180">
						<option value=''>全部</option>
						<c:forEach items="${statusList}" var="lab">
						   <option value="${lab.code}" 
						   <c:if test="${lab.code==delayEntity.dictLoanStatus }">selected=true</c:if>
								>${lab.name}</option>
						  </c:forEach>
					 </form:select>
				  </td>
				</tr>
				<tr id="T3" style="display: none">
				<td>
					<label class="lab">合同审核日期：</label>
	               <input  name="startContractTime"  id="startContractTime"  
	                 value="<fmt:formatDate value='${delayEntity.startContractTime}' pattern='yyyy-MM-dd'/>"
	                     type="text" class="Wdate input_text70" size="10"  
	                                       onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endContractTime\')}'})" style="cursor: pointer" ></input>
	               -
	               <input  name="endContractTime" 
	                 value="<fmt:formatDate value='${delayEntity.endContractTime}' pattern='yyyy-MM-dd'/>"
	                     type="text" class="Wdate input_text70" size="10" id="endContractTime"  
	                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startContractTime\')}'})" style="cursor: pointer" ></input>
				</td>
				
				<td>
					<label class="lab">费率审核日期：</label>
	               <input  name="startTateTime"  id="startTateTime"  
	                 value="<fmt:formatDate value='${delayEntity.startTateTime}' pattern='yyyy-MM-dd'/>"
	                     type="text" class="Wdate input_text70" size="10"  
	                                       onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endTateTime\')}'})" style="cursor: pointer" ></input>
	               -
	               <input  name="endTateTime" 
	                 value="<fmt:formatDate value='${delayEntity.endTateTime}' pattern='yyyy-MM-dd'/>"
	                     type="text" class="Wdate input_text70" size="10" id="endTateTime"  
	                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startTateTime\')}'})" style="cursor: pointer" ></input>
				</td>
				<td>
					<label class="lab">确认签署日期：</label>
	               <input  name="startSignconfirmTime"  id="startSignconfirmTime"  
	                 value="<fmt:formatDate value='${delayEntity.startSignconfirmTime}' pattern='yyyy-MM-dd'/>"
	                     type="text" class="Wdate input_text70" size="10"  
	                                       onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endSignconfirmTime\')}'})" style="cursor: pointer" ></input>
	               -
	               <input  name="endSignconfirmTime" 
	                 value="<fmt:formatDate value='${delayEntity.endSignconfirmTime}' pattern='yyyy-MM-dd'/>"
	                     type="text" class="Wdate input_text70" size="10" id="endSignconfirmTime"  
	                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startSignconfirmTime\')}'})" style="cursor: pointer" ></input>
				</td>
				</tr>
				<tr id="T4" style="display: none">
					<td colspan="2" >
					<label class="lab">汇诚审核时间：</label>
	               <input  name="startLoanAuditTime"  id="startLoanAuditTime"  
	                 value="<fmt:formatDate value='${delayEntity.startLoanAuditTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
	                     type="text" class="Wdate input_text178" size="10"  
	                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen',maxDate:'#F{$dp.$D(\'endLoanAuditTime\')}'})" style="cursor: pointer" ></input>
	               -
	               <input  name="endLoanAuditTime" id="endLoanAuditTime"
	                 value="<fmt:formatDate value='${delayEntity.endLoanAuditTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
	                     type="text" class="Wdate input_text178" size="10"  
	                                        onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen',minDate:'#F{$dp.$D(\'startLoanAuditTime\')}'})" style="cursor: pointer" ></input>
				</td>
				</tr>
			</table> 
			</form:form> 
			<div class="tright pr30 pb5">
				<button class="btn btn-primary" id="searchBtn" onclick="return page();">搜索</button>
				<button class="btn btn-primary" id="clearBtn">清除</button>
			
			<div style="float: left; margin-left: 45%; padding-top: 10px">
				<img src="../../../static/images/up.png" id="showMore" ></img>
			</div>
		</div>
		</div>
		 <%--    <sys:columnCtrl pageToken="outside"></sys:columnCtrl> --%>
		 <p class="mb5">
			<button class="btn btn-small" id="postponeExport">导出Excel</button>
		</p> 
		<div class="box5">
           <table  id="tableList"class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
                <thead>
                    <tr>
                    	<th><input type = "checkbox" id="checkAll"/></th>
                        <th>序号</th>
						<th>合同编号</th>
						<th>版本号</th>
                        <th>客户姓名</th>
                        <th>共借人</th>
                        <th>自然人保证人</th>
                        <th>省份</th>
                        <th>城市</th>
                        <th>门店</th>
                        <th>产品</th>
                        <th>状态</th>
                        <th>批复金额</th>
                        <th>批复期数</th>
                        <th>是否电销</th>
                        <th>进件时间</th>
                        <th>汇诚审批时间</th>
                        <th>确认签署日期</th>
                        <th>费率审核日期</th>
                        <th>合同审核日期</th>
                        <th>是否冻结</th>
                        <th>加急标识</th>
                        <th>模式</th>
						<th>渠道</th>
						<th>风险等级</th>
                        <th>操作</th>
                    </tr>
                   </thead>
                   <tbody id="prepareListBody">
                   <c:forEach items="${page.list }" var="ps" varStatus="xh">
                      <tr>
                      	<td><input type="checkbox" name="checkBoxItem" contractCode = "${ps.contractCode}"/>
                        <td>${xh.count+(page.pageSize*(page.pageNo-1))}</td>
                        <td>${ps.contractCode}</td>
                        <td>${ps.contractVersion}</td>
                        <td>${ps.customerName}</td>
                        <td>                     
                        	<c:if test="${ps.loaninfoOldornewFlag eq '0' || ps.loaninfoOldornewFlag eq ''}">
                        		${ps.coboName}
                        	</c:if>
                        </td>
                        <td>
                        	<c:if test="${ps.loaninfoOldornewFlag eq '1'}">
                        		${ps.bestCoborrower}
                        	</c:if>
                        </td> 
                        <td>${ps.provinceName}</td>
                        <td>${ps.cityName}</td>
                        <td>${ps.storeName}</td>
                        <td>${ps.productName}</td>
                        <td>
                         <c:choose>
                           <c:when test="${ps.frozenCode != null && ps.frozenCode!=' ' && ps.frozenCode!=''}">
                             ${ps.dictLoanStatusName}(门店申请冻结)
                           </c:when>
                           <c:otherwise>
                           
                           ${ps.dictLoanStatusName}
                           </c:otherwise>
                         </c:choose>
                        </td>
                        <td><fmt:formatNumber value="${ps.loanAuditAmount}" pattern=".00"/> </td>
                        <td>${ps.loanAuditMonths}</td>
                        <td>
                        <c:if test="${ps.customerTelesalesFlag=='0'}">否</c:if>
                        <c:if test="${ps.customerTelesalesFlag=='1'}">是</c:if>
                        </td>
                        <td><fmt:formatDate value="${ps.customerIntoTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${ps.loanAuditTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${ps.signconfirmTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${ps.rateTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${ps.contractTime}" pattern="yyyy-MM-dd"/></td>
                        <td>
                        	<c:choose>
                           <c:when test="${ps.frozenCode != null && ps.frozenCode!=' ' && ps.frozenCode!=''}">
                          	   是
                           </c:when>
                           <c:otherwise>
                                                                              否
                           </c:otherwise>
                         </c:choose>
                        </td>
                        <td>
                        <c:if test="${ps.loanUrgentFlag=='1'}">加急</c:if>
                        </td>
                        <td><c:if test="${ps.model=='TG'}">${ps.model}</c:if></td>
                        <td>${ps.loanFlagName }</td>
                        <td>${ps.riskLevel }</td>
                        <td style="position:relative">
                           <input type="button" class="btn btn_edit edit" value="延期" />
                           <div class="alertset" style="display:none">
                           	 延期至：	 <input  id="postponeTime"  name="postponeTime"
	                    		 type="text" class="Wdate input_text70" size="10"  
	                             onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>
	                             <input type="button" class="btn btn-primary" value="确定"  onclick="postponeFunction('${ps.loanCode}','${ps.applyId}','${ps.contractCode}','${ps.dictLoanStatus }',this);"/>
                           </div>	
                           <a class="jkhj_createddoc_history" href="#" onclick="showAllHisByLoanCode('${ps.loanCode}')">历史</a>
                        </td> 
                      </tr>
                   </c:forEach>
                   </tbody>
                </table>
                </div>
			  <div class="pagination">${page}</div>
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-240,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
