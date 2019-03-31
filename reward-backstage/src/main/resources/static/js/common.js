$(function () {
    $.ajaxSetup({cache: false});
});

function ajaxLink(event) {
    event.preventDefault();
    var loadContainner = null;
    var callback = null;
    var data = null;
    if (event.data) {
        loadContainner = event.data.loadContainner;
        callback = event.data.callback;
        data = event.data.data;
    }
    var loadUrl = $(this).attr("href");
    loadHTML(loadUrl, loadContainner, callback, data);
}

function ajaxFormLoad(event) {
    event.preventDefault();
    var loadContainner = null;
    var fragment = null;
    var callback = null;
    var data = null;
    if (event.data) {
        loadContainner = event.data.loadContainner;
        fragment = event.data.fragment;
        callback = event.data.callback;
        data = event.data.data;
    }
    var loadUrl = $(this).attr("action");
    var extra = $(this).serialize();
    var url = loadUrl + "&" + extra + (fragment ? " " + fragment : "");
    loadHTML(url, loadContainner, callback, data);
}

function ajaxFormLoadAddParam(event) {
    event.preventDefault();
    var loadContainner = null;
    var fragment = null;
    var callback = null;
    var data = null;
    var param = event.data.param;
    if (event.data) {
        loadContainner = event.data.loadContainner;
        fragment = event.data.fragment;
        callback = event.data.callback;
        data = event.data.data;
    }
    var loadUrl = $(this).attr("action");
    var extra = $(this).serialize();
    var url = loadUrl + "&" + extra + param + (fragment ? " " + fragment : "");
    loadHTML(url, loadContainner, callback, data);
}

function loadHTML(loadUrl, loadContainner, callback, data) {
    if (!loadContainner) {
        loadContainner = $("#load");
    }

    if (!callback) {
        if (loadUrl.lastIndexOf(".jsp") > -1) {
            callback = loadUrl.substring(loadUrl.lastIndexOf("/") + 1, loadUrl.lastIndexOf(".jsp"));
        } else if(loadUrl.lastIndexOf(".html") > -1) {
            callback = loadUrl.substring(loadUrl.lastIndexOf("/") + 1, loadUrl.lastIndexOf(".html"));
            if(!data && loadUrl.indexOf("?") > -1) {
                data = loadUrl.substring(loadUrl.indexOf("?") + 1);
            }
        } else {
            var action = loadUrl.substring(loadUrl.lastIndexOf("/") + 1);
            if (action.indexOf("&") > -1) {
                callback = action.substring(0, action.indexOf("&"));
            } else {
                callback = action;
            }
            if (callback.indexOf(" ") > -1) {
                callback = callback.substring(0, callback.indexOf(" "));
            }
        }
    }

    loadContainner.load(loadUrl, function (response, status, xhr) {
        ajaxCallback(response, status, xhr, function () {
            $.parser.parse(loadContainner);
            var fn = null;
            if (typeof(callback) === "string") {
                try {
                    fn = eval(callback);
                } catch (e) {
                }
            } else if (callback instanceof Function) {
                fn = callback;
            }
            if (fn instanceof Function) {
                if (data)
                    fn(data);
                else
                    fn();
            }
        });
    });
}

function ajaxCallback(response, status, xhr, successFn) {
    if (status == "error") {
        alert("服务器异常，请稍后再试！");
    } else {
        successFn(response, status, xhr);
    }
}

function ajaxErrorCallback(jqXHR, textStatus, errorThrown) {
    alert("服务器异常！textStatus=" + textStatus + ",e=" + errorThrown);
}

function loadFilterForDataGrid(data) {
    if(data.result != undefined && data.result === 0) {
        return data.message
    } else {
        return data;
    }
}

function serializeJson(formSelector) {
    var formData = $(formSelector).serializeArray();
    var json = {};
    for(var i = 0; i < formData.length; i++) {
        var data = formData[i];
        json[data.name] = data.value;
    }
    return json;
}

function formatTimestampToDate(value, rec, index) {
    return DateUtils.format(value, "yyyy-MM-dd")
}

function formatTimestampToDateTime(value, rec, index) {
    return DateUtils.format(value, "yyyy-MM-dd HH:mm")
}

function openDialog(dialogId, url, title, formId, okHandler, onLoadHandler) {
    $('<div></div>')
        .dialog({
            id: dialogId,
            href: url,
            title:title,
            width: 400,
            height: 300,
            closed: false,
            cache: false,
            modal: true,
            onLoad: function () {
                if(onLoadHandler) onLoadHandler(url, formId, dialogId);
            },
            onClose: function () {
                $(this).dialog('destroy');
            },
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    okHandler(formId, dialogId);
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $("#" + dialogId).dialog('destroy');
                }
            }]

        });
}