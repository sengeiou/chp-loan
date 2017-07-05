<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/payback/common.js"></script>
<script type="text/javascript" src="${context}/js/contract/contractSign.js?ver=2"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${ctxStatic}/lightbox/jquery.lightbox.js"></script>
<link rel="stylesheet" href="${ctxStatic}/lightbox/css/lightbox.css" type="text/css" media="screen" />
<script type="text/javascript">
 REDIRECT_URL="/borrow/borrowlist/fetchTaskItems";
 imageUrl = '${workItem.bv.imageUrl}';
 dispatchParam={
	response:'',
	redirectUrl:REDIRECT_URL
 };
 function cancelFlag(){
	 $.ajax({
			type : 'post',
			url : ctx + '/apply/contractAudit/asynChangeFlag',
			data : {
				'batchColl':$('#flagCancelParam').val(),
				'attributName':'loanFlag',
				'batchValue':' ',
				"batchLabel":' '
			},
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				$('#loanFlagText').html('财富');
				//$('#CancelJXFlag').attr('disabled',true);
				art.dialog.alert('金信标识已取消，请重新生成合同');
			},
			error : function() {
				art.dialog.error('请求异常！', '提示信息');
			}
		});
  }
 $(document).ready(function(){
	/* $('#downLoadBtn').bind('click',function(){
		var result = contractSign.judgeSignTime();
		if(!result){
			art.dialog.alert("预约签署日期早于当前日期，不允许操作",'提示');
			return false;
		} 
		contractSign.download($(this).attr("applyId"),$('#token').val(),$('#wobNum').val());
	});  */
	 contractSign.load();
});
 $(document).ready(function(){ 
		$("#downPdfVal").val($("#myTab a").attr("docId"));
		$("#downPdfValName").val($("#myTab a").attr("fileName"));
		$("#downPdfBtn").hide();
		$('#myTab a').click(function (e) {
			if($(this).attr("docId")!=null){
				$("#downPdfBtn").show();
			}else{
				$("#downPdfBtn").hide();
			}			
			$("#downPdfVal").val($(this).attr("docId"));
			$("#downPdfValName").val($(this).attr("fileName"));
		});
	});
 function downLoadPdf(){
		window.location="${ctx}/apply/contractAudit/downLoadContractsingle?docId="+$("#downPdfVal").val()+"&fileName="+$("#downPdfValName").val();
	}
 
 $(function(){
 	$('#newRetBtn').bind("click", function(){
 		window.location = ctx+"/newBorrow/borrowlist/fetchTaskItems";
	 });
 })
 function dateEnd(type){
	 var loanCode=$('#loanCode1').val();
	 var applyId=$('#applyId1').val();
	 var redirectUrl=$('#redirectUrl1').val();
	 if(type=='1'){
		 art.dialog.confirm("是否确认执行客户放弃",
			function(){
			 window.location = ctx+"/newBorrow/borrowlist/dateEnd?type=1&loanCode="+loanCode+"&redirectUrl="+redirectUrl+'&applyId='+applyId+'&stepName=合同签订';  
		 
	  });
	 }else{
		 art.dialog.confirm("是否确认执行门店拒绝",
					function(){
					 window.location = ctx+"/newBorrow/borrowlist/dateEnd?type=2&loanCode="+loanCode+"&redirectUrl="+redirectUrl+'&applyId='+applyId+'&stepName=合同签订';   
					 
		 });
	 }
 }
</script>
 <style>
 	img{cursor: pointer;border: 1px solid rgb(204, 204, 204);padding: 4px}
 	.img{height:120px;width:80px;float:left;margin-left:20px ;position:relative}
 	.icon-search{position:relative;bottom:20px;right:-5px}
 	
 </style>
<meta name="decorator" content="default" />
</head>
<body>
<div>
<inut type="hidden" id="downPdfVal"/>
<inut type="hidden" id="downPdfValName"/>
  <ul id="myTab" class="nav nav-tabs">
   <c:if test="${fn:length(workItem.bv.files)>0}">
     <li class="active">
       <a href="#home" data-toggle="tab">
        	  客户信息
       </a>
     </li>
    </c:if>
   	<c:forEach items="${workItem.bv.files}" var="file" varStatus="status">
   	  <c:if test="${file.docId!=null && file.docId!='' }">
	      <li>
	       <a href="#${file.id}" data-toggle="tab" docId="${file.docId}" fileName="${file.contractFileName}">
	         ${file.contractFileName} 
	       </a>
	      </li>
      </c:if>
   </c:forEach>
     <input type="button" style="float: right" id="downPdfBtn"
				onclick="downLoadPdf()" class="btn btn-success"
				value="下载">
 </ul>

 <div id="myTabContent" class="tab-content" style="padding-top:5px">
   <div id="home"  class="tab-pane fade in active">
    <p class="tright  pr30  pb5" >
    <c:set var="bview" value="${workItem.bv}"/>
        <%-- 
        <a type="button" class="btn btn-small" id ="downLoadBtn" name="downLoadContract" applyId="${bview.applyId}">
                                        <span style="color:#333">下载</span>
        </a> --%>
        <button class="btn btn-small" loanCode="${bview.oldLoanCode}" 
          <c:if test="${bview.dictLoanStatus=='62'}">
            disabled=true
          </c:if> 
         id="upLoadBtn">上传合同</button>
           <c:if test="${bview.issplit=='0'}">
			<button class="btn btn-small" loanCode="${bview.loanCode}" oldLoanCode="${bview.oldLoanCode}"
			 <c:if test="${bview.dictLoanStatus=='62'}">
	            disabled = true
	          </c:if> 
			 applyId="${bview.applyId}" id="confirmBtn">提交</button>
		   </c:if>
            <c:if test="${bview.issplit=='1'}">
           	 <button class="btn btn-small" loanCode="${bview.loanCode}"  oldLoanCode="${bview.oldLoanCode}" <c:if test="${bview.dictLoanStatus=='62'}">  disabled = true </c:if>  applyId="${bview.applyId}" id="confirmBtn">提交</button>
            </c:if>
		<%--  <c:if test="${bview.isStoreAssistant=='1'}"> --%>
			<c:if test="${bview.proposeFlag=='1' || bview.issplit!='0'}">
				<button class="btn btn-small" id="storeGiveUp">门店拒绝</button>
				<button class="btn btn-small" id="giveUpBtn">客户放弃</button>
			</c:if>
			<c:if test="${bview.reconsiderFlag=='0' && bview.proposeFlag=='0' && bview.issplit=='0'}">
				<c:if test="${bview.loanInfo.refuseFlag!='1'}">
					<button class="btn btn-small" id="storeGiveUp1">建议拒绝</button>
				</c:if>
				<c:if test="${bview.loanInfo.outFlag!='1'}">
					<button class="btn btn-small" id="giveUpBtn1">建议放弃</button>
				</c:if>
			</c:if>
		<%--   </c:if> --%>
	<%-- 	<c:if test="${bview.loanFlag=='金信'}">
			<button class="btn btn-small" id="CancelJXFlag">取消金信标识</button>
		</c:if> --%>
		<button class="btn btn-small" loanCode="${bview.loanCode}" id="hisBtn">历史</button>
		<c:if test="${bview.isStoreAssistant=='1' && bview.issplit=='0'}"> 
			<button class="btn btn-small" 
				<c:if test="${bview.dictLoanStatus=='62'}"> 
				disabled = true 
				</c:if> 
			id="backBtn">退回</button> 
		</c:if> 
		<button class="btn btn-small" loanCode="${bview.loanCode}" id="newRetBtn">返回</button>
    </p>
    <h2 class=" pl10 f14 ">客户信息</h2>
    <!-- 门店拒绝弹出框 -->
		<div id="refuseMod" style="display:none">
			<form method="post" action="">
				<table class="table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr valign="top">
						<td><label >备注原因：</label>
							<textarea id="rejectReason" maxlength="450" cols="55" class="textarea_refuse" ></textarea>
						</td>
					</tr>
				</table>		
			</form>
		</div>
    <div class="box2 " style="clear:both">
        <form id="loanapplyForm" method="post">
          <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
          <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
         	 <input type="hidden" value="${bview.issplit}" name="contract.issplit"></input>
          <c:if test="${bview.issplit=='1'}">
         	 <input type="hidden" value="${bview.stepName}" name="stepName"></input>
          </c:if>
          <c:if test="${bview.issplit=='0'}">
         	 <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
          </c:if>
          <input type="hidden" value="${workItem.token}" id="token" name="token"></input>
          <input type="hidden" value="${workItem.flowId}" name="flowId"></input>
          <input type="hidden" value="${workItem.wobNum}" id="wobNum" name="wobNum"></input>
          <input type="hidden" value="${bview.applyId}" name="applyId"></input>
          <input type="hidden" id="applyId1" value="${bview.applyId}" name="contract.applyId"/>
          <input type="hidden" id="loanCode1" value="${bview.loanCode}" name="contract.loanCode"/>
          <input type="hidden" value="${bview.contractCode}" name="contract.contractCode"/>
          <input type="hidden" id="response" name="response"></input>
          <input type="hidden" value="${bview.paperlessFlag}" name="paperLessFlag"/>
          <input type="hidden" name="flowFlag" value=""/>
          <input type="hidden" id="redirectUrl1" name="redirectUrl" value="/newBorrow/borrowlist/fetchTaskItems"/>
          <input type="hidden" value="2" id="dictOperateResult" name="dictOperateResult"></input>
          <input type="hidden" name="signingPlatformName" id="signingPlatformName"/>
          <input type="hidden" name="loanBank.id" value="${bview.loanBank.id}"/>
          <input type="hidden" name="loanBank.bankSigningPlatform" id="bankSigningPlatformHid"/>
          <input type="hidden" name="appSignFlag" id="custAppSignH" value="${bview.appSignFlag}"/>
          <input type="hidden" name="oldLoanCode" value="${bview.oldLoanCode}"/>
          <input type="hidden" name="remarks" id="remark"/>
         </form>
          <input type="hidden" id="flagCancelParam" value="${workItem.bv.applyId},${workItem.wobNum},${workItem.flowName},${workItem.token},${workItem.stepName},${bview.loanCode}"/>
          <input type="hidden" value="<fmt:formatDate value="${bview.contractDueDay}" pattern="yyyy-MM-dd"/>" id="dueDay"/>
          <input type="hidden" value="<fmt:formatDate value="${bview.curDay}" pattern="yyyy-MM-dd"/>" id="curDay"/>
       <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户编号：</label>${bview.customerCode}</td>
                <td><label class="lab">客户姓名：</label>${bview.customerName}</td>
                <td><label class="lab">性别：</label>${bview.customerSexName}</td>
            </tr>
            <tr>
                <td><label class="lab">婚姻状况：</label>${bview.dictMarryName}</td>
                <td><label class="lab">证件类型：</label>${bview.dictCertTypeName}</td>
                <td><label class="lab">证件号码：</label>${bview.customerCertNum}</td>
            </tr>
            <tr>
                <td><label class="lab">学历：</label>${bview.dictEducationName}</td>
                <td><label class="lab">证件有效期：</label>
                 <c:if test="${bview.idStartDay!=null && bview.idEndDay!=null}">
                  <fmt:formatDate value="${bview.idStartDay}" pattern="yyyy-MM-dd"/>&nbsp;至&nbsp;
                  <fmt:formatDate value="${bview.idEndDay}" pattern="yyyy-MM-dd"/>
                 </c:if>
                 <c:if test="${bview.idStartDay!=null && bview.idEndDay==null}">
                                                       长期
                 </c:if>
                </td>
                 <td>
                  <%-- <label class="lab">出生日期：</label><fmt:formatDate value="${bview.customerBirthday}" pattern="yyyy-MM-dd"/> --%>
                 </td>
            </tr>
        </table>
        </div>
         <h2 class="pt5 pl10 f14 mt20">其它信息</h2>
         <div class="box2">
         <table>
           <tr>
             <td colspan="1"><label class="lab">签约平台：</label>
                <select id="bankSigningPlatform" class="select180">
                   <option value=''>请选择</option>
				   <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
				   	   <c:if test="${item.label!='中金' && item.value!='6' && item.value != '7'  && item.value != '4'}">
					    <option value="${item.value}"
					     <c:if test="${bview.loanBank.bankSigningPlatform==item.value}">
					       selected=true 
					     </c:if> 
					   	>${item.label}
					  	</option>
					 </c:if>
				   </c:forEach>
               </select>
               <span id="bankSigningPlatformMsg" style="color:red;"></span>
             </td>
             <td>
               <label class="lab">模式：</label>
               <span>${bview.modelName}</span>
             </td>
             <td>
               <label class="lab">渠道：</label>
               <span id="loanFlagText">${bview.loanFlag}</span>
             </td>
           </tr>
         </table>
    </div>
 <!--  <div class="tright mt20"><a href="#" class="more">更多>></a> </div> -->
 <c:if test="${bview.paperlessFlag=='1'}">
   <h2  class=" pl10 f14 mt20">签约信息</h2>
    <div class="box2">
       <table>
       <tr> <td>
                 <label class="lab">保证人：</label>
                  ${bview.legalMan}
             </td>
             <td>
                 <label class="lab">公司名称：</label>
                  ${bview.companyName}
             </td>
             <td><!--checked="checked"  -->
                <input type="checkbox" name="legalAppSign" disabled="disabled" id="legalAppSignCheck" value="1">APP签字</input>
                <input type="button" class="btn btn-small" disabled="disabled" id="legalSignBtn" value="签约"/>
             </td></tr>
          
          <tr style="height:20px"></tr>
            <tr style="height:140px">
            	<td><label class="lab">借款人：</label>${bview.customerName}</td>
            	 <td>
             	 	<div style="height:120px;width:200px;">
              	 	<div id="mainCurrentPhotoZone" class="img">
               		 <img
               	  		 <c:choose>
               	    		 <c:when test="${bview.currPlotId!=null && bview.currPlotId!=''}">
               	       		 src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${bview.currPlotId}" 
               	       		  updated='1'
               	    	 </c:when>
               	     	 <c:otherwise>
               	        	 src="${context}/static/images/image-1.png" 
               	    	 </c:otherwise>
               	 	   </c:choose>
               	    style="cursor: pointer;" class="img-rounded" 
               	   onclick="contractSign.openFileDialog('mainPlotPhoto','mainPlotPhotoA','mainBorrower','${bview.customerId}','0','0','${bview.oldLoanCode}','${bview.contractCode}');" id="mainPlotPhoto" z-index="1" alt="现场照片" height='100px' width='80px' />
               	  <a id="mainPlotPhotoA" 
               	   <c:choose>
               	     <c:when test="${bview.currPlotId!=null && bview.currPlotId!=''}">
               	        href="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${bview.currPlotId}" 
               	     </c:when>
               	     <c:otherwise>
               	        href="${context}/static/images/image-1.png" 
               	     </c:otherwise>
               	    </c:choose> 
               	      style="float:right" class="lightbox-2" rel="flowers" title="现场照片">
				    <i class="icon-search" style="cursor: pointer;"></i>
				 </a> 
               </div>
               <div id="mainCardPhotoZone" class="img">
                 <img 
                  <c:choose>
               	     <c:when test="${bview.idCardId!=null && bview.idCardId!=''}">
               	        src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${bview.idCardId}" 
               	         updated='1'
               	     </c:when>
               	     <c:otherwise>
               	        src="${context}/static/images/image-2.png" 
               	     </c:otherwise>
               	    </c:choose>
                    style="cursor: pointer;" class="img-rounded"  
               	   onclick="contractSign.openFileDialog('mainIdPhoto','mainIdPhotoA','','${bview.customerId}','1','0','${bview.oldLoanCode}','${bview.contractCode}');" id="mainIdPhoto" z-index="1" alt="身份证照片" height='100px' width='80px' />
               	  <a id="mainIdPhotoA"
               	   	<c:choose>
               	     	<c:when test="${bview.idCardId!=null && bview.idCardId!=''}">
               	      	  href="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${bview.idCardId}" 
               	    	 </c:when>
               	    	 <c:otherwise>
               	       	  href="${context}/static/images/image-2.png" 
               	    	 </c:otherwise>
               	    </c:choose>
               	      style="float:right" class="lightbox-2" rel="flowers" title="身份证照片">
				    <i class="icon-search" style="cursor: pointer;"></i>
				 </a> 
               </div>
              </div>
             </td>
              <td style="vertical-align:bottom;padding-bottom:20px">
               <input type="button" class="btn btn-small ml20" id="mainBorrower"
                onclick="contractSign.validIDCard('${bview.customerId}','0',this);"
                  <c:if test="${bview.currPlotId!=null && bview.currPlotId!=''}">
                    docId = ${bview.currPlotId}
                  </c:if>
                 validResult="${bview.idValidFlag}"  
                  <c:if test="${bview.idValidFlag=='1'||bview.idValidFlag=='2'}"> 
                        disabled=true 
                    </c:if> 
                 value="身份验证"></input></td>
             
             <td style="vertical-align:bottom;padding-bottom:23px">
             <div id="mainSignPhotoZone" class="ml20" style="width:80px;">
                 <!-- <span id="signPhoto" >请签字</span>  --> 
             </div>
             </td>
           </tr>
          
           <c:forEach items="${bview.coborrowerList}" var="cobo" varStatus="vstatus">
               <tr style="height:140px">
                <td><label class="lab">
                <c:if test="${bview.loanInfoOldOrNewFlag=='1'}"> 
                	自然人保证人：
                </c:if>
                 <c:if test="${bview.loanInfoOldOrNewFlag!='1'}">
                 	共借人： 
                </c:if>
                </label>${cobo.coboName}</td>
                <td>
             	  <div style="height:120px;width:200px;">
              	    <div id="coboCurrentPhotoZone${vstatus}" class="img">
                	 	<img 
                	 	   <c:choose>
               	    		 <c:when test="${cobo.curPlotId!=null && cobo.curPlotId!=''}">
               	        		src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${cobo.curPlotId}" 
               	        		 updated='1'
               	    		 </c:when>
               	   			 <c:otherwise>
               	        		src="${context}/static/images/image-1.png" 
               	    		 </c:otherwise>
               			  </c:choose>
                	 	   style="cursor: pointer;" class="img-rounded"  
               	   			onclick="contractSign.openFileDialog('plotPhoto${vstatus.index}','plotPhotoA${vstatus.index}','coboIDValidBtn${vstatus.index}','${cobo.id}','0','1','${bview.oldLoanCode}','${bview.contractCode}');" id="plotPhoto${vstatus.index}" z-index="1" alt="现场照片" height='100px' width='80px' />
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
               	    </div>
               		<div id="coboCardPhotoZone${vstatus}" class="img">
                 		 <img 
                 		    <c:choose>
               	     			<c:when test="${cobo.idCardId!=null && cobo.idCardId!=''}">
               	      			  src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${cobo.idCardId}" 
               	      			   updated='1'
               	    	 		</c:when>
               	    		 <c:otherwise>
               	       			  src="${context}/static/images/image-2.png" 
               	    		 </c:otherwise>
               	   		   </c:choose>
                   	         style="cursor: pointer;" class="img-rounded"  
               	  			 onclick="contractSign.openFileDialog('IdPhoto${vstatus.index}','IdPhotoA${vstatus.index}','','${cobo.id}','1','1','${bview.oldLoanCode}','${bview.contractCode}');" id="IdPhoto${vstatus.index}" z-index="1" alt="身份证照片" height='100px' width='80px' />
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
              		</div>
              	   </div>
             </td>
             <td style="vertical-align:bottom;padding-bottom:20px">
             <input type="button" id="coboIDValidBtn${vstatus.index}"
                  <c:if test="${cobo.curPlotId!=null && cobo.curPlotId!=''}">
                     docId=${cobo.curPlotId}
                  </c:if>
                  validResult="${cobo.idValidFlag}" 
                    <c:if test="${cobo.idValidFlag=='1'||cobo.idValidFlag=='2'}">
                        disabled=true 
                    </c:if> 
                   onclick="contractSign.validIDCard('${cobo.id}','1',this);" class="btn btn-small ml20" value="身份验证"/>
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
                 <c:if test="${bview.appSignFlag!='0'}">
                    checked="checked" 
                 </c:if>
                 name="custAppSign" id="custAppSignCheck" value="1"/>APP签字</label>
                   <input type="button" class="btn btn-small" disabled="disabled" id="signBtn" value="签约"/>
               </td>
           </tr>
       </table>
    </div>
    </c:if>
</div> 
    <c:forEach items="${workItem.bv.files}" var="file" varStatus="status">
    	 <c:if test="${file.docId!=null && file.docId!='' }">
	   		 <div class="tab-pane fade in" id="${file.id}" style="">
	     		<iframe height="500px" width="100%" margin-left="50%" scrolling="auto" src="${ctx}/apply/contractAudit/contractFilePreviewShow?docId=${file.docId}"></iframe>
	   		 </div>
   		 </c:if>
 	 </c:forEach>  
</div>
</div> 
</body>
</html>