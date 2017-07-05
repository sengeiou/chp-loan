<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src='${context}/js/bootstrap.autocomplete.js' type="text/javascript"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" language="javascript" src='${context}/js/reconsider/reconsider.js?version=1'></script>
<script type="text/javascript">
  LAUNCHURL='${ctx}/bpm/flowController/openLaunchForm?flowCode=reconsiderApply';
  $(document).ready(function(){
	$('#showMore').bind('click',function(){
		show();
	});  
	$('#clrBtn').bind('click',function(){
		loan.queryFormClear('reconsiderForm');
		$('#teamManagerCode').val('');
		$('#customerManagerCode').val('');
	});
	// 搜索绑定
	$('#searchBtn').bind('click', function() {
		var teleFlag='${teleFlag}';
		if(teleFlag=='0'){
			window.location = ctx + "/apply/reconsiderApply/queryReconsiderList?" + $('#reconsiderForm').serialize();
		}else if(teleFlag=='1'){
			window.location = ctx + "/apply/reconsiderApply/queryTelesalesReconsiderList?" + $('#reconsiderForm').serialize();
		}
	});
		$("input[name='dealReconsiderBtn']").each(function() {
			$(this).bind('click', function() {
				consider.openform(LAUNCHURL, $(this).attr("applyId"),'${teleFlag}');
			});
		});
		consider.autoMatch("teamManagerName", "teamManagerCode");
		consider.autoMatch("customerManagerName", "customerManagerCode");
	});
</script>
</head>
<body>
<div>
    <div class="control-group">
      <form id="reconsiderForm">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                 <td><label class="lab">客户姓名：</label>
                     <input name="customerName" value="${param.customerName}" type="text" class="input_text178">
                 </td>
				 <td>
				    <label class="lab">身份证号：</label>
				    <input type="text" name="identityCode" value="${param.identityCode}" class="input_text178">
				 </td>
				 <td>
				    <label class="lab">合同编号：</label>
				    <input type="text" name="contractCode" value="${param.contractCode}" class="input_text178">
				 </td>
            </tr>
            <tr>
                <td>
                  <label class="lab">团队经理：</label>
                   <input type="text" id="teamManagerName" name="teamManagerName" value="${param.teamManagerName}" class="input_text178"/>
                   <input type="hidden" id="teamManagerCode" name="teamManagerCode" value="${param.teamManagerCode}"/>
                </td>
                <td><label class="lab">客户经理：</label>
                    <input type="text" id="customerManagerName" name="customerManagerName" value="${param.customerManagerName}" class="input_text178"/>
                    <input type="hidden" id="customerManagerCode" name="customerManagerCode" value="${param.customerManagerCode}"/>
                </td>
				<td><label class="lab">是否加急：</label>
				  <c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="urgentFlag">
						   <input type="radio" class="ml10 mr6" value="${urgentFlag.value}" name='urgentFlag'
						     <c:if test="${urgentFlag.value==param.urgentFlag}">
						       checked=true
						     </c:if>
						    />
					 ${urgentFlag.label}
				  </c:forEach>
			    </td>
            </tr>
            <tr id="T1" style="display:none">
              <td><label class="lab">模式：</label>
						<select id="model" name="model" class="select180">
                          <option value="">全部</option>
                          <c:forEach items="${fns:getDictList('jk_loan_model')}" var="item">
                         	  <option value="${item.value}"
					      		 <c:if test="${item.value==param.model}">
					      		   selected=true
					      		 </c:if>
					      		>${item.label}</option>
					       </c:forEach>
				        </select>	
					</td>
                <td><label class="lab">渠道：</label>
                     <select name="channelCode" class="select180">
					   <option value=''>请选择</option>
					   <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="mark">
						 <option value="${mark.value}"
						 <c:if test="${param.channelCode==mark.value}">
						    selected=true
						 </c:if>
						>${mark.label}
						</option>
					   </c:forEach>
					</select>
                </td>
            	<td><label class="lab">产品：</label>
	    			<select name="replyProductCode" value="${param.replyProductCode}" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${productList}" var="product">
									<option value="${product.productCode}"
									  <c:if test="${param.replyProductCode==product.productCode}">
									    selected=true
									  </c:if>
									>${product.productName}</option>
								</c:forEach>
     				</select>
			    </td>
            </tr>
            <tr id="T2" style="display:none"> 
               <td>
                  <label class="lab">借款状态：</label>
	    			<select name="queryDictStatus" class="select180">
						<option value=''>全部</option>
						<c:forEach items="${queryStatusList}" var="item">
						 <option value="${item.code}"
						 <c:if test="${queryCode==item.code}">
							selected=true
						 </c:if>>${item.name}
						</option>
						</c:forEach>
     				</select>
               </td>
               <!-- 
               <td><label class="lab">是否追加借：</label>
				   <c:forEach items="${fns:getDictList('yes_no')}" var="item">
					 <input type="radio" name="additionalFlag"
								value="${item.value}"
								<c:if test="${item.value==param.additionalFlag}">
                                checked='true'
                             	</c:if> />
                             	${item.label}
               	         </c:forEach>
				</td>
				 -->
	            <td>
	              <label class="lab">是否电销：</label>
                   <c:forEach items="${fns:getDictList('yes_no')}" var="item">
					 <input type="radio" name="telesalesFlag"
								value="${item.value}"
								<c:if test="${item.value==param.telesalesFlag}">
                                checked='true'
                             	</c:if> />
                             	${item.label}
               	   </c:forEach>
	            </td>
            </tr>
			
        </table>
       </form>
        <div class="tright pr30 pb5">
           <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
		   <input type="button" class="btn btn-primary" id="clrBtn"   value="清除"></input>
         <div class="xiala" style="float: left; margin-left: 45%; padding-top: 10px">
			<img src="${context}/static/images/up.png" id="showMore"></img>
       </div>
    </div>
    </div>
    <sys:columnCtrl pageToken="reconff"></sys:columnCtrl>
   <div class='box5'> 
        <table id="tableList"  class="table  table-bordered table-condensed table-hover ">
        <thead>
        <tr>
            <th>序号</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>共借人</th>
            <th>自然人保证人</th>
            <th>省份</th>
            <th>城市</th>
            <th>门店</th>
            <th>状态</th>
            <th>产品</th>
            <th>批复金额</th>
            <th>批复分期</th>
            <th>加急标识</th>
            <th>是否电销</th>
            <th>团队经理</th>
            <th>销售人员</th>
            <th>进件时间</th>
            <th>上调标识</th>
            <th>模式</th>
            <th>渠道</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:if test="${itemList!=null && fn:length(itemList)>0}">
		<c:forEach items="${itemList}" var="item"> 
           <tr>
             <td><c:set var="index" value="${index+1}"/>${index}</td>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td>
             	<c:if test="${item.loanInfoOldOrNewFlag eq 0}">
               		${item.coborrowerName}
               	</c:if>
             </td>
             <td>
             	<c:if test="${item.loanInfoOldOrNewFlag eq 1}">
               		${item.bestCoborrower}
               	</c:if>
             </td>
             <td>${item.provinceName}</td>
             <td>${item.cityName}</td>
             <td>${item.storeName}</td>
             <td>${item.loanStatusName}</td>
             <td>${item.replyProductName}</td>
             <td>${item.replyMoney}</td>
             <td>${item.replyMonth}</td>
             <td>
                <c:if test="${item.urgentFlag=='1'}">
                    <span style="color:red">加急</span>
                </c:if>
             </td>
             <td>
                <c:if test="${item.telesalesFlag=='1'}">
                    <span>是</span>
                </c:if>
                <c:if test="${item.telesalesFlag=='0'}">
                    <span>否</span>
                </c:if>
             </td>
             <td>${item.teamManagerName}</td>
             <td>${item.customerManagerName}</td>
             <td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
             <td>${item.raiseFlag}</td>
             <td>${item.modelName}</td>
             <td>${item.channelName}</td>
             <td>
               <input type="button" class="btn_edit" applyId="${item.applyId}" name="dealReconsiderBtn" value="办理"></input>
              </td>
         </tr>  
         </c:forEach>  
         </c:if>
         <c:if test="${itemList==null || fn:length(itemList)==0}">
           <tr>
            <td colspan="19" style="text-align:center;">暂无满足条件的数据</td>
           </tr>
         </c:if>
        </table>
    </div>
</div> 
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>