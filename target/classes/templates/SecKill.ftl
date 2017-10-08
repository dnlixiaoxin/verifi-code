<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>iPhoneX !!!  1元秒杀！！！
</h1>


<form id="msform" action="${APP_PATH}/doseckill">
	<input type="hidden" id="prodid" name="prodid" value="0101">
	<input type="button"  id="miaosha_btn" name="seckill_btn" value="秒杀点我"/>
</form>

</body>
<script  type="text/javascript" src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script  type="text/javascript">
$(function(){
	$("#miaosha_btn").click(function(){	 
			$.ajax({
			 url:"${APP_PATH}/doseckill",
			 type:"post",
			 dataType:"json",
			 data:{
			 	prodid:$("#prodid").val()
			 },
			 success:function(result){
			    if(!result.success){
			      alert("抢光了" );
    			  $("#miaosha_btn").attr("disabled",true);
			    }
			 }
		});
	})
})
</script>
</html>