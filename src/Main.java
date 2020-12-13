import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        saveGame("C://Games/savegames/save.dat", new GameProgress(94, 10, 2, 254.32));

        saveGame("C://Games/savegames/save1.dat", new GameProgress(115, 20, 3, 300.21));

        saveGame("C://Games/savegames/save2.dat", new GameProgress(80, 15, 4, 400.81));
        ArrayList<String> list = new ArrayList<>();
        list.add("C://Games/savegames/save.dat");
        list.add("C://Games/savegames/save1.dat");
        list.add("C://Games/savegames/save2.dat");

        zipFiles("C://Games/savegames/zip_output.zip", list);
    }

    public static void saveGame(String path, GameProgress progress) {

        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathWithZip, ArrayList<String> list) {
        int i = 0;
        ZipOutputStream zout = null;
        try {
            zout = new ZipOutputStream(new FileOutputStream(pathWithZip));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String str : list) {
            try (FileInputStream fin = new FileInputStream(str)) {
                byte[] buffer = new byte[fin.available()];
                fin.read(buffer);

                zout.putNextEntry(new ZipEntry("packed_save" + i + ".dat"));
                zout.write(buffer);
                zout.closeEntry();
                i++;


            } catch (IOException e) {
                e.printStackTrace();
            }
            if (new File(str).delete()) System.out.println(str + " удалил");

        }


    }
}

