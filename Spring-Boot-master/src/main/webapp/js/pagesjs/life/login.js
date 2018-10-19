$(document).ready(function(){
	var addressUrl=getQueryString("addressUrl");
	if(addressUrl != null)
	{
		$('#addressUrl').val(addressUrl);	
	}
	var username=getCookie('username');//获取用户username from cookie
	var password=getCookie('password');//获取用户password from cookie
	if(username !=null && password != null)
	{
		
		window.location.href="mypage.html";
	}
});

function login()
{
	var username=$('#username').val();
	var password=$('#password').val();
	var addressUrl=$('#addressUrl').val();
	var checked=$('.form_link input').is(':checked');
	setCookie('keepuser',checked);
	$.ajax({
		url:'/login?username='+username+'&password='+password,
		async:false,
		success:function(data)
		{
			console.log(data);
			if(data.code == 'success')
 			{
				setCookie('userid',data.id);
				setCookie('username',data.userName);
				setCookie('password',data.password);
				setCookie('point',data.point);
				if(addressUrl == 1)
				{
					window.history.go(-1);
				}else
				{
					window.location.href="mypage.html";	
				}
 			}else{
 				alert('登录失败！');
 			}
		}
	});
}