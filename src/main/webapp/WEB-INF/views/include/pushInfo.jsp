<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.creditharmony.core.cms.type.PushInfoUtil"%>
<%@page import="com.creditharmony.common.util.SpringContextHolder"%>
<% 
String pushUrl = ((PushInfoUtil)SpringContextHolder.getBean("pushInfoUtil")).getInitPushUrl();
request.setAttribute("pushUrl", pushUrl); %> 
<script type="text/javascript" src="${ctxStatic}/common/comet4j2.js"></script>
<script type="text/javascript" src="${ctxStatic}/Lobibox/js/Lobibox.js"></script>
<link rel="Stylesheet" href="${ctxStatic}/Lobibox/css/lobibox.css" />
<script type="text/javascript">
var cometlist= new Array();
function init(){
    JS.Engine.on({
            loan : function(kb){
            	var kbS=kb.split("##");
            	if(cometlist.indexOf(kbS[0]) == -1){
            		Lobibox.notify('info', {
                        size: 'large',
                        img:false,
                        icon: true,
                        id:kbS[0],
                        url:kbS[4]+"?ssousr=<%=request.getRemoteUser() %>",
                        title: kbS[2],
                        urlPath:'${ctx}',
                        showClass: 'fadeInDown',
                        hideClass: 'fadeUpDown', 
                        sound: '${ctxStatic}/Lobibox/sounds/sound6',
                        msg: kbS[3]	
                	});
                	cometlist.push(kbS[0]);
            	}  
            		
            		//$.ajax({
                	//	type : "POST",
                	//	url : "${ctx}/cmsPush/delete",
                	//	data : {id:me.$options.id},	
                	//	async:false,
                	//	success : function(result) {
                	//		window.open(me.$options.url,"_blank");
                	//	}
                	//}); 
            }
    });
    JS.Engine.start('${pushUrl}','userId='+<%=request.getRemoteUser() %>);
    JS.Engine.on(
    'start',function(cId,channelList,engine){
    });
}
    window.onload=init;
</script>