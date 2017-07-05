<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html style="overflow-x: auto; overflow-y: auto;">
<head>
<title>车借展期合同签约上传</title>
<meta name="decorator" content="default" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style>
.mainCurrentPhotoZoneClas li {
width:100px;
float:left;
}</style>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/payback/common.js"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendContractHandle.js"></script>
<script type="text/javascript" src="${context}/js/car/contract/contractSign.js"></script>
<script type="text/javascript">
function queryuploadForm(){
	$("#handleUploadContract").removeAttr("disabled");
}
$(document).ready(function(){
	// 客户放弃		
	$("#giveUpBtn").bind('click',function(){
		$("#response").val("EXTEND_GIVE_UP");
		var url = ctx + "/car/carExtendContract/extendUploadContractHandle";
		art.dialog.confirm("是否放弃合同签约上传?",function(){
			$("#workParam").attr('action',url);
			$("#workParam").submit();
		});
	});
	//退回节点
	$("#backSure").bind('click',function(){
		$("#response").val("BACK_SIGN_CONTRACT");
		var url = ctx + "/car/carExtendContract/extendUploadContractHandle";
		$("#workParam").attr('action',url);
		$("#workParam").submit();
	});
	 $("#xyckBtn").click(function(){
		  var loanCode=$('#loanCode').val();
		  var contractCode=$('#contractCode').val();
		  var url='${ctx}/car/carContract/checkRate/xieyiList?loanCode='+loanCode+'&contractCode='+contractCode;
		  art.dialog.open(url, {  
			   id: 'protcl',
			   title:'协议查看',
			   lock:false,
			   width:1500,
			   height:600
			},false); 
	});
	$("#ImageData").click(function(){
		art.dialog.open('${workItem.bv.imageurl}'/* 'http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111' */, {
			title: "客户影像资料",	
			top: 80,
			lock: true,
			    width: 1000,
			    height: 450,
		}, false);	
	});
});
</script>
</head>
<body>
	<%-- <form id="param" class="hide">
	 	<input type="text" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
	</form> --%>
	<div
		style="height: 100%; position: absolute; top: 0px; bottom: 2px; width: 100%; overflow: hidden">
		<div class="pt5 pr30 mb5">
			<div class="r">
			    <input type="hidden" value="${workItem.bv.carLoanInfo.loanCode}" id="loanCode"/>
    			<input type="hidden" value="${workItem.bv.carContract.contractCode }" id="contractCode"/>
				<input id="giveUpBtn" type="button" class="btn btn-small" value="客户放弃" />
				<button  class="btn btn-small"  data-target="#back_div" data-toggle="modal" name="back" id="sendBack"  />退回</button>
				<input type="button" class="btn btn-small" id="showView" loanCode="${workItem.bv.carLoanInfo.applyId}"  value="查看" >
				<input type="button" class="btn btn-small" id="historyBtn" loanCode="${workItem.bv.carLoanInfo.loanCode}" value="历史">
				<input type="button" class="btn btn-small" id="ImageData" value="上传合同">
				<button class="btn btn-small" id="xyckBtn">协议查看</button>
				<button class="btn btn-small" onclick="contractDownload('${workItem.bv.carLoanInfo.loanCode}','3')" >协议下载</button>
				<button class="btn btn-small" loanCode="" onclick="queryuploadForm()">确认上传</button>
			</div>
		</div>
		<%-- <div style="width: 60%; height: 99%; float: left;">
			<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0"
				width="100%" height="100%"
				src="${workItem.bv.imageurl}"
				>
				<!-- http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111 -->
			</iframe>
		</div> --%>
		<div style="width: 100%; height: 100%; float: right; overflow: auto">
			<form>
				<input type="hidden" name="signingPlatformName" id="signingPlatformName" />
				<h2 class="f14 pl10">客户信息</h2>
				<div class="box2">
					<table class="table1" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr>
							<td><label class="lab">合同编号：</label>${workItem.bv.carContract.contractCode }</td>
							<td><label class="lab">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
						</tr>
						<tr>
							<td><label class="lab">客户姓名：</label>${workItem.bv.carCustomer.customerName}</td>
							<td><label class="lab">证件号码：</label>${workItem.bv.carCustomer.customerCertNum}</td>
						</tr>
						<c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
							<tr>
								<td><label class="lab">共借人姓名：</label>${cobos.coboName}</td>
								<td><label class="lab">共借人身份证号：</label>${cobos.certNum}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<h2 class="f14 pl10 mt20">借款信息</h2>
				<div class="box2">
					<table class="table1" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr>
							<td><label class="labl">合同金额：</label>
							<fmt:formatNumber
									value="${workItem.bv.carContract.contractAmount}"
									pattern="0.00" />元</td>
							<td><label class="labl">展期费用：</label>
							<fmt:formatNumber value="${workItem.bv.carContract.extensionFee}"
									pattern="0.00" />元</td>
							<td><label class="labl">降额：</label>
							<fmt:formatNumber value="${workItem.bv.carContract.derate}"
									pattern="0.00" />元</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${workItem.bv.carContract.productTypeName == 'GPS'}">
									<td><label class="labl">平台及流量费：</label>
									<fmt:formatNumber value="${workItem.bv.carLoanInfo.flowFee == null ? 0 : workItem.bv.carLoanInfo.flowFee }"
											pattern="0.00" />元</td>
								</c:when>
								<c:otherwise>
									<td><label class="labl">停车费：</label>
									<fmt:formatNumber
											value="${workItem.bv.carLoanInfo.parkingFee }" pattern="0.00" />元</td>
								</c:otherwise>
							</c:choose>
							<td><label class="labl">展期还款总金额：</label>
							<fmt:formatNumber
									value="${workItem.bv.carCheckRate.extendPayAmount}"
									pattern="0.00" />元</td>
							<td><label class="labl">合同签订日期：</label> <fmt:formatDate
									value='${workItem.bv.carContract.contractFactDay}' type='date'
									pattern="yyyy-MM-dd" /></td>
						</tr>
						<c:if test="${workItem.bv.carLoanInfo.dictProductType=='CJ01'}">
					    <tr>
					    	<td>
					    		<label class="labl">设备使用费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.deviceUsedFee == null ? 0 : workItem.bv.carLoanInfo.deviceUsedFee }" pattern="#,##0.00" />元
					    	</td>
					    	<td></td>
					    	<td></td>
					    </tr>
					</c:if>
					</table>
				</div>
				<h2 class="f14 pl10 mt20">其他信息</h2>
				<div class="box2">
					<table class="table1" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr>
							<td>
							<label class="labl">签约平台：</label>
			                <select id="bankSigningPlatform" va class="select180">
		                   <option value="">请选择</option>
		                     <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="curItem">
		                     <c:if test="${curItem.label!='中金' && curItem.value!='6'}">
							  <option value="${curItem.value}"
							    <c:if test="${workItem.bv.carCustomerBankInfo.bankSigningPlatform==curItem.value}">
							      selected="true"
							    </c:if>
							  >${curItem.label}</option>
							  </c:if>
						    </c:forEach> 
		               </select>
							</td>
						</tr>
					</table>
				</div>
				<c:if test="${workItem.bv.carContract.paperLessFlag=='1'}"> 
			   <h2  class=" pl10 f14 mt20">签约信息</h2>
			    <div class ="box2">
			       <table>
			          <tr style="height:20px"></tr>
			            <tr style="height:140px">
			            	<td><label class="lab">借款人：</label>${workItem.bv.carCustomer.customerName}</td>
			            	 <td>
			             	 	<ul class="mainCurrentPhotoZoneClas" style="height:120px;width:220px;list-style:none;padding-left:20px;">
			              	 	<li id="mainCurrentPhotoZone" class="img">
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
			               	  <a id="mainPlotPhotoA" 
			               	   <c:choose>
			               	     <c:when test="${workItem.bv.carCustomer.curPlotId!=null && workItem.bv.carCustomer.curPlotId!=''}">
			               	        href="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${workItem.bv.carCustomer.curPlotId}" 
			               	     </c:when>
			               	     <c:otherwise>
			               	        href="${context}/static/images/image-1.png" 
			               	     </c:otherwise>
			               	    </c:choose> 
			               	      style="float:right" class="lightbox-2" rel="flowers" title="现场照片">
							    <i class="icon-search" style="cursor: pointer;"></i>
							 </a> 
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
			              <td style="vertical-align:bottom;padding-bottom:20px"><input type="button" class="btn btn-small ml20" id="mainBorrower" 
			              		onclick="contractSign.validIDCard('${workItem.bv.carCustomer.id}','0',this);"
			              		<c:if test="${workItem.bv.carCustomer.curPlotId!=null && workItem.bv.carCustomer.curPlotId!=''}">
			                  	  docId = ${workItem.bv.carCustomer.curPlotId}
				                </c:if>
				                  validResult="${workItem.bv.carCustomer.idValidFlag}"  
				                 <c:if test="${workItem.bv.carCustomer.idValidFlag=='1'}"> 
				                        disabled=true 
				                 </c:if>  
			              		value="身份验证"></input>
			              </td>
			             
			             <td style="vertical-align:bottom;padding-bottom:23px">
			              <div id="mainSignPhotoZone" class="ml20" style="width:80px;">
			                 <!-- <span id="signPhoto" >请签字</span>   -->
			               </div>
			             </td>
			           </tr>
			          
			           <c:forEach items="${workItem.bv.carLoanCoborrowers}" var="cobo" varStatus="vstatus">
			               <tr style="height:140px">
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
			               	   			onclick="contractSign.openFileDialog( 'plotPhoto${vstatus.index}','plotPhotoA${vstatus.index}','','${cobo.id}','0','1','${cobo.loanCode}','${workItem.bv.carContract.contractCode}');" id="plotPhoto${vstatus.index}" z-index="1" alt="现场照片" height='100px' width='80px' />
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
			             <td style="vertical-align:bottom;padding-bottom:20px">
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
			              <td style="vertical-align:bottom;padding-bottom:23px">
			                <div id="coboSignPhotoZone" class="ml20" style="width:80px;">
			                  <!--  <span id="signPhoto" >请签字</span> -->
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
		 	<input type="hidden" name="checkResult" value="TO_AUDIT_CONTRACT">	
	 		<div class="tright mt10" style="margin-bottom: 50px">
				<input type="button" id="handleUploadContract" disabled="disabled" class="btn btn-primary" value="确认"></input>
				<input type="button" class="btn btn-primary" onclick="javascript:window.location='${ctx}/car/carExtendWorkItems/fetchTaskItems/extendReceived'" value="取消"></input>
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
			<input type="hidden" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
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
		
		  	  <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%" id="tpTable">
       	  	  	<tr>
					<td><lable class="lab">退回原因：</lable>
					<textarea rows="" cols="" class="textarea_refuse" id="remark" name="backReason"></textarea>
					</td>
				</tr>
		      	  </table>
		 	</div>
		 	<div class="modal-footer">
		 	<button id="backSure" class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >确定</button>
		 	<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
		 	</div>
			</div>
			</div>
		
			</div>
	</form>
</body>
</html>