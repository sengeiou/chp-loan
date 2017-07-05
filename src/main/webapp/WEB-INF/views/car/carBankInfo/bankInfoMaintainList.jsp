<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript" src="${context}/js/car/carBankInfo/carBankInfo.js"></script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
		<form id="accountApplyForm" action="${ctx}/car/carBankInfo/carCustomerBankInfo/accountMaintainList" method="post">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">合同编号：</label><input type="text" class="input_text178" style="width: 270px;" name="contractCode" value="${info.contractCode}"/></td>
					<td><label class="lab">客户姓名：</label><input type="text" class="input_text178" name="customerName" value="${info.customerName}"/></td>
					<td><label class="lab">身份证号码：</label><input type="text" class="input_text178" name="customerCertNum" value="${info.customerCertNum}"/></td>
				</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" onclick="resets()" value="清除">
		</div>
	</div>
	<div class="box5">
		<table class="table  table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th>操作日期</th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>账号</th>
					<th>开户行</th>
					<th>附件</th>
					<th>维护状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${bankInfoList!=null && fn:length(bankInfoList.list)>0}">
         		<c:set var="index" value="0"/>
					<c:forEach items="${bankInfoList.list}" var="item">
         			<c:set var="index" value="${index+1}" />
						<tr>
							<td>
								<fmt:formatDate value="${item.modifyTime}" type="date" pattern="yyyy-MM-dd"/>
							</td>
							<td>${item.contractCode}</td>
							<td>${item.customerName}</td>
							<td>${item.bankCardNo}</td>
							<td>${item.cardBank}</td>
							<td>${item.fileName}</td>
							<td>${item.dictMaintainStatusName}</td>
							<td>
								<c:if test="${info.dictMaintainStatus eq '2'}">
									<c:if test="${item.dictMaintainStatus eq '2' or item.dictMaintainStatus eq null}">
										<button class="btn btn_edit" onclick="newAccount('${item.id}','${item.loanCode}','0','${item.coboFlag}')">新建</button>
										<button class="btn btn_edit" onclick="editPhone('${item.id}','${item.coboFlag}')">修改还款手机号</button>
										<button class="btn btn_edit" onclick="editAccount('${item.id}','${item.coboFlag}')">修改还款账号</button>
										<button class="btn btn_edit" onclick="editEmail('${item.id}','${item.coboFlag}')">修改邮箱</button>
									</c:if>
									<c:if test="${item.dictMaintainStatus eq '1'}">
										<c:if test="${item.dictMaintainType eq '0'}">
											<button class="btn btn_edit" onclick="update('${item.id}','${item.loanCode}','${item.dictMaintainType}','${item.updateType }','${item.coboFlag }')">修改</button>
										</c:if>
										<c:if test="${item.dictMaintainType ne '0'}">
											<button class="btn btn_edit" onclick="update('${item.oldBankAccountId}','${item.loanCode}','${item.dictMaintainType}','${item.updateType }','${item.coboFlag }')">修改</button>
										</c:if>
									</c:if>
								</c:if>
								<c:if test="${item.dictMaintainStatus eq '2' and item.top eq '0'}">
									<button class="btn btn_edit" onclick="setTop('${item.id}','${item.loanCode}')">置顶</button>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if
					test="${ bankInfoList==null || fn:length(bankInfoList.list)==0}">
					<tr>
						<td colspan="19" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="pagination">${bankInfoList}</div> 
	
	<!-- 新建div -->
	<div id="addDiv" style="display:none;margin-top: 0px;margin-bottom: 0px;margin-left: 0px;margin-right: 0px;">
		<form id="addAccountMessage" class="validate" action="${ctx}/car/carBankInfo/carCustomerBankInfo/saveAccountMessage" method="post" enctype="multipart/form-data">
			<input type="hidden" id="loanCode" name="loanCode"/>
			<input type="hidden" id="addCoboId" name="coboId"/>
			<input type="hidden" id="newOldBankAccountId" name="oldBankAccountId"/>
			<table class="table  table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="addCustomerName" name="customerName" readonly="readonly"/></td>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="addContractCode" name="contractCode"  style="width: 220px;" readonly="readonly"/></td>
						<td><label class="lab">客户身份证号：</label></td>
						<td><input type="text" class="input-medium" id="addCustomerCard" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">账号姓名：</label></td>
						<td>
							<input type="text" class="input-medium" id="phoneBankAccountName" name="bankAccountName"  readonly="readonly"/>
							<input type="checkbox" onclick="selectIsrare();" id="israre" name="israre">
							是否生僻字</input>
						</td>
						<td><label class="lab">客户手机号：</label></td>
						<td><input type="text" class="input-medium" id="addCustomerPhone" name="customerPhone" readonly="readonly"/></td>
						<td><label class="lab">合同版本号：</label></td>
						<td><input type="text" class="input-medium" id="addContractVersion" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">省份：</label></td>
						<td>
							<select class="select180" id="provinceId" name="bankProvince">
								<option value="">请选择</option>
								<c:forEach items="${provinceList}" var="province">
									<option value="${province.areaCode}">${province.areaName}</option>
								</c:forEach>
							</select>
						</td>
						<td><label class="lab">城市：</label></td>
						<td>
							<select class="select180" id="cityId" name="bankProvinceCity">
							</select>
						</td>
						<td><label class="lab">划扣平台：</label></td>
						<td>
							<select class="select180" id="phoneBankSigningPlatform" name="bankSigningPlatform">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="plat">
									<c:if test="${plat.label != '中金'}">
										<option value="${plat.value}">${plat.label}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>	
					</tr>
					<tr>
						<td><label class="lab">划扣账号：</label></td>
						<td><input type="text" class="input-medium" id="phoneBankCardNo"  name="bankCardNo"/></td>
						<td><label class="lab">开户行名称：</label></td>
						<td>
							<select class="select180" id="phoneCardBank" name="cardBank">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_open_bank')}" var="bank">
									<option value="${bank.value}">${bank.label}</option>
								</c:forEach>
							</select>
						</td>
						<td><label class="lab">开户行支行：</label></td>
						<td><input type="text" class="input-medium" id="phoneApplyBankName" name="applyBankName" maxlength="25"/>
							<label><font color="red">限定输入25个文字</font></label>
						</td>
					</tr>
					<tr id="coboTitle">
						<td colspan="6"><label class="lab"><font color="red">共借人信息</font></label></td>
					</tr>
					<tr id="coboMsg">
						<td><label class="lab">共借人姓名：</label></td>
						<td><input type="text" class="input-medium" id="addCoboName" name="coboName" readonly="readonly"/></td>
						<td><label class="lab">共借人身份证号：</label></td>
						<td><input type="text" class="input-medium" id="addCoboCertNum" name="coboCertNum" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">上传附件：</label></td>
						<td><input type="file" class="input-medium" name="file" onchange="fileChange()"/></td>
						<td><label class="lab">下载附件：</label></td>
						<td>
							<input id="cj1add" class="jx btn_edit" value="借款人客户信息变更申请书 " onclick="download('7');"> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 修改账号div -->
	<div id="editAccountDiv" style="display:none;margin-top: 0px;margin-bottom: 0px;margin-left: 0px;margin-right: 0px;">
		<form id="editAccountMessage" class="validate" action="${ctx}/car/carBankInfo/carCustomerBankInfo/updateBankInfoData" method="post" enctype="multipart/form-data">
			<input type="hidden" id="editCoboId" name="coboId"/>
			<input type="hidden" name="updateType" id="updateType" value="2"/>
			<input type="hidden" id="oldBankAccountId" name="oldBankAccountId"/>
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="editCustomerName" readonly="readonly"/></td>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="editContractCode"  style="width: 220px;" readonly="readonly"/></td>
						<td><label class="lab">客户身份证号：</label></td>
						<td><input type="text" class="input-medium" id="editCustomerCard" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">账号姓名：</label></td>
						<td><input type="text" class="input-medium" id="accountName" readonly="readonly"/></td>
						<td><label class="lab">客户手机号：</label></td>
						<td><input type="text" class="input-medium" id="editCustomerPhone" readonly="readonly"/></td>
						<td><label class="lab">合同版本号：</label></td>
						<td><input type="text" class="input-medium" id="editContractVersion" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">省份：</label></td>
						<td><input type="text" class="input-medium" id="provinceName" readonly="readonly"/></td>
						<td><label class="lab">城市：</label></td>
						<td><input type="text" class="input-medium" id="cityName" readonly="readonly"/></td>
						<td><label class="lab">划扣平台：</label></td>
						<td>
					 	<select id="deductPlatName" onchange="setDeductPlat(this.value);" name="bankSigningPlatform" class="select180">
                   		  <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
                    		  <c:if test="${item.label!='中金' && item.value!='6'}">
					  		  <option value="${item.value}"
					    		>${item.label}
					   		 </option>
					    	</c:if>
					     </c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td><label class="lab">划扣账号：</label></td>
						<td>
							<input type="text" class="input-medium isModify" id="account" name="bankCardNo"/>
							<input type="hidden" id="isModify"/>
						</td>
						<td><label class="lab">开户行名称：</label></td>
						<td><input type="text" class="input-medium" id="editOpenBankName" readonly="readonly"/></td>
						<td><label class="lab">开户行支行：</label></td>
						<td><input type="text" class="input-medium" id="bankBranch" name="applyBankName" maxlength="25"/>
							<label><font color="red">限定输入25个文字</font></label>
						</td>
					</tr>
					<tr id="editCoboTitle">
						<td colspan="6"><label class="lab"><font color="red">共借人信息</font></label></td>
					</tr>
					<tr id="editCoboMsg">
						<td><label class="lab">共借人姓名：</label></td>
						<td><input type="text" class="input-medium" id="editCoboName" name="coboName" readonly="readonly"/></td>
						<td><label class="lab">共借人身份证号：</label></td>
						<td><input type="text" class="input-medium" id="editCoboCertNum" name="coboCertNum" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">上传附件：</label></td>
						<td><input type="file" class="input-medium" name="file" onchange="fileChange()"/></td>
						<td><label class="lab">下载附件：</label></td>
						<td>
							<input id="cj1" class="jx btn_edit" value="借款人客户信息变更申请书 " onclick="download('7');"> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 修改手机号div -->
	<div id="editPhoneDiv" class=" pb5 pt10 pr30 title ohide" style="display:none;padding: 0px 0px;" >
		<form id="editPhoneMessage" class="validate" action="${ctx}/car/carBankInfo/carCustomerBankInfo/updateBankInfoData" method="post" enctype="multipart/form-data">
			<input type="hidden" id="phoneCoboId" name="coboId"/>
			<input type="hidden" id="phoneBankAccountId" name="oldBankAccountId"/>
			<input type="hidden" id="customerPhoneFirst" name="customerPhoneFirst"/>
			<input type="hidden" name="updateType" id="updateType" value="1"/>
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="phoneCustomerName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="phoneContractCode" style="width: 270px;" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">客户身份证号：</label></td>
						<td><input type="text" class="input-medium" id="phoneCustomerCard" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">客户手机号：</label></td>
						<td><input type="text" class="input-medium isPhoneModify" id="phoneCustomerPhone" name="newCustomerPhone" maxlength="11"/>
<!-- 						<td><input type="text" class="input-medium" id="phoneCustomerPhone" name="newCustomerPhone" maxlength="11"/> -->
<!-- 							<input type="hidden" id="isPhoneModify"/> -->
							<label><font color="red">手机号码为11个数字</font></label>
						</td>
					</tr>
					<tr id="pCoboTitle">
						<td colspan="2"><label class="lab"><font color="red">共借人信息</font></label></td>
					</tr>
					<tr id="pCoboName">
						<td><label class="lab">共借人姓名：</label></td>
						<td><input type="text" class="input-medium" id="phoneCoboName" name="coboName" readonly="readonly"/></td>
					</tr>
					<tr id="pCoboCertNum">
						<td><label class="lab">共借人身份证号：</label></td>
						<td><input type="text" class="input-medium" id="phoneCoboCertNum" name="coboCertNum" readonly="readonly"/></td>
					</tr>
					<tr id="pCoboMobile">
						<td><label class="lab">共借人手机号码：</label></td>
						<td>
							<input type="text" class="input-medium" id="phoneCoboMobile" name="coboMobile"/>
						</td>
					</tr>
					<tr>
						<td><label class="lab">上传附件：</label></td>
						<td><input type="file" class="input-medium" name="file" onchange="fileChange()"/></td>
					</tr>
					<tr>
					<td><label class="lab">下载附件：</label></td>
						<td>
							<input id="cj2" class="jx btn_edit" value="借款人客户信息变更申请书 " onclick="download('6');"> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 修改邮箱div -->
	<div id="editEmailDiv" style="display:none;margin-top: 0px;margin-bottom: 0px;margin-left: 0px;margin-right: 0px;">
		<form id="editEmailMessage" class="validate" action="${ctx}/car/carBankInfo/carCustomerBankInfo/updateBankInfoData" method="post" enctype="multipart/form-data">
			<input type="hidden" id="emailCoboId" name="coboId"/>
			<input type="hidden" id="emailOldBankAccountId" name="oldBankAccountId"/>
			<input type="hidden" name="updateType" id="updateType" value="3"/>
			<input type="hidden" name="customerEmail" id="emailCustomerEmail" value="3"/>
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="mailCustomerName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="mailContractCode" style="width: 270px;" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">客户身份证号：</label></td>
						<td><input type="text" class="input-medium" id="mailCustomerCard" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">邮箱地址：</label></td>
						<td>
							<input type="text" class="input-medium" id="mailCustomerEmail" name="newEmail"/>
<!-- 							<input type="text" class="input-medium isEmailModify" id="mailCustomerEmail" name="newEmail"/> -->
<!-- 							<input type="hidden" id="isEmailModify"/> -->
						</td>
					</tr>
					<tr id="emailCoboTitle">
						<td colspan="2"><label class="lab"><font color="red">共借人信息</font></label></td>
					</tr>
					<tr id="eCoboName">
						<td><label class="lab">共借人姓名：</label></td>
						<td><input type="text" class="input-medium" id="emailCoboName" name="coboName" readonly="readonly"/></td>
					</tr>
					<tr id="eCoboCertNum">
						<td><label class="lab">共借人身份证号：</label></td>
						<td><input type="text" class="input-medium" id="emailCoboCertNum" name="coboCertNum" readonly="readonly"/></td>
					</tr>
					<tr id="eCoboEmail">
						<td><label class="lab">共借人邮箱：</label></td>
						<td>
							<input type="text" class="input-medium" id="emailCoboEmail" name="coboEmail"/>
						</td>
					</tr>
					<tr>
						<td><label class="lab">上传附件：</label></td>
						<td><input type="file" class="input-medium" name="file" onchange="fileChange()"/></td>
					</tr>
					<tr>
					<td><label class="lab">下载附件：</label></td>
						<td>
							<input id="cj3" class="jx btn_edit" value="借款人客户信息变更申请书 " onclick="download('6');"> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>