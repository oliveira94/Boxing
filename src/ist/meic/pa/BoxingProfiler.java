package ist.meic.pa;

import javassist.*;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by artur on 21/03/16.
 */

public class BoxingProfiler {

    public static void main(String[] args) throws CannotCompileException {

        try {
            //create the ClassPool
            ClassPool pool = ClassPool.getDefault();

            CtClass cc = pool.get("ist.meic.pa.SumInts");

            for (CtMethod ctMethod : cc.getDeclaredMethods()){
                //valueof boxing
                //...value unboxing
                ctMethod.instrument(
                        new ExprEditor() {
                            public void edit(MethodCall m) throws CannotCompileException
                            {
                                if(m.getMethodName().equals("valueOf"))
                                {
                                    if(ctMethod.getName().equals("main")){
                                        System.out.print(ctMethod.getLongName());

                                        System.out.print(" boxed 1 ");
                                        try {
                                            System.out.println(m.getMethod().getReturnType().getName());
                                        } catch (NotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    } else try {
                                        if(ctMethod.getLongName().toLowerCase().endsWith(m.getMethod().getReturnType().getName()+")")){
                                            System.out.print(ctMethod.getLongName());

                                            System.out.print(" boxed 1 ");
                                            try {
                                                System.out.println(m.getMethod().getReturnType().getName());
                                            } catch (NotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (NotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if(m.getMethodName().endsWith("Value"))
                                {
                                    if(ctMethod.getName().equals("main")){
                                        System.out.print(ctMethod.getLongName());

                                        System.out.print(" unboxed 1 ");
                                        try {
                                            System.out.println(m.getMethod().getReturnType().getName().getClass());
                                        } catch (NotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    } else try {
                                        if(ctMethod.getLongName().toLowerCase().endsWith(m.getMethod().getReturnType().getName()+")")){
                                            System.out.print(ctMethod.getLongName());

                                            System.out.print(" unboxed 1 ");
                                            try {
                                                System.out.println(m.getMethod().getReturnType().getName().getClass());
                                            } catch (NotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (NotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }
        }  catch (IllegalArgumentException | NotFoundException e) {
            e.printStackTrace();
        }
    }
}
