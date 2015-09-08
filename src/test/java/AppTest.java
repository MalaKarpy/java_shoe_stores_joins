import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.junit.Assert.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();


  // @Test
  //   public void addStoreTest() {
  //   goTo("http://localhost:4567/stores");
  //   fill("#name").with("Walmart");
  //   submit(".btn");
  //   assertThat(pageSource()).contains("Walmart");
  // }

  // @Test
  // public void allStoresBrandsAreDisplayedTest() {
  //   Store myStore = new Store("Ross!");
  //   myStore.save();
  //   Brand firstBrand = new Brand("Nike");
  //   firstBrand.save();
  //   Brand secondBrand = new Brand("puma");
  //   secondBrand.save();
  //   String storePath = String.format("http://localhost:4567/stores/%d", myStore.getId());
  //   goTo(storePath);
  //   assertThat(pageSource()).contains("Ross!");
  //   assertThat(pageSource()).contains("puma");
  //}

}
