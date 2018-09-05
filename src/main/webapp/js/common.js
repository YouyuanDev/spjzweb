//------公共函数

//弹出框函数
function  hlAlertOne() {
    $.messager.alert('Warning','请选择要删除的行!');
}
function hlAlertTwo() {
    $.messager.alert('Warning','请选择要修改的行!');
}
function hlAlertThree() {
    $.messager.alert('Warning','系统繁忙!');
}
function hlAlertFour(txt) {
    $.messager.alert('Warning',txt);
}
function hlAlertSix(url,$imglist,$dialog,$obj) {
    var hlparam=$imglist.val().trim();
    var imgarr=[];
    if(hlparam!=""){
        imgarr=hlparam.split(';');
    }
    if((imgarr.length-1)>0){
        $.messager.confirm('系统提示',"取消上传，图片会自动删除!",function (r) {
            if(r){
                $.ajax({
                    url:url,
                    dataType:'json',
                    data:{"imgList":hlparam},
                    success:function (data) {
                    },
                    error:function () {
                        hlAlertThree();
                    }
                });
                clearMultiUpload($obj);
                $imglist.val('');
                $('#hl-gallery-con').empty();
                // $dialog.dialog('close');
            }
        });
    }else{
        clearMultiUpload($obj);
        $imglist.val('');
        $('#hl-gallery-con').empty();
        // $dialog.dialog('close');
    }
}
//表单验证函数
//---验证是否为空
function hlValidateNull($obj) {
  if($obj.val().trim()==""){
     return false;
  }
}

//清理选择文件框
function  clearMultiUpload($grid) {
    var rows = $grid.getData();
    for (var i = 0, l = rows.length; i < l; i++) {
        $grid.uploader.cancelUpload(rows[i].fileId);
        $grid.customSettings.queue.remove(rows[i].fileId);
    }
    $grid.clearData();
}
//时间转化函数
function getDate(str){
    var oDate = new Date(str);
    year=oDate.getFullYear();
    month = oDate.getMonth()+1;
    month<10?"0"+month:month;
    day = oDate.getDate();
    day<10?"0"+day:day;
    hour=oDate.getHours();
    minute=oDate.getMinutes();
    second=oDate.getSeconds();
    return year+"年"+month+"月"+day+"日"+" "+hour+":"+minute+":"+second;
}
//时间转化函数
function getDate1(str){
    var oDate = new Date(str);
    y=oDate.getFullYear();
    m = oDate.getMonth()+1;
    d = oDate.getDate();
    h=oDate.getHours();
    mins=oDate.getMinutes();
    s=oDate.getSeconds();
    return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(mins<10?('0'+mins):mins)+':'+(s<10?('0'+s):s);
}
//时间格式化
function formatterdate(value,row,index){
    return getDate1(value);
}
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparsedate(s){
    if (!s) return new Date();
    return new Date(Date.parse(s));
}
function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

// 日期格式为 2/20/2017 12:00:00 PM
function myformatter2(date){
    return getDate1(date);
}
// 日期格式为 2/20/2017 12:00:00 PM
function myparser2(s){
    if (!s) return new Date();
    return new Date(Date.parse(s));
}
function dateParser(s){
    var date=new Date(s);
    return getDate1(date);
}
//加载钢管信息
function loadPipeBaiscInfo(row) {
    $('#project_name').text(row.project_name);$('#contract_no').text(row.contract_no);
    $('#pipe_no').text(row.pipe_no);$('#status_name').text(row.status_name);
    $('#od').text(row.od);$('#wt').text(row.wt);
    $('#p_length').text(row.p_length);$('#weight').text(row.weight);
    $('#grade').text(row.grade);$('#heat_no').text(row.heat_no);
}
//图片展示模板
function getGalleryCon() {
    var str='<div id="hl-gallery" class="hl-gallery">'+
        '<span class="prev"><</span>'+
        '<div id="content">' +
        '<div id="content_list">'+
        '</div>'+
    '</div>'+
    '<span class="next">></span>'+
    '</div>';
    return str;
}
function getCalleryChildren(imgUrl) {
    var str='<dl class="content-dl">' +
        '<dt><img src="'+imgUrl+'"/></dt>' +
        '<a class="content-del">X</a>' +
        '</dl>';
    return str;
}
function editFilesList(type,imgUrl) {
    var $obj=$('#fileslist');
    if(type==0){
        var filesList=$('#fileslist').val();
        $obj.val(filesList+imgUrl+";");
    }else{
        $obj.val($obj.val().replace(imgUrl+";",''));
    }
    return $obj.val();
}
//删除选择的图片
function delUploadPicture($obj) {
    var imgUrl=$obj.siblings('dt').find('img').attr('src');
    var imgName=imgUrl.substr(imgUrl.lastIndexOf('/')+1);
    var total=$('#hl-gallery-con').find('#content_list').children('.content-dl').length;
    $.ajax({
        url:'../UploadFile/delUploadPicture.action',
        dataType:'json',
        data:{"imgList":imgName+";"},
        success:function (data) {
            if(data.success){
                var imgList=editFilesList(2,imgName);
                $obj.parent('.content-dl').remove();
                if(total<=0){
                    $('#hl-gallery-con').empty();
                }
            }else{
                hlAlertFour("移除失败!");
            }
        },
        error:function () {
            hlAlertThree();
        }
    });

}
//创建图片展示模型(参数是图片集合)
function  createPictureModel(basePath,imgList) {
    //var basePath ="<%=basePath%>"+"/upload/pictures/";
    if($('#hl-gallery').length>0){
        $('#content_list').empty();
        for(var i=0;i<imgList.length-1;i++){
            $('#content_list').append(getCalleryChildren(basePath+imgList[i]));
        }
    }else{
        $('#hl-gallery-con').append(getGalleryCon());
        for(var i=0;i<imgList.length-1;i++){
            $('#content_list').append(getCalleryChildren(basePath+imgList[i]));
        }
    }
}
//设置空值
function  setParams($obj) {
    if($obj.val()==null||$obj.val()=="")
        $obj.val(0);
}
//钢管编号异步获取钢管信息清理数据
function  clearLabelPipeInfo() {
     $('.hl-label').text('');
}
//钢管编号异步获取钢管信息添加数据
function addLabelPipeInfo(element) {
        $('#thread_pitch_gauge_no').text(element.thread_pitch_gauge_no);
        $('#thread_pitch_calibration_framwork').text(element.thread_pitch_calibration_framwork);
        $('#sealing_surface_gauge_no').text(element.sealing_surface_gauge_no);
        $('#sealing_surface_calibration_ring_no').text(element.sealing_surface_calibration_ring_no);
        $('#depth_caliper_no').text(element.depth_caliper_no);
        $('#threading_distance_gauge_no').text(element.threading_distance_gauge_no);
        $('#thread_distance_calibration_sample_no').text(element.thread_distance_calibration_sample_no);
        $('#taper_gauge_no').text(element.taper_gauge_no);
        $('#tooth_height_gauge_no').text(element.tooth_height_gauge_no);
        $('#tooth_height_calibration_sample_no').text(element.tooth_height_calibration_sample_no);
        $('#tooth_width_stop_gauge_no').text(element.tooth_width_stop_gauge_no);
        $('#thread_min_length_sample_no').text(element.thread_min_length_sample_no);
        $('#coupling_length_sample_no').text(element.coupling_length_sample_no);
        $('#caliper_no').text(element.caliper_no);
        $('#caliper_tolerance').text(element.caliper_tolerance);
        $('#collar_gauge_no').text(element.collar_gauge_no);
}
//选样改变事件
function selectIsSample() {
    if($('#is-sample').is(":checked")){
        $('#is-sample').prop('checked', true);
        $("input[name='is_sample']").val(1);
    }else{
        $('#is-sample').prop('checked', false);
        $("input[name='is_sample']").val(0);
    }
}
//
