<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>预约划扣列表</title>
<script type="text/javascript" src="${context}/js/payback/deductsdue.js"></script>
<script type="text/javascript">
var con = '${context }';
</script>
</head>
<body>
 <div class="control-group">
 <form method="post" id="centerapplyPayForm" name="centerapplyPayForm">
    <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="id" name="id" type="hidden" />
            <tr>
              <%-- <td><label class="lab">预约银行：</label>
	                <sys:bankForMulPage fbankClick="selectBankBtn" fbankName="search_applyBankName" fbankId="bankId"></sys:bankForMulPage>
                       <input id="search_applyBankName" type="text" class="input_text178" name="dueBank"  value='${paybackDeductsDue.dueBank}' readonly>&nbsp;<i id="selectBankBtn"
						  class="icon-search" style="cursor: pointer;"></i> 
					    <input type="hidden" id="bankId" name='bank' value='${paybackDeductsDue.bank}'>
                </td> --%>
                
                 <td><label class="lab">方式：</label>
                 <select name="modeWay" id="modeWay" class="select180">
	               <option value=''>请选择</option>
                   <option value="1" <c:if test="${paybackDeductsDue.modeWay == '1' }">selected</c:if>>
                                                          委托充值
                   </option>
                   <option value="2" <c:if test="${paybackDeductsDue.modeWay == '2' }">selected</c:if>>
                                                         划扣
                   </option>
	              </select>
                </td>
                <td><label class="lab">预约时间：</label>
                    <input name="dueTimeStr"  type="text" readonly="readonly" maxlength="40" class="input-medium Wdate"
                     onfocus="WdatePicker({minDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					value="${paybackDeductsDue.dueTimeStr}" 
					 onclick="WdatePicker({minDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'});"
					  />
                </td>
                <td><label class="lab">是否有效：</label>
                 <select name="effectiveFlag" id="effectiveFlag" class="select180">
	               <option value=''>请选择</option>
				     <c:forEach items="${fns:getDictList('yes_no')}" var="effectiveFlag">
                   <option value="${effectiveFlag.value }" <c:if test="${paybackDeductsDue.effectiveFlag == effectiveFlag.value }">selected</c:if>>
                      ${effectiveFlag.label }
                   </option>
                    </c:forEach>
	              </select>
                </td>
                
            </tr>
        </table>
        <div class="tright pr30 pb5">
        <button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
        <button class="btn btn-primary" id="reset">清除</button></div>
  </form>
    </div>
    <p class="mb5"> 
    <button id="addDue" role="button" class="btn btn-small" data-target="#addDueDiv" data-toggle="modal" onclick="emp();">新增预约</button>
	<button class="btn btn-small" id="toUnUse">置为无效</button>
    <button class="btn btn-small" id="toUse">置为有效</button></p>
   <div class="box5"> 
        <table class="table  table-bordered table-condensed table-hover">
          <thead>
            <tr>
                <th><input type="checkbox" class="checkbox" id="checkAll"/></th>
                <th>序号</th>
                <th>方式</th>
                <th>预约时间</th>
                <th>创建日期</th>
                <th>是否有效</th>
                <th>操作人</th>
            </tr>
            </thead>
        <c:if test="${ waitPage.list!=null && fn:length(waitPage.list)>0}">
         <c:forEach items="${waitPage.list }" var="packbackdeductsdue" varStatus="pac">
					<tr>
						<td><input type="checkbox" name="checkBox"
							value="${packbackdeductsdue.id }" /></td>
						<td>${pac.count }</td>
						<td>
						<c:if test="${packbackdeductsdue.modeWay == '1'}">
						 委托充值
						</c:if>
						<c:if test="${packbackdeductsdue.modeWay == '2'}">
						划扣
						</c:if>
						</td>
						<td><fmt:formatDate value="${packbackdeductsdue.dueTime}"
								type="both" /></td>

						<td><fmt:formatDate value="${packbackdeductsdue.createTime}"
								type="both" /></td>


						<td><c:if test="${packbackdeductsdue.effectiveFlag =='1' }">有效</c:if>
							<c:if test="${packbackdeductsdue.effectiveFlag =='0'}">无效</c:if>
						</td>


						<td>${ packbackdeductsdue.createBy}</td>
					</tr>
				</c:forEach>
		</c:if>
        <c:if test="${ waitPage.list==null || fn:length(waitPage.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
        </c:if>
      </table>
    </div>
   	<div class="pagination">${waitPage}</div>
</div>

<!-- 新增预约弹框 -->
<div class="modal fade"  id="addDueDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
             <h3 class="pop_title">新增预约</h3>
          </div>
        <div class="box1 mb10">
            <form action="${ctx }/borrow/payback/deductsDue/addDue" method="post">
            <table id="backTB" class="table4" cellpadding="0" cellspacing="0" border="0" width="100%"> 
              <%--    <tr>
                    <td><label class="lab">预约银行：</label> 
                      <select name="bankIdA" class="select180" id ="bankIdA">
					    <option value=''>请选择</option>
						  <c:forEach items="${fns:getDictList('jk_open_bank')}" var="applyBankName">
	                         <option value="${applyBankName.value }" <c:if test="${paybackDeductsDue.dueBank == applyBankName.value }">selected</c:if>>${applyBankName.label }</option>
	                      </c:forEach>
	                    </select>
	                    
	                    <sys:multipleBank bankClick="bankBtn" bankName="bankName" bankId="bankIdA"></sys:multipleBank>
                    <input id="bankName" type="text" class="input_text178" name="bankName" readonly>&nbsp;<i id="bankBtn"
						class="icon-search" style="cursor: pointer;"></i> 
					<input type="hidden" id="bankIdA" name='bankIdA' >
                    </td>
                </tr> --%>
                <tr>
                   <td><label class="lab">方式：</label> 
                   <input type ='radio' value='1' name='modeWay'>委托充值
                   <input type ='radio' value='2' name='modeWay'>划扣
                   </td>
                </tr>
                 <tr>
                    <td><label class="lab">选择预约日期：</label>
                        <label><input id="appointmentDate" type="text" readonly="readonly" maxlength="80" class="input_text178 Wdate"
							value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>"
							 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
							 onfocus="WdatePicker({minDate:'%y-%M-{%d}'})"
							 />
						</label>
						  
                   </td>
                </tr>
                <%-- <tr>
                   <td><label class="lab">预约时间：</label>
		                 <label><input name="appointmentTime" id='appointmentDate'  type="text"  maxlength="40" 
		                    value="<fmt:formatDate value="${log.beginDate}" pattern="HH:mm"/>" onChange="selectIsAppointment(this.value,'0')"
							onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm',disabledDates:['..:[1,2,4,5].','..:.[5]']})" class=" input_text178 Wdate" readonly="readonly"
							/>
						 </label>
						 <img alt="" src="${context }/static/images/u207.png" onclick="addRow();">
						 <font color="red"><span id="span0"></span></font>
							<!-- <input type="button" onclick="deleteRow()" style="background:url(${context }/static/images/jianhao.png);background-size:100%">-->
                   </td>
               </tr> --%>
            </table>
            </form>
       </div>
     </div> 
       <div class="modal-footer">
	       <button class="btn btn-primary" onclick="deductsDueSubmit();">确认</button>
	       <a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
       </div>
       </div>
    
    </div>

</body>
</html>