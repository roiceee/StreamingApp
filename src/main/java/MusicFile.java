import java.io.File;
import java.nio.file.Path;

public class MusicFile implements Streamable {
    private String name;
    private int price;
    private Path path;
    private boolean isPremium;

    public MusicFile(String name, int price, boolean isPremium, Path path) {
        this.name = name;
        this.price = price;
        this.path = path;
        this.isPremium = isPremium;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public File toFile() {
        return path.toFile();
    }

    @Override
    public String toString() {
        return "TRACK NAME: " + name + " || PRICE: " + price;
    }
}