package com.ilemgroup.inventory.product;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductDaoImp implements ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImp(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT * FROM product where product_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new ProductRowMapper());

    }

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO product (name, description, price, cost, quantity_in_stock, reorder_level, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getPrice(), product.getCost(), product.getQuantity_in_stock(), product.getReorder_level(), product.getIs_active());
    }

    @Override
    public void update (Product product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, cost = ?, quantity_in_stock = ?, reorder_level = ?, is_active = ? WHERE product_id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getPrice(), product.getCost(), product.getQuantity_in_stock(), product.getReorder_level(), product.getIs_active(), product.getProduct_id());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM product where product_id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setProduct_id(rs.getLong("product_id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getFloat("price"));
            product.setPrice(rs.getFloat("cost"));
            product.setPrice(rs.getInt("quantity_in_stock"));
            product.setReorder_level(rs.getInt("reorder_level"));
            product.setIs_active(rs.getBoolean("is_active"));
            product.setCreated_at(rs.getTimestamp("created_at"));
            product.setUpdated_at(rs.getTimestamp("updated_at"));
            return product;
        }
        
    }
}
