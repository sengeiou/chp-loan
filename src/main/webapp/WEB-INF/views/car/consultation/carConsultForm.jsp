<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借客户咨询</title>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript"
	src="${context}/js/consult/consultValidate.js"></script>
<meta name="decorator" content="default" />

<script type="text/javascript">
//真实姓名验证
jQuery.validator.addMethod("realName2", function(value, element) {
    return this.optional(element) || /^[\u4e00-\u9fa5·]{2,30}$/.test(value);
}, "姓名只能为2-10个汉字或·号");
jQuery.validator.addMethod(
		"moreZero",function(value,element,params){
			if(value > 0){
				return true;
			}else{
				return false;
			}
		},"输入的数字要大于零");
	$(document).ready(
			function() {
				$("#idStartDay").live("blur", function() {
					var idStartDay = $(this).val();
					if (idStartDay != null && idStartDay != '') {
						$("#idEndDay").addClass("required");
					//	$("#longTerm").addClass("required");
					} else {
						$("#idEndDay").removeClass("required");
					//	$("#longTerm").removeClass("required");
					}
				});
				loan.initCity("liveProvinceId", "liveCityId",
						"liveDistrictId");
				loan.initCity("registerProvince",
						"customerRegisterCity", "customerRegisterArea");
				areaselect.initCity("liveProvinceId", "liveCityId",
						"liveDistrictId", $("#liveCityIdHidden").val(), $("#liveDistrictIdHidden")
								.val());
				areaselect.initCity("registerProvince",
						"customerRegisterCity", "customerRegisterArea",
						$("#customerRegisterCityHidden").val(), $(
								"#customerRegisterAreaHidden").val());
						
						$('#longTerm').bind('click', function() {
							if ($(this).attr('checked') == true
									|| $(this).attr('checked') == 'checked') {
								$('#idEndDay').val('');
								$('#idEndDay').attr('disabled', true);
								$("#idStartDay").addClass("required");
							} else {
								$('#idEndDay').attr('disabled', false);
								$('#idStartDay').removeClass('required');
							}
						});
				//保存咨询数据 
				   $("#contactNextBtn").click(function(){
					   //loading("正在提交数据，请稍等...");
/* 					if(typeof($('#idEndDay').attr('disabled')) == 'disabled'){
						$("#idStartDay").addClass("required");
					}
					else{
						$("#idStartDay").removeClass("required");
					} */
					 //验证JS
						var Tflag =$("#carCustomer").validate({
									rules:{
										/*dictCertType:{
											isCertificate:true,
										},*/
										customerAddress:{
											maxlength:100
										},
										vehicleBrandModel:{
											maxlength:30
										},
										customerCertNum:{
											card:true,
										},
										customerMobilePhone:{
											isMobile : true,
										},
										consLoanRemarks:{
											maxlength:200
										},
										consLoanAmount:{
											maxlength:12,
											number:true,
											moreZero:true
										}
								},
									messages : {
										vehicleBrandModel:{
											maxlength:"长度不能大于30"
										},
										consLoanRemarks:{
											maxlength:"200字以内"
										},
										customerAddress:{
											maxlength:"100字以内"
										},
										consLoanAmount:{
											number:"请输入合法的数字",
											maxlength:"12字以内"
										}
									}
						}).form();
					if(!Tflag){
						return;
					}
					 //不符合进件条件验证
					   $.ajax({
				    		url:ctx+"/car/carConsult/checkInto",
				    		type:'post',
				    		data: {'customerName':$("#customerName").val(),
				    				'customerMobilePhone':$("#customerMobilePhone").val(),
				    				'plateNumbers':$("#plateNumbers").val(),
				    				'dictOperStatus':$("#dictOperStatus").val()},
				    		success:function(data){
				    			if(data == "客户姓名" || data == "客户手机号码"){
				    				art.dialog.confirm("根据"+data+"查询得知该客户不符合进件条件,是否继续咨询?",function(){
				    					$("#contactNextBtn").attr("disabled","disabled"); 
				    					submitValidate(Tflag);
				    				});
				    			}else if(data == "true"){
									 	$.ajax({
										   type: "POST",
										   url: ctx+"/car/appraiserEntry/vehicleCeiling",
										   data: {'customerCertNum':$("#customerCertNum").val(),'hasFirst':'1'},
										   async: false,
										   success: function(data){
											   if(data != "success"){
												   $.jBox.error(data, '提示信息');
											   }else{
												   submitValidate(Tflag);
											   }
										   }
										});
				    			}else if(data == "7"){
				    				$.jBox.error('初审拒绝7天内不允许再次提交','提示' ); 
				    			}else if(data == "90"){
				    				$.jBox.error('复审、终审拒绝90天内不允许再次提交','提示' ); 
				    			}else if(data == "重复"){
				    				$.jBox.error('该车牌号已被录入，未满7天不能重复提交','提示' );
				    			}else if(data == "姓名" || data == "手机号码"){
				    				art.dialog.alert("根据客户"+data+"查询得知该客户是门店拒绝的客户");
				    			}
				    		}
				    	});
					  
				    });
				   $("#customerCertNum").blur(function(){
						 if($(this).val()!=""){
						 	$.ajax({
							   type: "POST",
							   url: ctx+"/car/appraiserEntry/vehicleCeiling",
							   data: {'customerCertNum':$(this).val(),'hasFirst':'1'},
							   async: false,
							   success: function(data){
								   if(data != "success"){
									   $.jBox.error(data, '提示信息');
								   }
							   }
							});
						 }
					 });

	});
//提交验证
function submitValidate(data){
	// 身份证性别验证
	var identityCode = $("#customerCertNum").val();
	var num = "";
	if(identityCode.length==18){
	  num = identityCode.charAt(identityCode.length-2);
	}
	if(identityCode.length==15){
	  num = identityCode.charAt(identityCode.length-1);
	}
	var remainder = parseInt(num)%2;
	var dictSex = $("#carCustomer").find("input:radio[name='customerSex']:checked").val();
	if(remainder==dictSex){
		art.dialog.alert("性别与身份证不符，请重新填写");
		return ;
	}
	if (data) {
		   $.ajax({
	    		url:ctx+"/car/carConsult/saveCustomerConsult",
	    		type:'post',
	    		data: $("#carCustomer").serialize(),
	    		success:function(data){
	    			var v = $('#consTelesalesFlag').val();
	    			if(data == "false"){
	    				if(v=='0'){
	    					art.dialog.alert("咨询录入信息成功!",function(){
	    						window.location = ctx+"/car/carConsult/toAddCarConsultation";
	    					});
	    				}else{
	    					art.dialog.alert("电销录入信息成功!",function(){
	    						window.location = ctx+"/car/carConsult/toAddCarTelesalesConsultation";
	    					});
	    				}
	    			}
	    		},
	    		error:function(){
	    			art.dialog.alert('服务器异常！');
	    			$("#contactNextBtn").removeAttr("disabled");
	    			return false;
	    		}
	    	});
	   }
}

</script>

</head>
<body>
			
 <form id="carCustomer">
 <h2 class=" f14 pl10 ">基本信息</h2>
  <div class="content box2">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
  	<tr>
		<td style="width:32%">
			<label class="lab"><span style="color: #ff0000">*</span>客户姓名：</label>
		  		<input id="customerName" name="customerName" class="input_text178 required realName2" maxlength="10" type="text" value=""/>
		  		<input id="customerCode" name="customerCode" type="hidden" value=""/>
		  		<input type="hidden" id="flag" />
		  		<input type="hidden" id="message"/>
		</td>
		
    	<td><label class="lab"><span style="color: #ff0000"></span>性别：</label>
						<c:choose>
							<c:when test="${customerSex!=null}">
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" name="customerSex" value="${item.value}"
										<c:if test="${customerSex==item.value}">
                                  checked
                                </c:if>>
                           ${item.label}
                         </input>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" class=""
										name="customerSex" value="${item.value}"
										<c:if test="${customerSex==item.value}">
                                  checked
                                </c:if>>
                             ${item.label}
                           </input>
								</c:forEach>
							</c:otherwise>
						</c:choose></td>
		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>手机号码：</label>
   				<input id="customerMobilePhone" name="customerMobilePhone" class="required isMobile input_text178" type="text" value=""  maxlength="11"/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
		   	</tr>
		<tr>
		<td style="width:32%">
  			<label class="lab"><span style="color: #ff0000"></span>证件类型：</label>
				身份证<input id="cardType" name="dictCertType" value="0" type="hidden"/>
		</td>
		<td style="width:32%" id="idCard">
   			<label class="lab"><span style="color: #ff0000"></span>证件号码：</label>
   			<input id="customerCertNum" name="customerCertNum" class="input_text178 card" type="text" value="" />
   			<span id="blackTip" style="color: red;"></span>
		</td>
		<td style="width:32%">
    		 <label class="lab"><span style="color: #ff0000"></span>证件有效期：</label>
		    <input id="idStartDay" name="idStartDay" type="text" class="Wdate input_text70" size="10"  
		      onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})" style="cursor: pointer" />
		      -<input id="idEndDay" name="idEndDay" type="text" class="Wdate input_text70" size="10"
			  onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})" style="cursor: pointer" />
			   <input type="checkbox" path="longTerm" name="isLongTerm" id="longTerm" value="1" />长期
     	</td>
     	
 	</tr>
 	
 	<tr>
   		<td>
    		<label class="lab"><span style="color: #ff0000"></span>学历：</label>
  				<select id="dictEducation" name="dictEducation"
						value="${dictEducation}" class="select2-container select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
								<option value="${curEdu.value}"
									<c:if test="${dictEducation==curEdu.value}">
					      selected = true
					     </c:if>>${curEdu.label}</option>
							</c:forEach>
					</select>
		</td>
		<td>
    		<label class="lab"><span style="color: #ff0000"></span>婚姻状态：</label>
  				<select id="dictMarry" name="dictMarryStatus" value="${dictMarryStatus}" class="select180">
                   <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_marriage')}" var="aitem">
					  <option value="${aitem.value}"
					   <c:if test="${dictMarryStatus==aitem.value}"> 
					    selected=true 
					   </c:if>
					  >${aitem.label}</option>
				     </c:forEach>
				</select>
		</td>
      </tr>
      <tr>
 		<td colspan="2">
 			<label class="lab"><span style="color: #ff0000"></span>现住址：</label>
  				
  				<select id="registerProvince" name="customerLiveProvince" class="select180">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${customerLiveArea==item.areaCode}">
							selected = true 
		                 </c:if>>
									${item.areaName}</option>
							</c:forEach>
					</select>-<select id="customerRegisterCity"
						name="customerLiveCity" class="select180"
						value="${liveCity}">
							<option value="">请选择</option>
					</select>-<select id="customerRegisterArea"
						name="customerLiveArea" class="select180" value="${liveArea}">
							<option value="">请选择</option>
					</select>
					<input type="text"
						name="customerAddress" class="input_text178"
						style="width: 250px" maxlength="40"
						value="${customerAddress}" />
   		</td>	
 	</tr>
 </table>
</div>
 <h2 class=" f14 pl10 mt20">车借信息</h2>
  <div class="content box2">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
   		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>车辆品牌与型号：</label>
   				<input id="vehicleBrandModel" name="vehicleBrandModel" class="required input_text178" type="text" value="" maxlength="30"/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
   		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>车牌号码：</label>
   				<input id="plateNumbers" name="plateNumbers" class="required input_text178 plateNum" maxlength="8" type="text" value=""/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
	<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>预计借款金额：</label>
   				<input id="consLoanAmount" name="consLoanAmount" class=" required input_text178" type="text" maxlength="8" value="" onblur="checkLoanAmoint()"/>
		</td>
		</tr>
		<tr>
		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>预计到店时间：</label>
   				 <input id="planArrivalTime" name="planArrivalTime" type="text" class="input_text178 required Wdate" size="10"  
		      onClick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-%d'})" style="cursor: pointer" />
   				<span id="blackTip" style="color: red;"></span>
		</td>
  		<td style="width:32%">
	    <label class="lab"><span style="color: #ff0000"></span>团队经理：</label>
	    <input name="consTeamEmpName" disabled="true" class="input_text178" value="${consult.consTeamEmpName}"/>
	    <input name="consTeamManagerCode" type="hidden" value="${consult.consTeamManagerCode}">
	    <input name="consTelesalesFlag" type="hidden" value="${consult.consTelesalesFlag}" id="consTelesalesFlag">
 	    </td>
  		<td >
     		<label class="lab"><span style="color: #ff0000">*</span>客户经理：</label>
  				<select name="managerCode" class="select180 required select2Req">
		    <option value="">请选择</option>
		    <c:forEach items="${customerManagers}" var="item">
		      <option value="${item.id}">${item.name}</option>
		  </c:forEach>
		</select>
   		</td>
	</tr>
 </table>
</div>
<h2 class=" f14 pl10 mt20">沟通记录及下一步操作</h2>
 <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
  		<td style="width:32%">
    		<label class="lab"><span style="color: #ff0000">*</span>下一步：</label>
    			<select id="dictOperStatus" name="dictOperStatus" path="dictOperStatus" class="required select180 select2Req">
		  <option value="">请选择</option>
		  <c:forEach items="${fns:getDictList('jk_next_step')}" var="item">
		     <c:if test="${item.label!='已进件'&&item.label!='待申请'&&item.label!='门店放弃'}">
		      <option value="${item.value}">${item.label}</option>
		     </c:if>
		  </c:forEach>
		</select>
		</td>
  		<td style="width:32%">
    		<label class="lab">备注：</label>
    			<textarea cols="20" rows="2" id="consLoanRemarks" name="consLoanRemarks"  maxlength="200"></textarea>
  		</td>
  		<td style="width:32%"></td>
   	</tr>
</table>
</div>
<div class="tright pt10 pr30"><input type="button" class="btn btn-primary" id="contactNextBtn"
					value="提交"></input>
					<input onclick="history.go();" type="button" class="btn btn-primary" value="取消" /></input>
					</div>
</form> 
</body>
</html>