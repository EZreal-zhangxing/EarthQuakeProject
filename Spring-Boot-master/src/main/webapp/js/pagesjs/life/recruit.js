var userid;
$(document).ready(function(){
	userid=getCookie('userid');//获取用户userid from cookie
	loadzp();
});
//加载街坊圈
function loadzp()
{
	$.ajax({
		url:'/getArticelsBymodel/12/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var pageStr="";
				var Data = data[index];
				console.log(Data);
				if(Data.outUrl!=null && Data.outUrl!="")
				{
					pageStr+='<a href="'+Data.outUrl+'">';	
				}else
				{
					pageStr+='<a href="recruit_details.html?articelId='+Data.id+'">';	
				}
				pageStr+='<ul class="recruit_box border">'+
				      	 '<li class="recruit_left">'+
				         '<strong>'+Data.title+'</strong>'+
				         '<span>'+Data.articelDesc+'</span>'+
				         '</li>'+
				         '<li class="recruit_right">'+Data.salary+'</li>'+
				         '</ul></a>';
				$('.recruit_list').append(pageStr);
			});
		}
	});
}
