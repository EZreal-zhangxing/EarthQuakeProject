var articelId;
$(document).ready(function(){
	articelId=getQueryString('articelId');//获取文章ID
	$.ajax({
		url:'/getArticelContent/'+articelId,
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				console.log(Data);
				console.log(Data.id);
				if(Data.isOrder == 0)
				{
					$('.order').hide();	
				}
				var time = new Date(Data.date).Format("yyyy-MM-dd");
				$('.signUp').append('<h1>'+Data.title+'</h1>'+
					'<span class="time">'+time+'</span>'+
					'<div><p>'+Data.content.replace(/div/g,'p')+'</p></div>');
			});
		}
	});
});

function showmodel()
{
	$('#articeId').attr('value',articelId);
	$(".msg_box").show();
	$(".shadow").show();
}
//参与我们
$(".sub_btn").click(function(){
	var userid=getCookie('userid');//获取用户userid from cookie
	if(userid != null && userid != '')
	{
		var articeId=$('#articeId').val();
		var name=$('#name').val();
		var userTel=$('#userTel').val();
		var userAddress=$('#userAddress').val();
		var comAddress=$('#comAddress').val();
		var modelId=10;
		$(".msg_box").hide();
		$(".shadow").hide();
		$.ajax({
			url:'/order?name='+name+'&userTel='+userTel+'&userAddress='+userAddress+'&comAddress='+comAddress+'&articelId='+articeId+'&modelId='+modelId+'&userId='+userid,
			async:false,
			success:function(data){
				if(data.code == 'success')
	 			{
					alert(data.message);
					window.location.reload();
	 			}else{
	 				alert(data.message);
	 			}
				$('#articeId').attr('value','');
				$('#name').attr('value','');
				$('#userTel').attr('value','');
				$('#userAddress').attr('value','');
				$('#comAddress').attr('value','');
			}
		});
	}else
	{
		alert("请先登录！");
		window.location.href="/life/login.html?addressUrl=1";
	}
	
});
