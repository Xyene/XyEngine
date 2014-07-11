package tk.ivybits.engine.gl.scene.gl20;

import org.lwjgl.opengl.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class glgen {
    public static void main(String[] argv) throws IllegalAccessException {
        Class[] gls = {
                GL11.class,
                GL12.class,
                GL13.class,
                GL14.class,
                GL15.class,
                GL20.class,
                GL21.class,
                GL30.class,
                GL31.class,
                GL32.class,
                GL33.class,
                GL40.class,
                GL41.class,
                GL42.class,
                GL43.class,
                GL44.class
        };
        String fieldBuf = "";
        String methBuf = "";
        Set<String> members = new HashSet<>();
        for(Class c : gls) {
            Field[] fields = c.getDeclaredFields();
            for(Field f : fields) {
                if(!members.add(f.getName())) continue;
                fieldBuf += String.format("public static %s %s = %s;", f.getType().getSimpleName(), f.getName(), f.get(null));
            }
            Method[] meths = c.getDeclaredMethods();
            for(Method m : meths) {
                if(!Modifier.isPublic(m.getModifiers())) continue;
                methBuf += "public static " +  m.getReturnType().getSimpleName() + " " + m.getName() + "(";
                String call = "";
                Class<?>[] parameterTypes = m.getParameterTypes();
                char n = 'a';
                for (int i = 0, parameterTypesLength = parameterTypes.length; i < parameterTypesLength; i++) {
                    Class p = parameterTypes[i];
                    String s = n + (i != parameterTypesLength - 1 ? "," : "");
                    methBuf += p.getSimpleName() + " " + s;
                    call += s;
                    n += 1;
                }
                methBuf += ") {" + (m.getReturnType() != void.class ? "return " : "") + c.getSimpleName() + "." + m.getName() + "(" + call + ");}";
            }
        }
        System.out.println(fieldBuf + methBuf);
    }
}
