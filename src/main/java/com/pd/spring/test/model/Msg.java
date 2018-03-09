package com.pd.spring.test.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author pd 2018/3/9.
 */
public class Msg {

    private Integer id;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
