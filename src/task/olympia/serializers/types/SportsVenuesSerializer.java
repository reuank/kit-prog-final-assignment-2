package task.olympia.serializers.types;

import task.interfaces.IListSerializer;
import task.olympia.models.IocCode;
import task.olympia.models.SportsVenue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SportsVenuesSerializer implements IListSerializer<SportsVenue, String> {
    public SportsVenuesSerializer() {
    }

    @Override
    public List<String> serialize(List<SportsVenue> list) {
        return IntStream.range(0, list.size())
                .boxed()
                .map(pos -> serializeOne(pos + 1, list.get(pos)))
                .collect(Collectors.toList());
    }

    public String serializeOne(int position, SportsVenue item) {
        return String.format("(%d %03d %s %d)",
                position,
                item.getId(),
                item.getLocation(),
                item.getSeatcount()
        );
    }
}