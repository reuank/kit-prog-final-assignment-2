package task.olympia.models;

import task.constructs.database.Model;

public class SportType extends Model {
    private String name;

    public SportType(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        return ((SportType) other).getName().equals(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}