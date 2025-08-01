package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.AppSettings;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
import java.util.List;
    

    @Repository
    public interface AppSettingsRepository 
        extends JpaRepository<AppSettings, String> {

        // Automatic query methods
        List<AppSettings> findBySettingkey(String settingKey);
    List<AppSettings> findBySettingvalue(String settingValue);
    List<AppSettings> findBySettingtype(AppSettings.SettingType settingType);
        
        // Custom JPQL queries
        @Query("SELECT e FROM AppSettings e WHERE e.settingKey = :value")
        List<AppSettings> findBySettingkey(String value);

        @Query("SELECT e FROM AppSettings e JOIN FETCH e.settingKey")
        List<AppSettings> findAllWithSettingkey();
        
        // Relationship queries
    
    }