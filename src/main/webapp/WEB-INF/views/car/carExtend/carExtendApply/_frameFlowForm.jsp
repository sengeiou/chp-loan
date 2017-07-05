<%@ page contentType="text/html;charset=UTF-8"%>
<form id="frameFlowForm" method="post">
  	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	<input type="hidden" value="${workItem.token}" name="token"></input>
	<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
	<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
</form>
