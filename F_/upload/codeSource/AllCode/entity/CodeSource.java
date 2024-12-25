package com.telecom.entity;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Table;

import com.telecom.base.BaseEntity;

@Table(name = "t_code_source")
public class CodeSource extends BaseEntity {

    private static final long serialVersionUID = -1L;

    private String adminId;// 
    private String adminName;// 
    private String fileName;// 
    private String fileUrl;// 

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


}
