package task.olympia.operations;

import task.constructs.database.Table;
import task.olympia.OlympiaApplication;
import task.olympia.models.IocCode;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Selector {
    private OlympiaApplication app;

    public Selector(OlympiaApplication app) {
        this.app = app;
    }

    public IocCode getIocCode(String countryName) {
        Table<IocCode> table = this.app.getDatabase().getTable(IocCode.class);
        return table.getRows().stream()
                .filter(row -> row.getCountryName().equals(countryName))
                .collect(Collectors.toList())
                .get(0);
    }

    public List<IocCode> getIocCodes() {
        Table<IocCode> table = this.app.getDatabase().getTable(IocCode.class);
        Comparator<IocCode> byDeterminationYear = Comparator.comparingInt(IocCode::getYear);

        return table.getRows().stream()
                .sorted(byDeterminationYear)
                .collect(Collectors.toList());
    }
}
