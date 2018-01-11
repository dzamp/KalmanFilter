package reflection;

public class ReflectionCost {


    public static void main(String[] args) throws Exception
    {
        A a = new A("sensor-a","sensor-a",23,88.3,12341243434L);
        for(int i=0;i < 100; i++) {
            System.out.println("Iteration " + i);
            doRegular(a);
            doReflection(a);
            getMemberByReflection(a);
        }

    }

    private static void getMemberByReflection(A a) {
        long start = System.currentTimeMillis();
        for (int i=0; i<1000000; i++)
        {
            try {
                String aString = (String) a.getClass().getField("id").get(a);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void doRegular(A a) throws Exception
    {
        long start = System.currentTimeMillis();
        for (int i=0; i<1000000; i++)
        {
            String aString = a.getId();

        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void doReflection(A a) throws Exception
    {
        long start = System.currentTimeMillis();
        for (int i=0; i<1000000; i++)
        {
           String aString = (String) a.getClass().getMethod("getId").invoke(a,null);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
