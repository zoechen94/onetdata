package com.andlinks.data.pojo.mid;

import lombok.Data;

import java.util.Date;

/**
 * @author 喜🐑
 * @date 2019-08-12 16:13
 */
@Data
public class Entity {
    private String id;
    private String name;
    private String storageName;
    private String modelId;
    private String ownerId;
    private String parentId;
    private String icon;
    private Date createTime;
    private Date modifyTime;
    private boolean isleaf;
}
