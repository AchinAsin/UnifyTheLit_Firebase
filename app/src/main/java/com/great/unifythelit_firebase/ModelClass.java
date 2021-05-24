package com.great.unifythelit_firebase;

public class ModelClass {

    private String FileName;
    private String MetaData;
    private String PictureLink;

    public ModelClass() {

    }

    public ModelClass(String fileName, String metaData, String pictureLink) {
        FileName = fileName;
        MetaData = metaData;
        PictureLink = pictureLink;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getMetaData() {
        return MetaData;
    }

    public void setMetaData(String metaData) {
        MetaData = metaData;
    }

    public String getPictureLink() {
        return PictureLink;
    }

    public void setPictureLink(String pictureLink) {
        PictureLink = pictureLink;
    }

}
