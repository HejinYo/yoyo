package basetest;

import org.junit.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipTest {

    @Test
    public void test_files() {
        File file = new File("D:/360Downloads/222");
        if(file.exists()){

            file.delete();
        }
    }

    public static void main(String[] args) {
        String logs_Path = "D:/360Downloads/123";
        String opload_Path = "D:/360Downloads";
        String zipName = "tomcat_logs";
        String logs_Name[] = {"text.txt", "text1.txt", "text2.txt", "text5.txt", "text6.txt",};
        boolean flag = Zip(logs_Path, logs_Name, opload_Path, zipName);
        if (flag) {
            System.out.println(">>>>>> 文件打包成功. <<<<<<");
        } else {
            System.out.println(">>>>>> 文件打包失败. <<<<<<");
        }
    }

    public static boolean Zip(String logs_Path, String[] logs_Name, String opload_Path, String zipName) {
        boolean flag = false;//成功标志
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        try {
            File oploadFile = new File(opload_Path);
            File[] oploadFiles = oploadFile.listFiles();
            //删除原来所有的日志打包文件,暂时存放在opload目录下，每次执行会删除前一天所有的打包文件
            for (int i = 0; i < oploadFiles.length; i++) {
                if (oploadFiles[i].getName().indexOf("tomcat_logs.zip") != -1) {
                    File zipoldFile = new File(opload_Path + "/" + oploadFiles[i].getName());
                    zipoldFile.delete();//文件存在就删除
                }
            }

            File zipFile = new File(opload_Path + "/" + zipName + ".zip");
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            byte[] bufs = new byte[1024 * 10];

            File[] Files = new File[logs_Name.length];
            for (int i = 0; i < logs_Name.length; i++) {
                Files[i] = new File(logs_Name[i]);
            }

            for (int i = 0; i < Files.length; i++) {
                if (Files[i].exists()) {
                    // 创建ZIP实体,并添加进压缩包
                    ZipEntry zipEntry = new ZipEntry(Files[i].getName());
                    zos.putNextEntry(zipEntry);
                    // 读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(Files[i]);
                    bis = new BufferedInputStream(fis, 1024 * 10);
                    int read = 0;
                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                        zos.write(bufs, 0, read);
                    }
                }
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {// 关闭流
            try {
                if (null != bis) bis.close();
                if (null != zos) zos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return flag;
    }
}
