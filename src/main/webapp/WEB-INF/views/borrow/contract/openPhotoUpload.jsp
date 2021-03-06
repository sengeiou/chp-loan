<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript">
  $(document).ready(function(){
	  $('#fileForm').validate({
		  onkeyup: false
	  });
  });
  function setFileSize(size){
	  $('#fileSize').val(size);    
  }
</script>
<title>图片上传</title>
</head>
<body>
<form id='fileForm' action='${ctx}/paperless/confirminfo/savePhoto' method='post' enctype='multipart/form-data'>
 <table>
  	<tr><td nowrap="nowrap">
  	    	<label class='lab'><span style="color:red;">*</span>文件：</label>
  	    	<input id='file' type='file' name="file" onchange="setFileSize(this.files[0].size);" class="required" accept="image/*"/>
  	        <input type="hidden" id="fileSize" value=''/>
  	    </td>
  	</tr>
  	<tr><td><input type='hidden' id='relationIdH' name='relationId' value='${relationId}'/>
    		<input type='hidden' id='photoTypeH' name='photoType' value='${photoType}'/>
    		<input type='hidden' id='customerTypeH' name='customerType' value='${customerType}'/>
    		<input type='hidden' id='loanCodeH' name='loanCode' value='${loanCode}'/>
    		<input type='hidden' id='contractCodeH' name='contractCode' value='${contractCode}'/>
    	</td>
    </tr>
   </table>
 </form>
</body>
</html>