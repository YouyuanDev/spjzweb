<%@ page import="java.util.ResourceBundle" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/13 0013
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script src="js/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
    <script src="js/language.js" type="text/javascript"></script>
    <script src="js/common.js" type="text/javascript"></script>
    <style type="text/css" >
        .ht-table,.ht-table td{border-collapse:collapse;border:1px solid #F0F0F0;}
        .ht-table{width:100%;margin-bottom:10px;}
        .hltr{border-bottom:2px solid #1f1f1f ;}
        .b3{border-style:inset;border-width:thin;}
    </style>
    <script type="text/javascript">
        var url;
        $(function(){
            var uriArr=["threadingprocess",
                "contractmanagement",
                "threadstandard","staticmeasureitem",
                "person","role","function"
                ];
            var startArr=uriArr.slice(0,1);
            var oneArr=uriArr.slice(1,2);
            var twoArr=uriArr.slice(2,4);
            var threeArr=uriArr.slice(4,7);
            var hsMapList="<%=session.getAttribute("userfunctionMap")%>";
            var funArr;
            if(hsMapList!=null&&hsMapList!=""&&hsMapList.length>0){
                var reg=new RegExp('=1',"g")
                hsMapList=hsMapList.replace(reg,"");
                funArr=hsMapList.substring(1,hsMapList.length-1).split(',');
            }

            var tempNameArr=new Array();//得到的是比对uri中新的权限数组
            for(var i=0;i<funArr.length;i++){
                if($.inArray(funArr[i].trim(),uriArr)!=-1){
                    tempNameArr.push(funArr[i].trim());
                }
            }
            var finalNameArr=new Array();
            $.each(uriArr,function (index,element) {
                if($.inArray(element,tempNameArr)!=-1){
                    finalNameArr.push(element);
                }
            });

            if(finalNameArr.length>0){

                var startDiv='<div title=\"检验信息\" class=\"i18n\" name=\"inspectioninfo\"  style=\"padding:10px;\"><ul id=\"inspection-ul\">';
                var startDivSon="";

                var oneDiv='<div title=\"基础信息\" class=\"i18n\" name=\"basicinfo\"  style=\"padding:10px;\"><ul id=\"basic-ul\">';
                var oneDivSon="";
                var twoDiv='<div title=\"生产工艺\" class=\"i18n\" name=\"productionprocess\"  style=\"padding:10px;\"><ul id=\"process-ul\">';
                var twoDivSon="";
                var threeDiv='<div title=\"账户管理\" class=\"i18n\" name=\"accountmanagement\"  style=\"padding:10px;\"><ul id=\"account-ul\">';
                var threeDivSon="";

                var endDiv="</ul></div>";


                $.each(finalNameArr,function (index,element) {
                    if($.inArray(element,startArr)!=-1){
                        startDivSon+=MakeMenus(element);
                        return true;
                    }

                    if($.inArray(element,oneArr)!=-1){
                        oneDivSon+=MakeMenus(element);
                        return true;
                    }
                    if($.inArray(element,twoArr)!=-1){
                        twoDivSon+=MakeMenus(element);
                        return true;
                    }
                    if($.inArray(element,threeArr)!=-1){
                        threeDivSon+=MakeMenus(element);
                        return true;
                    }

                });

                if(startDivSon!=""&&startDivSon.length>0){
                    startDiv+=startDivSon;
                    startDiv+=endDiv;
                    $('#aa').append(startDiv);
                }

                if(oneDivSon!=""&&oneDivSon.length>0){
                    oneDiv+=oneDivSon;
                    oneDiv+=endDiv;
                    $('#aa').append(oneDiv);
                }
                if(twoDivSon!=""&&twoDivSon.length>0){
                    twoDiv+=twoDivSon;
                    twoDiv+=endDiv;
                    $('#aa').append(twoDiv);
                }
                if(threeDivSon!=""&&threeDivSon.length>0){
                    threeDiv+=threeDivSon;
                    threeDiv+=endDiv;
                    $('#aa').append(threeDiv);
                }
            }
            hlLanguage("i18n/");
            //hlLanguage("i18n/");



            //检验信息
            $('#inspection-ul').tree({
                onClick:function(node){
                    var tab=$('#bgTab').tabs('getTab',node.text);
                    var xy=node.text;
                    if(tab){
                        //切换
                        $('#bgTab').tabs('select',node.text);
                    }else{
                        //添加新的选项卡
                        if("螺纹检验"==xy||"Threading Inspection"==xy){
                            $('#bgTab').tabs('add',{
                                title:node.text,
                                content:"<iframe scrolling='auto' frameborder='0'  src='inspection/threadingprocess.jsp' style='width:100%;height:100%;'></iframe>",
                                closable:true
                            });
                            hlLanguage();
                        }


                    }
                }
            });




            $('#basic-ul').tree({
                onClick:function(node){
                    var tab=$('#bgTab').tabs('getTab',node.text);
                    var xy=node.text;
                    if(tab){
                        //切换
                        $('#bgTab').tabs('select',node.text);
                    }else{
                        //添加新的选项卡
                        if("合同管理"==xy||"Contract Management"==xy){
                            $('#bgTab').tabs('add',{
                                title:node.text,
                                content:"<iframe scrolling='auto' frameborder='0'  src='basicinfo/contractmanagement.jsp' style='width:100%;height:100%;'></iframe>",
                                closable:true
                            });
                            hlLanguage();
                        }

                    }
                }
            });
            //账户管理
            $("#account-ul").tree({
                onClick:function (node) {
                    var tab=$('#bgTab').tabs('getTab',node.text);
                    var nodeTxt=node.text;
                    if(tab){
                        $('#bgTab').tabs('select',node.text);
                    }else{
                        if("人员管理"==nodeTxt||"Person Management"==nodeTxt){

                            $('#bgTab').tabs('add',{
                                title:node.text,
                                content:"<iframe scrolling='auto' frameborder='0'  src='account/person.jsp' style='width:100%;height:100%;'></iframe>",
                                closable:true
                            });
                            hlLanguage();
                        }
                        else if("角色管理"==nodeTxt||"Role Management"==nodeTxt){

                            $('#bgTab').tabs('add',{
                                title:node.text,
                                content:"<iframe scrolling='auto' frameborder='0'  src='account/role.jsp' style='width:100%;height:100%;'></iframe>",
                                closable:true
                            });
                            hlLanguage();
                        }
                        else if("权限管理"==nodeTxt||"Function Management"==nodeTxt){

                            $('#bgTab').tabs('add',{
                                title:node.text,
                                content:"<iframe scrolling='auto' frameborder='0'  src='account/function.jsp' style='width:100%;height:100%;'></iframe>",
                                closable:true
                            });
                            hlLanguage();
                        }
                    }
                }
            });
            $('#process-ul').tree({
                onClick:function(node){
                    var tab=$('#bgTab').tabs('getTab',node.text);
                    var xy=node.text;
                    if(tab){
                        //切换
                        $('#bgTab').tabs('select',node.text);
                    }else{
                        //添加新的选项卡
                        if("螺纹检验标准"==xy||"Thread Standard"==xy){
                            $('#bgTab').tabs('add',{
                                title:node.text,
                                content:"<iframe scrolling='auto' frameborder='0'  src='production/threadstandard.jsp' style='width:100%;height:100%;'></iframe>",
                                closable:true
                            });
                            hlLanguage();
                        }else  if("静态测量项"==xy||"Static Measure Item"==xy){
                            $('#bgTab').tabs('add',{
                                title:node.text,
                                content:"<iframe scrolling='auto' frameborder='0'  src='production/staticmeasureitem.jsp' style='width:100%;height:100%;'></iframe>",
                                closable:true
                            });
                            hlLanguage();
                        }
                        // else  if("动态测量项"==xy||"Dynamic Measure Item"==xy){
                        //     $('#bgTab').tabs('add',{
                        //         title:node.text,
                        //         content:"<iframe scrolling='auto' frameborder='0'  src='production/dynamicmeasureitem.jsp' style='width:100%;height:100%;'></iframe>",
                        //         closable:true
                        //     });
                        //     hlLanguage();
                        // }
                    }
                }
            });

        });
        function  MakeMenus(name) {
            var res='<li class=\"i18n1\" name=\"'+name+'\" ></li>';
            return res;
        }

    </script>
</head>
<body class="easyui-layout">
<%--<div data-options="region:'south',split:true" style="height:50px;">--%>
    <%--<div style="text-align: center"><h3>@2018 友元科技 版权所有</h3></div>--%>
<%--</div>--%>
<div data-options="region:'north',split:true">
    <div style="float: right;padding:10px">
        <select id="language">
            <option value="zh-CN">中文</option>
            <option value="en">ENGLISH</option>
        </select>
    </div>
</div>
<div data-options="region:'west'" title="导航菜单" class="i18n" name="navigation" style="width:200px;">

    <div id="aa" class="easyui-accordion">
        <%--<div title="基础信息" class="i18n" name="basicinfo"  style="padding:10px;">--%>
            <%--<ul id="basic-ul">--%>
                <%--<li class="i18n1" name="threadinginspection">螺纹检验</li>--%>
                <%--<li class="i18n1" name="toolmeasuringrecord">工具测量使用记录</li>--%>
            <%--</ul>--%>
        <%--</div>--%>
        <%--<div title="生产工艺" class="i18n" name="productionprocess" style="padding:10px;">--%>
          <%--<ul id="process-ul">--%>
            <%--<li class="i18n1" name="threadstandard">螺纹检验标准</li>--%>
           <%--</ul>--%>
        <%--</div>--%>
        <%--<div title="账户管理" class="i18n" name="accountmanagement" style="padding:10px;">--%>
            <%--<ul id="account-ul">--%>
                <%--<li class="i18n1" name="personmanagement">人员管理</li>--%>
                <%--<li class="i18n1" name="rolemanagement">角色管理</li>--%>
                <%--<li class="i18n1" name="functionmanagement">权限管理</li>--%>
            <%--</ul>--%>
        <%--</div>--%>

    </div>

</div>


<div data-options="region:'center'," style="padding:5px;">
    <div id="bgTab" class="easyui-tabs" data-options="fit:true">
        <div title="首页" class="i18n" name="home" style="padding:5px;display:none;" data-options="iconCls:'icon-lightbulb'">
            <%--<img src="images/44.jpg" style="width:100%;height:100%">--%>
        </div>
    </div>
</div>
<div id="hlTb">
    <a href="#" id="addLinkBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">Add</a>
    <a href="#" id="editLinkBtn" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">Edit</a>
    <a href="#" id="deltLinkBtn" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">Delete</a>
</div>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
</body>

</html>
<script type="text/javascript">
    hlLanguage("i18n/");
</script>