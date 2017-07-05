
$(document).ready(function(){
	
	
	
	//点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){
	 $(':input','#inputForm')
	  .not(':button, :reset,:hidden')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
	});
	
/*	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
		}
	});*/
	
	// 点击全选
	$("#checkAll").click(function(){
		var urgeDecuteMoeny=0.00;
		var deductNumber = 0;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checks']").each(function() {
				$(this).attr("checked",'true');
				urgeDecuteMoeny+=parseFloat($(this).attr("urgeDecuteMoeny"));
				deductNumber+=1;
				});
		}else{
			$(":input[name='checks']").each(function() {
				$(this).removeAttr("checked");
			});
		}
		$("#deductAmount").text(fmoney(urgeDecuteMoeny,2));
		$("#totalNum").text(deductNumber);
	});
	
	//计算金额和放款总笔数
	$(":input[name='checks']").click(function(){
		var urgeDecuteMoeny=0.00;
		var deductNumber = 0;
			$(":input[name='checks']:checked").each(function(){
				urgeDecuteMoeny+=parseFloat($(this).attr("urgeDecuteMoeny"));
				deductNumber+=1; 
        	});
			$("#deductAmount").text(fmoney(urgeDecuteMoeny,2));
			$("#totalNum").text(deductNumber);

	});
	
	// 金额精确到小数点后2位
	  function fmoney(s, n) {  
	        n = n > 0 && n <= 20 ? n : 2;  
	        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
	        t = "";  
	        for (i = 0; i < l.length; i++) {  
	            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	        }  
	        return t.split("").reverse().join("") + "." + r;  
	    }  
 
	// 上传回执结果,在上传之前要进行验证，所以上传回执结果要使用Ajax进行控制
	$("#sureBtn").click(function(){
		var formData = new FormData($("#uploadAuditForm")[0]);
		if(validateXlsFileImport('fileid','file')){
			$.ajax({
				type : 'post',
			    url:ctx +"/car/creditorRight/batchImport",
				data : formData,
				cache: false,
				processData: false,
				contentType: false,
				dataType : 'text',
				success : function(data) {
					   alert(data);
					   window.location.href = ctx + "/car/creditorRight/list";
				}
			});
		}
		return false;
	});
	
	// 批量发送
	$("#batchSend").click(function(){
		var idVal = "";
		if($(":input[name='checks']:checked").length==0){
			 var flag=confirm("确认将所有的数据批量发送？");
	           if(flag){
	   			$.ajax({
				    type: "POST",
				    url:ctx +"/car/creditorRight/batchSend?idVal="+idVal,
				    data:$('#inputForm').serialize(),
				    success: function(data) {
				        alert("操作成功");
				        page(1,30);
				    },
					error: function() {
						alert("操作失败");
					}
				});
	   			
	           }else{
	        	   return;
	           }
	          				
			}else{
			$(":input[name='checks']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).val();
        		}else{
        			idVal=$(this).val();
        		}
        	});
			art.dialog({
				content : "确认将选中的数据发送？",
				ok: function () {
		   			$.ajax({
					    type: "POST",
					    url:ctx +"/car/creditorRight/batchSend?idVal="+idVal,
					    data:$('#inputForm').serialize(),
					    success: function(data) {
					        alert("操作成功");
					        window.location.href = ctx + "/car/creditorRight/list";
					    },
						error: function() {
							alert("操作失败");
							return false;
						}
					});
				    },
				cancel: function () {
				        $("input[name='checks']").each(function(){
				        	$(this).attr("checked",false);
				        });
				    }
			});
		}
	});
});
var creditorRights = {};

creditorRights.validate = function(){
	$("#inputForm").validate({
		rules: {
			loanCustomerName: {	required: true},
			certType: {required: true},
			customerCertNum: {card: true},
			loanAmount:{number:true,maxlength:13},
		},
		messages: {
			
			loanCustomerName: {realName:"姓名只能为2-10个汉字"},
			certType: {remote: "请填写证件类型"},
			customerCertNum: {equalTo: "请填写证件号码"},
			loanAmount:{number:"请输入合法的数字",
				maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串")},
			
		},
		submitHandler: function(form){
			//loading('正在提交，请稍等...');
//			form.submit();
//			验证数据后保存债权信息
			$.ajax({
			    type: "POST",
			    url:ctx +"/car/creditorRight/add",
			    data:$('#inputForm').serialize(),
			    async: false,
			    success: function(data) {
			        alert("车借债权信息录入成功");
			        window.location.href = ctx + "/car/creditorRight/list";
			    },
				error: function() {
					alert("操作失败，请联系管理员");
				}
			});
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
}


/*//批量上传


$("#sureBtn").bind('click',function(){
	  var formData = new FormData($( "#uploadAuditForm" )[0]);
	  $.ajax({
		   type: "POST",
		   url: ctx + "/car/creditorRight/batchImport",
		   data: $("#uploadAuditForm").serialize(),
		   data:formData,
		   cache: false,  
         processData: false,  
         contentType: false ,
		   dataType : 'text',
		   success: function(data){
			   alert("data"+data);
			   if(data == 'false'){
				   $.jBox.error('传入文件类型不正确', '提示信息');
			   }else if(data == 'date'){
				   $.jBox.error('传入的数据不正确', '提示信息');
			   }else if(data == 'true'){
				   $.jBox.info('上传操作成功', '提示信息');
				   window.location = ctx + "/car/creditorRight/list";
			   }
		   },
		   error:function(date){
			   $.jBox.error('传输错误', '提示信息');
		   }
		});
})*/






