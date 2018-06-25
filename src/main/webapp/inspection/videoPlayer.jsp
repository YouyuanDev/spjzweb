<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    basePath=basePath.substring(0,basePath.lastIndexOf('/'));
    basePath=basePath.substring(0,basePath.lastIndexOf('/'));
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <script src="../easyui/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
             var videoName=GetQueryString("video_url");
             var videoSrc="<%=basePath%>"+"upload/videos/"+videoName;
             $('#video1').attr("src",videoSrc);
             $("#video1")[0].play();
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
   <div style="width:100%;height:600px;">
       <video id="video1" width="100%" height="100%" controls>
           <source src="" type="video/mp4">
       </video>
   </div>
</body>
</html>
