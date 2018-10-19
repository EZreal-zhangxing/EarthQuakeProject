var userid;
var point;
$(document).ready(function(){
	userid=getCookie('userid');//获取用户userinfo from cookie
	dayloginaddpoint();//每天登陆加分
});
function singin()
{
	$.ajax({
		url:'/singin?userid='+userid,
		async:false,
		type:'post',
		success:function(data){
			console.log(data);
			if(data.code == 'success')
			{
				$(".sign_success").show();
				$(".shadow").show();
			}else if(data.code == 'exist')
			{
				alert("本日您已签到！");
			}else if(data.code == 'fail')
			{
				alert("签到失败！");
			}
			getpoint();
			//window.location.reload();
		}
	});
}
function getpoint()
{
	$.ajax({
		url:'/getPoint/'+userid,
		async:false,
		type:'post',
		success:function(data){
			point=data;
			setCookie('point',data);//重设point
			var username=getCookie('username');//获取用户userinfo from cookie
			if(userid == null)
			{
				window.location.href="login.html";
			}else
			{
				console.log(point);
				$('.my_tit_left dd .my_gold').html(data);
				$('.my_tit_left dd .my_name').html(username);
			}
		}
	});
}function dayloginaddpoint()
{
	$.ajax({
		url:'/loginaddpoint/'+userid,
		async:false,
		type:'post',
		success:function(data){
			getpoint();
		}
	});
}
