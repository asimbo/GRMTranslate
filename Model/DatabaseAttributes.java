package com.asimbongeni.asie.grmtranslate.Model;

/**
 * Created by Asie on 21/01/2017.
 */
public class DatabaseAttributes {

    private String ndebTranslated;
    private int ndebSentenceCount;
    private int ndebTranslatedCount;
    private int id;
    private String ndebSentences;
    private String isihloko;
    private int volumeNumber;

    public int getServerSync() {
        return serverSync;
    }

    public void setServerSync(int serverSync) {
        this.serverSync = serverSync;
    }

    private int serverSync;

    public String getIsihloko() {
        return isihloko;
    }

    public void setIsihloko(String isihloko) {
        this.isihloko = isihloko;
    }

    public int getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(int volumeNumber) {
        this.volumeNumber = volumeNumber;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getNdebTranslated() {
        return ndebTranslated;
    }

    public void setNdebTranslated(String ndebTranslated) {
        this.ndebTranslated = ndebTranslated;
    }

    public String getNdebSentences() {
        return ndebSentences;
    }

    public void setNdebSentences(String ndebSentences) {
        this.ndebSentences = ndebSentences;
    }

    public int getNdebSentenceCount() {
        return ndebSentenceCount;
    }

    public void setNdebSentenceCount(int ndebSentenceCount) {
        this.ndebSentenceCount = ndebSentenceCount;
    }

    public int getNdebTranslatedCount() {
        return ndebTranslatedCount;
    }

    public void setNdebTranslatedCount(int ndebTranslatedCount) {
        this.ndebTranslatedCount = ndebTranslatedCount;
    }

    public void MyDatabaseAttributes(){}


}
