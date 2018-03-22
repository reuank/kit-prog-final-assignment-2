package task.olympia.serializers.types;

import task.interfaces.IListSerializer;
import task.olympia.models.SportDiscipline;

import java.util.List;
import java.util.stream.Collectors;

public class OlympicSportsSerializer implements IListSerializer<SportDiscipline, String> {
    @Override
    public List<String> serialize(List<SportDiscipline> list) {
        return list.stream().map(this::serializeOne).collect(Collectors.toList());
    }

    public String serializeOne(SportDiscipline item) {
        return String.format("%s %s",
                item.getSportType().getName(),
                item.getName()
        );
    }
}
