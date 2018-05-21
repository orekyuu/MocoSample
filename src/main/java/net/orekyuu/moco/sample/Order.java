package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.annotations.BelongsTo;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.HasMany;
import net.orekyuu.moco.core.annotations.Table;

import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
public class Order {
    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "customer_id")
    private int customerId;
    @HasMany(key = "id", foreignKey = "order_id")
    private List<LineItem> lineItems = new ArrayList<>();
    @BelongsTo(key = "customer_id", foreignKey = "id")
    private Customer customer;

    public Order() {
    }

    public Order(Customer customer) {
        this.customerId = customer.getId();
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", lineItems=").append(lineItems);
        sb.append(", customer=").append(customer);
        sb.append('}');
        return sb.toString();
    }
}
