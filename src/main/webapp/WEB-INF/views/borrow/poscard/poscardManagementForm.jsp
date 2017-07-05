<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/poscard/posBacktage.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  infos.datavalidate();
  });
 
</script>
<meta name="decorator" content="default"/>

</head>
<body>
 <div class="box2 mb10">
  <form id="inputForm">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="95%">
              <tr>
                  <input type="hidden" name="id" value="${id}"/>
               <td><label class="lab"><span style="color: #ff0000">*</span>匹配状态：</label><select class="required select180" name="matchingState">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_matching')}" var="matching">
                                   <option value="${matching.value }" <c:if test="${matchingState == matching.value }">selected</c:if>>${matching.label }</option>
                              </c:forEach>
                	</select>
               </td>
			   <td><label class="lab"><span style="color: #ff0000">*</span>合同编号：</label><input value="${contractCode}" type="text" name="contractCode" class="required input_text178"></input></td>
               <td><label class="lab"><span style="color: #ff0000">*</span>查账日期：</label><input id="auditDate" name="auditDate"  type="text" readonly="readonly" maxlength="20" class="required input_text70 Wdate"
								value="<fmt:formatDate value="${auditDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
               </td>
          </tr>
        </table>
        <div class="tright pr30"><input type="submit" id="submitBtn" value="提交" class="btn btn-primary"></input> </div>
 </form>  
</div>
</body>
</html>
