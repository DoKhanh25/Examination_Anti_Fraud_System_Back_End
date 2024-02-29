package com.dean.server.controller;

import com.dean.server.security.JwtUtil;
import com.dean.server.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/api/admin/account")
@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/sampleAccountFile")
    public ResponseEntity<?> getSampleAccountCreateFile(HttpServletRequest request){
        Boolean isAdminRole = jwtUtil.isAdminRole(request);
        if (!isAdminRole){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }

        Resource accountCreateFileResource = new ClassPathResource("/static/account_create_sample.xlsx");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + accountCreateFileResource.getFilename())
                .body(accountCreateFileResource);
    }

    @PostMapping("/uploadAccountFile")
    public ResponseEntity<?> uploadAccountFile(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request){

        Boolean isAdminRole = jwtUtil.isAdminRole(request);
        if (!isAdminRole){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }
        accountService.saveAccountsByExcel(file);

        return null;
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<?> getAllAccounts(HttpServletRequest request){
        Boolean isAdminRole = jwtUtil.isAdminRole(request);
        if (!isAdminRole){
            return ResponseEntity.status(403).body("You are not authorized to access this resource");
        }
        return  accountService.getAllStudentAccount();
    }
}
