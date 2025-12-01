package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.competencia.CreateCompetenciaDTO;
import com.poo.proyecto.dto.competencia.UpdateCompetenciaDTO;
import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.mapper.common.MoneyMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { MoneyMapper.class, TorneoMapper.class })
//indica que la interfaz es un mapper,
// y que MapStruct debe generar automáticamente el código para convertir objetos.
public interface CompetenciaMapper {

    Competencia toEntity(CreateCompetenciaDTO dto);

    @AfterMapping //se usa para ejecutar lógica adicional después de que MapStruct termine el mapeo automático,
    // como completar campos calculados.
    default void linkTorneo(CreateCompetenciaDTO dto, @MappingTarget Competencia entity) {
        if (dto.getTorneoId() != null) {
            var torneo = new com.poo.proyecto.entity.Torneo();
            try {
                java.lang.reflect.Field idField = torneo.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(torneo, dto.getTorneoId());
            } catch (Exception e) {
                throw new RuntimeException("Error setting Torneo ID manually", e);
            }
            entity.setTorneo(torneo);
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    //define reglas generales del mapeo, por ejemplo ignorar campos nulos cuando actualizamos una entidad.
    void updateEntity(UpdateCompetenciaDTO dto, @MappingTarget Competencia entity);

    @Mapping(source = "torneo", target = "torneo")
    //especifica cómo se mapea cada atributo:
        // si cambia de nombre, si se ignora o si necesita una regla especial.
    CompetenciaResponseDTO toResponse(Competencia entity);
}
