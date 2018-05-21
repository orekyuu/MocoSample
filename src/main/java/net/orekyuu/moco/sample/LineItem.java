package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.annotations.BelongsTo;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

@Table(name = "line_items")
public class LineItem {
    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private int amount;
    @Column(name = "order_id")
    private int orderId;
    @BelongsTo(key = "order_id", foreignKey = "id")
    private Order order;

    public LineItem() {
    }

    public LineItem(String name, int amount, Order order) {
        this.name = name;
        this.amount = amount;
        this.orderId = order.getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public Order getOrder() {
        return order;
    }

    public int getOrderId() {
        return orderId;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("LineItem{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", orderId=").append(orderId);
        sb.append(", order=").append(order);
        sb.append('}');
        return sb.toString();
    }
}
