package com.mlsc.waste.wastecircle.model;

public class SearchCondition {
    private String entId;
    private String entName;
    private String codeWaste;
    private String area;
    private String isable;
    private String iscare;
    private String isdistance;
    private boolean favorited; //是否收藏
    private String posx;
    private String posy;
    private double longitudeUpper;
    private double longitudeLower;
    private double latitudeUpper;
    private double latitudeLower;
    private String loginUserEntId;
    private String releaseStatus;
    private double infinity;
    private String cantonCode;
    private int startRowIndex;
    private int rows;

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getCodeWaste() {
        return codeWaste;
    }

    public void setCodeWaste(String codeWaste) {
        this.codeWaste = codeWaste;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIsable() {
        return isable;
    }

    public void setIsable(String isable) {
        this.isable = isable;
    }

    public String getIscare() {
        return iscare;
    }

    public void setIscare(String iscare) {
        this.iscare = iscare;
    }

    public String getIsdistance() {
        return isdistance;
    }

    public void setIsdistance(String isdistance) {
        this.isdistance = isdistance;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public String getPosx() {
        return posx;
    }

    public void setPosx(String posx) {
        this.posx = posx;
    }

    public String getPosy() {
        return posy;
    }

    public void setPosy(String posy) {
        this.posy = posy;
    }

    public double getLongitudeUpper() {
        return longitudeUpper;
    }

    public void setLongitudeUpper(double longitudeUpper) {
        this.longitudeUpper = longitudeUpper;
    }

    public double getLongitudeLower() {
        return longitudeLower;
    }

    public String getLoginUserEntId() {
        return loginUserEntId;
    }

    public void setLoginUserEntId(String loginUserEntId) {
        this.loginUserEntId = loginUserEntId;
    }

    public void setLongitudeLower(double longitudeLower) {
        this.longitudeLower = longitudeLower;
    }

    public double getLatitudeUpper() {
        return latitudeUpper;
    }

    public void setLatitudeUpper(double latitudeUpper) {
        this.latitudeUpper = latitudeUpper;
    }

    public double getLatitudeLower() {
        return latitudeLower;
    }

    public void setLatitudeLower(double latitudeLower) {
        this.latitudeLower = latitudeLower;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public double getInfinity() {
        return infinity;
    }

    public void setInfinity(double infinity) {
        this.infinity = infinity;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
