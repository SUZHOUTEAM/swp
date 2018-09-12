<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="企业管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#sysorgcomlist"/>
</jsp:include>

<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">权限管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">组织机构管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div>
        <div class="row">
            <div class="col-md-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button"  onclick="createOrgCom(this)">新增</a>
                        <a class="btn btn-default" href="#" role="button" onclick="editOrgCom(this)">编辑</a>
                        <a class="btn btn-default hidden" href="#" role="button" onclick="viewOrgCom(this)">查看详情</a>
                    </div>
                </div>
                <div class="waste-panel table-responsive">
                    <table id="orgComlist" class="table table-striped table-hover table-condensed">
                        <thead>
                            <tr>
                                <th>
                                    <label class="checkbox-inline c-checkbox">
                                        <input id="checkAll" name="checkAll" type="checkbox">
                                        <span class="fa fa-check"></span>
                                    </label>
                                </th>
                                <th>组织机构名称</th>
                                <th>组织机构代码</th>
                                <th>备注</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                        <tfoot class='filter'>
                            <tr>
                                <th></th>
                                <th>组织机构名称</th>
                                <th>组织机构代代码</th>
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
    var $filter = $('#orgComlist tfoot th');
    $filter.each( function () {
        var title = $filter.eq($(this).index()).text();
        if(title != ''){
            $(this).html( '<input type="text" placeholder="搜索 '+ title + '" class="form-control input-sm datatable_input_col_search "/>');
        }
    });
    
   // DataTable
    table = $('#orgComlist').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": $.fn.dataTable.pipeline( {
            url: '<%=appPath%>/sysorgcom/listForJson.htm?ticketId=<%=ticketId %>',
            pages: 5
        } ),
        "columns": [
            { "data": "comId" },
            { "data": "comName" },
            { "data": "comCode" },
            { "data": "comDesc" }
        ],
        "columnDefs": [
            {
                "render": function(data, type, row) {
                    var $label = $("<label class='checkbox-inline c-checkbox'></label>").append("<input type='checkbox' class='checkEnt' name='comId' value='" + data + "'/>");
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

//创建企业
function createOrgCom(that){
    location = "<%=appPath%>/sysorgcom/addOrgCom.htm?ticketId=<%=ticketId %>";
}

//编辑
function editOrgCom(that){
    if($("[name='comId']:checked").length == 1){
        location = "<%=appPath%>/sysorgcom/editOrgCom.htm?ticketId=<%=ticketId %>&comId=" + $("[name='comId']:checked").val();
    } else {
        swal({  
            title: "请选择一条进行编辑",   
            type: "info",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        });
    }
}

</script>