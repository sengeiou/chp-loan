<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>分配卡号</title>

<script src="${context}/js/grant/disCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
<script type="text/javascript">


$(document).ready(function(){
	
	 // 点击返回
	 $("#bankBtn1").click(function(){
		 window.location.href=ctx+"/channel/jyj/JyjBorrowBankConfigure/getGCDiscardInfo";
	 })
	// 点击提交，将中间人id和放款人员id更新到放款记录表中，同时更新放款状态，走流程
	 $("#disCommitBtn1").click(function(){
		 $(this).attr('disabled',true);
		 if($("#middleName").val()==""||$("#middleName").val()==null){
			 art.dialog.alert("请选择卡号再进行提交");
			 $('#disCommitBtn1').removeAttr('disabled');
			 return false;
		 }else{
			 art.dialog.confirm("确认提交信息?",function() {
			 	commit();
			 },function(){
				$('#disCommitBtn1').removeAttr('disabled');
			 });
		 }
	 });
	 
	 var money = 0,num = 0;
	 $(".cssLess").each(function (){
		 money += parseFloat($(this).val());
		 num ++;
	 });
	 $("#totalMoney").text(fmoney(money,2));
	 $("#totalNum").text(num);
});
/**
 * 弹出历史界面
 * applyId ：借款申请表主键
 */
function showLoanHis(applyId){
 		var url=ctx + "/common/history/showLoanHisByApplyId?applyId="+applyId;
	    art.dialog.open(url, {  
		   id: 'his',
		   title: '信借信息历史',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}
//点击提交，获得applyId等处理的功能
function commit(){
	
	 var ParamEx=$("#param").serialize();
	 var userCode=$("#userCode").val();
	 var deftokenId=$("#deftokenId").val();
	 var deftoken=$("#deftoken").val();
	 middleId = $("#middleId").val();
	 ParamEx+="&userCode="+userCode+"&middleId="+middleId+"&deftokenId="+deftokenId+"&deftoken="+deftoken;
	 var dialog = art.dialog({
	      content: '正在提交...',
	      cancel:false,
	      lock:true
		});
	 $.ajax({
			type : 'post',
			url : ctx+'/channel/goldcredit/discard/disCardCommit',
			data :ParamEx,
			cache : false,
			success : function(result) {
				var obj = eval("("+result+")");
				dialog.close();
				art.dialog.alert(obj.message,function(){
					window.location.href=ctx+"/channel/jyj/JyjBorrowBankConfigure/getGCDiscardInfo";
			  	});
				 /* if(result=="0")
				{
					art.dialog.alert("分配卡号成功",function(){
						window.location.href=ctx+"/channel/goldcredit/discard/fetchTaskItems";
				  	});
				}
				else if(result=="1")
				{
					art.dialog.alert("CA签章异常",function(){
						window.location.href=ctx+"/channel/goldcredit/discard/fetchTaskItems";
				  	});
				}
				else if(result=="3")
				{
					art.dialog.alert("已经提交过，请不要重复提交");
				}
				else
				{
					art.dialog.alert("分配卡号失败",function(){
						window.location.href=ctx+"/channel/goldcredit/discard/fetchTaskItems";
				  	});
				} */
				$('#disCommitBtn1').removeAttr('disabled');
			},
			error : function(result) {
				/*var resultText=result.responseText;
				 if(resultText!=null&&resultText.indexOf("2;")>-1){
					resultText=resultText.split(";");
					if(resultText[0]=="2"){
						art.dialog.alert("分配卡号失败，出现异常的借款编号："+resultText[1],function(){
							window.location.href=ctx+"/channel/goldcredit/discard/fetchTaskItems";
					  	});
					}
				}else{
					art.dialog.alert('请求异常！');
				} */
				dialog.close();
				art.dialog.alert('请求异常！');
				$('#disCommitBtn1').removeAttr('disabled');
			}
		});
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
</script>
</head>
<body>
        <h4 class=" pl10  f14">已选客户</h4>
        <input type="hidden" id="flag" value='${flag}'>
        <table class="table  table-bordered table-condensed table-hover ">
        <thead>
            <tr>
                <th>合同编号</th>
                <th>客户姓名</th>
                <th>放款金额</th>
                <th>渠道</th>
                <th>操作</th>
            </tr>
          </thead>
          <tbody>
          <form id="param">
	            <c:forEach items="${list}" var="item" varStatus="stat">
	            <tr>
	                <td>${item.contractCode}</td>
	                <td>${item.customerName}</td>
	                <td><fmt:formatNumber value='${item.contractMoney}' pattern="#,#00.00"/></td>
	                <td>${item.channelName}</td>
	                <td>
	                	 <input type="button" class="btn_edit" onclick="showLoanHis('${item.applyId}')" value="历史"/>
	                </td>
	                <input type="hidden" name="list[${stat.index}].loanCode" value='${item.loanCode}'></input>
	                <input type="hidden" name="list[${stat.index}].applyId" value='${item.applyId}'></input>
	               <%--  <input type="hidden" name="list[${stat.index}].workItemView.wobNum" value='${item.wobNum}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.flowName" value='${item.flowName}'></input>
	                
	                <input type="hidden" name="list[${stat.index}].workItemView.token" value='${item.token}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.stepName" value='${item.stepName}'></input> --%>
	                <input type="hidden" name="list[${stat.index}].contractCode" value='${item.contractCode}'></input>
	                <input type="hidden" name="list[${stat.index}].loanInfoOldOrNewFlag" value='${item.loanInfoOldOrNewFlag}'></input>
	            	<input type = "hidden" class = "cssLess" value = "${item.contractMoney}"/>
	            </tr>
	            </c:forEach>
          </form>  
            </tbody>
        </table>
        <label class = "red">累计放款金额(元)：<span id = "totalMoney"></span>&nbsp;&nbsp; 累计放款数量(条)：<span id  = "totalNum"></span></label>
        <h4 class="f14 pl10 mt20">输入放款信息</h4>
    <div class="control-group pb10 ">
     <input type="hidden" id="userCode" name = "userCode"/>
     <input type = "hidden" id = "middleId" name = "middleId"/>
     <input type="hidden" id="deftokenId" value='${deftokenId}'></input>
     <input type="hidden" id="deftoken" value='${deftoken}'></input>
        <table cellspacing="0" cellpadding="0" border="0"  class="table3" width="100%">
            <tr>
                <td>&nbsp;&nbsp;&nbsp;放款账户：<input type="text" class="input_text178" readonly="readonly" id="middleName"/>&nbsp;
                <input type="text" class="input_text178" readonly="readonly" id="midBankName"/>&nbsp;
                <input type="text" class="input_text178" readonly="readonly" id="bankCardNo"/>
				<input type="button" class="btn btn-small" style="margin-bottom:4px" value="选择账户" id="selectMiddleBtn"></input>
				</td>
            </tr> 
            <tr> <td id="001"></td></tr>
        </table>
      </div>
            <div class="tright mt10 mr34 ">
            <button class="btn btn-primary" id="disCommitBtn1">提交</button>
             <button class="btn btn-primary" id="bankBtn1">返回</button>
     </div>
</body>
</html>