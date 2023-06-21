package jaeseok.numble.mybox.common.auth;

public interface JwtHandler {
    String create(Long id);

    Long getId(String jwt);

    Long getId();

    Boolean validate(String jwt);
}
