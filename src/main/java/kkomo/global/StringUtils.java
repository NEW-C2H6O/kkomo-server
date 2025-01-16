package kkomo.global;

public class StringUtils {

    public static String toSnakeCase(final String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }
        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2");
    }

    public static String toUpperSnakeCase(final String camelCase) {
        return toSnakeCase(camelCase).toUpperCase();
    }
}
