package com.wasupstudio.converter;

import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.model.vo.LicenseVo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-30T23:52:58+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230413-0857, environment: Java 17.0.7 (Eclipse Adoptium)"
)
public class LicenseConverterImpl implements LicenseConverter {

    @Override
    public LicenseEntity map(LicenseVo licenseVo) {
        if ( licenseVo == null ) {
            return null;
        }

        LicenseEntity licenseEntity = new LicenseEntity();

        licenseEntity.setActivated( licenseVo.getActivated() );
        licenseEntity.setActivationDate( licenseVo.getActivationDate() );
        licenseEntity.setCustomerEmail( licenseVo.getCustomerEmail() );
        licenseEntity.setCustomerName( licenseVo.getCustomerName() );
        licenseEntity.setExpirationDate( licenseVo.getExpirationDate() );
        licenseEntity.setId( licenseVo.getId() );
        licenseEntity.setLicenseKey( licenseVo.getLicenseKey() );

        return licenseEntity;
    }

    @Override
    public List<LicenseEntity> map(List<LicenseVo> licenseVoList) {
        if ( licenseVoList == null ) {
            return null;
        }

        List<LicenseEntity> list = new ArrayList<LicenseEntity>( licenseVoList.size() );
        for ( LicenseVo licenseVo : licenseVoList ) {
            list.add( map( licenseVo ) );
        }

        return list;
    }
}
