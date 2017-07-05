<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
<style type="text/css">
  a{
    cursor:pointer;
    text-decoration:none;
  }
 
  #d1{
    width:200px;
    float:left;
  }
  #sf{position:absolute;top:0;right:0
  }
 .wj{font-size:12px;color:#666}
 tr{height:50px}
 .btn-primary{color: #fff;
	background-color: rgb(91, 118, 189);
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	padding: 4px 12px;}
.btn{border-radius: 3px;display: inline-block;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;}
</style>
<script src="${context}/static/flash/flexpaper/jquery.js" type="text/javascript"></script>
<script src="${context}/static/flash/flexpaper/flexpaper_flash.js" type="text/javascript"></script>
<script src="${context}/static/flash/flexpaper/flexpaper_flash_debug.js" type="text/javascript"></script>
<script src="${context}/static/artDialog4.1.7/artDialog.js" type="text/javascript"></script>
<script type="text/javascript">
    
    function getFile(x){
    	
    	var docId=$('#doc').val();
    	var file=$('#file').val();
    	var loanCode=$('#loanCode').val();
    	file=x;
    	var name=$('#name').val();

    	  $.ajax({
    		url:'${ctx}/apply/contractAudit/getFile?loanCode='+loanCode+'&name='+file,
    		datatype:'json',
    		async:false,
    		type:'GET',
    		success:function(data){
    			var f=data.file;
    			name=f;
    		},
    		error:function(){
    			alert('请求出错');
    		}
    	}); 
        var url='${context}/static/flash/flexpaper/'+name;
    	  $('#sf').attr('src',url);
    	  $('#sf').reload();
    };
    
    function getContext(id){
    	var url='${ctx}/apply/contractAudit/writeTo?docId='+id;
    	$('#sf').attr('src',url);
    	$('#sf').reload();
    };
    
    function xianshi(id){
    	var url='${ctx}/apply/contractAudit/writeTo?docId='+id;
		window.location=url;
    }
    $(function(){
    	$('#fh').click(function(){
    		var docId=$('#doc').val();
        	var contractCode=$('#contractCode').val();
        	var loanCode=$('#loanCode').val();
    		window.location='${ctx}/apply/contractAudit/contractAndPersonDetails?loanCode='+loanCode+'&docId='+docId+'&contractCode='+contractCode;
    	});
    })
    
   // $(function(){
    	//$("a[name='qp']").each('click',function(){
    		//var docId=$(this).attr('docId');
    		//var url='${ctx}/apply/contractAudit/writeTo?docId='+docId;
    		//window.location=url;
    		/* art.dialog.open(url, {
				title: '全屏查看',	
				top: '0',
				lock: true,
			    width: '100%',
			    height:'100%',
			},false); */
    	//});
    //})

</script>
</head>
<body>
<div>
   <input type="hidden" id="loanCode" value="${loanCode}" />
   <input type="hidden" id="doc" value="${docId}" />
   <input type="hidden" id="contractCode" value="${contractCode}" />
   <input type="hidden" id="file" value="" />
   <input type="hidden" id="name" value="" />
</div>
       <p style="font-size:14px;height:30px;line-height:30px;">合同相关文件</p>
        <div id="d1" style="width:30%;margin-left:3%">
          <c:forEach items="${cfs}" var="cf">
             <table  cellpadding="0" cellspacing="0" border="0"  width="100%" >
          <tr >
             <td style="width:200px"><a class="wj"  href="#" onclick="getContext('${cf.docId}')">${cf.contractFileName}</a></td>
             <td><input type="button" style="font-size:12px;color:#337ab7;border:none;background:none" onclick="xianshi('${cf.docId}')" value="全屏"  /></td>
          </tr>
          
             </table>
              
          </c:forEach>
          <table  cellpadding="0" cellspacing="0" border="0"  width="100%" >
             <tr>
             <td></td>
             <td></td>
            </tr>
             <tr>
             <td style="width:200px"></td>
             <td><input class="btn btn-primary" type="button" id="fh" value="返回" /></td>
              </tr>
               </table>
    </div> 
   
 
    <iframe  id="sf"  width="70%" align="right" border="0" height="100%" frameBorder=0 frameSpacing=0>
        
    </iframe>
 
 
</body>
</html>