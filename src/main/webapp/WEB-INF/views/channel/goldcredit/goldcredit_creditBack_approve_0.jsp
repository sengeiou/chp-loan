<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script src="${context}/js/channel/goldcredit/creditBackDeal.js" type="text/javascript"></script>
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
	 </tr>
	 </table>
	</div>
    <div class="title">
		<div class="pt5 f14 pl10" style="float:left">合同相关文件</div>
		<div style="float:right"class="mb5 mt5">
		<input type = "hidden" name = "imageUrl" value = "${imageUrl}"/>
		<form id="param">
		<input type="hidden"  name="applyId" value='${workItem.bv.applyId}'></input>
		<input type="hidden"  name="wobNum" value='${workItem.wobNum}'></input>
		<input type="hidden"  name="flowName" value='${workItem.flowName}'></input>
		<input type="hidden"  name="token" value='${workItem.token}'></input>
		<input type="hidden"  name="stepName" value='${workItem.stepName}'></input>
		<input type="hidden"  name="loanCode" value='${workItem.bv.loanCode}'></input>
		</form>
		<input type="hidden" id="flowParam" contractCode='${workItem.bv.contractCode}'></input>
       
        <input type="button" class="btn btn-small" onclick="showLoanHis('${workItem.bv.applyId}')"  value="历史"/> 
        <input type="button" id="grantBackBtn" class="btn btn-small grantBackBtn"  data-toggle="modal" value = "退回"/> 
    </div>
    </div>
    <div style="height:100%">
        <ul class="ul01" style="height:100%">
            <li class="li01"><a href="javascript:void(0);" name="lis">借款协议</a></li>
        	<li class="li01"><a href="javascript:void(0);" name="lis">委托和授权书</a></li>
        	<li class="li01"><a href="javascript:void(0);" name="lis">信用咨询及管理服务协议</a></li>
        	<li class="li01"><a href="javascript:void(0);" name="lis">信合还款事项客户通知书</a></li>
        	<li class="li01"><a href="javascript:void(0);" name="lis">还款管理服务说明确认书</a></li>
        </ul>
    </div>
	<div class="btn_next">
	<button class="btn btn-primary"  style="float:right" onclick="history.go(-1);">返回</button>
	</div>
    
    
     <div class='modal fade' id="sureBack" style="width:90%;height:90%">
       <div class="modal-dialog" role="document">
	       <div class="modal-content">
           <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h3 class="modal-title" id="grantSureBack">金信退回</h3>
		    </div>
       <div class="modal-body">
			<table class="table table-striped table-bordered table-condensed">
                <tr>
                    <td align="left"><label>退回原因</label></td>
                    <td align="left">
                      <select id="sel" class="select180">
                        <option>风险客户</option>
                        <option>客户原因放弃</option>
                        <option>其他</option>
                      </select>
                    </td>
                </tr>
                <tr id="other" style="display:none">
                    <td align="left"><label>其他退回原因</label>
                    <span class = "red">*</span><textarea id = "textarea"  rows="" cols="" style='font-family:"Microsoft YaHei";' ></textarea></td>
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