package tk.ivybits.engine.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToString {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Printable {

    }

    public static String toString(Object obj) {
        StringBuilder toString = new StringBuilder();
        toString.append(obj.getClass().getSimpleName());
        Class clazz = obj.getClass();
        List<Field> fields = new ArrayList<>();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        toString.append("{");
        boolean first = true;
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            if (f.getAnnotation(Printable.class) == null) continue;
            f.setAccessible(true);
            if(!first) {
                if (i + 1 < fields.size()) {
                    toString.append(", ");
                }
            } else first = false;
            try {
                toString.append(f.getName()).append("=").append(f.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        toString.append("}");
        return toString.toString();
    }
}
