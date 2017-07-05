<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>预约银行及时间维护列表</title>

<script type="text/javascript">

function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#auditForm").attr("action", "${ctx}/borrow/payback/appointment/queryList");
	$("#auditForm").submit();
	return false;
}
$(document).ready(function() {
	
	//清除按钮绑定事件
 $("#cleBtn").click(function(){
		$(":input").val("");
		$("#auditForm").submit();
 });
	
  $("#newAppoint").click(function(){
		var url = ctx+"/borrow/payback/appointment/goDetail";
		window.location.href = url;
  });
  
  $("#editAppoint").click(function(){
	   var obj = $("input:checkbox[name='checkBox']:checked");
		var length = obj.length;
		if(length==0){
			art.dialog.alert("请选择要修改的数据!");
			return false;
		}
		if(length>1){
			art.dialog.alert("请选择一条数据修改!");
			return false;
		}
		var  id = obj.val();
		var url = ctx+"/borrow/payback/appointment/goDetail?id="+id;
		window.location.href = url;
  })
  
  //删除
   $("#deleteAppoint").click(function(){
	   var obj = $("input:checkbox[name='checkBox']:checked");
		var length = obj.length;
		 if(length == 1){
			     var status = obj.attr("status");
				 if(status == '0'){
					  art.dialog.alert("此条数据生效,不允许删除!");
			
			     }else{
			    	 art.dialog.confirm("请确认是否删除此条数据!", function () {
				       var url = ctx+"/borrow/payback/appointment/deleteData";
				       var id = obj.val();
				               // 单条修改
						    	$.ajax({
						        type: "POST",
						        url: url,
						        data:{'id':id},// 要提交的表单
						        success: function(data){
						        	 if(data.success == true){
						        			art.dialog.alert(data.msg,function(){
						        				window.location.reload(true);
						        			});
						        	  }else{
						        		    art.dialog.alert(data.msg);
						        	  }
						         }
						   }); 
					   }, function () {
						      // art.dialog.tips('');
					  });
		      }
	    }else{
    	 var ids  = '';
    	 var flag = '';
    	 art.dialog.confirm("是否确认批量删除!", function () {
	    	 obj.each(function(){						
				  ids+=$(this).val()+",";
				  status = $(this).attr("status");
				  /* if(status == '0'){
					     flag = '1';
						 art.dialog.alert("存在不允许删除的数据,请重新选择数据!");
					     return false;
					 
				  } */
				});
	    	/*   if(flag){
			    	return false;
			   } */
	    	    $("#ids").val(ids);
	    	    var url = ctx+"/borrow/payback/appointment/deleteData";
				$.ajax({
			        type: "POST",
			        url: url,
			        async: false,
			        data:$('#auditForm').serialize(),// 要提交的表单
			        success: function(data){
			        	 if(data.success == true){
			        			art.dialog.alert(data.msg,function(){
			        				window.location.reload(true);
			               		});
			        	  }else{
			        		  art.dialog.alert(data.msg);
			        	  }
			         }
			   });
	    	 
    	    },function () {
		      // art.dialog.tips('');
			});
	     }
      })
      
      $("#checkAll").click(function() {
  		$("input[name='checkBox']").attr("checked", this.checked);
  	  });
      $("input[name='checkBox']").click(function(){
	       var length = $("input[name='checkBox']:checked").length;
	       var checkLength = $("input[name='checkBox']").length;
	       if(length == checkLength){
	    	   $("#checkAll").attr("checked", true);
	      }else{
	    	  $("#checkAll").attr("checked", false);
	      }
	  
  })
      
});

function changEffect(sign){
	 var obj = $("input:checkbox[name='checkBox']:checked");
	 var length = obj.length;
	 if(length == 1){
		 var status = obj.attr("status");
		 if(sign ==status ){
			 if(sign == '0'){
				 art.dialog.alert("此记录已生效,请重新选择!");
			 }
			 if(sign == '1'){
				 art.dialog.alert("此记录已失效,请重新选择!");
			 }
		 }else{
			  var url = ctx+"/borrow/payback/appointment/changSingleEffect";
			  var id = obj.val();
			  // 单条修改
				$.ajax({
			        type: "POST",
			        url: url,
			        data:{'id':id,'status':sign},// 要提交的表单
			        success: function(data){
			        	 if(data.success == true){
			        			art.dialog.alert(data.msg,function(){
			        				window.location.reload(true);
			        			});
			        	  }else{
			        		    art.dialog.alert(data.msg);
			        	  }
			         }
			   }); 
		   } 
	  }else{
		   var ids  = '';
		  var status = '';
		  var flag = '';
		  var msg = '';
		  if(sign == '0'){
			  msg = '是否确认批量生效？'
			 }
		 if(sign == '1'){
			 msg = '是否确认批量失效？'
		    }
		 
		 art.dialog.confirm(msg, function () {
			 if(length > 1){
				  /*   obj.each(function(){						
					  ids+=$(this).val()+",";
					  status = $(this).attr("status");
					  if(status == sign){
						  if(sign == '0'){
							     flag = '1';
								 art.dialog.alert("存在生效的数据,请重新选择数据!");
							 }
						  if(sign == '1'){
							     flag = '1';
								 art.dialog.alert("存在失效的数据,请重新选择数据!");
						  }
					  }
					});
				    if(flag){
				    	return false;
				    } */
				    
				    obj.each(function(){						
						  ids+=$(this).val()+",";
						});
				    $("#ids").val(ids);
			     }
			   $("#signStatus").val(sign);
                var url = ctx+"/borrow/payback/appointment/changEffect";
				$.ajax({
			        type: "POST",
			        url: url,
			        async: false,
			        data:$('#auditForm').serialize(),// 要提交的表单
			        success: function(data){
			        	 if(data.success == true){
			        			art.dialog.alert(data.msg,function(){
			        				 window.location.href = ctx+"/borrow/payback/appointment/queryList";
			               		});
			        	  }else{
			        		  art.dialog.alert(data.msg);
			        	  }
			         }
			   });
			}, 
				    function () {
				      // art.dialog.tips('');
				});
	     }
}

/**
 * 弹出历史界面(集中划扣)
 * applyId ：借款申请表主键
 */
function showPaybackHis(rPaybackId, payBackApplyId,lisi) {
	if(!lisi){
		lisi='';	
	}
	var url = ctx + "/common/history/showPayBackHis?rPaybackId=" + rPaybackId
			+ "&payBackApplyId=" + payBackApplyId+"&lisi="+lisi;
	art.dialog.open(url, {
		id : 'his',
		title : '预约划扣操作流水',
		lock : true,
		width : 700,
		height : 350
	}, false);
}

function checkNum(obj) {  
    //检查是否是非数字值  
    if (isNaN(obj.value)) {  
        obj.value = "";  
    }  
    if (obj != null) {  
        //检查小数点后是否对于两位http://blog.csdn.net/shanzhizi  
        if (obj.value.toString().split(".").length > 1 && obj.value.toString().split(".")[1].length > 2) {  
        	 art.dialog.alert("小数点后多于两位！");  
            obj.value = "";  
        }  
    }  
} 

//规则设置
function  appointmentRule(){
	var  ruleCodeId = $("#ruleCodeId").val();
	var  appointmentDate = $("#appointmentDate").val();
	
	if(!ruleCodeId){
		 art.dialog.alert("规则不能为空"); 
		 return false;
	}
	if(!appointmentDate){
		art.dialog.alert("预约日期不能为空"); 
		 return false;
	}
	var url = ctx+"/borrow/payback/appointment/appointmentRule";
	$.ajax({
        type: "POST",
        url: url,
        async: false,
        data:{"appointmentDay":appointmentDate,"ruleCode":ruleCodeId},// 要提交的表单
        success: function(data){
        	 if(data.success == true){
        			art.dialog.alert(data.msg,function(){
        				 window.location.href = ctx+"/borrow/payback/appointment/queryList";
               		});
        	  }else{
        		  art.dialog.alert(data.msg);
        	  }
         }
   });
}


</script>
</head>
<body>
<div class="control-group">
    <form id="auditForm" method="post" action="${ctx}/borrow/payback/appointment/queryList" >
    <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="ids"   type="hidden" name ="ids" />
			<input id="signStatus"  type="hidden"  name ="signStatus" />
			 <tr>
		 	  <td><label class="lab">签约平台：</label> <select
							class="select180" name="signPlat" id="signplat">
								<option value="">全部</option>
								<c:forEach var="plat"
									items="${fns:getDictList('jk_deduct_plat')}">
									  <c:if test="${plat.value !='6' && plat.value !='7' && plat.value !='2'}">
									    <option value="${plat.value }"
										   <c:if test="${paramMap.signPlat==plat.value }">selected</c:if>>${plat.label }
										</option>
										</c:if>
								</c:forEach>
						</select>
			   </td>
                <td><label class="lab">银行：</label>
                    <select
							class="select180" name="bank">
								<option value="">全部</option>
								<c:forEach var="bank"
									items="${fns:getDictList('jk_open_bank')}">
									<option value="${bank.value }"
										<c:if test="${paramMap.bank==bank.value }">selected</c:if>>${bank.label }</option>
								</c:forEach>
				    </select>
				</td>
				 <td><label class="lab">预约日期：</label>
				  <input name="appointmentDay" id="appointmentDay"  type="text" readonly="readonly" maxlength="40" class="input-medium Wdate"
					    value="<fmt:formatDate value="${paramMap.appointmentDay}" pattern="yyyy-MM-dd"/>"
					     onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" style="cursor: pointer"/>
			    </td>
            </tr>
            
            <tr>
               	<td><label class="lab">划扣平台：</label> <select
							class="select180" name="deductPlat" id="deductPlat">
								<option value="">全部</option>
								<c:forEach var="plat"
									items="${fns:getDictList('jk_deduct_plat')}">
									<c:if test="${plat.value!='4'}">
									<option value="${plat.value }"
										<c:if test="${paramMap.deductPlat==plat.value }">selected</c:if>>${plat.label }</option>
									</c:if>
								</c:forEach>
						</select></td>
               
                <td><label class="lab">通联是否批量签约：</label> 
                  <select class="select180" name="tlSign">
                        <option value="">全部</option>
	                    <c:forEach var="plat" items="${fns:getDictList('yes_no')}">
								<option value="${plat.value }"
								<c:if test="${paramMap.tlSign==plat.value }">selected</c:if>>${plat.label }</option>
					    </c:forEach>
				    </select>
				</td>
				
				 <td><label class="lab">卡联是否签约：</label><select class="select180" name="klSign">
	                 <option value="">全部</option>
	                 <option value="0" <c:if test="${paramMap.klSign=='0'}">selected</c:if>>否</option>
					 <option value="1" <c:if test="${paramMap.klSign=='1'}">selected</c:if>>是</option>
                  </select>
                </td>
            </tr>
            
            <tr>
              
                <td><label class="lab">借款状态：</label><select class="select180" name="loanStatus">
	                 <option value="">全部</option>
	                 <option value="87" <c:if test="${paramMap.loanStatus=='87'}">selected</c:if>>还款中</option>
					 <option value="88" <c:if test="${paramMap.loanStatus=='88'}">selected</c:if>>逾期</option>
					 <option value="89" <c:if test="${paramMap.loanStatus=='89'}">selected</c:if>>结清</option>
					 <option value="90" <c:if test="${paramMap.loanStatus=='90'}">selected</c:if>>提前结清</option>
					 <option value="91" <c:if test="${paramMap.loanStatus=='91'}">selected</c:if>>提前结清待审核</option>
					 <option value="92" <c:if test="${paramMap.loanStatus=='92'}">selected</c:if>>结清待确认</option>
                 </select>
                 </td>
                 
                   <td><label class="lab">累计逾期期数：</label>
                      <select class="select180" name="overCount">
                      <option value=>全部</option>
                 	  <c:forEach var="overCount" items="${overCountList}">
									<option value="${overCount}"
									<c:if test="${paramMap.overCount==overCount}">selected</c:if>>${overCount}</option>
					  </c:forEach>
				    </select>
                   </td>
                     <td><label class="lab">畅捷是否签约：</label><select class="select180" name="cjSign">
	                 <option value="">全部</option>
	                 <option value="0" <c:if test="${paramMap.cjSign=='0'}">selected</c:if>>否</option>
					 <option value="1" <c:if test="${paramMap.cjSign=='1'}">selected</c:if>>是</option>
                     </select>
                     </td>
                     
            </tr>
               
            <tr>
                <td><label class="lab">逾期天数：</label>
                      <select class="select180" name="lateMark">
                          <option value="">全部</option>
					      <option <c:if test="${paramMap.lateMark=='0'}">selected</c:if> value="0" > &lt;X </option>
					      <option <c:if test="${paramMap.lateMark=='1'}">selected</c:if> value="1" > &gt;=X </option>
                      </select>
                        <input type ="number"  name ="overdueDays"  min="0"  value ="${paramMap.overdueDays}">
                </td>
                
                <td><label class="lab">接口：</label>
                      <select class="select180" name="deducttype">
                          <option value="">全部</option>
	                       <c:forEach var="deductType" items="${fns:getDictList('com_deduct_type')}">
								<option value="${deductType.value }"
								<c:if test="${paramMap.deducttype==deductType.value }">selected</c:if>>${deductType.label }</option>
					    </c:forEach>
                      </select>
                </td>
                
                 <td><label class="lab">状态：</label>
                      <select class="select180" name="status">
                           <option value="">全部</option>
	                       <option value="0" <c:if test="${paramMap.status=='0'}">selected</c:if>>生效</option>
					       <option value="1" <c:if test="${paramMap.status=='1'}">selected</c:if>>失效</option>
                      </select>
                </td>
            </tr>
            <tr>
               <td><label class="lab">划扣金额：</label>
                <select class="select180" name="amountMark">
                          <option value="">全部</option>
					      <option <c:if test="${paramMap.amountMark=='0'}">selected</c:if> value="0" > &lt;X </option>
					      <option <c:if test="${paramMap.amountMark=='1'}">selected</c:if> value="1" > &gt;=X </option>
                </select>
               <input type='text' onkeyup="checkNum(this)"  name ="applyReallyAmount"  value ="${paramMap.applyReallyAmount}">
              </td>
               <td><label class="lab">规则：</label>
                      <select class="select180" name="ruleCode">
                          <option value="">全部</option>
	                       <c:forEach var="rule" items="${mapRule}">
								<option value="${rule.key}"
								<c:if test="${paramMap.ruleCode==rule.key }">selected</c:if>>${rule.value }</option>
					    </c:forEach>
                      </select>
                </td>
            </tr>
            
    </table>
        <div class="tright pr30 pb5"><button class="btn btn-primary" onclick="document.getElementById('auditForm').submit();">搜索</button>
        <button class="btn btn-primary" id="cleBtn" > 清除</button></div>
    </div>
   </form>
  <p class="mb5">
   <button class="btn btn-small" id="newAppoint">新增预约</button>
   <button class="btn btn-small" id="deleteAppoint">删除</button>
   <button class="btn btn-small" id="editAppoint">修改</button>
   <button class="btn btn-small" onclick = "changEffect('0')">生效</button>
   <button class="btn btn-small" onclick = "changEffect('1')">失效</button>
   <button id="addDue" role="button" class="btn btn-small" data-target="#addDueDiv" data-toggle="modal">还款日预约规则设置</button>
   </p>
   <div class="box5"> 
        <table  class="table  table-bordered table-condensed" >
        <thead>
            <tr>
                <th><input type="checkbox" class="checkbox" id="checkAll"/></th>
                <th>签约平台</th>
                <th>银行</th>
                <th>预约时间</th>
                <th>划扣平台</th>
                <th>通联是否批量签约</th>
                <th>卡联是否签约</th>
                <th>畅捷是否签约</th>
                <th>借款状态</th>
                <th>累计逾期期数</th>
                <th>逾期天数</th>
                <th>划扣金额</th>
                <th>接口</th>
                <th>状态</th>
                <th>规则</th>
				<th>操作</th>
            </tr>
         </thead>   
        <c:if test="${page.list!=null && fn:length(page.list)>0}">
         <c:forEach items="${page.list }" var="bean" varStatus="status">
            <tr>
                  <td><input type="checkbox" name="checkBox" value="${bean.id }"  status ="${bean.status}"/>
                  </td>
                  <td>
                    ${bean.signPlatLabel}
                  </td>
                  <td title="${bean.bankLabel}" style="cursor:pointer">
                  <c:choose>
                   <c:when test="${fn:length(bean.bankLabel)>20}">   
				     ${fn:substring(bean.bankLabel, 0, 20)}
				  </c:when> 
				  <c:otherwise>
				  ${bean.bankLabel}
				  </c:otherwise>
                  </c:choose>
                  </td>
                  <td>
                  ${bean.appointmentDayLabel}
                  </td>
                  <td>
                      ${bean.deductPlatLabel}
                  </td>
                  <td>
                  <c:if test="${empty  bean.tlSign}">
                                                 全部
                  </c:if>
                   <c:if test="${bean.tlSign=='1'}">
                                                  是
                   </c:if>
                   <c:if test="${bean.tlSign=='0'}">
                                                 否
                   </c:if>
                  </td>
                  <td>
                   <c:if test="${empty  bean.klSign}">
                                                 全部
                  </c:if>
                   <c:if test="${bean.klSign=='1'}">
                                                  是
                   </c:if>
                   <c:if test="${bean.klSign=='0'}">
                                                 否
                   </c:if>
                 </td>
                 <td>
                  <c:if test="${empty  bean.cjSign}">
                                                 全部
                  </c:if>
                   <c:if test="${bean.cjSign=='1'}">
                                                  是
                   </c:if>
                   <c:if test="${bean.cjSign=='0'}">
                                                 否
                   </c:if>
                  </td>
                  <td>
                  
                      ${bean.loanStatusLabel}
                  </td>
                  <td>
                   <c:if test="${empty  bean.overCount}">
                                                          全部
                  </c:if>
                    ${bean.overCount}
                 </td>
                 <td>
                  <c:if test="${empty bean.overdueDays}">
                                                          全部
                  </c:if>
                   <c:if test="${bean.lateMark == '0'}">
                    &lt;
                   </c:if>
                    <c:if test="${bean.lateMark == '1'}">
                    &gt;=
                   </c:if>
                       ${bean.overdueDays}
                 </td>
                  <td>
                  <c:if test="${empty bean.applyReallyAmount}">
                                                     全部
                  </c:if>
                   <c:if test="${bean.amountMark == '0'}">
                    &lt;
                   </c:if>
                    <c:if test="${bean.amountMark == '1'}">
                    &gt;=
                   </c:if>
                      ${bean.applyReallyAmount}
                 </td>
                 <td>
                 <c:if test="${bean.deducttype=='0'}">
	                                             实时                         
	             </c:if>
	             <c:if test="${bean.deducttype=='1'}">
	                                            批量                 
                  </c:if>
                  </td>
                  <td>
                  <c:if test="${bean.status=='0'}">
                                                生效                         
                  </c:if>
                  <c:if test="${bean.status=='1'}">
                                                 失效                
                  </c:if>
                 </td>
                  <td>
                  <c:forEach var="rulefield" items="${mapRule}">
						<c:if test="${bean.ruleCode==rulefield.key }">${rulefield.value }</c:if>
			      </c:forEach>
                 </td>
                 <td>
                  <input type="button" class="btn_edit" onclick="showPaybackHis('','${bean.id}','');" value="历史">
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
</div>


<!-- 新增预约弹框 -->
<div class="modal fade"  id="addDueDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
             <h3 class="pop_title">新增预约</h3>
          </div>
        <div class="box1 mb10">
            <table id="backTB" class="table4" cellpadding="0" cellspacing="0" border="0" width="100%"> 
                 <tr>
                    <td><label class="lab">规则：</label>
                      <select class="select180" name="ruleCode" id = "ruleCodeId">
                          <option value="">全部</option>
	                       <c:forEach var="rule" items="${mapRule}">
								<option value="${rule.key}"
								<c:if test="${paramMap.ruleCode==rule.key }">selected</c:if>>${rule.value }</option>
					    </c:forEach>
                      </select>
                   </td>
                </tr>
                  <tr>
                    <td><label class="lab">选择预约日期：</label>
                        <label><input id="appointmentDate" type="text" readonly="readonly" maxlength="80" class="input_text178 Wdate"
							 onclick="WdatePicker({minDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd',isShowClear:true});" style="cursor: pointer"/>
						</label>
						  
                   </td>
                </tr>
            </table>
       </div>
     </div> 
       <div class="modal-footer">
	       <button class="btn btn-primary" onclick="appointmentRule();">确认</button>
	       <a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
       </div>
       </div>
    </div>
</body>
</html>