package extras.javaCompat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaString {

    public String myStringIndeterminateNullable;

    @Nullable
    public String myStringNullable;

    @NotNull // (No aparece el warning porque la variable no está inicializada)
    public String myStringNotNullable;

    public void testWithKotlinCode() {
        //KotlinUsingJavaKt.myTopLevelFunction("Hello World!!");
        Utils.myTopLevelFunction("Hello World!!");

        // Uso de funciones de estensión desde Java
        Utils.myTopLevelFunctionExt("Hello", "World!");

        // Llamada a una función con sobrecarga
        Utils.myTopLevelFunctionStr("Hello ");

        // Funciones con prametros de tipo lambdas
        Utils.myTopLevelFunctionLambda((string, integer) -> string + " cosa " + integer);

        MyKotlinClassTest myKotlinClassTest = new MyKotlinClassTest();
        myKotlinClassTest.setProperty("Hello World!!");
        String cosa = myKotlinClassTest.getProperty();
    }
}

