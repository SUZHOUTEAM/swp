package com.mlsc.waste.fileupload.controller;

import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.waste.fileupload.model.FileMeta;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 字典管理Controller
 *
 * @author dinghanqi
 */
@Controller
@RequestMapping("/fileUpload")
@Scope("prototype")
public class FileUploadController {

    @Autowired
    private UploadfileService uploadfileService;

    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;


    /**
     * 保存上传文件信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveUploadFile")
    public Result<Map<String, String>> saveUploadFile(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String businessCode = request.getParameter("businessCode");
        String md5Id = request.getParameter("fileFingerPrint");
        String fileId = request.getParameter("fileId");
        String fileType = request.getParameter("fileType");
        String referenceId = request.getParameter("referenceId");
        String msg = null;
        try {
            uploadfileService.saveUploadFile(ticketId, businessCode, md5Id, fileId, fileType, referenceId);
            msg = "已成功保存上传文件信息";
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    /**
     * 根据businessCode获取上传文件信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFileByBusinessCode")
    public Result<Map<String, Object>> getFileByBusinessCode(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String businessCode = request.getParameter("businessCode");
        String msg = null;
        Uploadfile uf = null;
        try {
            uf = uploadfileService.getFileByBusinessCode(businessCode);
            msg = "已成功获取上传文件信息";
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("uploadFileInfo", uf);
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    /**
     * 根据businessCode获取上传文件信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateFileByBusinessCode")
    public Result<Map<String, String>> updateFileByBusinessCode(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String ticketId = request.getParameter("ticketId");
        String businessCode = request.getParameter("businessCode");
        String md5Id = request.getParameter("fileFingerPrint");
        String fileId = request.getParameter("fileId");
        String fileType = request.getParameter("fileType");
        String referenceId = request.getParameter("referenceId");
        String msg = null;
        try {
            uploadfileService.updateFileByBusinessCode(ticketId, businessCode, md5Id, fileId, fileType, referenceId);
            msg = "已成功更新上传文件信息";
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    /**
     * 根据文件类型和referenceId获取文件信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFileByFileTypeAndReferenceId")
    public Result<Map<String, Object>> getFileByFileTypeAndReferenceId(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String referenceId = request.getParameter("referenceId");
        String fileType = request.getParameter("fileType");
        String msg = null;
        List<Uploadfile> uf = new ArrayList<Uploadfile>();
        try {
            uf = uploadfileService.getFileByFileTypeAndReferenceId(fileType, referenceId);
            msg = "已成功获取上传文件信息";
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("uploadFileInfo", uf);
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }

    /**
     * 根据fileid删除上传文件
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteFileByFileId")
    public Result<Map<String, String>> deleteFileByFileId(HttpServletRequest request) {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> dataMap = new HashMap<String, String>();
        String fileId = request.getParameter("fileId");
        String msg = null;
        try {
            uploadfileService.deleteFileByFileId(fileId);
            msg = "已成功删除该文件";
            result.setStatus(ResultStatus.Success);
        } catch (Exception e) {
            msg = Constant.SYS_MSG;
            result.setStatus(ResultStatus.Failure);
        }
        dataMap.put("msg", msg);
        result.setData(dataMap);
        return result;
    }
}
