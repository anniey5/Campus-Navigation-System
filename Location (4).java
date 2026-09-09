import java.util.Locale;

/**
 * Location
 *
 * Represents a vertex in the campus graph.
 */
public class Location implements Displayable {

    private String name;
    private int x;
    private int y;

    public Location(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Location name cannot be empty."
            );
        }

        this.name = name.trim();

        this.x = 0;
        this.y = 0;
    }
    
    public Location(String name, int x, int y) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Location name cannot be empty."
            );
        }

        this.name = name.trim();
        this.x = x;
        this.y = y;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Location)) {
            return false;
        }

        Location other = (Location) obj;

        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {

        // Generates a hash code based on the location name.
        return name
                .toLowerCase(Locale.ROOT)
                .hashCode();
    }

    @Override
    public String toString() {

        return name;
    }
}