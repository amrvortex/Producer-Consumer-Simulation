package mainpackage;

public interface Observer {
    public abstract void updatePrevious();

    public abstract void updateNext(Product product);
}
