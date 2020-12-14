package daily.code_generator;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

/**
 * Create by tuke on 2020/11/18
 */
public class PojoFlatGeneratorTest {

    @Test
    public void test() {
        String code = PojoFlatGenerator.generate(A.class);
        System.out.println(code);
    }

    @Data
    private static class S {
        int a;
        Double b;
        String c;
        Date d;
        BigDecimal e;
    }

    @Getter
    @Setter
    private static class A extends S {
        B aa;
        C ab;
        D ac;
    }

    @Data
    private static class B {
        int ba;
        Double bb;
        String bc;
        Date bd;
        C be;
    }

    @Data
    private static class C {
        int ca;
        Double cb;
        String cc;
        Date cd;
        D ce;
    }

    @Data
    private static class D {
        int da;
        Double db;
        String dc;
        Date dd;
        C de;
    }

}
