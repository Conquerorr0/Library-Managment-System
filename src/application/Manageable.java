package application;

import java.io.IOException;

public interface Manageable {
    void save() throws IOException;
    void create();
    Object read();
    void update();
    void delete();
}

