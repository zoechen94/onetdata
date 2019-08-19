package com.andlinks.data.pojo.rel;

import lombok.Data;

@Data
public class Rel {
    private String id;
    private String type;
    private String start;
    private String end;
    private String startType;
    private String endType;
    public Rel(){}
    public Rel(String id,String type,String start,String end,String startType,String endType){
        this.id=id;
        this.type=type;
        this.start=start;
        this.end=end;
        this.startType=startType;
        this.endType=endType;
    }
}
