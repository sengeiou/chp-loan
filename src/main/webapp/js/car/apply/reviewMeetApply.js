var reviewMeetApply = {};
reviewMeetApply.changeTabs = function(url){
	$('#frameFlowForm').attr('action',url);
    $('#frameFlowForm').submit();
};


//var reviewMeetApply2 = {};
//var clickedUrl = [];
//reviewMeetApply2.changeTabs = function(url){
//	if ($.inArray(url, clickedUrl) == -1) { // 数组中不包含url
//		clickedUrl.push(url);
//	} else {
//		$('#frameFlowForm').attr('action',url);
//	    $('#frameFlowForm').submit();
//	}
//};