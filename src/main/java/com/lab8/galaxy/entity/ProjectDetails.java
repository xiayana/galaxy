package com.lab8.galaxy.entity;

import java.io.Serializable;
import lombok.Data;
/**
 * (ProjectDetails)实体类
 *
 * @author xy
 * @since 2024-01-04 18:18:24
 */
@Data
public class ProjectDetails implements Serializable {
    private static final long serialVersionUID = -26229543433870775L;
    
    private Integer id;
    
    private String projectrelated;//关于项目
    
    private String projectfeatures;//项目特点
    
    private String tokenomics;//FT类型下Tokenomics 图片base64
    private String tokenomicsSuffix;//FT类型下Tokenomics 图片base64
    private Integer pid;//简介id


}

