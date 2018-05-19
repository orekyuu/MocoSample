package net.orekyuu.moco.sample;

import com.mysql.cj.jdbc.MysqlDataSource;
import net.orekyuu.moco.core.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        setup();
        transaction(() -> {
            Companies.create(new Company("Company A"));
            Companies.create(new Company("Company B"));

            Optional<Company> companyA = Companies.findByName("Company A");
            Company company = companyA.orElseThrow(RuntimeException::new);

            Employees.create(new Employee(company, "foo", 20, JobCategory.BUSINESS));
            Employees.create(new Employee(company, "bar", 23, JobCategory.BUSINESS));

            Employees.create(new Employee(company, "hoge", 25, JobCategory.ENGINEER));
            Employees.create(new Employee(company, "fuga", 21, JobCategory.ENGINEER));

            // Company Aの社員を引く
            List<Employee> employees = Employees.all().where(Employees.COMPANY_ID.eq(company.getId())).toList();

            // Companyと一緒に社員を引く
            Optional<Company> companyOptional = Companies.findById(company.getId(), Companies.COMPANY_TO_EMPLOYEES);
            employees = companyOptional.map(Company::getEmployees).orElseGet(ArrayList::new);

            // エンジニア職かつ年齢の昇順
            List<Employee> engineers = Employees.all().where(Employees.JOB_CATEGORY.eq(JobCategory.ENGINEER)).order(Employees.AGE.asc()).toList();

            // 2件ずつページングしながら取得
            String employeeNames = Employees.all().stream(2).map(Employee::getName).collect(Collectors.joining(", "));
            System.out.println(employeeNames);
        });
    }

    public static void setup() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/moco_sample");
        dataSource.setUser("moco");
        dataSource.setPassword("moco");
        ConnectionManager.initialize(dataSource);
    }

    public interface TransactionTask {
        void run() throws SQLException;
    }

    public static void transaction(TransactionTask task) {
        Connection connection = ConnectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            task.run();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
