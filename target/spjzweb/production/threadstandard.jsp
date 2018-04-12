<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>内防腐标准</title>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
    <link href="../miniui/multiupload/multiupload.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="../css/common.css"/>
    <script src="../easyui/jquery.min.js" type="text/javascript"></script>
    <%--<script src="../js/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>--%>
    <%--<script src="../js/language.js" type="text/javascript"></script>--%>
    <script src="../js/common.js" type="text/javascript"></script>
    <script src="../miniui/boot.js" type="text/javascript"></script>
    <%--<script  src="../miniui/js/miniui.js" type="text/javascript"></script>--%>
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
        function addAcceptance(){
            $('#hlcancelBtn').attr('operationtype','add');
            $('#addEditDialog').dialog('open').dialog('setTitle','新增');
            $('#idacceptanceId').text('');
            clearFormLabel();
            url="/AcceptanceCriteriaOperation/saveThreadAcceptanceCriteria.action";
        }
        function delAcceptance() {
            var row = $('#AcceptanceDatagrids').datagrid('getSelections');
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
                                $("#AcceptanceDatagrids").datagrid("reload");
                            }
                            hlAlertFour(data.message);
                        },"json");
                    }
                });
            }else{
                hlAlertOne();
            }
        }
        function editAcceptance(){
            $('#hlcancelBtn').attr('operationtype','edit');
            var row = $('#AcceptanceDatagrids').datagrid('getSelected');
            if(row){
                $('#addEditDialog').dialog('open').dialog('setTitle','修改');
                $('#addEditForm').form('load',row);
                $("#idacceptanceId").text(row.id);
                var lasttime=formatterdate(row.last_update_time);
                $("#lastupdatetime").text(lasttime);
                url="/AcceptanceCriteriaOperation/saveThreadAcceptanceCriteria.action?id="+row.id;
            }else{
                hlAlertTwo();
            }
        }
        function searchAcceptance() {
            $('#AcceptanceDatagrids').datagrid('load',{
                'thread_acceptance_criteria_no': $('#thread_acceptance_criteria_no').val()
            });
        }
        function addEditFormSubmit() {
            $('#addEditForm').form('submit',{
                url:url,
                onSubmit:function () {
                    if($("input[name='thread_acceptance_criteria_no']").val()==""){
                        hlAlertFour("螺纹检验接收标准编号");
                        return false;
                    }
                    setParams();
                },
                success: function(result){
                    var result = eval('('+result+')');
                    $('#addEditDialog').dialog('close');
                    if (result.success){
                        $('#AcceptanceDatagrids').datagrid('reload');
                    }
                    hlAlertFour(result.message);
                },
                error:function () {
                    //clearFormLabel();
                    hlAlertThree();
                }
            });

        }
        function idAcceptanceCancelSubmit() {
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
        <table class="easyui-datagrid" id="AcceptanceDatagrids" url="/AcceptanceCriteriaOperation/getAllThreadAcceptanceCriteria.action" striped="true" loadMsg="正在加载中。。。" textField="text" pageSize="20" fitColumns="true" pagination="true" toolbar="#toolsTab">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="id" align="center" width="100" class="i18n1" name="id">流水号</th>
                <th field="thread_acceptance_criteria_no" align="center" width="100" class="i18n1" name="threadacceptancecriteriano">螺纹检验接收标准编号</th>
                <th field="thread_pitch_diameter_max" align="center" width="100" class="i18n1" name="threadpitchdiametermax">螺纹中径最大值</th>
                <th field="thread_pitch_diameter_min" align="center" width="100" class="i18n1" name="threadpitchdiametermin">螺纹中径最小值</th>
                <th field="thread_sealing_surface_diameter_max" align="center" width="100" class="i18n1" name="threadsealingsurfacediametermax">螺纹密封面最大值</th>
                <th field="thread_sealing_surface_diameter_min" align="center" width="100" class="i18n1" name="threadsealingsurfacediametermin">螺纹密封面最小值</th>
                <th field="thread_sealing_surface_ovality_max" align="center" width="100" class="i18n1" name="threadsealingsurfaceovalitymax">螺纹密封面椭圆度最大值</th>
                <th field="thread_sealing_surface_ovality_min" align="center" width="100" class="i18n1" name="threadsealingsurfaceovalitymin">螺纹密封面椭圆度最小值</th>
                <th field="thread_pitch_max" align="center" width="100" class="i18n1" name="threadpitchmax" hidden="true">螺纹螺距最大值</th>
                <th field="thread_pitch_min" align="center" width="100" class="i18n1" name="threadpitchmin" hidden="true">螺纹螺距最小值</th>
                <th field="thread_taper_max" align="center" width="100" class="i18n1" name="threadtapermax" hidden="true">螺纹锥度最大值</th>
                <th field="thread_taper_min" align="center" width="100" class="i18n1" name="threadtapermin" hidden="true">螺纹锥度最小值</th>
                <th field="thread_height_max" align="center" width="100" class="i18n1" name="threadheightmax" hidden="true">螺纹齿高最大值</th>
                <th field="thread_height_min" align="center" width="100" class="i18n1" name="threadheightmin" hidden="true">螺纹齿高最小值</th>
                <th field="thread_bearing_surface_width_max" align="center" width="100" class="i18n1" name="threadbearingsurfacewidthmax" hidden="true">承载面宽度最大值</th>
                <th field="thread_bearing_surface_width_min" align="center" width="100" class="i18n1" name="threadbearingsurfacewidthmin" hidden="true">承载面宽度最小值</th>
                <th field="couping_inner_end_depth_max" align="center" width="100" class="i18n1" name="coupinginnerenddepthmax" hidden="true">内端面宽度最大值</th>
                <th field="couping_inner_end_depth_min" align="center" width="100" class="i18n1" name="coupinginnerenddepthmin" hidden="true">内端面宽度最小值</th>
                <th field="thread_hole_inner_diameter_max" align="center" width="100" class="i18n1" name="threadholeinnerdiametermax" hidden="true">通孔内径最大值</th>
                <th field="thread_hole_inner_diameter_min" align="center" width="100" class="i18n1" name="threadholeinnerdiametermin" hidden="true">通孔内径最小值</th>
                <th field="couping_od_max" align="center" width="100" class="i18n1" name="coupingodmax" hidden="true">接箍外径最大值</th>
                <th field="couping_od_min" align="center" width="100" class="i18n1" name="coupingodmin" hidden="true">接箍外径最小值</th>
                <th field="couping_length_max" align="center" width="100" class="i18n1" name="coupinglengthmax" hidden="true">接箍长度最大值</th>
                <th field="couping_length_min" align="center" width="100" class="i18n1" name="coupinglengthmin" hidden="true">接箍长度最小值</th>
                <th field="last_update_time" align="center" width="100" class="i18n1" name="lastupdatetime" hidden="true">更新时间</th>
            </tr>
            </thead>
        </table>
    </div>
</fieldset>

<!--工具栏-->
<div id="toolsTab" style="padding:10px;">
    <span class="i18n1" name="threadacceptancecriteriano">螺纹检验接收标准编号</span>:
    <input id="thread_acceptance_criteria_no" name="threadacceptancecriteriano" style="line-height:22px;border:1px solid #ccc">
    <a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="searchAcceptance()">Search</a>
    <div style="float:right">
        <a href="#" id="addObpLinkBtn" class="easyui-linkbutton i18n1" name="add" data-options="iconCls:'icon-add',plain:true" onclick="addAcceptance()">添加</a>
        <a href="#" id="editObpLinkBtn" class="easyui-linkbutton i18n1" name="edit" data-options="iconCls:'icon-edit',plain:true" onclick="editAcceptance()">修改</a>
        <a href="#" id="deltObpLinkBtn" class="easyui-linkbutton i18n1" name="delete" data-options="iconCls:'icon-remove',plain:true" onclick="delAcceptance()">删除</a>
    </div>
</div>

<!--添加、修改框-->
<div id="addEditDialog" class="easyui-dialog" data-options="title:'添加',modal:true"  closed="true" buttons="#dlg-buttons" style="display: none;padding:5px;width:950px;max-height:500px;overflow-y:auto;">
    <form id="addEditForm" method="post">
        <fieldset style="width:900px;border:solid 1px #aaa;margin-top:8px;position:relative;">
            <legend>内防接收标准信息</legend>
            <table class="ht-table"  width="100%" border="0">
                <tr>
                    <td class="i18n1" name="id">流水号</td>
                    <td colspan="1">
                        <label id="idacceptanceId" class="hl-label"></label>
                    </td>
                    <td></td>
                    <td class="i18n1" name="threadacceptancecriteriano"></td>
                    <td><input class="easyui-textbox" type="text" name="thread_acceptance_criteria_no" value=""/></td>
                    <td>格式(外径*壁厚):(73.02*5.51)</td>
                </tr>
                <tr>
                    <td class="i18n1" name="threadpitchdiametermax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_pitch_diameter_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadpitchdiametermin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_pitch_diameter_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="threadsealingsurfacediametermax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_sealing_surface_diameter_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadsealingsurfacediametermin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_sealing_surface_diameter_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="threadsealingsurfaceovalitymax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_sealing_surface_ovality_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadsealingsurfaceovalitymin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_sealing_surface_ovality_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="threadpitchmax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_pitch_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadpitchmin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_pitch_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="threadtapermax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_taper_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadtapermin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_taper_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="threadheightmax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_height_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadheightmin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_height_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="threadbearingsurfacewidthmax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_bearing_surface_width_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadbearingsurfacewidthmin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_bearing_surface_width_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="coupinginnerenddepthmax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="couping_inner_end_depth_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="coupinginnerenddepthmin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="couping_inner_end_depth_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="threadholeinnerdiametermax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_hole_inner_diameter_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="threadholeinnerdiametermin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="thread_hole_inner_diameter_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="coupingodmax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="couping_od_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="coupingodmin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="couping_od_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="coupinglengthmax"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="couping_length_max" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="coupinglengthmin"></td>
                    <td><input class="easyui-textbox" type="text" data-options="precision:2" name="couping_length_min" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="lastupdatetime">最后更新时间</td>
                    <td>
                        <label class="hl-label" id="lastupdatetime" type="text" name="last_update_time" value="" data-options="formatter:myformatter2,parser:myparser2"/>
                    </td>
                    <td></td>
                </tr>



            </table>
        </fieldset>
    </form>


</div>
<div id="dlg-buttons" align="center" style="width:900px;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="addEditFormSubmit()">Save</a>
    <a href="#" class="easyui-linkbutton" id="hlcancelBtn" operationtype="add" iconCls="icon-cancel" onclick="idAcceptanceCancelSubmit()">Cancel</a>
</div>


<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    var grid= mini.get("multiupload1");

    hlLanguage("../i18n/");
</script>