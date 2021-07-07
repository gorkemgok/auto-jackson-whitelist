# AutoJacksonWhiteList

The aim of The AutoJacksonWhiteList library is to make safe polymorphic deserialization easier
with `@AutoJacksonWhiteList` annotation.

Everytime you use `@JsonTypeInfo(use = Id.CLASS)` you introduce a security risk.
> Please refer to the link below for more information.
>
> [On Jackson CVEs: Don’t Panic — Here is what you need to know](https://cowtowncoder.medium.com/on-jackson-cves-dont-panic-here-is-what-you-need-to-know-54cd0d6e8062)

With jackson-2.10 `PolymorphicTypeValidator` is introduces.

> Please refer to the link below for more information.
>
> [Jackson 2.10 features](https://cowtowncoder.medium.com/jackson-2-10-features-cd880674d8a2)

## Installation

To use the library add the maven dependency below.

```xml

<dependency>
    <groupId>dev.ggok.json</groupId>
    <artifactId>auto-jackson-whitelist</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

## Usage

The `PolymorphicTypeValidator` should be explicitly set in jackson `ObjectMapper` to activate `@AutoJacksonWhiteList`
annotation as shown below.

```java
ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setPolymorphicTypeValidator(AutoJacksonList.createValidator());
```

or

```java
ObjectMapper objectMapper=JsonMapper.builder()
        .polymorphicTypeValidator(AutoJacksonList.createValidator())
        .build();
```

After set type validator you can use `@AutoJacksonWhiteList` annotation in your data objects.

```java
import dev.ggok.jackson.whitelist.AutoJacksonWhiteList;

@AutoJacksonWhiteList
public class AllowedTypePojo {

    private int field;

    ... //getters - setters etc
}
```

```java
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class PojoWithPolymorphicField<T> {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    private T polymorphicField;
    
    ... //getters - setters etc
}
```

> If a class is not annotated with `@AutoJacksonWhiteList` or not in the `auto.jackson.whitelist`
> then it would **NOT be deserialized**

> If a class is in `auto.jackson.blacklist` the its deserialization **will be denied in any case**.

https://github.com/FasterXML/jackson-databind/issues/2524