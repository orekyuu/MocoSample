package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.HasMany;
import net.orekyuu.moco.core.annotations.Table;

import java.util.ArrayList;
import java.util.List;

@Table(name = "companies")
public class Company {
    @Column(name = "id", unique = true, generatedValue = true)
    private int id;
    @Column(name = "name", unique = true)
    private String name;
    @HasMany(foreignKey = "company_id", key = "id")
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
