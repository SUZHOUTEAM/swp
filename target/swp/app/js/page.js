$.page = {
	webSiteRootUrl: "",
	ticketId: "",
	getAjaxSettings: function(properties, cSettings) {
		var settings = {
			data: null,
			success: function() {}
		};
		$.extend(settings, properties);
		settings.type = settings.type ? settings.type : "GET";
		settings.dataType = settings.dataType ? settings.dataType : "json";
		settings.jsonp = settings.jsonp ? settings.jsonp : "jsoncallback";
		if(settings.url.indexOf($.page.webSiteRootUrl) < 0) {
			settings.url = $.page.webSiteRootUrl + settings.url;
		}
		var paramData = {
			ticketId: $.page.getUrlParam('ticketId')
		};
		if((typeof settings.data) === 'string') {
			settings.data = settings.data + "&ticketId=" + paramData.ticketId;
		} else {
			$.extend(settings.data, paramData);
		}

		return settings;
	},
	getUrlParam: function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if(r != null) {
			return decodeURIComponent(r[2]);
		} else {
			return null;
		}
	},
	ajax: function(ajaxSettings) {
		if($.isFunction(ajaxSettings.beforeSend)) {
			ajaxSettings.uiBeforeSend = ajaxSettings.beforeSend;
			delete ajaxSettings.beforeSend;
		};
		if($.isFunction(ajaxSettings.success)) {
			ajaxSettings.uiSuccess = ajaxSettings.success;
			delete ajaxSettings.success;
		};
		if($.isFunction(ajaxSettings.error)) {
			ajaxSettings.uiError = ajaxSettings.error;
			delete ajaxSettings.error;
		};
		if($.isFunction(ajaxSettings.complete)) {
			ajaxSettings.uiComplete = ajaxSettings.complete;
			delete ajaxSettings.complete;
		};
		switch(ajaxSettings.type.toLowerCase()) {
			case "post":
				$.ajax(ajaxSettings);
				break;
			default:
				$.ajax(ajaxSettings);
				break;
		};
	},
	openUrl: function(url, windowId, windowTitle, width, height) {
		if(url.indexOf("ticketId") < 0) {
			if(url.indexOf("?") > 0) {
				url = url + "&ticketId=" + $.page.ticketId;
			} else {
				url = url + "?ticketId=" + $.page.ticketId;
			}
		}
		window.top.layer.open({
			id: windowId,
			type: 2,
			title: windowTitle,
			shadeClose: false,
			shade: [0.3, '#393D49'],
			maxmin: true, //开启最大化最小化按钮
			area: [width || '950px', height || '540px'],
			content: [url, 'yes'],
		});
	},
	openUrl_endRefresh: function(url, windowId, windowTitle, windowObj, width, height) {
		if(url.indexOf("ticketId") < 0) {
			if(url.indexOf("?") > 0) {
				url = url + "&ticketId=" + $.page.ticketId;
			} else {
				url = url + "?ticketId=" + $.page.ticketId;
			}
		}
		window.top.layer.open({
			id: windowId,
			type: 2,
			title: windowTitle,
			shadeClose: false,
			shade: [0.3, '#393D49'],
			maxmin: true, //开启最大化最小化按钮
			area: [width || '950px', height || '540px'],
			content: [url, 'yes'],
			end: function() {
				windowObj.location.reload();
			}
		});
	},
	gotoTargetLocation: function(pageUrl, param) {
		var paramStr = "";
		if(param) {
			for(p in param) {
				if(!((typeof param[p]) === 'string' || (typeof param[p]) === 'number' || (typeof param[p]) === 'boolean')) {
					console.error("页面跳转的参数只能是简单类型的值");
					return;
				}
				paramStr += "&" + p + "=" + param[p];
			}
		}
		window.location = $.page.webSiteRootUrl + pageUrl + "?ticketId=" + $.page.ticketId + paramStr;
	},
	goLogin: function(isLogout) {
		window.top.location = "/login.jsp";
	}
};
// #endregion

// #region 设置全局 AJAX 默认选项
$.ajaxSetup({
	type: "POST",
	contentType: "application/x-www-form-urlencoded",
	/* java项目使用 contentType : "application/x-www-form-urlencoded" */
	cache: false,
	async: true,
	timeout: 300000,
	error: function(jqXHR, textStatus, errorThrown) {
		if($.isFunction(this.uiError)) {
			this.uiError(jqXHR, textStatus, errorThrown);
		} else {
			if(jqXHR.status == "404") {
				location = $.page.webSiteRootUrl + "/404.jsp";
			} else if(jqXHR.status == "500") {
				location = $.page.webSiteRootUrl + "/500.jsp";
			}
		}
	},
	beforeSend: function(jqXHR, settings) {
		// 发送请求之前
		if($.isFunction(this.uiBeforeSend)) {
			this.uiBeforeSend(jqXHR, settings);
		};
	},
	success: function(data, textStatus, jqXHR) {
		try {
			convertToNumber(data);
			doUiSuccess(this, data, textStatus, jqXHR);
		} catch(e) {
			console.error(e.message);
		};
	},
	complete: function(jqXHR, textStatus) {
		// 完成请求
		if($.isFunction(this.uiComplete)) {
			this.uiComplete(jqXHR, textStatus);
		};
	}
});
// #endregion

// #region 页面完全就绪之后执行
$(document).ready(function() {
	var http_reg = new RegExp(/(http:\/\/)|((https:\/\/))/g); //去掉【http://】
	var locationUrl = location.href.replace(http_reg, '').split("/");
	$.page.webSiteRootUrl = window.webSiteRootUrl;
	if($.page.webSiteRootUrl == null) {
		$.page.webSiteRootUrl = "/" + locationUrl[1];
	};
	$.page.ticketId = $.page.getUrlParam('ticketId');
});

// 请求成功后返回的状态标识转变为数字
function convertToNumber(data) {
	if(isNaN(data.status)) {
		if(data.status == "Failure") {
			data.status = 0;
		} else if(data.status == "Success") {
			data.status = 1;
		}
	}
};

// 根据返回的状态执行对应的逻辑
function doUiSuccess(that, data, textStatus, jqXHR) {
	switch(data.status) {
		case 0:
			openMessageWindow(data);
			break;
		default:
			if($.isFunction(that.uiSuccess)) {
				that.uiSuccess(data, textStatus, jqXHR);
			};
			break;
	};
};

//页面信息提示框弹出
function openMessageWindow(data) {
	var msgData = data.data.msgInfo;
	if(!msgData || msgData.length == 0) {
		return;
	}

	var layerInfo = "";
	if(typeof msgData === 'string') {
		layerInfo = msgData;
	} else {
		for(var i = 0; i < msgData.length; i++) {
			if(msgData[i].noticeType == "1") {
				$.notify(msgData[i].errorContent, {
					"status": msgData[i].errorType,
					"pos": "top-center"
				});
			} else if(msgData[i].noticeType == "2") {
				layerInfo += msgData[i].errorContent + "\n\r";
			}
		};
	}

	if($.trim(layerInfo) != "") {
		layer.open({
			type: 1,
			title: '系统运行过程中，发生了异常！',
			shadeClose: true,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['800px', '600px'],
			content: layerInfo
		});
	}
}