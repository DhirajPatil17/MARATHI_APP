package com.example.languageapp;

public class word {
    private String mMarathi;
    private String mEnglish;
    private int mAudioId;

    private int mImageId=mNoImageId;

   private final  static  int  mNoImageId=-1;
    public word(String marathi,String english,int AudioId)
    {
        mEnglish=english;
        mMarathi=marathi;
        mAudioId=AudioId;

    }
    public word(String marthi,String english,int imageId,int AudioId)
    {
        mEnglish=english;
        mMarathi=marthi;
        mImageId=imageId;
        mAudioId=AudioId;

    }
    public String getMarathi()
    {
        return mMarathi;
    }
    public String getEnglish()
    {
        return mEnglish;
    }
    public int getImageId()
    {
        return mImageId;
    }
    public boolean isImage()
    {
        return mImageId!=mNoImageId;
    }
    public int getAudio()
    {
        return mAudioId;
    }

}
