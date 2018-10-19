var userid;
$(document).ready(function(){
	userid=getCookie('userid');//获取用户userid from cookie
	loadash();
});
//加载街坊圈
function loadash()
{
	$.ajax({
		url:'/getArticelsBymodel/10/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var pageStr="";
				var Data = data[index];
				console.log(Data);
				
				pageStr+='<dl>'+'<dt class="border">';
				if(Data.outUrl != null && Data.outUrl != '')
				{
					pageStr+='<a href="'+Data.outUrl+'">';
				}else
				{
					pageStr+='<a href="activity_details.html?articelId='+Data.id+'">';
				}
				pageStr+=
			    '<img src="/readpicFile?filepath='+Data.articelPicPath+'"></a></dt>'+
			    '<dd class="activity_txt"><span>';
			     if(Data.outUrl != null && Data.outUrl != '')
				{
					pageStr+='<a href="'+Data.outUrl+'">';
				}else
				{
					pageStr+='<a href="activity_details.html?articelId='+Data.id+'">';
				}
			    pageStr+=Data.title+
			    '</a></span>'+
			    '<b class="left"><i>'+Data.orderNum+'</i>/<i>'+Data.maxMannum+'</i></b><b class="right dz" onclick="dianzan('+Data.id+')">' +
			    '<i class="dznum" >'+Data.likeNum+'</i>';
			    if(Data.haslike == 1)
			    {
			    	pageStr+='<img id="hx" src="../images/heart_on.png" />';
			    }else
			    {
			    	pageStr+='<img id="hx" src="../images/heart_off.png" />';
			    }
			    pageStr+='</b></dd></dl>';
				$('.activity').append(pageStr);
			});
		}
	});
}

function dianzan(id)
{
	var dznum=$(".dznum");
 	var hx=$("#hx");
	 if(userid == null){
	 	//请先登录
	 	alert("请先登录");
	 	window.location.href="login.html?addressUrl=1";
	 }else{
	 	//点赞\取消点赞
	 	$.ajax({
 			type:"get",
 			url:"/pointLike?articelId="+id+"&userId="+userid,
 			dataType:"json",
 			async:true,
 			success:function(data){
			 	if(data.code == 'dissuccess'){
				 	hx.attr("src","../images/heart_off.png");
				 	//取消点赞成功
					var num=dznum.html();
					num=parseInt(num)-1;
					dznum.html(num);
				}else{
					hx.attr("src","../images/heart_on.png")
				 	//点赞成功
					var num=dznum.html();
					num=parseInt(num)+1;
					dznum.html(num);
				 }
			 }
			});
	 	}
}