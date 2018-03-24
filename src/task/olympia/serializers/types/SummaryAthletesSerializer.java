package task.olympia.serializers.types;

import task.interfaces.IListSerializer;
import task.olympia.models.AthleteSummaryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User for serializing the athlete summary.
 */
public class SummaryAthletesSerializer implements IListSerializer<AthleteSummaryEntry, String> {
    /**
     * Instantiates a new SummaryAthletesSerializer
     */
    public SummaryAthletesSerializer() {
    }

    @Override
    public List<String> serialize(List<AthleteSummaryEntry> list) {
        return list == null ? new ArrayList<>() : list.stream().map(this::serializeOne).collect(Collectors.toList());
    }

    private String serializeOne(AthleteSummaryEntry item) {
        return String.format("%04d %s %s %d",
                item.getId(),
                item.getFirstName(),
                item.getLastName(),
                item.getMedalCount()
        );
    }
}
