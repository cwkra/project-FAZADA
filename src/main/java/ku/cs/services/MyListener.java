package ku.cs.services;

import ku.cs.models.Address;
import ku.cs.models.Product;

public interface MyListener {

    public void onClickListener(Product product);
    public void onClickListener(Address address);

}
