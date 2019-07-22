package  com.hdsx.lh.maptiles.utils;

import java.io.*;

/**
 * Created by 黄聪 on 2017/10/30.
 */
public class FileUtils {

    public static void deleteDir(File dir){
        File[] filelist=dir.listFiles();
        for(File file:filelist){
            if(file.isFile()){
                file.delete();
            }else{
                deleteDir(file);
            }
        }
        dir.delete();
    }

    public static void copy(File origin,File newfile) throws FileNotFoundException, IOException {
        if(!newfile.getParentFile().exists()){
            newfile.getParentFile().mkdirs();
        }
        FileInputStream fis=new FileInputStream(origin);
        FileOutputStream fos=new FileOutputStream(newfile);
        byte[] buf=new byte[2048];
        int read;
        while((read=fis.read(buf))!=-1){
            fos.write(buf,0,read);
            fos.flush();
        }
        fis.close();
        fos.close();
    }

    public static void writeFile(String filename,String contentStr,String charset) throws FileNotFoundException, IOException{
        byte[] content=contentStr.getBytes(charset);
        FileOutputStream fos=new FileOutputStream(filename);
        fos.write(content);
        fos.flush();
        fos.close();
    }

    public static void writeFile(File file,String contentStr,String charset) throws FileNotFoundException, IOException{
        byte[] content=contentStr.getBytes(charset);
        FileOutputStream fos=new FileOutputStream(file);
        fos.write(content);
        fos.flush();
        fos.close();
    }

    public static void writeFileWithParent(String filename,String contentStr,String charset) throws FileNotFoundException, IOException{
        File file=new File(filename);
        File parent=file.getParentFile();
        if(!parent.exists()){
            parent.mkdirs();
        }
        byte[] content=contentStr.getBytes(charset);
        FileOutputStream fos=new FileOutputStream(file);
        fos.write(content);
        fos.flush();
        fos.close();
    }

    public static void writeFileWithParent(File file,String contentStr,String charset) {
        File parent=file.getParentFile();
        if(!parent.exists()){
            parent.mkdirs();
        }
        byte[] content = null;
        FileOutputStream fos = null;
        try {
            content = contentStr.getBytes(charset);
            fos =new FileOutputStream(file);
            fos.write(content);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static Boolean writeFile(String filename,byte[] content) {
        BufferedOutputStream bos=null;
        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream(filename);
            bos = new BufferedOutputStream(fos);
            bos.write(content);
            bos.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if(fos!=null){
                    fos.close();
                }
                if(bos!=null){
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    public static void writeFile(File file,byte[] content) throws FileNotFoundException, IOException{
        FileOutputStream fos=new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }

    public static void writeFileWithParent(String filename,byte[] content) throws FileNotFoundException, IOException{
        File file=new File(filename);
        File parent=file.getParentFile();
        if(!parent.exists()){
            parent.mkdirs();
        }
        FileOutputStream fos=new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }

    public static void writeFileWithParent(File file,byte[] content) throws FileNotFoundException, IOException{

        File parent=file.getParentFile();
        if(!parent.exists()){
            parent.mkdirs();
        }
        FileOutputStream fos=new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }

    public static byte[] readFile(File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[2048];
        int read;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((read = fis.read(buf)) != -1) {
            bos.write(buf, 0, read);
        }

        fis.close();
        return bos.toByteArray();
    }
}
