package sloshbot.raspberrypi_api.util;

import sloshbot.raspberrypi_api.models.HibernatePOJO;

public class HibernatePojoUtil {
    public static HibernatePOJO ValidateCreatedModified(HibernatePOJO HP1, HibernatePOJO HP2) throws Exception {
        HibernatePOJO result = HP1;
        if(HP1.getCreatedDate() == null || HP1.getCreatedBy() == null){
            if(HP2.getCreatedDate() == null || HP2.getCreatedBy() == null){
                throw new Exception("There is not a valid created by and created date");
            }
            else{
                return HP2;
            }
        }
        if(HP1.getCreatedDate().getTime() > HP2.getCreatedDate().getTime()){
            result = HP2;
            result.setModifiedBy(HP1.getCreatedBy());
            result.setModifiedDate(HP1.getCreatedDate());
        }else{
            result.setModifiedBy(HP2.getCreatedBy());
            result.setModifiedDate(HP2.getCreatedDate());
        }
        return result;
    }
}
