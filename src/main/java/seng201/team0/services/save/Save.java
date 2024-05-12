package seng201.team0.services.save;


import java.io.*;
import java.util.*;

public class Save {
    public void loadSave(File loadpath){
        try {
            Scanner loadScanner = new Scanner(loadpath);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
