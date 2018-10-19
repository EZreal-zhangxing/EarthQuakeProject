// JavaScript Document
!function(){     
	//rem  计算方式  px/45
	function setFontSize(){            	
		document.documentElement.style.fontSize = document.documentElement.clientWidth /16 +"px";
	}
	var _t = null;
	window.addEventListener("resize",function(){
		clearTimeout(_t);
		_t = setTimeout(setFontSize,100);
	},false);
	setFontSize();
	var _v = new Date().getTime();
	document.write('<link rel="stylesheet" type="text/css" href="../css/style.css?_v='+_v+'">');
}(window);


$(document).ready(function(){
	//参与我们
	$(".close").click(function(){
		$(".msg_box").hide();
		$(".shadow").hide();
	});
	$(".show_join,.order").click(function(){
		$(".msg_box").show();
		$(".shadow").show();
	});
	
 //视频详情 
	$(".input_talk input").click(function(){
		$(".input_talk").hide();
		$(".shadow").hide();
		$(".inTalk").show();
	});
	$(".inTalk").click(function(){
		$(".input_talk").show();
		$(".shadow").show();
		$(".inTalk").hide();
	});
	//我的
	$(".off_box").click(function(){
		$(".sign_success").hide();
		$(".submission").hide();
		$(".integral_details").hide();
		$(".shadow").hide();
	});
//	$(".click_sign").click(function(){
//		$(".sign_success").show();
//		$(".shadow").show();
//	});
	$(".click_submission").click(function(){
		$(".submission").show();
		$(".shadow").show();
	});
	$(".click_integral").click(function(){
		$(".integral_details").show();
		$(".shadow").show();
	});
});

//图片轮换
//$(function () {
//      $('#home_slider').flexslider({
//          animation : 'slide',
//          controlNav : true, 
//          directionNav : true,
//          animationLoop : true,
//          slideshow : true,
//          useCSS : false
//      });
//});

//性别选择
function myRadio(sName){
	var aSex = document.getElementsByName(sName);
	var aSpan = [];
	for(var i=0;i<aSex.length;i++){
		var oSpan = document.createElement('i');
		aSpan.push(oSpan);
		oSpan.innerHTML=aSex[i].value;
		aSex[i].parentNode.insertBefore(oSpan,aSex[i]);aSpan[0].className='on';
		(function(index){
			oSpan.onclick=function(){
				for(var i=0;i<aSpan.length;i++){
					aSpan[i].className='';
					
				}
				this.className='on';
				aSex[index].checked=true;
			};
		})(i);
	}
	for(var i=0;i<aSex.length;i++){
		aSex[i].style.display='none';
	}

}
window.onload=function(){
	myRadio('sex');
}
//时间排序
$(function(){
    $(".talk_time a").click(function() {
        $(this).toggleClass('time_click'); 
    });
}); 