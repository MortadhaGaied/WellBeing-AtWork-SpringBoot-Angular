package com.wellbeignatwork.backend.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FirebaseCredential {
    String type;
    String project_id;
    String private_key_id;
    String private_key;
    String client_email;
    String client_id;
    String auth_uri;
    String token_uri;
    String auth_provider_x509_cert_url;
    String client_x509_cert_url;

}
