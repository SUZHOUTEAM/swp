var jqueryDialog={
	alert:function(data){
		var msg=data.msg||'',
			tip=data.tip||'',
			type=data.type||'info',
			timeout=data.timeout||0;
		var str='<div class="dialog_bg">'+
			'<div class="dialog">'+
				'<div class="dialogClose"></div>'+
				'<div class="dialogMessage"><i class="icon '+type+'"></i><span>'+msg+'</span></div>'+
				'<div class="dialogTip">'+tip+'</div>'+
			'</div>'+
		'</div>';
		$('body').append(str);
		jqueryDialog.initClose();
		if(timeout&&timeout!=0){
			setTimeout(function(){
				$('.dialog_bg').remove();
			},timeout);
		}
	},
	initClose:function(){
		$('.dialogClose,.btn-cancel').on('click',function(){
			$('.dialog_bg').remove();
		});
	},
	getHtmlBythData:function(thData){
		var str='';
		for (i=0;i<thData.length;i++) {
			var obj=thData[i];
			str+='<th>'+obj+'</th>';
		}
		return str;
	},
	getHtmlBytdData:function(tdData,len){
		var str='';
		for (i=0;i<tdData.length;i++) {
			var obj=tdData[i];
			str+='<tr>';
				for (var j=0;j<len;j++) {
					str+='<td>'+obj[j]+'</td>';
				}
			str+='</tr>';
		}
		return str;
	},
	getHtmlByTotal:function(total, len, groupBy){
		var str='';
		if(total&&total.length>0){
			str+='<tr><td colspan="'+len+'" class="total">';
			for (i=0;i<total.length;i++) {
				var obj=total[i];
				str+='<span>'+obj.name+'：<label>'+obj.value+'</label></span>';
			}
			str+='</td></tr>';
		}
		return str;
		
		/*var str = '';
		if(total&&total.length>0){
			str += '<tr><th colspan="'+(len/2).toFixed(2)+'" rowspan="'+jqueryDialog.getLength(groupBy)+'" align="right"> 合计：</th>';
			for(var i=0; i<jqueryDialog.getLength(groupBy); i++){
				str += '<th>&nbsp;</th>';
			}	
			str += '</tr><tr>';
			
			for (var key in groupBy){
	    		str += '<th colspan="'+(len/2).toFixed(2)+'">'+groupBy[key]+'：'+key+'</th>'
	        }
			str += '</tr>';
		}
		return str;*/
	},
	confirmTable:function(data){
		var title=data.title||'',
		msg=data.msg||'',
		tip=data.tip||'',
		type=data.type||'info',
		thData=data.thData||[],
		tdData=data.tdData||[],
		total=data.total||[],
		cancelText=data.cancelText||'取消',
		sureText=data.sureText||'确认',
		groupBy=data.groupBy||{},
		func=data.success||function(){};
		var str='<div class="dialog_bg">'+
				'<div class="dialog dialog_table">'+
					'<div class="dialogClose"></div>'+
					(!title?'':('<div class="dialogMessage"><i class="icon '+type+'"></i><span>'+title+'</span></div>'))+
					'<div class="dialog_body">'+
					'<table class="table table-striped table-hover table-condensed dataTable">'+
						'<thead>'+
							'<tr>'+jqueryDialog.getHtmlBythData(thData)+'</tr>'+
						'</thead>'+
						'<tbody>'+jqueryDialog.getHtmlBytdData(tdData,thData.length)+jqueryDialog.getHtmlByTotal(total,thData.length,groupBy)+
						'</tbody>'+
					'</table></div>'+
					'<div class="dialogtitle">'+msg+'</div>'+
					'<div class="dialogTip">'+tip+'</div>'+
					'<div class="dialogBtn">'+
						'<a href="javascript:;" class="btn btn-cancel" title="'+cancelText+'">'+cancelText+'</a>'+
						'<a href="javascript:;" class="btn btn-sure" title="'+sureText+'">'+sureText+'</a>'+
					'</div>'+
				'</div>'+
			'</div>';
		$('body').append(str);
		$('.dialog.dialog_table td, .dialog.dialog_table th').css('width',(100/thData.length).toFixed(2)+'%');
		$('.dialog.dialog_table td.total').css('width','100%');
		$('.dialog.dialog_table td.total >span').css('width',(100/total.length).toFixed(2)+'%');
		jqueryDialog.initClose();
		$('.btn-sure').on('click',function(){
			$('.dialog_bg').remove();
			func();
		});
	},
	contains:function(json,key){
		var flag = false;
    	for (var k in json){
    		if(k==key){
    			flag = true;
    			break;
    		}
        }
    	return flag;
	},
	getLength: function(json){
		var length = 0;
		for (var k in json){
			length += 1;
        }
		return length;
	}
}
