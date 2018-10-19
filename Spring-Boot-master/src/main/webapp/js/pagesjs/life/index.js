var userid;
$(document).ready(function(){
	userid=getCookie('userid');//获取用户userid from cookie
	if(getCookie('keepuser')=='false')
	{
		delCookie("userid");
		delCookie("username");
		delCookie("password");
		delCookie("point");
		delCookie("keepuser");
	}
	loadjfq();
});
//加载街坊圈
function loadjfq()
{
	$.ajax({
		url:'/getArticelsBymodel/11/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				console.log(Data.outUrl);
				$('.neighbor').attr('href',Data.outUrl);
			});
		}
	});
}