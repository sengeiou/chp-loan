var reviewMeetApply = {};
reviewMeetApply.changeTabs = function(url, flag){
	$('#frameFlowForm').attr('action', url + "&flag=" + flag);
    $('#frameFlowForm').submit();
};