package app.quickfood.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RestaurantModel {

    @SerializedName("results")
    public List<Datauser> data = new ArrayList<>();



    public class Datauser {

        String name;

        @SerializedName("formatted_address")
        String address;

        @SerializedName("rating")
        float rating;

        @SerializedName("opening_hours")
        JsonObject openinghours;

        @SerializedName("photos")
        JsonArray photos;

        @SerializedName("icon")
        String image;

        @SerializedName("geometry")
        JsonObject geometry;

        public JsonObject getGeometry() {
            return geometry;
        }

        public void setGeometry(JsonObject geometry) {
            this.geometry = geometry;
        }

        public JsonArray getPhotos() {
            return photos;
        }

        public void setPhotos(JsonArray photos) {
            this.photos = photos;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public JsonObject getOpeninghours() {
            return openinghours;
        }

        public void setOpeninghours(JsonObject openinghours) {
            this.openinghours = openinghours;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

    }

    public class OpeningHours {

        @SerializedName("open_now")
        Boolean openNow;

        public Boolean getOpenNow() {
            return openNow;
        }

        public void setOpenNow(Boolean openNow) {
            this.openNow = openNow;
        }
    }

    public  class PhotoReference{

        @SerializedName("photo_reference")
        String imgRef;

        public String getImgRef() {
            return imgRef;
        }

        public void setImgRef(String imgRef) {
            this.imgRef = imgRef;
        }
    }

    public class Geometry{

        @SerializedName("location")
        JsonObject location;

        public JsonObject getLocation() {
            return location;
        }

        public void setLocation(JsonObject location) {
            this.location = location;
        }
    }
}