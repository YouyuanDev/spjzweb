<%--
  Created by IntelliJ IDEA.
  User: kurt
  Date: 4/21/18
  Time: 5:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>客户端程序更新</title>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
    <link href="../miniui/multiupload/multiupload.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="../css/common.css"/>
    <script src="../easyui/jquery.min.js" type="text/javascript"></script>
    <script src="../js/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
    <script src="../js/language.js" type="text/javascript"></script>
    <script src="../js/common.js" type="text/javascript"></script>
    <script src="../miniui/boot.js" type="text/javascript"></script>
    <script  src="../miniui/js/miniui.js" type="text/javascript"></script>
    <script src="../miniui/fileupload/swfupload/swfupload.js" type="text/javascript"></script>
    <script src="../miniui/multiupload/multiupload.js" type="text/javascript"></script>
    <script  src="../js/lrscroll.js" type="text/javascript"></script>
    <script src="../js/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
    <script src="../js/language.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">

    <script type="text/javascript">//

    </script>

</head>
<body>
<br />
<div align="left">
    <h3><span class="i18n1" name="plsselectupladclientappfiles">请选择要上传的客户端程序包（.zip）</span></h3>
    <br />
    <input id="fileupload1" class="mini-fileupload" name="Fdata" limitType="*.zip;"
           flashUrl="../miniui/fileupload/swfupload/swfupload.swf"
           uploadUrl="/ClientAppUpdate/uploadClientAppPackage.action"
           onuploadsuccess="onUploadSuccess"
           onuploaderror="onUploadError" onfileselect="onFileSelect" width="400px"
    />
    <br /><br />

    <a class="mini-button mini-button-success" width="100px" value="上传" onclick="startUpload()"><span class="i18n1" name="upload">上传</span></a>

</div>
<div class="description">
    <h3><span class="i18n1" name="description">描述</span></h3>
    <p><span class="i18n1" name="upgradeinfo">更新信息</span></p>

</div>


</body>
</html>
<script type="text/javascript">
    mini.parse();

    //动态设置url
    //    var fileupload = mini.get("fileupload1");
    //    fileupload.setUploadUrl("upload.aspx");

    function onFileSelect(e) {
        //alert("选择文件");
    }
    function onUploadSuccess(e) {

        //alert("上传成功：" + e.serverData);
        var result = eval('('+e.serverData+')');
        if(result.success){
            alert("成功上传客户端程序包");
        }

        this.setText("");
    }
    function onUploadError(e) {
        alert("上传错误：" + e.serverData);
    }

    function startUpload() {
        var fileupload = mini.get("fileupload1");
        fileupload.setUploadUrl("/ClientAppUpdate/uploadClientAppPackage.action");
        fileupload.startUpload();
    }

    hlLanguage("../i18n/");
</script>