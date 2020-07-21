package project.dto;

import java.util.Objects;

public class ActorCreateDTO {
    private String name;
    private String surName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorCreateDTO that = (ActorCreateDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(surName, that.surName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surName);
    }
}
