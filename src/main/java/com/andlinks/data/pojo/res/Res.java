package com.andlinks.data.pojo.res;

import lombok.Data;

@Data
public class Res {
    private String id;
    private String type;
    public Res(){}
    public Res(String id,String type){
        this.id=id;
        this.type=type;
    }
}
