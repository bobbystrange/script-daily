package org.dreamcat.daily.script.parser;

import java.lang.reflect.Method;
import java.util.Arrays;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.text.argparse.ArgParseException;
import org.dreamcat.common.text.argparse.ArgParser;
import org.dreamcat.daily.script.ApiDoc;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
@Slf4j
public abstract class CliParser implements Parser {

    protected boolean verbose;
    // x.y.z#f(a, b, c[])
    protected String qualifiedMethodName;

    ///

    protected String className;
    protected String methodName;
    protected String[] paramNames;
    protected Class<?>[] parameterTypes;
    protected Method method;
    protected Class<?> returnType;

    protected abstract ApiDoc doParse();

    @Override
    public ApiDoc parse(String... args) {
        this.parseArgs(args);

        // x.y.z#func(int, a.b.C, [Ljava.lang.String)
        int sharpI = qualifiedMethodName.indexOf('#');
        int leftBraceI = qualifiedMethodName.indexOf('(');
        int rightBraceI = qualifiedMethodName.indexOf(')');

        this.className = qualifiedMethodName.substring(0, sharpI); // x.y.z
        this.methodName = qualifiedMethodName.substring(sharpI + 1, leftBraceI); // func
        this.paramNames = Arrays.stream(qualifiedMethodName.substring(leftBraceI + 1, rightBraceI).split(","))
                .map(String::trim).toArray(String[]::new);

        this.parameterTypes = Arrays.stream(paramNames)
                .map(this::tryToClass).toArray(Class[]::new);
        Class<?> clazz = this.tryToClass(className);
        try {
            this.method = clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            log.error("fail to get method `{}`: {}", methodName, e.toString());
            System.exit(1);
            return null;
        }
        this.returnType = method.getReturnType();

        return this.doParse();
    }

    protected ArgParser defineArgs() {
        ArgParser argParser = ArgParser.newInstance();
        argParser.addBool("verbose", "v", "verbose");
        argParser.add("qualifiedMethod", "qm", "qualified-method");
        return argParser;
    }

    protected ArgParser parseArgs(String... args) {
        ArgParser argParser = this.defineArgs();
        try {
            argParser.parse(args);
        } catch (ArgParseException e) {
            log.error("fail to parse args: {}", e.toString());
            System.exit(1);
        }

        this.verbose = argParser.getBool("verbose");
        this.qualifiedMethodName = argParser.get("qualifiedMethod");
        return argParser;
    }

    private Class<?> tryToClass(String className) {
        try {
            return toClass1(className);
        } catch (Exception e) {
            log.error("fail to get class `{}`: {}", className, e.toString());
            System.exit(1);
            return null;
        }
    }

    public static Class<?> toClass1(String className) throws NotFoundException, CannotCompileException {
        return toCtClass(className).toClass();
    }

    public static CtClass toCtClass(String className) throws NotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        return classPool.get(className);
    }
}
