


// 显示历史弹框事件
function showDeductHirstory(applyId){
	  var wid = screen.width/2 + 150;
		var hei = screen.height/2 + 100;
		var popwin = window.open(ctx+"/borrow/payback/payBackSplitapply/getHirstory?applyId="+applyId,"window","width="+wid+",height="+hei+",status=no,scrollbars=yes");		
		popwin.moveTo(screen.width/4,screen.height/4);
		popwin.focus();
}
