//敲回车执行input条件查询
$(function(){
document.onkeydown = function(e){ 
var ev = document.all ? window.event : e;
		if(ev.keyCode== 13) {
			$('body form:first').submit();
		}
	}
});
