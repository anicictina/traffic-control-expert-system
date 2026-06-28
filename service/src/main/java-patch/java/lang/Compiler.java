package java.lang;

/**
 * Stub za java.lang.Compiler koji je uklonjen u Java 17+.
 * Drools 7.x (MVELDialect) referise ovu klasu u statickom inicijalizatoru.
 * Ovaj stub se ubacuje u java.base modul putem --patch-module JVM opcije.
 */
@SuppressWarnings("all")
@Deprecated(since = "9", forRemoval = true)
public final class Compiler {

    private Compiler() {}

    public static boolean compileClass(Class<?> clazz) {
        return false;
    }

    public static boolean compileClasses(String string) {
        return false;
    }

    public static Object command(Object any) {
        return null;
    }

    public static void enable() {}

    public static void disable() {}
}
