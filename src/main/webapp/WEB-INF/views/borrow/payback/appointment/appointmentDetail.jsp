<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title><c:if test="${ bean.id == '' || bean.id == null }">新增预约</c:if>
        <c:if test="${ bean.id != '' && bean.id != null }">修改预约</c:if>
</title>

<script type="text/javascript">
var oldobj = new Object();
$(function(){
	$("#resets").click(function(){
		var url = ctx+"/borrow/payback/appointment/queryList";
		window.location.href = url;
	});
     oldobj = strToObj($("#appoinForm").serialize());
     
     /*  $("#overdueDaysId").blur(function(){
	 if($(this).val()){
		 if(!$("#lateMarkId").val()){
			 $("#overdueDaysId").val('');
			  art.dialog.alert("【全部】选项请勿输入!")
			  return false;
		 }
	 }else{
	 }
 })
  */
})

 function strToObj(str){
	str = str.replace(/&/g, "','" );
	str = str.replace(/=/g, "':'" );
	str = "({'" +str + "'})" ;
	obj = eval(str);
	return obj;
	} 
// 比较修改前和修改后的数据是不是已经改了
function compareData(oldData,newData){
	var flag = false;
	for(var o in oldobj){  
        if(oldobj[o] != newData[o]){
        	flag = true 
        	break;
        }
    }  
	return flag;
}

function formSubmit(){
  var platId = $("#platId").val(); // 签约平台
  var  bankId = $("#bankId").val(); // 银行
  var  appointmentDay = $("#appointmentDay").val(); // 预约时间
  var  deductPlat  = $("#deductPlat").val(); //划扣平台
  var  loanStatusId = $("#loanStatusId").val();//借款状态
  
  if(!platId){
	  art.dialog.alert("签约平台不能为空!")
	  return false;
	  
  }
  if(!bankId){
	  art.dialog.alert("银行不能为空!")
	  return false;
	  
  }
  
  if(!appointmentDay){
	  art.dialog.alert("预约时间不能为空!")
	  return false;
	  
  }
  
  if(!deductPlat){
	  art.dialog.alert("划扣平台不能为空!")
	  return false;
	  
  }
  
  if(!loanStatusId){
	  art.dialog.alert("借款状态不能为空!")
	  return false;
	  
  }
	
	
	
	var url = ctx+"/borrow/payback/appointment/insertOrUpdate";
	var flag = $("#beanId").val();
	var msg = "";
	if(flag){
		msg ="修改成功！"
	    var newDate = strToObj($('#appoinForm').serialize())
		var  chang = compareData(oldobj,newDate)
		if(!chang){
			art.dialog.alert("没有数据改变,请修改数据再提交!")
		    return false;
		}
	}else{
		msg ="新增成功！"
	}
	$.ajax({
        type: "POST",
        url: url,
        data:$('#appoinForm').serialize(),// 要提交的表单
        success: function(data){
        	 if(data.success == true){
        			art.dialog.alert(msg,function(){
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
    <form id="appoinForm" method="post">
    <input  id ='beanId' type="hidden" class="input_text178" name="id"  value='${bean.id}'>         
    <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			 <tr>
		 	   <td><label class="lab">签约平台：</label>
                <sys:multireSignPlatform platClick="platClick"  platId="platId" platName ='platName'></sys:multireSignPlatform>
                <input id="platName" type="text" class="input_text178" name="signPlatLabel"  value='${bean.signPlatLabel}'>&nbsp;
                <i id="platClick" class="icon-search" style="cursor: pointer;"></i> 
				<input type="hidden" id="platId" name='signPlat' value='${bean.signPlat}'>
				<span style="color: red">*</span>
                </td>
               <td><label class="lab" >银行：</label>
                <sys:multipleBank bankClick="selectBankBtn" bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
                <input id="search_applyBankName" type="text" class="input_text178" name="bankLabel"  value='${bean.bankLabel}'>&nbsp;<i id="selectBankBtn"
						class="icon-search" style="cursor: pointer;"></i> 
						<input type="hidden" id="bankId" name='bank' value='${bean.bank}'>
				<span style="color: red">*</span>
                </td>
                <td><label class="lab">预约时间：</label>
				  <input name="appointmentDay" id="appointmentDay"  type="text" readonly="readonly"  maxlength="40" class="input-medium Wdate"
				    value="<fmt:formatDate value="${bean.appointmentDay}" pattern="yyyy-MM-dd HH:mm"/>"
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m'});" style="cursor: pointer"/>
			      <span style="color: red">*</span>
			    </td>
			  
            </tr>
 
  <tr>
               	<td><label class="lab">划扣平台：</label> 
                   <select class="select180" name="deductPlat" id="deductPlat">
								 <option value="">请选择</option>
								 <c:forEach var="plat"
									items="${fns:getDictList('jk_deduct_plat')}">
									<c:if test="${plat.value!='4' }">
									     <option value="${plat.value }"
										<c:if test="${bean.deductPlat==plat.value }">selected</c:if>>${plat.label }</option>
										</c:if>
								    </c:forEach>
					</select>
					  <span style="color: red">*</span>
					</td>
               
                <td><label class="lab">通联是否批量签约：</label> 
                  <select class="select180" name="tlSign">
                        <option value="">全部</option>
	                    <c:forEach var="plat" items="${fns:getDictList('yes_no')}">
								<option value="${plat.value }"
								<c:if test="${bean.tlSign==plat.value }">selected</c:if>>${plat.label }</option>
					    </c:forEach>
				    </select>
				</td>
				
				 <td><label class="lab">卡联是否签约：</label><select class="select180" name="klSign">
	                 <option value="">全部</option>
	                 <option value="0" <c:if test="${bean.klSign=='0'}">selected</c:if>>否</option>
					 <option value="1" <c:if test="${bean.klSign=='1'}">selected</c:if>>是</option>
                  </select>
                </td>
            </tr>
            
            <tr>
                <td><label class="lab">畅捷是否签约：</label><select class="select180" name="cjSign">
                    <option value="">全部</option>
                    <option value="0" <c:if test="${bean.cjSign=='0'}">selected</c:if>>否</option>
				    <option value="1" <c:if test="${bean.cjSign=='1'}">selected</c:if>>是</option>
                    </select>
                </td>
            
                <td><label class="lab">借款状态：</label>
	            <sys:multireLoanStatus loanStatusClick="loanStatusClick" loanStatusName="loanStatusName" loanStatusId="loanStatusId"></sys:multireLoanStatus>
                <input id="loanStatusName" type="text" class="input_text178" name="loanStatusLabel"  value='${bean.loanStatusLabel}'>&nbsp;
                <i id="loanStatusClick" class="icon-search" style="cursor: pointer;"></i> 
				<input type="hidden" id="loanStatusId" name='loanStatus' value='${bean.loanStatus}'>
				<span style="color: red">*</span>
                </td>
                 <td><label class="lab">累计逾期期数：</label>
				  <sys:multireOverData overDateClick="overDateClick" overDateId="overCountId"></sys:multireOverData>
				  <input id="overCountId"  name="overCount" class="input_text178" readonly ="readonly"   value="${bean.overCount}">
				  <i id="overDateClick" class="icon-search" style="cursor: pointer;"></i> 
                  </td>
            </tr>
               
            <tr>
               <td><label class="lab">逾期天数：</label>
                      <select class="select180" name="lateMark">
                          <option value="">全部</option>
					      <option <c:if test="${bean.lateMark=='0'}">selected</c:if> value="0" > &lt;X </option>
					      <option <c:if test="${bean.lateMark=='1'}">selected</c:if> value="1" > &gt;=X </option>
                      </select>
                        <input type ="number"  name ="overdueDays"  min="1" max="1000" value ="${bean.overdueDays}">
                </td>
                
                <td><label class="lab">接口：</label>
                      <select class="select180" name="deducttype">
	                       <c:forEach var="deductTypes" items="${fns:getDictList('com_deduct_type')}">
								<option value="${deductTypes.value }"
								<c:if test="${bean.deducttype==deductTypes.value }">selected</c:if>>${deductTypes.label }</option>
					    </c:forEach>
                      </select>
                 </td>
                 
                 <td><label class="lab">划扣金额：</label>
                      <select class="select180" name="amountMark">
                          <option value="">全部</option>
					      <option <c:if test="${bean.amountMark=='0'}">selected</c:if> value="0" > &lt;X </option>
					      <option <c:if test="${bean.amountMark=='1'}">selected</c:if> value="1" > &gt;=X </option>
                      </select>
                        <input type ="number"  name ="applyReallyAmount"  min="0" value ="${bean.applyReallyAmount}">
                </td>
            </tr>
          
      </table>
     </div>
     </form>
     
     <div class="tright pr30 pb5"><button class="btn btn-primary" onclick="formSubmit()">
      <c:if test="${ bean.id == '' || bean.id == null }">确认</c:if>
      <c:if test="${ bean.id != '' && bean.id != null }">修改</c:if>
     </button>
     <button class="btn btn-primary" id="resets">取消</button></div>
</body>
</html>