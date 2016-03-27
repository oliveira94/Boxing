package ist.meic.pa;

import javassist.*;
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
            // Obtain the class object
            Class sumInts = SumInts.class;

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
                                if(m.getMethodName().toString().equals("valueOf"))
                                {
                                    System.out.print(ctMethod.getLongName());
                                    System.out.print(" boxed 1 ");
                                    try {
                                        System.out.println(ctMethod.getReturnType().getName());
                                    } catch (NotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if(m.getMethodName().toString().endsWith("Value"))
                                {
                                    System.out.print(ctMethod.getLongName());
                                    System.out.print(" unboxed 1 ");
                                    try {
                                        System.out.println(ctMethod.getReturnType().getName());
                                    } catch (NotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }

            // get name of the class
//            String SumIntsClassPackage = sumInts.getName();
//            System.out.println("Class Name is: " + SumIntsClassPackage);
//
//            // get the simple name of the class without package
//            String SumIntsClassNoPackage = sumInts.getSimpleName();
//            System.out.println("Class Name without package is: "
//                    + SumIntsClassNoPackage);
//
//            // get the package name of the class
//            Package SumInstsPackage = sumInts.getPackage();
//            System.out.println("Package Name is: " + SumInstsPackage);
//
//            // get all the constructors of the class
//            Constructor[] constructors = sumInts.getConstructors();
//            System.out.println("Constructors are: "
//                    + Arrays.toString(constructors));
//
//            // get constructor with specific argument
//            //Constructor constructor = sumInts.getConstructor(Integer.TYPE);
//            Constructor constructor = sumInts.getConstructor();
//
//            // initializing an object of the RentCar class
//            SumInts ints = (SumInts) constructor.newInstance();
//            //SumInts ints = (SumInts) constructor.newInstance(455);
//
//            // get all methods of the class including declared methods of the superclasses
//            Method[] allmethods = sumInts.getMethods();
//            System.out.println("Methods are: " + Arrays.toString(allmethods));
//            for (Method method : allmethods) {
//                System.out.println("method = " + method.getName());
//            }
//
//            // get all methods declared in the class
//            // but excludes inherited methods.
//            Method[] declaredMethods = sumInts.getDeclaredMethods();
//            System.out.println("Declared Methods are: \n"
//                    + Arrays.toString(declaredMethods));
//            for (Method dmethod : declaredMethods) {
//                System.out.println("method = " + dmethod.getName());
//            }



        }  catch (IllegalArgumentException e) {
            e.printStackTrace();
        }catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
