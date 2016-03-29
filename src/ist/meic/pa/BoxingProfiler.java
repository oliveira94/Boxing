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
            ClassPool pool = ClassPool.getDefault();

            CtClass cc = pool.get("ist.meic.pa.SumInts");

            //to thread every method
            for (CtMethod ctMethod : cc.getDeclaredMethods()){
                ctMethod.instrument(
                        new ExprEditor() {
                            public void edit(MethodCall m) throws CannotCompileException
                            {
                                //conditon to detect boxing because when exist boxing the method valueOf
                                //is always called
                                if(m.getMethodName().equals("valueOf"))
                                {
                                    //if the method is main
                                    if(ctMethod.getName().equals("main")){
                                        System.err.print(ctMethod.getLongName());

                                        System.err.print(" boxed 1 ");
                                        try {
                                            //return the java language type of the returned value
                                            System.err.println(m.getMethod().getReturnType().getName());
                                        } catch (NotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    } else try {
                                        //if is another method we need to compare the type of the parameter and
                                        //the type of returned value, and if is, is a boxing, if not, isn't a boxing
                                        if(ctMethod.getLongName().toLowerCase().endsWith(m.getMethod().getReturnType().getName()+")")){
                                            System.err.print(ctMethod.getLongName());

                                            System.err.print(" boxed 1 ");
                                            try {
                                                System.err.println(m.getMethod().getReturnType().getName());
                                            } catch (NotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (NotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //conditon to detect unboxing because when exist unboxing the method like intValue
                                //or longValue, so its always called a method that ends with Value
                                else if(m.getMethodName().endsWith("Value"))
                                {
                                    if(ctMethod.getName().equals("main")){
                                        System.err.print(ctMethod.getLongName());

                                        System.err.print(" unboxed 1 ");
                                        try {
                                            String type_returned = m.getMethod().getReturnType().getName();
                                            //called the method getType to get the java language type that its returned
                                            getType(type_returned);

                                        } catch (NotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    } else try {
                                        if(ctMethod.getLongName().toLowerCase().endsWith(m.getMethod().getReturnType().getName()+")")){
                                            System.err.print(ctMethod.getLongName());

                                            System.err.print(" unboxed 1 ");
                                            try {
                                                String type_returned = m.getMethod().getReturnType().getName();
                                                getType(type_returned);
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

    //Method responsable for print a java language type based on a primitive type
    public static void getType(String type){
        switch (type){
            case "int": System.err.println("java.lang.Integer");
                break;
            case "long": System.err.println("java.lang.Long");
                break;
            case "char": System.err.println("java.lang.Character");
                break;
            case "byte": System.err.println("java.lang.Byte");
                break;
            case "short": System.err.println("java.lang.Short");
                break;
            case "double": System.err.println("java.lang.Double");
                break;
            case "float": System.err.println("java.lang.Float");
                break;
            case "boolean": System.err.println("java.lang.Boolean");
                break;
        }
    }
}
