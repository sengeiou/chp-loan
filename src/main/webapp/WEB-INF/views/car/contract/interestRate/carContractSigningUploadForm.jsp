<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html style="overflow-x: auto; overflow-y: auto;">
<head>
<title>合同签约上传</title>
<meta name="decorator" content="default" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

<style>
.mainCurrentPhotoZoneClas li {
width:100px;
float:left;
}</style>
<script type="text/javascript" src="${context}/js/payback/common.js"></script>
<script type="text/javascript" src="${context}/js/car/contract/carContractHandle.js"></script>
<script type="text/javascript" src="${context}/js/car/contract/contractSign.js"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript">
$(function(){
	  $("#xyckBtn").click(function(){
		  var loanCode=$('#loanCode').val();
		  var contractCode=$('#contractCode').val();
		  var url='${ctx}/car/carContract/checkRate/xieyiList?loanCode='+loanCode+'&contractCode='+contractCode;
		  art.dialog.open(url, {  
			   id: 'protcl',
			   title: '协议查看',
			   lock:false,
			   width:1500,
			   height:600
			},false); 
		});
})
$(document).ready(function(){
	//必填验证
	$("#sendBack").bind('click',function(){
		$("#workParam").validate({
			onclick: true
		});
	})
	$('#historyBtn').bind('click',function(){
		  showCarLoanHis($(this).attr('loanCode'));
	});  
	//查看详细信息
	$('#showView').bind('click',function(){
		  showCarLoanInfo($(this).attr('loanCode'));
	});
	// 客户放弃		
	$("#giveUpBtn").bind('click',function(){
		$("#response").val("UPLOAD_CONTRACT_ABANDON");
		var url = ctx + "/car/carContract/checkRate/signUploadContractHandle";
		art.dialog.confirm("是否确定放弃客户?",function(){
			$("#workParam").attr('action',url);
			$("#workParam").submit();
		});
	});
	// 门店拒绝
	$("#storeRefuseBtn").bind('click',function(){
		$("#response").val("UPLOAD_CONTRACT_STORE_REFUSE");
		var url = ctx + "/car/carContract/checkRate/signUploadContractHandle";
		art.dialog.confirm("确定执行门店拒绝?",function(){
			$("#workParam").attr('action',url);
			$("#workParam").submit();
		});
	});
	//退回节点
	$("#backSure").bind('click',function(){
		$("#response").val("BACK_SIGN_CONTRACT");
		var url = ctx + "/car/carContract/checkRate/signUploadContractHandle";
		$("#workParam").attr('action',url);
		$("#workParam").submit();
	});
});
/**
 *合同上传 
 * 
 */	
imageUrl = '${workItem.bv.imageurl}';
function showCarLoanDownload(){
	/* art.dialog.open('http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:HUIJINSY1;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111', { */
	art.dialog.open(imageUrl, {
		title: "客户影像资料",	
	top: 80,
	lock: true,
	    width: 1000,
	    height: 450,
	},false);	
} 
	
function queryuploadForm(){
	$("#handleUploadContract").removeAttr("disabled");
}
</script>
</head>
<body>
	<form id="param" class="hide">
		<input type="text" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
	</form>
	<div
		style="height: 100%; position: absolute; top: 0px; bottom: 2px; width: 100%; overflow: auto;">
		<div class="pt5 pr30 mb5">
			<div class="r">
				<input id="giveUpBtn" type="button" class="btn btn-small" value="客户放弃" />
				<input id="storeRefuseBtn" type="button" class="btn btn-small" value="门店拒绝" />
				<button  class="btn btn-small"  data-target="#back_div" data-toggle="modal" name="back" id="sendBack"  />退回</button>
				<input type="button" class="btn btn-small" id="historyBtn" loanCode="${workItem.bv.carLoanInfo.loanCode}"  value="历史">
				<input type="button" class="btn btn-small" id="showView" loanCode="${workItem.bv.carLoanInfo.loanCode}"  value="查看" >
				<button class="btn btn-small" id="xyckBtn">协议查看</button>
				<%-- <button class="btn btn-small" onclick="contractDownload('${workItem.bv.carLoanInfo.loanCode}','3')" >协议下载</button> --%>
				<button class="btn btn-small" loanCode="${workItem.bv.carLoanInfo.loanCode}" onclick="showCarLoanDownload()">上传合同</button>
				<button class="btn btn-small" loanCode="" onclick="queryuploadForm()">确认上传</button>
			</div>
		</div>
		<!-- <div style="width: 60%; height: 99%; float: left;">
			<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0"
				width="100%" height="100%"
				src="http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111">
			</iframe>
		</div> -->
		<div style="width: 100%; height: 100%; float: right; overflow: auto">
			<form>
				<h2 class="f14 pl10">客户信息</h2>
				<div class="box2" style="overflow: auto">
					<table class="table1" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td><label class="lab">合同编号：</label>${workItem.bv.carContract.contractCode}</td>
							<td><label class="lab">客户姓名：</label>${workItem.bv.carCustomer.customerName}</td>
						</tr>
						<tr>
							<td><label class="lab">性别：</label>${workItem.bv.carCustomer.customerSex}</td>
							<td><label class="lab">证件类型：</label>${workItem.bv.carCustomer.dictCertType}</td>
						</tr>
						<tr>
							<td><label class="lab">证件号码：</label>${workItem.bv.carCustomer.customerCertNum}</td>
							<td><label class="lab">证件有效期：</label>
								<c:choose>
									<c:when test="${workItem.bv.carCustomer.isLongTerm == '1'}">
										长期
									</c:when>
									<c:otherwise>
										<fmt:formatDate value='${workItem.bv.carCustomer.idStartDay}' pattern="yyyy-MM-dd"/> - <fmt:formatDate value='${workItem.bv.carCustomer.idEndDay}' pattern="yyyy-MM-dd"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td><label class="lab">学历：</label>${workItem.bv.carCustomer.dictEducation}</td>
							<td><label class="lab">婚姻状况：</label>${workItem.bv.carCustomer.dictMarryStatus}</td>
						</tr>						
						<c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
							<tr>
								<td><label class="lab">共借人姓名：</label>${cobos.coboName}</td>
								<td><label class="lab">共借人身份证号：</label>${cobos.certNum}</td>
							</tr>
							<tr>
								<td><label class="lab">共借人地址：</label><sys:carAddressShow detailAddress="${cobos.dictLiveView}"></sys:carAddressShow>  </td>
								<td></td>
							</tr>

						</c:forEach>
					</table>
				</div>
				
	   <h2 class="pt5 pl10 f14 mt20">其它信息</h2>
         <div class="box2">
		         <table>
		           <tr>
		             <td colspan="1"><label class="lab">签约平台：</label>
		                <select id="bankSigningPlatform" va class="select180">
		                   <option value="">请选择</option>
		                     <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="curItem">
		                    <c:if test="${curItem.label!='中金' && curItem.value!='6'}">
							  <option value="${curItem.value}"
							    <c:if test="${workItem.bv.carCustomerBankInfo.bankSigningPlatform==curItem.value}">
							      selected=true 
							    </c:if>
							  >${curItem.label}</option>
							  </c:if>
						    </c:forEach> 
		               </select>
		               <span id="bankSigningPlatformMsg" style="color:red;"></span>
		             </td>
		           </tr>
		         </table>
		    </div>
		 <c:if test="${workItem.bv.carContract.paperLessFlag=='1'}"> 
			   <h2  class=" pl10 f14 mt20">签约信息</h2>
			    <div class ="box2">
			       <table>
			          <tr style="height:20px"></tr>
			            <tr style="height:240px">
			            	<td><label class="lab">借款人：</label>${workItem.bv.carCustomer.customerName}</td>
			            	 <td>
			             	 	<ul class="mainCurrentPhotoZoneClas" style="height:120px;width:220px;list-style:none;padding-left:20px;">
			              	 	<li id="mainCurrentPhotoZone" class="img">
			              	 	<a id="mainPlotPhotoA" 
			               	   <c:choose>
			               	     <c:when test="${workItem.bv.carCustomer.curPlotId!=null && workItem.bv.carCustomer.curPlotId!=''}">
			               	        href="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${workItem.bv.carCustomer.curPlotId}" 
			               	     </c:when>
			               	     <c:otherwise>
			               	        href="${context}/static/images/image-1.png" 
			               	     </c:otherwise>
			               	    </c:choose> 
			               	      style="float:right；" class="lightbox-2" rel="flowers" title="现场照片">
							    <i class="icon-search" style="cursor: pointer;"></i>
							 </a>
			               		 <img class="pc1Image"
			               	  		 <c:choose>
			               	    		 <c:when test="${workItem.bv.carCustomer.curPlotId!=null && workItem.bv.carCustomer.curPlotId!=''}">
			               	       		 src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${workItem.bv.carCustomer.curPlotId}" 
			               	    	 </c:when>
			               	     	 <c:otherwise>
			               	        	 src="${context}/static/images/image-1.png" 
			               	    	 </c:otherwise>
			               	 	   </c:choose>
			               	    style="cursor: pointer;" class="img-rounded" 
			               	   onclick="contractSign.openFileDialog('mainPlotPhoto','mainPlotPhotoA','','${workItem.bv.carCustomer.id}','0','0','${workItem.bv.carCustomer.loanCode}','${workItem.bv.carContract.contractCode}');" id="mainPlotPhoto" z-index="1" alt="现场照片" height='100px' width='80px' />
			               </li>
			               <li id="mainCardPhotoZone" class="img">
			                 <img class="pc2Image"
			                  <c:choose>
			               	     <c:when test="${workItem.bv.carCustomer.idCardId!=null && workItem.bv.carCustomer.idCardId!=''}">
			               	        src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${workItem.bv.carCustomer.idCardId}" 
			               	     </c:when>
			               	     <c:otherwise>
			               	        src="${context}/static/images/image-2.png" 
			               	     </c:otherwise>
			               	    </c:choose>
			                    style="cursor: pointer;" class="img-rounded"  
			               	   onclick="contractSign.openFileDialog('mainIdPhoto','mainIdPhotoA','mainBorrower','${workItem.bv.carCustomer.id}','1','0','${workItem.bv.carCustomer.loanCode}','${workItem.bv.carContract.contractCode}');" id="mainIdPhoto" z-index="1" alt="身份证照片" height='100px' width='80px' />
			               	  <a id="mainIdPhotoA"
			               	   	<c:choose>
			               	     	<c:when test="${workItem.bv.carCustomer.idCardId!=null && workItem.bv.carCustomer.idCardId!=''}">
			               	      	  href="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${workItem.bv.carCustomer.idCardId}" 
			               	    	 </c:when>
			               	    	 <c:otherwise>
			               	       	  href="${context}/static/images/image-2.png" 
			               	    	 </c:otherwise>
			               	    </c:choose>
			               	      style="float:right" class="lightbox-2" rel="flowers" title="身份证照片">
							    <i class="icon-search" style="cursor: pointer;"></i>
							 </a> 
			               </li>
			              </ul>
			             </td>
			              <td><input type="button" class="btn btn-small ml20" id="mainBorrower" 
			              	onclick="contractSign.validIDCard('${workItem.bv.carCustomer.id}','0',this);" 
			              	<c:if test="${workItem.bv.carCustomer.curPlotId!=null && workItem.bv.carCustomer.curPlotId!=''}">
			                    docId = ${workItem.bv.carCustomer.curPlotId}
			                </c:if>
			                  validResult="${workItem.bv.carCustomer.idValidFlag}"  
			                 <c:if test="${workItem.bv.carCustomer.idValidFlag=='1'}"> 
			                        disabled=true 
			                 </c:if> 
			              	value="身份验证"></input></td>
			             
			             <td>
			             <div id="mainSignPhotoZone" class="ml20" style="width:80px;">
			                   </div>
			             </td>
			           </tr>
			          
			           <c:forEach items="${workItem.bv.carLoanCoborrowers}" var="cobo" varStatus="vstatus">
			               <tr style="height:240px">
			                <td><label class="lab">共借人：</label>${cobo.coboName}</td>
			                <td>
			             	  <ul class="mainCurrentPhotoZoneClas" style="height:120px;width:220px;list-style:none;padding-left:20px;">
			              	    <li id="coboCurrentPhotoZone${vstatus}" class="img">
			                	 	<img class="coboimage1"
			                	 	   <c:choose>
			               	    		 <c:when test="${cobo.curPlotId!=null && cobo.curPlotId!=''}">
			               	        		src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${cobo.curPlotId}" 
			               	    		 </c:when>
			               	   			 <c:otherwise>
			               	        		src="${context}/static/images/image-1.png" 
			               	    		 </c:otherwise>
			               			  </c:choose>
			                	 	   style="cursor: pointer;" class="img-rounded"  
			               	   			onclick="contractSign.openFileDialog('plotPhoto${vstatus.index}','plotPhotoA${vstatus.index}','','${cobo.id}','0','1','${cobo.loanCode}','${workItem.bv.carContract.contractCode}');" id="plotPhoto${vstatus.index}" z-index="1" alt="现场照片" height='100px' width='80px' />
			               	  		<a id='plotPhotoA${vstatus.index}'
			               	  		   <c:choose>
			               	    		 <c:when test="${cobo.curPlotId!=null && cobo.curPlotId!=''}">
			               	        		href="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${cobo.curPlotId}" 
			               	    		 </c:when>
			               	   			 <c:otherwise>
			               	        		href="${context}/static/images/image-1.png" 
			               	    		 </c:otherwise>
			               			   </c:choose>
			               	  		     style="float:right" class="lightbox-2" rel="flowers" title="现场照片">
							    		<i class="icon-search" style="cursor: pointer;"></i>
							 		</a> 
			               	    </li>
			               		<li id="coboCardPhotoZone${vstatus}" class="img">
			                 		 <img class="coboimage2"
			                 		    <c:choose>
			               	     			<c:when test="${cobo.idCardId!=null && cobo.idCardId!=''}">
			               	      			  src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${cobo.idCardId}" 
			               	    	 		</c:when>
			               	    		 <c:otherwise>
			               	       			  src="${context}/static/images/image-2.png" 
			               	    		 </c:otherwise>
			               	   		   </c:choose>
			                   	         style="cursor: pointer;" class="img-rounded"  
			               	  			 onclick="contractSign.openFileDialog('IdPhoto${vstatus.index}','IdPhotoA${vstatus.index}','coboIDValidBtn${vstatus.index}','${cobo.id}','1','1','${cobo.loanCode}','${workItem.bv.carContract.contractCode}');" id="IdPhoto${vstatus.index}" z-index="1" alt="身份证照片" height='100px' width='80px' />
			               	 	     <a id='IdPhotoA${vstatus.index}' 
			               	 	       <c:choose>
			               	     			<c:when test="${cobo.idCardId!=null && cobo.idCardId!=''}">
			               	      			  href="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${cobo.idCardId}" 
			               	    	 		</c:when>
			               	    		 <c:otherwise>
			               	       			  href="${context}/static/images/image-2.png" 
			               	    		 </c:otherwise>
			               	   		   </c:choose>
			               	 	         style="float:right" class="lightbox-2" rel="flowers" title="身份证照片">
							    		<i class="icon-search" style="cursor: pointer;"></i>
							 		 </a>
			              		</li>
			              	   </ul>
			             </td>
			             <td >
			             <input type="button" id="coboIDValidBtn${vstatus.index}" 
			             onclick="contractSign.validIDCard('${cobo.id}','1',this);" class="btn btn-small ml20" 
			               <c:if test="${cobo.curPlotId!=null && cobo.curPlotId!=''}">
		                     docId=${cobo.curPlotId}
		                  </c:if>
		                  validResult="${cobo.idValidFlag}" 
		                    <c:if test="${cobo.idValidFlag=='1'}">
		                        disabled=true 
		                    </c:if> 
			             value="身份验证"/>
			             </td>
			              <td>
			                <div id="coboSignPhotoZone" class="ml20" style="width:80px;">
			                   <!-- <span id="signPhoto" >请签字</span> -->
			                  </div>
			              </td>
			               
			              </tr>
			           </c:forEach><!-- checked="checked"  -->
			           <tr>
			           <td><label class="lab"><input type="checkbox"  disabled="disabled" 
			                 <c:if test="${cobo.appSignFlag!='0'}">
			                    checked="checked" 
			                 </c:if>
			                 name="custAppSign" id="custAppSignCheck" value="1">APP签字</input></label>
			                   <input type="button" class="btn btn-small" disabled="disabled" id="signBtn" value="签约"/>
			               </td>
			           </tr>
			       </table>
			    </div>
			 </c:if>  
				<input type="hidden" value="${workItem.bv.carLoanInfo.loanCode}" id="loanCode"/>
		    	<input type="hidden" value="${workItem.bv.carContract.contractCode}" id="contractCode"/>
		    	<input type="hidden" name="signingPlatformName" id="signingPlatformName" />
				<input type="hidden" name="checkResult" value="TO_AUDIT_CONTRACT"/>
				<input type="hidden" name="conditionThroughFlag" value="${workItem.bv.carAuditResult.auditResult }">
				<div class="tright mt10" style="margin-bottom: 50px">
					<input type="button" id="handleUploadContract" disabled="disabled" class="btn btn-primary" value="确认"></input>
					<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
					onclick="JavaScript:history.go(-1);"></input> 
				</div>
			</form>
			
		</div>
	</div>
	<form id="workParam">
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input name="menuId" type="hidden" value="${param.menuId}" />
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input> <input
			type="hidden" value="${workItem.flowCode}" name="flowCode"></input> <input
			type="hidden" value="${workItem.stepName}" name="stepName"></input> <input
			type="hidden" value="${workItem.wobNum}" name="wobNum"></input> <input
			type="hidden" value="${workItem.token}" name="token"></input> <input
			type="hidden" value="" name="response" id="response"></input>
			<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
			<input type="hidden" name="loanCode" value="${workItem.bv.carLoanInfo.loanCode }" /> 
			<!--退回弹框  -->
		    <div  id="back_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
			<div class="modal-dialog" role="document">
			<div class="modal-content">
			<div class="modal-header">
		   	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		
			<div class="l">
		         		 <h4 class="pop_title">退回</h4>
		       	 	</div>
		 	</div>
		 	<div class="modal-body">
								<table class="table4" cellpadding="0" cellspacing="0"
							border="0" width="100%" id="tpTable">
							<tr>
								<td><lable class="lab"><span style="color: red;">*</span>退回至流程节点：</lable></td>
								<td> <select class="required">
										<option value="">请选择退回节点</option>
										<c:forEach
											items="${fns:getDictLists('16', 'jk_car_loan_status')}"
											var="item">
											<option value="${item.value}">${item.label}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
			       	  	  	<tr>
								<td><lable class="lab"><span style="color: red;">*</span>退回原因：</lable></td>
								<td>
								<textarea rows="" cols="" class="textarea_refuse required" id="remark" name="remark"></textarea>
								</td>
							</tr>
						</table>
		 	</div>
		 	
		 	
		 	<div class="modal-footer">
		 	<button id="backSure" class="btn btn-primary"  aria-hidden="true" >确定</button>
		 	<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
		 	</div>
			</div>
			</div>
		
			</div>
	</form>
</body>
</html>