package com.myretail.product.adaptor;

import com.myretail.product.connector.MongoConnector;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MongoAdaptor {

    private final MongoConnector mongoConnector;

    public MongoAdaptor(MongoConnector mongoConnector) {
        this.mongoConnector = mongoConnector;
    }

    public Mono<Price> productPrice(Long id) {

        Mono<Product> product = mongoConnector.findById(id);

        return product.map(p -> Price.builder()
                .value(p.getCurrentPrice().getValue())
                .currencyCode(p.getCurrentPrice().getCurrencyCode())
                .build());
        //return Mono.just(Price.builder().value(12.12).currencyCode("USD").build());
    }

    public Mono<Product> updatePrice(Price price, Long id) {
        Product product = Product.builder()
                .id(id)
                .currentPrice(price)
                .build();
        return mongoConnector.insert(product);
    }
}
