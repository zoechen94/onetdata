package com.andlinks.data.pojo.mid;


import lombok.Data;

import java.util.Date;

/**
 * @author 喜🐑
 * @date 2019-08-13 19:48
 */
@Data
public class RelationVO {
    private String id;

    private String name;

    private String modelId;

    private String storageName;

    private String ownerId;

    private Date createTime;

    private Date modifyTime;

    private Integer total;

    private String modelName;

    private String subGraphNames;


}
