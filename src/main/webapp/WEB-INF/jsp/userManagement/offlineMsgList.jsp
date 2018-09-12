<%--Created by zhanghj on 2017/7/4--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="消息管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#offlineMsg"/>
</jsp:include>
<style type="text/css">
    #offlineMsgList{
        width: 100% !important;
    }
    .modal{
        margin-top:89px !important;
    }

</style>
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">消息管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">离线消息</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div>
        <div class="row">
            <div class="col-md-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                    </div>
                </div>
                <div class="waste-panel table-responsive">
                    <table id="offlineMsgList" class="table table-striped table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>
                                <label class="checkbox-inline c-checkbox">
                                    <input id="checkAll" name="checkAll" type="checkbox">
                                    <span class="fa fa-check"></span>
                                </label>
                            </th>
                            <th>留言的企业</th>
                            <th>留言人</th>
                            <th>留言人电话</th>
                            <th>被留言的企业</th>
                            <th>留言时间</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                        <tfoot class='filter'>
                        <tr>
                            <th></th>
                            <th>留言的企业</th>
                            <th></th>
                            <th></th>
                            <th>被留言的企业</th>
                            <th></th>
                            <th>状态</th>
                            <th></th>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <div class="modal fade" id="msgDetailModal" tabindex="-1" role="dialog" aria-labelledby="msgDetailModal" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="msgDetailLabel">
                                    消息详情
                                </h4>
                            </div>
                            <div class="modal-body">
                                <textarea rows="4" cols="40" style="width:100%;height:100%;resize:none;border: none" id="msgDetail" value="" ></textarea>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" onclick="closeModal()">关闭</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
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

    //查看详情
    function readDetail(id) {
       $.ajax({
            url: "<%=appPath%>/userManage/getOfflineMsgById",
            data: {
                'ticketId': '<%=ticketId%>',
                'id': id
            },
            type: "POST",
            dataType: 'json',
            async: false,
            success: function(result) {
                var data =result.data.offlineMessageVo;
                $("#msgDetail").val("");
                $("#msgDetail").val(data.context);
                $('#msgDetailModal').modal({
                    keyboard: true,
                    backdrop: false,
                    show: true,
                });
            },
            error:function(){
            }
        });
    }
    function closeModal(){
        $("#msgDetail").hide();
        location.reload();
    }
    function search2() {
        table.column(idx).search(val).draw();
    }
    $(document).ready( function () {
        var $filter = $('#offlineMsgList tfoot th');
        $filter.each( function () {
            var title = $filter.eq($(this).index()).text();
            if(title != ''){
                $(this).html( '<input type="text" placeholder="搜索 '+ title + '" class="form-control input-sm datatable_input_col_search "/>');
            }
            if($(this).index() == 6){
                var select = $('<select class="form-control input-sm datatable_input_col_search"></select>').appendTo( $(this).empty() );
                select.append("<option selected='selected' value='total'>全部</option>");
                select.append("<option value='1'>未处理</option>");
                select.append("<option value='0'>已处理</option>");
            }
        });

        // DataTable
        table = $('#offlineMsgList').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": $.fn.dataTable.pipeline( {
                url: '<%=appPath%>/userManage/getOfflineMsgList.htm?ticketId=<%=ticketId %>',
                pages: 5
            } ),
        "columns": [
                { "data": "id" },
                { "data": "fromEntName" },
                { "data": "fromUserName" },
                { "data": "fromPhoneNum" },
                { "data": "toEntName" },
                { "data": "createTime"},
                { "data": "status"},
                { "data": "id" }
            ],   
          "columnDefs": [
                {
                    "render": function(data, type, row) {
                        var $label = $("<label class='checkbox-inline c-checkbox'></label>").append("<input type='checkbox' class='checkEnt' name='id' value='" + data + "'/>");
                        $label.append("<span class='fa fa-check'></span>");
                        return $label.prop("outerHTML");
                    },
                    "orderable": false,
                    "targets": 0 //第1列
                },
              //状态显示
                {
                  "render": function(data, type, row) {
                      if(data==1){
                          var $a = $("<font color='blue'></font>");
                          return $a.append("<span>未处理</span>").prop("outerHTML");
                      }else if(data==0){
                          return '已处理';
                      }
                  },
                  "targets": 6//第6列
                },
                //查看详情
                {
                  "render": function(data, type, row) {
                      var $a = $("<a href='javascript:;' onclick='readDetail(\"" + data + "\")'></a>");
                       return $a.append("<span>查看消息</span>").prop("outerHTML");
                  },
                  "targets": 7 //第7列
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

</script>