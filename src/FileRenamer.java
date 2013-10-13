import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileRenamer {
    private File[] _listOfFiles;
    private File _sourceFolder;
    private File _destinationFolder;
    private final static String DROPBOX_FORMAT = "([0-9]){4}-([0-9]){2}-([0-9]){2} ([0-9]|.){8}.(jpeg|JPG|JPEG|jpg)";
    private final static String SAMSUNG_FORMAT = "([0-9]){8}_([0-9]){6}.(jpeg|JPG|JPEG|jpg)";

    public FileRenamer(){
        /*System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.getProperty("user.dir");*/
    }

    public void setSourceFolder(String folderName){
        _sourceFolder = new File(folderName);
    }
    public void setDestinationFolder(String folderName){
        _destinationFolder = new File(folderName);
    }

    public void renameFiles(){
        File[] listOfFiles = _sourceFolder.listFiles();

        assert listOfFiles != null;
        for (File file: listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                if(isSamsungFormat( fileName )) {
                    try{
                        File f = new File(_sourceFolder.getPath() + "\\" + file.getName());
                        fileName = samsungToDropboxFormat(fileName);
                        boolean renamed = f.renameTo(new File(_destinationFolder.getPath() + "\\" + fileName));
                        System.out.println("File renamed? " + renamed);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean isDropboxFormat(String filename){
        Pattern pattern = Pattern.compile(DROPBOX_FORMAT);
        Matcher matcher = pattern.matcher(filename);
        return matcher.matches();
    }
    public boolean isSamsungFormat(String filename){
        Pattern pattern = Pattern.compile(SAMSUNG_FORMAT);
        Matcher matcher = pattern.matcher(filename);
        return matcher.matches();
    }

    public String dropboxToSamsungFormat(String filename){
        String year = filename.substring(0,4);
        String month = filename.substring(5,7);
        String day = filename.substring(8, 10);
        String time = filename.substring(11, 19).replace(".", "");
        return year+month+day+"_"+time+getExtension(filename);
    }
    public String samsungToDropboxFormat(String filename){
        if(!isDropboxFormat(filename)){
            String year = filename.substring(0,4);
            String month = filename.substring(4,6);
            String day = filename.substring(6,8);
            String time = filename.substring(9, 11);
            time += "." + filename.substring(11, 13);
            time += "." + filename.substring(13, 15);
            return year+'-'+month+'-'+day+" "+time+getExtension(filename);
        }
        return filename;
    }

    public String getExtension(String filename){
        return filename.substring(filename.lastIndexOf('.'), filename.length());
    }
/*
    public String getFileName(String filename) {

        return null;
    }*/


}