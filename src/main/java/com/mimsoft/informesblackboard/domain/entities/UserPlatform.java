package com.mimsoft.informesblackboard.domain.entities;

import com.mimsoft.informesblackboard.domain.core.EntityCore;
import jakarta.persistence.*;

@Entity
@Table(name = "user_platform")
public class UserPlatform extends EntityCore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @JoinColumn(name = "user_platform_roles_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserPlatformRoles userPlatformRolesId;

    public UserPlatform() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserPlatformRoles getUserPlatformRolesId() {
        return userPlatformRolesId;
    }

    public void setUserPlatformRolesId(UserPlatformRoles userPlatformRolesId) {
        this.userPlatformRolesId = userPlatformRolesId;
    }

    @Override
    public String toString() {
        return "UserPlatform{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userPlatformRolesId=" + userPlatformRolesId +
                '}';
    }
}
