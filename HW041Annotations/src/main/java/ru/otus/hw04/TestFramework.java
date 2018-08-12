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
        for (Method method:methods){
            if(method.isAnnotationPresent(Test.class)){
                Object testObj = ReflectionHelper.instantiate(klass);
                    beforeMethods(methods).forEach(metod->ReflectionHelper.callMethod(testObj,metod.getName()));
                    ReflectionHelper.callMethod(testObj,method.getName());
                    afterMethods(methods).forEach(metod->ReflectionHelper.callMethod(testObj,metod.getName()));
            }
        }
    }

    private List<Method> beforeMethods (Method [] methods){
        ArrayList<Method> methodsList = new ArrayList<>();
        for (Method method:methods){
            if(method.isAnnotationPresent(Before.class)){
                methodsList.add(method);
            }
        }
        return methodsList;
    }

    private  List<Method> afterMethods (Method [] methods){
        List<Method> methodsList = new ArrayList<>();
        for (Method method:methods){
            if(method.isAnnotationPresent(After.class)){
                methodsList.add(method);
            }
        }
        return methodsList;
    }
}
