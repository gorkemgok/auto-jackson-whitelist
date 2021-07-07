package dev.ggok.jackson.whitelist;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

public class AutoWhiteListPolymorphicTypeValidator extends PolymorphicTypeValidator.Base {

    private final ListRepository listRepository;

    public AutoWhiteListPolymorphicTypeValidator(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    @Override
    public Validity validateBaseType(MapperConfig<?> mapperConfig, JavaType javaType) {
        return validate(javaType);
    }

    @Override
    public Validity validateSubClassName(MapperConfig<?> mapperConfig, JavaType javaType, String s) throws JsonMappingException {
        return validate(javaType);
    }

    @Override
    public Validity validateSubType(MapperConfig<?> mapperConfig, JavaType javaType, JavaType javaType1) throws JsonMappingException {
        return validate(javaType1);
    }

    private Validity validate(JavaType javaType) {
        Class<?> clazz = javaType.getRawClass();
        if (Object.class.equals(clazz)) {
            return Validity.INDETERMINATE;
        }
        String className = clazz.getName();
        Validity validity = listRepository.getValidity(className);
        if (!validity.equals(Validity.INDETERMINATE)) {
            return validity;
        }
        AutoJacksonWhiteList autoJacksonWhiteList = clazz.getAnnotation(AutoJacksonWhiteList.class);
        if (autoJacksonWhiteList == null) {
            return Validity.DENIED;
        } else {
            listRepository.addToWhiteList(className);
            return Validity.ALLOWED;
        }
    }
}
