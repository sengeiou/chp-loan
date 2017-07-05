$(document).ready(function() {
		loan.getstorelsit("orgName","storeId");
		/*$('#showMore').bind('click',function(){
			 show();

		});*/
		
		// 点击全选
		$("#checkAll").click(function(){
			var count = 0;
			var contract = 0.00;
			var grant = 0.00;
			var audit = 0.00;
			if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
			{
				$(":input[name='checkBoxItem']").each(function() {
					$(this).attr("checked",'true');
					count += 1;
					contract += parseFloat($.isBlank($(this).attr("contractAmount")),10);
					grant += parseFloat($.isBlank($(this).attr("grantAmount")),10);
					audit += parseFloat($.isBlank($(this).attr("auditAmount")),10);
				});
			}else{
				$(":input[name='checkBoxItem']").each(function() {
					$(this).removeAttr("checked");
				});
				count = $('#hiddenCount').val();
				contract = $('#hiddenContract').val();		
				grant = $('#hiddenGrant').val();
				audit = $('#hiddenAudit').val();
			}
			setValue(count,contract,grant,audit);
		});
		$(":input[name='checkBoxItem']").click(function(){
			var count1 = 0;
			var contract1 = 0.00;
			var grant1 = 0.00;
			var audit1 = 0.00;
			var t = true;
			$(":input[name='checkBoxItem']").each(function() {
				if($(this).attr('checked')=='checked'|| $(this).attr('checked'))
				{
					count1 += 1;
					contract1 += parseFloat($.isBlank($(this).attr("contractAmount")),10);
					grant1 += parseFloat($.isBlank($(this).attr("grantAmount")),10);
					audit1 += parseFloat($.isBlank($(this).attr("auditAmount")),10);
					t = false;
				}
			});
			if(t){
				$(":input[name='checkBoxItem']").each(function(){
					grant1 += parseFloat($.isBlank($(this).attr("grantAmount")));
					contract1 += parseFloat($.isBlank($(this).attr("contractAmount")));
					audit1 += parseFloat($.isBlank($(this).attr("auditAmount")),10);
					count1 += 1;
				});
			}
			setValue(count1,contract1,grant1,audit1);
		});
		setValue($('#hiddenCount').val(),$('#hiddenContract').val(),$('#hiddenGrant').val(),$('#hiddenAudit').val());
});

(function($){
	  $.isBlank = function(obj){
	    return(!obj || $.trim(obj) === "") ? 0 : obj;
	  };
})(jQuery);
		
function clear1(){
	$("#pushDate").val('');
	$("#loanCustomerName").val('');
	$("#orgName").val('');
	$("#storeId").val('');
	$("#loanStatus").val('');
	$("#loanStatus").trigger("change");
	$("#lendingTime").val('');
	$("#paperlessFlag").val('');
	$("#paperlessFlag").trigger("change");
	$("#legalMan").val('');
	$("#legalMan").trigger("change");
	$("#contractVersion").val('');
	$("#contractVersion").trigger("change");
}

function setValue(count,contract,grant,audit) {
	$("#count").text(count);
	$("#contract").text(fmoney(contract,2));
	$("#grant").text(fmoney(grant,2));
	$("#audit").text(fmoney(audit,2));
}		

//格式化，保留两个小数点
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