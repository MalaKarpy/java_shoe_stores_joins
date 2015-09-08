import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Brand {

  private int id;
  private String description;

  public Brand(String description) {
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public static List<Brand> all() {
    String sql = "SELECT id, description FROM brands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Brand.class);
    }
  }

  @Override
  public boolean equals(Object otherBrand){
    if (!(otherBrand instanceof Brand)) {
      return false;
    } else {
      Brand newBrand = (Brand) otherBrand;
      return this.getDescription().equals(newBrand.getDescription());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Brands(description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public static Brand find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM brands where id=:id";
      Brand Brand = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Brand.class);
      return Brand;
    }
  }

  public void addStore(Store store) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores_brands (store_id, brand_id)" +
                   " VALUES (:store_id, :brand_id)";
      con.createQuery(sql)
        .addParameter("store_id", store.getId())
        .addParameter("brand_id", this.getId())
        .executeUpdate();
    }
  }

  public List<Store> getStores() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT stores.* FROM brands JOIN stores_brands ON (stores_brands.brand_id = brands.id) JOIN stores ON (stores_brands.store_id = stores.id) WHERE brand_id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Store.class);
      }
    }


}
