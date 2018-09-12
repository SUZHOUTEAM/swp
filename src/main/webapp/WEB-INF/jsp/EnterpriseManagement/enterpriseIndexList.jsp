<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="企业索引管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#enterpriseIndexManagement"/>
</jsp:include>

<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">权限管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">企业索引管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div >
        <div class="row">
            <div class="col-md-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button" onclick="deleteEnterpriseIndex(this)">删除</a>
                    </div>
                </div>
                <div class="waste-panel table-responsive">
                    <table id="enterpriselist" class="table table-striped table-hover table-condensed">
                        <thead>
                            <tr>
                                <th>
                                    <label class="checkbox-inline c-checkbox">
                                        <input id="checkAll" name="checkAll" type="checkbox">
                                        <span class="fa fa-check"></span>
                                    </label>
                                </th>
                                <th>企业名称</th>
                                <th>企业代码</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                        <tfoot class='filter'>
                            <tr>
                                <th></th>
                                <th>企业名称</th>
                                <th></th>
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
    table.column(idx).search(val).draw();
}
$(document).ready( function () {
    var $filter = $('#enterpriselist tfoot th');
    $filter.each( function () {
	    var title = $filter.eq($(this).index()).text();
	    if(title != ''){
	        $(this).html( '<input type="text" placeholder="搜索 '+ title + '" class="form-control input-sm datatable_input_col_search "/>');
	    }
    });
    
   // DataTable
    table = $('#enterpriselist').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": $.fn.dataTable.pipeline( {
            url: '<%=appPath%>/enterprisemanagement/enterpriseIndexList.htm?ticketId=<%=ticketId %>',
            pages: 5
        } ),
        "columns": [
            { "data": "id" },
            { "data": "entName" },
            { "data": "entCode" }
        
        ],
        "columnDefs": [
            {
                "render": function(data, type, row) {
                    var $label = $("<label class='checkbox-inline c-checkbox'></label>").append("<input type='checkbox' class='checkEnt' name='entId' value='" + data + "'/>");
                    $label.append("<span class='fa fa-check'></span>");
                    return $label.prop("outerHTML");
                },
                "orderable": false,
                "targets": 0
            }
        ],
        "ordering": false,
        "pagingType": "simple",
        "language": language,
        "dom": 'rt<"bottom"lip >'
    });
   
     // Apply the search
    table.columns().eq( 0 ).each( function ( colIdx ) {
        $( 'input,select', table.column(colIdx).footer()).on('keyup change propertychange input', function () {
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
            $('.checkEnt').prop('checked',$(this).prop('checked')); 
        });  

        /*点击每一行的选中复选框时*/  
        $('.checkEnt').click(function(event){
            $checkAll.prop('checked',$('table tbody tr').find('input:checked').length == $('.checkEnt').length ? true : false);  

        }); 
    }  
    initTableCheckbox();  
} );








function deleteEnterpriseIndex(that){
    var num =  $("input[type='checkbox']:checked").length;
    if(num > 0){
        swal({title: "删除企业索引",   
            text: "请确认是否删除企业索引",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "确认",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: true 
        },
        function(isConfirm){
            if(isConfirm){
                $(that).parent().find(".btn").attr("disabled","disabled");
                var enterpriseId = "";
                $("input:checkbox:checked").each(function(i){
                    enterpriseId += $(this).val() + ","; 
                });
                $.post('<%=appPath %>/enterprisemanagement/deleteEnterpriseIndex.htm?ticketId=<%=ticketId %>&etc='+new Date().getTime() + "&enterpriseId=" + enterpriseId,function(result){
                    var obj = $.parseJSON(result);
                    if(obj.status == 1){
                        location="<%=appPath %>/enterprisemanagement/enterpriseIndexInitList.htm?ticketId=${ticketId}";
                    }else{
                        $.notify("系统错误,请联系管理员!","danger");
                        $(that).parent().find(".btn").removeAttr("disabled");
                    };
                });
            };
        }); 
    }else{
        swal({  
            title: "请至少选择一条进行操作",   
            type: "info",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: true 
        });
    };
};
   
</script>