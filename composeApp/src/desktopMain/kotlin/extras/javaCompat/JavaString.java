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

        MyKotlinClassTestObject.INSTANCE.getProperty();
        MyKotlinClassTestObject.propertyWithJvmLabel = "Eeesooo!!";
        String jvmProperty = MyKotlinClassTestObject.propertyWithJvmLabel;
        System.out.println(jvmProperty);

        MyKotlinClassTestObject.myFunCosa();

        Person23262 person1 = new Person23262("Jorge", 40);
        person1.component1(); // caca
        person1.getName();
        person1.getAge();
    }

    public void testSealed(Op op) {
        // caca, para Java las sealed classes se comportan como una clase normal, ya que no existen
        // y no podemos obtener sus beneficios

    }
}

