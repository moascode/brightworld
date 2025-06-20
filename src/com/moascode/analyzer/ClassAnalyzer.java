package com.moascode.analyzer;

import java.util.Arrays;
import java.util.List;

public class ClassAnalyzer {
    private static final List<String> JDK_PACKAGE_PREFIXES =
            Arrays.asList("com.sun.", "java", "javax", "jdk", "org.w3c", "org.xml");

    public static PopupTypeInfo createPopupTypeInfoFromClass(Class<?> inputClass) {
        PopupTypeInfo popupTypeInfo = new PopupTypeInfo();

        /** Complete the Code **/

        popupTypeInfo.setPrimitive(inputClass.isPrimitive())
                .setInterfacez(inputClass.isInterface())
                .setEnumz(inputClass.isEnum())
                .setName(inputClass.getSimpleName())
                .setJdk(isJdkClass(inputClass))
                .addAllInheritedClassNames(getAllInheritedClassNames(inputClass));

        return popupTypeInfo;
    }

    /*********** Helper Methods ***************/

    public static boolean isJdkClass(Class<?> inputClass) {
        /** Complete the code
         Hint: What does inputClass.getPackage() return when the class is a primitive type?
         **/
        return JDK_PACKAGE_PREFIXES.stream().anyMatch(p -> inputClass.isPrimitive() || inputClass.getPackage().getName().startsWith(p));
    }

    public static String[] getAllInheritedClassNames(Class<?> inputClass) {
        /** Complete the code
         Hints: What does inputClass.getSuperclass() return when the inputClass doesn't inherit from any class?
         What does inputClass.getSuperclass() return when the inputClass is a primitve type?
         **/
        Boolean isInterface = inputClass.isInterface();

        return isInterface ?
                Arrays.stream(inputClass.getInterfaces()).map(Class::getSimpleName).toArray(String[]::new) :
                inputClass.getSuperclass() == null ? null : new String[] {inputClass.getSuperclass().getSimpleName()};
    }
}
