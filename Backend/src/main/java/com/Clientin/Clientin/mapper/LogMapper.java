package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.dto.LogDTO;
import com.Clientin.Clientin.entity.Log;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LogMapper {

    public LogDTO toDTO(Log entity) {
        if (entity == null) {
            return null;
        }
        
        LogDTO dto = new LogDTO();
        dto.setId(entity.getId());
        dto.setLevel(entity.getLevel());
        dto.setAction(entity.getAction());
        dto.setEntity(entity.getEntity());
        dto.setEntityId(entity.getEntityId());
        dto.setUserId(entity.getUserId());
        dto.setUserEmail(entity.getUserEmail());
        dto.setUserRole(entity.getUserRole());
        dto.setMessage(entity.getMessage());
        dto.setDetails(entity.getDetails());
        dto.setRequestId(entity.getRequestId());
        dto.setSessionId(entity.getSessionId());
        dto.setIpAddress(entity.getIpAddress());
        dto.setUserAgent(entity.getUserAgent());
        dto.setEndpoint(entity.getEndpoint());
        dto.setHttpMethod(entity.getHttpMethod());
        dto.setResponseStatus(entity.getResponseStatus());
        dto.setExecutionTimeMs(entity.getExecutionTimeMs());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }

    public Log toEntity(LogDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Log entity = new Log();
        entity.setId(dto.getId());
        entity.setLevel(dto.getLevel());
        entity.setAction(dto.getAction());
        entity.setEntity(dto.getEntity());
        entity.setEntityId(dto.getEntityId());
        entity.setUserId(dto.getUserId());
        entity.setUserEmail(dto.getUserEmail());
        entity.setUserRole(dto.getUserRole());
        entity.setMessage(dto.getMessage());
        entity.setDetails(dto.getDetails());
        entity.setRequestId(dto.getRequestId());
        entity.setSessionId(dto.getSessionId());
        entity.setIpAddress(dto.getIpAddress());
        entity.setUserAgent(dto.getUserAgent());
        entity.setEndpoint(dto.getEndpoint());
        entity.setHttpMethod(dto.getHttpMethod());
        entity.setResponseStatus(dto.getResponseStatus());
        entity.setExecutionTimeMs(dto.getExecutionTimeMs());
        
        return entity;
    }

    public List<LogDTO> toDTOList(List<Log> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Log> toEntityList(List<LogDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
