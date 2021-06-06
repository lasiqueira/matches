package com.lucas.matches.service.util;

import com.lucas.matches.service.exception.InvalidLicenseException;
import com.lucas.matches.service.domain.License;
import com.lucas.matches.service.domain.LicenseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LicenseParser {
    private final Logger logger;

    public LicenseParser() {
        this.logger = LoggerFactory.getLogger(LicenseParser.class);
    }

    public List<License> parse(List<String> licenseStringList){
        List<License> licenses = new ArrayList<>();
        licenseStringList.forEach(licenseString -> licenses.add(parse(licenseString)));
        return  licenses;
    }
    private License parse(String licenseString){
        String[] licenseArray = licenseString.split("-");

       if(licenseArray.length < 2){
           logger.error("Invalid license: " + licenseString);
           throw new InvalidLicenseException("Invalid license.");
       }
        License license = new License(LicenseType.value(licenseArray[0]), licenseArray[1]);
        if(license.getLicenseType() == null || license.getId() == null || license.getId().isEmpty()){
            logger.error("Invalid license: " + licenseString);
            throw new InvalidLicenseException("Invalid license.");
        }
        return license;
    }
}
