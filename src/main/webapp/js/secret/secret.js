var secret = {};

secret.automatch = function(inputId) {
	$('#' + inputId).autocomplete({
		source : function(query, process) {
			var matchCount = this.options.items;// 返回结果集最大数量
			$.post(ctx + "/common/userinfo/userdata", {
				"userCode" : query,
				"matchCount" : matchCount
			}, function(respData) {
				return process(respData);
			});
		},
		formatItem : function(item) {
			return item["name"] + "(" + item["userCode"] + ")";
		},
		setValue : function(item) {
			$('#' + inputId + 'Code').val(item["userCode"]);
			return {
				'data-value' : item["name"],
				'real-value' : item["userCode"]
			};
		}
	});
};

// 点击暗访列表的办理按钮,查询暗访信息
function secretTra(obj){
	var contractCode = obj.id;
	var url = ctx +"/borrow/secret/getInfoByCode?contractCode="+contractCode;
	window.location.href = url; 
};

// 暗访完成按钮
function secretOk(obj){	
	var loanCode = obj.id;
	var url = ctx +"/borrow/secret/updateSecret?loanCode="+loanCode;
	window.location.href = url;
}