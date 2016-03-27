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
                System.out.println(ctMethod.getLongName());
                
                ctMethod.instrument(
                        new ExprEditor() {
                            public void edit(MethodCall m)
                                          throws CannotCompileException
                            {
                                System.out.println(m.getClassName() + "." + m.getMethodName() + " " + m.getSignature());
                            }
                        });

            }




            //cc.writeFile();

            //cc.setSuperclass(pool.get("test.Point"));
            // get name of the class
            String SumIntsClassPackage = sumInts.getName();
            System.out.println("Class Name is: " + SumIntsClassPackage);

            // get the simple name of the class without package
            String SumIntsClassNoPackage = sumInts.getSimpleName();
            System.out.println("Class Name without package is: "
                    + SumIntsClassNoPackage);

            // get the package name of the class
            Package SumInstsPackage = sumInts.getPackage();
            System.out.println("Package Name is: " + SumInstsPackage);

            // get all the constructors of the class
            Constructor[] constructors = sumInts.getConstructors();
            System.out.println("Constructors are: "
                    + Arrays.toString(constructors));

            // get constructor with specific argument
            //Constructor constructor = sumInts.getConstructor(Integer.TYPE);
            Constructor constructor = sumInts.getConstructor();

            // initializing an object of the RentCar class
            SumInts ints = (SumInts) constructor.newInstance();
            //SumInts ints = (SumInts) constructor.newInstance(455);

            // get all methods of the class including declared methods of the superclasses
            Method[] allmethods = sumInts.getMethods();
            System.out.println("Methods are: " + Arrays.toString(allmethods));
            for (Method method : allmethods) {
                System.out.println("method = " + method.getName());
            }

            // get all methods declared in the class
            // but excludes inherited methods.
            Method[] declaredMethods = sumInts.getDeclaredMethods();
            System.out.println("Declared Methods are: \n"
                    + Arrays.toString(declaredMethods));
            for (Method dmethod : declaredMethods) {
                System.out.println("method = " + dmethod.getName());
            }

            // get method with specific name and parameters
            Method oneMethod = sumInts.getMethod("test",
                    new Class[] { Long.TYPE });
            System.out.println("Method is: " + oneMethod);
            // we use getDeclaredMethod because getMethod only catch the public methods
            //Method oneMethod = sumInts.getDeclaredMethod("sumOfIntegerUptoN",
            //        new Class[] { Integer.TYPE });

            //define the args that are passed in the method
            Class[] cArg = new Class[1];
            cArg[0] = Long.class;
            //Method oneMethod = sumInts.getDeclaredMethod("sumOfIntegerUptoN", cArg );

            //Method oneMethod = ints.getClass().getDeclaredMethod("identity", cArg);
            //oneMethod.setAccessible(true);
            //System.out.println("Printed!!!");
            //oneMethod.invoke(ints, 5);

//          cArg[0] = Integer.class;
//          Method secondMethod = ints.getClass().getDeclaredMethod("sumOfIntegerUptoN", cArg);
//          oneMethod.setAccessible(true);
//          oneMethod.invoke(ints, 4L);

            System.out.println("Method is: " + oneMethod);

            // get all the parameters of sumOfIntegerUptoN
            Class[] parameterTypes = oneMethod.getParameterTypes();
            System.out.println("Parameter types are: "
                    + Arrays.toString(parameterTypes));

            // get the return type of method
            Class returnType = oneMethod.getReturnType();
            System.out.println("Return type is: " + returnType);

            // gets all the public member fields of the class
            //Field[] fields = sumInts.getFields();
            // returns the array of Field objects
            Field[] fields = sumInts.getFields();
            for(int i = 0; i < fields.length; i++) {
                System.out.println("Field = " + fields[i].toString());
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
