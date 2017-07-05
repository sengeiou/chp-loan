<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行平台接口列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#centerapplyPayForm").attr("action", "${ctx}/borrow/payback/bankplantport/queryPage");
		$("#centerapplyPayForm").submit();
		return false;
	}

	var bankPlant = {};
	/**
	 * 保存
	 */
	 bankPlant.save = function() {
		
		$("#addDue").bind('click', function() {
			  $("#bankCode").val("");
			  $("#plantCode").val("");
			  $("#isConcentrate").val("");
			  $("#bankCode").change();
			  $("#plantCode").change();
			  $("#isConcentrate").change();
			art.dialog({
			    content: document.getElementById("addDueDiv"),
			    title:'保存',
			    fixed: true,
			    lock:true,
			    id: 'confirm',
			    okVal: '确认保存',
			    ok: function () {
			    	save();
					return false;
			    },
			    cancel: true
			});
		});
	}
	 /**
	  * 修改
	  */
	  bankPlant.update = function() {
	 	
	 	$("#toUnUse").bind('click', function() {
	 		if($("input:checkbox[name='checkBox']:checked").length==0){
	 			alert("请选择要修改的数据！");
	 			return false;
	 		}
	 		if($("input:checkbox[name='checkBox']:checked").length>1){
	 			alert("请选择一条数据修改！");
	 			return false;
	 		}
	 		var id = $("input:checkbox[name='checkBox']:checked").val();
	 		$.ajax({  
				   type : "POST",
				   async:false,
				   data:{
					   id:id
				   },
				   url : ctx+"/borrow/payback/bankplantport/getBean",
				   datatype : "json",
				   success : function(data){
					   var data = eval( "(" + data + ")" );
					   $("#bankCode").val(data.bankCode);
					   $("#plantCode").val(data.plantCode);
					   $("#batchFlag").val(data.batchFlag);
					   $("#isConcentrate").val(data.isConcentrate );
					   $("#id").val(data.id);
					   
					   $("#bankCode").trigger("change");
					   $("#plantCode").trigger("change");
					   $("#batchFlag").trigger("change");
					   $("#isConcentrate").trigger("change");
					   
				}
	 		});
	 		
	 		art.dialog({
	 		    content: document.getElementById("addDueDiv"),
	 		    title:'修改',
	 		    fixed: true,
	 		    lock:true,
	 		    id: 'confirm',
	 		    okVal: '确认修改',
	 		    ok: function () {
	 		    	save();
	 				return false;
	 		    },
	 		    cancel: true
	 		});
	 	});
	 };
	  $(document).ready(function() {
		  bankPlant.save();
		  bankPlant.update();
	  });
	  function save(){
		  var bankCode= $("#bankCode").val();
		  var plantCode= $("#plantCode").val();
		  var batchFlag= $("#batchFlag").val();
		  var isConcentrate = $("#isConcentrate").val();
		  var flag =  changebank();
		  if(!flag){
			 return false; 
		  }
		  var id = $("#id").val();
			$.ajax({  
				   type : "POST",
				   async:false,
				   data:{
					   bankCode:bankCode,
					   plantCode:plantCode,
					   batchFlag:batchFlag,
					   isConcentrate:isConcentrate,
					   id:id
				   },
				   url : ctx+"/borrow/payback/bankplantport/save",
				   datatype : "json",
				   success : function(msg){
					   if(id){
						   msg="修改成功"; 
					   }else{
						   msg = "保存成功";
					   }
					    art.dialog(
					    {
					    	content: msg,
						    title:'银行平台接口',
						    fixed: true,
						    lock:true,
						    id: 'oflineconfirm',
						    okVal: '确认',
						    ok: function () {
						    	window.location.href=ctx+"/borrow/payback/bankplantport/queryPage";
								return false;
						    }
					       }		
					     );
					},
				   error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
						}
				});
	  }
	  
	  function del(){
			if($("input:checkbox[name='checkBox']:checked").length==0){
	 			alert("请选择要修改的数据！");
	 			return false;
	 		}
			var ids='';
			$("input:checkbox[name='checkBox']:checked").each(function(){
			    	ids=ids+","+$(this).val();
			   })
			
			$.ajax({  
				   type : "POST",
				   async:false,
				   data:{
					   id:ids
				   },
				   url : ctx+"/borrow/payback/bankplantport/delete",
				   datatype : "json",
				   success : function(msg){
					  msg = '删除成功！'
					    art.dialog(
					    {
					    	content: msg,
						    title:'银行平台接口',
						    fixed: true,
						    lock:true,
						    id: 'oflineconfirm',
						    okVal: '确认',
						    ok: function () {
						    	window.location.href=ctx+"/borrow/payback/bankplantport/queryPage";
								return false;
						    }
					       }		
					     );
					},
				   error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
						}
				});
	  }
	//点击清除按钮调用的的方法
	  function resets(){
	  	// 清除text	
	    $(":input").val("");
	  	$("#centerapplyPayForm").submit();
	  }
	
	   function changebank(obj){
		   var flag = false;
		   var bankCode = $("#bankCode").val();
		   var plantCode = $("#plantCode").val();
		   var isConcentrate = $("#isConcentrate").val();
		   var batchFlag = $("#batchFlag").val();
		   var id = $("#id").val();
		   var concentrateName = "";
		   if(bankCode && plantCode && isConcentrate){
			   if(isConcentrate == '0'){
				   concentrateName = "集中";
			   }else{
				   concentrateName = "非集中";
			   }
			   $.ajax({  
				   type : "POST",
				   async:false,
				   data:{
					   'bankCode':bankCode,
					   'plantCode':plantCode,
					   'isConcentrate':isConcentrate,
					   'id' : id
				   },
				   url : ctx+"/borrow/payback/bankplantport/querybank",
				   datatype : "json",
				   success : function(data){
					   if(data == 'false'){
						   art.dialog.alert("银行平台已经存在！请重新选择");
						   return false;
					   }
					   flag = true;
					},
				   error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
							return false;
						}
				});
		   }
		   return flag;	
	   }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/borrow/payback/bankplantport/queryPage">银行平台接口列表</a></li>
		<li><a href="${ctx}/borrow/payback/plantskiporder/queryPage">平台跳转顺序列表</a></li>
	</ul>
	<div class="control-group">
	<form method="post" id="centerapplyPayForm">
       <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
            <tr>
                <td><label class="lab">预约银行：</label>
                   <select class="select180" name="bankCode">
				    <option value="">请选择</option>
			         <c:forEach var="bank" items="${fns:getDictList('jk_open_bank')}">
						<option value="${bank.value }" <c:if test="${record.bankCode==bank.value }">selected</c:if> >${bank.label}</option>
					 </c:forEach>
				  </select>
                </td>
                
                <td><label class="lab">集中：</label>
                  <select class="select180" name="isConcentrate">
				    <option value="">请选择</option>
			        <c:forEach var="concentrate" items="${fns:getDictList('yes_no')}">
						<option value="${concentrate.value }"   <c:if test="${record.isConcentrate==concentrate.value }">selected</c:if> >${concentrate.label}</option>
					</c:forEach>
				  </select>
              </td>
            </tr>
        </table>
        <div class="tright pr30 pb5">
        <button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
        <button class="btn btn-primary" onclick ='resets()'>清除</button></div>
   </form>
   </div>
	<p class="mb5"> 
    <button id="addDue" role="button" class="btn btn-small">新增</button>
	<button class="btn btn-small" id="toUnUse">修改</button>
    <button class="btn btn-small" onclick ='del()'>删除</button></p>
	<div class="box5"> 
        <table  class="table  table-bordered table-condensed" >
        <thead>
            <tr>
                <th><!-- <input type="checkbox" class="checkbox" id="checkAll"/> --></th>
               	<th>序号</th>
                <th>银行</th>
                <th>平台</th>
                <th>批量或实时</th>
                <th>集中非集中</th>
            </tr>
         </thead>   
        <c:if test="${page.list!=null && fn:length(page.list)>0}">
         <c:forEach items="${page.list}" var="bankPlantPort" varStatus="status">
            <tr>
                 <td><input type="checkbox" name="checkBox" value="${bankPlantPort.id }"/>
                 </td>
                 <td>${(page.pageNo-1)*page.pageSize+status.count}</td>
                 <td>${bankPlantPort.bankName}</td>
                 <td>
                    ${bankPlantPort.plantName}
                 </td>
                
                   <td>
                    ${bankPlantPort.batchFlag}
                 </td>
                  <td>
                    ${bankPlantPort.isConcentrate}
                 </td>
            </tr>
			</c:forEach>
		</c:if>
        <c:if test="${ page.list==null || fn:length(page.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
        </c:if>
      </table>
    </div>
<div class="pagination">${page}</div>
    <!-- 新增银行平台接口弹框 -->
<div  id="addDueDiv" style="display: none">
       <input type ="hidden"  id="id" >
       <table id="backTB" class="table4" cellpadding="0" cellspacing="0" border="0" width="100%"> 
           <tr>
               <td><label class="lab">银行：</label> 
                     <select class="select180" id="bankCode">
				    <option value="">请选择</option>
			        <c:forEach var="bank" items="${fns:getDictList('jk_open_bank')}">
						<option value="${bank.value }">${bank.label}</option>
					</c:forEach>
				  </select>
               </td>
           </tr>
           <tr>
               <td><label class="lab">平台：</label>
                    <select class="select180"  id="plantCode">
					<option value="">请选择</option>
                        <c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
							<option value="${plat.value }">${plat.label }</option>
						</c:forEach>
					</select>
				</td>
           </tr>
           <tr>
              <td><label class="lab">批量或实时：</label>
                  <select class="select180" id="batchFlag">
				    <option value="">请选择</option>
			        <c:forEach var="flag" items="${fns:getDictList('com_deduct_type')}">
						<option value="${flag.value }">${flag.label}</option>
					</c:forEach>
				  </select>
			  </td>
           </tr>
           <tr>
              <td><label class="lab">集中/非集中:</label>
                  <select class="select180" id="isConcentrate" >
				    <option value="">请选择</option>
			        <c:forEach var="isConcentrate" items="${fns:getDictList('yes_no')}">
						<option value="${isConcentrate.value }">${isConcentrate.label}</option>
					</c:forEach>
				  </select>
              </td>
          </tr>
      </table>
</div>
</body>
</html>