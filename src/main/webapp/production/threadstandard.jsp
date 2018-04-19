<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>螺纹检验标准</title>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
    <link href="../miniui/multiupload/multiupload.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="../css/common.css"/>
    <script src="../easyui/jquery.min.js" type="text/javascript"></script>
    <%--<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>--%>
    <script src="../js/common.js" type="text/javascript"></script>
    <script src="../miniui/boot.js" type="text/javascript"></script>
    <%--<script src="../miniui/fileupload/swfupload/swfupload.js" type="text/javascript"></script>--%>
    <%--<script src="../miniui/multiupload/multiupload.js" type="text/javascript"></script>--%>
    <script  src="../js/lrscroll.js" type="text/javascript"></script>
    <script src="../js/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
    <script src="../js/language.js" type="text/javascript"></script>
    <script type="text/javascript">
        var url;
        var staticItem=[];
        $(function () {
            $('#addEditDialog').dialog({
                onClose:function () {
                    clearFormLabel();
                }
            });
            $('.mini-buttonedit .mini-buttonedit-input').css('width','150px');

            //loadDynamicByAcceptanceNo();
        });
        function addFunction(){
            $('#hlcancelBtn').attr('operationtype','add');
            $('#addEditDialog').dialog('open').dialog('setTitle','新增');
            $('#serialNumber').text('');
            clearFormLabel();
            url="/AcceptanceCriteriaOperation/saveThreadAcceptanceCriteria.action";
        }
        function delFunction() {
            var row = $('#contentDatagrids').datagrid('getSelections');
            if(row.length>0){
                var idArr=[];
                for (var i=0;i<row.length;i++){
                    idArr.push(row[i].id);
                }
                var idArrs=idArr.join(',');
                $.messager.confirm('系统提示',"您确定要删除这<font color=red>"+idArr.length+ "</font>条数据吗？",function (r) {
                    if(r){
                        $.post("/AcceptanceCriteriaOperation/delThreadAcceptanceCriteria.action",{"hlparam":idArrs},function (data) {
                            if(data.success){
                                $("#contentDatagrids").datagrid("reload");
                            }
                            hlAlertFour(data.message);
                        },"json");
                    }
                });
            }else{
                hlAlertOne();
            }
        }
        function editFunction(){
            $('#hlcancelBtn').attr('operationtype','edit');
            var row = $('#contentDatagrids').datagrid('getSelected');
            if(row){
                $('#addEditDialog').dialog('open').dialog('setTitle','修改');
                $('#thread_acceptance_criteria_no').text(row.thread_acceptance_criteria_no);
                $('#createNoBtn').css('display','none');
                getStataticItem();
                loadDynamicByAcceptanceNo(row.thread_acceptance_criteria_no);
            }else{
                hlAlertTwo();
            }
        }
        function searchFunction() {
            $('#contentDatagrids').datagrid('load',{
                'thread_acceptance_criteria_no': $('#standardno').val()
            });
        }
        function addEditFormSubmit() {
            $('#addEditForm').form('submit',{
                url:url,
                onSubmit:function () {

                },
                success: function(result){
                    var result = eval('('+result+')');
                    $('#addEditDialog').dialog('close');
                    if (result.success){
                        $('#contentDatagrids').datagrid('reload');
                    }
                    hlAlertFour(result.message);
                },
                error:function () {
                    //clearFormLabel();
                    hlAlertThree();
                }
            });
        }
        function CancelSubmit() {
            $('#addEditDialog').dialog('close');
        }

        function  clearFormLabel(){
            $('#addEditForm').form('clear');
            $('.hl-label').text('');
        }
        function addAcceptanceCriteriaFunction() {
             var timestamp=new Date().getTime();
            $("#thread_acceptance_criteria_no").textbox("setValue", timestamp);
            //$("＃standard_no").val(timestamp);
        }
        //根据接收标准编号加载动态测量项
        function loadDynamicByAcceptanceNo(thread_acceptance_criteria_no) {
            $('#dynamicDatagrids').datagrid({
                title:'',
                iconCls:'',
                width:900,
                height:250,
                idField:'id',
                singleSelect:true,
                fitColumns:true,
                toolbar:[
                    { text: '增加', iconCls: 'icon-add', handler: function () {
                               rowInsert();
                          }
                    },{ text: '保存', iconCls: 'icon-save', handler: function () {
                            rowSave(thread_acceptance_criteria_no);
                        }
                    },{ text: '编辑', iconCls: 'icon-edit', handler: function () {
                            rowEdit();
                        }
                    },{ text: '删除', iconCls: 'icon-remove', handler: function () {
                            rowDelete(thread_acceptance_criteria_no);
                        }
                    },{ text: '撤销', iconCls: 'icon-undo', handler: function () {
                            rowCancel();
                        }
                    }
                ],
                url:'/DynamicMeasure/getDynamicMeasureItemByAcceptanceNo.action?thread_acceptance_criteria_no='+thread_acceptance_criteria_no,
                columns:[[
                    {field:'id',title:'流水号',width:60},
                    {field:'measure_item_code',title:'测量项编号',width:100,editor:'text',
                        formatter:function(value){
                            for(var i=0; i<staticItem.length; i++){
                                if (staticItem.measure_item_code == value) return  staticItem.measure_item_name;
                            }
                            return value;
                        },
                        editor:{
                            type:'combobox',
                            options:{
                                valueField:'measure_item_code',
                                textField:'measure_item_name',
                                data:staticItem,
                                required:true
                            }
                        }
                    },
                    {field:'item_max_value',title:'接收最大值',width:80,editor:{type:'numberbox',options:{precision:2}}},
                    {field:'item_min_value',title:'接收最小值',width:80,editor:{type:'numberbox',options:{precision:2}}},
                    {field:'item_frequency',title:'检验频率',width:80,editor:{type:'numberbox',options:{precision:2}}}
                ]]
            });
        }
        function getRowIndex(target){
            var tr = $(target).closest('tr.datagrid-row');
            return parseInt(tr.attr('datagrid-row-index'));
        }

        function deleterow(target){

        }
        function rowInsert(){
            var row = $('#dynamicDatagrids').datagrid('getSelected');
            //var index;
            if (row){
                var index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
            } else {
                index = 0;
            }
            $('#dynamicDatagrids').datagrid('insertRow', {
                index: index,
                row:{
                }
            });
            $('#dynamicDatagrids').datagrid('selectRow',index);
            $('#dynamicDatagrids').datagrid('beginEdit',index);
        }
        function rowSave(thread_acceptance_criteria_no) {
            var row = $('#dynamicDatagrids').datagrid('getSelected');
            if (row){
                var index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
                $('#dynamicDatagrids').datagrid('endEdit',index);
                $.ajax({
                    url:'/DynamicMeasure/saveDynamicMeasureItem.action',
                    dataType:'json',
                    data:{id:row.id,measure_item_code:row.measure_item_code,thread_acceptance_criteria_no:thread_acceptance_criteria_no,item_max_value:row.item_max_value,item_min_value:row.item_min_value,item_frequency:row.item_frequency},
                    success:function (data) {
                        //如果是新增，则返回新增id,如果是修改，则返回执行结果
                        if(data.promptkey=="success"){
                            $("#dynamicDatagrids").datagrid("reload");
                            // var newId=data.promptValue;
                            // if(newId!="addsuccess"){//证明是新增
                            //     $("#dynamicDatagrids").datagrid("reload");
                            // }
                            // $('#dynamicDatagrids').datagrid('beginEdit',index);
                        }else if(data.promptkey="ishave"){
                            hlAlertFour(data.promptValue);
                            $('#dynamicDatagrids').datagrid('beginEdit',index);
                        }else{
                            hlAlertFour(data.promptValue);
                        }

                    },error:function () {
                        hlAlertFour("系统繁忙!");
                    }
                });
            } else {
                hlAlertFour("请选中要修改或添加的行!");
            }
        }
        function rowEdit() {
            var row = $('#dynamicDatagrids').datagrid('getSelected');
            if(row){
                var index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
                $('#dynamicDatagrids').datagrid('beginEdit',index);
            }else {
                hlAlertFour("请选中要修改或添加的行!");
            }
        }
        function  rowDelete(thread_acceptance_criteria_no){

            var row = $('#dynamicDatagrids').datagrid('getSelected');
            //var index;
            if (row){
                $.messager.confirm('Confirm','确定要删除吗?',function(r){
                    if (r){
                        var index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
                        $.ajax({
                            url:'/DynamicMeasure/delDynamicMeasureItem.action',
                            dataType:'json',
                            data:{hlparam:row.id},
                            success:function (data) {
                                //如果是新增，则返回新增id,如果是修改，则返回执行结果
                                if(data.promptkey=="success"){
                                    $('#dynamicDatagrids').datagrid('deleteRow',index);
                                }else{
                                    hlAlertFour("删除失败!");
                                }
                            },error:function () {
                                hlAlertFour("系统繁忙!");
                            }
                        });
                    }
                });

            } else {
                hlAlertFour("请选中要修改或添加的行!");
            }
        }
        function rowCancel() {
            var row = $('#dynamicDatagrids').datagrid('getSelected');
            if (row){
                var index= $('#dynamicDatagrids').datagrid('getRowIndex',row);
                $('#dynamicDatagrids').datagrid('endEdit',index);
            }
        }
        function getStataticItem() {
            $.ajax({
                url:'/StaticMeasure/getAllDropdownStaticItem.action',
                async:false,
                dataType:'json',
                success:function (data) {
                    if(data.promptkey=="success"){
                        staticItem= $.parseJSON(data.promptValue);
                    }
                },error:function () {
                    alert("失败");
                }
            });
        }
    </script>
</head>

<body>
<fieldset class="b3" style="padding:10px;margin:10px;">
    <legend> <h3><b style="color: orange" >|&nbsp;</b><span class="i18n1" name="datadisplay">数据展示</span></h3></legend>
    <div  style="margin-top:5px;">
        <table class="easyui-datagrid" id="contentDatagrids" url="/AcceptanceCriteriaOperation/getThreadAcceptanceCriteriaAllByLike.action" striped="true" loadMsg="正在加载中。。。" textField="text" pageSize="20" fitColumns="true" pagination="true" toolbar="#toolsTab">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="id" align="center" width="100" class="i18n1" name="id"></th>
                <th field="thread_acceptance_criteria_no" align="center" width="100" class="i18n1" name="threadacceptancecriteriano"></th>
                <th field="last_update_time" align="center" width="100" class="i18n1" name="lastupdatetime" data-options="formatter:formatterdate"></th>
            </tr>
            </thead>
        </table>
    </div>
</fieldset>

<!--工具栏-->
<div id="toolsTab" style="padding:10px;">
    <span class="i18n1" name="threadacceptancecriteriano">检验标准编号</span>:
    <input id="standardno" style="line-height:22px;border:1px solid #ccc">
    <a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="searchFunction()">Search</a>
    <div style="float:right">
        <a href="#" id="addObpLinkBtn" class="easyui-linkbutton i18n1" name="add" data-options="iconCls:'icon-add',plain:true" onclick="addFunction()">添加</a>
        <a href="#" id="editObpLinkBtn" class="easyui-linkbutton i18n1" name="edit" data-options="iconCls:'icon-edit',plain:true" onclick="editFunction()">修改</a>
        <a href="#" id="deltObpLinkBtn" class="easyui-linkbutton i18n1" name="delete" data-options="iconCls:'icon-remove',plain:true" onclick="delFunction()">删除</a>
    </div>
</div>
<%--<div id="dynamicToolsTab" style="padding:4px;">--%>
        <%--<a href="#" id="addLinkBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addDynamicItem()"></a>--%>
        <%--<a href="#" id="delLinkBtn" class="easyui-linkbutton"  data-options="iconCls:'icon-remove',plain:true" onclick="delDynamicItem()"></a>--%>
<%--</div>--%>
<!--添加、修改框-->
<div id="addEditDialog" class="easyui-dialog" data-options="title:'添加',modal:true" closed="true" buttons="#dlg-buttons" style="display: none;padding:5px;width:950px;max-height:500px;overflow-y:auto;">
    <form id="addEditForm" method="post">
        <%--<fieldset style="width:900px;border:solid 1px #aaa;position:relative;">--%>
            <%--<legend>标准信息</legend>--%>
            <div style="width:100%;padding-bottom:5px;">
                <%--<a href="#" class="easyui-linkbutton i18n1" name="add" onclick="insert()">添加</a>--%>
                <label class="i18n1" name="threadacceptancecriteriano"></label>:
                <label id="thread_acceptance_criteria_no"></label>
                <a href="#" id="createNoBtn" class="easyui-linkbutton i18n1" name="createstandardno" onclick="addAcceptanceCriteriaFunction()">生成标准编号</a>
            </div>
            <table  id="dynamicDatagrids">

            </table>
            <%--<table class="easyui-datagrid" id="dynamicDatagrids" idField="id" fitColumns="true" singleSelect="true">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                    <%--<th data-options="field:'ck',checkbox:true"></th>--%>
                    <%--<th field="id" align="center" width="100" class="i18n1" name="id"></th>--%>
                    <%--<th field="measure_item_code" align="center" width="100" class="i18n1" name="measureitemcode"></th>--%>
                    <%--<th field="item_max_value" align="center" width="100" class="i18n1" name="itemmaxvalue"></th>--%>
                    <%--<th field="item_min_value" align="center" width="100" class="i18n1" name="itemminvalue"></th>--%>
                    <%--<th field="item_frequency" align="center" width="100" class="i18n1" name="itemfrequency"></th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
            <%--</table>--%>
        <%--</fieldset>--%>

    </form>
</div>
<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    hlLanguage("../i18n/");
</script>