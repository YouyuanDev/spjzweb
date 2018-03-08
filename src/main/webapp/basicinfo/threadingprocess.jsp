<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>螺纹检验</title>
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
            url="/ThreadingOperation/saveThreadingProcess.action";
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
                        $.post("/ThreadingOperation/delThreadingProcess.action",{"hlparam":idArrs},function (data) {
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
                $('#inspection_time').datetimebox('setValue',getDate1(row.inspection_time));
                url="/ThreadingOperation/saveThreadingProcess.action?id="+row.id;
                look1.setText(row.tool_measuring_record_no);
                look1.setValue(row.tool_measuring_record_no);
                $.ajax({
                    url:'../ToolOperation/getToolRecordByNo.action',
                    data:{'tool_measuring_record_no':row.tool_measuring_record_no},
                    dataType:'json',
                    success:function (data) {
                        if(data!=null&&data!=""){
                            addLabelPipeInfo(data);
                        }
                    },
                    error:function () {
                        hlAlertThree();
                    }
                });
            }else{
                hlAlertTwo();
            }
        }
        function searchOperationPro() {
            $('#oprationDatagrids').datagrid('load',{
                'couping_no': $('#couping_no').val(),
                'operator_no': $('#operatorno').val(),
                'begin_time': $('#begintime').val(),
                'end_time': $('#endtime').val()
            });
        }
        function OperationProFormSubmit() {
            $('#OperationProForm').form('submit',{
                url:url,
                onSubmit:function () {
                    setAllParams();
                    if($("input[name='inspectiontime']").val()==""){
                        hlAlertFour("请输入检验时间");
                        return false;
                    }
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
            $('.hl-label').text('');
        }
        function setAllParams() {
            setParams($("input[name='thread_tooth_pitch_diameter_max']"));
            setParams($("input[name='thread_tooth_pitch_diameter_avg']"));
            setParams($("input[name='thread_tooth_pitch_diameter_min']"));
            setParams($("input[name='thread_sealing_surface_diameter_max']"));
            setParams($("input[name='thread_sealing_surface_diameter_avg']"));
            setParams($("input[name='thread_sealing_surface_diameter_min']"));
            setParams($("input[name='thread_sealing_surface_ovality']"));
            setParams($("input[name='thread_width']"));
            setParams($("input[name='thread_pitch']"));
            setParams($("input[name='thread_taper']"));
            setParams($("input[name='thread_height']"));
            setParams($("input[name='thread_length_min']"));
            setParams($("input[name='thread_bearing_surface_width']"));
            setParams($("input[name='couping_inner_end_depth']"));
            setParams($("input[name='thread_hole_inner_diameter']"));
            setParams($("input[name='couping_od']"));
            setParams($("input[name='couping_length']"));
            setParams($("input[name='thread_tooth_angle']"));
            setParams($("input[name='thread_throug_hole_size']"));
        }
    </script>





</head>

<body>
<fieldset class="b3" style="padding:10px;margin:10px;">
    <legend> <h3><b style="color: orange" >|&nbsp;</b><span class="i18n1" name="datadisplay">数据展示</span></h3></legend>
    <div  style="margin-top:5px;">
         <table class="easyui-datagrid" id="oprationDatagrids" url="/ThreadingOperation/getAllByLike.action" striped="true" loadMsg="正在加载中。。。" textField="text" pageSize="20" fitColumns="true" pagination="true" toolbar="#toolTb">
             <thead>
               <tr>
                   <th data-options="field:'ck',checkbox:true"></th>
                   <th field="id" align="center" width="100" class="i18n1" name="id">流水号</th>
                   <th field="couping_no" align="center" width="100" class="i18n1" name="coupingno"></th>
                   <th field="process_no" align="center" width="100" class="i18n1" name="processno"></th>
                   <th field="operator_no" align="center" width="100" class="i18n1" name="operatorno"></th>
                   <th field="inspection_time" align="center" width="100" class="i18n1" name="inspectiontime" data-options="formatter:formatterdate"></th>
                   <th field="visual_inspection" align="center" width="100" class="i18n1" name="visualinspection"></th>
                   <th field="thread_tooth_pitch_diameter_max" align="center" width="100" class="i18n1" name="threadtoothpitchdiametermax"></th>
                   <th field="thread_tooth_pitch_diameter_avg" align="center" width="100" class="i18n1" name="threadtoothpitchdiameteravg"></th>
                   <th field="thread_tooth_pitch_diameter_min" align="center" width="100" class="i18n1" name="threadtoothpitchdiametermin"></th>
                   <th field="thread_sealing_surface_diameter_max" align="center" width="100" class="i18n1" name="threadsealingsurfacediametermax" hidden="true"></th>
                   <th field="thread_sealing_surface_diameter_avg" align="center" width="100" class="i18n1" name="threadsealingsurfacediameteravg" hidden="true"></th>
                   <th field="thread_sealing_surface_diameter_min" align="center" width="100" class="i18n1" name="threadsealingsurfacediametermin" hidden="true"></th>
                   <th field="thread_sealing_surface_ovality" align="center" width="100" class="i18n1" name="threadsealingsurfaceovality" hidden="true"></th>
                   <th field="thread_width" align="center" width="100" class="i18n1" name="threadwidth" hidden="true"></th>
                   <th field="thread_pitch" align="center" width="100" class="i18n1" name="threadpitch" hidden="true"></th>
                   <th field="thread_taper" align="center" width="100" class="i18n1" name="threadtaper" hidden="true"></th>
                   <th field="thread_height" align="center" width="100" class="i18n1" name="threadheight" hidden="true"></th>
                   <th field="thread_length_min" align="center" width="100" class="i18n1" name="threadlengthmin" hidden="true"></th>
                   <th field="thread_bearing_surface_width" align="center" width="100" class="i18n1" name="threadbearingsurfacewidth" hidden="true"></th>
                   <th field="couping_inner_end_depth" align="center" width="100" class="i18n1" name="coupinginnerenddepth" hidden="true"></th>
                   <th field="thread_hole_inner_diameter" align="center" width="100" class="i18n1" name="threadholeinnerdiameter" hidden="true"></th>
                   <th field="couping_od" align="center" width="100" class="i18n1" name="coupingod" hidden="true"></th>
                   <th field="couping_length" align="center" width="100" class="i18n1" name="coupinglength" hidden="true"></th>
                   <th field="thread_tooth_angle" align="center" width="100" class="i18n1" name="threadtoothangle" hidden="true"></th>
                   <th field="thread_throug_hole_size" align="center" width="100" class="i18n1" name="threadthrougholesize" hidden="true"></th>
                   <th field="video_no" align="center" width="100" class="i18n1" name="videono" hidden="true"></th>
                   <th field="tool_measuring_record_no" align="center" width="100" class="i18n1" name="toolmeasuringrecordno" hidden="true"></th>
                   <th field="inspection_result" align="center" width="100" class="i18n1" name="inspectionresult" hidden="true"></th>
               </tr>
             </thead>
         </table>

    </div>
</fieldset>

<!--工具栏-->
<div id="toolTb" style="padding:10px;">
    <span class="i18n1" name="coupingno">工位编号</span>:
    <input id="couping_no" name="couping_no" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="operatorno">操作工工号</span>:
    <input id="operatorno" name="operatorno" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="begintime">开始时间</span>:
    <input id="begintime" name="begintime" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser">
    <span class="i18n1" name="endtime">结束时间</span>:
    <input id="endtime" name="endtime" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser">
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
           <legend>工具信息</legend>
           <table  width="100%" border="0">
               <tr>
                   <td align="center" class="i18n1" name="toolmeasuringrecordno"></td>
                   <td colspan="7">
                       <input id="lookup1" name="tool_measuring_record_no" class="mini-lookup" style="text-align:center;width:180px;"
                              textField="tool_measuring_record_no" valueField="id" popupWidth="auto"
                              popup="#gridPanel1" grid="#datagrid1" multiSelect="false"/>
                   </td>
               </tr>
               <tr>
                   <td align="center" class="i18n1" name="threadpitchgaugeno"></td>
                   <td align="center"><label class="hl-label" id="thread_pitch_gauge_no"></label></td>
                   <td align="center" class="i18n1" name="threadpitchcalibrationframwork"></td>
                   <td align="center"><label class="hl-label" id="thread_pitch_calibration_framwork"></label></td>
                   <td align="center" class="i18n1" name="sealingsurfacegaugeno"></td>
                   <td align="center"><label class="hl-label" id="sealing_surface_gauge_no"></label></td>
                   <td align="center" class="i18n1" name="sealingsurfacecalibrationringno"></td>
                   <td align="center"><label class="hl-label" id="sealing_surface_calibration_ring_no"></label></td>
               </tr>
               <tr>
                   <td align="center" class="i18n1" name="depthcaliperno"></td>
                   <td align="center"><label class="hl-label" id="depth_caliper_no"></label></td>
                   <td align="center" class="i18n1" name="threadingdistancegaugeno"></td>
                   <td align="center"><label class="hl-label" id="threading_distance_gauge_no"></label></td>
                   <td align="center" class="i18n1" name="threaddistancecalibrationsampleno"></td>
                   <td align="center"><label class="hl-label" id="thread_distance_calibration_sample_no"></label></td>
                   <td align="center" class="i18n1" name="tapergaugeno"></td>
                   <td align="center"><label class="hl-label" id="taper_gauge_no"></label></td>
               </tr>
               <tr>
                   <td align="center" class="i18n1" name="toothheightgaugeno"></td>
                   <td align="center"><label class="hl-label" id="tooth_height_gauge_no"></label></td>
                   <td align="center" class="i18n1" name="toothheightcalibrationsampleno"></td>
                   <td align="center"><label class="hl-label" id="tooth_height_calibration_sample_no"></label></td>
                   <td align="center" class="i18n1" name="toothwidthstopgaugeno"></td>
                   <td align="center"><label class="hl-label" id="tooth_width_stop_gauge_no"></label></td>
                   <td align="center" class="i18n1" name="threadminlengthsampleno"></td>
                   <td align="center"><label class="hl-label" id="thread_min_length_sample_no"></label></td>
               </tr>
               <tr>
                   <td align="center" class="i18n1" name="couplinglengthsampleno"></td>
                   <td align="center"><label class="hl-label" id="coupling_length_sample_no"></label></td>
                   <td align="center" class="i18n1" name="caliperno"></td>
                   <td align="center"><label class="hl-label" id="caliper_no"></label></td>
                   <td align="center" class="i18n1" name="calipertolerance"></td>
                   <td align="center"><label class="hl-label" id="caliper_tolerance"></label></td>
                   <td align="center" class="i18n1" name="collargaugeno"></td>
                   <td align="center"><label class="hl-label" id="collar_gauge_no"></label></td>
               </tr>
           </table>
       </fieldset>
       <fieldset style="width:900px;border:solid 1px #aaa;margin-top:8px;position:relative;">
           <legend>螺纹检验信息</legend>
           <table class="ht-table" width="100%" border="0">
               <tr>
                   <td class="i18n1" name="coupingno"></td>
                   <td><input class="easyui-textbox" type="text" name="couping_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="processno"></td>
                   <td><input class="easyui-textbox" type="text" name="process_no" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="operatorno"></td>
                   <td><input class="easyui-textbox" type="text" name="operator_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="inspectiontime"></td>
                   <td>
                       <input class="easyui-datetimebox" id="inspection_time" type="text" name="inspectiontime" value="" data-options="formatter:myformatter2,parser:myparser2"/>
                   </td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="visualinspection"></td>
                   <td><input class="easyui-textbox" type="text" name="visual_inspection" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadtoothpitchdiametermax"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_tooth_pitch_diameter_max" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadtoothpitchdiameteravg"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_tooth_pitch_diameter_avg" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadtoothpitchdiametermin"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_tooth_pitch_diameter_min" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadsealingsurfacediametermax"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_sealing_surface_diameter_max" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadsealingsurfacediameteravg"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_sealing_surface_diameter_avg" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadsealingsurfacediametermin"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" name="thread_sealing_surface_diameter_min" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadsealingsurfaceovality"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_sealing_surface_ovality" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadwidth"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_width" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadpitch"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_pitch" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadtaper"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_taper" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadheight"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_height" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadlengthmin"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_length_min" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadbearingsurfacewidth"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_bearing_surface_width" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="coupinginnerenddepth"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="couping_inner_end_depth" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadholeinnerdiameter"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_hole_inner_diameter" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="coupingod"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="couping_od" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="coupinglength"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="couping_length" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="threadtoothangle"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_tooth_angle" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="threadthrougholesize"></td>
                   <td><input class="easyui-numberbox" data-options="precision:2" type="text" name="thread_throug_hole_size" value=""/></td>
                   <td></td>
               </tr>
               <tr>
                   <td class="i18n1" name="videono"></td>
                   <td><input class="easyui-textbox" type="text" name="video_no" value=""/></td>
                   <td></td>
                   <td class="i18n1" name="inspectionresult"></td>
                   <td>
                       <select  class="easyui-combobox" data-options="editable:false"  name="inspection_result" style="width:185px;">
                           <option value="0">合格</option>
                           <option value="1">不合格</option>
                           <option value="2">待定</option>
                       </select>
                   </td>
                   <td></td>
               </tr>
           </table>
       </fieldset>
   </form>
</div>
<div id="dlg-buttons" align="center" style="width:900px;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="OperationProFormSubmit()">Save</a>
    <a href="#" class="easyui-linkbutton" id="hlcancelBtn" operationtype="add" iconCls="icon-cancel" onclick="OperationProCancelSubmit()">Cancel</a>
</div>
<div id="gridPanel1" class="mini-panel" title="header" iconCls="icon-add" style="width:450px;height:250px;"
     showToolbar="true" showCloseButton="true" showHeader="false" bodyStyle="padding:0" borderStyle="border:0"
>
    <div property="toolbar" id="searchBar1" style="padding:5px;padding-left:8px;text-align:center;display: none">
        <div style="float:left;padding-bottom:2px;">
            <span class="i18n1" name="toolmeasuringrecordno">量具记录编号</span><span>:</span>
            <input id="keyText1" class="mini-textbox" style="width:110px;" onenter="onSearchClick(1)"/>
            <a class="mini-button" onclick="onSearchClick(1)">查找</a>
            <a class="mini-button" onclick="onClearClick(1)" name="clear">清除</a>
        </div>
        <div style="float:right;padding-bottom:2px;">
            <a class="mini-button" onclick="onCloseClick(1)" name="close">关闭</a>
        </div>
        <div style="clear:both;"></div>
    </div>
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;"
         borderStyle="border:0" showPageSize="false" showPageIndex="false"
         url="/ToolOperation/getAllToolRecord.action">
        <div property="columns">
            <div type="checkcolumn" ></div>
            <div field="tool_measuring_record_no" width="80" headerAlign="center" allowSort="true" class="i18n1" name="toolmeasuringrecordno">钢管编号</div>
        </div>
    </div>
</div>
<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    var keyText1=mini.get('keyText1');
    var grid1=mini.get("datagrid1");
    var look1=mini.get('lookup1');
    function onSearchClick(type) {
        if(type==1)
        {
            grid1.load({
                tool_measuring_record_no:keyText1.value
            });
        }
    }
    function onCloseClick(type) {
        if(type==1)
            look1.hidePopup();
    }
    function onClearClick(type) {
        if(type==1)
            look1.deselectAll();
    }
    look1.on('valuechanged',function () {
        var rows = grid1.getSelected();
        $("input[name='tool_measuring_record_no']").val(rows.tool_measuring_record_no);
        clearLabelPipeInfo(rows);
        $.ajax({
            url:'../ToolOperation/getToolRecordByNo.action',
            data:{'tool_measuring_record_no':rows.tool_measuring_record_no},
            dataType:'json',
            success:function (data) {
                if(data!=null&&data!=""){
                    addLabelPipeInfo(data);
                }
            },
            error:function () {
                hlAlertThree();
            }
        });
    });

    look1.on("showpopup",function(e){
        $('.mini-shadow').css('z-index','99999');
        $('.mini-popup').css('z-index','100000');
        $('.mini-panel').css('z-index','100000');
        $('#searchBar1').css('display','block');
        grid1.load({
            tool_measuring_record_no:keyText1.value
        });
    });
    hlLanguage("../i18n/");
</script>