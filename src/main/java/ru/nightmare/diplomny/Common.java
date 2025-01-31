package ru.nightmare.diplomny;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class Common {
    public static String readFileFromResources(String fileName) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = Common.class.getClassLoader().getResourceAsStream(fileName);



            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));


            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            inputStream.close();
        } catch (Exception e) {
            if(inputStream!=null)
                inputStream.close();
            throw new IOException("Cannot load by path: "+fileName+" cuz: "+e.getMessage());
        }

        return contentBuilder.toString();
    }
}
