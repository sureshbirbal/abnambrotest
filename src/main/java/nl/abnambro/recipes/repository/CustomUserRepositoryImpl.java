package nl.abnambro.recipes.repository;

import nl.abnambro.recipes.data.UserModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public UserModel findByUsername(String username) {
        Query query = manager.createNativeQuery(
                "SELECT u.* FROM user AS u WHERE u.username = :username",
                UserModel.class);
        query.setParameter("username", username);
        List<UserModel> list = query.getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
