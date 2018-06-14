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
    //提交登录验证
    function submitForm() {
        $("#frmReg").form('submit',{
            url: "/Login/registKey.action",
            onSubmit:function() {
                return $(this).form('validate');
            },
            success:function(result) {
                var data = eval('(' + result + ')');
                $.messager.alert("错误提示",data.msg);
            }
        });
    }
    </script>

</head>
<body>
<br />

<div align="left">

    <form id="frmReg" method="post">
        <label>key1</label>
    <input id="key1" name="key1" class="easyui-textbox" style="width:200px;"/>

        <label>key2</label>
    <input id="key2" name="key2" class="easyui-textbox" style="width:200px;"/>
        <a href="javascript:;" onclick="submitForm();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:5px 0px;">
            <span>注册</span>
        </a>
    </form>
</div>





<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</body>
</html>
<script type="text/javascript">
    mini.parse();


    hlLanguage("../i18n/");
</script>