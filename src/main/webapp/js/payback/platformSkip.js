/**
 * 配置js
 */
jQuery(document).ready(function($){
	//var iframe = getCurrentIframeContents();
	$("#submitButton").click(function(){
		$form = $('#addOrEdit');
		if(!$form.validate().form()){
			//验证不成功
			return false;
		}
		/*if($("select[name='platformId']")!=""){
			contents_getJsonForSync(
					ctx+"/platformRule/checkPlatform",
					{"platformId":$("select[name='platformId']").val()},
					"post",
					function(result){
						if(result == 'false'){
						BootstrapDialog.alert("你选中的平台,已经存在");	
						 return;
						}else{
							$form.submit();
						}
					}
				)
		}*/
		
		var platIdList = document.getElementsByName("platIdList");
		var deductTypeList = document.getElementsByName("deductTypeList");
		
		for(var i = 0; i<platIdList.length;i++){
			if(!platIdList[i].value){
				art.dialog.alert("平台不允许为空！");
				return false;
			}
			
		}
		
		for(var i = 0; i<deductTypeList.length;i++){
			if(!deductTypeList[i].value){
				if(platIdList[i].value!= '3'){
					art.dialog.alert("接口不允许为空！");
					return false;
				}
			}
			
		}
		
		$form.submit();
		
	
	})
	
   $("select[name ='platIdList']").live('click',function(){
			//alert($(this).val());
			 var plantId = $(this).val();
			 if(plantId == '3'){
				 $(this).next().hide();
				 $(this).parent().find("select[name='deductTypeList']").hide();

			 }else{
				 $(this).next().show();
				 $(this).parent().find("select[name='deductTypeList']").show();

			 }
	});
	
	$("#add").click(function(){
		window.location.href = ctx + "/borrow/payback/plantskiporder/addOrEdit";
	})
	
	// 校验平台的重复
/*	$("select[name='platformId']").change(function(){
		if($(this).val()!=""){
			var isConcentrate =  $("input[name='isConcentrate']:checked").val()
			if(isConcentrate){
				$.ajax({  
					type : "POST",
				    data:{"platformId":$(this).val(),
				    	   "isConcentrate":isConcentrate},
					url :ctx+"/borrow/payback/plantskiporder/checkPlatform",
					datatype : "json",
					success : function(data){
						if(data == 'false'){
							art.dialog.alert("你选中的平台,已经存在");	
							$("select[name='platformId']").val("");
							$("select[name='platformId']").change();
							 return;
						}					 
					}
				});
			}
		}
			 
	});*/
	
	// 添加划扣平台
	var index=1;
	$("#addhkpt").click(function(){
		// 动态创建td
		// var $td=$("<td></td>");
		// 动态创建删除按钮
	/*	var $input=$('<input class="btn btn_sc ml10" type="button" value="删除" id="removeTr">');
		// 增加节点
		$td.append($input);*/
		// 复制tr
		var $tr=$("#template").clone(true).show().removeAttr('id').attr('id','deductRuleTr'+index);
		
		var optionId = $("select[name='platIdList']");
		optionId.each(function(){
			$tr.find('#platIdList').find("option[value='"+this.value+"']").remove();
		});
		
		$tr.find("#deductTypeList").attr('name',"deductTypeList").removeAttr('id');
		$tr.find("#platIdList").attr('name',"platIdList").removeAttr('id');
		$tr.appendTo("#onLineTable");
		$tr.appendTo("#onLineTable");
		$tr.find('select').trigger("change");
		index++;
	});
	// 删除划扣平台
	$("#removeTr").live("click",function(){
		$(this).parent().remove();
	})
	
	
	$("#delete").live("click",function(){
		var url = $(this).attr("url");
		art.dialog.confirm("您确定要删除该条规则吗？",function() {
				window.location.href = url;
		})
	});
	
	//清除按钮绑定事件
	$("#reset").click(function(){
		$(":input").val("");
		$("#searchForm").submit();
	});
});

function go(){
	window.location.href = ctx + "/borrow/payback/plantskipordernew/queryPage";
}


function checkPlatform(){
	var bankCode = $("select[name='bankCode']").val();
	if(bankCode){
		var isConcentrate =  $("input[name='isConcentrate']:checked").val()
		if(isConcentrate){
			$.ajax({  
				type : "POST",
			    data:{"bankCode":bankCode,
			    	   "isConcentrate":isConcentrate},
				url :ctx+"/borrow/payback/plantskipordernew/checkPlatform",
				datatype : "json",
				success : function(data){
					if(data == 'false'){
						art.dialog.alert("你选中的银行,已经存在");	
						$("select[name='bankCode']").val("");
						$("select[name='bankCode']").change();
						 return;
					}					 
				}
			});
		}
	}
}
