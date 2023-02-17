package ku.cs.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductList {
    private ArrayList<Product> products;
    public ProductList() {
        this.products = new ArrayList<>();
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void removeProduct(Product product) {
        Product removeProduct = searchProductById(product.getId());
        products.remove(removeProduct);
    }
    public ArrayList<Product> getAllProducts() {
        return products;
    }
    public void editProduct(Product product) {
        Product editProduct = searchProductById(product.getId());
        editProduct.setName(product.getName());
        editProduct.setPrice(product.getPrice());
        editProduct.setStock(product.getStock());
        editProduct.setCategory(product.getCategory());
        editProduct.setDetails(product.getDetails());
        editProduct.setImagePath(product.getImagePath());
    }
    public ArrayList<Product> getMyProducts(String shopName) {
        ArrayList<Product> myProducts = new ArrayList<>();
        for (Product product: products) {
            if (product.isShopName(shopName)) {
                myProducts.add(product);
            }
        }
        return myProducts;
    }
    public int count() {
        return products.size();
    }
    public void sort(Comparator<Product> productComparator) {
        Collections.sort(this.products, productComparator);
    }
    public Product searchProductById(String id) {
        for (Product product: this.products) {
            if (product.isId(id)) {
                return product;
            }
        }
        return null;
    }
    public String toCSV() {
        String result = "";
        for (Product product: this.products) {
            result += product.toCSV() + "\n";
        }
        return result;
    }
}
