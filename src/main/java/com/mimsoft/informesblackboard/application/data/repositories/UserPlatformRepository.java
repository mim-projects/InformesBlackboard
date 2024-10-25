package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.UserPlatform;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import java.util.List;

@RequestScoped
public class UserPlatformRepository {

    @Inject
    @RepositoryClass(UserPlatform.class)
    private Repository<UserPlatform> repository;

    public UserPlatform findByUsernamePassword(String username, String password) {
        return repository.findOneWhereEqual(
                new String[]{"username", "password"},
                new Object[]{"'" + username + "'", "'" + password + "'"}
        );
    }

    public UserPlatform findById(Integer id) {
        return repository.findId(id);
    }

    public UserPlatform findByUsername(String username) {
        return repository.findOne("username", "'" + username + "'");
    }

    public void update(UserPlatform currenUser) {
        repository.update(currenUser);
    }

    public void updatelang(UserPlatform currenUser, String lang) {
        System.out.println("entre a repositorio de updatelang en UserPlatformRepository ");
        currenUser.setLanguage(lang); // Actualiza el campo 'theme'
        repository.update(currenUser);
    }
   
    // Método para obtener el tema actual del usuario
    public String getCurrentlang(UserPlatform currentUser) {
        System.out.println("Obteniendo el tema actual del usuario.");
        return currentUser.getLanguage(); // Devuelve el tema actual
    }

    // Método actualizado para recibir el tema
    public void updatetheme(UserPlatform currenUser, String theme) {
        System.out.println("entre a repositorio de updatetheme en UserPlatformRepository ");
        currenUser.setTheme(theme); // Actualiza el campo 'theme'
        repository.update(currenUser);
    }

    // Método para obtener el tema actual del usuario
    public String getCurrentTheme(UserPlatform currentUser) {
        System.out.println("Obteniendo el tema actual del usuario.");
        return currentUser.getTheme(); // Devuelve el tema actual
    }

    public List<UserPlatform> findAll() {
        return repository.findAll();
    }

    public void create(UserPlatform item) {
        repository.create(item);
    }

    public void remove(UserPlatform item) {
        repository.delete(item);
    }
}
