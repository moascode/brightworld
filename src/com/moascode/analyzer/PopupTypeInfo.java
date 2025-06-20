package com.moascode.analyzer;

import java.util.Arrays;

public class PopupTypeInfo {
    private boolean primitive;
    private boolean interfacez;
    private boolean enumz;
    private String name;
    private boolean jdk;
    private String[] inheritedClassNames;

    public PopupTypeInfo setPrimitive(boolean primitive) {
        this.primitive = primitive;
        return this;
    }

    public PopupTypeInfo setInterfacez(boolean interfacez) {
        this.interfacez = interfacez;
        return this;
    }

    public PopupTypeInfo setEnumz(boolean enumz) {
        this.enumz = enumz;
        return this;
    }

    public PopupTypeInfo setName(String name) {
        this.name = name;
        return this;
    }

    public PopupTypeInfo setJdk(boolean jdk) {
        this.jdk = jdk;
        return this;
    }

    public PopupTypeInfo addAllInheritedClassNames(String[] inheritedClassNames) {
        this.inheritedClassNames = inheritedClassNames;
        return this;
    }

    @Override
    public String toString() {
        return "PopupTypeInfo{" +
                "primitive=" + primitive +
                ", interface=" + interfacez +
                ", enum=" + enumz +
                ", name='" + name + '\'' +
                ", jdk=" + jdk +
                ", inheritedClassNames=" + Arrays.toString(inheritedClassNames) +
                '}';
    }
}
