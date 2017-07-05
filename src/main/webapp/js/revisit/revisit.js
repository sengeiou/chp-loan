	/**
	清除查询表单中的数据项
	 一定要加[0]
	 */
	function queryFormClear(tarForm) {
		 $(':input','#'+tarForm)
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 if($("select").length>0){
				$("select").each(function(){
					$(this).trigger("change");
				});
			}
	}
	
	/**
	 全选事件
	 @Parameter checked 全选框的check状态
	 */
	function selectAll(checked) {
		$("input[name='checkBoxItem']").each(function() {
			$(this).attr('checked', checked);
		});
	}
	/**
	 查询历史 
	 */
	function viewAuditHistory(loanCode) {
		showAllHisByLoanCode(loanCode);
	}
	
    function display(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
		}
   }
	
