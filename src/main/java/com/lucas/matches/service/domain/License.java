package com.lucas.matches.service.domain;


import java.util.Objects;

public class License {
    private LicenseType licenseType;
    private String id;

    public License() {
    }

    public License(LicenseType licenseType, String id) {
        this.licenseType = licenseType;
        this.id = id;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        License license = (License) o;
        return Objects.equals(licenseType, license.licenseType) && Objects.equals(id, license.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licenseType, id);
    }

    @Override
    public String toString() {
        return "License{" +
                "licenseType=" + licenseType +
                ", id='" + id + '\'' +
                '}';
    }
}
