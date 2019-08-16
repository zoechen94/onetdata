package com.andlinks.data.pojo;

import com.andlinks.data.pojo.mid.Entity;
import lombok.Data;

import java.util.List;

/**
 * @author 喜🐑
 * @date 2019-08-12 16:11
 */
@Data
public class EntityList {
    private int code;
    private String message;
    private List<Entity> payload;
}
