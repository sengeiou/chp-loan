<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>系统设定数据</title>
<script>
var system = {};
/**
 * 保存
 */
 system.save = function() {
	
	$("#addDue").bind('click', function() {
		art.dialog({
		    content: document.getElementById("addDueDiv"),
		    title:'保存',
		    fixed: true,
		    lock:true,
		    id: 'confirm',
		    okVal: '确认保存',
		    ok: function () {
		    	save();
				return false;
		    },
		    cancel: true
		});
	});
}
 /**
  * 修改
  */
  system.update = function() {
 	
 	$("#toUnUse").bind('click', function() {
 		
 		if($("input:checkbox[name='checkBox']:checked").length==0){
 			alert("请选择要修改的数据！");
 			return false;
 		}
 		if($("input:checkbox[name='checkBox']:checked").length>1){
 			alert("请选择一条数据修改！");
 			return false;
 		}
 		var id = $("input:checkbox[name='checkBox']:checked").val();
 		$.ajax({  
			   type : "POST",
			   async:false,
			   data:{
				   id:id
			   },
			   url : ctx+"/common/systemSetMater/getBean",
			   datatype : "json",
			   success : function(data){
				   var data = eval( "(" + data + ")" );
				   $("#sysValue").val(data.sysValue);
				   $("#sysName").val(data.sysName);
				   $("#sysFlag").val(data.sysFlag);
				   $("#flagId").val(data.id);
			}
 		});
 		
 		
 		art.dialog({
 		    content: document.getElementById("addDueDiv"),
 		    title:'修改',
 		    fixed: true,
 		    lock:true,
 		    id: 'confirm',
 		    okVal: '确认修改',
 		    ok: function () {
 		    	save();
 				return false;
 		    },
 		    cancel: true
 		});
 	});
 }

 
  $(document).ready(function() {
	  system.save();
	  system.update();
  });
  
  
  
  function save(){
	  var sysValue= $("#sysValue").val();
	  var sysName= $("#sysName").val();
	  var sysFlag= $("#sysFlag").val();
	  var id = $("#flagId").val();
	  
		$.ajax({  
			   type : "POST",
			   async:false,
			   data:{
				   sysValue:sysValue,
				   sysName:sysName,
				   sysFlag:sysFlag,
				   id:id
			   },
			   url : ctx+"/common/systemSetMater/save",
			   datatype : "json",
			   success : function(msg){
				   if(id){
					   msg="修改成功"; 
				   }else{
					   msg = "保存成功";
				   }
				    art.dialog(
				    {
				    	content: msg,
					    title:'系统设定',
					    fixed: true,
					    lock:true,
					    id: 'oflineconfirm',
					    okVal: '确认',
					    ok: function () {
					    	window.location.href=ctx+"/common/systemSetMater/list";
							return false;
					    }
				    }		
				    );
					
				},
			   error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
			});
	 
	  
  }
</script>
</head>
<body>
 <div class="control-group">
 <form method="post" id="centerapplyPayForm">
    <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
            <tr>
                <td><label class="lab">预约银行：</label>
                </td>
            </tr>
        </table>
        <div class="tright pr30 pb5">
        <button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
        <button class="btn btn-primary" id="reset">清除</button></div>
  </form>
    </div>
    <p class="mb5"> 
    <button id="addDue" role="button" class="btn btn-small">新增</button>
	<button class="btn btn-small" id="toUnUse">修改</button>
    <button class="btn btn-small" id="toUse">删除</button></p>
   <div class="box5"> 
        <table class="table  table-bordered table-condensed table-hover">
          <thead>
            <tr>
                <th><input type="checkbox" class="checkbox" id="checkAll"/></th>
                <th>系统设定标记</th>
                <th>系统设定名称</th>
                <th>设定值</th>
            </tr>
            </thead>
        <c:if test="${sysPage!=null && fn:length(sysPage)>0}">
         <c:forEach items="${sysPage}" var="item">
            <tr>
                 <td><input type="checkbox" name="checkBox"  value="${item.id }"/>
                 </td>
                 <td>
                   ${item.sysFlag} 
                 </td>
                 
                 <td>
                 ${item.sysName}
                </td>
                
                <td>
                ${item.sysValue}
                </td>
            </tr>
			</c:forEach>
		</c:if>
        <c:if test="${ sysPage==null || fn:length(sysPage)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
        </c:if>
      </table>
    </div>
</div>

<!-- 新增预约弹框 -->
<div  id="addDueDiv" style="display: none">
 <input type ="text"  id="flagId" >
            <table id="backTB" class="table4" cellpadding="0" cellspacing="0" border="0" width="100%"> 
                 <tr>
                    <td><label class="lab">系统设定标记：</label> 
                      <input type ="text" name="sysFlag" id="sysFlag" >
                    </td>
                </tr>
                 <tr>
                    <td><label class="lab">系统设定名称:</label>
                     <input type ="text" name="sysName" id='sysName'>
                   </td>
                </tr>
                <tr>
                   <td><label class="lab">设定值:</label>
                    <input type ="text" name="sysValue"  id="sysValue">
                   </td>
               </tr>
            </table>
    </div>
</body>
</html>