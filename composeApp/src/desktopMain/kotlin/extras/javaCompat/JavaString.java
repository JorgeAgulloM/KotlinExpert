package extras.javaCompat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaString {

    public String myStringIndeterminateNullable;

    @Nullable
    public String myStringNullable;

    @NotNull // (No aparece el warning porque la variable no está inicializada)
    public String myStringNotNullable;
}
