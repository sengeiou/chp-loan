<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借OCR办理</title>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${context }/js/car/operateRecord.js" ></script>
<script type="text/javascript"
	src="${context}/js/consult/consultValidate.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
//真实姓名验证
jQuery.validator.addMethod("realName2", function(value, element) {
    return this.optional(element) || /^[\u4e00-\u9fa5·]{2,30}$/.test(value);
}, "姓名只能为2-10个汉字或·号");
jQuery.validator.addMethod("moreZero",function(value,element,params){
if(value > 0){return true;}else{return false;}
},"输入的数字要大于零");
$(document).ready(function() {
	loan.initCity("liveProvinceId", "liveCityId","liveDistrictId");
	loan.initCity("registerProvince","customerRegisterCity", "customerRegisterArea");
	areaselect.initCity("liveProvinceId", "liveCityId","liveDistrictId", $("#liveCityIdHidden").val(), $("#liveDistrictIdHidden").val());
	areaselect.initCity("registerProvince","customerRegisterCity", "customerRegisterArea",
			$("#customerRegisterCityHidden").val(), $("#customerRegisterAreaHidden").val());
	//保存咨询数据 
	$("#contactNextBtn").click(function(){
		//验证JS
		var Tflag =$("#carCustomer").validate({
					rules:{
						customerAddress:{
							maxlength:100
						},
						vehicleBrandModel:{
							maxlength:19
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
							maxlength:"长度不能大于19"
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
	   $.ajax({
    		url:ctx+"/car/carOcr/savrCarOcrQue",
    		type:'post',
    		data: $("#carCustomer").serialize()+"&id="+$("#coustomerId").val(),
    		success:function(data){
    			if(data == "false"){
    					art.dialog.alert("确认成功!",function(){
    						window.location = ctx+"/car/carOcr/fetchTaskItems";
    				});
    			}
    		},
    		error:function(){
    			art.dialog.alert('服务器异常！');
    			$("#contactNextBtn").removeAttr("disabled");
    			return false;
    		}
    	});
	});
	
	
	//初始化loanCode、operateStep 值
	//$("input[name='loanCode']").val('${loanCustomer.loanCode}');
	$("input[name='loanCode']").val('${carCustomerBase.customerCode}'); //ocr待办没有提交时，没有loan_code，用customerCode删除
	$("input[name='operateStep']").val("OCR待办"); 
	
	
});

//调用删除docId的业务逻辑 
function deleteDocId(loanCode,btnId,data1,callback){
	var data = {"loanCode":loanCode,
				"btnId":btnId};
	$.ajax({
   		url:ctx+"/car/carOcr/delDocId",
   		type:'post',
   		data: data,
   		success:function(data2){
   			if(data2 == "true"){
   				callback(data1,data2,btnId);
   			}
   		},
   		error:function(){
   			return false;
   		}
   	});

}

</script>
</head>
<body>
 <form id="carCustomer">
 <h2 class=" f14 pl10 ">基本信息</h2>
  <div class="content box2">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
    <input type="hidden" id="coustomerId" value="${id}"/>
    <c:choose>
	   <c:when test="${carCustomerBase.customerNameOcr!=null && carCustomerBase.customerNameOcr!=''}">  
	        <tr>
		    	<td style="padding-left:130px;" colspan="2">
		    		<img src="${ctx}/carpaperless/confirminfo/getPreparePhoto?docId=${carCustomerBase.customerNameOcr}" width="110px" height="30px"/>
		    		<button  class="btn btn-small"  data-target="#del_div" data-toggle="modal" name="back" id="delNameOcr"/>删除图片</button>
		    	</td>
		    </tr>
	   </c:when>
	   <c:otherwise> 
	   </c:otherwise>
	</c:choose>

  	<tr>
		<td><label class="lab"><span style="color: red">*</span>客户姓名：</label>
						<input type="text" name="customerName"
						value="${loanCustomer.customerName}" class="input_text178 required"/></td>
		
    	<td><label class="lab"><span style="color: #ff0000"></span>性别：</label>
						<c:choose>
							<c:when test="${loanCustomer.customerSexCode!=null}">
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" name="customerSex" value="${item.value}"
										<c:if test="${loanCustomer.customerSexCode==item.value}">
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
										<c:if test="${loanCustomer.customerSexCode==item.value}">
                                  checked
                                </c:if>>
                             ${item.label}
                           </input>
								</c:forEach>
							</c:otherwise>
						</c:choose></td>
					<td><label class="lab"><span style="color: red">*</span>手机号码：</label>
						<input type="text" id="bankAccountName" name="customerMobilePhone"
						value="${carCustomerBase.customerMobilePhone}" class="required isMobile"/>
				    </td>
		</tr>
		
		<c:choose>
		   <c:when test="${carCustomerBase.customerCretOcr!=null && carCustomerBase.customerCretOcr!=''}">  
        	    <tr>
			    	<td style="padding-left:545px;" colspan="3">
			    		<img src="${ctx}/carpaperless/confirminfo/getPreparePhoto?docId=${carCustomerBase.customerCretOcr}" width="110px" height="30px"/>
			    		<button  class="btn btn-small"  data-target="#del_div" data-toggle="modal" name="back" id="delCretOcr"/>删除图片</button>
			    	</td>
			    </tr>  
		   </c:when>
		   <c:otherwise> 
		   </c:otherwise>
		</c:choose>   	

		<tr>
		<td style="width:32%">
  			<label class="lab"><span style="color: #ff0000"></span>证件类型：</label>
				身份证<input id="cardType" name="dictCertType" value="0" type="hidden"/>
		</td>
		<td><label class="lab"><span style="color: red">*</span>证件号码：</label>
						<c:choose>
							<c:when test="${loanCustomer.customerCertNum!=null}">
								<input name="customerCertNum"
									id="customerCertNum" type="text"
									value="${loanCustomer.customerCertNum}"
									class="input_text178" />
							</c:when>
							<c:otherwise>
								<input name="customerCertNum" id="customerCertNum"
									type="text" value="${loanCustomer.mateCertNum}"
									class="input_text178 required card" />
							</c:otherwise>
						</c:choose> <span id="blackTip" style="color: red;"></span>
		</td>
 	</tr>
      <tr>
 		<td colspan="2"><label class="lab"><span style="color: red">*</span>现住址：</label>
						<select class="select78 required" id="liveProvinceId"
						name="customerLiveProvince">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${loanCustomer.customerLiveProvince==item.areaCode}">
		                    selected = true 
		                 </c:if>>${item.areaName}</option>
							</c:forEach>
					</select>-<select class="select78 required" id="liveCityId"
						name="customerLiveCity"
						value="${loanCustomer.customerLiveCity}">
							<c:forEach var="item" items="${cityList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${loanCustomer.customerLiveCity==item.areaCode}">
		                    selected = true 
		                 </c:if>>${item.areaName}</option>
							</c:forEach>
					</select>-<select id="liveDistrictId" class="select78 required"
						name="customerLiveArea"
						value="${loanCustomer.customerLiveArea}">
							<c:forEach var="item" items="${districtList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${loanCustomer.customerLiveArea==item.areaCode}">
		                    selected = true 
		                 </c:if>>${item.areaName}</option>
							</c:forEach>
					</select>
				<input type="hidden" id="liveCityIdHidden" value="${loanCustomer.customerLiveCity}"/>
					<input type="hidden" id="liveDistrictIdHidden" value="${loanCustomer.customerLiveArea}"/> 
					
					<input type="text"
						name="customerAddress" maxlength="50"
						value="${loanCustomer.customerAddress}" style="width: 250px"
						class="required" />
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
									<c:if test="${loanCustomer.dictEducationCode==curEdu.value}">
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
					   <c:if test="${loanCustomer.dictMarryStatusCode==aitem.value}"> 
					    selected=true 
					   </c:if>
					  >${aitem.label}</option>
				     </c:forEach>
				</select>
		</td>
      </tr>
 </table>
</div>
 <h2 class=" f14 pl10 mt20">车借信息</h2>
  <div class="content box2">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	<c:choose>
	   <c:when test="${carCustomerBase.customerNameOcr!=null && carCustomerBase.customerNameOcr!=''}">  
	     	<tr>
		    	<td style="padding-left:130px;" colspan="2">
		    		<img src="${ctx}/carpaperless/confirminfo/getPreparePhoto?docId=${carCustomerBase.customerNameOcr}" width="110px" height="30px"/>
		    		<button  class="btn btn-small"  data-target="#del_div" data-toggle="modal" name="back" id="delCarOcr"/>删除图片</button>
		    	</td>
		    </tr>
	   </c:when>
	   <c:otherwise> 
	   </c:otherwise>
	</c:choose>  

	<tr>
   		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>车辆品牌与型号：</label>
   				<input id="vehicleBrandModel" name="vehicleBrandModel" class="required input_text178" type="text" value="${carVehicleInfo.vehicleBrandModel}" maxlength="19"/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
   		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>车牌号码：</label>
   				<input id="plateNumbers" name="plateNumbers" class="required input_text178 plateNum" maxlength="8" type="text" value="${carVehicleInfo.plateNumbers}"/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
		</tr>
		<tr>
		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>预计到店时间：</label>
   				 <input id="planArrivalTime" name="planArrivalTime" type="text" class="input_text178 required Wdate" size="10"  
		      onClick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-%d'})" style="cursor: pointer" value="<fmt:formatDate value='${carCustomerConsultation.planArrivalTime}' pattern="yyyy-MM-dd"/>" />
   				<span id="blackTip" style="color: red;"></span>
		</td>
  		<td style="width:32%">
	    <label class="lab"><span style="color: #ff0000"></span>团队经理：</label>
	    <input name="consTeamEmpName" disabled="true" class="input_text178" value="${consTeamEmpName}"/>
	    <input name="consTeamManagerCode" type="hidden" value="${consTeamManagerCode}">
 	    </td>
  		<td >
     		<label class="lab"><span style="color: #ff0000">*</span>客户经理：</label>
  				<select name="managerCode" class="select180 required select2Req">
		    <option value="">请选择</option>
		    <c:forEach items="${termManagers}" var="item">
		      <option value="${item.id}" ${item.id==carCustomerConsultation.managerCode?"selected = true":""}>
		      	${item.name}
		      </option>
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

<jsp:include page="../delImageForm.jsp"></jsp:include>

</body>
</html>