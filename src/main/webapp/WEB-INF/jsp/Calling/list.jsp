<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="行业管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#callingItem"/>
</jsp:include>

<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">基础数据管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"  onclick="history.go(-1);">行业管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div>
        <%--<div class="content-heading">--%>
            <%--<strong class="lead">行业管理</strong>--%>
            <%--<small data-localize="dashboard.WELCOME">行业信息管理</small>--%>
        <%--</div>--%>
        <div class="row">
            <div class="col-md-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button"  onclick="add(this)">新增</a>
                        <a class="btn btn-default" href="#" role="button" onclick="edit(this)">编辑</a>
                        <a class="btn btn-default" href="#" role="button"  onclick="del(this)">删除</a>
                    </div>
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button"  onclick="enableCalling(this)">启用</a>
                        <a class="btn btn-default" href="#" role="button"  onclick="disableCalling(this)">停用</a>
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
                                <th>一级行业</th>
								<th>二级行业</th>
								<th>三级行业</th>
								<th>四级行业</th>
								<th>状态</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                        <tfoot class='filter'>
                            <tr>
                                <th></th>
								<th>一级行业</th>
								<th>二级行业</th>
								<th>三级行业</th>
								<th>四级行业</th>
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
        if($(this).index() != 5){
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
    
   // DataTable
    table = $('#example').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": $.fn.dataTable.pipeline( {
            url: 'listForJson.htm?ticketId=<%=ticketId %>',
            pages: 5 // number of pages to cache
        } ),
        "columns": [
            { "data": "id" },
            { "data": "firstLevel" },
            { "data": "secondLevel" },
            { "data": "thirdLevel" },
            { "data": "fourthLevel" },
            { "data": "c_status" }
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
                "targets": 5 //第几列
            }
        ],
        "ordering": false,
        "pagingType": "simple",
        "language": language,
        "dom": 'rt<"bottom"lip >'
    });
   
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

//编辑
function edit(t){
    if($("[name='id']:checked").length == 1){
        location = "<%=appPath%>/calling/edit.htm?ticketId=<%=ticketId %>&id="+$("[name='id']:checked").val();
    } else {
        swal({  
            title: "请选择一条进行编辑",   
            type: "warning",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        });
    }
}
//新增
function add(t){
    location = "<%=appPath%>/calling/add.htm?ticketId=<%=ticketId %>";
}

//删除 
function del(t){
    if($("[name='id']:checked").length == 0){
        swal({  
            title: "请选择一条或多条进行删除",   
            type: "warning",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        });
    }else{
        swal({  title: "确认删除",   
            text: "请确认是否删除数据",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "删除",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: true 
        }, 
        function(isConfirm){  
            if(isConfirm){
                $.post('removeCalling.htm?ticketId=<%=ticketId %>&etc='+new Date().getTime(),$('input[name="id"]').serialize(),function(json){
                    var obj = $.parseJSON(json);
                    if(obj.status == "1"){
                        swal({  
                            title: "删除成功",   
                            type: "success",   
                            confirmButtonColor: "#3399FF",   
                            confirmButtonText: "确定",   
                            closeOnConfirm: false,   
                            closeOnCancel: false 
                        });
                    } else {
                        swal({  
                            title: obj.data.msg,   
                            type: "warning",   
                            confirmButtonColor: "#DD6B55",   
                            confirmButtonText: "确定",   
                            closeOnConfirm: false,   
                            closeOnCancel: false 
                        });
                    }
                    table.column( 0 ).search( Math.random() ).draw(); //刷新表格数据，实际就是给了个null查全部
                });
            }
        });
    }
}
//停用
function disableCalling(t){
    if($("[name='id']:checked").length == 0){
        swal({  
            title: "请选择一条或多条进行停用",   
            type: "warning",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        });
    }else{
        swal({  title: "确认停用",   
            text: "请确认是否需要停用",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "停用",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: true 
        }, 
        
        function(isConfirm){  
            if(isConfirm){
                $.post("disableCalling.htm?ticketId=<%=ticketId %>&etc=" + new Date().getTime(),$("input[name='id']").serialize(),function(json){
                    var obj = $.parseJSON(json);
                    if(obj.status=="1"){
                        swal({  
                            title: "停用成功",   
                            type: "success",   
                            confirmButtonColor: "#3399FF",   
                            confirmButtonText: "确定",   
                            closeOnConfirm: false,   
                            closeOnCancel: false 
                        });
                    }else{
                        swal({  
                            title: "停用失败",   
                            type: "warning",   
                            confirmButtonColor: "#DD6B55",   
                            confirmButtonText: "确定",   
                            closeOnConfirm: false,   
                            closeOnCancel: false 
                        });
                    }
                    table.column( 0 ).search(Math.random()).draw();
                });
            }
        });
    } 
}

//启用
function enableCalling(t){
    if($("[name='id']:checked").length == 0){
        swal({  
            title: "请选择一条或多条进行启用",   
            type: "warning",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        });
    }else{
        swal({  title: "确认启用",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "启用",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: true 
        }, 
        function(isConfirm){  
            if(isConfirm){
                $.post('enableCalling.htm?ticketId=<%=ticketId %>&etc='+new Date().getTime(),$('input[name="id"]').serialize(),function(json){
                    var obj = $.parseJSON(json);
                    if(obj.status=="1"){
                        swal({  
                            title: "启用成功",   
                            type: "success",   
                            confirmButtonColor: "#3399FF",   
                            confirmButtonText: "确定",   
                            closeOnConfirm: false,   
                            closeOnCancel: false 
                        });
                        
                    }else{
                        swal({  
                            title: "启用失败",   
                            type: "warning",   
                            confirmButtonColor: "#DD6B55",   
                            confirmButtonText: "确定",   
                            closeOnConfirm: false,   
                            closeOnCancel: false 
                        });
                    }
                    table.column( 0 ).search(Math.random()).draw();
                });
            }
        });
    } 
}
</script>