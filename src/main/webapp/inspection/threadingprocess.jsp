<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

    basePath=basePath.substring(0,basePath.lastIndexOf('/'));
    basePath=basePath.substring(0,basePath.lastIndexOf('/'));

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
    <script src="../js/lrscroll.js" type="text/javascript"></script>
    <script src="../js/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
    <script src="../js/language.js" type="text/javascript"></script>
    <script type="text/javascript">
        var url;
        var thread_inspection_record_code;
        var staticItem=[];
        //var videoAddress="";
        $(function () {
            $('#addEditDialog').css('top','30px');
            $('#addEditDialog').dialog({
                onClose:function () {
                    clearFormLabel();
                    $("#video1")[0].pause();
                }
            });
            $('#win').dialog({
                onClose:function () {
                    $("#video1")[0].pause();
                }
            });
            $('.mini-buttonedit .mini-buttonedit-input').css('width','150px');
            showVideoPlayer();
        });

        function addFunction(){
            $('#hlcancelBtn').attr('operationtype','add');
            $('#addEditDialog').dialog('open').dialog('setTitle','新增');
            $('#serialNumber').text('');//流水号
            clearFormLabel();
            $('#videoDatagrid tbody').empty();
            thread_inspection_record_code="";
            loadItemRecordByInspectionRecordCode();
            $('#itemrecordDatagrids').datagrid('loadData', { total: 0, rows: [] });
            url="/ThreadingOperation/saveThreadingProcess.action";
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
                        $.post("/ThreadingOperation/delThreadingProcess.action",{"hlparam":idArrs},function (data) {
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
                $("#instime").datetimebox('setValue',getDate1(row.inspection_time));
                url="/ThreadingOperation/saveThreadingProcess.action?id="+row.id;
                thread_inspection_record_code=row.thread_inspection_record_code;
                getStataticItem(thread_inspection_record_code);
                loadItemRecordByInspectionRecordCode(thread_inspection_record_code);
                loadVideoDatagrid(row.video_no);
            }else{
                hlAlertTwo();
            }
        }
        function searchFunction() {
            $('#contentDatagrids').datagrid('load',{
                'contract_no': $('#searcharg1').val(),
                'coupling_no': $('#searcharg2').val(),
                'operator_no': $('#searcharg3').val(),
                'begin_time': $('#searcharg4').val(),
                'end_time': $('#searcharg5').val(),
            });
        }
        function addEditFormSubmit() {
            $('#addEditForm').form('submit',{
                url:url,
                onSubmit:function (){
                    if($("input[name='instime']").val()==""){
                        hlAlertFour("请输入操作时间");
                        return false;
                    }
                    return $(this).form('validate');
                },
                success: function(result){
                    var datajson=eval('(' + result + ')');
                    if (datajson.promptkey=="success"){
                        $('#addEditDialog').dialog('close');
                        $('#contentDatagrids').datagrid('reload');
                    }else{
                        hlAlertFour(datajson.promptValue);
                    }
                    //hlAlertFour(result.promptValue);
                },
                error:function () {
                    hlAlertThree();
                }
            });
            $("#video1")[0].pause();
        }
        function CancelSubmit() {
            $('#addEditDialog').dialog('close');
            $("#video1")[0].pause();
        }
        function  clearFormLabel(){
            $('#addEditForm').form('clear');
            $('.hl-label').text('');
        }

        function loadItemRecordByInspectionRecordCode() {
            $('#itemrecordDatagrids').datagrid({
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
                            rowSave();
                        }
                    },{ text: '编辑', iconCls: 'icon-edit', handler: function () {
                            rowEdit();
                        }
                    },{ text: '删除', iconCls: 'icon-remove', handler: function () {
                            rowDelete();
                        }
                    },{ text: '撤销', iconCls: 'icon-undo', handler: function () {
                            rowCancel();
                        }
                    }
                ],
                url:'/ItemRecordOperation/getItemRecordByInspectionNo.action?thread_inspection_record_code='+thread_inspection_record_code,
                columns:[[
                    {field:'id',title:'流水号',width:60},
                    {field:'itemcode',title:'测量项编号',width:150,
                        formatter:function(value){
                            for(var i=0; i<staticItem.length; i++){
                                if (staticItem.measure_item_code == value)  return  staticItem.measure_item_name;
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
                    {field:'measure_item_name',title:'检测项名称',width:150},
                    {field:'itemvalue',title:'检测项值',width:80,editor:'textbox'},
                    {field:'reading_max',title:'最大值',width:80,editor:'textbox'},
                    {field:'reading_min',title:'最小值',width:80,editor:'textbox'},
                    {field:'reading_avg',title:'均值',width:80,editor:'textbox'},
                    {field:'reading_ovality',title:'椭圆度',width:80,editor:'textbox'},
                    // {field:'item_frequency',title:'检测频率',width:80},
                    {field:'toolcode1',title:'量具编号1',width:80,editor:'textbox'},
                    {field:'toolcode2',title:'量具编号2',width:80,editor:'textbox'},
                    {field:'measure_sample1',title:'量具样块1编号',width:80,editor:'textbox'},
                    {field:'measure_sample2',title:'量具样块2编号',width:80,editor:'textbox'},
                    {field:'both_ends',hidden:true,title:'是否AB两端检测',width:80},
                    {field:'thread_acceptance_criteria_no',title:'标准编号',width:100},
                    {field:'item_std_value',title:'检测项值',width:80,editor:'textbox'},
                    {field:'item_max_value',title:'最大值',width:80,editor:'textbox'},
                    {field:'item_min_value',title:'最小值',width:80,editor:'textbox'},
                    {field:'ovality_max',title:'椭圆度最大值',width:80,editor:'textbox'}
                ]],
                rowStyler:function(index,row){
                    //验证测量值是否合格
                    var flag=false;
                    var rItemValue=row.itemvalue;
                    var rMaxValue=row.reading_max;
                    var rMinValue=row.reading_min;
                    var rAvgValue=row.reading_avg;
                    var rOvalityValue=row.reading_ovality;
                    //var stdItemValue=row.item_std_value;
                    var stdMaxValue=row.item_max_value;
                    var stdMinValue=row.item_min_value;
                    var stdOvality=row.ovality_max;
                    //判断是不是单值
                    if(rItemValue!=null&&rItemValue.length>0){
                        var itemValueArr=rItemValue.split(',');
                        for(var i=0;i<itemValueArr.length;i++){
                            if(!isNaN(itemValueArr[i])){
                                if(parseFloat(itemValueArr[i])>stdMaxValue||parseFloat(itemValueArr[i])<stdMinValue){
                                    flag=true;
                                }
                            }
                        }
                    }
                    //判断是否有最大值、最小值、均值、椭圆度
                    if(rMaxValue!=null&&rMaxValue.length>0){
                        var rMaxValueArr=rMaxValue.split(',');
                        for(var i=0;i<rMaxValueArr.length;i++){
                            if(!isNaN(rMaxValueArr[i])){
                                if(parseFloat(rMaxValueArr[i])>stdMaxValue||parseFloat(rMaxValueArr[i])<stdMinValue){
                                    flag=true;
                                }
                            }
                        }
                    }
                    if(rMinValue!=null&&rMinValue.length>0){
                        var rMinValueArr=rMinValue.split(',');
                        for(var i=0;i<rMinValueArr.length;i++){
                            if(!isNaN(rMinValueArr[i])){
                                if(parseFloat(rMinValueArr[i])>stdMaxValue||parseFloat(rMinValueArr[i])<stdMinValue){
                                    flag=true;
                                }
                            }
                        }
                    }
                    if(rAvgValue!=null&&rAvgValue.length>0){
                        var rAvgValueArr=rAvgValue.split(',');
                        for(var i=0;i<rAvgValueArr.length;i++){
                            if(!isNaN(rAvgValueArr[i])){
                                if(parseFloat(rAvgValueArr[i])>stdMaxValue||parseFloat(rAvgValueArr[i])<stdMinValue){
                                    flag=true;
                                }
                            }
                        }
                    }
                    if(rOvalityValue!=null&&rOvalityValue.length>0){
                        var rOvalityValueArr=rOvalityValue.split(',');
                        for(var i=0;i<rOvalityValueArr.length;i++){
                            if(!isNaN(rOvalityValueArr[i])){
                                if(parseFloat(rOvalityValueArr[i])<0||parseFloat(rOvalityValueArr[i])>stdOvality){
                                    flag=true;
                                }
                            }
                        }
                    }
                    if(flag)
                        return 'background-color:pink;color:blue;font-weight:bold;';
                    flag=false;
                }
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
            $('#itemrecordDatagrids').datagrid('appendRow',{ });
            var insertIndex = $('#itemrecordDatagrids').datagrid('getRows').length-1;
            $('#itemrecordDatagrids').datagrid('selectRow', insertIndex);
            onClickRow(insertIndex);
            //$('#itemrecordDatagrids').datagrid('beginEdit', insertIndex);
        }
        function rowSave(){
            var row = $('#itemrecordDatagrids').datagrid('getSelected');
            if (row){
                var index= $('#itemrecordDatagrids').datagrid('getRowIndex', row);
                var ed_itemcode = $('#itemrecordDatagrids').datagrid('getEditor', {index:index,field:'itemcode'});
                var itemcode=$(ed_itemcode.target).combobox('getValue');
                //alert(itemcode);
                if(itemcode==null||itemcode==""||itemcode==undefined){
                    hlAlertFour("请选择测量编号!");
                }else{
                    $('#itemrecordDatagrids').datagrid('endEdit',index);
                    $.ajax({
                        url:'/ItemRecordOperation/saveItemRecord.action',
                        dataType:'json',
                        data:{id:row.id,thread_inspection_record_code:thread_inspection_record_code,itemcode:row.itemcode,itemvalue:row.itemvalue,toolcode1:row.toolcode1,toolcode2:row.toolcode2,
                            measure_sample1:row.measure_sample1,measure_sample2:row.measure_sample2,reading_max:row.reading_max,reading_min:row.reading_min,reading_avg:row.reading_avg,reading_ovality:row.reading_ovality},
                        success:function (data) {
                            if(data.promptkey=="success"){
                                //$("#itemrecordDatagrids").datagrid("reload");
                                loadItemRecordByInspectionRecordCode(thread_inspection_record_code);
                            }else if(data.promptkey="ishave"){
                                hlAlertFour(data.promptValue);
                                $('#itemrecordDatagrids').datagrid('beginEdit',index);
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
            var row = $('#itemrecordDatagrids').datagrid('getSelected');
            if(row){
                var index= $('#itemrecordDatagrids').datagrid('getRowIndex', row);

                //关闭其他row的edit模式
                onClickRow(index);
                //$('#itemrecordDatagrids').datagrid('beginEdit',index);


            }else {
                hlAlertFour("请选中要修改或添加的行!");
            }
        }
        function  rowDelete(){
            var row = $('#itemrecordDatagrids').datagrid('getSelected');

            if (row){
                var index= $('#itemrecordDatagrids').datagrid('getRowIndex', row);
                if(row.id==null){
                    //删除未保存的新增记录行
                    $('#itemrecordDatagrids').datagrid('deleteRow',index);
                    return;
                }

                $.messager.confirm('Confirm','确定要删除吗?',function(r){
                    if (r){
                        $.ajax({
                            url:'/ItemRecordOperation/delItemRecord.action',
                            dataType:'json',
                            data:{hlparam:row.id},
                            success:function (data) {
                                //如果是新增，则返回新增id,如果是修改，则返回执行结果
                                if(data.promptkey=="success"){
                                    $('#itemrecordDatagrids').datagrid('deleteRow',index);
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
            var row = $('#itemrecordDatagrids').datagrid('getSelected');
            if (row){
                var index= $('#itemrecordDatagrids').datagrid('getRowIndex',row);
                $('#itemrecordDatagrids').datagrid('endEdit',index);
            }
        }
        function getStataticItem() {
            $.ajax({
                url:'/DynamicMeasure/getAllDropdownMeasureItemByInspectionNo.action?thread_inspection_record_code='+thread_inspection_record_code,
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
            if($('#itemrecordDatagrids').datagrid('validateRow',editIndex))
            {
                $('#itemrecordDatagrids').datagrid('endEdit',editIndex);//当前行编辑事件取消
                editIndex = undefined;
                return true;//重置编辑行索引对象，返回真，允许编辑
            }else
            {return false;}//否则，为假，返回假，不允许编辑
        }

        function onClickRow(index)//这是触发行事件
        {
            if(endEditing()){
                $('#itemrecordDatagrids').datagrid('selectRow',index).datagrid('beginEdit',index)//其中beginEdit方法为datagrid的方法，具体可以参看api
                editIndex = index;//给editIndex对象赋值，index为当前行的索引
            }else {
                $('#itemrecordDatagrids').datagrid('selectRow',editIndex);
            }
        }
        function loadVideoDatagrid(videoNumber) {
             $('#videoDatagrid tbody').empty();
             var videoArr=videoNumber.split(';');
             var VideoTr="";
             for(var i=0;i<videoArr.length;i++){
                 if(videoArr[i]!=""){
                     VideoTr+="<tr><td>"+videoArr[i]+"</td><td><a class='videoOfSpjz' href='#'>"+videoArr[i]+"</a></td></tr>";
                 }
             }
            $('#videoDatagrid tbody').append(VideoTr);
        }
        function showVideoPlayer() {
              $(document).on('click','.videoOfSpjz',function () {
                  var videoName=$(this).text().trim();
                  var videoSrc="<%=basePath%>"+"upload/videos/"+videoName;
                  $('#video1').attr("src",videoSrc);
                  $("#video1")[0].play();
                  $('#win').window({
                      width:710,
                      height:520,
                      modal:true
                  });
                  $('#win').window('open');

              });
        }
    </script>

</head>

<body>
<fieldset class="b3" style="padding:10px;margin:10px;">
    <legend> <h3><b style="color: orange" >|&nbsp;</b><span class="i18n1" name="datadisplay">数据展示</span></h3></legend>
    <div  style="margin-top:5px;">
        <table class="easyui-datagrid" id="contentDatagrids" url="/ThreadingOperation/getThreadInspectionAllByLike.action" striped="true" loadMsg="正在加载中。。。" textField="text" pageSize="20" fitColumns="true" pagination="true" toolbar="#toolsTab">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="id" align="center" width="100" class="i18n1" name="id"></th>
                <th field="thread_inspection_record_code" align="center" width="100" class="i18n1" name="threadinspectionrecordcode"></th>
                <th field="coupling_no" align="center" width="100" class="i18n1" name="couplingno"></th>
                <th field="contract_no" align="center" width="100" class="i18n1" name="contractno"></th>
                <th field="production_line" align="center" width="100" class="i18n1" name="productionline"></th>
                <th field="machine_no" align="center" width="100" class="i18n1" name="machineno"></th>
                <th field="process_no" align="center" width="100" class="i18n1" name="processno"></th>
                <th field="operator_no" align="center" width="100" class="i18n1" name="operatorno"></th>
                <th field="production_crew" align="center" width="100" class="i18n1" name="productioncrew"></th>
                <th field="production_shift" align="center" width="100" class="i18n1" name="productionshift"></th>
                <th field="coupling_heat_no" align="center" width="100" class="i18n1" name="couplingheatno"></th>
                <th field="coupling_lot_no" align="center" width="100" class="i18n1" name="couplinglotno"></th>
                <th field="video_no" align="center" width="100" class="i18n1" name="videono"></th>
                <th field="inspection_result" align="center" width="100" class="i18n1" name="inspectionresult"></th>
                <th field="inspection_time" align="center" width="100" class="i18n1" name="inspectiontime" data-options="formatter:formatterdate"></th>
            </tr>
            </thead>
        </table>
    </div>
</fieldset>

<!--工具栏-->
<div id="toolsTab" style="padding:10px;">
    <span class="i18n1" name="contractno">合同号</span>:
    <input id="searcharg1" name="searcharg1" type="text" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="couplingno">接箍编号</span>:
    <input id="searcharg2" name="searcharg2" type="text" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="operatorno">操作工号</span>:
    <input id="searcharg3" name="searcharg3" type="text" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="begintime">开始时间</span>:
    <input id="searcharg4" name="searcharg4" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="line-height:22px;border:1px solid #ccc">
    <span class="i18n1" name="endtime">结束时间</span>:
    <input id="searcharg5" name="searcharg5" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="line-height:22px;border:1px solid #ccc">
    <a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="searchFunction()">Search</a>
    <div style="float:right">
        <a href="#" id="addObpLinkBtn" class="easyui-linkbutton i18n1" name="add" data-options="iconCls:'icon-add',plain:true" onclick="addFunction()">添加</a>
        <a href="#" id="editObpLinkBtn" class="easyui-linkbutton i18n1" name="edit" data-options="iconCls:'icon-edit',plain:true" onclick="editFunction()">修改</a>
        <a href="#" id="deltObpLinkBtn" class="easyui-linkbutton i18n1" name="delete" data-options="iconCls:'icon-remove',plain:true" onclick="delFunction()">删除</a>
    </div>
</div>
<!--添加、修改框-->
<div id="addEditDialog" class="easyui-dialog" data-options="title:'添加',modal:true"  closed="true" buttons="#dlg-buttons" style="display: none;padding:5px;width:950px;max-height:600px;overflow-y:auto;">
    <form id="addEditForm" method="post">
        <input type="hidden" name="thread_inspection_record_code" value="">
        <fieldset style="width:900px;border:solid 1px #aaa;margin-top:8px;position:relative;">
            <legend>检验记录</legend>
            <table class="ht-table"  width="100%" border="0">

                <tr>
                    <td class="i18n1" name="id">流水号</td>
                    <td>
                        <label id="serialNumber" class="hl-label"></label>
                    </td>
                    <td></td>
                    <td class="i18n1" name="contractno"></td>
                    <td>
                        <input style="width:185px;" id="contract_no" class="easyui-combobox" type="text" name="contract_no"  data-options=
                                "url:'/Contract/getAllDropDownContractInfo.action',
					        method:'get',
					        valueField:'id',
					        width:285,
					        editable:false,
					        textField:'text'
                         "/>
                        <%--<input class="easyui-validatebox" data-options="required:true,validType:'nullandlength'" type="text" name="contract_no" value=""/></td>--%>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="couplingno"></td>
                    <td><input class="easyui-validatebox" type="text" data-options="required:true,validType:'nullandlength'" name="coupling_no" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="machineno"></td>
                    <td><input class="easyui-textbox" type="text" name="machine_no" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="productionline"></td>
                    <td><input class="easyui-textbox" type="text" name="production_line" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="processno"></td>
                    <td><input class="easyui-textbox" type="text" name="process_no" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="operatorno"></td>
                    <td><input class="easyui-textbox" type="text" name="operator_no" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="videono"></td>
                    <td><input class="easyui-textbox" type="text" name="video_no" value=""/>
                    </td>
                    <td>
                        <%--<div style="text-align:center">--%>
                            <%--&lt;%&ndash;<video id="video1" width="320" height="240">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<source src="ftp://ftpadmin:123456@192.168.0.200/InitVideo.mp4" type="video/mp4">&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</video>&ndash;%&gt;--%>
                        <%--</div>--%>

                    </td>
                </tr>
                <tr>
                    <td class="i18n1" name="productioncrew"></td>
                    <td>
                        <select id="pc" class="easyui-combobox" data-options="editable:false" name="production_crew"   style="width:185px;">
                            <option value="甲" selected="selected">甲</option>
                            <option value="乙">乙</option>
                            <option value="丙">丙</option>
                            <option value="丁">丁</option>
                        </select>
                        <%--<input class="easyui-textbox" type="text" name="production_crew" value=""/>--%>
                    </td>
                    <td></td>
                    <td class="i18n1" name="productionshift"></td>
                    <td>
                        <select id="ps" class="easyui-combobox" data-options="editable:false" name="production_shift"   style="width:185px;">
                            <option value="白班" selected="selected">白班</option>
                            <option value="夜班">夜班</option>
                        </select>
                        <%--<input class="easyui-textbox" type="text" name="production_shift" value=""/>--%>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="couplingheatno"></td>
                    <td><input class="easyui-textbox" type="text" name="coupling_heat_no" value=""/></td>
                    <td></td>
                    <td class="i18n1" name="couplinglotno"></td>
                    <td><input class="easyui-textbox" type="text" name="coupling_lot_no" value=""/></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="i18n1" name="inspectionresult"></td>
                    <td>
                        <%--<input class="easyui-textbox" type="text" name="inspection_result" value=""/>--%>
                            <select id="bfs" class="easyui-combobox" data-options="editable:false" name="inspection_result"   style="width:185px;">
                                <option value="合格" selected="selected">合格</option>
                                <option value="不合格">不合格</option>
                            </select>
                    </td>
                    <td></td>
                    <td class="i18n1" name="inspectiontime"></td>
                    <td>
                        <input class="easyui-datetimebox"  type="text" id="instime" name="instime" value="" data-options="formatter:myformatter2,parser:myparser2"/>
                        <%--<input class="easyui-textbox" type="text" name="inspection_time" value=""/>--%>
                    </td>
                    <td></td>
                </tr>
            </table>
            <table id="videoDatagrid"  data-options="fitColumns:true" style="width:100%;height:auto;margin-top:5px;">
                <thead>
                <th width="50%">视频编号</th>
                <th width="50%">视频链接</th>
                </thead>
                <tbody>

                </tbody>
            </table>
           </fieldset>
            <fieldset style="width:900px;border:solid 1px #aaa;margin-top:8px;position:relative;">
                <legend>检测项记录</legend>
            <table  id="itemrecordDatagrids">
            </table>

        </fieldset>
    </form>
</div>
<div id="win" class="easyui-window" closed="true"  title="视频播放" style="width:600px;height:400px;display: none;"
     data-options="iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false,modal:true">
        <video id="video1" width="100%" height="100%" controls>
            <source src="" type="video/mp4">
        </video>
</div>
<div id="dlg-buttons" align="center" style="width:900px;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="addEditFormSubmit()">Save</a>
    <a href="#" class="easyui-linkbutton" id="hlcancelBtn" operationtype="add" iconCls="icon-cancel" onclick="CancelSubmit()">Cancel</a>
</div>
<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/validate.js"></script>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    hlLanguage("../i18n/");
</script>