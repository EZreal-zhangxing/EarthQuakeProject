$(document).ready(function(){
	var userid=getCookie('userid');//获取用户userid from cookie
	$.ajax({
		url:'/getArticelsBymodel/8/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				console.log(Data);
				console.log(Data.id);
				var time = new Date(Data.date).Format("yyyy-MM-dd");
				if(Data.outUrl != null && Data.outUrl!='')
				{
					$('.report').append(
						'<a href="'+Data.outUrl+'">'+
					        '<dl>'+
					        '<dt><img src="/readpicFile?filepath='+Data.articelPicPath+'" /></dt>'+  
					        '<dd>'+Data.title+'</dd>'+
					        '</dl>'+
					    '</a>'
					);
				}else
				{
					//没有外链
					$('.report').append(
						'<a href="article_details.html?articelId='+Data.id+'">'+
					        '<dl>'+
					        '<dt><img src="/readpicFile?filepath='+Data.articelPicPath+'" /></dt>'+  
					        '<dd>'+Data.title+'</dd>'+
					        '</dl>'+
					    '</a>'
					);
				}
			});
		}
	});
});
