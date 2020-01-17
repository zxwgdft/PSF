(function ($) {

    // --------------------------------------
    // common
    // --------------------------------------

    $.extend({
        namespace2fn: function (name, fun) {
            if (name) {
                $.fn[name] = fun ? fun : function () {
                    arguments.callee.$ = this;
                    return arguments.callee;
                };
            }
            return this;
        },
        namespace2win: function () {
            var a = arguments,
                o = null,
                i, j, d;
            for (i = 0; i < a.length; i = i + 1) {
                d = a[i].split(".");
                o = window;
                for (j = (d[0] == "window") ? 1 : 0; j < d.length; j = j + 1) {
                    o[d[j]] = o[d[j]] || {};
                    o = o[d[j]];
                }
            }
            return o;
        },
        formatDate: function (date, format) {

            var o = {
                "M+": date.getMonth() + 1, // month
                "d+": date.getDate(), // day
                "h+": date.getHours(), // hour
                "m+": date.getMinutes(), // minute
                "s+": date.getSeconds(), // second
                "q+": Math.floor((date.getMonth() + 3) / 3), // quarter
                "S": date.getMilliseconds() // millisecond
            }
            if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(format))
                    format = format.replace(RegExp.$1,
                        RegExp.$1.length == 1 ? o[k] :
                            ("00" + o[k]).substr(("" + o[k]).length));
            return format;
        },
        getUrlVariable: function (variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split("=");
                if (pair[0] == variable) {
                    return pair[1];
                }
            }
            return false;
        }
    });

    $.fn.serializeObject = function (param) {
        var obj = param || {};
        $(this).each(function () {
            var a = $(this).serializeArray();
            a.forEach(function (i) {
                if (i.value) {
                    var v = obj[i.name];
                    if (v) {
                        if (v instanceof Array) {
                            v.push(i.value);
                        } else {
                            var arr = [];
                            arr.push(v, i.value);
                            obj[i.name] = arr;
                        }
                    } else {
                        obj[i.name] = i.value;
                    }
                }
            });
        });

        return obj;
    };

    // --------------------------------------
    // constant
    // --------------------------------------

    $.namespace2win('tonto.constant');

    // --------------------------------------
    // messager
    // --------------------------------------

    /*
     * 基于layer组件提供弹框消息，参考 http://www.layui.com/doc/modules/layer.html
     * 
     */

    $.extend({
        infoMessage: function (message, top) {
            layer.msg(message, {icon: 6, offset: top ? top : undefined});
        },
        failMessage: function (message, top) {
            layer.msg(message, {icon: 2, offset: top ? top : undefined});
        },
        errorMessage: function (message, top) {
            layer.msg(message, {icon: 5, offset: top ? top : undefined});
        },
        successMessage: function (message, top) {
            layer.msg(message, {icon: 1, offset: top ? top : undefined});
        },
        doAlert: function (msg, icon, fun, top) {
            if (typeof fun === 'number' || typeof fun === 'string') {
                top = fun;
            }
            layer.alert(msg, {icon: icon, offset: top ? top : undefined}, function (index) {
                layer.close(index);
                if (typeof fun === 'function') fun();
            });
        },
        successAlert: function (msg, fun, top) {
            $.doAlert(msg, 1, fun, top);
        },
        warnAlert: function (msg, fun, top) {
            $.doAlert(msg, 3, fun, top);
        },
        failAlert: function (msg, fun, top) {
            $.doAlert(msg, 2, fun, top);
        },
        errorAlert: function (msg, fun, top) {
            $.doAlert(msg, 5, fun, top);
        },
        infoAlert: function (msg, fun, top) {
            $.doAlert(msg, 6, fun, top);
        },
        isLayer: function () {
            if (parent && parent.layer && parent.layer.getFrameIndex(window.name)) {
                return true;
            } else {
                return false;
            }
        },
        openPageLayer: function (content, options) {
            options = options || {};

            if (typeof options == "string") {
                options = {
                    title: options
                }
            } else if (typeof options == "function") {
                options = {
                    success: options
                };
            }

            options = $.extend(options, {
                type: 1,
                title: options.title || '',
                maxmin: true, //开启最大化最小化按钮
                area: $.getOpenLayerSize(options.width, options.height),
                content: content,
                success: options.success
            });

            return layer.open(options);
        },
        openUrlLayerOrLocate: function (url, options) {
            if (options && options.data) {
                url = $.wrapGetUrl(url, options.data);
            }

            if ($.isLayer()) {
                window.location = url;
            }

            options = options || {};

            if (typeof options == "string") {
                options = {
                    title: options
                }
            } else if (typeof options == "function") {
                options = {
                    success: options
                };
            }

            options = $.extend(options, {
                type: 2,
                title: options.title || '',
                maxmin: true, //开启最大化最小化按钮
                area: $.getOpenLayerSize(options.width, options.height),
                content: url,
                success: options.success
            })

            return layer.open(options);
        },
        getOpenLayerSize: function (w, h) {
            w = w || 0.8;
            h = h || 0.9;

            var ww = $(window).width();
            var wh = $(window).height();

            if (w > ww) {
                w = ww * 0.8;
            } else if (w <= 1) {
                w = ww * w;
            }

            if (h > wh) {
                h = wh * 0.9;
            } else if (h <= 1) {
                h = wh * h;
            }

            return [w + "px", h + "px"];
        }
    });


    // --------------------------------------
    // ajax
    // --------------------------------------

    $.extend({
        setToken: function (token) {
            tonto.constant.token = token;
        },
        getToken: function () {
            return tonto.constant.token;
        },
        sendAjax: function (options) {
            // 发送ajax请求 对应$.ajax()
            var beforeSend = options.beforeSend;
            var submitBtn = options.submitBtn;
            options.beforeSend = function (XMLHttpRequest) {
                var token = $.getToken();

                if (token) {
                    XMLHttpRequest.setRequestHeader("Authorization", token);
                }

                if (typeof beforeSend === 'function') {
                    var r = beforeSend(XMLHttpRequest);
                    if (r === false) {
                        return false;
                    }
                }

                if (submitBtn) {
                    $(submitBtn).each(function () {
                        var that = $(this);
                        that.data("loading", true);
                        var text = that.text();
                        that.data("orginText", text);
                        that.text(text + '中...').prop('disabled', true).addClass('disabled');
                    });
                }
            }

            var completeHandler = options.complete;
            options.complete = function (XMLHttpRequest, textStatus) {

                $(submitBtn).each(function () {
                    var that = $(this);
                    var text = that.text();
                    that.removeClass('disabled').prop('disabled', false).text(that.data("orginText"));
                });

                if (typeof completeHandler === 'function') {
                    completeHandler(XMLHttpRequest, textStatus);
                }
            }

            if (!options.error) {
                options.error = function (XMLHttpRequest, textStatus, errorThrown) {
                    $.errorMessage("系统异常:" + XMLHttpRequest.status);
                }
            }

            $.ajax(options);
        },
        postJsonAjax: function (url, data, callback, submitBtn) {
            if (callback && typeof callback != 'function' && !submitBtn) {
                submitBtn = callback;
                callback = null;
            }

            // 发送json格式ajax请求
            $.sendAjax({
                type: "POST",
                url: url,
                dataType: "json",
                data: JSON.stringify(data),
                contentType: "application/json;charset=utf-8",
                success: callback,
                submitBtn: submitBtn
            });
        },
        getAjax: function (url, callback, submitBtn) {
            if (callback && typeof callback != 'function' && !submitBtn) {
                submitBtn = callback;
                callback = null;
            }

            // 发送json格式ajax请求
            $.sendAjax({
                type: "GET",
                url: url,
                contentType: "application/json;charset=utf-8",
                success: callback,
                submitBtn: submitBtn
            });
        },
        postAjax: function (url, data, callback, submitBtn) {
            // 对应$.post()
            if (typeof data === 'function') {
                submitBtn = callback;
                callback = data;
                data = null;
            } else {
                if (callback && typeof callback != 'function' && !submitBtn) {
                    submitBtn = callback;
                    callback = null;
                }
            }

            // 发送json格式ajax请求
            $.sendAjax({
                type: "POST",
                url: url,
                contentType: "application/json;charset=utf-8",
                success: callback,
                submitBtn: submitBtn
            });
        },
    });

})(jQuery);