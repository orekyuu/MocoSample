package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.annotations.BelongsTo;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.HasMany;
import net.orekyuu.moco.core.annotations.Table;

import java.util.ArrayList;
import java.util.List;

@Table(name = "customers")
public class Customer {
    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "city")
    private String city;
    @Column(name = "company_id")
    private int companyId;

    @BelongsTo(key = "company_id", foreignKey = "id")
    private Company company;
    @HasMany(key = "id", foreignKey = "customer_id")
    private List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, String city, Company company) {
        this.name = name;
        this.city = city;
        this.companyId = company.getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getCompanyId() {
        return companyId;
    }

    public Company getCompany() {
        return company;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", companyId=").append(companyId);
        sb.append(", company=").append(company);
        sb.append(", orders=").append(orders);
        sb.append('}');
        return sb.toString();
    }
}
