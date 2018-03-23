package task.olympia.serializers.types;

import task.interfaces.IListSerializer;
import task.olympia.models.OlympicMedalTableEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OlympicMedalTableSerializer implements IListSerializer<OlympicMedalTableEntry, String>{
    @Override
    public List<String> serialize(List<OlympicMedalTableEntry> list) {
        return list == null ? new ArrayList<>() : IntStream.range(0, list.size())
                .boxed()
                .map(pos -> serializeOne(pos + 1, list.get(pos)))
                .collect(Collectors.toList());
    }

    public String serializeOne(int pos, OlympicMedalTableEntry item) {
        return String.format("(%d,%03d,%s,%s,%d,%d,%d,%d)",
                pos,
                item.getIocId(),
                item.getIocCode(),
                item.getCountryName(),
                item.getGold(),
                item.getSilver(),
                item.getBronze(),
                item.getTotalMedals()
        );
    }
}
