-- Product Table
CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_category VARCHAR(255) NOT NULL,
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    added_by VARCHAR(255) NOT NULL
);

-- Product Price Table
CREATE TABLE product_price (
    price_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    product_price DECIMAL(10, 2) NOT NULL,
    curr_discount DECIMAL(5, 2) DEFAULT 0,
    time_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- Product Price Change Log
CREATE TABLE product_price_change_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    old_price DECIMAL(10, 2) NOT NULL,
    updated_price DECIMAL(10, 2) NOT NULL,
    old_discount DECIMAL(5, 2) NOT NULL,
    updated_discount DECIMAL(5, 2) NOT NULL,
    op_time_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    op_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    op_performed_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- Query to JOIN product and product price
SELECT
    prod.name AS product_name,
    prod.category AS product_category,
    prodp.product_price AS product_price,
    prodp.updated_by AS updated_by,
    prodp.time_updated AS updated_time
FROM
    product prod
JOIN
    product_price prodp ON prod.product_id = prodp.product_id;