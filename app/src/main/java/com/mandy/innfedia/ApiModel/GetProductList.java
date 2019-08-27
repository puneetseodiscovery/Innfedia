package com.mandy.innfedia.ApiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductList {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("special_price")
        @Expose
        private String specialPrice;
        @SerializedName("special_price_start")
        @Expose
        private String specialPriceStart;
        @SerializedName("special_price_end")
        @Expose
        private String specialPriceEnd;
        @SerializedName("qty")
        @Expose
        private Integer qty;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("stock")
        @Expose
        private String stock;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("seo_title")
        @Expose
        private Object seoTitle;
        @SerializedName("seo_key")
        @Expose
        private Object seoKey;
        @SerializedName("seo_desc")
        @Expose
        private Object seoDesc;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("length")
        @Expose
        private Object length;
        @SerializedName("height")
        @Expose
        private Object height;
        @SerializedName("weight")
        @Expose
        private Object weight;
        @SerializedName("product_cat_id")
        @Expose
        private Integer productCatId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSpecialPrice() {
            return specialPrice;
        }

        public void setSpecialPrice(String specialPrice) {
            this.specialPrice = specialPrice;
        }

        public String getSpecialPriceStart() {
            return specialPriceStart;
        }

        public void setSpecialPriceStart(String specialPriceStart) {
            this.specialPriceStart = specialPriceStart;
        }

        public String getSpecialPriceEnd() {
            return specialPriceEnd;
        }

        public void setSpecialPriceEnd(String specialPriceEnd) {
            this.specialPriceEnd = specialPriceEnd;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Object getSeoTitle() {
            return seoTitle;
        }

        public void setSeoTitle(Object seoTitle) {
            this.seoTitle = seoTitle;
        }

        public Object getSeoKey() {
            return seoKey;
        }

        public void setSeoKey(Object seoKey) {
            this.seoKey = seoKey;
        }

        public Object getSeoDesc() {
            return seoDesc;
        }

        public void setSeoDesc(Object seoDesc) {
            this.seoDesc = seoDesc;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getLength() {
            return length;
        }

        public void setLength(Object length) {
            this.length = length;
        }

        public Object getHeight() {
            return height;
        }

        public void setHeight(Object height) {
            this.height = height;
        }

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
        }

        public Integer getProductCatId() {
            return productCatId;
        }

        public void setProductCatId(Integer productCatId) {
            this.productCatId = productCatId;
        }

    }
}
