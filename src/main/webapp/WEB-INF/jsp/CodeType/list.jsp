<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="常用字典管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#codeTypeItem"/>
</jsp:include>

<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">基础数据管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"  onclick="history.go(-1);">常用数据管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div>
        <div class="row">
            <div class="col-xs-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button"  onclick="add(this)">新增</a>
                        <a class="btn btn-default" href="#" role="button" onclick="detail(this)">查看</a>
                        <a class="btn btn-default" href="#" role="button" onclick="edit(this)">编辑</a>
                        <a class="btn btn-default" href="#" role="button"  onclick="del(this)" id="deleteCodeType" >删除</a>
                    </div>
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button"  onclick="enableCodeType(this)">启用</a>
                        <a class="btn btn-default" href="#" role="button"  onclick="disableCodeType(this)">停用</a>
                    </div>
                </div>
                <div class="waste-panel">
                    <table id="example" class="table table-striped table-hover table-condensed">
                        <thead>
                            <tr>
                                <th>
                                    <label class="checkbox-inline c-checkbox">
                                        <input id="checkAll" name="checkAll" type="checkbox">
                                        <span class="fa fa-check"></span>
                                    </label>
                                </th>
                                <th>字典类型编码</th>
                                <th>字典类型名称</th>
                                <th>字典信息</th>
                                <th>状态</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                        <tfoot class='filter'>
                            <tr>
                                <th></th>
                                <th>字典类型编码</th>
                                <th>字典类型名称</th>
                                <th>字典信息</th>
                                <th>状态</th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
           </div>
        </div>
     </div>
</section>
  
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script type="text/javascript">
var table ;
var searchTimeOut;//timeout对象
var idx;//过虑的列序号
var val;//过虑值
function search2() {
    table.column( idx ).search( val ).draw();
}

$(document).ready( function () {    
	$('#example tfoot th').each( function () {
	    if($(this).index() != 4){
	        var title = $('#example tfoot th').eq( $(this).index() ).text();
	        if(title!=''){
	            $(this).html( '<input type="text" placeholder="搜索 '+title+'" class="form-control input-sm datatable_input_col_search"/>' );
	        }
	    }else{
	          var select = $('<select class="form-control input-sm datatable_input_col_search"><option value="">全部</option></select>')
	          .appendTo( $(this).empty() );
	        select.append( '<option value="0">停用</option>');
	        select.append( '<option value="1">启用</option>' );
	    }
	});
        
	table = $('#example').DataTable({
	    "processing": true,
	    "serverSide": true,
	    "ajax": $.fn.dataTable.pipeline( {
	        url: '<%=appPath %>/codeType/listForJson.htm?ticketId=<%=ticketId %>',
	        pages: 7 // number of pages to cache
	    } ),
	    "columns": [
	            { "data": "id" },
	            { "data": "type_code" },
	            { "data": "type_name" },
	            { "data": "information" },
	            { "data": "status" }
	            ],
	    "columnDefs": [
	        {
				"render": function(data, type, row) {
				    return '<label class="checkbox-inline c-checkbox"><input type="checkbox" class="checkCalling" name="id" value="'+ data +'">'
				          +'<span class="fa fa-check"></span></label>';
				},
				"orderable": false,
				"targets": 0
	        },
	        //状态栏的转换显示
	        {
				"render": function(data, type, row) {
				    return (data == '0') ? '停用' : '启用';
				},
				"orderable": false,
				"targets": 4 //第几列
	        } 
	    ],
	    "order": [[ 1, "desc" ]],
	    "pagingType": "simple",
	    "language": language,
	    "scrollX": true,
	    "dom": 'rt<"bottom"lip >'
	});
    
     // Apply the search
	table.columns().eq( 0 ).each( function ( colIdx ) {
	    $( 'input,select', table.column( colIdx ).footer() ).on( 'keyup change propertychange input', function () {
			clearTimeout(searchTimeOut);
			idx = colIdx;
			val = this.value;
			searchTimeOut = setTimeout("search2()", 1000);
	    });
	});
     
    function initTableCheckbox(){
        /*“全选/反选”复选框*/  
        var $checkAll = $('#checkAll');
        $checkAll.click(function(event){  
            /*将所有行的选中状态设成全选框的选中状态*/  
            $('.checkCalling').prop('checked',$(this).prop('checked')); 
        });  

        /*点击每一行的选中复选框时*/  
        $('.checkCalling').click(function(event){
            $checkAll.prop('checked',$('table tbody tr').find('input:checked').length == $('.checkCalling').length ? true : false);  

        }); 
    }  
    initTableCheckbox();  
} );

//编辑
function edit(t){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length == 1) {
        var paramObj = {};
        paramObj.id = $checkItem.val();
        var pageUrl = "/codeType/edit.htm";
        $.page.gotoTargetLocation(pageUrl, paramObj);
    } else {
        $.notify("请选择一条进行编辑", {"status": "info"});
    }
}
//新增
function add(t){
    var paramObj = {};
    var pageUrl = "/codeType/add.htm";
    $.page.gotoTargetLocation(pageUrl, paramObj);
}
//查看
function detail(that){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length == 1) {
        var paramObj = {};
        paramObj.id = $checkItem.val();
        var pageUrl = "/codeType/detail.htm";
        $.page.gotoTargetLocation(pageUrl, paramObj);
    } else {
        $.notify("请选择一条进行查看", {"status": "info"});
    }
}
//删除 
function del(t){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length == 0) {
        $.notify("请选择一条或多条进行删除", {"status": "info"});
    } else {
        swal({  title: "确认删除",   
            text: "请确认是否删除数据",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "删除",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        }, function(isConfirm){
            if(isConfirm){
                $.page.ajax($.page.getAjaxSettings({
                    async: false,
                    type: "POST",
                    dataType: "json",
                    url: "/codeType/removeCodeType.htm",
                    data: $checkItem.serialize(),
                    success: function (result, textStatus, jqXHR) {
                        swal("删除成功!", "删除成功!", "success");
                        location.reload();
                    }
                }));
            } else {
                swal("操作已取消!", "操作已取消.", "success");
            }
        });
    }
}
//停用
function disableCodeType(t){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length == 0) {
        $.notify("请选择一条或多条进行停用", {"status": "info"});
    } else {
        swal({  title: "确认停用",   
            text: "请确认是否需要停用",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "停用",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        }, function(isConfirm){
            if(isConfirm){
                $.page.ajax($.page.getAjaxSettings({
                    async: false,
                    type: "POST",
                    dataType: "json",
                    url: $.page.webSiteRootUrl + "/codeType/disableCodeType.htm",
                    data: $checkItem.serialize(),
                    success: function (result, textStatus, jqXHR) {
                        swal("停用成功!", "停用成功!", "success");
                        location.reload();
                    }
                }));
            } else {
                swal("操作已取消!", "操作已取消.", "success");
            }
        });
    }
}

//启用
function enableCodeType(t){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length == 0) {
        $.notify("请选择一条或多条进行启用", {"status": "info"});
    } else {
        swal({  title: "确认启用",   
            text: "请确认是否需要启用",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "启用",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        }, function(isConfirm){
            if(isConfirm){
                $.page.ajax($.page.getAjaxSettings({
                    async: false,
                    type: "POST",
                    dataType: "json",
                    url: $.page.webSiteRootUrl + "/codeType/enableCodeType.htm",
                    data: $checkItem.serialize(),
                    success: function (result, textStatus, jqXHR) {
                        swal("启用成功!", "启用成功!", "success");
                        location.reload();
                    }
                }));
            } else {
                swal("操作已取消!", "操作已取消.", "success");
            }
        });
    }
}
</script>