package pl.dudios.shopmvn.admin.order.model;

public enum AdminOrderStatus {
    NEW("NEW"),
    PAID("PAID"),
    PROCESSING("PROCESSING"),
    WAITING_FOR_DELIVERY("WAITING_FOR_DELIVERY"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    REFUND("REFUND");

    private String value;

    AdminOrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
