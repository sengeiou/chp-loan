<%@ page contentType="text/html;charset=UTF-8"%>

<!-- 删除弹框提示开始 -->

	<form id="delForm">
		<input type="hidden" name="loanCode"  /> 
		<input type="hidden" name="operateStep"  /> 
		<input type="hidden" name="buttonId" id="buttonId"/>
			<!--删除弹框  -->
		    <div  id="del_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
			<div class="modal-dialog" role="document">
			<div class="modal-content">
			<div class="modal-header">
		   	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		
			<div class="l">
		         		 <h4 class="pop_title">删除提示</h4>
		       	 	</div>
		 	</div>
		 	<div class="modal-body">
						<table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%" id="tpTable">
			       	  	  	<tr>
								<td><lable class="lab"><span style="color: red;">*</span>删除原因：(50字以内)</lable></td>
								<td>
								<textarea rows="" cols="" class="textarea_refuse required" maxlength="50" id="reason" name="reason"></textarea>
								</td>
							</tr>
						</table>
		 	</div>
		 	
		 	<div class="modal-footer">
		 	<button id="delSure" class="btn btn-primary"  aria-hidden="true" >确定</button>
		 	<button id="cancel" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
		 	</div>
			</div>
			</div>
		
			</div>
	</form>
	
	<!-- 删除弹框提示结束-->