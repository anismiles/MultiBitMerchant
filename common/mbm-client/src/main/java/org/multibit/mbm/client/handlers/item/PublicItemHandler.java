package org.multibit.mbm.client.handlers.item;

import com.google.common.base.Optional;
import com.yammer.dropwizard.client.JerseyClient;
import org.multibit.mbm.api.hal.HalMediaType;
import org.multibit.mbm.model.PublicItem;

import javax.ws.rs.core.HttpHeaders;
import java.net.URI;
import java.util.Locale;

/**
 * <p>Handler to provide the following to {@link org.multibit.mbm.client.PublicMerchantClient}:</p>
 * <ul>
 * <li>Construction of public single item requests</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class PublicItemHandler {

  private final JerseyClient jerseyClient;
  private final Locale locale;

  /**
   * @param jerseyClient The {@link com.yammer.dropwizard.client.JerseyClient} for consuming the upstream data
   * @param locale       The locale that applies to the request
   */
  public PublicItemHandler(JerseyClient jerseyClient, Locale locale) {
    this.jerseyClient = jerseyClient;
    this.locale = locale;
  }

  /**
   * Retrieve single item using its ID
   *
   * @param sku The required Stock Keeping Unit (SKU)
   * @return A matching {@link org.multibit.mbm.model.PublicItem}
   */
  public Optional<PublicItem> retrieveBySku(String sku) {

    // Sanity check
    // TODO How to sanity check an SKU?

    // TODO Replace "magic string" with auto-discover based on link rel
    String rawUri = String.format("http://localhost:8080/mbm/item/%s"
      ,sku);
    URI uri = URI.create(rawUri);

    // TODO Need to reconstruct from JSON
    PublicItem item = jerseyClient
      .resource(uri)
      .accept(HalMediaType.APPLICATION_HAL_JSON)
      .header(HttpHeaders.ACCEPT_LANGUAGE, locale.toString())
      .get(PublicItem.class);

    return Optional.of(item);
  }
}