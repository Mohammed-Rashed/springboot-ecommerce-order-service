CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        customer_id BIGINT NOT NULL,
                        status VARCHAR(30) NOT NULL,
                        total_amount NUMERIC(12, 2) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                        CONSTRAINT chk_orders_total_amount_non_negative
                            CHECK (total_amount >= 0)
);

CREATE TABLE order_items (
                             id BIGSERIAL PRIMARY KEY,
                             order_id BIGINT NOT NULL,
                             product_id BIGINT NOT NULL,
                             product_name VARCHAR(255) NOT NULL,
                             quantity INT NOT NULL,
                             unit_price NUMERIC(12, 2) NOT NULL,

                             CONSTRAINT fk_order_items_order
                                 FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,

                             CONSTRAINT chk_order_items_quantity_positive
                                 CHECK (quantity > 0),

                             CONSTRAINT chk_order_items_unit_price_positive
                                 CHECK (unit_price > 0)
);

CREATE INDEX idx_orders_customer_id ON orders(customer_id);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);