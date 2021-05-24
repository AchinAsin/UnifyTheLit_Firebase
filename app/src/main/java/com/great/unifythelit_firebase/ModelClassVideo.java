package com.great.unifythelit_firebase;

public class ModelClassVideo {
    private String FileName;
    private String MetaData;
    private String VideoLink;


    public ModelClassVideo()
    {

    }

    public ModelClassVideo(String fileName, String metaData, String videoLink) {
        FileName = fileName;
        MetaData = metaData;
        VideoLink = videoLink;
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

    public String getVideoLink() {
        return VideoLink;
    }

    public void setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }
}
