package task.olympia.serializers.types;

import task.interfaces.IListSerializer;
import task.olympia.models.SportsVenue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Serializes a sports venue list.
 */
public class SportsVenuesSerializer implements IListSerializer<SportsVenue, String> {
    /**
     * Instantiates a new SportsVenuesSerializer.
     */
    public SportsVenuesSerializer() {
    }

    @Override
    public List<String> serialize(List<SportsVenue> list) {
        return list == null ? new ArrayList<>() : IntStream.range(0, list.size())
                .boxed()
                .map(pos -> serializeOne(pos + 1, list.get(pos)))
                .collect(Collectors.toList());
    }

    private String serializeOne(int position, SportsVenue item) {
        return String.format("(%d %03d %s %d)",
                position,
                item.getId(),
                item.getLocation(),
                item.getSeatcount()
        );
    }
}