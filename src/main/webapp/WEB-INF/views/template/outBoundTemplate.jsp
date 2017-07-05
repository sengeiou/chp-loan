<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript">
	function showHT()
	{
		alert("不要急, 正在加载中...........");
/* 		window.open('../模板管理/合同预览.html','window','width=500px,height=700px, top=0,left=300px, modal=yes,status=no');
 */	}
     function emptyBtn(){
    	 document.getElementById("emptyForm").reset();
     }
     /* 跳转到添加页面 */
     function saveBtn(){
    	 location.href = ctx +"/borrow/template/outboundtemplate/saveForm";
     }
     /* 跳转到详细页面 */
     function lookBtn(id){
    	alert("深奥啊.......");
     }
</script>

</head>
<body>

    <div class="control-group">
        <form action="${ctx }/borrow/template/outboundtemplate/findList" method="post" id="emptyForm">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr >
                <td><label class="lab">外访单名称：</label><input type="text" class="input_text178" name="templateName" value="${templateName }"/></td>
                <td><label class="lab">外访单类型：</label>
				       <select class="select180" onchange="busiS(this.value);" name="templateType" value="${templateType }">
					      <option value="">请选择</option>
						  <option value="1">工作单位现场考察</option>
					      <option value="2">实名家庭现场考察</option>
						  <option value="3">实名企业现场考察</option>
					    </select>
				</td>
                <td nowrap="nowrap">
				</td>
            </tr>
             <tr>
			    <td><label class="lab">版本号：</label><input class="input_text178" type="text" name="version" value="${version }"/></td>
                <td><label class="lab">使用状态：</label><select class="select180" name="status" value="${status }"><option value="">请选择</option><option value="1">启用</option><option value="0">停用</option></select></td>
            </tr>
        </table>
        <div class="tright pr30"><input type="submit" value="搜索" class="btn btn-primary" />
            <input value="清除" type="button" class="btn btn-primary" onclick="emptyBtn()"/></div>
       </form>
    </div>
     <p class="mb5"> 
     <input type="button" value="新增外访单" class="btn btn-small" onclick="saveBtn()"/>
     </p>
     <form action="${ctx }/borrow/template/outBoundTemplate/findList">
    <table class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px">
        <tr>
            <th>外访单名称</th>
			<th>外访单类型</th>
	    	<th>使用状态</th>
            <th>版本号</th>
            <th>上传人</th>
			<th>上传时间</th>
            <th>更新人</th>
            <th>更新时间</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${list }" var="ls">
        <input type="hidden" name="id" value="${ls.id }"/>
        <tr>
            <td>${ls.templateName }</td>
            <td>${ls.templateType }</td>
            <td>${ls.status }</td>
            <td>${ls.version }</td>
			<td>${ls.createBy }</td>
            <td>${ls.createTime }</td>
			<td>${ls.modifyBy }</td>
            <td>${ls.modifyTime }</td>
            <td class="tcenter">
			   <!-- <input  type="button" value="查看" onclick="detailBtn()"/> -->
			   <button class="btn_edit" onclick="lookBtn('${ls.id }')">查看</button>
		       <button class="btn_edit" onclick="javascript:showHT();">预览</button>
			</td>
        </tr>
        </c:forEach>
    </table>
    </form>
    </div>
    <div class="page">
        <div class="r">当前总记录<strong>5</strong>条，每页显示<strong>10</strong>条</div>
    </div>
</body>
</html>
