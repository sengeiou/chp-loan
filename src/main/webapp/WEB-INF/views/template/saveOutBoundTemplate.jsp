<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title></title>
<head>
<meta name="decorator" content="default"/>
<script language="javascript">
	function fileBtn(){
		alert("正在加载中......");
	}
</script>
</head>
<body>
<div class="body_r">
     <p class="bar"><span class="color_b97226">首页>模板管理></span>外访单新增</p>
    <div class="box1 mb10">
        <form action="${ctx }/borrow/template/outboundtemplate/saveOutBoundTemplate" method="post">
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
				   <label class="lab">外访单附件：</label><input class="input_text178" type="text" name="fileName" value="${fileName }"/><input type="button" value="文件" onclick="fileBtn()"/>
				</td>
            </tr>
             <tr>
			    <td><label class="lab">版本号：</label><input class="input_text178" type="text" name="version" value="${version }"/></td>
                <td><label class="lab">使用状态：</label><select class="select180" name="status" value="${status }"><option value="">请选择</option><option value="1">启用</option><option value="0">停用</option></select></td>
				<td></td>
            </tr>
			<tr>
               <td colspan="3">
				 <label class="lab">备注：</label> <input type="textarea" cols="60" rows="5" name="remark"  value="${remark }"/>
				</td>
			</tr>
        </table>
        <div class="tright pr30"><input type="submit" value="新增"/><input type="button" name="go" value="清除" onclick="javascrip:history.go(-1)"/></div>
      </form>
    </div>
   </div>
</body>
</html>
