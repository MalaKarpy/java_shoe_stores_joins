import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    /* Index */
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
     model.put("template", "templates/index.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());




    get("/stores", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Store> stores = Store.all();
      model.put("stores", stores);
      model.put("template", "templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  post("/stores", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        String name = request.queryParams("name");

        Store newStore = new Store(name);
        newStore.save();
        response.redirect("/stores");
        return null;
      });

  get("/stores/:id", (request,response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params(":id"));
        Store store = Store.find(id);
        model.put("store", store);
        model.put("allBrands", Brand.all());
        model.put("template", "templates/store.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());


  post("/add_brands", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int store_id = Integer.parseInt(request.queryParams("store_id"));
        int brand_id = Integer.parseInt(request.queryParams("brand_id"));
        Brand brand = Brand.find(brand_id);
        Store store = Store.find(store_id);
        store.addBrand(brand);
        response.redirect("/stores/" + store_id);
        return null;
      });

  get("/stores/:id/edit", (request,response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params(":id"));
        Store store = Store.find(id);
        model.put("store", store);

        model.put("template", "templates/store-edit-form.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

  post("/stores/:id/edit", (request,response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params(":id"));
        Store store  = Store.find(id);
        String new_store_name = request.queryParams("new_store_name");
        store.update(new_store_name);

        model.put("store", store);
        model.put("allBrands", Brand.all());
        model.put("template", "templates/store.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

  get("/stores/:id/delete", (request,response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params(":id"));
        Store store = Store.find(id);
        store.delete();
        response.redirect("/stores");
        return null;
      });

  get("/brands", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    List<Brand> brands = Brand.all();
    model.put("brands", brands);
    model.put("template", "templates/brands.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/brands", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        String description = request.queryParams("description");
        Brand newBrand = new Brand(description);
        newBrand.save();
        response.redirect("/brands");
        return null;
      });

  get("/brands/:id", (request,response) ->{
        HashMap<String, Object> model = new HashMap<String, Object>();
        int newid = Integer.parseInt(request.params(":id"));
        Brand brand = Brand.find(newid);
        model.put("brand", brand);
        model.put("allStores", Store.all());
        model.put("template", "templates/brand.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());


  post("/add_stores", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int store_id = Integer.parseInt(request.queryParams("store_id"));
        int brand_id = Integer.parseInt(request.queryParams("brand_id"));
        Brand brand = Brand.find(brand_id);
        Store store = Store.find(store_id);
        brand.addStore(store);
        response.redirect("/brands/" + brand_id);
        return null;
      });

}
}
