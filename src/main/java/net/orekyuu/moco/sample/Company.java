package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.HasMany;
import net.orekyuu.moco.core.annotations.Table;

import java.util.List;

@Table(name = "companies")
public class Company {

    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "name", unique = true)
    private String name;

    @HasMany(key = "id", foreignKey = "company_id")
    private List<Customer> customers;

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

    public List<Customer> getCustomers() {
        return customers;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("Company{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", customers=").append(customers);
        sb.append('}');
        return sb.toString();
    }
}
