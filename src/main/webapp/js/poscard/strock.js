 $(document).ready(function(){
 
		 $('#takeSingle').bind('click',function(){
				var url = ctx + '/telesales/customerManagement/findTelesaleOrderList';
				art.dialog.open(url, {
					title : '取单',
					width : 800,
					lock:true,
					height : 450,
					close:function()
					{
						$("form:first").submit();
				    }
		     });
		 }); 
	  
	 $('#searchBtn').bind('click',function(){
		$('#inputForm').submit(); 
	 });
	 
	 $('#clearBtn').bind('click',function(){
	   $(':input','#inputForm')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected'); 
	   $('#telesalesFlag').val('');
	   $("#telesalesFlag").trigger("change");
	   $('#inputForm').submit(); 
	 });
	 $('#showMore').bind('click',function(){
		show(); 
	 });
	 $("input[name='openFormBtn']").each(function(){
		 $(this).bind('click',function(){
			 $('#flowCode').val($(this).attr('flowCode'));
			 $('#customerCode').val($(this).attr('customerCode'));
			 $('#consultId').val($(this).attr('consultId'));
			 $('#dealType').val($(this).attr('dealType'));
			 $('#openLaunchForm').submit();
		 });
	 });
	 autoMatch("teamManagerName","teamManagerCode");
	 autoMatch("customerManagerName","customerManagerCode"); 
	 
 });
function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/borrow/borrowlist/fetchItemsForLaunch");
		$("#inputForm").submit();
		return false;
 }