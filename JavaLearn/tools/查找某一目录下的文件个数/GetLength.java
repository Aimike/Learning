/**
 * 返回文件夹下所有的文件个数
 */

import java.io.File;
import java.util.ArrayList;

public class GetLength {
    /**
     *
     * @param path 文件夹路径
     * @return 文件夹内文件的个数
     */
    public static int getLen(String path){
        File[] files = new File(path).listFiles();
        int count = 0;
        for(File file : files){
            if(file.isFile()){
                count++;
            }else{
                count += getLen(file.getAbsolutePath());
            }
        }
        return count;
    }

    /**
     *
     * @param path 文件夹路径
     * @param reg 文件名格式或者后缀
     * @return 文件夹内文件的个数
     */
    public static int getLen(String path,ArrayList<String> reg){
        File[] files = new File(path).listFiles();
        int count = 0;
        for(File file : files){
            if(file.isFile()){
                for (String type : reg){

                    if(file.getName().contains(type)){
                        count = count+1;
                    }
                }
            }else if (file.isDirectory()){
                count += getLen(file.getAbsolutePath(),reg);
            }
        }
        return count;
    }

    public static void main(String[] args) {
//        System.out.println(getLen("D:\\BaiduNetdiskDownload\\黑马\\黑马Java\\7.十次方\\后端\\十次方微服务day07",".avi")+
//                getLen("D:\\BaiduNetdiskDownload\\黑马\\黑马Java\\7.十次方\\后端\\十次方微服务day07",".mp4"));
        String path1 = "D:\\BaiduNetdiskDownload\\黑马\\黑马Java\\1.基础班";
        String path2 ="D:\\BaiduNetdiskDownload\\黑马\\黑马Java\\2.javaweb";
        String path3 = "D:\\BaiduNetdiskDownload\\黑马\\黑马Java\\3.黑马旅游网";
        String path4 = "D:\\BaiduNetdiskDownload\\黑马\\黑马Java\\4.框架";
        String path5 ="D:\\BaiduNetdiskDownload\\黑马\\黑马Java\\5.项目实战";
        String path6 = "D:\\BaiduNetdiskDownload\\黑马\\黑马Java\\6.乐优商城项目";
        String path7 = "D:\\BaiduNetdiskDownload\\黑马\\黑马Java\\7.十次方";
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add(".mp4");
        typeList.add(".avi");
        typeList.add(".rmvb");
        typeList.add(".wmv");
        System.out.println(path1.split("\\\\")[4]+":"+getLen(path1,typeList));
        System.out.println(path2.split("\\\\")[4]+":"+getLen(path2,typeList));
        System.out.println(path3.split("\\\\")[4]+":"+getLen(path3,typeList));
        System.out.println(path4.split("\\\\")[4]+":"+getLen(path4,typeList));
        System.out.println(path5.split("\\\\")[4]+":"+getLen(path5,typeList));
        System.out.println(path6.split("\\\\")[4]+":"+getLen(path6,typeList));
        System.out.println(path7.split("\\\\")[4]+":"+getLen(path7,typeList));
    }
}
