package ru.otus.hw04;

import ru.otus.hw04.annotations.After;
import ru.otus.hw04.annotations.Before;
import ru.otus.hw04.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

import java.util.List;

public class TestFramework {

    public void run (Class<?> klass){
        Method [] methods = klass.getDeclaredMethods();
        List<Method> beforeMethods = calcAnnotatedMethods(methods, Before.class);
        List<Method> afterMethods = calcAnnotatedMethods(methods, After.class);
        for (Method method:methods){
            if(method.isAnnotationPresent(Test.class)){
                Object testObj = ReflectionHelper.instantiate(klass);
                    beforeMethods
                            .forEach(metod->ReflectionHelper.callMethod(testObj,metod.getName()));
                    ReflectionHelper.callMethod(testObj,method.getName());
                    afterMethods
                            .forEach(metod->ReflectionHelper.callMethod(testObj,metod.getName()));
            }
        }
    }

    private List<Method> calcAnnotatedMethods (Method [] methods, Class annotationClass){
        ArrayList<Method> methodsList = new ArrayList<>();
        for (Method method:methods){
            if(method.isAnnotationPresent(annotationClass)){
                methodsList.add(method);
            }
        }
        return methodsList;
    }
}