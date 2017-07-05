
$(function(){
		
		$("#searchBtn111").click(function(){
			 $("#statisticsFlag").val("");
			  $("#deductPlant").submit();
			
		})
		
		$("#searchBtn").click(function(){
			 $("#statisticsFlag").val("");
			  $("#deductPlant").submit();
			
		})
		
		
		//清除按钮绑定事件
		$("#clearBtn").click(function(){ 
			$("#statisticsFlag").val("");
			$(":input").not(":button").val("");
			$("#deductPlant").submit();
		});
		
		
		
		$("#sumBtn").click(function(){
			  $("#statisticsFlag").val("YES");
			  $("#deductPlant").attr("action",ctx+"/borrow/payback/deductPlantStatisticsReport/periodStatistics");
			  $("#deductPlant").submit();
			  $("#deductPlant").attr("action",ctx+"/borrow/payback/deductPlantStatisticsReport/queryPage");
			
		})
	})
	function exportAll(){
	 var statisticsFlag =  $("#statisticsFlag").val();
	   if(statisticsFlag == "YES"){
		   $("#deductPlant").attr("action",ctx+"/borrow/payback/deductPlantStatisticsReport/exportPeriodExcel");
	   }else{
		   $("#deductPlant").attr("action",ctx+"/borrow/payback/deductPlantStatisticsReport/exportExcel");
	   }
	   $("#deductPlant").submit();
	   $("#deductPlant").attr("action",ctx+"/borrow/payback/deductPlantStatisticsReport/queryPage");
}
