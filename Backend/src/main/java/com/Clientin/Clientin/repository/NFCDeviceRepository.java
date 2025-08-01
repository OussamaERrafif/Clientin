package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.NFCDevice;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
import java.util.List;
    

    @Repository
    public interface NFCDeviceRepository 
        extends JpaRepository<NFCDevice, String> {

        // Automatic query methods
        List<NFCDevice> findByDevicename(String deviceName);
    List<NFCDevice> findByDeviceserial(String deviceSerial);
    List<NFCDevice> findByLocation(String location);
        
        // Custom JPQL queries
        @Query("SELECT e FROM NFCDevice e WHERE e.deviceName = :value")
        List<NFCDevice> findByDevicename(String value);

        @Query("SELECT e FROM NFCDevice e JOIN FETCH e.deviceName")
        List<NFCDevice> findAllWithDevicename();
        
        // Relationship queries
    
    }