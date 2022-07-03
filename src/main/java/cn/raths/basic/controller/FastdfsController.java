package cn.raths.basic.controller;

import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.BaiduAuditUtils;
import cn.raths.basic.utils.FastdfsUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import java.io.IOException;

@RestController
@RequestMapping("/fastdfs")
public class FastdfsController {

    @PostMapping
    public AjaxResult upload(@RequestPart(value = "file", required = true) MultipartFile file){
        try {
            byte[] bytes = file.getBytes();
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String path = FastdfsUtil.upload(bytes, extension);
            return AjaxResult.getAjaxResult().setResultObj(path);

        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @DeleteMapping
    public AjaxResult delete(@RequestParam(value = "filePath", required = true) String filePath){
        try {
            String newPath = filePath.substring(1);
            int i = newPath.indexOf("/");
            String groupName = newPath.substring(0, i);
            String fileName = newPath.substring(i + 1);
            FastdfsUtil.delete(groupName,fileName);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }

    }

}
