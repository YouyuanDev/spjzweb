<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测量工具记录</title>
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
        var basePath ="<%=basePath%>"+"/upload/pictures/";
        $(function () {
                $('#toolDialog').dialog({
                    onClose:function () {
                        var type=$('#hlcancelBtn').attr('operationtype');
                        clearFormLabel();
                    }
                });
               $('.mini-buttonedit .mini-buttonedit-input').css('width','150px');
               // hlLanguage("../i18n/");
        });
        function addOperationPro(){
            $('#hlcancelBtn').attr('operationtype','add');
            $('#toolDialog').dialog('open').dialog('setTitle','新增');
            $('#OperationProForm').form('clear');
            url="/ToolOperation/saveToolMeasuringRecord.action";
        }
        function delOperationPro() {
            var row = $('#oprationDatagrids').datagrid('getSelections');
            if(row.length>0){
                var idArr=[];
                for (var i=0;i<row.length;i++){
                    idArr.push(row[i].id);
                }
                var idArrs=idArr.join(',');
                $.messager.confirm('系统提示',"您确定要删除这<font color=red>"+idArr.length+ "</font>条数据吗？",function (r) {
                    if(r){
                        $.post("/ToolOperation/delToolMeasuringRecord.action",{"hlparam":idArrs},function (data) {
                            if(data.success){
                                $("#oprationDatagrids").datagrid("reload");
                            }
                            hlAlertFour(data.message);
                        },"json");
                    }
                });
            }else{
                hlAlertOne();
            }
        }
        function editOperationPro() {
            $('#hlcancelBtn').attr('operationtype','edit');
            var row = $('#oprationDatagrids').datagrid('getSelected');
            if(row){
                $('#toolDialog').dialog('open').dialog('setTitle','修改');
                $('#OperationProForm').form('load',row);
                url="/ToolOperation/saveToolMeasuringRecord.action?id="+row.id;
            }else{
                hlAlertTwo();
            }
        }
        function searchOperationPro() {
            $('#oprationDatagrids').datagrid('load',{
                'tool_measuring_record_no': $('#tool_measuring_record_no').val()
            });
        }
        function OperationProFormSubmit() {
            $('#OperationProForm').form('submit',{
                url:url,
                onSubmit:function () {
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.success){
                        $('#toolDialog').dialog('close');
                        $('#oprationDatagrids').datagrid('reload');
                        clearFormLabel();
                    }
                    hlAlertFour(result.message);
                },
                error:function () {
                    hlAlertThree();
                }
            });
        }
        function OperationProCancelSubmit() {
            $('#toolDialog').dialog('close');
        }
        function  clearFormLabel() {
            $('#OperationProForm').form('clear');
        }
    </script>





</head>

<body>
<fieldset class="b3" style="padding:10px;margin:10px;">
    <legend> <h3><b style="color: orange" >|&nbsp;</b><span class="i18n1" name="datadisplay">数据展示</span></h3></legend>
    <div  style="margin-top:5px;">
         <table class="easyui-datagrid" id="oprationDatagrids" url="/ToolOperation/getAllByLike.action" striped="true" loadMsg="正在加载中。。。" textField="text" pageSize="20" fitColumns="true" pagination="true" toolbar="#toolTb">
             <thead>
               <tr>
                   <th data-options="field:'ck',checkbox:true"></th>
                   <th field="id" align="center" width="100" class="i18n1" name="id">流水号</th>
                   <th field="tool_measuring_record_no" align="center" width="100" class="i18n1" name="toolmeasuringrecordno"></th>
                   <th field="thread_pitch_gauge_no" align="center" width="100" class="i18n1" name="threadpitchgaugeno"></th>
                   <th field="thread_pitch_calibration_framwork" align="center" width="100" class="i18n1" name="threadpitchcalibrationframwork"></th>
                   <th field="sealing_surface_gauge_no" align="center" width="100" class="i18n1" name="sealingsurfacegaugeno"></th>
                   <th field="sealing_surface_calibration_ring_no" align="center" width="100" class="i18n1" name="sealingsurfacecalibrationringno"></th>
                   <th field="depth_caliper_no" align="center" width="100" class="i18n1" name="depthcaliperno"></th>
                   <th field="threading_distance_gauge_no" align="center" width="100" class="i18n1" name="threadingdistancegaugeno"></th>
                   <th field="thread_distance_calibration_sample_no" align="center" width="100" class="i18n1" name="threaddistancecalibrationsampleno" hidden="true"></th>
                   <th field="taper_gauge_no" align="center" width="100" class="i18n1" name="tapergaugeno" hidden="true"></th>
                   <th field="tooth_height_gauge_no" align="center" width="100" class="i18n1" name="toothheightgaugeno" hidden="true"></th>
                   <th field="tooth_height_calibration_sample_no" align="center" width="100" class="i18n1" name="toothheightcalibrationsampleno" hidden="true"></th>
                   <th field="tooth_width_stop_gauge_no" align="center" width="100" class="i18n1" name="toothwidthstopgaugeno" hidden="true"></th>
                   <th field="thread_min_length_sample_no" align="center" width="100" class="i18n1" name="threadminlengthsampleno" hidden="true"></th>
                   <th field="coupling_length_sample_no" align="center" width="100" class="i18n1" name="couplinglengthsampleno" hidden="true"></th>
                   <th field="caliper_no" align="center" width="100" class="i18n1" name="caliperno" hidden="true"></th>
                   <th field="caliper_tolerance" align="center" width="100" class="i18n1" name="calipertolerance" hidden="true"></th>
                   <th field="collar_gauge_no" align="center" width="100" class="i18n1" name="collargaugeno" hidden="true"></th>
               </tr>
             </thead>
         </table>

    </div>
</fieldset>

<!--工具栏-->
<div id="toolTb" style="padding:10px;">
    <span class="i18n1" name="toolmeasuringrecordno"></span>:
    <input id="tool_measuring_record_no" name="tool_measuring_record_no" style="line-height:22px;border:1px solid #ccc">
    <a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="searchOperationPro()">Search</a>
    <div style="float:right">
     <a href="#" id="addObpLinkBtn" class="easyui-linkbutton i18n1" name="add" data-options="iconCls:'icon-add',plain:true" onclick="addOperationPro()">添加</a>
     <a href="#" id="editObpLinkBtn" class="easyui-linkbutton i18n1" name="edit" data-options="iconCls:'icon-edit',plain:true" onclick="editOperationPro()">修改</a>
     <a href="#" id="deltObpLinkBtn" class="easyui-linkbutton i18n1" name="delete" data-options="iconCls:'icon-remove',plain:true" onclick="delOperationPro()">删除</a>
    </div>
</div>

<!--添加、修改框-->
<div id="toolDialog" class="easyui-dialog" data-options="title:'添加',modal:true"  closed="true" buttons="#dlg-buttons" style="display: none;padding:5px;width:950px;height:auto;">
   <form id="OperationProForm" method="post">
       <fieldset style="width:900px;border:solid 1px #aaa;margin-top:8px;position:relative;">
           <legend>工具测量使用记录</legend>
           <table class="ht-table" width="100%" border="0">
               <tr>
                   <td class="i18n1" name="toolmeasuringrecordno"></td>
                   <td><input class="easyui-textbox" type="text" name="tool_measuring_record_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadpitchgaugeno"></td>
                   <td><input class="easyui-textbox" type="text" name="thread_pitch_gauge_no" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadpitchcalibrationframwork"></td>
                   <td><input class="easyui-textbox" type="text" name="thread_pitch_calibration_framwork" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="sealingsurfacegaugeno"></td>
                   <td><input class="easyui-textbox" type="text" name="sealing_surface_gauge_no" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="sealingsurfacecalibrationringno"></td>
                   <td><input class="easyui-textbox" type="text" name="sealing_surface_calibration_ring_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="depthcaliperno"></td>
                   <td><input class="easyui-textbox" type="text" name="depth_caliper_no" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadingdistancegaugeno"></td>
                   <td><input class="easyui-textbox" type="text" name="threading_distance_gauge_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threaddistancecalibrationsampleno"></td>
                   <td><input class="easyui-textbox" type="text" name="thread_distance_calibration_sample_no" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="tapergaugeno"></td>
                   <td><input class="easyui-textbox" type="text" name="taper_gauge_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="toothheightgaugeno"></td>
                   <td><input class="easyui-textbox" type="text" name="tooth_height_gauge_no" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="toothheightcalibrationsampleno"></td>
                   <td><input class="easyui-textbox" type="text" name="tooth_height_calibration_sample_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="toothwidthstopgaugeno"></td>
                   <td><input class="easyui-textbox" type="text" name="tooth_width_stop_gauge_no" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadminlengthsampleno"></td>
                   <td><input class="easyui-textbox" type="text" name="thread_min_length_sample_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="couplinglengthsampleno"></td>
                   <td><input class="easyui-textbox" type="text" name="coupling_length_sample_no" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="caliperno"></td>
                   <td><input class="easyui-textbox" type="text" name="caliper_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="calipertolerance"></td>
                   <td><input class="easyui-textbox" type="text" name="caliper_tolerance" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="collargaugeno"></td>
                   <td><input class="easyui-textbox" type="text" name="collar_gauge_no" value=""/></td>
                   <td></td>
           </table>
       </fieldset>
   </form>
</div>
<div id="dlg-buttons" align="center" style="width:900px;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="OperationProFormSubmit()">Save</a>
    <a href="#" class="easyui-linkbutton" id="hlcancelBtn" operationtype="add" iconCls="icon-cancel" onclick="OperationProCancelSubmit()">Cancel</a>
</div>

<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    hlLanguage("../i18n/");
</script>