import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyDynamicProxyInterface implements InvocationHandler{

    private Object proxyObj;

    public MyDynamicProxyInterface(Object obj) {
        this.proxyObj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           Object obj = method.invoke(proxyObj,args);
            return obj;
    }
}
