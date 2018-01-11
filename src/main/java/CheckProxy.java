import java.lang.reflect.Proxy;

public class CheckProxy {

    public static void main(String[] args) {
        String aString = "proxy";
        MyProxyInterface foo = (MyProxyInterface)
                MyDynamicProxyClass.newInstance(aString, new Class[]
                        { MyProxyInterface.class });


        foo.myMethod();
    }


}
