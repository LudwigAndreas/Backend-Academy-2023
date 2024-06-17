package ru.tinkoff.seminars.accounting.auth.keycloak;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsProvider {

    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }

}
