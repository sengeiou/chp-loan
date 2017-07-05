<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/poscard/storeRestriced.js"></script>
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
  <label class="lab">选择高危线标准:</label>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="95%">
              <tr>
                  <input type="hidden" name="id" value="${id}"/>
			      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" style="margin-top:1px" id="paymentsManual" name="highStandard" value="0" checked="checked"  ><label class="lab">省份高危线</label></td>
              </tr>
              <tr>
              	  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" style="margin-top:1px" id="paymentsManual" name="highStandard" value="1"><label class="lab">自定义高危线</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;M1逾期率高危线值 <input value="${restrictedInlet.m1Yql}" type="text" name="m1Yql" class="input_text178">%</input></td>
              </tr>
        </table>
        <div class="tright pr30"><input type="submit" id="submitBtn" value="提交" class="btn btn-primary"></input> </div>
 </form>  
</div>
</body>
</html>
