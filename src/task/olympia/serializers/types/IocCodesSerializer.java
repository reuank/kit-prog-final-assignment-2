package task.olympia.serializers.types;

import task.interfaces.IListSerializer;
import task.olympia.models.IocCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IocCodesSerializer implements IListSerializer<IocCode, String> {
    public IocCodesSerializer() {
    }

    @Override
    public List<String> serialize(List<IocCode> list) {
        return list == null ? new ArrayList<>() : list.stream().map(this::serializeOne).collect(Collectors.toList());
    }

    public String serializeOne(IocCode item) {
        return String.format("%d %03d %s %s",
                item.getYear(),
                item.getId(),
                item.getIocCode(),
                item.getCountryName()
        );
    }
}