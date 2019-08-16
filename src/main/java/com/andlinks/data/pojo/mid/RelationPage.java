package com.andlinks.data.pojo.mid;

import lombok.Data;

import java.util.List;

/**
 * @author 喜🐑
 * @date 2019-08-13 19:45
 */
@Data
public class RelationPage {
    private Integer pageNum;

    private Integer pageSize;

    private Integer size;

    private Integer startRow;

    private Integer endRow;

    private Integer total;

    private Integer pages;

    private List<RelationVO> list;
}
