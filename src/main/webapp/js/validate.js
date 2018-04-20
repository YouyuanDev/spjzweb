$(function () {
    $.extend($.fn.validatebox.defaults.rules, {
        nullandlength: {
            validator: function (value, param) {
                if(value.length>0&&value.length<100)
                    return true;
                else
                    return false;
            },
            message: '该项内容(1-100)个字符!'
        }
    });
});