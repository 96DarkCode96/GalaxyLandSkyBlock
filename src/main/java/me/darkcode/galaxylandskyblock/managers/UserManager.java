package me.darkcode.galaxylandskyblock.managers;

import me.darkcode.galaxylandskyblock.objects.User;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Used to manage Users at server!
 */
public class UserManager {

    private final ArrayList<User> usersList;

    public UserManager(){ usersList = new ArrayList<>(); }

    /**
     * Load user to temp. list of users!
     *
     * @param uuid  UUID of user you want to load.
     */
    public void loadUser(UUID uuid){
        usersList.add(new User(uuid));
    }

    /**
     * Unload user from temp. list of users!
     *
     * @param uuid  UUID of user you want to unload.
     */
    public void unloadUser(UUID uuid){
        try {
            usersList.remove(findUser(uuid));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Find user by UUID from list of users!
     *
     * @param uuid  User's UUID you're looking for!
     * @return  Return user you're finding!
     * @throws Exception    This Exception is called, when user is not found!
     */
    public User findUser(UUID uuid) throws Exception {
        for(User user : getUsersList()){
            if(user.getUuid().equals(uuid)){
                return user;
            }
        }
        throw new Exception("User not found!");
    }

    /**
     * Find user by Username from list of users!
     *
     * @param userName  User's name you're looking for!
     * @return  Return user you're finding!
     * @throws Exception    This Exception is called, when user is not found!
     */
    public User findUser(String userName) throws Exception {
        for(User user : getUsersList()){
            if(user.getName().equalsIgnoreCase(userName)){
                return user;
            }
        }
        throw new Exception("User not found!");
    }

    /**
     * Get list of users!
     *
     * @return  List of Users.
     */
    public ArrayList<User> getUsersList() {
        return usersList;
    }
}
