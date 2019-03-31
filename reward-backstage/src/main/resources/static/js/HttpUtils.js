/*
 * Copyright (c) 2014-2016. JarkimZhu
 * This software can not be used privately without permission
 */

/**
 * Created on 2016/8/1.
 *
 * @author JarkimZhu
 * @class
 */
var HttpUtils = function () {
    this.token = null;
    $.ajaxSetup({
        statusCode: {
            302: function (data) {
                console.log("302", data);
                alert(data.responseText);
                //window.location.href = data.responseText;
            }
        }
    });
};

HttpUtils.prototype.post = function(url, param, cb) {
    var token = this.token;

    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: url,
        processData: false,
        dataType: 'json',
        data: JSON.stringify(param),
        beforeSend: function (XMLHttpRequest) {
            if(token) {
                XMLHttpRequest.setRequestHeader("token", token);
            }
        },
        success: function (data) {
            var result = data.result;
            if (result === 0) {
                if(cb) cb(null, data);
            } else {
                if(cb) cb(data);
            }
        },
        error: function (e) {
            if(cb) cb(e);
        }
    });

};

HttpUtils.prototype.get = function(url, cb, isScriptGet, enableCache) {
    var token = this.token;

    $.ajax({
        type: 'get',
        url: url,
        processData: false,
        beforeSend: function (XMLHttpRequest) {
            if(token) {
                XMLHttpRequest.setRequestHeader("token", token);
            }
        },
        success: function (data) {
            var result = data.result;
            if (result === 0) {
                if(cb) cb(null, data);
            } else {
                if(cb) cb(data);
            }
        },
        error: function (e) {
            if(cb) cb(e);
        }
    });

};

HttpUtils.prototype.clearCookie = function() {
    
};

var httpUtils = new HttpUtils();