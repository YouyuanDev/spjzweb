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
        function addContractInfo(){
            $('#hlcancelBtn').attr('operationtype','add');
            $('#addEditDialog').dialog('open').dialog('setTitle','新增');
            $('#idContractInfoId').text('');
            clearFormLabel();
            url="/AcceptanceCriteriaOperation/saveThreadAcceptanceCriteria.action";
        }
        function delContractInfo() {
            var row = $('#ContractInfoDatagrids').datagrid('getSelections');
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
                                $("#ContractInfoDatagrids").datagrid("reload");
                            }
                            hlAlertFour(data.message);
                        },"json");
                    }
                });
            }else{
                hlAlertOne();
            }
        }
        function editContractInfo(){
            $('#hlcancelBtn').attr('operationtype','edit');
            var row = $('#ContractInfoDatagrids').datagrid('getSelected');
            if(row){
                $('#addEditDialog').dialog('open').dialog('setTitle','修改');
                $('#addEditForm').form('load',row);
                $("#idContractInfoId").text(row.id);
                var lasttime=formatterdate(row.last_update_time);
                $("#lastupdatetime").text(lasttime);
                url="/AcceptanceCriteriaOperation/saveThreadAcceptanceCriteria.action?id="+row.id;
            }else{
                hlAlertTwo();
            }
        }
        function searchContractInfo() {
            $('#ContractInfoDatagrids').datagrid('load',{
                'contract_no': $('#contract_no').val()
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
                    setParams();
                },
                success: function(result){
                    var result = eval('('+result+')');
                    $('#addEditDialog').dialog('close');
                    if (result.success){
                        $('#ContractInfoDatagrids').datagrid('reload');
                    }
                    hlAlertFour(result.message);
                },
                error:function () {
                    //clearFormLabel();
                    hlAlertThree();
                }
            });
        }
        function idContractInfoCancelSubmit() {
            $('#addEditDialog').dialog('close');
        }
        function setParams() {
            setParamsMax($("input[name='thread_pitch_diameter_max']"));
            setParamsMin($("input[name='thread_pitch_diameter_min']"));
            setParamsMax($("input[name='thread_sealing_surface_diameter_max']"));
            setParamsMin($("input[name='thread_sealing_surface_diameter_min']"));
            setParamsMax($("input[name='thread_sealing_surface_ovality_max']"));
            setParamsMin($("input[name='thread_sealing_surface_ovality_min']"));
            setParamsMax($("input[name='thread_pitch_max']"));
            setParamsMin($("input[name='thread_pitch_min']"));
            setParamsMax($("input[name='thread_taper_max']"));
            setParamsMin($("input[name='thread_taper_min']"));
            setParamsMax($("input[name='thread_height_max']"));
            setParamsMin($("input[name='thread_height_min']"));
            setParamsMax($("input[name='thread_bearing_surface_width_max']"));
            setParamsMin($("input[name='thread_bearing_surface_width_min']"));
            setParamsMax($("input[name='couping_inner_end_depth_max']"));
            setParamsMin($("input[name='couping_inner_end_depth_min']"));
            setParamsMax($("input[name='thread_hole_inner_diameter_max']"));
            setParamsMin($("input[name='thread_hole_inner_diameter_min']"));
            setParamsMax($("input[name='couping_od_max']"));
            setParamsMin($("input[name='couping_od_min']"));
            setParamsMax($("input[name='couping_length_max']"));
            setParamsMin($("input[name='couping_length_min']"));
        }
        function  setParamsMax($obj) {
            if($obj.val()==null||$obj.val()=="")
                $obj.val(9999);
        }
        function  setParamsMin($obj) {
            if($obj.val()==null||$obj.val()=="")
                $obj.val(-9999);
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
        <table class="easyui-datagrid" id="ContractInfoDatagrids" url="/AcceptanceCriteriaOperation/getAllThreadAcceptanceCriteria.action" striped="true" loadMsg="正在加载中。。。" textField="text" pageSize="20" fitColumns="true" pagination="true" toolbar="#toolsTab">
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
                <th field="pipe_meterial" align="center" width="100" class="i18n1" name="pipemeterial" hidden="true"></th>
                <th field="graph_no" align="center" width="100" class="i18n1" name="graphno" hidden="true"></th>
                <th field="handbook_no" align="center" width="100" class="i18n1" name="handbookno" hidden="true"></th>
                <th field="seal_sample_graph_no" align="center" width="100" class="i18n1" name="sealsamplegraphno" hidden="true"></th>
                <th field="thread_sample_graph_no" align="center" width="100" class="i18n1" name="threadsamplegraphno" hidden="true"></th>
                <th field="thread_acceptance_criteria_no" align="center" width="100" class="i18n1" name="threadacceptancecriteriano" hidden="true"></th>
                <th field="remark" align="center" width="100" class="i18n1" name="remark" hidden="true"></th>
            </tr>
            </thead>
        </table>
    </div>
</fieldset>

<!--工具栏-->
<div id="toolsTab" style="padding:10px;">
    <span class="i18n1" name="contractno">合同号</span>:
    <input id="contract_no" name="contract_no" style="line-height:22px;border:1px solid #ccc">
    <a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="searchContractInfo()">Search</a>
    <div style="float:right">
        <a href="#" id="addObpLinkBtn" class="easyui-linkbutton i18n1" name="add" data-options="iconCls:'icon-add',plain:true" onclick="addContractInfo()">添加</a>
        <a href="#" id="editObpLinkBtn" class="easyui-linkbutton i18n1" name="edit" data-options="iconCls:'icon-edit',plain:true" onclick="editContractInfo()">修改</a>
        <a href="#" id="deltObpLinkBtn" class="easyui-linkbutton i18n1" name="delete" data-options="iconCls:'icon-remove',plain:true" onclick="delContractInfo()">删除</a>
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
                        <label id="idContractInfoId" class="hl-label"></label>
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
                    <td><input class="easyui-textbox" type="text" name="thread_acceptance_criteria_no" value=""/></td>
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
    <a href="#" class="easyui-linkbutton" id="hlcancelBtn" operationtype="add" iconCls="icon-cancel" onclick="idContractInfoCancelSubmit()">Cancel</a>
</div>


<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    var grid= mini.get("multiupload1");

    hlLanguage("../i18n/");
</script>