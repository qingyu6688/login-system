package com.example.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/file")
@lombok.RequiredArgsConstructor
@Tag(name = "文件模块", description = "文件上传接口")
public class FileController {

    @Value("${file.upload-path}")
    private String uploadPath;

    private final com.example.login.service.SysFileService sysFileService;

    @PostMapping("/upload")
    @Operation(summary = "文件上传", description = "上传文件并返回访问URL")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "文件不能为空"));
        }

        try {
            // Create directory if not exists
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : ".jpg";
            String filename = UUID.randomUUID().toString() + suffix;

            // Save file
            File dest = new File(uploadPath + filename);
            file.transferTo(dest);

            // Return URL (relative path)
            String url = "/uploads/" + filename;

            // Save to DB
            com.example.login.entity.SysFile sysFile = new com.example.login.entity.SysFile();
            sysFile.setFileName(filename);
            sysFile.setOriginalName(originalFilename);
            sysFile.setFileType(suffix);
            sysFile.setFileSize(file.getSize());
            sysFile.setFilePath(dest.getAbsolutePath());
            sysFile.setFileUrl(url);
            sysFileService.save(sysFile);

            return ResponseEntity.ok(Map.of("url", url));

        } catch (IOException e) {
            log.error("File upload failed", e);
            return ResponseEntity.internalServerError().body(Map.of("message", "文件上传失败"));
        }
    }
}
