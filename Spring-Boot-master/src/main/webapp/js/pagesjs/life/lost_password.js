var checkusername=false;
function checktel()
{
	var username=$('#username').val();
	re = /^1\d{10}$/;
	if(username!='')
	{
		if (re.test(username)) {
	    	$.ajax({
				url:'/checkusernamehasregist?username='+username,
				type:'POST',
				async:'false',
				success:function(data){
	    			if(data.code=='fail')
	    			{
	    				alert("该号码尚未注册！请先注册。");
	    			}else
	    			{
	    				checkusername=true;	
	    				$("#send").attr("onclick", "");
						$("#send").css("background","#DCDCDC");
						sendcode();
						djstime();
	    			}
				}
			});
	    } else {
	        alert("请输入正确手机号码！");
	    }
	}
}
function sendcode()
{
	var username=$('#username').val();
	re = /^1\d{10}$/;
	if(username!='')
	{
		if (re.test(username)) {
	    	$.ajax({
				url:'/sendCode/'+username+'/2',
				type:'POST',
				async:'false',
				success:function(data){
	    			console.log(data);
	    			setCookie('userid',data.id);
				}
			});
	    } else {
	        alert("请输入正确手机号码！");
	    }
	}
}
function clicksend()
{
	checktel();		
}
//	$("#send").click(function(){
//		
//	});
/*验证码倒计时*/
function djstime(){
	var e1=$("#send").first();
	var i=60;
	var interval=setInterval(function(){
		e1.html("剩余("+i+")秒");
		i--;
		if(i<0){
			$("#send").css({cursor:"pointer"});
			e1.html("重新获取");
			$("#send").attr("onclick", "clicksend()");
			$("#send").css("background","#F60");
			clearInterval(interval);	
		}
	},1000);	
}

function findpassword()
{
	var username=$('#username').val(); 
	var checkcode=$('#checkcode').val(); 
	var password=$('#password').val();
	if(checkusername)
	{
		$.ajax({
			url:'/findpassword?username='+username+'&checkcode='+checkcode+'&password='+password,
			type:'POST',
			async:'false',
			success:function(data){
				if(data.code=="success")
				{
					alert("密码修改成功！");
					window.location.href="/life/login.html";
				}else
				{
					alert(data.message);					
				}
			}
		});
	}else
	{
		alert("请填写正确的手机号！");	
	}
}