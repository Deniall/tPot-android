package com.cpssd.organizr.pojos.Settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Intolerances {

    @SerializedName("dairy")
    @Expose
    private Boolean dairy;
    @SerializedName("egg")
    @Expose
    private Boolean egg;
    @SerializedName("gluten")
    @Expose
    private Boolean gluten;
    @SerializedName("peanut")
    @Expose
    private Boolean peanut;
    @SerializedName("sesame")
    @Expose
    private Boolean sesame;
    @SerializedName("seafood")
    @Expose
    private Boolean seafood;
    @SerializedName("shellfish")
    @Expose
    private Boolean shellfish;
    @SerializedName("soy")
    @Expose
    private Boolean soy;
    @SerializedName("sulfite")
    @Expose
    private Boolean sulfite;
    @SerializedName("treeNut")
    @Expose
    private Boolean treeNut;
    @SerializedName("wheat")
    @Expose
    private Boolean wheat;

    public Boolean getDairy() {
        return dairy;
    }

    public void setDairy(Boolean dairy) {
        this.dairy = dairy;
    }

    public Boolean getEgg() {
        return egg;
    }

    public void setEgg(Boolean egg) {
        this.egg = egg;
    }

    public Boolean getGluten() {
        return gluten;
    }

    public void setGluten(Boolean gluten) {
        this.gluten = gluten;
    }

    public Boolean getPeanut() {
        return peanut;
    }

    public void setPeanut(Boolean peanut) {
        this.peanut = peanut;
    }

    public Boolean getSesame() {
        return sesame;
    }

    public void setSesame(Boolean sesame) {
        this.sesame = sesame;
    }

    public Boolean getSeafood() {
        return seafood;
    }

    public void setSeafood(Boolean seafood) {
        this.seafood = seafood;
    }

    public Boolean getShellfish() {
        return shellfish;
    }

    public void setShellfish(Boolean shellfish) {
        this.shellfish = shellfish;
    }

    public Boolean getSoy() {
        return soy;
    }

    public void setSoy(Boolean soy) {
        this.soy = soy;
    }

    public Boolean getSulfite() {
        return sulfite;
    }

    public void setSulfite(Boolean sulfite) {
        this.sulfite = sulfite;
    }

    public Boolean getTreeNut() {
        return treeNut;
    }

    public void setTreeNut(Boolean treeNut) {
        this.treeNut = treeNut;
    }

    public Boolean getWheat() {
        return wheat;
    }

    public void setWheat(Boolean wheat) {
        this.wheat = wheat;
    }

}

