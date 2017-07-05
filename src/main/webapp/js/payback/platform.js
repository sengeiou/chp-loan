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
//		if($("select[name='platformId']")!=""){
//			contents_getJsonForSync(
//					ctx+"/platformRule/checkPlatform",
//					{"platformId":$("select[name='platformId']").val()},
//					"post",
//					function(result){
//						if(result == 'false'){
//							BootstrapDialog.alert("你选中的平台,已经存在");	
//							 return;
//						}else{
//							$form.submit();
//						}
//					}
//				)
//		}
		$form.submit();
	})
	
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
		
		var optionId = $("input[name='platformRule']").val().split(",");
		$.each(optionId,function(i,item){
			$tr.find('select').find("option[value='"+item+"']").remove();
		})
		$tr.find('select').attr('name','platformIdList').removeAttr('id');
		$tr.appendTo("#onLineTable");
		$tr.appendTo("#onLineTable");
		$tr.find('select').trigger("change");
		index++;
	});
	// 删除划扣平台
	$("#removeTr").live("click",function(){
		$(this).parent().remove();
		platformRule();
	})
	
	$("select[name='platformId']").change(function(){
		$("input[name='platformRuleText']").val($(this).find("option:selected").text())
		$("input[name='platformRule']").val($(this).val())
	})
	
	$("select[name='platformIdList']").live("change",function(){
		var text ="";
		var platformRule = "";
		$("select:not(#hiddenSelect)").each(function(index){
			if($("select:not(#hiddenSelect)").size()-1 == index){
				text = text + $(this).find("option:selected").text();
				platformRule = platformRule +  $(this).val();
			}else{
				text = text + $(this).find("option:selected").text() + "-->";
				platformRule = platformRule +  $(this).val() + ",";
			}
			
			
		})
		$("input[name='platformRuleText']").val(text);
		$("input[name='platformRule']").val(platformRule);
	})
	
	function platformRule(){
		var text ="";
		var platformRule = "";
		$("select:not(#hiddenSelect)").each(function(index){
			if($("select:not(#hiddenSelect)").size()-1 == index){
				text = text + $(this).find("option:selected").text();
				platformRule = platformRule +  $(this).val();
			}else{
				text = text + $(this).find("option:selected").text() + "-->";
				platformRule = platformRule +  $(this).val() + ",";
			}
			
			
		})
		$("input[name='platformRuleText']").val(text);
		$("input[name='platformRule']").val(platformRule);
	}
	
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
	window.location.href = ctx + "/borrow/payback/plantskiporder/queryPage";
}


function checkPlatform(){
	var platformId = $("select[name='platformId']").val();
	if(platformId){
		var isConcentrate =  $("input[name='isConcentrate']:checked").val()
		if(isConcentrate){
			$.ajax({  
				type : "POST",
			    data:{"platformId":platformId,
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
}
