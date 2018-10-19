var userid;
$(document).ready(function(){
	userid=getCookie('userid');//获取用户userid from cookie
	$.ajax({
		url:'/getArticelsBymodel/6/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var pagestr="";
				var Data = data[index];
				var time = new Date(Data.date).Format("yyyy-MM-dd");
				if(Data.outUrl != null && Data.outUrl != '')
				{
					pagestr=pagestr+
					'<div class="join_box">'+
					'<dl>'+
					'<dt><img src="/readpicFile?filepath='+Data.articelPicPath+'" /></dt>'+
					'<dd>'+
					'<a href="'+Data.outUrl+'">'+
					'<h2>'+Data.title+'</h2>'+
					'<p>'+Data.articelDesc+'</p>'+
					'</a>';
					if(Data.isOrder == 1)
					{
						pagestr+='<span class="show_join" onclick="showmodel('+Data.id+')">点击报名</span>';
					}else
					{
						pagestr+='<span class="show_join join_end">报名结束</span>';
					}
					pagestr+=
					'</dd>'+
					'</dl>'+
					'<hr />'+
					'<ul>'+
					'<li class="calendar">'+time+'</li>'+
					'<li class="number">'+Data.orderNum+'人</li>';
					if(Data.haslike == 1)
					{
						pagestr+='<li id="praise'+Data.id+'" class="praise_cant" onclick="dianzan('+Data.id+')">'+Data.likeNum+'</li>';
					}else
					{
						pagestr+='<li id="praise'+Data.id+'" class="praise" onclick="dianzan('+Data.id+')">'+Data.likeNum+'</li>';
					}
					pagestr+='</ul></div>';
					
					$('#joinlist').append(pagestr);
				}else
				{
					pagestr=pagestr+
					'<div class="join_box">'+
					'<dl>'+
					'<dt><img src="/readpicFile?filepath='+Data.articelPicPath+'" /></dt>'+
					'<dd>'+
					'<a href="apply.html?articelId='+Data.id+'">'+
					'<h2>'+Data.title+'</h2>'+
					'<p>'+Data.articelDesc+'</p>'+
					'</a>';
					if(Data.isOrder == 1)
					{
						pagestr+='<span class="show_join" onclick="showmodel('+Data.id+')">点击报名</span>';
					}else
					{
						pagestr+='<span class="show_join join_end">报名结束</span>';
					}
					pagestr+=
					'</dd>'+
					'</dl>'+
					'<hr />'+
					'<ul>'+
					'<li class="calendar">'+time+'</li>'+
					'<li class="number">'+Data.orderNum+'人</li>';
					if(Data.haslike == 1)
					{
						pagestr+='<li id="praise'+Data.id+'" class="praise_cant" onclick="dianzan('+Data.id+')">'+Data.likeNum+'</li>';
					}else
					{
						pagestr+='<li id="praise'+Data.id+'" class="praise" onclick="dianzan('+Data.id+')">'+Data.likeNum+'</li>';
					}
					pagestr+='</ul></div>';
					
					$('#joinlist').append(pagestr);
				}
			});
		}
	});
});
function showmodel(id)
{
	$('#articeId').attr('value',id);
	$(".msg_box").show();
	$(".shadow").show();
}
//参与我们
$(".sub_btn").click(function(){
	if(userid != null && userid != '')
	{
		var articeId=$('#articeId').val();
		var name=$('#name').val();
		var userTel=$('#userTel').val();
		var userAddress=$('#userAddress').val();
		var comAddress=$('#comAddress').val();
		var modelId=6;
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

function dianzan(id)
{
	var dznum=$('#praise'+id);
 	if(userid == null){
 		//请先登录
 		alert("请先登录");
 		window.location.href="/life/login.html?addressUrl=1";
 	}else{
 		//点赞\取消点赞
 		$.ajax({
			 	type:"get",
	 			url:"/pointLike?articelId="+id+"&userId="+userid,
	 			dataType:"json",
	 			async:true,
	 			success:function(data){
	 				if(data.code == 'dissuccess'){
			 			//取消点赞成功
						var num=dznum.html();
						num=parseInt(num)-1;
						dznum.html(num);
						dznum.attr("class","praise");
		 			}else{
					 	//点赞成功
						var num=dznum.html();
						num=parseInt(num)+1;
						dznum.html(num);
						dznum.attr("class","praise_cant");
				 	}
		 		}
		 	});
 	}
	
}
