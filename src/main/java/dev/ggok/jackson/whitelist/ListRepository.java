package dev.ggok.jackson.whitelist;

import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

public interface ListRepository {

    PolymorphicTypeValidator.Validity getValidity(String className);

    void addToWhiteList(String className);

}
