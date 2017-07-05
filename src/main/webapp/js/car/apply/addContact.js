var contact={};
contact.delRow=function(obj,tableId,ItemType){
	if($('#'+tableId+" tr").length==2){
		art.dialog.alert("当前行禁止删除！");
		return false;
	}
	art.dialog.confirm("是否确认删除",
			function(){
	var id = $(obj).parent().parent().find("td :input[name$='.id']").val();
	if(id!= null && id!='' && id!= undefined){
		   	$.ajax({
		        		type:'post',
		        		url:ctx+"/apply/dataEntry/delAdditionItem",
		        		data:{
		        			'delType':ItemType,
		        			'tagId':id
		        		},
		        		dataType:'json',
		        		success:function(data){
		        			if(data){
		        				$(obj).parent().parent().remove();
		        				art.dialog.alert("删除成功！");
		        			}else{
		        				art.dialog.alert("删除失败！");
		        			}
		        		},
		        		error:function(){
		        			art.dialog.alert("服务器异常！");
		        		}
				
		        	});
	}else{
		 $(obj).parent().parent().remove();	
	}
	});
};