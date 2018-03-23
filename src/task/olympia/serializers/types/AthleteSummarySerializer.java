package task.olympia.serializers.types;

import task.interfaces.IListSerializer;
import task.olympia.models.Athlete;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AthleteSummarySerializer implements IListSerializer<Athlete, String> {
    public AthleteSummarySerializer() {
    }

    @Override
    public List<String> serialize(List<Athlete> list) {
        return list == null ? new ArrayList<>() : list.stream().map(this::serializeOne).collect(Collectors.toList());
    }

    public String serializeOne(Athlete item) {
        return String.format("%03d %s %s %d",
                item.getId(),
                item.getFirstname(),
                item.getLastname()
        );
    }
}
