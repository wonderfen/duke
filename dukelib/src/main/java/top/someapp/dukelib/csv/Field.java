package top.someapp.dukelib.csv;

import javax.annotation.Nonnull;

/**
 * 字段/列/属性
 *
 * @author zwz
 * Created on 2020-11-19
 */
public class Field {

    private final String name;
    private BasicType type;

    public Field(@Nonnull String name) {
        this(name, BasicType.UNDEFINED);
    }

    public Field(@Nonnull String name, @Nonnull BasicType type) {
        this.name = name;
        this.type = type;
    }

    public BasicType getType() {
        return type;
    }

    public void setType(BasicType type) {
        this.type = type;
    }
}
