package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyFromFileaToFileb {
    public static void copyContent(String fileName1, String fileName2) throws Exception {
        File file1 = new File(fileName1);
        File file2 = new File(fileName2);

        FileInputStream in = new FileInputStream(file1);
        FileOutputStream out = new FileOutputStream(file2);

        try {

            int nrBytesRead;

            // read() function to read the
            // byte of data
            while ((nrBytesRead = in.read()) != -1) {
                // write() function to write
                // the byte of data
                out.write(nrBytesRead);
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
