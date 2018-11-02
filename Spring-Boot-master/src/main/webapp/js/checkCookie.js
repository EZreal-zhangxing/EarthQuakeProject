function getCookie(c_name)
{
    if (document.cookie.length>0)
    {
        c_start=document.cookie.indexOf(c_name + "=")
        if (c_start!=-1)
        {
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
}
$(function(){
    var token = getCookie("token");
    var userId = getCookie("userId")
    $.ajax({
       url:"/user/checkAdminCode?id="+userId+"&loginCode="+token,
        type:"POST",
        async:false,
        success:function(data){
            var msg=eval(data);
            console.log(msg);
            if(msg.code != "success")
            {
                window.location.href="../index.html";
            }
        }
    });
});