package dto;

import java.util.Map;

public class CountryWisePlayerCountDTO implements java.io.Serializable {
    Map<String, Integer> countryCountMap = null;

    public void setCountryCountMap(Map<String, Integer> countryCountMap) {
        this.countryCountMap = countryCountMap;
    }

    public Map<String, Integer> getCountryCountMap() {
        return countryCountMap;
    }
}
