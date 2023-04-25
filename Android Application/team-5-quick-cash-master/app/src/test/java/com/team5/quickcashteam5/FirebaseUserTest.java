package com.team5.quickcashteam5;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FirebaseUserTest {
    /**
     * STATIC VARIABLES FOR TESTS
     */
    private final String FirstName = "Frederick";
    private final String LastName = "Hoekstra";
    private final String Email = "testAdmin@team5.ca";

    /**
     * COMMON VARIABLES FOR TESTS
     */
    private FirebaseUser user;

    @Before
    public void CreateFirebaseUser() {
        user = new FirebaseUser(FirstName, LastName, Email);
    }

    @Test
    public void FirebaseUserGetFirstName() {
        assert(FirstName.equals(user.firstname));
    }

    @Test
    public void FirebaseUserGetLastName() {
        assert(LastName.equals(user.lastname));
    }

    @Test
    public void FirebaseUserGetEmail() {
        assert(Email.equals(user.email));
    }
}
