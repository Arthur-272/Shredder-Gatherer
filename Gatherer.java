package SGP;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.util.*;

class Gatherer{

    public static byte[] Dec(byte [] b , String skey) throws  Exception{

        Key key = new SecretKeySpec(skey.getBytes(),"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,key);
        b = cipher.doFinal(b);
        return b;

    }

    public static void main(String[] args)throws Exception {
        Gatherer gatherer = new Gatherer();
        gatherer.gatherer();
    }

    public void gatherer()throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the file:");
        String generated_file = scanner.nextLine();
        System.out.println("Enter the key (32 bit) ");
        String skey = scanner.nextLine();
        String temp[] = generated_file.split("[.]");
        String generated_file_without_extension = temp[0];
        String pathOfDesktop = System.getProperty("user.home");
        String pathFile = pathOfDesktop + "\\OneDrive\\Desktop\\Generated\\" +generated_file_without_extension+"_PathFile.txt";
        String generate = pathOfDesktop + "\\OneDrive\\Desktop\\"+generated_file;
        BufferedReader br = new BufferedReader(new FileReader(pathFile));
        FileOutputStream out = new FileOutputStream(generate);
        String paths = br.readLine();
        String temp1 = null;
        while((temp1=br.readLine())!=null){
            paths += temp1;
        }


        String singlePath [] = paths.split("-->");
        for(int i=0 ; i<singlePath.length ; i++){
            File f = new File(singlePath[i]);
            int len = (int)f.length();
            byte bytes[] = new byte[len];
            try {
                FileInputStream fin = new FileInputStream(f);
                fin.read(bytes);
                fin.close();
                bytes = Dec(bytes, skey);
                out.write(bytes);
                f.delete();
            }catch (Exception e){

            }
        }
        br.close();
        File file = new File(pathFile);
        file.delete();
    }
}