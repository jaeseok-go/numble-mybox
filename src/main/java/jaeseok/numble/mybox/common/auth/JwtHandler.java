package jaeseok.numble.mybox.common.auth;

public interface JwtHandler {
    String create(String id);

    String getId(String jwt);

    String getId();

    Boolean validate(String jwt);
}
