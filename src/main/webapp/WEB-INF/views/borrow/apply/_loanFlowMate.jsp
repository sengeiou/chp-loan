<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#mateNextBtn').bind('click',function(){
		$(this).attr('disabled',true);
		launch.saveApplyInfo('2','mateForm','mateNextBtn');
	});
     loan.initCity('compProvince', 'compCity','compArer');
     areaselect.initCity("compProvince", "compCity",
		 "compArer", $("#compCityHidden").val(),$("#compArerHidden").val());
     if($('#mateDelBtn').length!=0){
    	 $('#mateDelBtn').click(function(){
    		 art.dialog.confirm("是否确认删除配偶信息",
 					function(){
    			 $.ajax({
    					type:'post',
    					url:ctx+"/apply/dataEntry/delAdditionItem",
    					data:{
    						'delType':'MATE',
    						'tagId':$('#mateId').val()
    					},
    					dataType:'json',
    					success:function(data){
    						if(data){
    							art.dialog.alert("删除成功！");
    							launch.getCustomerInfo('3');
    						}else{
    							art.dialog.alert("删除失败！");
    						}
    					},
    					error:function(){
    						art.dialog.alert("服务器异常！");
    					}
     				});
 					},function(){
 						return true;
 					}
 			    );
    	 });
     }
});
var msg = "${message}";
if (msg != "" && msg != null) {
	top.$.jBox.tip(msg);
};
</script>
<body>
     <ul class="nav nav-tabs">
	  <li><a href="javascript:launch.getCustomerInfo('1');">个人资料</a></li>
	  <li class="active"><a href="javascript:launch.getCustomerInfo('2');">配偶资料</a></li>
	  <li><a href="javascript:launch.getCustomerInfo('3');">申请信息</a></li>

		<c:if test="${workItem.bv.oneedition==-1}"> 
			<li><a href="javascript:launch.getCustomerInfo('4');">共同借款人</a></li>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
			<li><a href="javascript:launch.getCustomerInfo('4');">自然人保证人</a></li>	
        </c:if>
      
	  <li><a href="javascript:launch.getCustomerInfo('5');">信用资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('6');">职业信息/公司资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('7');">房产资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('8');">联系人资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('9');">银行卡资料</a></li>
	</ul>

	<form id="custInfoForm" method="post">
	   <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
	   <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
	   <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanInfo.loanCode" id="loanCode" />
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanCode"/>
	  <input type="hidden" value="${workItem.bv.loanCustomer.id}" 
	            name="loanCustomer.id" id="custId" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode"/> 
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName" />
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerName}"
				name="loanCustomer.customerName" />
	  <input type="hidden" value="${workItem.bv.consultId}"  name="consultId" id="consultId"/>
	  <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	  <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	  <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	</form>
	<div id="div2" class="control-group" >
	 <form id="mateForm" method="post">
	     <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
		 <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
	     <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanInfo.loanCode" id="loanCode" />
	  	 <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanCode"/>
	  	 <input type="hidden" value="${workItem.bv.loanCustomer.id}" 
	            name="loanCustomer.id" id="custId" /> 
	  	 <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> 
	  	 <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode"/> 
	  	 <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName" />
	  	 <input type="hidden" value="${workItem.bv.loanCustomer.customerName}"
				name="loanCustomer.customerName" />
	  	 <input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId"/>
	  	 <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  	 <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	 	 <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	  	 <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	     <input type="hidden" value="${applyInfoEx.loanMate.id}" name="loanMate.id" id="mateId"/>
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
           <tbody id="loanMateTbody"> 
            <tr>
              <td  >
                 <label class="lab"><span class="red">*</span>姓名：</label>
                 <input name="loanMate.mateName" value="${applyInfoEx.loanMate.mateName}" type="text" maxlength="50" class="input_text178 required stringCheck"/>
              </td>
              <td>
              <label class="lab"><span class="red">*</span>证件类型：</label>
                 <select id="mateCardType" name="loanMate.dictCertType" value="${applyInfoEx.loanMate.dictCertType}" class="select180 required">
                     <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('com_certificate_type')}" var="item">
                      
					  <option value="${item.value}"
					   <c:if test="${applyInfoEx.loanMate.dictCertType==item.value}"> 
				            selected=true 	   
					   </c:if>
					  >${item.label}</option>
				     </c:forEach>
				</select>
              </td>
              <td>
                 <label class="lab" ><span class="red">*</span>证件号码：</label>
                 <input type="text" value="${applyInfoEx.loanMate.mateCertNum}" name="loanMate.mateCertNum" class="input_text178 required mateNumCheck"/>
              </td>
            </tr>
            <tr>
              <td>
                  <label class="lab"><span class="red">*</span>手机：</label>
                  <input type="text" name="loanMate.mateTel" value="${applyInfoEx.loanMate.mateTel}" class="input_text178 required mateMobileDiff">
              </td>
              <td>
                 <label class="lab">单位名称：</label>
                 <input name="loanMate.mateLoanCompany.compName" value="${applyInfoEx.loanMate.mateLoanCompany.compName}" type="text" class="input_text178 compNameCheck"/>
                 <input type="hidden" id="mateLoanCompId" name="loanMate.mateLoanCompany.id" value="${applyInfoEx.loanMate.mateLoanCompany.id}"/>
               </td>
               <td>
                 <label class="lab">单位电话：</label>
                 <input name="loanMate.mateLoanCompany.compTel" value="${applyInfoEx.loanMate.mateLoanCompany.compTel}" type="text" class="input_text178 isTel"/>
               </td>
            </tr>
            <tr>
              <td colspan="3"><label class="lab" >单位地址：</label>
                <select class="select180" id="compProvince" name="loanMate.mateLoanCompany.compProvince" value="${applyInfoEx.loanMate.mateLoanCompany.compProvince}">
                   <option value="">请选择</option>
                   <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <option value="${item.areaCode}" 
		              <c:if test="${applyInfoEx.loanMate.mateLoanCompany.compProvince==item.areaCode}"> 
				            selected=true 	   
					   </c:if>
		             >${item.areaName}</option>
	               </c:forEach>
                </select>
                <select class="select180" id="compCity" name="loanMate.mateLoanCompany.compCity" value="${applyInfoEx.loanMate.mateLoanCompany.compCity}">
                   <option value="">请选择</option>
                </select>
                <select class="select180" id="compArer" name="loanMate.mateLoanCompany.compArer" value="${applyInfoEx.loanMate.mateLoanCompany.compArer}">
                   <option value="">请选择</option>
                </select>
                 <input type="hidden" id="compCityHidden" value="${applyInfoEx.loanMate.mateLoanCompany.compCity}"/>
                 <input type="hidden" id="compArerHidden" value="${applyInfoEx.loanMate.mateLoanCompany.compArer}"/>
                 <input name="loanMate.mateLoanCompany.compAddress" value="${applyInfoEx.loanMate.mateLoanCompany.compAddress}" type="text" class="input_text178" style="width:250px"/> 
              </td>
            </tr>
          </tbody>
        </table>
        <div class="tright mr10 mb5">
           <input type="submit" class="btn btn-primary" id="mateNextBtn" value="下一步"
             <c:if test="${applyInfoEx.isMarried=='0'}">
                 disabled=true
             </c:if>
           />
           <c:if test="${applyInfoEx.isMarried=='0' && applyInfoEx.loanMate.id!=null && applyInfoEx.loanMate.id!=''}">
              <input type="button" class="btn btn-primary" id="mateDelBtn" value="删除"/>
           </c:if>
        </div>
       </form>
    </div>
    </body>
</html>