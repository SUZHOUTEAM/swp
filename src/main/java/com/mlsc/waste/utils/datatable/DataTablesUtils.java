package com.mlsc.waste.utils.datatable;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * datatables的工具类
 * 
 * @author dinghq
 *
 */
public class DataTablesUtils {

    public static List<Columns> getColumnsParaments(HttpServletRequest request) {

        List<Columns> list = new ArrayList<Columns>();

        // 使用循环获取列参数，列数未知，所以需要通过判断data参数为null的时候才终止循环
        for (int n = 0; true; n++) {

            String data = request.getParameter("columns[" + n + "][data]");
            // 如果获取到null值，则终止获取参数
            if (data == null) {
                break;
            }

            String name = request.getParameter("columns[" + n + "][name]");
            boolean searchable = Boolean.parseBoolean(request.getParameter("columns[" + n + "][searchable]"));
            boolean orderable = Boolean.parseBoolean(request.getParameter("columns[" + n + "][orderable]"));
            String searchValue = request.getParameter("columns[" + n + "][search][value]");
            String searchRegex = request.getParameter("columns[" + n + "][search][regex]");
            if(StringUtils.isNotBlank(searchValue)){

                // 构造Columns对象并加入到返回参数中
                list.add(new Columns(data, name, searchable, orderable, searchValue, searchRegex));
            }

        }
        return list;
    }

    /**
     * 构造查询条件
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public static FilterConditions generateFilterConditions(HttpServletRequest request) {
        // 获取datatables的列参数
        List<Columns> columns = DataTablesUtils.getColumnsParaments(request);

        // 查询条件
        StringBuilder where = new StringBuilder("");
        Map<String, Object> paramMap = new HashMap<String, Object>();

        try {
            // 构造查询条件
            for (Columns column : columns) {
                if (!column.getData().equals("id")) {
                    if (StringUtils.isNotBlank(column.getSearchValue())) {
                        String temp = column.getSearchValue().trim();
                        where.append(" and " + column.getData() + " like :" + column.getData() + " ");
                        paramMap.put(column.getData(), "%" + temp + "%");
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new FilterConditions(where.toString(), paramMap);
    }

    /**
     * 构造结果集
     * 
     * @param draw
     * @param recordsTotal
     * @param recordsFiltered
     * @param dataList
     * @return
     */
    public static Map<String, Object> generateResult(String draw, int recordsTotal, int recordsFiltered, List<?> dataList) {
        Map<String, Object> resultMap = new HashMap<String, Object>(4);
        resultMap.put("draw", draw);// 回传给datatable的重画记数
        resultMap.put("recordsTotal", recordsTotal);// 记录总数
        resultMap.put("recordsFiltered", recordsFiltered);// 过滤前记录数，？

        if (dataList == null) {
            resultMap.put("data", new ArrayList<Object>(0));
        } else {
            resultMap.put("data", dataList);
        }
        return resultMap;
    }

    /**
     * 构造分页参数对象
     * 
     * @param start
     * @param length
     * @return
     */
    public static PagingParameter generatePagingParameter(int start, int length) {
        PagingParameter paging = new PagingParameter();
        paging.setStart(start);
        paging.setLimit(length);

        return paging;
    }
}
