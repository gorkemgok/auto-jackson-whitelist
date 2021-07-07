package dev.ggok.jackson.whitelist.pojo;

import dev.ggok.jackson.whitelist.AutoJacksonWhiteList;

@AutoJacksonWhiteList
public class AllowedTypePojo {

    private int field;

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }
}
