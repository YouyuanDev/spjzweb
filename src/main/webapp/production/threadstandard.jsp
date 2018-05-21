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
        var editIndex = undefined;
        var addOrEdit=true;
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
            $('#createNoBtn').css('display','block');
            addOrEdit=true;
            clearFormLabel();
            getStataticItem();
            $('#dynamicDatagrids').datagrid('loadData',{total:0,rows:[]});
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
            addOrEdit=false;
            var row = $('#contentDatagrids').datagrid('getSelected');
            if(row){
                $('#addEditDialog').dialog('open').dialog('setTitle','修改');
                $('#addEditForm').form('load',row);
                //$('#thread_acceptance_criteria_no').text(row.thread_acceptance_criteria_no);
                $('#createNoBtn').css('display','none');
                getStataticItem();
                loadDynamicByAcceptanceNo(row.thread_acceptance_criteria_no);
            }else{
                hlAlertTwo();
            }
        }
        function searchFunction() {
            $('#contentDatagrids').datagrid('load',{
                'thread_acceptance_criteria_no': $('#standardno').val(),
                'od': $('#searchod').val(),
                'wt': $('#searchwt').val(),
                'customer_spec': $('#searchcustomerspec').val(),
                'coupling_type': $('#searchcouplingtype').val(),
                'threading_type': $('#searchthreadingtype').val()
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
            var timestamp="BZ"+new Date().getTime();
            $.ajax({
                url:'/AcceptanceCriteriaOperation/saveThreadAcceptanceCriteria.action',
                dataType:'json',
                data:{thread_acceptance_criteria_no:timestamp},
                success:function (data) {
                    //如果是新增，则返回新增id,如果是修改，则返回执行结果
                    if(data.promptkey=="success"){
                        $("#thread_acceptance_criteria_no").text(timestamp);
                    }else{
                        hlAlertFour("删除失败!");
                    }
                },error:function () {
                    hlAlertFour("系统繁忙!");
                }

            });
            //$("＃standard_no").val(timestamp);
        }
        //根据接收标准编号加载动态测量项
        function loadDynamicByAcceptanceNo(thread_acceptance_criteria_no) {
            if(addOrEdit){
                thread_acceptance_criteria_no=$("#thread_acceptance_criteria_no").text().trim();
            }
            $('#dynamicDatagrids').datagrid({
                title:'',
                iconCls:'',
                width:900,
                height:350,
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
                    {field:'measure_item_code',title:'测量项编号',width:150,
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
                                textField:'code_and_name',
                                data:staticItem,
                                required:true
                            },
                            width:250
                        }
                    },
                    {field:'measure_item_name',title:'测量项名称',width:100},
                    {field:'item_max_value',title:'接收最大值',width:60,editor:{type:'numberbox',options:{precision:2}}},
                    {field:'item_min_value',title:'接收最小值',width:60,editor:{type:'numberbox',options:{precision:2}}},
                    {field:'item_frequency',title:'检验频率',width:80,editor:{type:'numberbox',options:{precision:2}}},
                    {field:'both_ends',title:'AB端检测',width:80,editor:{type:'combobox',editable:false,
                            options: {
                                required: true,
                                data:
                                    [
                                        {'id': '0', 'text': '一端'},
                                        {'id': '1', 'text': 'AB两端'}
                                    ],

                            valueField:'id',
                            textField:'text'
                            }
                            }},
                    {field:'item_std_value',title:'目标值',width:80,editor:{type:'numberbox',options:{precision:2}}},
                    {field:'item_pos_deviation_value',title:'正偏差',width:80,editor:{type:'numberbox',options:{precision:2}}},
                    {field:'item_neg_deviation_value',title:'负偏差',width:80,editor:{type:'numberbox',options:{precision:2}}},
                    {field:'reading_types',title:'读数类型',width:80,editor:{type:'checkbox',editable:false,
                            options:{
                                required: true,
                                data:
                                    [
                                        {'id': '1', 'text': '单值'},
                                        {'id': '2', 'text': '最大值'},
                                        {'id': '3', 'text': '最小值'},
                                        {'id': '4', 'text': '均值'},
                                        {'id': '5', 'text': '椭圆度'}
                                    ],
                                valueField:'id',
                                textField:'text'
                            }


                    }}

                ]]
            });
        }
        function getRowIndex(target){
            var tr = $(target).closest('tr.datagrid-row');
            return parseInt(tr.attr('datagrid-row-index'));
        }


        function rowInsert(){
            if(!endEditing()){
                return;
            }

            $('#dynamicDatagrids').datagrid('appendRow',{ });
            var insertIndex = $('#dynamicDatagrids').datagrid('getRows').length-1;
            $('#dynamicDatagrids').datagrid('selectRow', insertIndex);
            onClickRow(insertIndex);

            //$('#dynamicDatagrids').datagrid('beginEdit', insertIndex);


            // var row = $('#dynamicDatagrids').datagrid('getSelected');
            // var index=0;
            // if (row){
            //     index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
            // }
            // $('#dynamicDatagrids').datagrid('insertRow', {
            //     index: index,
            //     row:{
            //         status:'P'
            //     }
            // });
            // $('#dynamicDatagrids').datagrid('selectRow',index);
            // $('#dynamicDatagrids').datagrid('beginEdit',index);
            // var row = $('#dynamicDatagrids').datagrid('getSelected');
            // //var index;
            // if (row){
            //     var index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
            // } else {
            //     index = 0;
            // }
            // $('#dynamicDatagrids').datagrid('insertRow', {
            //     index: index,
            //     row:{
            //     }
            // });
            // $('#dynamicDatagrids').datagrid('selectRow',index);
            // $('#dynamicDatagrids').datagrid('beginEdit',index);
        }
        function rowSave(thread_acceptance_criteria_no) {
            if(addOrEdit){
                thread_acceptance_criteria_no=$("#thread_acceptance_criteria_no").text().trim();
            }
            var row = $('#dynamicDatagrids').datagrid('getSelected');
            if (row){
                var index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
                var ed_measure_item_code = $('#dynamicDatagrids').datagrid('getEditor', {index:index,field:'measure_item_code'});
                var measure_item_code=$(ed_measure_item_code.target).textbox('getValue');
                //alert("measure_item_code:"+measure_item_code);
                var ed_item_max_value = $('#dynamicDatagrids').datagrid('getEditor', {index:index,field:'item_max_value'});
                var item_max_value=$(ed_item_max_value.target).textbox('getValue');
                //alert("item_max_value:"+item_max_value);
                var ed_item_min_value = $('#dynamicDatagrids').datagrid('getEditor', {index:index,field:'item_min_value'});
                var item_min_value=$(ed_item_min_value.target).textbox('getValue');
                //alert("item_min_value:"+item_min_value);
                var ed_item_frequency = $('#dynamicDatagrids').datagrid('getEditor', {index:index,field:'item_frequency'});
                var item_frequency=$(ed_item_frequency.target).textbox('getValue');
                //alert("item_frequency:"+item_frequency);
                var ed_both_ends = $('#dynamicDatagrids').datagrid('getEditor', {index:index,field:'both_ends'});
                var both_ends=$(ed_both_ends.target).combobox('getValue');
                //alert("both_ends:"+both_ends);

                var ed_reading_types = $('#dynamicDatagrids').datagrid('getEditor', {index:index,field:'reading_types'});
                var reading_types=$(ed_reading_types.target).combobox('getValue');

                var ed_item_std_value = $('#dynamicDatagrids').datagrid('getEditor', {index:index,field:'item_std_value'});
                var item_std_value=$(ed_item_std_value.target).combobox('getValue');

                var ed_item_pos_deviation_value = $('#dynamicDatagrids').datagrid('getEditor', {index:index,field:'item_pos_deviation_value'});
                var item_pos_deviation_value=$(ed_item_pos_deviation_value.target).combobox('getValue');

                var ed_item_neg_deviation_value = $('#dynamicDatagrids').datagrid('getEditor', {index:index,field:'item_neg_deviation_value'});
                var item_neg_deviation_value=$(ed_item_neg_deviation_value.target).combobox('getValue');


                if(measure_item_code==null||measure_item_code==""){
                    hlAlertFour("请选择测量项!");
                    return false;
                }
                else if(item_max_value!=null&&item_min_value!=null&&item_max_value!=""&&item_min_value!=""&&item_max_value<item_min_value){
                    //alert("max:"+item_max_value);
                    hlAlertFour("请确保【最大值】>=【最小值】!");
                    return false;
                }
                else if(item_frequency!=null&&item_frequency!=""&&(item_frequency>1||item_frequency<=0)){
                    hlAlertFour("请确保0<检验频率<1!");
                    return false;
                }

                else if(both_ends==null||both_ends==""){
                    hlAlertFour("请选择测量AB端项!");
                    return false;
                }
                else if(reading_types==null||reading_types==""){
                    hlAlertFour("请选择测量项类型!");
                    return false;
                }



                else{
                    $('#dynamicDatagrids').datagrid('endEdit',index);
                    $.ajax({
                        url:'/DynamicMeasure/saveDynamicMeasureItem.action',
                        dataType:'json',
                        data:{id:row.id,measure_item_code:row.measure_item_code,thread_acceptance_criteria_no:thread_acceptance_criteria_no,item_max_value:row.item_max_value,item_min_value:row.item_min_value,item_frequency:row.item_frequency,both_ends:row.both_ends},
                        success:function (data) {
                            //如果是新增，则返回新增id,如果是修改，则返回执行结果
                            if(data.promptkey=="success"){
                                $("#dynamicDatagrids").datagrid("reload");
                                hlAlertFour("保存成功!");
                            }else if(data.promptkey="ishave"){
                                hlAlertFour(data.promptValue);
                                $('#dynamicDatagrids').datagrid('beginEdit',index);
                            }else{
                                hlAlertFour("系统繁忙!");
                            }
                        },error:function () {
                            hlAlertFour("系统繁忙!");
                        }
                    });
                }
            } else {
                hlAlertFour("请选中要修改或添加的行!");
            }
        }
        function rowEdit() {
            var row = $('#dynamicDatagrids').datagrid('getSelected');
            if(row){
                var index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
                onClickRow(index);
                //$('#dynamicDatagrids').datagrid('beginEdit',index);
            }else {
                hlAlertFour("请选中要修改或添加的行!");
            }
        }
        function  rowDelete(thread_acceptance_criteria_no){

            var row = $('#dynamicDatagrids').datagrid('getSelected');
            //var index;
            if (row){
                var index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
                if(row.id==null){
                    //删除未保存的新增记录行
                    $('#dynamicDatagrids').datagrid('deleteRow',index);
                    return;
                }


                $.messager.confirm('Confirm','确定要删除吗?',function(r){
                    if (r){
                        var index= $('#dynamicDatagrids').datagrid('getRowIndex', row);
                        if(row.id==null||row.id==""){
                            $('#dynamicDatagrids').datagrid('deleteRow',index);
                        }else{
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


        var editIndex = undefined;
        function endEditing()
        {

            if(editIndex == undefined)
            {return true;}//如果为undefined的话，为真，说明可以编辑
            if($('#dynamicDatagrids').datagrid('validateRow',editIndex))
            {
                $('#dynamicDatagrids').datagrid('endEdit',editIndex);//当前行编辑事件取消
                editIndex = undefined;
                return true;//重置编辑行索引对象，返回真，允许编辑
            }else
            {return false;}//否则，为假，返回假，不允许编辑
        }

        function onClickRow(index)//这是触发行事件
        {
            if(endEditing()){
                $('#dynamicDatagrids').datagrid('selectRow',index).datagrid('beginEdit',index)//其中beginEdit方法为datagrid的方法，具体可以参看api
                editIndex = index;//给editIndex对象赋值，index为当前行的索引
            }else {
                $('#dynamicDatagrids').datagrid('selectRow',editIndex);
            }
        }

        //readingTypes改变事件
        function selectReadingTypes(){

            var a='0';
            var b='0';
            var c='0';
            var d='0';
            var e='0';

            if($('#is-singlevalue').is(":checked")){
                $('#is-glass-sample').prop('checked', true);
                a='1';
            }
            if($('#is-maxvalue').is(":checked")){
                $('#is-maxvalue').prop('checked', true);
                b='1';
            }
            if($('#is-minvalue').is(":checked")){
                $('#is-minvalue').prop('checked', true);
                c='1';
            }
            if($('#is-avgvalue').is(":checked")){
                $('#is-avgvalue').prop('checked', true);
                d='1';
            }
            if($('#is-ovalityvalue').is(":checked")){
                $('#is-ovalityvalue').prop('checked', true);
                e='1';
            }
            var readingTypes=a+b+c+d+e;

            alert(readingTypes);

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
                <th field="od" align="center" width="100" class="i18n1" name="od"></th>
                <th field="wt" align="center" width="100" class="i18n1" name="wt"></th>
                <th field="customer_spec" align="center" width="100" class="i18n1" name="customerspec"></th>
                <th field="coupling_type" align="center" width="100" class="i18n1" name="couplingtype"></th>
                <th field="threading_type" align="center" width="100" class="i18n1" name="threadingtype"></th>

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
    <span class="i18n1" name="od">外径</span>:
    <input id="searchod" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="wt">壁厚</span>:
    <input id="searchwt" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="customerspec">客户标准</span>:
    <input id="searchcustomerspec" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="couplingtype">接箍类型</span>:
    <input id="searchcouplingtype" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="threadingtype">螺纹类型</span>:
    <input id="searchthreadingtype" style="line-height:22px;border:1px solid #ccc">


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
                <label id="thread_acceptance_criteria_no" class="hl-label"></label>
                <a href="#" id="createNoBtn" class="easyui-linkbutton i18n1" style="display:inline-block;float:right;right:30px;" name="createstandardno" onclick="addAcceptanceCriteriaFunction()">生成标准编号</a>
                    <table class="ht-table"  width="100%" border="0">

                    <tr>
                        <td class="i18n1" name="od"></td>
                        <td><input class="easyui-textbox"   type="text" name="od" value=""/></td>
                        <td></td>
                        <td class="i18n1" name="wt"></td>
                        <td><input class="easyui-textbox"   type="text" name="wt" value=""/></td>
                        <td></td>
                    </tr>
                        <tr>
                            <td class="i18n1" name="customerspec"></td>
                            <td><input class="easyui-textbox"   type="text" name="customer_spec" value=""/></td>
                            <td></td>
                            <td class="i18n1" name="couplingtype"></td>
                            <td><input class="easyui-textbox"   type="text" name="coupling_type" value=""/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="i18n1" name="threadingtype"></td>
                            <td><input class="easyui-textbox"   type="text" name="threading_type" value=""/></td>
                            <td></td>
                        </tr>

                    </table>
                    <div id="dlg-buttons" align="center" style="width:900px;">
                        <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="addEditFormSubmit()">Save</a>
                        <a href="#" class="easyui-linkbutton" id="hlcancelBtn" operationtype="add" iconCls="icon-cancel" onclick="CancelSubmit()">Cancel</a>
                    </div>

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