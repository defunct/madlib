package com.goodworkalan.go.go.mix;

import com.goodworkalan.go.go.Artifact;
import com.goodworkalan.mix.BasicJavaModule;

public class MadlibModule extends BasicJavaModule {
    public MadlibModule() {
        super(new Artifact("com.goodworkalan", "madlib", "0.1"));
        addTestDependency(new Artifact("org.testng", "testng", "5.10"));
    }
}
