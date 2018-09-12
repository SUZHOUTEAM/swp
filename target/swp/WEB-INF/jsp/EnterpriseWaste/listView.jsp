<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
    String enterpriseId = (String)request.getAttribute("enterpriseId");
%>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<div id="app">
    <div class="el-table el-table--fit el-table--border el-table--enable-row-hover el-table--enable-row-transition"
         style="width: 100%; position: relative;display: none;max-height:443px;overflow-y: auto" id="tableDiv">
        <div class="el-table__header-wrapper">
            <table cellspacing="0" cellpadding="0" border="0" class="el-table__header" style="width: 100%;">
                <thead class="has-gutter">
                <tr class="">
                    <th colspan="1" rowspan="1" class="el-table_1_column_1     is-leaf">
                        <div class="cell">危废名称</div>
                    </th>
                    <th colspan="1" rowspan="1" class="el-table_1_column_2     is-leaf">
                        <div class="cell">危废编码</div>
                    </th>
                    <th colspan="1" rowspan="1" class="el-table_1_column_3     is-leaf">
                        <div class="cell">二位码</div>
                    </th>
                    <th class="gutter" style="width: 0px; display: none;"></th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="el-table__body-wrapper is-scrolling-none">

            <table cellspacing="0" cellpadding="0" border="0" class="el-table__body" style="width: 100%" id="table">
                <tbody>
                </tbody>
            </table><!----><!----></div><!----><!----><!----><!---->
        <div class="el-table__column-resize-proxy" style="display: none;"></div>
        <div class="resize-triggers">
            <div class="expand-trigger">
                <div style="width: 932px; height: 218px;"></div>
            </div>
            <div class="contract-trigger"></div>
        </div>
    </div>
    <div style="text-align: center" id="loading"><i class="el-icon-loading" style="font-size: 30px"></i></div>
    <%--<el-table  tooltip-effect="dark"
              :data="tableData"  v-loading.body="listLoading"  border
              style="width: 100%">
        <el-table-column
                label="危废名称"
                prop="wasteName">
        </el-table-column>
        <el-table-column
                label="危废编码"
                prop="wasteCode">
        </el-table-column>
        <el-table-column
                label="二位码"
                prop="wasteTypeDesc">
        </el-table-column>
    </el-table>--%>
    <%--<div v-show="tableData&&tableData.length>0" align="right" style="margin-top: 10px">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
    </div>--%>
</div>
<script type="text/javascript" charset="utf8"
        src="<%=appPath %>/main/mobile/js/zepto.min.js"></script>
<script>
    $(document).ready(function () {
       loadData();
    })
    function loadData() {
        var param={
            wasteCode:'',
            wasteName:'',
            wasteTypeDesc:'',
            entId:'<%=enterpriseId%>'
        }
        $.ajax({
            url:'<%=appPath%>/entWaste/listEntWaste.htm?ticketId=<%=ticketId%>',
            dataType:'json',
            contentType:'application/json',
            data:JSON.stringify(param),
            type:'post',
            success:function (result) {
                $('#loading').hide();
                if(result.status==1){
                    var str='';
                    for(var i in result.data.entWasteList){
                        var obj=result.data.entWasteList[i];
                        str+='<tr class="el-table__row">'+
                            '<td class="el-table_1_column_1  ">'+
                            '<div class="cell">'+obj['wasteName']+'</div>'+
                            '</td>'+
                            '<td class="el-table_1_column_2  ">'+
                            '<div class="cell">'+obj['wasteCode']+'</div>'+
                            '</td>'+
                            '<td class="el-table_1_column_3  ">'+
                            '<div class="cell">'+obj['wasteTypeDesc']+'</div>'+
                            '</td>'+
                            '</tr>';
                    }
                    $('#table tbody').append(str);
                    $('#tableDiv').show();
                }
            }
        })
    }
</script>

</script>