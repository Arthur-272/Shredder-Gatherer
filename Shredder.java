package SGP;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.util.*;
class Shredder{
    public static String pathGenerator(){
        String path = new String ("C:\\");
        Random random = new Random();
        int nol = random.nextInt(10);
        for(int i=0;i<nol;i++){
            File check = new File(path);
            File dir = new File(path);
            File temp [] = dir.listFiles();
            try{
                if(temp.length>0){
                    int rand = random.nextInt(temp.length);
                    check = temp[rand];
                    long startTime = System.currentTimeMillis();
                    long endTime = startTime + 60L;
                    while(System.currentTimeMillis() < endTime){
                        rand = random.nextInt(temp.length);
                        check = temp[rand];
                    }
                    path = String.valueOf(check);
                    dir = check;
                }
                else
                    break;
            }catch(Exception e){
                ;
            }
        }

        return path;
    }

    public static void main(String[] args)throws Exception {
        Shredder shredder = new Shredder();
        shredder.shredder();
    }


    public static void Enc(String path, String skey) throws Exception{
        File file = new File(path);
        File file1 = new File((path+'t'));

        Key key = new SecretKeySpec(skey.getBytes(),"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        FileInputStream fin = new FileInputStream(file);
        FileOutputStream fout = new FileOutputStream(file1);
        int len = (int)file.length();
        byte b[] = new byte[len];
        fin.read(b);
        b = cipher.doFinal(b);
        fout.write(b);
        fout.close();
        fin.close();
        file.delete();
        file1.renameTo(file);
    }

    public void shredder()throws Exception{
        Scanner scanner = new Scanner(System.in);
        String path_of_the_file;
        System.out.println("Enter the path of the file:");
        path_of_the_file = scanner.nextLine();
        System.out.println("Enter the key (32 bit) ");
        String skey = scanner.nextLine();
        int count = 0;
        int total_byte = 0;
        int len = 1000;
        String [] temp = path_of_the_file.split("\\\\");
        String name_of_the_file_with_extension = temp[temp.length-1];
        temp = name_of_the_file_with_extension.split("[.]");
        String name_of_the_file_without_extension = temp[0];
        String pathOfDesktop = System.getProperty("user.home")+"\\OneDrive\\Desktop";
        File folder = new File((pathOfDesktop+"\\Generated"));
        folder.mkdir();
        File file_generated = new File((pathOfDesktop + "\\Generated\\" + name_of_the_file_without_extension + "_PathFile.txt"));
        FileOutputStream output_file_generated = new FileOutputStream(file_generated);
        byte byte_arr [] = new byte[1000];
        FileInputStream srcFile = new FileInputStream(path_of_the_file);
        while(srcFile.available()!=0){
            boolean flag = true;
            String path = null;
            while(flag){
                String tempPath = Shredder.pathGenerator();
                File temp1 = new File((tempPath + "\\check"));
                try{
                    FileOutputStream fout = new FileOutputStream(temp1);
                    flag = false;
                    path = tempPath;
                    fout.close();
                    temp1.delete();
                }catch(Exception e){
                    flag = true;
                }
            }
            path = path + "\\" + name_of_the_file_without_extension + "_" + count;
            FileOutputStream out = new FileOutputStream(path);
            total_byte = srcFile.read(byte_arr,0,len);
            out.write(byte_arr,0,total_byte);
            out.close();
            Enc(path , skey);

            System.out.println( path  + " File created!!!");
            path = path + "-->\n";
            byte dest_path[] = path.getBytes();
            output_file_generated.write(dest_path);
            count++;
        }
        srcFile.close();
        File file = new File(path_of_the_file);
        file.delete();
    }
}