package task.olympia.serializers.types;

import task.interfaces.IListSerializer;
import task.olympia.models.OlympicSport;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OlympicSportsSerializer implements IListSerializer<OlympicSport, String> {
    @Override
    public List<String> serialize(List<OlympicSport> list) {
        return list == null ? new ArrayList<>() : list.stream().map(this::serializeOne).collect(Collectors.toList());
    }

    private String serializeOne(OlympicSport item) {
        return String.format("%s %s",
                item.getSportType(),
                item.getSportDiscipline()
        );
    }
}
