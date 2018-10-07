package main.java.com.example.wallpaperdecode;

import com.drew.imaging.jpeg.JpegSegmentType;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.jpeg.JpegReader;
import com.drew.tools.FileUtil;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class WallpaerUtils {
    public static String[] sss=new String[]{"renwu","fengjing","zhiwu","dongwu","yundong","meishi","menghuan","katongdongman","yishu","meinv","kuche","keji","qita"};
    public static void main(String[] args) {

        System.out.print("start"+"\n");
        for(String prefix:sss){
            doMain(prefix);
            System.out.print("doing="+prefix+"\n");
        }
        System.out.print("end"+"\n");

    }

    public static void doMain(String prefix){

        String proPath =System.getProperty("user.dir") + "\\app\\src\\Main\\assets\\wallpaper\\"+prefix;

        String saveConfigPath = System.getProperty("user.dir") + "\\app\\src\\Main\\res\\xml\\"+prefix+"_config.xml";

        File wallpapersRoot=new File(proPath);
        File[] wallpapersTemp=wallpapersRoot.listFiles();

        //按照创建日期排序
        Arrays.sort(wallpapersTemp,new Comparator< File>(){
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }
            public boolean equals(Object obj) {
                return true;
            }

        });


        int size=20;

        List<File> fffff=new ArrayList<>();
        for(File f:wallpapersTemp){
            if(!f.getAbsolutePath().endsWith(".png.s.jpg")){
                fffff.add(f);
            }
        }

        File[] wallpapers=new File[fffff.size()];
        fffff.toArray(wallpapers);

        int pageSize= (int) Math.ceil((float)wallpapers.length/20f);

        //save page size
        List<String>saveConfig=new ArrayList<>();
        String temp="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<Config xmlns:android=\"http://schemas.android.com/apk/res/android\">\n" +
                "    <WallpaperConfig>\n" +
                "        <version>2</version>\n" +
                "        <prefix>wallpaper</prefix>\n" +
                "        <page>"+pageSize+"</page>\n" +
                "    </WallpaperConfig>\n" +
                "</Config>";
        saveConfig.add(temp);


        saveStringsToFile(saveConfigPath,saveConfig);
        for(int i=0;i<pageSize;i++){
            List<String> saveFiles=new ArrayList<>();
            saveFiles.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<Wallpapers xmlns:android=\"http://schemas.android.com/apk/res/android\">");
            int startIndex=i*size;
            int endIndex=startIndex+size;
            for(;startIndex<endIndex;startIndex++){
                if(startIndex<wallpapers.length){
                    File img=wallpapers[startIndex];
                    //获取缩略图
                    float radio=1f;
                    try {
                        Metadata metadata = new Metadata();
                        new JpegReader().extract(FileUtil.readBytes(img), metadata, JpegSegmentType.SOF0);

                        JpegDirectory directory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
                        radio=directory.getImageHeight()/(float)directory.getImageWidth();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (MetadataException e) {
                        e.printStackTrace();
                    }
                    int thumw=540;
                    int thumh= (int) (540*radio);


                    //原图
                    String fileName=System.currentTimeMillis()+"";
                    String oldFileName=img.getName();
                    oldFileName=oldFileName.substring(0,oldFileName.indexOf("."));
                    String strXmlUtl="<Wallpaper>"+"https://raw.githubusercontent.com/chongbo2013/Wallpaper/master/app/src/main/assets/wallpaper/"+prefix+"/"+fileName+".png.xs.jpg</Wallpaper>";

                    String strFileOrighin=new File(img.getParent(),fileName+".png").getAbsolutePath();
                    //缩略图
                    String strFiles=new File(img.getParent(),fileName+".png.s.jpg").getAbsolutePath();

                    String oldstrFiles=new File(img.getParent(),oldFileName+".png.s.jpg").getAbsolutePath();
                    try {
                        File suoluetu =new File(oldstrFiles);
                        if(suoluetu.exists())
                            suoluetu.delete();
                        Thumbnails.of(img).size(thumw,thumh).toFile(new File(strFiles));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //
                    img.renameTo(new File(strFileOrighin));
                    saveFiles.add(strXmlUtl);
                }
            }
            saveFiles.add("</Wallpapers>");
            //save to xml
            String saveConfigWallpaperPath = System.getProperty("user.dir") + "\\app\\src\\Main\\res\\xml\\"+prefix+"_wallpaper"+(i+1)+".xml";
            saveStringsToFile(saveConfigWallpaperPath,saveFiles);
            System.out.print("progress-"+i);
        }

    }


    public static void saveStringsToFile(final String strFilename,List<String> sourceStringRelults){

        File file=new File(strFilename);
        if(file.exists())
            file.delete();
        String strBuffer="";
        for(String str:sourceStringRelults){
            strBuffer+=str+"\n";
        }

        TextToFile(strFilename,strBuffer);

    }
    /**
     * 传入文件名以及字符串, 将字符串信息保存到文件中
     *
     * @param strFilename
     * @param strBuffer
     */
    public static void TextToFile(final String strFilename, final String strBuffer)
    {
        try
        {
            // 创建文件对象
            File fileText = new File(strFilename);

            //C:\Users\x002\Desktop\langetranslation\java\src\lang\values-en\strings.xml

            if(!fileText.getParentFile().exists()){
                fileText.getParentFile().mkdirs();
            }

            if(fileText.exists())
                fileText.delete();
            if(!fileText.exists()){
                fileText.createNewFile();
            }
            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(fileText);

            // 写文件
            fileWriter.write(strBuffer);
            // 关闭
            fileWriter.close();
        }
        catch (IOException e)
        {
            //
            e.printStackTrace();
        }
    }
}
