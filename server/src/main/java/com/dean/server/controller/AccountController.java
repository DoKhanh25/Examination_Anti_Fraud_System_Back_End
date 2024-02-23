package com.dean.server.controller;

import com.dean.server.service.AccountService;
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

    @GetMapping("/sampleAccountFile")
    public ResponseEntity<?> getSampleAccountCreateFile(){
        Resource accountCreateFileResource = new ClassPathResource("/static/account_create_sample.xlsx");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + accountCreateFileResource.getFilename())
                .body(accountCreateFileResource);
    }

    @PostMapping("/uploadAccountFile")
    public ResponseEntity<?> uploadAccountFile(@RequestParam("file") MultipartFile file){
        accountService.saveAccountsByExcel(file);
        return null;
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<?> getAllAccounts(){
        return  accountService.getAllAccount();
    }
}
