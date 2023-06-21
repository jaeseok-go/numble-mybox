package jaeseok.numble.mybox.storage;

import lombok.Getter;

@Getter
public class FileKey {
    private String key;

    public FileKey(Object key) {
        this.key = String.valueOf(key);
    }
}
