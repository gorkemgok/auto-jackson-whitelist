package dev.ggok.jackson.whitelist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.ggok.jackson.whitelist.pojo.AllowedTypeInFilePojo;
import dev.ggok.jackson.whitelist.pojo.AllowedTypePojo;
import dev.ggok.jackson.whitelist.pojo.BlackListedTypePojo;
import dev.ggok.jackson.whitelist.pojo.NotAllowedTypePojo;
import dev.ggok.jackson.whitelist.pojo.TestPojoWithPolymorphicField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AutoWhiteListPolymorphicTypeValidatorTest {

    static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = JsonMapper.builder()
                .polymorphicTypeValidator(AutoJacksonList.createValidator())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPolymorphicTypeValidator(AutoJacksonList.createValidator());
    }

    @Test
    void convertAllowedPojo_SerializeCorrectly() throws JsonProcessingException {
        TestPojoWithPolymorphicField<AllowedTypePojo> allowed = new TestPojoWithPolymorphicField<>();
        AllowedTypePojo allowedTypePojo = new AllowedTypePojo();
        allowedTypePojo.setField(3);
        allowed.setPolymorphicField(allowedTypePojo);

        String allowedJson = objectMapper.writeValueAsString(allowed);

        TestPojoWithPolymorphicField<?> testPojo =
                objectMapper.readValue(allowedJson, TestPojoWithPolymorphicField.class);
        assertEquals(testPojo.getPolymorphicField().getClass(), AllowedTypePojo.class);
    }

    @Test
    void convertAllowedInFilePojo_SerializeCorrectly() throws JsonProcessingException {
        TestPojoWithPolymorphicField<AllowedTypeInFilePojo> allowed = new TestPojoWithPolymorphicField<>();
        AllowedTypeInFilePojo allowedInFileTypePojo = new AllowedTypeInFilePojo();
        allowedInFileTypePojo.setField(3);
        allowed.setPolymorphicField(allowedInFileTypePojo);

        String allowedJson = objectMapper.writeValueAsString(allowed);

        TestPojoWithPolymorphicField<?> testPojo =
                objectMapper.readValue(allowedJson, TestPojoWithPolymorphicField.class);
        assertEquals(testPojo.getPolymorphicField().getClass(), AllowedTypeInFilePojo.class);
    }

    @Test
    void convertNotAllowedPojo_ThrowInvalidTypeIdException() throws JsonProcessingException {
        TestPojoWithPolymorphicField<NotAllowedTypePojo> notAllowed = new TestPojoWithPolymorphicField<>();
        NotAllowedTypePojo notAllowedTypePojo = new NotAllowedTypePojo();
        notAllowedTypePojo.setField(2);
        notAllowed.setPolymorphicField(notAllowedTypePojo);

        String deniedJson = objectMapper.writeValueAsString(notAllowed);

        InvalidTypeIdException invalidTypeIdException = assertThrows(InvalidTypeIdException.class,
                () -> objectMapper.readValue(deniedJson, TestPojoWithPolymorphicField.class));
        assertEquals(NotAllowedTypePojo.class.getName(), invalidTypeIdException.getTypeId());
    }

    @Test
    void convertBlackListedPojo_ThrowInvalidTypeIdException() throws JsonProcessingException {
        TestPojoWithPolymorphicField<BlackListedTypePojo> blackListed = new TestPojoWithPolymorphicField<>();
        BlackListedTypePojo blackListedTypePojo = new BlackListedTypePojo();
        blackListedTypePojo.setField(2);
        blackListed.setPolymorphicField(blackListedTypePojo);

        String blackListedPojo = objectMapper.writeValueAsString(blackListed);

        InvalidTypeIdException invalidTypeIdException = assertThrows(InvalidTypeIdException.class,
                () -> objectMapper.readValue(blackListedPojo, TestPojoWithPolymorphicField.class));
        assertEquals(BlackListedTypePojo.class.getName(), invalidTypeIdException.getTypeId());
    }
}