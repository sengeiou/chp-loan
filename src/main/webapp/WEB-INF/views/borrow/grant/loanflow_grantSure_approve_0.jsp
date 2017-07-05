<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script src="${context}/js/grant/grantSureDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
</head>
<body>

   
	<div class="title mt5">
	 <table class="table table-striped table-bordered table-condensed" >
      <tr>
	   <td>客户名称：
	  ${workItem.bv.customerName}</td>
       <td>合同编号：
       ${workItem.bv.contractCode}</td>
	   <td>证件号码：
	  ${workItem.bv.customerCertNum}</td>
	   <input type="hidden" value="${workItem.bv.loanFlag}" name="flag">
	   <input type="hidden" value="${workItem.bv.grantBackMes}" name="backReason">
	 </tr>
	 </table>
	</div>
    <div class="title">
		<div class="pt5 f14 pl10" style="float:left">合同相关文件</div>
		<div style="float:right"class="mb5 mt5">
		<form id="param">
		<input type="hidden"  name="applyId" value='${workItem.bv.applyId}'></input>
		<input type="hidden"  name="wobNum" value='${workItem.wobNum}'></input>
		<input type="hidden"  name="flowName" value='${workItem.flowName}'></input>
		<input type="hidden"  name="token" value='${workItem.token}'></input>
		<input type="hidden"  name="stepName" value='${workItem.stepName}'></input>
		</form>
		<input type="hidden" id="urgentFlag" value="${workItem.bv.urgentFlag}"/>
		<input type="hidden" id="flowParam" contractCode='${workItem.bv.contractCode}'></input>
        <input  class="btn btn-small" id="grantSureBtn" type="button" value="放款确认" />  
        <input type="button" class="btn btn-small" id="addP2PBtn" value="P2P"></input>
        <input type="button" class="btn btn-small" onclick="showLoanHis('${workItem.bv.applyId}')"  value="历史"></input> 
        <button  id="grantBackBtn" role="button" class="btn btn-small"  data-toggle="modal">退回</button> 
    </div>
    </div>
    <div style="height:100%">
        <ul class="ul01" style="height:100%">
            <li class="li01"><a href="#" name="lis">借款协议</a></li>
        	<li class="li01"><a href="#" name="lis">委托和授权书</a></li>
        	<li class="li01"><a href="#" name="lis">信用咨询及管理服务协议</a></li>
        	<li class="li01"><a href="#" name="lis">信合还款事项客户通知书</a></li>
        	<li class="li01"><a href="#" name="lis">还款管理服务说明确认书</a></li>
        </ul>
    </div>
	<div class="btn_next">
	<button class="btn btn-primary"  style="float:right" id="goBackBtn">返回</button>
	</div>
    
    
     <div class='modal fade'  id="sureBack" style="width:90%;height:90%">
       <div class="modal-dialog" role="document">
	       <div class="modal-content">
           <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="grantSureBack">待放款确认退回</h4>
		    </div>
       <div class="modal-body">
			<table class="table table-striped table-bordered table-condensed">
                <tr>
                    <td align="left"><label>退回原因</label></td>
                    <td align="left">
                      <select id="sel">
                        <c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
                        <c:if test="${card_type.label=='客户主动放弃'||card_type.label=='风险客户'||card_type.label=='其他'}">
							<option value="${card_type.value}">${card_type.label}</option>
						</c:if>
				       </c:forEach>
                      </select>
                    </td>
                </tr>
                <tr id="other" style="display:none">
                    <td align="left"><label>其他退回原因</label></td>
                    <td align="left"><textarea  rows="" cols="" style='font-family:"Microsoft YaHei";' >请填写其他退回原因！</textarea></td>
                </tr>
            </table>
		</div>
            <div class="modal-footer"><button class="btn btn-primary" id="grantSureBackBtn">确认</button>
            <button class="btn btn-primary" class="close" data-dismiss="modal" onclick="closeModal('sureBack')">取消</button></div>
		</div>
		</div>
		</div>
</body>
</html>