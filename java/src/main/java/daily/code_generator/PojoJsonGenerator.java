package daily.code_generator;

import java.lang.reflect.Field;
import java.util.List;
import org.dreamcat.common.util.ReflectUtil;

/**
 * Create by tuke on 2021/1/6
 * <p/>
 * flat a bean to json
 */
public class PojoJsonGenerator {

    public static String generate(Class<?> pojoClass) {
        List<Field> fields = ReflectUtil.retrieveNoStaticFields(pojoClass);
        for (Field field : fields) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

        }
        return null;
    }
}
