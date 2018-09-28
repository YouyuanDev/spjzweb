<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>螺纹检验标准</title>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
    <link href="../miniui/multiupload/multiupload.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../css/common.css"/>
    <script src="../easyui/jquery.min.js" type="text/javascript"></script>
    <%--<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>--%>
    <script src="../js/common.js" type="text/javascript"></script>
    <script src="../miniui/boot.js" type="text/javascript"></script>
    <%--<script src="../miniui/fileupload/swfupload/swfupload.js" type="text/javascript"></script>--%>
    <%--<script src="../miniui/multiupload/multiupload.js" type="text/javascript"></script>--%>
    <script src="../js/lrscroll.js" type="text/javascript"></script>
    <script src="../js/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
    <script src="../js/language.js" type="text/javascript"></script>
    <script type="text/javascript">
        var url;
        var staticItem = [];
        var editIndex = undefined;
        var addOrEdit = true;
        var thread_acceptance_criteria_no;
        $(function () {
            $('#addEditDialog').css('top', '30px');
            $('#addEditDialog').dialog({
                onClose: function () {
                    clearFormLabel();
                }
            });
            $('.mini-buttonedit .mini-buttonedit-input').css('width', '150px');
            //loadDynamicByAcceptanceNo();
        });

        function addFunction() {
            $('#hlcancelBtn').attr('operationtype', 'add');
            $('#addEditDialog').dialog('open').dialog('setTitle', '新增');
            $('#serialNumber').text('');
            $('#createNoBtn').css('display', 'block');
            addOrEdit = true;
            clearFormLabel();
            getStataticItem();
            thread_acceptance_criteria_no = "";
            loadDynamicByAcceptanceNo();
            $('#dynamicDatagrids').datagrid('loadData', {total: 0, rows: []});
            url = "/AcceptanceCriteriaOperation/saveThreadAcceptanceCriteria.action";
        }

        function delFunction() {
            var row = $('#contentDatagrids').datagrid('getSelections');
            if (row.length > 0) {
                var idArr = [];
                for (var i = 0; i < row.length; i++) {
                    idArr.push(row[i].id);
                }
                var idArrs = idArr.join(',');
                $.messager.confirm('系统提示', "您确定要删除这<font color=red>" + idArr.length + "</font>条数据吗？", function (r) {
                    if (r) {
                        $.post("/AcceptanceCriteriaOperation/delThreadAcceptanceCriteria.action", {"hlparam": idArrs}, function (data) {
                            if (data.success) {
                                $("#contentDatagrids").datagrid("reload");
                            }
                            hlAlertFour(data.message);
                        }, "json");
                    }
                });
            } else {
                hlAlertOne();
            }
        }

        function editFunction() {
            $('#hlcancelBtn').attr('operationtype', 'edit');
            addOrEdit = false;
            var row = $('#contentDatagrids').datagrid('getSelected');
            if (row) {
                $('#addEditDialog').dialog('open').dialog('setTitle', '修改');
                $('#addEditForm').form('load', row);
                $("#serialNumber").text(row.id);
                //$('#thread_acceptance_criteria_no').text(row.thread_acceptance_criteria_no);
                $('#createNoBtn').css('display', 'none');
                url = "/AcceptanceCriteriaOperation/saveThreadAcceptanceCriteria.action?id=" + row.id;
                thread_acceptance_criteria_no = row.thread_acceptance_criteria_no;
                getStataticItem();
                loadDynamicByAcceptanceNo();
            } else {
                hlAlertTwo();
            }
        }

        function searchFunction() {
            $('#contentDatagrids').datagrid('load', {
                'thread_acceptance_criteria_no': $('#standardno').val(),
                'od': $('#searchod').val(),
                'wt': $('#searchwt').val(),
                'customer_spec': $('#searchcustomerspec').val(),
                'coupling_type': $('#searchcouplingtype').val(),
                'threading_type': $('#searchthreadingtype').val()
            });
        }

        function addEditFormSubmit() {
            var temp = $('#hlcancelBtn').attr('operationtype');
            if (editIndex != undefined && temp == "edit") {
                hlAlertFour("请先保存测量项!");
                return false;
            }
            $('#addEditForm').form('submit', {
                url: url,
                onSubmit: function () {

                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    $('#addEditDialog').dialog('close');
                    if (result.success) {
                        $('#contentDatagrids').datagrid('reload');
                    }
                    hlAlertFour(result.message);
                },
                error: function () {
                    //clearFormLabel();
                    hlAlertThree();
                }
            });
        }

        function CancelSubmit() {
            $('#addEditDialog').dialog('close');
        }

        function clearFormLabel() {
            $('#addEditForm').form('clear');
            $('.hl-label').text('');
        }

        //根据接收标准编号加载动态测量项
        function loadDynamicByAcceptanceNo() {
            $('#dynamicDatagrids').datagrid({
                title: '',
                iconCls: '',
                width: 900,
                height: 350,
                idField: 'id',
                singleSelect: true,
                fitColumns: true,
                toolbar: [
                    {
                        text: '增加', iconCls: 'icon-add', handler: function () {
                            rowInsert();
                        }
                    }, {
                        text: '编辑', iconCls: 'icon-edit', handler: function () {
                            rowEdit();
                        }
                    }, {
                        text: '删除', iconCls: 'icon-remove', handler: function () {
                            rowDelete();
                        }
                    }
                ],
                url: '/DynamicMeasure/getDynamicMeasureItemByAcceptanceNo.action?thread_acceptance_criteria_no=' + thread_acceptance_criteria_no,
                columns: [[
                    {field: 'id', title: '流水号', width: 60},
                    {
                        field: 'measure_item_code', title: '测量项编号', width: 150
                    },
                    {field: 'measure_item_name', title: '测量项名称', width: 100},
                    {field: 'item_max_value', title: '接收最大值', width: 80},
                    {field: 'item_min_value', title: '接收最小值', width: 80},
                    {
                        field: 'item_frequency',
                        title: '检验频率',
                        width: 80
                    },
                    {
                        field: 'both_ends', title: 'AB端检测', width: 80
                    },
                    {
                        field: 'item_std_value',
                        title: '目标值',
                        width: 80
                    },
                    {
                        field: 'item_pos_deviation_value',
                        title: '正偏差',
                        width: 80
                    },
                    {
                        field: 'item_neg_deviation_value',
                        title: '负偏差',
                        width: 80
                    },
                    {
                        field: 'reading_types', title: '读数类型', width: 100
                    },
                    {
                        field: 'ovality_max',
                        title: '椭圆度最大值',
                        width: 80
                    }
                ]]
            });
        }

        function rowInsert() {
            isAdd = true;
            $('#cmbMeasureItem').combobox('enable');
            clearForm();
            $('#win').window('open');
        }

        function rowEdit() {
            isAdd = false;
            var row = $('#dynamicDatagrids').datagrid('getSelected');
            if (row) {
                $('#ff').form('load', row);
                $('#cmbMeasureItem').combobox('disable');
                $('#win').window('open');
            } else {
                hlAlertFour("请选中要修改或添加的行!");
            }
        }

        function rowDelete() {
            var row = $('#dynamicDatagrids').datagrid('getSelected');
            if (row) {
                $.messager.confirm('Confirm', '确定要删除吗?', function (r) {
                    if (r) {
                        var index = $('#dynamicDatagrids').datagrid('getRowIndex', row);
                        if (row.id == undefined || row.id == "") {
                            $('#dynamicDatagrids').datagrid('deleteRow', index);
                        } else {
                            $.ajax({
                                url: '/DynamicMeasure/delDynamicMeasureItem.action',
                                dataType: 'json',
                                data: {hlparam: row.id},
                                success: function (data) {
                                    //如果是新增，则返回新增id,如果是修改，则返回执行结果
                                    if (data.promptkey == "success") {
                                        $('#dynamicDatagrids').datagrid('deleteRow', index);
                                    } else {
                                        hlAlertFour("删除失败!");
                                    }
                                }, error: function () {
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

        function getStataticItem() {
            $.ajax({
                url: '/StaticMeasure/getAllDropdownStaticItem.action',
                async: false,
                dataType: 'json',
                success: function (data) {
                    if (data.promptkey == "success") {
                        staticItem = $.parseJSON(data.promptValue);
                        $("#cmbMeasureItem").combobox("loadData", staticItem);
                    }
                }, error: function () {
                    alert("失败");
                }
            });
        }

        var isAdd = true;

        //测量项下拉框值改变
        function ChangeMeasureItem() {
            if (isAdd) {
                var val = $('#cmbMeasureItem').combobox('getText');
                $('#txtMeasureItemName').textbox('setValue', val);
            }
        }

        //椭圆度值改变
        function ChangeOvality() {
            var ovality_max = $('#nbMeasureOvality').numberbox('getValue');
            if (ovality_max < 0) {
                hlAlertFour('请保证椭圆度>=0!');
                $('#nbMeasureOvality').numberbox('setValue', 0);
            }
        }

        function RequireMaxAndMIn() {
            try {
                var item_std_value = $('#nbMeasureStd').numberbox('getValue');
                var item_pos_deviation_value = $('#nbMeasurePos').numberbox('getValue');
                var item_neg_deviation_value = $('#nbMeasureNeg').numberbox('getValue');
                if (!isNaN(item_std_value) && !isNaN(item_pos_deviation_value) && !isNaN(item_neg_deviation_value)) {
                    if (item_pos_deviation_value < 0) {
                        hlAlertFour("请保证正偏差>=0!");
                        $('#nbMeasurePos').numberbox('setValue', 0);
                        return false;
                    }
                    if (item_neg_deviation_value > 0) {
                        hlAlertFour("请保证负偏差<=0!");
                        $('#nbMeasureNeg').numberbox('setValue', 0);
                        return false;
                    }
                    var item_max_value = (parseFloat(item_std_value) + parseFloat(item_pos_deviation_value));
                    var item_min_value = (parseFloat(item_std_value) + parseFloat(item_neg_deviation_value));
                    $('#nbMeasureMax').numberbox('setValue', item_max_value);
                    $('#nbMeasureMin').numberbox('setValue', item_min_value);
                }
            } catch (e) {

            }
        }

        function submitForm() {
            if (!$('#ff').form('validate')) {
                return;
            }
            if (thread_acceptance_criteria_no == "") {
                hlAlertFour("未找到接收标准编号,请重新打开!");
                return;
            }
            var id = "";
            var measure_item_code = "";
            var item_max_value = $('#nbMeasureMax').numberbox('getValue');
            var item_min_value = $('#nbMeasureMin').numberbox('getValue');
            var ovality_max = $('#nbMeasureOvality').numberbox('getValue');
            var item_frequency = $('#nbMeasureFre').numberbox('getValue');
            var both_ends = $('#cmbMeasureBoth').combobox('getValue');
            var reading_types = $('#cmbMeasureTypes').combobox('getValues');
            var item_std_value = $('#nbMeasureStd').numberbox('getValue');
            var item_pos_deviation_value = $('#nbMeasurePos').numberbox('getValue');
            var item_neg_deviation_value = $('#nbMeasureNeg').numberbox('getValue');
            if (reading_types.length > 0) {
                reading_types = reading_types.join(',');
            } else {
                hlAlertFour("请选择读数类型!");
                return;
            }
            //如果是新增
            if (isAdd) {
                id = "";
                measure_item_code = $('#cmbMeasureItem').combobox('getValue');
            } else {//修改
                var row = $('#dynamicDatagrids').datagrid('getSelected');
                if (row) {
                    id = row.id;
                    measure_item_code = row.measure_item_code;
                }
            }
            $.ajax({
                url: '/DynamicMeasure/saveDynamicMeasureItem.action',
                dataType: 'json',
                data: {
                    id: id,
                    measure_item_code: measure_item_code,
                    thread_acceptance_criteria_no: thread_acceptance_criteria_no,
                    item_max_value: item_max_value,
                    item_min_value: item_min_value,
                    item_frequency: item_frequency,
                    both_ends: both_ends,
                    reading_types: reading_types,
                    item_std_value: item_std_value,
                    item_pos_deviation_value: item_pos_deviation_value,
                    item_neg_deviation_value: item_neg_deviation_value,
                    ovality_max: ovality_max
                },
                success: function (data) {
                    //如果是新增，则返回新增id,如果是修改，则返回执行结果
                    if (data.promptkey == "success") {
                        $("#dynamicDatagrids").datagrid("reload");
                        hlAlertFour("保存成功!");
                        $('#win').window('close');
                    } else if (data.promptkey == "ishave") {
                        hlAlertFour(data.promptValue);
                    } else {
                        hlAlertFour("保存失败!");
                    }
                }, error: function () {
                    hlAlertFour("系统繁忙!");
                }
            });
        }

        function clearForm() {
            $('#ff').form('clear');
            $('#nbMeasureStd').numberbox('setValue', 0);
            $('#nbMeasurePos').numberbox('setValue', 0);
            $('#nbMeasureNeg').numberbox('setValue', 0);
            $('#nbMeasureMax').numberbox('setValue', 0);
            $('#nbMeasureMin').numberbox('setValue', 0);
            $('#win').window('close');
        }
    </script>
</head>

<body>
<fieldset class="b3" style="padding:10px;margin:10px;">
    <legend><h3><b style="color: orange">|&nbsp;</b><span class="i18n1" name="datadisplay">数据展示</span></h3></legend>
    <div style="margin-top:5px;">
        <table class="easyui-datagrid" id="contentDatagrids"
               url="/AcceptanceCriteriaOperation/getThreadAcceptanceCriteriaAllByLike.action" striped="true"
               loadMsg="正在加载中。。。" textField="text" pageSize="20" fitColumns="true" pagination="true"
               toolbar="#toolsTab">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="id" align="center" width="100" class="i18n1" name="id"></th>
                <th field="thread_acceptance_criteria_no" align="center" width="100" class="i18n1"
                    name="threadacceptancecriteriano"></th>
                <th field="od" align="center" width="100" class="i18n1" name="od"></th>
                <th field="wt" align="center" width="100" class="i18n1" name="wt"></th>
                <th field="customer_spec" align="center" width="100" class="i18n1" name="customerspec"></th>
                <th field="coupling_type" align="center" width="100" class="i18n1" name="couplingtype"></th>
                <th field="threading_type" align="center" width="100" class="i18n1" name="threadingtype"></th>
                <th field="remark" align="center" width="100" class="i18n1" name="remark"></th>
                <th field="last_update_time" align="center" width="100" class="i18n1" name="lastupdatetime"
                    data-options="formatter:formatterdate"></th>
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
    <input id="searchcustomerspec" style="line-height:22px;border:1px solid #ccc"><br>
    <div style="margin-top:10px;"></div>
    <span class="i18n1" name="couplingtype">接箍类型</span>:
    <input id="searchcouplingtype" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="threadingtype">螺纹类型</span>:
    <input id="searchthreadingtype" style="line-height:22px;border:1px solid #ccc">


    <a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="searchFunction()">Search</a>
    <div style="float:right">
        <a href="#" id="addObpLinkBtn" class="easyui-linkbutton i18n1" name="add"
           data-options="iconCls:'icon-add',plain:true" onclick="addFunction()">添加</a>
        <a href="#" id="editObpLinkBtn" class="easyui-linkbutton i18n1" name="edit"
           data-options="iconCls:'icon-edit',plain:true" onclick="editFunction()">修改</a>
        <a href="#" id="deltObpLinkBtn" class="easyui-linkbutton i18n1" name="delete"
           data-options="iconCls:'icon-remove',plain:true" onclick="delFunction()">删除</a>
    </div>
</div>
<%--<div id="dynamicToolsTab" style="padding:4px;">--%>
<%--<a href="#" id="addLinkBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addDynamicItem()"></a>--%>
<%--<a href="#" id="delLinkBtn" class="easyui-linkbutton"  data-options="iconCls:'icon-remove',plain:true" onclick="delDynamicItem()"></a>--%>
<%--</div>--%>
<!--添加、修改框-->
<div id="addEditDialog" class="easyui-dialog" data-options="closable:false,title:'添加',modal:true" closed="true"
     buttons="#dlg-buttons" style="display: none;padding:5px;width:950px;max-height:500px;overflow-y:auto;">
    <form id="addEditForm" method="post">
        <fieldset style="width:900px;border:solid 1px #aaa;position:relative;">
            <legend>标准信息</legend>
            <div style="width:100%;padding-bottom:5px;">
                <%--<a href="#" class="easyui-linkbutton i18n1" name="add" onclick="insert()">添加</a>--%>
                <%--<label class="i18n1" name="threadacceptancecriteriano"></label>:--%>
                <%--<label id="thread_acceptance_criteria_no" class="hl-label"></label>--%>
                <%--<a href="#" id="createNoBtn" class="easyui-linkbutton i18n1" style="display:inline-block;float:right;right:30px;" name="createstandardno" onclick="addAcceptanceCriteriaFunction()">生成标准编号</a>--%>
                <table class="ht-table" width="100%" border="0">
                    <tr>
                        <td class="i18n1" name="id">流水号</td>
                        <td colspan="1">
                            <label id="serialNumber" class="hl-label"></label>
                            <%--<input class="easyui-textbox" id="tid" type="text" name="tid" readonly="true" value=""/>--%>
                        </td>
                        <td></td>
                        <td class="i18n1" name="threadacceptancecriteriano"></td>
                        <td><input class="easyui-textbox" type="text" name="thread_acceptance_criteria_no"
                                   readonly="true" value=""/></td>
                        <td></td>

                    </tr>
                    <tr>
                        <td class="i18n1" name="od"></td>
                        <td><input class="easyui-textbox" type="text" name="od" value=""/></td>
                        <td></td>
                        <td class="i18n1" name="wt"></td>
                        <td><input class="easyui-textbox" type="text" name="wt" value=""/></td>
                        <td></td>
                    </tr>

                    <tr>
                        <td class="i18n1" name="customerspec"></td>
                        <td><input class="easyui-textbox" type="text" name="customer_spec" value=""/></td>
                        <td></td>
                        <td class="i18n1" name="threadingtype"></td>
                        <td><input class="easyui-textbox" type="text" name="threading_type" value=""/></td>
                        <td></td>
                    </tr>
                    <tr>

                        <td class="i18n1" name="couplingtype"></td>
                        <td><input class="easyui-textbox" type="text" name="coupling_type" value=""/></td>
                        <td></td>
                        <td class="i18n1" name="remark"></td>
                        <td><input class="easyui-textbox" type="text" name="remark" value=""/></td>
                        <td></td>
                    </tr>


                </table>
                <div id="dlg-buttons" align="center" style="width:900px;">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="addEditFormSubmit()">保存</a>
                    <a href="#" class="easyui-linkbutton" id="hlcancelBtn" operationtype="add" iconCls="icon-cancel"
                       onclick="CancelSubmit()">取消</a>
                </div>

            </div>
        </fieldset>
        <fieldset style="width:900px;border:solid 1px #aaa;position:relative;">
            <legend>测量项信息</legend>
            <table id="dynamicDatagrids">

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
        </fieldset>

    </form>
</div>

<div id="win" class="easyui-window" title="测量项" style="width:650px;height:400px;display: none;"
     data-options="iconCls:'icon-save',modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true">
    <form id="ff" method="post">
        <div style="margin-bottom:20px;padding:5px">
            <input class="easyui-textbox" name="id" style="width:300px;" editable="false" data-options="label:'流水号:'">
            <input id="cmbMeasureItem" class="easyui-combobox" name="measure_item_code" style="width:300px"
                   data-options="label:'测量项编号:',required:true,valueField:'measure_item_code',
                                textField:'code_and_name',onChange:ChangeMeasureItem">
        </div>
        <div style="margin-bottom:20px;padding:5px">
            <input id="txtMeasureItemName" class="easyui-textbox" name="measure_item_name" style="width:300px;"
                   editable="false" data-options="label:'测量项名称:'">
            <input id="nbMeasureFre" class="easyui-numberbox" name="item_frequency" style="width:300px"
                   data-options="label:'检验频率:',required:true,precision:2">
        </div>
        <div style="margin-bottom:20px;padding:5px">
            <input id="cmbMeasureBoth" class="easyui-combobox" name="both_ends" style="width:300px;"
                   data-options="label:'AB端检测:',valueField: 'id',required:true,
		textField: 'text',
		data: [{
			id: '0',
			text: '一端'
		},{
			id: '1',
			text: 'AB两端'
		}]">
            <input id="nbMeasureStd" class="easyui-numberbox" value="0" name="item_std_value" style="width:300px"
                   data-options="label:'目标值:',required:true,precision:3,onChange:RequireMaxAndMIn">
        </div>
        <div style="margin-bottom:20px;padding:5px">
            <input id="nbMeasurePos" class="easyui-numberbox" value="0" name="item_pos_deviation_value"
                   style="width:300px;"
                   data-options="label:'正偏差:',required:true,precision:3,onChange:RequireMaxAndMIn">
            <input id="nbMeasureNeg" class="easyui-numberbox" value="0" name="item_neg_deviation_value"
                   style="width:300px"
                   data-options="label:'负偏差:',required:true,precision:3,onChange:RequireMaxAndMIn">
        </div>
        <div style="margin-bottom:20px;padding:5px">
            <input id="cmbMeasureTypes" class="easyui-combobox" name="reading_types" style="width:300px;"
                   data-options="label:'读数类型:',valueField: 'id',required:true,multiple:true,
		textField: 'text',
		data: [{
			id: '1',
			text: '单值'
		},{
			id: '2',
			text: '最大值'
		},{
			id: '3',
			text: '最小值'
		},{
			id: '4',
			text: '均值'
		},{
			id: '5',
			text: '椭圆度'
		}]">
            <input id="nbMeasureOvality" class="easyui-numberbox" name="ovality_max" value="0" style="width:300px"
                   data-options="label:'椭圆度最大值:',required:true,precision:3,onChange:ChangeOvality">
        </div>
        <div style="margin-bottom:20px;padding:5px">
            <input id="nbMeasureMax" class="easyui-numberbox" value="0" name="item_max_value" style="width:300px;"
                   data-options="label:'接收最大值:',precision:3">
            <input id="nbMeasureMin" class="easyui-numberbox" value="0" name="item_min_value" style="width:300px;"
                   data-options="label:'接收最小值:',precision:3">
        </div>
    </form>
    <div style="text-align:center;padding:5px 0">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">取消</a>
    </div>
</div>

<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    hlLanguage("../i18n/");
</script>