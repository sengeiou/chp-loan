<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>回退清单</title>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/storereview/backStoreView.js"></script>
</head>
<body>
	<form id="fallBack" style="position: absolute; width:100%;">
        <table class="f14 table1" cellpadding="0" cellspacing="0" border="0" width="100%" style="margin-bottom:120px;">
            <tr>
                <td class="pl10">
                  <input type="hidden" id="relId" value="${relationId}">
            		<c:forEach items="${dicts}" var="firstList">
            			<input type="checkbox" id="${firstList.value}" disabled="disabled"
            			style="display: block;float: left;margin: 10px 3px 0;"/>
            			<label style="float: left;">${firstList.label}&nbsp;&nbsp;&nbsp;&nbsp;</label>
             		</c:forEach>    
				</td>
            </tr>
            <c:forEach items="${dicts}" var="firstList">
            	<c:if test="${fn:length(firstList.value) eq 3}">
            		<c:forEach items="${firstList.dictExs}" var="secondList">
			            <tr>
			           		<td>
			                  <p class="listbg01 pl10" style="line-height:28px;">
			                  	${secondList.label}
			                  	</p>
			                </td>
			   		    </tr>
			            <tr>
			                <td class="pl10">
			                	<c:forEach items="${secondList.dictExs}" var="thirdList">
			                		<input type="checkbox" id="${thirdList.value }" disabled="disabled"
			                		style="display: block;float: left;margin: 10px 3px 0;"/>
			                		<label style="float: left;">&nbsp;${thirdList.label}&nbsp;&nbsp;&nbsp;&nbsp;</label>
			                	</c:forEach>
			                </td>
			            </tr>
		            </c:forEach>
            	</c:if>
            </c:forEach>
            
            <!-- <tr>
           		<td>
                   <p class="listbg0101 pl10" style="line-height:28px;">回馈意见</p>
                   </td>
   		    </tr>
            <tr>
                <td class="pl10"><textarea id="feedBack" class="textarea_refuse" disabled="disabled"></textarea></td>
            </tr> -->
        </table>
        
	    <div style="position:fixed;bottom:0;background:#fff;width:100%;">
		    <label class="lab mt10" style="vertical-align: top;">回馈意见：</label>
		    <textarea id="feedBackText" name="auditBack.feedBack" class="textarea_refuse mt10" style="width:540px;"></textarea>
		</div>
	</form>
</body>
</html>