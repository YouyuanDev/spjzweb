<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <script src="../easyui/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
             var videoName=GetQueryString("video_url");
             var videoSrc="<%=basePath%>/upload/videos/"+videoName;
             var x = document.getElementById("video1");
             var source ='<source src='+videoSrc+' type="video/mp4">';
             x.innerHTML=source;
        });
        function GetQueryString(name)
        {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }
    </script>

</head>

<body>
   <div style="width:100%;height:100%;padding:10px;background:black;">
       <video id="video1" width="100%" height="100%" autoplay="autoplay" controls>

       </video>
   </div>
</body>
</html>
