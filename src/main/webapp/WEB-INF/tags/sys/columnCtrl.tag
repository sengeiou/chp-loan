<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="pageToken" type="java.lang.String" required="true" description="页面token"%>
<div class="column_ctrl" style="line-height: 30px">
&nbsp;<b>选择列：</b><!-- <input type='checkbox' id = 'allselect'/>全选 -->
</div>
<script>

jQuery(document).ready(function($){
	var ignore=["","全选","序号","合同编号","借款编号","客户姓名","操作"];
	$ctrl = $('.column_ctrl');
	var $table=$("#tableList");
	var savedStatus = $.cookie('${pageToken}');
	
	if($table.length == 0){
		return false;
	}
	var $columns = $table.find('th');
	//该页面cookie不存在
	if(savedStatus==null){
		savedStatus={};
		$.each($columns,function(){
			savedStatus[$(this).text()]=true;
		});
		$.cookie('${pageToken}', JSON.stringify(savedStatus));
	}else{
		savedStatus = JSON.parse(savedStatus);
	}
	$.each($columns,function(index){
		var self = $(this);
		
		var columnName=$(this).text().trim();
		if($.inArray(columnName, ignore) == -1){
			var checkbox = $("<input name='cellcheckbox' type='checkbox'/>"+columnName);
			//注册点击事件
			checkbox.on('click',function(){
				if ($('#reportTableDiv')) {
					if($(checkbox).prop('checked')) {
						$('#reportTableDiv table thead').find('th:eq('+index+')').css("display", "table-cell");
					} else {
						$('#reportTableDiv table thead').find('th:eq('+index+')').css("display", "none");
					}
				}
				$.each($table.find('tr'),function(){
					if($(checkbox).prop('checked')){
						$(this).find('th:eq('+index+'),td:eq('+index+')').show();
					}else{
						$(this).find('th:eq('+index+'),td:eq('+index+')').hide();
					}	
				});
				//cookie更新
				savedStatus[columnName]=$(checkbox).prop('checked');
				$.cookie('${pageToken}', JSON.stringify(savedStatus));
			});
			$ctrl.append(checkbox);
			
			//初始化
			if(savedStatus[columnName]){
				//显示
				$(checkbox).prop('checked',true);
				if ($('#reportTableDiv')) {
					$('#reportTableDiv table thead').find('th:eq('+index+')').css("display", "table-cell");
				}
				$.each($table.find('tr'),function(){
					$(this).find('th:eq('+index+'),td:eq('+index+')').show();
				});	
			}else{
				//隐藏
				$(checkbox).prop('checked',false);
				if ($('#reportTableDiv')) {
					$('#reportTableDiv table thead').find('th:eq('+index+')').css("display", "none");
				}
				$.each($table.find('tr'),function(){
					$(this).find('th:eq('+index+'),td:eq('+index+')').hide();
				});	
			}
		}
		
	});
	
	$("#allselect").click(function(){
		 if(this.checked){
			 $.each($columns,function(index){ 
				 var columnName=$(this).text().trim();
				 if($.inArray(columnName, ignore) == -1){
				 $.each($table.find('tr'),function(){
					 $(this).find('th:eq('+index+'),td:eq('+index+')').show();
					 })
				 }
			 }); 
			 $("input[name='cellcheckbox']").attr("checked", true);  
		}else{
			$.each($columns,function(index){ 
				 var columnName=$(this).text().trim();
				 if($.inArray(columnName, ignore) == -1){
					 $.each($table.find('tr'),function(){
						 $(this).find('th:eq('+index+'),td:eq('+index+')').hide();
					 })
				 }
			 })
			 $("input[name='cellcheckbox']").attr("checked", false);  
		} 
	})
});
</script>