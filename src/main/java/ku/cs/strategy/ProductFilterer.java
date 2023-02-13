package ku.cs.strategy;

import ku.cs.models.Product;

public interface ProductFilterer {
    boolean filter(Product product);
}
