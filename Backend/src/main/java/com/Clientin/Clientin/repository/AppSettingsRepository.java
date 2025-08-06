package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.AppSettings;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
import java.util.List;

    @Repository
    public interface AppSettingsRepository 
        extends JpaRepository<AppSettings, String>, JpaSpecificationExecutor<AppSettings> {

        // Automatic query methods
        List<AppSettings> findBySettingKey(String settingKey);
        List<AppSettings> findBySettingValue(String settingValue);
        List<AppSettings> findBySettingType(AppSettings.SettingType settingType);
        
        // Custom JPQL queries
        @Query("SELECT e FROM AppSettings e WHERE e.settingKey = :value")
        List<AppSettings> findBySettingKeyCustom(String value);

        @Query("SELECT e FROM AppSettings e JOIN FETCH e.settingKey")
        List<AppSettings> findAllWithSettingKey();
        
        // Relationship queries
    
    }