package menu_functionalities;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IMenuInterface {

    void createContent() throws IOException;
    void readContent();
    void updateContent();
    void deleteContent();


}
