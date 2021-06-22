package com.lind.http2.controller;

import com.lind.http2.feign.SelfClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@Slf4j
public class FileController {
    @Autowired
    SelfClient selfClient;

    /**
     * 响应体加入文件流
     *
     * @param response
     * @param filePath 文件从盘符开始的完整路径
     */
    public static void buildExcelDocument(HttpServletResponse response, String filePath) {
        log.debug("responseFileStream imgPath:" + filePath);
        if (filePath.contains("%")) {
            try {
                filePath = URLDecoder.decode(filePath, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.debug("responseFileStream decode error:" + e.toString());
            }
        }
        if (filePath.contains("%")) {
            try {
                filePath = URLDecoder.decode(filePath, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.debug("responseFileStream decode error:" + e.toString());
            }
        }

        ServletOutputStream out = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
            String[] dir = filePath.split("/");
            String fileName = dir[dir.length - 1];
            String[] array = fileName.split("[.]");
            String fileType = array[array.length - 1].toLowerCase();
            //设置文件ContentType类型
            if ("jpg,jepg,gif,png".contains(fileType)) {//图片类型
                response.setContentType("image/" + fileType);
            } else if ("pdf".contains(fileType)) {//pdf类型
                response.setContentType("application/pdf");
            } else {//自动判断下载文件类型
                response.setContentType("multipart/form-data");
            }
            //设置文件头：最后一个参数是设置下载文件名
            //response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
            out = response.getOutputStream();
            // 读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            log.error("responseFileStream error:FileNotFoundException" + e.toString());
        } catch (Exception e) {
            log.error("responseFileStream error:" + e.toString());
        } finally {
            try {
                out.close();
                in.close();
            } catch (NullPointerException e) {
                log.error("responseFileStream stream close() error:NullPointerException" + e.toString());
            } catch (Exception e) {
                log.error("responseFileStream stream close() error:" + e.toString());
            }
        }
    }

    @GetMapping("get-file")
    public void getFile(HttpServletResponse response, String path) {
        path = "D:\\test\\" + path;
        buildExcelDocument(response, path);
    }

    @GetMapping("send2")
    public String send2() {
        log.info("read send2");
        selfClient.send3();
        return "ok send2";
    }

    @SneakyThrows
    @GetMapping("send3")
    public String send3() {
        log.info("read send3");
        Thread.sleep(1000);
        selfClient.send4();
        return "ok send3";
    }

    @GetMapping("send4")
    public String send4() {
        log.info("read send4");
        selfClient.send5();
        return "ok send4";
    }

    @GetMapping("send5")
    public String send5() {
        log.info("read send5");
        return "ok send5";
    }

    @GetMapping("send")
    public void send(HttpServletResponse response, String path) {
        log.info("read send1");
        selfClient.send2();
    }
}


