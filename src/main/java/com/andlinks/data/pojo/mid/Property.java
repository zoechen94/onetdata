package com.andlinks.data.pojo.mid;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 喜🐑
 * @date 2019-08-14 09:46
 */
@Data
public class Property implements Serializable {
    private String id;
    private String name;
    private String dataType;
    private String storageName;
    private String modelId;
    private String upperId;
    private Integer upperType;
    private String ownerId;
    private Integer propType;
    private Date createTime;
    private Date modifyTime;
    private String values;
    private boolean filter;
    private boolean index;
    private boolean inherit;
}
