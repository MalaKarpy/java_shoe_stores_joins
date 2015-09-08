import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class StoreTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Store.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Store firstStore = new Store("walmart");
    Store secondStore = new Store("walmart");
    assertTrue(firstStore.equals(secondStore));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Store myStore = new Store("walmart");
    myStore.save();
    Store savedStore = Store.getFirstDBEntry();
    assertTrue(savedStore.equals(myStore));
  }

  @Test
  public void save_assignsIdToObject() {
    Store myStore = new Store("walmart");
    myStore.save();
    Store savedStore = Store.getFirstDBEntry();
    assertEquals(myStore.getId(), savedStore.getId());
  }

  @Test
  public void find_findsStoreInDatabase_true() {
    Store myStore = new Store("walmart");
    myStore.save();
    Store savedStore = Store.find(myStore.getId());
    assertTrue(myStore.equals(savedStore));
  }

  @Test
  public void addBrand_addsBrandToStore() {
    Brand myBrand = new Brand("Nike");
    myBrand.save();

    Store myStore = new Store("walmart");
    myStore.save();

    myStore.addBrand(myBrand);
    Brand savedBrand = myStore.getBrands().get(0);
    assertTrue(myBrand.equals(savedBrand));
  }

  @Test
  public void getBrands_returnsAllBrands_ArrayList() {
    Brand myBrands = new Brand("Nike");
    myBrands.save();

    Store myStore = new Store("walmart");
    myStore.save();

    myStore.addBrand(myBrands);
    List savedBrands = myStore.getBrands();
    assertEquals(savedBrands.size(), 1);
  }

}
