package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.annotations.BelongsTo;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

@Table(name = "employees")
public class Employee {
    @Column(name = "id", unique = true, generatedValue = true)
    private int id;
    @Column(name = "company_id")
    private int companyId;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "job_category")
    private JobCategory jobCategory;

    @BelongsTo(foreignKey = "id", key = "company_id")
    private Company company;

    public Employee() {
    }

    public Employee(Company company, String name, int age, JobCategory jobCategory) {
        this.companyId = company.getId();
        this.name = name;
        this.age = age;
        this.jobCategory = jobCategory;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public JobCategory getJobCategory() {
        return jobCategory;
    }

    public int getCompanyId() {
        return companyId;
    }

    public Company getCompany() {
        return company;
    }
}
