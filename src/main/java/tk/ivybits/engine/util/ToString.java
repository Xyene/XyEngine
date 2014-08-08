/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

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
    /**
     * Annotation to define which fields should be included in {@link tk.ivybits.engine.util.ToString#toString}.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Printable {

    }

    /**
     * Reflectively generates a string representing an object, useful for easy implementations of {@link java.lang.Object#toString}.
     * <p/>
     * Fields to be included in the string representation must be annotated with {@link tk.ivybits.engine.util.ToString.Printable}.
     * <p/>
     * For example:
     * <p/>
     * <code>
     * class Foo {
     *      private @Printable float fizz = 0;
     *      private @Printable int buzz = 1;
     *
     *      @Override
     *      public String toString() {
     *          return ToString.toString(this);
     *      }
     * }
     * ...
     * Foo foo = new Foo();
     * System.out.println(foo);
     * </code>
     * <p/>
     * Would yield:
     * <p/>
     * <code>
     * "Foo{fizz=0.0, buzz=1}"
     * </code>
     * <p/>
     *
     * <b>Note: the order of the fields as they appear in the toString-ified representation is unspecified,
     * and may not be the order they appear in source. On the Oracle VM and OpenJDK, however, they are.</b>
     *
     * @param obj The object to stringify.
     * @return A string representation of the given object.
     */
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
            if (!first) {
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
