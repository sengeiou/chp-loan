<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript">
        function searchForm(){
      //判断录入的时间先后的大小
        var endtime = $('#endTime').val();
            var starttime = $('#startTime').val();
            var start = new Date(starttime.replace("-", "/").replace("-", "/"));
            var end = new Date(endtime.replace("-", "/").replace("-", "/"));
            if (end < start) {
                alert('结束日期不能小于开始日期！');
                return false;
            }
            else {
                return true;
            }; 
    	 
    	 $.ajax({
   		  dataType:"html",
   		  url:ctx +"/borrow/template/sms/findAl",
   		  data:$("#search").serialize(),
   		  asyn:false,
   		  cache:true,
   		  success:function(data){
   			$("#temp").html(data);
   		    
   		  }
    	 });
     };
     /*跳转到添加页面  */
  function saveSms(){
		  location.href = ctx +"/borrow/template/sms/saveForm";
  };
     /* 跳转到修改页 */
     function updateGo(id){
       location.href = ctx +"/borrow/template/sms/updateForm?id="+id;
     } 
</script>
</head>
<body>


<div class="body_top">
          <p class="bar"><c:if test="businesshuijin" >汇金</c:if><c:if test="businesscaifu">财富</c:if></p>
      <form id="search" method="post">
      <div class="control-group">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">模板名称：</label><input type="text" class="input_text178" name="templateName" value="${templateName }"/></td>
                <td><label class="lab">录入日期：</label><input type="text" class="input_text70" id="startTime" name="createStart"/>-<input type="text" class="input_text70" id="endTime" name="createEnd"/></td>
                <td><label class="lab">模板状态：</label><select class="select180" name="templateStatus"><option value="">请选择</option><option value="0">停用</option><option value="1">可用</option></select></td>
            </tr>
        </table>
        <div class="tright pr30 pb5"><button class="btn btn-primary" onclick="searchForm()">搜索</button>
        <button class="btn btn-primary" onclick="javascript:window.history.go(-1)">清除</button></div>
    </div>
    </form>
     <p class="mb5">
     	<input type="button" class="btn btn-small" onclick="saveSms()" value="新增短信模板" />
     </p>
    <div class="box5">
    <table class="table table-hover table-bordered table-condensed" style="margin-bottom:100px">
        		        <tr>
        		            <th>模板名称</th>
        		            <th>描述</th>
        		            <th>模板内容</th>
        		            <th>模板状态</th>
        		            <th>创建时间</th>
        		            <th>操作</th>
        		        </tr>
        		        <tbody id="temp">
        		      <c:forEach items="${list}" var="ls">
			                <input type="hidden" name="id" value="${ls.id }"/>
			               	<tr>
			                   <td>${ls.templateName}</td>
			                   <td>${ls.templateDescription }</td>
			                   <td>${ls.templateContent }</td>
			                   <td>${ls.templateStatus }</td>
			                   <td>${ls.createTime }</td>
			                   <td><input type="submit" class="btn_edit" value="修正" onclick="updateGo('${ls.id }')"/></td>
                           </tr>
                       </c:forEach>
                       </tbody>
              </table>
    </div>

</div>
</body>
</html>
