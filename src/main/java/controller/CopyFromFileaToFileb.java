package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyFromFileaToFileb {
    public static void copyContent(String fileName1, String fileName2) throws Exception {
        File a = new File(fileName1);
        File b = new File(fileName2);

        FileInputStream in = new FileInputStream(a);
        FileOutputStream out = new FileOutputStream(b);

        try {

            int n;

            // read() function to read the
            // byte of data
            while ((n = in.read()) != -1) {
                // write() function to write
                // the byte of data
                out.write(n);
            }
        } finally {
            if (in != null) {

                // close() function to close the
                // stream
                in.close();
            }
            // close() function to close
            // the stream
            if (out != null) {
                out.close();
            }
        }
    }
}
