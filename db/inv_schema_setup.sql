-- Reset Script

-- Drop Triggers
BEGIN
    EXECUTE IMMEDIATE 'DROP TRIGGER trg_product_updated_at';
    EXECUTE IMMEDIATE 'DROP TRIGGER trg_supplier_updated_at';
    EXECUTE IMMEDIATE 'DROP TRIGGER trg_purchase_order_updated_at';
    EXECUTE IMMEDIATE 'DROP TRIGGER trg_purchase_order_item_updated_at';
    EXECUTE IMMEDIATE 'DROP TRIGGER trg_inventory_transaction_log';
EXCEPTION
    WHEN OTHERS THEN
        NULL; -- Ignore errors if the trigger does not exist
END;
/

-- Drop Indexes
BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_product_name';
    EXECUTE IMMEDIATE 'DROP INDEX idx_product_is_active';
    EXECUTE IMMEDIATE 'DROP INDEX idx_product_quantity_in_stock';
    EXECUTE IMMEDIATE 'DROP INDEX idx_supplier_name';
    EXECUTE IMMEDIATE 'DROP INDEX idx_supplier_contact_email';
    EXECUTE IMMEDIATE 'DROP INDEX idx_purchase_order_supplier_id';
    EXECUTE IMMEDIATE 'DROP INDEX idx_purchase_order_order_date';
    EXECUTE IMMEDIATE 'DROP INDEX idx_purchase_order_status_id';
    EXECUTE IMMEDIATE 'DROP INDEX idx_purchase_order_item_order_id';
    EXECUTE IMMEDIATE 'DROP INDEX idx_purchase_order_item_product_id';
    EXECUTE IMMEDIATE 'DROP INDEX idx_inventory_transaction_product_id';
    EXECUTE IMMEDIATE 'DROP INDEX idx_inventory_transaction_type_id';
    EXECUTE IMMEDIATE 'DROP INDEX idx_inventory_transaction_transaction_date';
EXCEPTION
    WHEN OTHERS THEN
        NULL; -- Ignore errors if the index does not exist
END;
/

-- Drop Sequences
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE product_seq';
    EXECUTE IMMEDIATE 'DROP SEQUENCE supplier_seq';
    EXECUTE IMMEDIATE 'DROP SEQUENCE purchase_order_seq';
    EXECUTE IMMEDIATE 'DROP SEQUENCE purchase_order_item_seq';
    EXECUTE IMMEDIATE 'DROP SEQUENCE inventory_transaction_seq';
    commit;
EXCEPTION
    WHEN OTHERS THEN
        NULL; -- Ignore errors if the sequence does not exist
END;
/

-- Drop Tables
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE inventory_transaction CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE purchase_order_item CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE purchase_order CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE supplier CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE product CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE inventory_transaction_type CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE purchase_order_status CASCADE CONSTRAINTS';

EXCEPTION
    WHEN OTHERS THEN
        NULL; -- Ignore errors if the table does not exist
END;
/

-- Drop Lookup Tables
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE inventory_transaction_type CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE purchase_order_status CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        NULL; -- Ignore errors if the lookup tables do not exist
END;
/

commit;

-- Recreate Sequences
CREATE SEQUENCE product_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
COMMIT;

CREATE SEQUENCE supplier_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
COMMIT;

CREATE SEQUENCE purchase_order_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
COMMIT;

CREATE SEQUENCE purchase_order_item_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
COMMIT;

CREATE SEQUENCE inventory_transaction_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
COMMIT;

-- Recreate Lookup Tables

-- Lookup Table for Purchase Order Status
CREATE TABLE purchase_order_status (
    status_id NUMBER PRIMARY KEY,
    status_name VARCHAR2(50) UNIQUE NOT NULL,
    status_description VARCHAR2(255),
    is_active NUMBER(1) DEFAULT 1 CHECK (is_active IN (0, 1))
    );

COMMIT;

-- Comments on Purchase Order Status Table
COMMENT ON TABLE purchase_order_status IS 'Stores possible statuses for purchase orders.';
COMMENT ON COLUMN purchase_order_status.status_id IS 'Unique identifier for each purchase order status.';
COMMENT ON COLUMN purchase_order_status.status_name IS 'Unique name for each purchase order status.';
COMMENT ON COLUMN purchase_order_status.status_description IS 'Description of the purchase order status.';
COMMIT;

-- Default Statuses for Purchase Orders
INSERT INTO purchase_order_status (status_id, status_name, status_description)
VALUES (1, 'Pending', 'Order has been placed but not yet processed.');

INSERT INTO purchase_order_status (status_id, status_name, status_description)
VALUES (2, 'Processing', 'Order is being processed by the supplier.');

INSERT INTO purchase_order_status (status_id, status_name, status_description)
VALUES (3, 'Shipped', 'Order has been shipped by the supplier.');

INSERT INTO purchase_order_status (status_id, status_name, status_description)
VALUES (4, 'Delivered', 'Order has been delivered to the customer.');

INSERT INTO purchase_order_status (status_id, status_name, status_description)
VALUES (5, 'Cancelled', 'Order has been cancelled by the customer or supplier.');

COMMIT;

-- Lookup Table for Inventory Transaction Types
CREATE TABLE inventory_transaction_type (
    type_id NUMBER PRIMARY KEY,
    type_name VARCHAR2(50) UNIQUE NOT NULL,
    type_description VARCHAR2(255),
    is_active NUMBER(1) DEFAULT 1 CHECK (is_active IN (0, 1))
    );

COMMIT;

-- Comments on Inventory Transaction Type Table
COMMENT ON TABLE inventory_transaction_type IS 'Stores possible types for inventory transactions.';
COMMENT ON COLUMN inventory_transaction_type.type_id IS 'Unique identifier for each inventory transaction type.';
COMMENT ON COLUMN inventory_transaction_type.type_name IS 'Unique name for each inventory transaction type.';
COMMENT ON COLUMN inventory_transaction_type.type_description IS 'Description of the inventory transaction type.';
COMMIT;

-- Default Types for Inventory Transactions
INSERT INTO inventory_transaction_type (type_id, type_name, type_description)
VALUES (1, 'Purchase', 'Stock added to inventory through a purchase order.');

INSERT INTO inventory_transaction_type (type_id, type_name, type_description)
VALUES (2, 'Sale', 'Stock removed from inventory through a sale.');

INSERT INTO inventory_transaction_type (type_id, type_name, type_description)
VALUES (3, 'Adjustment', 'Stock quantity adjusted manually.');

INSERT INTO inventory_transaction_type (type_id, type_name, type_description)
VALUES (4, 'Return', 'Stock returned to inventory.');

INSERT INTO inventory_transaction_type (type_id, type_name, type_description)
VALUES (5, 'Transfer', 'Stock transferred between locations.');

INSERT INTO inventory_transaction_type (type_id, type_name, type_description)
VALUES (6, 'Waste', 'Stock disposed of due to damage or expiration.');

INSERT INTO inventory_transaction_type (type_id, type_name, type_description)
VALUES (7, 'Other', 'Other types of inventory transactions.');

COMMIT;

-- Recreate Tables

-- Product Table
CREATE TABLE product (
    product_id NUMBER DEFAULT product_seq.NEXTVAL PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    description CLOB,
    price NUMBER(10, 2) NOT NULL,
    cost NUMBER(10, 2) NOT NULL,
    quantity_in_stock NUMBER DEFAULT 0 NOT NULL,
    reorder_level NUMBER DEFAULT 0 NOT NULL,
    is_active NUMBER(1) DEFAULT 1 CHECK (is_active IN (0, 1)), -- Changed to 0 or 1
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMIT;

-- Comments on Product Table
COMMENT ON TABLE product IS 'Stores information about each product in the inventory.';
COMMENT ON COLUMN product.product_id IS 'Unique identifier for each product.';
COMMENT ON COLUMN product.name IS 'Name of the product.';
COMMENT ON COLUMN product.description IS 'Detailed description of the product.';
COMMENT ON COLUMN product.price IS 'Selling price of the product.';
COMMENT ON COLUMN product.cost IS 'Cost price of the product.';
COMMENT ON COLUMN product.quantity_in_stock IS 'Current stock quantity of the product.';
COMMENT ON COLUMN product.reorder_level IS 'Quantity at which reorder should be triggered.';
COMMENT ON COLUMN product.is_active IS 'Status indicating if the product is active (1) or inactive (0).';
COMMENT ON COLUMN product.created_at IS 'Timestamp when the product record was created.';
COMMENT ON COLUMN product.updated_at IS 'Timestamp when the product record was last updated.';
COMMIT;

-- Supplier Table
CREATE TABLE supplier (
    supplier_id NUMBER DEFAULT supplier_seq.NEXTVAL PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    contact_email VARCHAR2(255) NOT NULL,
    phone VARCHAR2(20) NOT NULL,
    address CLOB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMIT;

-- Comments on Supplier Table
COMMENT ON TABLE supplier IS 'Stores information about suppliers who provide products.';
COMMENT ON COLUMN supplier.supplier_id IS 'Unique identifier for each supplier.';
COMMENT ON COLUMN supplier.name IS 'Name of the supplier.';
COMMENT ON COLUMN supplier.contact_email IS 'Contact email address of the supplier.';
COMMENT ON COLUMN supplier.phone IS 'Phone number of the supplier.';
COMMENT ON COLUMN supplier.address IS 'Address of the supplier.';
COMMENT ON COLUMN supplier.created_at IS 'Timestamp when the supplier record was created.';
COMMENT ON COLUMN supplier.updated_at IS 'Timestamp when the supplier record was last updated.';
COMMIT;

-- Purchase Order Table
CREATE TABLE purchase_order (
    order_id NUMBER DEFAULT purchase_order_seq.NEXTVAL PRIMARY KEY,
    supplier_id NUMBER REFERENCES supplier(supplier_id),
    order_date DATE NOT NULL,
    status_id NUMBER DEFAULT 1 REFERENCES purchase_order_status(status_id), -- Foreign key referencing the status lookup table
    total_cost NUMBER(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMIT;

-- Comments on Purchase Order Table
COMMENT ON TABLE purchase_order IS 'Records details of orders placed with suppliers for purchasing products.';
COMMENT ON COLUMN purchase_order.order_id IS 'Unique identifier for each purchase order.';
COMMENT ON COLUMN purchase_order.supplier_id IS 'Foreign key referencing the supplier for the order.';
COMMENT ON COLUMN purchase_order.order_date IS 'Date when the purchase order was placed.';
COMMENT ON COLUMN purchase_order.status_id IS 'Foreign key referencing the status of the purchase order.';
COMMENT ON COLUMN purchase_order.total_cost IS 'Total cost of the purchase order.';
COMMENT ON COLUMN purchase_order.created_at IS 'Timestamp when the purchase order was created.';
COMMENT ON COLUMN purchase_order.updated_at IS 'Timestamp when the purchase order was last updated.';
COMMIT;

-- Purchase Order Item Table
CREATE TABLE purchase_order_item (
    item_id NUMBER DEFAULT purchase_order_item_seq.NEXTVAL PRIMARY KEY,
    order_id NUMBER REFERENCES purchase_order(order_id),
    product_id NUMBER REFERENCES product(product_id),
    quantity_ordered NUMBER NOT NULL,
    unit_cost NUMBER(10, 2) NOT NULL,
    total_cost NUMBER(10, 2) NOT NULL,
    received_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMIT;

-- Comments on Purchase Order Item Table
COMMENT ON TABLE purchase_order_item IS 'Contains details of each item within a purchase order.';
COMMENT ON COLUMN purchase_order_item.item_id IS 'Unique identifier for each purchase order item.';
COMMENT ON COLUMN purchase_order_item.order_id IS 'Foreign key referencing the associated purchase order.';
COMMENT ON COLUMN purchase_order_item.product_id IS 'Foreign key referencing the product being ordered.';
COMMENT ON COLUMN purchase_order_item.quantity_ordered IS 'Quantity of the product ordered.';
COMMENT ON COLUMN purchase_order_item.unit_cost IS 'Cost per unit of the product.';
COMMENT ON COLUMN purchase_order_item.total_cost IS 'Total cost for the ordered quantity of the product.';
COMMENT ON COLUMN purchase_order_item.received_date IS 'Date when the items were received.';
COMMENT ON COLUMN purchase_order_item.created_at IS 'Timestamp when the purchase order item record was created.';
COMMENT ON COLUMN purchase_order_item.updated_at IS 'Timestamp when the purchase order item record was last updated.';
COMMIT;

-- Inventory Transaction Table
CREATE TABLE inventory_transaction (
    transaction_id NUMBER DEFAULT inventory_transaction_seq.NEXTVAL PRIMARY KEY,
    product_id NUMBER REFERENCES product(product_id),
    type_id NUMBER REFERENCES inventory_transaction_type(type_id), -- Foreign key referencing the transaction type lookup table
    transaction_date DATE NOT NULL,
    quantity NUMBER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMIT;

-- Comments on Inventory Transaction Table
COMMENT ON TABLE inventory_transaction IS 'Logs all inventory transactions affecting stock levels.';
COMMENT ON COLUMN inventory_transaction.transaction_id IS 'Unique identifier for each inventory transaction.';
COMMENT ON COLUMN inventory_transaction.product_id IS 'Foreign key referencing the product involved in the transaction.';
COMMENT ON COLUMN inventory_transaction.type_id IS 'Foreign key referencing the type of inventory transaction.';
COMMENT ON COLUMN inventory_transaction.transaction_date IS 'Date when the transaction occurred.';
COMMENT ON COLUMN inventory_transaction.quantity IS 'Quantity of the product involved in the transaction.';
COMMENT ON COLUMN inventory_transaction.created_at IS 'Timestamp when the inventory transaction record was created.';
COMMENT ON COLUMN inventory_transaction.updated_at IS 'Timestamp when the inventory transaction record was last updated.';
COMMIT;

-- Recreate Indexes

-- Product Indexes
CREATE INDEX idx_product_name ON product(name);
CREATE INDEX idx_product_is_active ON product(is_active);
CREATE INDEX idx_product_quantity_in_stock ON product(quantity_in_stock);
COMMIT;

-- Supplier Indexes
CREATE INDEX idx_supplier_name ON supplier(name);
CREATE INDEX idx_supplier_contact_email ON supplier(contact_email);
COMMIT;

-- Purchase Order Indexes
CREATE INDEX idx_purchase_order_supplier_id ON purchase_order(supplier_id);
CREATE INDEX idx_purchase_order_order_date ON purchase_order(order_date);
CREATE INDEX idx_purchase_order_status_id ON purchase_order(status_id);
COMMIT;

-- Purchase Order Item Indexes
CREATE INDEX idx_purchase_order_item_order_id ON purchase_order_item(order_id);
CREATE INDEX idx_purchase_order_item_product_id ON purchase_order_item(product_id);
COMMIT;

-- Inventory Transaction Indexes
CREATE INDEX idx_inventory_transaction_product_id ON inventory_transaction(product_id);
CREATE INDEX idx_inventory_transaction_type_id ON inventory_transaction(type_id);
CREATE INDEX idx_inventory_transaction_transaction_date ON inventory_transaction(transaction_date);
COMMIT;

-- Recreate Triggers

-- Trigger to Update `updated_at` Column in `product` Table
CREATE OR REPLACE TRIGGER trg_product_updated_at
BEFORE UPDATE ON product
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
COMMIT;

-- Trigger to Update `updated_at` Column in `supplier` Table
CREATE OR REPLACE TRIGGER trg_supplier_updated_at
BEFORE UPDATE ON supplier
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
COMMIT;

-- Trigger to Update `updated_at` Column in `purchase_order` Table
CREATE OR REPLACE TRIGGER trg_purchase_order_updated_at
BEFORE UPDATE ON purchase_order
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
COMMIT;

-- Trigger to Update `updated_at` Column in `purchase_order_item` Table
CREATE OR REPLACE TRIGGER trg_purchase_order_item_updated_at
BEFORE UPDATE ON purchase_order_item
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

COMMIT;

exit sql.sqlcode;
