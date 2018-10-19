package com.zx.Controller;

import com.zx.Pojo.Pageinfo;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class BaseController {
    //页面初始化
    public Pageinfo initpage(Integer datanum,String page,Integer pageSize)
    {
        Pageinfo pageif=new Pageinfo();
        int pageshownum=pageSize==null?10:pageSize;//每页展示数
        pageif.setShownum(pageshownum);
        pageif.setFirstpage(1);
        Integer allpage=(datanum>0)?(((datanum)%pageshownum==0)?(datanum/pageshownum):(datanum/pageshownum)+1):1;
        pageif.setAllpage(allpage);
        pageif.setLastpage(allpage);
        Integer pagenum=0;

        if(page==null)
        {
            pageif.setPagenum(1);
        }else
        {
            pagenum=Integer.parseInt(page);
            pageif.setPagenum(pagenum);
        }
        pageif.setPrev((pageif.getPagenum()-1)>0?pageif.getPagenum()-1:1);
        pageif.setNext((pageif.getPagenum()+1)<allpage?pageif.getPagenum()+1:allpage);
        String pageStr="";
        if(pagenum-3<=1||allpage<=7)
        {
            for (int i = 1; i <= ((allpage>7)?7:allpage); i++) {
                if(i==((allpage>7)?7:allpage))
                {
                    pageStr+=i;
                }else
                {
                    pageStr+=i+",";
                }
            }
            if(allpage>7)
            {
                pageStr+=",...";
            }
        }else
        {
            pageStr+="...,";
            int num=((((pagenum+3>allpage)?allpage:pagenum)>3)?3:((pagenum+3>allpage)?allpage:pagenum));
            int cnum=(num+pagenum)>allpage?(allpage-pagenum):num;
            for (int i = cnum-7; i <=cnum; i++) {
                if(i==cnum)
                {
                    if(cnum+pagenum==allpage)
                    {
                        pageStr+=(pagenum+i);
                    }else
                    {
                        pageStr+=(pagenum+i)+",...";
                    }
                }else
                {
                    pageStr+=(pagenum+i)+",";
                }
            }
        }
        pageif.setPageStr(pageStr.split(","));

        pageif.setBegin(pageif.getShownum()*(pageif.getPagenum()-1));
        return pageif;
    }

    public void jumpPage(HttpServletResponse response, int result,String page) throws IOException {
        PrintWriter bw=response.getWriter();

        String html="<!DOCTYPE html><html lang='en'><head>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' /></head>" +
                "<title>保存结果</title>" +
                "<body onload=jump()>";
        if(result>0)
        {
            html+="保存成功！";
//			return new Message("success", "保存成功", new Date());
        }else
        {
            html+="保存失败！";
//			return new Message("fail", "保存失败", new Date());
        }
        //
        html+="<a href='../"+page+"'>返回</a> <b id='sec'></b>秒后跳转！";
        html+="</body><script>var i;var int;function jump(){i=3;" +
                "document.getElementById('sec').innerHTML=i;" +
                "int=window.setInterval('clock(i)',1000)} function clock(){i=i-1;if(i<0){window.clearInterval(int);window.location.href='../"+page+"';}else{document.getElementById('sec').innerHTML=i;}}</script></html>";
        bw.write(html);
        bw.flush();
        bw.close();
    }

    public void delFileByfilePath(String filepath){
        if(filepath!=null && !"".equals(filepath)){
            File file=new File(filepath);
            if(file.exists()){
                file.delete();
            }
        }
    }

    /**
     * 从输入流IS中 将文件读取到指定的文件夹 file
     * @param file 文件保存磁盘对象
     * @param is 数据流
     * @throws IOException
     */
    public synchronized void saveFile(File file, InputStream is, String filepath) throws IOException
    {
        File filedir=new File(filepath);
        if(!filedir.exists()){
            filedir.mkdirs();
        }
        FileOutputStream fos=new FileOutputStream(file);
        byte[] bytes=new byte[1024*1024];
        int len=0;
        while((len=is.read(bytes))!=-1)
        {
            fos.write(bytes, 0, len);
        }
        fos.flush();
        fos.close();
        is.close();
    }
}
