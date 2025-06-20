package com.moascode.analyzer;

import java.util.List;

public class ClassAnalyzerTest {
    public static void main(String[] args) {
        // Test with primitive type
        PopupTypeInfo intInfo = ClassAnalyzer.createPopupTypeInfoFromClass(int.class);
        System.out.println(intInfo);

        // Test with interface
        PopupTypeInfo listInfo = ClassAnalyzer.createPopupTypeInfoFromClass(List.class);
        System.out.println(listInfo);

        // Test with a class from JDK
        PopupTypeInfo stringInfo = ClassAnalyzer.createPopupTypeInfoFromClass(String.class);
        System.out.println(stringInfo);
    }
}
