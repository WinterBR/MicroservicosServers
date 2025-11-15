package br.com.winter.ClienteService.mapper;


import br.com.winter.ClienteService.dto.ClientRequestDTO;
import br.com.winter.ClienteService.dto.ClientResponseDTO;
import br.com.winter.ClienteService.entity.Client;

public class ClientMapper {

    public static Client toEntity(ClientRequestDTO dto) {
        if (dto == null) return null;
        return Client.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .cpf(dto.getCpf())
                .build();
    }

    public static ClientResponseDTO toDTO(Client entity) {
        if (entity == null) return null;
        return ClientResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .cpf(entity.getCpf())
                .build();
    }
}
