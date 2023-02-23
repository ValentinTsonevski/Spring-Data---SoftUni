package com.example.gamestore.domain.dtos;


import java.math.BigDecimal;
import java.time.LocalDate;

public class GameDTO {

    private String title;
    private BigDecimal price;
    private double size;
    private String trailer;
    private String url;
    private String description;
    private LocalDate releaseDate;

    public GameDTO() {
    }

    public GameDTO(String title, BigDecimal price, double size, String trailer, String url, String description, LocalDate releaseDate) {
        setTitle(title);
        setPrice(price);
        setSize(size);
        setTrailer(trailer);
        setUrl(url);
        setDescription(description);
        setReleaseDate(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (!Character.isUpperCase(title.charAt(0)) || title.length() < 3 || title.length() > 100) {
            throw new IllegalArgumentException("Incorrect title");
        }
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private BigDecimal zero = new BigDecimal(0);

    public void setPrice(BigDecimal price) {

        if (price.longValue() < 0) {
            throw new IllegalArgumentException("Price must be positive number");
        }
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be positive number");
        }
        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        if (trailer.length() != 11) {
            throw new IllegalArgumentException("Length must be exact 11.");
        }
        this.trailer = trailer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("Unknown url");
        }
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.length() < 20) {
            throw new IllegalArgumentException("Description length must be at least 20");
        }
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
