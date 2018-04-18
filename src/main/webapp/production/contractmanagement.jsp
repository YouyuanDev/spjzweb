<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>合同管理</title>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
    <link href="../miniui/multiupload/multiupload.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="../css/common.css"/>
    <script src="../easyui/jquery.min.js" type="text/javascript"></script>
    <script src="../js/common.js" type="text/javascript"></script>
    <script src="../miniui/boot.js" type="text/javascript"></script>
    <script src="../miniui/fileupload/swfupload/swfupload.js" type="text/javascript"></script>
    <script src="../miniui/multiupload/multiupload.js" type="text/javascript"></script>
    <script  src="../js/lrscroll.js" type="text/javascript"></script>
    <script src="../js/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
    <script src="../js/language.js" type="text/javascript"></script>



    <script type="text/javascript">
        var url;
        $(function () {
            $('#addEditDialog').dialog({
                onClose:function () {
                    clearFormLabel();
                }
            });
            $('.mini-buttonedit .mini-buttonedit-input').css('width','150px');
            // hlLanguage("../i18n/");
        });
        function addFunction(){
            $('#hlcancelBtn').attr('operationtype','add');
            $('#addEditDialog').dialog('open').dialog('setTitle','新增');
            $('#serialNumber').text('');//流水号
            clearFormLabel();
            url="/Contract/saveContract.action";
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
                        $.post("/Contract/delContract.action",{"hlparam":idArrs},function (data) {
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
                $('#addEditForm').form('load',row);
                $("#serialNumber").text(row.id);
                var lasttime=formatterdate(row.last_update_time);
                $("#lastupdatetime").text(lasttime);
                url="/Contract/saveContract.action?id="+row.id;
            }else{
                hlAlertTwo();
            }
        }
        function searchFunction() {
            $('#contentDatagrids').datagrid('load',{
                'contract_no': $('#searcharg1').val()
            });
        }
        function addEditFormSubmit() {
            $('#addEditForm').form('submit',{
                url:url,
                onSubmit:function () {
                    if($("input[name='contract_no']").val()==""){
                        hlAlertFour("合同编号");
                        return false;
                    }
                    setParams($("input[name='od']"));
                    setParams($("input[name='wt']"));
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.promptkey=="success"){
                        $('#addEditDialog').dialog('close');
                        $('#contentDatagrids').datagrid('reload');
                    }else if(result.promptkey=="fail1"){
                        hlAlertFour(result.promptValue);
                    }
                    hlAlertFour(result.promptValue);
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
    </script>





</head>

<body>
<fieldset class="b3" style="padding:10px;margin:10px;">
    <legend> <h3><b style="color: orange" >|&nbsp;</b><span class="i18n1" name="datadisplay">数据展示</span></h3></legend>
    <div  style="margin-top:5px;">
        <table class="easyui-datagrid" id="contentDatagrids" url="/Contract/getContractByLike.action" striped="true" loadMsg="正在加载中。。。" textField="text" pageSize="20" fitColumns="true" pagination="true" toolbar="#toolsTab">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="id" align="center" width="100" class="i18n1" name="id"></th>
                <th field="contract_no" align="center" width="100" class="i18n1" name="contractno"></th>
                <th field="machining_contract_no" align="center" width="100" class="i18n1" name="machiningcontractno"></th>
                <th field="od" align="center" width="100" class="i18n1" name="od"></th>
                <th field="wt" align="center" width="100" class="i18n1" name="wt"></th>
                <th field="pipe_heat_no" align="center" width="100" class="i18n1" name="pipeheatno"></th>
                <th field="pipe_lot_no" align="center" width="100" class="i18n1" name="pipelotno"></th>
                <th field="pipe_steel_grade" align="center" width="100" class="i18n1" name="pipesteelgrade"></th>
                <th field="pipe_meterial" align="center" width="100" class="i18n1" name="pipemeterial"></th>
                <th field="graph_no" align="center" width="100" class="i18n1" name="graphno"></th>
                <th field="handbook_no" align="center" width="100" class="i18n1" name="handbookno"></th>
                <th field="seal_sample_graph_no" align="center" width="100" class="i18n1" name="sealsamplegraphno"></th>
                <th field="thread_sample_graph_no" align="center" width="100" class="i18n1" name="threadsamplegraphno"></th>
                <th field="thread_acceptance_criteria_no" align="center" width="100" class="i18n1" name="threadacceptancecriteriano"></th>
                <th field="remark" align="center" width="100" class="i18n1" name="remark" hidden="true"></th>
            </tr>
            </thead>
        </table>
    </div>
</fieldset>

<!--工具栏-->
<div id="toolsTab" style="padding:10px;">
    <span class="i18n1" name="contractno">合同号</span>:
    <input id="searcharg1" name="searcharg1" style="line-height:22px;border:1px solid #ccc">
    <a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="searchFunction()">Search</a>
    <div style="float:right">
        <a href="#" id="addObpLinkBtn" class="easyui-linkbutton i18n1" name="add" data-options="iconCls:'icon-add',plain:true" onclick="addFunction()">添加</a>
        <a href="#" id="editObpLinkBtn" class="easyui-linkbutton i18n1" name="edit" data-options="iconCls:'icon-edit',plain:true" onclick="editFunction()">修改</a>
        <a href="#" id="deltObpLinkBtn" class="easyui-linkbutton i18n1" name="delete" data-options="iconCls:'icon-remove',plain:true" onclick="delFunction()">删除</a>
    </div>
</div>

<!--添加、修改框-->
<div id="addEditDialog" class="easyui-dialog" data-options="title:'添加',modal:true"  closed="true" buttons="#dlg-buttons" style="display: none;padding:5px;width:950px;max-height:500px;overflow-y:auto;">
    <form id="addEditForm" method="post">
        <fieldset style="width:900px;border:solid 1px #aaa;margin-top:8px;position:relative;">
            <legend>合同信息</legend>
            <table class="ht-table"  width="100%" border="0">
                <tr>
                    <td class="i18n1" name="id">流水号</td>
                    <td colspan="1">
                        <label id="serialNumber" class="hl-label"></label>
                    </td>
                    <td></td>
                    <td class="i18n1" name="contractno"></td>
                    <td><input class="easyui-textbox" type="text" name="contract_no" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="machiningcontractno"></td>
                    <td><input class="easyui-textbox" type="text" name="machining_contract_no" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadacceptancecriteriano"></td>
                    <td>
                        <input id="millno" class="easyui-combobox" type="text" name="thread_acceptance_criteria_no"  data-options=
                                "url:'/AcceptanceCriteriaOperation/getAllDropDownAcceptanceCriteria.action',
					        method:'get',
					        valueField:'id',
					        width: 185,
					        editable:false,
					        textField:'text'"/>
                        <%--<input class="easyui-textbox" type="text" name="thread_acceptance_criteria_no" value=""/>--%>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="od"></td>
                    <td><input class="easyui-numberbox" type="text" data-options="precision:2" name="od" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="wt"></td>
                    <td><input class="easyui-numberbox" type="text" data-options="precision:2" name="wt" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="pipeheatno"></td>
                    <td><input class="easyui-textbox" type="text" name="pipe_heat_no" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="pipelotno"></td>
                    <td><input class="easyui-textbox" type="text" name="pipe_lot_no" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="pipesteelgrade"></td>
                    <td><input class="easyui-textbox" type="text" name="pipe_steel_grade" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="pipemeterial"></td>
                    <td><input class="easyui-textbox" type="text" name="pipe_meterial" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="graphno"></td>
                    <td><input class="easyui-textbox" type="text" name="graph_no" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="handbookno"></td>
                    <td><input class="easyui-textbox" type="text" name="handbook_no" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="sealsamplegraphno"></td>
                    <td><input class="easyui-textbox" type="text" name="seal_sample_graph_no" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadsamplegraphno"></td>
                    <td><input class="easyui-textbox" type="text" name="thread_sample_graph_no" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                <tr>
                    <td class="i18n1" name="remark"></td>
                    <td colspan="5" ><input class="easyui-textbox" type="text" data-options="multiline:true" name="remark" value=""/></td>
                </tr>
            </table>
        </fieldset>
    </form>


</div>
<div id="dlg-buttons" align="center" style="width:900px;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="addEditFormSubmit()">Save</a>
    <a href="#" class="easyui-linkbutton" id="hlcancelBtn" operationtype="add" iconCls="icon-cancel" onclick="CancelSubmit()">Cancel</a>
</div>


<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    var grid= mini.get("multiupload1");

    hlLanguage("../i18n/");
</script>