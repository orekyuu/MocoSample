package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.core.DataSourceType;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        setup();
        transaction(() -> {
            // amozon
            Companies.findByName("amozon", Companies.COMPANY_TO_CUSTOMERS).ifPresent(System.out::println);

            // Tokyoに住んでいる顧客
            List<Customer> tokyoCustomers = Customers.all().where(Customers.CITY.eq("Tokyo")).toList();
            System.out.println(tokyoCustomers);

            // noteを買ったOrder一覧
            List<Order> noteOrders = LineItems.all()
                    .where(LineItems.NAME.eq("note"))
                    .preload(LineItems.LINE_ITEM_TO_ORDER)
                    .stream()
                    .map(LineItem::getOrder)
                    .collect(Collectors.toList());
            System.out.println(noteOrders);

            // penを最もたくさん買っている人を探す
            Optional<Order> order = LineItems.all()
                    .preload(LineItems.LINE_ITEM_TO_ORDER)
                    .where(LineItems.NAME.eq("pen"))
                    .order(LineItems.AMOUNT.desc())
                    .limit(1)
                    .first().map(LineItem::getOrder);
            Optional<Customer> customerOpt = order.flatMap(o -> Orders.findById(o.getId(), Orders.ORDER_TO_CUSTOMER).map(Order::getCustomer));
            customerOpt.ifPresent(System.out::println);

            // eraserを削除
            LineItems.all().where(LineItems.NAME.eq("eraser")).delete();

            // penをpen -> Pen, amount -> 100にする
            LineItems.all().where(LineItems.NAME.eq("pen")).update(LineItems.NAME.set("Pen"), LineItems.AMOUNT.set(100));
        });
    }

    public static void setupModels() {
        Companies.create(new Company("amozon"));
        Companies.create(new Company("hogeten"));
        Company amozon = Companies.findOrNullByName("amozon");
        Company hogeten = Companies.findOrNullByName("hogeten");

        Customers.create(new Customer("Yamada", "Tokyo", amozon));
        Customers.create(new Customer("Sato", "Kyoto", amozon));
        Customers.create(new Customer("Suzuki", "Tokyo", hogeten));

        Customer yamada = Customers.all().where(Customers.NAME.eq("Yamada")).firstOrNull();
        Customer sato = Customers.all().where(Customers.NAME.eq("Sato")).firstOrNull();
        Customer suzuki = Customers.all().where(Customers.NAME.eq("Suzuki")).firstOrNull();

        Orders.create(new Order(yamada));
        Orders.create(new Order(yamada));
        Orders.create(new Order(sato));
        Orders.create(new Order(suzuki));

        List<Order> orders = Orders.all().order(Orders.ID.asc()).toList();
        Order order1 = orders.get(0);
        LineItems.create(new LineItem("pen", 10, order1));
        LineItems.create(new LineItem("eraser", 5, order1));

        Order order2 = orders.get(1);
        LineItems.create(new LineItem("pen", 12, order2));
        LineItems.create(new LineItem("note", 7, order2));

        Order order3 = orders.get(2);
        LineItems.create(new LineItem("note", 15, order3));

        Order order4 = orders.get(3);
        LineItems.create(new LineItem("pen", 3, order4));
        LineItems.create(new LineItem("eraser", 3, order4));
        LineItems.create(new LineItem("note", 10, order4));
    }

    public static void setupTables(Connection connection) throws SQLException {
        connection.prepareStatement("CREATE TABLE companies (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)").execute();
        connection.prepareStatement("CREATE TABLE customers (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, city TEXT NOT NULL, company_id INTEGER NOT NULL)").execute();
        connection.prepareStatement("CREATE TABLE orders (id INTEGER PRIMARY KEY AUTOINCREMENT, customer_id INTEGER NOT NULL)").execute();
        connection.prepareStatement("CREATE TABLE line_items (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, amount INTEGER NOT NULL, order_id INTEGER NOT NULL)").execute();
    }

    public static void setup() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite::memory:");
        dataSource.setDatabaseName("moco");
        ConnectionManager.initialize(dataSource, DataSourceType.SQLITE);
    }

    public interface TransactionTask {
        void run() throws SQLException;
    }

    public static void transaction(TransactionTask task) {
        Connection connection = ConnectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            setupTables(connection);
            setupModels();
            task.run();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
