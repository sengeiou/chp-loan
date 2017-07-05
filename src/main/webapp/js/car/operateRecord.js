
$(function(){
	
	//删除图片
	$("#delSure").bind('click',function(){
		var flag = $("#delForm").validate({
			onclick: true,
			submitHandler: function(){
				var btnId = $("#buttonId").val();
				var url = ctx + "/car/operateRecord/saveOperateRecord";
			    $.ajax({
		    		url:ctx+"/car/operateRecord/saveOperateRecord",
		    		type:'post',
		    		data: $("#delForm").serialize(),
		    		success:function(data){
		    			//删除docId的业务逻辑
		    			var loanCode = $("input[name='loanCode']").val();
		    			deleteDocId(loanCode,btnId,data,callback);
		    		},
		    		error:function(){
		    			art.dialog.alert('服务器异常！');
		    			return false;
		    		}
		    	});
			}
		});
	});
	
	//监听点击的按钮,设置事件源，清除删除原因 ,错误提示等 
	$(".btn-small").click(function(){
		var btnId = $(this).attr("id");
		$("#buttonId").val(btnId);
		$("#reason").val("");
		$(".error").text("").removeClass("error");
	})
})

function callback(status1,status2,btnId){
	if(status1 == "true" && status2=="true"){
			art.dialog.alert("删除成功!",function(){
				$("#cancel").click();
				$("#"+btnId).parent().parent().remove();
		});
	}
}