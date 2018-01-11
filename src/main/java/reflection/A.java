package reflection;

public class A {
    String name;
    public String id;
    int value;
    double percentage;
    long timestamp;


    public A(String name, String id, int value, double percentage, long timestamp) {
        this.name = name;
        this.id = id;
        this.value = value;
        this.percentage = percentage;
        this.timestamp = timestamp;
    }

    public A() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
