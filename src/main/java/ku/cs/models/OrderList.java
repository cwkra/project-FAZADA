package ku.cs.models;

import java.util.ArrayList;

public class OrderList {

    private ArrayList<Order> orders;
    public OrderList() {
        orders = new ArrayList<>();
    }
    public void addOrder(Order order) {
        orders.add(order);
    }
    public int count() {
        return orders.size();
    }
    public Order searchOrderById(String id) {
        for (Order order: orders) {
            if (order.checkId(id)) {
                return order;
            }
        }
        return null;
    }

    public void removeOrder(Order order) {
        Order removeOrder = searchOrderById(order.getId());
        orders.remove(removeOrder);
    }

    public void editOrder(Order order) {
        Order editOrder = searchOrderById(order.getId());
        editOrder.setStatus(order.getStatus());
        editOrder.setTrackingCode(order.getTrackingCode());
    }

    public ArrayList<Order> getAllOrders() {
        return orders;
    }

    public ArrayList<Order> getMyOrders(String username) {
        ArrayList<Order> myOrders = new ArrayList<>();
        for (Order order: orders) {
            if (order.checkUsername(username)) {
                myOrders.add(order);
            }
        }
        return myOrders;
    }

    public ArrayList<Order> getShopOrders(String shopName) {
        ArrayList<Order> shopOrders = new ArrayList<>();
        for (Order order: orders) {
            if (order.checkShop(shopName)) {
                shopOrders.add(order);
            }
        }
        return shopOrders;
    }

    public ArrayList<Order> getShopOrderList(String shopName) {
        ArrayList<Order> shopOrders = getShopOrders(shopName);
        ArrayList<Order> shopOrdersList = new ArrayList<>();
        for (Order order: shopOrders) {
            if (order.isPreparingToShip()) {
                shopOrdersList.add(order);
            }
        }
        return shopOrdersList;
    }

    public ArrayList<Order> getShopDeliveryList(String shopName) {
        ArrayList<Order> shopOrders = getShopOrders(shopName);
        ArrayList<Order> shopDeliveryList = new ArrayList<>();
        for (Order order: shopOrders) {
            if (order.isInDelivery() || order.isDelivered() || order.isCompleted()) {
                shopDeliveryList.add(order);
            }
        }
        return shopDeliveryList;
    }

    public String toCSV() {
        String result = "";
        for (Order order: this.orders) {
            result += order.toCSV() + "\n";
        }
        return result;
    }

    public ArrayList<Order> getMyOrdersToReceived(String username) {
        ArrayList<Order> myOrders = getMyOrders(username);
        ArrayList<Order> myOrdersToReceived = new ArrayList<>();
        for (Order order: myOrders) {
            if (order.isPreparingToShip() || order.isInDelivery() || order.isDelivered()) {
                myOrdersToReceived.add(order);
            }
        }
        return myOrdersToReceived;
    }

    public ArrayList<Order> getMyCompleteOrders(String username) {
        ArrayList<Order> myOrders = getMyOrders(username);
        ArrayList<Order> myCompleteOrders = new ArrayList<>();
        for (Order order: myOrders) {
            if (order.isCompleted()){
                myCompleteOrders.add(order);
            }
        }
        return myCompleteOrders;
    }
}
