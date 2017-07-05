$(document).ready(function() {	
	setInterval("noticeShow()","600000");//每隔6000毫秒执行一次testFunction()函数，执行无数次。
});

function noticeShow()
{
	art.dialog.notice({
	    title: '系统提示',
	    id:'tips',
	    width: 220,// 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
	    content: '您有待办任务，请及时处理',
	    icon: 'face-sad',
	    time: 5
	});
}