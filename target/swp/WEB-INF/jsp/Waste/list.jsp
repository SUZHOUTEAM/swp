<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="危废名录"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#wasteItem"/>
</jsp:include>

<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">基础数据管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"  onclick="history.go(-1);">危废名录管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div>
        <div class="row">
            <div class="col-xs-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button"  onclick="add(this)">新增</a>
                        <a class="btn btn-default" href="#" role="button"  onclick="edit(this)">编辑</a>
                        <%--<a class="btn btn-default" href="#" role="button"  onclick="del(this)" >删除</a>--%>
                    </div>
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button"  onclick="enableWaste(this)">启用</a>
                        <a class="btn btn-default" href="#" role="button"  onclick="disableWaste(this)">停用</a>
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
                                <th>废物类别</th>
                                <th>行业来源</th>
                                <th>八位码</th>
                                <th>危废废物名称</th>
                                <th>危险特性</th>
                                <th>别称</th>
                                <th>更新时间</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                            <tfoot class='filter'>
                                <tr>
                                    <th></th>
                                    <th>废物类别</th>
                                    <th>行业来源</th>
                                    <th>八位码</th>
                                    <th>危废废物名称</th>
                                    <th>危险特性</th>
                                    <th>别称</th>
                                    <th class="hidden-xs"></th>
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
        if($(this).index() != 7){
            var title = $('#example tfoot th').eq( $(this).index() ).text();
            
            if(title!=''){
                $(this).html( '<input type="text" placeholder="搜索 '+title+'" class="datatable_input_col_search"/>' );
            }
        }
    } );
    
   // DataTable
    table = $('#example').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": $.fn.dataTable.pipeline( {
            url: '<%=appPath%>/Waste/listForJson.htm?ticketId=<%=ticketId %>',
            pages: 7 // number of pages to cache
        } ),
        "columns": [
                    { "data": "id", "width": "40px", 
                        "render": function(data, type, row) {
                            return '<label class="checkbox-inline c-checkbox"><input type="checkbox" class="checkCalling" name="id" value="'+ data +'">'
                              +'<span class="fa fa-check"></span></label>';
                        },
                        "orderable": false,
                    },
                    { "data": "wasteType", "width": "90px"},
                    { "data": "callingName", "width": "90px" },
                    { "data": "code","width": "85px" },
                    { "data": "description", "width": "190px" },
                    { "data": "riskCharacteristics", "width": "80px", "className": "hidden-xs hidden-sm" },
                    { "data": "w_name", "width": "90px", "className": "hidden-xs hidden-sm" },
                    { "data": "edit_time", "width": "90px", "className": "hidden-xs hidden-sm" }
        ],
        "ordering": false,
        "pagingType": "simple",
        "language": language,
        "scrollX": true,
        "dom": 'rt<"bottom"lip >'
    });
   
    function search( colIdx,value ) {
        table
           .column( colIdx )
           .search( value )
           .draw();
    }
   
     // Apply the search
    table.columns().eq( 0 ).each( function ( colIdx ) {
        $( 'input,select', table.column( colIdx ).footer() ).on( 'keyup change propertychange input', function () {
                clearTimeout(searchTimeOut);
                idx = colIdx;
                val = this.value;
                searchTimeOut = setTimeout("search2()", 1000);
        } );
    } );
     
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

function edit(t){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length == 1) {
        var paramObj = {};
        paramObj.id = $checkItem.val();
        var pageUrl = "/Waste/edit.htm";
        $.page.gotoTargetLocation(pageUrl, paramObj);
    } else {
        $.notify("请选择一条进行编辑", {"status": "info"});
    }
}

function add(t){
    var paramObj = {};
    var pageUrl = "/Waste/add.htm";
    $.page.gotoTargetLocation(pageUrl, paramObj);
}

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
                    url: "/Waste/removeWaste.htm",
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

function enableWaste(t){
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
                    url: $.page.webSiteRootUrl + "/Waste/enableWaste.htm",
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

function disableWaste(t){
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
                    url: $.page.webSiteRootUrl + "/Waste/disableWaste.htm",
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
</script>