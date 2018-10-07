package main.java.com.example.wallpaperdecode;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ReadClassMain {
    public static String[] sss=new String[]{"renwu","fengjing","zhiwu","dongwu","yundong","meishi","menghuan","katongdongman","yishu","meinv","kuche","keji","qita"};

    public static void main(String[] args) {
        //最热 默认排序
        //最新 则把后面放前面

        String saveConfigPath = System.getProperty("user.dir") + "\\app\\src\\Main\\res\\xml";
        File[]  files2=new File(saveConfigPath).listFiles();
        Arrays.sort(files2, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });

        List<List<String>> classStrings=new ArrayList<>();
        for(String s:sss){
            List<String> fsgs=new ArrayList<>();
            for(File f:files2){
                if(f.getName().contains(s+"_wallpaper")){
                    List<String> dfsfsfsd=  getWallpapersFromFile(f.getAbsolutePath());
                    fsgs.addAll(dfsfsfsd);
                }
            }
            classStrings.add(fsgs);
        }





        int totalCount=0;
        for(List<String> ssss:classStrings){
            totalCount+=ssss.size();
        }


        boolean isDefault=true;//默认排序

        int size=20;
        int pageSize= (int) Math.ceil((float)totalCount/(float) size);


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

        String savexml = System.getProperty("user.dir") + "\\app\\src\\Main\\res\\xml\\"+(isDefault?"config.xml":"config_new_wallpaper.xml");
        saveStringsToFile(savexml,saveConfig);


        for(int i=0;i<pageSize;i++){
            //生成页
            List<String> finalStrings=new ArrayList<>();
            finalStrings.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<Wallpapers xmlns:android=\"http://schemas.android.com/apk/res/android\">");
            int needSize=size;
            if(i==pageSize-1){
                needSize=totalCount%size;
            }
            while (needSize>0){
                //遍历classStrings 获取数目

                for(List<String> ssfsdfsdfdf:classStrings){

                    if(ssfsdfsdfdf.size()>0){
                        finalStrings.add(ssfsdfsdfdf.get(isDefault?0:ssfsdfsdfdf.size()-1));
                        ssfsdfsdfdf.remove(isDefault?0:ssfsdfsdfdf.size()-1);
                        needSize-=1;
                        if(needSize<1)
                        break;
                    }
                }

            }
            finalStrings.add("</Wallpapers>");
            //save
            System.out.print("save-"+i+"\n");
            String saveConfigWallpaperPath = System.getProperty("user.dir") + "\\app\\src\\Main\\res\\xml\\"+(isDefault?"wallpaper":"new_wallpaper")+(i+1)+".xml";
            saveStringsToFile(saveConfigWallpaperPath,finalStrings);

        }

        System.out.print("end");


    }
    public static List<String> getWallpapersFromFile(String file_path) {
        List<String> mWallpaperBeans = null;
        String wallpaperBean = null;
        InputStream inputStream=null;
        try {
            inputStream = new FileInputStream(file_path);
            XmlPullParser parser = new KXmlParser();
            parser.setInput(inputStream, "UTF-8");
            beginDocument(parser, "Wallpapers");
            int eventType = parser.getEventType();
            String parserName;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        parserName=parser.getName();
                        if (parserName.equals("Wallpapers")) {
                            if(mWallpaperBeans==null)
                                mWallpaperBeans=new ArrayList<>();
                        }else  if (parserName.equals("Wallpaper")||parserName.equals("Walpaper")) {
                            eventType = parser.next();
                            String text=parser.getText();
                            wallpaperBean="<Wallpaper>"+text+"</Wallpaper>";
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        parserName=parser.getName();
                        if (parserName.equals("Wallpaper")||parserName.equals("Walpaper")) {
                            mWallpaperBeans.add(wallpaperBean);
                        }
                        break;
                }
                eventType = parser.next();
            }
            return mWallpaperBeans;
        } catch (XmlPullParserException e) {
            return mWallpaperBeans;
        } catch (IOException e) {
            return mWallpaperBeans;
        } catch (RuntimeException e) {
            return mWallpaperBeans;
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                    inputStream=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    static final void beginDocument(XmlPullParser parser, String firstElementName)
            throws XmlPullParserException, IOException {
        int type;
        while ((type = parser.next()) != XmlPullParser.START_TAG
                && type != XmlPullParser.END_DOCUMENT) {
        }
        if (type != XmlPullParser.START_TAG) {
            throw new XmlPullParserException("No start tag found");
        }
        if (!parser.getName().equals(firstElementName)) {
            throw new XmlPullParserException("Unexpected start tag: found " + parser.getName() +
                    ", expected " + firstElementName);
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
