package task.olympia.serializers.types;

import task.interfaces.IListSerializer;
import task.olympia.models.Athlete;
import task.olympia.models.AthleteSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SummaryAthletesSerializer implements IListSerializer<AthleteSummary, String> {
    public SummaryAthletesSerializer() {
    }

    @Override
    public List<String> serialize(List<AthleteSummary> list) {
        return list == null ? new ArrayList<>() : list.stream().map(this::serializeOne).collect(Collectors.toList());
    }

    public String serializeOne(AthleteSummary item) {
        return String.format("%04d %s %s %d",
                item.getId(),
                item.getFirstName(),
                item.getLastName(),
                item.getMedalCount()
        );
    }
}
