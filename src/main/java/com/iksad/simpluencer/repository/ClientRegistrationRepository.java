package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.model.ClientRegistration;

public interface ClientRegistrationRepository {
    ClientRegistration findByRegistrationId(String typeProvider);
}
