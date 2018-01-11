import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyDynamicProxyClass implements InvocationHandler{

    private Object proxyObj;

    public MyDynamicProxyClass(Object obj) {
        this.proxyObj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           Object obj = method.invoke(proxyObj,args);
            return obj;
    }
    static public Object newInstance(Object obj, Class[] interfaces) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                interfaces,
                new MyDynamicProxyClass(obj));
    }
}
