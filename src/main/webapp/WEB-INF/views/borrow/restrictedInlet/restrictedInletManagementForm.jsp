<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/poscard/restricted.js"></script>
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
			   <td><label class="lab">客户经理M1逾期率高危线设置：</label><input value="${customerNum}" type="text" name="customerNum" class="input_text178"></input>%</td>
              </tr>
              <tr>
			   <td><label class="lab">团队经理M1逾期率高危线设置：</label><input value="${termNum}" type="text" name="termNum" class="input_text178"></input>%</td>
              </tr>
              <tr>
			   <td><label class="lab">门店经理M1逾期率高危线设置：</label><input value="${storeNum}" type="text" name="storeNum" class="input_text178"></input>%</td>
              </tr>
        </table>
        <div class="tright pr30"><input type="submit" id="submitBtn" value="提交" class="btn btn-primary"></input> </div>
 </form>  
</div>
</body>
</html>
