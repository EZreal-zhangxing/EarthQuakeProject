$(document).ready(function(){
	var articelId=getQueryString('articelId');//获取文章ID
	$.ajax({
		url:'/getArticelContent/'+articelId,
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				console.log(Data);
				console.log(Data.id);
				var time = new Date(Data.date).Format("yyyy-MM-dd");
				$('.signUp').append('<h1>'+Data.title+'</h1>'+
					'<span class="time">'+time+'</span>'+
					'<div><p>'+Data.content.replace(/div/g,'p')+'</p></div>');
			});
		}
	});
});
